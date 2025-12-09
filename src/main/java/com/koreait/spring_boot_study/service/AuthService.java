package com.koreait.spring_boot_study.service;

import com.koreait.spring_boot_study.dto.req.SignInReqDto;
import com.koreait.spring_boot_study.dto.req.SignUpReqDto;
import com.koreait.spring_boot_study.dto.res.SignInResDto;
import com.koreait.spring_boot_study.entity.RefreshToken;
import com.koreait.spring_boot_study.entity.User;
import com.koreait.spring_boot_study.exception.RefreshTokenException;
import com.koreait.spring_boot_study.exception.UserException;
import com.koreait.spring_boot_study.repository.mapper.RefreshTokenMapper;
import com.koreait.spring_boot_study.repository.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import com.koreait.spring_boot_study.jwt.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service    @RequiredArgsConstructor // final필드만 초기화하는 생성자. final필드에 대해서 자동으로 autowired됨.(다른 생성자 없을때)
public class AuthService {
    private final RefreshTokenMapper refreshTokenMapper;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    @Value("${jwt.refresh-expire-millis}")
    private long refreshExpireMillis;

    //refresh 토큰 저장
    private void saveRefreshToken(int userId , String refreshToken){
        LocalDateTime expireAt = LocalDateTime . now() . plus(refreshExpireMillis , ChronoUnit.MILLIS);
        int successCount = refreshTokenMapper . insertRefreshToken(userId , refreshToken , expireAt);

        if(successCount <= 0){
          throw new RefreshTokenException("refresh토큰 저장 오류 발생" , HttpStatus.INTERNAL_SERVER_ERROR); // INTERNAL_SERVER_ERROR = 500
        }
    }

    //refresh 토큰 업데이트
    private void rotateRefreshToken(String oldToken , String newToken){
        int successCount = refreshTokenMapper . updateRefreshToken(oldToken , newToken);

        if(successCount <= 0){
            //todo : 예외
        }
    }

    // User 객체만 가져오면 토큰 쌍으로 바꿔서 리턴
    private SignInResDto generateTokenPair(User user) {
        // 토큰에 담을 sub, extraClaims를 User로부터 추출
        String sub = String.valueOf(user.getUserId());

        Map<String, Object> extraClaims = Map.of(
                "role", user.getRole().getRoleName()
        );

        // TokenPair 생성
        String accessToken = jwtUtil.generateAccessToken(sub, extraClaims);
        String refreshToken = jwtUtil.generateRefreshToken(sub);

        return new SignInResDto(accessToken, refreshToken);
    }

    public void signUp(SignUpReqDto dto) {
        //1.아이디 , 이메일 중복검사
        boolean isDuplicatedUserName = userMapper.getUserByUserName(dto.getUserName()).isPresent(); //Optional 안에 값 있으면 true.
        if (isDuplicatedUserName) throw new UserException("이미 존재하는 아이디입니다", HttpStatus.CONFLICT);

        boolean isDuplicatedEmail = userMapper.getUserByEmail(dto.getEmail()).isPresent();
        if (isDuplicatedEmail) throw new UserException("이미 존재하는 이메일입니다", HttpStatus.CONFLICT);

        // 2.dto -> entity
        User user = dto.toEntity();
        //password 암호화해서 set해줘야함.
        user.setPassword(encoder.encode(dto.getPassword()));

        //3.db에 저장
        int successCount = userMapper.addUser(user);
        if (successCount <= 0) throw new UserException("회원가입중 에러가 발생하였습니다", HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }
        // 로그인
        @Transactional(rollbackFor = Exception.class)
        public SignInResDto signIn (SignInReqDto dto){
            //실제 아이디 있는지 검사
            User user = userMapper.getUserByUserName(dto.getUserName())
                    .orElseThrow(() -> new UserException("사용자 정보를 잘못 입력하였습니다.", HttpStatus.BAD_REQUEST));

            // 비밀번호 확인
            if (!encoder.matches(dto.getPassword(), user.getPassword())) {// encoder.matches(평문암호 , 암호화된 암호) 이 둘이 동일하면 true ,틀리면 false.
                throw new UserException("사용자 정보를 잘못 입력하였습니다.", HttpStatus.BAD_REQUEST); // 비밀번호 틀렸을때
            }

            //id , pw 모두 통과 -> 로그인 시켜줘야함 -> 토큰 발급.
            SignInResDto tokenPair = generateTokenPair(user);

            //refresh 토큰을 db에 저장
            saveRefreshToken(user.getUserId() , tokenPair.getRefreshToken());

            return tokenPair;
        }

        @Transactional(rollbackFor = Exception.class)
        public SignInResDto refreshToken(String refresh) {
            // 1. 타입 검증
            if(!jwtUtil.isRefreshToken(refresh)) { // refresh토큰이 아니라면
                throw new RefreshTokenException("refresh토큰이 아닙니다" , HttpStatus.BAD_REQUEST); // BAD_REQUEST = 400
            }
            //2. DB에 실제 있는 토큰인지 검사.
            refreshTokenMapper.findByToken(refresh) . orElseThrow(() -> new RefreshTokenException(
                    "refresh토큰이 유효하지 않습니다" , HttpStatus.UNAUTHORIZED)); // UNAUTHORIZED - 401


            // 3. 쿠키에서 가져온 토큰으로부터 claims 추출.
            Claims claims;
            try{
                claims = jwtUtil.getClaims(refresh);
            } catch(ExpiredJwtException e){
                // 리프레쉬 토큰마저 만료됐을 경우. DB에서 토큰을 제거해줘야함.(한번에 주기적으로 삭제하는 방법도 있음)
                refreshTokenMapper.deleteByToken(refresh);

                // 응답으로 에러메시지를 내려줌. -> 프론트에서 로그인창으로 리다이렉션.
                throw new RefreshTokenException(
                        Map.of("errorMsg" , "refresh토큰이 만료됐습니다" ,
                               "errorCode" , "RT_EXPIRED").toString() , HttpStatus.UNAUTHORIZED);
            } catch(JwtException e){ // 위조된 경우.(보험. 생길면 안되는 경우이지만 일단 작성.) db에서 삭제.
                refreshTokenMapper.deleteByToken(refresh);
                throw new RefreshTokenException("유효하지 않은 토큰입니다." , HttpStatus.UNAUTHORIZED);
            }

            //4. claims에서 subject(userId) 추출.
            String userIdStr = claims.get("sub" , String.class);
            int userId = Integer.parseInt(userIdStr);
            // ↓ userId로 조회해서 user없으면 에러 반환.
           User user = userMapper.getUserById(userId) . orElseThrow(() -> new UserException("사용자를 찾을 수 없습니다" , HttpStatus.NOT_FOUND)); // 404

            //5. 새토큰 발급.(rotation - 이전것 삭제하고 새로 발급)
            SignInResDto newTokens = generateTokenPair(user);

            //6. 기존 사용자의 모든 refresh 토큰 삭제.
            refreshTokenMapper.deleteAllByUserId(userId);

            //7. 새로 발급한 토큰으로 다시 저장.
            saveRefreshToken(userId , newTokens.getRefreshToken());

            return newTokens;
        }

        public void logout(String refreshToken){
            int successCount = refreshTokenMapper.deleteByToken(refreshToken);
            if(successCount <= 0){
                throw new RefreshTokenException("이미 로그아웃 했거나 유효하지 않은 접근입니다." , HttpStatus.UNAUTHORIZED);
            }
        }
}


















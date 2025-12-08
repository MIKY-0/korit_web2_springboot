package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.req.SignInReqDto;
import com.koreait.spring_boot_study.dto.req.SignUpReqDto;
import com.koreait.spring_boot_study.dto.res.SignInResDto;
import com.koreait.spring_boot_study.jwt.JwtUtil;
import com.koreait.spring_boot_study.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController     @RequestMapping("/auth")        @RequiredArgsConstructor
public class AuthController { // 회원가입 , 로그인 , 로그아웃
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @Value("${jwt.refresh-expire-millis}")
    private long refreshExpireMillis;

    //쿠키 : 특정 서버에서 쿠키를 내려주면 앞으로 모든 클라이언트 서버 http교신에서 헤더에 쿠키를 담고 있게 된다.(탈취하기 쉬움)
    //리프레쉬 토큰.(쿠키에 담아서 응답)
    private void addRefreshTokenCookie(String refreshToken , HttpServletResponse response) { //쿠키를 응답헤더에 담는 메서드.
        ResponseCookie cookie = ResponseCookie
                .from("refreshToken" , refreshToken)
                .httpOnly(false) // 실제 운영시 true여야됨 : JS(자바스크립트) 조작 못하게함.
                .secure(false) // 실제 운영시 ture여야됨 : https 프로토콜만 허용.
                .sameSite("Lax") //csrf 정책 : get요청은 허용.
                .path("/")
                .maxAge(-1) // 쿠키의 유효기간.   -1 : 탭종료시 쿠키도 삭제.
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE , cookie.toString());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> singUp(@RequestBody @Valid SignUpReqDto dto) {
        authService.signUp(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("계정생성 완료");
    }
        //논리적으로 getMapping이 맞으나 -> param등에 민감정보가 노출. -> body가 필요해서 postMapping.
        @PostMapping("/signin")
        public ResponseEntity<?> signIn(@RequestBody SignInReqDto reqDto ,
            //컨트롤러 : servlet Dispatcher가 일을 시키는 구조.
            //servlet Dispatcher가 request , response 객체 가지고 있음.
            HttpServletResponse response){
            SignInResDto resDto = authService.signIn(reqDto);

            //refreshToken은 cookie(헤더)에 담아서 응답(나중에)

            return ResponseEntity.ok(resDto.getAccessToken()); // body로 accessToken만 응답해줌.
    }

    /*
    accessToken이 만료되면 entryPoint에서 "error" : "ACCESS_TOKEN_EXPIRED" 라는 에러메시지를 응답함.
    프론트엔드에서 이 응답을 받으면 자동으로 /auth/refresh로 요청하게끔 설게한다.
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletResponse response , @CookieValue(value = "refreshToken" , required = false) String refreshToken) {
        // 쿠키에서 refresh 토큰을 꺼내와야함.
        if(refreshToken == null){
            //todo : 예외 던져줘야됨.
        }

        //서비스로 쿠키값(refresh 토큰) 넘김.
        return ResponseEntity.ok("")
    }
}

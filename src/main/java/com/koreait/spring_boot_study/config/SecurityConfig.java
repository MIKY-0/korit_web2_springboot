package com.koreait.spring_boot_study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
    @Bean // 사용자 패스워드를 암호화해주는 객체(시큐리티 라이브러리)
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /*
    CORS(Cross-Origin-Resource-Sharing) 설정 : CORS에러를 방지하기 위해 설정.
    브라우저에서 다른출처(origin)로 요청을 보낼 떄 , 브라우저정책 떄문에 요청을 막아서 발생하는 에러.
     -> origin이 다르면 에러 발생. *origin) 1.프로토콜(http , https) , 2.도메인(localhost , naver.com) , 3.포트(8080 , 3000)
     위 3가지 요소가 모두 일치해야 같은 origin.
     전통웹(SSR) 방식에서는 localhost:8080/page.html -> 사용자화면
     http:localhost:8080/main.js 주세요 요청 가능.  http:localhost:8080/img.png 주세요 요청 가능.
     http:localhost:5173 에서 http:localhost:8080으로 요청 불가능(origin 불일치) -> 이때 브라우저는 이 상황자체를 '보안상 위험' 하다고 판단 -> CORS 에러
     결론 : 서버(localhost:8080)에서 우리에게 보내는 요청은 안전하다고 설정해줘야함. 이게 CORS 설정.
     */
    @Bean // cors설정
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration cors = new CorsConfiguration(); // 설정객체
        cors.addAllowedOriginPattern(CorsConfiguration.ALL); // 요청을 보내는 쪽의 도메인 모두 허용.
        cors.addAllowedHeader(CorsConfiguration.ALL); // 요청을 보내는 쪽의 Req , Res 헤더 정보에 대한 제한 모두 허용.
        cors.addAllowedMethod(CorsConfiguration.ALL); // 요청을 보내는 쪽의 메서드(get,post...) 모두 허용.

        UrlBasedCorsConfigurationSource sc = new UrlBasedCorsConfigurationSource(); // 요청 url에 대한 cors설정을 적용하기 위한 객체(배달부)
        sc.registerCorsConfiguration("/**" , cors); // /** : 모든 url 패턴
        return sc;
    }

    //filterChain 설정(filter 설정)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.cors(Customizer.withDefaults()); // 위에서 Bean으로 만든 설정객체를 security에 적용.
        //세션기반 기능들 off
        //csrf : 세션기반 공격(사용자의 세션을 탈취해서 공격)
        http.csrf(csrf -> csrf.disable());
        //폼로그인(서버사이드 렌더링방식)
        http.formLogin(form -> form.disable());
        //세션기반 로그인 방식
        http.httpBasic(basic -> basic.disable());
        //세션 로그아웃
        http.logout(lg -> lg.disable());
        //세션을 무상태 방식으로 변경(jwt 토큰 방식)
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //jwt 관련 필터설정(나중에)

        //url 요청에 대한 권한 설정.
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/post/**" , "/product/**").permitAll(); // 특정 url 요청에 대해서는 검사하지않고 허용
            //auth.anyRequest().authenticated(); // 그외 모든 url 요청은 검사하겠다.
            auth.anyRequest().permitAll(); // 우선 모두 통과
        });
        return http.build();
    }
}

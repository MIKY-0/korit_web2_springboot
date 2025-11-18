package com.koreait.spring_boot_study.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // IOC 컨테이너에 직접 등록한 Bean들을 메서드 형식으로 선언하면 됨.
public class AppConfig {
    /*컴포넌트 스캔 -> @Component , @Service , @Repo-...스캔
    그런데 외부 라이브러리(좌측에 External Librariex)는 스캔범위 밖이다. -> 내가 직접 선언해줘야됨
    Jackson 라이브러리의 ObjectMapper 클래스를 bean으로 만들어서 IOC컨테이너에 보관하고 싶음. ->
    아래 코드처럼 생성하면 됨.
    주의사항) 싱글톤 객체기 때문에 bean으로 등록할 클래스는 반드시 상태가 없어야한다. 여기서 상태가 없다는 뜻은
    필드에 값을 넣어준게 아니고 선언만 해놓은것.
     */

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}

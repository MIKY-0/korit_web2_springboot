package com.koreait.spring_boot_study.diAndleioc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext; //IOC 컨테이너 패키지
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class IocController {
    private ApplicationContext context; //어플리케이션 컨텍스트 -> IOC 컨테이너. run()하면 가장 먼저 생성되는 싱글톤 객체!
    // @Autowired // B시점으로 주입하는 방식. 근데 쓰지말자.
    private ObjectMapper objectMapper;
    private IocService iocService;

    @Autowired // 생성자 시점인 A시점에서 쓰자
    public IocController(IocService iocService , ApplicationContext context , ObjectMapper objectMapper){
        this.iocService = iocService;
        this.context = context;
        this.objectMapper = objectMapper;
    }
    @GetMapping("/ioc")
    public ResponseEntity<?> diTest() throws JsonProcessingException {
        int total = iocService.getTotal();
        double avg = iocService.getAvg();
        Map<String , Object> resData = Map.of(
                "total" , total,
                "avg" , avg
        );
        String jsonData = objectMapper.writeValueAsString(resData); // 자바객체 -> JSOM(문자열)로 변환. (ObjectMapper타입인) Jackson라이브러리에 의해.
        /*아래 return코드에 대한 설명 :
        외부에서 들어오거나 나가는 데이터 타입은 문자열취급 한다.
        이걸 raw로 보겠다는 문자열로 보겠다는 뜻. JSoN으로 보겠다는 JSON포맷으로 읽겠다는 뜻.

         */
        return ResponseEntity.status(HttpStatus.OK) // 응답코드(헤더)
                .contentType(MediaType.APPLICATION_JSON) // 바디의 자료구조지정(헤더)
                .body(jsonData); //바디 데이터(바디)
    }

    @GetMapping("/beans")
    public ResponseEntity<?> showBeans(){
        //컴포넌트 스캔 마친후 등록된(컨테이너에 있는) 싱글톤 객체들의 이름들을 배열로 리턴하는 코드.
        String beans[] = context.getBeanDefinitionNames();
        return ResponseEntity.ok(beans);
    }
    /*우리가 직접 선언하지 않은 클래스의 객체는 bean으로 만들 수 없는가??
    외부 라이브러리 사용시 유틸리티 클래스의 객체를 bean으로 만들고 싶을 때. -> @Configuration 사용
     */

}

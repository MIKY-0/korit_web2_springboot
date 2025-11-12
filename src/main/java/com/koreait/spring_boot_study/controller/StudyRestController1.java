package com.koreait.spring_boot_study.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // html을 리턴하는 거 아님. 데이터(객체 , 문자열 ....) 리턴.
@Slf4j //원래는 메서드 안에 sout 하면 안되고 Slf4j 로 사용.
public class StudyRestController1 {
    @GetMapping("/study/test1") // -> localhost:8080/study/test1 으로 접속.
    public String test1(){
        //System.out.println("test1 컨트롤러 수신");
        log.info("test1 컨트롤러 수신"); // sout 대신 얘로 로깅.
        return "양호합니다";
    }
    // GET 요청의 경우 쿼리 스트링으로 데이터를 전달할 수 있다. 클라이언트 -> 서버로 전달.
    @GetMapping("/study/test2") // -> localhost:8080/study/test2 으로 접속.
    //localhost:8080/study/test2?name="홍길동"&age=20 -> 서버주소/경로/쿼리. ?이하 구문 : 쿼리.
    public String test2(@RequestParam("name") String str){ // RequestParam은 쿼리 스트링의 키와 매개변수의 이름이 같으면 생략 가능.
        //System.out.println("test1 컨트롤러 수신");
        log.info("test2 컨트롤러 수신");
        log.info("들어온 데이터 : {}" , str);
        return str;
    }

    // 파라미터 2개 & RequestParam 생략
    //localhost:8080/study/test3?name=홍길동&age=20
    @GetMapping("/study/test3")
    public String test3(String name , Integer age){
        // RequestParam은 쿼리 스트링의 키와 매개변수의 이름이 같으면 생략 가능.
        //정수의 경우 알아서 매개변수 타입으로 자동 변환(Parse).
        log.info("test3 컨트롤러 수신");
        log.info("들어온 데이터 : {} , {}" , name , age);
        return "수신성공";
    }
}

package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.AddPostReqDto;
import com.koreait.spring_boot_study.dto.StudyRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController     @RequestMapping("/study3")      @Slf4j

public class StudyRestController3 {
    /*
    HTTP 요청 메서드 : (GET , Post , PUT , PATCH , DELETE)
    GET 요청을 제외한 모든 요청 메서드에는 바디가 존재.
    바디 특징 - url과 무관하게 데이터 송신가능. -> 보안 good.
    JSON(서버와 클라이언트 사이에 통신해주는)을 바디에 담아서 송신.
     */
    @PostMapping("/test1")
    public String test1(@RequestBody Map<String , Object> data){
        //@RequestBody -> body에 작성되어있는 JSON 데이터를 알아서 자바객체로 바꿔준다. 참고)  JACKSON 라이브러리가 개입해서 자동으로 변환해줌.
        log.info("test1 컨트롤러 수신");
        log.info("들어온 데이터 : {}" , data);
        return "성공";
    }

    @PostMapping("/test2")
    public String test2(@RequestBody StudyRequestDto dto){
        log.info("test2 컨트롤러 수신");
        log.info("들어온 데이터 : {}" , dto);
        return "성공";
    }

    //(문제1) test3라는 이름으로 AddPostReqDto 타입 데이터 수신. PostMapping
    //(주의) 필드명과 JSON 키 이름이 동일해야함. private String 필드명을 JSON 키 이름으로.
    @PostMapping("/test3")
    public ResponseEntity<?> test3(@RequestBody AddPostReqDto dto){ // String , Map으로 리턴하기보단 ResponseEntity으로 리턴. String , Map 은 그냥 임시방편.
        //ResponseEntity : HTTP응답을 자바에서 커스터마이징하기 편하게 만든 클래스. 빌더패턴으로 형성되어있음.
        //제너릭타입을 받는다 -> 바디에 들어가는 데이터타입.
        //HTTP 상태코드 , body , header등을 쉽게 지정할 수 있다.
        log.info("test3");
        log.info("들어온 데이터 : {}" , dto);
        /*
        HTTP 상태코드
        200 : 성공    201 : 생성 성공(CREATED)
        400 : 잘못된 요청.(BAD_REQUEST)    401 : 인증실패(UNAUTHORIZED)    403 : 권한없음(FORBIDDEN)     404 : 리소스 없음.주소입력 잘못했음.(NOT_FOUND)
        500 : 서버 내부 오류.(코드문제 , 예외처리 불량 , DB에러가 서버까지 올라온 경우)
         */
        return ResponseEntity.status(HttpStatus.OK).body("success"); // HttpStatus.OK 를 200으로 대체가능.
    }

    /*
    RequestBody로 id 전달하면 되지 않나? StudtyRequestDto에 id필드를 만들어서.
    -기술적으로는 RequestBody로 id까지 객체로 데이터를 받아서 코딩 가능.
    -RESTful 설계
        -URL : "어떤 자원을 다룰것인가?"(식별) -> id
        -METHOD : "무엇을 할건가?"(행위에 관한것) -> put(수정)
        -body : "어떤 데이터로?"(내용) -> 내가보낸 dto로 바꿔줘.
     */
    @PutMapping("/test/{id}")
    public ResponseEntity<?> test4(@PathVariable int id , @RequestBody StudyRequestDto dto){
        log.info("test4컨트롤러 수신");
        log.info("들어온 데이터 : id = {} , dto = {}" , id , dto); // log.info에 중괄호가 생기면 인덱스 번호 부여됨.
        return ResponseEntity.ok("성공"); // 상태코드 OK + body
    }
    //PUT , PATCH 둘다 수정하겠다는 의미. PUT : 들어온 dto데이터로 싹 다 바꾸겠다.    PATCH : 들어온 dto데이터중 일부만 반영하겠다.

    //DELETE : 바디 쓸 수 있는데 보통 안씀.
    public ResponseEntity<?> test5(@PathVariable int id){

        return ResponseEntity.ok("삭제 완료");
    }
}

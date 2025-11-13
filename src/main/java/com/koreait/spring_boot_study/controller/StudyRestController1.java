package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController // html을 리턴하는 거 아님. 데이터(객체 , 문자열 ....) 리턴.
@Slf4j //원래는 메서드 안에 sout 하면 안되고 Slf4j 로 사용.
@RequestMapping("/study")  // "/test1" ,  "/test2" , "/test3" , "/test4" 앞에 "/study"가 공통으로 들어가서 @RequestMapping으로 생략 가능. ->
// "/study/test1" , "/study/test2" , ...

public class StudyRestController1 {
    @GetMapping("/test1") // -> localhost:8080/study/test1 으로 접속.
    public String test1(){
        //System.out.println("test1 컨트롤러 수신");
        log.info("test1 컨트롤러 수신"); // sout 대신 얘로 로깅.
        return "양호합니다";
    }
    // GET 요청의 경우 쿼리 스트링으로 데이터를 전달할 수 있다. 클라이언트 -> 서버로 전달.
    @GetMapping("/test2") // -> localhost:8080/study/test2 으로 접속.
    //localhost:8080/study/test2?name="홍길동"&age=20 -> 서버주소/경로/쿼리. ?이하 구문 : 쿼리.
    public String test2(@RequestParam("name") String str){ // RequestParam은 쿼리 스트링의 키와 매개변수의 이름이 같으면 생략 가능.
        //System.out.println("test1 컨트롤러 수신");
        log.info("test2 컨트롤러 수신");
        log.info("들어온 데이터 : {}" , str);
        return str;
    }

    // 파라미터 2개 & RequestParam 생략
    //localhost:8080/study/test3?name=홍길동&age=20
    @GetMapping("/test3")
    public String test3(String name , Integer age){
        // RequestParam은 쿼리 스트링의 키와 매개변수의 이름이 같으면 생략 가능.
        //정수의 경우 알아서 매개변수 타입으로 자동 변환(Parse).
        log.info("test3 컨트롤러 수신");
        log.info("들어온 데이터 : {} , {}" , name , age);
        return "수신성공";
    }
    //객체 리턴 가능1
    @GetMapping("/test4")
    public List<String> test4(){
        log.info("test4 컨트롤러 수신");
        List<String> names = List.of("홍길동" , "김길동" , "고길동");
        return names;
    }
        //객체 리턴 가능2
    //JSON : 서버와 클라이언트 사이에 주고받는 웹 데이터 표준형식 중 하나(XML 등). 자바의 MAP과 유사하게 생김.(아래 test5처럼 생겨서.)
    //자바의 객체도 전송 가능. -> (자바객체 -> JSON -> JavaScript 객체)
    @GetMapping("/test5")
    public List<Map<String , Object>> test5() {
        log.info("test5 컨트롤러 수신");
        List<Map<String, Object>> data = new ArrayList<>();
        /*
        [
        {key1 : value1},
        {key2 : value2},
        {key3 : value3},
        ]
         */
        Map<String, Object> data1 = Map.of(
                "name", "홍길동",
                "age", 20
        );
        Map<String, Object> data2 = Map.of(
                "name", "김길동",
                "age", 22
        );
        data.add(data1);
        data.add(data2);
        return data;
    }

        @GetMapping("/test6/{id}")
        public Map<String , Object> getStudent(@PathVariable("id") int iid) { // "id"를 int iid에 넣겠다.
        List<Student> studentList = List.of(
                new Student(1 , "피카츄"),
                new Student(2 , "라이츄"),
                new Student(3 , "파이리"),
                new Student(4 , "꼬부기")
        );
        //있는 번호인지 검증. 없으면 없다고 return.
           Student target = null;
            for(Student student : studentList) {
                if (student.getId() == iid) target = student;
            }

            if(target == null) return Map.of("error" , "해당 id 학생 없음"); // new (0 , "해당 id 학생은 없음")
            return Map.of("success" , target);
        }

        @GetMapping("/test7")
        // 쿼리스트링으로 데이터를 받을때 객체로 받을 수 있을까? StudyRestController의 quiz2에서 num1 , num2 , num3 , num4를 일일이 받기 귀찮음.
        //참고) 잭슨 라이브러리 사용 이유 : 잭슨 라이브러리가 요청을 하이재킹해서 바꾼뒤 매개변수에 할당해줌.
        //1. 쿼리스트링의 키들과 클래스의 필드명이 동일해야됨.  2.해당 클래스에 생성자 or 생성자가 무조건 정의되어야함.
        public String test7(@ModelAttribute Student student){
        log.info("들어온 데이터 : {} " , student);


        return "성공";
        }
    }



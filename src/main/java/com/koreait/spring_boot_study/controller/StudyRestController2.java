package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController // 스프링부트는 톰캣(자바로 만든 서버) 내장. 로컬에서 8080포트로 실행됨. localhost:8080 -> 스프링부트 주소
//localhost:8080/practice/quiz1 -> quiz1메서드 실행. 이 주소 경로 못찾으면 404뜸.
@Slf4j
@RequestMapping("/practice")
public class StudyRestController2 {

    //(문제1) 쿼리스트링으로 숫자 2개를 받아서 더한 결과 리턴하는 컨트롤러.
    @GetMapping("/quiz1") // "/quiz1"을 받았을 때 작동.
    public Integer quiz1(Integer num1, Integer num2) {
        log.info("두 정수 : {} , {}", num1, num2);
        log.info("두 정수 합 : {}", num1 + num2);
        return num1 + num2;
    }

    //(문제2) 쿼리스트링으로 숫자 4개를 받아서 평균 리턴하는 컨트롤러.
    @GetMapping("/quiz2")
    public Double quiz2(Integer num1, Integer num2, Integer num3, Integer num4) {
        log.info("네 정수 : {} , {} , {} , {}", num1, num2, num3, num4);
        log.info("네 정수 평균 : {}", (num1 + num2 + num3 + num4) / 4.0);
        return (num1 + num2 + num3 + num4) / 4.0;
    }
    /*(문제3)
    포스트맨 응답 :
    [
        {
            "name" : "홍길동",
            "address" : "부산시"
         },
         {
         "name" : "고길동",
         "address" : "서울시"
         }
     ]
     */
    @GetMapping("/profiles")
    public List<Map<String, String>> profiles(){ // Map<String , String> 대신 와일드 카드인 '?' 사용가능.
    List<Map<String, String>> info = new ArrayList<>();
    Map<String, String> data1 = Map.of(
            "name", "홍길동",
            "address", "부산시 연제구"
    );
    Map<String, String> data2 = Map.of(
            "name", "고길동",
            "address", "부산시 부산진구"
    );
    info.add(data1);
    info.add(data2);
    return info;
}

    @GetMapping("/profiles2")
    public List<?> profiles2(){ // Map<String , String> 대신 와일드 카드인 '?' 사용가능.  profiles1과 profiles2는 서로 같은 코드.
        return List.of(
                Map.of(
                        "name" , "홍길동",
                        "address" , "부산시 연제구"
                ),
                Map.of(
                        "name" , "고길동",
                        "address" , "부산시 부산진구"
                )
        );
    }
//(문제4) 게시물 조회 컨트롤러 작성. id로 조회할 수 있게 구현.
    @GetMapping("/quiz4/{id}")
    public Map<String , Object> getPost(@PathVariable("id") int iid) {
        List<Post> postList = List.of(
                new Post(1, "페이커 그는 신인가", "ㅇㅈ?"),
                new Post(2, "구마유시 그는 신인가", "ㅇㅈ?"),
                new Post(3, "케리아 그는 신인가", "ㅇㅈ?"),
                new Post(4, "오너 그는 신인가", "ㅇㅈ?"),
                new Post(5, "도란 그는 신인가", "ㅇㅈ?")
        );

        /* 스트림 이용해서 찾기 아래 향상 for문과 같은 코드.
        Optional<Post> optionalPost = postList.stream() // Optional : null일 수도 있는 값을 포장한 컨테이너 클래스.
                .filter(post -> post.getId() == iid)
                .findFirst();   // 처음 찾은것 가져오기. 타입(옵셔널)  'Optional<Post> optionalPost = ' 추가!
        if (optionalPost.isEmpty()) {   // 옵셔널이 가지고 있는 값이 null이면
            return Map.of("error", "해당 id의 게시글 없음");
        }
        Post target = optionalPost.get();
        return Map.of("success", target);
    */


        Post text = null;
        for(Post p : postList){
            if(p.getId() == iid) text = p;
        }
        if(text == null) return Map.of("0" , "해당id 없음");

        return Map.of("해당 id 존재" , text);
    }
}

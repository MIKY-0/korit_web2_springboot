package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.model.Hello;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*Controller - 클라이언트(웹 브라우저)와 서버(스프링부트) 사이에 데이터를 주고받는 진입점.
두가지 컨트롤러(Controller , REST Controller)
1.Controller - html파일을 반환하는 컨트롤러. - 서버측 렌더링(SSR - 서버사이드 렌더링).  쉽게 생각해서 컴퓨터 부품들이 배송되서 내가 직접 조립.
2.REST Controller - JSON , 문자열 등의 데이터들만 반환하는 컨트롤러. - 클라이언트사이드랜더링(CSR) 쉽게 생각해서 컴퓨터 부품들이 조립되어서 나에게 배송.
*/
@Controller
public class StudyController {
    /*인터넷 통신(HTTP 통신) - 웹에서 클라이언트와 서버가 데이터를 주고받는 규칙.
    규칙 1. 한번 요청(ACK)하면 한번 응답. (요청/응답 패킷은 객체. 헤더와 바디로 이루어져 있음.)
    규칙 2. 요청의 경우 , 메서드(방법)가 있다.
        방법1. GET 요청 : 자원 조회 요청. - 바디가 없음 , url에 쿼리 스트링으로 데이터 전달.
        방법2. POST 요청 : 자원 생성 요청.
        방법3. DELETE 요청 : 자원 삭제 요청.
        방법4. PUT 요청 : 자원 전체 수정 요청.
        방법5. PATCH 요청 : 자원 일부 수정 요청.
    톰캣서버(8080 포트) + 로컬 -> localhost:8080(서버주소). main 실행시 콘솔창에 뜨는 톰캣 포트번호 나옴. 학원 pc에서는 8080포트.
    localhost:8080/hello -> 접속시 GET 요청하게 되니까 helloPage 컨트롤러가 실행됨.
    */
    @GetMapping("/hello") // hello라는 경로로 GET 요청이 들어오면 실행하라.
    public String helloPage(Model model){ // Model : html에 데이터를 전달해주는 자바 객체.
        System.out.println("hello 컨트롤러 수신"); // 콘솔창에 출력.
        Hello hello = Hello.builder()
                .hello1("데이터1")
                .hello2("데이터2")
                .build();
        model.addAttribute("hellohello" , hello); // hello 객체를 "hellohello" 이름으로 HTML에 전달.
        return "hello"; // templates 경로 안에 hello.html을 찾아서 클라이언트에 보내라.
        //구글에 localhost:8080/hello 치면 나옴.
    }
}

package com.koreait.spring_boot_study.diAndleioc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
public class DiController {
    //Controller데이터 -> Service -> Repository
    //Controller 영역 : 요청수신 / 응답송신관련 코드작성.  Service 영역 : 비즈니스로직 / 트랜잭션관리.   Repository 영역 : DB연결 / DB와 관련된 코드작성.

    @GetMapping("/di")
    public ResponseEntity<?> diTest(){ // diTest() 컨트롤러는 DiService객체에 의존.
        //DiService는 DiRepository객체에 의존하고 있다. private DiRepository diRepository 필드가 있다. 근데 DiRepository는 뭔가 의존중이지 않음.
        //이 의존성을 내가 직접 코드로 컨트롤 하고있다. -> Di를 직접하고 있다.
        //getInstance()를 직접 호출함으로써 내가 직접 객체를 new 하는 효과.(객체 생성도 직접 컨트롤 하고있다는 말)
        DiRepository diRepository = DiRepository.getInstance();
        DiService diService = DiService.getInstance(diRepository);

        int totalScore = diService.getTotal();
        double avgScore = diService.getAvg();

        Map<String , Object> resMap = Map.of(
                "총점" , totalScore ,
                "평균" , avgScore
        );
        return ResponseEntity.ok(resMap);
    }
}

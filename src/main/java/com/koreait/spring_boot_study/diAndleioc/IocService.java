package com.koreait.spring_boot_study.diAndleioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IocService {
    /*
    스프링부트에서는 객체생성(new) , 의존성주입(di)의 주도권이 내가 아니라 프레임워크(스프링부트)에 있다.
    언제 생성되는지 소멸되는지 스프링부트가 결정. Inversion of Control(Ioc)
    스프링부트에는 IOC컨테이너라는게 존재. 이 컨테이너에 어노테이션이 붙은 클래스들을 싱글톤 객체로 생성해서 보관.

    ------main에서의 코드들 실행 순서------
    1.SpringApplication.run() 실행시 Ioc컨테이너 객체 생성.(싱글톤)
    2.컴포넌트스캔(@Component 붙은 클래스 탐색). 탐색범위 - run()한 곳이 포함된 패키지를 기준 모든 하위 클래스. 여기서는 com.koreait.spring_boot_study에 포함된 모든 클래스들.
    3.스캔이 끝나면 그 클래스들로부터 싱글톤 객체 생성. -> 이걸 bean이라고 부름. -- A시점 - 필요할 때 올리고 주입하는 방법
    4.생성한 bean들을 Ioc컨테이너에 보관.
    5.필요한 곳에 해당 bean 주입. -- B시점 - 미리 싹 다 올려놓고 필요하면 그때 주입하는 방법. (A시점 주입을 권장)


    -----@Component 역할 하는 어노테이션들----
    1.@Component : 특별한 역할 없음.
    2.@RequestController : HTTP 요청 / 응답 처리.
    3.@Service : 비즈니스 로직 / 트랜잭션 관리.
    4.@Repository : DB와의 작업 담당.
    5.@Configuration : 직접 Bean을 등록하는 설정클래스.

     */
    //IOC로 관리되는 것들 : 싱글톤 -> 상태(필드값) 변하지 않음.
    @Autowired // B시점에서 주입.
    private IocRepository iocRepository;

    @Autowired // A시점에서 주입. A시점 주입을 권장.
    public IocService(IocRepository iocRepository) { // priavate DiRepository diRepository 필드 때문에 생성
        this.iocRepository = iocRepository;
    }
    /*
    순환참조(A , B라는 bean을 예시로)
    A는 B를 필드로 가지고 있음 , B는 A를 필드로 가지고 있음.
    A를 만드려니 B가 필요하네? -> B만들자
    B를 만드려니 A가 필요하네? -> A만들자... 무한반복
    A시점 추천이유 : 미리 탐지 가능 , 예방 가능. A시점은 오류가 바로 생김.1분안에.  하지만 B시점은 오류가 한참 뒤에 생김. 몇개월 뒤에.
     */

    public int getTotal(){ // 총점 구하는 메서드
        List<Integer> scores = iocRepository.getScores();
        int total = 0;
        for(int s : scores)  total += s;
        return total;
    }
    public double getAvg(){ // 평균연산
        List<Integer> scores = iocRepository.getScores();
        double avg = (double) getTotal() / scores.size();
         return avg;
    }
}

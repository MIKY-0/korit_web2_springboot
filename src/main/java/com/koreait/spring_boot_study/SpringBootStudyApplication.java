package com.koreait.spring_boot_study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//스케쥴러 : 개발자가 지정한 시간마다 메서드 실행.
@EnableScheduling // 스케쥴러 설정.
@SpringBootApplication
public class SpringBootStudyApplication {
	//이 클래스가 스프링부트 서버 시작 진입점
	//자바와 다르게 스프링 부트는 유일하게 main이 선언된 곳.
	public static void main(String[] args) {
		SpringApplication.run(SpringBootStudyApplication.class, args);
	}
}

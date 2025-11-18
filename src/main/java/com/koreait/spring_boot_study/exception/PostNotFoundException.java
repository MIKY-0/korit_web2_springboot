package com.koreait.spring_boot_study.exception;

//커스텀예외 - RuntimeException을 상속받으면 커스텀예외를 만들 수 있음.
public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String msg){
        super(msg);
    }
}

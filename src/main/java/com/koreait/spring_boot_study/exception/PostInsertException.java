package com.koreait.spring_boot_study.exception;

public class PostInsertException extends RuntimeException{
    public PostInsertException(String msg){
        super(msg);
    }
}

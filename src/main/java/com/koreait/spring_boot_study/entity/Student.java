package com.koreait.spring_boot_study.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor  @Getter   @ToString
public class Student {
    private int id;
    private String name;

    /*
    {
        "id" : 1,
        "name" : "홍길동"
    }
     */
}

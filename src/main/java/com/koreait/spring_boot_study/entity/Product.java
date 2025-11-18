package com.koreait.spring_boot_study.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor     @Getter     @Setter
//엔티티 : 관계형 데이터베이스와 1:1 대응되는 자바 객체.
public class Product {
    private int id;
    private String name;
    private int price;
}

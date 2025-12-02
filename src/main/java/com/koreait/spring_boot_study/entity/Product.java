package com.koreait.spring_boot_study.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor     @Data
@NoArgsConstructor
//엔티티 : 관계형 데이터베이스와 1:1 대응되는 자바 객체.
public class Product {
    private int id;
    private String name;
    private int price;
    public Product(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    private List<OrderDetail> orderDetails; // 1개의 product는 여러번 주문될 수 있다.
    //-> 하나의 product는 n개의 orderDetail를 가지고 있음. -> 하나의 product_id로 여러줄의 orderDetail 결과를 받을 수 있다.
    //-> 하나의 product객체가 n개의 orderDetail객체를 가질 수 있다.
}

package com.koreait.spring_boot_study.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//아래 3가지 어노테이션만 있으면 알아서 보일러 플레이트 코드가 웬만하면 완성됨.
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetail {
    private int orderDetailId;
    private int orderId; // fk
    //private int productId; // fk
    private int quantity;

    private Product product; // 연관관계를 가지려면 fk대신 객체를 가지고 있으면 된다.
    //fk를 컬럼으로 가지고 있다? -> 1개의 product는 N개의 orderDetail을 가질 수 있음.
}

package com.koreait.spring_boot_study.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor     @Getter     @Setter
public class AddProductDto {
    private String name;
    private int price;

}

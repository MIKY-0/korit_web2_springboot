package com.koreait.spring_boot_study.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor  @Getter  @Setter   @ToString
public class Post {
    private int id;
    private String title , content;
}

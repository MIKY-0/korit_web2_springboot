package com.koreait.spring_boot_study.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor     @NoArgsConstructor      @Data
public class Comment {
    private int commentId;
    private String commentContent;
    //private int postId;
    private Post post; // fk대신 객체를 필드로 가지고 있어야함.
    //comment.getPost().getId() -> 계속된 참조로 탐색하는 것(객체 그래프탐색)

    //하나의 글에는 여러개의 댓글이 있다. -> 1개의 post는 여러개의 comment를 가질 수 있음.
    //1.fk는 누가 가져야하나? comment가 postId를 가지고 있어야함. fk가 있는 쪽이 N.
    //2.하나의 댓글은 하나의 글에만 달릴 수 있음. -> post : comment = 1 : N 관계

    /*
    테이블 설계 노하우
    fk를 설정하는 순간 -> 1 : N(fk를 가진 테이블) 관계를 만들겠다는 것.
    N : M 관계도 설정 가능.
    학생 : 강의 -> 학생이 강의로 fk를 , 강의도 학생을 fk로 가짐.( N : M 관계)
    등록테이블 생성시켜서 (학생 fk , 강의 fk 기입)
    학생 : 등록(1 : N)
    강의 : 등록(1 : N)  --> 모든 테이블이 1 : N관계로 설정 가능.
     */
}

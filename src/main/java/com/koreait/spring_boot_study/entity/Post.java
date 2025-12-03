package com.koreait.spring_boot_study.entity;

import lombok.*;

import java.util.List;

//jackson 라이브러리 , mybatis -> 기본생성자로 객체를 만든다음 set()
@AllArgsConstructor  @NoArgsConstructor     @Data   @Builder
public class Post {
    private int id;
    private String title , content;

    public Post(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    //pk를 fk로 들고있는 쪽이 N. Post : Comment = 1 : N
    private List<Comment> comments;
}

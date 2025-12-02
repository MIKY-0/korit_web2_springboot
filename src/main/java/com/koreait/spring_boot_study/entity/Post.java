package com.koreait.spring_boot_study.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor  @NoArgsConstructor     @Data
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

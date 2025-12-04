package com.koreait.spring_boot_study.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor     @NoArgsConstructor      @Data       @Builder
public class User {
    private int userId;
    private String userName;
    private String password;
    private String name;
    private String email;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    private Role role; // fk컬럼(role_id) 대신


}

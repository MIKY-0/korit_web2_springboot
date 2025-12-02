package com.koreait.spring_boot_study.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
//(문제2)
@AllArgsConstructor     @NoArgsConstructor      @Data
public class PostWithCommentsResDto {
    private String postTitle;
    private String postContent;
    private List<String> comments;
}

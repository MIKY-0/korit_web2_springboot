package com.koreait.spring_boot_study.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter     @Setter     @ToString   @AllArgsConstructor     @NoArgsConstructor

public class AddPostReqDto {
    @NotBlank(message = "게시글 제목과 게시글 내용은 비울 수 없음")  @Size(min = 1 , message = "게시글 제목과 게시글 내용은 한글자 이상!")
    private String title , content;
}

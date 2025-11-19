package com.koreait.spring_boot_study.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor     @NoArgsConstructor  @Data
public class ModifyPostReqDto {
    @NotBlank(message = "게시글 제목은 비울 수 없음")    @Size(max = 50 , message = "게시글 제목은 50자 이하로 작성")
    private String title;
    @NotBlank(message = "게시글 내용은 비울 수 없음")
    private String content;
}

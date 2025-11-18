package com.koreait.spring_boot_study.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor     @Getter     @Setter
public class PostResDto {
    //DB에는 민감한 정보가 같이 저장되어있음. 이러한 민감정보를 entity는 필드로 가지고 있음.
    //entity를 그대로 사용자에게 노출하면 안된다(DB 구조 노출을 금지한다). 응답하는 dto를 통해 민감정보를 제외하고 필요한 정보만 담아서 응답해야한다.
    private String title , content;

}

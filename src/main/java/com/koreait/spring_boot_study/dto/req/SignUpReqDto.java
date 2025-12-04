package com.koreait.spring_boot_study.dto.req;

import com.koreait.spring_boot_study.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor     @NoArgsConstructor      @Data
public class SignUpReqDto {
    @NotBlank(message = "아이디 입력해주세요")
    private String userName;
    @NotBlank(message = "패스워드 입력해주세요")
    private String password;
    @NotBlank(message = "이메일 입력해주세요")
    private String name;
    @NotBlank(message = "이메일 입력해주세요")   @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;

    //dto -> entity로 변환
    public User toEntity(){
        return User.builder() . userName(this.userName) . name(this.name) . email(this.email) . build();
    }
}

package com.koreait.spring_boot_study.dto.req;

import com.koreait.spring_boot_study.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor     @NoArgsConstructor      @Data
public class SignUpReqDto {
    /*
    정규표현식(regex) - 프로그래밍 언어로 입력데이터 패턴을 지정하는 방식.
    [a - z] : a ~ z 중 1개 , [A - Z] : A ~ Z 중 1개 , [0 - 9] : 숫자 0 ~ 9 중 1개 , [a - zA - z] : 영문 대소문자중 1개
    {4 , 20} : 4개 이상 20개 이하 , {4 , } : 4개 이상 , {4} : 정확히 4개. --> ex) [a - z]{4 , } : 소문자 4개 이상
    ^ : 정규식 시작 , $ : 정규식 끝 , ?=.*[패턴] : 해당패턴을 하나이상 포함.
    ^?=.*[A - Za - z]{4 , 10}$ : 대소문자를 하나이상 포함하는 4 ~ 10자.
     */
    @NotBlank(message = "아이디 입력해주세요")
    @Pattern(regexp = "^[a - z0 - 9]{4,20}$" , message = "아이디는 4~20자 영문 소문자 , 숫자만 사용가능합니다.")
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

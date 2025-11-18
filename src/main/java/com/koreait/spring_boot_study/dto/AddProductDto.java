package com.koreait.spring_boot_study.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor     @Getter     @Setter
public class AddProductDto {

    @NotBlank(message = "이름은 비울 수 없음")   @Size(max = 50 , message = "50글자 이하로 지어야함.")
    private String name;
    //원래는 name , price 검증 실패시 name의 예외만 처리하고 price는 무시했었다. -> advice.GlobalHandlerException 참고(자세한 설명 있음)
    @Positive(message = "가격은 양수여야 힘.") // 이 message들은 옵션으로 추가시킬 수 있음. 검증 실패시 예외를 던짐. 이때 예외에 들어갈 메시지 : message.
    private int price;
    /*
    스프링 validation 라이브러리 사용법
     1. 문자열 --> @NotBlank : null , "" , "  " 모두 허용 안하겠다.   @NotEmpty : null , "" 허용 안하겠다. 스페이스바는 허용.
    @Size(min = ? , max = ?) : 문자열 길이 제한    @Email : 이메일형식 알아서 검사
     2. 숫자 --> Min(value = ?) : 최솟값 , Max(value = ?) : 최댓값 , @Positive : 양수만 , @Negative : 음수만
     3. 객체 --> @Valid : 내부객체 검증     @NouNull : null만 금지.
     */
}

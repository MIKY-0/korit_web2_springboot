package com.koreait.spring_boot_study.dto.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor     @NoArgsConstructor      @Data
public class ModifyProductReqDto {
    @NotBlank(message = "상품이름은 비울 수 없음")    @Size(max = 50 , message = "상품이름은 50자 이하로 작성")
    private String name;
    @Positive(message = "상품가격은 양수")     @Min(value = 1000 , message = "상품은 1000원 이상 등록")
    private int price;
}

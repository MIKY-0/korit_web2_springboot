package com.koreait.spring_boot_study.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor     @NoArgsConstructor      @Data
public class SearchProductReqDto {
    private String nameKeyWord;
    private Integer maxPrice;
    private Integer minPrice;
}

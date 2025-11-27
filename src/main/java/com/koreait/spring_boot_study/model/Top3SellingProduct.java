package com.koreait.spring_boot_study.model;

/*
DB 테이블과 1:1 매핑된 엔티티가 아님.
기술적인 부분과 상관 없음. -> top3를 뽑아내라는 요구사항은 업계의 요구.(비즈니스 로직)
클라이언트가 요구한 것(top3판매량 순위 조회) : 이런것들을 domain model이라 함.
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor     @NoArgsConstructor      @Data
public class Top3SellingProduct {
    private int productId; // product_id
    private String productName;
    private int totalSoldCount; // 집계함수 결과
}

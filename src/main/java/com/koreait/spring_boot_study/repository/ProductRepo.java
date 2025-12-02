package com.koreait.spring_boot_study.repository;

import com.koreait.spring_boot_study.entity.Product;
import com.koreait.spring_boot_study.model.Top3SellingProduct;

import java.util.List;

public interface ProductRepo {
    //다건조회
    public List<Product> findAllProducts();
    //단건조회
    public String findProductNameById(int id);
   //상품추가
    public int insertProduct(String name , int price);
    //단건 삭제
    public int deleteProductById(int id);
    //단건 업데이트
    public int updateProduct(int id , String name , int price);

    //join 결과를 받아오기. 판매량 기준 top3 받아오기.
    public List<Top3SellingProduct> findTop3SellingProducts();
}

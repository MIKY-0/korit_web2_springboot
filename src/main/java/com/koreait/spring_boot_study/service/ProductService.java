package com.koreait.spring_boot_study.service;

//ProductRepository를 호출할 서비스를 만들것이다.

import com.koreait.spring_boot_study.dto.AddProductDto;
import com.koreait.spring_boot_study.entity.Product;
import com.koreait.spring_boot_study.exception.ProductInsertException;
import com.koreait.spring_boot_study.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    //1.다건조회(상품 이름만)
    //형변환 / 비즈니스 로직(로깅 , 외부 api 호출 등등) 이런것들을 서비스영역에서 해주면 됨.
    public List<String> getAllProductNames(){
        //1) stream을 사용하는 방법
        List<String> productNames = productRepository.findAllProducts().stream()
                .map(product -> product.getName()) // findAllProducts()는 객체를 가져오는것이고
                // 여기서 우리가 필요한건 객체 자체가 아닌 객체의 상품명이기 때문에 (객체 -> 상품명)으로 변환해줘야됨. 그래서 map!!
                .collect(Collectors.toList());

        //2)for문을 사용하는 방법
        List<String> productNames2 = new ArrayList<>();
        List<Product> products = productRepository.findAllProducts();
        for(Product p : products){
            productNames2.add(p.getName());
        }
        return productNames;
    }
    //2.단건조회(상품 이름만) - id를 받아서 상품명 추출.
    public String getProductNameById(int id){
        return productRepository.findProductNameById(id);
    }

    //3.상품추가(등록)
    public void addProduct(AddProductDto dto){
        int successCount = productRepository
                .insertProduct(dto.getName() , dto.getPrice());
        if(successCount <= 0){
            throw new ProductInsertException("상품등록 중 문제가 생김");
        }
    }
}

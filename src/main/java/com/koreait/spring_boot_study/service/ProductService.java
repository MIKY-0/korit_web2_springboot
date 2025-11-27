package com.koreait.spring_boot_study.service;

//ProductRepository를 호출할 서비스를 만들것이다.

import com.koreait.spring_boot_study.dto.AddProductDto;
import com.koreait.spring_boot_study.dto.ModifyProductReqDto;
import com.koreait.spring_boot_study.dto.Top3SellingProductResDto;
import com.koreait.spring_boot_study.entity.Product;
import com.koreait.spring_boot_study.exception.ProductInsertException;
import com.koreait.spring_boot_study.exception.ProductNotFoundException;
import com.koreait.spring_boot_study.model.Top3SellingProduct;
import com.koreait.spring_boot_study.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    //private ProductRepository productRepository;
    private ProductRepo productRepository; // 필드로 인터페이스타입으로 필드를 가지고있음.

    @Autowired
    public ProductService(@Qualifier("jdbc") ProductRepo productRepository){
        this.productRepository = productRepository;
    }
    /*
    ProductRepo -> 인터페이스. 인터페이스 타입 객체는 존재할 수 없다. -> 구현체가 있나 Ioc컨테이너를 검사.
    ProductJDBCRepo , ProductRepository 둘다 ProductRepo를 implements 받았음.
    여러개인 경우가 되버림 -> 우선순위를 지정해줘서 해결 가능.
    1.필드 변수명과 bean이름이 같으면 매칭.
    2.@Qualifier 사용.
    3.@Primary를 달아주면 우선순위를 가진다.
     */
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

    //4.상품삭제
    public void removeProduct(int id){
        int successCount = productRepository.deleteProductById(id);
        if(successCount <= 0){
            throw new ProductNotFoundException("해당 상품은 존재하지 않음");
        }
    }

    //5.상품 업데이트
    public void modifyProduct(int id , ModifyProductReqDto dto){
        int successCount = productRepository.updateProduct(id , dto.getName() , dto.getPrice());
        if(successCount <= 0){
            throw new ProductNotFoundException("해당 상품은 존재하지 않음");
        }
    }

    //Top3 상품들 리턴해주는 메서드(model을 리턴하면 안됨)
    public List<Top3SellingProductResDto> getTop3SellingProduct() {
        List<Top3SellingProduct> results = productRepository.findTop3SellingProducts();
        List<Top3SellingProductResDto> outputs = new ArrayList<>();
        for (Top3SellingProduct r : results) {
            Top3SellingProductResDto dto = Top3SellingProductResDto.from(r);
            outputs.add(dto);
        }

        return productRepository.findTop3SellingProducts().stream()
                .map(model -> Top3SellingProductResDto.from(model)) // 메서드참조로 더 축약 가능.
                .collect(Collectors.toList());
    }
}

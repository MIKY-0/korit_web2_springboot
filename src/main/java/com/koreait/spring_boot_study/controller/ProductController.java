package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.AddProductDto;
import com.koreait.spring_boot_study.dto.ModifyProductReqDto;
import com.koreait.spring_boot_study.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    //전체 상품명 조회
    @GetMapping("/name/all")
    public ResponseEntity<?> getProductNames() {return ResponseEntity.ok(productService.getAllProductNames());}

    //상품명 단건 조회
    @GetMapping("/name/{id}")
    public ResponseEntity<?> getProductName(@PathVariable int id){
        return ResponseEntity.ok(productService.getProductNameById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> postProduct(@Valid @RequestBody AddProductDto dto){
        productService.addProduct(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("성공");
    }

    //localhost:8080/product/1 - Delete
    //delete요청은 바디를 포함할 수 있지만 잘 사용하지 않음.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        productService.removeProduct(id);
        return ResponseEntity.ok("삭제완료");
    }

    //왜 RequestBody로 id까지 전달받지않고 굳이 PathVariable로 id는 따로 받지?
    //-> Restful설계 : url과 요청메서드만으로도 뭐하는지 예측할 수 있다.
    //localhost:8080/product/1 - Put : product에 1번을 수정.
    @PutMapping("/{id}")
    public ResponseEntity<?> putProduct(@PathVariable int id , @Valid @RequestBody ModifyProductReqDto dto){
        productService.modifyProduct(id , dto);
        return ResponseEntity.ok("수정완료");
    }

    @GetMapping("/top3")
    public ResponseEntity<?> top3(){
        return ResponseEntity.ok(productService.getTop3SellingProduct());
    }

    @GetMapping("/{productId}/quantity")
    public ResponseEntity<?> getProductWithQuantities(@PathVariable int productId){
        return ResponseEntity.ok(productService.getProductQuantitiesById(productId));
    }

}







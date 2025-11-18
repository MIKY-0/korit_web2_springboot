package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.AddProductDto;
import com.koreait.spring_boot_study.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController     @RequestMapping("/product")
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
}

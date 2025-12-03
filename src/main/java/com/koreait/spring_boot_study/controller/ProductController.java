package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.req.AddProductReqDto;
import com.koreait.spring_boot_study.dto.req.ModifyProductReqDto;
import com.koreait.spring_boot_study.dto.req.SearchProductReqDto;
import com.koreait.spring_boot_study.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> postProduct(@Valid @RequestBody AddProductReqDto dto){
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

    //조건검색. localhost:8080/product/search?nameKeyWord=키보드&minPrice=10000
    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(@ModelAttribute SearchProductReqDto dto){
        return ResponseEntity.ok(productService.searchDetailProducts(dto));
    }

    //다건입력
    /*
    [
        {
            "name" : "키보드",
            "price" : 30000
         },
         {
            "name" : "무선마우스",
             "price" : 25000
         }
     ]
     */
    @PostMapping("/add/bulk")
    public ResponseEntity<?> addProducts(@RequestBody @Valid List<AddProductReqDto> dtoList){
        productService.addProducts(dtoList);
        return ResponseEntity.status(HttpStatus.CREATED).body("전체상품 등록 성공 : " + dtoList.size() + "건");
    }
}







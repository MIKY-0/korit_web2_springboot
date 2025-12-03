package com.koreait.spring_boot_study.service;

//ProductRepository를 호출할 서비스를 만들것이다.

import com.koreait.spring_boot_study.dto.req.AddProductReqDto;
import com.koreait.spring_boot_study.dto.req.ModifyProductReqDto;
import com.koreait.spring_boot_study.dto.req.SearchProductReqDto;
import com.koreait.spring_boot_study.dto.res.ProductQuantityResDto;
import com.koreait.spring_boot_study.dto.res.SearchProductResDto;
import com.koreait.spring_boot_study.dto.res.Top3SellingProductResDto;
import com.koreait.spring_boot_study.entity.Product;
import com.koreait.spring_boot_study.exception.ProductInsertException;
import com.koreait.spring_boot_study.exception.ProductNotFoundException;
import com.koreait.spring_boot_study.model.Top3SellingProduct;
import com.koreait.spring_boot_study.repository.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    //private ProductRepository productRepository;
    private ProductMapper productRepository; // 필드로 인터페이스타입으로 필드를 가지고있음. || ProductRepo를 ProductMapper로 변경

    @Autowired
    public ProductService(/*@Qualifier("jdbc")*/ ProductMapper productRepository) {
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
    public List<String> getAllProductNames() {
        //1) stream을 사용하는 방법
        List<String> productNames = productRepository.findAllProducts().stream()
                .map(product -> product.getName()) // findAllProducts()는 객체를 가져오는것이고
                // 여기서 우리가 필요한건 객체 자체가 아닌 객체의 상품명이기 때문에 (객체 -> 상품명)으로 변환해줘야됨. 그래서 map!!
                .collect(Collectors.toList());

        //2)for문을 사용하는 방법
        List<String> productNames2 = new ArrayList<>();
        List<Product> products = productRepository.findAllProducts();
        for (Product p : products) {
            productNames2.add(p.getName());
        }
        return productNames;
    }

    //2.단건조회(상품 이름만) - id를 받아서 상품명 추출.
    public String getProductNameById(int id) {
        return productRepository.findProductNameById(id);
    }

    //3.상품추가(등록)
    public void addProduct(AddProductReqDto dto) {
        int successCount = productRepository
                .insertProduct(dto.getName(), dto.getPrice());
        if (successCount <= 0) {
            throw new ProductInsertException("상품등록 중 문제가 생김");
        }
    }

    //4.상품삭제
    public void removeProduct(int id) {
        int successCount = productRepository.deleteProductById(id);
        if (successCount <= 0) {
            throw new ProductNotFoundException("해당 상품은 존재하지 않음");
        }
    }

    //5.상품 업데이트
    public void modifyProduct(int id, ModifyProductReqDto dto) {
        int successCount = productRepository.updateProduct(id, dto.getName(), dto.getPrice());
        if (successCount <= 0) {
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
        return outputs;
//        return productRepository.findTop3SellingProducts().stream()
//                .map(model -> Top3SellingProductResDto.from(model)) // 메서드참조로 더 축약 가능.
//                .collect(Collectors.toList());
//    }
    }

    public List<ProductQuantityResDto> getProductQuantitiesById(int productId) {
        Product product = productRepository.findProductWithQuantities(productId); // Product객체를 가져옴. 근데 orderDetails필드(list)를 mybatis가 알아서 채워옴.
        //만약 A entity가 B를 가지고 있고 B entity가 A를 가지고 있을 수 있음(양방향). a.getB().getA().getB().getA()....
        //-> 양방향 설정을 되도록 쓰지말자.


        //옵셔널이 아니라서 null체크 해줌. product가 null이거나 List<OrderDetail>이 null이면
        if(product == null || product.getOrderDetails() == null)  return List.of();  // 비어있는 리스트 리턴
        // 1.stream 사용버전
        List<ProductQuantityResDto> resultData = new ArrayList<>();
                                    resultData = product.getOrderDetails() // List<OrderDetail>
                                                .stream() // Stream<OrderDetail>
                                                .map(od -> new ProductQuantityResDto(product.getName() , product.getPrice() , od.getQuantity()))
                                                // Stream<ProductQuantityResDto>. od.getProduct() -> xml에 정의해놓지 않아서 null(단방향)
                                                .collect(Collectors.toList()); // List<ProductQuantityResDto>

        //2.for문 사용 버전
//        for(OrderDetail od : product.getOrderDetails()) {
//            ProductQuantityResDto dto = new ProductQuantityResDto(
//                    product.getName(), product.getPrice(), od.getQuantity()
//            );
//            resultData.add(dto);
//        }
            return resultData;
    }
        //dto-req,res 2가지
    //req : nameKeyWord , minPrice , maxPrice.  res : id가 필요하면 id까지 dto에 작성하여 리턴.
    public List<SearchProductResDto> searchDetailProducts(SearchProductReqDto dto) {
        List<Product> products = productRepository.searchDetailProducts(
                dto.getNameKeyWord(),
                dto.getMinPrice(),
                dto.getMaxPrice()
        );
        if(products == null || products.isEmpty()){
            throw new ProductNotFoundException("조건에 맞는 상품이 없습니다");
        }
        List<SearchProductResDto> dtos = new ArrayList<>();
        //1. stream api 사용버전.
        dtos = products.stream()
                .map(p -> new SearchProductResDto(p.getName() , p.getPrice()))
                .collect(Collectors.toList());

        //for문 사용버전
//        for(Product p : products){
//            SearchProductResDto resDto = new SearchProductResDto(p.getName() , p.getPrice());
//            dtos.add(resDto);
//        }
        return dtos;
    }

    //트랜잭션 어노테이션만 설정하면 스프링부트가 알아서 db로
    //start transaction , commit , rollback 쿼리를 삽입해서 송신함.
    @Transactional(rollbackFor = Exception.class) // 해당 메서드를 트랜잭션으로 실행하겠다. 메서드 종료직전에 정상 종료라면 commit , 예외 발생할 경우 rollback.
    public void addProducts(List<AddProductReqDto> dtoList) {
        //List<dto> -> List<entity> 변환
        List<Product> products = dtoList.stream()
                        .map(dto -> Product.builder().name(dto.getName()).price(dto.getPrice()).build())
                                .collect(Collectors.toList());
        int successCount = productRepository.insertProducts(products);

        if(successCount != products.size()){ // 전체건수만큼 insert 되지 않았다면 예외처리.
            throw new ProductInsertException("상품등록중 문제가 발생했습니다");
        }
    }
}








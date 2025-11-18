package com.koreait.spring_boot_study.repository;

import com.koreait.spring_boot_study.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    //DB 대용 데이터. 원래는 하면 안되지만 지금은 DB 연동 전이니까
    private List<Product> products = new ArrayList<>(
            Arrays.asList(
                    new Product(1 , "노트북" , 1500000),
                    new Product(2 , "마우스" , 30000),
                    new Product(3 , "키보드" , 80000),
                    new Product(4 , "모니터" , 350000)
            )
    );
    /*컨트롤러 -> 서비스 -> 레포지토리
    1.다건조회(전체조회)
    2.단건조회(상품 하나만 조회)
     */
    //1.다건 조회
    public List<Product> findAllProducts(){
        return products;
    }
    //2.단건조회(id를 받아서 상품이름 조회)
    public String findProductNameById(int id){
        //Optional은 컨테이너 클래스.(null일수 도 있고 아닐 수도 있음)
        Optional<Product> optionalProduct = products.stream()
                .filter(product -> product.getId() == id)
                .findFirst(); // 매칭되는 첫번째 객체를 리턴 or 매칭 없으면 null 리턴.
        //예) id가 4번까지 있는데 id를 10번 입력하면 없는 상품이니 null을 출력.
        //옵셔널을 펼치는것 -> Repository에서 할까 , Service에서 할까? -> 이건 개발자의 맘.
        if(optionalProduct.isEmpty()){
            //정석) 예외를 던져야함. 근데 아직 안배웠으니 아래처럼 작성.
            return "해당 id상품 없음";
        }
        String targetName = optionalProduct.get().getName(); // get() 하면 객체를 가져오고 그다음 getName()은 그 객체의 이름 추출.
        return targetName;
    }
    //상품추가
    public int insertProduct(String name , int price){
        //id 최댓값 추적
        //1.stream 사용
        int maxId = products.stream()
                .map(product -> product.getId())
                .max((id1 , id2) -> id1 - id2)
                .get();

        //2.for문 사용
        int maxId2 = 0;
        for(Product p : products){
            if(p.getId() > maxId2){
                maxId2 = p.getId();
            }
        }
        Product product = new Product(maxId + 1 , name , price);
        products.add(product);
        return 1; // 한줄추가 -> 1 리턴 , n줄 추가 -> n 리턴
    }
}

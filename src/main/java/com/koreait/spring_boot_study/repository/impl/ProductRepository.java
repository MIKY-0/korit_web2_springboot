package com.koreait.spring_boot_study.repository.impl;

import com.koreait.spring_boot_study.entity.Product;
import com.koreait.spring_boot_study.model.Top3SellingProduct;
import com.koreait.spring_boot_study.repository.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository     @Slf4j
public class ProductRepository implements ProductRepo {
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

//단건 삭제. id를 통해 단건 삭제
    public int deleteProductById(int id){
        //매개변수로 들어온 id가 유효한지 검증. 유효하지 않으면 0리턴 : 예외를 던져서.
        Optional<Product> target = products.stream()
                .filter(product -> product.getId() == id) . findFirst(); // findFirst : 매칭되는 첫번쨰 데이터를 옵셔녈에 포장해서 들고와라
        if(target.isEmpty())  return 0; // 찾은 optional을 언패킹했더니 null이라면 0 리턴
        products.remove(target.get()); // Product product = target.get(); productsremove(product); 를 한줄로 작성한 것.
        log.info("상품삭제 완료 : {}" , target.get());
        return 1;
    }

    @Override // 구현안했음.
    public List<Top3SellingProduct> findTop3SellingProducts() {
        return List.of();
    }

    //단건 업데이트
    public int updateProduct(int id , String name , int price){
        //매개변수로 들어온 id가 유효한지 검증. 이번엔 Optional 안쓰고 해보기
        Product target = null;
        for(Product p : products){
            if(p.getId() == id){ // 매개변수로 들어온 id와 같다면
                target = p;
                break;
            }
        }
        if(target == null) return 0; // 타겟이 업뎃 안됐다면 id는 유효하지 않은것. -> 업데이트 0건 했다.
        //List 업데이트. set(index , 저장할 데이터). ( = 키 , 밸류)
        int index = products.indexOf(target);
        Product newProduct = new Product(id , name , price); // entity형태로 DB에 저장.
        products.set(index , newProduct); // index(target이 있던 자리)에 새로 만든 객체 newProduct가 저장됨.
        return 1; // 1건 업데이트했다.

    }
}




package com.koreait.spring_boot_study.repository.mapper;

//mapper는 xml파일과 1:1 매칭되는 자바파일. xml을 통해 db에서 가져온 결과(rs)를 자바객체로 가져오는 심부름역할.

import com.koreait.spring_boot_study.entity.Product;
import com.koreait.spring_boot_study.model.Top3SellingProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    /*
    1.conn , ps , rs , try-catch-finally.
    이런 코드들이 통째로 보일러 플레이트 코드.(자동으로 작성이 가능한 코드들.) -> 개발자는 sql만 신경썼으면 좋겠다.(캡슐화시켜버림)

    2.sql울 String자료형으로 작성했었음 지금까지.
    자바랑 sql은 독립적인데 왜 java코드로 작성해야되지? -> sql이 길어지면 java코드가 난잡해짐.(자바와 분리하고 싶다)
    -> java파일말고 xml로 따로 분리시키겠다.

(양날의검)3. jdbc에서 사용하던 rsToProduct() 메서드 -> 자동으로 지원해줌.
        객체간 참조(그래프탐색)을 지원해줌.
        DB측 : db의 테이블과 1:1 대응되는 것이 entity. -> fk컬럼을 id필드로 가지고 있음.
        JAVA측 : 객체 지향적(그래프탐색) entity -> fk컬럼을 객체자체를 필드로 가지고 있음.(연관관계 설정이라고도 함)

     */



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
    List<Top3SellingProduct> findTop3SellingProducts();
}

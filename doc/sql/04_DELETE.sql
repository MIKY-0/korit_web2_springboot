#DELETE 기본문법. 
DELETE FROM PRODUCT
WHERE PRODUCT_ID = 2;

DELETE FROM PRODUCT
WHERE PRODUCT_NAME IN ("스피커" , "프린터");

#주의사항. 절대로 WHERE 없이 DELETE 하지말자!!!! 테이블 자체가 삭제되기 때문.

/*TRUNCATE , DELETE
1.DELETE : WHERE절 가능 , 한 ROW씩 삭제 , 트랜잭션 가능 -> 복구도 가능
AUTO_INCREMENT 값을 유지.

2.TRUNCATE : WHERE 불가 , 매우 빠름 , AUTO_INCREMENT 초기화 됨.

DELETE 안전가이드 
1.WHERE문 작성.
2.PRIMARY KEY 기준으로 작성하자.
3.SELECT로 확인 먼저 하자.
4.대량삭제는 반드시 TRANSACTION을 걸어주자.(나중에)
5.FOREIGN KEY가 걸린 테이블은 자식테이블 먼저 삭제 -> 부모 테이블 삭제 또는 CASCADE 걸자.
*/

SELECT * FROM PRODUCT;
#(문제1) ID가 1인 PRODUCT_NAME에 "[HOT]" 을 붙힘.
UPDATE PRODUCT SET PRODUCT_NAME = CONCAT("[HOT]" , PRODUCT_NAME)
WHERE PRODUCT_ID = 1;

#(문제2) PRODUCT_NAME에 "[HOT]"이 포함된 상품을 삭제.
DELETE FROM PRODUCT
WHERE PRODUCT_NAME LIKE "%[HOT]%";
SET SQL_SAFE_UPDATES = 1;
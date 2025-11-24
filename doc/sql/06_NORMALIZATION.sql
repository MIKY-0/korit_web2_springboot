CREATE TABLE raw_table (
    order_id INT,
    customer_name VARCHAR(50),
    customer_phone VARCHAR(20),
    customer_address VARCHAR(100),
    product_names VARCHAR(200),
    product_prices VARCHAR(100),
    quantities VARCHAR(50),
    order_date DATETIME
);

INSERT INTO raw_table VALUES
(1, '김철수', '010-1234-5678', '서울시 강남구', '노트북, 마우스', '1500000, 30000', '1, 2', '2025-11-15'),
(2, '이영희', '010-2345-6789', '부산시 해운대구', '키보드', '80000', '1', '2025-11-16'),
(3, '김철수', '010-1234-5678', '서울시 강남구', '모니터', '400000', '1', '2025-11-17');

SELECT * FROM RAW_TABLE;

/*
제1 정규화.
-PRODUCT_NAMES , PRODUCT_PRICES -> 하나의 셀에 여러 값이 기록되어 있음
문제점 : 검색 불가능(마우스만 주문한 사람을 조회 불가능). 집계함수 불가능. SUM , AVG.

제2 정규화.
-부분 종속제거 : PK가 두개 이상의 칼럼으로 이루어져 있을 때 , 일부 PK에만 종속된 칼럼이 있으면 안됨.(사용하지 않는 PK가 있으면 안됨).
CUSTOMER_PHONE , CUSTOMER_ADDRESS는 주문 , 상품과 전혀 관계없고 , 단지 CUSTOMER_NAME에 관계가 있음. 단순한 고객정보.
해결 : 고객정보 테이블 , 주문정보 테이블 , 주문별 상품정보 테이블. 이렇게 테이블 분리.

제3 정규화.
-이행적 종속 제거 : PK가 아닌 일반칼럼에 종속된 칼럼을 제거.
ORDER_ID로 CUSTOMER_ID를 찾고 CUSTOMER_ID로 CUSTOMER_PHONE을 찾는다고 가정.
CUSTOMER_PHONE이 ORDER_ID에 이행적으로 종속되어있음.
A -> B -> C 일때 , A -> B / B -> C로 분리.
*/

/*ORDER_DETAIL TABLE
ORDER_DETAIL_ID - PK
ORDER_ID - FK
PRODUCT_ID - FK
QUANTITY
---ORDERS 테이블에 QUANTITY 넣으면 안될까?
ORDER_ID가 1인 경우 , 노트북 1개 , 마우스 2개 일 때
ORDER_ID(PK)  CUSTOMER_ID  PRODUCT_ID  QUANTITY
1				1			1(노트북)			1
1				1			2(마우스)			2
*/

/*테이블 분리
RAW_TABLE -> 1,2,3 정규화
ORDERS TABLE -> 누가 언제 주문했는가?
ORDER_DETAILS -> 무슨 상품을 몇개 주문했는가?
--QUANTITY가 ORDER에 있으면 안되는 이유 : 
실제로 하나의 주문에 여러 상품이 들어가기 때문. -> ORDER_ID가 중복 되어야한다 -> ORDERS 테이블은 불가능. -> ORDER_DETAILS 테이블로 분리하자.
CUSTOMER TABLE -> 고객정보 , PRODUCT TABLE -> 상품정보.
*/ 
CREATE TABLE ORDER_DETAILS (
	ORDER_DETAIL_ID INT AUTO_INCREMENT PRIMARY KEY,
    ORDER_ID INT NOT NULL,
    PRODUCT_ID INT NOT NULL,
    QUANTITY INT NOT NULL CHECK(QUANTITY > 0)
    );

#RAW_TABLE -> 정규화시킴. 정규화된 테이블을 다시 비정규화된 TABLE로 조회하는 방법 : JOIN.
#JOIN(FK를 기준으로 실행)

#(문제1) 고객테이블에 데이터 삽입(RAW_TABLE에 있는 고객데이터)
INSERT INTO CUSTOMERS (CUSTOMER_NAME , CUSTOMER_PHONE , CUSTOMER_ADDRESS)
SELECT CUSTOMER_NAME , CUSTOMER_PHONE , CUSTOMER_ADDRESS FROM RAW_TABLE; # SELECT결과를 그대로 INSERT.

#PRODUCT 데이터 있음 , ORDERS 데이터 있음
#ORDER_DETAIL 더미데이터 INSERT
INSERT INTO ORDER_DETAILS (ORDER_ID , PRODUCT_ID , QUANTITY)
VALUES (1 , 1 , 1) , (1 , 2 , 2) , (2 , 3 , 1) , (3 , 4 , 1); 

INSERT INTO PRODUCT (PRODUCT_ID , PRODUCT_NAME , PRODUCT_PRICE)
VALUES (1 , '노트북' , 1500000);	









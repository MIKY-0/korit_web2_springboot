#테이블 생성
CREATE DATABASE web2;
#테이블 선택
USE web2;
/*1.테이블 생성
CREATE TABLE [테이블명](
	속성1이름 자료형 제약사항,
    속성2이름 자료형 제약사항
    );
    데이터베이스 자료형 : int - 일반 정수 , BIGINT : 매우 큰 정수(LONG) , DOUBLE : 실수
	문자열 : VATVHAR(N) - 가변길이(n 사이즈) , TEST : 긴 텍스트.
    
    -날짜/시간 : DATETIME : 날짜 + 시간저장 -> 자바의 LOCALDATETIM 스키마 타입 2025-11-20 19.34.30
    2. NOT NULL , UNIQUE(중복값 저장 = 저장하지 않겠다.) , PROMARY KEY : 식별자 NOT NULL + UNIQUE ->  주민번호 : 하나의 행을 고유하느 고유갑 -> 
    자바의 인스턴스와 1:1 대응.
    AUTO_INCREMENT :자동 증가하는 번호(주로 PRIMARY KET와 함께 사용.)
    FOREIGN KEY : 외래키(다른 테이블의 PRIMARY KEY) 정규화 , 조인할 때 사용.
    DEFAULT : 값이 없을 때 기본값 지정.   CHECK : 값 검증(특정 조건을 만족하는 값만 허용) - 스프링부트에서도 검증하지만 더블체크 하는걸 권장.
*/
CREATE TABLE PRODUCT (
	PRODUCT_ID INT AUTO_INCREMENT PRIMARY KEY ,
    PRODUCT_NAME VARCHAR(100)  NOT NULL,
    PRODUCT_PRICE INT NOT NULL CHECK(PRODUCT_PRICE > 0)
    );
    
    #스프링부트 서버에서 DML쿼리를 DBMS로 전송
#INSERT. 컬럼 지정하면 순서 상관없음.
INSERT INTO PRODUCT (PRODUCT_ID , PRODUCT_NAME , PRODUCT_PRICE)
VALUES (1 , "노트북" , 1500000);
INSERT INTO PRODUCT (PRODUCT_NAME , PRODUCT_ID , PRODUCT_PRICE)
VALUES ("노트북" , 2 , 2000000);

#AUTO_INCREMENT 활용. AUTO_INCREMENT가 걸려있는 컬럼은 생략하거나 NULL 입력해도 자동 증가됨.
INSERT INTO PRODUCT(PRODUCT_NAME , PRODUCT_PRICE)
VALUES ("마우스" , 30000);

#전체 컬럼의 순서로 넣으면 컬럼명 생략 가능.
INSERT INTO PRODUCT
VALUES (NULL , "USB 메모리" , 20000);

#여러행을 한번에 INSERT 가능
 INSERT INTO PRODUCT
 VALUES (NULL , "HDML 케이블" , 8000) , (NULL , "마이크" , 45000) , (NULL , "헤드셋" , 98000);
 
 #(문제1) "노트북 쿨링 패드" , 가격 25000 , ID 자동추가.
 INSERT INTO PRODUCT 
 VALUES (NULL , "노트북 쿨링패드" , 25000);
    
#(문제2)한번에 USB-C 케이블 : 7000원 , 고속충전기 : 18000 , 블루투스 리모컨 : 9000 추가.
INSERT INTO PRODUCT
VALUES (NULL , "USB - C 케이블" , 7000) , (NULL , "고속충전기" , 18000) , (NULL , "블루투스 리모컨" , 9000);

#PRIMARY KEY라서 ID가 50인 RAW가 있으면 에러 발생.(EX. 14번까지 1씩 증가하다가 50으로 점프뛰고 그다음 생성하는 AI는 51부터 시작.)
INSERT INTO PRODUCT
VALUES (50 , "갤럭시 S1" , 150000);
    
    SELECT * FROM PRODUCT;
    
    

    
    
    
    
    
    
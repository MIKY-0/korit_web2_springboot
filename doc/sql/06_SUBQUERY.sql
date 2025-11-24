/*
서브쿼리란? sql문(select로 시작해서 ;끝나는 문장) 안에 포함된 또 다른 sql문
1. select안에 사용 -> 스칼라 서브쿼리
2. where절에 사용 -> 중첩 서브쿼리(단일행 서브쿼리 , 다중행 서브쿼리)
3. from절에 사용 -> 인라인 뷰
*/
create table orders (
	order_id int primary key,
    customer_id int not null, # -> 외래키(다른 테이블의 pk)
    product_id int not null, # -> 외래키(다른 테이블의 pk)
    order_date datetime
);

insert into
	orders
values
	(1, 1, 1, '2024-01-10'),
    (2, 2, 3, '2024-01-12'),
    (3, 1, 1, '2024-01-15'),
    (4, 3, 8, '2024-02-10'),
    (5, 2, 4, '2024-02-11');

# select안에 사용하는 서브쿼리
# 각 상품의 주문 수를 같이 출력
select
	p.product_name,
    p.product_price,
    (	# 이 상품이 orders 테이블에서 몇개 주문되었는지 계산
		select
			count(*)
        from
			orders o
		where
			o.product_id = p.product_id 
            # product_id 컬럼은 orders랑 product 둘다 존재함
            # 그래서 product 테이블은 p로, orders 테이블은 o로 식별해준 것
    ) as `order_count`
from
	product p;

# 평균가격을 스칼라 서브쿼리로 조회 - 전체 평균을 각 상품 옆에 출력

select
	product_name,
    product_price,
    (
		select
			avg(product_price)
		from
			product
    ) as `avg_price` # ()안에 작성된 sql문을 하나의 컬럼으로 보겠다.
from
	product;

# where절에 사용되는 서브쿼리
# 가장 최근에 주문된 상품을 조회
select
	*
from
	product
where
	product_id = ( # = 연산자는 매칭되는 값이 하나일때
		select
			product_id
		from
			orders
		order by order_date desc
        limit 1
    );


# 주문내역이 존재하는 상품만 조회
select
	*
from
	product
where
	product_id in ( # in 연산자는 매칭되는 값이 여러개일때
		select
			product_id
		from
			orders
    );

# 주문내역이 존재하지 않는 상품들만 조회
select
	*
from
	product
where
	product_id not in ( # not in을 사용해서 주문된 상품 id에 없는 상품만 필터링
		# 주문된 상품 id 목록 반환
		select
			product_id
		from
			orders
    );

# exists 연산자
# 조건을 만족하는 하나의 행이라도 존재하면 TRUE리턴 , 없으면 FALSE 리턴 -> 경우에 따라 빠름.
# 주문된 내역이 있는 상품만 조회
select
	*
from
	product p
where
	# 인스턴스의 id(product_id)가 주문테이블에 존재하는지 확인
    exists ( # 행(row)의 존재여부만 판단
		select
			1 # 목적이 존재여부이기 때문에 반환값은 의미가 없다.
		from
			orders o
		where
			p.product_id = o.product_id
    );


# (문제1) where 서브쿼리를 작성해서 2024년 1월에 주문된 상품들만 조회
SELECT * FROM PRODUCT
WHERE PRODUCT_ID IN (
	SELECT PRODUCT_ID FROM ORDERS
    WHERE '2024-01-01' <= ORDER_DATE AND ORDER_DATE < '2024-02-01' 
    );
    
#EXISTS 사용해서 문제1 풀이.
SELECT * FROM PRODUCT P
WHERE EXISTS ( # 연산중인 PRODUCT_ID가 ORDERS테이블의 PRODUCT_ID에 있는지 검색
	SELECT 1 FROM ORDERS O
    WHERE P.PRODUCT_ID = O.PRODUCT_ID AND ('2024-01-01' <= ORDER_DATE AND ORDER_DATE < '2024-02-01' ) 
    #주문 내역이 있는 상품을 조회 AND 2024-01-01이상 2월 미만 상품 조회.
    );
    
#인라인뷰. 서브쿼리 결과를 하나의 테이블로 간주.(가상 테이블)
SELECT * FROM (
	SELECT PRODUCT_ID , PRODUCT_NAME , PRODUCT_PRICE , 
		CASE WHEN PRODUCT_PRICE <= 30000 THEN '저가'
			 WHEN PRODUCT_PRICE <= 100000 THEN '중가'
			 ELSE '고가'
		END AS 'PRICE_RANGE'
	FROM PRODUCT 
    ) AS VIEW_TABLE # 쿼리결과를 하나의 가상테이블로 만듦. -> 캐싱.
WHERE PRICE_RANGE = "중가"; # 가상 테이블 : 인라인뷰를 하나의 테이블로 간주하고  WHERE로 필터링.



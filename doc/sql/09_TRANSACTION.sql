/*
트랜잭션 : 여러가지 SQL문을 하나의 작업으로 묶는 문법.
1.송금을 수행할 때 나의 계좌에서 돈이 빠져나감 -> 하나의 SQL문(UPDATE)
2.상대방 계좌에 돈이 들어옴 -> 하나의 SQL문(UPDATE)
3.내역이 저장되어야 함 -> 하나의 SQL문(INSERT)
: INSERT , UPDATE , DELETE가 하나의 작업으로 묶일 수 있다.
가상 로그에 기록을 해서 복원이 가능하게 만들 수 있음. -> 1,2,3 수행 도중 하나라도 실패하면 전체작업 ROLLBACK가능.
*/
#ORDERS 삭제하기 전에 참조하는 ORDER_DETAILS 테이블의 ROW 먼저 삭제해야함. -> 하나의 트랜잭션으로 묶을 수 있음.
START TRANSACTION;

#주문상세(자식테이블) ROW 삭제
DELETE FROM ORDER_DETAILS 
WHERE ORDER_ID = 1;

#주문(부모 테이블) ROW 삭제
DELETE FROM ORDERS
WHERE ORDER_ID = 1;

#저장. COMMIT
COMMIT; # 실패시 원복. ROLLBACK.

/*
스프링부트에서 트랜잭션 쿼리를 직접 작성해서 전송하나? NO.
어노테이션으로 트랜잭션을 걸어줄 수 있음.(메서드에)
메서드가 정상적으로 리턴되면 COMMIT , 메서드 실행중 예외가 생기면 ROLLBACK.
*/






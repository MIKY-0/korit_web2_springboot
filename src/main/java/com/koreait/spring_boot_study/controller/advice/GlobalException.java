package com.koreait.spring_boot_study.controller.advice;

import com.koreait.spring_boot_study.exception.PostNotFoundException;
import com.koreait.spring_boot_study.exception.ProductInsertException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//예외는 catch 되지 않으면 계속 전파(호출한 쪽으로 돌아간다)
//컨트롤러 까지 전파되었지만 catch가 없었음 -> dispather servlet에 catch가 존재
//1.RestControllerAdvice 어노테이션을 가진 클래스를 찾음( -> 핸들러를 찾음)
//2.전파되어 온 예외의 클래스를 처리할 수 있는 컨트롤러를 찾는다.
//3.찾으면 해당 컨트롤러를 실행
@RestControllerAdvice
public class GlobalException {
    //게시글 찾을 수 없음 , 조회불가(404)

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<?> handlePostNotFound(PostNotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(ProductInsertException.class)
    public ResponseEntity<?> handleProductError(
            ProductInsertException e
    ){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}

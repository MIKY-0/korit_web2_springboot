package com.koreait.spring_boot_study.controller.advice;

import com.koreait.spring_boot_study.exception.PostInsertException;
import com.koreait.spring_boot_study.exception.PostNotFoundException;
import com.koreait.spring_boot_study.exception.ProductInsertException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//예외는 catch 되지 않으면 계속 전파(호출한 쪽으로 돌아간다)
//컨트롤러 까지 전파되었지만 catch가 없었음 -> dispather servlet에 catch가 존재
//1.RestControllerAdvice 어노테이션을 가진 클래스를 찾음( -> 핸들러를 찾음)
//2.전파되어 온 예외의 클래스를 처리할 수 있는 컨트롤러를 찾는다.
//3.찾으면 해당 컨트롤러를 실행
@RestControllerAdvice
public class GlobalHandlerException {
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
    //validation 예외처리 핸들러(추가 , 수정) : 400
    //validation에 실패하면 MethodArgumentNotValidException을 던지게 됨.
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationHandler(MethodArgumentNotValidException e) {
        //해당 Dto에 validation 어노테이션이 붙은 필드를 모두 검사
        //여러개중 한가지 필드만 에러에 추가되는게 아님 -> name , price 둘다 검증 실패시 name , price의 예외를 둘다 던진다.
        //원래는 name의 예외를 던지고 price의 예외는 무시했었음.
        //ErrorMap을 리턴할건데 , 필드가 여러개니까 Map이 여러개. 리턴값이 Map이 여러개 들어간 List리턴.
        List<Map<String , String>> errorResp = null;
        BindingResult bindingResult = e.getBindingResult();

        if(bindingResult.hasErrors()){
            bindingResult.getFieldErrors() // 필드에러들을 List로 리턴
                    .stream() // [ 객체1,객체2....]를
                    .map(fieldError -> Map.of(
                            fieldError.getField() , fieldError.getDefaultMessage()
                    )) //[Map1,Map2...]로 변환했음. 그게 List<Map<String , String>>이 하는 역할.
                    .collect(Collectors.toList());
        }
        /* ↓ 최종 리턴값.
        [
            {
                "name" : "이름은 비울 수 없음"
            {
            ,
            {
                "price" : "가격은 음수일 수 없음"
            }
        ]
         */
        return ResponseEntity.status(HttpStatus.BAD_REQUEST) . body(errorResp);
    }

    @ExceptionHandler(PostInsertException.class)
    public ResponseEntity<?> handlePostError(
            PostInsertException e
    ){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}

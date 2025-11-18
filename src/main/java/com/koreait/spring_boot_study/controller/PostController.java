package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.AddPostReqDto;
import com.koreait.spring_boot_study.dto.PostResDto;
import com.koreait.spring_boot_study.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController     @RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //    @GetMapping("/title/all")
    public ResponseEntity<?> getPostTitles() {
        List<String> posts = postService.getPostTitle(); {
            return ResponseEntity.ok(posts);
        }
    }

    //게시글 전체 리턴
    @GetMapping("/all")
    public ResponseEntity<?> getAllPost(){
        List<PostResDto> dtos = postService.getAllPost();
        return ResponseEntity.ok(dtos);
    }
    //localhost:8080/post/2 -> Get: 2번게시글 참조
    @GetMapping("{id}")
    public ResponseEntity<?> getPostById(@PathVariable int id){
        PostResDto dto = postService.getPostById(id);
        return ResponseEntity.ok(dto);
    }

        //전체 게시글 제목 조회
        @GetMapping("/title/all")
        public ResponseEntity<?> getPostTitle ( @PathVariable int id){
            return ResponseEntity.ok(postService.getPostNameById(id));
        }

        //(문제1) 단건 추가 컨트롤러 -> 서비스 -> 레포 코드 작성(validation 사용해보자)
        @PostMapping("/add")
    public ResponseEntity<?> post(@Valid @RequestBody AddPostReqDto dto){
        postService.addPost(dto);
        return ResponseEntity . status(HttpStatus.CREATED) . body("업데이트 완료");
        }

        //4.게시글 title , content를 담은 List응답
    }


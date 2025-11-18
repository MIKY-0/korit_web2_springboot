package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        @GetMapping("/title/{id}")
        public ResponseEntity<?> getPostTitle (@PathVariable int id){
            return ResponseEntity.ok(postService.getPostNameById(id));
        }
    }


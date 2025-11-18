package com.koreait.spring_boot_study.service;

import com.koreait.spring_boot_study.entity.Post;
import com.koreait.spring_boot_study.exception.PostNotFoundException;
import com.koreait.spring_boot_study.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    //전체 게시글 조회
    public List<String> getPostTitle(){
        return postRepository.findAll().stream()
                .map(post -> post.getTitle())
                .collect(Collectors.toList());
    }
    //단건 조회
    public String getPostNameById(int id){
        Optional<Post> postOptional = postRepository.findTitleById(id);
        //옵셔널을 언패킹하는 다른 방법(예외도 같이 던질 수 있음)

        //옵셔널.orElseThorw() : Optional에 포장된 객체가 null이 아니면 post변수에 담고 null이면 예외를 던져라.
        Post post = postOptional.orElseThrow(() -> new PostNotFoundException("게시물을 찾을 수 없습니다"));

        return post.getTitle();
    }
}

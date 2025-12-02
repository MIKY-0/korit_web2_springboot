package com.koreait.spring_boot_study.service;

import com.koreait.spring_boot_study.dto.AddPostReqDto;
import com.koreait.spring_boot_study.dto.ModifyPostReqDto;
import com.koreait.spring_boot_study.dto.PostResDto;
import com.koreait.spring_boot_study.entity.Post;
import com.koreait.spring_boot_study.exception.PostInsertException;
import com.koreait.spring_boot_study.exception.PostNotFoundException;
import com.koreait.spring_boot_study.repository.PostRepo;
import com.koreait.spring_boot_study.repository.impl.PostRepository;
import com.koreait.spring_boot_study.repository.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private PostMapper postRepository;

    @Autowired
    public PostService(PostMapper postRepository) {
        this.postRepository = postRepository;
    }

    //전체 게시글 조회
    public List<String> getPostTitle() {
        return postRepository.findAll().stream()
                .map(post -> post.getTitle())
                .collect(Collectors.toList());
    }

    //단건 조회
    public String getPostNameById(int id) {
        Optional<Post> postOptional = postRepository.findTitleById(id);
        //옵셔널을 언패킹하는 다른 방법(예외도 같이 던질 수 있음)

        //옵셔널.orElseThorw() : Optional에 포장된 객체가 null이 아니면 post변수에 담고 null이면 예외를 던져라.
        Post post = postOptional.orElseThrow(() -> new PostNotFoundException("게시물을 찾을 수 없습니다"));

        return post.getTitle();
    }

    //게시글 전체 리턴
    public List<PostResDto> getAllPost() {
        return postRepository.findAll() // 결과 : List<Post>
                .stream().map(post -> new PostResDto(post.getTitle(), post.getContent())).collect(Collectors.toList());
    }

    //게시글 단건 리턴
    public PostResDto getPostById(int id) {
        Post post = postRepository.findTitleById(id) //결과 : Optional<Post>
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없음"));
        return new PostResDto(post.getTitle(), post.getContent());
    }

    //(문제1) 단건 추가 컨트롤러 -> 서비스 -> 레포 코드 작성(validation 사용해보자)
    public void addPost(AddPostReqDto dto) {
        int updateCount = postRepository.insertPost(dto.getTitle(), dto.getContent());
        if (updateCount <= 0) {
            throw new PostInsertException("게시글 업데이트 중 오류");
        }
    }

    //(문제2) id를 받아서 게시글 삭제하는 컨트롤러 , 서비스 , 레포. (난 Optional 사용해서 풀거임)
    public void removePost(int id) {
        int successCount = postRepository.deletePostById(id);
        if (successCount <= 0) {
            throw new PostNotFoundException("해당 게시글은 존재하지 않음");
        }
    }

    //(문제3)id와 dto를 받아서 게시글을 업데이트하는 컨트롤러 , 서비스 , 레포. (난 Optional 사용해서 풀거임)
    public void modifyPost(int id, ModifyPostReqDto dto) {
        int successCount = postRepository.updatePostById(id, dto.getTitle(), dto.getContent());
        if (successCount <= 0) {
            throw new PostNotFoundException("해당 게시글은 존재하지 않음");
        }
    }
}
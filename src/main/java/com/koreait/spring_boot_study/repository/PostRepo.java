package com.koreait.spring_boot_study.repository;

import com.koreait.spring_boot_study.entity.Post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface PostRepo {

    //전체 게시글 조회
    public List<Post> findAll() ;
    //게시글 단건 조회
    public Optional<Post> findTitleById(int id);

    //단건 추가
    public int insertPost(String title , String content);

    //단건 삭제 by id
    public int deletePostById(int id);

    //단건 업데이트 by id and entity
    public int updatePostById(int id , String title , String content);
}

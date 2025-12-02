package com.koreait.spring_boot_study.repository.mapper;

import com.koreait.spring_boot_study.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {
    //전체 게시글 조회
    public List<Post> findAll();
    //게시글 단건 조회
    public Optional<Post> findTitleById(int id);

    //단건 추가
    public int insertPost(@Param("title") String title , @Param("content") String content);
    //Param안에 매개변수는 xml의 #{title}과 동일하게 작성
    //컨트롤러는 구체적인 서비스 클래스를 알 필요 없음. 서비스는 구체적인 레포지토리 클래스를 알 필요 없음.
    //-> 컨트롤러는 서비스의 메서드 시그니처들만 알면 됨.(넘겨줄 매개변수 , 받을 리턴값)
    //서비스는 레포지토리의 메서드 시그니처들만 알면 됨(넘겨줄 매개변수 , 받을 리턴값)
    // contoroller <interface> service <interface> repository : 가장 이상적

    //단건 삭제 by id
    public int deletePostById(int id);

    //단건 업데이트 by id and entity
    public int updatePostById(@Param("id") int id , @Param("title") String title , @Param("content") String content);


    //(문제)1.titleKeyWord혹은 contentKeyWord로 post를 상세검색하는 xml,mapper,service,controller 작성.
    List<Post> searchDetailPosts(@Param("titleKeyWord") String titleKeyWord , @Param("contentKeyWord") String contentKeyWord);

    //(문제)2.Post + Comment 조인 조회. ->
    /* 최종결과 : postTitle : ~
    postContent : ~
    comments : [
        '댓글1',
        '댓글2',
        '댓글3'
        ]
    */
    Optional<Post> findPostWithComments();

}

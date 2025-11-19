package com.koreait.spring_boot_study.repository;

import com.koreait.spring_boot_study.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository     @Slf4j
public class PostRepository {
    //CRUD(생성 , 조회 , 수정 , 삭제) (DDL)
    //DB 대용 필드 - sql쿼리로 DB에서 데이터를 받아옴(주로 LIST로)
    private List<Post> posts = new ArrayList<>(
            Arrays.asList(
                    new Post(1 , "페이커 vs 손흥민" , "누가이김?"),
                    new Post(2 , "박지성 vs 손흥민" , "누가이김?"),
                    new Post(3 , "피카츄 vs 라이큐" , "누가이김?"),
                    new Post(4 , "스프링부트 공부중" , "반복 ㄱㄱ")
            )
    );
    //전체 게시글 조회
    public List<Post> findAll() {return posts;}
    //게시글 단건 조회
    public Optional<Post> findTitleById(int id){ //Optional 장점 : 바로 리턴가능
        return posts.stream()
                .filter(post -> post.getId() == id)
                .findFirst(); // 객체가 있으면 객체를 optinal로 감싸서 리턴 , 없으면 null을 optional로 감싸서 리턴
    }

    //(문제1) 단건 추가 컨트롤러 -> 서비스 -> 레포 코드 작성(validation 사용해보자)
    public int insertPost(String title , String content){
        int addId = posts . stream() . map(post -> post.getId()) . max((id1 , id2) -> id1 - id2) . get();
        Post post = new Post(addId + 1 , title , content); // sql insert쿼리와 동일
        posts.add(post);
        return 1;
    }

    //(문제2) id를 받아서 게시글 삭제하는 컨트롤러 , 서비스 , 레포. (난 Optional 사용해서 풀거임)
    public int deletePostById(int id){
        Optional<Post> target = posts.stream() // Optional<> -> 코드를 선언하는 쪽에서 타입을 지정하겠다 : 제네릭
                .filter(post -> post.getId() == id) . findFirst();
        if(target.isEmpty()) return 0;
        posts.remove(target.get());
        log.info("게시글 삭제 완료 : {}" , target.get());
        return 1;
    }

    //(문제3)id와 dto를 받아서 게시글을 업데이트하는 컨트롤러 , 서비스 , 레포. (난 Optional 사용해서 풀거임)
    public int updatePostById(int id , String title , String content){
        Optional<Post> target = posts.stream()
                .filter(post -> post.getId() == id) . findFirst();
        if(target.isEmpty()) return 0;
        int index = posts.indexOf(target.get());

        Post newPost = new Post(id , title , content);
        posts.set(index , newPost);
        log.info("게시글 수정 완료 : {}" , target.get());
        return 1;
    }
}







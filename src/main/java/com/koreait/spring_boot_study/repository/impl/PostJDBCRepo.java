package com.koreait.spring_boot_study.repository.impl;

import com.koreait.spring_boot_study.entity.Post;
import com.koreait.spring_boot_study.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class PostJDBCRepo implements PostRepo {
    private DataSource dataSource;

    private void close(AutoCloseable a){
        if(a != null){
            try{a.close();} catch(Exception e) {}
        }
    }

    @Autowired
    public PostJDBCRepo(DataSource dataSource) {this.dataSource = dataSource;}
    private Post rsToPost(ResultSet rs) throws SQLException{ // 이 메서드 작성시 while문에서 작성할 코드가 간결해짐.
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String content = rs.getString("content");
        Post post = new Post(id,  title , content);
        return post;
    }
    //(문제1) findAllPosts를 작성.
    @Override
    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select id , title , content from post";
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Post post = rsToPost(rs);
                posts.add(post);
//                int id = rs.getInt("id");  //이 코드들을 위 두줄 코드로 축약 가능. rsToPost메서드 때문.
//                String title = rs.getString("title");
//                String content = rs.getString("content");
//                Post post = new Post(id, title, content);
//                posts.add(post);
            }
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
        } finally {
            close(rs); close(ps); close(con);
        }
        return posts;
    }

    @Override
    public Optional<Post> findTitleById(int id) {
        String sql = "select id , title , content from post where id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = dataSource.getConnection(); // 도로를 깔고
            ps = conn.prepareStatement(sql); // 화물차에 sql문 실어서 도로에 넣음.
            ps.setInt(1 , id); // ?에 들어갈 값
            rs = ps.executeQuery(); // 화물차 출발하고 결과물(rs : ResultSet)을 가져옴.
            while(rs.next()){ // rs.next의 다음줄이 존재한다면 실행.
                Post targetPost = rsToPost(rs);
                return Optional.of(targetPost); // targetPost를 Optional로 감싸서 리턴.
            }
        }catch(SQLException e){
            e.printStackTrace(); // 콘솔에 에러스택 모두 출력
        }finally{
            close(rs); /* 결과반납 */ close(ps); /* 화물차반납 */ close(conn); /* 도로반납 */
        }
         return Optional.empty(); //Optional이 비어있다는 것을 명시적으로 리턴.
        //옵셔널.orElseThorw(() -> new 예외클래스()) 작동한다.   옵셔널isEmpty() -> true.
        //옵셔널.isPresent() -> false
    }

    @Override
    public int insertPost(String title, String content) {
        String sql = "insert into post (title , content) values (? , ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1 , title);
            ps.setString(2 , content);
            int successCount = ps.executeUpdate();
            return successCount;
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            close(ps); close(conn);
        }
        return 0;
    }

    @Override
    public int deletePostById(int id) {
        /*
        entity 클래스이름은 테이블명 파스칼표기법으로 작성. id -> postId.
        컬럼명은 스네이크표기법 , 필드명은 카멜표기법.
         */
        String sql = "delete from post where id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1 , id);
            return ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            close(ps); close(conn);
        }
        return 0;
    }

    @Override
    public int updatePostById(int id, String title, String content) {
        StringBuilder sb = new StringBuilder();
        sb.append("update post set title = ? , content = ?");
        sb.append("where id = ?");
        String sql = sb.toString();
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1 , title);
            ps.setString(2 , content);
            ps.setInt(3 , id);
            return ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            close(ps); close(conn);
        }
        return 0;
    }
}

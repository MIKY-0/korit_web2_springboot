package com.koreait.spring_boot_study.repository.impl;

import com.koreait.spring_boot_study.entity.Product;
import com.koreait.spring_boot_study.model.Top3SellingProduct;
import com.koreait.spring_boot_study.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Qualifier("jdbc")
@Repository
public class ProductJDBCRepo implements ProductRepo {


    private final DataSource dataSource; // DB경로 or 비밀번호처럼 민감한 정보들을 소스코드로 노출되지 않게 yaml에 적어둔 DB 설정값을
    //스프링이 자동으로 읽어서 그값을 가진 DataSource 객체를 자동으로 만들어 Bean으로 등록해줌.

    @Autowired
    public ProductJDBCRepo(DataSource dataSource) {this.dataSource = dataSource;}

    private void close(AutoCloseable ac){ // close(rs); close(ps); close(conn); 로 줄이기 위한 메서드.
        // conn , ps , rs -> AutoCloseable이라는 인터페이스를 이식받고 있음.
        if(ac != null){ // 그 인터페이스에서 close라는 추상메서드가 있음.
            try {ac.close();} catch(Exception e) {}
        }
    }

    public List<Product> findAllProducts(){
        List<Product> products = new ArrayList<>();
        //이 부분에서 DB로 sql전송 / 응답받기
        Connection conn = null; // DB 와 실제 연결을 수행하는 객체.
        PreparedStatement ps = null; // Connection의 필드로 주입되어서 DB로 전송될 sql객체.
        ResultSet rs = null; //DB에서 가져온 데이터를 자바에서 읽기 좋은 형태(자바객체)로 제공하는 객체.
        //ResultSet은 select 할 때만 필요. -> 테이블을 결과로 받을때 만 필요.

        String sql = "select product_id , product_name , product_price from product";
        try{ // db에서 제공하는 연결을 하나 대여해 옴.
          conn = dataSource.getConnection();
          ps = conn.prepareStatement(sql); // preparedStatement에는 실제 문자열로 sql쿼리가 들어가야 함.
            //작성한 ps를 db에 전달.
          //DB에서 조회한 결과를 rs안에다가 테이블 형태로 들고온다고 보면 됨.
          rs = ps.executeQuery(); //작성한 ps를 db에 전달하고 실행시킨 결과를 가져옴. select -> executeQuery() : rs 리턴.
          while(rs.next()){ //rs.next는 테이블에서 한줄씩 읽어올건데 그 다음줄이 존재하는지 검사하는 메서드.
              //next가 true면 해당 줄의 컬럼 값들을 가져올 수 있음.
              int id = rs.getInt("product_id"); // product_id를 읽어오세요
              String name = rs.getString("product_name"); // product_name을 읽어오세요
              int price = rs.getInt("product_price"); //product_price를 읽어오세요
              Product product = new Product(id , name , price);
              products.add(product);
          }
        }
        catch(SQLException e){ //String sql -> sql을 잘못 작성했거나 DB 에러 처리해줌.
            System.out.println(e.getStackTrace()); // DB에러들을 출력.
        } finally{ //대여했던 객체들을 반납.
//            if(rs != null){ // rs -> ps -> conn 순으로 close
//                try {rs.close();} catch(Exception e) {}
//                try {ps.close();} catch(Exception e) {}
//                try {conn.close();} catch(Exception e) {}
//            }
            close(rs); close(ps); close(conn); // 위 try-catch 3개 코드를 AutoCloseable을 사용해서 줄여서 작성한것.
        }
        //conn = dataSource.getConnection(); // db와 연결. 이 코드만 쓰면 에러생김. -> try-catch 해줘야됨.
        return products;
    }
    public String findProductNameById(int id){
        String sql = "select product_name from product where product_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1 , id); // sql 문자열 왼쪽부터 스캔해서 1번째로 나오는 ?에다가 매개변수로 들어온 id를 넣어라.
            rs = ps.executeQuery();
            if(rs.next()) {return rs.getString("product_name");}
        }catch(SQLException e){
            System.out.println(e.getStackTrace());
        }finally{
            close(rs); close(ps); close(conn);
        }
        return "해당 id의 상품은 존재하지 않음";
    }
    @Override
    public int insertProduct(String name, int price) {
        String sql = "insert into product (product_name , product_price) values (? , ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1 , name); // sql문자열 왼쪽부터 스캔해서 1번째 나오는 ?에 매개변수 name값 삽입.
            ps.setInt(2 , price); // sql문자열 왼쪽부터 스캔해서 2번째 나오는 ?에 매개변수 price값 삽입.
            int successCount = ps.executeUpdate(); // 영향받은 row의 수
            return successCount;
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            close(ps); close(conn);
        }
        return 0;
    }

    @Override
    public int deleteProductById(int id) {
        String sql = "delete from product where product_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1 , id);
            return ps.executeUpdate(); // 쿼리로 영향받은 row수를 db가 리턴해줌.
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            close(ps); close(conn);
        }
        return 0;
    }

    @Override
    public List<Top3SellingProduct> findTop3SellingProducts() {
        List<Top3SellingProduct> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select p.product_id , p.product_name , sum(ob.quantity) as 'total_sold_count' ");
        sb.append("from product p join order_details od ");
        sb.append("on p.product_id = od.product_id ");
        sb.append("group by p.product_id , p.product_name ");
        sb.append("order by total_sold_count desc ");
        sb.append("limit 3");
        String sql = sb.toString();
        try{
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("product_id");
                String name = rs.getString("product_name");
                int totalSoldCount = rs.getInt("total_sold_count");

                result.add(new Top3SellingProduct(id , name , totalSoldCount));
            }
            return result;
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            close(rs); close(ps); close(conn);
        }
        return List.of();
    }

    @Override
    public int updateProduct(int id, String name, int price) {
        //StringBuilder
        StringBuilder sb = new StringBuilder();
        sb.append("update product");
        sb.append("set product_name = ? , product_price = ?");
        sb.append("where product_id = ?");
        String sql = sb.toString();

        String sql2 = "update product set product_name = ? , product_price = ? where product_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1 , name);
            ps.setInt(2 , price);
            ps.setInt(3 , id);
            return ps.executeUpdate(); // 단건이라 1리턴될 것. / id가 이상하면 0리턴
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            close(ps); close(conn);
        }
        return 0;

    }
}

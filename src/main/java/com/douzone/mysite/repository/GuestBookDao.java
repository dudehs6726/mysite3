package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public GuestBookVo get(long valNo) {
		
		return sqlSession.selectOne("guestbook.getByLong", valNo);
	}
	/*
	public boolean insert(GuestBookVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "insert into guestbook values ( "
					+ " null, ?, ?, ?, now())";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getMessage());
			
			int count = pstmt.executeUpdate();
			result = count == 1;
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	*/
	public Long insert(GuestBookVo vo) {
		sqlSession.insert("guestbook.insert", vo);
		long no = vo.getNo();
		return no;
	}
	
	public boolean delete(GuestBookVo vo) {
		int vInt = 0;
		vInt = sqlSession.delete("guestbook.delete", vo);
		
		return vInt > 0;
	}
	
	public List<GuestBookVo> getList(){
			
		List<GuestBookVo> list = sqlSession.selectList("guestbook.getList");
		
		return list;
	}
	
	public List<GuestBookVo> getList(int page){

		List<GuestBookVo> list = sqlSession.selectList("guestbook.getListByPage", page);

		return list;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {
			// 1.JDBC Driver(MySQL) 로딩
			Class.forName("com.mysql.jdbc.Driver");
			
			// 2.연결하기(Connection 객체 얻어오기)
			String url = "jdbc:mysql://localhost:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패" + e);
		}
		
		return conn;
	}

}

package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.douzone.mysite.vo.GuestBookVo;
import com.douzone.mysite.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
	public UserVo get(UserVo paramVo) {
		
		//Map 처리 방식
		/*
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("password", password);
		*/
		UserVo userVo = sqlSession.selectOne("user.getByEmailAndPassword", paramVo);
		
		return userVo;
	}
	
	public UserVo get(Long paramNo) {
		UserVo userVo = sqlSession.selectOne("user.getByNo", paramNo);
		return userVo;
	}
	
	public UserVo get(String email) {
		
		UserVo userVo = sqlSession.selectOne("user.getByEmail", email);
		
		return userVo;
		//return sqlSession.selectOne("user.getByEmail", email);
	}
	
	public int insert(UserVo vo) {
		return sqlSession.insert("user.insert", vo);
	}
	
	public void update(UserVo vo) {
		sqlSession.update("user.update", vo);
	}
	
	public List<GuestBookVo> getList(){
		
		List<GuestBookVo> list = new ArrayList<GuestBookVo>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "select a.no, "
					+ "			 a.name, "
					+ "			 a.password, "
					+ "			 a.message, "
					+ "			 DATE_FORMAT(a.reg_date, '%Y-%m-%d %h:%m:%s ') as reg_date "  
					+ "  	from guestbook a"  
					+ "  	order by a.no desc ";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				long no = rs.getLong(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String message = rs.getString(4);
				String regDate = rs.getString(5);
				
				GuestBookVo vo = new GuestBookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setPassword(password);
				vo.setMessage(message);
				vo.setRegDate(regDate);
				
				list.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
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
		
		return list;
	}
	
}

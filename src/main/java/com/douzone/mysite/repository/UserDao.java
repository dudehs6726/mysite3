package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.UserDaoException;
import com.douzone.mysite.vo.GuestBookVo;
import com.douzone.mysite.vo.UserVo;

@Repository
public class UserDao {
	
	public UserVo get(String email, String password) {
		UserVo result = null;
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "select no, name"
					+ 	" 	from user "
					+   "  where email = ? "
					+ "      and password = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				long no = rs.getLong(1);
				String name = rs.getString(2);
				
				result = new UserVo();
				result.setNo(no);
				result.setName(name);	
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
		
		return result;
	}
	
	public UserVo get(Long v_no) {
		UserVo result = null;
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "select no, name, email, gender"
					+ 	" 	from user "
					+   "  where no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, v_no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				long no = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String gender = rs.getString(4);
				
				result = new UserVo();
				result.setNo(no);
				result.setName(name);
				result.setEmail(email);
				result.setGender(gender);
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
		
		return result;
	}
	
	public UserVo get(String email) {
		
		UserVo result = null;
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "select no, name"
					+ 	" 	from user "
					+   "  where email = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, email);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				long no = rs.getLong(1);
				String name = rs.getString(2);
				
				result = new UserVo();
				result.setNo(no);
				result.setName(name);
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
		
		return result;
	}
	
	public boolean insert(UserVo vo) throws UserDaoException {
		int count = 0;
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "insert into user values ( "
					+ " null, ?, ?, ?, ?, now()) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
			count = pstmt.executeUpdate();
			result = count == 1;
			
		} catch (SQLException e) {
			throw new UserDaoException("회원 정보 저장 실패");
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(SQLException e) {
				throw new UserDaoException("회원 정보 저장 실패");
			}
		}
		
		return result;
	}
	
	public boolean update(UserVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = " update user " 
						  + " set name = ? , "
							+ "  gender = ? ";
						  if(vo.getPassword() != "") {
							  sql = sql + " , password = ? ";
						  }
						  sql = sql + " where no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getGender());
			if(vo.getPassword() != "") {
				pstmt.setString(3, vo.getPassword());
				pstmt.setLong(4, vo.getNo());
			}else {
				pstmt.setLong(3, vo.getNo());
			}
			
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
	
	public List<GuestBookVo> getList(){
		
		List<GuestBookVo> list = new ArrayList<GuestBookVo>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
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

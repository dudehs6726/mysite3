package com.douzone.mysite.repository;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.SiteVo;

@Repository
public class SiteDao {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
	public void update(SiteVo siteVo) {
		sqlSession.update("site.update", siteVo);
	}
	
	public SiteVo get() {
		
		return sqlSession.selectOne("site.getSite");
	}

}

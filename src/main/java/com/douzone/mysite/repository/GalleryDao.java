package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GalleryVo;

@Repository
public class GalleryDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<GalleryVo> getList(){
		
		List<GalleryVo> list = sqlSession.selectList("gallery.getList");
		
		return list;
	}
	
	public GalleryVo get(int no) {
		GalleryVo vo = sqlSession.selectOne("gallery.getByNo", no);
		return vo;
	}
	
	public void insert(GalleryVo galleryVo) {
		sqlSession.insert("gallery.insert", galleryVo);
	}
	
	public boolean delete(int no) {
		
		if(sqlSession.delete("gallery.delete", no) > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}

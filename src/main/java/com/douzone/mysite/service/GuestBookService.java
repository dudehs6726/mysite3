package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestBookDao;
import com.douzone.mysite.vo.GuestBookVo;

@Service
public class GuestBookService {

	@Autowired
	private GuestBookDao guestBookDao;
	
	public List<GuestBookVo> list() {
		
		List<GuestBookVo> list = guestBookDao.getList();
		
		return list;
	}
	
	public List<GuestBookVo> list(int page){

		page = (page-1) * 5;
		
		List<GuestBookVo> list = guestBookDao.getList(page);
		
		return list;
	}
	public GuestBookVo get(long no){
		
		return guestBookDao.get(no);
	}
	
	public long insert(GuestBookVo guestBookVo) {
		long no = 0;
		no = guestBookDao.insert(guestBookVo);

		return no;
	
	}
	
	public boolean delete(GuestBookVo guestBookVo) {
		
		return guestBookDao.delete(guestBookVo);
	
	}
}

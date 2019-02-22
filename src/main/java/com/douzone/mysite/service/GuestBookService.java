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
	
	public void insert(GuestBookVo guestBookVo) {
		long no = 0;
		no = guestBookDao.insert(guestBookVo);
		
		System.out.println(no);
	
	}
	
	public void delete(GuestBookVo guestBookVo) {
		
		guestBookDao.delete(guestBookVo);
	
	}
}

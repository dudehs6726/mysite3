package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.GuestBookVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;

	public Map<String, Object> list(BoardVo boardVo){
		
		List<BoardVo> list = boardDao.getList(boardVo);
		//int totalCount = boardDao.getPageList(v_vo, kwd);
		boardDao.getTotalCount(boardVo);
		boardDao.makeBlock(boardVo, boardVo.getPage());
		boardDao.makeLastPageNum(boardVo);
		
		//pager 알고리즘
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		//map.put("totalPageCount", toTalPageCount);
		
		return map;
	}
	
	public void write(BoardVo boardVo) {
		
		boardDao.insert(boardVo);
	
	}
	
	public void updateHit(Long no) {
		
		boardDao.updateHit(no);
	
	}
	
	public BoardVo view(Long no) {
		
		BoardVo boardVo = boardDao.get(no);
		
		return boardVo;
	
	}
	
	public void modify(BoardVo boardVo) {
		
		boardDao.update(boardVo);
		
	}
	
	public void delete(BoardVo boardVo) {
		
		boardDao.delete(boardVo);
		
	}
	
	public void answer(BoardVo boardVo) {
		
		boardVo.setDepth(1);
		
		boardDao.answer(boardVo);
		
	}
	
}

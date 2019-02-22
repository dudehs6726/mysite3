package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.repository.BoardDao;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value= "list/{page}", method=RequestMethod.GET)
	public String list(@PathVariable("page") Integer page, @ModelAttribute BoardVo boardVo, Model model) {
		

		if(page != null)
		{
			boardVo.setPage(page);
		}else {
			boardVo.setPage(1);
		}

		Map<String, Object> map = boardService.list(boardVo);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("vo", boardVo);
		
		return "/board/list";
	}
	
	@RequestMapping(value= "search/{page}/{kwd}", method=RequestMethod.GET)
	public String search(@PathVariable("page") Integer page, @PathVariable("kwd") String kwd, @ModelAttribute BoardVo boardVo, Model model) {		

		if(page != null)
		{
			boardVo.setPage(page);
		}else {
			boardVo.setPage(1);
		}
		
		if(kwd != null)
		{
			boardVo.setKwd(kwd);
		}else {
			boardVo.setKwd("");
		}
	
		Map<String, Object> map = boardService.list(boardVo);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("vo", boardVo);
		
		return "/board/list";
	}
	
	@RequestMapping(value= "search/{page}", method=RequestMethod.POST)
	public String search(@PathVariable("page") Integer page, @ModelAttribute BoardVo boardVo, Model model) {		

		if(page != null)
		{
			boardVo.setPage(page);
		}else {
			boardVo.setPage(1);
		}
	
		Map<String, Object> map = boardService.list(boardVo);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("vo", boardVo);
		
		return "/board/list";
	}
	
	@RequestMapping(value="write/{page}", method=RequestMethod.GET)
	public String write(HttpSession session, @PathVariable("page") Integer page,  Model model) {

		UserVo authUser = (UserVo)session.getAttribute("authuser");
		if(authUser == null){
			return "redirect:/";
		}
		
		model.addAttribute("page", page);
		model.addAttribute("userNo", authUser.getNo());
		
		return "/board/write";
	}
	
	@RequestMapping(value="write", method=RequestMethod.POST)
	public String write(@ModelAttribute BoardVo boardVo) {
		
		boardService.write(boardVo);
		
		return "redirect:/board/list/1";
	}
	
	@RequestMapping(value="view/{no}/{page}", method=RequestMethod.GET)
	public String view(@PathVariable("no") Long no, @PathVariable("page") Integer page,  Model model) {
		/*
		UserVo authUser = (UserVo)session.getAttribute("authuser");
		if(authUser == null){
			return "redirect:/";
		}
		*/
		
		/*
		//쿠키
		boolean isGet = false;
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie c: cookies) {
				if(c.getName().equals(no)) {
					isGet = true;
				}
			}
			
			if(!isGet) {
				//조회 증가 쿼리 메소드
				new BoardDao().updateHit(Long.valueOf(no));
				Cookie ck = new Cookie(no, no);
				ck.setMaxAge(5*60);
				response.addCookie(ck);
			}
		}
		*/
		boardService.updateHit(no);
		BoardVo boardVo = boardService.view(no);
		model.addAttribute("vo", boardVo);
		model.addAttribute("page", page);
		
		return "/board/view";
	}
	
	@RequestMapping(value="view/{no}/{page}/{kwd}", method=RequestMethod.GET)
	public String view(@PathVariable("no") Long no, @PathVariable("page") Integer page,  @PathVariable("kwd") String kwd, Model model) {
		/*
		UserVo authUser = (UserVo)session.getAttribute("authuser");
		if(authUser == null){
			return "redirect:/";
		}
		*/
		
		/*
		//쿠키
		boolean isGet = false;
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie c: cookies) {
				if(c.getName().equals(no)) {
					isGet = true;
				}
			}
			
			if(!isGet) {
				//조회 증가 쿼리 메소드
				new BoardDao().updateHit(Long.valueOf(no));
				Cookie ck = new Cookie(no, no);
				ck.setMaxAge(5*60);
				response.addCookie(ck);
			}
		}
		*/
		
		boardService.updateHit(no);
		BoardVo boardVo = boardService.view(no);
		
		if(kwd != null)
		{
			boardVo.setKwd(kwd);
		}else {
			boardVo.setKwd("");
		}
		model.addAttribute("vo", boardVo);
		model.addAttribute("page", page);
		
		return "/board/view";
	}
	
	@RequestMapping(value="modify/{no}/{page}", method=RequestMethod.GET)
	public String modify(HttpSession session, @PathVariable("no") Long no, @PathVariable("page") Integer page,  Model model) {

		UserVo authUser = (UserVo)session.getAttribute("authuser");
		if(authUser == null){
			return "redirect:/";
		}
		
		BoardVo boardVo = boardService.view(no);
		model.addAttribute("vo", boardVo);
		model.addAttribute("userNo", authUser.getNo());
		model.addAttribute("page", page);
		
		return "/board/modify";
	}
	
	@RequestMapping(value="modify", method=RequestMethod.POST)
	public String modify(@ModelAttribute BoardVo boardVo,  Model model) {
		
		boardService.modify(boardVo);
		
		return "redirect:/board/list/1";
	}

	@RequestMapping(value= "delete/{no}", method=RequestMethod.GET)
	public String delete(HttpSession session, @PathVariable("no") Long no) {

		UserVo authUser = (UserVo)session.getAttribute("authuser");
		if(authUser == null){
			return "redirect:/";
		}
		
		BoardVo boardVo = new BoardVo();
		boardVo.setNo(no);
		boardVo.setUserNo(authUser.getNo());
		
		boardService.delete(boardVo);
		
		return "redirect:/board/list/1";
	}
	
	@RequestMapping(value="answer/{no}/{page}", method=RequestMethod.GET)
	public String answer(HttpSession session, @PathVariable("no") Long no, @PathVariable("page") Integer page,  Model model) {

		UserVo authUser = (UserVo)session.getAttribute("authuser");
		if(authUser == null){
			return "redirect:/";
		}
		
		BoardVo boardVo = boardService.view(no);
		
		model.addAttribute("page", page);
		model.addAttribute("vo", boardVo);
		model.addAttribute("userNo", authUser.getNo());
		
		/*
		BoardVo boardVo = boardService.view(no);
		model.addAttribute("vo", boardVo);
		model.addAttribute("userNo", authUser.getNo());
		model.addAttribute("page", page);
		*/
		return "/board/answer";
	}
	
	@RequestMapping(value="answer", method=RequestMethod.POST)
	public String answer(@ModelAttribute BoardVo boardVo) {
		
		//BoardVo boardVo = boardService.view(no);
		
		boardService.answer(boardVo);
		//model.addAttribute("page", page);
		//model.addAttribute("vo", boardVo);
		//model.addAttribute("userNo", authUser.getNo());
		
		/*
		BoardVo boardVo = boardService.view(no);
		model.addAttribute("vo", boardVo);
		model.addAttribute("userNo", authUser.getNo());
		model.addAttribute("page", page);
		*/
		return "redirect:/board/list/1";
	}
	
}

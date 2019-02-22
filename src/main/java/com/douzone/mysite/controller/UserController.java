package com.douzone.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="join", method=RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value="join", method=RequestMethod.POST)
	public String join(@ModelAttribute UserVo userVo) {
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	@RequestMapping(value="login", method=RequestMethod.POST)
	public String login(HttpSession session, @ModelAttribute UserVo userVo, Model model) {
		UserVo authUser = userService.login(userVo);

		if(authUser == null)
		{
			/* 인증 실패 */
			model.addAttribute("result", "fail");
			return "user/login";
		}
		
		session.setAttribute("authuser", authUser);
		
		return "redirect:/";
	}
	
	@RequestMapping(value="modify", method=RequestMethod.GET)
	public String modify(HttpSession session, Model model) {
		
		UserVo authUser = (UserVo)session.getAttribute("authuser");
		if(authUser == null){
			return "redirect:/";
		}

		UserVo userVo = userService.modifyForm(authUser);
		model.addAttribute("vo", userVo);
		
		return "user/modify";
	}
	
	@RequestMapping(value="modify", method=RequestMethod.POST)
	public String modify(HttpSession session, @ModelAttribute UserVo userVo) {
		
		userService.modify(userVo);

		session.setAttribute("authuser", userVo);
		
		return "redirect:/";
	}
	
	@RequestMapping(value="logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		
		if(session != null && session.getAttribute("authuser") != null) {
			//logout 처리
			session.removeAttribute("authuser");
			session.invalidate();
		}
		
		return "redirect:/";
	}
	
}

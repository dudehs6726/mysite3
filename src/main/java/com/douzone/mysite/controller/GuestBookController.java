package com.douzone.mysite.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.GuestBookService;
import com.douzone.mysite.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {

	@Autowired
	private GuestBookService guestBookService;
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public String list(
			@ModelAttribute GuestBookVo guestBookVo, Model model) {
		
		List<GuestBookVo> list = guestBookService.list();
		model.addAttribute("list", list);
		
		return "guestbook/list";
	}
	
	@RequestMapping(value="insert", method=RequestMethod.POST)
	public String insert(
			@ModelAttribute @Valid GuestBookVo guestBookVo,
			BindingResult result,
			Model model) {
		
		if(result.hasErrors()) {
			
			model.addAllAttributes(result.getModel());//map으로 만들어 보냄
			
			List<GuestBookVo> list = guestBookService.list();
			model.addAttribute("list", list);
			return "guestbook/list";
		}
		
		guestBookService.insert(guestBookVo);
		
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping(value="delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, Model model) {
		
		model.addAttribute("no", no);
		
		return "guestbook/delete";
	}
	
	@RequestMapping(value="delete", method=RequestMethod.POST)
	public String delete(@ModelAttribute GuestBookVo guestBookVo) {
		
		guestBookService.delete(guestBookVo);
		
		return "redirect:/guestbook/list";
	}
}

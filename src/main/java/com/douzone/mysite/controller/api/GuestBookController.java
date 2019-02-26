package com.douzone.mysite.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.dto.JSONResult;
import com.douzone.mysite.service.GuestBookService;
import com.douzone.mysite.vo.GuestBookVo;

@Controller("guestApiController")
@RequestMapping("/guestbook/api")
public class GuestBookController {
	
	@Autowired
	private GuestBookService guestBookService;
	
	@RequestMapping("")
	public String main() {

		return "guestbook/index-ajax";
	}
	
	@ResponseBody
	@RequestMapping("/ajaxList")
	public JSONResult getList(@RequestParam(value="p", required=true, defaultValue="") String sPage) {
	
		if("".equals(sPage)) {
			sPage = "1";
		}
		
		if(sPage.matches("\\d*") == false) {
			sPage = "1";
		}
		
		int page = Integer.parseInt(sPage);
		List<GuestBookVo>list = guestBookService.list(page);

		return JSONResult.success(list);
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajaxInsert", method=RequestMethod.POST)
	public JSONResult insert(@RequestParam(value="name", required=true, defaultValue="") String name,
			@RequestParam(value="password", required=true, defaultValue="") String password,
			@RequestParam(value="message", required=true, defaultValue="") String message) {
		
		GuestBookVo guestBookVo = new GuestBookVo();
		guestBookVo.setName(name);
		guestBookVo.setMessage(message);
		guestBookVo.setPassword(password);
		
		long no = guestBookService.insert(guestBookVo);
		
		GuestBookVo newVo = guestBookService.get(no);

		return JSONResult.success(newVo);
			
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajaxDelete", method=RequestMethod.POST)
	public JSONResult delete(@RequestParam(value="no", required=true, defaultValue="") String no,
							@RequestParam(value="password", required=true, defaultValue="") String password) {
	
		GuestBookVo vo = new GuestBookVo();
		vo.setNo(Long.valueOf(no));
		vo.setPassword(password);
		
		if(guestBookService.delete(vo))
		{
			return JSONResult.success(vo.getNo());
		}
		else {
			return JSONResult.fail("실패");
		}

	}

}

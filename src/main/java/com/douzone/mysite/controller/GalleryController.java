package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.FileuploadService;
import com.douzone.mysite.service.GalleryService;
import com.douzone.mysite.vo.GalleryVo;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.security.Auth;
import com.douzone.security.Auth.Role;
import com.douzone.security.AuthUser;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	
	@Autowired
	private GalleryService gallryService;
	
	@Autowired
	private FileuploadService fileuploadService;
	
	@RequestMapping("")
	public String index(@AuthUser UserVo authUser, Model model) {
		List<GalleryVo> list = gallryService.getGalleryList();
		model.addAttribute("list", list);
		model.addAttribute("authUser", authUser);
		return "gallery/index";
	}
	
	@Auth(Role.ADMIN)
	@RequestMapping("/upload")
	public String upload(
			@ModelAttribute GalleryVo galleryVo, 
			@RequestParam(value="upload-image") MultipartFile multipartFile) {
		
		String imageUrl = fileuploadService.restore(multipartFile);
		galleryVo.setImageUrl(imageUrl);
		gallryService.insert(galleryVo);
		
		return "redirect:/gallery";
	}
	
	@Auth(Role.ADMIN)
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String upload(
			@PathVariable("no") Integer no) {
		
		GalleryVo galleryVo = gallryService.get(no);
		String fileUrl = galleryVo.getImageUrl();
		fileuploadService.deleteFile(fileUrl);
		
		gallryService.delete(no);
		
		return "redirect:/gallery";
	}
}



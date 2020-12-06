package com.sbs.khr.hanbyuk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ArticleController {
	
	// 사용자 메뉴 시작
	@RequestMapping("usr/article/{boardCode}-list")
	public String showEventList(@PathVariable("boardCode") String boardCode, Model model) {
		model.addAttribute(boardCode, boardCode);
		return "usr/article/list";
	}
	
	@RequestMapping("usr/article/takeAPhoto")
	public String showTakeAPhoto() {
		return "usr/article/takeAPhoto";
	}
	
	
	
	// 사용자 메뉴 끝
	
	// 관리자 메뉴 시작 
	@RequestMapping("admin/article/{boardCode}-list")
	public String showEventListAd(@PathVariable("boardCode") String boardCode, Model model) {
		model.addAttribute(boardCode, boardCode);
		return "admin/article/list";
	}
	
	@RequestMapping("admin/article/write")
	public String showWriteAd() {
		return "admin/article/write";
	}
	
	
	
	
	@RequestMapping("admin/article/takeAPhotoAd")
	public String showTakeAPhotoAd() {
		return "admin/article/takeAPhoto";
	}
	
	// 관리자 메뉴 끝 
	
	
	

}

package com.sbs.khr.hanbyuk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	// 사용자 메인
	@RequestMapping("/usr/home/main")
	public String showMain() {
		return "usr/home/main";
	}
	
	// 관리자 메인
	@RequestMapping("/admin/home/main")
	public String showMainAd() {
		return "admin/home/main";
	}
}

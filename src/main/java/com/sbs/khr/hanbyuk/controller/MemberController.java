package com.sbs.khr.hanbyuk.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.khr.hanbyuk.dto.Member;
import com.sbs.khr.hanbyuk.service.MemberService;
import com.sbs.khr.hanbyuk.util.Util;

@Controller
public class MemberController {
	
	
	@Autowired
	private MemberService memberService;


	@RequestMapping("/admin/member/login")
	public String showLogin() {
		
		return "admin/member/login";
	}
	
	
	@RequestMapping("/admin/member/doLogin")
	public String doLogin(@RequestParam Map<String, Object> param, HttpSession session, Model model) {
		
		String loginId = Util.getAsStr(param.get("loginId"));
		String redirectUri = Util.getAsStr(param.get("redirectUri"));
		System.out.println("redirectUri는? : " + redirectUri);
		System.out.println("param은? : " + param);
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if ( member == null || member.getLoginPw().equals(Util.getAsStr(param.get("loginPwReal")))  == false ) {
			model.addAttribute("msg", "회원정보가 일치하지 않습니다.");
			model.addAttribute("historyBack", true);
			return "admin/common/redirect";
		}
		
		
		session.setAttribute("loginedMemberId", member.getId());
		
		model.addAttribute("msg", "관리자님 로그인 되셨습니다.");
		
		
		if ( redirectUri.length() > 0 ) {
			model.addAttribute("replaceUri", redirectUri);
			return "admin/common/redirect";
		}
		model.addAttribute("replaceUri", "../home/main");
		
		return "admin/common/redirect";
	}
	
	@RequestMapping("/admin/member/logout")
	public String showLogout(HttpSession session, Model model) {
		
		session.removeAttribute("loginedMemberId");
		
		model.addAttribute("replaceUri", "../home/main");
		
		
		return "admin/common/redirect";
	}
}

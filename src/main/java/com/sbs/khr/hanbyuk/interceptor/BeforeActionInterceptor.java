package com.sbs.khr.hanbyuk.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sbs.khr.hanbyuk.config.AppConfig;

@Component("beforeActionInterceptor") // 컴포넌트 이름 설정
public class BeforeActionInterceptor implements HandlerInterceptor {

	/*
	 * @Autowired private MemberService memberService;
	 */
	@Autowired
	private AppConfig appConfig;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {


		// 로그인 여부에 관련된 정보를 request에 담는다.
		/*
		 * boolean isLogined = false; int loginedMemberId = 0; Member loginedMember =
		 * null;
		 * 
		 * if (session.getAttribute("loginedMemberId") != null) {
		 * System.out.println("이 session 뭔데?????? " + session); loginedMemberId = (int)
		 * session.getAttribute("loginedMemberId"); isLogined = true;
		 * System.out.println("loginedMemberId를 왜 불러와????? 7번 회원이 어디서 나와???  " +
		 * loginedMemberId); loginedMember =
		 * memberService.getMemberByIdForSession(loginedMemberId);
		 * 
		 * }
		 * 
		 * request.setAttribute("loginedMemberId", loginedMemberId);
		 * request.setAttribute("isLogined", isLogined);
		 * request.setAttribute("loginedMember", loginedMember);
		 * 
		 */		
		request.setAttribute("appConfig", appConfig);


		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}

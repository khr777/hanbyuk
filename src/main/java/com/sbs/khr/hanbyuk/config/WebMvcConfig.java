package com.sbs.khr.hanbyuk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	// beforeActionInterceptor 인터셉터 불러오기
	@Autowired
	@Qualifier("beforeActionInterceptor")
	HandlerInterceptor beforeActionInterceptor;
	// needToLoginInterceptor 인터셉터 불러오기
	@Autowired
	@Qualifier("needToLoginInterceptor")
	HandlerInterceptor needToLoginInterceptor;

	// needToLogoutInterceptor 인터셉터 불러오기
	@Autowired
	@Qualifier("needToLogoutInterceptor")
	HandlerInterceptor needToLogoutInterceptor;

	// 이 함수는 인터셉터를 적용하는 역할을 합니다.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// beforeActionInterceptor 인터셉터가 모든 액션 실행전에 실행되도록 처리
		registry.addInterceptor(beforeActionInterceptor).addPathPatterns("/**").excludePathPatterns("/resource/**");
		// 로그인 없이도 접속할 수 있는 URI 전부 기술
		registry.addInterceptor(needToLoginInterceptor).addPathPatterns("/**").excludePathPatterns("/")
				.excludePathPatterns("/resource/**").excludePathPatterns("/admin/home/main")
				.excludePathPatterns("/admin/member/login").excludePathPatterns("/admin/member/doLogin")
				.excludePathPatterns("/usr/article/*-list").excludePathPatterns("/usr/home/main")
				.excludePathPatterns("/admin/article/*-list").excludePathPatterns("/admin/article/*-detail")
				.excludePathPatterns("/admin/article/takeAPhoto").excludePathPatterns("/admin/file/streamVideo")
				.excludePathPatterns("/usr/article/*-detail").excludePathPatterns("/usr/article/takeAPhoto")
				.excludePathPatterns("/admin/file/showImg").excludePathPatterns("/admin/article/takeAPhotoAd")
				.excludePathPatterns("/admin/article/goPhoto").excludePathPatterns("/admin/file/doUploadAjax")
				.excludePathPatterns("/usr/article/doGoPhoto");
		
		

		// 로그인 상태에서 접속할 수 없는 URI 전부 기술
		registry.addInterceptor(needToLogoutInterceptor).addPathPatterns("/admin/member/login")
				.addPathPatterns("/admin/member/doLogin").addPathPatterns("/admin/member/join")
				.addPathPatterns("/admin/member/doJoin");
	}
}

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="로그인" />
<%@ include file="../part/head.jspf"%>


<script
	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>



<form action="doLogin" method="POST" class="con" onsubmit="LoginFormSubmit(this); return false;">
	<input type="hidden" name="loginPwReal" />
	<input type="hidden" name="redirectUri" value="${param.redirectUri}"/>
	<div>로그인 아이디</div>
	<input type="text" name="loginId" autofocus="autofocus"/>
	<div>로그인 비밀번호</div>
	<input type="password" name="loginPw" />
	<button type="submit">로그인</button>
</form>



<script>
var loginFormSubmitDone = false;
function LoginFormSubmit(form) {
	if ( loginFormSubmitDone ) {
		alert('처리중입니다.');
		return;
	}

	form.loginId.value = form.loginId.value.trim();
	if ( form.loginId.value.length == 0 ) {
		alert('로그인 아이디를 입력해주세요.');
		form.loginId.focus();
		return;
	}

	form.loginPw.value = form.loginPw.value.trim();
	if ( form.loginPw.value.length == 0 ) {
		alert('로그인 비밀번호를 입력해주세요.');
		form.loginPw.focus();
		return;
	}


	form.loginPwReal.value = sha256(form.loginPw.value);
	form.loginPw.value = '';

	form.submit(); 
}
</script>
<%@ include file="../part/foot.jspf"%>
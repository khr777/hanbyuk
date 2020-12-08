<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="사진찍기" />
<%@ include file="../part/head.jspf"%>

<h1 class="con">관리자 사진찍기 메뉴입니다.</h1>



<h1 class="con">배경화면 선택</h1>
<c:forEach items="${articles}" var="article">
	<div class="menu-box con ">
		<img
			src="/admin/file/showImg?id=${article.extra.file__common__attachment['1'].id}&updateDate=${article.extra.file__common__attachment['1'].updateDate}"
			alt="" />
		<button type="button"
			onclick="location.replace('../article/${board.code}-modify?id=${article.id}')">수정</button>
	</div>
	<hr />
</c:forEach>

<%@ include file="../part/foot.jspf"%>



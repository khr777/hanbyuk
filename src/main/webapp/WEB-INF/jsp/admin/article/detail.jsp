<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="게시물 상세보기" />
<%@ include file="../part/head.jspf"%>

<h1 class="con">관리자 ${board.name} 게시물 상세보기</h1>

<div class="con">
	<div>번호 : ${article.id}</div>
	<div>작성일 : ${article.regDate}</div>
	<div>수정일 : ${article.updateDate }</div>
	<div>제목 : ${article.title }</div>
	<div>내용 : ${article.body }</div>
</div>
<button type="button"
	onclick="location.replace('../article/${board.code}-modify?id=${article.id}')">수정</button>
<button type="button"
	onclick="if(confirm('삭제하시겠습니까?') == false ) return false; location.replace('../article/${board.code}-doDelete?id=${article.id }')">삭제</button>
<button type="button"
	onclick="history.back();">뒤로가기</button>






<%@ include file="../part/foot.jspf"%>

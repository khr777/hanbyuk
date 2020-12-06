<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="행사안내" />
<%@ include file="../part/head.jspf"%>

<h1 class="con">${board.name}</h1>
<div class="con">
	<c:forEach items="${articles }" var="article">
		<div>번호 : ${article.id}</div>
		<div>작성일 : ${article.regDate}</div>
		<div>수정일 : ${article.updateDate}</div>
		<div>제목 : ${article.title}</div>
		<div>내용 : ${article.body}</div>
		<hr />
	</c:forEach>
</div>


<%@ include file="../part/foot.jspf"%>
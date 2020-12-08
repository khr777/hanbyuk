<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="행사안내" />
<%@ include file="../part/head.jspf"%>

<h1 class="con">${board.name}</h1>

<c:if test="${board.code ne 'facility' }">
	<div class=" con">
		<c:forEach items="${articles }" var="article">
			<div class="menu-box"
				onclick="location.replace('../article/${board.code }-detail?id=${article.id}')">
				<div>제목 : ${article.title}</div>
			</div>
			<hr />
		</c:forEach>
	</div>
</c:if>
<c:if test="${board.code eq 'facility'}">
	<div class="con">
		<c:forEach items="${articles }" var="article">
			<div class=" menu-box "
				onclick="location.replace('../article/${board.code }-detail?id=${article.id}')">
				<div>시설명 : ${article.title}</div>
			</div>

			<hr />
		</c:forEach>
	</div>
</c:if>
<style>
.menu-box:hover {
	cursor: pointer;
}
</style>

<%@ include file="../part/foot.jspf"%>
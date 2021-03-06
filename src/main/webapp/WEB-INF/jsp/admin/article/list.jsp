<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="안내" />
<%@ include file="../part/head.jspf"%>

<h1 class="con">관리자 ${board.name}</h1>


<c:if test="${board.code ne 'facility' }">
	<div class=" con">
		<c:forEach items="${articles }" var="article">
			<div class="menu-box"
				onclick="location.replace('../article/${board.code }-detail?id=${article.id}')">
				<div>번호 : ${article.id}</div>
				<div>작성일 : ${article.regDate}</div>
				<div>수정일 : ${article.updateDate}</div>
				<div>제목 : ${article.title}</div>
			</div>
<%-- 			<c:if test="${isLogined}">
				<button type="button"
					onclick="location.replace('../article/${board.code}-modify?id=${article.id}')">수정</button>
				<button type="button"
					onclick="if(confirm('삭제하시겠습니까?') == false ) return false; location.replace('../article/${board.code}-doDelete?id=${article.id }')">삭제</button>
			</c:if>
 --%>			<hr />
		</c:forEach>
	</div>
</c:if>
<c:if test="${board.code eq 'facility'}">
	<div class="con">
		<c:forEach items="${articles }" var="article">
			<div class="menu-box"
				onclick="location.replace('../article/${board.code }-detail?id=${article.id}')">
				<div>시설명 : ${article.title}</div>
			</div>
			<%-- <c:if test="${isLogined}">
				<button type="button"
					onclick="location.replace('../article/${board.code}-modify?id=${article.id}')">수정</button>
				<button type="button"
					onclick="if(confirm('삭제하시겠습니까?') == false ) return false; location.replace('../article/${board.code}-doDelete?id=${article.id }')">삭제</button>
			</c:if> --%>
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
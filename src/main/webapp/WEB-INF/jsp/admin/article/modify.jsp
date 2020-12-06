<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="게시물 수정" />
<%@ include file="../part/head.jspf"%>

<h1 class="con">관리자 게시물 수정</h1>


<form action="${board.code}-doModify" method="POST" class=" con"
	onsubmit="ModifyFormSubmit(this); return false;">
	<input type="hidden" name="id" value="${param.id }"/>
	<select name="boardCode">
		<option value="${board.code}">${board.name}</option>
		<c:if test="${board.code ne 'event'}">
			<option value="event">행사안내</option>
		</c:if>
		<c:if test="${board.code ne 'performance'}">
			<option value="performance">공연안내</option>
		</c:if>
		<c:if test="${board.code ne 'facility'}">
			<option value="facility">시설안내</option>
		</c:if>
	</select> <input type="text" name="title" placeholder="제목을 입력해주세요." value="${article.title }"/> <input
		type="text" name="body" placeholder="내용을 입력해주세요." value=" ${article.body }"/>
	<button type="submit">수정</button>
</form>

<script>
	var ModifyFormSubmitDone = false;
	function ModifyFormSubmit(form) {
		if (ModifyFormSubmitDone) {
			alert('처리중입니다.');
			return;
		}

		form.boardCode.value = form.boardCode.value.trim();
		if (form.boardCode.value.length == 0) {
			alert('카테고리를 선택해주세요.');
			return;
		}

		form.title.value = form.title.value.trim();
		if (form.title.value.length == 0) {
			alert('제목을 입력해주세요.');
			form.title.focus();
			return;
		}

		form.body.value = form.body.value.trim();
		if (form.body.value.length == 0) {
			alert('내용을 입력해주세요.');
			form.body.focus();
			return;
		}

		form.submit();
		ModifyFormSubmitDone = true;
	}
</script>



<%@ include file="../part/foot.jspf"%>

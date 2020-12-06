<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="게시물 작성" />
<%@ include file="../part/head.jspf"%>

<h1 class="con">관리자 게시물 작성</h1>


<form action="doWrite" method="POST" class=" con"
	onsubmit="WriteFormSubmit(this); return false;">
	<select name="boardCode">
		<option value="">카테고리 선택</option>
		<option value="event">행사안내</option>
		<option value="performance">공연안내</option>
		<option value="facility">시설안내</option>
	</select> <input type="text" name="title" placeholder="제목을 입력해주세요." /> <input
		type="text" name="body" placeholder="내용을 입력해주세요." />
	<button type="submit">작성</button>
</form>

<script>
	var writeFormSubmitDone = false;
	function WriteFormSubmit(form) {
		if (writeFormSubmitDone) {
			alert('처리중입니다.');
			return;
		}

		form.boardCode.value = form.boardCode.value.trim();
		if ( form.boardCode.value.length == 0 ) {
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
		writeFormSubmitDone = true;
	}
</script>



<%@ include file="../part/foot.jspf"%>

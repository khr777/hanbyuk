<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="게시물 작성" />
<%@ include file="../part/head.jspf"%>

<h1 class="con">관리자 게시물 작성</h1>


<form action="doWrite" method="POST" class=" con"
	onsubmit="WriteFormSubmit(this); return false;">
	<input type="hidden" name="fileIdsStr" /> <select name="boardCode">
		<option value="">카테고리 선택</option>
		<option value="performance">공연안내</option>
		<option value="event">행사안내</option>
		<option value="facility">시설안내</option>
	</select> <input type="text" name="title" placeholder="제목을 입력해주세요." /> <input
		type="text" name="body" placeholder="내용을 입력해주세요." />
	<div>첨부1 비디오</div>
	<input type="file" accept="video/*"
		name="file__article__0__common__attachment__1" />
	<div>첨부2 비디오</div>
	<input type="file" accept="video/*"
		name="file__article__0__common__attachment__2" />
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

		var maxSizeMb = 50;
		var maxSize = maxSizeMb * 1024 * 1024 //50MB
		
		if (form.file__article__0__common__attachment__1.value) {
			if ( form.file__article__0__common__attachment__1.files[0].size > maxSize ) {
				alert(maxSizeMb + "MB 이하의 파일을 업로드 해주세요.");
				return;
			} 
		}
		if (form.file__article__0__common__attachment__2.value) {
			if ( form.file__article__0__common__attachment__2.files[0].size > maxSize ) {
				alert(maxSizeMb + "MB 이하의 파일을 업로드 해주세요.");
				return;
			} 
		}
		var startUploadFiles = function(onSuccess) {
			if ( form.file__article__0__common__attachment__1.value.length == 0 && form.file__article__0__common__attachment__2.value.length == 0 ) {
				onSuccess();
				return;
			}
			var fileUploadFormData = new FormData(form); 
			$.ajax({
				url : './../file/doUploadAjax',
				data : fileUploadFormData,
				processData : false,
				contentType : false,
				dataType:"json",
				type : 'POST',
				success : onSuccess
			});
		}
		writeFormSubmitDone = true;
		startUploadFiles(function(data) {
			var fileIdsStr = '';
			if ( data && data.body && data.body.fileIdsStr ) {
				fileIdsStr = data.body.fileIdsStr;
			}
			form.fileIdsStr.value = fileIdsStr;
			form.file__article__0__common__attachment__1.value = '';
			form.file__article__0__common__attachment__2.value = '';
			
			form.submit();
		});
	}
</script>



<%@ include file="../part/foot.jspf"%>

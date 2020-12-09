<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="사진찍기" />
<%@ include file="../part/head.jspf"%>

<h1 class="con">${title }</h1>


<form action="/usr/article/doGoPhoto" method="POST" class=" con"
	onsubmit="GoPhotoFormSubmit(this); return false;">
	<input type="hidden" name="fileIdsStr" />
	<input style="width: 400px;" type="email"
		name="email" placeholder="사진을 전송받을 이메일을 입력해주세요." /> 
	<div>첨부1 이미지</div>
	<input type="file" accept="image/*"
		name="file__photo__0__common__attachment__1">


	<button type="submit">작성</button>
</form>

<script>
	var GoPhotoFormSubmitDone = false;
	function GoPhotoFormSubmit(form) {
		if (GoPhotoFormSubmitDone) {
			alert('처리중입니다.');
			return;
		}

		form.email.value = form.email.value.trim();
		if ( form.email.value.length == 0 ) {
			alert('이메일을 입력해주세요.');
			form.email.focus();
			return;
		}
		


		var maxSizeMb = 50;
		var maxSize = maxSizeMb * 1024 * 1024 //50MB

		if ( form.file__photo__0__common__attachment__1.value.length == 0 ) {
			alert('사진을 첨부해주세요.');
			return;
		}

		if (form.file__photo__0__common__attachment__1.value) {
			if (form.file__photo__0__common__attachment__1.files[0].size > maxSize) {
				alert(maxSizeMb + "MB 이하의 파일을 업로드 해주세요.");
				return;
			}
		}
		var startUploadFiles = function(onSuccess) {
			var needToUpload = form.file__photo__0__common__attachment__1.value.length > 0;
			if (!needToUpload) {
				needToUpload = form.file__photo__0__common__attachment__2.value.length > 0;
			}
			if (!needToUpload) {
				needToUpload = form.file__photo__0__common__attachment__3.value.length > 0;
			}
			if (needToUpload == false) {
				onSuccess();
				return;
			}
			var fileUploadFormData = new FormData(form);
			$.ajax({
				url : './../file/doUploadAjax',
				data : fileUploadFormData,
				processData : false,
				contentType : false,
				dataType : "json",
				type : 'POST',
				success : onSuccess
			});
		}
		GoPhotoFormSubmitDone = true;
		startUploadFiles(function(data) {
			var fileIdsStr = '';
			if (data && data.body && data.body.fileIdsStr) {
				fileIdsStr = data.body.fileIdsStr;
			}
			form.fileIdsStr.value = fileIdsStr;
			form.file__photo__0__common__attachment__1.value = '';

			form.submit();
		});
	}
</script>



<%@ include file="../part/foot.jspf"%>

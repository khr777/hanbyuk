<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="게시물 수정" />
<%@ include file="../part/head.jspf"%>

<h1 class="con">관리자 게시물 수정</h1>


<form action="${board.code}-doModify" method="POST" class=" con"
	onsubmit="ModifyFormSubmit(this); return false;">
	<input type="hidden" name="id" value="${param.id }" /> <input
		type="hidden" name="fileIdsStr" /><select name="boardCode">
		<option value="${board.code}">${board.name}</option>
		<c:if test="${board.code ne 'performance'}">
			<option value="performance">공연안내</option>
		</c:if>
		<c:if test="${board.code ne 'event'}">
			<option value="event">행사안내</option>
		</c:if>

		<c:if test="${board.code ne 'facility'}">
			<option value="facility">시설안내</option>
		</c:if>
	</select> <input type="text" name="title" placeholder="제목을 입력해주세요."
		value="${article.title }" /> <input type="text" name="body"
		placeholder="내용을 입력해주세요." value=" ${article.body }" />
	<div>첨부 파일 1</div>
	<input type="file" accept="video/*"
		name="file__article__${article.id}__common__attachment__1" />
	<c:if test="${article.extra.file__common__attachment['1'] != null}">
		<div class="video-box">
			<video controls
				src="/admin/file/streamVideo?id=${article.extra.file__common__attachment['1'].id}&updateDate=${article.extra.file__common__attachment['1'].updateDate}">video
				not supported
			</video>
		</div>
	</c:if>
	<div>첨부 파일 1 삭제</div>
	<input type="checkbox"
		name="deleteFile__article__${article.id}__common__attachment__1"
		value="Y" /> 삭제
	<div>첨부 파일 2</div>
	<input type="file" accept="video/*"
		name="file__article__${article.id}__common__attachment__2" />
	<c:if test="${article.extra.file__common__attachment['2'] != null}">
		<div class="video-box">
			<video controls
				src="/admin/file/streamVideo?id=${article.extra.file__common__attachment['2'].id}&updateDate=${article.extra.file__common__attachment['1'].updateDate}">video
				not supported
			</video>
		</div>
	</c:if>
	<div>첨부 파일 2 삭제</div>
	<div class="form-control-box">
		<input type="checkbox"
			name="deleteFile__article__${article.id}__common__attachment__2"
			value="Y" /> 삭제
	</div>
	<button type="submit">수정</button>
</form>

<script>
	var ModifyFormSubmitDone = false;
	var param = $(location).attr('search').slice(
			$(location).attr('search').indexOf('=') + 1);

	function ModifyFormSubmit(form) {

		
		var fileInput1 = form["file__article__" + param
				+ "__common__attachment__1"];
		var fileInput2 = form["file__article__" + param
				+ "__common__attachment__2"];

		var deleteFileInput1 = form["deleteFile__article__" + param
				+ "__common__attachment__1"];
		var deleteFileInput2 = form["deleteFile__article__" + param
				+ "__common__attachment__2"];

		
		if (deleteFileInput1.checked) {
			fileInput1.value = '';
		}
		if (deleteFileInput2.checked) {
			fileInput2.value = '';
		}

		

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

		var maxSizeMb = 50;
		var maxSize = maxSizeMb * 1024 * 1024 //50MB
		if (fileInput1.value) {
			if (fileInput1.files[0].size > maxSize) {
				alert(maxSizeMb + "MB 이하의 파일을 업로드 해주세요.");
				return;
			}
		}
		if (fileInput2.value) {
			if (fileInput2.files[0].size > maxSize) {
				alert(maxSizeMb + "MB 이하의 파일을 업로드 해주세요.");
				return;
			}
		}
		var startUploadFiles = function(onSuccess) {
			if (fileInput1.value.length == 0 && fileInput2.value.length == 0) {
				if (deleteFileInput1.checked == false
						&& deleteFileInput2.checked == false) {
					onSuccess();
					return;
				}
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

		ModifyFormSubmitDone = true;

		startUploadFiles(function(data) {
			var fileIdsStr = '';

			if (data && data.body && data.body.fileIdsStr) {
				fileIdsStr = data.body.fileIdsStr;
			}

			form.fileIdsStr.value = fileIdsStr;
			fileInput1.value = '';
			fileInput2.value = '';
			form.submit();
		});

	}
</script>



<%@ include file="../part/foot.jspf"%>

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
	<c:forEach var="i" begin="1" end="3" step="1">
		<c:set var="fileNo" value="${String.valueOf(i)}" />
		<c:set var="file" value="${article.extra.file__common__attachment[fileNo]}" />
		<div>첨부파일 ${fileNo}
			${appConfig.getAttachmentFileExtTypeDisplayName('article', i)}</div>
		<input type="file"
			accept="${appConfig.getAttachemntFileInputAccept('article', i)}"
			name="file__article__${article.id }__common__attachment__${fileNo}">
		<c:if test="${file != null && file.fileExtTypeCode == 'video'}">
			<video controls
				src="/admin/file/streamVideo?id=${file.id}&updateDate=${file.updateDate}">
			</video>
		</c:if>
		<c:if test="${file != null && file.fileExtTypeCode == 'img'}">
			<img
				src="/admin/file/showImg?id=${file.id}&updateDate=${file.updateDate}">
		</c:if>
		<div>첨부파일 ${fileNo} 삭제</div>
		<input type="checkbox"
			name="deleteFile__article__${article.id}__common__attachment__${fileNo}"
			value="Y" /> 삭제 
	</c:forEach>
	<div>
		<button type="submit">수정</button>
	</div>

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
		var fileInput3 = form["file__article__" + param
				+ "__common__attachment__3"];
		var deleteFileInput1 = form["deleteFile__article__" + param
				+ "__common__attachment__1"];
		var deleteFileInput2 = form["deleteFile__article__" + param
				+ "__common__attachment__2"];
		var deleteFileInput3 = form["deleteFile__article__" + param
				+ "__common__attachment__3"];
		if (fileInput1 && deleteFileInput1) {
			if (deleteFileInput1.checked) {
				fileInput1.value = '';
			}
		}
		if (fileInput2 && deleteFileInput2) {
			if (deleteFileInput2.checked) {
				fileInput2.value = '';
			}
		}
		if (fileInput3 && deleteFileInput3) {
			if (deleteFileInput3.checked) {
				fileInput3.value = '';
			}
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
		if (fileInput1 && fileInput1.value) {
			if (fileInput1.files[0].size > maxSize) {
				alert(maxSizeMb + "MB 이하의 파일을 업로드 해주세요.");
				return;
			}
		}
		if (fileInput2 && fileInput2.value) {
			if (fileInput2.files[0].size > maxSize) {
				alert(maxSizeMb + "MB 이하의 파일을 업로드 해주세요.");
				return;
			}
		}

		if (fileInput3 && fileInput3.value) {
			if (fileInput3.files[0].size > maxSize) {
				alert(maxSizeMb + "MB 이하의 파일을 업로드 해주세요.");
				return;
			}
		}

		var startUploadFiles = function(onSuccess) {
			var needToUpload = false;


			if (!needToUpload) {
				needToUpload = fileInput1 && fileInput1.value.length > 0;
			}
			if (!needToUpload) {
				needToUpload = deleteFileInput1 && deleteFileInput1.checked;
			}
			if (!needToUpload) {
				needToUpload = fileInput2 && fileInput2.value.length > 0;
			}
			if (!needToUpload) {
				needToUpload = deleteFileInput2 && deleteFileInput2.checked;
			}
			if (!needToUpload) {
				needToUpload = fileInput3 && fileInput3.value.length > 0;
			}
			if (!needToUpload) {
				needToUpload = deleteFileInput3 && deleteFileInput3.checked;
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

		ModifyFormSubmitDone = true;

		startUploadFiles(function(data) {

			var fileIdsStr = '';
			if (data && data.body && data.body.fileIdsStr) {
				fileIdsStr = data.body.fileIdsStr;
			}
			form.fileIdsStr.value = fileIdsStr;
			if (fileInput1) {
				fileInput1.value = '';
			}
			if (fileInput2) {
				fileInput2.value = '';
			}
			if (fileInput3) {
				fileInput3.value = '';
			}
			form.submit();
		});
	}
</script>



<%@ include file="../part/foot.jspf"%>

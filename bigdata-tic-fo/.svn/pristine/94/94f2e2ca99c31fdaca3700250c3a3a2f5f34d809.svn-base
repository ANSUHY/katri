<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cm" uri="/WEB-INF/tlds" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
	/* File Ready Function */
	$(document).ready(function() {
		// 드래그앤드롭 파일
		fn_fileDropDown();

		// 첨부파일
		$("#file").on("change", fn_addAttachment);
	});

	/* 드래그앤드롭 파일 */
	var vFileIndex = 0;						// 파일 리스트 번호
	var vTotalFileSize = 0;					// 등록할 전체 파일 사이즈
	var vFileList = new Array();			// 파일 리스트
	var vFileSizeList = new Array();		// 파일 사이즈 리스트
	var vUploadSize = 40 * 1024 * 1024;		// 등록 가능한 파일 사이즈 MB
	var vMaxUploadSize = 400 * 1024 * 1024;	// 등록 가능한 총 파일 사이즈 MB

	/* 첨부파일 드롭 추가 */
	function fn_fileDropDown() {
		var vDropZone = $("#dropZone");								// Drag기능

		vDropZone.on('dragenter', function(e) {
			e.stopPropagation();
			e.preventDefault();
			vDropZone.css('background-color','#E3F2FC');			// 드롭다운 영역 css
		});

		vDropZone.on('dragleave', function(e) {
			e.stopPropagation();
			e.preventDefault();
			vDropZone.css('background-color','#FFFFFF');			// 드롭다운 영역 css
		});

		vDropZone.on('dragover', function(e) {
			e.stopPropagation();
			e.preventDefault();
			vDropZone.css('background-color','#E3F2FC');			// 드롭다운 영역 css
		});

		vDropZone.on('drop', function(e) {
			e.preventDefault();
			vDropZone.css('background-color','#FFFFFF');			// 드롭다운 영역 css

			var vFiles = e.originalEvent.dataTransfer.files;
			if (vFiles != null){
				if (vFiles.length < 1) {
					alert("<spring:message code='common.message.file.template.validation01'/>");
					return;
				}

				fn_selectFile(vFiles);
			} else {
				alert("ERROR");
			}
		});
	}

	/* 첨부파일 선택 추가 */
	function fn_addAttachment(e) {
		var vFiles = e.target.files;
		var vArrFiles = Array.prototype.slice.call(vFiles);
		fn_selectFile(vArrFiles);
		$(this).val("");
	}

	// 파일 선택시
	function fn_selectFile(pFiles) {
		// 다중파일 등록
		if (pFiles != null) {
			for (var i = 0; i < pFiles.length; i++) {
				// 파일 이름
				var vFileName = pFiles[i].name;
				var vFileNameArr = vFileName.split("\.");
				var vExt = vFileNameArr[vFileNameArr.length - 1];		// 확장자
				var vFileSize = pFiles[i].size;							// 파일 사이즈

				if ($.inArray(vExt, ['exe', 'bat', 'sh', 'java', 'jsp', 'html', 'js', 'css', 'xml']) >= 0) {		// 확장자 체크
					alert("<spring:message code='common.message.file.template.validation03'/>");
					break;
				} else if(vFileSize > vUploadSize) {																// 파일 사이즈 체크
					alert("Capacity exceeded \n Uploadable capacity : " + vUploadSize + " MB");
					break;
				} else {
					vTotalFileSize += vFileSize;						// 전체 파일 사이즈
					vFileList[vFileIndex] = pFiles[i];					// 파일 배열에 넣기
					vFileSizeList[vFileIndex] = vFileSize;				// 파일 사이즈 배열에 넣기
					fn_addFileList(vFileIndex, vFileName, vFileSize);	// 업로드 파일 목록 생성
					vFileIndex++;										// 파일 번호 증가
				}
			}
		} else {
			alert("ERROR");
		}
	}

	/* 등록할 첨부파일 생성 */
	function fn_addFileList(pIndex, pFileName, pFileSize) {
		var vFile = {
			fileIndex: pIndex,
			fileName: pFileName,
			fileSize: pFileSize,
		};

		$("#tempFileUnitTemplate").tmpl(vFile).appendTo(".uploadfileName");

		// 사이즈 조절
		fn_frameSizeResizing();
	}

	/* 등록할 첨부파일 삭제 */
	function fn_delTempFile(pIndex) {
		vTotalFileSize -= vFileSizeList[pIndex];		// 전체 파일 사이즈 수정
		delete vFileList[pIndex];						// 파일 배열에서 삭제
		delete vFileSizeList[pIndex];					// 파일 사이즈 배열 삭제

		$("#tempFile_" + pIndex).remove();				// 업로드 파일 테이블 목록에서 삭제

		// 사이즈 조절
		fn_frameSizeResizing();
	}
</script>

<div id="attach_file_save">
	<table class="view mt5" style="width:100%;height:100%" summary="<spring:message code='common.label.attachment'/>">
		<colgroup>
			<col style="width: 140px;" />
			<col />
		</colgroup>
		<tbody id="fileTableTbody">
			<tr>
				<th rowspan="2"><spring:message code='common.label.attachment'/></th>
				<td>
					<div class="filebox" style="width: 104px;" >
						<input type="file" name="files" id="file" multiple="multiple" />
						<input type="text" id="file_text" class="upload-name blind" />
						<label for="file"><spring:message code='common.btn.attachFile'/></label>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="fileframe_border" style="min-height: 30px;">
						<ul class="fileframe uploadfileName" id="dropZone" style="height:auto; min-height:50px;">

						</ul>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<script id="tempFileUnitTemplate" type="text/x-jquery-tmpl">
<li id="tempFile_\${fileIndex}">
	\${fileName}
	<a href="javascript:void(0);" class="del" onclick="fn_delTempFile('\${fileIndex}');" >
		<i class="fas fa-times"></i>
	</a>
</li>
</script>
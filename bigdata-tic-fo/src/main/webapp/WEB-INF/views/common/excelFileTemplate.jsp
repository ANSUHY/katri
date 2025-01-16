<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cm" uri="/WEB-INF/tlds" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
	/* File Ready Function */
	$(document).ready(function() {
		// 드래그앤드롭 파일
		fn_excelFileDropDown();

		// 첨부파일
		$("#excelFile").on("change", fn_addExcelAttachment);
	});

	/* 드래그앤드롭 파일 */
	var vExcelFileIndex = 0;						// 파일 리스트 번호
	var vExcelTotalFileSize = 0;					// 등록할 전체 파일 사이즈
	var vExcelFileList = new Array();				// 파일 리스트
	var vExcelFileSizeList = new Array();			// 파일 사이즈 리스트
	var vExcelUploadSize = 40 * 1024 * 1024;		// 등록 가능한 파일 사이즈 MB
	var vExcelMaxUploadSize = 400 * 1024 * 1024;	// 등록 가능한 총 파일 사이즈 MB

	/* 첨부파일 드롭 추가 */
	function fn_excelFileDropDown() {
		var vExcelDropZone = $("#excelDropZone");						// Drag기능

		vExcelDropZone.on('dragenter', function(e) {
			e.stopPropagation();
			e.preventDefault();
			vExcelDropZone.css('background-color','#E3F2FC');			// 드롭다운 영역 css
		});

		vExcelDropZone.on('dragleave', function(e) {
			e.stopPropagation();
			e.preventDefault();
			vExcelDropZone.css('background-color','#FFFFFF');			// 드롭다운 영역 css
		});

		vExcelDropZone.on('dragover', function(e) {
			e.stopPropagation();
			e.preventDefault();
			vExcelDropZone.css('background-color','#E3F2FC');			// 드롭다운 영역 css
		});

		vExcelDropZone.on('drop', function(e) {
			e.preventDefault();
			vExcelDropZone.css('background-color','#FFFFFF');			// 드롭다운 영역 css

			var vExcelFiles = e.originalEvent.dataTransfer.files;
			if (vExcelFiles != null){
				if (vExcelFiles.length < 1) {
					alert("<spring:message code='common.message.file.template.validation01'/>");
					return;
				}

				fn_selectExcelFile(vExcelFiles);
			} else {
				alert("ERROR");
			}
		});
	}

	/* 첨부파일 선택 추가 */
	function fn_addExcelAttachment(e) {
		var vExcelFiles = e.target.files;
		var vArrExcelFiles = Array.prototype.slice.call(vExcelFiles);
		fn_selectExcelFile(vArrExcelFiles);
		$(this).val("");
	}

	// 파일 선택시
	function fn_selectExcelFile(pFiles) {
		// 다중파일 등록
		if (pFiles != null) {
			for (var i = 0; i < pFiles.length; i++) {
				// 파일 이름
				var vFileName = pFiles[i].name;
				var vFileNameArr = vFileName.split("\.");
				var vExt = vFileNameArr[vFileNameArr.length - 1];		// 확장자
				var vFileSize = pFiles[i].size;							// 파일 사이즈

				if ($.inArray(vExt, ['xls', 'xlsx']) <= 0) {			// 확장자 체크
					alert("<spring:message code='common.message.file.template.validation02'/>");
					break;
				} else if(vFileSize > vExcelUploadSize) {							// 파일 사이즈 체크
					alert("Capacity exceeded \n Uploadable capacity : " + vExcelUploadSize + " MB");
					break;
				} else {
					vExcelTotalFileSize += vFileSize;								// 전체 파일 사이즈
					vExcelFileList[vExcelFileIndex] = pFiles[i];					// 파일 배열에 넣기
					vExcelFileSizeList[vExcelFileIndex] = vFileSize;				// 파일 사이즈 배열에 넣기
					fn_addExcelFileList(vExcelFileIndex, vFileName, vFileSize);		// 업로드 파일 목록 생성
					vExcelFileIndex++;												// 파일 번호 증가
				}
			}
		} else {
			alert("ERROR");
		}
	}

	/* 등록할 첨부파일 생성 */
	function fn_addExcelFileList(pIndex, pFileName, pFileSize) {
		var vFile = {
			fileIndex: pIndex,
			fileName: pFileName,
			fileSize: pFileSize,
		};

		$("#tempExcelFileUnitTemplate").tmpl(vFile).appendTo(".excelUploadFileName");

		// 사이즈 조절
		fn_frameSizeResizing();
	}

	/* 등록할 첨부파일 삭제 */
	function fn_delExcelTempFile(pIndex) {
		vExcelTotalFileSize -= vExcelFileSizeList[pIndex];		// 전체 파일 사이즈 수정
		delete vExcelFileList[pIndex];						// 파일 배열에서 삭제
		delete vExcelFileSizeList[pIndex];					// 파일 사이즈 배열 삭제

		$("#excelTempFile_" + pIndex).remove();				// 업로드 파일 테이블 목록에서 삭제

		// 사이즈 조절
		fn_frameSizeResizing();
	}
</script>

<div id="attach_file_save">
	<table class="view mt5" style="width:100%;height:100%" summary="<spring:message code='common.label.attachment'/>">
		<colgroup>
			<col style="width: 160px;" />
			<col />
		</colgroup>
		<tbody id="excelFileTableTbody">
			<tr>
				<th rowspan="2"><spring:message code='common.label.attachment'/></th>
				<td>
					<div class="filebox" style="width: 104px;" >
						<input type="file" name="files" id="excelFile" multiple="multiple" />
						<input type="text" id="excel_file_text" class="upload-name blind" />
						<label for="excelFile"><spring:message code='common.btn.attachFile'/></label>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="fileframe_border" style="min-height: 30px;">
						<ul class="fileframe excelUploadFileName" id="excelDropZone" style="height:auto; min-height:50px;">

						</ul>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<script id="tempExcelFileUnitTemplate" type="text/x-jquery-tmpl">
<li id="excelTempFile_\${fileIndex}">
	\${fileName}
	<a href="javascript:void(0);" class="del" onclick="fn_delExcelTempFile('\${fileIndex}');" >
		<i class="fas fa-times"></i>
	</a>
</li>
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page import="com.katri.common.FileConst" %>
<c:set var="FILE_DIV_NM_BOARD_IMG" 	value="<%=FileConst.File.FILE_DIV_NM_BOARD_IMG%>" />
<c:set var="FILE_DIV_NM_BOARD_FILE" value="<%=FileConst.File.FILE_DIV_NM_BOARD_FILE%>" />

<script type="text/javascript" src="/smarteditor/js/service/HuskyEZCreator.js" charset="charset=UTF-8"></script>
<script type="text/javascript">
	let oEditors = [];

	$(function(){
		fn_preventEnter(); // 엔터 키 방지
		fn_faqPopUpInit(); // 초기 작업
		fn_getFaqClfCd(); // 분류 코드
	});

	/**
	 * 엔터 키 입력 시 submit 방지
	 */
	function fn_preventEnter() {
		$(document).keydown(function(e){
			if (e.keyCode === 13) {
				e.preventDefault();
			}
		});
	}

	/**
	 * 초기 작업
	 */
	function fn_faqPopUpInit() {
		nhn.husky.EZCreator.createInIFrame({ 
			oAppRef: oEditors,
			elPlaceHolder: "nttCn",
			sSkinURI: "/smarteditor/SmartEditor2Skin.html",
			fCreator: "createSEditor2",
			htParams : {fOnBeforeUnload : function(){}}, //alert창 띄우지 않기
			fOnAppLoad: function() {
				fn_getFaqDetail(); // FAQ 조회
			}
		});
	}

	/**
	 * 공지사항 분류 코드 가져오기
	 */
	function fn_getFaqClfCd() {
		$.blockUI();

		$.ajax({
			  url: "/ctnt/faq/getFaqClfCd"
			, type: "GET"
			, dataType: "json"
		})
		.done(function(jsonData, textStatus, jqXHR){
			$.unblockUI();

			let html = "";
			if (jsonData.resultCode === 200) {
				if (jsonData.data.list !== null && jsonData.data.list.length > 0) {
					$.each(jsonData.data.list, function(index, data) {
						html += "<option value='" + data.comnCd + "'>" + data.comnCdNm + "</option>";
					});
				}

				$("#nttClfCd").html(html);
			} else {
				alert(jsonData.resultMessage);
			}
		});
	}

	/**
	 * 공지사항 글 작성/수정
	 */
	function fn_saveFaq() {
		oEditors.getById["nttCn"].exec("UPDATE_CONTENTS_FIELD", []);

		if (fn_validCheck()) {
			$.blockUI();

			$.ajax({
				  url: "/ctnt/faq/saveFaq"
				, type: "POST"
				, dataType	: "json"
				, data: $("#faqFrm").serialize()
			})
			.done(function(jsonData, textStatus, jqXHR){
				$.unblockUI();

				if (jsonData.resultCode === 200) {
					alert("<spring:message code='result-message.messages.common.message.save'/>"); // 정상적으로 저장 하였습니다.
					opener.fn_getFaqList(1);
					self.close();
				} else {
					alert(jsonData.resultMessage);
					self.close();
				}
			});
		}
	}

	/**
	 * 선택한 FAQ 조회
	 */
	function fn_getFaqDetail() {
		$("#nttTyCd").val(opener.$("#nttTyCd").val()); // 게시물 유형 코드

		if (!fn_emptyCheck(opener.$("#targetNttSn").val())) {
			$.blockUI();

			$.ajax({
				  url: "/ctnt/faq/getFaqDetail"
				, type: "GET"
				, dataType: "json"
				, data: {nttSn : opener.$("#targetNttSn").val()}
			})
			.done(function(jsonData, textStatus, jqXHR){
				$.unblockUI();

				if (jsonData.resultCode === 200) {
					if (jsonData.data !== null) {
						const data = jsonData.data;

						// 데이터 세팅
						$("#nttSn").val(data.nttSn);
						$("#nttSjNm").val(fn_convertXss(unescapeHtml(data.nttSjNm)));
						$("#nttCn").html(data.nttCn);
						oEditors.getById["nttCn"].exec("LOAD_CONTENTS_FIELD", []);
						$("#nttClfCd").val(data.nttClfCd).prop("selected", true);
					}
				} else {
					alert(jsonData.resultMessage);
					self.close();
				}
			});
		}
	}

	/**
	 * 작성/수정 시 유효성 검사
	 */
	function fn_validCheck() {
		const nttSjNm = $("#nttSjNm").val(); // 제목
		const nttCn = $("#nttCn").val(); // 내용
		const nttClfCd = $("#nttClfCd").val(); // 분류

		let isValid = true;

		// 제목
		if($.trim(nttSjNm) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='제목'/>") // '{0}는(은) 필수 입력항목입니다.'
			$("#nttSjNm").focus();
			isValid = false;
			return false;
		}

		// 네이버에디터
		if(nttCn == ""  || nttCn == null || nttCn == '&nbsp;' || nttCn == '<p>&nbsp;</p>' || nttCn == '<p><br></p>')  {
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='내용'/>") // '{0}는(은) 필수 입력항목입니다.'
			$("#nttCn").focus();
			isValid = false;
			return false;
		}

		// 분류
		if (fn_emptyCheck(nttClfCd)) {
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='분류'/>") // '{0}는(은) 필수 입력항목입니다.'
			isValid = false;
			return false;
		}

		return isValid;
	}

	/**
	 * 팝업 닫기
	 */
	function fn_closePopUp() {
		self.close();
	}

	/**
	 * html unescape 처리 (특수문자 복원)
	 */
	function unescapeHtml(text) {
		let doc = new DOMParser().parseFromString(text, "text/html");
		return doc.documentElement.textContent;
	}
</script>

<div id="pop-wrap">

	<h1 class="pop-tit">FAQ</h1>

	<div class="pop-contnet">
		<form name="faqFrm" id="faqFrm">
			<input type="hidden" name="nttTyCd" id="nttTyCd" />
			<input type="hidden" name="nttSn" id="nttSn" />
			<div class="hd-search">
				<table>
					<colgroup>
						<col style="width:100px;"><col>
					</colgroup>
					<tbody>
						<tr>
							<th>
								<span class="blt_req">*</span> 분류
							</th>
							<td>
								<select name="nttClfCd" id="nttClfCd">
								</select>
							</td>
						</tr>
						<tr>
							<th>
								<span class="blt_req">*</span> 질문
							</th>
							<td>
								<input type="text" name="nttSjNm" id="nttSjNm" class="input" style="width:100%;" />
							</td>
						</tr>
						<tr>
							<th>
								<span class="blt_req">*</span> 답변
							</th>
							<td>
								<!-- 에디터 영역-->
								<textarea name="nttCn" id="nttCn" class="textarea block"></textarea>
								<!-- // 에디터 영역-->
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>

		<div class="btn-box">
			<a href="javascript:void(0)" onclick="fn_closePopUp()" class="btn btn-default">취소</a>
			<a href="javascript:void(0)" onclick="fn_saveFaq()" class="btn btn-blue">저장</a>
		</div>

	</div><!-- /pop-contnet -->
	<a href="javascript:void(0)" onclick="fn_closePopUp()" class="pop-close"><i class="fa fa-times"></i></a>
</div><!-- /pop-wrao -->
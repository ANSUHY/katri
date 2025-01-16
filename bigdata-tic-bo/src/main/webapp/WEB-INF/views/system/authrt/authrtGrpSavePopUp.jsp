<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

	<script>

		$(document).ready(function() {

			// 페이지 첫 로드 시, 값 셋팅
			let targetAuthrtGrpSn = opener.document.getElementById('targetAuthrtGrpSn').value;

			// 값이 있는 경우 상세 조회
			if( targetAuthrtGrpSn != '' ) {
				$("#authrtGrpSn").val(targetAuthrtGrpSn);

				// 상세 조회
				fn_authrtGrpBasDetail(targetAuthrtGrpSn);
			}

		});

		function fn_authrtGrpBasDetail( authrtGrpSn ) {

			$.blockUI();

			// 0. 값 셋팅
			let siteTyCd = opener.document.getElementById('siteTyCd').value;

			// 1. 상세 조회
			$.ajax({
				url	 		: "/system/authrt/getAuthrtGrpBasDetail"
				, type 		: "GET"
				, dataType 	: "json"
				, data 		: {
								  "searchSiteTyCd"		: siteTyCd
								, "targetAuthrtGrpSn"	: authrtGrpSn
							  }
				, success 	: function(jsonData, textStatus, jqXHR) {
					$.unblockUI();

					if(jsonData.resultCode === 200){

						// 2. 값 셋팅
						$("#authrtGrpSn").val(jsonData.data.authrtGrpSn);
						$("#authrtGrpNm").val(jsonData.data.authrtGrpNm);

						if( jsonData.data.useYn == 'Y') {
							$("#useY").val('Y');
							$("#useY").prop("checked", true);
						}else {
							$("#useN").val('N');
							$("#useN").prop("checked", true);
						}

					} else {
						alert(jsonData.resultMessage);
					}
				}
			});


		}

		/*
		 * 함수명 : fn_validation
		 * 설   명 : 권한 그룹 저장 전 유효성 검사
		 */
		function fn_validation() {

			let isValid = true;

			/* 권한 그룹명 검사 */
			if($.trim($("#authrtGrpNm").val()) == "") {
				alert("<spring:message code='result-message.messages.common.message.required.data' arguments='권한 그룹명'/>") // '{0}는(은) 필수 입력항목입니다.'
				$("#authrtGrpNm").focus();
				isValid = false;
				return false;
			}

			/* 사용여부 검사 */
			if( !$("#useY").is(":checked") && !$("#useN").is(":checked") ) {
				alert("<spring:message code='result-message.messages.common.message.required.data' arguments='사용여부'/>") // '{0}는(은) 필수 입력항목입니다.'
				isValid = false;
				return false;
			}

			return isValid;

		}

		/*
		 * 함수명 : fn_authrtGrpBasSave
		 * 설   명 : 권한 그룹 저장
		 */
		function fn_authrtGrpBasSave() {

			// 0. 유효성 검사
			if(! fn_validation()){
				return;
			}

			// 1. 저장값 셋팅
			let form = $("#frm_authrtGrpBas")[0];
			let jData = new FormData(form);

			// site_ty_cd 추가
			let siteTyCd = opener.document.getElementById('siteTyCd').value;
			jData.append("siteTyCd", siteTyCd);

			// 2. 저장 시작
			$.blockUI();

			$.ajax({
				url				: "/system/authrt/saveAuthrtGrpBas"
				, type			: "POST"
				, cache			: false
				, data			: jData
				, processData 	: false
				, contentType	: false
				, success 		: function(result) {
					$.unblockUI();

					if (result.resultCode == "200") {

						// 3. 저장 성공 후, 처리 함수 호출
						fn_afterSaveAuthrtGrpBas(result);

					} else {
						alert(result.resultMessage);
					}
				}
			});

		}

		/*
		 * 함수명 : fn_afterSaveAuthrtGrpBas
		 * 설   명 : 권한 그룹 저장 후 처리
		 */
		function fn_afterSaveAuthrtGrpBas( result ){

			alert(result.resultMessage); // 성공 메시지

			// 0. 창 닫기
			window.self.close();

			// 1. 부모창 > 사이트 타입별 리스트 재조회
			window.opener.fn_authrtGrpBasList();

		}

	</script>

	<div id="pop-wrap">

		<h1 class="pop-tit">권한 그룹 추가</h1>

		<form id="frm_authrtGrpBas" name="frm_authrtGrpBas" method="post">

			<input type="hidden" name="authrtGrpSn" id="authrtGrpSn" 	value=""/>

			<div class="pop-contnet">
				<div class="hd-search">
					<table>
						<colgroup>
							<col style="width:100px;"><col>
						</colgroup>
						<tbody>
							<tr>
								<th>
									<span class="blt_req">*</span> 권한그룹명
								</th>
								<td>
									<input type="text" name="authrtGrpNm" id="authrtGrpNm" class="input" style="width:100%;">
								</td>
							</tr>

							<tr>
								<th>
									<span class="blt_req">*</span> 사용여부
								</th>
								<td>
									<label><input type="radio" name="useYn" id="useY" checked="checked" value="Y"> Y</label>
									<label><input type="radio" name="useYn" id="useN" value="N"> N</label>
								</td>
							</tr>
						</tbody>
					</table>
				</div>

				<div class="btn-box r">
					<a href="javascript:self.close();" 	class="btn btn-default">취소</a>
					<a href="javascript:void(0);" 		class="btn btn-blue" onclick="fn_authrtGrpBasSave();">저장</a>
				</div>

			</div><!-- /pop-contnet -->
		</form>

		<a href="javascript:self.close();" class="pop-close"><i class="fa fa-times"></i></a>

	</div><!-- /pop-wrao -->
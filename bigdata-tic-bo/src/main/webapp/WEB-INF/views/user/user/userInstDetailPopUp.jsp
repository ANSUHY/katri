<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

<%@ page import="com.katri.common.Const" %>
<c:set var="USER_TYPE_INST_MASTER" 	value="<%=Const.Code.UserTyCd.INST_MASTER%>" />
<c:set var="USER_TYPE_INST_GENERAL" value="<%=Const.Code.UserTyCd.INST_GENERAL%>" />

	<script>

		$(document).ready(function() {

			// 페이지 첫 로드 시, 값 셋팅
			let targetUserId 	= opener.document.getElementById('targetUserId').value;
			let targetUserTypeCd 	= opener.document.getElementById('targetUserTypeCd').value;

			// 담당자 [저장] 노출 x
			$("#userMasterInfoBtn").hide();

			// 값이 있는 경우 상세 조회
			if( targetUserId != '' ) {

				// 상세 조회
				fn_userBasDetail(targetUserId, targetUserTypeCd);
			}

		});

		// 회사 이메일 형식 체크
		$(document).on("keyup", '#encptEmlAddrVal', function(event){

			if (!(event.keyCode >=37 && event.keyCode<=40)) {
				var inputVal=$(this).val();
				$(this).val(inputVal.replace(/[^a-z0-9@_.-]/gi,''));
			}

		});

		// 회사 전화번호 형식 체크
		$(document).on("keyup", '#wrcTelno', function(event){

			if (!(event.keyCode >=37 && event.keyCode<=40)) {
				var inputVal = $(this).val();
				$(this).val(inputVal.replace(/[^0-9]/gi,''));
			}
		});

		// 휴대폰 번호 형식 체크
		$(document).on("keyup", '#encptMblTelnoVal', function(event){

			if (!(event.keyCode >=37 && event.keyCode<=40)) {
				var inputVal = $(this).val();
				$(this).val(inputVal.replace(/[^0-9]/gi,''));
			}
		});

		/*
		 * 함수명 : fn_userBasDetail
		 * 설   명 : 회원 상세 조회
		 */
		function fn_userBasDetail( userId, userTyCd ) {

			$.blockUI();

			// 1. 상세 조회
			$.ajax({
				url	 		: "/user/user/getUserBasDetail"
				, type 		: "GET"
				, dataType 	: "json"
				, data 		: {
								  "targetUserId"	: userId
								, "targetUserTypeCd"	: userTyCd
							  }
				, success 	: function(jsonData, textStatus, jqXHR) {
					$.unblockUI();

					if(jsonData.resultCode === 200){

						// 2. 회원 상세 값 셋팅
						$("#userId").val(jsonData.data.userId);
						$("#userTyCd").val(jsonData.data.userTyCd);

						/* 2_0. 계정 정보 */
						$("#info_userTyNm").html(jsonData.data.userTyNm);
						$("#info_userSttNm").html(jsonData.data.userSttNm);
						$("#info_userId").html(jsonData.data.userId);
						$("#info_joinYmd").html(jsonData.data.joinYmd);

						/* 2_1. 기관 정보 */
						$("#info_instNm").html(jsonData.data.instNm);

						/* 2_2. 담당자/회원 정보 */
						if( "${USER_TYPE_INST_MASTER}" == jsonData.data.userTyCd ){
							// 담당자 정보
							$("#userMasterInfo").html("담당자 정보");

							$("span[id$='Req']").show();
							$("#userMasterInfoBtn").show();

							let userDeptNmHtml 			= "<input type='text' name='userDeptNm' 	id='userDeptNm' 		value='" + jsonData.data.userDeptNm 		+ "' maxlength='20' class='block'/>";
							let wrcTelnoHtml 			= "<input type='text' name='wrcTelno' 		id='wrcTelno' 			value='" + fn_replaceAll(jsonData.data.wrcTelno,"-", "") + "' maxlength='15' class='block number'/>";
							let userNmHtml 				= "<input type='text' name='userNm' 		id='userNm' 			value='" + jsonData.data.userNm 			+ "' maxlength='15' class='block'/>";
							let encptMblTelnoValHtml 	= "<input type='text' name='mblTelnoVal'	id='encptMblTelnoVal' 	value='" + fn_replaceAll(jsonData.data.encptMblTelnoVal,"-", "") + "' maxlength='15' class='block number'/>";
							let encptEmlAddrValHtml 	= "<input type='text' name='emlAddrVal' 	id='encptEmlAddrVal' 	value='" + jsonData.data.encptEmlAddrVal 	+ "' maxlength='30' class='block'/>";

							$("#info_userDeptNm").html(userDeptNmHtml);
							$("#info_wrcTelno").html(wrcTelnoHtml);
							$("#info_userNm").html(userNmHtml);
							$("#info_encptMblTelnoVal").html(encptMblTelnoValHtml);
							$("#info_encptEmlAddrVal").html(encptEmlAddrValHtml);

						} else {
							// 회원 정보
							$("#userMasterInfo").html("회원 정보");

							$("#userMasterInfoBtn").hide();
							$("span[id$='Req']").hide();

							$("#info_userDeptNm").html(jsonData.data.userDeptNm);
							$("#info_wrcTelno").html(jsonData.data.wrcTelno);
							$("#info_userNm").html(jsonData.data.userNm);
							$("#info_encptMblTelnoVal").html(jsonData.data.encptMblTelnoVal);
							$("#info_encptEmlAddrVal").html(jsonData.data.encptEmlAddrVal);

						}
						/*
						// 3. 회원 파일 목록 셋팅
						let fileHtml 		= "";
						let userfileList 	= jsonData.data.lstUserFile;

						if ( userfileList.length > 0 ) {

							$.each(userfileList, function(index, data){
								fileHtml += "<a href='/file/downloadFile?encodeFileSn=" + data.encodeFileSn + "' style='text-decoration: underline;'>";
								fileHtml += data.orgnlFileNm;
								fileHtml += "</a>";
								fileHtml += "&nbsp;&nbsp;&nbsp;&nbsp;"; // 간격
							})
						}

						$("#userFileArea").html(fileHtml);

						// 4. 회원 관심 키워드 값 셋팅

						let prdtHtml 		= "";
						let userPrdtList 	= jsonData.data.lstUserPrdt;

						if ( userPrdtList != null ) {

							$.each(userPrdtList, function(index, data){
								prdtHtml += data.stdLgclfNm + " > " + data.stdMlclfNm;
								prdtHtml += "<br/>";
							})
						}


						$("#userPrdtArea").html(prdtHtml);
						*/

					} else {

						alert(jsonData.resultMessage);
						self.close();
					}
				}
			});
		}

		/*
		 * 함수명 : fn_userInfoValidation
		 * 설   명 : 회원 담담자 정보 저장 유효성 검사
		 */
		function fn_userInfoValidation() {

			let isValid = true;

			/* 부서명 검사 */
			if($.trim($("#userDeptNm").val()) == "") {
				alert("<spring:message code='result-message.messages.common.message.required.data' arguments='부서명'/>") // '{0}는(은) 필수 입력항목입니다.'
				$("#userDeptNm").focus();
				isValid = false;
				return false;
			}

			/* 회사 전화번호 검사 */
			if($.trim($("#wrcTelno").val()) == "") {
				alert("<spring:message code='result-message.messages.common.message.required.data' arguments='회사 전화번호'/>") // '{0}는(은) 필수 입력항목입니다.'
				$("#wrcTelno").focus();
				isValid = false;
				return false;
			}

			/* 이름 검사 */
			if($.trim($("#userNm").val()) == "") {
				alert("<spring:message code='result-message.messages.common.message.required.data' arguments='이름'/>") // '{0}는(은) 필수 입력항목입니다.'
				$("#userNm").focus();
				isValid = false;
				return false;
			}

			/* 휴대폰 번호 검사 */
			if($.trim($("#encptMblTelnoVal").val()) == "") {
				alert("<spring:message code='result-message.messages.common.message.required.data' arguments='휴대폰 번호'/>") // '{0}는(은) 필수 입력항목입니다.'
				$("#encptMblTelnoVal").focus();
				isValid = false;
				return false;
			}

			/* 회사 이메일 검사 */
			if($.trim($("#encptEmlAddrVal").val()) == "") {
				alert("<spring:message code='result-message.messages.common.message.required.data' arguments='회사 이메일'/>") // '{0}는(은) 필수 입력항목입니다.'
				$("#encptEmlAddrVal").focus();
				isValid = false;
				return false;
			}

			return isValid;
		}

		/*
		 * 함수명 : fn_userInfoSave
		 * 설   명 : 회원 담담자 정보 저장
		 */
		function fn_userInfoSave() {

			// 0. 유효성 검사
			if(! fn_userInfoValidation() ){
				return;
			}

			// 1. 저장 시작
			$.blockUI();

			let form = $("#frm_userInfo")[0];
			let jData = new FormData(form);

			$.ajax({
				url				: "/user/user/saveUserInfo"
				, type			: "POST"
				, cache			: false
				, data			: jData
				, processData 	: false
				, contentType	: false
				, success 		: function(result) {
					$.unblockUI();

					if (result.resultCode == "200") {
						// 2. 저장 성공 메시지 후, 창 닫기
						alert(result.resultMessage);

						let targetUserId = $("#userId").val();
						let targetUserTypeCd = $("#userTyCd").val();

						fn_userBasDetail(targetUserId, targetUserTypeCd);

					} else {
						alert(result.resultMessage);
					}
				}
			});

		}

	</script>

	<div id="pop-wrap">

		<h1 class="pop-tit">회원 상세</h1>

		<div class="pop-contnet">

			<!-- 계정 정보 -->
			<div class="btn-box bot r">
				<div class="left">
					<h2 class="pop-tit2" style="margin-top: 0px;">계정 정보</h2>
				</div>
				<a href="javascript:void(0);" class="btn btn-blue" onclick="opener.fn_userPwdChgPop();">비밀번호 초기화</a>
			</div>

			<div class="hd-search">
				<table>
					<colgroup>
						<col style="width:150px;">
						<col>
						<col style="width:150px">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th>
								회원유형
							</th>
							<td id="info_userTyNm">

							</td>
							<th>
								회원 상태
							</th>
							<td id="info_userSttNm">

							</td>
						</tr>
						<tr>
							<th>
								아이디
							</th>
							<td id="info_userId">

							</td>
							<th>
								가입일
							</th>
							<td id="info_joinYmd">

							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- // 계정 정보 -->

			<!-- 기관 정보 -->
			<h2 class="pop-tit2 left" style="margin-top: 10px;">기관 정보</h2>
			<div class="hd-search">
				<table>
					<colgroup>
						<col style="width:150px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th>
								기관명
							</th>
							<td id="info_instNm">

							</td>
						</tr>
					<!--
						<tr>
							<th>
								주소
							</th>
							<td>
								서울특별시 금천구
							</td>
						</tr>
					-->
					</tbody>
				</table>
			</div>
			<!-- // 기관 정보 -->

			<!-- 담당자 정보 --> <!-- 회원 정보 -->
			<form action="" method="post" name="frm_userInfo" id="frm_userInfo">

				<input type="hidden" name="targetUserId" 	id="userId" 	value=""/>
				<input type="hidden" name="targetUserTyCd"	id="userTyCd" 	value=""/>

				<div class="btn-box bot r">
					<div class="left">
						<h2 class="pop-tit2 left" style="margin-top: 0px;" id="userMasterInfo"></h2>
					</div>
					<a href="javascript:void(0);" id="userMasterInfoBtn" class="btn btn-blue" onclick="fn_userInfoSave();" >저장</a>
				</div>

				<div class="hd-search">
					<table>
						<colgroup>
							<col style="width:150px;">
							<col>
							<col style="width:150px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th>
									<span class="blt_req" id="info_userDeptNmReq">*</span>
									부서명
								</th>
								<td id="info_userDeptNm">

								</td>
								<th>
									<span class="blt_req" id="info_wrcTelnoReq">*</span>
									회사 전화번호
								</th>
								<td id="info_wrcTelno">

								</td>
							</tr>
							<tr>
								<th>
									<span class="blt_req" id="info_userNmReq">*</span>
									이름
								</th>
								<td id="info_userNm">

								</td>
								<th>
									<span class="blt_req" id="info_encptMblTelnoValReq">*</span>
									휴대폰 번호
								</th>
								<td id="info_encptMblTelnoVal">

								</td>
							</tr>
							<tr>
								<th>
									<span class="blt_req" id="info_encptEmlAddrValReq">*</span>
									회사 이메일
								</th>
								<td colspan="3" id="info_encptEmlAddrVal">

								</td>
							</tr>
						<!--
							<tr>
								<th>
									근무지 주소
								</th>
								<td colspan="3">

								</td>
							</tr>
						-->
						</tbody>
					</table>
				</div>
			</form>
			<!-- // 담당자 정보 --> <!-- // 회원 정보 -->
			<%--
			<!-- 관심 키워드 -->
			<h2 class="pop-tit2 left" style="margin-top: 10px;">관심 키워드</h2>
			<div class="hd-search">
				<table>
					<colgroup>
						<col style="width:150px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th>
								선택항목
							</th>
							<td id="userPrdtArea">

							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- // 관심 키워드 -->

			<!-- 첨부파일 -->
			<h2 class="pop-tit2 left" style="margin-top: 10px;">첨부파일</h2>
			<div class="hd-search">
				<table>
					<colgroup>
						<col style="width:150px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th>
								첨부파일
							</th>
							<td id="userFileArea">

							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- // 첨부파일 -->
			--%>
			<div class="btn-box r">
				<a href="javascript:void(0);" class="btn btn-blue" onclick="opener.fn_userLogHistPop();">접속 로그</a>
				<a href="javascript:void(0);" class="btn btn-blue" onclick="opener.fn_userTrmsAgreHistPop();">약관동의 이력</a>
			</div>

		</div><!-- /pop-contnet -->
		<a href="javascript:self.close();" class="pop-close"><i class="fa fa-times"></i></a>
	</div><!-- /pop-wrao -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

<%@ page import="com.katri.common.Const" %>
<c:set var="USER_TYPE_ENT_MASTER" 	value="<%=Const.Code.UserTyCd.ENT_MASTER%>" />
<c:set var="USER_TYPE_ENT_GENERAL" 	value="<%=Const.Code.UserTyCd.ENT_GENERAL%>" />

<c:set var="USER_STT_JOIN_APPLY" 		value="<%=Const.Code.UserSttCd.JOIN_APPLY%>" />
<c:set var="USER_STT_WITHDRAWAL_APPLY" 	value="<%=Const.Code.UserSttCd.WITHDRAWAL_APPLY%>" />

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

	<script>

		$(document).ready(function() {

			// 페이지 첫 로드 시, 값 셋팅
			let targetUserId 		= opener.document.getElementById('targetUserId').value;
			let targetUserTypeCd 	= opener.document.getElementById('targetUserTypeCd').value;

			// 값이 있는 경우 상세 조회
			if( targetUserId != '' ) {

				// 승인, 반려 버튼 숨기기
				$(".apprBtn").hide();

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
		 * 함수명 : fn_openAddressPop
		 * 설   명 : 근무지 주소 [우편번호] 클릭 시, api 호출
		 */
		function fn_openAddressPop() {

			new daum.Postcode({

				// 주소 선택 시,
				oncomplete: function(data) {
					// 우편 번호 셋팅
					$("#wrcZip").val( data.zonecode );
					// 기본 주소 셋팅
					$("#wrcBasAddr").val( data.address );
					// 상세 주소 초기화
					$("#wrcDaddr").val('');
				}

			}).open();

		}

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
						$("#userSttCd").val(jsonData.data.userSttCd);

						$("#info_userTyNm").html(jsonData.data.userTyNm);
						$("#info_userSttNm").html(jsonData.data.userSttNm);
						$("#info_userId").html(jsonData.data.userId);
						$("#info_joinYmd").html(jsonData.data.joinYmd);

						/* 2_0. 그룹 영역 셋팅 */
						$("#info_entGrpNm").html(jsonData.data.entGrpNm);
						$("#info_entGrpMngNo").html(jsonData.data.entGrpMngNo);

						/* 2_1. 사업자 영역 셋팅 */
						$("#info_entNm").html(jsonData.data.entNm);
						$("#info_opbizYmd").html(jsonData.data.opbizYmd);
						$("#info_rprsvNm").html(jsonData.data.rprsvNm);
						$("#info_brno").html(jsonData.data.brno);
						let entGrpBasFullAddrHtml = "(" + jsonData.data.entGrpZip + ")&nbsp;" + jsonData.data.entGrpBasAddr + "&nbsp;" + jsonData.data.entGrpDaddr;
						$("#info_entGrpBasFullAddr").html( entGrpBasFullAddrHtml );

						/* 2_2. 담당자/회원 정보 */
						if( "${USER_TYPE_ENT_MASTER}" == jsonData.data.userTyCd ) {

							// 담당자 정보
							$("#userMasterInfo").html("담당자 정보");

							$("span[id$='Req']").show();
							$("#userMasterInfoBtn").show();

							if( "${USER_STT_JOIN_APPLY}" == jsonData.data.userSttCd || "${USER_STT_WITHDRAWAL_APPLY}" == jsonData.data.userSttCd ) {
								$(".apprBtn").show();
							} else {
								$(".apprBtn").hide();
							}

							let userDeptNmHtml 			= "<input type='text' name='userDeptNm' 	id='userDeptNm' 		value='" + jsonData.data.userDeptNm 		+ "' maxlength='20' class='block'/>";
							let wrcTelnoHtml 			= "<input type='text' name='wrcTelno' 		id='wrcTelno' 			value='" + fn_replaceAll(jsonData.data.wrcTelno,"-", "") + "' maxlength='15' class='block number'/>";
							let userNmHtml 				= "<input type='text' name='userNm' 		id='userNm' 			value='" + jsonData.data.userNm 			+ "' maxlength='15' class='block'/>";
							let encptMblTelnoValHtml 	= "<input type='text' name='mblTelnoVal'	id='encptMblTelnoVal' 	value='" + fn_replaceAll(jsonData.data.encptMblTelnoVal,"-", "") + "' maxlength='15' class='block number'/>";
							let encptEmlAddrValHtml 	= "<input type='text' name='emlAddrVal' 	id='encptEmlAddrVal' 	value='" + jsonData.data.encptEmlAddrVal 	+ "' maxlength='30' class='block'/>";
							let userJbgdNmHtml			= "<input type='text' name='userJbgdNm' 	id='userJbgdNm' 		value='" + jsonData.data.userJbgdNm 		+ "' maxlength='25' class='block'/>";

							$("#info_userDeptNm").html(userDeptNmHtml);
							$("#info_wrcTelno").html(wrcTelnoHtml);
							$("#info_userNm").html(userNmHtml);
							$("#info_encptMblTelnoVal").html(encptMblTelnoValHtml);
							$("#info_encptEmlAddrVal").html(encptEmlAddrValHtml);
							$("#info_userJbgdNm").html(userJbgdNmHtml);

							let wrcZipHtml				= "<input class='read' type='text' name='wrcZip' 	id='wrcZip' 		value='" + jsonData.data.wrcZip 			+ "' readonly />";
							let wrcZipBtnHtml			= "<a href='javascript:void(0);' class='btn btn-blue' style='margin-left:10px;' onclick='fn_openAddressPop();'>우편번호</a> <br/>"
							let wrcBasAddrHtml			= "<input class='read' style='width:45%; margin-top:5px;' 		 type='text' name='wrcBasAddr' 	id='wrcBasAddr' value='" + jsonData.data.wrcBasAddr	+ "' readonly />";
							let wrcDaddrHtml			= "<input style='width:45%; margin:5px 0px 0px 5px;' type='text' name='wrcDaddr' id='wrcDaddr' value='" + jsonData.data.wrcDaddr 	+ "' maxlength='30'/>";

							$("#info_wrcBasFullAddr").html( wrcZipHtml + wrcZipBtnHtml + wrcBasAddrHtml + wrcDaddrHtml );

							// 2_3. 회원 파일 목록 셋팅 - 기업(마스터)만 파일 존재 o
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

						} else {

							// 2_4. 회원 정보
							$("#userMasterInfo").html("회원 정보");

							$("span[id$='Req']").hide();
							$("#userMasterInfoBtn").hide();

							$(".apprBtn").hide();

							$("#info_userDeptNm").html(jsonData.data.userDeptNm);
							$("#info_userJbgdNm").html(jsonData.data.userJbgdNm);
							$("#info_userNm").html(jsonData.data.userNm);
							$("#info_wrcTelno").html(jsonData.data.wrcTelno);
							$("#info_encptMblTelnoVal").html(jsonData.data.encptMblTelnoVal);
							$("#info_encptEmlAddrVal").html(jsonData.data.encptEmlAddrVal);

							let wrcBasFullAddrHtml = "(" + jsonData.data.wrcZip + ")&nbsp;" + jsonData.data.wrcBasAddr + "&nbsp;" + jsonData.data.wrcDaddr
							$("#info_wrcBasFullAddr").html( wrcBasFullAddrHtml );

							// 2_5. 기업(일반) 파일 존재 x
							$("#fileTitle").hide();
							$("#fileArea").hide();
						}

						// 3. 회원 관심 키워드 값 셋팅
						let prdtHtml 		= "";
						let userPrdtList 	= jsonData.data.lstUserPrdt;

						if ( userPrdtList != null ) {

							$.each(userPrdtList, function(index, data){
								prdtHtml += data.stdLgclfNm + " > " + data.stdMlclfNm;
								prdtHtml += "<br/>";
							})
						}

						$("#userPrdtArea").html(prdtHtml);

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

			/* 직급 검사 */
			if($.trim($("#userJbgdNm").val()) == "") {
				alert("<spring:message code='result-message.messages.common.message.required.data' arguments='직급'/>") // '{0}는(은) 필수 입력항목입니다.'
				$("#userJbgdNm").focus();
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

			/* 회사 전화번호 검사 */
			if($.trim($("#wrcTelno").val()) == "") {
				alert("<spring:message code='result-message.messages.common.message.required.data' arguments='회사 전화번호'/>") // '{0}는(은) 필수 입력항목입니다.'
				$("#wrcTelno").focus();
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

			/* 우편 번호 검사 */
			if($.trim($("#wrcZip").val()) == "") {
				alert("<spring:message code='result-message.messages.common.message.required.data' arguments='우편 번호'/>") // '{0}는(은) 필수 입력항목입니다.'
				$("#wrcZip").focus();
				isValid = false;
				return false;
			}

			/* 기본 주소 검사 */
			if($.trim($("#wrcBasAddr").val()) == "") {
				alert("<spring:message code='result-message.messages.common.message.required.data' arguments='기본 주소'/>") // '{0}는(은) 필수 입력항목입니다.'
				$("#wrcBasAddr").focus();
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
						// 2. 저장 성공 메시지 후, 재 조회
						alert(result.resultMessage);

						let targetUserId 	 = $("#userId").val();
						let targetUserTypeCd = $("#userTyCd").val();

						fn_userBasDetail(targetUserId, targetUserTypeCd);

					} else {
						alert(result.resultMessage);
					}
				}
			});

		}

		/*
		 * 함수명 : fn_userSttApprInit
		 * 설   명 : 회원 승인/반려 처리 초기 작업
		 */
		function fn_userSttApprInit( apprFlag ) {

			let sttCd	= $("#userSttCd").val();
			let msg		= "";

			// 0. 가입 신청
			if( sttCd == "${USER_STT_JOIN_APPLY}" ) {
				if( apprFlag == 'Y' ) {
					msg = "<spring:message code='result-message.messages.user.message.join.approval'/>"; // '회원가입을 승인 하시겠습니까?'
				} else {
					msg = "<spring:message code='result-message.messages.user.message.join.reject'/>"; // '회원가입을 반려 하시겠습니까?'
				}
			}

			// 1. 탈퇴 신청
			if( sttCd == "${USER_STT_WITHDRAWAL_APPLY}" ) {
				if( apprFlag == 'Y' ) {
					msg = "<spring:message code='result-message.messages.user.message.withdrawal.approval'/>"; // '회원탈퇴를 승인 하시겠습니까?'
				} else {
					msg = "<spring:message code='result-message.messages.user.message.withdrawal.reject'/>"; // '회원탈퇴를 반려 하시겠습니까?'
				}
			}

			// 확인 시, 승인/반려 처리 함수 호출
			if( confirm( msg ) ){
				fn_userSttAppr( apprFlag );
			}

		}

		/*
		 * 함수명 : fn_userSttAppr
		 * 설   명 : 회원 상태 승인/반려 처리
		 */
		function fn_userSttAppr( apprFlag ) {

			// 1. 저장 시작
			$.blockUI();

			let form = $("#frm_userInfo")[0];
			let jData = new FormData(form);

			// 2. 승인/반려 여부 셋팅
			jData.append("apprFlag", apprFlag);

			$.ajax({
				url				: "/user/user/saveUserSttAppr"
				, type			: "POST"
				, cache			: false
				, data			: jData
				, processData 	: false
				, contentType	: false
				, success 		: function(result) {
					$.unblockUI();

					if (result.resultCode == "200") {
						// 2. 저장 성공 후
						fn_userSttApprAfter(result);

					} else {
						alert(result.resultMessage);
					}
				}
			});

		}

		/*
		 * 함수명 : fn_userSttApprAfter
		 * 설   명 : 회원 상태 승인/반려 처리 후
		 */
		function fn_userSttApprAfter(result) {

			// 1. 저장 성공 메시지 후, 재 조회
			alert(result.resultMessage);

			// 2. 상세 재 조회
			if( result.data.userTyCd == "${USER_STT_JOIN_APPLY}" && result.data.apprFlag == "N" ) {
				// 가입 반려 시,
				self.close();
			} else {
				// 이 외, 재 조회
				fn_userBasDetail(result.data.userId, result.data.userTyCd);
			}

			// 3. 목록 재 조회
			let pageNum = opener.$("#currPage").val();
			opener.fn_userBasList(pageNum);

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
						<col style="width:200px">
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
							<td>
								<p id="info_userSttNm"></p>
								<div class="apprBtn btn-box r" style="margin-bottom: 5px;">
									<div class="left">
										<div>
											<a href="javascript:void(0);" class="btn btn-blue" 		style="min-width: 30px;" onclick="fn_userSttApprInit('Y');">승인</a>
											<a href="javascript:void(0);" class="btn btn-default" 	style="min-width: 30px;" onclick="fn_userSttApprInit('N');">반려</a>
										</div>
									</div>
								</div>
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

			<!-- 그룹 정보 -->
			<h2 class="pop-tit2 left" style="margin-top: 10px;">그룹 정보</h2>
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
								그룹명
							</th>
							<td id="info_entGrpNm">

							</td>
							<th>
								그룹아이디
							</th>
							<td id="info_entGrpMngNo">

							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- // 그룹 정보 -->

			<!-- 사업자 정보 -->
			<h2 class="pop-tit2 left" style="margin-top: 10px;">사업자 정보</h2>
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
								상호명
							</th>
							<td id="info_entNm">

							</td>
							<th>
								개업일
							</th>
							<td id="info_opbizYmd">

							</td>
						</tr>
						<tr>
							<th>
								대표자명
							</th>
							<td id="info_rprsvNm">

							</td>
							<th>
								사업자 등록 번호
							</th>
							<td id="info_brno">

							</td>
						</tr>
						<tr>
							<th>
								사업장 소재지
							</th>
							<td colspan="3" id="info_entGrpBasFullAddr">

							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- // 사업자 정보 -->

			<!-- 담당자 정보 --> <!-- 회원 정보 -->
			<form action="" method="post" name="frm_userInfo" id="frm_userInfo">

				<input type="hidden" name="targetUserId" 	id="userId" 	value=""/>
				<input type="hidden" name="targetUserTyCd"	id="userTyCd" 	value=""/>
				<input type="hidden" name="targetUserSttCd"	id="userSttCd" 	value=""/>

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
									<span class="blt_req" id="info_userJbgdNmReq">*</span>
									직급
								</th>
								<td id="info_userJbgdNm">

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
									<span class="blt_req" id="info_wrcTelnoReq">*</span>
									회사 전화번호
								</th>
								<td id="info_wrcTelno">

								</td>
							</tr>
							<tr>
								<th>
									<span class="blt_req" id="info_encptMblTelnoValReq">*</span>
									휴대폰 번호
								</th>
								<td id="info_encptMblTelnoVal">

								</td>
								<th>
									<span class="blt_req" id="info_encptEmlAddrValReq">*</span>
									회사 이메일
								</th>
								<td id="info_encptEmlAddrVal">

								</td>
							</tr>
							<tr>
								<th>
									<span class="blt_req" id="info_wrcBasFullAddrReq">*</span>
									근무지 주소
								</th>
								<td colspan="3" id="info_wrcBasFullAddr">

								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
			<!-- // 담당자 정보 --> <!-- // 회원 정보 -->

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
			<h2 class="pop-tit2 left" style="margin-top: 10px;" id="fileTitle">첨부파일</h2>
			<div class="hd-search" id="fileArea">
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

			<div class="btn-box r">
				<a href="javascript:void(0);" class="btn btn-blue" onclick="opener.fn_userLogHistPop();">접속 로그</a>
				<a href="javascript:void(0);" class="btn btn-blue" onclick="opener.fn_userTrmsAgreHistPop();">약관동의 이력</a>
			</div>

		</div><!-- /pop-contnet -->
		<a href="javascript:self.close();" class="pop-close"><i class="fa fa-times"></i></a>
	</div><!-- /pop-wrao -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

	<script type="text/javascript">

		$(document).ready(function(){

			// 페이지 첫 로드 시, 값 셋팅
			let targetUserId 	= opener.document.getElementById('targetUserId').value;

			// 값이 있는 경우 상세 조회
			if( targetUserId != '' ) {
				$("#userId").val(targetUserId);
			}

		})

		/*
		 * 함수명 : fn_pwdValidation
		 * 설 명 : 비밀번호 유효성 검사
		 */
		function fn_pwdValidation() {

			let isValid = true;

			// 영문+숫자+특수문자 조합의 8~20자 비밀번호
			const regPwd =	/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$/;

			let chgPwd = $("#chgUserPwd").val();
			let chkPwd = $("#chgUserPwdChk").val();

			/* 새 비밀번호 검사 */
			if($.trim(chgPwd) == "") {
				alert("<spring:message code='result-message.messages.common.message.required.data' arguments='새 비밀번호'/>") // '{0}는(은) 필수 입력항목입니다.'
				$("#chgUserPwd").focus();
				isValid = false;
				return false;
			}

			/* 비밀번호 확인 검사 */
			if($.trim(chkPwd) == "") {
				alert("<spring:message code='result-message.messages.common.message.required.data' arguments='비밀번호 확인'/>") // '{0}는(은) 필수 입력항목입니다.'
				$("#chgUserPwdChk").focus();
				isValid = false;
				return false;
			}

			/* 비밀번호 형식 검사 */
			if( !regPwd.test(chgPwd) ){
				alert("<spring:message code='result-message.messages.user.message.data.password.error'/>"); // 비밀번호가 형식에 맞지 않습니다.
				$("#chgUserPwd").focus();
				isValid = false;
				return false;
			}

			/* 비밀번호가 일치 검사 */
			if( chgPwd != chkPwd ) {
				alert("<spring:message code='result-message.messages.user.message.data.password.accord.error'/>"); // 비밀번호가 일치하지 않습니다.
				$("#chgUserPwdChk").focus();
				isValid = false;
				return false;
			}

			return isValid;
		}

		/*
		 * 함수명 : fn_userPwdSave
		 * 설 명 : 비밀번호 유효성 검사
		 */
		function fn_userPwdSave() {

			// 0. 유효성 검사
			if(! fn_pwdValidation() ){
				return;
			}

			// 1. 저장 시작
			$.blockUI();

			let form = $("#frm_userPwd")[0];
			let jData = new FormData(form);

			$.ajax({
				url				: "/user/user/saveUserPwdChg"
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
						self.close();
					} else {
						alert(result.resultMessage);
					}
				}
			});

		}

	</script>

	<div id="pop-wrap">

		<h1 class="pop-tit">비밀번호 초기화</h1>

		<div class="pop-contnet">
			<form action="" method="post" id="frm_userPwd" name="frm_userPwd">

				<input type="hidden" name="targetUserId" 	id="userId" 	value=""/>

				<div class="hd-search">
					<table>
						<colgroup>
							<col style="width:100px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th>
									<span class="blt_req">*</span> 새 비밀번호
								</th>
								<td>
									<input type="password" class="input" style="width:100%;" id="chgUserPwd" name="chgUserPwd" maxlength="20">

									<p style="margin-top: 5px;">* 영문+숫자+특수문자 각 최소 1자 이상의 문자를 조합하여 최소 8 ~ 20자</p>
								</td>
							</tr>
							<tr>
								<th>
									<span class="blt_req">*</span> 비밀번호 확인
								</th>
								<td>
									<input type="password" class="input" style="width:100%;" id="chgUserPwdChk" name="chgUserPwdChk" maxlength="20">
									<br/>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>

			<div class="btn-box r">
				<a href="javascript:self.close();" class="btn btn-default">취소</a>
				<a href="javascript:void(0)" class="btn btn-blue" onclick="fn_userPwdSave();">저장</a>
			</div>

		</div><!-- /pop-contnet -->
		<a href="javascript:self.close();" class="pop-close"><i class="fa fa-times"></i></a>
	</div><!-- /pop-wrao -->

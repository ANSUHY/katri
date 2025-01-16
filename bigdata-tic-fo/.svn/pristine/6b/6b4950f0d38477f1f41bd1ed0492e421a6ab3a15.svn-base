<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">

	$(document).ready(function() {

	});

	/*
	 * 함수명 : fn_updateChgOverDayChgPwd()
	 * 설   명 : 비밀번호 변경(기간 지난 비밀번호에 대해)
	 */
	function fn_updateChgOverDayChgPwd() {

		/* ======= 1. 유효성검사 */
		if ( !fn_validation() ) {
			return;
		}

		/* ======= 2. 비밀번호 변경(기간 지난 비밀번호에 대해) 처리 */
		$.blockUI();
		$.ajax({
			url	 		: "/auth/updateChgOverDayChgPwd"
			, type 		: "POST"
			, dataType 	: "json"
			, data 		: $("#frm_overDayChgPwd").serialize()
			, success 	: function(result) {
				$.unblockUI();

				if (result.resultCode === 200) {

					alert("<spring:message code='result-message.messages.pwd.message.save.success'/>");  //'비밀번호가 변경되었습니다.'
					location.href = "/";

				} else {

					if(result.data.chgResultCode === "noLoginId"){
						alert(result.resultMessage);
						location.href = "/auth/login";
					} else {
						alert(result.resultMessage);
					}

				}
			}
		});

	}

	/*
	 * 함수명 : fn_validation()
	 * 설  명  : 유효성 검사 체크
	 */
	function fn_validation() {

		let isValid = true;

		// 1. [현재 비밀번호] 빈값 체크
		if (fn_emptyCheck($("#frm_overDayChgPwd #nowPwd").val())) {
			alert("<spring:message code='result-message.messages.common.message.required.data2' arguments='현재 비밀번호'/>") //'{0}를(을) 입력해주세요.'
			$("#frm_overDayChgPwd #nowPwd").focus();
			isValid = false;
			return false;
		}

		// 2. [새비밀번호] 빈값 체크
		if (fn_emptyCheck($("#frm_overDayChgPwd #chgPwd").val())) {
			alert("<spring:message code='result-message.messages.common.message.required.data2' arguments='변경할 비밀번호'/>") //'{0}를(을) 입력해주세요.'
			$("#frm_overDayChgPwd #chgPwd").focus();
			isValid = false;
			return false;
		}

		// 3. [새비밀번호확인] 빈값 체크
		if (fn_emptyCheck($("#frm_overDayChgPwd #chgPwdCheck").val())) {
			alert("<spring:message code='result-message.messages.common.message.required.data2' arguments='비밀번호 확인'/>") //'{0}를(을) 입력해주세요.'
			$("#frm_overDayChgPwd #chgPwdCheck").focus();
			isValid = false;
			return false;
		}

		// 4. [새비밀번호] 와 [새비밀번호확인] 값같은지 체크
		if ($("#frm_overDayChgPwd #chgPwd").val() != $("#frm_overDayChgPwd #chgPwdCheck").val()) {
			alert("<spring:message code='result-message.messages.pwd.message.nomatch.chgpwd.chgpwdcheck'/>");  //'비밀번호가 일치하지 않습니다.'
			$("#frm_overDayChgPwd #chgPwdCheck").focus();
			isValid = false;
			return false;
		}

		// 5. [새비밀번호] 패턴 체크
		const patternPwd = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$/; //영문, 숫자, 특수문자 중 3가지 이상의 문자를 조합하여 최소 8 ~ 20자 이하로 설정
		if ( !patternPwd.test( $("#frm_overDayChgPwd #chgPwd").val() ) ) {
			alert("<spring:message code='result-message.messages.pwd.message.nopattern.chgpwd'/>");  //'비밀번호가 형식에 맞지 않습니다.'
			$("#frm_overDayChgPwd #chgPwd").focus();
			isValid = false;
			return false;
		}

		return isValid;
	}

	/*
	 * 함수명 : fn_updatePwdChgDtNow();
	 * 설   명 : 다음에 변경하기(last_pwd_chg_dt를 지금으로 update)
	 */
	function fn_updatePwdChgDtNow() {

		/* ======= 1. (last_pwd_chg_dt를 지금으로 update) 처리 */
		$.blockUI();
		$.ajax({
			url	 		: "/auth/updatePwdChgDtNow"
			, type 		: "POST"
			, dataType 	: "json"
			, data 		: {}
			, success 	: function(result) {
				$.unblockUI();

				if (result.resultCode === 200) {

					location.href = "/";

				} else {

					if(result.data.chgResultCode === "noLoginId"){
						alert(result.resultMessage);
						location.href = "/auth/login";
					} else {
						alert(result.resultMessage);
					}

				}
			}
		});

	}

</script>

<!-- ===== header ====== -->
<header id="header">
	<div id="sub-mv" class="sub-mem">
		<div class="inner">
			<h2>비밀번호 변경</h2>
			<div class="sub-obj">오브젝트</div>
		</div>
	</div>
</header>
<!-- ===== /header ====== -->

<!-- ===== container ====== -->
<div id="container">

	<!--container-->
	<div id="cont" class="cont-login">
		<h2 class="tit">비밀번호 변경</h2>
		<h3 class="tit">회원님의 비밀번호를 변경해주세요</h3>

		<form id="frm_overDayChgPwd" name="frm_overDayChgPwd" method="post" onSubmit="return false;">

			<article class="info-rect tBr">
				<p>최근 90일 동안 같은 비밀번호를 변경하지 않고, 동일한 비밀번호를 사용 중이 십니다.</p>
				<p>시험인증 빅데이터 플랫폼에서는 회원님의 소중한 개인정보를 보호하기 위하여 비밀번호 변경을 안내해 드리고 있습니다.</p>
				<p>주기적인 비밀번호 변경으로 소중한 개인정보를 보호해 주세요.</p>
			</article>

			<article class="info-rect change-wr">
				<ul>
					<li>
						<label>현재 비밀번호</label>
						<input type="password" id="nowPwd"			name="nowPwd"/>
					</li>
					<li>
						<label>새 비밀번호</label>
						<input type="password" id="chgPwd"			name="chgPwd"/>
					</li>
					<li>
						<label>새 비밀번호 확인</label>
						<input type="password" id="chgPwdCheck"		name="chgPwdCheck"/>
					</li>
				</ul>
				<p class="cau">
					영문, 숫자, 특수문자 중 3가지 이상의 문자를 조합하여 최소 8 ~ 20자 이하로 설정 <br/>
					기존비밀번호 사용불가 <br/>
					비밀번호에 아이디 포함 불가
				</p>
			</article>

		</form>

		<div class="btn-wr">
			<a href="javascript:void(0);" onclick="fn_updatePwdChgDtNow();" class="btn">다음에 변경하기</a>
			<a href="javascript:void(0);" onclick="fn_updateChgOverDayChgPwd();" class="btn cancel">변경하기</a>
		</div>
	</div>
</div>
<!-- ===== /container ====== -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<script type="text/javascript">


	$(document).ready(function() {
	});

	/**
	 * 유효성 체크
	 */
	function fn_valid() {
		const chgUserPwd = $("#chgUserPwd").val();
		const chgUserPwdChk = $("#chgUserPwdChk").val();

		// 변경 비밀번호
		if ($.trim(chgUserPwd) === "") {
			alert("<spring:message code='result-message.messages.pwd.message.required.chgpwd'/>") // 변경할 비밀번호를 입력해 주세요.
			$("#chgUserPwd").focus();
			return false;
		}
		// 변경 비밀번호 확인
		if ($.trim(chgUserPwdChk) === "") {
			alert("<spring:message code='result-message.messages.pwd.message.required.chgpwdcheck'/>") // 비밀번호 확인이 누락되었습니다. 입력해주세요.
			$("#chgUserPwdChk").focus();
			return false;
		}
		// 비밀번호 동일 체크
		if ($.trim(chgUserPwd) !== $.trim(chgUserPwdChk)) {
			alert("<spring:message code='result-message.messages.pwd.message.nomatch.chgpwd.chgpwdcheck'/>") // 비밀번호가 일치하지 않습니다.
			return false;
		}
		// 비밀번호 패턴 체크
		const pattern = RegExp("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$");
		if (!pattern.test($.trim(chgUserPwd))) {
			alert("<spring:message code='result-message.messages.join.message.pwd.pattern.error'/>") // 비밀번호는 8~20자의 영문, 숫자, 특수문자 중 3가지 이상의 문자를 조합하여 사용할 수 있습니다.
			return false;
		}

		return true;
	}

	/**
	 * 비밀번호 재설정
	 */
	function fn_chgUserPwd() {
		if (fn_valid()) {
			$.blockUI();

			$.ajax({
				url : "/auth/chgUserPwd",
				type : "POST",
				dataType : "json",
				data : $("#resetPwdFrm").serialize(),
				success : function(result) {
					$.unblockUI();

					if (result.resultCode === 200) {
						alert(result.resultMessage);
						location.replace("/"); // 주소 히스토리 남지 않음!
					} else {
						alert(result.resultMessage);
					}
				}
			});
		}
	}
</script>

<!-- ===== header ====== -->
<header id="header">
	<div id="sub-mv" class="sub-mem">
		<div class="inner">
			<h2>로그인</h2>
			<div class="sub-obj">오브젝트</div>
		</div>
	</div>
</header>
<!-- ===== /header ====== -->

<!-- ===== container ====== -->
<div id="container">

	<!--container-->
	<div id="cont" class="cont-login">
		<form name="resetPwdFrm" id="resetPwdFrm">
			<input type="hidden" name="userTyCd" id="userTyCd" value="${user.userTyCd}" />
			<input type="hidden" name="targetUserId" id="targetUserId" value="${user.targetUserId}" />

			<h2 class="tit">비밀번호 재설정</h2>
			<h3 class="tit">
				본인 인증이 완료되었습니다.<br>새로운 비밀번호를 등록하여 주시기 바랍니다.
			</h3>

			<article class="info-rect tBr change-wr">
				<ul>
					<li><label>새 비밀번호</label> <input type="password"
						name="chgUserPwd" id="chgUserPwd" /></li>
				</ul>
				<p class="cau clr-black">※ 영문, 숫자, 특수문자 중 3가지 이상의 문자를 조합하여 최소 8 ~ 20자 이하로 설정</p>
			</article>

			<article class="info-rect change-wr">
				<ul>
					<li><label>새 비밀번호 확인</label> <input type="password"
						name="chgUserPwdChk" id="chgUserPwdChk" /></li>
				</ul>
			</article>

			<div class="btn-wr">
				<a href="javascript:void(0)" onclick="fn_chgUserPwd()" class="btn"
					id="change-pop">완료</a>
			</div>
		</form>
	</div>
</div>
<!-- ===== /container ====== -->
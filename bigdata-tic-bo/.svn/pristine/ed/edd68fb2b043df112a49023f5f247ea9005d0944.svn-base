<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script>

	/*
	 * 로그인 함수
	 */
	function fn_login() {

		let count = 0;

		/* ------ 1. 유효성검사 */
		// 1-1. 아이디 빈값
		if (fn_emptyCheck($("#loginId").val())) {
			alert("<spring:message code='result-message.messages.login.message.required.loginid.data'/>");
			count++;
			return;
		}

		// 1-2. 비밀번호 빈값
		if (fn_emptyCheck($("#loginPwd").val())) {
			alert("<spring:message code='result-message.messages.login.message.required.loginpwd.data'/>");
			count++;
			return;
		}

		/* ------ 2. 로그인 처리 */
		if (count == 0) {

			$.blockUI();

			$.ajax({
				url	 		: "/login/processLogin"
				, type 		: "POST"
				, dataType 	: "json"
				, data 		: $("#frm_login").serialize()
				, success 	: function(result) {
					$.unblockUI();

					if (result.resultCode === 200 ) {
						location.href = result.data.firstMenuUrlAddr;
					} else {
						alert(result.resultMessage);
					}
				}
			});

		}
	}

</script>

	<p class="txCloud">LOGIN</p>
	<div class="formBox">
		<form id="frm_login" name="frm_login" method="post" onSubmit="return false;">

			<input type="text" 		class="input" placeholder="아이디" 		id="loginId"  name="loginId" />
			<input type="password" 	class="input" placeholder="비밀번호" 	id="loginPwd" name="loginPwd" />

			<button class="btLogin" style="background-color:#535c6d; cursor:pointer;" onclick="fn_login();">로그인</button>
		</form>
	</div>
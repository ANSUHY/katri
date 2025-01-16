<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">

	$(document).ready(function() {

	});

	/*
	 * 함수명 : fn_mailSend4();
	 * 설   명 : 메일 보내기(개인 회원가입 완료 시 발송)
	 */
	function fn_mailSend4() {

		/* ======= 1. 메일 보내기 */
		$.blockUI();
		$.ajax({
			url	 		: "/test/mailSendComp"
			, type 		: "POST"
			, dataType 	: "json"
			, data 		: {}
			, success 	: function(result) {
				$.unblockUI();

				alert(result.resultMessage);
			}
		});

	}

	/*
	 * 함수명 : fn_mailSend3();
	 * 설   명 : 메일 보내기([SERVICE1 에서 DB업데이트]  +  [SERVICE2 에서 메일 예약 전송(6분후)])
	 */
	function fn_mailSend3() {

		/* ======= 1. 메일 보내기 */
		$.blockUI();
		$.ajax({
			url	 		: "/test/mailSend3"
			, type 		: "POST"
			, dataType 	: "json"
			, data 		: {}
			, success 	: function(result) {
				$.unblockUI();

				alert(result.resultMessage);
			}
		});

	}


</script>

<div id="container">
	<div class="titArea">
		<div class="location">
			<span class="ic-home"><i></i></span><em>test페이지</em>
		</div>
	</div>

	<h2 class="pop-tit2">test페이지</h2>


	<a href="javascript:void(0);" onclick="fn_mailSend4();" class="btn">메일 보내기4_개인 회원가입 완료 시 발송</a>
	<a href="javascript:void(0);" onclick="fn_mailSend3();" class="btn">메일 보내기3_6분후메일</a>

</div><!-- /container -->
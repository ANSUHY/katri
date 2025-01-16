<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">

	$(document).ready(function() {

	});

	/*
	 * 함수명 : fn_mailSend1();
	 * 설   명 : 메일 보내기([한 SERVICE에서 DB업데이트 + 메일 전송 ])
	 */
	function fn_mailSend1() {

		/* ======= 1. 메일 보내기 */
		$.blockUI();
		$.ajax({
			url	 		: "/test/mailSend1"
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
	 * 함수명 : fn_mailSend2();
	 * 설   명 : 메일 보내기([SERVICE1 에서 DB업데이트]  +  [SERVICE2 에서 메일 전송])
	 */
	function fn_mailSend2() {

		/* ======= 1. 메일 보내기 */
		$.blockUI();
		$.ajax({
			url	 		: "/test/mailSend2"
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
	 * 함수명 : fn_mailSend5();
	 * 설   명 : 메일 보내기(1:1 문의 등록 발송)
	 */
	function fn_mailSend5() {

		/* ======= 1. 메일 보내기 */
		$.blockUI();
		$.ajax({
			url	 		: "/test/mailSendInquiry"
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

<!-- ===== header ====== -->
<header id="header">
	<div id="sub-mv" class="sub-mem">
		<div class="inner">
			<h2>TEST</h2>
			<div class="sub-obj">TEST</div>
		</div>
	</div>
</header>
<!-- ===== /header ====== -->

<!-- ===== container ====== -->
<div id="container">

	<!--container-->
	<div id="cont" class="cont-login">
		<section class="login-wr">

			testtesttesttest

		</section>

		<a href="javascript:void(0);" onclick="fn_mailSend1();" class="btn">메일 보내기1</a>
		<a href="javascript:void(0);" onclick="fn_mailSend2();" class="btn">메일 보내기2</a>
		<a href="javascript:void(0);" onclick="fn_mailSend3();" class="btn">메일 보내기3_6분후메일</a>

		<a href="javascript:void(0);" onclick="fn_mailSend4();" class="btn">메일 보내기4_개인 회원가입 완료 시 발송</a>
		<a href="javascript:void(0);" onclick="fn_mailSend5();" class="btn">메일 보내기5_1:1 문의 등록 발송</a>

		<a href="/test/testChart" class="btn">차트 가기</a>
	</div>
</div>
<!-- ===== /container ====== -->
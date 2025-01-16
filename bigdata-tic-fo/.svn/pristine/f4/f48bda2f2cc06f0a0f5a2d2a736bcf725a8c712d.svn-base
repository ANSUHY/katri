<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" 		uri="/WEB-INF/tlds" %>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

<script type="text/javascript">

	$(document).ready(function() {

		/* 초기 작업 */
		fn_popTemrmiSvcInit();

	});

	/*
	 * 함수명 : fn_popTemrmiSvcInit()
	 * 설	 명 : 초기 작업
	 */
	function fn_popTemrmiSvcInit() {


	}

	/*
	 * 함수명 : fn_popTemrmiSaveSvcAppl()
	 * 설   명 : [[내 손안의 시험인증 서비스 해지하기]] 눌렀을때 서비스 해지하기
	 */
	function fn_popTemrmiSaveSvcAppl(){

		$.blockUI();
		// ==========데이터 전송====================
		$.ajax({

			url 		: "/platformSvc/myData/temrmiSvcAppl"
			, method 	: "POST"
			, dataType 	: "json"
			, async 	: true
			, data 		: {"clctAgreSbmsnCoRlsInstId" : $("#targetPopTemrmiSvcApplInstId").val() }
			, success 	: function(result) {
				$.unblockUI();
				if (result.resultCode === 200) {

					alert("<spring:message code='result-message.messages.my.data.message.temrmi.svc' />"); // '내손안의 시험인증 서비스가 해지되었습니다.'
					/* 새로고침 */
					location.reload();

				} else {
					alert(result.resultMessage);
				}
			}
		});

	}

</script>

<!-- ===== 서비스해지 modal ====== -->
<div class="svc-apl-end-md" id="divPopTemrmiSvcAppl">

	<input type="hidden" name="targetPopTemrmiSvcApplInstId" 	id="targetPopTemrmiSvcApplInstId" 		value=""/>

	<div class="md-rect">
		<a href="javascript:void(0);" class="btn-close">닫기</a>
		<h3 class="md-tit">서비스 해지</h3>
		<div class="end-wr">
			<h4 class="tit">해지 전에 꼭 확인해주세요!</h4>
			<p>“<span id="areaPopTemrmiSvcInstNm"></span>＂서비스 해지하시면 기존에 <br class="mo-block">동의 하셨던 시험인증<br class="pc-block">
				정보가 모두 철회합니다.<br>
				서비스 해지 후에는 기존에 수집되었던 <br class="mo-block">해당 기관의<br class="pc-block">
				시험인증정보 이용이 제한됩니다</p>
		</div>
		<div class="btn-wr"><a href="javascript:void(0);" onclick="fn_popTemrmiSaveSvcAppl()" class="btn md01">내 손안의 시험인증 서비스 해지하기</a></div>
	</div>
</div>
<!--// ===== 서비스해지 modal ====== -->


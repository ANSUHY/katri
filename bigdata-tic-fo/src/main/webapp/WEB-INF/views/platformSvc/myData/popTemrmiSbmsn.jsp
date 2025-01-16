<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" 		uri="/WEB-INF/tlds" %>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

<script type="text/javascript">

	$(document).ready(function() {

		/* 초기 작업 */
		fn_popTemrmiSbInit();

	});

	/*
	 * 함수명 : fn_popTemrmiSbInit()
	 * 설	 명 : 초기 작업
	 */
	function fn_popTemrmiSbInit() {


	}

	 /*
	 * 함수명 : fn_popTemrmiSaveSb()
	 * 설   명 : [[제출처 제공 해지하기]] 눌렀을때 제출처 제공 해지하기
	 */
	function fn_popTemrmiSaveSb(){

		$.blockUI();
		// ==========데이터 전송====================
		$.ajax({

			url 		: "/platformSvc/myData/temrmiSbmsn"
			, method 	: "POST"
			, dataType 	: "json"
			, async 	: true
			, data 		: {"sbmsnCoRlsInstId" : $("#targetPopTemrmiSbInstId").val() }
			, success 	: function(result) {
				$.unblockUI();
				if (result.resultCode === 200) {

					alert("<spring:message code='result-message.messages.my.data.message.temrmi.sbmsn' />"); // '제출처 제공 서비스가 해지되었습니다.'
					/* 새로고침 */
					location.reload();

				} else {
					alert(result.resultMessage);
				}
			}
		});

	}

</script>

<!-- ===== 제출처 해지 modal ====== -->
<div class="svc-apl-end-md01" id="divPopTemrmiSb">

	<input type="hidden" name="targetPopTemrmiSbInstId" 	id="targetPopTemrmiSbInstId" 		value=""/>

	<div class="md-rect">
		<a href="javascript:void(0);" class="btn-close">닫기</a>
		<h3 class="md-tit">제출처 제공 해지</h3>
		<div class="end-wr">
			<h4 class="tit">해지 전에 꼭 확인해주세요!</h4>
			<p>“<span id="areaPopTemrmiSbInstNm"></span>＂제출처 데이터 제공 해지하시면 기존에<br class="pc-block">
				동의하셨던 정보가 모두 철회합니다.<br>
				서비스 해지 후에는 기존에 수집되었던 <br class="mo-block">해당 기관의<br class="pc-block">
				정보 이용이 제한됩니다.</p>
		</div>
		<div class="btn-wr"><a href="javascript:void(0);" onclick="fn_popTemrmiSaveSb()" class="btn md01">제출처 제공 해지하기</a></div>
	</div>
</div>
<!--// ===== 제출처 해지 modal ====== -->


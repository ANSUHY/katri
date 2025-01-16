<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" 		uri="/WEB-INF/tlds" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<script type="text/javascript">

	$(document).ready(function() {

		/* 초기 작업 */
		fn_popSbInit();

	});

	/*
	 * 함수명 : fn_popSbInit()
	 * 설   명 : 초기 작업
	 */
	function fn_popSbInit() {

		// ==========[제3자 제공 동의] 체크====================
		fn_popSbChkOfferCheck();

		/* [제3자 제공 동의] 의 체크여부에 따라 [제출처 제공 대상기관 선택] 보이고 안보이고*/
		$(document).on("click", "#frm_popSbReg input[id='chkPopSbOfferTerms_c_offer']", function(){
			fn_popSbChkOfferCheck();
		});

	}

	 /*
	 * 함수명 : fn_popSbChkOfferCheck()
	 * 설   명 : [제3자 제공 동의]에 따른 설정
	 */
	function fn_popSbChkOfferCheck(){

		if( $("input[id='chkPopSbOfferTerms_c_offer']").is(":checked") ){ //체크되어있으면 밑에 [제출처 제공 대상기관 선택] 제출처 보이기
			$("#titlePopSbSbmsnCoRls").show();
			$("#ulPopSbSbmsnCoRls").show();
		} else {
			$("#titlePopSbSbmsnCoRls").hide();
			$("#ulPopSbSbmsnCoRls").hide();
		}
	}

	/*
	 * 함수명 : fn_popSbSaveSvcAppl()
	 * 설   명 : [[확인]] 눌렀을때 서비스 신청하기
	 */
	function fn_popSbSaveSvcAppl(){

		// ==========체크된게 없으면 그냥 닫음====================
		if( ! $("#chkPopSbOfferTerms_c_offer").is(":checked") || $("input[id^='chkPopSbSbmsnCoRls_c_']:checked").length === 0){
			$('.svc-submit-md01').removeClass('active');
			return;
		}

		$.blockUI();
		// ==========데이터 전송====================
		$.ajax({

			url 		: "/platformSvc/myData/saveSbmsn"
			, method 	: "POST"
			, dataType 	: "json"
			, async 	: true
			, data 		: $("#frm_popSbReg").serialize()
			, success 	: function(result) {
				$.unblockUI();
				if (result.resultCode === 200) {
					/* 새로고침 */
					location.reload();
				} else {
					alert(result.resultMessage);
				}
			}
		});

	}

</script>

<form method="post" name="frm_popSbReg" id="frm_popSbReg">

	<c:set var="img_T001"	value="/asset/images/svc-ofc-01.png" />
	<c:set var="img_T003"	value="/asset/images/svc-ofc-02.png" />
	<c:set var="img_T005"	value="/asset/images/svc-ofc-03.png" />
	<c:set var="img_T006"	value="/asset/images/svc-ofc-04.png" />
	<c:set var="img_T007"	value="/asset/images/svc-ofc-05.png" />
	<c:set var="img_T002"	value="/asset/images/svc-ofc-06.png" />
	<c:set var="img_T004"	value="/asset/images/svc-ofc-07.png" />

	<c:set var="img_src"	value="" />

	<!-- ===== 제3자 제공 동의 modal ====== -->
	<div class="svc-submit-md01" id="divPopSbSbmsnCoRls">
		<div class="md-rect">
			<a href="javascript:void(0);" class="btn-close">닫기</a>

			<h3 class="md-tit">제3자 제공 동의</h3>

			<div class="svc-wr">
				<ul class="chk-ul">
					<li class="agree-f">
						<label for="chkPopSbOfferTerms_c_offer">
							<input type="checkbox" id="chkPopSbOfferTerms_c_offer" name="chkOfferTemrsSn" value="${termThirdOffer.trmsSn}"/>
							<span>"시험인증 데이터 제3자(제출처) 제공 약관" 약관에 동의합니다. <strong>(선택)</strong></span>
						</label>
<!-- 						<i></i> -->
					</li>
					<li class="agree-c">
						<div style="overflow-y: auto; height:200px; ">
							${termThirdOffer.trmsCnUnescape}
						</div>
					</li>
				</ul>
				<h3 class="md-tit" id="titlePopSbSbmsnCoRls" style="display: none;">제출처 제공 대상기관 선택</h3>
				<ul class="office-chk-ul" id="ulPopSbSbmsnCoRls" style="display: none;">

					<c:forEach items="${svcConnInstList}" var="item">

						<c:if test="${item.sbmsnCoRlsAgreYn != 'Y'}">
							<!-- img주소 셋팅 -->
							<c:choose>
								<c:when test="${item.instId == 'T001'}">
									<c:set var="img_src"	value="${img_T001}" />
								</c:when>
								<c:when test="${item.instId == 'T002'}">
									<c:set var="img_src"	value="${img_T002}" />
								</c:when>
								<c:when test="${item.instId == 'T003'}">
									<c:set var="img_src"	value="${img_T003}" />
								</c:when>
								<c:when test="${item.instId == 'T004'}">
									<c:set var="img_src"	value="${img_T004}" />
								</c:when>
								<c:when test="${item.instId == 'T005'}">
									<c:set var="img_src"	value="${img_T005}" />
								</c:when>
								<c:when test="${item.instId == 'T006'}">
									<c:set var="img_src"	value="${img_T006}" />
								</c:when>
								<c:when test="${item.instId == 'T007'}">
									<c:set var="img_src"	value="${img_T007}" />
								</c:when>
							</c:choose>
							<!--// img주소 셋팅 -->

							<li>
								<label for="chkPopSbSbmsnCoRls_c_${item.instId}">
									<input type="checkbox" id="chkPopSbSbmsnCoRls_c_${item.instId}"	name="arrChkSbmsnCoRlsInstId"	value="${item.instId}"/>
									<div class="img"><img src="${img_src}" alt="${item.instNm}"></div>
									<span>${item.instNm}</span>
								</label>
							</li>
						</c:if>

					</c:forEach>

				</ul>

				<div class="btn-wr"><a href="javascript:void(0);" onclick="fn_popSbSaveSvcAppl()" id="btnPopSbSaveSvcAppl" class="btn md01">확인</a></div>

			</div>

		</div>
	</div>
	<!--// ===== 제3자 제공 동의 modal ====== -->

</form>



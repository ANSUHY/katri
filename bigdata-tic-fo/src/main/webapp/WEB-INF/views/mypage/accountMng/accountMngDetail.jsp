<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="com.katri.common.Const" %>
<c:set var="ROW_COUNT" value="<%=Const.Paging.ROW_COUNT%>" />

<c:set var="USER_TYPE_INST_MASTER" 	value="<%=Const.Code.UserTyCd.INST_MASTER%>" />
<c:set var="USER_TYPE_ENT_MASTER" 	value="<%=Const.Code.UserTyCd.ENT_MASTER%>" />

<c:set var="USER_STT_JOIN_APPLY" 		value="<%=Const.Code.UserSttCd.JOIN_APPLY%>" />
<c:set var="USER_STT_JOIN_REJECT" 		value="<%=Const.Code.UserSttCd.JOIN_REJECT%>" />
<c:set var="USER_STT_NORMAL" 			value="<%=Const.Code.UserSttCd.NORMAL%>" />
<c:set var="USER_STT_DRMNCY" 			value="<%=Const.Code.UserSttCd.DRMNCY%>" />
<c:set var="USER_STT_WITHDRAWAL_APPLY" 	value="<%=Const.Code.UserSttCd.WITHDRAWAL_APPLY%>" />
<c:set var="USER_STT_WITHDRAWAL" 		value="<%=Const.Code.UserSttCd.WITHDRAWAL%>" />

<style>
	/* Mobile인 경우 */
	@media screen and (max-width:1440px){
		.mypage-acc { display: none; }
	}
</style>

<script type="text/javascript">

	$(document).ready(function() {

	});

	/*
	 * 함수명 : fn_accountMngList
	 * 설   명 : 계정 목록 페이지 이동
	 */
	function fn_accountMngList() {
		$("#frm_list").attr("method", "GET");
		$("#frm_list").attr("action", "/mypage/accountMng/accountMngList");
		$("#frm_list").submit();
	}

	/*
	 * 함수명 : fn_joinApprInit
	 * 설   명 : 계정 가입 승인/반려 처리 초기 작업
	 */
	function fn_joinApprInit( apprFlag ) {

		let msg = "";

		// 0. 메시지 구분
		if( apprFlag == 'Y' ){
			msg = "<spring:message code='result-message.messages.mypage.account.mng.message.join.approval.confirm'/>"; // '회원가입을 승인 하시겠습니까?'
		} else {
			msg = "<spring:message code='result-message.messages.mypage.account.mng.message.join.reject.confirm'/>"; // '회원가입을 반려 하시겠습니까?'
		}

		// 1. 확인 시, 승인/반려 처리 함수 호출
		if( confirm( msg ) ){
			fn_joinAppr( apprFlag );
		}
	}

	/*
	 * 함수명 : fn_joinAppr
	 * 설   명 : 계정 가입 승인/반려 처리
	 */
	function fn_joinAppr( apprFlag ) {

		// 0. 값 셋팅
		$.blockUI();

		let form = $("#frm_appr")[0];
		let jData = new FormData(form);

		// 1. 승인/반려 여부 셋팅
		jData.append("apprFlag", apprFlag);

		// 2. 처리 시작
		$.ajax({
			url				: "/mypage/accountMng/saveJoinAppr"
			, type			: "POST"
			, cache			: false
			, data			: jData
			, processData 	: false
			, contentType	: false
			, success 		: function(result) {
				$.unblockUI();

				if (result.resultCode == "200") {
					// 3. 저장 성공 후
					fn_joinApprAfter(result);

				} else {
					alert(result.resultMessage);
				}
			}
		});

	}

	/*
	 * 함수명 : fn_joinApprAfter
	 * 설   명 : 계정 가입 승인/반려 처리 성공 후
	 */
	function fn_joinApprAfter( result ) {

		// 0. 메시지 노출
		alert( result.resultMessage );

		// 1. [가입 승인] 시,
		if( result.data.apprFlag == "Y" ) {
			// 1_0. 페이지 새로고침
			location.reload();
		}
		// 2. [가입 반려] 시,
		else {
			// 2_0. 목록으로 이동
			fn_accountMngList();
		}

	}

	/*
	 * 함수명 : fn_whdwlPrcs
	 * 설   명 : 계정 탈퇴 처리
	 */
	function fn_whdwlPrcs() {

		// 0. 확인 후 탈퇴 처리
		if( confirm( "<spring:message code='result-message.messages.mypage.account.mng.message.whdwl.prcs.confirm'/>" ) ){ // '회원탈퇴 처리 하시겠습니까?'

			// 0. 값 셋팅
			$.blockUI();

			let form = $("#frm_appr")[0];
			let jData = new FormData(form);

			// 1. 처리 시작
			$.ajax({
				url				: "/mypage/accountMng/saveWhdwlPrcs"
				, type			: "POST"
				, cache			: false
				, data			: jData
				, processData 	: false
				, contentType	: false
				, success 		: function(result) {
					$.unblockUI();

					if (result.resultCode == "200") {
						// 2. 저장 성공 후
						fn_whdwlPrcsAfter(result);

					} else {
						alert(result.resultMessage);
					}
				}
			});
		}
	}

	/*
	 * 함수명 : fn_whdwlPrcsAfter
	 * 설   명 : 계정 탈퇴 처리 성공 후
	 */
	function fn_whdwlPrcsAfter( result ) {

		// 0. 메시지 노출
		alert( result.resultMessage );

		// 1. 계정 관리 목록 이동
		fn_accountMngList();
	}

</script>

	<!-- ===== header ====== -->
	<header id="header">
		<div id="sub-mv" class="sub-mypage">
			<div class="inner">
				<h2>마이페이지</h2>
				<div class="sub-obj">오브젝트</div>
			</div>
		</div>
	</header>
	<!-- ===== /header ====== -->

	<!-- ===== container ====== -->
	<form id="frm_list" name="frm_list">
		<input type="hidden" id="currPage"			name="currPage"			value="${accountSearchData.currPage}" />
		<input type="hidden" id="chgPage"			name="chgPage"			value="${accountSearchData.chgPage}" />
		<input type="hidden" id="rowCount"			name="rowCount"			value="${ROW_COUNT * accountSearchData.chgPage}" />
		<input type="hidden" id="searchKeyword"		name="searchKeyword"	value="${accountSearchData.searchKeyword}" />
		<input type="hidden" id="searchUserSttCd"	name="searchUserSttCd"	value="${accountSearchData.searchUserSttCd}" />
		<input type="hidden" id="accountUserId" 	name="accountUserId"	value="${accountSearchData.accountUserId}" />
	</form>

	<form id="frm_appr" name="frm_appr" method="post">
		<input type="hidden" id="targetUserId"	name="targetUserId"	value="${accountUserData.userId}" />
	</form>

	<div id="container">
		<!--container-->
		<div id="cont" class="cont-mypage mypage-account dtl-account"><!--상세페이지 구분 클래스 추가-->

			<h2 class="tit">회원정보 상세</h2>

			<!--리스트-->
			<div class="mypage-acc">
				<div class="table">
				<div class="tr">
					<div class="th">회원유형</div>
					<div class="td">${accountUserData.userTyNm}</div>
				</div>

				<div class="tr">
					<div class="th">아이디</div>
					<div class="td">
						<cm:stringFormat type="masking" format="id" value="${accountUserData.userId}" />
					</div>
				</div>

				<div class="tr">
					<div class="th">이름</div>
					<div class="td">
						<c:choose>
							<c:when test="${not empty accountUserData.userNm}">
								${accountUserData.userNm}
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</div>
				</div>

				<div class="tr">
					<div class="th">휴대폰</div>
					<div class="td">
						<c:choose>
							<c:when test="${not empty accountUserData.encptMblTelnoVal}">
								<cm:stringFormat type="phone" value="${accountUserData.encptMblTelnoVal}" />
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<c:choose>
					<%-- 기관 마스터 --%>
					<c:when test="${userInfoData.userTyCd eq USER_TYPE_INST_MASTER}">
						<div class="tr">
							<div class="th">회사 이메일</div>
							<div class="td">
								<c:choose>
									<c:when test="${not empty accountUserData.encptEmlAddrVal}">
										${accountUserData.encptEmlAddrVal}
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
							</div>
						</div>

						<div class="tr">
							<div class="th">기관명</div>
							<div class="td">
								${accountUserData.instNm}
							</div>
						</div>

						<div class="tr">
							<div class="th">부서명</div>
							<div class="td">
								<c:choose>
									<c:when test="${not empty accountUserData.userDeptNm }">
										${accountUserData.userDeptNm}
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
							</div>
						</div>

						<div class="tr">
							<div class="th">회사 전화번호</div>
							<div class="td">
								<c:choose>
									<c:when test="${not empty accountUserData.wrcTelno}">
										<cm:stringFormat type="phone" value="${accountUserData.wrcTelno}" />
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</c:when>

					<%-- 기업 마스터 --%>
					<c:when test="${userInfoData.userTyCd eq USER_TYPE_ENT_MASTER}">

						<div class="tr">
							<div class="th">회사 이메일</div>
							<div class="td">
								<c:choose>
									<c:when test="${not empty accountUserData.encptEmlAddrVal}">
										${accountUserData.encptEmlAddrVal}
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
							</div>
						</div>

						<div class="tr">
							<div class="th">회사명</div>
							<div class="td">
								${accountUserData.entNm}(${accountUserData.entGrpNm})
							</div>
						</div>

						<div class="tr">
							<div class="th">부서명</div>
							<div class="td">
								<c:choose>
									<c:when test="${not empty accountUserData.userDeptNm}">
										${accountUserData.userDeptNm}
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
							</div>
						</div>

						<div class="tr">
							<div class="th">직급</div>
							<div class="td">
								<c:choose>
									<c:when test="${not empty accountUserData.userJbgdNm}">
										${accountUserData.userJbgdNm}
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
							</div>
						</div>

						<div class="tr">
							<div class="th">회사 전화번호</div>
							<div class="td">
								<c:choose>
									<c:when test="${not empty accountUserData.wrcTelno}">
										<cm:stringFormat type="phone" value="${accountUserData.wrcTelno}" />
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
							</div>
						</div>

						<div class="tr">
							<div class="th">근무지 주소</div>
							<div class="td">
								<c:choose>
									<c:when test="${not empty accountUserData.wrcBasAddr}">
										${accountUserData.wrcBasAddr}
										<cm:stringFormat type="masking" format="address" value="${accountUserData.wrcDaddr}" />
										<%--
											상세주소 숫자만 마스킹 처리할 시,
											<cm:stringFormat type="masking" format="address" value="${accountUserData.wrcDaddr}" />
										--%>
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
							</div>
						</div>

						<div class="tr">
							<div class="th">관심 키워드1</div>
							<div class="td">
								<c:choose>
									<c:when test="${not empty accountUserData.lstPrdt }">
										<c:choose>
											<c:when test="${not empty accountUserData.stdLgclfCd1 }">
												${accountUserData.stdLgclfNm1}
												(
												<c:forEach items="${accountUserData.lstPrdt}" var="item" varStatus="status">
													<c:if test="${item.stdLgclfCd eq accountUserData.stdLgclfCd1}">
														<c:set var="stdLgcLength1" value="${stdLgcLength1+1}"/>
													</c:if>
												</c:forEach>
												<c:forEach items="${accountUserData.lstPrdt}" var="item" varStatus="status">
													<c:if test="${item.stdLgclfCd eq accountUserData.stdLgclfCd1}">
														<c:set var="comCnt1" value="${comCnt1+1}"/>
														${item.stdMlclfNm}<c:if test="${comCnt1 < stdLgcLength1}">,</c:if>
													</c:if>
												</c:forEach>
												)
											</c:when>
											<c:otherwise>
												-
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
							</div>
						</div>

						<div class="tr">
							<div class="th">관심 키워드2</div>
							<div class="td">
								<c:choose>
									<c:when test="${not empty accountUserData.lstPrdt }">
										<c:choose>
											<c:when test="${not empty accountUserData.stdLgclfCd2 }">
												${accountUserData.stdLgclfNm2}
												(
												<c:forEach items="${accountUserData.lstPrdt}" var="item" varStatus="status">
													<c:if test="${item.stdLgclfCd eq accountUserData.stdLgclfCd2}">
														<c:set var="stdLgcLength2" value="${stdLgcLength2+1}"/>
													</c:if>
												</c:forEach>
												<c:forEach items="${accountUserData.lstPrdt}" var="item" varStatus="status">
													<c:if test="${item.stdLgclfCd eq accountUserData.stdLgclfCd2}">
														<c:set var="comCnt2" value="${comCnt2+1}"/>
														${item.stdMlclfNm}<c:if test="${comCnt2 < stdLgcLength2}">,</c:if>
													</c:if>
												</c:forEach>
												)
											</c:when>
											<c:otherwise>
												-
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
							</div>
						</div>

					</c:when>
				</c:choose>

				<div class="tr">
					<div class="th">최근 로그인 기록</div>
					<div class="td">
						<c:choose>
							<c:when test="${not empty accountUserData.lastLgnDt }">
								${accountUserData.lastLgnDt}
							</c:when>
							<c:otherwise>
								-
						</c:otherwise>
						</c:choose>
					</div>
				</div>

				<div class="tr">
					<div class="th">상태</div>

					<c:choose>
						<c:when test="${accountUserData.userSttCd eq USER_STT_JOIN_APPLY}">
							<div class="td type01">
								${accountUserData.userSttNm}
							</div>
						</c:when>
						<c:when test="${accountUserData.userSttCd eq USER_STT_NORMAL}">
							<div class="td type02">
								${accountUserData.userSttNm}
							</div>
						</c:when>
						<c:when test="${accountUserData.userSttCd eq USER_STT_DRMNCY}">
							<div class="td type03">
								${accountUserData.userSttNm}
							</div>
						</c:when>
						<c:otherwise>
							<div class="td type04">
								${accountUserData.userSttNm}
							</div>
						</c:otherwise>
					</c:choose>
				</div>

				</div>
			</div>
			<div class="btn-wr">

				<c:choose>
					<c:when test="${accountUserData.userSttCd eq USER_STT_JOIN_APPLY}">
						<a href="javascript:void(0);" class="btn cancel" onclick="fn_joinApprInit('Y');">가입 승인</a>
						<a href="javascript:void(0);" class="btn cancel" onclick="fn_joinApprInit('N');">가입 반려</a>
					</c:when>
					<c:when test="${accountUserData.userSttCd eq USER_STT_NORMAL or accountUserData.userSttCd eq USER_STT_DRMNCY}">
						<a href="javascript:void(0);" class="btn cancel" onclick="fn_whdwlPrcs();">탈퇴 처리</a>
					</c:when>
				</c:choose>

				<a href="javascript:void(0);" class="btn" onclick="fn_accountMngList();">목록</a>

			</div>
		</div>

		<!-- ===== 모바일접근금지 modal ====== -->
		<div class="rounge-mobile-err-md active">
			<div class="md-rect">
				<!--a href="javascript:" class="btn-close">닫기</a-->
				<h3 class="md-tit">안내</h3>
				<div class="end-wr">
					<div class="img"><img src="../../asset/images/ic-error-m.svg" alt="접근금지"/></div>
					<p>해당 메뉴는 콘텐츠 특성 상<br />모바일 버전을 제공하지 않습니다.<br />PC에서 접속해 주세요.</p>
				</div>
			</div>
		</div>

	</div>
	<!-- ===== /container ====== -->
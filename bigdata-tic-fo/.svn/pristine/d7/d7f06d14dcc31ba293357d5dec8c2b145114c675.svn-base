<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

<%@ page import="com.katri.common.Const" %>
<c:set var="USER_TYPE_ENT_MASTER" 	value="<%=Const.Code.UserTyCd.ENT_MASTER%>" />

<script type="text/javascript">

	$(document).ready(function() {

	});

	/*
	 * 함수명 : fn_userWhdwlConfirm
	 * 설 명 : 회원 탈퇴 확인
	*/
	function fn_userWhdwlConfirm() {

		if( "${userInfoData.userTyCd}" == "${USER_TYPE_ENT_MASTER}" ) {
			fn_getEntMasterGrpUserChk();
		} else {
			if( confirm("<spring:message code='result-message.messages.mypage.info.mng.message.whdwl.confirm'/>") ) { // 회원 탈퇴 시 모든 개인정보가 삭제됩니다. 정말로 회원 탈퇴하시겠습니까?
				fn_userWhdwl();
			}
		}

	}

	/*
	 * 함수명 : fn_getEntMasterGrpUserChk
	 * 설 명 : 기업(마스터) 종속되어 있는 그룹 회원 확인
	*/
	function fn_getEntMasterGrpUserChk() {

		$.blockUI();

		let form = $("#frm_userWhdwl")[0];
		let jData = new FormData(form);

		$.ajax({
			url	 		: "/mypage/infoMng/getEntMasterGrpUserChk"
			, type			: "POST"
			, cache			: false
			, data			: jData
			, processData 	: false
			, contentType	: false
			, success 		: function(result) {

				$.unblockUI();

				if (result.resultCode == "200") {
					if( result.data < 1 ){
						if( confirm(result.resultMessage.replaceAll("\\n" , "\\\n").replaceAll("\\", "")) ) { // 회원 탈퇴 시 모든 개인정보가 삭제됩니다. 정말로 회원 탈퇴하시겠습니까?
							fn_userWhdwl();
						}
					}
				} else {
					alert(result.resultMessage.replaceAll("\\n" , "\\\n").replaceAll("\\", "")); // // 종속된 회원이 있어 탈퇴 신청이 불가합니다.
				}
			}
		});
	}

	/*
	 * 함수명 : fn_userWhdwl
	 * 설 명 : 회원 탈퇴
	*/
	function fn_userWhdwl() {

		// 1. 저장 시작
		$.blockUI();

		let form = $("#frm_userWhdwl")[0];
		let jData = new FormData(form);

		$.ajax({
			url				: "/mypage/infoMng/saveUserWhdwl"
			, type			: "POST"
			, cache			: false
			, data			: jData
			, processData 	: false
			, contentType	: false
			, success 		: function(result) {
				$.unblockUI();

				if (result.resultCode == "200") {
					// 2. 저장 성공 메시지 후, 탈퇴(신청) 페이지 이동
					fn_userWhdwlAfter( result );

				} else {
					alert(result.resultMessage);
				}
			}
		});
	}

	/*
	 * 함수명 : fn_userWhdwlAfter
	 * 설   명 : 탈퇴 처리 후 페이지 이동
	 */
	function fn_userWhdwlAfter( result ) {

		alert(result.resultMessage);

		$("#frm_userWhdwl").attr("method", "POST");
		$("#frm_userWhdwl").attr("action", "/mypage/infoMng/infoMngWhdwlCmptn");
		$("#frm_userWhdwl").submit();
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
	<div id="container">
		<form id="frm_userWhdwl" name="frm_userWhdwl" method="post">
			<input type="hidden" name="targetUserId" 	id="targetUserId" 	value="${userInfoData.userId}" />
			<input type="hidden" name="targetUserTyCd" 	id="targetUserTyCd" value="${userInfoData.userTyCd}" />
		</form>

		<!--container-->
			<c:choose>
				<c:when test="${userInfoData.userTyCd eq USER_TYPE_ENT_MASTER }">
					<div id="cont" class="cont-mypage mypage-out">
						<h3 class="tit">시험인증 빅데이터 플랫폼을 이용해 주셔서 감사합니다.<em>정말 탈퇴하시겠습니까?</em></h3>
						<div class="info-rect tBr">
							<p class="stit">회원 탈퇴 시</p>
							<ul>
								<li>아이디, 휴대폰번호는 부정이용 가입 방지를 위해 탈퇴 처리 시 기존 아이디로 재가입이 불가합니다.	</li>
								<li>이용하시는 동안 등록된 게시물은 즉시 삭제됩니다.</li>
								<li>고객님의 모든 개인 정보와 관련 1:1문의, 이용 내역이 모두 삭제되며, 추후 복구가 불가능합니다.</li>
								<li>시험인증 빅데이터 플랫폼 관리자의 검토 후 탈퇴 승인이 됩니다.</li>
							</ul>
						</div>
						<div class="out-txt">위의 사항을 모두 확인하셨습니까<br>탈퇴하기 버튼을 눌러주세요.</div>
						<div class="btn-wr">
							<a href="javascript:void(0);" class="btn" onclick="fn_userWhdwlConfirm();">탈퇴하기</a>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div id="cont" class="cont-mypage mypage-out">
						<h3 class="tit">시험인증 빅데이터 플랫폼을 이용해 주셔서 감사합니다.<em>정말 탈퇴하시겠습니까?</em></h3>
						<div class="info-rect tBr">
							<p class="stit">회원 탈퇴 시</p>
							<ul>
								<li>아이디, 휴대폰번호는 부정이용 가입 방지를 위해 탈퇴 처리 시 기존 아이디로 재가입이 불가합니다.	</li>
								<li>이용하시는 동안 등록된 게시물은 즉시 삭제됩니다.</li>
								<li>고객님의 모든 개인 정보와 관련 1:1문의, 이용 내역이 모두 삭제되며, 추후 복구가 불가능합니다.</li>
							</ul>
						</div>
						<div class="out-txt">위의 사항을 모두 확인하셨습니까<br>탈퇴하기 버튼을 눌러주세요.</div>
						<div class="btn-wr">
							<a href="javascript:void(0);" class="btn" onclick="fn_userWhdwlConfirm();">탈퇴하기</a>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
	</div>
	<!-- ===== /container ====== -->
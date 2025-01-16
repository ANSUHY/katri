<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script>

	$(function(){

	});

</script>

<!--모바일만 노출-->
<div class="hd-wr">
	<h1><a href="/">로고</a></h1>
	<div class="tnb">
		<ul>
			<c:choose>
				<c:when test="${sessionScope.SS_KATRI_FO == null}">
					<li><a href="/auth/login" class="ic login">로그인</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="/auth/logout" class="ic logout">로그아웃</a></li>
				</c:otherwise>
			</c:choose>

			<li><a href="/mypage/infoMng/infoMngPwdChk" class="ic mypage">마이페이지</a></li><!--마이페이지 알림 시 mypage에 on추가-->
		</ul>
		<button type="button" class="btn-rnb-close">닫기</button>
	</div>
</div>
<div class="inner">

	<!-- [플랫폼 서비스] > [내 손안의 시험인증] URL 로그인한 사용자의 상태에 따라 달라짐 -->
	<c:set var="urlIntroSvc" value="/platformSvc/myData/introSvc"/>
	<c:if test="${sessionScope.SS_KATRI_FO.login_ent_grp_clct_agre_y_cnt != null && sessionScope.SS_KATRI_FO.login_ent_grp_clct_agre_y_cnt > 0}">
		<c:set var="urlIntroSvc" value="/platformSvc/myData/myInfoCert/myCertList"/>
	</c:if>

	<!--pc만 노출-->
	<div class="btn-wr"><button type="button" class="btn-rnb-close">닫기</button></div>
	<ul class="snb">

		<li class="menu"><a href="javascript:void(0);">데이터 활용안내</a>
			<ul class="sub">
				<li class="smenu"><a href="/dataUsageGuide/certDataList">인증데이터 조회</a></li>
			</ul>
		</li>
		<li class="menu"><a href="javascript:void(0);">플랫폼 서비스</a>
			<ul class="sub">
				<li class="smenu"><a href="${urlIntroSvc}">내 손안의 시험인증 ( Varotic )</a></li>
				<li class="smenu"><a href="/platformSvc/qrSvc/qrSvcGuide">인증정보 QR서비스 ( Certishot )</a></li>
			</ul>
		</li>
		<li class="menu"><a href="javascript:void(0);">참여기관 라운지</a>
			<ul class="sub">
				<li class="smenu"><a href="/particiLounge/dataGatherStatus">데이터 수집현황</a></li>
				<li class="smenu"><a href="javascript:void(0);" onclick="fn_goMetaSite()">메타데이터 관리시스템</a></li>
				<li class="smenu"><a href="/particiLounge/dataVisualEnvi">데이터 시각화 환경</a></li>
				<li class="smenu"><a href="/particiLounge/dataAnalyEnvi">데이터 분석 환경</a></li>
			</ul>
		</li>
		<li class="menu"><a href="javascript:void(0);">플랫폼 소개</a>
			<ul class="sub">
				<li class="smenu"><a href="/platformInfo/testCertBicDataInfo">시험인증 빅데이터 플랫폼 소개</a></li>
				<li class="smenu"><a href="/platformInfo/instiParticiInfo">참여기관 소개</a></li>
				<li class="smenu"><a href="/platformInfo/testCertIndustryInfo;">시험인증 산업 소개</a></li>
			</ul>
		</li>
		<li class="menu"><a href="javascript:void(0);">이용안내</a>
			<ul class="sub">
				<li class="smenu"><a href="/ctnt/notice/noticeList">공지사항</a></li>
				<li class="smenu"><a href="/ctnt/faq/faqList">FAQ</a></li>
				<li class="smenu"><a href="/ctnt/archive/archiveList">자료실</a></li>
			</ul>
		</li>
	</ul>
</div>
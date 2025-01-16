<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

<%@ page import="com.katri.common.Const" %>
<c:set var="USER_TYPE_ENT_MASTER" 	value="<%=Const.Code.UserTyCd.ENT_MASTER%>" />

<script type="text/javascript">

	$(document).ready(function() {

	});

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
		<c:choose>

			<c:when test="${userInfoData.targetUserTyCd eq USER_TYPE_ENT_MASTER }">
				<!--container-->
				<div id="cont" class="cont-mypage mypage-out">
					<h3 class="tit">탈퇴 신청이 완료되었습니다.</h3>
					<div class="info-rect tBr mypage-find">
						<p>회원님의 계정은 비활성화되었으며 탈퇴 완료 시 영구 삭제될 예정입니다.</p>
						<p>그동안 시험인증 빅데이터 플랫폼을 이용해 주셔서 감사합니다.</p>
						<p>보다 나은 서비스로 다시 찾아 뵙겠습니다.</p>
					</div>
					<div class="btn-wr">
					  <a href="/" class="btn">메인으로 이동</a>
					</div>
				</div>
			</c:when>

			<c:otherwise>
				<!--container-->
				<div id="cont" class="cont-mypage mypage-out">
					<h3 class="tit">회원탈퇴가 완료되었습니다.</h3>
					<div class="info-rect tBr mypage-find">
						<p>그동안 시험인증 빅데이터 플랫폼을 이용해 주셔서 감사합니다.<br>보다 나은 서비스로 다시 찾아 뵙겠습니다.</p>
					</div>
					<div class="btn-wr">
						<a href="/" class="btn">메인으로 이동</a>
					</div>
				</div>
			</c:otherwise>

		</c:choose>

	</div>
	<!-- ===== /container ====== -->
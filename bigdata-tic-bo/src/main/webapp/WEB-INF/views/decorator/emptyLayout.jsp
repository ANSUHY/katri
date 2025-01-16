<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">

	<head>

		<jsp:include page="../layout/topLayout.jsp" />

		<script>
			$( document ).ready(function() {
				$('body').addClass('bgColor');
			});
		</script>

	</head>

	<body>
		<!-- ===== wrap ====== -->
		<div class="loginWrap">
			<h1 class="logo" style="color:#000; font-weight:500; padding:20px 0px;">
				<span style="font-weight:700;">시험인증</span> 빅데이터 포탈 관리자
			</h1>

			<!-- container -->
				<sitemesh:write property='body'/>
			<!-- /container -->

			<div class="txCopy">Copyright ⓒ KATRi. All rights reserved.</div>

		</div>
		<!-- ===== /wrap ====== -->
	</body>
</html>
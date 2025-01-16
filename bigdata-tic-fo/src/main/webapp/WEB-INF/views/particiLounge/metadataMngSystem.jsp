<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" uri="/WEB-INF/tlds" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- ===== header ====== -->
<header id="header">
	<div id="sub-mv" class="sub-rounge">
		<div class="inner">
			<h2>참여기관 라운지</h2>
			<div class="sub-obj">오브젝트</div>
		</div>
	</div>
</header>
<!-- ===== /header ====== -->

<!-- ===== container ====== -->
<div id="container">
	<!--breadcrumb-wr-->
	<jsp:include page="/WEB-INF/views/layout/breadcrumbWr.jsp">
		<jsp:param name="menuTopNo" value="03"/>
		<jsp:param name="menuSubNo" value="0302"/>
	</jsp:include>
	<!-- // breadcrumb-wr-->
</div>
<!-- ===== /container ====== -->
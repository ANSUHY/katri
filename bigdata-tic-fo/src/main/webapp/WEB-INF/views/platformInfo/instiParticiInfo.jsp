<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" 		uri="/WEB-INF/tlds" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<script type="text/javascript">

	$(document).ready(function() {
	});

</script>

<!-- ===== header ====== -->
<header id="header">
	<div id="sub-mv" class="sub-platform">
		<div class="inner">
			<h2>플랫폼 소개</h2>
			<div class="sub-obj">오브젝트</div>
		</div>
	</div>
</header>
<!-- ===== /header ====== -->

<!-- ===== container ====== -->
<div id="container">

	<!--breadcrumb-wr-->
	<jsp:include page="/WEB-INF/views/layout/breadcrumbWr.jsp">
		<jsp:param name="menuTopNo" value="04"/>
		<jsp:param name="menuSubNo" value="0402"/>
	</jsp:include>
	<!-- // breadcrumb-wr-->

	<!--container-->
	<div id="cont" class="cont-platform">

		<!--tit-->
		<div class="cont-platform-tit01">
			<h2 class="tit">참여기관 소개</h2>
		</div>
		<!--// tit-->

		${ platformInfoData.menuCptnCnUnescape }

	</div>
	<!--// container-->

</div>
<!-- ===== /container ====== -->

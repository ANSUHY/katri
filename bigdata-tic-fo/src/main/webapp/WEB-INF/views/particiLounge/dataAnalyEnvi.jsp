<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" 		uri="/WEB-INF/tlds" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<script type="text/javascript">

	$(document).ready(function() {

		/*
			AAAAAAAAAAASH 남은거
			- 메뉴얼 다운로드시 진짜 파일, 파일명

		*/

	});

	/*
	 * 함수명 : fn_downloadAnalyMenuFile()
	 * 설   명 : 파일 다운로드_DB에 없는거
	*/
	function fn_downloadAnalyMenuFile() {
		fn_pageFileDownload('da_f_upload_test_taeubahk.docx' , '데이터 분석환경 메뉴얼.docx');
	}

</script>

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
		<jsp:param name="menuSubNo" value="0304"/>
	</jsp:include>
	<!-- // breadcrumb-wr-->

	<!--container-->
	<div id="cont" class="cont-rounge">

		<!--tit-->
		<div class="cont-platform-tit">
			<h2 class="tit">데이터 분석 환경</h2>
		</div>
		<!--// tit-->

		${ particiLoungeData.menuCptnCnUnescape }

	</div>
	<!--// container-->

</div>
<!-- ===== /container ====== -->

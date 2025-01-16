<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" 		uri="/WEB-INF/tlds" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<%@ page import="com.katri.common.Const" %>
<c:set var="USER_TYPE_CD_ENT_MASTER" value="<%=Const.Code.UserTyCd.ENT_MASTER%>" />

<script type="text/javascript">

	$(document).ready(function() {

		/* 서비스 신청 버튼 눌렀을때 Pop띄우기*/
		$(document).on("click", "#btnCallPopSvc", function(){
			$('#divPopSvcClctAgre').addClass('active');
		});

	});


</script>

<!-- ===== header ====== -->
<header id="header">
	<div id="sub-mv" class="sub-myservice">
		<div class="inner">
			<h2>플랫폼 서비스</h2>
			<div class="sub-obj">오브젝트</div>
		</div>
	</div>
</header>
<!-- ===== /header ====== -->

<!-- ===== container ====== -->
<div id="container">

	<!--breadcrumb-wr-->
	<jsp:include page="/WEB-INF/views/layout/breadcrumbWr.jsp">
		<jsp:param name="menuTopNo" value="02"/>
		<jsp:param name="menuSubNo" value="0201"/>
	</jsp:include>
	<!-- // breadcrumb-wr-->

	<div id="cont" class="cont-myservice">

		<!--tit-->
		<div class="cont-platform-tit bMg100">
			<h2 class="tit">내 손안의 시험인증 서비스 안내</h2>
		</div>
		<!--// tit-->

		${ introSvcData.menuCptnCnUnescape }

		<!-- 로그인한 사용자가 [기업마스터] 일때만 버튼 보이기 && 기업그룹수집동의이력 최종값이 Y인것이 0 개일때-->
		<c:if test="${sessionScope.SS_KATRI_FO.login_user_ty_cd != null && sessionScope.SS_KATRI_FO.login_user_ty_cd eq  USER_TYPE_CD_ENT_MASTER && (sessionScope.SS_KATRI_FO.login_ent_grp_clct_agre_y_cnt == null || sessionScope.SS_KATRI_FO.login_ent_grp_clct_agre_y_cnt == 0)}">

			<div class="btn-wr">
				<a href="javascript:void(0);" class="btn" id="btnCallPopSvc">서비스 신청</a>
			</div>

			<jsp:include page="/WEB-INF/views/platformSvc/myData/popSvcAppl.jsp"/>

		</c:if>
		<!--// 로그인한 사용자가 [기업마스터] 일때만 버튼 보이기 && 기업그룹수집동의이력 최종값이 Y인것이 0 개일때 -->

	</div>
	<!--// container-->

</div>
<!-- ===== /container ====== -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags" %>
<!-- getRemoteAddr : <%=request.getRemoteAddr()%> -->

<%@ page import="com.katri.common.Const" %>
<c:set var="USER_TYPE_CD_INST_MASTER" 	value="<%=Const.Code.UserTyCd.INST_MASTER%>" />
<c:set var="USER_TYPE_CD_INST_GENERAL" 	value="<%=Const.Code.UserTyCd.INST_GENERAL%>" />

	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="format-detection" content="email=no">
	<title>시험인증 빅데이터 플랫폼</title>
	<meta name="description" content="">
	<meta name="keywords" content="">
	
	<link rel="favicon" href="/favicon.ico">

	<script type="text/javascript">
		var UI_NAT_CD = "KR"; // 국가코드
		var UI_LANG_CD = "ko"; // 언어코드
		var UI_MOBILE_YN = "N"; // 모바일 여부
	</script>

	<link rel="stylesheet" href="<c:url value='/asset/css/style.css'/>">
	<link rel="stylesheet" href="<c:url value='/asset/css/swiper.min.css'/>">
	<link rel="stylesheet" href="<c:url value='/asset/css/jquery-ui.css'/>">
	<!-- select2 -->
	<link rel="stylesheet" href="<c:url value='/asset/css/select2.min.css'/>">

	<script type="text/javascript" src="<c:url value='/asset/js/jquery.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/asset/js/jquery-ui.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/asset/js/jquery.blockUI.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/asset/js/jquery.tmpl.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/asset/js/jquery.mask.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/asset/js/moment.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/asset/js/jquery.fileDownload.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/asset/js/jquery.serializeObject.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/asset/js/swiper.min.js'/>"></script>

	<!-- Util -->
	<script type="text/javascript" src="<c:url value='/asset/js/util/commonUtil.js?version=20211119'/>"></script>
	<script type="text/javascript" src="<c:url value='/asset/js/util/autoNumeric.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/asset/js/util/grid.js?version=1.3'/>"></script>
	<script type="text/javascript" src="<c:url value='/asset/js/util/validate.js'/>"></script>

	<script type="text/javascript" src="<c:url value='/asset/js/common.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/asset/js/custom.js'/>"></script>

	<!-- select2 -->
	<script type="text/javascript" src="<c:url value='/asset/js/select2.min.js'/>"></script>

	<style>

		/* select2 마우스오버 색 설정 */
		.select2-results__option:hover {
			background-color:#f1f1f1;
			color:#333;
			outline:none;
		}

	</style>

	<script>

		/*
		 * 함수명 : fn_pageFileDownload
		 * 설   명 : DB에 없는 파일 다운로드(파일은 실제로 넣어줘야함)
		 */
		function fn_pageFileDownload(strgFileNm, orgnlFileNm){

			$("#frm_fileDown").remove();

			//동적폼생성
			let form = $('<form id="frm_fileDown" name="frm_fileDown"></form>');
			form.attr('action','/file/pageFileDownload');
			form.attr('method','post');
			form.appendTo('body');

			let inputStrgFileNm 	= $("<input type='hidden' name='strgFileNm' 	value='" + strgFileNm + "' />");
			let inputOrgnlFileNm 	= $("<input type='hidden' name='orgnlFileNm'	value='" + orgnlFileNm + "' />");
			form.append(inputStrgFileNm);
			form.append(inputOrgnlFileNm);

			form.submit();

		}

		/*
		 * 함수명 : fn_goMetaSite()
		 * 설   명 : [메타데이터 관리시스템] 새창 열기
		 */
		function fn_goMetaSite(){

			$("#frm_goMetaSite").remove();

			/* ========== 로그인한 사용자가 [기관마스터, 기관일반]일때만 새창열림 */
			let loginUserTyCd = "${sessionScope.SS_KATRI_FO.login_user_ty_cd}";
			if( (!fn_emptyCheck(loginUserTyCd)) && (loginUserTyCd == "${USER_TYPE_CD_INST_MASTER}" || loginUserTyCd == "${USER_TYPE_CD_INST_GENERAL}") ){

				//동적폼생성
				let form = $('<form id="frm_goMetaSite" name="frm_goMetaSite"></form>');
				form.attr('target',	'_blank');
				form.attr("method", "GET");
				form.attr("action", "<c:url value='https://meta.bigdata-tic.kr:8088'/>");
				form.appendTo('body');

				let inputFromPortal	= $("<input type='hidden' name='fromPortal' value='Y'/>");
				form.append(inputFromPortal);

				form.submit();

			} else if( fn_emptyCheck("${sessionScope.SS_KATRI_FO}") ) { /* ========== 로그인된 사용자가 아닐때 */
				alert("로그인이 필요합니다.");
				location.href = "/auth/login";

			} else { /* ========== 권한없을때 */
				alert("<spring:message code='result-message.messages.login.message.not.auth'/>"); //해당 아이디에 권한이 부여되어 있지 않습니다. 관리자에게 문의하여 주십시요.

			}

		}

		/*
		 * 함수명 : fn_readyAlert
		 * 설   명 : 준비중입니다 알럿
		 */
		function fn_readyAlert(){
			alert("준비중입니다");
		}

	</script>



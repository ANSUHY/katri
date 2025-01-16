<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="com.katri.common.Const" %>
<c:set var="EQP_TYPE_CD_MOBILE" 	value="<%=Const.Code.EquipmentTypeCd.MOBILE%>" />
<c:set var="EQP_TYPE_CD_PC" 		value="<%=Const.Code.EquipmentTypeCd.PC%>" />

<script>

	$(function(){

		//사용자로그이력 쌓기
		fn_addUserLogHist();

		$("#data-flag").val('');

		/* 이용약관, 개인정보처리방침 레이어팝업 */
		$('.user-guide').click(function(){
			 $(this).addClass('agree');
	         $('.user-guide-md').addClass('active');

	         let value = $(this).attr("data-key");
	         fn_openKatriInfo(value);
	      });

	      $('.user-guide-md .md-rect > .btn-close').click(function(){
	         $('.user-guide-md').removeClass('active');
	         $('.user-guide').removeClass('agree');
	      });

		// main만 하단 banner 노출
		fn_mainBannerShow();
	});

	/*
	 * 함수명 : fn_addUserLogHist
	 * 설   명 : 사용자로그이력 저장
	 */
	function fn_addUserLogHist() {

		/* ------ 1. 데이터 셋팅*/

		// 1-1. 가이드
		let strCookieEqmtCntnId = fn_getCookie("eqmtCntnId");
		let strCookieSsnCntnId = fn_getCookie("ssnCntnId");
		if ( strCookieEqmtCntnId == "" ) {
			strCookieEqmtCntnId = fn_returnGuid();
			fn_setCookie ( "eqmtCntnId", strCookieEqmtCntnId );
		}
		if ( strCookieSsnCntnId == "" ) {
			strCookieSsnCntnId = fn_returnGuid();
			fn_setCookie ( "ssnCntnId", strCookieSsnCntnId, 365 );
		}

		// 1-2. 모바일 디바이스 여부
		var strDevicInfo	= ( fn_checkMobileDevice() ) ? "${EQP_TYPE_CD_MOBILE}" : "${EQP_TYPE_CD_PC}";

		// 1-3. 모바일 디바이스명
		var strDeviceNm		= fn_mobileDeviceNm();

		// 1-4. 브라우저명
		var userAgent		= navigator.userAgent.toLowerCase();
		var browser;
		if(userAgent.indexOf('edge')>-1){
			browser='edge';
		}else if(userAgent.indexOf('whale')>-1){
			browser='whale';
		}else if(userAgent.indexOf('chrome')>-1){
			browser='chrome';
		}else if(userAgent.indexOf('firefox')>-1){
			browser='firefox';
		}else{
			browser='IE';
		}

		/* ------ 2. 데이터 저장*/
		$.ajax({
			url				: "/comm/addUserLogHist"
			, type			: "POST"
			, data			: {
								"eqmtCntnId"	: strCookieEqmtCntnId
								,"ssnCntnId"	: strCookieSsnCntnId
								,"eqmtTyCd"		: strDevicInfo
								,"operSysmNm"	: strDeviceNm
								,"userAgntVal"	: navigator.userAgent.toLowerCase()
								,"brwsrNm"		: browser
								,"logUrlAddr"	: window.location.pathname
								,"logUrlPrmtrCn": window.location.search
								,"bfrUrlAddr"	: document.referrer
			}
			, success 		: function(strData, textStatus, jqXHR){

			}
			, error 		: function(jqXHR, textStatus, errorThrown){
				console.log(jqXHR)
			}
		});
	}

	/*
	 * 함수명 : fn_openKatriInfo
	 * 설 명 : 푸터 (이용약관/개인정보처리방침) 클릭 -> 모달창
	*/

	function fn_openKatriInfo(code){
		 if($("#data-flag").val() == code) {
			 $('.user-guide-md').addClass('active');
		 } else {
			 $(".md-tit").text("");
			 $("#layout-policy-area").html("");
			 $.ajax({
				 url		: "/comm/terms/getKatriTerms"
			   , type		: "POST"
			   , dataType	: "json"
			   , data		: {"trmsTyCd" : code }
			   , success	: function(jsonData) {
					//jsonData.data.trmsTyCd === 'TTC001' ? $(".md-tit").text("이용약관") : $(".md-tit").text("개인정보처리방침");
					if (jsonData.data.trmsTyCd === 'TTC001')
						$(".md-tit").text("이용약관")
					else if (jsonData.data.trmsTyCd === 'TTC005')
						$(".md-tit").text("개인정보처리방침");
					else if (jsonData.data.trmsTyCd === 'TTC006')
						$(".md-tit").text("저작권 정책");
					$("#layout-policy-area").html(fn_convertXss(jsonData.data.trmsCn));

					$('.user-guide-md').addClass('active');
					$("#data-flag").val(code);

			   }
			 });
		 }
	 }


	 /*
		 * 함수명 : fn_goKatriFlatFormInfo
		 * 설 명 : 푸터 (플랫폼소개) 클릭 -> 페이지 이동
		*/

	 function fn_goKatriFlatFormInfo() {
		 $(location).attr('href', '/platformInfo/testCertBicDataInfo');
	 }


	/*
	 * 함수명 : fn_mainBannerShow
	 * 설 명 : 배너 -> 메인페이지만 노출
	*/
	function fn_mainBannerShow() {
		let urlPath = window.location.pathname;

		if( urlPath !== "/" ) {
			$(".ft-banner").remove();
		}
	}

</script>

<input type="hidden" id="data-flag"/>
 <!-- ===== 약관 modal ====== -->
 <div class="user-guide-md">
        <div class="md-rect">
            <a href="javascript:" class="btn-close">닫기</a>
            <h3 class="md-tit">이용약관</h3>
            <div class="guide-wr">
            	<div id="layout-policy-area"></div>
            </div>
        </div>
    </div>


<div class="ft-banner">
	<ul>
		<li><a href="https://www.katri.re.kr" target="_blank"><img src="/asset/images/ft-banner01.png" alt="KATRI"></a></li>
		<li><a href="https://www.ktl.re.kr" target="_blank"><img src="/asset/images/ft-banner04.png" alt="KTL"></a></li>
		<li><a href="http://www.ktr.or.kr" target="_blank"><img src="/asset/images/ft-banner05.png" alt="KTR"></a></li>
		<li><a href="https://www.kcledu.re.kr" target="_blank"><img src="/asset/images/ft-banner06.png" alt="KCL"></a></li>
		<li><a href="http://www.ktc.re.kr" target="_blank"><img src="/asset/images/ft-banner07.png" alt="KTC"></a></li>
		<li><a href="https://www.fiti.re.kr" target="_blank"><img src="/asset/images/ft-banner02.png" alt="FITI"></a></li>
		<li><a href="http://www.kotiti-global.com" target="_blank"><img src="/asset/images/ft-banner03.png" alt="KOTTI"></a></li>
	</ul>
</div>

<div class="inner">
	<div class="logo"><img src="/asset/images/ft-logo.png" alt="시험인증 빅데이터 플랫폼"></div>
	<div class="copyright">
		<ul> <!--  id="policy-area" -->
 			<li><a href="javascript:void(0);" onclick="fn_goKatriFlatFormInfo();">플랫폼 소개</a></li>
			<li><a href="javascript:void(0);" class="user-guide" data-key="TTC001">이용약관</a></li>
			<li><a href="javascript:void(0);" class="user-guide" data-key="TTC005">개인정보처리방침</a></li>
			<li><a href="javascript:void(0);" class="user-guide" data-key="TTC006">저작권 정책</a></li>
		</ul>
		<p>주소 : 서울특별시 동대문구 왕산로 51(용두동)  사업자등록번호 204-82-01330  TEL 02-3668-3000  FAX 02-3668-2900~1<br>COPYRIGHT 2022 KATRI. ALL RIGHTS RESERVED.</p>
	</div>
</div>
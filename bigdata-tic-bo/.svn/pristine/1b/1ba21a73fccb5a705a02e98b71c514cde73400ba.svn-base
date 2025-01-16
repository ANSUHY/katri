<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

	<style>

		/************* mainvisual swiper ****************/
		.swiper-container {
			width:650px;
			height:440px;
			border-radius:5px;
			box-shadow:0 0 20px #ccc inset;
		}
		.swiper-slide {
			text-align:center;
		}
		.swiper-slide img {
			border:1px solid #000;
			box-shadow:7px 7px 2px #ccc;
			width:100%;
			height:100%;
		}

		.swiper-button-next {
			width: 60px;
			height: 60px;
			margin: 0 0 0 1740px;
			padding: 15px 22px;
			border-radius: 30px;
			box-shadow: 0 10px 20px 0 rgba(0, 0, 0, 0.3);
			background-color: #fff;
		}

		.swiper-button-prev {
			width: 60px;
			height: 60px;
			margin: 0 1740px 0 0;
			padding: 15px 22px;
			border-radius: 30px;
			box-shadow: 0 10px 20px 0 rgba(0, 0, 0, 0.3);
			background-color: #fff;
		}

		.swiper-button-next::after,
		.swiper-button-prev::after {
			display: none;
		}

	</style>

	<script>

		$(document).ready(function() {

			// pc 탭 가장 먼저 보여주기
			fn_pcPopupShow();

		});

		/*
		 * 함수명 : fn_popupZoneSwiperSet
		 * 설   명 : swiper 설정
		 */
		function fn_popupZoneSwiperSet() {

			new Swiper('.swiper-container');

			let swiper = new Swiper('.swiper-container', {

				// 동적로딩 설정
				lazy : {
					loadPrevNext : true // 이전, 다음 이미지는 미리 로딩
				},

				// 페이징 설정
				pagination : {
					el : '.swiper-pagination',
					clickable : true, // 페이징을 클릭하면 해당 영역으로 이동, 필요시 지정해 줘야 기능 작동
				},

				// 네비게이션 설정
				navigation : {
					nextEl : '.swiper-button-next', // 다음 버튼 클래스명
					prevEl : '.swiper-button-prev', // 이번 버튼 클래스명
				},
			});

		}

		/*
		 * 함수명 : fn_pcPopupShow
		 * 설   명 : PC 비주얼 목록 조회
		 */
		function fn_pcPopupShow() {

			// 0. PC 탭 클래스 부여
			$("#tabPc").addClass('on');
			$("#tabMobile").removeClass('on');

			// 2. PC 비주얼 목록 조회
			fn_popupZonePreviewList('pc');

		}

		/*
		 * 함수명 : fn_mobilePopupShow
		 * 설   명 : Mobile 비주얼 목록 조회
		 */
		function fn_mobilePopupShow() {

			// 0. Mobile 탭 클래스 부여
			$("#tabMobile").addClass('on');
			$("#tabPc").removeClass('on');

			// 2. Mobile 비주얼 목록 조회
			fn_popupZonePreviewList('mobile');

		}

		/*
		 * 함수명 : fn_popupZonePreviewList
		 * 설   명 : 사용중인 비주얼 목록 조회
		 */
		function fn_popupZonePreviewList( type ) {

			$.blockUI();

			// 1. 검색 조건 셋팅
			let searchUseYn			= "Y"; // 사용 중인 것만 show
			let searchHmpgCptnTyCd	= opener.document.getElementById('hmpgCptnTyCd').value;

			// 2. 비주얼 목록 조회
			$.ajax({
				url	 		: "/hmpgCptn/popupZone/getPopupList"
				, type 		: "GET"
				, dataType 	: "json"
				, data 		: {
									"searchUseYn" 			: searchUseYn
									, "searchHmpgCptnTyCd" 	: searchHmpgCptnTyCd
								}
				, success 	: function(jsonData, textStatus, jqXHR) {
					$.unblockUI();

					if(jsonData.resultCode === 200){

						// 3. 조회 > 데이터 출력
						fn_popupZonePreviewListHtml( jsonData.data, type );

					} else {
						alert(jsonData.resultMessage);
					}
				}
			});

		}

		/*
		 * 함수명 : fn_popupZonePreviewListHtml
		 * 설   명 : 사용중인 비주얼 목록 HTML 출력
		 */
		function fn_popupZonePreviewListHtml( data, type ) {

			let mainVisualHtml = "";

			// 1. 데이터 있는 경우
			if( data.totCnt > 0 ) {

				for( let nLoop=0; nLoop < data.list.length; nLoop++ ) {

					if( type == 'pc' ) {

						/* PC 이미지 */
						mainVisualHtml += "	<div class='swiper-slide'>";
						mainVisualHtml += "		<img data-src='" + data.list[nLoop].pcImgStrgFileFullPathAddr + "' class='swiper-lazy' onerror='opener.fn_imgErrorSet(this);' >";
						mainVisualHtml += "	</div>";

					} else {

						/* Mobile 이미지 */
						mainVisualHtml += "	<div class='swiper-slide'>";
						mainVisualHtml += "		<img data-src='" + data.list[nLoop].mobileImgStrgFileFullPathAddr + "' class='swiper-lazy' onerror='opener.fn_imgErrorSet(this);' >";
						mainVisualHtml += "	</div>";

					}
				}

			} else {
				mainVisualHtml += "<div class='swiper-slide'></div>"
			}

			/* 목록 HTML 출력 */
			$("#mainVisualPreviewList").html(mainVisualHtml);

			/* swiper 셋팅 */
			fn_popupZoneSwiperSet();

		}

	</script>

	<div id="pop-wrap">

		<h1 class="pop-tit">팝업존 미리보기</h1>

		<div class="pop-contnet">

			<div class="div-tab">
				<ul>
					<li id="tabPc" class="on">	<a href="javascript:void(0);" onclick="fn_pcPopupShow();">PC</a></li>
					<li id="tabMobile">			<a href="javascript:void(0);" onclick="fn_mobilePopupShow();">Mobile</a></li>
				</ul>
			</div>

			<!--  swiper-container -->
			<div class="swiper-container" style="margin-top: 30px;">
				<div class="swiper-wrapper" id="mainVisualPreviewList">
				</div>

				<!-- 네비게이션 버튼 -->
				<div class="swiper-button-next"></div><!-- 다음 버튼 (오른쪽에 있는 버튼) -->
				<div class="swiper-button-prev"></div><!-- 이전 버튼 -->

				<!-- 페이징 -->
				<div class="swiper-pagination"></div>
			</div>
			<!--  /swiper-container -->

		</div><!-- /pop-contnet -->

		<a href="javascript:self.close();" class="pop-close"><i class="fa fa-times"></i></a>

	</div><!-- /pop-wrao -->
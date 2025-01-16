<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" 		uri="/WEB-INF/tlds" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<%@ page import="com.katri.common.Const" %>
<c:set var="POST_TYPE_CD_FAQ" value="<%=Const.Code.PostTypeCd.FAQ%>" />
<c:set var="ROW_COUNT" value="<%=Const.Paging.ROW_COUNT%>" />

<script type="text/javascript">
	let isSearch = false; // 검색 구분

	$(function() {
		// 목록, 페이지 초기화
		$("#areaData").html("");

		// 페이지 초기화
		if ($("#currPage").val() === "0") {
			$("#currPage").val(1);
		}

		// 게시글 더보기
		fn_moreView();

		// 검색 결과 구분
		fn_searchResultDiv();

		// FAQ 목록 조회
		fn_getFaqList($("#currPage").val());
	});

	/**
	 * 게시글 더보기
	 */
	function fn_moreView() {
		/* 더보기 */
		$(".cont-board .btn-more").on("click", function () {
			// 페이지 증가
			const currPage = parseInt($("#currPage").val()) + 1;

			// FAQ 조회
			fn_getFaqList(currPage);
		});
	}

	/**
	 * 검색 결과 구분
	 */
	function fn_searchResultDiv() {
		/* 등록글이 없을 때 검색 결과가 없을 때 구분 */
		$("#searchBtn").on("click", function(e){
			e.preventDefault();

			// 검색
			fn_faqSearch();
		});

	}

	/**
	 * FAQ 답변 보기
	 */
	$(document).on("click", "li.li-q", function(key) {
		const dataType = $(this).attr("data-type"); // 현재 조회 여부 확인
		const dataKey = $(this).attr("data-key"); // 게시글 번호
		const listAnswer = $(this).next("li.li-a"); // FAQ 답변 영역

		if (dataType === "false") {
			$.ajax({
				  url: "/ctnt/faq/getFaqCn"
				, type: "GET"
				, dataType: "json"
				, data: {
					  nttSn : dataKey
					, nttTyCd : $("#nttTyCd").val()
				}
				, success: function(result) {
					if (result.resultCode === 200) {
						if (result.data === null) {
							alert(result.resultMessage);
						} else {
							// FAQ 답변 HTML 추가
							listAnswer.html(fn_convertXss(result.data.nttCn));
						}
					}
				}
			});
		}

		// false는 닫 -> 열, true는 열 -> 닫 (조회 구분)
		let chgDataType = dataType === "false" ? "true" : "false";
		$(this).attr("data-type", chgDataType);

		// 해당 게시글을 제외한 다른 게시글들은 모두 false
		$("li.li-q").not($(this)).attr("data-type", "false");

		$(this).next("li.li-a").stop().slideToggle(300);
		$(this).toggleClass('on').siblings().removeClass('on');
		$(this).next("li.li-a").siblings("li.li-a").slideUp(300); // 1개씩 펼치기
	});

	/**
	 * 엔터 검색 FAQ
	 */
	function fn_searchEnterFaq() {
		if (window.event.keyCode == 13) {
			fn_faqSearch(); // 검색
		}
	}

	/**
	 * 검색
	 */
	function fn_faqSearch() {
		isSearch = true;

		// 데이터 초기화 (기본값)
		$("#areaData").html("");

		// 검색
		fn_getFaqList(1);
	}

	/**
	 * FAQ 목록 조회
	 */
	function fn_getFaqList(page) {
		if(page == ''){
			page = 1;
		}

		// 현재 페이지
		$("#currPage").val(page);

		// 검색어가 존재하는 경우 '검색 결과가 없습니다'
		if ($.trim($("#searchKeyword").val()) !== "") {
			isSearch = true;
		}

		// 분류 변경 시 분류 값에 대한 검색을 위한
		$("#nttClfCd").attr("onchange", "fn_faqSearch()");

		$.blockUI();

		// 데이터 가져오기
		$.ajax({
			  url: "/ctnt/faq/getFaqList"
			, type: "GET"
			, dataType: "json"
			, data: $("#faqFrm").serialize()
			, success : function(jsonData, textStatus, jqXHR) {
				$.unblockUI();

				let html = "";

				const noData = "<spring:message code='result-message.messages.ctnt.message.faq.no.search'/>"; // 등록된 FAQ가 없습니다.
				const noResult = "<spring:message code='result-message.messages.ctnt.message.common.no.search'/>"; // 검색 결과가 없습니다.

				if (jsonData.resultCode === 200) {
						// 더보기 버튼 초기화
						$('.cont-board .btn-more').show();

						const bigdataList = "#board-list .board-tr:not(.active)"; // 위치
						const bigdataTotalCnt = jsonData.data.totCnt; // 총 길이
						const bigdataLength = jsonData.data.list.length; // 조회된 길이

					if (jsonData.data.list !== null && jsonData.data.list.length > 0) { // 데이터 O
						// 조회된 개수에 따라 더보기 버튼 숨기기
						if (Math.ceil(bigdataTotalCnt/${ROW_COUNT}) === parseInt($("#currPage").val())) {
							$('.cont-board .btn-more').hide();
						}

						// 1. FAQ 게시글 html 제작
						$.each(jsonData.data.list, function(index, data){
							html += fn_returnFaqListHtml(data);
						});

					} else { // 데이터 X
						$('.cont-board .btn-more').hide();

						html	=	"<li class='no-data'>"
								+ 		(isSearch ? noResult : noData)
								+	"</li>";

						isSearch = false;
					}

					// 2. FAQ 게시글 출력
					$("#areaData").append(html);

					// 3. 답변 열기 (메인에서 클릭했을 때)
					if($("#nttSn").val() != '') {
						fn_moveScroll($("#nttSn").val());
						$("#board-tr-" + $("#nttSn").val()).trigger("click");
						$("#nttSn").val('');
					}
				} else {
					alert(result.resultMessage);
				}
			}
		});
	}

	/**
	 * FAQ 게시글 html 제작
	 */
	function fn_returnFaqListHtml(data){
		let returnHtml = "";

		returnHtml += "<li class='li-q board-tr' data-type='false' data-key='" + data.nttSn + "' id='board-tr-" + data.nttSn  +"'><span>Q</span><em class='type'>";
		returnHtml += 	data.comnCdNm + "</em><p>" + data.nttSjNm + "</p>";
		returnHtml += "</li>";
		returnHtml += "<li class='li-a'>";
		returnHtml += "</li>";

		return returnHtml;
	}

	/**
	 * 공지사항 목록 이동
	 */
	function fn_goNoticeList() {
		location.href = "/ctnt/notice/noticeList";
	}

	/**
	 * 자료실 목록 이동
	 */
	function fn_goArchiveList() {
		location.href = "/ctnt/archive/archiveList";
	}

	/**
	 * 1:1 문의 작성 페이지 이동
	 */
	function fn_goInquiry() {
		location.href = "/mypage/inquiry/inquiryReg";
	}

	/**
	 * 스크롤 이동
	 */
	function fn_moveScroll(nttSn) {
		let ref = document.getElementById("board-tr-" + nttSn);
		ref.scrollIntoView({behavior: "smooth", block: "center"});
	}

</script>

<!-- ===== header ====== -->
<header id="header">
	<div id="sub-mv" class="sub-board">
		<div class="inner">
			<h2>이용안내</h2>
			<div class="sub-obj">오브젝트</div>
		</div>
	</div>
</header>
<!-- ===== /header ====== -->

<!-- ===== container ====== -->
<div id="container">

	<!--breadcrumb-wr-->
	<jsp:include page="/WEB-INF/views/layout/breadcrumbWr.jsp">
		<jsp:param name="menuTopNo" value="05"/>
		<jsp:param name="menuSubNo" value="0502"/>
	</jsp:include>
	<!-- // breadcrumb-wr-->

	<!--container-->
	<div id="cont" class="cont-board">
		<!--  조건 -->
		<form name="faqFrm" id="faqFrm">
			<input type="hidden" name="nttSn" id="nttSn" value="${faqSearchData.nttSn}">
			<input type="hidden" name="nttTyCd" id="nttTyCd" value="${POST_TYPE_CD_FAQ}" />
			<input type="hidden" name="currPage" id="currPage" value="${faqSearchData.currPage}">
			<input type="hidden" name="rowCount" id="rowCount" value="${ROW_COUNT}">

			<h2 class="tit">FAQ</h2>
			<!--board-->
			<div class="board-sch-wr">
				<cm:makeTag
					cls="select"
					tagType="select"
					tagId="nttClfCd"
					name="nttClfCd"
					code="PD_FAQ"
					defaultUseYn="Y"
					defaultNm="FAQ유형을 선택하세요"
					selVal="${faqSearchData.nttClfCd}"
					title="FAQ유형"
				/>
				<input type="text"  name="searchKeyword" id="searchKeyword" placeholder="검색어를 입력하세요." class="inp-sch" value="${faqSearchData.searchKeyword}" onkeypress="fn_searchEnterFaq()" title="검색">
				<button type="submit" id="searchBtn" class="btn">검색</button>
			</div>
		</form>
		<div class="board-cont" id="board-lst">
			<ul class="board-faq" id="areaData">

			</ul>
		</div>
		<div class="btn-wr">
			<a href="javascript:void(0)" class="btn cancel btn-more">더보기<i></i></a>
			<a href="javascript:void(0)" onclick="fn_goInquiry()" class="btn">1:1문의하기</a>
		</div>
		<!--//board-->
	</div>
</div>
<!-- ===== /container ====== -->
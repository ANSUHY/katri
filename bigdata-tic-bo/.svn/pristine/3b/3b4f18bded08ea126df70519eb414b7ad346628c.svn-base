<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page import="com.katri.common.Const" %>
<c:set var="POST_TYPE_CD_FAQ" value="<%=Const.Code.PostTypeCd.FAQ%>" />
<c:set var="ROW_COUNT" value="<%=Const.Paging.ROW_COUNT%>" />

<script type="text/javascript">
	$(function(){
		fn_faqListInit(); // 초기 작업
		fn_getFaqList($("#currPage").val()); // FAQ 목록 조회
	});

	/**
	 * 초기 작업
	 */
	function fn_faqListInit() {
		$(document).on("keydown", "#searchKeyword", function(key) {
			if(key.keyCode == 13)
				fn_getFaqList(1);
		});
	}

	/**
	 * FAQ 목록 조회
	 */
	function fn_getFaqList(page) {
		if(page == ''){
			page = 1;
		}

		// 목록, 페이지 초기화
		$("#areaData").html("");
		$("#areaPaging").html("");

		/* form setting */
		// 현재 페이지
		$("#currPage").val(page);

		// 검색
		const inputList = $(".search_list").find("input:text");

		for (let i = 0; i < inputList.length; i++) {
			let name = $(inputList[i]).attr("name");
			$("#" + name).val($(inputList[i]).val());
		}

		$.blockUI();

		// 데이터 가져오기
		$.ajax({
			  url: "/ctnt/faq/getFaqList"
			, type: "GET"
			, dataType: "json"
			, data: $("#faqFrm").serialize()
		})
		.done(function(jsonData, textStatus, jqXHR){
			$.unblockUI();

			let html = "";
			if (jsonData.resultCode === 200) {
				if (jsonData.data.list !== null && jsonData.data.list.length > 0) { // 데이터 O
					// 1. FAQ 게시글 html 제작
					$.each(jsonData.data.list, function(index, data){
						html += fn_returnFaqListHtml(data);
					});

					// 2. FAQ 게시글 html 출력
					fn_makePaging("#areaPaging", jsonData.data.totCnt, $("#currPage").val(), $("#rowCount").val(), "fn_goPage");
				} else { // 데이터 X
					html = "<tr><td class='center' colspan='4'>" + "<spring:message code='result-message.messages.common.message.no.data'/>" + "</td></tr>";
				}

				// 3. FAQ 게시글 출력
				$("#areaData").html(html);
			} else {
				alert(jsonData.resultMessage);
			}
		});
	}

	/**
	 * FAQ 게시글 html 제작
	 */
	function fn_returnFaqListHtml(data){
		let returnHtml = "";

		returnHtml += "<tr>";
		returnHtml += "	<td class='c'>" + data.rownum + "</td>";
		returnHtml += "	<td class='c'>" + data.comnCdNm + "</td>";
		returnHtml += "	<td class='l' onclick='fn_openFaqPopUp(" + data.nttSn + ");' style='cursor:pointer;'>" + fn_convertXss(unescapeHtml(data.nttSjNm)) + "</td>";
		returnHtml += "	<td class='c'>" + data.crtDt + "</td>";
		returnHtml += "</tr>";

		return returnHtml;
	}

	/**
	 * FAQ 팝업 (추가/수정)
	 */
	function fn_openFaqPopUp(nttSn) {
		$("#targetNttSn").val("");

		if (!fn_emptyCheck(nttSn)) {
			$("#targetNttSn").val(nttSn);
		}

		fn_openPop("/ctnt/faq/faqPopUp", "_blank", "900" ,"600", "Y");
	}

	/**
	 * 페이지 이동
	 */
	function fn_goPage(page) {
		if(page == null) {
			page = $("#currPage").val();
		}

		fn_getFaqList(page); // FAQ 목록 조회
	}

	/**
	 * html unescape 처리 (특수문자 복원)
	 */
	function unescapeHtml(text) {
		let doc = new DOMParser().parseFromString(text, "text/html");
		return doc.documentElement.textContent;
	}
</script>

	<div id="container">
		<!-- 검색조건 -->
		<form name="faqFrm" id="faqFrm">
			<input type="hidden" name="targetNttSn" id="targetNttSn" />
			<input type="hidden" name="nttTyCd" id="nttTyCd" value="${POST_TYPE_CD_FAQ}" />
			<input type="hidden" name="rowCount" id="rowCount" value="${ROW_COUNT}" />
			<input type="hidden" name="currPage" id="currPage" value="${faqSearchData.currPage}" /> <!-- 현재 페이지 -->
			<input type="hidden" name="searchKeyword" id="searchKeyword" value="${faqSearchData.searchKeyword}" /> <!-- 검색어 -->
		</form>

		<div class="search_list">
			<div class="titArea">
				<div class="location">
					<span class="ic-home"><i></i></span><span>콘텐츠 관리</span><em>FAQ 관리</em>
				</div>
			</div>

			<div class="btn-box bot r">
				<div class="left">
					<h2 class="pop-tit2 left" style="margin-top: 0px;">목록</h2>
				</div>
				<input type="text" name="searchKeyword" id="searchKeyword" value="${faqSearchData.searchKeyword}"/>
				<a href="javascript:void(0)" onclick="fn_getFaqList(1)" class="btn btn-blue">검색</a>
			</div>
		</div>

		<div class="row-tbl">
			<table>
				<colgroup>
					<col style="width: 100px;">
					<col style="width: 150px;">
					<col>
					<col style="width: 250px;">
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>분류</th>
						<th>제목</th>
						<th>등록일</th>
					</tr>
				</thead>
				<tbody id="areaData">
				</tbody>
			</table>
		</div>

		<div class="btn-box r">
			<a href="javascript:void(0)" onclick="fn_openFaqPopUp()" class="btn btn-blue">글 등록</a>
		</div>

		<div class="paginate_complex" id="areaPaging">
		</div>

	</div><!-- /container -->
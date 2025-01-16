<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags"%>
<%@ page import="com.katri.common.Const" %>
<c:set var="ROW_COUNT" value="<%=Const.Paging.ROW_COUNT%>" />
<c:set var="POST_TYPE_CD_INQUIRY" value="<%=Const.Code.PostTypeCd.INQUIRY%>" />

<style>
	.text-over-cut {
		display: block;
		width: 300px;
		white-space: nowrap;
		text-overflow: ellipsis;
		overflow: hidden;
		text-align: left;
	}

</style>
<script type="text/javascript">

	$(document).ready(function(){
		/* 목록 조회 */
		fn_getInqList($("#currPage").val());

		/* 셀렉트 박스 */
		$("#searchSelect").change(function(){
			//form에 데이터 셋팅
			$("#searchBoardType").val($("#searchSelect option:selected").val());
			$("#searchKeyword").val($("#inqKeyword").val());
			$("#isSearch").val("Y");

			/* List 조회 */
			fn_getInqList(1);
		})

		/* 초기 작업 */
		fn_inqListInIt();
	});

	/*
	 * 함수명	: fn_inqListInIt
	 * 설 명 : 엔터키 처리
	 */
	function fn_inqListInIt() {
		$(document).on("keydown", "#inqKeyword", function(key) {
			if(key.keyCode == 13)
				fn_searchInquiry();
		});
	 }

	/*
	 * 함수명	: fn_getInqList
	 * 설 명	: 1:1문의 목록 조회
	 */
	function fn_getInqList(p_page) {

		 if(p_page == '') {
			 p_page = 1;
		 }

		 //리스트, 페이징 초기화
		 $("#inqListArea").html("");
		 $("#areaPaging").html("");

		 //form에 데이터 셋팅
		 $("#currPage").val(p_page);
		 $("#searchKeyword").val($("#inqKeyword").val());

		$.ajax({
			url			: "/ctnt/inquiry/getInqList"
		  , type		: "GET"
		  , dataType	: "json"
		  , data		: $("#frm_inqList").serialize()
		  , success		: function(jsonData, textStatus, jqXHR) {

			  let html = '';
			  const noData = "<spring:message code='result-message.messages.common.message.no.data'/>";
			  const noSearch = "<spring:message code='result-message.messages.notice.message.no.data'/>"

			  if(jsonData.resultCode == 200){
				  if(jsonData.data.list !== null && jsonData.data.list.length > 0) {
					  //1.html 만들기
					  $.each(jsonData.data.list, function(index,data){
						  html += fn_makeInqListHtml(data);
					  })
					 //2. 페이징 만들기
					  fn_makePaging("#areaPaging", jsonData.data.totCnt, $("#currPage").val(), $("#rowCount").val(), "fn_goPage");
				  } else {
					  html += "<tr><td class='center' colspan='7'>";

					  if(!$("#isSearch").val("Y")){
						  html += noData;    //데이터 없을 경우
					  } else {
						  html += noSearch; //검색 결과 없을 경우
					  }
					  html += "</td></tr>";
				  }

			  } else {
				  alert(jsonData.resultMessage);
			  }
			  //3. 리스트 출력
			  $("#inqListArea").html(html);

			  //frm 셋팅 초기화
			  $("#isSearch").val("");

		  }
		});
	}

	/*
	 * 함수명	: fn_makeInqListHtml
	 * 설 명	: 1:1 문의 리스트 생성
	 */
	 function fn_makeInqListHtml(data) {
		let returnHtml = '';

		returnHtml += "<tr style='cursor:pointer' onclick='fn_goDetail("+ data.nttSn + "," + '"' + data.nttAnsYn  + '"'  + ")'>";
		returnHtml += 	"<td>" + data.rownum + "</td>";
		returnHtml +=	"<td>" + data.comnCdNm +"</td>";
		returnHtml += 	"<td>" + "<div class='text-over-cut'>" + data.nttCn + "</div>" + "</td>";

		/* 답변 여부 (Y/N) */
		if(data.nttAnsYn == "N") {
			//답변 대기
			returnHtml += "<td>대기</td>";
		} else {
			//답변 완료
			returnHtml += "<td>답변 완료</td>";
		}

		returnHtml += 	"<td style='padding: 5px;'>" + data.crtrId + "</td>";
		returnHtml += 	"<td>" + data.crtDt.substring(0,10) + "</td>";

		/* 답변 일자 (O/X) */
		if(data.nttAnsCrtDt != null) {
			//답변 일자 O
			returnHtml += "<td>" + data.nttAnsCrtDt.substring(0,10) + "</td>";
		} else {
			//답변 일자 X
			returnHtml += "<td></td>";
		}
		returnHtml += "</tr>";

		return returnHtml;
	}

	/*
	 * 함수명 : fn_goPage
	 * 설   명 : 페이지이동
	 */
	function fn_goPage(p_page) {
		if(p_page == null) {
			p_page = $("#currPage").val();
		}
		fn_getInqList(p_page);
	}

	/*
	 * 함수명	: fn_searchInquiry
	 * 설 명 : 검색어 서치
	 */
	function fn_searchInquiry() {
		//frm에 값 셋팅
		$("#searchKeyword").val($("#inqKeyword").val());
		$("#searchBoardType").val($("#searchSelect option:selected").val());
		$("#isSearch").val("Y");
		//함수 호출
		fn_getInqList(1);
	}

	/*
	 * 함수명	: fn_goDetail
	 * 설 명	: 상세 팝업 열기
	 */
	function fn_goDetail(num, ans_yn){
		$("#num").val(num);

		let url		= "/ctnt/inquiry/inquiryDetailPopUp";
		let name	= "1:1 문의 추가 및 수정"
		let width	= 950;
		let height	= 600;

		fn_openPop(url, name, width, height,"Y");
	}


</script>


	<div id="container">

		<div class="titArea">
			<div class="location">
				<span class="ic-home"><i></i></span><span>콘텐츠 관리</span><em>1:1 문의 관리</em>
			</div>
		</div>

	<form action="" method="get" name="frm_inqList" id="frm_inqList">
		<input type="hidden" name="rowCount" 			id="rowCount" 			value="${ROW_COUNT}"/>
		<input type="hidden" name="targetBoardNo" 		id="targetBoardNo" 		value=""/> 		<!-- 디테일갈경우 targetBoardNo -->
		<input type="hidden" name="currPage" 			id="currPage" 			value="${boardSearchData.currPage}"/> 	<!-- 페이지 no -->
		<input type="hidden" name="searchType" 			id="searchType" 		value="${boardSearchData.searchType}"/>		<!-- 검색조건 -->
		<input type="hidden" name="searchKeyword" 		id="searchKeyword" 		value="${boardSearchData.searchKeyword}"/>	<!-- 검색어 -->
		<input type="hidden" name="searchBoardType" 	id="searchBoardType" 	value="${boardSearchData.searchBoardType}"/><!-- 검색 BOARD TYPE -->
		<input type="hidden" name="nttCyCd"				id="nttCyCd"			value="${POST_TYPE_CD_INQUIRY}"/> <!-- ntt_cy_cd (INQUIRY) -->
		<input type="hidden" name="isSearch"			id="isSearch"			value=""/>
		<c:forEach items="${boardSearchData.searchArrBoardType}" var="item" varStatus="status">
			<input type="hidden" name="searchArrBoardType" 	id="searchArrBoardType_${status.index}" value="${item}"/>		<!-- 검색 BOARD TYPE ARR -->
		</c:forEach>
	</form>

	<input type="hidden" name="regYn" id="regYn" value=""/>
	<input type="hidden" name="num" id="num" value=""/>

		<div class="btn-box bot r">
			<div class="left">
				<h2 class="pop-tit2 left" style="margin-top: 0px;">목록</h2>
				<select class="select" id="searchSelect" name="searchSelect" style="width:200px">
					<option value="">전체</option>
					<option value="WAIT">대기</option>
					<option value="DONE">답변완료</option>
				</select>
			</div>
			<input type="text" id="inqKeyword" name="inqKeyword" style="width:300px;">
			<a href="javascript:void(0)" class="btn btn-blue" onclick="fn_searchInquiry();">검색</a>
		</div>

		<div class="row-tbl">
			<table style="table-layout: fixed;">
				<colgroup>
					<col style="width: 50px;;">
					<col style="width: 100px;">
					<col style="width: 250px;">
					<col style="width: 100px;">
					<col style="width: 100px;">
					<col style="width: 100px;">
					<col style="width: 100px;">
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>분류</th>
						<th>1:1 문의</th>
						<th>상태</th>
						<th>작성자</th>
						<th>등록일</th>
						<th>답변일</th>
					</tr>
				</thead>
				<tbody id="inqListArea">
					<!-- 여기에 데이터 넣기 -->
				</tbody>
			</table>
		</div>

		<div class="paginate_complex" id="areaPaging">
		</div>


	</div><!-- /container -->

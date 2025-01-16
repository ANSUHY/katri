<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

<script type="text/javascript">

	$(document).ready(function() {

		/* 초기 작업 */
		fn_boardListInit();

		/* board List data 가져오기 */
		fn_getBoardList($("#currPage").val());
	});

	/*
	 * 함수명 : fn_boardListInit
	 * 설   명 : 초기 작업
	 */
	function fn_boardListInit() {

		// 검색어 엔터 처리
		$(document).on("keydown", ".searchInput", function(key) {
			if(key.keyCode==13)
				fn_getBoardList(1);
		});

	}

	/*
	 * 함수명 : fn_goPage(p_page)
	 * 설   명 : 페이지이동
	 */
	function fn_goPage(p_page) {
		if(p_page == null) {
			p_page = $("#currPage").val();
		}
		fn_getBoardList(p_page);
	}

	/*
	 * 함수명 : fn_getBoardList()
	 * 설   명 : boardList data 가져오기
	*/
	function fn_getBoardList(p_page) {

		if(p_page == ''){
			p_page = 1;
		}

		/* ------ 0. html비우기 */
		$("#areaData").html("");
		$("#areaPaging").html("");

		/* ------ 1. 유효성검사 */

		/* ------ 2. frm에 데이터 셋팅 */
		// 2-1. 페이지 frm에 셋팅하기
		$("#currPage").val(p_page);

		// 2-2. 검색조건 frm에 셋팅하기
		let inputList	= $(".search_inner").find("input:text");//input태그 리스트
		let selectList 	= $(".search_inner").find("select"); //select태그 리스트
		let checkList_type	= $(".search_inner").find("input:checkbox[name=searchArrBoardType]:checked"); //checkbox_searchArrBoardType 체크된 리스트
		/* 2-2-1. inupt태그 리스트 */
		for (let i = 0 ; i < inputList.length ; i++) {
			let name = $(inputList[i]).attr('name');
			$("#"+name).val($(inputList[i]).val());
		}
		/* 2-2-2. select태그 리스트 */
		for (let i = 0 ; i < selectList.length ; i++) {
			let name = $(selectList[i]).attr('name');
			$("#"+name).val($(selectList[i]).val());
		}
		/* 2-2-3. checkbox_searchArrBoardType 체크된 리스트 */
		$("#frm_boardList input[id^='searchArrBoardType_']").remove(); //기존input박스 제거
		let inputHmtl = "";
		for (let i = 0 ; i < checkList_type.length ; i++) {
			if( $(checkList_type[i]).val()  != ''){
				inputHmtl =  '<input type="hidden" name="searchArrBoardType" id="searchArrBoardType_' + i + '" value="' + $(checkList_type[i]).val() + '"/>';
				$('#frm_boardList').append(inputHmtl);
			}
		}

		/* ------ 3. 데이터 가져오기*/
		$.blockUI();
		$.ajax({
			url	 		: "/board/getBoardList"
			, type 		: "GET"  
			, dataType 	: "json"
			, data 		: $("#frm_boardList").serialize()
			, success 	: function(jsonData, textStatus, jqXHR) {
				$.unblockUI();

				let html = '';
				if(jsonData.resultCode === 200){

					if(jsonData.data.list !== null && jsonData.data.list.length > 0){ /* 데이터가 있을 경우 */
						// 1. 리스트 html 만들기
						$.each(jsonData.data.list, function(index, data){
							html += fn_returnBoardListHtml(data);
						})

						// 2. 페이징 뿌리기
						fn_makePaging2("#areaPaging", jsonData.data.totCnt, $("#currPage").val(), $("#rowCount").val(), "fn_goPage");

					} else { /* 데이터가 없을 경우 */
						html += '<tr><td class="center" colspan="4">' + jsonData.resultMessage + '</td></tr>';
					}

					//3. 리스트 뿌리기
					$("#areaData").html(html);

				} else {
					alert(jsonData.resultMessage);
				}
			}
		});

	}

	/*
	 * 함수명 : fn_returnBoardListHtml
	 * 설   명 : boardList html생성
	*/
	function fn_returnBoardListHtml(data){
		let returnHtml = "";

		/* 1. html생성*/
		returnHtml += '<tr onclick="fn_goBoardDetail(\'' + data.boardNo + '\');">';
		returnHtml += '	<td class="center" title="' + data.rownum + '">' + data.rownum + '</td>';
		returnHtml += '	<td class="center" title="' + data.typeNm + '">' + data.typeNm + '</td>';
		returnHtml += '	<td class="center" title="' + data.title + '">' + data.title + '</td>';
		returnHtml += '	<td class="center" title="' + data.regDt+ '">' + data.regDt + '</td>';
		returnHtml += '</tr>';

		return returnHtml;
	}

	/*
	 * 함수명 : fn_resetSearch()
	 * 설   명 : 검색창 리셋
	*/
	function fn_resetSearch(){

		let inputList 		= $(".search_inner").find("input:text");//input태그 리스트
		let selectList 		= $(".search_inner").find("select");	//select태그 리스트
		let checkList_type	= $(".search_inner").find("input:checkbox[name=searchArrBoardType]:checked"); //checkbox_searchArrBoardType 체크된 리스트
		for (let i = 0 ; i < inputList.length ; i++) {
			$(inputList[i]).val("");
		}
		for (let i = 0 ; i < selectList.length ; i++) {
			$(selectList[i]).val("");
		}
		for (let i = 0 ; i < checkList_type.length ; i++) {
			$(checkList_type[i]).attr("checked", false);
		}

		$("select[name='searchType']").val('title'); //기초값 셋팅
		$("select[name='searchBoardType']").val(''); //기초값 셋팅

		//리셋 하고 search
		fn_getBoardList(1);

	}

 	/*
	 * 함수명 : fn_goBoardDetail(boardNo)
	 * 설   명 : 상세가기
	*/
	function fn_goBoardDetail(boardNo) {
		$("#frm_boardList #targetBoardNo").val(boardNo);
		$("#frm_boardList").attr("method", "GET");
		$("#frm_boardList").attr("action", "/board/boardDetail");
		$("#frm_boardList").submit();
	}

	 /*
	 * 함수명 : fn_downExcelBoard()
	 * 설   명 : 엑셀다운로드(아래 리스트대로_검색조건 바꾸고 해도 리스트대로나옴)
	*/
	function fn_downloadExcelBoard() {
		$("#frm_boardList").attr("action", "/board/downloadExcelBoard");
		$("#frm_boardList").attr("method", "GET");
		$("#frm_boardList").submit();
	}

	/*
	 * 함수명 : fn_downloadTestFile()
	 * 설   명 : 파일 다운로드_DB에 없는거
	*/
	function fn_downloadTestFile() {
		fn_pageFileDownload('test_hoho_v1.1.0.txt' , '테스트파일.txt');
	}

	/*
	 * 함수명 : fn_boardChart()
	 * 설   명 : 게시판 차트 페이지 이동 ( 검색조건 상관 x )
	*/
	function fn_boardChart() {
		location.href = "/board/boardChart";
	}

</script>

<!-- ===== header ====== -->
<header id="header">
	<div id="sub-mv" class="sub-data">
		<div class="inner">
			<h2>board List</h2>
			<div class="sub-obj">오브젝트</div>
		</div>
	</div>
</header>
<!-- ===== /header ====== -->

<!-- ===== container ====== -->
<div id="container">

	<!--breadcrumb-wr-->
	<jsp:include page="/WEB-INF/views/layout/breadcrumbWr.jsp">
		<jsp:param name="menuTopNo" value="01"/>
		<jsp:param name="menuSubNo" value="0101"/>
	</jsp:include>
	<!-- // breadcrumb-wr-->

	<div id="cont" class="cont-myservice board-myservice board-data">

		<form method="get" name="frm_boardList" id="frm_boardList">
			<input type="hidden" name="rowCount" 			id="rowCount" 			value="10"/>

			<input type="hidden" name="targetBoardNo" 		id="targetBoardNo" 		value=""/> 		<!-- 디테일갈경우 targetBoardNo -->

			<input type="hidden" name="currPage" 			id="currPage" 			value="${boardSearchData.currPage}"/> 	<!-- 페이지 no -->

			<input type="hidden" name="searchType" 			id="searchType" 		value="${boardSearchData.searchType}"/>		<!-- 검색조건 -->
			<input type="hidden" name="searchKeyword" 		id="searchKeyword" 		value="${boardSearchData.searchKeyword}"/>	<!-- 검색어 -->
			<input type="hidden" name="searchBoardType" 	id="searchBoardType" 	value="${boardSearchData.searchBoardType}"/><!-- 검색 BOARD TYPE -->
			<c:forEach items="${boardSearchData.searchArrBoardType}" var="item" varStatus="status">
				<input type="hidden" name="searchArrBoardType" 	id="searchArrBoardType_${status.index}" value="${item}"/>		<!-- 검색 BOARD TYPE ARR -->
			</c:forEach>

		</form>

		<!--tit-->
		<div class="cont-platform-tit bMg80">
			<h2 class="tit">board</h2>
		</div>
		<!--// tit-->

		<div class="mysvc-sch-wr" id="pSchInner">

			<table class="tbl">
				<tr>
					<th>검색조건</th>
					<td>
						<select name="searchType">
							<option value="title" <c:if test="${boardSearchData.searchType == 'title'}">selected</c:if>>제목</option>
							<option value="titleCont" ${boardSearchData.searchType == "titleCont" ? "selected" : ""}>제목 및 내용</option>
						</select>
					</td>
					<th>검색어</th>
					<td>
						<input type="text" name="searchKeyword" placeholder="입력하세요" value="${boardSearchData.searchKeyword}" class="searchInput">
					</td>
					<th>board 타입</th>
					<td>
						<cm:makeTag tagType="select" tagId="tempBoardType" name="searchBoardType" code="ABOT" defaultUseYn="Y" defaultNm="선택하세요" selVal="${boardSearchData.searchBoardType}"/>
					</td>
				</tr>
				<tr>
					<th>board 타입_CheckBox ${aa}</th>
					<td>
						<cm:makeTag tagType="checkbox" tagId="tempArrBoardType" name="searchArrBoardType" code="ABOT" arraySelVal="${boardSearchData.searchArrBoardType}"/>
					</td>

				</tr>
			</table>
			<div class="btn-wr">
				<a href="javascript:void(0);" onclick="fn_resetSearch();" class="btn reset">초기화</a>
				<a href="javascript:void(0);" onclick="fn_getBoardList(1);" class="btn b-sch">검색</a>

				<a href="javascript:void(0);" onclick="fn_downloadExcelBoard();" class="btn b-sch">엑셀다운로드</a>
				<a href="javascript:void(0);" onclick="fn_boardChart();" class="btn b-sch">게시판 차트</a>
				<a href="javascript:void(0);" onclick="fn_downloadTestFile();" class="btn b-sch">DB X TEST파일 다운</a>
			</div>
		</div>

		<!--list st-->
		<div class="board-cont" id="board-lst">
			<table class="data-lst">
				<colgroup>
					<col width="6%">
					<col width="15%">
					<col width="*">
					<col width="8%">
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>board 타입</th>
						<th>제목</th>
						<th>날짜</th>
					</tr>
				</thead>
				<tbody id="areaData">
				</tbody>
			</table>
		</div>
		<!-- //list ed -->

		<div class="btn-wr paging">
			<div class="paging_wrap" id="areaPaging">
			</div>
		</div>
		<div class="mt10"><button type="button" class="button_default register" onclick="window.location.href='/board/boardReg';">등록</button></div>
	</div>
	<!--// container-->

</div>
<!-- ===== /container ====== -->
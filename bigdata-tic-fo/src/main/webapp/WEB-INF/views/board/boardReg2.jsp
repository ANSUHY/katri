<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

<%@ page import="com.katri.common.FileConst" %>
<c:set var="FILE_DIV_NM_BOARD_IMG" 	value="<%=FileConst.File.FILE_DIV_NM_BOARD_IMG%>" />
<c:set var="FILE_DIV_NM_BOARD_FILE" value="<%=FileConst.File.FILE_DIV_NM_BOARD_FILE%>" />


<!-- 네이버 스마트 에디터 -->
<script type="text/javascript" src="/smarteditor/js/service/HuskyEZCreator.js" charset="charset=UTF-8"></script>

<script type="text/javascript">

	let oEditors = [];
	let arrDelFileEncodeNo = new Array();

	$(document).ready(function() {

		/* 초기 작업 */
		fn_boardRegInit();

	});

	/*
	 * 함수명 : fn_boardRegInit
	 * 설   명 : 초기 작업
	 */
	function fn_boardRegInit() {

		// ----------- 네이버 에디터
		nhn.husky.EZCreator.createInIFrame({ 
			oAppRef: oEditors,
			elPlaceHolder: "cont",
			sSkinURI: "/smarteditor/SmartEditor2Skin.html",      
			fCreator: "createSEditor2",    

		});

		// ----------- 파일 변경 유효성 체크
		$(".file_upload").on("change", function(){

			let type = $(this).attr("name");
			let chkExt = new Array();

			//1. 허용되는 확장자 넣기
			if(type == "${FILE_DIV_NM_BOARD_IMG}") {
				chkExt = ["jpg", "png", "gif"];
			}else if(type == "${FILE_DIV_NM_BOARD_FILE}"){
				chkExt = ["mp3","wma","wav","m4a","3gp","hwp","doc","docx","ppt","pptx","zip","xls","xlsx","pdf","txt","jpg","gif","png"];
			}else{
				chkExt = ["mp3","wma","wav","m4a","3gp","hwp","doc","docx","ppt","pptx","zip","xls","xlsx","pdf","txt","jpg","gif","png"];
			}

			//2. 파일 확장자 체크
			let files = $(this)[0].files;
			let targetExt = "";
			for(let i= 0; i<files.length; i++){

				// 파일 확장자
				targetExt = files[i].name.split('.').pop().toLowerCase();

				if(! ($.inArray(targetExt, chkExt) > -1)) {
					$(this).val("");
					alert("<spring:message code='result-message.messages.common.message.data.file.exte.error'/>");
					return false;
				}
			}

		});


	}

	/*
	 * 함수명 : fn_saveBoard
	 * 설   명 : 저장 및 수정
	 */
	function fn_saveBoard() {

		/* ------ 1. 네이버에디터 설정 */
		oEditors.getById["cont"].exec("UPDATE_CONTENTS_FIELD", []);

		/* ------ 2. 유효성검사 */
		if(! fn_validation()){
			return;
		}

		$.blockUI();
		/* ------ 3. 데이터 셋팅 */
		// 3-1. 데이터 폼 만들기
		let form = $("#frm_boardReg")[0];
		let jData = new FormData(form);

		// 3-2. 삭제할 파일 셋팅
		let arrDelFileSn = new Array();
		$('div[class="del_fileDiv"]').each(function(index, data){
			arrDelFileSn.push($(this).data("encodefilesn"));
		})
		jData.append('arrdelEncodeFileSn', arrDelFileSn);

		/* ------ 4. 데이터 전송 */
		$.ajax({
			url				: "/board/saveBoard"
			, type			: "POST"
			, enctype		: "multipart/form-data"
			, cache			: false
			, data			: jData
			, processData 	: false
			, contentType	: false
			, success 		: function(result) {
				$.unblockUI();

				if (result.resultCode == "200") {
					fn_afterSaveBoard(result);
				} else {
					alert("aaaaaaaaaaa")
					alert(result.resultMessage);
				}
			}
		});

	}

	/*
	 * 함수명 : fn_afterSaveBoard
	 * 설   명 : 저장 및 수정 작업후
	 */
	function fn_afterSaveBoard(data) {

		let boardNo = $("#boardNo").val();

		if( boardNo !== null && boardNo !== "") { //수정
			$("#frm_boardList #targetBoardNo").val(boardNo);
			$("#frm_boardList").attr("method", "GET");
			$("#frm_boardList").attr("action", "/board/boardDetail");
			$("#frm_boardList").submit();
		} else { //저장
			location.href='/board/boardList';
		}

	}

	/*
	 * 함수명 : fn_validation()
	 * 설  명  : 유효성 검사 체크
	 */
	function fn_validation() {

		let isValid = true;

		// 제목
		/* if($.trim($("#title").val()) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='제목'/>") // '{0}는(은) 필수 입력항목입니다.'
			$("#title").focus();
			isValid = false;
			return false;
		} */

		// 네이버에디터
		let cont = $("#cont").val();
		if( cont == ""  || cont == null || cont == '&nbsp;' || cont == '<p>&nbsp;</p>' || cont == '<p><br></p>')  {
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='내용'/>") // '{0}는(은) 필수 입력항목입니다.'
			$("#cont").focus();
			isValid = false;
			return false;
		}

		return isValid
	}

	/*
	 * 함수명 : fn_deleteFile
	 * 설   명 : 파일 삭제
	 */
	function fn_deleteFile(encodeFileSn) {

		if(confirm("<spring:message code='result-message.messages.common.message.data.delete'/>")) { // 삭제 하시겠습니까?
			$.ajax({
				url				: "/file/deleteFile"
				, method		: "POST"
				, async			: false
				, data			: {"encodeFileSn" : encodeFileSn}
				, success		: function(result) {

					if (result.resultCode == "200") {
						location.reload();
					} else {
						alert(result.resultMessage);
					}

				}
			});
		}
	}

	/*
	 * 함수명 : fn_addDelFileSet
	 * 설   명 : 저장시에 삭제할 파일 셋팅 + 파일 안보이게 하기
	 */
	function fn_addDelFileSet(e) {

		//상위에 div 안보이게 하기
		$(e).parent('div[class="fileDiv"]').hide();

		//상위에 div class명 변경
		$(e).parent('div[class="fileDiv"]').attr("class", "del_fileDiv");

	}

</script>

<!-- ===== header ====== -->
<header id="header">
	<div id="sub-mv" class="sub-myservice">
		<div class="inner">
			<h2>Board</h2>
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

	<!--container-->
	<div id="cont" class="cont-myservice dtl-myservice">

		<form method="get" name="frm_boardList" id="frm_boardList">
			<input type="hidden" name="targetBoardNo" 		id="targetBoardNo" 		value=""/> 	<!-- 디테일갈경우 targetBoardNo -->

			<input type="hidden" name="currPage" 			id="currPage" 			value="${boardSearchData.currPage}"/> 		<!-- 페이지 no -->

			<input type="hidden" name="searchType" 			id="searchType" 		value="${boardSearchData.searchType}"/>		<!-- 검색조건 -->
			<input type="hidden" name="searchKeyword" 		id="searchKeyword" 		value="${boardSearchData.searchKeyword}"/>	<!-- 검색어 -->
			<input type="hidden" name="searchBoardType" 	id="searchBoardType" 	value="${boardSearchData.searchBoardType}"/><!-- 검색 BOARD TYPE -->
			<c:forEach items="${boardSearchData.searchArrBoardType}" var="item" varStatus="status">
				<input type="hidden" name="searchArrBoardType" 	id="searchArrBoardType_${status.index}" value="${item}"/>		<!-- 검색 BOARD TYPE ARR -->
			</c:forEach>
		</form>

		<!--tit-->
		<div class="cont-platform-tit bMg80">
			<h2 class="tit">board 수정</h2>
		</div>
		<!--// tit-->

		<form method="post" name="frm_boardReg" id="frm_boardReg" enctype="multipart/form-data" onsubmit="return flase;">

			<input type="hidden" id="boardNo" name="boardNo" value="${boardDetail.boardNo}"/>

			<table class="register_type">

				<colgroup>
					<col width="14%">
					<col width="*">
				</colgroup>

				<tbody>

					<!-- board 타입 -->
					<tr>
						<th><span>board 타입</span></th>
						<td>
							<pre><cm:makeTag tagType="select" tagId="typeCd" name="typeCd" code="ABOT" selVal="${boardDetail.typeCd}"/></pre>
						</td>
					</tr>

					<!-- 제목 -->
					<tr>
						<th><span>제목</span></th>
						<td>
							<input type="text" 	id="title" name="title" placeholder="제목을 입력하세요" value="${boardDetail.title}"/>
						</td>
					</tr>

					<!-- 내용 -->
					<tr>
						<th><span>내용</span></th>
						<td>
							<textarea name="cont" id="cont" style="width: 100%">${boardDetail.cont}</textarea>
						</td>
					</tr>

					<!-- 파일_img -->
					<tr>
						<th>파일_img</th>
						<td>
							<c:forEach items="${boardDetail.listFileImg}" var="item">
								<div class="fileDiv" data-encodefilesn="${item.encodeFileSn}">
									<a href="/file/downloadFile?encodeFileSn=${item.encodeFileSn}" class="btn file flex">
										${item.orgnlFileNm}
									</a>
									<a onclick="fn_deleteFile('${item.encodeFileSn}')" >
										####[[바로 삭제]]
									</a>
									<a onclick="fn_addDelFileSet(this)" >
										####[[저장시에 삭제]]
									</a>
								</div>
							</c:forEach>
							<br/>
							<input type="file" class="file_upload" name="${FILE_DIV_NM_BOARD_IMG}" multiple="multiple" accept=".jpg, .png, .gif"/>
						</td>
					</tr>

					<!-- 파일_file -->
					<tr>
						<th>파일_file</th>
						<td>
							<c:forEach items="${boardDetail.listFileFile}" var="item">
								<div class="fileDiv" data-encodefilesn="${item.encodeFileSn}">
									<a href="/file/downloadFile?encodeFileSn=${item.encodeFileSn}" class="btn file flex">
										${item.orgnlFileNm}
									</a>
									<a onclick="fn_deleteFile('${item.encodeFileSn}')" >
										####[[바로 삭제]]
									</a>
									<a onclick="fn_addDelFileSet(this)" >
										####[[저장시에 삭제]]
									</a>
								</div>
							</c:forEach>
							<br/>
							<input type="file" class="file_upload" name="${FILE_DIV_NM_BOARD_FILE}" multiple="multiple" />
						</td>
					</tr>

				</tbody>
			</table>
		</form>

		<div class="btn-wr" style="justify-content:center;">
			<a href="javascript:void(0)" onclick="fn_saveBoard()" class="btn">등록하기</a>
			<a href="javascript:void(0)" onclick="fn_deleteBoard()" class="btn">삭제</a>
			<a href="javascript:void(0)" onclick="fn_goList()" class="btn">목록</a>
		</div>

	</div>
	<!--// container-->

</div>
<!-- ===== /container ====== -->

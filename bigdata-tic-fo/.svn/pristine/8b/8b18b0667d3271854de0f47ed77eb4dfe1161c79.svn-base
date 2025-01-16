<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page import="com.katri.common.Const" %>
<c:set var="POST_TYPE_CD_ARCHIVE" value="<%=Const.Code.PostTypeCd.ARCHIVE%>" />
<c:set var="ROW_COUNT" value="<%=Const.Paging.ROW_COUNT%>" />

<script type="text/javascript">
	$(function(){
	});

	/**
	 * 공지사항 목록 이동
	 */
	function fn_goArchiveList() {
		$("#archiveFrm").attr({
			  method: "GET"
			, action: "/ctnt/archive/archiveList"
		}).submit();
	}

	/**
	 * 파일 다운로드
	 */
	function fn_downloadFile(encodeFileSn) {
		location.href = "/file/downloadFile?encodeFileSn=" + encodeFileSn;
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
		<jsp:param name="menuSubNo" value="0503"/>
	</jsp:include>
	<!-- // breadcrumb-wr-->

	<!--container-->
	<div id="cont" class="cont-board">
		<!--  조건 -->
		<form name="archiveFrm" id="archiveFrm">
			<input type="hidden" name="nttSn" id="nttSn" value="${archiveDetail.nttSn}">
			<input type="hidden" name="nttTyCd" id="nttTyCd" value="${POST_TYPE_CD_ARCHIVE}" />
			<input type="hidden" name="searchKeyword" id="searchKeyword" value="${archiveSearchData.searchKeyword}" /> <!-- 검색어 -->
			<input type="hidden" name="chgPage" id="chgPage" value="${archiveSearchData.chgPage}">
			<input type="hidden" name="rowCount" id="rowCount" value="${ROW_COUNT * archiveSearchData.chgPage}">
		</form>

		<h2 class="tit">자료실</h2>
		<!--board-->
		<div class="board-dtl">
			<div class="dtl-stit">
				<span>작성자 : ${archiveDetail.mngrNm}</span>
				<span>등록일 : ${archiveDetail.crtDt}</span>
			</div>
			<h3 class="dtl-tit">${archiveDetail.nttSjNm}</h3>
			<!--file-->
			<div class="dtl-file">
				<label>첨부파일</label>
				<p>
					<c:choose>
						<c:when test="${not empty archiveDetail.fileDtoList}">
							<c:forEach var="file" items="${archiveDetail.fileDtoList}">
								<a href="javascript:void(0)" onclick="fn_downloadFile('${file.encodeFileSn}')">${file.orgnlFileNm}</a>
							</c:forEach>
						</c:when>
						<c:otherwise>
							첨부된 파일이 없습니다.
						</c:otherwise>
					</c:choose>
				</p>
			</div>
			<!--내용 // 임시로 넣음-->
			<div class="dtl-cont">
				${archiveDetail.nttCn}
			</div>
		</div>
		<div class="btn-wr dtl-btn-wr">
			<a href="javascript:void(0)" onclick="fn_goArchiveList()" class="btn btn-list">목록</a>
		</div>
		<!--//board-->
	</div>
</div>
<!-- ===== /container ====== -->

</body>
</html>
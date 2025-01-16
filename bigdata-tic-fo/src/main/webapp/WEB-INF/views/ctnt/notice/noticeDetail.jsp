<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page import="com.katri.common.Const" %>
<c:set var="POST_TYPE_CD_NOTICE" value="<%=Const.Code.PostTypeCd.NOTICE%>" />
<c:set var="ROW_COUNT" value="<%=Const.Paging.ROW_COUNT%>" />

<script type="text/javascript">
	/**
	 * 공지사항 목록 이동
	 */
	function fn_goNoticeList() {
		$("#noticeFrm").attr({
			  method: "GET"
			, action: "/ctnt/notice/noticeList"
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
		<jsp:param name="menuSubNo" value="0501"/>
	</jsp:include>
	<!-- // breadcrumb-wr-->

	<!--container-->
	<div id="cont" class="cont-board">
		<!--  조건 -->
		<form name="noticeFrm" id="noticeFrm">
			<input type="hidden" name="nttSn" id="nttSn" value="${noticeDetail.nttSn}">
			<input type="hidden" name="nttTyCd" id="nttTyCd" value="${POST_TYPE_CD_NOTICE}" />
			<input type="hidden" name="searchKeyword" id="searchKeyword" value="${noticeSearchData.searchKeyword}" /> <!-- 검색어 -->
			<input type="hidden" name="nttClfCd" id="nttClfCd" value="${noticeSearchData.nttClfCd}" />
			<input type="hidden" name="chgPage" id="chgPage" value="${noticeSearchData.chgPage}">
			<input type="hidden" name="rowCount" id="rowCount" value="${ROW_COUNT * noticeSearchData.chgPage}">
		</form>

		<h2 class="tit">공지사항</h2>
		<!--board-->
		<div class="board-dtl">
			<div class="dtl-stit">
				<span>작성자 : ${noticeDetail.mngrNm}</span>
				<span>등록일 : ${noticeDetail.crtDt}</span>
				<span>분류 : ${noticeDetail.comnCdNm}</span>
			</div>
			<h3 class="dtl-tit">${noticeDetail.nttSjNm}</h3>
			<!--file-->
			<div class="dtl-file">
				<label>첨부파일</label>
				<p>
					<c:choose>
						<c:when test="${not empty noticeDetail.fileDtoList}">
							<c:forEach var="file" items="${noticeDetail.fileDtoList}">
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
				${noticeDetail.nttCn}
			</div>
		</div>
		<div class="btn-wr dtl-btn-wr">
			<a href="javascript:void(0)" onclick="fn_goNoticeList()" class="btn btn-list">목록</a>
		</div>
		<!--//board-->
	</div>
</div>
<!-- ===== /container ====== -->
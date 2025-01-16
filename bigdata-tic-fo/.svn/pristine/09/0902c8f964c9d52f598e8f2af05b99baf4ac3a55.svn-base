<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cm" uri="/WEB-INF/tlds" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">

	// 페이징 이동 함수
	function goPage (idx) {
		$("#pageNum").val(idx);
		$("#frm").attr("method", "GET");
		$("#frm").attr("action", "/mbr/mbrLst");
		$("#frm").submit();
	}
	
	// 검색
	function searchAction () {
		$("#pageNum").val(1);
		var count = 0;
		var name = $("#nameField").val();
		var sDate = $("#sDateField").val();
		var eDate = $("#eDateField").val();
	
		if (sDate != '' || eDate != '') {
			if (sDate == '') alert("시작일을 함께 선택해주세요.");
			if (eDate == '') alert("종료일을 함께 선택해주세요.");
		}
	
		if (count == 0) {
			$("#frm").attr("action", "/mbr/mbrLst");
			$("#mbrNm").val(name);
			$("#sDt").val(sDate);
			$("#eDt").val(eDate);
			$("#frm").submit();
		}
	}
	
	// 상세
	function goView (id) {
		$("#targetId").val(id);
		$("#frm").attr("method", "GET");
		$("#frm").attr("action", "/mbr/mbrDtl");
		$("#frm").submit();
	}
	
	// 엑셀 다운로드 ( 기존 action 함수 참조, 조건 안걸고 전체 다운 )
	function fnExcelDownload() {
		
		$("#frm").attr("action", "/mbr/mbrLstExcelDown");
		$("#frm").attr("method", "GET");
		$("#frm").submit();
		
	}

</script>

<div id="container">
	<div class="subVisual"><span>회원관리</span></div>

	<form action="/mbr/mbrLst" method="get" name="frm" id="frm">
		<input type="hidden" name="pageNum" id="pageNum" value="${pageNum}"/>
		<input type="hidden" name="mbrNm" id="mbrNm" value="${mbrNm}"/>
		<input type="hidden" name="sDt" id="sDt" value="${sDt}"/>
		<input type="hidden" name="eDt" id="eDt" value="${eDt}"/>
		<input type="hidden" name="targetId" id="targetId" value=""/>
	</form>

	<section>
		<div class="contentsInner">
			<div class="search_inner">

				<div class="search_col">
					<div class="row member">
						<div class="serch_title">이름</div>
						<div class="formfield">
							<input type="text" id="nameField" value="${mbrNm}">
						</div>
					</div>

					<div class="row">
						<div class="serch_title">검색일 기준</div>
						<div class="disable">
							<input type="text" disabled value="가입일자">
						</div>
						<div class="formfield datebox">
							<em class="cal"><input type="text" class="datepicker" id="sDateField" value="${sDt}"></em>
							<span class="blankTxt">~</span>
						</div>
						<div class="formfield datebox lastbox">
							<em class="cal"><input type="text" class="datepicker" id="eDateField" value="${eDt}"></em>
						</div>
					</div>
				</div>
				<div class="buttonframe">
					<span><button class="button_type search" onclick="searchAction();">검색</button></span>
				</div>
			</div>
			
			<!-- 엑셀 다운로드 버튼 추가 시작 -->
			<div style="margin-top: 40px; text-align: center;">
				<span><button class="button_type list" onclick="fnExcelDownload();">엑셀다운로드</button></span>
			</div>
			<!-- 엑셀 다운로드 버튼 추가 종료 -->
			
			<div class="list_inner">
				<table class="list_type">
					<colgroup>
						<col width="6%">
						<col width="8%">
						<col width="7%">
						<col width="*">
						<col width="11%">
						<col width="13%">
						<col width="17%">
						<col width="10%">
						<col width="6%">
					</colgroup>
					<thead>
						<tr>
							<th>번호</th>
							<th>아이디</th>
							<th>이름</th>
							<th>기관명</th>
							<th>소속부서</th>
							<th>연락처</th>
							<th>이메일</th>
							<th>가입일자</th>
							<th>상태</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${list.size() == 0}"><tr><td class="center" colspan="9">검색된 데이터가 없습니다.</td></tr></c:when>
							<c:otherwise>
								<c:forEach items="${list}" var="item">
									<tr onclick="goView('${item.mbrId}');">
										<td class="center" title="${item.rownum}">${item.rownum}</td>
										<td title="${item.mbrId}">${item.mbrId}</td>
										<td class="center" title="${item.mbrNm}">${item.mbrNm}</td>
										<td title="${item.certInstNm}">${item.certInstNm}</td>
										<td class="center" title="${item.deptNm}">${item.deptNm}</td>
										<td class="center" title="${item.mbrCpNo}">${item.mbrCpNo}</td>
										<td title="${item.mbrEmlAddr}">${item.mbrEmlAddr}</td>
										<td class="center" title="${item.regDt}">${item.regDt}</td>
										<td class="center" title="${item.sttCd}">${item.sttCd}</td>
									</tr>
								</c:forEach>

							</c:otherwise>
						</c:choose>

					</tbody>
				</table>
			</div>

			<c:if test="${list.size() > 0}">
				<div class="paging-wrap">
					<div class="paging network">
						<c:if test="${pageNum > 10}"><a href="javascript:goPage(${sPage-1});" class="direction first">이전</a></c:if>
						<c:if test="${pageNum > 1}"><a href="javascript:goPage(${pageNum-1});" class="direction prev">pre</a></c:if>

						<c:forEach begin="${sPage}" end="${ePage}" var="item">
							<c:choose>
								<c:when test="${item == pageNum}">
									<a href="javascript:return false;" class="on">${item}</a>
								</c:when>
								<c:otherwise>
									<a href="javascript:goPage(${item});" >${item}</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${allPage > pageNum}"><a href="javascript:goPage(${pageNum+1});" class="direction next">next</a></c:if>
						<c:if test="${allPageGroup > nowPageGroup}"><a href="javascript:goPage(${ePage+1});" class="direction last">다음</a></c:if>

					</div>
				</div>
			</c:if>
		</div>
	</section>
</div>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" uri="/WEB-INF/tlds"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">

// 페이징 이동 함수
function goPage (idx) {
	$("#pageNum").val(idx);
	$("#intgrRcptNo").val('');
	$("#frm").attr("method", "GET");
	$("#frm").attr("action", "/cert/certLst");
	$("#frm").submit();
}

//검색
function searchAction () {

	$("#pageNum").val(1);
	
	var inputList = $(".search_inner").find("input");
	var selectList = $(".search_inner").find("select");

	for (var i = 0 ; i < inputList.length ; i++) {
		var name = $(inputList[i]).attr('name');
		$("#"+name).val($(inputList[i]).val());
	}
	for (var i = 0 ; i < selectList.length ; i++) {
		var name = $(selectList[i]).attr('name');
		$("#"+name).val($(selectList[i]).val());
	}
	$("#intgrRcptNo").val('');
	$("#frm").attr("method", "GET");
	$("#frm").attr("action", "/cert/certLst");
	$("#frm").submit();
}

// 검색 조건 초기화
function resetAction () {
	var inputList = $(".search_inner").find("input");
	var selectList = $(".search_inner").find("select");

	for (var i = 0 ; i < inputList.length ; i++) {
		$(inputList[i]).val("");
		//var name = $(inputList[i]).attr('name');
		//$("#"+name).val('');
	}
	for (var i = 0 ; i < selectList.length ; i++) {
		$(selectList[i]).val("");
		//var name = $(selectList[i]).attr('name');
		//$("#"+name).val('');
	}
}

// 상세 페이지로 이동
function goView (id, obj) {
	
	/* if ($("#intgrRcptNo").size() > 0) {
		$("#intgrRcptNo").val(id);
	} else {
		var input = '<input type="hidden" name="intgrRcptNo" id="intgrRcptNo" value="'+id+'"/>'
		$("#frm").append(input);
	} */
	$("#intgrRcptNo").val(id);
	$("#certNo2").val(obj);
	$("#frm").attr("method", "GET");
	$("#frm").attr("action", "/cert/certDtl");
	$("#frm").submit();
}
</script>

<div id="container">
	<div class="subVisual"><span>인증정보 조회</span></div>
	<form action="/cert/certLst" method="get" name="frm" id="frm">
		<input type="hidden" name="pageNum" id="pageNum" value="${pageNum}"/>
		<input type="hidden" name="instId" id="instId" value="${instId}"/>
		<input type="hidden" name="certNo" id="certNo" value="${certNo}"/>
		<input type="hidden" name="mkrNm" id="mkrNm" value="${mkrNm}"/>
		<input type="hidden" name="prdtNm" id="prdtNm" value="${prdtNm}"/>
		<input type="hidden" name="mdlNm" id="mdlNm" value="${mdlNm}"/>
		<input type="hidden" name="iptrNm" id="iptrNm" value="${iptrNm}"/>
		<input type="hidden" name="intgrRcptNo" id="intgrRcptNo"/>
		<input type="hidden" name="certNo2" id="certNo2"/>
	</form>
	<section>
		<!--contentsInner st-->
		<div class="contentsInner">
			<!--search st-->
			<div class="search_inner">
				<!--search fiels st-->
				<div class="search_col">
					<div class="row">
						<div class="serch_title">인증기관</div>
						<div class="formfield">
							<select name="instId">
								<option value="">전체</option>
								<c:forEach items="${certInstList}" var="item">
									<c:choose>
										<c:when test="${instId == item.comnCd}">
											<option value="${item.comnCd}" selected="selected">${item.comnCdNm}</option>
										</c:when>
										<c:otherwise>
											<option value="${item.comnCd}">${item.comnCdNm}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="serch_title">인증번호</div>
						<div class="formfield"><input type="text" name="certNo" placeholder="입력하세요" value="${certNo}"></div>
					</div>
					<div class="row">
						<div class="serch_title">제조업체명</div>
						<div class="formfield"><input type="text" name="mkrNm" placeholder="입력하세요" value="${mkrNm}"></div>
					</div>
				</div>
				<div class="search_col">
					<div class="row">
						<div class="serch_title">제품명</div>
						<div class="formfield"><input type="text" name="prdtNm" placeholder="입력하세요" value="${prdtNm}"></div>
					</div>
					<div class="row">
						<div class="serch_title">모델명</div>
						<div class="formfield"><input type="text" name="mdlNm" placeholder="입력하세요" value="${mdlNm}"></div>
					</div>
					<div class="row">
						<div class="serch_title">수입업체명</div>
						<div class="formfield"><input type="text" name="iptrNm" placeholder="입력하세요" value="${iptrNm}"></div>
					</div>
				</div>
				<!--//search fiels ed-->
				<!--search button st-->
				<div class="buttonframe">
					<span><button class="button_type search" onclick="searchAction();">검색</button></span>
					<span><button class="button_type reset" onclick="resetAction();">초기화</button></span>
				</div>
				<div style='margin-top:30px'>
					- 각 기관별 인증정보를 상세 조건을 통해 검색할 수 있습니다.<br/>
					- 2022년 3월 1일 이후 각 기관에서 제공한 데이터를 기준으로 검색이 가능합니다. (인증일자 기준)
				</div>
				<!--//search button ed-->
			</div>
			<!--//search ed-->
			<!--list st-->
			<div class="list_inner">
				<table class="list_type">
					<colgroup>
						<col width="6%">
						<col width="*">
						<col width="32%">
						<col width="10%">
						<col width="8%">
						<col width="13%">
					</colgroup>
					<thead>
						<tr>
							<th>번호</th>
							<th>인증기관</th>
							<th>모델명</th>
							<th>제품명</th>
							<th>인증상태</th>
							<th>인증번호</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${list.size() == 0}"><tr><td class="center" colspan="6">검색된 데이터가 없습니다.</td></tr></c:when>
							<c:otherwise>
								<c:forEach items="${list}" var="item">
									<tr onclick="goView('${item.intgrRcptNo}','${item.certNo}');">
										<td class="center" title="${item.rownum}">${item.rownum}</td>
										<td title="${item.certInstNm}">${item.certInstNm}</td>
										<td title="${item.mdlNm}">${item.mdlNm}</td>
										<td title="${item.prdtNm}">${item.prdtNm}</td>
										<td class="center" title="${item.certSttNm}">${item.certSttNm}</td>
										<td class="center" title="${item.certNo}">${item.certNo}</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<!--//list ed-->

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
		<!--//contentsInner ed-->
	</section>
</div>
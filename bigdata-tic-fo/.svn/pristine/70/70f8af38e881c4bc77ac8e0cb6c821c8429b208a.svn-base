<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" uri="/WEB-INF/tlds"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">


$(document).ready(function(){
	//이미지 경로 가져오기
	<c:if test="${'' ne img}">
	var obj = ${img};

	if(obj.FileStatuses.FileStatus.length > 0){
		imgCallBack(obj.FileStatuses.FileStatus);

	}
   </c:if>

});

function imgCallBack(data){
	$("#imgTb").empty();
	for (var i = 0; i <data.length; i++) {
		var img =  data[i].pathSuffix;

		$("#imgTb").append('<tr><td><img src="/cert/displayImage?cert=${info.smInstNm}&path=${info.certNo}&img='+img+'" style="max-width: 700px; max-height: 700px;"></div></td></tr>');
	};

}

// 목록으로 이동
function goList () {
	$("#frm").submit();
}

//검색
function searchAction () {

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

	$("#frm").submit();
}

// 상세 페이지로 이동
function goView (id) {
	$("#intgrRcptNo").val(id);
	$("#frm").attr("action", "/cert/certDtl");
	$("#frm").submit();
}
</script>

<div id="container">
	<div class="subVisual"><span>인증정보 상세</span></div>

	<form action="/cert/certLst" method="get" name="frm" id="frm">
		<input type="hidden" name="pageNum" id="pageNum" value="${pageNum}"/>
		<input type="hidden" name="instId" id="instId" value="${instId}"/>
		<input type="hidden" name="certNo" id="certNo" value="${certNo}"/>
		<input type="hidden" name="mkrNm" id="mkrNm" value="${mkrNm}"/>
		<input type="hidden" name="prdtNm" id="prdtNm" value="${prdtNm}"/>
		<input type="hidden" name="mdlNm" id="mdlNm" value="${mdlNm}"/>
		<input type="hidden" name="iptrNm" id="iptrNm" value="${iptrNm}"/>
	</form>
	<section>
		<div class="contentsInner">
			<h2 class="subTile">인증정보</h2>
			<div class="detailbox">
				<table class="detail_type">
					<colgroup>
						<col width="14%">
						<col width="*">
						<col width="14%">
						<col width="*">
					</colgroup>
					<tr>
						<th>인증기관</th>
						<td>${info.certInstNm}(${info.smInstNm})</td>
						<th>통합접수번호</th>
						<td>${info.intgrRcptNo}</td>
					</tr>
					<tr>
						<th>인증번호</th>
						<td>${info.certNo}</td>
						<th>인증구분</th>
						<td>${info.certDivNm}(${info.certDivCd})</td>
					</tr>
					<tr>
						<th>인증일자</th>
						<td>${info.certYmd}</td>
						<th>인증상태</th>
						<td>${info.certSttNm}</td>
					</tr>
					<tr>
						<th>최초인증 번호</th>
						<td>${info.frstCertNo}</td>
						<th>인증변경 일자</th>
						<td>${info.certLastChgYmd}</td>
					</tr>
					<tr>
						<th>인증변경 사유</th>
						<td colspan="3">${info.certChgResnCn}</td>
					</tr>
				</table>
			</div>

			<h2 class="subTile mt_01">제품정보</h2>
			<div class="detailbox">
				<table class="detail_type">
					<colgroup>
						<col width="14%">
						<col width="*">
						<col width="14%">
						<col width="*">
					</colgroup>
					<tr>
						<th>제품명</th>
						<td>${info.prdtNm}</td>
						<th>브랜드명</th>
						<td>${info.brdNm}</td>
					</tr>
					<tr>
						<th>모델명</th>
						<td>${info.mdlNm}</td>
						<th>규격</th>
						<td>${info.stndVal}</td>
					</tr>
					<tr>
						<th>제품분류명</th>
						<td colspan="3">${info.sttyPrdtClfNm}</td>
						
						<%-- <th>제품분류 코드</th>
						<td>${info.sttyPrdtClfCd}</td>
						<th>제품세분류명</th>
						<td>${info.sttyPrdtDtclfNm}</td> --%>
					</tr>
					<tr>
						<th>파생모델명</th>
						<td colspan="3">${info.drvnMdlNm}</td>
					</tr>
					<tr>
						<th>비고</th>
						<td colspan="3">${info.rmkCn}</td>
					</tr>
				</table>
			</div>
			<h2 class="subTile mt_01">제조사 정보</h2>
			<div class="detailbox">
				<table class="detail_type">
					<colgroup>
						<col width="14%">
						<col width="*">
						<col width="14%">
						<col width="*">
					</colgroup>
					<tr>
						<th>제조사</th>
						<td>${info.mkrNm}</td>
						<th>사업자 등록번호</th>
						<td>${info.mkrBrno}</td>
					</tr>
					<tr>
						<th>제조국</th>
						<td>${info.mkrNtnNm}</td>
						<th>대표자</th>
						<td>${info.mkrRprsvNm}</td>
					</tr>
					<tr>
						<th>주소</th>
						<td colspan="3">${info.mkrAddr}</td>
					</tr>
					<tr>
						<th>전화번호</th>
						<td colspan="3">${info.mkrTelno}</td>
					</tr>
				</table>
			</div>
			<h2 class="subTile mt_01">수입업체 정보</h2>
			<div class="detailbox">
				<table class="detail_type">
					<colgroup>
						<col width="14%">
						<col width="*">
						<col width="14%">
						<col width="*">
					</colgroup>
					<tr>
						<th>수입업체</th>
						<td>${info.iptrNm}</td>
						<th>사업자 등록번호</th>
						<td>${info.iptrBrno}</td>
					</tr>
					<tr>
						<th>전화번호</th>
						<td>${info.iptrTelno}</td>
						<th>대표자</th>
						<td>${info.iptrRprsvNm}</td>
					</tr>
					<tr>
						<th>주소</th>
						<td colspan="3">${info.iptrAddr}</td>
					</tr>
				</table>
			</div>
			<h2 class="subTile mt_01">제품사진</h2>
			<div class="detailbox">
				<table class="detail_type bordrbottomn">

					<tbody id="imgTb">

					</tbody>

				</table>
			</div>
			<div class="buttonframe block t_c mt20">
				<button class="button_type list" onclick="goList();">목록</button>
			</div>
		</div>
	</section>
</div>
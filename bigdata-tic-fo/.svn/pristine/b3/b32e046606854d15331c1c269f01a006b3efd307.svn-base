<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cm" uri="/WEB-INF/tlds" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="tab-2" class="tabContent">
	<!--search st-->
	<div class="search_inner">
		<!--search fiels st-->
		<div class="search_col">
			<div class="row static">
				<div class="serch_title">인증기관</div>
				<div class="formfield">
					<select id="instId2">
						<option value="">전체</option>
						<c:forEach items="${certInstList}" var="item">
							<option value="${item.comnCd}">${item.comnCdNm}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="serch_title">검색기간</div>
				<div class="formfield datebox">
					<em class="cal"><input type="text" class="datepicker" id="startDate2" readonly="readonly"></em>
					<span class="blankTxt">~</span>
				</div>
				<div class="formfield datebox lastbox">
					<em class="cal"><input type="text" class="datepicker" id="endDate2" readonly="readonly"></em>
				</div>
			</div>
			
		</div>
		<!--//search fiels ed-->
		<!--search button st-->
		<div class="buttonframe">
			<span><button class="button_type search" onclick="typeList(1);">검색</button></span>
		</div>
		<div style='margin-top:20px'>
			<ul>
			<li>- 인증구분 통계는 법정별 안전인증에 대한 총건수와 적합 및 취소 현황입니다. </li>
			<li>- 기타건수는 개선명령, 사용금지, 효력상실, 반납, 청문실시, 기간만료에 대한 합계를 나타냅니다.</li>
			</ul>
			</div>
		<!--//search button ed-->
	</div>
	<!--//search ed-->
	<!--list st-->
	<div class="list_inner">
		<table class="list_type">
			<colgroup>
				<col width="*">
				<col width="15%">
				<col width="15%">
				<col width="15%">
				<col width="15%">
			</colgroup>
			<thead>
				<tr>
					<th>인증구분</th>
					<th>총건수</th>
					<th>적합건수</th>
					<th>취소건수</th>
					<th>기타건수</th>
				</tr>
			</thead>
			<tbody id="typeListBox">
				<tr>
					<td class="center" colspan="5">검색된 데이터가 없습니다.</td>
				</tr>
			</tbody>
		</table>
	</div>
	<!--//list ed-->
	<div class="paging-wrap" id="typePagingBox">

	</div>
</div>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cm" uri="/WEB-INF/tlds" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="tabContent current" id="tab-1">
	<div class="search_inner">
		<div class="search_col">
			<div class="row static">
				<div class="serch_title">인증기관</div>
				<div class="formfield">
					<select id="instId1">
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
					<em class="cal"><input type="text" class="datepicker" id="startDate1" readonly="readonly"></em>
					<span class="blankTxt">~</span>
				</div>
				<div class="formfield datebox lastbox">
					<em class="cal"><input type="text" class="datepicker" id="endDate1" readonly="readonly"></em>
				</div>
			</div>
		</div>
		<div class="buttonframe">
			<span><button class="button_type search" onclick="statsList();">검색</button></span>
		</div>
		<div style='margin-top:20px'>
			<ul>
			<li>- 인증상태 통계는 각 기관별 안전인증에 대한 적합 및 취소 현황입니다. </li>
			<li>- 기타건수는 개선명령, 사용금지, 효력상실, 반납, 청문실시, 기간만료에 대한 합계를 나타냅니다.</li>
		</ul>
		</div>
	</div>

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
					<th>인증기관</th>
					<th>총건수</th>
					<th>적합건수</th>
					<th>취소건수</th>
					<th>기타건수</th>
				</tr>
			</thead>
			<tbody id="statsListBox">
				<tr>
					<td class="center" colspan="5">검색된 데이터가 없습니다.</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
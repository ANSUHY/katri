<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">

	$(document).ready(function() {

		/* adminList 데이터 가져오기 */
		fn_getAdminList($("#currPage").val());
	});

	/*
	 * 함수명: fn_getAdminList
	 * 설 명:  관리자 리스트 가지고 오기
	 */
	function fn_getAdminList(page) {

		if(page == '' || page == null || page == 0) {
			page = 1;
		}

		//[[0]]. frm에 데이터 셋팅하기
		$("#currPage").val(page);

		// [[1]]. html 비우기
		$("#adminArea").html("");
		$("#pagingArea").html("");

		// [[2]]. 데이터 가져오기
			$.ajax({
				url			: "/system/admin/getAdminList"
			  , data		: $("#frm_adminList").serialize()
			  , type		: "GET"
			  , dataType	: "json"
			  , success	: function(jsonData, textStatus, jqXHR) {

				let html = '';

				if(jsonData.resultCode == 200) {

					if(jsonData.data.list !== null && jsonData.data.list.length > 0){
						$.each(jsonData.data.list, function(index, data){
							html += fn_returnAdminListHtml(data);
						});

					} else {
						html += '<tr><td class="center" colspan="4">' + "<spring:message code='result-message.messages.common.message.no.data'/>" + '</td></tr>'; //데이터가 없습니다.
					}

				 	//3. list 뿌리기
				 	$("#adminArea").html(html);

				} else {
					alert(jsonData.resultMessage);
				}
			  }
			}); //end of ajax
	} //end of function


	/*
	 * 함수명 : fn_returnAdminListHtml
	 * 설 명 : 리스트 생성 함수
	 */
	function fn_returnAdminListHtml(data) {

		let returnHtml = '';

		returnHtml	 += "<tr onclick='fn_goAdminDetail(" + '"' + data.mngrId  + '"' +")' style='cursor:pointer;'>";
		returnHtml	 += "<td>" + data.rowNum + "</td>";
		returnHtml	 += "<td>" + data.mngrId + "</td>";
		returnHtml	 += "<td>" + data.mngrNm + "</td>";
		if(data.useYn == "Y")
			returnHtml	 += "<td><b>" + data.useYn + "</b></td>";
		else
			returnHtml	 += "<td>" + data.useYn + "</td>";
		returnHtml	 += "</tr>";

		return returnHtml;
	}

	/*
	 * 함수명 : fn_goPage
	 * 설 명 : 페이지 이동 함수
	 */
	function fn_goPage(page){
		if(page == '' || page == 0 || page == null) {
			page = $("#currPage").val();
		}
		fn_getAdminList(page);
	 }

	/*
	 * 함수명 : fn_openPopUp
	 * 설 명 : 관리자 추가 팝업 열기
	*/
	function fn_openPopUp() {
		let url = "/system/admin/adminAddPopUp";
		let name = "adminAddPopUp";
// 		let option = "toolbar=no, menubar=no, scrollbars=no, resizable=no, width=900, height=400, height=400, top=100, left=300";
// 		window.open(url, name, option);
		fn_openPop(url, name, 900, 300, "Y");
	 }

	/*
	 * 함수명 : fn_goAdminDetail
	 * 설 명 : 관리자 상세 팝업 열기
	*/
	function fn_goAdminDetail(mngrId) {

		$("#detailId").val(mngrId);

		let url = "/system/admin/adminDetailPopUp";
		let name = "adminDetailPopUp";
// 		let option = "toolbar=no, menubar=no, scrollbars=no, resizable=no, width=900, height=400, height=400, top=100, left=300";
// 		window.open(url, name, option);
		fn_openPop(url, name, 900, 230, "Y");
	 }

</script>

<div id="container">
	<form action="" method="get" name="frm_adminList" id="frm_adminList">
		<input type="hidden" id="currPage" name="currPage" value=""/>
		<input type="hidden" id="detailId" name="detailId" value=""/>
	</form>

	<div class="titArea">
		<div class="location">
			<span class="ic-home"><i></i></span><span>시스템 관리</span><em>관리자 관리</em>
		</div>
	</div>

	<div class="btn-box bot r">
		<div class="left">
			<h2 class="pop-tit2" style="margin-top: 0px;">관리자 목록</h2>
		</div>
		<a href="javascript:void(0)" class="btn btn-blue" onclick="fn_openPopUp()">추가</a>
	</div>

	<div class="row-tbl">
		<table>
			<colgroup>
				<col style="width: 100px;">
				<col>
				<col>
				<col style="width: 250px;">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>아이디</th>
					<th>관리자명</th>
					<th>사용여부</th>
				</tr>
			</thead>
				<tbody id="adminArea">
				</tbody>
		</table>
	</div>


</div><!-- /container -->

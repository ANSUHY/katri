<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

<%@ page import="com.katri.common.Const" %>
<c:set var="SITE_TYPE_CD_FRONT" 	value="<%=Const.Code.SiteTypeCd.FRONT%>" />
<c:set var="SITE_TYPE_CD_ADMIN" 	value="<%=Const.Code.SiteTypeCd.ADMIN%>" />

	<script>

		$(document).ready(function() {
			// 페이지 첫 로드 시, front 메뉴 show
			fn_frontShow();
		});

		/*
		 * 함수명 : fn_frontShow
		 * 설   명 : Frontend 영역 셋팅
		 */
		function fn_frontShow() {

			$("#tabFrontArea").show();
			$("#tabBackArea").hide();

			$("#tabFront").addClass('on');
			$("#tabBack").removeClass('on');

			// 1. siteTyCd 설정
			$("#siteTyCd").val("${SITE_TYPE_CD_FRONT}");

			// 2. 권한 그룹 목록 조회
			fn_authrtGrpBasList();

		}

		/*
		 * 함수명 : fn_backShow
		 * 설   명 : BackOffice 영역 show
		 */
		function fn_backShow() {

			$("#tabBackArea").show();
			$("#tabFrontArea").hide();

			$("#tabBack").addClass('on');
			$("#tabFront").removeClass('on');

			// 1. siteTyCd 설정
			$("#siteTyCd").val("${SITE_TYPE_CD_ADMIN}");

			// 2. 권한 그룹 목록 조회
			fn_authrtGrpBasList();

		}

		/*
		 * 함수명 : fn_authrtGrpSavePop
		 * 설   명 : 권한 그룹 저장 팝업 호출
		 */
		function fn_authrtGrpSavePop(authrtGrpSn) {

			let url 	 = "/system/authrt/authrtGrpSavePopUp";
			let name	 = "권한 그룹 저장";
			let width	 = 500;
			let height	 = 250;
			let centerYn = 'Y'

			// 팝업 호출
			fn_openPop(url, name, width, height, centerYn);

			// target 값 넣어주기
			if( authrtGrpSn != '' ) {
				$("#targetAuthrtGrpSn").val(authrtGrpSn);
			} else {
				$("#targetAuthrtGrpSn").val('');
			}

		}

		/*
		 * 함수명 : fn_authrtMenuListPop
		 * 설   명 : 권한그룹 > 메뉴설정 팝업 호출
		 */
		function fn_authrtMenuListPop(authrtGrpSn) {

			let url 	 = "/system/authrt/authrtMenuListPopUp"
			let name	 = "권한 그룹 > 메뉴 권한 설정";
			let width	 = 800;
			let height	 = 650;
			let centerYn = 'Y'

			// 팝업 호출
			fn_openPop(url, name, width, height, centerYn);

			// target 값 넣어주기
			if( authrtGrpSn != '' ) {
				$("#targetAuthrtGrpSn").val(authrtGrpSn);
			} else {
				$("#targetAuthrtGrpSn").val('');
			}
		}


		/*
		 * 함수명 : fn_authrtGrpBasList
		 * 설   명 : 접속 권한 리스트 조회
		 */
		function fn_authrtGrpBasList() {

			$.blockUI();

			// 1. 검색 조건 셋팅
			let siteTyCd 		 = $("#siteTyCd").val();

			// 2. 권한 그룹 리스트 조회
			$.ajax({
				url	 		: "/system/authrt/getAuthrtGrpBasList"
				, type 		: "GET"
				, dataType 	: "json"
				, data 		: {
									"searchSiteTyCd"	 : siteTyCd
								}
				, success 	: function(jsonData, textStatus, jqXHR) {
					$.unblockUI();

					if(jsonData.resultCode === 200){

						// 3. 조회 > 데이터 출력
						fn_authrtGrpBasListHtml( jsonData.data );

					} else {
						alert(jsonData.resultMessage);
					}
				}
			});
		}

		/*
		 * 함수명 : fn_authrtGrpBasListHtml
		 * 설   명 : 접속 권한 리스트 조회 후, HTML 출력
		 */
		function fn_authrtGrpBasListHtml( data ) {

			// 0. 사이트 값 가져오기
			let siteTyCd 		 = $("#siteTyCd").val();

			let authHtml 	= "";

			// 1. 데이터 있는 경우
			if( data.totCnt > 0 ) {

				for( let nLoop=0; nLoop < data.list.length; nLoop++ ) {

					authHtml += "<tr>";
					authHtml += "		<td onclick='fn_authrtGrpSavePop(" + data.list[nLoop].authrtGrpSn + ")' style='cursor:pointer;'>" + data.list[nLoop].rnum + "</td>";
					authHtml += "		<td onclick='fn_authrtGrpSavePop(" + data.list[nLoop].authrtGrpSn + ")' style='cursor:pointer;'>" + data.list[nLoop].authrtGrpNm + "</td>";
					authHtml += "		<td onclick='fn_authrtGrpSavePop(" + data.list[nLoop].authrtGrpSn + ")' style='cursor:pointer;'>" + data.list[nLoop].useYn + "</td>";
					authHtml += "		<td>";
					authHtml += "			<a href='javascript:void(0);' onclick='fn_authrtMenuListPop(" + data.list[nLoop].authrtGrpSn + ")' class='btn btn-white btn-sm' style='text-decoration:none;' >메뉴설정</a>";
					authHtml += "		</td>";
	 				authHtml += "</tr>";
				}

				$("#" + siteTyCd + "AuthrtGrpBas").html(authHtml);

			}

		}

	</script>

	<div id="container">

		<input type="hidden" id="siteTyCd" 			value="" />
		<input type="hidden" id="targetAuthrtGrpSn" value="" />

		<div class="titArea">
			<div class="location">
				<span class="ic-home"><i></i></span><span>시스템 관리</span><em>접속권한 관리</em>
			</div>
		</div>

		<div class="div-tab">
			<ul>
				<li id="tabFront" class="on"><a href="javascript:void(0);" onclick="fn_frontShow()">Frontend</a></li>
				<li id="tabBack"><a href="javascript:void(0);" onclick="fn_backShow()">Backoffice</a></li>
			</ul>
		</div>

		<!-- Frontend Area -->
		<div id="tabFrontArea">

			<div>
				<div style="float: left;">
					<h2 class="pop-tit2">접속권한 목록</h2>
				</div>
			</div>

			<div class="row-tbl">
				<table>
					<colgroup>
						<col style="width: 100px;">
						<col>
						<col style="width: 200px;">
						<col style="width: 250px;">
					</colgroup>
					<thead>
						<tr>
							<th>번호</th>
							<th>권한그룹</th>
							<th>사용여부</th>
							<th>메뉴설정</th>
						</tr>
					</thead>
					<tbody id="frontAuthrtGrpBas">
						<tr>
							<td colspan="4">No Data.</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- // Frontend Area -->

		<!-- Backoffice Area -->
		<div id="tabBackArea">

			<div class="btn-box bot r">
				<div class="left">
					<h2 class="pop-tit2" style="margin-top: 0px;">접속권한 목록</h2>
				</div>
				<a href="javascript:void(0);" class="btn btn-blue" onclick="fn_authrtGrpSavePop('');">추가</a>
			</div>

			<div class="row-tbl">

				<table>
					<colgroup>
						<col style="width: 100px;">
						<col>
						<col style="width: 200px;">
						<col style="width: 250px;">
					</colgroup>
					<thead>
						<tr>
							<th>번호</th>
							<th>권한그룹</th>
							<th>사용여부</th>
							<th>메뉴설정</th>
						</tr>
					</thead>
					<tbody id="adminAuthrtGrpBas">
						<tr>
							<td colspan="4">No Data.</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- // Backoffice Area -->

	</div><!-- /container -->
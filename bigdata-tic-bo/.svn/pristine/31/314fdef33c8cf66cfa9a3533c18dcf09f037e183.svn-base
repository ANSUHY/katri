<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

	<script>

		$(document).ready(function() {

			// 페이지 첫 로드 시, 값 셋팅
			let trmsAgreHistUserId = opener.document.getElementById('targetUserId').value;

			$("#trmsAgreHistUserId").val(trmsAgreHistUserId);

			// 회원 약관 동의 이력 목록 조회
			fn_userTrmsAgreHistList();

		});

		// 약관 변경 시, 해당 약관 동의 이력 목록 조회
		$(document).on("change", '#searchTrmsCdVal', function(){
			fn_userTrmsAgreHistList();
		});

		/*
		 * 함수명 : fn_userTrmsAgreHistList
		 * 설   명 : 회원 약관 동의 이력 목록 조회
		 */
		function fn_userTrmsAgreHistList() {

			$.blockUI();

			// 0. HTML 셋팅
			let searchTrmsCdVal = $("#searchTrmsCdVal").val();

			let userColHtml  = "";
			let userHeadHtml = "";
			let userBodyHtml = "";

			if( searchTrmsCdVal === "JOIN" ) {
				userColHtml	 += "<col style='width:75%'><col>";
				userHeadHtml += "<tr><th>약관종류</th><th>동의 일자</th></tr>";
				userBodyHtml += "<tr><td colspan='2'>No Data.</td></tr>";
			} else {
				userColHtml	 += "<col style='width:35%'><col style='width:45%'><col>";
				userHeadHtml += "<tr><th>유형</th><th>약관종류</th><th>동의/해제 일자</th></tr>";
				userBodyHtml += "<tr><td colspan='3'>No Data.</td></tr>";
			}

			$("#areaTrmsAgreHistColData").html(userColHtml);
			$("#areaTrmsAgreHistTheadData").html(userHeadHtml);
			$("#areaTrmsAgreHistTbodyData").html(userBodyHtml);

			$.ajax({
				url	 		: "/user/user/getUserTrmsAgreHistList"
				, type 		: "GET"
				, dataType 	: "json"
				, data 		: $("#frm_trmsAgreHist").serialize()
				, success 	: function(jsonData, textStatus, jqXHR) {
					$.unblockUI();

					if(jsonData.resultCode === 200){

						// 1. 조회 > 데이터 출력
						fn_userTrmsAgreHistListHtml( searchTrmsCdVal, jsonData.data );

					} else {
						alert(jsonData.resultMessage);
						self.close();
					}
				}
			});

		}

		/*
		 * 함수명 : fn_userTrmsAgreHistListHtml
		 * 설   명 : 회원 약관 동의 이력 목록 조회 데이터 HTML 출력
		 */
		function fn_userTrmsAgreHistListHtml( searchTrmsCdVal, data ) {

			let userHeadHtml = "";
			let userBodyHtml = "";

			// 1. 데이터 있는 경우
			if( data.totCnt > 0 ) {

				for( let nLoop=0; nLoop < data.list.length; nLoop++ ) {

					if( searchTrmsCdVal === "JOIN") {
						userBodyHtml += '<tr>';
						userBodyHtml += "	<td style='text-align:left;'>" + data.list[nLoop].trmsNm + "</td>";
						userBodyHtml += "	<td>" + data.list[nLoop].trmsAgreHistCrtDt + "</td>";
						userBodyHtml += "</tr>";
					} else {
						userBodyHtml += '<tr>';

						userBodyHtml += "	<td style='text-align:left;'>";
						userBodyHtml += 		data.list[nLoop].trmsTyNm + " ";
						if( data.list[nLoop].agreYn === 'Y' ) {
							userBodyHtml += 	" 동의";
						} else {
							userBodyHtml += 	" 동의 해제";
						}
						userBodyHtml += 	"</td>";

						if( data.list[nLoop].trmsNm !== null ) {
							userBodyHtml += "<td style='text-align:left;'>";
							userBodyHtml += "[" + data.list[nLoop].instNm + "] ";
							userBodyHtml += data.list[nLoop].trmsNm;
						} else {
							userBodyHtml += "<td style='text-align:left;'>";
							userBodyHtml +=  "[" + data.list[nLoop].instNm + "] ";
							userBodyHtml +=  " 동의 해제";
						}
						userBodyHtml +=	"</td>";

						userBodyHtml += "	<td>" + data.list[nLoop].trmsAgreHistCrtDt + "</td>";
						userBodyHtml += "</tr>";
					}
				}

				$("#areaTrmsAgreHistTbodyData").html(userBodyHtml);
			}

		}

	</script>

	<div id="pop-wrap">

		<h1 class="pop-tit">약관동의 이력</h1>

		<div class="pop-contnet">

			<form action="" method="get" id="frm_trmsAgreHist" name="frm_trmsAgreHist">
				<input type="hidden" name="targetUserId" id="trmsAgreHistUserId" value="" />

				<select class="select" name="searchTrmsCdVal" id="searchTrmsCdVal" style="margin-bottom: 20px; width: 300px;">
					<option value="JOIN" selected="selected">회원가입</option>
					<option value="CERT">내손안의 시험인증</option>
				</select>

			</form>

			<div class="row-tbl">
				<table>
					<colgroup id="areaTrmsAgreHistColData">
						<col style="width:75%">
						<col>
					</colgroup>
					<thead id="areaTrmsAgreHistTheadData">
						<tr>
							<th>약관종류</th>
							<th>동의일자</th>
						</tr>
					</thead>
					<tbody id="areaTrmsAgreHistTbodyData">
						<tr>
							<td colspan="2">No Data.</td>
						</tr>
					</tbody>
				</table>
			</div>

		</div><!-- /pop-contnet -->
		<a href="javascript:self.close();" class="pop-close"><i class="fa fa-times"></i></a>
	</div><!-- /pop-wrao -->
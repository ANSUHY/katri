<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

	<script>

		$(document).ready(function() {

			// 페이지 첫 로드 시, 값 셋팅
			let logHistUserId = opener.document.getElementById('targetUserId').value;

			$("#logHistUserId").val(logHistUserId);

			// 회원 접속 로그 목록 조회
			fn_userLogHistList(1);

		});

		/*
		 * 함수명 : fn_goLogHistPage(pageNum)
		 * 설   명 : 페이지 이동
		 */
		function fn_goLogHistPage( pageNum ) {

			if(pageNum == null) {
				pageNum = $("#currPage").val();
			}

			fn_userLogHistList(pageNum);
		}

		/*
		 * 함수명 : fn_userLogHistList
		 * 설   명 : 회원 접속 로그 목록 조회
		 */
		function fn_userLogHistList( pageNum ) {

			$.blockUI();

			// 0. HTML 셋팅
			let userHtml = "<tr><td colspan='3'>No Data.</td></tr>";

			$("#areaLogHistData").html(userHtml);
			$("#areaLogHistPaging").html("");

			// 1. 검색 조건 셋팅
			if(pageNum == ''){
				pageNum = 1;
			}

			$("#currPage").val(pageNum);

			// 2. 회원 접속 로그 목록 조회
			$.ajax({
				url	 		: "/user/user/getUserLogHistList"
				, type 		: "GET"
				, dataType 	: "json"
				, data 		: $("#frm_logHist").serialize()
				, success 	: function(jsonData, textStatus, jqXHR) {
					$.unblockUI();

					if(jsonData.resultCode === 200){

						// 3. 조회 > 데이터 출력
						fn_userLogHistListHtml( jsonData.data );

					} else {
						alert(jsonData.resultMessage);
						self.close();
					}
				}
			});
		}

		/*
		 * 함수명 : fn_userLogHistListHtml
		 * 설   명 : 회원 접속 로그 목록 조회 데이터 HTML 출력
		 */
		function fn_userLogHistListHtml( data ) {

			let userHtml 	= "";

			// 1. 데이터 있는 경우
			if( data.totCnt > 0 ) {

				for( let nLoop=0; nLoop < data.list.length; nLoop++ ) {

					userHtml += '<tr>';
					userHtml += "	<td>" + data.list[nLoop].rnum + "</td>";
					userHtml += "	<td>" + data.list[nLoop].lgnHistCrtDt + "</td>";
					userHtml += "	<td>" + data.list[nLoop].userIpAddr + "</td>";
					userHtml += "</tr>";
				}

				$("#areaLogHistData").html(userHtml);

				// 2. 페이징 처리
				fn_makePaging("#areaLogHistPaging", data.totCnt, $("#currPage").val(), $("#rowCount").val(), "fn_goLogHistPage");

			}
		}

	</script>

	<div id="pop-wrap">

		<form action="" method="get" id="frm_logHist" name="frm_logHist">
			<input type="hidden" name="targetUserId" id="logHistUserId"	value="" />
			<input type="hidden" name="rowCount" 	 id="rowCount" 		value="10" />
			<input type="hidden" name="currPage" 	 id="currPage" 		value="" />
		</form>

		<h1 class="pop-tit">접속 로그</h1>

		<div class="pop-contnet">
			<div class="row-tbl">
				<table>
					<colgroup>
						<col style="width:100px;">
						<col>
						<col>
					</colgroup>
					<thead>
						<tr>
							<th>번호</th>
							<th>접속시간</th>
							<th>IP</th>
						</tr>
					</thead>
					<tbody id="areaLogHistData">
						<tr>
							<td colspan="3">
								No Data.
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="paginate_complex" id="areaLogHistPaging">
			</div>

		</div><!-- /pop-contnet -->
		<a href="javascript:self.close();" class="pop-close"><i class="fa fa-times"></i></a>

	</div><!-- /pop-wrao -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

	<script>

		$(document).ready(function() {

			// 초기 함수 호출
			fn_userBasList(1);
		});

		// 상태 변경 시, 목록 조회
		$(document).on("change", '#searchUserSttCd', function(){
			fn_userBasList(1);
		});

		// 아이디 검색 시, 엔터 처리
		$(document).on("keydown", "#searchUserId", function(key) {
			if(key.keyCode==13) {
				fn_userBasList(1);
			}
		});

		/*
		 * 함수명 : fn_goPage(pageNum)
		 * 설   명 : 페이지 이동
		 */
		function fn_goPage( pageNum ) {

			if(pageNum == null) {
				pageNum = $("#currPage").val();
			}

			fn_userBasList(pageNum);
		}

		/*
		 * 함수명 : fn_userBasList
		 * 설   명 : 회원 목록 조회
		 */
		function fn_userBasList( pageNum ) {

			$.blockUI();

			// 0. HTML 셋팅
			let userHtml = "<tr><td colspan='6'>No Data.</td></tr>";
			$("#areaData").html(userHtml);
			$("#areaPaging").html("");

			// 1. 검색 조건 셋팅
			if(pageNum == ''){
				pageNum = 1;
			}

			$("#currPage").val(pageNum);

			// 2. 회원 현황 목록 조회
			$.ajax({
				url	 		: "/user/user/getUserBasList"
				, type 		: "GET"
				, dataType 	: "json"
				, data 		: $("#frm_user").serialize()
				, success 	: function(jsonData, textStatus, jqXHR) {
					$.unblockUI();

					if(jsonData.resultCode === 200){

						// 3. 조회 > 데이터 출력
						fn_userBasListHtml( jsonData.data );

					} else {
						alert(jsonData.resultMessage);
					}
				}
			});
		}

		/*
		 * 함수명 : fn_userBasListHtml
		 * 설   명 : 회원 목록 조회 데이터 HTML 출력
		 */
		function fn_userBasListHtml( data ) {

			let userHtml 	= "";

			// 1. 데이터 있는 경우
			if( data.totCnt > 0 ) {

				for( let nLoop=0; nLoop < data.list.length; nLoop++ ) {

					userHtml += '<tr style="cursor:pointer;" onclick="fn_userBasDetailPop(this)" data-key="' + data.list[nLoop].userId + '" data-type="' + data.list[nLoop].userTyCd + '">';

						// 번호
						userHtml += "	<td>" + data.list[nLoop].rnum + "</td>";
						// 아이디
						userHtml += "	<td>" + data.list[nLoop].maskingUserId + "</td>";
						// 회원유형
						userHtml += "	<td>" + data.list[nLoop].userTyNm + "</td>";
						// 회원상태
						userHtml += "	<td>" + data.list[nLoop].userSttNm + "</td>";
						// 가입일
						userHtml += "	<td>" + data.list[nLoop].joinYmd + "</td>";
						// 최근접속일
						userHtml += "	<td>";
						if( data.list[nLoop].lastLgnDt != null ) {
							userHtml += data.list[nLoop].lastLgnDt;
						} else {
							userHtml += '-';
						}
						userHtml += "	</td>";

					userHtml += "</tr>";
				}

				$("#areaData").html(userHtml);

				// 2. 페이징 처리
				fn_makePaging("#areaPaging", data.totCnt, $("#currPage").val(), $("#rowCount").val(), "fn_goPage");

			}
		}

		/*
		 * 함수명 : fn_userBasDetailPop
		 * 설   명 : 회원 상세 조회 팝업 호출
		 */
		function fn_userBasDetailPop( obj ) {

			let targetUserId = $(obj).attr("data-key");
			let targetUserTypeCd = $(obj).attr("data-type");

			let url 	 = ""
			let name	 = "회원 현황 > 회원 상세";
			let width	 = 800;
			let height	 = 650;
			let centerYn = 'Y'

			// 팝업 호출
			fn_openPop("", name, width, height, centerYn);

			// target 값 넣어주기
			if( targetUserId != '' ) {

				$("#frm_user #targetUserId").val(targetUserId);
				$("#frm_user #targetUserTypeCd").val(targetUserTypeCd);

				frm_user.action = "/user/user/userDetailPopUp";
				frm_user.method = "POST";
				frm_user.target = name;
				frm_user.submit();

			} else {
				$("#targetUserId").val('');
			}

		}

		/*
		 * 함수명 : fn_userLogHistPop
		 * 설   명 : 회원 접속 로그 팝업 호출
		 */
		function fn_userLogHistPop() {

			let url 	 = "/user/user/userLogHistPopUp";
			let name	 = "회원 상세 > 회원 접속 로그";
			let width	 = 600;
			let height	 = 450;
			let centerYn = 'Y'

			// 팝업 호출
			fn_openPop(url, name, width, height, centerYn);

		}

		/*
		 * 함수명 : fn_userTrmsAgreHistPop
		 * 설   명 : 회원 약관 동의 이력 팝업 호출
		 */
		function fn_userTrmsAgreHistPop() {

			let url 	 = "/user/user/userTrmsAgreHistPopUp";
			let name	 = "회원 상세 > 약관 동의 이력";
			let width	 = 600;
			let height	 = 400;
			let centerYn = 'Y'

			// 팝업 호출
			fn_openPop(url, name, width, height, centerYn);

		}

		/*
		 * 함수명 : fn_userPwdChgPop
		 * 설   명 : 회원 비밀번호 초기화 팝업
		 */
		function fn_userPwdChgPop() {

			let url 	 = "/user/user/userPwdChgPopUp";
			let name	 = "회원 상세 > 비밀번호 초기화";
			let width	 = 550;
			let height	 = 250;
			let centerYn = 'Y'

			// 팝업 호출
			fn_openPop(url, name, width, height, centerYn);

		}

	</script>

	<div id="container">

		<div class="titArea">
			<div class="location">
				<span class="ic-home"><i></i></span><span>회원 관리</span><em>회원 현황</em>
			</div>
		</div>

		<!-- 검색 조건 -->
		<form action="" method="get" id="frm_user" name="frm_user">
			<input type="hidden" name="targetUserId" 		id="targetUserId" value="" />
			<input type="hidden" name="targetUserTypeCd" 	id="targetUserTypeCd" value="" />
			<input type="hidden" name="rowCount" id="rowCount" value="10" />
			<input type="hidden" name="currPage" id="currPage" value="" />

			<div class="btn-box bot r">
				<div class="left">
					<h2 class="pop-tit2 left" style="margin-top: 0px;">회원 현황</h2>
					<!-- 회원 상태 코드 조회 -->
					<cm:makeTag tagType="select" tagId="searchUserSttCd" name="searchUserSttCd" code="USC" defaultUseYn="Y" defaultNm="전체"/>
				</div>
				<input type="text" id="searchUserId" name="searchUserId" style="width: 300px;"/>
				<a href="javascript:void(0);" class="btn btn-blue" onclick="fn_userBasList(1);">검색</a>
			</div>
		</form>
		<!-- // 검색 조건 -->

		<div class="row-tbl">
			<table>
				<colgroup>
					<col style="width: 100px;">
					<col>
					<col>
					<col>
					<col>
					<col>
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>아이디</th>
						<th>회원유형</th>
						<th>회원상태</th>
						<th>가입일</th>
						<th>최근접속일</th>
					</tr>
				</thead>
				<tbody id="areaData">
				</tbody>
			</table>
		</div>

		<div class="paginate_complex" id="areaPaging">
		</div>

	</div><!-- /container -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

<%@ page import="com.katri.common.Const" %>
<c:set var="SITE_TYPE_CD_FRONT" 	value="<%=Const.Code.SiteTypeCd.FRONT%>" />
<c:set var="SITE_TYPE_CD_ADMIN" 	value="<%=Const.Code.SiteTypeCd.ADMIN%>" />

	<script>

		/*
		 * 설   명 : chk_authrt_all 선택 시 처리 (전체 선택)
		 */
		$(document).on("click", ".chkAuthrtAll", function(){

			if( $('.chkAuthrtAll').is(':checked') ) {
				$(".chkAuthrt").prop('checked', true);
				$(".chkCntnAuthrt").val('Y');
			} else {
				$(".chkAuthrt").prop('checked', false);
				$(".chkCntnAuthrt").val('N');
			}

		});

		/*
		 * 설   명 : chk_authrt 선택 시 처리 (전체 선택)
		 */
		$(document).on("click", ".chkAuthrt", function(){

			// 전체 체크 확인 후 처리 함수
			fnAuthAllCheck();

		});

		/*
		 * 함수명 : fnAuthAllCheck
		 * 설   명 : 전체 체크 확인 후 처리 함수
		 */
		function fnAuthAllCheck() {
			let totalCnt = $(".chkAuthrt").length;
			let checkCnt = $(".chkAuthrt:checked").length;

			if( totalCnt == 0 || totalCnt != checkCnt ) {
				$("#chkAuthrtAll").prop('checked', false);
			} else {
				$("#chkAuthrtAll").prop('checked', true);
			}
		}

		/*
		 * 함수명 : fnAuthrtYn
		 * 설   명 : 권한 별 체크박스 뎁스 처리
		 */
		function fnAuthrtYn(bFlag, thisObj) {

			let strSelAllMenuSn = $(thisObj).attr("data-allMenuSnVal");
			let strSelUpMenuSn 	= $(thisObj).attr("data-upMenuSn");
			let strSelMenuSn 	= $(thisObj).attr("data-menuSn");
			let arrSelAllVal 	= strSelAllMenuSn.split(",");

			$('[name*="arrAuthrtYn"]').each( function() {

				let strMenuSn 		= $(this).attr("data-menuSn");
				let strUpMenuSn 	= $(this).attr("data-upMenuSn");
				let strAllMenuSnVal	= $(this).attr("data-allMenuSnVal");

				/* 선택한 메뉴에 포함 되어 있는지 */
				for( let nLoop=0; nLoop < arrSelAllVal.length; nLoop++ ) {

					if ( arrSelAllVal[nLoop] === strMenuSn ) {
						$(this).prop("checked", bFlag);

						if(bFlag) {
							$("#chkCntnAuthrtYn" + strMenuSn).val('Y');
						} else {
							$("#chkCntnAuthrtYn" + strMenuSn).val('N');
						}
					}

				}

				/* 다른 메뉴 중 선택한 메뉴가 상위 메뉴 인지 */
				let arrAllMenuSnVal = strAllMenuSnVal.split(",");

				for( let nLoop=0; nLoop < arrAllMenuSnVal.length; nLoop++ ) {

					if( arrAllMenuSnVal[nLoop] === strSelMenuSn ) {
						$(this).prop("checked", bFlag);

						if(bFlag) {
							$("#chkCntnAuthrtYn" + strMenuSn).val('Y');
						} else {
							$("#chkCntnAuthrtYn" + strMenuSn).val('N');
						}
					}
				}

				/* 메뉴가 선택되어 있는 경우 하위메뉴 전체 선택 */
				if ( $("#chkAuthrt" + strMenuSn).is(":checked") ) {

					let arrUpMenu = $("#chkAuthrt" + strMenuSn).attr("data-allMenuSnVal").split(',');

					for( let i=0; i < arrUpMenu.length; i++ ) {
						$("#chkAuthrt" + arrUpMenu[i]).prop("checked", true);
						$("#chkCntnAuthrtYn" + arrUpMenu[i]).val('Y');
					}
				}
			});
		}

		$(document).ready(function() {

			// 페이지 첫 로드 시, 값 셋팅
			let targetAuthrtGrpSn = opener.document.getElementById('targetAuthrtGrpSn').value;

			// 값이 있는 경우 상세 조회
			if( targetAuthrtGrpSn != '' ) {
				$("#authrtGrpSn").val(targetAuthrtGrpSn);
			}

			// 메뉴 목록 조회
			fn_authrtGrpMenuList();

		});

		/*
		 * 함수명 : fn_authrtGrpMenuList
		 * 설   명 : 메뉴 별 접속 권한 리스트 데이터 조회
		 */
		function fn_authrtGrpMenuList() {

			$.blockUI();

			// 1. 검색 조건 셋팅
			let siteTyCd 	= opener.document.getElementById('siteTyCd').value;
			let authrtGrpSn	= $("#authrtGrpSn").val();

			// 2. 권한 그룹 리스트 조회
			$.ajax({
				url	 		: "/system/authrt/getAuthrtGrpMenuList"
				, type 		: "GET"
				, dataType 	: "json"
				, data 		: {
									"searchSiteTyCd"		: siteTyCd
									, "targetAuthrtGrpSn" 	: authrtGrpSn
								}
				, success 	: function(jsonData, textStatus, jqXHR) {
					$.unblockUI();

					if(jsonData.resultCode === 200){

						// 3. 조회 > 데이터 출력
						fn_authrtGrpMenuListHtml( jsonData.data );

					} else {
						alert(jsonData.resultMessage);
					}
				}
			});
		}

		/*
		 * 함수명 : fn_authrtGrpMenuListHtml
		 * 설   명 : 메뉴 별 접속 권한 리스트 데이터 조회 후, HTML 출력
		 */
		function fn_authrtGrpMenuListHtml( data ) {

			$.blockUI();

			let siteTyCd 	 = opener.document.getElementById('siteTyCd').value;
			let menuLevel	 = siteTyCd == "${SITE_TYPE_CD_FRONT}" ? 4 : 2;

			// [[0]]. Table Col, Head Html
			let authMenuHeadHtml = "<tr>";
			let	authMenuColHtml  = "";

			for( let nLoop=1; nLoop <= menuLevel; nLoop++ ) {
				authMenuColHtml	 += "<col>";
				authMenuHeadHtml += "<th>" + nLoop + "레벨</th>";
			}

			authMenuHeadHtml += "	<th>접속권한 체크";
			authMenuHeadHtml += "		<label><input type='checkbox' class='chkAuthrtAll' id='chkAuthrtAll'></label>";
			authMenuHeadHtml += "	</th>";
			authMenuHeadHtml += "</tr>";

			$("#authrtGrpMenuColList").html(authMenuColHtml);
			$("#authrtGrpMenuHeadList").html(authMenuHeadHtml);

			// [[0]]. Table Body

			let authMenuBodyHtml = "";

			if( data.list.length > 0 ) {

				for( let nLoop=0; nLoop < data.list.length; nLoop++ ) {

					authMenuBodyHtml += "<tr>";

					/* 메뉴명 셋팅 */
					// 1 Depth
					if( data.list[nLoop].menuLvlVal == 1) {
						authMenuBodyHtml += "		<td>" + data.list[nLoop].menuNm  + "</td>";
					} else {
						authMenuBodyHtml += "		<td></td>";
					}

					// 2 Depth
					if( data.list[nLoop].menuLvlVal == 2) {
						authMenuBodyHtml += "		<td>" + data.list[nLoop].menuNm  + "</td>";
					} else {
						authMenuBodyHtml += "		<td></td>";
					}

					if( data.list[nLoop].siteTyCd == "${SITE_TYPE_CD_FRONT}" ) {
						// 3 Depth
						if( data.list[nLoop].menuLvlVal == 3) {
							authMenuBodyHtml += "		<td>" + data.list[nLoop].menuNm  + "</td>";
						} else {
							authMenuBodyHtml += "		<td></td>";
						}

						// 4 Depth
						if( data.list[nLoop].menuLvlVal == 4) {
							authMenuBodyHtml += "		<td>" + data.list[nLoop].menuNm  + "</td>";
						} else {
							authMenuBodyHtml += "		<td></td>";
						}
					}

					/* 체크박스 셋팅 */
					authMenuBodyHtml += "		<td>"
					authMenuBodyHtml += "			<label>";
					if( data.list[nLoop].cntnAuthrtYn == 'Y' ) {
						authMenuBodyHtml += "			<input type='checkbox'	class='chkAuthrt' id='chkAuthrt" + data.list[nLoop].menuSn  + "' name='arrAuthrtYn' data-menuSn='" + data.list[nLoop].menuSn  + "' data-upMenuSn='" + data.list[nLoop].upMenuSn  + "' data-allMenuSnVal='" + data.list[nLoop].allMenuSnVal  + "' onclick='fnAuthrtYn( this.checked, this )' checked>";
						authMenuBodyHtml += "			<input type='hidden'	class='chkCntnAuthrt' id='chkCntnAuthrtYn" + data.list[nLoop].menuSn  + "' name='arrCntnAuthrtYn' value='Y'>";
					} else{
						authMenuBodyHtml += "			<input type='checkbox'	class='chkAuthrt' id='chkAuthrt" + data.list[nLoop].menuSn  + "' name='arrAuthrtYn' data-menuSn='" + data.list[nLoop].menuSn  + "' data-upMenuSn='" + data.list[nLoop].upMenuSn  + "' data-allMenuSnVal='" + data.list[nLoop].allMenuSnVal  + "' onclick='fnAuthrtYn( this.checked, this )' >";
						authMenuBodyHtml += "			<input type='hidden' 	class='chkCntnAuthrt' id='chkCntnAuthrtYn" + data.list[nLoop].menuSn  + "' name='arrCntnAuthrtYn' value='N'>";
					}
					authMenuBodyHtml += "				<input type='hidden' id='chkMenu" + data.list[nLoop].menuSn  + "' name='arrMenuSn' value='"+ data.list[nLoop].menuSn +"'>";

					authMenuBodyHtml += "			</label>";
					authMenuBodyHtml += "		</td>";
	 				authMenuBodyHtml += "</tr>";
				}

			} else {

				authMenuBodyHtml += "<tr>";
				authMenuBodyHtml += "		<td colspan='" + (menuLevel + 1) + "'>No Data.</td>";
				authMenuBodyHtml += "</tr>";
			}

			$("#authrtGrpMenuBodyList").html(authMenuBodyHtml);

			$.unblockUI();

			// 전체 체크 처리하는 함수
			fnAuthAllCheck();

		}

		/*
		 * 함수명 : fn_authrtGrpMenuSave
		 * 설   명 : 메뉴 별 접속 권한 저장
		 */
		function fn_authrtGrpMenuSave() {

			// 1. 저장값 셋팅
			let form = $("#frm_authrtGrpMenu")[0];
			let jData = new FormData(form);

			// site_ty_cd 추가
			let siteTyCd = opener.document.getElementById('siteTyCd').value;
			jData.append("siteTyCd", siteTyCd);

			// 2. 저장 시작
			$.blockUI();

			$.ajax({
				url				: "/system/authrt/saveAuthrtGrpMenu"
				, type			: "POST"
				, cache			: false
				, data			: jData
				, processData 	: false
				, contentType	: false
				, success 		: function(result) {
					$.unblockUI();

					if (result.resultCode == "200") {

						// 3. 저장 성공 후, 처리 함수 호출
						fn_afterSaveAuthrtGrpMenu(result);

					} else {
						alert(result.resultMessage);
					}
				}
			});
		}

		function fn_afterSaveAuthrtGrpMenu( result ) {

			alert(result.resultMessage); // 성공 메시지

			// 0. 창 닫기
			window.self.close();

			// 1. 부모창 > 사이트 타입별 리스트 재조회
			window.opener.fn_authrtGrpMenuList();

		}

	</script>

</head>

	<div id="pop-wrap">

		<h1 class="pop-tit">권한메뉴 설정</h1>

		<form id="frm_authrtGrpMenu" name="frm_authrtGrpMenu" method="post">

			<input type="hidden" name="authrtGrpSn" id="authrtGrpSn" 	value=""/>

			<div class="pop-contnet">
				<div class="row-tbl">
					<table>

						<colgroup id="authrtGrpMenuColList">
						</colgroup>

						<thead id="authrtGrpMenuHeadList">
						</thead>

						<tbody id="authrtGrpMenuBodyList">
						</tbody>

					</table>
				</div>

				<div class="btn-box">
					<a href="javascript:self.close();"	class="btn btn-default">취소</a>
					<a href="javascript:void(0);" 		class="btn btn-blue" onclick="fn_authrtGrpMenuSave();">저장</a>
				</div>

			</div><!-- /pop-contnet -->
		</form>

		<a href="javascript:self.close();" class="pop-close"><i class="fa fa-times"></i></a>

	</div><!-- /pop-wrao -->

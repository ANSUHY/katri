<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

<%@ page import="com.katri.common.Const" %>
<c:set var="SITE_TYPE_CD_FRONT" 	value="<%=Const.Code.SiteTypeCd.FRONT%>" />
<c:set var="SITE_TYPE_CD_ADMIN" 	value="<%=Const.Code.SiteTypeCd.ADMIN%>" />

<script>

	$(document).ready(function() {

		// 0. 페이지 접근 시, front 메뉴 셋팅
		fn_frontShow();

	});

	// 레별별 메뉴 변경 시, 상위 메뉴 가져오기
	$(document).on("change", '[id*=UpMenuList]', function(){

		let menuLvl  = $(this).find("option:selected").attr("data-menulvl");
		let upMenuSn = $(this).find("option:selected").attr("data-upmenusn");

		fn_upMenuListHtml(menuLvl, '', upMenuSn);

	});

	/*
	 * 함수명 : fn_frontShow
	 * 설   명 : Frontend 영역 셋팅
	 */
	function fn_frontShow() {

		// 0. back 메뉴 정보 hide
		$("#tabFrontArea").show();
		$("#tabBackArea").hide();

		$("#tabFront").addClass('on');
		$("#tabBack").removeClass('on');

		// 1. siteTyCd 설정
		$("#siteTyCd").val("${SITE_TYPE_CD_FRONT}");

		// 2. front 메뉴 리스트 조회
		fn_menuInit('1','');

	}

	/*
	 * 함수명 : fn_backShow
	 * 설   명 : BackOffice 영역 show
	 */
	function fn_backShow() {

		// 0. front 메뉴 정보 hide
		$("#tabBackArea").show();
		$("#tabFrontArea").hide();

		$("#tabBack").addClass('on');
		$("#tabFront").removeClass('on');

		// 1. siteTyCd 설정
		$("#siteTyCd").val("${SITE_TYPE_CD_ADMIN}");

		// 2. back 메뉴 리스트 조회
		fn_menuInit('1','');

	}

	/*
	 * 함수명 : fn_menuSaveInit
	 * 설   명 : 메뉴 정보 등록 영역 리셋 - 신규 입력
	 */
	function fn_menuSaveInit() {

		// 0. 값 셋팅 및 초기화
		let siteTyCd = $("#siteTyCd").val();
		$("#" + siteTyCd + "MenuSn").val('');

		// 1. siteTyCd 별 form 영역 활성화
		$("#frm_" + siteTyCd + "Menu").find("input, select").prop("disabled", false);
		$("#frm_" + siteTyCd + "Menu").find("input:text, select").val('');

		// 2. 상위메뉴 1레벨 조회
		let menuLevel	= siteTyCd == "${SITE_TYPE_CD_FRONT}" ? 3 : 1;

		for(let nLoop = 1; nLoop <= menuLevel; nLoop++ ) {
			$("#" + siteTyCd + "UpMenuList" + nLoop).html("<option value=''>선택</option>");
		}

		fn_upMenuListHtml('1','','');

		// 3. 메뉴 목록 초기화
		menuLevel	= siteTyCd == "front" ? 4 : 2;

		let menuHtml = "";

		menuHtml += "<tr>";
		menuHtml += "		<td colspan='3'>No Data.</td>";
		menuHtml += "</tr>";

		for( let nLoop=2; nLoop<= menuLevel; nLoop++ ) {

			$("#" + siteTyCd + "MenuList" + nLoop).html(menuHtml);
		}

		fn_menuInit('1','');

	}

	/*
	 * 함수명 : fn_menuInit
	 * 설   명 : 메뉴 정보 등록 영역 리셋
	 */
	function fn_menuInit(menuLvlVal, upMenuSn) {

		// 1. 타입별 사이트 조회 후, 메뉴 셋팅
		$.blockUI();

		// 2. 검색 조건 셋팅
		let siteTyCd 		 = $("#siteTyCd").val();
		let searchUseYn	 	 = $("#" + siteTyCd + "ChkYn").is(":checked") ? "Y" : null;
		let searchMenuLvlVal = menuLvlVal != '' ? menuLvlVal : 1;

		// 3. 메뉴 리스트 조회
		$.ajax({
			url	 		: "/system/menu/getMenuList"
			, type 		: "GET"
			, dataType 	: "json"
			, data 		: {
								  "searchSiteTyCd"	 : siteTyCd
								, "searchUseYn"		 : searchUseYn
								, "searchMenuLvlVal" : searchMenuLvlVal
								, "searchUpMenuSn"	 : upMenuSn
							}
			, success 	: function(jsonData, textStatus, jqXHR) {
				$.unblockUI();

				if(jsonData.resultCode === 200){
					// 4. 조회 > 데이터 출력
					fn_menuListHtml( searchMenuLvlVal, jsonData.data );

				} else {
					alert(jsonData.resultMessage);
				}
			}
		});

	}

	/*
	 * 함수명 : fn_menuDetail
	 * 설   명 : 메뉴 선택시 상세 정보 셋팅
	 */
	function fn_menuDetail(menuSn, menuLvlVal) {

		$.blockUI();

		// 초기 작업
		let siteTyCd  = $("#siteTyCd").val();
		let menuLevel = siteTyCd == "${SITE_TYPE_CD_FRONT}" ? 4 : 2;

		let menuHtml = "";

		menuHtml += "<tr>";
		menuHtml += "		<td colspan='3'>No Data.</td>";
		menuHtml += "</tr>";

		// 2 레벨 위로 목록 제거
		for( let nLoop=menuLvlVal+2; nLoop <= menuLevel; nLoop++ ) {

			$("#" + siteTyCd + "MenuList" + nLoop).html(menuHtml);
		}

		// 0. 상세 값 셋팅
		$("#targetMenuSn").val(menuSn);

		// 1. 상세 조회
		$.ajax({
			url	 		: "/system/menu/getMenuDetail"
			, type 		: "GET"
			, dataType 	: "json"
			, data 		: {
								"targetMenuSn"	: menuSn
							}
			, success 	: function(jsonData, textStatus, jqXHR) {
				$.unblockUI();

				if(jsonData.resultCode === 200){

					// 2. 값 셋팅
					$("#"+ jsonData.data.siteTyCd + "MenuSn").val(jsonData.data.menuSn);
					$("#"+ jsonData.data.siteTyCd + "MenuNm").val(jsonData.data.menuNm);
					$("#"+ jsonData.data.siteTyCd + "MenuUrlAddr").val(jsonData.data.menuUrlAddr);
					$("#"+ jsonData.data.siteTyCd + "LinkTyCd").val(jsonData.data.linkTyCd);

					if( jsonData.data.useYn == 'Y') {
						$("#"+ jsonData.data.siteTyCd + "UseY").val('Y');
						$("#"+ jsonData.data.siteTyCd + "UseY").prop("checked", true);
					}else {
						$("#"+ jsonData.data.siteTyCd + "UseN").val('N');
						$("#"+ jsonData.data.siteTyCd + "UseN").prop("checked", true);
					}

					$("#frm_" + jsonData.data.siteTyCd + "Menu").find("input, select").prop("disabled", false);

					// 3. 상위 메뉴 변경 안됨 처리
					$('[id*=' + jsonData.data.siteTyCd + 'UpMenuList]').each( function() {
						$(this).prop("disabled", true);
					});

					// 4. 해당 상세 하위 레벨 리스트 가져오기
					fn_menuInit( jsonData.data.menuLvlVal + 1, jsonData.data.menuSn );

					// 5. 상위 메뉴 선택
					let upMenuLevel	= jsonData.data.siteTyCd == "${SITE_TYPE_CD_FRONT}" ? 3 : 1;

					if( jsonData.data.upMenuSn != null ) {

						fn_upMenuListHtml(jsonData.data.menuLvlVal - 1, jsonData.data.upMenuSn, jsonData.data.upMenuSn);

					} else {

						for( let nLoop = upMenuLevel; nLoop >= jsonData.data.menuLvlVal; nLoop-- )  {
							$("#" + jsonData.data.siteTyCd + "UpMenuList" + nLoop).html("<option value=''>선택</option>");
						}
					}

				} else {
					alert(jsonData.resultMessage);
				}
			}
		});

	 }

	/*
	 * 함수명 : fn_upMenuListHtml
	 * 설   명 : 상위 메뉴 목록 html 셋팅
	 */
	function fn_upMenuListHtml(menuLvl, menuSn, upMenu) {

		let siteTyCd 	= $("#siteTyCd").val();
		let menuLvlVal	= menuLvl == null ? 1 : menuLvl;
		let upMenuSn	= upMenu != menuSn ? upMenu : '';

		// 초기 작업
		let menuLevel	= siteTyCd == "${SITE_TYPE_CD_FRONT}" ? 3 : 1;

		for( let nLoop=menuLvlVal; nLoop <= menuLevel; nLoop++ ) {
			$("#" + siteTyCd + "UpMenuList" + nLoop).html("<option value=''>선택</option>");
		}

		$.blockUI();

		// 0. 메뉴 조회
		$.ajax({
			url	 		: "/system/menu/getMenuList"
			, type 		: "GET"
			, dataType 	: "json"
			, data 		: {
								  "searchSiteTyCd"	 : siteTyCd
								, "searchMenuLvlVal" : menuLvlVal
								, "searchUpMenuSn"	 : upMenuSn
							}
			, success 	: function(jsonData, textStatus, jqXHR) {
				$.unblockUI();

				if(jsonData.resultCode === 200){

					let data = jsonData.data;

					let upMenuHtml 	= "<option value=''>선택</option>";

					if( data.list != null ) {
						for( let i=0; i<data.list.length; i++ ) {
							if( data.list[i].menuSn == menuSn ) {

								upMenuHtml 	+= "<option data-menuLvl='"+ (data.list[i].menuLvlVal + 1) +"' data-upMenuSn='" + data.list[i].menuSn + "' value='" + data.list[i].menuSn + "' selected>" + data.list[i].menuNm + "</option>"

							} else {
								upMenuHtml 	+= "<option data-menuLvl='"+ (data.list[i].menuLvlVal + 1) +"' data-upMenuSn='" + data.list[i].menuSn + "' value='" + data.list[i].menuSn + "'>" + data.list[i].menuNm + "</option>"
							}

						}
					}

					$("#" + siteTyCd + "UpMenuList" + menuLvlVal).html(upMenuHtml);

				} else {
					alert(jsonData.resultMessage);
				}
			}
		});

	}

	/*
	 * 함수명 : fn_menuListHtml
	 * 설   명 : 메뉴 목록 html 셋팅
	 */
	function fn_menuListHtml( menuLvlVal, data ) {

		// 메뉴 레벨 설정
		let siteTyCd 	= $("#siteTyCd").val();
		let menuLevel	= siteTyCd == "${SITE_TYPE_CD_FRONT}" ? 4 : 2;
		// 메뉴 html
		let menuHtml 	= "";

		// 데이터 있는 경우
		if( data.totCnt > 0 ) {

			for( let nLoop=0; nLoop < data.list.length; nLoop++ ) {

				menuHtml += "<tr data-key='" + data.list[nLoop].menuSn + "'>";
				menuHtml += "		<td onclick='fn_menuDetail("+ data.list[nLoop].menuSn + "," + data.list[nLoop].menuLvlVal + ");' style='cursor:pointer;'>" + data.list[nLoop].menuNm + "</td>";
				menuHtml += "		<td onclick='fn_menuDetail("+ data.list[nLoop].menuSn + "," + data.list[nLoop].menuLvlVal + ");' style='cursor:pointer;'>" + data.list[nLoop].useYn  + "</td>";
				menuHtml += "		<td>";
				menuHtml += "			<button style='cursor:pointer;' onclick='fn_menuSeqUp(this);'>▲</button>";
				menuHtml +=	"			<button style='cursor:pointer;' onclick='fn_menuSeqDown(this);'>▼</button>";
				menuHtml +=	"		</td>";
 				menuHtml += "</tr>";

				$("#" + siteTyCd + "MenuList" + menuLvlVal).html(menuHtml);
			}

		} else {

			menuHtml += "<tr>";
			menuHtml += "		<td colspan='3'>No Data.</td>";
			menuHtml += "</tr>";

			$("#" + siteTyCd + "MenuList" + menuLvlVal).html(menuHtml);
		}

		for ( let j = 1; j <= menuLevel; j++ ) {

			menuHtml = "";

			// 아무 데이터도 없는 경우
			if ( $("#" + siteTyCd + "MenuList" + j + " tr").length == 0 ) {
				menuHtml += "<tr>";
				menuHtml += "		<td colspan='3'>No Data.</td>";
				menuHtml += "</tr>";

				$("#" + siteTyCd + "MenuList" + j).html(menuHtml);
			}
		}
	}

	/*
	 * 함수명 : fn_validation
	 * 설   명 : 메뉴 저장 전 유효성 검사
	 */
	function fn_validation(siteTyCd) {

		let isValid = true;

		/* 메뉴명 검사 */
		if($.trim($("#" + siteTyCd + "MenuNm").val()) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='메뉴명'/>") // '{0}는(은) 필수 입력항목입니다.'
			$("#" + siteTyCd + "MenuNm").focus();
			isValid = false;
			return false;
		}

		/* 사용여부 검사 */
		if( !$("#" + siteTyCd + "UseY").is(":checked") && !$("#" + siteTyCd + "UseN").is(":checked") ) {
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='사용여부'/>") // '{0}는(은) 필수 입력항목입니다.'
			isValid = false;
			return false;
		}

		return isValid;

	}

	/*
	 * 함수명 : fn_menuSave
	 * 설   명 : 메뉴 정보 저장
	 */
	function fn_menuSave() {

		let siteTyCd = $("#siteTyCd").val();

		// 0. 유효성 검사
		if(! fn_validation(siteTyCd)){
			return;
		}

		// 1. 저장 시작
		$.blockUI();

		let form = $("#frm_" + siteTyCd + "Menu")[0];
		let jData = new FormData(form);

		// site_ty_cd 추가
		jData.append("siteTyCd", siteTyCd);

		// 셋팅 값 추가
		let menuLvlVal 		= 1;
		let upMenuSn 		= "";
		let bestMenuSn 		= "";
		let allMenuSnVal 	= "";

		$('[id*=' + siteTyCd + 'UpMenuList]').each( function(index) {

			if( $(this).val() != '' ) {

				if( index == 0 ) {
					bestMenuSn = $(this).val();
				}

				allMenuSnVal += $(this).val() + ","
				upMenuSn = $(this).val();
				menuLvlVal++;
			}

		});

		jData.append("menuLvlVal"	, menuLvlVal);
		jData.append("upMenuSn"		, upMenuSn);
		jData.append("bestMenuSn"	, bestMenuSn);
		jData.append("allMenuSnVal"	, allMenuSnVal);

		// 2. 저장 시작
		$.ajax({
			url				: "/system/menu/saveMenu"
			, type			: "POST"
			, cache			: false
			, data			: jData
			, processData 	: false
			, contentType	: false
			, success 		: function(result) {
				$.unblockUI();

				if (result.resultCode == "200") {
					fn_afterSaveMenu(result, allMenuSnVal);
				} else {
					alert(result.resultMessage);
				}
			}
		});

	}

	/*
	 * 함수명 : fn_afterSaveMenu
	 * 설   명 : 메뉴 정보 저장 후 함수
	 */
	function fn_afterSaveMenu(result, allMenuSnVal) {

		alert(result.resultMessage);

		// 0. 저장 후 목록 재조회
		let allUpMenu = allMenuSnVal.split(',');

		if( allUpMenu.length > 1 ) {
			for( let nLoop=0; nLoop < allUpMenu.length - 1; nLoop++ ) {
				fn_menuInit(nLoop+2, allUpMenu[nLoop]);
			}
		} else {
			fn_menuInit(result.data.menuLvlVal, result.data.upMenuSn);
		}


		// 0. 저장 후 detail 조회
		fn_menuDetail(result.data.menuSn, result.data.menuLvlVal);

	}

	/*
	 * 함수명 : fn_menuSeqUp
	 * 설   명 : 순서 정렬 ( 위 )
	 */
	function fn_menuSeqUp( obj ) {

		let menuIndex = $(obj).parent().parent().index();

		// 0. 첫번째 순서인 경우
		if( menuIndex == 0 ) {
			alert("<spring:message code='result-message.messages.menu.message.sort.first'/>"); // 이미 첫번째 순서의 메뉴 입니다.
			return;
		} else {
			// 1. 메뉴 정렬 저장
			fn_menuSeqSave("up", obj);
		}
	}

	/*
	 * 함수명 : fn_menuSeqDown
	 * 설   명 : 순서 정렬 ( 아래 )
	 */
	function fn_menuSeqDown( obj ) {

		let menuTrCnt = $(obj).parent().parent().parent().find("tr").length;
		let menuIndex = $(obj).parent().parent().index() + 1;

		// 0. 마지막 순서인 경우
		if( menuTrCnt == menuIndex ) {
			alert("<spring:message code='result-message.messages.menu.message.sort.last'/>"); // 이미 마지막 순서의 메뉴 입니다.
			return;
		} else {
			// 1. 메뉴 정렬 저장
			fn_menuSeqSave("down", obj);
		}
	}

	/*
	 * 함수명 : fn_menuSeqSave
	 * 설   명 : 정렬 순서 저장
	 */
	function fn_menuSeqSave(type, obj) {

		let menuSn		= $(obj).parent().parent().attr("data-key"); 		// 현재 메뉴 번호
		let chgMenuSn;

		if ( type == "up" ) {
			chgMenuSn 	= $(obj).parent().parent().prev().attr("data-key"); // 이전 메뉴 번호
		} else {
			chgMenuSn 	= $(obj).parent().parent().next().attr("data-key"); // 다음 메뉴 번호
		}

		$.blockUI();

		// 0. 저장 시작
		$.ajax({
			url				: "/system/menu/saveMenuSeq"
			, type			: "POST"
			, dataType 		: "json"
			, data			: {
								  "menuSn" 		: menuSn
								, "chgMenuSn" 	: chgMenuSn
								, "seqType" 	: type
							}
			, success 		: function(result) {
				$.unblockUI();

				if (result.resultCode == "200") {

					if ( type == "up" ) {
						$(obj).parent().parent().prev().before($(obj).parent().parent());
					} else {
						$(obj).parent().parent().next().after($(obj).parent().parent());
					}

				} else {
					alert(result.resultMessage);
				}
			}
		});

	}

</script>

	<div id="container">

		<div class="titArea">
			<div class="location">
				<span class="ic-home"><i></i></span><span>시스템 관리</span> <em>메뉴 관리</em>
			</div>
		</div>

		<div class="div-tab">
			<input type="hidden" id="siteTyCd" 		value="" />
			<input type="hidden" id="targetMenuSn" 	value="" />

			<ul>
				<li id="tabFront" class="on"><a href="javascript:void(0);" onclick="fn_frontShow();">Frontend</a></li>
				<li id="tabBack"><a href="javascript:void(0);" onclick="fn_backShow();">Backoffice</a></li>
			</ul>
		</div>

		<!-- Frontend Area -->
		<div id="tabFrontArea">

			<div class="btn-box bot r">
				<div class="left">
					<h2 class="pop-tit2" style="margin-top: 0px;">메뉴 정보</h2>
				</div>
				<a href="javascript:void(0);" class="btn btn-default"	onclick="fn_menuSaveInit();">신규입력</a>
				<a href="javascript:void(0);" class="btn btn-blue"	onclick="fn_menuSave();">저장</a>
			</div>

			<form id="frm_frontMenu" name="frm_frontMenu" method="post">

				<input type="hidden" id="frontMenuSn" name="menuSn" value=""/>

				<div class="rows">
					<div class="rows-col5" style="padding: 10px;">
						<div class="row-tbl">
							<table>
								<colgroup>
									<col style="width:400px">
									<col>
									<col style="width:250px">
									<col style="width:250px">
								</colgroup>
								<thead>
									<tr>
										<th>
											상위메뉴 선택
										</th>
										<td colspan="2">
											&nbsp;&nbsp;1레벨
											<select class="select" style="width: 150px;" id="frontUpMenuList1" name="frontUpMenuList1">
												<option value="">선택</option>
											</select>
											&nbsp;&nbsp;2레벨
											<select class="select" style="width: 150px;" id="frontUpMenuList2" name="frontUpMenuList2">
												<option value="">선택</option>
											</select>
											&nbsp;&nbsp;3레벨
											<select class="select" style="width: 150px;" id="frontUpMenuList3" name="frontUpMenuList3">
												<option value="">선택</option>
											</select>
										</td>
									</tr>
									<tr>
										<th>
											<span class="blt_req">*</span>&nbsp;메뉴명
										</th>
										<th>
											메뉴 URL
										</th>
										<th>
											메뉴 연계 유형
										</th>
										<th>
											<span class="blt_req">*</span>&nbsp;사용여부
										</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											<input type="text" id="frontMenuNm" name="menuNm" class="input block" style="padding: 10px;"/>
										</td>
										<td>
											<input type="text" id="frontMenuUrlAddr" name="menuUrlAddr" class="input block" style="padding: 10px;"/>
										</td>
										<td>
											<cm:makeTag tagType="select" tagId="frontLinkTyCd" name="linkTyCd" code="LTC" defaultUseYn="Y" defaultNm="선택"/>
										</td>
										<td>
											<label><input type="radio" name="useYn" id="frontUseY" checked="checked" value="Y"/> Y</label>
											<label><input type="radio" name="useYn" id="frontUseN" value="N"/> N</label>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>

			</form>

			<div class="btn-box bot r" style="margin-top: 20px;">
				<div class="left">
					<h2 class="pop-tit2" style="margin-top: 0px;">메뉴 목록</h2>
				</div>
				<label><input type="checkbox" id="frontChkYn" onclick="fn_menuSaveInit();"/>&nbsp;사용중인 메뉴만 보기</label>
				<!-- <a href="javascript:alert('준비중입니다.');" class="btn btn-blue">순서저장</a> -->
			</div>


			<div class="rows">

				<div class="rows-col5" style="padding: 10px;">

					<div class="row-tbl scroll" style="height:150px;">
						<table>
							<colgroup>
								<col>
								<col style="width:150px;">
								<col style="width:100px;">
							</colgroup>
							<thead>
								<tr>
									<th>1레벨</th>
									<th>사용여부</th>
									<th>순서</th>
								</tr>
							</thead>
							<tbody id="frontMenuList1">
							</tbody>
						</table>
					</div>
				</div><!-- /row-col5 -->

				<div class="rows-col5" style="padding: 10px;">

					<div class="row-tbl scroll" style="height:150px;">
						<table>
							<colgroup>
								<col>
								<col style="width:150px;">
								<col style="width:100px;">
							</colgroup>
							<thead>
								<tr>
									<th>2레벨</th>
									<th>사용여부</th>
									<th>순서</th>
								</tr>
							</thead>
							<tbody id="frontMenuList2">

							</tbody>
						</table>
					</div>
				</div><!-- /row-col5 -->
			</div>
			<div class="rows">

				<div class="rows-col5" style="padding: 10px;">

					<div class="row-tbl scroll" style="height:150px; margin-top: 10px;">
						<table>
							<colgroup>
								<col>
								<col style="width:150px;">
								<col style="width:100px;">
							</colgroup>
							<thead>
								<tr>
									<th>3레벨</th>
									<th>사용여부</th>
									<th>순서</th>
								</tr>
							</thead>
							<tbody id="frontMenuList3">
							</tbody>
						</table>
					</div>
				</div><!-- /row-col5 -->

				<div class="rows-col5" style="padding: 10px;">

					<div class="row-tbl scroll" style="height:150px; margin-top: 10px;">
						<table>
							<colgroup>
								<col>
								<col style="width:150px;">
								<col style="width:100px;">
							</colgroup>
							<thead>
								<tr>
									<th>4레벨</th>
									<th>사용여부</th>
									<th>순서</th>
								</tr>
							</thead>
							<tbody id="frontMenuList4">
							</tbody>
						</table>
					</div>
				</div><!-- /row-col5 -->
			</div>
		</div>
		<!-- // Frontend Area -->



		<!-- Backoffice Area -->
		<div id="tabBackArea">

			<div class="btn-box bot r">
				<div class="left">
					<h2 class="pop-tit2" style="margin-top: 0px;">메뉴 정보</h2>
				</div>
				<a href="javascript:void(0);" class="btn btn-default"	onclick="fn_menuSaveInit();">신규입력</a>
				<a href="javascript:void(0);" class="btn btn-blue"	onclick="fn_menuSave();">저장</a>
			</div>

			<form id="frm_adminMenu" name="frm_adminMenu" method="post">

				<input type="hidden" id="adminMenuSn" name="menuSn" value=""/>

				<div class="rows">
					<div class="rows-col5" style="padding: 10px;">
						<div class="row-tbl">
							<table>
								<colgroup>
									<col style="width:400px">
									<col>
									<col style="width:250px">
									<col style="width:250px">
								</colgroup>
								<thead>
									<tr>
										<th>
											상위메뉴 선택
										</th>
										<td colspan="2">
											&nbsp;&nbsp;1레벨
											<select class="select" style="width: 150px;" id="adminUpMenuList1" name="adminUpMenuList1">
												<option value="">선택</option>
											</select>
										</td>
									</tr>
									<tr>
										<th>
											<span class="blt_req">*</span>&nbsp;메뉴명
										</th>
										<th>
											메뉴 URL
										</th>
										<th>
											메뉴 연계 유형
										</th>
										<th>
											<span class="blt_req">*</span>&nbsp;사용여부
										</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											<input type="text" id="adminMenuNm" name="menuNm" class="input block" style="padding: 10px;" />
										</td>
										<td>
											<input type="text" id="adminMenuUrlAddr" name="menuUrlAddr" class="input block" style="padding: 10px;" />
										</td>
										<td>
											<cm:makeTag tagType="select" tagId="adminLinkTyCd" name="linkTyCd" code="LTC" defaultUseYn="Y" defaultNm="선택" />
										</td>
										<td>
											<label><input type="radio" name="useYn" id="adminUseY" checked="checked" value="Y" /> Y</label>
											<label><input type="radio" name="useYn" id="adminUseN" value="N" /> N</label>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</form>

			<div class="btn-box bot r" style="margin-top: 20px;">
				<div class="left">
					<h2 class="pop-tit2" style="margin-top: 0px;">메뉴 목록</h2>
				</div>
				<label><input type="checkbox" id="adminChkYn" onclick="fn_menuSaveInit();" />&nbsp;사용중인 메뉴만 보기</label>
				<!-- <a href="javascript:alert('준비중입니다.');" class="btn btn-blue">순서저장</a> -->
			</div>


			<div class="rows">

				<div class="rows-col5" style="padding: 10px;">

					<div class="row-tbl scroll" style="height:150px;">
						<table>
							<colgroup>
								<col>
								<col style="width:150px;">
								<col style="width:100px;">
							</colgroup>
							<thead>
								<tr>
									<th>1레벨</th>
									<th>사용여부</th>
									<th>순서</th>
								</tr>
							</thead>
							<tbody id="adminMenuList1">
							</tbody>
						</table>
					</div>
				</div><!-- /row-col5 -->

				<div class="rows-col5" style="padding: 10px;">

					<div class="row-tbl scroll" style="height:150px;">
						<table>
							<colgroup>
								<col>
								<col style="width:150px;">
								<col style="width:100px;">
							</colgroup>
							<thead>
								<tr>
									<th>2레벨</th>
									<th>사용여부</th>
									<th>순서</th>
								</tr>
							</thead>
							<tbody id="adminMenuList2">
							</tbody>
						</table>
					</div>
				</div><!-- /row-col5 -->
			</div>

		</div>
		<!-- // Backoffice Area -->

	</div><!-- /container -->
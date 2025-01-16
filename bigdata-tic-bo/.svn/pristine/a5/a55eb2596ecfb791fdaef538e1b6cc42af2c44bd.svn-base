<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

<script type="text/javascript">

	$(document).ready(function() {

		/* 초기 작업 */
		fn_trmsListInit();

		/* trms List data 가져오기 */
		fn_getTrmsList();

	});

	/*
	 * 함수명 : fn_trmsListInit
	 * 설   명 : 초기 작업
	 */
	function fn_trmsListInit() {

		// select박스 변경으로 검색처리
		$(document).on("change", "#frm_trmsList select[name='searchTrmsTyCd']", function(){//약관 목록
			fn_getTrmsList();
		})

	}

	/*
	 * 함수명 : fn_getTrmsList()
	 * 설   명 : trms List data 가져오기
	*/
	function fn_getTrmsList() {

		/* ------ 0. html비우기 */
		$("#areaTrmsListData").html("");

		/* ------ 1. 데이터 가져오기 */
		$.blockUI();
		$.ajax({
			url	 		: "/trms/trms/getTrmsList"
			, type 		: "GET"
			, dataType 	: "json"
			, data 		: $("#frm_trmsList").serialize()
			, success 	: function(jsonData, textStatus, jqXHR) {
				$.unblockUI();

				let html = '';
				if(jsonData.resultCode === 200){

					if(jsonData.data.list !== null && jsonData.data.list.length > 0){ /* 데이터가 있을 경우 */
						// 1. 리스트 html 만들기
						$.each(jsonData.data.list, function(index, data){
							html += fn_returnTrmsListHtml(data);
						})

					} else { /* 데이터가 없을 경우 */
						html += '<tr><td class="center" colspan="4">' + jsonData.resultMessage + '</td></tr>';
					}

					//2. 리스트 뿌리기
					$("#areaTrmsListData").html(html);

				} else {
					alert(jsonData.resultMessage);
				}
			}
		});

	}

	/*
	 * 함수명 : fn_returnTrmsListHtml
	 * 설   명 : trmsList html생성
	*/
	function fn_returnTrmsListHtml(data){
		let returnHtml = "";

		/* 1. html생성*/
		returnHtml += '<tr id="trmsTr_' + data.trmsSn + '" data-trmstycd="' + data.trmsTyCd + '" >';
		returnHtml += '		<td>' + data.rownum + '</td>';
		returnHtml += '		<td style="text-align: left; cursor:pointer;" onclick="fn_openTrmsRegPopup(\'U\' , \'' + data.trmsSn + '\' )">';
		returnHtml += 			data.trmsTyNm
		returnHtml += '		</td>';
		returnHtml += '		<td style="padding: 5px;">';
		if(data.useYn === 'N'){
			returnHtml += '		<a href="javascript:void(0);" onclick="fn_chgTrmsUseYn(\'' + data.trmsSn +'\')" class="btn btn-default" style="width:80px; text-decoration:none;">' ;
			returnHtml += '			미사용';
			returnHtml += '		</a>';
		} else {
			returnHtml += '		<span class="btn btn-blue" style="width:80px; text-decoration:none; cursor:default;">' ;
			returnHtml += '			사용';
			returnHtml += '		</span>';
		}
		returnHtml += '		</td>';
		returnHtml += '		<td>' + fn_dbDateFormat(data.crtDt, '-') + '</td>';
		returnHtml += '</tr>';

		return returnHtml;
	}

	/*
	 * 함수명 : fn_openTrmsRegPopup(saveType)
	 * 설   명 : 수정, 등록 팝업
	 * @param saveType : 'U'(update) OR 'I'(insert)
	 * @param trmsSn  :수정일 경우 수정할 trmsSn
	*/
	function fn_openTrmsRegPopup(saveType, trmsSn) {

		//데이터 셋팅
		$("#frm_openTrmsPopUp #popSaveType").val(saveType);
		$("#frm_openTrmsPopUp #popTargetTrmsSn").val(trmsSn);

		// url
		let url = "/trms/trms/trmsRegPopUp";

		//name
		let name = "약관 추가 및 수정"

		// option
		let width = 850;
		let height = 800;

		/* 팝업 열기 */
		fn_openPop(url, name, width, height, "Y");

	}

	/*
	 * 함수명 : fn_chgTrmsUseYn(trmsSn)
	 * 설   명 : 사용여부 change
	*/
	function fn_chgTrmsUseYn(trmsSn) {

		if(confirm("<spring:message code='result-message.messages.common.message.data.apply' arguments='약관'/>")){ //선택한 {0}을/를 적용하겠습니까?

			$.blockUI();

			// [사용] 으로 change할 선택한 데이터
			let chgUseYTrmsSn		= trmsSn;
			let chgUseYTrmsType	= $("#trmsTr_"+trmsSn).data("trmstycd");

			// 데이터 전송
			$.ajax({
				url				: "/trms/trms/updateChgTrmsUseYn"
				, method		: "POST"
				, async			: false
				, data			: {"chgUseYTrmsSn"	: chgUseYTrmsSn
									, "chgUseYTrmsType" : chgUseYTrmsType
								  }
				, success		: function(result) {

					$.unblockUI();

					if (result.resultCode == "200") {
						fn_getTrmsList();
					} else {
						alert(result.resultMessage);
					}

				}
			});
		}

	}

</script>

<div id="container">

	<div class="titArea">
		<div class="location">
			<span class="ic-home"><i></i></span><span>약관 관리</span><em>약관 및 개인정보처리방침 관리</em>
		</div>
	</div>

	<div class="btn-box bot r">

		<form action="" method="post" name="frm_openTrmsPopUp" id="frm_openTrmsPopUp">
			<input type="hidden" name="popSaveType" 		id="popSaveType" 		value=""/> <!-- 팝업 열때 saveType 데이터 셋팅해줄 INPUT -->
			<input type="hidden" name="popTargetTrmsSn" 	id="popTargetTrmsSn" 	value=""/> <!-- 팝업 열때 trmsSn 데이터 셋팅해줄 INPUT -->
		</form>

		<form action="" method="get" name="frm_trmsList" id="frm_trmsList">
			<div class="left">
				<h2 class="pop-tit2 left" style="margin-top: 0px;">약관 목록</h2>
				<cm:makeTag tagType="select" tagId="searchTrmsTyCd" name="searchTrmsTyCd" code="TTC"/>
			</div>
		</form>
		<a class="btn btn-blue" onclick="fn_openTrmsRegPopup('I','')">추가</a>
	</div>

	<div class="row-tbl">
		<table>
			<colgroup>
				<col style="width: 100px;">
				<col>
				<col style="width: 250px;">
				<col style="width: 250px;">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>약관명</th>
					<th>사용여부</th>
					<th>등록일</th>
				</tr>
			</thead>
			<tbody id="areaTrmsListData">
			</tbody>
		</table>
	</div>

</div><!-- /container -->
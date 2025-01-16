<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

<%@ page import="com.katri.common.Const" %>
<%@ page import="com.katri.common.FileConst" %>

<c:set var="FILE_DIV_NM_POPUP_PC_IMG" 		value="<%=FileConst.File.FILE_DIV_NM_POPUP_PC_IMG%>" />
<c:set var="FILE_DIV_NM_POPUP_MOBILE_IMG" 	value="<%=FileConst.File.FILE_DIV_NM_POPUP_MOBILE_IMG%>" />
<c:set var="HMPG_CPTN_TYPE_CD_POPUP" 		value="<%=Const.Code.HmpgCptnTypeCd.POPUP_ZONE%>" />

	<script>

		$(document).ready(function() {

			// 초기 함수 호출
			fn_popupInIt();

		});

		/*
		 * 함수명 : fn_popupInIt
		 * 설   명 : 비주얼 관리 초기 함수
		 */
		function fn_popupInIt() {

			// 첫 페이지 로드 시, 비주얼 목록 조회
			fn_popupZoneList();

			// 파일 업로드 시, 유효성 체크
			$(".file_upload").on("change", function(){

				let type = $(this).attr("name");
				let chkExt = new Array();
				let imgObj 	= $(this).parent().prev().find("img");

				//1. 허용되는 확장자 넣기
				if(type == "${FILE_DIV_NM_POPUP_PC_IMG}" || type == "${FILE_DIV_NM_POPUP_MOBILE_IMG}") {
					chkExt = ["jpg", "jpeg", "png", "gif"];
				}

				//2. 파일 확장자 체크
				let files = $(this)[0].files;
				let targetExt = "";
				for(let i= 0; i<files.length; i++){

					// 파일 확장자
					targetExt = files[i].name.split('.').pop().toLowerCase();

					if(! ($.inArray(targetExt, chkExt) > -1)) {
						$(this).val("");

						fn_popupZoneImageSet(this, imgObj, type);

						alert("<spring:message code='result-message.messages.common.message.data.file.exte.error'/>");
						return false;
					}
				}

				// 3. 성공 시 이미지 태그에 넣어주기
				fn_popupZoneImageSet(this, imgObj , type);
			});

		}


		/*
		 * 함수명 : fn_popupZoneImageSet
		 * 설   명 : 이미지 등록 시 이미지 삽입
		 */
		function fn_popupZoneImageSet( input , obj , type ) {

			let imgSrc = "/images/common/default_img.jpg";

			if (input.files && input.files[0]) {

				const reader = new FileReader();

				reader.onload = function (e) {

					$(obj).attr('src', e.target.result);
				}

				reader.readAsDataURL(input.files[0]);
		} else {

			if( type == "${FILE_DIV_NM_POPUP_PC_IMG}") {

				if( $("#pcImgEncodeFileSn").val() != '' ){

					imgSrc = $("#pcImgSrc").val();
				}
			} else {

				if( $("#mobileImgEncodeFileSn").val() != '' ){

					imgSrc = $("#mobileImgSrc").val();
				}
			}

			$(obj).attr('src', imgSrc);
		}
	}

		/*
		 * 함수명 : fn_imgErrorSet
		 * 설   명 : 에러 이미지 셋팅
		 */
		function fn_imgErrorSet( obj ) {
			$(obj).attr('src', '/images/common/default_img.jpg');
		}

		/*
		 * 함수명 : fn_popupZoneReset
		 * 설   명 : 팝업 관리 초기화
		 */
		function fn_popupZoneReset() {

			// 0. 값 셋팅 및 초기화
			$("#frm_popupZone").find("input:text, input:hidden, input:file, select").val('');
			$("#frm_popupZone").find("img").attr('src', '/images/common/default_img.jpg');

			$("#hmpgCptnSn").val('');
			$("#hmpgCptnTyCd").val('${HMPG_CPTN_TYPE_CD_POPUP}');

			$("#useY").prop("checked", true);

			$("#pcImgEncodeFileSn").val('');
			$("#mobileImgEncodeFileSn").val('');

		}

		/*
		 * 함수명 : fn_popupZoneList
		 * 설   명 : 팝업 목록 조회
		 */
		function fn_popupZoneList() {

			$.blockUI();

			// 1. 검색 조건 셋팅
			let searchUseYn			= $("#chkUseYn").is(":checked") ? "Y" : null;
			let searchHmpgCptnTyCd	= $("#hmpgCptnTyCd").val();

			// 2. 비주얼 목록 조회
			$.ajax({
				url	 		: "/hmpgCptn/popupZone/getPopupList"
				, type 		: "GET"
				, dataType 	: "json"
				, data 		: {
									"searchUseYn" 			: searchUseYn
									, "searchHmpgCptnTyCd" 	: searchHmpgCptnTyCd
								}
				, success 	: function(jsonData, textStatus, jqXHR) {
					$.unblockUI();

					if(jsonData.resultCode === 200){

						// 3. 조회 > 데이터 출력
						fn_popupZoneListHTML( jsonData.data );

					} else {
						alert(jsonData.resultMessage);
					}
				}
			});

		}

		/*
		 * 함수명 : fn_popupZoneListHTML
		 * 설   명 : 팝업 목록 조회 후, HTML 출력
		 */
		function fn_popupZoneListHTML( data ) {

			let popupHTML = "";

			// 1. 데이터 있는 경우
			if( data.totCnt > 0 ) {

				for( let nLoop=0; nLoop < data.list.length; nLoop++ ) {

					popupHTML += "<tr data-key='" + data.list[nLoop].hmpgCptnSn + "'>";

					/* PC 이미지 */
					popupHTML += "		<td style='padding:10px; cursor:pointer;' onclick='fn_popupZoneDetail(" + data.list[nLoop].hmpgCptnSn + ")'>";
					popupHTML += "			<img alt='" + data.list[nLoop].hmpgCptnDescCn + "' src='" + data.list[nLoop].pcImgStrgFileFullPathAddr + "' style='width: 300px; height: 150px' onerror='fn_imgErrorSet(this);' />";
					popupHTML += "		</td>";

					/* Mobile 이미지 */
					popupHTML += "		<td style='padding:10px; cursor:pointer;' onclick='fn_popupZoneDetail(" + data.list[nLoop].hmpgCptnSn + ")'>";
					popupHTML += "			<img alt='" + data.list[nLoop].hmpgCptnDescCn + "' src='" + data.list[nLoop].mobileImgStrgFileFullPathAddr + "' style='width: 300px; height: 150px' onerror='fn_imgErrorSet(this);' />";
					popupHTML += "		</td>";

					/* 순번 */
					popupHTML += "		<td style='cursor:pointer;' onclick='fn_popupZoneDetail(" + data.list[nLoop].hmpgCptnSn + ")'>" + data.list[nLoop].srtSeq +"</td>";

					/* 사용여부 */
					popupHTML += "		<td style='cursor:pointer;' onclick='fn_popupZoneDetail(" + data.list[nLoop].hmpgCptnSn + ")'>" + data.list[nLoop].useYn +"</td>";

					/* 정렬(버튼) */
					popupHTML += "		<td>";
					popupHTML += "			<button style='cursor:pointer;' onclick='fn_popupSeqUp(this)';>▲</button>";
					popupHTML += "			<button style='cursor:pointer;' onclick='fn_popupDown(this)';>▼</button>";
					popupHTML += "		</td>";

	 				popupHTML += "</tr>";

				}

			} else {
				popupHTML += "<tr>"
				popupHTML += "		<td colspan='5'>No Data.</td>"
				popupHTML += "</tr>"

			}

			/* 목록 HTML 출력 */
			$("#popupBodyList").html(popupHTML);

		}

		/*
		 * 함수명 : fn_popupZoneDetail
		 * 설   명 : 팝업존 상세 조회
		 */
		function fn_popupZoneDetail(hmpgCptnSn) {

			$.blockUI();

			// 0. 상단 비주얼 정보 영역 초기화
			fn_popupZoneReset();

			// 1. 상세 조회 시작
			$.ajax({
				url	 		: "/hmpgCptn/popupZone/getPopupDetail"
				, type 		: "GET"
				, dataType 	: "json"
				, data 		: {
									"hmpgCptnSn" : hmpgCptnSn
								}
				, success 	: function(jsonData, textStatus, jqXHR) {
					$.unblockUI();

					if(jsonData.resultCode === 200){

						// 3. 조회 > 데이터 출력
						if( jsonData.data != null ) {


							// 0. 값 셋팅
							$("#hmpgCptnSn").val(jsonData.data.hmpgCptnSn);

							/* 3_0. * PC 파일번호, 이미지 */
							$("#pcImgEncodeFileSn").val(jsonData.data.pcImgFile.encodeFileSn);
							$("#pcImgSrc").val(jsonData.data.pcImgFile.strgFilePathAddr + jsonData.data.pcImgFile.strgFileNm);
							$("#mainVisualPcImg").attr("alt" , jsonData.data.hmpgCptnDescCn );
							$("#mainVisualPcImg").attr("src" , jsonData.data.pcImgFile.strgFilePathAddr + jsonData.data.pcImgFile.strgFileNm );


							/* 3_1. * Mobile 파일번호, 이미지 */
							$("#mobileImgEncodeFileSn").val(jsonData.data.mobileImgFile.encodeFileSn);
							$("#mobileImgSrc").val(jsonData.data.mobileImgFile.strgFilePathAddr + jsonData.data.mobileImgFile.strgFileNm);
							$("#mainVisualMobileImg").attr("alt" , jsonData.data.hmpgCptnDescCn );
							$("#mainVisualMobileImg").attr("src" , jsonData.data.mobileImgFile.strgFilePathAddr + jsonData.data.mobileImgFile.strgFileNm );

							/* 3_2. * 이미지 설명 */
							$("#hmpgCptnDescCn").val(jsonData.data.hmpgCptnDescCn);

							/* 3_3. 링크 주소 */
							$("#linkUrlAddr").val(jsonData.data.linkUrlAddr);

							/* 3_4. 링크 연계 유형 */
							$("#linkTyCd").val(jsonData.data.linkTyCd);

							/* 3_5. * 사용 여부 */
							if( jsonData.data.useYn == 'Y') {
								$("#useY").val('Y');
								$("#useY").prop("checked", true);
							}else {
								$("#useN").val('N');
								$("#useN").prop("checked", true);
							}

						}

					} else {
						alert(jsonData.resultMessage);
					}
				}
			});

		}

		/*
		 * 함수명 : fn_validation
		 * 설   명 : 팝업 정보 저장 전 유효성 검사
		 */
		function fn_validation() {

			let isValid = true;

			/* PC 이미지 검사 */
			if($("#pcImgEncodeFileSn").val() == "") {

				if($.trim($("#pcImgFile").val()) == "") {
					alert("<spring:message code='result-message.messages.common.message.required.data' arguments='PC 이미지 파일 등록'/>") // '{0}는(은) 필수 입력항목입니다.'
					$("#pcImgFile").click();
					isValid = false;
					return false;
				}
			}

			/* Mobile 이미지 검사 */
			if($("#mobileImgEncodeFileSn").val() == "") {

				if($.trim($("#mobileImgFile").val()) == "") {
					alert("<spring:message code='result-message.messages.common.message.required.data' arguments='Mobile 이미지 파일 등록'/>") // '{0}는(은) 필수 입력항목입니다.'
					$("#mobileImgFile").click();
					isValid = false;
					return false;
				}
			}

			/* 이미지 설명 검사 */
			if($.trim($("#hmpgCptnDescCn").val()) == "") {
				alert("<spring:message code='result-message.messages.common.message.required.data' arguments='이미지 설명'/>") // '{0}는(은) 필수 입력항목입니다.'
				$("#hmpgCptnDescCn").focus();
				isValid = false;
				return false;
			}

			/* 사용여부 검사 */
			if( !$("#useY").is(":checked") && !$("#useN").is(":checked") ) {
				alert("<spring:message code='result-message.messages.common.message.required.data' arguments='사용여부'/>") // '{0}는(은) 필수 입력항목입니다.'
				isValid = false;
				return false;
			}

			return isValid;

		}

		/*
		 * 함수명 : fn_popupZoneSave
		 * 설   명 : 팝업 정보 저장
		 */
		function fn_popupZoneSave() {

			// 0. 유효성 검사
			if(! fn_validation()){
				return;
			}

			// 1. 저장 시작
			$.blockUI();

			/* 1_0. 수정 인 경우, 기존 파일번호 > 삭제 번호 셋팅 */
			/* 1_0_0. pc img */
			if( $("#pcImgEncodeFileSn").val() != '' && $("#pcImgFile").val() != "" ) {

				$("#pcImgEncodeDelFileSn").val($("#pcImgEncodeFileSn").val());
			}

			/* 1_0_1. mobile img */
			if( $("#mobileImgEncodeFileSn").val() != '' && $("#mobileImgFile").val() != "" ) {

				$("#mobileImgEncodeDelFileSn").val($("#mobileImgEncodeFileSn").val());
			}

			// 1_1. 값 셋팅
			let form = $("#frm_popupZone")[0];
			let jData = new FormData(form);


			$.ajax({
				url				: "/hmpgCptn/popupZone/savePopup"
				, type			: "POST"
				, enctype		: "multipart/form-data"
				, cache			: false
				, data			: jData
				, processData 	: false
				, contentType	: false
				, success 		: function(result) {
					$.unblockUI();

					if (result.resultCode == "200") {
						fn_afterSavePopup(result);
					} else {
						alert(result.resultMessage);
					}
				}
			});
		}

		/*
		 * 함수명 : fn_afterSavePopup
		 * 설   명 : 비주얼 정보 저장 후 함수
		 */
		function fn_afterSavePopup( result ) {

			alert(result.resultMessage);

			// 0. 저장된 번호로 상세 조회
			fn_popupZoneDetail(result.data.hmpgCptnSn);

			// 1. 목록 조회
			fn_popupZoneList();

		}

		/*
		 * 함수명 : fn_popupSeqUp
		 * 설   명 : 순서 정렬 ( 위 )
		 */
		function fn_popupSeqUp( obj ) {

			let visualIndex = $(obj).parent().parent().index();

			// 0. 첫번째 순서인 경우
			if( visualIndex == 0 ) {
				alert("<spring:message code='result-message.messages.visual.message.sort.first'/>"); // 이미 첫번째 순서의 이미지 입니다.
				return;
			} else {

				// 1. 메뉴 정렬 저장
				fn_popupSeqSave("up", obj);
			}

		}

		/*
		 * 함수명 : fn_popupDown
		 * 설   명 : 순서 정렬 ( 아래 )
		 */
		function fn_popupDown( obj ) {

			let visualTrCnt = $(obj).parent().parent().parent().find("tr").length;
			let visualIndex = $(obj).parent().parent().index() + 1;

			// 0. 마지막 순서인 경우
			if( visualTrCnt == visualIndex ) {
				alert("<spring:message code='result-message.messages.visual.message.sort.last'/>"); // 이미 마지막 순서의 메뉴 입니다.
				return;
			} else {
				// 1. 메뉴 정렬 저장
				fn_popupSeqSave("down", obj);
			}

		}

		/*
		 * 함수명 : fn_popupSeqSave
		 * 설   명 : 순서 정렬 저장
		 */
		function fn_popupSeqSave(type, obj) {

			let hmpgCptnSn 		= $(obj).parent().parent().attr("data-key"); // 현재 이미지 값
			let chgHmpgCptnSn;

			if ( type == "up" ) {
				chgHmpgCptnSn 	= $(obj).parent().parent().prev().attr("data-key"); // 이전 이미지 값
			} else {
				chgHmpgCptnSn 	= $(obj).parent().parent().next().attr("data-key"); // 다음 이미지 값
			}

			$.blockUI();

			// 0. 저장 시작
			$.ajax({
				url				: "/hmpgCptn/popupZone/savePopupZoneSeq"
				, type			: "POST"
				, dataType 		: "json"
				, data			: {
									  "hmpgCptnSn" 		: hmpgCptnSn
									, "chgHmpgCptnSn" 	: chgHmpgCptnSn
									, "seqType" 		: type
								}
				, success 		: function(result) {
					$.unblockUI();

					if (result.resultCode == "200") {

						// 0. 목록 조회
						fn_popupZoneList();

					} else {
						alert(result.resultMessage);
					}
				}
			});
		}

		/*
		 * 함수명 : fn_popupZonePreviewPop
		 * 설   명 : 미리보기 팝업 호출
		 */
		function fn_popupZonePreviewPop() {

			let url 	 = "/hmpgCptn/popupZone/PopupZonePreviewPopUp";
			let name	 = "팝업존 미리보기"
			let width	 = 850;
			let height	 = 720;
			let centerYn = 'Y'

			// 팝업 호출
			fn_openPop(url, name, width, height, centerYn);
		}


	</script>

	<div id="container">

		<div class="titArea">
			<div class="location">
				<span class="ic-home"><i></i></span><span>메인화면 관리</span><em>팝업존 관리</em>
			</div>
		</div>

		<div class="btn-box bot r">
			<div class="left">
				<h2 class="pop-tit2" style="margin-top: 0px;">팝업 정보</h2>
			</div>
			<a href="javascript:void(0);" class="btn btn-default" 	onclick="fn_popupZoneReset();">신규입력</a>
			<a href="javascript:void(0);" class="btn btn-blue"		onclick="fn_popupZoneSave();">저장</a>
		</div>

		<form id="frm_popupZone" name="frm_popupZone" method="post">

			<input type="hidden" id="hmpgCptnSn" 	name="hmpgCptnSn" 	value="" />
			<input type="hidden" id="hmpgCptnTyCd" 	name="hmpgCptnTyCd" value="${HMPG_CPTN_TYPE_CD_POPUP}" />

			<div class="rows">
				<div class="rows-col5" style="padding: 10px;">
					<div class="row-tbl">
						<table>
							<colgroup>
								<col>
								<col>
								<col style="width:200px">
								<col style="width:250px">
							</colgroup>
						<thead>
							<tr>
								<th>
									<span class="blt_req">*</span>&nbsp;PC
								</th>
								<th>
									<span class="blt_req">*</span>&nbsp;Mobile
								</th>
								<th colspan="2">
									부가 정보
								</th>
							</tr>
						</thead>
							<tbody>
								<tr>
									<!-- PC 이미지 -->
									<td rowspan="8" style="padding: 10px;">
										<div style="width: 100%; height: 250px;">
											<input type="hidden" id="pcImgEncodeDelFileSn" 	name="arrDelFileSn" value="" />
											<input type="hidden" id="pcImgEncodeFileSn" 	value="" />
											<input type="hidden" id="pcImgSrc" 				value="" />

											<img id="mainVisualPcImg" alt="" src="/images/common/default_img.jpg" style="width: 100%; height: 100%"/>
										</div>

										<div style="margin-top: 15px;">
											<input type="file" id="pcImgFile" name="${FILE_DIV_NM_POPUP_PC_IMG}" class="file_upload block" style="height: 25px;" accept="image/*" >
											<p style="text-align: left; margin-top: 5px;">* jpg, png 등 이미지 파일만 등록 가능(880x620)</p>
										</div>
									</td>
								</tr>
								<tr>
									<!-- Mobile 이미지 -->
									<td rowspan="8" style="padding: 10px;">
										<div style="width: 100%; height: 250px;">
											<input type="hidden" id="mobileImgEncodeDelFileSn" 	name="arrDelFileSn" value="" />
											<input type="hidden" id="mobileImgEncodeFileSn" value="" />
											<input type="hidden" id="mobileImgSrc" 			value="" />

											<img id="mainVisualMobileImg" alt="" src="/images/common/default_img.jpg" style="width: 100%; height: 100%"/>
										</div>
										<div style="margin-top: 15px;">
											<input type="file" id="mobileImgFile" name="${FILE_DIV_NM_POPUP_MOBILE_IMG}" class="file_upload block" style="height: 25px;" accept="image/*" >
											<p style="text-align: left; margin-top: 5px;">* jpg, png 등 이미지 파일만 등록 가능(670x740)</p>
										</div>

									</td>
								</tr>
								<tr>
									<td rowspan="1">
										<span class="blt_req">*</span>&nbsp;이미지 설명
									</td>
									<td rowspan="1">
										<input type="text" class="input block" name="hmpgCptnDescCn" id="hmpgCptnDescCn">
									</td>
								</tr>
								<tr>
									<td rowspan="1">
										링크주소
									</td>
									<td rowspan="1">
										<input type="text" class="input block" name="linkUrlAddr" id="linkUrlAddr">
									</td>
								</tr>
								<tr>
									<td rowspan="1">
										링크 연계 유형
									</td>
									<td rowspan="1">
										<cm:makeTag tagType="select" tagId="linkTyCd" name="linkTyCd" code="LTC" defaultUseYn="Y" defaultNm="선택"/>
									</td>
								</tr>
								<tr>
									<td rowspan="1">
										<span class="blt_req">*</span>&nbsp;사용 여부
									</td>
									<td rowspan="1">
										<label><input type="radio" name="useYn" id="useY" value="Y" checked="checked">Y</label>
										<label><input type="radio" name="useYn" id="useN" value="N">N</label>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</form>

		<div class="btn-box bot r">
			<div class="left">
				<h2 class="pop-tit2 left">팝업 목록</h2>
			</div>
		</div>

		<div class="rows">

			<div class="rows-col5" style="padding: 10px;">

				<div style="min-height: 30px;">
					<div style="float: right;">
						<label><input type="checkbox" id="chkUseYn" onclick="fn_popupZoneList();" />&nbsp;사용중인 이미지만 보기</label>
						<a href="javascript:void(0);" class="btn btn-blue" onclick="fn_popupZonePreviewPop();">미리보기</a>
					</div>
				</div>

				<div class="row-tbl scroll" style="height:400px; margin-top: 10px;">
					<table>
						<colgroup>
							<col>
							<col>
							<col style="width:100px;">
							<col style="width:100px;">
							<col style="width:150px;">
						</colgroup>
						<thead>
							<tr>
								<th>PC</th>
								<th>Mobile</th>
								<th>순번</th>
								<th>사용여부</th>
								<th>정렬</th>
							</tr>
						</thead>
						<tbody id="popupBodyList">
							<tr>
								<td colspan="5">
									No Data.
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div><!-- /row-col5 -->

		</div>

	</div><!-- /container -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cm" uri="/WEB-INF/tlds"%>

<spring:eval var="foAddr" expression="@environment.getProperty('domain.web.fo')" />

<script type="text/javascript">
	let oEditors = [];

	const POPSAVETITLE		= opener.$("#searchMenuCptnCd option:checked").text();
	const POPMENUCPTNCD		= opener.$("#searchMenuCptnCd option:checked").val();
	const POPMENUCPTNURL	= opener.$("#searchMenuCptnCd option:checked").attr("code_val"); //미리보기시 갈 주소
	const POPSAVETYPE 		= opener.$("#frm_openMenuCptnPopUp #popSaveType").val();
	const POPTARGETTRMSSN	= opener.$("#frm_openMenuCptnPopUp #popTargetMenuCptnSn").val();

	$(document).ready(function() {

		/* 초기 작업 */
		fn_menuCptnRegPopUpInit();

	});

	/*
	 * 함수명 : fn_menuCptnRegPopUpInit
	 * 설   명 : 초기 작업
	 */
	function fn_menuCptnRegPopUpInit() {

		/* 데이터 셋팅 */
		$("#areaMenuCptnPopTitle").text(POPSAVETITLE); // 제목
		$("#frm_menuCptnReg #saveType").val(POPSAVETYPE);
		$("#frm_menuCptnReg #menuCptnSn").val(POPTARGETTRMSSN);
		$("#frm_menuCptnReg #menuCptnCd").val(POPMENUCPTNCD);

		/* UPDATE일경우 */
		if(POPSAVETYPE === 'U' && POPTARGETTRMSSN != ""){

			//1. [불러오기] 박스 안보이게
			$("#menuCptnListSelArea").attr("style", "display:none");

			//2. menuCptn data 가져오기
			fn_getMenuCptnDetail();

		}

		/* INSERT일경우 */
		if(POPSAVETYPE == 'I'){

			//1. [불러오기] 박스 보이게
			$("#menuCptnListSelArea").show();

			//2. [불러오기] 리스트 가져오기
			fn_getImportMenuCptnSelectList();
		}
	}

	/*
	 * 함수명 : fn_getMenuCptnDetail()
	 * 설   명 : menuCptn data 가져오기
	 */
	function fn_getMenuCptnDetail() {

		$.blockUI();
		$.ajax({
			url 		: "/ctnt/menuCptn/getMenuCptnDetail",
			type 		: "GET",
			dataType 	: "json",
			asyc 		: "true",
			data 		: {
				"targetMenuCptnSn" : POPTARGETTRMSSN
			},
			success : function(jsonData, textStatus, jqXHR) {
				$.unblockUI();

				if (jsonData.resultCode === 200) {

					/* 콘텐츠명 */
					$("#menuCptnNm").val(jsonData.data.menuCptnNm);

					/* 소스코드 */
					let menuCptnCn = fn_convertXss(jsonData.data.menuCptnCn);
					$("#menuCptnCn").val(menuCptnCn);

				} else {
					alert(jsonData.resultMessage);

					//창닫기
					window.close();
				}
			}
		});

	}

	/*
	 * 함수명 : fn_getImportMenuCptnSelectList()
	 * 설   명 : [불러오기] 리스트 가져오기
	 */
	function fn_getImportMenuCptnSelectList() {

		$.blockUI();
		$.ajax({
			url 		: "/ctnt/menuCptn/getImportMenuCptnSelectList",
			type 		: "GET",
			dataType 	: "json",
			asyc 		: "true",
			data 		: {
				"searchMenuCptnCd" : POPMENUCPTNCD
			},
			success : function(jsonData, textStatus, jqXHR) {
				$.unblockUI();

				let html = '';
				if (jsonData.resultCode === 200) {

					html += '<option value="">선택</option>'

					if(jsonData.data.list !== null && jsonData.data.list.length > 0){ /* 데이터가 있을 경우 */
						// 1. 리스트 html 만들기
						$.each(jsonData.data.list, function(index, data){
							html += '<option value="' + data.menuCptnSn + '">';
							html += 		data.menuCptnNm;
							html += '</option>';
						})
					}

					//2. 리스트 뿌리기
					$("#menuCptnListSelect").html(html);

				} else {
					alert(jsonData.resultMessage);

					//창닫기
					window.close();
				}
			}
		});

	}

	/*
	 * 함수명 : fn_getImportMenuCptnDetail()
	 * 설   명 : [불러오기] 눌렀을때 가져오기
	 */
	function fn_getImportMenuCptnDetail() {

		let selMenuCptnSn = $("#menuCptnListSelect").val();

		if($.trim(selMenuCptnSn) == "") {
			alert("<spring:message code='result-message.messages.common.message.error.after.select'/>") //선택 후 불러오기를 해주세요.
			$("#menuCptnListSelect").focus();
			isValid = false;
			return false;
		}

		$.blockUI();
		$.ajax({
			url 		: "/ctnt/menuCptn/getImportMenuCptnDetail",
			type 		: "GET",
			dataType 	: "json",
			asyc 		: "true",
			data 		: {
				"targetMenuCptnSn" : selMenuCptnSn
			},
			success : function(jsonData, textStatus, jqXHR) {
				$.unblockUI();

				if (jsonData.resultCode === 200) {

					/* 소스코드 */
					let menuCptnCn = fn_convertXss(jsonData.data.menuCptnCn);
					$("#menuCptnCn").val(menuCptnCn);

				} else {
					alert(jsonData.resultMessage);

					//창닫기
					window.close();
				}
			}
		});

	}

	/*
	 * 함수명 : fn_saveMenuCptn
	 * 설   명 : [등록 및 수정]
	 */
	function fn_saveMenuCptn() {

		/* ========= 1. 유효성검사 */
		if (!fn_validation()) {
			return;
		}

		$.blockUI();
		/* ========= 3. 데이터 전송 */
		$.ajax({

			url : "/ctnt/menuCptn/saveMenuCptn",
			method : "POST",
			dataType : "json",
			async : false,
			data : $("#frm_menuCptnReg").serialize(),
			success : function(result) {

				$.unblockUI();

				if (result.resultCode == "200") {
					alert("<spring:message code='result-message.messages.common.message.save'/>") //정상적으로 저장 하였습니다.
					fn_afterSaveMenuCptn(result);
				} else {
					alert(result.resultMessage);
				}

			}

		});
	}

	/*
	 * 함수명 : fn_afterSaveMenuCptn
	 * 설   명 : [등록 및 수정] 작업후
	 */
	function fn_afterSaveMenuCptn(data) {

		//부모창 menuCptn List data 다시 가져오기
		opener.fn_getMenuCptnList();

		//창닫기
		window.close();

	}

	/*
	 * 함수명 : fn_validation()
	 * 설  명  : [등록 및 수정] 시 유효성 검사 체크
	 */
	function fn_validation() {

		let isValid = true;

		// 타입
		let menuCptnCd = $("#menuCptnCd").val();
		if($.trim(menuCptnCd) == "") {
			alert("<spring:message code='result-message.messages.common.message.error'/>") //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
			isValid = false;
			return false;
		}

		// 콘텐츠명(메뉴 구성명)
		if($.trim($("#menuCptnNm").val()) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='콘텐츠명'/>") // '{0}는(은) 필수 입력항목입니다.'
			$("#menuCptnNm").focus();
			isValid = false;
			return false;
		}

		// 소스코드((메뉴 구성내용)
		if($.trim($("#menuCptnCn").val()) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='소스코드'/>") // '{0}는(은) 필수 입력항목입니다.'
			$("#menuCptnCn").focus();
			isValid = false;
			return false;
		}

		return isValid;
	}

	/*
	 * 함수명 : fn_previewMenuCptn
	 * 설   명 : [미리보기]
	 */
	function fn_previewMenuCptn() {

		if(POPMENUCPTNURL == null || POPMENUCPTNURL === '' || POPMENUCPTNURL === 'null'){

			alert("<spring:message code='result-message.messages.menucpnt.message.error.no.urladdress'/>"); //주소가 설정되어있지않습니다. [시스템관리] > [코드관리] 에서 코드값을 설정해주세요.
			return;
		}

		let name 	= "previewMenuCptn";
		let option 	= 'height=' + screen.height + ',width=' + screen.width + ',resizable=yes,scrollbars=yes,toolbar=yes,menubar=yes,location=yes';

		// 팝업 열기
		window.open('', name, option);

		// 동적폼생성
		$("#frm_previewMenuCptn").remove();
		let form = $('<form id="frm_previewMenuCptn" name="frm_previewMenuCptn"></form>');
		form.attr('action'	, '${foAddr}'+POPMENUCPTNURL);
		form.attr('method'	, 'post');
		form.attr('target'	, name)
		form.appendTo('body');

		let inputMenuCptnCn = $("<input type='hidden' name='previewMenuCptnCn' 	value='" + $("#menuCptnCn").val() + "' />");
		form.append(inputMenuCptnCn);

		// 폼보내기
		form.submit();

	}

</script>


<div id="pop-wrap">

	<h1 class="pop-tit" id="areaMenuCptnPopTitle"></h1>

	<div class="pop-contnet">

		<form action="" method="post" name="frm_menuCptnReg" id="frm_menuCptnReg">
			<div class="hd-search">

				<!-- 부모창에서 보낸 데이터-->
				<input type="hidden" name="saveType" 	id="saveType" 		value=""/>
				<input type="hidden" name="menuCptnSn" 	id="menuCptnSn" 	value=""/>
				<input type="hidden" name="menuCptnCd" 	id="menuCptnCd" 	value=""/>
				<!-- // 부모창에서 보낸 데이터-->

				<table>
					<colgroup>
						<col style="width:100px;"><col>
					</colgroup>
					<tbody>
						<tr>
							<th>
								<span class="blt_req">*</span> 콘텐츠 명
							</th>
							<td>
								<input type="text" name="menuCptnNm" id="menuCptnNm" class="input" style="width:100%;">
								<span class="blt_req">*</span>콘텐츠 관리를 위한 명칭이며, 메뉴명은 시스템관리 > 메뉴관리에서 변경이 가능합니다
							</td>
						</tr>
						<tr>
							<th>
								<span class="blt_req">*</span> 소스코드
							</th>
							<td>
								<div id="menuCptnListSelArea" style="display: none;" >
									<select class="select" id="menuCptnListSelect" style="width: 300px;">
									</select>
									<a href="javascript:void(0);" onclick="fn_getImportMenuCptnDetail();" class="btn btn-blue" >불러오기</a>
								</div>
								<textarea name="menuCptnCn" id="menuCptnCn" class="textarea block" style="height: 550px;"> </textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>

		<div class="btn-box">
			<a href="javascript:void(0);" onclick="self.close();" class="btn btn-default">취소</a>
			<a href="javascript:void(0);" onclick="fn_saveMenuCptn();" class="btn btn-blue">저장</a>
			<a href="javascript:void(0);" onclick="fn_previewMenuCptn();" class="btn btn-blue">미리보기</a>
		</div>

	</div>
	<!-- /pop-contnet -->
	<a href="javascript:void(0);" onclick="self.close();" class="pop-close"><i class="fa fa-times"></i></a>

</div>
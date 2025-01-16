<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cm" uri="/WEB-INF/tlds"%>

<!-- 네이버 스마트 에디터 -->
<script type="text/javascript" src="/smarteditor/js/service/HuskyEZCreator.js" charset="charset=UTF-8"></script>

<script type="text/javascript">
	let oEditors = [];

	const POPSAVETITLE = opener.$("#searchTrmsTyCd option:checked").text();
	const POPTRMSTYCD = opener.$("#searchTrmsTyCd option:checked").val();
	const POPSAVETYPE = opener.$("#frm_openTrmsPopUp #popSaveType").val();
	const POPTARGETTRMSSN = opener.$("#frm_openTrmsPopUp #popTargetTrmsSn").val();

	$(document).ready(function() {

		/* 초기 작업 */
		fn_trmsRegPopUpInit();

	});

	/*
	 * 함수명 : fn_trmsRegPopUpInit
	 * 설   명 : 초기 작업
	 */
	function fn_trmsRegPopUpInit() {

		// 네이버 에디터
		nhn.husky.EZCreator.createInIFrame({ 
			oAppRef: oEditors,
			elPlaceHolder: "trmsCn",
			sSkinURI: "/smarteditor/SmartEditor2Skin.html",      
			fCreator: "createSEditor2",   
			htParams : {fOnBeforeUnload : function(){}},  //alert창 띄우지 않기
			fOnAppLoad : function(){
				/* UPDATE일경우 : trms data 가져오기 */
				if(POPSAVETYPE === 'U' && POPTARGETTRMSSN != ""){
					fn_getTrmsDetail();
				}
			}
		});

		// 데이터 셋팅
		$("#areaTrmsPopTitle").text(POPSAVETITLE); // 제목
		$("#frm_trmsReg #saveType").val(POPSAVETYPE);
		$("#frm_trmsReg #trmsSn").val(POPTARGETTRMSSN);
		$("#frm_trmsReg #trmsTyCd").val(POPTRMSTYCD);
	}

	/*
	 * 함수명 : fn_getTrmsDetail()
	 * 설   명 : trms data 가져오기
	 */
	function fn_getTrmsDetail() {

		$.blockUI();
		$.ajax({
			url 		: "/trms/trms/getTrmsDetail",
			type 		: "GET",
			dataType 	: "json",
			asyc 		: "true",
			data 		: {
				"targetTrmsSn" : POPTARGETTRMSSN
			},
			success 	: function(jsonData, textStatus, jqXHR) {
				$.unblockUI();

				if (jsonData.resultCode === 200) {

					let trmsCn = jsonData.data.trmsCn;
					$("#trmsCn").html(trmsCn);
					oEditors.getById["trmsCn"].exec("LOAD_CONTENTS_FIELD", []);

				} else {
					alert(jsonData.resultMessage);

					//창닫기
					window.close();
				}
			}
		});

	}

	/*
	 * 함수명 : fn_saveTrms
	 * 설   명 : [등록 및 수정]
	 */
	function fn_saveTrms() {

		/* ========= 1. 네이버에디터 설정 */
		oEditors.getById["trmsCn"].exec("UPDATE_CONTENTS_FIELD", []);

		/* ========= 2. 유효성검사 */
		if (!fn_validation()) {
			return;
		}

		$.blockUI();
		/* ========= 3. 데이터 전송 */
		$.ajax({

			url : "/trms/trms/saveTrms",
			method : "POST",
			dataType : "json",
			async : false,
			data : $("#frm_trmsReg").serialize(),
			success : function(result) {

				$.unblockUI();

				if (result.resultCode == "200") {
					alert("<spring:message code='result-message.messages.common.message.save'/>") //정상적으로 저장 하였습니다.
					fn_afterSaveTrms(result);
				} else {
					alert(result.resultMessage);
				}

			}

		});
	}

	/*
	 * 함수명 : fn_afterSaveTrms
	 * 설   명 : [등록 및 수정] 작업후
	 */
	function fn_afterSaveTrms(data) {

		//부모창 trms List data 다시 가져오기
		opener.fn_getTrmsList();

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
		let trmsTyCd = $("#trmsTyCd").val();
		if($.trim(trmsTyCd) == "") {
			alert("<spring:message code='result-message.messages.common.message.error'/>") //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
			isValid = false;
			return false;
		}

		// 네이버에디터
		let trmsCn = $("#trmsCn").val();
		if( trmsCn == ""  || trmsCn == null || trmsCn == '&nbsp;' || trmsCn == '<p>&nbsp;</p>' || trmsCn == '<p><br></p>')  {
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='내용'/>") // '{0}는(은) 필수 입력항목입니다.'
			$("#trmsCn").focus();
			isValid = false;
			return false;
		}

		return isValid;
	}
</script>


<div id="pop-wrap">

	<h1 class="pop-tit" id="areaTrmsPopTitle"></h1>

	<div class="pop-contnet">

		<form action="" method="post" name="frm_trmsReg" id="frm_trmsReg">
			<div class="hd-search">
				<!-- 부모창에서 보낸 데이터-->
				<input type="hidden" name="saveType" 	id="saveType" 		value=""/>
				<input type="hidden" name="trmsSn" 		id="trmsSn" 		value=""/>
				<input type="hidden" name="trmsTyCd" 	id="trmsTyCd" 		value=""/>
				<!-- // 부모창에서 보낸 데이터-->

				<!-- 에디터 영역-->
				<textarea name="trmsCn" id="trmsCn"class="textarea block" style="height: 550px;"> </textarea>
				<!-- // 에디터 영역-->
			</div>
		</form>

		<div class="btn-box">
			<a href="javascript:void(0);" onclick="self.close()" class="btn btn-default">취소</a>
			<a href="javascript:void(0);" onclick="fn_saveTrms();" class="btn btn-blue">저장</a>
		</div>

	</div>
	<!-- /pop-contnet -->
	<a href="javascript:void(0);" onclick="self.close()" class="pop-close"><i class="fa fa-times"></i></a>

</div>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- 네이버 스마트 에디터 -->
<script type="text/javascript" src="/smarteditor/js/service/HuskyEZCreator.js" charset="charset=UTF-8"></script>
<script type="text/javascript">
	let oEditors = [];

	$(document).ready(function(){
		fn_inqPopInit();
	})

	function fn_inqPopInit() {

		// 네이버 에디터
		nhn.husky.EZCreator.createInIFrame({ 
			oAppRef: oEditors,
			elPlaceHolder: "inqContent",
			sSkinURI: "/smarteditor/SmartEditor2Skin.html",      
			fCreator: "createSEditor2",
			htParams : {fOnBeforeUnload : function(){}}, //alert창 띄우지 않기
			fOnAppLoad : function(){
				fn_getDetailData();
			}
		});
	}

	/*
	 *	함수명	: fn_getDetailData
	 *  설 명		: 상세 조회
	 */
	function fn_getDetailData() {
		let ntt_sn = opener.$("#num").val();

		$.ajax({
			url			: "/ctnt/inquiry/getDetailData"
		  , type		: "POST"
		  , dataType	: "json"
		  , data		: {"num" : ntt_sn }
		  , success		: function(jsonData, textStatus, jqXHR){

			  if(jsonData.resultCode == 200) {
					if(jsonData.data != null) {
						let content =  fn_convertXss(jsonData.data.nttCn);
						$("#nttCn").html(content);
						$("#crtrId").val(jsonData.data.crtrId);	//hidden으로 가지고 있는 작성자 아이디(이메일 전송 용도)
						$("#userId").val(jsonData.data.crtrId);	//화면에 보이는 작성자 아이디
						$("#crtDt").text(jsonData.data.crtDt.substring(0,10));
						$("#comnCdNm").val(jsonData.data.comnCdNm);

						//답변 상태
						if(jsonData.data.nttAnsYn == 'Y') {
							$("#nttAnsYn").text("답변 완료");
							$("#flag").val("U");	//update용 flag
						} else {
							$("#nttAnsYn").text("대기");
							$("#flag").val("N");	//신규 등록용 flag
						}
						//수정 날짜
						if(jsonData.data.nttAnsCrtDt != null) {
							$("#nttAnsCrtDt").text(jsonData.data.nttAnsCrtDt.substring(0,10));
						}

						//답변 내용
						if(jsonData.data.nttAnsCn != null) {
							$("#inqContent").html(jsonData.data.nttAnsCn);
							oEditors.getById["inqContent"].exec("LOAD_CONTENTS_FIELD", []);
						}

					}
			  	}
		 	 }
		})
	}

	/*
	 *	함수명	: fn_saveAns
	 *  설 명		: 업데이트 / 신규 등록 분기 처리
	*/
	function fn_saveAns() {

		let flag = $("#flag").val();
		let isTrue = fn_contValidation();

		if(!isTrue){
			return;
		}

		if(flag == 'N') {
			/* 신규등록용 ajax 호출 */
			fn_regInqAns();
		} else if(flag == 'U'){
			/* 업데이트용 ajax 호출 */
			fn_updateInqAns();
		}
	}

	/*
	 *	함수명	: fn_contValidation
	 *  설 명		: 필수값 입력 여부 검사
	*/
	function fn_contValidation() {
		let cont = $.trim(oEditors[0].getContents());

		if( cont == ""  || cont == null || cont == '&nbsp;' || cont == '<p>&nbsp;</p>' || cont == '<p><br></p>')  {
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='답변'/>") // '{0}는(은) 필수 입력항목입니다.'
			$("#inqContent").focus();
			return false;
		}

		return true;
	}

	/*
	 * 함수명	: fn_updateInqAns
	 * 설 명 : 1:1 문의 답변 업데이트
	*/
	function fn_updateInqAns(){

		$.ajax({
			url			: '/ctnt/inquiry/updateInquiryAns'
		  , type		: 'POST'
		  , dataType	: 'json'
		  , data		: {
			  				"nttAnsCn"	: $.trim(oEditors[0].getContents())
			  			  , "nttSn"		: opener.$("#num").val()  //부모창에 있는 문의번호
		  }
		  , success		: function(jsonData, textStatus, jqXHR) {
			  if(jsonData.resultCode == 200){
			 	alert("<spring:message code='result-message.messages.common.message.update'/>");  //정상적으로 수정하였습니다.
				location.reload();

				opener.fn_getInqList(1);
			  } else {
				  alert(jsonData.resultMessage);
			  }
		  }
		})
	}

	/*
	 * 함수명	: fn_regInqAns
	 * 설 명	: 1:1 문의 답변 신규 등록
	*/
	function fn_regInqAns() {
		 $.ajax({
			 url		: '/ctnt/inquiry/regInqAns'
		   , type		: 'POST'
		   , dataType	: 'json'
		   , data		: {
			   				"nttAnsCn"	: $.trim(oEditors[0].getContents())
	  			  		  , "nttSn"		: opener.$("#num").val()  //부모창에 있는 문의번호
	  			  		  , "crtrId"	: $("#crtrId").val()
		   	}
		   , success	: function(jsonData, textStatus, jqXHR){
			   alert("<spring:message code='result-message.messages.common.message.insert'/>"); //정상적으로 등록 하였습니다.
			   location.reload();

			   opener.fn_getInqList(1);
			  // window.close();
		   }
		 })
	 }

</script>
<input type="hidden" id="crtrId" name="crtrId" value=""/>

	<div id="pop-wrap">

		<h1 class="pop-tit">1:1 문의</h1>

		<input type="hidden" id="flag" name="flag" value=""/>

		<div class="pop-contnet">
		<!--
		<form action="" method="POST" id="frm_modifyAdmin" name="frm_modifyAdmin">
			<input type="hidden" name="_method" value="PUT"/> -->
			<div class="hd-search">
				<table>
					<colgroup>
						<col style="width:100px;">
						<col>
						<col style="width:100px;">
						<col>
					</colgroup>
					<tbody id="detailArea">
						<tr>
							<th>
								문의내용
							</th>
							<td colspan="3">
								<textarea  class="input" id="nttCn" name="nttCn" style="width:100%; height:90px; resize:none;" readonly></textarea>
							</td>
						</tr>
						<tr>
							<th>
								분류
							</th>
							<td colspan="3">
								<input type="text" id="comnCdNm" name="comnCdNm" class="input" style="width:100%;" readonly>
							</td>
						</tr>
						<tr>
							<th>
								<span class="blt_req"></span> 상태
							</th>
							<td id="nttAnsYn">
								<!-- 상태 -->
							</td>
							<th>
								<span class="blt_req"></span> 작성자
							</th>
							<td>
								<input type="text" id="userId" name="userId" class="input" style="width:100%;" readonly />
							</td>
						</tr>
						<tr>
							<th>
								<span class="blt_req"></span> 등록일
							</th>
							<td id="crtDt">
								<!-- 등록일 -->
							</td>
							<th>
								<span class="blt_req"></span> 답변일
							</th>
							<td id="nttAnsCrtDt">
								<!-- 답변일 -->
							</td>
						</tr>
						<tr>
							<th>
								<span class="blt_req">*</span> 답변
							</th>
							<td colspan="3">
								<!-- 에디터 영역-->
								<textarea name="inqContent" id="inqContent" class="textarea block"> </textarea>
								<!-- // 에디터 영역-->
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="btn-box r">
				<a href="javascript:self.close();" class="btn btn-default">취소</a>
				<a href="javascript:void(0)" class="btn btn-blue" onclick="fn_saveAns();">저장</a>
			</div>


		</div><!-- /pop-contnet -->
		<a href="javascript:self.close();" class="pop-close"><i class="fa fa-times"></i></a>
	</div><!-- /pop-wrao -->

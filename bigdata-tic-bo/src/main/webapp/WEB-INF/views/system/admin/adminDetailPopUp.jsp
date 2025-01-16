<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">

$(document).ready(function(){
	/* 초기 작업 */
	fn_adminDetailInit();

	/* 데이터 가지고 오기 */
	fn_getData(opener.$("#detailId").val());
});

/*
 * 함수명	: fn_adminDetailInit
 * 설 명 : 엔터키 처리
 */
function fn_adminDetailInit() {
	$(document).on("keydown", function(key) {
		if(key.keyCode==13)
			fn_modifyAdmin();
	});
}

/*
 * 함수명	: fn_getData
 * 설 명 : 관리자 상세 데이터 조회
 */
function fn_getData(id) {

	$.ajax({
		type		: 'POST'
	  , url			: '/system/admin/getAdminDetail'
	  , dataType	: 'json'
	  , data		: {'id': id}
	  , success		: function(jsonData, textStatus, jqXHR) {

		  	let listHtml = '';

		  	if(jsonData.resultCode == 200){
		  		if(jsonData.data != null) {

			  		/* [[1]]. 데이터 셋팅 */
					$("#mngr_id").val(jsonData.data.mngrId);
					$("#mngr_nm").val(jsonData.data.mngrNm);

					/* 1-1. 권한 리스트 html 만들어주는 함수 호출 */
					$.each(jsonData.data.authorList, function(index,data){
						listHtml += fn_makeAuthorList(data);
					});

					/* 1-2. 권한 리스트 html 추가 */
					$("#authorArea").html(listHtml);

					/* 1-3. 사용 여부에 따라 라디오 버튼 체크 처리 */
					if(jsonData.data.useYn == 'Y') {
						$("#use" + jsonData.data.useYn).prop("checked",true);
					} else {
						$("#use" + jsonData.data.useYn).prop("checked",true);
					}

					/* 1-4. 권한 번호에 따라 selected 선택 처리 */
					$("#authorArea").val(jsonData.data.authrtGrpSn).prop("selected",true);
			  	} else {
			  		html = '<tr><td class="center" colspan="4">' + jsonData.resultMessage + '</td></tr>';
			  	}
		  } else {
			  alert(jsonData.resultMessage);
		  }
	  }
});

}


 /*
  * 함수명 : fn_makeAuthorList
  * 설 명 : 권한 리스트 html 만들기
  */
function fn_makeAuthorList(data){
	let listHtml = '';
	listHtml	+=	"<option value='" + data.authrt_grp_sn  +"'>" + data.authrt_grp_nm +"</option>";

	return listHtml;
}

/*
 * 함수명 : fn_modifyAdmin
 * 설 명 : 관리자 정보 수정 ajax
 */
function fn_modifyAdmin() {
	/* 필수값 입력 여부 체크 */
	let count = fn_checkValue();

		if(count == 0) {
			$.ajax({
				type	: 'POST'
			  , url		: '/system/admin/modifyAdmin'
			  , data	: {	  'mngr_id'		: $("#mngr_id").val()
				  		 	, 'mngr_nm'		: $("#mngr_nm").val()
				  		 	, 'auth_grp_sn'	: $("select[name=authority] option:selected").val()
				  		 	, 'use_yn'		: $('input[name="use_yn"]:checked').val()
				  		   }
			 , dataType	: 'json'
			 , success	: function(jsonData, textStatus, jqXHR){
				 if(jsonData.resultCode == 200){
					 if(jsonData.data == 1) {
						 alert("정상적으로 저장되었습니다.");
						 opener.location.reload();
						 window.close();
					 }
				 } else {
					 alert(jsonData.resultMessage);
				 }

			},  error		: function(xhr,status,error) {}
		});
	 }
}

/*
 * 함수명 : fn_checkValue
 * 설 명 : 필수값 입력 여부 체크
 */
function fn_checkValue() {

	let count = 0;

	if(fn_emptyCheck($("#mngr_id").val())){
		alert("<spring:message code='result-message.messages.common.message.required.data' arguments='아이디'/>"); //아이디는 필수 입력항목입니다.
		count += 1;
	}

	if(fn_emptyCheck($("#mngr_nm").val())){
		alert("<spring:message code='result-message.messages.common.message.required.data' arguments='관리자명'/>"); //관리자명은 필수 입력항목입니다.
		count += 1;
	}

	if(fn_emptyCheck($("#authorArea").val())){
		alert("<spring:message code='result-message.messages.common.message.required.data' arguments='권한'/>"); //권한은 필수 입력항목입니다.
		count += 1;
	}

	if(!$('input:radio[name=use_yn]').is(":checked")) {
		alert("<spring:message code='result-message.messages.common.message.required.data' arguments='사용여부'/>"); //사용여부는 필수 입력항목입니다.
		count += 1;
	}

	if(count > 0) {
		return;
	}

	return count;
}

</script>


	<div id="pop-wrap">

		<h1 class="pop-tit">관리자 조회</h1>

		<div class="pop-contnet">
		<!--
		<form action="" method="POST" id="frm_modifyAdmin" name="frm_modifyAdmin">
			<input type="hidden" name="_method" value="PUT"/> -->
			<div class="hd-search">
				<table>
					<colgroup>
						<col style="width:100px;">
						<col>
					</colgroup>
					<tbody id="detailArea">
						<tr>
							<th>
								아이디
							</th>
							<td>
								<input type="text" id='mngr_id' name='mngr_id' class="input" style="width:100%;" readonly>
							</td>
						</tr>
						<tr>
							<th>
								<span class="blt_req">*</span> 관리자명
							</th>
							<td>
								<input type="text" id='mngr_nm' name='mngr_nm' class="input" style="width:100%;">
							</td>
						</tr>
						<tr>
							<th>
								<span class="blt_req">*</span> 권한
							</th>
							<td>
								<select class="select" id="authorArea" name="authority" style="width: 100%;">
									<!-- 권한 리스트 -->
								</select>
							</td>
						</tr>
						<tr>
							<th>
								<span class="blt_req">*</span> 사용여부
							</th>
							<td>
								<label><input type="radio" id='useY' name='use_yn' value="Y"> Y</label>
								<label><input type="radio" id='useN' name='use_yn' value="N"> N</label>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="btn-box r">
				<a href="javascript:self.close();" class="btn btn-default">취소</a>
				<a href="javascript:void(0)" class="btn btn-blue" onclick="fn_modifyAdmin();">저장</a>
			</div>
		<!--  </form> -->

		</div><!-- /pop-contnet -->
		<a href="javascript:self.close();" class="pop-close"><i class="fa fa-times"></i></a>
	</div><!-- /pop-wrao -->

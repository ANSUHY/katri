<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

<script type="text/javascript">

	$(document).ready(function(){
		//아이디 중복 검사 여부를 알기 위해 값을 셋팅
		$("#idValidation").val(1);

		fn_getAuthorList();

		//keyup 이벤트
		$("#mngr_id").keyup(function(event){
			$("#idValidation").val(1);
		});
	})

	/*
	 * 함수명 : fn_validation
	 * 설 명 : 유효성 검사
	 */
	function fn_adminValidation() {

		 //필수값 입력 여부 검사
		 let count = fn_checkValue();
		 if(count > 0){
			 return;
		 }

		 //아이디 유효성 검사
		 let isTrue = fn_IdValidation();
		 if(!isTrue) {
			 return;
		 }

		 //비밀번호 유효성 검사
		 let pwdTrue = fn_pwdValidation();
		 if(!pwdTrue){
			 return;
		 }

		 if($("#idValidation").val() == 1) {
			 alert("<spring:message code='result-message.messages.admin.message.id.validation'/>") //아이디 중복 체크를 해주세요.
			 return;
		 }

		 if(count == 0){
			 fn_regAdmin();
		 }
	}

	/*
	 * 함수명 : fn_IdValidation
	 * 설 명 : 아이디 유효성 검사
	 */
	function fn_IdValidation() {

		//영문+숫자 조합의 6~20자 아이디
		 const regId = /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{6,20}$/;
		 let adminId = $("#mngr_id").val();

		 if(!regId.test(adminId) && $("#mngr_id").val() !=''){
			 alert("<spring:message code='result-message.messages.user.message.duplicat.user'/>") //입력하신 ID는 사용하실 수 없는 ID 입니다.
			 $("#fn_IdValidation").val(1);
			return false;
		 }
		 return true;
	}

	/*
	 * 함수명 : fn_pwdValidation
	 * 설 명 : 비밀번호 유효성 검사
	 */
	function fn_pwdValidation() {

		 //영문+숫자+특수문자 조합의 8~20자 비밀번호
		 const regPwd = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$/;
		 let adminPwd = $("#mngr_pwd").val();

		 if(!regPwd.test(adminPwd) && $("#mngr_pwd").val() !=''){
			 alert("<spring:message code='result-message.messages.admin.message.error.password'/>"); //비밀번호가 형식에 맞지 않습니다.
			 return false;
		 }

		 return true;
	}

	/*
	 * 함수명 : fn_checkValue
	 * 설 명 :  필수값 체크 하기
	 */
	function fn_checkValue() {

		let count = 0;

		if(fn_emptyCheck($("#mngr_id").val())){
			count += 1;
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='아이디'/>") // '아이디 는(은) 필수 입력항목입니다.'
		}

		if(fn_emptyCheck($("#mngr_pwd").val())){
			count += 1;
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='비밀번호'/>") // '비밀번호 는(은) 필수 입력항목입니다.'
		}

		if(fn_emptyCheck($("#mngr_nm").val())){
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='관리자명'/>") // '관리자명 는(은) 필수 입력항목입니다.'
			count += 1;
		}

		if(fn_emptyCheck($("#auth_grp_sn").val())){
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='권한'/>") // '권한 는(은) 필수 입력항목입니다.'
			count += 1;
		}

		if(!$('input:radio[name=use_yn]').is(":checked")) {
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='사용 여부'/>") // '사용여부 는(은) 필수 입력항목입니다.'
			count += 1;
		}

		return count;
	}

	/*
	 * 함수명 : fn_regAdmin
	 * 설 명 : 관리자 등록
	 */
	 function fn_regAdmin() {

		 $.ajax({
			  url		: "/system/admin/regAdmin"
			, data		: $("#frm_regAdmin").serialize()
			, type		: "POST"
			, dataType	: "json"
			, success	: function(jsonData, textStatus, jqXHR ) {

				if(jsonData.resultCode == 200) {
					alert("<spring:message code='result-message.messages.common.message.insert'/>"); //정상적으로 등록 하였습니다.
					opener.location.reload();
					window.close();
				} else {
					alert(jsonData.resultMessge);
				}
			}
		 	, error		: function(xhr,status,error) {}
		 });

	 }

	 /*
	  * 함수명 : fn_checkId
	  * 설 명 : 아이디 중복 여부 체크
	  */
	  function fn_checkId() {

		//사용자가 입력한 id
		let id = $("#mngr_id").val();

		 //아이디 유효성 검사
		 let isTrue = fn_IdValidation();
		 if(!isTrue) {
			 return;
		 }

		if(!$("#mngr_id").val() == ''){
			$.ajax({
				url		 : "/system/admin/checkAdminId"
			  , data	 : { "adminId" : id }
			  , type	 : 'POST'
			  , dataType : "json"
			  , success	 : function(jsonData,textStatus, jqXHR) {

				  //리턴된 result가 1이나 2면 중복, 0이면 사용 가능한 아이디
					if(jsonData.data == 0) {
						 alert("<spring:message code='result-message.messages.user.message.not.duplicat.user'/>") // 입력하신 ID는 사용하실 수 있는 ID 입니다.
						//아이디 중복 여부 체크 확인을 위한 값 셋팅
						$("#idValidation").val(0);
					} else {
						 alert("<spring:message code='result-message.messages.user.message.duplicat.user'/>") //입력하신 ID는 사용하실 수 없는 ID 입니다.
						//아이디 중복 여부 체크 확인을 위한 값 셋팅
						$("#idValidation").val(1);
					}
			 	}
			 });
		} else {
			alert("아이디를 입력해주세요");
		}

	}

	  /*
	  * 함수명 : fn_getAuthorList()
	  * 설 명 : 권한 목록 조회
	  */
	  function fn_getAuthorList() {
			$.ajax({
				url			: "/system/admin/getAuthorList"
			  , type		: "POST"
			  , dataType	: "json"
			  , success		: function(jsonData, textStatus, jqXHR) {

				  let listHtml = '';

				  if(jsonData.resultCode == 200) {
					  $.each(jsonData.data, function(index,data){
							listHtml += fn_makeAuthorList(data);
						});

					  $("#auth_grp_sn").html(listHtml);
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

</script>

	<div id="pop-wrap">

		<h1 class="pop-tit">관리자 추가</h1>

		<div class="pop-contnet">
				<form action="" method="post" id="frm_regAdmin" name="frm_regAdmin">
					<div class="hd-search">
						<input type="hidden" id="idValidation" name="idValidation" value=""/>
						<table>
							<colgroup>
								<col style="width:100px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th>
										<span class="blt_req">*</span> 아이디
									</th>
									<td>
										<input type="text" class="input" style="width:80%;" id="mngr_id" name="mngr_id">
										<span id="idBtn">
											<a href="javascript:void(0);" class="btn btn-white btn-sm" id="idCheck" onclick="fn_checkId();">중복체크</a>
										</span>
										<br/>
										* 영문+숫자 중 2가지 이상의 문자를 조합하여 최소 6 ~ 20자
									</td>
								</tr>
								<tr>
									<th>
										<span class="blt_req">*</span> 패스워드
									</th>
									<td>
										<input type="password" class="input" style="width:100%;" id="mngr_pwd" name="mngr_pwd">
										<br/>
										* 영문+숫자+특수문자 각 최소 1자 이상의 문자를 조합하여 최소 8 ~ 20자
									</td>
								</tr>
								<tr>
									<th>
										<span class="blt_req">*</span> 관리자명
									</th>
									<td>
										<input type="text" class="input" style="width:100%;" id="mngr_nm" name="mngr_nm">
									</td>
								</tr>
								<tr>
									<th>
										<span class="blt_req">*</span> 권한
									</th>
									<td>
 										<select class="select" id="auth_grp_sn" name="auth_grp_sn" style="width: 100%;">
											<!-- 권한 리스트 들어가는 곳 -->
										</select>
									</td>
								</tr>
								<tr>
									<th>
										<span class="blt_req">*</span> 사용여부
									</th>
									<td>
										<label><input type="radio" id="use_yn" name="use_yn" checked="checked" value="Y"> Y</label>
										<label><input type="radio" id="use_yn" name="use_yn" value="N"> N</label>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</form>

			<div class="btn-box r">
				<a href="javascript:self.close();" class="btn btn-default">취소</a>
				<a href="javascript:void(0)" class="btn btn-blue" onclick="fn_adminValidation();">저장</a>
			</div>

		</div><!-- /pop-contnet -->
		<a href="javascript:self.close();" class="pop-close"><i class="fa fa-times"></i></a>
	</div><!-- /pop-wrao -->

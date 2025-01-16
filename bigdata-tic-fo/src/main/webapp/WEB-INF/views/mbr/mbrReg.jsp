<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
$(document).ready(function() {
	$(".checkcopy").hide();

	$("#mbrId").keyup(function(event){

		if (!(event.keyCode >=37 && event.keyCode<=40)) {

		var inputVal = $(this).val();

		$(this).val(inputVal.replace(/[^a-z0-9]/gi,''));

		}

		});

	/* $("#mbrNm").keyup(function(event){

		if (!(event.keyCode >=37 && event.keyCode<=40)) {

		var inputVal = $(this).val();

		$(this).val(inputVal.replace(/[^a-z0-9]/gi,''));

		}

		}); */
	
	$("#mbrCpNo").keyup(function(event){

		if (!(event.keyCode >=37 && event.keyCode<=40)) {

		var inputVal = $(this).val();

		$(this).val(inputVal.replace(/[^0-9]/gi,''));

		}

		});

	$("#mbrEmlAddr").keyup(function(event){
	    if (!(event.keyCode >=37 && event.keyCode<=40)) { 
	      var inputVal=$(this).val();

	      $(this).val(inputVal.replace(/[^a-z0-9@_.-]/gi,''));  
	    } 
	  });
	
		
});


function chkChar(obj){
    var RegExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+┼<>@\#$%&\'\"\\\(\=]/gi;	//정규식 구문
    if (RegExp.test(obj.value)) {
      // 특수문자 모두 제거    
      obj.value = obj.value.replace(RegExp , '');
    }
  }

function chk(obj){
    var RegExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+┼<>@\#$%&\'\"\\\(\=]/gi;	//정규식 구문
    if (RegExp.test(obj.value)) {
      // 특수문자 모두 제거    
      obj.value = obj.value.replace(RegExp , '');
    }
  }
  
var idReg = /^[A-Za-z0-9+]{6,10}$/; // 아이디 용 영문 + 숫자 정규식
var pwReg = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&\*_~])[a-zA-Z0-9!@#\$%^&\*_~]{9,12}$/; // 비밀번호 용 하나 이상의 대문자, 하나의 소문자 및 하나의 숫자 정규식
var cpReg = /^[0-9]{10,11}$/; // 전화번호 용 숫자 정규식

// 유효성 검사
function validationAction () {
	$(".checkcopy").hide();
	var count = 0;

	// 기관명
	var instId = $("#instId").val();
	if (fn_emptyCheck(instId)) {
		$("#instIdMsg").show();
		count++;
	}

	// 소속부서
	var deptNm = $("#deptNm").val();
	if (fn_emptyCheck(deptNm)) {
		$("#deptNmMsg").show();
		count++;
	}

	// 아이디
	var mbrId = $("#mbrId").val();
	if (fn_emptyCheck(mbrId)) {
		$("#mbrIdMsg01").show();
		count++;
	} else {
		if (!idReg.test(mbrId)) {
			$("#mbrIdMsg02").show();
			count++;
		}
	}

	// 비밀번호
	var mbrPwd = $("#mbrPwd").val();
	if (fn_emptyCheck(mbrPwd)) {
		$("#mbrPwdMsg01").show();
		count++;
	} else {
		if (!pwReg.test(mbrPwd)) {
			$("#mbrPwdMsg02").show();
			count++;
		}
	}

	// 비밀번호확인
	var mbrPwdChk = $("#mbrPwdChk").val();
	if (fn_emptyCheck(mbrPwdChk)) {
		$("#mbrPwdChkMsg01").show();
		count++;
	} else {
		if (mbrPwd != mbrPwdChk) {
			$("#mbrPwdChkMsg02").show();
			count++;
		}
	}

	// 이름
	var mbrNm = $("#mbrNm").val();
	if (fn_emptyCheck(mbrNm)) {
		$("#mbrNmMsg").show();
		count++;
	}

	// 이메일
	var mbrEmlAddr = $("#mbrEmlAddr").val();
	if (fn_emptyCheck(mbrEmlAddr)) {
		$("#mbrEmlAddrMsg").show();
		count++;
	}

	// 휴대전화
	var mbrCpNo = $("#mbrCpNo").val();
	if (fn_emptyCheck(mbrCpNo)) {
		$("#mbrCpNoMsg01").show();
		count++;
	} else {
		if (!cpReg.test(mbrCpNo)) {
			$("#mbrCpNoMsg02").show();
			count++;
		}
	}

	// 전송 로직
	if (count == 0) {
		$.blockUI();

		var jData = $("#frm").serialize();
		$("#frm").attr("action", "/mbr/mbrSave");
		fn_submitAjax($("#frm"), jData, fn_CallBack, 'json');
	}

}


function fn_CallBack (result) {
	$.unblockUI();

	if (result.resultCode == "200") {
		var mbrEmlAddr = $("#mbrEmlAddr").val();
		$("#userEmail").html(mbrEmlAddr);
		msgAction();
		//window.location.href = '/';
	} else {
		alert(result.resultMessage);
	}
}

function msgAction () {

	$('#register_submit').bPopup({
			onOpen: function() {$('body').addClass('scrolloff') },
			onClose: function() {
				$('body').removeClass('scrolloff');
				window.location.href = '/';
			},
	});
}



	
	/*
	 * 사업자 등록 API 호출 시작
	*/
	
	$(document).ready(function() {
		
		$("#bsnNo").keyup(function(event){
	
			if (!(event.keyCode >=37 && event.keyCode<=40)) {
				var inputVal = $(this).val();
				$(this).val(inputVal.replace(/[^0-9]/gi,''));
			}
	
		});
		
		$("#bsnStartDt").keyup(function(event){
	
			if (!(event.keyCode >=37 && event.keyCode<=40)) {
				var inputVal = $(this).val();
				$(this).val(inputVal.replace(/[^0-9]/gi,''));
			}
	
		});
	});
	
	/*
	 * 함수명 : fn_authBsnValidationChk()
	 * 설   명 : 사업자 유효성 검사
	*/
	function fn_authBsnValidationChk() {
		
		// [TODO] 저장 처리 후, 세션에 존재하는 bsnChk key값 제거하게 되면 사용
		// sessionStorage.removeItem("bsnChk");
		
		let nAuthChk	= true;
		
		/* 0. 필수값 체크 */
		let bsnNo 		= $("#bsnNo").val(); 		// 사업자 등록 번호
		let bsnStartDt 	= $("#bsnStartDt").val();	// 개업일자
		let bsnNm 		= $("#bsnNm").val();		// 대표자 성명1
		
		/* 1. 사업자 등록 번호 필수값 체크 */
		if (fn_emptyCheck(bsnNo)) {
			$("#bsnNo01").show();
			nAuthChk	= false;
		} else {
			$("#bsnNo01").hide();
		}
		
		/* 2. 개업 일자 필수값 체크 */
		if (fn_emptyCheck(bsnStartDt)) {
			$("#bsnStartDt01").show();
			nAuthChk	= false;
		} else {
			$("#bsnStartDt01").hide();
		}
		
		/* 3. 대표자 성명1 필수값 체크 */
		if (fn_emptyCheck(bsnNm)) {
			$("#bsnNm01").show();
			nAuthChk	= false;
		} else {
			$("#bsnNm01").hide();
		}
		
		return nAuthChk;
		
	}
	/*
	 * 함수명 : fn_authBsnStatus()
	 * 설   명 : 사업자 등록 번호 상태 조회
	*/
	function fn_authBsnStatus() {
		
		alert( "사업자 등록번호 상태를 조회합니다." );
		
		let bsnNo 		= $("#bsnNo").val(); // 사업자 등록 번호
		
		if( fn_authBsnValidationChk() ) {
			
			// 0. 인증키 입력 ( 예비 발급 )
			let apiKey = "pXoCEyUmIKg%2FcpRXHv1djsZcZc%2BLdd%2FF1LADb43uEbdHKFcZHkKLKQ63qRniusKsMCnSw1h4jEpirWMyYueXgw%3D%3D";
			
			// 1. 상태 조회할 사업자 번호
			let data = {
				"b_no"	: 
					[
						bsnNo	// * 사업자 등록 번호 ('-' 제거)
					]
			};
		
			// 3. url + 인증키 셋팅
			let strUrl = "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=" + apiKey; 
			
			// 4. 사업자 번호 상태 조회 API 호출
			$.ajax({
				
				beforeSend	: function (request) {}, // 공통 js - $.ajaxSetup에서 자동 셋팅되어, 값 비우기
				url			: strUrl,
				type		: "POST",
				data		: JSON.stringify(data), // json 을 string으로 변환하여 전송
				dataType	: "JSON",
				contentType	: "application/json",
				accept		: "application/json",
				success: function(result) {
					
					// console.log(result);
					if( result.status_code === "OK" ) {
						
						if( result.data[0].tax_type_cd != "" ) { // Valid
							
							alert( result.data[0].tax_type );
							// 현재 세션 key : "bsnChk" > "Y" 담기
							// [TODO] 정책에 따라 수정 
							sessionStorage.setItem("bsnChk", "Y");
							
						} else { // Invalid
							
							alert( result.data[0].tax_type );
							sessionStorage.setItem("bsnChk", "N");
							
						} 
					}
					
				},
				
				error: function(result) {
					
					// console.log( result.responseText ); // responseText의 에러메세지 확인
					alert( "<spring:message code='result-message.messages.common.message.error'/>" );
					
				}
			});
			
		}
	}
	
	 /*
	 * 함수명 : fn_authBsnValid()
	 * 설   명 : 사업자 등록 번호 진위 여부 조회
	*/
	function fn_authBsnValid() {
		
		alert( "사업자 진위여부를 확인합니다." );
		
		let bsnNo 		= $("#bsnNo").val(); 		// 사업자 등록 번호
		let bsnStartDt 	= $("#bsnStartDt").val();	// 개업일자
		let bsnNm 		= $("#bsnNm").val();		// 대표자 성명1
		
		if( fn_authBsnValidationChk() ) {
			
			// 0. 인증키 입력 ( 예비 발급 )
			let apiKey = "pXoCEyUmIKg%2FcpRXHv1djsZcZc%2BLdd%2FF1LADb43uEbdHKFcZHkKLKQ63qRniusKsMCnSw1h4jEpirWMyYueXgw%3D%3D";
			
			// 1. 진위 여부 조회할 사업자 정보
			let data = {
						"businesses": 
							[
								{
								  "b_no"	: bsnNo			// * 사업자 등록 번호 ('-' 제거)
								, "start_dt": bsnStartDt	// * 개업일자 (YYYYMMDD 포맷)
								, "p_nm"	: bsnNm			// * 대표자성명1
								, "p_nm2"	: ""			// 대표자성명2 - 대표자성명1 이 한글이 아닌 경우, 이에 대한 한글명
								, "b_nm"	: ""			// 상호 (Optional)
								, "corp_no"	: ""			// 법인등록번호 (Optional)
								, "b_sector": ""			// 주업태명 (Optional)
								, "b_type"	: ""			// 주종목명 (Optional)
								}
							]
					};
			
			// 3. url + 인증키 셋팅
			let strUrl = "https://api.odcloud.kr/api/nts-businessman/v1/validate?serviceKey=" + apiKey; 
			
			// 4. 사업자 번호 상태 조회 API 호출
			$.ajax({
				
				beforeSend	: function (request) {}, // 공통 js - $.ajaxSetup 자동 셋팅되어, 값 비우기
				url			: strUrl,
				type		: "POST",
				data		: JSON.stringify(data), // json 을 string으로 변환하여 전송
				dataType	: "JSON",
				contentType	: "application/json",
				accept		: "application/json",
				success		: function(result) {
					
					// console.log(result);
					if( result.status_code === "OK" ) {
						
						if( result.data[0].valid === "01" ) { // Valid
							
							alert( "등록된 사업자 진위 확인 완료" );
							// 현재 세션 key : "bsnChk" > "Y" 담기 
							// [TODO] 정책에 따라 수정 
							sessionStorage.setItem("bsnChk", "Y");
						
						} else { // Invalid
							
							alert( result.data[0].valid_msg );
							sessionStorage.setItem("bsnChk", "N");
						} 
					}
					
				},
				
				error: function(result) {
					
					// console.log( result.responseText ); // responseText의 에러메세지 확인
					alert( "<spring:message code='result-message.messages.common.message.error'/>" );
					
				}
			});
		}
	}
	
	/*
	 * 사업자 등록 API 호출 종료
	*/
	

</script>
<div id="container">
	<div class="subVisual">
		<span>회원가입</span>
	</div>

	<section>
		<div class="contentsInner">
			<div class="register">
				<div class="mem_copy">필수 입력사항입니다.</div>

				<form action="" method="post" id="frm" onsubmit="return flase;">
					<table class="register_type">
						<colgroup>
							<col width="14%">
							<col width="*">
						</colgroup>
						<tbody>
							<tr>
								<th><span>기관명</span></th>
								<td>
									<select name="instId" id="instId" style="width: 340px;">
										<option value="">선택</option>

										<c:forEach items="${certInstList}" var="item">
											<option value="${item.comnCd}">${item.comnCdNm}</option>
										</c:forEach>

									</select>
									<div class="checkcopy register" id="instIdMsg">기관을 선택하세요.</div>
								</td>
							</tr>
							<tr>
								<th><span>소속부서</span></th>
								<td>
									<input type="text" name="deptNm" id="deptNm" placeholder="소속부서를 입력해주세요" onkeyup="chkChar(this)"/>
									<div class="checkcopy register" id="deptNmMsg">소속부서를 입력하세요.</div>
								</td>
							</tr>
							<tr>
								<th><span>아이디</span></th>
								<td>
									<input type="text" name="mbrId" id="mbrId" maxlength="10" placeholder="영문+숫자 6~10자 이내"/>
									<div class="checkcopy register" id="mbrIdMsg01">아이디를 입력하세요.</div>
									<div class="checkcopy register" id="mbrIdMsg02">6자 이상의 영문+숫자를 입력해 주세요.</div>
								</td>
							</tr>
							<tr>
								<th><span>비밀번호</span></th>
								<td>
									<input type="password" name="mbrPwd" id="mbrPwd" maxlength="12" placeholder="영문+숫자+특수문자(!@#$) 혼합하여 9~12자 이내">
									<div class="checkcopy register" id="mbrPwdMsg01">비밀번호를 입력하세요.</div>
									<div class="checkcopy register" id="mbrPwdMsg02">9자 이상의 영문+숫자+특수문자(!@#$%^&*_~)를 입력해주세요.</div>
								</td>
							</tr>
							<tr>
								<th><span>비밀번호 확인</span></th>
								<td>
									<input type="password" name="mbrPwdChk" id="mbrPwdChk" placeholder="영문+숫자+특수문자(!@#$) 혼합하여 9~12자 이내">
									<div class="checkcopy register" id="mbrPwdChkMsg01">비밀번호를 한번 더 입력하세요.</div>
									<div class="checkcopy register" id="mbrPwdChkMsg02">비밀번호가 일치하지 않습니다.</div>
								</td>
							</tr>
							<tr>
								<th><span>이름</span></th>
								<td>
									<input type="text" name="mbrNm" id="mbrNm" placeholder="이름을 입력해주세요." onkeyup="chkChar(this)">
									<div class="checkcopy register" id="mbrNmMsg">이름을 입력하세요.</div>
								</td>
							</tr>
							<tr>
								<th><span>이메일</span></th>
								<td>
									<input type="text" name="mbrEmlAddr" id="mbrEmlAddr" placeholder="이메일을 입력해주세요."  >
									<div class="checkcopy register" id="mbrEmlAddrMsg">이메일을 입력하세요.</div>
								</td>
							</tr>
							<tr>
								<th><span>휴대전화</span></th>
								<td><input type="text" name="mbrCpNo" id="mbrCpNo" placeholder="-(하이픈) 없이 입력(예 : 01012345678)">
									<div class="checkcopy register" id="mbrCpNoMsg01">휴대전화를 입력하세요.</div>
									<div class="checkcopy register" id="mbrCpNoMsg02">-(하이픈)" 없이 입력해 주세요.(예 : 01012345678)</div>
								</td>
							</tr>
						</tbody>
					</table>
					
					<!-- 사업자 조회 영역 시작 -->
					<div class="mem_copy" style="padding-top: 10px;">필수 입력사항입니다.</div>
					
					<table class="register_type">
						<colgroup>
							<col width="14%">
							<col width="*">
						</colgroup>
						<tbody>
							<tr>
								<th><span>사업자 등록 번호</span></th>
								<td>
									<input type="text" name="bsnNo" id="bsnNo" placeholder="-(하이픈) 없이 입력(예 : 101234567890)">
									<div class="checkcopy register" id="bsnNo01">사업자 등록 번호 입력 (예 : 101234567890)</div>
								</td>
							</tr>
							
							<tr>
								<th><span>개업 일자</span></th>
								<td>
									<input type="text" name="bsnStartDt" id="bsnStartDt" placeholder="구분자 없이 입력(예 : 20220101)">
									<div class="checkcopy register" id="bsnStartDt01">개업 일자 입력 (예 : 20220101)</div>
								</td>
							</tr>
							
							<tr>
								<th><span>대표자 성명</span></th>
								<td>
									<input type="text" name="bsnNm" id="bsnNm" placeholder="대표자 성명" maxlength="15">
									<div class="checkcopy register" id="bsnNm01">대표자 성명 입력</div>
								</td>
							</tr>
							
						</tbody>
					</table>
					<!-- 사업자 조회 영역 종료 -->
				</form>
				
				<!-- 사업자 조회 버튼 시작 -->
				<div style="margin-top: 40px; text-align: center;">
					<span><button class="button_type list" onclick="fn_authBsnStatus();">사업자 상태 조회</button></span>
					<span><button class="button_type list" onclick="fn_authBsnValid();">사업자 진위 여부 조회</button></span>
				</div>
				<!-- 사업자 조회 버튼 종료 -->
				
				<div class="buttonframe block t_c mt20">
					<button class="button_type memberegister" id="submit" onclick="validationAction();">가입하기</button>
				</div>
			</div>
		</div>
	</section>
</div>

<!-- popUpload-->
<div class="popup register_submit" id="register_submit">
	<div class="pop pop-con">
			<a href="#a" class="close b-close">닫기</a>
			<div class="inner">
				<p><strong>회원가입이 완료되었습니다.</strong></p>
				<p>승인까지 3일정도 소요됩니다.</p>
				<!-- <p><span id="userEmail">OOOO@OOOO.kr</span>로 연락드리겠습니다.</p> -->
				<p>(승인 관련 문의처 : 031-538-9588)</p>
				<button class="button_type submit mt31 b-close">확인</button>
			</div>
	</div>
</div>
<!-- /popUpload-->

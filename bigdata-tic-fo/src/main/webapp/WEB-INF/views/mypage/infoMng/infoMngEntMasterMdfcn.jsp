<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

<%@ page import="com.katri.common.Const" %>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<style>

	/* 관심키워드 > select2 -> PC인 경우 */
	@media screen and (min-width:1440px){
		.cate-wr .selectlgc {
			margin-right: var(--w-10);
			width:0%;
		}
		.cate-wr .select2-container {
			width: calc((100% - var(--w-20)) / 3) !important;
		}
		.cate-wr .select2-container .select2-selection--single{
			height: var(--w-45);
		}
		.cate-wr .select2-container--default .select2-selection--single .select2-selection__rendered {
			line-height: var(--w-45);
		}
		.cate-wr .select2-container--default .select2-selection--single .select2-selection__arrow {
			height: var(--w-45);
			line-height: var(--w-45);
		}
		.select2-container--default .select2-search--dropdown .select2-search__field {
			height: 35px;
		}
	}

	/* 관심키워드 > select2 -> Mobile인 경우 */
	@media screen and (max-width:1440px) {
		.cate-wr .selectlgc {
			margin-bottom: var(--w-10);
			width:100%;
		}
		.cate-wr .select2-container {
			width: 100% !important;
		}
		.cate-wr .select2-container .select2-selection--single{
			height: var(--w-40);
		}
		.cate-wr .select2-container--default .select2-selection--single .select2-selection__rendered {
			line-height: var(--w-40);
		}
		.cate-wr .select2-container--default .select2-selection--single .select2-selection__arrow {
			height: var(--w-40);
			line-height: var(--w-40);
		}
		.select2-container--default .select2-search--dropdown .select2-search__field {
			height: 30px;
		}
	}

</style>

<script type="text/javascript">

	//영문+숫자+특수문자 조합의 8~20자 비밀번호
	const regPwd = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$/;

	// 숫자 형식 체크
	$(document).on("keyup", '.number', function(event){

		if (!(event.keyCode >=37 && event.keyCode<=40)) {
			var inputVal = $(this).val();
			$(this).val(inputVal.replace(/[^0-9]/gi,''));
		}
	});

	$(document).ready(function() {

		// [비밀번호 변경] 클릭 시, modal 값 비우기
		$('.btn-pw-chg').click(function(){
			$("#frm_userPwd #chgUserPwd").val('');
			$("#frm_userPwd #chgUserPwdChk").val('');
		})

		// [이메일 변경] 클릭 시,
		$('.bt-chg-mail').click(function(){

			// readonly 해제 x
			$('.inp-chg-mail').prop('readonly', true);

			// 변경 값 화면 셋팅
			if( ! $('.bt-chg-mail').hasClass('add') ) {

				$('.chg-mail-chk').addClass('active');
				$('.bt-chg-mail').addClass('add');
				$(".bt-chg-mail").html('이메일 변경 취소');

			} else {

				$('.chg-mail-chk').removeClass('active');
				$('.bt-chg-mail').removeClass('add');
				$(".bt-chg-mail").html('이메일 변경');
				$("#emlAddrVal").val("${userInfoData.encptEmlAddrVal}");

			}
		})

		// 정보관리 페이지 초기 셋팅
		fn_infoMngInit();

	});

	/*
	 * 함수명 : fn_infoMngInit
	 * 설 명 : 정보관리 페이지 초기 셋팅
	*/
	function fn_infoMngInit(){

		// 이메일 주소 초기값 셋팅
		$("#chkEmlAddr").val( $("#selEmlAddr option:checked").text() );

		// 이메일 주소 변경 시, 값 처리
		$(document).on("change", '#selEmlAddr', function(){

			let selAddr = $(this).val();

			// 직접 입력인 경우
			if( selAddr == "etc" ) {
				$("#chkEmlAddr").prop("readonly", false);
				$("#chkEmlAddr").val('');
			} else {
				$("#chkEmlAddr").prop("readonly", true);
				$("#chkEmlAddr").val( $("#selEmlAddr option:checked").text() );
			}

		});

		// 관심 키워드 대분류 값 변경 시,
		$(document).on("change", "select[id^='kw_']", function(){

			let upComnCd = $(this).val();
			let selId	 = $(this).attr("id");
			let selName	 = $(this).attr("name");
			let DpcnChk	 = true;

			// 해당 대분류 코드 중복 체크 확인
			if( upComnCd != '' ) {
				$("select[id^='kw_']").each( function(index) {
					if( selId != $(this).attr("id") ) {
						if( upComnCd == $(this).val() ) {
							alert("<spring:message code='result-message.messages.join.message.keyword.duplicate.error'/>"); // '중복된 키워드입니다. 다시 확인해 주세요.'
							DpcnChk = false;
						}
					}
				});
			}

			// 중복체크 후, 해당 대분류 코드 중분류 값 조회 후 값 셋팅
			if( DpcnChk ) {
				fn_mlclfCdList(upComnCd, selName);
			} else {
				$(this).val("").select2();
				fn_mlclfCdList("", selName);
			}

		});

		// 관심 키워드 중분류 값 변경 시,
		$(document).on("change", "select[id^='stdLgclfCd']", function(){

			let comnCd 	= $(this).val();
			let selId	= $(this).attr("id");
			let DpcnChk	= true;

			// 해당 중분류 코드 중복 체크 확인
			if( comnCd != '' ) {
				$("select[id^='stdLgclfCd']").each( function(index) {
					if( selId != $(this).attr("id") ) {
						if( comnCd == $(this).val() ) {
							alert("<spring:message code='result-message.messages.join.message.keyword.duplicate.error'/>"); // '중복된 키워드입니다. 다시 확인해 주세요.'
							DpcnChk = false;
						}
					}
				});
			}

			// 중복체크 후, 중복되면 해당 중분류 코드 비우기
			if( !DpcnChk ) {
				$(this).val("").select2();
			}
		});

		// 관심키워드 1 노출
		if( "" != "${userInfoData.stdLgclfCd1}") {
			fn_mlclfCdList("${userInfoData.stdLgclfCd1}", "stdLgclfCd1", "stdMlclfCd1");
		}

		// 관심키워드 2 노출
		if( "" != "${userInfoData.stdLgclfCd2}") {
			fn_mlclfCdList("${userInfoData.stdLgclfCd2}", "stdLgclfCd2", "stdMlclfCd2");
		}

		// 이메일 값 변경 시 - [메일:인증확인] 체크값 변경
		$("#chkEmlAddrId").on("propertychange change paste input", function() {
			$("#emailCertYn").val('N');
		});
		$("#chkEmlAddr").on("propertychange change paste input", function() {
			$("#emailCertYn").val('N');
		});
		$("#selEmlAddr").on("change", function() {
			$("#emailCertYn").val('N');
		});

		// 관심키워드 1,2 > select2 변경
		for( let i=1; i<=2; i++ ) {
			$("#kw_stdLgclfCd" + i).select2();
			for( let j=1; j<=3; j++ ) {
				$("#stdLgclfCd" + i + "_0" + j).select2();
			}
		}

	}

	/*
	 * 함수명 : fn_mlclfCdList
 	 * 설 명 : 코드 중분류 값 목록 조회
	*/
	function fn_mlclfCdList(upComnCd, selName, selMlcName) {

		// 0. 조회 시작
		$.blockUI();

		$.ajax({
			url	 		: "/join/getMlclfCdList"
			, type 		: "GET"
			, dataType 	: "json"
			, data 		: {
							"upCd" 	: upComnCd
						}
			, success 	: function(jsonData, textStatus, jqXHR) {
				$.unblockUI();

				if(jsonData.resultCode === 200){

					// 1. 조회 > 데이터 출력
					let codeHtml = "<option value=''>중분류 선택하세요</option>";

					$.each(jsonData.data.list, function(index, data){
						codeHtml += "<option value='" + data.stdMlclfCd + "'>";
						codeHtml += data.stdMlclfNm;
						codeHtml += "</option>";
					});

					// 2. select box에 html 출력
					$("select[id^='" + selName +"']").html(codeHtml);
					$("select[id^='" + selName +"']").select2();

					// 3. select value 셋팅
					for(let nLoop=1; nLoop <=3; nLoop++ ) {
						let selVal = $("#" + selMlcName + "_0" + nLoop).val();
						if( selVal != "" ) {
							$("#"+ selName +"_0"+ nLoop).val(selVal).select2();
						}
					}

				} else {
					alert(jsonData.resultMessage);
				}
			}
		});

	}

	/*
	 * 함수명 : fn_openEntAddressPop
	 * 설 명 : 사업장 주소 [우편번호] 클릭 시, api 호출
	 */
	function fn_openEntAddressPop() {

		new daum.Postcode({

			// 주소 선택 시,
			oncomplete: function(data) {
				// 우편 번호 셋팅
				$("#entGrpZip").val( data.zonecode );
				// 기본 주소 셋팅
				$("#entGrpBasAddr").val( data.address );
				// 상세 주소 초기화
				$("#entGrpDaddr").val('');
				// 모달창 닫기
				$(".md-rect .btn-close").click();
			}
			, width: '100%'
			, height: '100%'

		}).embed($(".zip-wr").get(0));

	}

	/*
	 * 함수명 : fn_openAddressPop
	 * 설 명 : 근무지 주소 [우편번호] 클릭 시, api 호출
	 */
	function fn_openAddressPop() {

		new daum.Postcode({

			// 주소 선택 시,
			oncomplete: function(data) {
				// 우편 번호 셋팅
				$("#wrcZip").val( data.zonecode );
				// 기본 주소 셋팅
				$("#wrcBasAddr").val( data.address );
				// 상세 주소 초기화
				$("#wrcDaddr").val('');
				// 모달창 닫기
				$(".md-rect .btn-close").click();
			}
			, width: '100%'
			, height: '100%'

		}).embed($(".zip-wr").get(0));

	}

	/*
	 * 함수명 : fn_pwdValidation
	 * 설 명 : 비밀번호 유효성 검사
	 */
	function fn_pwdValidation() {

		let isValid = true;

		let userPwd 	= $("#chgUserPwd").val();
		let userPwdChk	= $("#chgUserPwdChk").val();

		/* 새 비밀번호 검사 */
		if($.trim(userPwd) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data2' arguments='새 비밀번호'/>"); // '{0}를(을) 입력해주세요.'
			$("#chgUserPwd").focus();
			isValid = false;
			return false;
		}

		/* 새 비밀번호 확인 검사 */
		if($.trim(userPwdChk) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data2' arguments='새 비밀번호 확인'/>"); // '{0}를(을) 입력해주세요.'
			$("#chgUserPwdChk").focus();
			isValid = false;
			return false;
		}

		/* 새 비밀번호 형식 검사 */
		if( !regPwd.test(userPwd) ){
			alert("<spring:message code='result-message.messages.join.message.pwd.pattern.error'/>"); // '비밀번호는 8~20자의 영문, 숫자, 특수문자 중 3가지 이상의 문자를 조합하여 사용할 수 있습니다.'
			$("#chgUserPwd").focus();
			isValid = false;
			return false;
		}

		/* 새 비밀번호 일치 검사 */
		if( userPwd != userPwdChk ) {
			alert("<spring:message code='result-message.messages.join.message.pwd.nomatch.error'/>"); // '비밀번호가 일치하지 않습니다.'
			$("#chgUserPwdChk").focus();
			isValid = false;
			return false;
		}

		return isValid;
	}

	/*
	 * 함수명 : fn_userPwdMdfcn
	 * 설 명 : 비밀번호 저장
	 */
	function fn_userPwdMdfcn() {

		// 0. 유효성 검사
		if(! fn_pwdValidation() ){
			return;
		}

		// 1. 저장 시작
		$.blockUI();

		let form = $("#frm_userPwd")[0];
		let jData = new FormData(form);

		$.ajax({
			url				: "/mypage/infoMng/saveUserPwdChg"
			, type			: "POST"
			, cache			: false
			, data			: jData
			, processData 	: false
			, contentType	: false
			, success 		: function(result) {
				$.unblockUI();

				if (result.resultCode == "200") {
					// 2. 저장 성공 메시지 후, 모달 창 닫기
					alert(result.resultMessage);
					$(".btn-close").click();
				} else {
					alert(result.resultMessage);
				}
			}
		});
	}

	/*
	 * 함수명 : fn_emailValidation
	 * 설 명 : 이메일 유효성 검사
	*/
	function fn_emailValidation() {

		let isValid = true;

		// 이메일 주소 검사
		if($.trim($("#chkEmlAddrId").val()) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data2' arguments='이메일 주소'/>") // '{0}를(을) 입력해주세요.'
			$("#chkEmlAddrId").focus();
			isValid = false;
			return false;
		}

		// 이메일 선택 검사
		if($.trim($("#selEmlAddr").val()) == "") {
			alert("<spring:message code='result-message.messages.join.message.select.required.checked.data' arguments='이메일'/>") // '{0}를(을) 선택해 주세요.'
			$("#selEmlAddr").focus();
			isValid = false;
			return false;
		}

		// 직접 입력 시,
		if($.trim($("#chkEmlAddr").val()) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data2' arguments='직접입력할 주소'/>") // '{0}를(을) 입력해주세요.'
			$("#chkEmlAddr").focus();
			isValid = false;
			return false;
		}

		return isValid;
	}

	/*
	 * 함수명 : fn_emailCert
	 * 설 명 : 이메일 인증
	*/
	function fn_emailCert() {

		/* 0. 유효성 검사 */
		if( ! fn_emailValidation() ) {
			return;
		}

		/* 1. 메일 보내기 */
		$.blockUI();

		let emlAddrId 	= $("#chkEmlAddrId").val();
		let emlAddr 	= $("#chkEmlAddr").val();

		let rcvrEmlAddr	 = emlAddrId + "@" + emlAddr;

		$.ajax({
			url	 		: "/join/certMailSend"
			, type 		: "POST"
			, dataType 	: "json"
			, data 		: { "rcvrEmlAddr" : rcvrEmlAddr }
			, success 	: function(result) {
				$.unblockUI();

				if (result.resultCode == "200") {
					// 메일 인증 발송 성공 시,
					if (result.data) {
						alert(result.resultMessage.replaceAll("\\n" , "\\\n").replaceAll("\\", ""));
					}
				} else {
					alert(result.resultMessage.replaceAll("\\n" , "\\\n").replaceAll("\\", ""));
				}
			}
		});

	}

	/*
	 * 함수명 : fn_emailCertNoChk
	 * 설 명 : 이메일 인증 번호 확인
	*/
	function fn_emailCertNoChk() {

		/* 0. 유효성 검사 */
		if( ! fn_emailValidation() ) {
			return;
		}

		if($.trim($("#chkCertNo").val()) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data2' arguments='이메일 인증번호'/>") // '{0}를(을) 입력해주세요.'
			$("#chkCertNo").focus();
			return;
		}

		/* 1. 확인 시작 */
		$.blockUI();

		let emlAddrId 	= $("#chkEmlAddrId").val();
		let emlAddr 	= $("#chkEmlAddr").val();

		let rcvrEmlAddr	= emlAddrId + "@" + emlAddr;
		let chkCertNo	= $("#chkCertNo").val();

		$.ajax({
			url	 		: "/join/certNoChk"
			, type 		: "POST"
			, dataType 	: "json"
			, data 		: {
							"rcvrEmlAddr" : rcvrEmlAddr
							, "chkCertNo" 	: chkCertNo
							}
			, success 	: function(result) {

				$.unblockUI();

				if (result.resultCode == "200") {
					// 비교한 값
					if ( result.data ) {
						alert(result.resultMessage);
						$("#emailCertYn").val('Y');
						$("#emlAddrId").val(emlAddrId);
						$("#emlAddr").val(emlAddr);
						$("#emlAddrVal").val(rcvrEmlAddr);
					} else {
						alert(result.resultMessage);
						$("#emailCertYn").val('N');
						$("#emlAddrId").val('');
						$("#emlAddr").val('');
						$("#chkCertNo").focus();
					}
				} else {
					alert(result.resultMessage);
				}
			}
		});
	}

	/*
	 * 함수명 : fn_userInfoMdfcnValidation
	 * 설 명 : 회원 정보 수정 전 유효성 검사
	*/
	function fn_userInfoMdfcnValidation() {

		let isValid = true;

		// 0. 사업장 주소 검사 - (우편번호) 검사
		if($.trim($("#entGrpZip").val()) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data2' arguments='사업장 주소'/>"); // '{0}를(을) 입력해주세요.'
			$("#entGrpZip").focus();
			fn_openEntAddressPop(); // 우편번호 검색 팝업창 호출
			isValid = false;
			return false;
		}

		// 1. 사업장 주소 검사 - (기본주소) 검사
		if($.trim($("#entGrpBasAddr").val()) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data2' arguments='사업장 주소'/>"); // '{0}를(을) 입력해주세요.'
			$("#entGrpBasAddr").focus();
			fn_openEntAddressPop(); // 우편번호 검색 팝업창 호출
			isValid = false;
			return false;
		}

		// 2. 사업장 주소 검사 - (상세주소) 검사
		if($.trim($("#entGrpDaddr").val()) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data2' arguments='사업장 상세주소'/>"); // '{0}를(을) 입력해주세요.'
			$("#entEntGrpDaddr").focus();
			isValid = false;
			return false;
		}

		// 3. 부서명 검사
		if($.trim($("#userDeptNm").val()) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data2' arguments='부서명'/>"); // '{0}를(을) 입력해주세요.'
			$("#userDeptNm").focus();
			isValid = false;
			return false;
		}

		// 4. 직급명 검사
		if($.trim($("#userJbgdNm").val()) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data2' arguments='직급'/>"); // '{0}를(을) 입력해주세요.'
			$("#userJbgdNm").focus();
			isValid = false;
			return false;
		}

		// 5. 회사 전화번호 검사
		if($.trim($("#wrcTelno").val()) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data2' arguments='회사 전화번호'/>"); // '{0}를(을) 입력해주세요.'
			$("#wrcTelno").focus();
			isValid = false;
			return false;
		}

		// 6. 근무지 주소 - (우편번호) 검사
		if($.trim($("#wrcZip").val()) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data2' arguments='근무지 주소'/>"); // '{0}를(을) 입력해주세요.'
			$("#wrcZip").focus();
			fn_openAddressPop(); // 우편번호 검색 팝업창 호출
			isValid = false;
			return false;
		}

		// 7. 근무지 주소 - (기본주소) 검사
		if($.trim($("#wrcBasAddr").val()) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data2' arguments='근무지 주소'/>"); // '{0}를(을) 입력해주세요.'
			$("#wrcBasAddr").focus();
			fn_openAddressPop(); // 우편번호 검색 팝업창 호출
			isValid = false;
			return false;
		}

		// 8. 근무지 주소 - (상세주소) 검사
		if($.trim($("#wrcDaddr").val()) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data2' arguments='근무지 상세주소'/>"); // '{0}를(을) 입력해주세요.'
			$("#wrcDaddr").focus();
			isValid = false;
			return false;
		}

		// 9. 이름 검사
		if($.trim($("#userNm").val()) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data2' arguments='이름'/>"); // '{0}를(을) 입력해주세요.'
			$("#userNm").focus();
			isValid = false;
			return false;
		}

		// 10. 휴대폰 검사
		if($.trim($("#mblTelnoVal").val()) == "") {
			alert("<spring:message code='result-message.messages.common.message.required.data2' arguments='휴대폰 번호'/>"); // '{0}를(을) 입력해주세요.'
			$("#mblTelnoVal").focus();
			isValid = false;
			return false;
		}

		// 11. 회사 이메일 검사
		if( $(".bt-chg-mail").hasClass("add") ) {

			// 11_1. 이메일 검사
			if( ! fn_emailValidation() ) {
				return false;
			}

			// 11_2. 이메일 인증확인 검사
			if( "Y" != $("#emailCertYn").val() ) {
				alert("<spring:message code='result-message.messages.join.message.cert.email.required.checked'/>"); // '이메일 인증을 진행해 주세요.'
				$("#chkCertNo").focus();
				isValid = false;
				return false;
			}

			// 11_3. 이메일 인증확인 후 셋팅 값 검사
			if( $("#chkEmlAddrId").val() != $("#emlAddrId").val() ) {
				alert("<spring:message code='result-message.messages.join.message.cert.email.required.checked'/>"); // '이메일 인증을 진행해 주세요.'
				$("#chkCertNo").focus();
				isValid = false;
				return false;
			}

		}

		// 12. 관심 키워드1 하위 선택 검사
		if( $("#kw_stdLgclfCd1").val() != "" ) {
			if( ! fn_keyWordStdValidation("select[name='arrStdMlclfCd1']") ) {
				$("#kw_stdLgclfCd1").focus();
				isValid = false;
				return false;
			}
		}

		// 13. 관심 키워드2 하위 선택 검사
		if( $("#kw_stdLgclfCd2").val() != "" ) {
			if( ! fn_keyWordStdValidation("select[name='arrStdMlclfCd2']") ) {
				$("#kw_stdLgclfCd2").focus();
				isValid = false;
				return false;
			}
		}

		return isValid;
	}

	/*
	 * 함수명 : fn_keyWordStdValidation
	 * 설 명 : 관심 키워드 대분류 선택시 중분류 유효성 검사
	*/
	function fn_keyWordStdValidation( obj ) {

		let isValid = true;

		let chkObj	= $(obj);
		let chkCnt	= 0;

		$(chkObj).each( function(index) {
			if( $(this).val() != "" ) {
				chkCnt++;
			}
		});

		if( chkCnt == 0 ) {
			alert("<spring:message code='result-message.messages.join.message.select.required.checked.data' arguments='관심키워드의 중분류'/>") // '{0}를(을) 선택해 주세요.'
			isValid = false;
			return false;
		}

		return isValid;
	}

	/*
	 * 함수명 : fn_userInfoMdfcn
	 * 설 명 : 회원 정보 수정
	*/
	function fn_userInfoMdfcn() {

		// 0. 유효성 검사
		if(! fn_userInfoMdfcnValidation() ){
			return;
		}

		// 1. 저장 시작
		$.blockUI();

		let form = $("#frm_userMdfcn")[0];
		let jData = new FormData(form);

		let emlAddrId 	= $("#emlAddrId").val();
		let emlAddr 	= $("#emlAddr").val();
		let emlAddrVal	= "";

		if( $("#emailCertYn").val() === 'Y' ) {
			emlAddrVal	= emlAddrId + "@" + emlAddr;
		} else {
			emlAddrVal	= $("#emlAddrVal").val();
		}

		jData.append("emlAddrVal", emlAddrVal);

		$.ajax({
			url				: "/mypage/infoMng/saveUserInfoMdfcn"
			, type			: "POST"
			, cache			: false
			, data			: jData
			, processData 	: false
			, contentType	: false
			, success 		: function(result) {
				$.unblockUI();

				if (result.resultCode == "200") {

					// 2. 저장 성공 메시지 후, 재 조회
					alert(result.resultMessage);

					// 3. 메인 페이지 이동
					window.location.href = "/";
/*
					// 4. 페이지 새로고침
					if( $("#frm_userMdfcnSucess").val() == "" ) {
						$("#frm_userMdfcnSucess").val("${returnUrl}");
					}
					$("#frm_userMdfcnSucess").attr("action", "/mypage/infoMng/infoMngMdfcn");
					$("#frm_userMdfcnSucess").submit();
 */
				} else {
					alert(result.resultMessage);
				}
			}
		});

	}

</script>

	<!-- ===== header ====== -->
	<header id="header">
		<div id="sub-mv" class="sub-mypage">
			<div class="inner">
				<h2>마이페이지</h2>
				<div class="sub-obj">오브젝트</div>
			</div>
		</div>
	</header>
	<!-- ===== /header ====== -->

	<!-- ===== 주소 찾기 modal ====== -->
	<div class="zip-md">
		<div class="md-rect">
			<a href="javascript:void(0);" class="btn-close">닫기</a>
			<h3 class="md-tit">우편번호 검색</h3>
			<div class="zip-wr">

			</div>
		</div>
	</div>

	<!-- 수정 성공 form -->
	<form id="frm_userMdfcnSucess" name="frm_userMdfcnSucess" method="post">
		<input type="hidden" name="infoMngPageNm" id="infoMngPageNm" value="${returnUrl}" />
	</form>

	<!-- ===== 비밀번호 변경 modal ====== -->
	<form id="frm_userPwd" name="frm_userPwd" method="post">
		<div class="pw-chg-md">
			<input type="hidden" name="targetUserId" id="targetUserId" value="${userInfoData.userId }"/>
			<div class="md-rect">
				<a href="javascript:void(0);" class="btn-close">닫기</a>
				<h3 class="md-tit">비밀번호 변경하기</h3>
				<div class="chg-wr">
					<h4 class="tit"><span>새 비밀번호</span></h4>
					<input type="password" placeholder="비밀번호 입력" id="chgUserPwd" name="chgUserPwd" maxlength="20" />
					<p class="cau-txt">영문, 숫자, 특수문자 중 3가지 이상의 문자를 조합하여 최소 8 ~ 20자 이하로 설정</p>
					<h4 class="tit"><span>새 비밀번호 확인</span></h4>
					<input type="password" placeholder="비밀번호 입력" id="chgUserPwdChk" name="chgUserPwdChk" maxlength="20" />
				</div>
				<div class="btn-wr">
					<a href="javascript:void(0);" class="btn md01" onclick="fn_userPwdMdfcn();">변경하기</a>
				</div>
			</div>
		</div>
	</form>

	<!-- ===== container ====== -->
	<div id="container">

		<!--container-->
		<form id="frm_userMdfcn" name="frm_userMdfcn" method="post">

			<input type="hidden" name="emailCertYn"		id="emailCertYn"	value="N"/>
			<input type="hidden" name="emlAddrId"		id="emlAddrId"		value="" />
			<input type="hidden" name="emlAddr"			id="emlAddr"		value="" />
			<input type="hidden" name="entGrpSn"		id="entGrpSn"		value="${userInfoData.entGrpSn}" />

			<div id="cont" class="cont-mypage join-insert mypage-modify"><!--회원정보관리 클래스 추가-->
				<!--tit-->
				<div class="cont-agree-tit">
					<h2 class="tit">회원정보 수정</h2>
					<a href="/mypage/infoMng/infoMngWhdwlApply" class="btn bt-out" style="min-width: var(--w-120);" >회원탈퇴 신청</a>
				</div>

				<!--입력 form-->
				<h4 class="tit"><span>회원유형</span></h4>
				<input type="text" readonly value="기업(마스터)회원" class="inp-readonly wd100">

				<h4 class="tit"><span>상호명</span></h4>
				<input type="text" readonly value="${userInfoData.entNm}" 	 class="inp-readonly wd100" />

				<h4 class="tit"><span>개업일</span></h4>
				<input type="text" readonly value="${userInfoData.opbizYmd}" class="inp-readonly wd100" />

				<h4 class="tit"><span>대표자명</span></h4>
				<input type="text" readonly value="${userInfoData.rprsvNm}"	 class="inp-readonly wd100" />

				<h4 class="tit"><span>사업자 등록번호</span></h4>
				<div class="input-wr">
					<input type="text" readonly value="${userInfoData.brno}" class="inp-readonly wd1287" />
				</div>

				<h4 class="tit"><span>그룹명</span></h4>
				<div class="input-wr">
					<input type="text" readonly value="${userInfoData.entGrpNm}" class="inp-readonly wd1287" />
				</div>

				<h4 class="tit"><span>사업장 주소</span></h4>
				<div class="input-wr">
					<input type="text" value="${userInfoData.entGrpZip}" id="entGrpZip" name="entGrpZip" class="wd-zip number" maxlength="10" />
					<button type="button" class="btn bt-chk wd-zip-btn" onclick="fn_openEntAddressPop();">우편번호 검색</button>
				</div>
				<div class="input-wr">
					<input type="text" value="${userInfoData.entGrpBasAddr}" id="entGrpBasAddr"	name="entGrpBasAddr" class="inp-readonly wd-addr1" readonly />
					<input type="text" value="${userInfoData.entGrpDaddr}"	 id="entGrpDaddr"	name="entGrpDaddr"	 class="wd-addr2" maxlength="30" />
				</div>

				<h4 class="tit"><span>부서명</span></h4>
				<input type="text" value="${userInfoData.userDeptNm}" class="wd100" id="userDeptNm" name="userDeptNm" maxlength="20" />

				<h4 class="tit"><span>직급</span></h4>
				<input type="text" value="${userInfoData.userJbgdNm}" class="wd100" id="userJbgdNm" name="userJbgdNm" maxlength="20" />

				<h4 class="tit"><span>회사 전화번호</span></h4>
				<div class="input-wr">
					<input type="text" value="${userInfoData.wrcTelno}" class="wd100 number" id="wrcTelno" name="wrcTelno" maxlength="13" />
				</div>

				<h4 class="tit"><span>근무지 주소</span></h4>
				<div class="input-wr">
					<input type="text" value="${userInfoData.wrcZip}" class="wd-zip number" id="wrcZip" name="wrcZip" maxlength="10" />
					<button type="button" class="btn bt-chk wd-zip-btn" onclick="fn_openAddressPop();">우편번호 검색</button>
				</div>
				<div class="input-wr">
					<input type="text" value="${userInfoData.wrcBasAddr}"	class="wd-addr1" id="wrcBasAddr" name="wrcBasAddr" readonly />
					<input type="text" value="${userInfoData.wrcDaddr}"		class="wd-addr2" id="wrcDaddr"	 name="wrcDaddr" maxlength="30" />
				</div>

				<h4 class="tit"><span>아이디</span></h4>
				<div class="input-wr">
					<input type="text" id="userId" name="targetUserId" value="${userInfoData.userId}" class="wd100 inp-readonly" readonly />
				</div>

				<h4 class="tit"><span>비밀번호</span></h4>
				<button type="button" class="btn bt-chk01 btn-pw-chg">비밀번호 변경</button>

				<h4 class="tit"><span>이름</span></h4>
				<input type="text" value="${userInfoData.userNm}" class="wd100" id="userNm" name="userNm" maxlength="50" />

				<h4 class="tit"><span>휴대폰</span></h4>
				<input type="text" value="${userInfoData.encptMblTelnoVal}" class="wd100 number" id="mblTelnoVal" name="mblTelnoVal" maxlength="20" />

				<h4 class="tit"><span>회사 이메일</span></h4>
				<div class="input-wr">
					<input type="text" id="emlAddrVal" readonly value="${userInfoData.encptEmlAddrVal}" class="inp-readonly wd1284 inp-chg-mail">
					<button type="button" class="btn bt-chk01 wd146 bt-chg-mail">이메일 변경</button>
				</div>
				<p class="cau-txt01"><i></i>이메일 변경 시 재인증이 필요합니다.</p>

				<div class="chg-mail-chk">
					<h4 class="tit"><span>새 이메일</span></h4>
					<div class="input-wr">
						<input type="text" class="wd720" id="chkEmlAddrId" name="chkEmlAddrId" maxlength="30"/><span class="wd25">@</span>
						<cm:makeTag cls="select" tagType="select" tagId="selEmlAddr" name="selEmlAddr" code="EML"/>
						<input type="text" class="inp-readonly wd285 ml10" id="chkEmlAddr" name="chkEmlAddr" maxlength="30" readonly />
						<button type="button" class="btn bt-chk"onclick="fn_emailCert();">메일인증</button>
					</div>
					<p class="cau-txt">메일 인증 받을 이메일 주소를 기재해주세요. 등록한 이메일로 발송된 인증번호 인증이 된 후 사용이 가능합니다.</p>

					<h4 class="tit"><span>새 이메일 인증</span></h4>
					<div class="input-wr">
						<input type="text" id="chkCertNo" name="chkCertNo" class="wd1335 number" maxlength="20" placeholder="이메일로 발송된 인증번호를 입력하세요.">
						<button type="button" class="btn bt-chk" onclick="fn_emailCertNoChk();">인증확인</button>
					</div>
					<p class="cau-txt">이메일이 수신되지 않았을 경우 스팸메일함을 확인해주세요.</p>
				</div>

				<h4 class="tit type-c"><span>관심 키워드1</span></h4>
				<div class="cate-wr">
					<p class="stit">대분류</p>
					<select class="select" id="kw_stdLgclfCd1" name="stdLgclfCd1">
						<option value="">대분류를 선택하세요</option>
						<c:forEach items="${lstPrdt}" var="item" varStatus="status">
							<c:choose>
								<c:when test="${item.stdLgclfCd eq userInfoData.stdLgclfCd1}">
									<option value="${item.stdLgclfCd}" selected="selected">${item.stdLgclfNm}</option>
								</c:when>
								<c:otherwise>
									<option value="${item.stdLgclfCd}">${item.stdLgclfNm}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>

					<p class="stit">중분류</p>
					<c:forEach items="${userInfoData.lstUserPrdt}" var="item" varStatus="status">
						<c:if test="${userInfoData.stdLgclfCd1 eq item.stdLgclfCd }">
							<input type="hidden" id="stdMlclfCd1_0${item.srtSeq }" value="${item.stdMlclfCd }" />
						</c:if>
					</c:forEach>

					<c:forEach begin="0" end="2" varStatus="status">
						<select class="select wd30" id="stdLgclfCd1_0${status.count }" name="arrStdMlclfCd1">
							<option value="">중분류 선택하세요</option>
						</select>
						<div class="selectlgc">
						</div>
					</c:forEach>
				</div>

				<h4 class="tit type-c"><span>관심 키워드2</span></h4>
				<div class="cate-wr">
					<p class="stit">대분류</p>
					<select class="select" id="kw_stdLgclfCd2" name="stdLgclfCd2">
						<option value="">대분류를 선택하세요</option>
						<c:forEach items="${lstPrdt}" var="item" varStatus="status">
							<c:choose>
								<c:when test="${item.stdLgclfCd eq userInfoData.stdLgclfCd2}">
									<option value="${item.stdLgclfCd}" selected="selected">${item.stdLgclfNm}</option>
								</c:when>
								<c:otherwise>
									<option value="${item.stdLgclfCd}">${item.stdLgclfNm}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>

					<p class="stit">중분류</p>
					<c:forEach items="${userInfoData.lstUserPrdt}" var="item" varStatus="status">
						<c:if test="${userInfoData.stdLgclfCd2 eq item.stdLgclfCd }">
							<input type="hidden" id="stdMlclfCd2_0${item.srtSeq }" value="${item.stdMlclfCd }" />
						</c:if>
					</c:forEach>

					<c:forEach begin="0" end="2" varStatus="status">
						<select class="select wd30" id="stdLgclfCd2_0${status.count }" name="arrStdMlclfCd2">
							<option value="">중분류 선택하세요</option>
						</select>
						<div class="selectlgc">
						</div>
					</c:forEach>
				</div>

				<h4 class="tit"><span>계정발급 신청서</span></h4>
				<div class="info-rect tBr">
					<ul class="file-ul">
						<c:forEach items="${userInfoData.lstAcntIssuAplyFile}" var="fileData" varStatus="stat">
							<li>
								<a href="/file/downloadFile?encodeFileSn=${fileData.encodeFileSn}">
									${fileData.orgnlFileNm}
								</a>
							</li>
						</c:forEach>
					</ul>
				</div>

				<h4 class="tit"><span>사업자 등록증</span></h4>
				<div class="info-rect tBr">
					<ul class="file-ul">
						<c:forEach items="${userInfoData.lstGnrlCertFile}" var="fileData" varStatus="stat">
							<li>
								<a href="/file/downloadFile?encodeFileSn=${fileData.encodeFileSn}">
									${fileData.orgnlFileNm}
								</a>
							</li>
						</c:forEach>
					</ul>
				</div>

				<h4 class="tit"><span>재직증명서 첨부</span></h4>
				<div class="info-rect tBr">
					<ul class="file-ul">
						<c:forEach items="${userInfoData.lstBzmnRegFile}" var="fileData" varStatus="stat">
							<li>
								<a href="/file/downloadFile?encodeFileSn=${fileData.encodeFileSn}">
									${fileData.orgnlFileNm}
								</a>
							</li>
						</c:forEach>
					</ul>
				</div>

				<div class="btn-wr">
					<a href="javascript:void(0);" class="btn" onclick="fn_userInfoMdfcn();">정보 수정하기</a>
				</div>

			</div>
		</form>
	</div>
	<!-- ===== /container ====== -->
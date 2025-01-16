<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" 		uri="/WEB-INF/tlds" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>


<script type="text/javascript">
	$(document).ready(function(){
		/* 회원 메일 주소 가져오기 */
		fn_getUserMail();
	});

	/*
	 * 함수명	: fn_getUserMail(
	 * 설 명	: 휴면 회원 메일 주소 가져오기 (마스킹 처리)
	 */
	function fn_getUserMail() {
		$.ajax({
			url			: "/auth/drmncy/getUserMail"
		  , type		: "POST"
		  , dataType	: "json"
		  , data		: {"userId" : $("#userId").val()}
		  , success		: function(jsonData) {
			  if(jsonData.resultCode === 200 && jsonData.data != null){
				  //마스킹 처리 된 메일
				  $("#userMail").val(jsonData.data.mailAddress);
				  //전체 메일 주소 (hidden으로 셋팅)
				  $("#email").val(jsonData.data.strEncemlAddr);
			  }
		  }
		});
	}


	/*
	 * 함수명	: fn_updateDrmncy(
	 * 설 명	: PASS 인증 회원 휴면 상태 해제
	 */
	 function fn_changeDrmncy(pCi) {
		 $.ajax({
			 url		: "/auth/drmncy/updateDrmncy"
		   , type		: "POST"
		   , dataType	: "json"
		   , data		: { "userId" : $("#userId").val(),
			   				"pCi"	 : pCi }
		   , success	: function(jsonData) {

				if(jsonData.resultCode === 200) {
					if(jsonData.data === 1 ){
						/* 휴면 해제 성공 */
						$(location).attr('href', '/auth/drmncy/drmncyAfter');
					}
				} else {
					/* 휴면 해제 실패 */
					alert(jsonData.resultMessage); //입력한 정보와 일치하는 회원정보가 없습니다.
				}
		   }

		 });
	 }

	 /*
		 * 함수명	: fn_changeDrmncyWithMail()
		 * 설 명	: 메일 인증 회원 휴면 상태 해제
		 */
		function fn_changeDrmncyWithMail() {
			$.ajax({
				url			: "/auth/drmncy/updateDrmncyWithMail"
			  , type		: "POST"
			  , dataType	: "json"
			  , data		: { "userId" : $("#userId").val() }
			  , success		: function(jsonData, textStatus, jqXHR) {

				  if(jsonData.resultCode == 200) {
						if(jsonData.data == 1 ){
							/* 휴면 해제 성공 */
							$(location).attr('href', '/auth/drmncy/drmncyAfter');
						}
					} else {
							/* 휴면 해제 실패 */
							alert(jsonData.resultMessage);
					}
			  }
			})
		}


	/*
	 * 함수명	: fn_sendMail
	 * 설 명	: 휴면 해제 인증 메일 발송
	 */
	 function fn_sendDrmncyMail() {
		 $.ajax({
			 url		: "/auth/drmncy/sendDrmncyMail"
		   , type		: "POST"
		   , dataType	: "json"
		   , data		: {"rcvrEmlAddr" : $("#email").val()}
		   , success	: function(jsonData) {
			   if(jsonData.resultCode == 200 && jsonData.data === true){
				   alert("<spring:message code='result-message.messages.auth.drmncy.mail.send.success'/>");  //'메일이 발송 되었습니다.'
			   } else {
				   alert(jsonData.resultMessage); //5분 동안 발송 제한 횟수 5회를 초과하여 메일 발송이 제한되었습니다.
			   }
		   }
		 })
	 }

	/*
	 * 함수명	: fn_certNoCheck
	 * 설 명 : 인증 번호 유효성 검사
	 */
	 function fn_certNoCheck() {

		 /*----- 빈값 여부 검사 -------*/
		 if(fn_emptyCheck($("#certNoArea").val())) {
			 alert("<spring:message code='result-message.messages.auth.drmncy.mail.cert.no.data'/>");  //'인증번호가 누락되었습니다. 입력해 주세요.'
			 $("#certNoArea").focus();
			 return;
		 }

		 /*-------- 인증 번호 확인 ----------*/
		 $.blockUI();

		 const emlAddrVal = $("#email").val();       //이메일 주소
		 const certNo	  = $("#certNoArea").val(); //입력한 인증 번호
		 const userId	  = $("#userId").val();		//유저 아이디

		 $.ajax({
			 url		: "/auth/drmncy/certNoCheck"
		   , type		: "POST"
		   , dataType	: "json"
		   , data		: {  "emlAddrVal" : emlAddrVal
			   			   , "certNo" 	  : certNo
			   			   , "userId"	  : userId }
		   , success	: function(jsonData) {
			   $.unblockUI();

			   if(jsonData.resultCode == 200 && jsonData.data != null){
				  if(jsonData.data == true) {
					  /* 인증 번호 일치 */
					  fn_changeDrmncyWithMail();
				  } else {
					  /* 인증 번호 불일치 */
					  alert(jsonData.resultMessage) //입력하신 인증번호가 불일치 합니다.
				  }
			   } else {
				   alert(jsonData.resultMessage); //'인증 확인 횟수 3회를 초과하였습니다. 이메일 인증 번호를 재전송하여 인증해야 합니다.'
			   }
		   }
		 });
	 }



	/**
	 * 휴대폰 인증 콜백
	 */
	 function fn_nicePassSuccessCallBack(  pReqSeq	, pResSeq, pAuthType	, pName	, pEncName
				, pBirthdate, pGender, pNationalinfo, pDi	, pCi
				, pMobileNo	, pMobileCo , nResultCode ) {

		if(nResultCode == 0) {
			fn_changeDrmncy(pCi);
		}
	}

	/**
	 * 휴대폰 인증
	 */
	function fn_checkPlusSafeModel(){
		window.name ="Parent_window";

		window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
		document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
		document.form_chk.target = "popupChk";
		document.form_chk.submit();
	}

</script>

	<input type="hidden" id="userId" name="userId" value="${tryLoginUserDetail.userId}"/>
	<input type="hidden" id="email"  name="email"  value=""/>

    <!-- ===== header ====== -->
    <header id="header">
        <div id="sub-mv" class="sub-mem">
            <div class="inner">
                <h2>로그인</h2>
                <div class="sub-obj">오브젝트</div>
            </div>
        </div>
    </header>
    <!-- ===== /header ====== -->


	<!-- ===== container ====== -->
	<div id="container">

		<c:choose>
			<c:when test="${tryLoginUserDetail.userTyCd eq 'MTC002' or tryLoginUserDetail.userTyCd eq 'MTC004' or tryLoginUserDetail.userTyCd eq 'MTC005'}">
		        <div id="cont" class="cont-login">
		          <h2 class="tit">로그인</h2>
		          <h3 class="tit">회원님의 아이디는 휴면 상태로 전환되었습니다.</h3>
		          <article class="info-rect tBr">
		            <p>회원님은 로그인 한 지 1년 이상이 지나 아이디가 휴면 상태로 전환되었습니다.
		              본인확인 후 다시 시험인증 빅데이터 플랫폼 사용이 가능합니다.</p>
		            <dl>
		              <dt id="last_drmncy_dt"> 휴면 전환 일자 : ${tryLoginUserDetail.lastDrmncyDt} </dt>
		              <dd>본인인증 버튼을 통해 분리보관된 개인정보에 대한 복원이 진행됩니다.<dd>
		            </dd>
		            </dl>
		          </article>

		          <em class="info-t">본인 확인이 되지 않으면 아이디 사용에 도움을 드리기 어렵습니다.</em>

		          <div class="btn-wr">
		          <!-- 본인인증 서비스 팝업을 호출하기 위해서는 다음과 같은 form이 필요합니다. -->
					<form name="form_chk" method="post">
						<input type="hidden" name="m" value="checkplusService">		<!-- 필수 데이타로, 누락하시면 안됩니다. -->
						<input type="hidden" name="EncodeData" value="${sEncData}">	<!-- 위에서 업체정보를 암호화 한 데이타입니다. -->
						<a href="javascript:void(0)" onclick="fn_checkPlusSafeModel()" class="btn">본인인증</a>
					</form>
				</div>

		          </div>
		          </section>
		        </div>
			</c:when>
			<c:when test="${tryLoginUserDetail.userTyCd eq 'MTC001' or tryLoginUserDetail.userTyCd eq 'MTC003'}">
		        <div id="cont" class="cont-login">
		          <h2 class="tit">로그인</h2>
		          <h3 class="tit">회원님의 아이디는 휴면 상태로 전환되었습니다.</h3>
		          <article class="info-rect tBr">
		            <p>회원님은 로그인 한 지 1년 이상이 지나 아이디가 휴면 상태로 전환되었습니다.</p>
		            <p>이메일 인증 확인 후 다시 시험인증 빅데이터 플랫폼 사용이 가능합니다.</p>
		            <dl>
		              <dt>휴면전환일자 :  ${tryLoginUserDetail.lastDrmncyDt}</dt>
		              <dd>본인인증 버튼을 통해 분리보관된 개인정보에 대한 복원이 진행됩니다.<dd>
		            </dd>
		          </article>

		          <em class="info-t">회원정보에 등록한 이메일 주소로 인증번호가 발송됩니다.</em>
		          <em class="info-t">이메일 확인이 되지 않으면 아이디 사용에 도움을 드리기 어렵습니다.</em>

		          <article class="info-rect lock-prove-wr">
		            <ul>
		              <li>
		                <label>이메일 인증</label>
		                <input type="text" value="" class="readonly" id="userMail" readonly title="이메일">
		                <button type="button" class="btn cancel" onclick="fn_sendDrmncyMail();">인증 메일 발송</button>
		              </li>
		              <li>
		                <label>인증번호</label>
		                <input type="text" placeholder="메일에서 복사한 인증번호를 입력해주세요." id="certNoArea" title="인증번호">
		                <button type="button" class="btn" onclick="fn_certNoCheck();">확인</button>
		              </li>
		            </ul>
		          </article>
		        </section>
		    </div>
		</c:when>
	</c:choose>

</div>


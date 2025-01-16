<%@ page language="java" contentType="text/html;charset=euc-kr" %>

<%
%>

	<title>NICE평가정보 - CheckPlus 안심본인인증 테스트</title>

<script type="text/javascript">
	window.name ="Parent_window";

	function fnPopup(){
		window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
		document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
		document.form_chk.target = "popupChk";
		document.form_chk.submit();
	}
</script>

<!-- ===== header ====== -->
<header id="header">
	<div id="sub-mv" class="sub-mem">
		<div class="inner">
			<h2>로그인</h2>
			<p>시험인증 빅데이터<br class="mo-block"> 플랫폼 로그인 입니다.</p>
			<div class="sub-obj">오브젝트</div>
		</div>
	</div>
</header>
<!-- ===== /header ====== -->

<div id="container">
	${sMessage}<br><br>
	업체정보 암호화 데이타 :  [ ${sEncData} ]<br><br>

	<!-- 본인인증 서비스 팝업을 호출하기 위해서는 다음과 같은 form이 필요합니다. -->
	<form name="form_chk" method="post">
		<input type="hidden" name="m" value="checkplusService">						<!-- 필수 데이타로, 누락하시면 안됩니다. -->
		<input type="hidden" name="EncodeData" value="${sEncData}">		<!-- 위에서 업체정보를 암호화 한 데이타입니다. -->

		<a href="javascript:fnPopup();"> CheckPlus 안심본인인증 Click</a>
	</form>
</div>
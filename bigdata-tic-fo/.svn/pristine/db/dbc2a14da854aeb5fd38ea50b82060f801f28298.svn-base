<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	// PASS 팝업 띄우기
	function fn_open_pass_popup(){
		window.open('', 'pass_popup', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
		document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
		document.form_chk.target = "pass_popup";
		document.form_chk.submit();
	}
</script>

<%-- 본인인증 서비스 팝업을 호출하기 위해서는 다음과 같은 form이 필요합니다. --%>
<form name="form_chk" method="post">
	<input type="hidden" name="m" value="checkplusService" /><%-- 필수 데이타로, 누락하시면 안됩니다. --%>
	<input type="hidden" name="EncodeData" value="${passEncData}" /><%-- 위에서 업체정보를 암호화 한 데이타입니다. --%>
</form>
<%@ page language="java" contentType="text/html;charset=euc-kr" %>

<%
%>

	<title>NICE������ - CheckPlus �Ƚɺ������� �׽�Ʈ</title>

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
			<h2>�α���</h2>
			<p>�������� ������<br class="mo-block"> �÷��� �α��� �Դϴ�.</p>
			<div class="sub-obj">������Ʈ</div>
		</div>
	</div>
</header>
<!-- ===== /header ====== -->

<div id="container">
	${sMessage}<br><br>
	��ü���� ��ȣȭ ����Ÿ :  [ ${sEncData} ]<br><br>

	<!-- �������� ���� �˾��� ȣ���ϱ� ���ؼ��� ������ ���� form�� �ʿ��մϴ�. -->
	<form name="form_chk" method="post">
		<input type="hidden" name="m" value="checkplusService">						<!-- �ʼ� ����Ÿ��, �����Ͻø� �ȵ˴ϴ�. -->
		<input type="hidden" name="EncodeData" value="${sEncData}">		<!-- ������ ��ü������ ��ȣȭ �� ����Ÿ�Դϴ�. -->

		<a href="javascript:fnPopup();"> CheckPlus �Ƚɺ������� Click</a>
	</form>
</div>
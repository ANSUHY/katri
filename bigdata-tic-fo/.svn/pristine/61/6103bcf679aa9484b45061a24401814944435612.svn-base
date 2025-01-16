<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	/**
	 * 비밀번호 초기화 이동
	 */
	function fn_goResetPwd() {
		const userTyCd = $("#userTyCd").val();

		location.href="/auth/resetPwd?userTyCd=" + userTyCd;
	}
</script>

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

	<!--container-->
	<div id="cont" class="cont-login">
		<input type="hidden" name="userTyCd" id="userTyCd" value="${user.userTyCd}">

		<h2 class="tit">아이디 찾기</h2>
		<h3 class="tit type-18">* 개인정보보호를 위해 아이디 앞자리 2자리를 제외한 나머지 ***로 표시 합니다.</h3>

		<article class="info-rect tBr result-id-wr">
			<p>사용중인 아이디는 <span>${user.userId}</span> 입니다.</p>
		</article>

		<div class="btn-wr">
			<a href="/auth/login" class="btn">로그인</a>
			<a href="javascript:void(0)" class="btn cancel" onclick="fn_goResetPwd()">비밀번호 재설정</a>
		</div>

	</div>
</div>
<!-- ===== /container ====== -->
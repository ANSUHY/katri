<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

<%@ page import="com.katri.common.Const" %>
<c:set var="USER_TYPE_INST_GENERAL" value="<%=Const.Code.UserTyCd.INST_GENERAL%>" />
<c:set var="USER_TYPE_ENT_MASTER" 	value="<%=Const.Code.UserTyCd.ENT_MASTER%>" />
<c:set var="USER_TYPE_ENT_GENERAL" 	value="<%=Const.Code.UserTyCd.ENT_GENERAL%>" />
<c:set var="USER_TYPE_GENERAL" 		value="<%=Const.Code.UserTyCd.GENERAL%>" />

<script type="text/javascript">

	$(document).ready(function() {

	});

	/*
	 * 회원 유형 선택 시, 해당 회원 유형 가입페이지로 이동
	 */
	$(document).on("click", '.userType', function(){

		let userTyCd = $(this).attr("data-type");

		fn_joinTermList( userTyCd );

	});

	/*
	 * 함수명 : fn_joinTermList
	 * 설   명 : 회원가입 약관 목록 페이지 이동
	 */
	function fn_joinTermList( userTyCd ) {

		$("#frm_join #targetUserTypeCd").val(userTyCd);
		$("#frm_join").attr("method", "POST");
		$("#frm_join").attr("action", "/join/joinTermsList");
		$("#frm_join").submit();

	}

</script>

	<!-- ===== header ====== -->
	<header id="header">
		<div id="sub-mv" class="sub-join">
			<div class="inner">
				<h2>회원가입</h2>
				<!-- <p>시험인증 빅데이터<br class="mo-block"> 플랫폼 회원가입 입니다.</p> -->
				<div class="sub-obj">오브젝트</div>
			</div>
		</div>
	</header>
	<!-- ===== /header ====== -->


	<!-- ===== container ====== -->
	<div id="container">
		<!-- <div id="breadcrumb-wr">
			<div class="inner">
				<a href="/" class="home">처음으로</a>
				<div class="bcb-wrap">
					<p class="breadcrumb">플랫폼 소개</p>
					<div class="snb-sbox">
						<ul>
							<li><a href="">데이터활용</a></li>
							<li><a href="">내 손안의 시험인증</a></li>
							<li><a href="">참여기관 라운지</a></li>
							<li><a href="">커뮤니티</a></li>
						</ul>
					</div>
				</div>
				<div class="bcb-wrap">
					<p class="breadcrumb">참여기관 소개</p>
					<div class="snb-sbox">
						<ul>
							<li><a href="">시험인증 빅데이터 플랫폼 소개</a></li>
							<li><a href="">참여기관 소개</a></li>
							<li><a href="">시험인증 산업 소개</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div> -->

		<!--container-->
		<div id="cont" class="cont-join">

			<form action="/join/joinTermsList" method="post" id="frm_join" name="frm_join">
				<input type="hidden" id="targetUserTypeCd" name="targetUserTypeCd" value="" />
			</form>

			<h2 class="tit">회원 유형 선택</h2>
			<article class="info-rect tBr join-rect">
			<p><strong>시험인증 빅데이터 플랫폼에 오신 것을 환영합니다.</strong></p>
			<p>시험인증 빅데이터 플랫폼 회원은 개인회원과 기업(일반)회원, 기업(마스터)회원, 기관(일반)회원으로 구분됩니다.</p>
			<p class="clr-red">※ 꼭 확인하신 후 가입해주시기 바랍니다.</p>
			</article>

			<!--유형선택-->
			<ul class="join-type-ul">
			<li class="type-01">
				<h3>개인회원</h3>
				<p>개인회원 가입 페이지로<br>이동합니다.</p>
				<a href="javascript:void(0);" class="userType" data-type="${USER_TYPE_GENERAL}">바로가기<i></i></a>
			</li>
			<li class="type-02">
				<h3>기관 일반 회원</h3>
				<p>기관 일반 회원 페이지로<br>이동합니다.</p>
				<a href="javascript:void(0);" class="userType" data-type="${USER_TYPE_INST_GENERAL}">바로가기<i></i></a>
			</li>
			<li class="type-03">
				<h3>기업 마스터 회원</h3>
				<p>기업 마스터 회원 페이지로<br>이동합니다.</p>
				<a href="javascript:void(0);" class="userType" data-type="${USER_TYPE_ENT_MASTER}">바로가기<i></i></a>
			</li>
			<li class="type-04">
				<h3>기업 일반 회원</h3>
				<p>기업 일반 회원 페이지로<br>이동합니다.</p>
				<a href="javascript:void(0);" class="userType" data-type="${USER_TYPE_ENT_GENERAL}">바로가기<i></i></a>
			</li>
			</ul>

		</div>
	</div>
	<!-- ===== /container ====== -->
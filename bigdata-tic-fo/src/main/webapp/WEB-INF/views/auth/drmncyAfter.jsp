<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">

/*
 * 함수명 : fn_goLogin
 * 설 명 	: 로그인 페이지로 보내기
 */
 function fn_goLogin() {
	 $(location).attr("href","/auth/login");
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


	<!-- ===== container ====== -->
    <div id="container">
        <div id="breadcrumb-wr">
            <div class="inner">
                <a href="/" class="home">처음으로</a>
                <button class="breadcrumb">로그인</button>
            </div>
        </div>

        <!--container-->
        <div id="cont" class="cont-login">
          <h2 class="tit">로그인</h2>
          <p class="stit">시험인증 빅데이터 플랫폼 로그인을 안내해 드립니다.</p>
          <h3 class="tit">휴면 계정 해제 완료</h3>
          <article class="info-rect tBr">
            <dl class="type-n">
              <dt>회원님 계정의 휴면 상태가 해제 되었습니다.</dt>
              <dd>시험인증 빅데이터 플랫폼의 모든 서비스를 정상 이용 가능합니다.<br>개인/정보 복원 후에는 정상적인 서비스 이용을 위하여 다시 로그인을 해주셔야 합니다.</dd>
            </dl>
          </article>

          <div class="btn-wr">
            <a href="javascript:void(0)" onclick="fn_goLogin();" class="btn">로그인</a>
          </div>
        </div>
    </div>
    <!-- ===== /container ====== -->


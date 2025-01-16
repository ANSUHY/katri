<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" 		uri="/WEB-INF/tlds" %>

<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
		<meta name="format-detection" content="telephone=no">
		<meta name="format-detection" content="email=no">
		<title>시험인증 빅데이터 플랫폼</title>
		<meta name="description" content="">
		<meta name="keywords" content="">

		<link href="/asset/css/style.css" rel="stylesheet">
		<script src="/asset/js/jquery.min.js"></script>
		<script src="/asset/js/common.js"></script>

		<!--swier css 추가-->
		<script src="/asset/css/swiper-bundle.min.css"></script>
		<!--swier 스크립트 추가-->
		<script src="/asset/js/swiper-bundle.min.js"></script>

		<script type="text/javascript">

			/* qr랜딩 이미지 */
			$(function(){
				var qrswiper = new Swiper(".qr-swiper", {
						scrollbar: {
						el: ".swiper-scrollbar",
						hide: false,
						},
						pagination: {
							el: ".swiper-pagination",
							type: "fraction",
						},
					});
				})

		</script>

	</head>

	<body>
		<!-- ===== wrap ====== -->
		<div id="wrap">

			<!-- ===== header ====== -->
			<header id="header">
				<div id="sub-mv" class="sub-myservice QRlanding">
					<div class="inner QRlanding">
						<h2>인증 정보</h2>
						<p><span>"${certDataDetail.prdtNm}"</span>의 인증상태</p>
						<div class="qr-state-mo"><span class="ok">${certDataDetail.certSttNm}</span></div>
					</div>
				</div>
			</header>
			<!-- ===== /header ====== -->

			<!-- ===== container ====== -->
			<!--container-->
			<div id="cont" class="cont-myservice dtl-myservice qr-chk-wr qr-block">

				<!--이미지-->
				<c:if test="${! empty certDataDetail.imgNameList}">
					<div class="qr-swiper page swiper">
						<div class="swiper-wrapper">
							<c:forEach items="${certDataDetail.imgNameList}" var="item">
								<div class="swiper-slide">
									<img src="/images/${certDataDetail.instEngNmLower}/cert/${certDataDetail.certNo}/${item}" alt="${item}" onerror="this.src='/asset/images/cert_no_image.png'" style="margin-bottom:10px;">
								</div>
							</c:forEach>
						</div>
						<div class="swiper-scrollbar"></div>
						<div class="swiper-pagination"></div>
					</div>
				</c:if>
				<c:if test="${empty certDataDetail.imgNameList}">
					<div style="text-align:center; padding:100px 0px; border:1px solid #cecbcb; margin-bottom:20px; border-radius:10px;">
						해당 사진이 없습니다.
					</div>
				</c:if>

				<h3 class="tit">인증 정보</h3>

				<!--리스트-->
				<div class="svc-info-dtl">
					<table>
						<caption>인증정보 상세 - 인증기관, 통합접수번호, 인증번호, 인증구분, 인증일자, 인증상태, 최초인증번호, 인증변경일자, 인증변경사유 내용 포함</caption>
						<tr>
							<th>인증번호</th>
							<td>${certDataDetail.certNo}</td>
							<th>인증구분</th>
							<td>${certDataDetail.certDivNm}</td>
						</tr>

						<tr>
							<th>인증일자</th>
							<td>
								<c:choose>
									<c:when test="${certDataDetail.certYmd == 0}">
										00000000
									</c:when>
									<c:otherwise>
										<cm:stringFormat type="date" format="YYYY-MM-DD" value="${certDataDetail.certYmd}" />
									</c:otherwise>
								</c:choose>
							</td>
							<th>인증상태</th>
							<td>${certDataDetail.certSttNm}</td>
						</tr>
					</table>
				</div>

				<h3 class="tit">제품 정보</h3>

				<!--리스트-->
				<div class="svc-info-dtl">
					<table>
						<caption>인증정보 상세 - 제품명, 브랜드명, 모델명, 규격, 제품분류코드, 제품세분류명, 파생모델명, 비고 내용 포함</caption>
						<tr>
							<th>제품명</th>
							<td>${certDataDetail.prdtNm}</td>
							<th>모델명</th>
							<td>${certDataDetail.mdlNm}</td>
						</tr>
					</table>
				</div>

				<div class="btn-wr" style="display:flex; flex-direction:row; justify-content:center; align-items:flex-start">
					<a href="/svc/certInfoDetail/${encQrKey}" class="btn md-b cancel">인증 상세정보</a>
					<c:if test="${certDataDetail.certAditInfoNm != null && certDataDetail.certAditInfoCn !=  null}">
						<a href="${certDataDetail.certAditInfoCn}"  target="_blank" class="btn md-b btn-test-sch" style="margin-left:var(--w-10); margin-top:0px;">
							${ (certDataDetail.certAditInfoNm eq '' || empty certDataDetail.certAditInfoNm) ? '추가정보' : certDataDetail.certAditInfoNm }
						</a>
					</c:if>
				</div>

			</div>
			<!--// container-->
			<!-- ===== /container ====== -->
		</div>
		<!-- ===== /wrap ====== -->

	</body>
</html>

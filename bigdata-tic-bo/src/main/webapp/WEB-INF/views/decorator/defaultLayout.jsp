<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="ko">

	<head>
		<jsp:include page="../layout/topLayout.jsp" />
	</head>

	<body>
		<!-- ===== wrap ====== -->
		<div id="wrap">

			<!-- header -->
			<header>
				<jsp:include page="../layout/headerLayout.jsp" />
			</header>
			<!-- //header -->

			<!-- container -->
					<sitemesh:write property='body'/>
			<!-- //container -->

			<!-- footer -->
			<footer>
				<jsp:include page="../layout/bottomLayout.jsp" />
			</footer>
			<!-- //footer -->

		</div>
		<!-- ===== //wrap ====== -->
	</body>

</html>
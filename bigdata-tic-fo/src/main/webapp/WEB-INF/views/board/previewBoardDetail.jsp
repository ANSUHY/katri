<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags"%>

<script type="text/javascript">

	$(document).ready(function() {


	});

</script>

<div id="container">
	<div class="subVisual"><span>previewBoardDetail</span></div>

	<section>
		<div class="contentsInner">

			<!-- register st-->
			<div class="register">

				${previewBoard.previewMenuCptnCnUnescape}

			</div>
			<!-- //register ed-->

		</div>
	</section>
</div>
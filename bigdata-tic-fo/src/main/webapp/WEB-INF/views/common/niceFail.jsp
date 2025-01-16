<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<script>

		$(document).ready(function() {

			let nChk 		= ${responseNice.iReturn};

			if( nChk === 0 ) {
				// [실패 메세지]
				alert("${responseNice.sMessage}");
			} else {
				// [복호화 외 에러메세지]
				alert("${responseNice.sMessage}");
			}

			self.close();

		});

	</script>

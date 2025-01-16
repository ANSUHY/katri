<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm"		uri="/WEB-INF/tlds" %>

	<script>

		$(document).ready(function() {

			// 페이지 첫 로드 시, 값 셋팅
			let targetUserId 	= opener.document.getElementById('targetUserId').value;
			let targetUserTypeCd 	= opener.document.getElementById('targetUserTypeCd').value;

			// 값이 있는 경우 상세 조회
			if( targetUserId != '' ) {

				// 상세 조회
				fn_userBasDetail(targetUserId, targetUserTypeCd);
			}

		});

		/*
		 * 함수명 : fn_userBasDetail
		 * 설   명 : 회원 상세 조회
		 */
		function fn_userBasDetail( userId, userTyCd ) {

			$.blockUI();

			// 1. 상세 조회
			$.ajax({
				url	 		: "/user/user/getUserBasDetail"
				, type 		: "GET"
				, dataType 	: "json"
				, data 		: {
								  "targetUserId"	: userId
								, "targetUserTypeCd": userTyCd
							  }
				, success 	: function(jsonData, textStatus, jqXHR) {
					$.unblockUI();

					if(jsonData.resultCode === 200){

						// 2. 회원 상세 값 셋팅
						$("#info_userTyNm").html(jsonData.data.userTyNm);
						$("#info_userSttNm").html(jsonData.data.userSttNm);
						$("#info_userId").html(jsonData.data.userId);
						$("#info_encptMblTelnoVal").html(jsonData.data.encptMblTelnoVal);
						$("#info_whdwlYmd").html(jsonData.data.whdwlYmd);

					} else {

						alert(jsonData.resultMessage);
						self.close();
					}
				}
			});

		}

	</script>

	<div id="pop-wrap">

		<h1 class="pop-tit">회원 상세</h1>

		<div class="pop-contnet">

			<!-- 탈퇴 정보 -->
			<div class="btn-box bot r">
				<div class="left">
					<h2 class="pop-tit2" style="margin-top: 0px;">탈퇴 정보</h2>
				</div>
			</div>

			<div class="hd-search">
				<table>
					<colgroup>
						<col style="width:150px;">
						<col>
						<col style="width:200px">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th>
								회원유형
							</th>
							<td id="info_userTyNm">

							</td>
							<th>
								회원 상태
							</th>
							<td id="info_userSttNm">

							</td>
						</tr>
						<tr>
							<th>
								아이디
							</th>
							<td id="info_userId">

							</td>
							<th>
								휴대폰 번호
							</th>
							<td id="info_encptMblTelnoVal">
							</td>
						</tr>
						<tr>
							<th>
								탈퇴일
							</th>
							<td colspan="3" id="info_whdwlYmd">

							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- // 탈퇴 정보 -->

		</div><!-- /pop-contnet -->
		<a href="javascript:self.close();" class="pop-close"><i class="fa fa-times"></i></a>
	</div><!-- /pop-wrao -->
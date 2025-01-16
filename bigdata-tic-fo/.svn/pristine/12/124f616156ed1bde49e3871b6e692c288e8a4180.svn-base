<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" uri="/WEB-INF/tlds"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">

//리스트 페이지로 돌아가기
function goList () {
	$("#frm").attr("method", "get");
	$("#frm").attr("action", "/mbr/mbrLst");
	$("#frm").submit();
}

// 가입 승인
function userUdate (code) {
	var result = confirm('탈퇴처리 하시겠습니까?');
	if(result){
		$.blockUI();
		$("#sttCd").val(code);
		var jData = $("#frm").serialize();
		$("#frm").attr("action", "/mbr/updateUser");
		fn_submitAjax($("#frm"), jData, fn_loginCallBack, 'json');
	}
}
<c:if test="${info.sttCd == '승인대기'}">
function userUdate2 (code) {
	
		$.blockUI();
		$("#sttCd").val(code);
		var jData = $("#frm").serialize();
		$("#frm").attr("action", "/mbr/updateUser");
		fn_submitAjax($("#frm"), jData, fn_loginCallBack, 'json');
	
}
</c:if>

function fn_loginCallBack (result) {
	$.unblockUI();
	alert(result.resultMessage);

	var sttCd = $("#sttCd").val();
	if (sttCd == 'A') {
		window.location.reload();
	}else{
		goList();
	}

}
</script>

<div id="container">
	<div class="subVisual"><span>회원정보 상세</span></div>

	<section>

		<div class="contentsInner">
			<!--register st-->
			<div class="register">
				<form action="/mbr/mbrLst" method="post" name="frm" id="frm">
					<input type="hidden" name="pageNum" id="pageNum" value="${pageNum}"/>
					<input type="hidden" name="mbrNm" id="mbrNm" value="${mbrNm}"/>
					<input type="hidden" name="sDt" id="sDt" value="${sDt}"/>
					<input type="hidden" name="eDt" id="eDt" value="${eDt}"/>
					<input type="hidden" name="mbrId" id="mbrId" value="${targetId}"/>
					<input type="hidden" name="sttCd" id="sttCd" value=""/>
				</form>
				<table class="detail_type type01">
					<colgroup>
						<col width="14%">
						<col width="*">
					</colgroup>
					<tbody>
						<tr>
							<th><span>기관명</span></th>
							<td>${info.certInstNm}</td>
						</tr>
						<tr>
							<th><span>소속부서</span></th>
							<td>${info.deptNm}</td>
						</tr>
						<tr>
							<th><span>아이디</span></th>
							<td>${info.mbrId}</td>
						</tr>
						<tr>
							<th><span>비밀번호</span></th>
							<td>***********</td>
						</tr>
						<tr>
							<th><span>이름</span></th>
							<td>${info.mbrNm}</td>
						</tr>
						<tr>
							<th><span>이메일</span></th>
							<td>${info.mbrEmlAddr}</td>
						</tr>
						<tr>
							<th><span>휴대전화</span></th>
							<td>${info.mbrCpNo}</td>
						</tr>
					</tbody>
				</table>
				<div class="buttonframe block mt20">
					<c:if test="${info.sttCd == '승인대기'}">
						<button class="button_type memberegister mr10" onclick="userUdate2('A')">가입 승인</button>
					</c:if>
					<button class="button_type memberegister membe_quit mr10" onclick="userUdate('S')">탈퇴 처리</button>
					<button class="button_type list ab_r" onclick="goList();">목록</button>
				</div>
			</div>
			<!--register ed-->
		</div>
		<!--//contentsInner ed-->
	</section>
</div>
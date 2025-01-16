<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
	$(function() {
		fn_getComnCdDetail();
	});

	/**
	 * 선택한 공통코드 조회
	 */
	function fn_getComnCdDetail() {
		$.blockUI();

		const comnCd = opener.$("#comnCd").val(); // 조회한 공통코드
		$("#openTargetComnCd").val(""); // 공통코드 타켓 (데이터 확인용)

		if (comnCd === null || comnCd === "") { // 추가 버튼 클릭 시
			$.unblockUI();

			$("#comnGrpCd").val(opener.$("#targetComnGrpCd").val());
		} else { // 공통코드를 선택했을 때
			$.unblockUI();

			$.ajax({
				  url: "/system/code/getComnCdDetail"
				, type: "GET"
				, dataType: "json"
				, data: {
					  comnGrpCd: opener.$("#targetComnGrpCd").val()
					, comnCd: opener.$("#comnCd").val()
				}
			})
			.done(function(jsonData, textStatus, jqXHR) {
				if (jsonData.resultCode === 200) { // 정상
					if (jsonData.data.object != null) { // 데이터 O
						const data = jsonData.data.object;

						// 값 세팅
						$("#comnGrpCd").val(fn_convertXss(data.comnGrpCd));
						$("#comnCd").val(fn_convertXss(data.comnCd));
						$("#comnCdNm").val(fn_convertXss(data.comnCdNm));
						$("#comnCdVal").val(fn_convertXss(data.comnCdVal));
						$("#use" + data.useYn).attr("checked", true);

						// 공통코드 readonly
						$("#comnCd").attr("readonly", true).addClass("read");
					}
				} else { // 데이터 X
					alert(jsonData.resultMessage);
				}
			});
		}
	}

	/**
	 * 공통코드 추가/수정 유효성 검사
	 */
	function fn_validCheck() {
// 		const comnGrpCd = $("#comnGrpCd").val(); // 공통그룹코드
		const comnCd = $("#comnCd").val(); // 공통코드
		const comnCdNm = $("#comnCdNm").val(); // 공통코드명
		const useYn = $('input:radio[name=useYn]:checked').val(); // 사용여부

		if (fn_emptyCheck(comnCd)) {
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='공통코드'/>");
			return false;
		}

		if (fn_emptyCheck(comnCdNm)) {
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='코드명'/>");
			return false;
		}

		if (fn_emptyCheck(useYn)) {
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='사용여부'/>");
			return false;
		}

		return true;
	}

	/**
	 * 공통코드 추가/수정
	 */
	function fn_submitCode() {
		if (fn_validCheck()) {
			$.blockUI();

			const comnCd = opener.$("#comnCd").val(); // 조회한 공통코드

			if (comnCd === null || comnCd === "") { // 추가 버튼 클릭 시
				$("#openTargetComnCd").val($("#comnCd").val()); // 조회한 공통코드 타켓 (데이터 확인용)
			}

			$.ajax({
				url: "/system/code/saveComnCd",
				type: "POST",
				dataType: "json",
				data: $("#codeAddFrm").serialize()
			})
			.done(function(jsonData, textStatus, jqXHR){
				$.unblockUI();

				if (jsonData.resultCode === 200) { // 정상
					alert("<spring:message code='result-message.messages.common.message.save'/>"); // 정상적으로 저장 하였습니다.
		 			opener.fn_getComnCdList();
		 			self.close();
				} else {
					alert(jsonData.resultMessage);
				}
			});
		}
	}

	/**
	 * 팝업 닫기
	 */
	function fn_closePopUp() {
		self.close();
	}
</script>

<div id="pop-wrap">
	<h1 class="pop-tit">공통코드 추가</h1>

	<div class="pop-contnet">
		<form name="codeAddFrm" id="codeAddFrm">
			<div class="hd-search">
				<table>
					<colgroup>
						<col style="width: 100px" />
						<col />
					</colgroup>
					<tbody>
						<tr>
							<th>대표코드</th>
							<td>
								<input type="text" name="comnGrpCd" id="comnGrpCd" class="input" style="width: 100%" readonly />
							</td>
						</tr>
						<tr>
							<th><span class="blt_req">*</span> 공통코드</th>
							<td>
								<input type="hidden" name="targetComnCd" id="openTargetComnCd" />
								<input type="text" name="comnCd" id="comnCd" class="input" style="width: 100%" />
							</td>
						</tr>
						<tr>
							<th><span class="blt_req">*</span> 코드명</th>
							<td>
								<input type="text" name="comnCdNm" id="comnCdNm" class="input" style="width: 100%" />
							</td>
						</tr>
						<tr>
							<th>코드값</th>
							<td>
								<input type="text" name="comnCdVal" id="comnCdVal" class="input" style="width: 100%" />
							</td>
						</tr>
						<tr>
							<th><span class="blt_req">*</span> 사용여부</th>
							<td>
								<label><input type="radio" name="useYn" id="useY" value="Y" checked="checked" />Y</label>
								<label><input type="radio" name="useYn" id="useN" value="N" />N</label>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="btn-box r">
				<a href="javascript:void(0)" onclick="fn_closePopUp()" class="btn btn-default">취소</a>
				<a href="javascript:void(0)" onclick="fn_submitCode()" class="btn btn-blue">저장</a>
			</div>
		</form>
	</div>
	<!-- /pop-contnet -->
	<a href="javascript:void(0);" onclick="fn_closePopUp()" class="pop-close"><i class="fa fa-times"></i></a>
</div>
<!-- /pop-wrao -->
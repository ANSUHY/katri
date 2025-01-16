<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<style type="text/css">
	tbody#comnCdList tr td {word-break:break-all;}
</style>

<script type="text/javascript">
	$(function () {
		fn_getComnGrpCdList(); // 공통그룹코드 목록 조회
		fn_getComnCdList(); // 공통코드 목록 조회
	});

	/**
	 * 공통그룹코드 목록 조회
	 */
	function fn_getComnGrpCdList() {
		$.blockUI();

		// 검색조건
		const isUseYnChk = $("#comnGrpCdChkUseYn").is(":checked") ? "Y" : "N";

		$.ajax({
			url: "/system/code/getComnGrpCdList",
			type: "GET",
			dataType: "json",
			data: { searchUseYn: isUseYnChk }
		})
		.done(function (jsonData, textStatus, jqXHR) {
			// success
			$.unblockUI();

			let html = "";

			if (jsonData.resultCode === 200) { // 정상
				if (jsonData.data.list != null && jsonData.data.list.length > 0) { // 데이터 O
					// 리스트 html 만들기
					$.each(jsonData.data.list, function (index, data) {
						html += fn_returnComnGrpCdListHtml(data);
					});
				} else { // 데이터 X
					html = "<tr><td colspan='4'>No Data.</td></tr>";
				}

				// 리스트 출력
				$("#comnGrpCdList").html(html);
			} else {
				alert(jsonData.resultMessage);
			}
		});
	}

	/**
	 * 공통그룹코드 목록 html
	 */
	function fn_returnComnGrpCdListHtml(data) {
		let returnHtml = "";

		returnHtml += "<tr onclick='fn_getComnGrpCdDetail(\"" + data.comnGrpCd + "\")' style='cursor:pointer;'>";
		returnHtml += "	<td>" + data.comnGrpCd + "</td>";
		returnHtml += "	<td>" + data.comnGrpCdNm + "</td>";
		returnHtml += "	<td>" + data.comnGrpCdDescCn + "</td>";
		returnHtml += "	<td>" + data.useYn + "</td>";
		returnHtml += "</tr>";

		return returnHtml;
	}

	/**
	 * 공통코드 목록 조회
	 */
	function fn_getComnCdList() {
		$.blockUI();

		// 검색조건
		const isUseYnChk = $("#comnCdChkUseYn").is(":checked") ? "Y" : null;

		$.ajax({
			url: "/system/code/getComnCdList",
			type: "GET",
			dataType: "json",
			data: {
				comnGrpCd: $("#targetComnGrpCd").val(),
				searchUseYn: isUseYnChk
			},
		})
		.done(function (jsonData, textStatus, jqXHR) { // success
			$.unblockUI();

			let html = "";

			if (jsonData.resultCode === 200) { // 정상
				if (jsonData.data.list != null && jsonData.data.list.length > 0) { // 데이터 O
					// 리스트 html 만들기
					$.each(jsonData.data.list, function (index, data) {
						html += fn_returnComnCdListHtml(data);
					});
				} else { // 데이터 X
					html = "<tr><td colspan='7'>No Data.</td></tr>";
				}

				// 리스트 출력
				$("#comnCdList").html(html);
			} else {
				alert(jsonData.resultMessage);
			}
		});
	}

	/**
	 * 공통코드 목록 html
	 */
	function fn_returnComnCdListHtml(data) {
		let returnHtml = "";

		returnHtml += "<tr onclick='fn_openCodeAddPopUp(\"" + data.comnCd + "\")' data-key='" + data.comnCd + "' style='cursor:pointer;'>";
		returnHtml += "	<td>" + data.comnGrpCd + "</td>";
		returnHtml += "	<td>" + data.comnCd + "</td>";
		returnHtml += "	<td>" + data.comnCdNm + "</td>";
		returnHtml += "	<td>" + data.comnCdVal + "</td>";
		returnHtml += "	<td>" + data.srtSeq + "</td>";
		returnHtml += "	<td>" + data.useYn + "</td>";
		returnHtml += "	<td onclick='event.cancelBubble=true'>";
		returnHtml += "		<button type='button' onclick='fn_comnCdSeqUp(this)' style='cursor:pointer;'>▲</button>";
		returnHtml += "		<button type='button' onclick='fn_comnCdSeqDown(this)' style='cursor:pointer;'>▼</button>";
		returnHtml += "	</td>";
		returnHtml += "</tr>";

		return returnHtml;
	}

	/**
	 * 선택한 공통그룹코드 조회
	 */
	function fn_getComnGrpCdDetail(comnGrpCd) {
		$.blockUI();

		$.ajax({
			url: "/system/code/getComnGrpCdDetail",
			type: "GET",
			dataType: "json",
			data: { comnGrpCd: comnGrpCd },
		})
		.done(function (jsonData, textStatus, jqXHR) {
			$.unblockUI();

			if (jsonData.resultCode === 200) { // 정상
				if (jsonData.data.object != null) { // 데이터 O
					const data = jsonData.data.object;

					// 값 세팅
					$("#targetComnGrpCd").val(fn_convertXss(data.comnGrpCd));
					$("#comnGrpCd").val(fn_convertXss(data.comnGrpCd)).attr("readonly", true).addClass("read");
					$("#comnGrpCdNm").val(fn_convertXss(data.comnGrpCdNm));
					$("#comnGrpCdDescCn").val(fn_convertXss(data.comnGrpCdDescCn));
					$("#use" + data.useYn).prop("checked", true);

					fn_getComnCdList(); // 공통코드 목록 조회
				}
			} else {
				// 데이터 X
				alert(jsonData.resultMessage);
			}
		});
	}

	/**
	 * 코드 정보 영역 초기화
	 */
	function fn_resetCode() {
		$("#comnGrpCd, #comnGrpCdNm, #comnGrpCdDescCn").val(""); // 입력 값 초기화
		$("#comnGrpCd").attr("readonly", false).removeClass("read"); // readonly false
		$("input[name=useYn]")[0].checked = true; // 사용여부 Y
	}

	/**
	 * 코드 정보 빈값 체크
	 */
	function fn_validCheck() {
		const comnGrpCd = $("#comnGrpCd").val(); // 대표코드
		const comnGrpCdNm = $("#comnGrpCdNm").val(); // 대표코드명

		if (fn_emptyCheck(comnGrpCd)) {
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='대표코드'/>");
			return false;
		}

		if (fn_emptyCheck(comnGrpCdNm)) {
			alert("<spring:message code='result-message.messages.common.message.required.data' arguments='대표코드명'/>");
			return false
		}

		return true;
	}

	/**
	 * 공통그룹코드 저장/수정
	 */
	function fn_saveComnGrpCd() {
		if (fn_validCheck()) {
			$.blockUI();

			$.ajax({
				url: "/system/code/saveComnGrpCd",
				type: "POST",
				dataType: "json",
				data: $("#comnGrpCdFrm").serialize(),
			})
			.done(function (jsonData, textStatus, jqXHR) {
				$.unblockUI();

				if (jsonData.resultCode === 200) { // 정상
					alert("<spring:message code='result-message.messages.common.message.save'/>"); // 정상적으로 저장 하였습니다.
				} else {
					alert(jsonData.resultMessage);
				}

				fn_resetCode(); // 입력 값 초기화
				fn_getComnGrpCdList(); // 공통그룹코드 목록 조회
			});
		}
	}

	/**
	 * 공통코드 추가 팝업 창
	 */
	function fn_openCodeAddPopUp(comnCd) {
		const targetComnGrpCd = $("#targetComnGrpCd").val(); // 선택된 공통그룹코드

		if (targetComnGrpCd === null || targetComnGrpCd === "") {
			alert("<spring:message code='result-message.messages.code.message.required.data'/>"); // 대표코드를 선택해주세요.
		} else {
			$("#comnCd").val(comnCd); // 조회하고자 하는 공통코드

			fn_openPop("/system/code/codeAddPopUp", "_blank", "500" ,"300", "Y");
		}
	}

	/**
	 * 공통코드 순서 정렬 UP
	 */
	function fn_comnCdSeqUp(obj) {
		const index = $(obj).parent().parent().index(); // 현재 정렬 순서

		if (index === 0) { // 첫번째 순서인 경우
			alert("<spring:message code='result-message.messages.menu.message.sort.first'/>"); // 이미 첫번째 순서의 메뉴 입니다.
		} else { // 메뉴 정렬 저장
			fn_chgComnCdSeq("up", obj);
		}

	}

	/**
	 * 공통코드 순서 정렬 DOWN
	 */
	function fn_comnCdSeqDown(obj) {
		const max = $(obj).parent().parent().parent().find("tr").length; // 목록 갯수
		const index = $(obj).parent().parent().index() + 1; // 현재 정렬 순서

		if(index === max) { // 마지막 순서인 경우
			alert("<spring:message code='result-message.messages.menu.message.sort.last'/>"); // 이미 마지막 순서의 메뉴 입니다.
		} else { // 메뉴 정렬 저장
			fn_chgComnCdSeq("down", obj);
		}
	}

	/**
	 * 공통코드 정렬 순서 저장
	 */
	function fn_chgComnCdSeq(type, obj) {
		$.blockUI();

		const currComnCd = $(obj).parent().parent().attr("data-key");

		let targetComnCd;

		if (type === "up") {
			targetComnCd = $(obj).parent().parent().prev().attr("data-key"); // 이전
		} else if (type === "down") {
			targetComnCd = $(obj).parent().parent().next().attr("data-key"); // 다음
		}

		$.ajax({
			url: "/system/code/chgComnCdSeq",
			type: "GET",
			dataType: "json",
			data: {
				comnGrpCd: $("#targetComnGrpCd").val(),
				targetComnCd: targetComnCd,
				comnCd: currComnCd,
				seqType: type
			}
		})
		.done(function (jsonData, textStatus, jqXHR) {
			$.unblockUI();

			if (jsonData.resultCode === 200) { // 정상
				fn_getComnCdList(); // 공통코드 목록 조회
			} else {
				alert(jsonData.resultMessage);
			}
		});
	}
</script>

<div id="container">
	<input type="hidden" name="comnCd" id="comnCd" />

	<div class="titArea">
		<div class="location">
			<span class="ic-home"><i></i></span><span>시스템 관리</span><em>코드 관리</em>
		</div>
	</div>

	<div class="btn-box bot r">
		<div class="left">
			<h2 class="pop-tit2" style="margin-top: 0px">코드 정보</h2>
		</div>
		<a href="javascript:void(0)" onclick="fn_resetCode()" class="btn btn-default">신규입력</a>
		<a href="javascript:void(0)" onclick="fn_saveComnGrpCd()" class="btn btn-blue">저장</a>
	</div>

	<div class="rows">
		<div class="rows-col5" style="padding: 10px">
			<form name="comnGrpCdFrm" id="comnGrpCdFrm">
				<div class="row-tbl">
					<table>
						<colgroup>
							<col style="width: 100px" />
							<col style="width: 100px" />
							<col style="width: 100px" />
							<col style="width: 100px" />
						</colgroup>
						<thead>
							<tr>
								<th><span class="blt_req">*</span>&nbsp;대표코드</th>
								<th><span class="blt_req">*</span>&nbsp;대표코드명</th>
								<th>설명</th>
								<th><span class="blt_req">*</span>&nbsp;사용여부</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>
									<input type="hidden" name="targetComnGrpCd" id="targetComnGrpCd" />
									<input type="text" name="comnGrpCd" id="comnGrpCd" class="input block" style="padding: 10px" />
								</td>
								<td>
									<input type="text" name="comnGrpCdNm" id="comnGrpCdNm" class="input block" />
								</td>
								<td>
									<input type="text" name="comnGrpCdDescCn" id="comnGrpCdDescCn" class="input block" />
								</td>
								<td>
									<label><input type="radio" name="useYn" id="useY" value="Y" checked="checked" />Y</label>
									<label><input type="radio" name="useYn" id="useN" value="N" />N</label>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
		</div>
	</div>

	<div class="btn-box bot r">
		<div class="left">
			<h2 class="pop-tit2 left">코드 목록</h2>
		</div>
	</div>

	<div class="rows">
		<div class="rows-col5" style="padding: 10px">
			<div style="min-height: 30px">
				<div style="float: left">
					<h4>#대표코드</h4>
				</div>
				<div style="float: right; margin-top: 5px">
					<label>
						<input type="checkbox" name="comnGrpCdChkUseYn" id="comnGrpCdChkUseYn" onclick="fn_getComnGrpCdList(comnGrpCdChkUseYn)" />&nbsp;사용중인 코드만 보기
					</label>
				</div>
			</div>

			<div class="row-tbl scroll" style="height: 200px; margin-top: 10px">
				<table>
					<colgroup>
						<col style="width:20%;" />
						<col style="width:35%;" />
						<col style="width:35%;" />
						<col style="width:10%;" />
					</colgroup>
					<thead>
						<tr>
							<th>대표코드</th>
							<th>대표코드명</th>
							<th>설명</th>
							<th>사용여부</th>
						</tr>
					</thead>
					<tbody id="comnGrpCdList"></tbody>
				</table>
			</div>
		</div>
		<!-- /row-col5 -->

		<div class="rows-col5" style="padding: 10px">
			<div style="min-height: 30px">
				<div style="float: left">
					<h4>#공통코드</h4>
				</div>
				<div style="float: right">
					<label>
						<input type="checkbox" name="comnCdChkUseYn" id="comnCdChkUseYn" onclick="fn_getComnCdList()" />&nbsp;사용중인 코드만 보기
					</label>
					<a href="javascript:void(0)" onclick="fn_openCodeAddPopUp()" class="btn btn-blue">추가</a>
				</div>
			</div>

			<div class="row-tbl scroll" style="height: 200px; margin-top: 10px">
				<table>
					<colgroup>
						<col style="width:15%;" />
						<col style="width:15%;" />
						<col style="width:20%;" />
						<col style="width:20%;" />
						<col style="width:10%;" />
						<col style="width:10%;" />
						<col style="width:10%;" />
					</colgroup>
					<thead>
						<tr>
							<th>대표코드</th>
							<th>공통코드</th>
							<th>코드명</th>
							<th>코드값</th>
							<th>순서</th>
							<th>사용여부</th>
							<th>정렬</th>
						</tr>
					</thead>
					<tbody id="comnCdList"></tbody>
				</table>
			</div>
		</div>
		<!-- /row-col5 -->
	</div>
</div>
<!-- /container -->

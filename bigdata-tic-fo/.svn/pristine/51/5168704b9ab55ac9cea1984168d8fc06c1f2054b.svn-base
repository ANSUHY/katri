<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" uri="/WEB-INF/tlds"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style type="text/css">
	table.list_type tbody tr>td {
		cursor: default;
	}
	table.list_type tbody tr:hover>td{background:none;} {
</style>
<script type="text/javascript">
	$(document).ready(function() {
	    //statsList();
	    //typeList(1);
	});

	function resetHtml (index) {
		var html = '<tr><td class="center" colspan="5">검색된 데이터가 없습니다.</td></tr>';
		$("#typeListBox").html(html);
		$("#typePagingBox").html("");
		$("#statsListBox").html(html);

		if (index == 1) {
			$("#instId2").val("");
		} else {
			$("#instId1").val("");
		}
	}

	// 인증상태 리스트
	function statsList () {
		$.blockUI();
		var count = 0;
		var msg = "";

		var instId = $("#instId1").val();

		var startDateText = $("#startDate1").val();
		var endDateText = $("#endDate1").val();
		var startDate = new Date(startDateText);
		var endDate = new Date(endDateText);


		if (endDateText != '') {
			if (startDateText == '') {
				count++;
				msg = '시작일을 입력해 주세요.'
			} else {
				if (startDate > endDate) {
					count++;
					msg = '종료일이 시작일보다 빠를 수 없습니다.'
				}
			}
		}

		if (count == 0) {
			$("#instId").val(instId);
			$("#startDate").val(startDateText);
			$("#endDate").val(endDateText);

			var jData = $("#frm").serialize();
			$("#frm").attr("action", "/stats/selectStatsCount");
			fn_submitAjax($("#frm"), jData, statsCallBack, 'json');
		} else {
			$.unblockUI();
			alert(msg);
		}

	}

	// 상태 - 콜백 함수
	function statsCallBack (result) {
		$.unblockUI();

		if (result.resultCode == "200") {
			$("#statsListBox").html("");
			var list = result.data;

			for (var i = 0 ; i < list.length ; i++) {
				var obj = list[i];
				var html = '<tr>';
				html += '<td>'+obj.certInstNm+'</td>';
				html += '<td class="center">'+obj.allCount+'</td>';
				html += '<td class="center">'+obj.confirmCount+'</td>';
				html += '<td class="center">'+obj.cancelCount+'</td>';
				html += '<td class="center">'+obj.etcCount+'</td>';
				html += '</tr>';
				$("#statsListBox").append(html);
			}
		} else {
			alert(result.resultMessage);
		}
	}

	// 인증구분 리스트
	function typeList (pageNum) {

		var count = 0;
		var msg = "";

		var instId = $("#instId2").val();
		var startDateText = $("#startDate2").val();
		var endDateText = $("#endDate2").val();
		var startDate = new Date(startDateText);
		var endDate = new Date(endDateText);

		if (endDateText != '') {
			if (startDateText == '') {
				count++;
				msg = '시작일을 입력해 주세요.'
			} else {
				if (startDate > endDate) {
					count++;
					msg = '종료일이 시작일보다 빠를 수 없습니다.'
				}
			}
		}

		if (count == 0) {
			$("#pageNum").val(pageNum);

			$("#instId").val(instId);
			$("#startDate").val(startDateText);
			$("#endDate").val(endDateText);

			var jData = $("#frm").serialize();
			$("#frm").attr("action", "/stats/selectTypeCount");
			fn_submitAjax($("#frm"), jData, typeCallBack, 'json');
		} else {
			$.unblockUI();
			alert(msg);
		}

	}

	// 구분 - 콜백함수
	function typeCallBack (result) {
		$.unblockUI();
		if (result.resultCode == "200") {
			$("#typeListBox").html("");
			$("#typePagingBox").html("");

			var data = result.data;
			var list = data.list;
			var pageMap = data.pageMap;

			if (pageMap.allCount > 0) {
				for (var i = 0 ; i < list.length ; i++) {
					var obj = list[i];
					var html = '<tr>';
					if(obj.certDivNm) {
						obj.certDivNm;
					}else{
						obj.certDivNm = "기타";
					}
					//html += '<td title="'+obj.certDivNm+'('+obj.certDivCd+')">'+obj.certDivNm+'('+obj.certDivCd+')</td>';
					html += '<td title="'+obj.certDivNm+'('+obj.certDivCd+')">'+ obj.certDivNm +'</td>';
					html += '<td class="center">'+obj.allCount+'</td>';
					html += '<td class="center">'+obj.confirmCount+'</td>';
					html += '<td class="center">'+obj.cancelCount+'</td>';
					html += '<td class="center">'+obj.etcCount+'</td>';
					html += '</tr>';
					$("#typeListBox").append(html);
				}
			} else {
				var html = '<tr><td class="center" colspan="5">검색된 데이터가 없습니다.</td></tr>';
				$("#typeListBox").append(html);
			}

			// 페이징 처리
			typePagingAction(pageMap);

		} else {
			alert(result.resultMessage);
		}
	}

	// 페이징 처리
	function typePagingAction (obj) {

		var pageNum = Number(obj.pageNum);
		var sPage = obj.sPage;
		var ePage = obj.ePage;
		var allPage = obj.allPage;
		var allPageGroup = obj.allPageGroup;
		var nowPageGroup = obj.nowPageGroup;

		var pagingHtml = '<div class="paging network">';

		if (pageNum > 10) pagingHtml += '<a href="javascript:typeList('+(Page-1)+');" class="direction first">이전</a>';
		if (pageNum > 1) pagingHtml += '<a href="javascript:typeList('+(pageNum-1)+');" class="direction first">pre</a>';

		for (var i = sPage; i <= ePage ; i++) {
			if (i == pageNum) {
				pagingHtml += '<a href="javascript:return false;" class="on">'+i+'</a>';
			} else {
				pagingHtml += '<a href="javascript:typeList('+i+');" class="on">'+i+'</a>';
			}
		}

		if (allPage > pageNum) pagingHtml += '<a href="javascript:typeList('+(pageNum+1)+');" class="direction next">next</a>';
		if (allPageGroup > nowPageGroup)  pagingHtml += '<a href="javascript:typeList('+(ePage+1)+');" class="direction last">다음</a>';
		pagingHtml += '</div>';

		$("#typePagingBox").append(pagingHtml);
	}
</script>

<div id="container">
	<form action="" id="frm" method="post">
		<input type="hidden" name="pageNum" value="1">
		<input type="hidden" name="instId" id="instId" value="">
		<input type="hidden" name="startDate" id="startDate" value="">
		<input type="hidden" name="endDate" id="endDate" value="">
	</form>
	<div class="subVisual">
		<span>통계</span>
	</div>
	<section>
		<div class="contentsInner">
			<ul class="tab">
				<li data-tab="tab-1" class="current" onclick="resetHtml(1);">인증상태</li>
				<li data-tab="tab-2" onclick="resetHtml(2);">인증구분</li>
			</ul>

			<jsp:include page="statsLstTab01.jsp" />
			<jsp:include page="statsLstTab02.jsp" />
		</div>
	</section>
</div>


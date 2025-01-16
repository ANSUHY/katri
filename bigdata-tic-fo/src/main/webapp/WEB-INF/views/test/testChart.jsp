<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src="<c:url value='/asset/js/chart/dist/chart.js'/>"></script>

<script type="text/javascript">

	$(document).ready(function() {

		//게시판 차트
		fn_getBoardChartData();

	});

	 /*
	 * 함수명 : fn_getBoardChartData()
	 * 설   명 : 게시판 차트 데이터 가져오기
	*/
	function fn_getBoardChartData() {

		/* ------ 데이터 가져오기*/
		$.blockUI();
		$.ajax({
			url	 		: "/test/getBoardChartData"
			, type 		: "GET"
			, dataType 	: "json"
			, data 		: {}
			, success 	: function(jsonData, textStatus, jqXHR) {
				$.unblockUI();

				let html = '';
				if(jsonData.resultCode === 200){

					fn_callBack(jsonData);

				} else {
					alert(jsonData.resultMessage);
				}
			}
		});

	}


	/*
	 * 함수명 : fn_callBack()
	 * 설   명 : 차트 데이터 가져온 callback 함수
	*/
	function fn_callBack(result) {

		$.unblockUI();

		if ( result != null ) {

			let lstChart = result.data.list;

			// 0. 차트 목록 상단 html 추가
			fn_chartListBoxHtml(lstChart);

			// 1. 차트 목록 좌측 하단 bar chart 추가
			fn_chartListLeftChart(lstChart);

			// 2. 차트 목록 우측 하단 doughnut chart 추가
			fn_chartListRightChart(lstChart);

		}
	}

	 /*
		 * 함수명 : fn_chartListBoxHtml()
		 * 설   명 : 차트 목록 데이터 Html edit
		*/
		function fn_chartListBoxHtml(list) {

			// 0. chart html 초기화
			$("#chartUpBox").html("");

			// 1. 상단 차트 데이터 영역 html 생성
			let html = '<table style="border:1px solid #02030f;">';
			html += '		<colgroup>';
			html += '			<col width="15%">';
			html += '			<col>';
			html += '		</colgroup>';

			for (var i = 0; i < list.length; i++) {

				let item = list[i];

				html += '<tr>';
				html += '	<td>';
				html += 		item.typeNm;
				html += '	</td>';
				html += '	<td>';
				html += 		item.totCnt;
				html += '	</td>';
				html += '</tr>';

			}

			html += "</table>";

			// 2. 생성한 차트 html append
			$("#chartUpBox").html(html);
		}

		/*
		 * 함수명 : fn_chartListLeftChart()
		 * 설   명 : 차트 목록 데이터 좌측 하단 chart data 셋팅
		*/
		function fn_chartListLeftChart (list) {

			// 0. 차트 x축 label 셋팅
			let arrLabel = ["게시판 Type"];

			// 1. 차트 붙일 영역 canvas 태그 넣기
			$("#barChartArea").html('<canvas id="barChart" width="500" height="500"></canvas>');

			// 2. 차트 color 셋팅
			let arrColols = [
				'rgba(150, 150, 255, 1)',
				'rgba(150, 255, 150, 1)',
				'rgba(255, 150, 150, 1)'
			];

			// 3. 차트 data 셋팅
			let dataSetList = [];

			for( let i = 0; i < list.length; i++ ) {

				let item	= list[i];
				let objData = new Object();

				objData.label			= [item.typeNm]; // 라벨명
				objData.data			= [item.totCnt]; // 건 수
				objData.backgroundColor	= [arrColols[i]];	 // 색상

				dataSetList.push(objData);

			}

			// 4. 차트 데이터 obj 셋팅
			var obj = {
				id			: 'barChart'
				, type		: 'bar'
				, lstData	: dataSetList
				, arrLabel
				, yDisplay	: true
				, position	: 'bottom'
			}

			// 5. 차트 그리기
			fn_chartAction(obj);

		}

		/*
		 * 함수명 : fn_chartListRightChart()
		 * 설   명 : 차트 목록 데이터 우측 하단 chart data 셋팅
		*/
		function fn_chartListRightChart (list) {

			// 1. 차트 붙일 영역 canvas 태그 넣기
			$(".doughnutChartArea").html('<canvas id="doughnutChart" width="500" height="500"></canvas>');

			// 2. 차트 color 셋팅
			let arrColols = [
				'rgba(150, 150, 255, 1)',
				'rgba(150, 255, 150, 1)',
				'rgba(255, 150, 150, 1)'
			];

			// 3. 차트 data 셋팅
			let arrLabel 	= [];
			let arrData		= [];
			let dataSetList = [];
			let objData 	= new Object();

			for( let i = 0; i < list.length; i++ ) {

				let item	= list[i];

				arrLabel.push(item.typeNm);
				arrData.push(item.totCnt);

			}

			objData.label			= "게시판 Type";
			objData.data			= arrData;
			objData.backgroundColor	= arrColols;

			dataSetList.push(objData);

			// 4. 차트 데이터 obj 셋팅
			var obj = {
				id			: 'doughnutChart'
				, type		: 'doughnut'
				, lstData	: dataSetList
				, arrLabel
				, yDisplay	: false
				, position	: 'right'
			}

			// 5. 차트 그리기
			fn_chartAction(obj);

		}

		/*
		 * 함수명 : fn_chartAction()
		 * 설   명 : 차트 그리기
		*/
		function fn_chartAction (obj) {

			// 0. 차트 데이터 가져오기
			let boxId			= obj.id;
			let type			= obj.type;
			let label			= obj.arrLabel;
			let dataSetList 	= obj.lstData;
			let yDisplay 		= obj.yDisplay;
			let legdPosition	= obj.position;

			// 1. 차트 영역
			let context = document.getElementById(boxId);

			// 2. 차트 추가
			let myChart = new Chart(context, {
				type, 							// 차트 형태
				data : { 						// 차트 셋팅 데이터
					labels 	 : label, 			// x축 데이타
					datasets : dataSetList		// 차트 데이터
				},
				options : {
					responsive: true,
					scales	: {
						y	: {
								min		: 0		// y축 최소값
								, ticks	: {		// y축 숨기기 ( true, false )
									display: yDisplay
								}
							}
					},
					//indexAxis : 'y',

					plugins	: {
						legend: {
							position: legdPosition, // 범례 위치 ( 'top', 'bottom', 'left', 'rigth' )
						},
					}
				}
			});

		}


</script>

<!-- ===== header ====== -->
<header id="header">
	<div id="sub-mv" class="sub-mem">
		<div class="inner">
			<h2>TEST CHART</h2>
			<div class="sub-obj">TEST CHART</div>
		</div>
	</div>
</header>
<!-- ===== /header ====== -->

<!-- ===== container ====== -->
<div id="container">

	<!--container-->
	<div id="cont" class="cont-login">
		<section class="login-wr">

			testChart

		</section>

		<!--dash board st-->
		<div class="dashboard_frame">

			<div class="dashboard_box" id="chartUpBox">
			</div>

		</div>
		<!--//dash board ed-->

		<!--graph st-->
		<div class="graph_frame">
			<div>
				<h4>차트1</h4>
				<div class="graphe_inner" style="width: 45%;">
					<div class="graphcontent" id="barChartArea">
						<!--차트가 그려질 부분-->
						<canvas id="myChart"></canvas>
					</div>
				</div>
			</div>

			<div>
				<h4>차트2</h4>
				<div class="graphe_inner" style="width: 45%;">
					<div class="graphcontent" id="doughnutChartArea">
						<canvas id="doughnutChart"></canvas>
					</div>
				</div>
			</div>
		</div>
		<!--//graph ed-->

	</div>

</div>
<!-- ===== /container ====== -->
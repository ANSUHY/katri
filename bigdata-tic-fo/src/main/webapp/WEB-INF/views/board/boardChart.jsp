<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" uri="/WEB-INF/tlds"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="<c:url value='/js/chart/dist/chart.js'/>"></script>
<script type="text/javascript">

	$(document).ready(function() {
		fn_getBoardChart();
	});

	/*
	 * 함수명 : fn_getBoardChart()
	 * 설   명 : 게시판 차트 데이터 가져오기
	*/
	function fn_getBoardChart() {
		 
		$.blockUI();

		var jData = $("#frm").serialize();
		$("#frm").attr("action", "/board/getBoardChart");
		fn_submitAjax($("#frm"), jData, fn_callBack, 'json');
		
	}

	/*
	 * 함수명 : fn_callBack()
	 * 설   명 : 차트 데이터 가져온 callback 함수
	*/ 
	function fn_callBack(result) {
		
		$.unblockUI();
		
		// console.log(result); // result 결과 출력
		
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

		for (var i = 0; i < list.length; i++) {
			
			let item = list[i];
			
			// 1. 상단 차트 데이터 영역 html 생성
			let html = '';
			
			html += '<div class="dashboard_col">';
			html += '	<h4>' + item.typeNm + '</h4>';
			html += '	<ul class="data-box">';
			html += '		<li class="latbox">';
			html += '			<div class="data_info">';
			html += '				<div>건 수</div>';
			html += '				<div>';
			html += '					<i>' + item.totCnt + '</i>';
			html += '				</div>';
			html += '			</div>';
			html += '		</li>';
			html += '	</ul>';
			html += '</div>';
			
			// 2. 생성한 차트 html append
			$("#chartUpBox").append(html);
			
		}
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
				
				plugins	: {
					legend: {
						position: legdPosition, // 범례 위치 ( 'top', 'bottom', 'left', 'rigth' ) 
					},
				}
			}
		});
		
	}
	
</script>

<div id="container">

	<form id="frm" name="frm" method='get'></form>

	<!--sub Visual st-->
	<div class="subVisual">
		<span>게시판 유형 별 차트</span>
	</div>
	
	<!--//sub Visual ed-->
	<section>
		<!--contentsInner st-->
		<div class="contentsInner">
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
					<div class="graphe_inner">
						<div class="graphcontent" id="barChartArea">
							<!--차트가 그려질 부분-->
							<canvas id="myChart"></canvas>
						</div>
					</div>
				</div>
				
				<div>
					<h4>차트2</h4>
					<div class="graphe_inner">
						<div class="graphcontent" id="doughnutChartArea">
							<canvas id="doughnutChart"></canvas>
						</div>
					</div>
				</div>
			</div>
			<!--//graph ed-->
		</div>
		<!--//contentsInner ed-->
	</section>

</div>

<!-- popUpload-->
<div class="popup errorpop" id="error_pop">
	<div class="pop pop-con">
		<a href="#a" class="close b-close">닫기</a>
		<div class="inner">
			<p id="errorMsg"></p>
			<!-- p>
				<span>(오류 코드 : ooo_ooo_ooo)</span>
			</p -->
			<button class="button_type submit mt31 b-close">확인</button>
		</div>
	</div>
</div>
<!-- /popUpload-->


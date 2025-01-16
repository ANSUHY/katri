<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" uri="/WEB-INF/tlds"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript"
	src="<c:url value='/js/chart/dist/chart.js'/>"></script>
<script type="text/javascript">

	$(document).ready(function() {
				// 데이터 호출
		getData('ALL');
	});

	// 에러 페이지 열기
	function popupAction(msg) {
		$('#error_pop').bPopup({
			onOpen : function() {
				$('body').addClass('scrolloff');
				$("#errorMsg").html('<p>'+msg+'</p>');
			},
			onClose : function() {
				$('body').removeClass('scrolloff')
			},
		});
	}

	// API 데이터 가져오기
	function getData(type) {
		$.blockUI();

		$("#callType").val(type);

		var jData = $("#frm").serialize();
		$("#frm").attr("action", "/dashboard/getData");
		fn_submitAjax($("#frm"), jData, callBack, 'json');
	}

	// API 콜백
	function callBack(result) {
		$.unblockUI();

		var refCntList = result.data.refCntList;
		var resCntList = result.data.resCntList;
		var caseCntList = result.data.caseCntList;

		// 기준일 데이터 여부 확인
		if (refCntList != null) {
			refCntBoxHtml(refCntList);
		}

		// 수집결과 추이
		if (resCntList != null) {
			resCntBoxHtml(resCntList);
		}

		// 수집결과 추이
		if (caseCntList != null) {
			caseCntBoxHtml(caseCntList);
		}

	}

	// 기준일 HTML 적용
	function refCntBoxHtml(list) {
		$("#refCntBox").html("");

		for (var i = 0; i < list.length; i++) {
			var item = list[i];
			var html = '';
			html += '<div class="dashboard_col">';
			html += '	<h4><img src="images/dashboard_'+item.instId+'.png" alt="한국의류시험연구원"></h4>';
			html += '	<ul class="data-box">';
			if (item.statusCd == 'SUCCESS') {
				html += '	<li><span>처리상태</span><em>정상</em></li>';
			} else {
				html += '	<li class="error" onclick="popupAction(\''+item.errLog+'\');"><span>처리상태</span><em class="error">비정상</em></li>';
			}
			html += '		<li class="latbox">';
			html += '			<div class="data_info"><div>데이터</div><div><i>'
					+ item.gatherRecordCnt + '</i></div></div>';
			html += '			<div class="data_info"><div>이미지</div><div><i>'
					+ item.gatherImageCnt + '</i></div></div>';
			html += '		</li>';
			html += '	</ul>';
			html += '</div>';
			$("#refCntBox").append(html);
		}
	}
	// 그래프 그리기
	function chrtAction (obj) {

		var colols = [
			'rgba(204, 136, 247, 1)',
			'rgba(126, 211, 168, 1)'
		];

		var boxId = obj.id;
		var type = obj.type;
		var titles = obj.titles;
		var datas = obj.datas;

		var context = document.getElementById(boxId).getContext('2d');

		var myChart = new Chart(context, {
			type, // 차트의 형태
			data : { // 차트에 들어갈 데이터
				labels : obj.labelList, //x 축
				datasets : [
					{
						label : titles[0], //차트 제목
						data : datas[0],//x축 label에 대응되는 데이터 값
						fill: false,
						lineTension: 0,
						backgroundColor : [ colols[0] ], //색상
					},
					{
						label : titles[1], //차트 제목
						data : datas[1],//x축 label에 대응되는 데이터 값
						fill: false,
						lineTension: 0,
						backgroundColor : [ colols[1] ], //색상
					}
				]
			},
			options : {
				responsive: true,
				scales: {
			      y: {
			        min: 0
			      }
			    },
				plugins: {
					legend: {
						position: 'bottom',
					},
				}
			}
		});

	}

	// 수집률 결과 HTML 적용
	function resCntBoxHtml (list) {
		var labelList = []; // X 축 명
		var sussCnts = []; // 성공 카운트
		var failCnts = []; // 실패 카운트

		$(".graphcontent").eq(0).html('<canvas id="resChart"></canvas>');

		for (var i = 0 ; i < list.length ; i++) {
			var item = list[i];
			labelList.push(item.referenceDt);
			sussCnts.push(item.sussCnt);
			failCnts.push(item.failCnt);
		}

		var obj = {
			id:'resChart',
			type: 'bar',
			titles:['정상', '비정상'],
			labelList,
			datas: [sussCnts, failCnts]
		}

		chrtAction(obj);

	}
	// 수집률 건수 HTML 적용
	function caseCntBoxHtml (list) {
		var labelList = []; // X 축 명
		var recordCnts = []; // 데이터 카운트
		var imageCnts = []; // 실패 카운트

		$(".graphcontent").eq(1).html('<canvas id="caseChart"></canvas>');

		for (var i = 0 ; i < list.length ; i++) {
			var item = list[i];
			labelList.push(item.referenceDt);
			recordCnts.push(item.gatherRecordCnt);
			imageCnts.push(item.gatherImageCnt);
		}

		var obj = {
			id:'caseChart',
			type: 'line',
			titles:['데이터', '이미지'],
			labelList,
			datas: [recordCnts, imageCnts]
		}

		chrtAction(obj);

	}

	// 수집 건수 추이 기관명 지정
	function instTypeChg () {
		var text = $("#userSelectType").val();
		$("#instId").val(text);
		getData('CASE');
	}
</script>



<div id="container">
	<form id="frm" name="frm" method='post'>
		<input type="hidden" id="callType" name="callType" value="ALL" />
		<input type="hidden" id="selectDate" name="selectDate" value="${referenceDt}" />
		<input type="hidden" id="instId" name="instId" value="" />
	</form>

	<!--sub Visual st-->
	<div class="subVisual">
		<span>데이터 수집현황</span>
	</div>
	<!--//sub Visual ed-->
	<section>
		<!--contentsInner st-->
		<div class="contentsInner">
			<!--dash board st-->
			<div class="dashboard_frame">
				<div class="borard_date">
					기준일 : <span>${referenceDt}</span>
				</div>
				<div class="dashboard_box" id="refCntBox">
					<div class="dashboard_col">
						<h4>
							<img src="images/dashboard_katri.png" alt="한국의류시험연구원">
						</h4>
						<ul class="data-box">
							<li><span>-</span><em>-</em></li>
							<li class="latbox">
								<div class="data_info"><div>데이터</div><div><i>0</i></div></div>
								<div class="data_info"><div>이미지</div><div><i>0</i></div></div>
							</li>
						</ul>
					</div>
					<div class="dashboard_col">
						<h4>
							<img src="images/dashboard_kcl.png" alt="kcl">
						</h4>
						<ul class="data-box">
							<li><span>-</span><em>-</em></li>
							<li class="latbox">
								<div class="data_info"><div>데이터</div><div><i>0</i></div></div>
								<div class="data_info"><div>이미지</div><div><i>0</i></div></div>
							</li>
						</ul>
					</div>
					<div class="dashboard_col">
						<h4>
							<img src="images/dashboard_fiti.png" alt="fiti">
						</h4>
						<ul class="data-box">
							<li><span>-</span><em>-</em></li>
							<li class="latbox">
								<div class="data_info"><div>데이터</div><div><i>0</i></div></div>
								<div class="data_info"><div>이미지</div><div><i>0</i></div></div>
							</li>
						</ul>
					</div>
					<div class="dashboard_col">
						<h4>
							<img src="images/dashboard_ktc.png" alt="ktc">
						</h4>
						<ul class="data-box">
							<li><span>-</span><em>-</em></li>
							<li class="latbox">
								<div class="data_info"><div>데이터</div><div><i>0</i></div></div>
								<div class="data_info"><div>이미지</div><div><i>0</i></div></div>
							</li>
						</ul>
					</div>
					<div class="dashboard_col">
						<h4>
							<img src="images/dashboard_kotiti.png" alt="kotiti">
						</h4>
						<ul class="data-box">
							<li><span>-</span><em>-</em></li>
							<li class="latbox">
								<div class="data_info"><div>데이터</div><div><i>0</i></div></div>
								<div class="data_info"><div>이미지</div><div><i>0</i></div></div>
							</li>
						</ul>
					</div>
					<div class="dashboard_col">
						<h4>
							<img src="images/dashboard_ktl.png" alt="ktl">
						</h4>
						<ul class="data-box">
							<li><span>-</span><em>-</em></li>
							<li class="latbox">
								<div class="data_info"><div>데이터</div><div><i>0</i></div></div>
								<div class="data_info"><div>이미지</div><div><i>0</i></div></div>
							</li>
						</ul>
					</div>
					<div class="dashboard_col">
						<h4>
							<img src="images/dashboard_ktr.png" alt="ktr">
						</h4>
						<ul class="data-box">
							<li><span>-</span><em>-</em></li>
							<li class="latbox">
								<div class="data_info"><div>데이터</div><div><i>0</i></div></div>
								<div class="data_info"><div>이미지</div><div><i>0</i></div></div>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<!--//dash board ed-->
			<!--graph st-->
			<div class="graph_frame">
				<div>
					<h4>수집결과 추이(전체)</h4>
					<div class="graphe_inner">
						<div class="graphcontent">
							<!--차트가 그려질 부분-->
							<canvas id="myChart"></canvas>
						</div>
					</div>
				</div>
				<div>
					<h4>
						수집건수 추이(전체)
						<span>
							<select id="userSelectType" style="width: 140px;" onchange="instTypeChg();">
									<option value="">전체</option>
									<c:forEach items="${certInstList}" var="item">
										<option value="${item.comnCd}">${item.comnCdNm}</option>
									</c:forEach>
							</select>
						</span>
					</h4>
					<div class="graphe_inner">
						<div class="graphcontent">
							<canvas id="caseChart"></canvas>
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


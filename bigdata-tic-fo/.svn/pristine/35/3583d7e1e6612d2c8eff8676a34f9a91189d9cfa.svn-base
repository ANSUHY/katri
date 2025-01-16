<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" 		uri="/WEB-INF/tlds" %>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags" %>
<%@ page import="com.katri.common.Const" %>

<script type="text/javascript" src="<c:url value='/asset/js/chart/dist/chart.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/asset/js/chart/custom.color.js'/>"></script>

<script type="text/javascript">

	const strHtml 		= "데이터가 없습니다.";
	const strLoadImg	= "<img src='/asset/images/common/ajax-loader.gif'/>";

	$(document).ready(function() {
		/* chart.js 함수 호출 */
		fn_getMyDataStatInfoDataInit();

		/* 검색 조건 (라디오 버튼 -전체/신청/제출 )*/
		$('input[type=radio][name=r-wr]').change(function(){
			let checkedVal = $('input:radio[name=r-wr]:checked').val();
			$("#searchType").val(checkedVal);
			fn_getMyDataStatInfoDataInit();
		});

		/* 검색 조건 (셀렉트 박스 -기관)*/
		$('#inst-select').change(function(){
			let selectedVal = $('#inst-select option:selected').val();
			$("#instId").val(selectedVal);
		});
	});

	/* [조회] 버튼 클릭 시 chart.js 다시 생성 */
	function fn_goSearchMyDataStat() {
		fn_getMyDataStatInfoDataInit();
	}

	/*
	 * 함수명 : fn_getMyDataStatInfoDataInit
	 * 설 명 : [나의 시험인증 현황] - 통계 데이터 조회
	 */
	function fn_getMyDataStatInfoDataInit() {
		const 	jData 	= $("#frm_myDataStatForm").serialize();
		let 	url		= "";

		// [0]. [통계] -> [~년 기준 의뢰 현황] -> [인증] 데이터 조회
		$("#stat-byYear-chartArea1").html(strLoadImg);
		url	= "/platformSvc/myData/myDataStat/getStatCertDataByYear";
		fn_submitAjax($("#frm_myDataStatForm"), jData, fn_getMyDataStatCertDataByYear, "json", url);

		// [1]. [통계] -> [~년 기준 의뢰 현황] -> [성적서] 데이터 조회
		$("#stat-byYear-chartArea2").html(strLoadImg);
		url = "/platformSvc/myData/myDataStat/getStatRpRtDataByYear";
		fn_submitAjax($("#frm_myDataStatForm"), jData, fn_getMyDataStatRprtDataByYear, "json", url);

		//[2]. [통계] -> [~년 기준 의뢰 현황] -> [시험항목] 데이터 조회
		$("#stat-byYear-chartArea3").html(strLoadImg);
		url = "/platformSvc/myData/myDataStat/getStatTestDataByYear";
		fn_submitAjax($("#frm_myDataStatForm"), jData, fn_getMyDataStatTestDataByYear, "json", url);

		//[3]. [통계] -> [~월 기준 의뢰 현황] -> [인증] 데이터 조회
		$("#stat-byMonth-chartArea1").html(strLoadImg);
		url = "/platformSvc/myData/myDataStat/getStatCertDataByMonth";
		fn_submitAjax($("#frm_myDataStatForm"), jData, fn_getMyDataStatCertDataByMonth, "json", url);

		//[4]. [통계] -> [~월 기준 의뢰 현황] -> [성적서] 데이터 조회
		$("#stat-byMonth-chartArea2").html(strLoadImg);
		url = "/platformSvc/myData/myDataStat/getStatRprtDataByMonth";
		fn_submitAjax( $("#frm_myDataStatForm"), jData, fn_getMyDataStatRprtDataByMonth, "json", url);

		//[5]. [통계] -> [~월 기준 의뢰 현황] -> [시험항목] 데이터 조회
		$("#stat-byMonth-chartArea3").html(strLoadImg);
		url ="/platformSvc/myData/myDataStat/getStatTestDataByMonth";
		fn_submitAjax($("#frm_myDataStatForm"), jData, fn_getMyDataStatTestDataByMonth, "json", url);

		//[6]. [통계] -> [월별 내 의뢰 추이]
		$("#stat-overYear-chartArea").html(strLoadImg);
		url = "/platformSvc/myData/myDataStat/getStatReqDataByMonth";
		fn_submitAjax($("#frm_myDataStatForm"), jData, fn_getMyDataStatusOverYear, "json", url);

	}

	/*
	 * 함수명 : fn_getMyDataStatCertData
	 * 설 명 : [~년 기준 의뢰 현황] -> [인증] 차트 작성
	 */
	function fn_getMyDataStatCertDataByYear(result) {

		//0. [~년 기준 의뢰 현황] -> [인증] 차트 작성
		if(result.data != null) {
			/* 건수 표시 */
			$("#lastYearCount1").text(fn_addComma(result.data[0].cnt, 0));
			$("#thisYearCount1").text(fn_addComma(result.data[1].cnt, 0));
			/* 퍼센트 표시 */
			$("#stat-data-percentage").text(fn_getPercentage(result.data[0].cnt, result.data[1].cnt));

			/* 색상 설정 */
			let colorSets = [CUSTOM_COLOR_NAVY, CUSTOM_COLOR_ORANGE];

			/* chart.js 함수 호출 */
			fn_chartJsStatDataByYear1(result.data, "stat-byYear-chartArea1", "myStatCertCanvas1", colorSets);
		} else {
			$("#stat-byYear-chartArea1").html(strHtml);
		}
	}


	 /*
	  * 함수명 : fn_getMyDataStatCertData
	  * 설 명 : [~년 기준 의뢰 현황] -> [성적서] 차트 작성
	  */
	function fn_getMyDataStatRprtDataByYear(result) {

		//0. [~년 기준 의뢰 현황] -> [성적서] 차트 작성
		if(result.data != null) {
			/* 건수 표시 */
		  	$("#lastYearCount2").text(fn_addComma(result.data[0].cnt, 0));
		  	$("#thisYearCount2").text(fn_addComma(result.data[1].cnt, 0));
		  	/* 퍼센트 표시 */
		  	$("#stat-data-percentage2").text(fn_getPercentage(result.data[0].cnt, result.data[1].cnt));
		  	/* 색상 설정 */
		  	let colorSets = [CUSTOM_COLOR_NAVY, CUSTOM_COLOR_YELLOW];
		  	/* chart.js 함수 호출 */
		  	fn_chartJsStatDataByYear1(result.data, "stat-byYear-chartArea2", "myStatRprtCanvas1", colorSets);
		} else {
			$("stat-byYear-chartArea2").html(strHtml);
		}
	}

	 /*
	 * 함수명 : fn_getMyDataStatCertData
	 * 설 명 : [~년 기준 의뢰 현황] -> [시험항목] 차트 작성
	 */
	 function fn_getMyDataStatTestDataByYear(result) {

		 //0. [~년 기준 의뢰 현황] -> [시험항목] 차트 작성
		 if(result.data != null) {
			 /* 건수 표시 */
			$("#lastYearCount3").text(fn_addComma(result.data[0].cnt, 0));
			$("#thisYearCount3").text(fn_addComma(result.data[1].cnt, 0));
			/* 퍼센트 표시 */
			$("#stat-data-percentage3").text(fn_getPercentage(result.data[0].cnt, result.data[1].cnt));
			/* 색상 설정 */
			let colorSets = [CUSTOM_COLOR_NAVY, CUSTOM_COLOR_GREEN];
			/* chart.js 함수 호출 */
			fn_chartJsStatDataByYear1(result.data, "stat-byYear-chartArea3", "myStatTestCanvas1", colorSets);
		 }
	 }

	 /*
		 * 함수명 : fn_getMyDataStatCertData
		 * 설 명 : [~월 기준 의뢰 현황] -> [인증] 차트 작성
		 */
		 function fn_getMyDataStatCertDataByMonth(result) {

			 //0. [~년 기준 의뢰 현황] -> [인증] 차트 작성
			 if(result.data != null) {
				 /* 건수 표시 */
			  	$("#lastMonthCount1").text(fn_addComma(result.data[0].cnt, 0));
			  	$("#thisMonthCount1").text(fn_addComma(result.data[1].cnt, 0));
			  	/* 퍼센트 표시 */
			  	$("#stat-data-percentage4").text(fn_getPercentage(result.data[0].cnt, result.data[1].cnt));
			  	/* 색상 설정 */
			  	let colorSets = [CUSTOM_COLOR_NAVY, CUSTOM_COLOR_ORANGE];
			  	/* chart.js 함수 호출 */
			  	fn_chartJsStatDataByYear1(result.data, "stat-byMonth-chartArea1", "myStatCertCanvas2", colorSets);

			 }
		 }

	 /*
	 * 함수명 : fn_getMyDataStatRprtDataByMonth
	 * 설 명 : [~월 기준 의뢰 현황] -> [성적서] 차트 작성
	 */
	 function fn_getMyDataStatRprtDataByMonth(result) {

		 //0. [~년 기준 의뢰 현황] -> [성적서] 차트 작성
		 if(result.data != null) {
			  /* 건수 표시 */
			  $("#lastMonthCount2").text(fn_addComma(result.data[0].cnt, 0));
			  $("#thisMonthCount2").text(fn_addComma(result.data[1].cnt, 0));
			  /* 퍼센트 표시 */
			  $("#stat-data-percentage5").text(fn_getPercentage(result.data[0].cnt, result.data[1].cnt));

			  /* 색상 설정 */
			  let colorSets = [CUSTOM_COLOR_NAVY, CUSTOM_COLOR_YELLOW];
			  /* chart.js 함수 호출 */
			  fn_chartJsStatDataByYear1(result.data, "stat-byMonth-chartArea2", "myStatRprtCanvas2", colorSets);
		 }
	 }

	 /*
	 * 함수명 : fn_getMyDataStatTestDataByMonth
	 * 설 명 : [~월 기준 의뢰 현황] -> [시험항목] 차트 작성
	 */
	 function fn_getMyDataStatTestDataByMonth(result) {

			 //0. [~년 기준 의뢰 현황] -> [시험항목] 차트 작성
			 if(result.data != null) {
				  /* 건수 표시 */
				  $("#lastMonthCount3").text(fn_addComma(result.data[0].cnt, 0));
				  $("#thisMonthCount3").text(fn_addComma(result.data[1].cnt, 0));
				  /* 퍼센트 표시 */
				  $("#stat-data-percentage6").text(fn_getPercentage(result.data[0].cnt, result.data[1].cnt));

				  /* 색상 설정 */
				  let colorSets = [CUSTOM_COLOR_NAVY, CUSTOM_COLOR_GREEN];

				 /* chart.js 함수 호출 */
				  fn_chartJsStatDataByYear1(result.data,"stat-byMonth-chartArea3", "myStatTestCanvas2", colorSets);
			 }
	 }

 	/*
	 * 함수명 : fn_getMyDataStatTestDataByMonth
	 * 설 명 : [~월 기준 의뢰 현황] -> [시험항목] 차트 작성
	 */
	function fn_getMyDataStatusOverYear(result) {

		//0. [월별 내 의뢰 추이] 차트 작성
		if(result.data != null) {
			fn_setChartJsStatDataOverYear(result.data.certDataList,
					                      result.data.rprtDataList,
					                      result.data.testDataList,
					                      "stat-overYear-chartArea",
					                      "stat-overYear-canvasArea");
		}
	}

	/*
	 * 함수명 : fn_getPercentage
	 * 설 명 : 증감 수치 구하기
	 */
	function fn_getPercentage(pVal1 , pVal2) {
		let lPctg = "0";

		if(pVal1 > pVal2) {
			if(pVal2 == 0)
				lPctg = "-" + fn_addComma( pVal1*100 , 0);
			else
				lPctg = fn_addComma((( (pVal2-pVal1) / pVal1) * 100).toFixed(0), 0);
		} else if(pVal2 > pVal1) {
			if(pVal1 == 0)
				lPctg = "+" + fn_addComma( pVal2*100 , 0);
			else
				lPctg = "+" + fn_addComma((( (pVal2-pVal1) / pVal1) * 100).toFixed(0), 0);
		}

		return lPctg + "%";
	}




	/*
	 * 함수명 : fn_chartJsStatDataByYear1
	 * 설 명 : [통계] -> [~년 기준 의뢰 현황], [~월 기준 의뢰 현황] 차트 그리기
	 */
	function fn_chartJsStatDataByYear1(data, chartArea, canvasArea, colorSets) {
		//1. 차트 영역 설정
		$("#" + chartArea).html("<canvas id='" + canvasArea + "' style='height:100%; width:100%; margin:0 auto;' > </canvas>" );

		//2. 차트 데이터
		let padding = 0;
		let labels = [''];

		let datasets = [{
				label : '',
				data: [''],
				backgroundColor: colorSets[0],
				borderColor: colorSets[0],
				borderWidth: 1,
			},
			{
				label : '',
				data: [''],
				backgroundColor: colorSets[1],
				borderColor: colorSets[1],
				borderWidth: 1,
			}];

			/* data 넣어주기 */
			for(let i = 0; i < datasets.length; i++) {
				datasets[i].label   = data[i].dt;
				datasets[i].data[0] = data[i].cnt;
			}


		//3. 차트 컬러 설정
		let arrColors = [colorSets[0], colorSets[1]];

		//5. 데이터 담기
		const obj = {
				id		: canvasArea,
				type	: "bar",
				datasets,
				labels,
				arrColors,
				yDisplay: true,
				position: "bottom",
				padding,
				display: true,
		}

		//5. 차트 그리기
		fn_makeMyDataStatChartJs(obj);
	}


		/*
		 * 함수명 : fn_chartJsStatDataByYear1
		 * 설 명 : [통계] -> [월별 내 의뢰 추이] 차트 그리기
		 */
	function fn_setChartJsStatDataOverYear(certDataList, rprtDataList, testDataList, chartArea, canvasArea) {
		//1. 차트 영역 설정
		$("#" + chartArea).html("<canvas id='" + canvasArea + "'  style='height:100%; width:100%; margin:0 auto;'> </canvas>");

		//2. 차트 데이터
		let labels = [];
		let datasets = [];

		//3. 차트 컬러 설정
		let arrColors = [
			CUSTOM_COLOR_ORANGE		// 인증
			, CUSTOM_COLOR_YELLOW	// 성적서
			, CUSTOM_COLOR_GREEN	// 시험항목
		];

		//4. datasets에 담기 위한 객체 생성 및 초기화
		let certChartData = {label : "인증" , data : [] }; //인증
		let rprtChartData = {label : "성적서", data : [] }; //성적서
		let testChartData = {label : "시험항목", data : [] }; //시험항목

		//5. 데이터 설정
		for(let i = 0; i < certDataList.length; i++){
			//labels 날짜 담기
			labels.push(certDataList[i].dt);
			//데이터 담기
			certChartData.data.push(certDataList[i].cnt);
			rprtChartData.data.push(rprtDataList[i].cnt);
			testChartData.data.push(testDataList[i].cnt);
		}

		//6. 차트 색상 설정
		fn_chartColorSetting(certChartData, arrColors[0], arrColors[0]);
		fn_chartColorSetting(rprtChartData, arrColors[1], arrColors[1]);
		fn_chartColorSetting(testChartData, arrColors[2], arrColors[2]);

		//6. 데이터 담기
		datasets.push(certChartData);
		datasets.push(rprtChartData);
		datasets.push(testChartData);

		//7. 차트데이터 obj 셋팅
		const obj = {
				id: canvasArea,
				type: "line",
				datasets,
				labels,
				yDisplay: true,
				position: "bottom",
				display: true,
		};

		//8. 차트 그리기
		fn_makeMyDataStatChartJs(obj);
	}


	/*
	 * 함수명	: fn_makeMyDataStatChartJs
	 * 설 명 : chart.js 그리는 함수
	 */
	function fn_makeMyDataStatChartJs(obj) {
		//0. 차트 데이터 가져오기
		const id = obj.id;
		const type = obj.type;
		const labels = obj.labels;
		const datasets = obj.datasets;
		const position = obj.position;
		const padding = obj.padding;
		const display = obj.display;

		//1. 차트 영역
		const ctx = document.getElementById(id);

		//2. 차트 그리기
		const myChart = new Chart(ctx, {
			type : type,
			data : {
				labels   : labels,
				datasets : datasets,
			},
			options: {
				reponsive: true,
				scales: {
					y: {
						min: 0,
					//	stepSize: 1,
					//	max: max,
						display: display,
					},
				},
				plugins: {
					legend: {
						  position: position,
					},//
				}//end of plugins
			}
		});
	}

	/**
	 * 차트 색상 설정
	 * @param chartData 	해당 차트 데이터
	 * @param bgColor 		차트 그래프 배경 색
	 * @param borderColor 	차트 그래프 선 색
	 */
	function fn_chartColorSetting(chartData, bgColor, borderColor) {
		chartData.backgroundColor = bgColor;
		chartData.borderColor = borderColor;
	}

	function fn_chartColorSetting(chartData, bgColor, borderColor) {
		chartData.backgroundColor = bgColor;
		chartData.borderColor = borderColor;
	}
</script>
    <!-- ===== header ====== -->
    <header id="header">
        <div id="sub-mv" class="sub-myservice">
            <div class="inner">
                <!--<h2>내 손안의 시험인증</h2> 박태우 수정(2023.01.03)-->
                <h2>플랫폼 서비스</h2>
                <!--<p>시험인증 빅데이터<br class="mo-block"> 내 손안의 시험인증 서비스입니다.</p> 박태우 수정(2023.01.03)-->
                <div class="sub-obj">오브젝트</div>
            </div>
        </div>
    </header>
    <!-- ===== /header ====== -->

	<form id="frm_myDataStatForm" name="frm_myDataStatForm" method="GET" action="/platformSvc/myData/myDataStat/myDataStatInfo">
		<input type="hidden" id="searchType" name="searchType" value="${myDataStatSelectReq.searchType}" />
		<input type="hidden" id="instId" name="instId" value="${myDataStatSelectReq.instId}" />
		<input type="hidden" id="brno" name="brno" value="${dateData.brno}"/>
	</form>

	<!-- ===== container ====== -->
    <div id="container">

        <!--breadcrumb-wr-->
		<jsp:include page="/WEB-INF/views/layout/breadcrumbWr.jsp">
			<jsp:param name="menuTopNo" value="02"/>
			<jsp:param name="menuSubNo" value="0201"/>
		</jsp:include>
		<!-- // breadcrumb-wr-->

        <!--container-->
        <div id="cont" class="cont-myservice">
          <!--tit-->
          <div class="cont-platform-tit">
            <h2 class="tit">나의 시험인증 현황</h2>
          </div>

          <!--서브메뉴-->
          <ul class="sub-tabs grid3 type02">
            <li class="on"><a href="">통계</a></li>
            <li class=""><a href="/platformSvc/myData/myDataStat/myDataCertInfo">인증</a></li>
            <li class=""><a href="/platformSvc/myData/myDataStat/myDataRprtInfo">성적서</a></li>
            <!--li class=""><a href="UI-FRONT-0304.html">시험항목</a></li-->
          </ul>

          <div class="slt-list-wr">									<%-- 1,2,3 으로 value 줬음 --%>
            <label for="r01"><input type="radio" id="r01" name="r-wr" value="" ${myDataStatSelectReq.instId eq null ? "checked" : ""}/><span>전체</span></label>
            <label for="r02"><input type="radio" id="r02" name="r-wr" value="2"/><span>신청</span></label>
            <label for="r03"><input type="radio" id="r03" name="r-wr" value="3"/><span>제출</span></label>
            <select class="select select-mysvc" id="inst-select">
            	<option value="">전체</option>
            	<c:forEach var="instList" items="${instList}">
					 <option value="${instList.instId}">${instList.instEngNm}</option>
            	</c:forEach>
            </select>
            <button type="button" class="btn sch" onclick="fn_goSearchMyDataStat();">조회</button>
          </div>

          <h4 class="stit">${dateData.year}년 의뢰 현황</h4>
          <ul class="chart-list grid3">
            <%-- ////////////// 인증 //////////////// --%>
            <li>
              <div class="hd"><h5 class="stit">인증</h5></div>
              <div id="stat-byYear-chartArea1" style="height:260px"></div>
              <!--  <div class="bd chart-circle">
              </div> -->
              <div class="ft">
                <div class="count">
                  <strong id="thisYearCount1"></strong>건
                </div>
                <div class="count01">
                  전년 <strong id="lastYearCount1"></strong>건(<span id="stat-data-percentage"></span>)
                </div>
              </div>
            </li>
            <%--//////////////////////////////////////// --%>

			<%--///////////// 성적서 ///////////////////// --%>
            <li>
              <div class="hd"><h5 class="stit">성적서</h5></div>
              <div id="stat-byYear-chartArea2" style="height:260px"></div>
              <!--  <div class="bd chart-circle">
              </div> -->
              <div class="ft">
                <div class="count">
                  <strong id="thisYearCount2"></strong>건
                </div>
                <div class="count01">
                  전년 <strong id="lastYearCount2"></strong>건(<span id="stat-data-percentage2"></span>)
                </div>
              </div>
            </li>
            <%--/////////////// 시험항목 ///////////////////////--%>
            <li>
              <div class="hd"><h5 class="stit">시험항목</h5></div>
              <div id="stat-byYear-chartArea3" style="height:260px"></div>
              <!--
              <div class="bd chart-circle">
              </div>
              -->
              <div class="ft">
                <div class="count">
                  <strong id="thisYearCount3"></strong>건
                </div>
                <div class="count01">
                  전년 <strong id="lastYearCount3"></strong>건(<span id="stat-data-percentage3"></span>)
                </div>
              </div>
            </li>

            <%--//////////////////////////////////////////// --%>
          </ul>

          <%--////////// 월 기준 의뢰현황 -> [인증] //////////////--%>
          <h4 class="stit">${dateData.year}년 ${dateData.month}월 의뢰 현황</h4>
          <ul class="chart-list grid3">
            <li>
              <div class="hd"><h5 class="stit">인증</h5></div>
               <div id="stat-byMonth-chartArea1" style="height:260px"></div>
             <!--
              <div class="bd chart-circle">
              </div>
              -->
              <div class="ft">
                <div class="count">
                  <strong id="thisMonthCount1"></strong>건
                </div>
                <div class="count01">
                  전월 <strong id="lastMonthCount1"></strong>건(<span id="stat-data-percentage4"></span>)
                </div>
              </div>
            </li>
            <%--///////////////////////////////////////// --%>

            <%--/////////// 월 기준 의뢰현황 -> [성적서] ////////// --%>
            <li>
              <div class="hd"><h5 class="stit">성적서</h5></div>
              <div id="stat-byMonth-chartArea2" style="height:260px"></div>
               <!--
              <div class="bd chart-circle">
              </div>
              -->
              <div class="ft">
                <div class="count">
                  <strong id="thisMonthCount2"></strong>건
                </div>
                <div class="count01">
                  전월 <strong id="lastMonthCount2"></strong>건(<span id="stat-data-percentage5"></span>)
                </div>
              </div>
            </li>
            <%--///////////////////////////////////////////// --%>

            <%--/////////////월 기준 의뢰현황 -> [시험항목] ///////// --%>
            <li>
              <div class="hd"><h5 class="stit">시험항목</h5></div>
           		<div id="stat-byMonth-chartArea3" style="height:260px"></div>
               <!--
              <div class="bd chart-circle">
              </div>
              -->
              <div class="ft">
                <div class="count">
                  <strong id="thisMonthCount3"></strong>건
                </div>
                <div class="count01">
                  전월 <strong id="lastMonthCount3"></strong>건(<span id="stat-data-percentage6"></span>)
                </div>
              </div>
            </li>
            <%--////////////////////////////////////////////// --%>
          </ul>
          <h4 class="stit tmg">최근 1년 의뢰 추이</h4>
          <ul class="chart-list noMg">

            <li><div class="bd chart-rect" id="stat-overYear-chartArea"></div></li>
          </ul>

          <div class="btn-wr">
            <a href="/platformSvc/myData/myInfoCert/myCertList" class="btn">나의 시험인증 정보조회 바로가기</a>
          </div>

        </div>


    </div>
    <!-- ===== /container ====== -->

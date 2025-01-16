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

	$(document).ready(function(){

		// 초기 셋팅
		fn_myDataRprtInfoInit();

		// 성적서(마이데이터) 데이터 조회
		fn_getMyDataRprtInfoDataInit();

	});

	/*
	 * 함수명 : fn_myDataRprtInfoInit
	 * 설 명 : [나의 시험인증 현황] - 성적서 초기 셋팅
	*/
	function fn_myDataRprtInfoInit() {

		// 입장 구분 변경 시,
		$(document).on("change", "input[id^='searchType']", function(){

			let selType = $(this).val();

			// 신청 인 경우 - [기업별 성적서 현황] 비노출
			if( selType === "APPLY" ) {
				$("#entRprtInfoHead").hide();
				$("#entRprtInfoBody").hide();
				$("#entRprtInfoTip").hide();
			// 전체/제출 인 경우 - [기업별 성적서 현황] 노출
			} else {
				$("#entRprtInfoHead").show();
				$("#entRprtInfoBody").show();
				$("#entRprtInfoTip").show();
			}

			// 차트 데이터 조회
			fn_getMyDataRprtInfoDataInit();

		});

	}

	/*
	 * 함수명 : fn_getMyDataRprtInfoDataInit
	 * 설 명 : [나의 시험인증 현황] - 성적서 데이터 조회
	*/
	function fn_getMyDataRprtInfoDataInit() {

		const 	jData 	= $("#frm_rprtInfo").serialize();
		let 	url		= "";

		// [0]. [성적서] -> [판정결과별 성적서 비율(건수)] 차트 데이터 조회
		$("#rprtPassRateChart").html(strLoadImg);
		url		= "/platformSvc/myData/myDataStat/getMyDataRprtPassRateData";
		fn_submitAjax( $("#frm_rprtInfo"), jData, fn_getMyDataRprtPassRateData		, "json", url );

		// [1]. [성적서] > [월별 판정결과별 성적서 추이(건수)] 차트 데이터 조회
		$("#rprtMonthPassCntChart").html(strLoadImg);
		url		= "/platformSvc/myData/myDataStat/getMyDataRprtMonthPassCnt";
		fn_submitAjax( $("#frm_rprtInfo"), jData, fn_getMyDataRprtMonthPassCntData	, "json", url );

		// [2]. [성적서] > [TOP10 품목별 성적서 비율(건수)], [월별 TOP10 품목별 성적서 추이(건수)] 차트 데이터 조회
		$("#rprtTopPdctgRateChart").html(strLoadImg);
		$("#rprtTopPdctgCntChart").html(strLoadImg);
		url		= "/platformSvc/myData/myDataStat/getMyDataRprtTopPdctg";
		fn_submitAjax( $("#frm_rprtInfo"), jData, fn_getMyDataRprtTopPdctg			, "json", url );

		// [3]. [성적서] > [TOP10 시험항목별 비율(건수)], [월별 TOP10 시험항목별 추이(건수)] 차트 데이터 조회
		$("#rprtTopItemRateChart").html(strLoadImg);
		$("#rprtTopItemCntChart").html(strLoadImg);
		url		= "/platformSvc/myData/myDataStat/getMyDataRprtTopItem";
		fn_submitAjax( $("#frm_rprtInfo"), jData, fn_getMyDataRprtTopItem			, "json", url );

		// [입장 구분] - 신청이 아닌 경우
		if ( $("input:radio[name='searchType']:checked").val() !== "APPLY" ) {

			// [4]. [성적서] > [TOP10 기업별 성적서 비율(건수)], [TOP10 기업별 성적서 추이(건수)] 차트 데이터 조회
			$("#rprtTopEntRateChart").html(strLoadImg);
			$("#rprtTopEntCntChart").html(strLoadImg);
			url		= "/platformSvc/myData/myDataStat/getMyDataRprtTopEnt";
			fn_submitAjax( $("#frm_rprtInfo"), jData, fn_getMyDataRprtTopEnt		, "json", url );

		}

	}

	/*
	 * 함수명 : fn_getMyDataRprtPassRateData
	 * 설 명 : [성적서] -> [성적서 합격률] 차트 작성
	*/
	function fn_getMyDataRprtPassRateData( result ) {
		// 0. [성적서] -> [판정결과별 성적서 비율(건수)] 차트 작성
		if ( result.data.rprtPassRateList != null && result.data.rprtPassRateList.length > 0 ) {
			fn_getMyDataRprtPassRateChart( result.data.rprtPassRateList, "rprtPassRateChart", "rprtPassRateChartCanvas");
		} else {
			$("#rprtPassRateChart").html(strHtml);
		}

	}

	/*
	 * 함수명 : fn_getMyDataRprtMonthPassCntData
	 * 설 명 : [성적서] > [월별 판정결과별 성적서 추이(건수)] 차트 작성
	*/
	function fn_getMyDataRprtMonthPassCntData( result ) {

		// 1. [성적서] > [월별 판정결과별 성적서 추이(건수)] 차트 작성
		if ( result.data.rprtMonthPassCntList != null && result.data.rprtMonthPassCntList.length > 0 ) {
			fn_getMyDataRprtMonthPassCntChart( result.data.rprtMonthPassCntList, "rprtMonthPassCntChart", "rprtMonthPassCntChartCanvas" );
		} else {
			$("#rprtMonthPassCntChart").html(strHtml);
		}

	}

	/*
	 * 함수명 : fn_getMyDataRprtMonthPassCntData
	 * 설 명 : [성적서] > [TOP10 품목별 성적서 비율(건수)] 차트 작성
	*/
	function fn_getMyDataRprtTopPdctg( result ) {

		// 2. [성적서] > [TOP10 품목별 성적서 비율(건수)] 차트 작성
		if ( result.data.rprtTopPdctgRateList != null && result.data.rprtTopPdctgRateList.length > 0 ) {
			fn_getMyDataRprtTopPdctgChart( result.data.rprtTopPdctgRateList, "rprtTopPdctgRateChart", "rprtTopPdctgRateChartCanvas" );
		} else {
			$("#rprtTopPdctgRateChart").html(strHtml);
		}

		// 3. [성적서] > [월별 TOP10 품목별 성적서 추이(건수)] 차트 작성
		if ( result.data.rprtTopPdctgCntList != null && result.data.rprtTopPdctgCntList.length > 0 ) {
			fn_getMyDataRprtTopPdctgCntChart( result.data.rprtTopPdctgCntList, "rprtTopPdctgCntChart", "rprtTopPdctgCntChartCanvas" );
		} else {
			$("#rprtTopPdctgCntChart").html(strHtml);
		}

	}

	/*
	 * 함수명 : fn_getMyDataRprtTopItem
	 * 설 명 : [성적서] > [TOP10 시험항목별 비율(건수)], [월별 TOP10 시험항목별 추이(건수)] 차트 작성
	*/
	function fn_getMyDataRprtTopItem( result ) {

		// 4. [성적서] > [TOP10 시험항목별 비율(건수)] 차트 작성
		if ( result.data.rprtTopItemRateList != null && result.data.rprtTopItemRateList.length > 0 ) {
			fn_getMyDataRprtTopItemChart( result.data.rprtTopItemRateList, "rprtTopItemRateChart", "rprtTopItemRateChartCanvas" );
		} else {
			$("#rprtTopItemRateChart").html(strHtml);
		}

		// 5. [성적서] > [월별 TOP10 시험항목별 추이(rprtTopItemRateList수)] 차트 작성
		if ( result.data.rprtTopItemCntList != null && result.data.rprtTopItemCntList.length > 0 ) {
			fn_getMyDataRprtTopItemCntChart( result.data.rprtTopItemCntList, "rprtTopItemCntChart", "rprtTopItemCntChartCanvas" );
		} else {
			$("#rprtTopItemCntChart").html(strHtml);
		}

	}

	/*
	 * 함수명 : fn_getMyDataRprtTopEnt
	 * 설 명 : [성적서] > [TOP10 기업별 성적서 비율(건수)], [TOP10 기업별 성적서 추이(건수)] 차트 작성
	*/
	function fn_getMyDataRprtTopEnt( result ) {

		// 6. [성적서] > [TOP10 기업별 성적서 비율(건수)] 차트 작성
		if ( result.data.rprtTopEntRateList != null && result.data.rprtTopEntRateList.length > 0 ) {
			fn_getMyDataRprtTopEntChart( result.data.rprtTopEntRateList, "rprtTopEntRateChart", "rprtTopEntRateChartCanvas" );
		} else {
			$("#rprtTopEntRateChart").html(strHtml);
		}

		// 7. [성적서] > [TOP10 기업별 성적서 추이(건수)] 차트  작성
		if ( result.data.rprtTopEntCntList != null && result.data.rprtTopEntCntList.length > 0 ) {
			fn_getMyDataRprtTopEntCntChart( result.data.rprtTopEntCntList, "rprtTopEntCntChart", "rprtTopEntCntChartCanvas" );
		} else {
			$("#rprtTopEntCntChart").html(strHtml);
		}

	}

	/*
	 * 함수명 : fn_getMyDataRprtPassRateChart
	 * 설 명 : [성적서] - [판정결과별 성적서 비율(건수)] 차트 작성 // chart type : pie
	*/
	function fn_getMyDataRprtPassRateChart( data, chartArea, canvasArea ) {

		// 1. 차트 영역 설정
		$("#" + chartArea).html("<canvas width='340px' height='400px' style='margin:0 auto' id='" + canvasArea + "'></canvas>");

		// 2. 차트 데이터 초기화
		let labels		= [];
		let datasets	= [];
		let padding		= 10; 	//라벨 정렬

		// 3. 차트 컬러 설정
		let arrColors = [
			CUSTOM_CHART_COLOR_06
			, CUSTOM_CHART_COLOR_04
			, CUSTOM_CHART_COLOR_07
			, CUSTOM_CHART_COLOR_02
			, CUSTOM_CHART_COLOR_05
		];

		// 4. 데이터 설정
		for(let i = 0; i < data.length; i++){
			if( data[i].intgrGnrlzJgmtRsltCd == "10" ) {
				labels.push("적합");
			} else if( data[i].intgrGnrlzJgmtRsltCd == "20" ) {
				labels.push("부적합");
			} else if( data[i].intgrGnrlzJgmtRsltCd == "30" ) {
				labels.push("합격");
			} else if( data[i].intgrGnrlzJgmtRsltCd == "40" ) {
				labels.push("불합격");
			} else {
				labels.push("해당없음");
			}

			datasets.push(data[i].cnt);
		}

		// 5. obj 셋팅
		const obj = {
			"id"			: canvasArea
			, "type" 		: "pie"
			, "datasets"	: datasets
			, "labels" 		: labels
			, "arrColor"	: arrColors
			, "legend" 		: {
				position: 'bottom'
				, align	: 'center'
				, labels: {
					padding: padding
				}
			}
		}

		// 6. 차트 그리기
		fn_chartAction(obj);

	}

	/*
	 * 함수명 : fn_getMyDataRprtPassRateChart
	 * 설 명 : [성적서] > [월별 판정결과별 성적서 추이(건수)] 차트 작성 // chart type : bar
	*/
	function fn_getMyDataRprtMonthPassCntChart( data, chartArea, canvasArea ) {

		// 1. 차트 영역 설정
		$("#" + chartArea).html("<canvas width='500' id='" + canvasArea + "'></canvas>");

		// 2. 바 차트 생성 셋팅
		let labels = [];

		let	rsltYObj = {
				  label : "적합"
				, type	: "bar"
				, backgroundColor: CUSTOM_CHART_COLOR_06
				, data : []
			};

		let rsltNObj = {
				  label : "부적합"
				, type	: "bar"
				, backgroundColor: CUSTOM_CHART_COLOR_04
				, data : []
			};

		let rsltPassObj = {
				  label : "합격"
				, type	: "bar"
				, backgroundColor: CUSTOM_CHART_COLOR_07
				, data : []
			};

		let rsltNoPassObj = {
				  label : "불합격"
				, type	: "bar"
				, backgroundColor: CUSTOM_CHART_COLOR_02
				, data : []
			};

		let rsltEtcObj = {
				  label : "해당없음"
				, type	: "bar"
				, backgroundColor: CUSTOM_CHART_COLOR_05
				, data : []
			};

		let legend = {
				position: 'bottom'
				, align: 'center'
			};

		// 3. 데이터 설정
		for(let i = 0; i < data.length; i++){

			if( data[i].intgrGnrlzJgmtRsltCd == "10" ) {
				labels.push(data[i].dt);
				rsltYObj.data.push(data[i].cnt);
			} else if( data[i].intgrGnrlzJgmtRsltCd == "20" ) {
				rsltNObj.data.push(data[i].cnt);
			} else if( data[i].intgrGnrlzJgmtRsltCd == "30" ) {
				rsltPassObj.data.push(data[i].cnt);
			} else if( data[i].intgrGnrlzJgmtRsltCd == "40" ) {
				rsltNoPassObj.data.push(data[i].cnt);
			} else {
				rsltEtcObj.data.push(data[i].cnt);
			}
		}

		// 4. 차트 그리기
		const ctx = document.getElementById(canvasArea);

		const myChart = new Chart(ctx, {
			type	: 'bar'
			, data	: {
				labels: labels
				, datasets: [
					  rsltYObj
					, rsltNObj
					, rsltPassObj
					, rsltNoPassObj
					, rsltEtcObj
				]
			}
			, options:{
				plugins:{
					legend: legend
					, tooltip: {
						callbacks: {
							title: function(tooltipItem) {
								let title = '';
								return title;
							}
						}
					}
				}
				, scales: {
					y: {
						ticks: {
							display: false
						}
					}
				}
			}
		});

	}

	/*
	 * 함수명 : fn_getMyDataRprtTopPdctgChart
	 * 설 명 : [성적서] > [TOP10 품목별 성적서 비율(건수)] 차트 작성 // chart type : pie
	*/
	function fn_getMyDataRprtTopPdctgChart( data, chartArea, canvasArea ) {

		// 1. 차트 영역 설정
		$("#" + chartArea).html("<canvas width='340px' height='400px' style='margin:0 auto' id='" + canvasArea + "'></canvas>");

		// 2. 차트 데이터 초기화
		let labels		= [];
		let datasets	= [];
		let padding		= 10;

		// 3. 차트 컬러 설정
		let arrColors = [
			CUSTOM_CHART_COLOR_01
			, CUSTOM_CHART_COLOR_02
			, CUSTOM_CHART_COLOR_03
			, CUSTOM_CHART_COLOR_04
			, CUSTOM_CHART_COLOR_05
			, CUSTOM_CHART_COLOR_06
			, CUSTOM_CHART_COLOR_07
			, CUSTOM_CHART_COLOR_08
			, CUSTOM_CHART_COLOR_09
			, CUSTOM_CHART_COLOR_10
		];

		// 4. 데이터 설정
		for(let i = 0; i < data.length; i++){
			labels.push(fn_convertXss(data[i].instPdctgNm));
			datasets.push(data[i].cnt);
		}

		// 5. obj 셋팅
		const obj = {
			"id"			: canvasArea
			, "type" 		: "pie"
			, "datasets"	: datasets
			, "labels" 		: labels
			, "arrColor"	: arrColors
			, "legend" 		: {
				position: 'bottom'
				, labels: {
					padding: padding
				}
			}
		}

		// 6. 차트 그리기
		fn_chartAction(obj);

	}

	/*
	 * 함수명 : fn_getMyDataRprtTopPdctgCntChart
	 * 설 명 : [성적서] > [월별 TOP10 품목별 성적서 추이(건수)] 차트 작성 // chart type : line
	*/
	function fn_getMyDataRprtTopPdctgCntChart( data, chartArea, canvasArea ) {

		// 1. 차트 영역 설정
		$("#" + chartArea).html("<canvas id='" + canvasArea + "'></canvas>");

		// 2. 차트 데이터 초기화
		let arrColors = [
			CUSTOM_CHART_COLOR_01
			, CUSTOM_CHART_COLOR_02
			, CUSTOM_CHART_COLOR_03
			, CUSTOM_CHART_COLOR_04
			, CUSTOM_CHART_COLOR_05
			, CUSTOM_CHART_COLOR_06
			, CUSTOM_CHART_COLOR_07
			, CUSTOM_CHART_COLOR_08
			, CUSTOM_CHART_COLOR_09
			, CUSTOM_CHART_COLOR_10
		];

		// 3. label 셋팅
		let labels 		= [];

		for( let i = 0; i < 12; i++ ) {
			labels.push( data[i].dt );
		}

		// 4. dataset 셋팅
		let dataLabel	= [];
		let dataLabelNm	= [];

		let compInstCd 	= data[0].instPdctgCd;

		dataLabel.push(compInstCd);
		dataLabelNm.push(fn_convertXss(data[0].instPdctgNm));

		for( let j = 0; j < data.length; j++ ) {
			if( data[j].instPdctgCd != compInstCd ) {
				dataLabel.push(data[j].instPdctgCd);
				dataLabelNm.push(fn_convertXss(data[j].instPdctgNm));
			}
			compInstCd = data[j].instPdctgCd;
		}

		let datasets	= [];

		for( let k = 0; k < dataLabel.length; k++ ) {

			let dataObj		= {};
			let arrData 	= [];

			dataObj.label = dataLabelNm[k];

			for( let z = 0; z < data.length; z++ ) {
				if( data[z].instPdctgCd == dataLabel[k] ) {
					arrData.push( data[z].cnt );
				}
			}

			dataObj.data 			= arrData;
			dataObj.backgroundColor = arrColors[k];
			dataObj.borderColor 	= arrColors[k];

			datasets.push(dataObj);
		}

		// 5. 차트 데이터 obj 셋팅
		const ctx = document.getElementById(canvasArea);

		// 6. 차트 그리기
		const myChart = new Chart(ctx, {
			type	: "line", // 차트 형태
			data	: {
				// 차트 셋팅 데이터
				labels: labels, // x축 데이타
				datasets: datasets, // 차트 데이터
			},
			options: {
				responsive: true,
				scales: {
					y: {
						ticks: {
							display: false
						}
					}
				},
				plugins: {
					legend: {
						position: "bottom"
					}
				}
			}
		});

	}

	/*
	 * 함수명 : fn_getMyDataRprtTopItemChart
	 * 설 명 : [성적서] > [TOP10 시험항목별 비율(건수)] 차트 작성 // chart type : pie
	*/
	function fn_getMyDataRprtTopItemChart( data, chartArea, canvasArea ) {

		// 1. 차트 영역 설정
		$("#" + chartArea).html("<canvas width='340px' height='400px' style='margin:0 auto' id='" + canvasArea + "'></canvas>");

		// 2. 차트 데이터 초기화
		let labels		= [];
		let datasets	= [];
		let padding		= 10;

		// 3. 차트 컬러 설정
		let arrColors = [
			CUSTOM_CHART_COLOR_01
			, CUSTOM_CHART_COLOR_02
			, CUSTOM_CHART_COLOR_03
			, CUSTOM_CHART_COLOR_04
			, CUSTOM_CHART_COLOR_05
			, CUSTOM_CHART_COLOR_06
			, CUSTOM_CHART_COLOR_07
			, CUSTOM_CHART_COLOR_08
			, CUSTOM_CHART_COLOR_09
			, CUSTOM_CHART_COLOR_10
		];

		// 4. 데이터 설정
		for(let i = 0; i < data.length; i++){
			labels.push(fn_convertXss(data[i].instTestItemNm));
			datasets.push(data[i].cnt);
		}

		// 5. obj 셋팅
		const obj = {
			"id"			: canvasArea
			, "type" 		: "pie"
			, "datasets"	: datasets
			, "labels" 		: labels
			, "arrColor"	: arrColors
			, "legend" 		: {
				position: 'bottom'
				, align	: 'center'
				, labels: {
					padding: padding
				}
			}
		}

		// 6. 차트 그리기
		fn_chartAction(obj);

	}

	/*
	 * 함수명 : fn_getMyDataRprtTopItemCntChart
	 * 설 명 : [성적서] > [월별 TOP10 시험항목별 추이(건수)] 차트 작성 // chart type : line
	*/
	function fn_getMyDataRprtTopItemCntChart( data, chartArea, canvasArea ) {

		// 1. 차트 영역 설정
		$("#" + chartArea).html("<canvas id='" + canvasArea + "'></canvas>");

		// 2. 차트 데이터 초기화
		let arrColors = [
			CUSTOM_CHART_COLOR_01
			, CUSTOM_CHART_COLOR_02
			, CUSTOM_CHART_COLOR_03
			, CUSTOM_CHART_COLOR_04
			, CUSTOM_CHART_COLOR_05
			, CUSTOM_CHART_COLOR_06
			, CUSTOM_CHART_COLOR_07
			, CUSTOM_CHART_COLOR_08
			, CUSTOM_CHART_COLOR_09
			, CUSTOM_CHART_COLOR_10
		];

		// 3. label 셋팅
		let labels 		= [];

		for( let i = 0; i < 12; i++ ) {
			labels.push( data[i].dt );
		}

		// 4. dataset 셋팅
		let dataLabel	= [];
		let dataLabelNm	= [];

		let compInstCd 	= data[0].instTestItemCd;

		dataLabel.push(compInstCd);
		dataLabelNm.push(fn_convertXss(data[0].instTestItemNm));

		for( let j = 0; j < data.length; j++ ) {
			if( data[j].instTestItemCd != compInstCd ) {
				dataLabel.push(data[j].instTestItemCd);
				dataLabelNm.push(fn_convertXss(data[j].instTestItemNm));
			}
			compInstCd = data[j].instTestItemCd;
		}

		let datasets	= [];

		for( let k = 0; k < dataLabel.length; k++ ) {

			let dataObj		= {};
			let arrData 	= [];

			dataObj.label = dataLabelNm[k];

			for( let z = 0; z < data.length; z++ ) {
				if( data[z].instTestItemCd == dataLabel[k] ) {
					arrData.push( data[z].cnt );
				}
			}

			dataObj.data 			= arrData;
			dataObj.backgroundColor = arrColors[k];
			dataObj.borderColor 	= arrColors[k];

			datasets.push(dataObj);
		}

		// 5. 차트 데이터 obj 셋팅
		const ctx = document.getElementById(canvasArea);

		// 6. 차트 그리기
		const myChart = new Chart(ctx, {
			type	: "line", // 차트 형태
			data	: {
				// 차트 셋팅 데이터
				labels: labels, // x축 데이타
				datasets: datasets, // 차트 데이터
			},
			options: {
				responsive: true,
				scales: {
					y: {
						ticks: {
							display: false
						}
					}
				},
				plugins: {
					legend: {
						position: "bottom"
					}
				}
			}
		});

	}

	/*
	 * 함수명 : fn_getMyDataRprtTopEntChart
	 * 설 명 : [성적서] > [TOP10 기업별 성적서 비율(건수)] 차트 작성 // chart type : pie
	*/
	function fn_getMyDataRprtTopEntChart( data, chartArea, canvasArea ) {

		// 1. 차트 영역 설정
		$("#" + chartArea).html("<canvas width='340px' height='400px' style='margin:0 auto' id='" + canvasArea + "'></canvas>");

		// 2. 차트 데이터 초기화
		let labels		= [];
		let datasets	= [];
		let padding		= 10;

		// 3. 차트 컬러 설정
		let arrColors = [
			CUSTOM_CHART_COLOR_01
			, CUSTOM_CHART_COLOR_04
			, CUSTOM_CHART_COLOR_06
			, CUSTOM_CHART_COLOR_02
			, CUSTOM_CHART_COLOR_03
			, CUSTOM_CHART_COLOR_05
			, CUSTOM_CHART_COLOR_07
			, CUSTOM_CHART_COLOR_08
			, CUSTOM_CHART_COLOR_09
			, CUSTOM_CHART_COLOR_10
		];

		// 4. 툴팁 데이터 셋팅
		let tooldata10 = [];
		let tooldata20 = [];
		let tooldata30 = [];
		let tooldata40 = [];
		let tooldata50 = [];

		// 4. 데이터 설정
		for(let i = 0; i < data.length; i++){
			labels.push(fn_convertXss(data[i].rcptCoNm));
			datasets.push(data[i].cnt);
			tooldata10.push(data[i].cnt10);
			tooldata20.push(data[i].cnt20);
			tooldata30.push(data[i].cnt30);
			tooldata40.push(data[i].cnt40);
			tooldata50.push(data[i].cnt50);
		}

		// 5. 차트 영역
		const ctx = document.getElementById(canvasArea);

		// 6. 차트 그리기
		const myChart = new Chart(ctx, {
			type	: "pie", // 차트 형태
			data	: {
				  labels: labels
				, datasets: [{
					  data: datasets
					, backgroundColor: arrColors
					, borderColor: arrColors
					, borderWidth: 1
				}]
			}, options: {
				responsive: false
				, maintainAspectRatio :false//그래프의 비율 유지
				, plugins:{
					legend 		: {
						position: 'bottom'
						, align	: 'center'
						, labels: {
							padding: padding
						}
					}
					, scales: {
						ticks: {
							display: false
						}
					}
					, tooltip: {
						callbacks: {
							afterLabel: function(tooltipItem, data) {
								let label 	=  '- 적합 건수 : ' 	+ tooldata10[tooltipItem.dataIndex] + '\n';
								label 		+= '- 부적합 건수 : '	+ tooldata20[tooltipItem.dataIndex] + '\n';
								label 		+= '- 합격 건수 : '	+ tooldata30[tooltipItem.dataIndex] + '\n';
								label 		+= '- 불합격 건수 : '	+ tooldata40[tooltipItem.dataIndex] + '\n';
								label 		+= '- 해당없음 : '		+ tooldata50[tooltipItem.dataIndex];
								return label;
							}
						}
					}
				}
				, yAxis: {
					pointOnColumn: false,
					showLabel: false
				}

			}
		});

	}

	/*
	 * 함수명 : fn_getMyDataRprtTopEntCntChart
	 * 설 명 : [성적서] > [월별 TOP10기업 성적서 부적합 추이(건수)] 차트 작성 // chart type : line
	*/
	function fn_getMyDataRprtTopEntCntChart( data, chartArea, canvasArea ) {

		// 1. 차트 영역 설정
		$("#" + chartArea).html("<canvas id='" + canvasArea + "'></canvas>");

		// 2. 차트 데이터 초기화
		let arrColors = [
			CUSTOM_CHART_COLOR_01
			, CUSTOM_CHART_COLOR_04
			, CUSTOM_CHART_COLOR_06
			, CUSTOM_CHART_COLOR_02
			, CUSTOM_CHART_COLOR_03
			, CUSTOM_CHART_COLOR_05
			, CUSTOM_CHART_COLOR_07
			, CUSTOM_CHART_COLOR_08
			, CUSTOM_CHART_COLOR_09
			, CUSTOM_CHART_COLOR_10
		];

		// 3. label 셋팅
		let labels 		= [];

		for( let i = 0; i < 12; i++ ) {
			labels.push( data[i].dt );
		}

		// 4. dataset 셋팅
		let dataLabel	= [];
		let dataLabelNm	= [];

		let compCd 	= data[0].rcptCoBrno;

		dataLabel.push(compCd);
		dataLabelNm.push(fn_convertXss(data[0].rcptCoNm));

		for( let j = 0; j < data.length; j++ ) {
			if( data[j].rcptCoBrno != compCd ) {
				dataLabel.push(data[j].rcptCoBrno);
				dataLabelNm.push(fn_convertXss(data[j].rcptCoNm));
			}
			compCd = data[j].rcptCoBrno;
		}

		let datasets	= [];

		for( let k = 0; k < dataLabel.length; k++ ) {

			let dataObj		= {};
			let arrData 	= [];

			// 5. 툴팁 데이터 셋팅
			let tooldata10 = [];
			let tooldata20 = [];
			let tooldata30 = [];
			let tooldata40 = [];
			let tooldata50 = [];

			dataObj.label = dataLabelNm[k];

			for( let z = 0; z < data.length; z++ ) {
				if( data[z].rcptCoBrno == dataLabel[k] ) {
					arrData.push( data[z].cnt );

					tooldata10.push(data[z].cnt10);
					tooldata20.push(data[z].cnt20);
					tooldata30.push(data[z].cnt30);
					tooldata40.push(data[z].cnt40);
					tooldata50.push(data[z].cnt50);
				}
			}

			dataObj.data 			= arrData;
			dataObj.borderColor 	= arrColors[k];
			dataObj.backgroundColor = arrColors[k];

			dataObj.tooldata10		= tooldata10;
			dataObj.tooldata20		= tooldata20;
			dataObj.tooldata30		= tooldata30;
			dataObj.tooldata40		= tooldata40;
			dataObj.tooldata50		= tooldata50;

			datasets.push(dataObj);

		}

		// 5. 차트 데이터 obj 셋팅
		const ctx = document.getElementById(canvasArea);

		// 6. 차트 그리기
		const myChart = new Chart(ctx, {
			type	: "line", // 차트 형태
			data	: {
				// 차트 셋팅 데이터
				labels: labels, // x축 데이타
				datasets: datasets, // 차트 데이터
			},
			options: {
				responsive: true,
				scales: {
					y: {
						ticks: {
							display: false
						}
					}
				},
				plugins: {
					legend: {
						position: "bottom"
					}
					, tooltip: {
						callbacks: {
							afterLabel: function(tooltipItem, data) {

								let label 	=  '- 적합 건수 : ' 	+ datasets[tooltipItem.datasetIndex].tooldata10[tooltipItem.dataIndex] + '\n';
								label 		+= '- 부적합 건수 : '	+ datasets[tooltipItem.datasetIndex].tooldata20[tooltipItem.dataIndex] + '\n';
								label 		+= '- 합격 건수 : '	+ datasets[tooltipItem.datasetIndex].tooldata30[tooltipItem.dataIndex] + '\n';
								label 		+= '- 불합격 건수 : '	+ datasets[tooltipItem.datasetIndex].tooldata40[tooltipItem.dataIndex] + '\n';
								label 		+= '- 해당없음 : '		+ datasets[tooltipItem.datasetIndex].tooldata50[tooltipItem.dataIndex];
								return label;
							}
						}
					}
				}
			}
		});

	}

	/*
	 * 함수명 : fn_chartAction()
	 * 설   명 : 차트 그리기
	*/
	function fn_chartAction (obj) {

		//0. 차트 데이터 가져오기
		const id 		= obj.id;
		const type 		= obj.type;
		const label		= obj.label;
		const labels 	= obj.labels;
		const chartDatas = obj.datasets;
		const colors 	= obj.arrColor;
		const padding 	= obj.padding;
		const indexAxis = obj.indexAxis;
		const legend 	= obj.legend;
		const tooldata	= obj.tooldata;

		//1. 차트 영역
		const ctx = document.getElementById(id);

		//2. 차트생성
		const myChart = new Chart(ctx, {
			type: type
			, data: {
				labels: labels
				, datasets: [{
						label: label
						, data: chartDatas
						, backgroundColor: colors
						, borderColor: colors
						, borderWidth: 1
				}]
			}//end of data
			, options: {
				responsive: false
				, maintainAspectRatio :false//그래프의 비율 유지
				, plugins:{
						legend: legend
						, scales: {
								ticks: {
									display: false
								}
						}
				}//end of plugins
				, indexAxis : indexAxis
				, yAxis: {
					pointOnColumn: false,
					showLabel: false
				}

			}//end of options
		});//end of myChart

	}

</script>

	<!-- ===== header ====== -->
	<header id="header">
		<div id="sub-mv" class="sub-myservice">
			<div class="inner">
				<!--<h2>내손안의 시험인증</h2> 박태우 수정(2023.01.03)-->
				<h2>플랫폼 서비스</h2>
				<!--<p>시험인증 빅데이터<br class="mo-block"> 내 손안의 시험인증 서비스입니다.</p> 박태우 수정(2023.01.03)-->
				<div class="sub-obj">오브젝트</div>
			</div>
		</div>
	</header>
	<!-- ===== /header ====== -->

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
			<li class=""><a href="/platformSvc/myData/myDataStat/myDataStatInfo">통계</a></li>
			<li class=""><a href="/platformSvc/myData/myDataStat/myDataCertInfo">인증</a></li>
			<li class="on"><a href="/platformSvc/myData/myDataStat/myDataRprtInfo">성적서</a></li>
			<!--li class=""><a href="UI-FRONT-0304.html">시험항목</a></li-->
		</ul>

		<%-- 조회 form --%>
		<form id="frm_rprtInfo" name="frm_rprtInfo" method="get">
			<div class="slt-list-wr">
				<label for="searchType01">
					<input type="radio" id="searchType01" name="searchType" value=""		${empty myDataStatSelectReq.searchType	? "checked" : "" }/>
					<span>전체</span>
				</label>
				<label for="searchType02">
					<input type="radio" id="searchType02" name="searchType" value="APPLY"	${myDataStatSelectReq.searchType eq "APPLY"	? "checked" : "" }/>
					<span>신청</span>
				</label>
				<label for="searchType03">
					<input type="radio" id="searchType03" name="searchType" value="SUBMIT"	${myDataStatSelectReq.searchType eq "SUBMIT" ? "checked" : "" }/>
					<span>제출</span>
				</label>

				<select class="select select-mysvc" id="instId" name="instId">
					<option value="">전체</option>
					<c:forEach var="instItem" items="${instList}">
						 <option value="${instItem.instId}" ${myDataStatSelectReq.instId eq instItem.instId ? "checked" : "" }>${instItem.instEngNm}</option>
					</c:forEach>
				</select>
				<button type="button" class="btn sch" onclick="fn_getMyDataRprtInfoDataInit();">조회</button>
			</div>
		</form>

		<!-- <h4 class="stit">${myDataStatSelectRes.year}년 성적서 현황</h4> 박태우 주석(2023.01.03)-->
		<h4 class="stit" style="display:inline">성적서 기본 현황</h4><h3 style="display:inline">(건수 기준)</h3><br></br>
		<ul class="chart-list grid2">
			<li>
				<!-- <div class="hd"><h5 class="stit">판정결과별 성적서 비율(건수)</h5></div> 박태우 주석(2023.01.03)-->
				<div class="hd"><h5 class="stit">${myDataStatSelectRes.year}년 성적서 판정결과</h5></div>
				<!--챠트 들어가는 부분-->
				<!--가로 205px-->
				<div class="bd" id="rprtPassRateChart">
					<img src="/asset/images/common/ajax-loader.gif" />
				</div>
			</li>
			<li>
				<!--<div class="hd"><h5 class="stit">월별 판정결과별 성적서 추이(건수)</h5></div> 박태우 주석(2023.01.03)-->
				<div class="hd"><h5 class="stit">최근 1년 판정결과별 성적서 추이</h5></div>
				<!--챠트 들어가는 부분-->
				<div class="bd chart-rect" id="rprtMonthPassCntChart">
					<img src="/asset/images/common/ajax-loader.gif" />
				</div>
			</li>
		</ul>

		<!--<h4 class="stit">${myDataStatSelectRes.year}년 품목별 성적서 현황</h4> 박태우 주석(2023.01.03)-->
		<h4 class="stit">품목별 성적서 현황</h4>
		<ul class="chart-list grid2">
			<li>
				<!--<div class="hd"><h5 class="stit">TOP10 품목별 성적서 비율(건수)</h5></div> 박태우 주석(2023.01.03)-->
				<div class="hd"><h5 class="stit">${myDataStatSelectRes.year}년 TOP10 품목</h5></div>
				<!--챠트 들어가는 부분-->
				<!--가로 205px-->
				<div class="bd" id="rprtTopPdctgRateChart">
					<img src="/asset/images/common/ajax-loader.gif" />
				</div>
			</li>
			<li>
				<div class="hd"><h5 class="stit">최근 1년 TOP10 품목별 성적서 추이</h5></div>
				<!--챠트 들어가는 부분-->
				<div class="bd chart-rect" id="rprtTopPdctgCntChart">
					<img src="/asset/images/common/ajax-loader.gif" />
				</div>
			</li>
		</ul>

		<!--<h4 class="stit">${myDataStatSelectRes.year}년 시험항목 현황</h4> 박태우 주석(2023.01.03)-->
		<h4 class="stit">시험항목별 성적서 현황</h4>
		<ul class="chart-list grid2">
			<li>
				<div class="hd"><h5 class="stit">${myDataStatSelectRes.year}년 TOP10 시험항목</h5></div>
				<!--챠트 들어가는 부분-->
				<!--가로 205px-->
				<div class="bd" id="rprtTopItemRateChart">
					<img src="/asset/images/common/ajax-loader.gif" />
				</div>
			</li>
			<li>
				<!--<div class="hd"><h5 class="stit">최근 1년 TOP10 시험항목 추이(건수)</h5></div> 박태우 주석(2023.01.03)-->
				<div class="hd"><h5 class="stit">최근 1년 TOP10 시험항목별 성적서 추이</h5></div>
				<!--챠트 들어가는 부분-->
				<div class="bd chart-rect" id="rprtTopItemCntChart">
					<img src="/asset/images/common/ajax-loader.gif" />
				</div>
			</li>
		</ul>

		<!--<h4 class="stit" id="entRprtInfoHead" style="margin-bottom:var(--w-10);">${myDataStatSelectRes.year}년 기업별 성적서 현황</h4> 박태우 주석(2023.01.03)-->
		<h4 class="stit" id="entRprtInfoHead" style="margin-bottom:var(--w-10);">기업별 성적서 현황</h4>
		<span id="entRprtInfoTip" style="color:#4e637e; font-size:var(--font-size-14);">※ 마우스 오버 시 툴팁에서 데이터를 확인하실 수 있습니다.</span><br/><br/>
		<ul class="chart-list grid2" id="entRprtInfoBody">
			<li>
				<div class="hd"><h5 class="stit">${myDataStatSelectRes.year}년 TOP10 기업</h5></div>
				<!--챠트 들어가는 부분-->
				<!--가로 205px-->
				<div class="bd" id="rprtTopEntRateChart">
					<img src="/asset/images/common/ajax-loader.gif" />
				</div>
			</li>
			<li>
				<div class="hd"><h5 class="stit">최근 1년 TOP10 기업별 성적서 추이</h5></div>
				<!--챠트 들어가는 부분-->
				<div class="bd chart-rect" id="rprtTopEntCntChart">
					<img src="/asset/images/common/ajax-loader.gif" />
				</div>
			</li>
		</ul>

		<div class="btn-wr">
			<a href="/platformSvc/myData/myInfoCert/myCertList" class="btn">나의 시험인증 정보조회 바로가기</a>
		</div>

		</div>
	</div>
	<!-- ===== /container ====== -->

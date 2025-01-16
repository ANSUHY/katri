package com.katri.web.platformSvc.myData.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.SessionUtil;
import com.katri.web.platformSvc.myData.mapper.MyDataStatMapper;
import com.katri.web.platformSvc.myData.model.MyDataStatSelectReq;
import com.katri.web.platformSvc.myData.model.MyDataStatSelectRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class MyDataStatService {

	/* MyDataStatMapper */
	private final MyDataStatMapper myDataStatMapper;

	/*****************************************************
	 * [나의 시험인증 현황] -> 기관 목록 조회
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public List<MyDataStatSelectRes> getInstList() {
		//[[0]]. 반환할 정보
		List<MyDataStatSelectRes> instList = null;

		//[[1]]. 조회
		instList	= myDataStatMapper.selectInstList();

		return instList;
	}

	/*****************************************************
	 * [나의 시험인증 현황] -> 날짜 정보(년/월) 조회
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public MyDataStatSelectRes getDateData() {
		//[[0]].반환할 정보
		MyDataStatSelectRes myDataStatSelectRes = new MyDataStatSelectRes();

		//[[1]]. 조회
		myDataStatSelectRes 					= myDataStatMapper.selectDateData();

		return myDataStatSelectRes;
	}

	/*****************************************************
	 * [나의 시험인증 현황] -> 사업자 등록 번호 조회
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public String getMyDataStatBrno(Integer entGrpSn) throws CustomMessageException {
		//[[0]]. 반환할 정보
		String brno	= "";

		//[[1]]. 조회
		brno		= myDataStatMapper.selectMyDataStatBrno(entGrpSn);

		if(("").equals(brno)){
			throw new CustomMessageException("result-message.messages.common.message.error");
		}

		return brno;
	}


	/*****************************************************
	 * [나의 시험인증 현황] -> [통계] -> 년도 기준 의뢰 현황 -> 인증 데이터 조회
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public List<MyDataStatSelectRes> getChartJsStatDataByYear(MyDataStatSelectReq myDataStatSelectReq) throws Exception {

		myDataStatSelectReq.setBrno(SessionUtil.getLoginBrno());	//사업자번호
		myDataStatSelectReq.setEntGrpMngNo(getDefaultEntGrpMngNo(SessionUtil.getLoginEntGrpMngNo()));	//기업그룹관리번호

		//[[0]]. 반환할 정보
		List<MyDataStatSelectRes> statDataByYearList 	= new ArrayList<MyDataStatSelectRes>();

		//[[1]]. 조회
		//인증
		statDataByYearList										= myDataStatMapper.selectStatCertDataByYear1(myDataStatSelectReq);

		return statDataByYearList;
	}

	/*****************************************************
	 * [나의 시험인증 현황] -> [통계] -> 년도 기준 의뢰 현황 -> 성적서 데이터 조회
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public List<MyDataStatSelectRes> getChartJsStatRprtDataByYear(MyDataStatSelectReq myDataStatSelectReq) throws Exception {


		myDataStatSelectReq.setBrno(SessionUtil.getLoginBrno());	//사업자번호
		myDataStatSelectReq.setEntGrpMngNo(getDefaultEntGrpMngNo(SessionUtil.getLoginEntGrpMngNo()));	//기업그룹관리번호

		//[[0]]. 반환할 정보
		List<MyDataStatSelectRes> statRprtDataByYearList	= null;

		//[[1]]. 조회
		statRprtDataByYearList								= myDataStatMapper.selectStatRprtDataByYear(myDataStatSelectReq);

		return statRprtDataByYearList;
	}

	/*****************************************************
	 * [나의 시험인증 현황] -> [통계] -> 년도별 -> 시험항목 데이터 조회
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public List<MyDataStatSelectRes> getChartJsStatTestDataByYear(MyDataStatSelectReq myDataStatSelectReq) throws Exception {

		myDataStatSelectReq.setBrno(SessionUtil.getLoginBrno());	//사업자번호
		myDataStatSelectReq.setEntGrpMngNo(getDefaultEntGrpMngNo(SessionUtil.getLoginEntGrpMngNo()));	//기업그룹관리번호

		//[[0]]. 반환할 정보
		List<MyDataStatSelectRes> statTestDataByYearList	= null;

		//[[1]]. 조회
		statTestDataByYearList								= myDataStatMapper.selectStatTestDataByYear(myDataStatSelectReq);

		return statTestDataByYearList;
	}

	/*****************************************************
	 * [나의 시험인증 현황] -> [통계] -> 월별 -> 인증 데이터 조회
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public List<MyDataStatSelectRes> getChartJsStatCertDataByMonth(MyDataStatSelectReq myDataStatSelectReq) throws Exception {

		myDataStatSelectReq.setBrno(SessionUtil.getLoginBrno());	//사업자번호
		myDataStatSelectReq.setEntGrpMngNo(getDefaultEntGrpMngNo(SessionUtil.getLoginEntGrpMngNo()));	//기업그룹관리번호

		//[[0]]. 반환할 정보
		List<MyDataStatSelectRes> statCertDataByMonthList	= null;

		//[[1]]. 조회
		statCertDataByMonthList								= myDataStatMapper.selectStatCertDataByMonth(myDataStatSelectReq);

		return statCertDataByMonthList;
	}


	/*****************************************************
	 * [나의 시험인증 현황] -> [통계] -> 월별 -> 성적서 데이터 조회
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public List<MyDataStatSelectRes> getChartJsStatrprtDataByMonth(MyDataStatSelectReq myDataStatSelectReq) throws Exception {

		myDataStatSelectReq.setBrno(SessionUtil.getLoginBrno());	//사업자번호
		myDataStatSelectReq.setEntGrpMngNo(getDefaultEntGrpMngNo(SessionUtil.getLoginEntGrpMngNo()));	//기업그룹관리번호

		//[[0]]. 반환할 정보
		List<MyDataStatSelectRes> statRprtDataByMonthList	= null;

		//[[1]]. 조회
		statRprtDataByMonthList								= myDataStatMapper.selectStatRprtDataByMonth(myDataStatSelectReq);

		return statRprtDataByMonthList;
	}

	/*****************************************************
	 * [나의 시험인증 현황] -> [통계] -> 월별 -> 시험항목 데이터 조회
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public List<MyDataStatSelectRes> getChartJsTestDataByMonth(MyDataStatSelectReq myDataStatSelectReq) throws Exception {

		myDataStatSelectReq.setBrno(SessionUtil.getLoginBrno());	//사업자번호
		myDataStatSelectReq.setEntGrpMngNo(getDefaultEntGrpMngNo(SessionUtil.getLoginEntGrpMngNo()));	//기업그룹관리번호

		//[[0]]. 반환할 정보
		List<MyDataStatSelectRes> statTestDataByMonthList	= null;

		//[[1]]. 조회
		statTestDataByMonthList								= myDataStatMapper.selectStatTestDataByMonth(myDataStatSelectReq);

		return statTestDataByMonthList;
	}

	/*****************************************************
	 * [나의 시험인증 현황] -> [통계] -> 월별 내 의뢰 추이
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public MyDataStatSelectRes getChartJsStatReqDataByMonth(MyDataStatSelectReq myDataStatSelectReq) throws Exception {

		myDataStatSelectReq.setBrno(SessionUtil.getLoginBrno());	//사업자번호
		myDataStatSelectReq.setEntGrpMngNo(getDefaultEntGrpMngNo(SessionUtil.getLoginEntGrpMngNo()));	//기업그룹관리번호

		//[[0]]. 반환할 정보
		MyDataStatSelectRes myDataStatSelectRes	= new MyDataStatSelectRes();
		List<MyDataStatSelectRes> certDataList  = null;
		List<MyDataStatSelectRes> rprtDataList = null;
		List<MyDataStatSelectRes> testDataList = null;

		//[[1]]. 조회
		certDataList							= myDataStatMapper.selectStatReqDataByMonth(myDataStatSelectReq);
		rprtDataList							= myDataStatMapper.selectCertRateData2(myDataStatSelectReq);
		testDataList							= myDataStatMapper.selectCertRateData3(myDataStatSelectReq);

		myDataStatSelectRes.setCertDataList(certDataList); //인증
		myDataStatSelectRes.setRprtDataList(rprtDataList); //성적서
		myDataStatSelectRes.setTestDataList(testDataList); //시험항목

		return myDataStatSelectRes;

	}

	/*======================================================== [인증] ========================================================================*/

	/*****************************************************
	 * [나의 시험인증 현황] -> [인증] -> [~년 인증 전체 현황] -> [전체 인증 건수]
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public MyDataStatSelectRes getChartJsCertCountData(MyDataStatSelectReq myDataStatSelectReq) throws Exception {

		myDataStatSelectReq.setBrno(SessionUtil.getLoginBrno());	//사업자번호
		myDataStatSelectReq.setEntGrpMngNo(getDefaultEntGrpMngNo(SessionUtil.getLoginEntGrpMngNo()));	//기업그룹관리번호

		//[[0]]. 반환할 정보
		MyDataStatSelectRes jsCertCountData	= new MyDataStatSelectRes();

		//[[1]]. 조회
		jsCertCountData						= myDataStatMapper.selectCertCountData(myDataStatSelectReq);

		return jsCertCountData;
	}

	/*****************************************************
	 * [나의 시험인증 현황] -> [인증] -> ~년 인증 현황 -> 법정기준 인증 비율 (인증 데이터)
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public List<MyDataStatSelectRes> getChartJsCertRateData(MyDataStatSelectReq myDataStatSelectReq) throws Exception {

		myDataStatSelectReq.setBrno(SessionUtil.getLoginBrno());	//사업자번호
		myDataStatSelectReq.setEntGrpMngNo(getDefaultEntGrpMngNo(SessionUtil.getLoginEntGrpMngNo()));	//기업그룹관리번호

		//[[0]]. 반환할 정보
		MyDataStatSelectRes myDataStatSelectRes		= new MyDataStatSelectRes();
		List<MyDataStatSelectRes> statCertRateList	= null;

		//[[1]]. 조회
		statCertRateList							= myDataStatMapper.selectCertRateDataByYear(myDataStatSelectReq);

		return statCertRateList;
	}

	/*****************************************************
	 * [나의 시험인증 현황] -> [인증] -> ~년 인증 전체 현황 -> 월별 인증 추이(건수)
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public List<MyDataStatSelectRes> getChartJsCertCountByMonthData(MyDataStatSelectReq myDataStatSelectReq) throws Exception {

		myDataStatSelectReq.setBrno(SessionUtil.getLoginBrno());	//사업자번호
		myDataStatSelectReq.setEntGrpMngNo(getDefaultEntGrpMngNo(SessionUtil.getLoginEntGrpMngNo()));	//기업그룹관리번호

		//[[0]]. 반환할 정보
		List<MyDataStatSelectRes> statCertCountbyMonthList	= null;

		//[[1]]. 조회
		statCertCountbyMonthList							= myDataStatMapper.selectStatCertCountByMonthData(myDataStatSelectReq);

		return statCertCountbyMonthList;

	}



	/*****************************************************
	 * [나의 시험인증 현황] -> [인증] -> ~년 인증 전체 현황 -> 월별 법정기준 인증 추이(건수)
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public MyDataStatSelectRes getChartJsCertProgressCount(MyDataStatSelectReq myDataStatSelectReq) throws Exception {

		myDataStatSelectReq.setBrno(SessionUtil.getLoginBrno());	//사업자번호
		myDataStatSelectReq.setEntGrpMngNo(getDefaultEntGrpMngNo(SessionUtil.getLoginEntGrpMngNo()));	//기업그룹관리번호

		//[[0]]. 반환할 정보
		MyDataStatSelectRes myDataStatSelectRes		= new MyDataStatSelectRes();
		List<MyDataStatSelectRes> progressList		= null;
		List<MyDataStatSelectRes> safetyConfirmList = new ArrayList<MyDataStatSelectRes>(); //안전확인 어린이
		List<MyDataStatSelectRes> safetyCertList	= new ArrayList<MyDataStatSelectRes>(); //안전인증 어린이
		List<MyDataStatSelectRes> safetyConfirmList2 = new ArrayList<MyDataStatSelectRes>(); //안전확인 전안
		List<MyDataStatSelectRes> safetyCertList2	= new ArrayList<MyDataStatSelectRes>(); //안전인증 전안

		//[[1]]. 조회
		progressList							= myDataStatMapper.selectStatProgressCount(myDataStatSelectReq);

		//[[1-1]]. 리스트 나누기
		for(int i = 0; i < progressList.size(); i++) {
			if(progressList.get(i).getCertDivCd().contains("A")) {
				progressList.get(i).setPdctgNm("안전 확인(어린이법)");
				safetyConfirmList.add(progressList.get(i));
			} else if(progressList.get(i).getCertDivCd().contains("B")) {
				progressList.get(i).setPdctgNm("안전 인증(어린이법)");
				safetyCertList.add(progressList.get(i));
			} else if(progressList.get(i).getCertDivCd().contains("C")) {
				progressList.get(i).setPdctgNm("안전 확인(전안법)");
				safetyConfirmList2.add(progressList.get(i));
			} else if(progressList.get(i).getCertDivCd().contains("D")) {
				progressList.get(i).setPdctgNm("안전 인증(전안법)");
				safetyCertList2.add(progressList.get(i));
			}
		}

		myDataStatSelectRes.setSafetyConfirmList(safetyConfirmList);
		myDataStatSelectRes.setSafetyCertList(safetyCertList);
		myDataStatSelectRes.setSafetyConfirmList2(safetyConfirmList2);
		myDataStatSelectRes.setSafetyCertList2(safetyCertList2);


		return myDataStatSelectRes;
	}

	/*****************************************************
	 * [나의 시험인증 현황] -> [인증] -> [~년 품목별 인증 현황] -> [품목별 인증 비율]
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public List<MyDataStatSelectRes> getChartJsCertRateByItem(MyDataStatSelectReq myDataStatSelectReq) throws Exception {

		myDataStatSelectReq.setBrno(SessionUtil.getLoginBrno());	//사업자번호
		myDataStatSelectReq.setEntGrpMngNo(getDefaultEntGrpMngNo(SessionUtil.getLoginEntGrpMngNo()));	//기업그룹관리번호

		//[[0]]. 반환할 정보
		List<MyDataStatSelectRes> chartJsRateByItemList	= null;

		//[[1]]. 조회
		chartJsRateByItemList							= myDataStatMapper.selectCertRateByItem(myDataStatSelectReq);

		return chartJsRateByItemList;
	}


	/*****************************************************
	 * [나의 시험인증 현황] -> [인증] -> [~년 품목별 인증 현황] -> [TOP3 제품 인증 추이]
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public List<MyDataStatSelectRes> getChartJsCertRateByItemTop3(MyDataStatSelectReq myDataStatSelectReq) throws Exception {

		myDataStatSelectReq.setBrno(SessionUtil.getLoginBrno());	//사업자번호
		myDataStatSelectReq.setEntGrpMngNo(getDefaultEntGrpMngNo(SessionUtil.getLoginEntGrpMngNo()));	//기업그룹관리번호

		//[[0]]. 반환할 정보
		List<MyDataStatSelectRes> chartJsRateByItemList	= null;

		//[[1]]. 조회
		chartJsRateByItemList							= myDataStatMapper.selectCertRateByItemTop3(myDataStatSelectReq);

		return chartJsRateByItemList;
	}



	/*****************************************************
	 * [나의 시험인증 현황] -> [인증] -> [~년 품목별 인증 현황] -> [월별 TOP3 품목별 인증 추이(건수) ]
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public MyDataStatSelectRes getChartJsTop3ProgressByItem(MyDataStatSelectReq myDataStatSelectReq) throws Exception {

		myDataStatSelectReq.setBrno(SessionUtil.getLoginBrno());	//사업자번호
		myDataStatSelectReq.setEntGrpMngNo(getDefaultEntGrpMngNo(SessionUtil.getLoginEntGrpMngNo()));	//기업그룹관리번호

		//[[0]]. 반환할 정보
		List<MyDataStatSelectRes> chartJsTop3ByItemList	= null;
		List<MyDataStatSelectRes> chartJsTop3ByItemfirstList = new ArrayList<MyDataStatSelectRes>();
		List<MyDataStatSelectRes> chartJsTop3ByItemSecondList = new ArrayList<MyDataStatSelectRes>();
		List<MyDataStatSelectRes> chartJsTop3ByItemThirdList = new ArrayList<MyDataStatSelectRes>();
		MyDataStatSelectRes myDataStatSelectRes				= new MyDataStatSelectRes();

		//[[1]]. 조회
		chartJsTop3ByItemList 							= myDataStatMapper.selectChartJsTop3ProgressByItem(myDataStatSelectReq);

		/* List 나누기 */
		String strPdctgCd = "";
		int indexPdctgCd = 0;

		for(int i = 0; i < chartJsTop3ByItemList.size(); i++) {
			if(i == 0) {
				chartJsTop3ByItemfirstList.add(chartJsTop3ByItemList.get(i));
			} else {
				if(strPdctgCd.equals(chartJsTop3ByItemList.get(i).getPdctgCd())) {
					if(indexPdctgCd == 0)
						chartJsTop3ByItemfirstList.add(chartJsTop3ByItemList.get(i));
					else if(indexPdctgCd == 1)
						chartJsTop3ByItemSecondList.add(chartJsTop3ByItemList.get(i));
					else if(indexPdctgCd == 2)
						chartJsTop3ByItemThirdList.add(chartJsTop3ByItemList.get(i));
				} else {
					indexPdctgCd++;
					if(indexPdctgCd == 1)
						chartJsTop3ByItemSecondList.add(chartJsTop3ByItemList.get(i));
					else if(indexPdctgCd == 2)
						chartJsTop3ByItemThirdList.add(chartJsTop3ByItemList.get(i));
				}
			}

			strPdctgCd = chartJsTop3ByItemList.get(i).getPdctgCd();
		}

		myDataStatSelectRes.setChartJsTop3ByItemfirstList(chartJsTop3ByItemfirstList);
		myDataStatSelectRes.setChartJsTop3ByItemSecondList(chartJsTop3ByItemSecondList);
		myDataStatSelectRes.setChartJsTop3ByItemThirdList(chartJsTop3ByItemThirdList);

		return myDataStatSelectRes;
	}



	/*===================================================================================================================================*/











	/* ------------------------------------------- 성적서 관련 ------------------------------------------- */
	/* ------------------------------------------- 성적서 관련 ------------------------------------------- */
	/* ------------------------------------------- 성적서 관련 ------------------------------------------- */
	/* ------------------------------------------- 성적서 관련 ------------------------------------------- */
	/* ------------------------------------------- 성적서 관련 ------------------------------------------- */
	/* ------------------------------------------- 성적서 관련 ------------------------------------------- */

	/*****************************************************
	 * [나의 시험인증 현황] - [성적서] -> [판정결과별 성적서 비율(건수)] 차트 데이터 조회
	 * @param myDataStatSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public MyDataStatSelectRes getMyDataRprtPassRateData( MyDataStatSelectReq myDataStatSelectReq ) throws Exception {

		MyDataStatSelectRes myDataStatSelectRes = new MyDataStatSelectRes();

		myDataStatSelectReq.setBrno(SessionUtil.getLoginBrno());	//사업자번호
		myDataStatSelectReq.setEntGrpMngNo(getDefaultEntGrpMngNo(SessionUtil.getLoginEntGrpMngNo()));	//기업그룹관리번호

		// [0]. [성적서] > [판정결과별 성적서 비율(건수)] 데이터 조회
		List<MyDataStatSelectRes> rprtPassRateList		= myDataStatMapper.selectMyDataRprtPassRate(myDataStatSelectReq);
		if ( rprtPassRateList.size() > 0 ) {
			myDataStatSelectRes.setRprtPassRateList(rprtPassRateList);
		}

		return myDataStatSelectRes;

	}

	/*****************************************************
	 * [나의 시험인증 현황] -[성적서] > [월별 판정결과별 성적서 추이(건수)] 차트 데이터 조회
	 * @param myDataStatSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public MyDataStatSelectRes getMyDataRprtMonthPassCnt( MyDataStatSelectReq myDataStatSelectReq ) throws Exception {

		MyDataStatSelectRes myDataStatSelectRes = new MyDataStatSelectRes();

		myDataStatSelectReq.setBrno(SessionUtil.getLoginBrno());	//사업자번호
		myDataStatSelectReq.setEntGrpMngNo(getDefaultEntGrpMngNo(SessionUtil.getLoginEntGrpMngNo()));	//기업그룹관리번호

		// [1]. [성적서] > [월별 판정결과별 성적서 추이(건수)] 데이터 조회
		List<MyDataStatSelectRes> rprtMonthPassCntList	= myDataStatMapper.selectMyDataRprtMonthPassCnt(myDataStatSelectReq);
		if ( rprtMonthPassCntList.size() > 0 ) {
			myDataStatSelectRes.setRprtMonthPassCntList(rprtMonthPassCntList);
		}

		return myDataStatSelectRes;

	}

	/*****************************************************
	 * [나의 시험인증 현황] - [성적서] > [TOP10 품목별 성적서 비율(건수)] 차트 데이터 조회 + [월별 TOP10 품목별 성적서 추이(건수)] 차트 데이터 조회
	 * @param myDataStatSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public MyDataStatSelectRes getMyDataRprtTopPdctg( MyDataStatSelectReq myDataStatSelectReq ) throws Exception {

		MyDataStatSelectRes myDataStatSelectRes = new MyDataStatSelectRes();

		myDataStatSelectReq.setBrno(SessionUtil.getLoginBrno());	//사업자번호
		myDataStatSelectReq.setEntGrpMngNo(getDefaultEntGrpMngNo(SessionUtil.getLoginEntGrpMngNo()));	//기업그룹관리번호

		// [2]. [성적서] > [TOP10 품목별 성적서 비율(건수)] 데이터 조회
		myDataStatSelectReq.setQueryType("");
		List<MyDataStatSelectRes> tmpRprtTopPdctgRateList	= myDataStatMapper.selectMyDataRprtTopPdctgRate(myDataStatSelectReq);

		if ( tmpRprtTopPdctgRateList.size() > 0 ) {

			myDataStatSelectReq.setQueryType("RATE");
			List<MyDataStatSelectRes> rprtTopPdctgRateList	= myDataStatMapper.selectMyDataRprtTopPdctgRate(myDataStatSelectReq);
			myDataStatSelectRes.setRprtTopPdctgRateList(rprtTopPdctgRateList);

			// [3]. [성적서] > [월별 TOP10 품목별 성적서 추이(건수)] 데이터 조회

			/* [3]-1. 조회하기 위해 [월별 TOP10 품목별 성적서 정보]_접수회사정보들 셋팅 */
			List<MyDataStatSelectReq> rankRprtPdctgInfoList = new ArrayList<MyDataStatSelectReq>(); //(조회시 필요)[월별 TOP10품목별 성적서 정보]_품목 정보들
			Integer index = 0;
			for( MyDataStatSelectRes rprtTopPdctg : tmpRprtTopPdctgRateList ) {

				MyDataStatSelectReq rankRprtPdctgInfo = new MyDataStatSelectReq();
				rankRprtPdctgInfo.setRankInstPdctgCd(rprtTopPdctg.getInstPdctgCd()); 	//[월별 TOP10품목별 성적서 정보]_기관품목코드
				rankRprtPdctgInfo.setRankInstPdctgNm(rprtTopPdctg.getInstPdctgNm()); 	//[월별 TOP10품목별 성적서 정보]_기관품목코명
				rankRprtPdctgInfo.setRankNo(index); 									//[월별 TOP10]_순위

				index ++;
				rankRprtPdctgInfoList.add(rankRprtPdctgInfo);
			}
			myDataStatSelectReq.setRankRprtPdctgInfoList(rankRprtPdctgInfoList);
			myDataStatSelectReq.setQueryType("MONTHCNT");

			/* [3]-2. 조회*/
			List<MyDataStatSelectRes> rprtTopPdctgCntList	= myDataStatMapper.selectMyDataRprtTopPdctgCnt(myDataStatSelectReq);
			if ( rprtTopPdctgCntList.size() > 0 ) {
				myDataStatSelectRes.setRprtTopPdctgCntList(rprtTopPdctgCntList);
			}

		}

		return myDataStatSelectRes;

	}


	/*****************************************************
	 * [나의 시험인증 현황] - [성적서] > [TOP10 항목별 성적서 비율(건수)] 차트 데이터 조회 + [월별 TOP10 항목별 성적서 추이(건수)] 차트 데이터 조회
	 * @param myDataStatSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public MyDataStatSelectRes getMyDataRprtTopItem( MyDataStatSelectReq myDataStatSelectReq ) throws Exception {

		MyDataStatSelectRes myDataStatSelectRes = new MyDataStatSelectRes();

		myDataStatSelectReq.setBrno(SessionUtil.getLoginBrno());	//사업자번호
		myDataStatSelectReq.setEntGrpMngNo(getDefaultEntGrpMngNo(SessionUtil.getLoginEntGrpMngNo()));	//기업그룹관리번호

		// [4]. [성적서] > [TOP10 항목별 성적서 비율(건수)] 데이터 조회
		myDataStatSelectReq.setQueryType("");
		List<MyDataStatSelectRes> tmpRprtTopItemRateList	= myDataStatMapper.selectMyDataRprtTopItemRate(myDataStatSelectReq);

		if ( tmpRprtTopItemRateList.size() > 0 ) {
			myDataStatSelectReq.setQueryType("RATE");
			List<MyDataStatSelectRes> rprtTopItemRateList	= myDataStatMapper.selectMyDataRprtTopItemRate(myDataStatSelectReq);
			myDataStatSelectRes.setRprtTopItemRateList(rprtTopItemRateList);

			// [5]. [성적서] > [월별 TOP10 항목별 성적서 추이(건수)] 데이터 조회

			/* [5]-1. 조회하기 위해 [월별 TOP10항목별 성적서 정보]_항목 정보들 셋팅 */
			List<MyDataStatSelectReq> rankRprtItemInfoList = new ArrayList<MyDataStatSelectReq>(); //(조회시 필요)[월별 TOP10항목별 성적서 정보]_항목 정보들
			Integer index = 0;
			for( MyDataStatSelectRes rprtTopItem : tmpRprtTopItemRateList ) {

				MyDataStatSelectReq rankRprtTopItemInfo = new MyDataStatSelectReq();
				rankRprtTopItemInfo.setRankInstTestItemCd(rprtTopItem.getInstTestItemCd()); 	//[월별 TOP10항목별 성적서 정보]_기관시험항목코드
				rankRprtTopItemInfo.setRankInstTestItemNm(rprtTopItem.getInstTestItemNm()); 	//[월별 TOP10항목별 성적서 정보]_기관시험항목명
				rankRprtTopItemInfo.setRankNo(index); 											//[월별 TOP10]_순위

				index ++;
				rankRprtItemInfoList.add(rankRprtTopItemInfo);
			}
			myDataStatSelectReq.setRankRprtItemInfoList(rankRprtItemInfoList);
			myDataStatSelectReq.setQueryType("MONTHCNT");

			/* [5]-2. 조회*/
			List<MyDataStatSelectRes> rprtTopItemCntList	= myDataStatMapper.selectMyDataRprtTopItemCnt(myDataStatSelectReq);
			if ( rprtTopItemCntList.size() > 0 ) {
				myDataStatSelectRes.setRprtTopItemCntList(rprtTopItemCntList);
			}

		}

		return myDataStatSelectRes;

	}

	/*****************************************************
	 * [나의 시험인증 현황] - [성적서] > [TOP10기업별 성적서 합격률] 차트 데이터 조회 + [월별 TOP10기업 성적서 부적합 추이(건수)] 차트 데이터 조회
	 * @param myDataStatSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public MyDataStatSelectRes getMyDataRprtTopEnt( MyDataStatSelectReq myDataStatSelectReq ) throws Exception {

		MyDataStatSelectRes myDataStatSelectRes = new MyDataStatSelectRes();

		myDataStatSelectReq.setBrno(SessionUtil.getLoginBrno());	//사업자번호
		myDataStatSelectReq.setEntGrpMngNo(getDefaultEntGrpMngNo(SessionUtil.getLoginEntGrpMngNo()));	//기업그룹관리번호

		// [6]. [성적서] > [TOP10기업별 성적서 합격률] 데이터 조회
		myDataStatSelectReq.setSearchType("SUBMIT"); // search type 제출로
		myDataStatSelectReq.setQueryType("");
		List<MyDataStatSelectRes> tmpRprtTopEntRateList	= myDataStatMapper.selectMyDataRprtTopEntRate(myDataStatSelectReq);

		if ( tmpRprtTopEntRateList.size() > 0 ) {

			myDataStatSelectReq.setQueryType("RATE");
			List<MyDataStatSelectRes> rprtTopEntRateList	= myDataStatMapper.selectMyDataRprtTopEntRate(myDataStatSelectReq);
			myDataStatSelectRes.setRprtTopEntRateList(rprtTopEntRateList);

			// [7]. [성적서] > [월별 TOP10기업 성적서 부적합 추이(건수)] 데이터 조회

			/* [7]-1. 조회하기 위해 [월별 TOP10기업 정보]_접수회사정보들 셋팅 */
			List<MyDataStatSelectReq> rankRcptCoInfoList = new ArrayList<MyDataStatSelectReq>(); //(조회시 필요)[월별 TOP10기업 정보]_접수회사정보들
			Integer index = 0;
			for( MyDataStatSelectRes rprtTopEnt : tmpRprtTopEntRateList ) {

				MyDataStatSelectReq rankRcptCoInfo = new MyDataStatSelectReq();
				rankRcptCoInfo.setRankRcptCoBrno(rprtTopEnt.getRcptCoBrno()); 	//[월별 TOP10기업 정보]_접수회사사업자번호
				rankRcptCoInfo.setRankRcptCoNm(rprtTopEnt.getRcptCoNm()); 		//[월별 TOP10기업 정보]_접수회사사업자번호
				rankRcptCoInfo.setRankNo(index); 								//[월별 TOP10기업 정보]_순위

				index ++;
				rankRcptCoInfoList.add(rankRcptCoInfo);
			}
			myDataStatSelectReq.setRankRcptCoInfoList(rankRcptCoInfoList);
			myDataStatSelectReq.setQueryType("MONTHCNT");

			/* [7]-2. 조회*/
			List<MyDataStatSelectRes> rprtTopEntCntList		= myDataStatMapper.selectMyDataRprtTopEntCnt(myDataStatSelectReq);
			if ( rprtTopEntCntList.size() > 0 ) {
				myDataStatSelectRes.setRprtTopEntCntList(rprtTopEntCntList);
			}

		}

		return myDataStatSelectRes;

	}


	/*****************************************************
	 * 조회용 기본 기업그룹관리번호 구하기(9999999999면 _로 치환하여 반환)
	 * @param entGrpMngNo 기업그룹관리번호
	 * @return String
	 * @throws Exception
	*****************************************************/
	public String getDefaultEntGrpMngNo( String entGrpMngNo ) throws Exception {
		return "9999999999".equals(entGrpMngNo)? "_" : entGrpMngNo;
	}

}

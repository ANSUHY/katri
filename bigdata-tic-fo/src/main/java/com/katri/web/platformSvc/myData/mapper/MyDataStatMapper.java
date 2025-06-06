package com.katri.web.platformSvc.myData.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.platformSvc.myData.model.MyDataStatSelectReq;
import com.katri.web.platformSvc.myData.model.MyDataStatSelectRes;

@Repository
@Mapper
@MainMapperAnnotation
public interface MyDataStatMapper {

	/*****************************************************
	 * [나의 시험인증 현황] -> 기관 목록 조회
	 * @return List
	 *****************************************************/
	List<MyDataStatSelectRes> selectInstList();

	/*****************************************************
	 * [나의 시험인증 현황] -> 년/월 정보 조회
	 * @return MyDataStatSelectRes
	 *****************************************************/
	MyDataStatSelectRes selectDateData();

	/*****************************************************
	 * [나의 시험인증 현황] -> 사업자 등록번호 조회
	 * @return List
	 *****************************************************/
	String selectMyDataStatBrno(Integer loginEntGrpSn);

	/*****************************************************
	 * [나의 시험인증 현황] -> [통계] -> 년 기준 의뢰 현황 -> 인증
	 * @return List
	 *****************************************************/
	List<MyDataStatSelectRes> selectStatCertDataByYear1(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [통계] -> 년 기준 의뢰 현황 -> 성적서
	 * @return List
	 *****************************************************/
	List<MyDataStatSelectRes> selectStatRprtDataByYear(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [통계] -> 년 기준 의뢰 현황 -> 시험항목
	 * @return List
	 *****************************************************/
	List<MyDataStatSelectRes> selectStatTestDataByYear(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [통계] -> 월 기준 의뢰 현황 -> 인증
	 * @return List
	 *****************************************************/
	List<MyDataStatSelectRes> selectStatCertDataByMonth(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [통계] -> 월 기준 의뢰 현황 -> 성적서
	 * @return List
	 *****************************************************/
	List<MyDataStatSelectRes> selectStatRprtDataByMonth(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [통계] -> 월 기준 의뢰 현황 -> 시험항목
	 * @return List
	 *****************************************************/
	List<MyDataStatSelectRes> selectStatTestDataByMonth(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [통계] -> 월별 내 의뢰 추이
	 * @return List
	 *****************************************************/
	List<MyDataStatSelectRes> selectStatReqDataByMonth(MyDataStatSelectReq myDataStatSelectReq);


	/*****************************************************
	 * [나의 시험인증 현황] -> [인증] -> ~년 인증 전체 현황 -> 전체 인증 건수
	 * @return List
	 *****************************************************/
	MyDataStatSelectRes selectCertCountData(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [인증] -> ~년 인증 전체 현황 -> 월별 인증 추이(건수)
	 * @return List
	 *****************************************************/
	List<MyDataStatSelectRes> selectStatCertCountByMonthData(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [인증] -> ~년 인증 현황 -> 법정기준 인증 비율(인증 데이터)
	 * @return List
	 *****************************************************/
	List<MyDataStatSelectRes> selectCertRateDataByYear(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [인증] -> ~년 인증 현황 -> 법정기준 인증 비율(성적서)
	 * @return List
	 *****************************************************/
	List<MyDataStatSelectRes> selectCertRateData2(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [인증] -> ~년 인증 현황 -> 법정기준 인증 비율(시험항목)
	 * @return List
	 *****************************************************/
	List<MyDataStatSelectRes> selectCertRateData3(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [인증] -> [~년 인증 현황] -> [월별 법정기준 인증 추이(건수)]
	 * @return List
	 *****************************************************/
	List<MyDataStatSelectRes> selectStatProgressCount(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [인증] -> [~년 품목별 인증 현황] -> [품목별 인증 비율]
	 * @return List
	 *****************************************************/
	List<MyDataStatSelectRes> selectCertRateByItem(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [인증] -> [~년 품목별 인증 현황] -> [TOP3 제품 인증 추이]
	 * @return List
	 *****************************************************/
	List<MyDataStatSelectRes> selectCertRateByItemTop3(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [인증] -> [~년 품목별 인증 현황] -> [월별 TOP3 품목별 인증 추이(건수)]
	 * @return List
	 *****************************************************/
	List<MyDataStatSelectRes> selectChartJsTop3ProgressByItem(MyDataStatSelectReq myDataStatSelectReq);










	/* ------------------------------------------- 성적서 관련 ------------------------------------------- */
	/* ------------------------------------------- 성적서 관련 ------------------------------------------- */
	/* ------------------------------------------- 성적서 관련 ------------------------------------------- */
	/* ------------------------------------------- 성적서 관련 ------------------------------------------- */
	/* ------------------------------------------- 성적서 관련 ------------------------------------------- */
	/* ------------------------------------------- 성적서 관련 ------------------------------------------- */

	/*****************************************************
	 * [나의 시험인증 현황] -> [성적서] > [판정결과별 성적서 비율(건수)] 데이터 조회
	 * @param myDataStatSelectReq
	 * @return
	*****************************************************/
	List<MyDataStatSelectRes> selectMyDataRprtPassRate(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [성적서] > [월별 판정결과별 성적서 추이(건수)] 데이터 조회
	 * @param myDataStatSelectReq
	 * @return
	*****************************************************/
	List<MyDataStatSelectRes> selectMyDataRprtMonthPassCnt(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [성적서] > [TOP10 품목별 성적서 비율(건수)] 데이터 조회
	 * @param myDataStatSelectReq
	 * @return
	*****************************************************/
	List<MyDataStatSelectRes> selectMyDataRprtTopPdctgRate(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [성적서] > [월별 TOP10 품목별 성적서 추이(건수)] 데이터 조회
	 * @param myDataStatSelectReq
	 * @return
	*****************************************************/
	List<MyDataStatSelectRes> selectMyDataRprtTopPdctgCnt(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [성적서] > [TOP10 시험항목별 비율(건수)] 데이터 조회
	 * @param myDataStatSelectReq
	 * @return
	*****************************************************/
	List<MyDataStatSelectRes> selectMyDataRprtTopItemRate(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [성적서] > [월별 TOP10 시험항목별 추이(건수)] 데이터 조회
	 * @param myDataStatSelectReq
	 * @return
	*****************************************************/
	List<MyDataStatSelectRes> selectMyDataRprtTopItemCnt(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [성적서] > [TOP10 기업별 성적서 비율(건수)] 데이터 조회
	 * @param myDataStatSelectReq
	 * @return
	*****************************************************/
	List<MyDataStatSelectRes> selectMyDataRprtTopEntRate(MyDataStatSelectReq myDataStatSelectReq);

	/*****************************************************
	 * [나의 시험인증 현황] -> [성적서] > [TOP10 기업별 성적서 추이(건수)] 데이터 조회
	 * @param myDataStatSelectReq
	 * @return
	*****************************************************/
	List<MyDataStatSelectRes> selectMyDataRprtTopEntCnt(MyDataStatSelectReq myDataStatSelectReq);

}

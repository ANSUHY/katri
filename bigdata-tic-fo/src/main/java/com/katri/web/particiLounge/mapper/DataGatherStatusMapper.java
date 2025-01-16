package com.katri.web.particiLounge.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.particiLounge.model.DataGatherStatusSelectReq;
import com.katri.web.particiLounge.model.DataGatherStatusSelectRes;


/**
 * @author sadap
 *
 */
@Repository
@Mapper
@MainMapperAnnotation
public interface DataGatherStatusMapper {

	/*****************************************************
	 * 기관별 데이터 수집 현황
	 * @param dataGatherStatusSelectReq
	 * @return
	*****************************************************/
	List<DataGatherStatusSelectRes> selectDataGatherStatus(DataGatherStatusSelectReq dataGatherStatusSelectReq);

	/*****************************************************
	 * 수집건수 추이
	 * @param dataGatherStatusSelectReq
	 * @return
	*****************************************************/
	List<DataGatherStatusSelectRes> selectNumberOfCollection(DataGatherStatusSelectReq dataGatherStatusSelectReq);

	/*****************************************************
	 * 최근 수집 일자
	 * @return
	*****************************************************/
	DataGatherStatusSelectRes selectLastGatherDt();

	/*****************************************************
	 * 기관코드 조회
	 * @return
	*****************************************************/
	List<DataGatherStatusSelectRes> selectInstIdList();

	/*****************************************************
	 * 접수데이터 수집결과 추이
	 * @param dataGatherStatusSelectReq
	 * @return
	*****************************************************/
	List<DataGatherStatusSelectRes> selectRcptCollectionResult(DataGatherStatusSelectReq dataGatherStatusSelectReq);

	/*****************************************************
	 * 시험데이터 수집결과 추이
	 * @param dataGatherStatusSelectReq
	 * @return
	*****************************************************/
	List<DataGatherStatusSelectRes> selectTestItemCollectionResult(DataGatherStatusSelectReq dataGatherStatusSelectReq);

	/*****************************************************
	 * 성적데이터 수집결과 추이
	 * @param dataGatherStatusSelectReq
	 * @return
	*****************************************************/
	List<DataGatherStatusSelectRes> selectRprtCollectionResult(DataGatherStatusSelectReq dataGatherStatusSelectReq);

	/*****************************************************
	 * 인증데이터 수집결과 추이
	 * @param dataGatherStatusSelectReq
	 * @return
	*****************************************************/
	List<DataGatherStatusSelectRes> selectCertCollectionResult(DataGatherStatusSelectReq dataGatherStatusSelectReq);

	/*****************************************************
	 * 인증이미지 수집결과 추이
	 * @param dataGatherStatusSelectReq
	 * @return
	*****************************************************/
	List<DataGatherStatusSelectRes> selectCertImgCollectionResult(DataGatherStatusSelectReq dataGatherStatusSelectReq);

}

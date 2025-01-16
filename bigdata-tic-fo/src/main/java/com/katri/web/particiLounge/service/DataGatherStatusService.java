package com.katri.web.particiLounge.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.web.particiLounge.mapper.DataGatherStatusMapper;
import com.katri.web.particiLounge.model.DataGatherStatusSelectReq;
import com.katri.web.particiLounge.model.DataGatherStatusSelectRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class DataGatherStatusService {

	private final DataGatherStatusMapper dataGatherStatusMapper;

	/*****************************************************
	 * 기관코드 조회
	 * @return
	 * @throws Exception
	*****************************************************/
	public List<DataGatherStatusSelectRes> getInstId() throws Exception {
		List<DataGatherStatusSelectRes> instIdList = null;

		instIdList = dataGatherStatusMapper.selectInstIdList();

		if (instIdList == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return instIdList;
	}

	/*****************************************************
	 * 최근 수집 일자 조회
	 * @return
	 * @throws Exception
	*****************************************************/
	public DataGatherStatusSelectRes getLastGatherDt() {
		DataGatherStatusSelectRes dataGatherStatusSelectRes = null;

		// 최근 수집 일자 조회
		dataGatherStatusSelectRes = dataGatherStatusMapper.selectLastGatherDt();

		return dataGatherStatusSelectRes;
	}

	/*****************************************************
	 * 기관별 데이터 수집 현황
	 * @param dataGatherStatusSelectReq
	 * @return
	*****************************************************/
	public List<DataGatherStatusSelectRes> getDataGatherStatus(DataGatherStatusSelectReq dataGatherStatusSelectReq) throws Exception {
		List<DataGatherStatusSelectRes> agencySpecificData = null;

		// 기관별 데이터 수집 현황 조회
		agencySpecificData = dataGatherStatusMapper.selectDataGatherStatus(dataGatherStatusSelectReq);

		if (agencySpecificData == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return agencySpecificData;
	}

	/*****************************************************
	 * 수집건수 추이
	 * @param dataGatherStatusSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public List<DataGatherStatusSelectRes> getNumberOfCollection(DataGatherStatusSelectReq dataGatherStatusSelectReq) throws Exception {
		List<DataGatherStatusSelectRes> chgNumberOfCollection = null;

		// 수집건수 조회
		chgNumberOfCollection = dataGatherStatusMapper.selectNumberOfCollection(dataGatherStatusSelectReq);

		if (chgNumberOfCollection == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return chgNumberOfCollection;
	}

	/*****************************************************
	 * 접수데이터 수집결과 추이
	 * @param dataGatherStatusSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public List<DataGatherStatusSelectRes> getRcptCollectionResult(DataGatherStatusSelectReq dataGatherStatusSelectReq) throws Exception {
		List<DataGatherStatusSelectRes> rcptCollectionResult = null;

		// 접수데이터 수집결과 추이 조회
		rcptCollectionResult = dataGatherStatusMapper.selectRcptCollectionResult(dataGatherStatusSelectReq);

		if (rcptCollectionResult == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return rcptCollectionResult;
	}

	/*****************************************************
	 * 시험데이터 수집결과 추이
	 * @param dataGatherStatusSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public List<DataGatherStatusSelectRes> getTestItemCollectionResult(DataGatherStatusSelectReq dataGatherStatusSelectReq) throws Exception {
		List<DataGatherStatusSelectRes> testItemCollectionResult = null;

		// 시험데이터 수집결과 추이 조회
		testItemCollectionResult = dataGatherStatusMapper.selectTestItemCollectionResult(dataGatherStatusSelectReq);

		if (testItemCollectionResult == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return testItemCollectionResult;
	}

	/*****************************************************
	 * 성적데이터 수집결과 추이
	 * @param dataGatherStatusSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public List<DataGatherStatusSelectRes> getRprtCollectionResult(DataGatherStatusSelectReq dataGatherStatusSelectReq) throws Exception {
		List<DataGatherStatusSelectRes> rprtCollectionResult = null;

		// 성적데이터 수집결과 추이 조회
		rprtCollectionResult = dataGatherStatusMapper.selectRprtCollectionResult(dataGatherStatusSelectReq);

		if (rprtCollectionResult == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return rprtCollectionResult;
	}

	/*****************************************************
	 * 인증데이터 수집결과 추이
	 * @param dataGatherStatusSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public List<DataGatherStatusSelectRes> getCertCollectionResult(DataGatherStatusSelectReq dataGatherStatusSelectReq) throws Exception {
		List<DataGatherStatusSelectRes> certCollectionResult = null;

		// 인증데이터 수집결과 추이 조회
		certCollectionResult = dataGatherStatusMapper.selectCertCollectionResult(dataGatherStatusSelectReq);

		if (certCollectionResult == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return certCollectionResult;
	}

	/*****************************************************
	 * 인증이미지 수집결과 추이
	 * @param dataGatherStatusSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public List<DataGatherStatusSelectRes> getCertImgCollectionResult(DataGatherStatusSelectReq dataGatherStatusSelectReq) throws Exception {
		List<DataGatherStatusSelectRes> certImgCollectionResult = null;

		// 인증이미지 수집결과 추이 조회
		certImgCollectionResult = dataGatherStatusMapper.selectCertImgCollectionResult(dataGatherStatusSelectReq);

		if (certImgCollectionResult == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return certImgCollectionResult;
	}

}

package com.katri.web.dataUsageGuide.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.dataUsageGuide.model.CertDataSelectReq;
import com.katri.web.dataUsageGuide.model.CertDataSelectRes;


@Repository
@Mapper
@MainMapperAnnotation
public interface CertDataMapper {

	/*****************************************************
	 * [인증데이터 조회] 리스트 개수 조회
	 * @param certDataSelectReq
	 * @return 리스트개수
	 *****************************************************/
	int selectCertDataCount(CertDataSelectReq certDataSelectReq);

	/*****************************************************
	 * [인증데이터 조회] 리스트 조회
	 * @param certDataSelectReq
	 * @return 리스트
	 *****************************************************/
	List<CertDataSelectRes> selectCertDataList(CertDataSelectReq certDataSelectReq);

	/*****************************************************
	 * [인증데이터 조회] 상세 조회
	 * @param certDataSelectReq
	 * @return 상세정보
	 *****************************************************/
	CertDataSelectRes selectCertDataDetail(CertDataSelectReq certDataSelectReq);

	/*****************************************************
	 * [인증데이터 조회] 이미지 리스트 조회
	 * @param certDataSelectReq
	 * @return 리스트
	 *****************************************************/
	List<String> selectCertDataImgList(CertDataSelectReq certDataSelectReq);

	/*****************************************************
	 * [##인증기관##] 리스트 조회
	 * @return 리스트
	 *****************************************************/
	List<CertDataSelectRes> selectCoInstList();

	/*****************************************************
	 * [##CO_법정제품분류##] 리스트 조회
	 * @return 리스트
	 *****************************************************/
	List<CertDataSelectRes> selectPtCoSttyPrdtClfCdList();

	/*****************************************************
	 * [##통합공통상세코드##] 리스트 조회
	 * @param grpCd
	 * @return 리스트
	 *****************************************************/
	List<CertDataSelectRes> selectPtComnDtlCdList(String grpCd);

	/*****************************************************
	 * [##설명문장##] 페이지에 맞는 데이터 조회
	 * @return 내용
	 *****************************************************/
	String selectExplanMenuCptnCnData();

}

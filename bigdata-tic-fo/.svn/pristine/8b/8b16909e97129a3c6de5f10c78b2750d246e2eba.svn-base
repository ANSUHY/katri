package com.katri.web.cert.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.cert.model.CertRes;


@Repository
@Mapper
@MainMapperAnnotation

public interface CertMapper {

	/*****************************************************
	 * 인증정보 조회 총 카운트
	 * @param CertReq 인증정보
	 * @return 인증정보 리스트
	 *****************************************************/
	int selectListCount(Map<String, Object> param);
	/*****************************************************
	 * 인증정보 조회
	 * @param CertReq 인증정보
	 * @return 인증정보 리스트
	 *****************************************************/
	List<CertRes> selectList(Map<String, Object> param);
	/*****************************************************
	 * 인증정보 상세
	 * @param CertReq 인증정보
	 * @return 인증정보
	 *****************************************************/
	CertRes selectInfo(Map<String, Object> param);

}

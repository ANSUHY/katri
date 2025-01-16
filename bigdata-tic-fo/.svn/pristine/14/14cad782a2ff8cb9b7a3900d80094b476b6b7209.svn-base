package com.katri.web.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.auth.model.DrmncySaveReq;
import com.katri.web.auth.model.DrmncySelectReq;

@Repository
@Mapper
@MainMapperAnnotation
public interface DrmncyMapper {

	/*****************************************************
	 * 휴면 아이디 해제 (PASS 인증 회원)
	 * @param drmncySaveReq
	 * @return int 업데이트 정보
	 *****************************************************/
	int updateDrmncy(DrmncySaveReq drmncySaveReq);

	/*****************************************************
	 * 휴면 아이디 해제 (메일 인증 회원)
	 * @param drmncySaveReq
	 * @return int 업데이트 정보
	 *****************************************************/
	int updateDrmncyWithMail(DrmncySaveReq drmncySaveReq);

	/*****************************************************
	 * 유저 메일 조회
	 * @param 휴면 상태 아이디
	 * @return String 유저 메일
	 *****************************************************/
	String selectUserMail(String userId);

	/*****************************************************
	 * 휴면 회원 메일 인증
	 * @param drmncySaveReq
	 * @return int
	 *****************************************************/
	 int insertCertNoSndngHist(DrmncySaveReq drmncySaveReq);

	 /*****************************************************
	 * 가장 최근의 이메일 인증 번호 조회
	 * @param drmncySelectReq
	 * @return String
	 *****************************************************/
	String selectCertNoOfEmlAddr(DrmncySelectReq drmncySelectReq);



}

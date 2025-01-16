package com.katri.web.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.auth.model.OverDayChgPwdSaveReq;
import com.katri.web.auth.model.OverDayChgPwdSelectRes;


@Repository
@Mapper
@MainMapperAnnotation
public interface OverDayChgPwdMapper {

	/*****************************************************
	 * 기존 비밀번호 조회
	 * @param 로그인한 사용자 ID
	 * @return 기존 비밀번호
	 *****************************************************/
	String selectOriPwd(String loginUserId);

	/*****************************************************
	 * 비밀번호 수정
	 * @param OverDayChgPwdSaveReq 수정할 비밀번호 정보
	 * @return 성공 카운트
	 *****************************************************/
	int updateChgOverDayChgPwd(OverDayChgPwdSaveReq overDayChgPwdSaveReq);

	/*****************************************************
	 * last_pwd_chg_dt를 지금으로 수정
	 * @return 성공 카운트
	 *****************************************************/
	int updatePwdChgDtNow(OverDayChgPwdSaveReq overDayChgPwdSaveReq);

	/*****************************************************
	 * [분석환경 API 호출] > 수정 대상 사용자 API 관련 정보 조회
	 * @param loginUserId
	 * @return
	*****************************************************/
	OverDayChgPwdSelectRes selectAnlsEnvUserInfo(String loginUserId);

}

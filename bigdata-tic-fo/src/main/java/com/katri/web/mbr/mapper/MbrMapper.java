package com.katri.web.mbr.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.mbr.model.MbrReq;
import com.katri.web.mbr.model.MbrRes;

import io.swagger.annotations.Api;

@Repository
@Mapper
@MainMapperAnnotation
@Api(tags = {"회원 Mapper"})
public interface MbrMapper {

	/*****************************************************
	 * 사용자 입력
	 * @param mbrReq 유져정보
	 * @return 성공 카운트
	 *****************************************************/
	int insertUser(MbrReq mbrReq);

	/*****************************************************
	 * ID, Email 중복 체크
	 * @param MbrReq 검색조건
	 * @return 카운트
	 *****************************************************/
	MbrRes selectInfoChk(MbrReq mbrReq);

	/*****************************************************
	 * 로그인 정보 조회
	 * @param mbrReq 유져정보
	 * @return 로그인 정보
	 *****************************************************/
	MbrRes selectLoginDetail(MbrReq mbrReq);

	/*****************************************************
	 * 로그인 실패 횟수 초기화
	 * @param loginReq 검색조건
	 * @return 로그인 정보
	 *****************************************************/
	int updateLoginFailCntInit(MbrReq mbrReq);

	/*****************************************************
	 * 로그인 실패 횟수 초기화
	 * @param loginReq 검색조건
	 * @return 로그인 정보
	 *****************************************************/
	int selectUserCount(Map<String, Object> param);


	/*****************************************************
	 * 회원 정보 조회
	 * @param HashMap<String, Object> 유져정보
	 * @return 회원 리스트
	 *****************************************************/
	List<MbrRes> selectUser(Map<String, Object> param);

	/*****************************************************
	 * 회원 정보 상세
	 * @param HashMap<String, Object> 유져정보
	 * @return 회원 리스트
	 *****************************************************/
	MbrRes selectUserInfo(String targetId);

	/*****************************************************
	 * 회원수정
	 * @param mbrReq 검색조건
	 * @return 로그인 정보
	 *****************************************************/
	int updateUser(MbrReq mbrReq);
	/*****************************************************
	 * 회원탈퇴
	 * @param mbrReq 검색조건
	 * @return 로그인 정보
	 *****************************************************/
	int deleteUser(MbrReq mbrReq);

	/*****************************************************
	 * 회원 관리 목록 엑셀 다운로드
	 * @param 회원 목록 조회
	 * @return 성공 카운트
	 *****************************************************/
	List<Map<String, Object>> selectMbrListExcelDown();
}

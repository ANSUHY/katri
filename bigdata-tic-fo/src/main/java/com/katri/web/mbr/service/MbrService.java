package com.katri.web.mbr.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.web.mbr.mapper.MbrMapper;
import com.katri.web.mbr.model.MbrReq;
import com.katri.web.mbr.model.MbrRes;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
@Slf4j
@Api(tags = {"회원 Service"})
public class MbrService {

	private final MbrMapper mbrMapper;

	/*****************************************************
	 * 유저 등록
	 * @param mbrReq 사용자 정보
	 * @return 등록된 카운트
	 *****************************************************/
	public int insertUser(MbrReq mbrReq) {
		return this.mbrMapper.insertUser(mbrReq);
	}

	/*****************************************************
	 * ID, Email 중복 체크
	 * @param MbrReq 검색조건
	 * @return 카운트
	 *****************************************************/
	public MbrRes selectInfoChk(MbrReq mbrReq) {
		return this.mbrMapper.selectInfoChk(mbrReq);
	}

	/*****************************************************
	 * 로그인 정보 조회
	 * @param MbrReq 검색조건
	 * @return 로그인 정보
	 *****************************************************/
	public MbrRes selectLoginDetail(MbrReq mbrReq) {
		return this.mbrMapper.selectLoginDetail(mbrReq);
	}

	/*****************************************************
	 * 로그인 실패 횟수 초기화 + IPAddress넣기
	 * @param loginReq 검색조건
	 * @return 업데이트 갯수
	 *****************************************************/
	public int updateLoginFailCntInit(MbrReq mbrReq) {
		return this.mbrMapper.updateLoginFailCntInit(mbrReq);
	}

	/*****************************************************
	 * 회원 리스트 카운트
	 * @param HashMap<String, Object> 검색조건
	 * @return 로그인 정보
	 *****************************************************/
	public int selectUserCount(Map<String, Object> param) {
		return this.mbrMapper.selectUserCount(param);
	}
	/*****************************************************
	 * 회원 리스트
	 * @param HashMap<String, Object> 검색조건
	 * @return 로그인 정보
	 *****************************************************/
	public List<MbrRes> selectUser(Map<String, Object> param) {
		return this.mbrMapper.selectUser(param);
	}

	/*****************************************************
	 * 회원 상세
	 * @param HashMap<String, Object> 검색조건
	 * @return 로그인 정보
	 *****************************************************/
	public MbrRes selectUserInfo(String targetId) {
		return this.mbrMapper.selectUserInfo(targetId);
	}

	/*****************************************************
	 * 회원 수정
	 * @param loginReq 검색조건
	 * @return 업데이트 갯수
	 *****************************************************/
	public int updateUser(MbrReq mbrReq) {
		return this.mbrMapper.updateUser(mbrReq);
	}
	/*****************************************************
	 * 회원 탈퇴
	 * @param loginReq 검색조건
	 * @return 업데이트 갯수
	 *****************************************************/
	public int deleteUser(MbrReq mbrReq) {
		return this.mbrMapper.deleteUser(mbrReq);
	}

	/*****************************************************
	 * 회원 현황 엑셀 리스트 조회
	 * @param
	 * @return 회원 현황 엑셀 리스트
	 *****************************************************/
	public List<Map<String, Object>> selectMbrListExcelDown() {

		return this.mbrMapper.selectMbrListExcelDown();
	}
}

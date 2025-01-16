package com.katri.web.system.authrt.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.SessionUtil;
import com.katri.web.system.authrt.mapper.AuthrtMapper;
import com.katri.web.system.authrt.model.AuthrtSaveReq;
import com.katri.web.system.authrt.model.AuthrtSaveRes;
import com.katri.web.system.authrt.model.AuthrtSelectReq;
import com.katri.web.system.authrt.model.AuthrtSelectRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class AuthrtService {

	/** Authrt Mapper */
	private final AuthrtMapper authrtMapper;

	/*****************************************************
	 * 권한 그룹 리스트 데이터 개수 조회
	 * @param authrtSelectReq
	 * @return
	*****************************************************/
	public Integer getAuthrtGrpBasCnt(AuthrtSelectReq authrtSelectReq) {
		int nTotCnt = 0;

		// 0. 권한 그룹 리스트 개수 조회
		nTotCnt = authrtMapper.selectAuthGrpBasCount(authrtSelectReq);

		return nTotCnt;
	}

	/*****************************************************
	 * 권한 그룹 리스트 데이터 조회
	 * @param authrtSelectReq
	 * @return
	*****************************************************/
	public List<AuthrtSelectRes> getAuthrtGrpBasList(AuthrtSelectReq authrtSelectReq) {

		List<AuthrtSelectRes> authrtList = null;

		// 0. 권한 그룹 리스트 조회
		authrtList = authrtMapper.selectAuthGrpBasList(authrtSelectReq);

		return authrtList;
	}

	/*****************************************************
	 * 접속 권한 그룹 상세 조회
	 * @param authrtSelectReq
	 * @return
	*****************************************************/
	public AuthrtSelectRes getAuthrtGrpBasDetail(AuthrtSelectReq authrtSelectReq) {

		AuthrtSelectRes authrtSelectRes = null;

		// 0. 권한 그룹 상세 조회
		authrtSelectRes = authrtMapper.selectAuthGrpBasDetail(authrtSelectReq);

		return authrtSelectRes;

	}

	/*****************************************************
	 * 접속 권한 메뉴 권한 등록
	 * @param authrtSaveReq
	 * @return
	 * @throws CustomMessageException
	*****************************************************/
	public AuthrtSaveRes insertAuthGrpBas(@Valid AuthrtSaveReq authrtSaveReq) throws Exception {

		String strSaveErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		// 0. 변수 설정
		AuthrtSaveRes authrtSaveRes = new AuthrtSaveRes();
		int			  nSaveCount	= 0;

		// 1. 등록 시작
		nSaveCount = authrtMapper.insertAuthGrpBas(authrtSaveReq);

		if(! (nSaveCount > 0 ) || authrtSaveReq.getAuthrtGrpSn() == null) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// 2. 반환값 셋팅
		authrtSaveRes.setAuthrtGrpSn(authrtSaveReq.getAuthrtGrpSn());

		return authrtSaveRes;
	}

	/*****************************************************
	 * 접속 권한 메뉴 권한 수정
	 * @param authrtSaveReq
	 * @return
	*****************************************************/
	public AuthrtSaveRes updateAuthGrpBas(@Valid AuthrtSaveReq authrtSaveReq) throws Exception {

		String strSaveErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		// 0. 변수 설정
		AuthrtSaveRes authrtSaveRes = new AuthrtSaveRes();
		int			  nSaveCount	= 0;

		// 1. 수정 시작
		nSaveCount = authrtMapper.updateAuthGrpBas(authrtSaveReq);

		if(! (nSaveCount > 0 ) || authrtSaveReq.getAuthrtGrpSn() == null) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// 2. 반환값 셋팅
		authrtSaveRes.setAuthrtGrpSn(authrtSaveReq.getAuthrtGrpSn());

		return authrtSaveRes;
	}

	/*****************************************************
	 * 메뉴 별 접속 권한 리스트 조회
	 * @param authrtSelectReq
	 * @return
	*****************************************************/
	public List<AuthrtSelectRes> getAuthrtGrpMenuList(AuthrtSelectReq authrtSelectReq) {

		List<AuthrtSelectRes> authrtGrpMenuList = null;

		// 0. 메뉴 별 접속 권한 리스트 조회
		authrtGrpMenuList = authrtMapper.selectAuthrtGrpMenuList(authrtSelectReq);

		return authrtGrpMenuList;

	}

	/*****************************************************
	 * 메뉴 별 접속 권한 저장 ( 삭제 > 재등록 )
	 * @param authrtSaveReq
	 * @return
	*****************************************************/
	public AuthrtSaveRes saveAuthrtGrpMenu(AuthrtSaveReq authrtSaveReq) throws Exception {

		AuthrtSaveRes authrtSaveRes = new AuthrtSaveRes();

		String 	strSaveErrMsgCode 	= "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		int		nSaveCount 			= 0;

		Integer[] arrMenuSn 			= authrtSaveReq.getArrMenuSn();
		String[]  arrAuthrtChkYn		= authrtSaveReq.getArrCntnAuthrtYn();

		// 0. 체크 및 메뉴 리스트 목록 확인
		if( arrMenuSn == null || arrAuthrtChkYn == null ) {
			strSaveErrMsgCode = "result-message.messages.common.message.no.data";
			throw new CustomMessageException(strSaveErrMsgCode); // 데이터가 없습니다.
		}

		if( arrMenuSn.length > 0 && arrMenuSn.length == arrAuthrtChkYn.length ) {

			/** ========== [0]. 해당 권한 그룹 메뉴 전체 삭제 ========== */
			// 0_0. 해당 그룹 메뉴 권한 개수 조회
			int nGrpMenuCount = authrtMapper.selectAuthGrpMenuCount(authrtSaveReq);

			// 0_1. 해당 그룹 메뉴 권한 있는 경우
			if( nGrpMenuCount > 0 ) {

				// 0_0. 해당 그룹 > 메뉴 권한 삭제
				nSaveCount = authrtMapper.deleteAuthrtGrpMenu(authrtSaveReq);

				if(! (nSaveCount > 0 ) ) {
					throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
				}
			}
			/** ====================== [0] ====================== */

			/** ========== [1]. 현재 메뉴 체크/미체크된 권한 저장 ========== */
			// 1_0. 해당 그룹 > 메뉴별 권한 저장
			AuthrtSaveReq tmpAuthrtSaveReq 		= new AuthrtSaveReq();
			Integer[]	  arrAuthrtGrpMenuSn	= new Integer[arrMenuSn.length];

			// 1_1. 그룹 번호 및 등록자 셋팅
			String	strCrtrId		= SessionUtil.getLoginMngrId();
			Integer	nAuthrtGrpSn	= authrtSaveReq.getAuthrtGrpSn();

			for( int nLoop = 0; nLoop < arrMenuSn.length; nLoop++ ) {

				// 1_0_1. 값 셋팅
				tmpAuthrtSaveReq.setAuthrtGrpSn(nAuthrtGrpSn);
				tmpAuthrtSaveReq.setMenuSn(arrMenuSn[nLoop]);
				tmpAuthrtSaveReq.setCntnAuthrtYn(arrAuthrtChkYn[nLoop]);
				tmpAuthrtSaveReq.setCrtrId(strCrtrId);

				// 1_0_1. 메뉴 순서대로 권한 저장
				nSaveCount += authrtMapper.insertAuthrtGrpMenu(tmpAuthrtSaveReq);

				// 1_0_2. 저장된 번호 배열 저장
				arrAuthrtGrpMenuSn[nLoop] = authrtSaveReq.getAuthrtGrpMenuSn();

			}

			// 1_2. 저장 체크
			if( !(nSaveCount > 0 ) ) {
				throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			} else {
				// 1_2_0. 저장 성공 시, 현재 그룹번호 및 등록된 권한그룹메뉴일련번호 배열 셋팅
				authrtSaveRes.setAuthrtGrpSn(nAuthrtGrpSn);
				authrtSaveRes.setArrAuthrtGrpMenuSn(arrAuthrtGrpMenuSn);
			}
			/** ====================== [1] ====================== */

		// 1. 아무 데이터도 없는 경우
		} else {

			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return authrtSaveRes;
	}

}

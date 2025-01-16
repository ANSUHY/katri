package com.katri.web.trms.trms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.SessionUtil;
import com.katri.web.trms.trms.mapper.TrmsMapper;
import com.katri.web.trms.trms.model.TrmsSaveReq;
import com.katri.web.trms.trms.model.TrmsSaveRes;
import com.katri.web.trms.trms.model.TrmsSelectReq;
import com.katri.web.trms.trms.model.TrmsSelectRes;
import com.nhncorp.lucy.security.xss.XssPreventer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class TrmsService {

	/** Trms Mapper */
	private final TrmsMapper trmsMapper;

	/*****************************************************
	 * Trms 리스트 개수
	 * @param trmsSelectReq
	 * @return Integer trms 리스트 개수
	 * @throws Exception
	*****************************************************/
	public Integer getTrmsCnt(TrmsSelectReq trmsSelectReq) throws Exception{

		// [[0]]. 반환할 정보들
		int trmsCnt = 0;

		// [[1]]. Trms 테이블 개수 조회
		trmsCnt = trmsMapper.selectTrmsListCount(trmsSelectReq);

		return trmsCnt;
	}

	/*****************************************************
	 * Trms 리스트_데이터
	 * @param TrmsSelectReq
	 * @return List<TrmsSelectRes> Trms 리스트
	 * @throws Exception
	*****************************************************/
	public List<TrmsSelectRes> getTrmsList(TrmsSelectReq trmsSelectReq) throws Exception{

		// [[0]]. 반환할 정보들
		List<TrmsSelectRes> trmsList = null;

		// [[1]]. Trms 테이블 조회
		trmsList = trmsMapper.selectTrmsList(trmsSelectReq);

		return trmsList;
	}

	/*****************************************************
	 * Trms 상세_데이터
	 * @param TrmsSelectReq
	 * @return TrmsSelectRes Trms 상세
	 * @throws Exception
	*****************************************************/
	public TrmsSelectRes getTrmsDetail(TrmsSelectReq trmsSelectReq) throws Exception{

		// [[0]]. 반환할 정보들
		TrmsSelectRes trmsDetail = null;

		// [[1]]. Trms 테이블 조회
		trmsDetail = trmsMapper.selectTrmsDetail(trmsSelectReq.getTargetTrmsSn());

		if(trmsDetail == null) {
			// 반환값 셋팅
			throw new CustomMessageException("result-message.messages.common.message.no.data"); //데이터가 없습니다.
		}

		trmsDetail.setUnecapeTrmsCn(XssPreventer.unescape(trmsDetail.getTrmsCn()));

		return trmsDetail;
	}

	/*****************************************************
	 * Trms 사용여부 수정
	 * @param TrmsSaveReq chgUseYTrmsSn,chgUseYTrmsType 가 담겨있는 정보
	 * @return TrmsSaveRes
	 * @throws Exception
	 *****************************************************/
	public TrmsSaveRes updateChgTrmsUseYn(TrmsSaveReq trmsSaveReq) throws Exception {

		String strUpdateErrMsgCode = "result-message.messages.common.message.update.error"; //수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		// [[0]]. 반환할 정보들 & 변수 지정
		TrmsSaveRes trmsSaveRes = new TrmsSaveRes();
		int saveCount = 0;
		int chgUseYTrmsSn;
		String chgUseYTrmsType;
		TrmsSaveReq newTrmsSaveReq;

		// [[1]]. validataion check
		/* 1-1. [사용] 으로 change할  약관일련번호 */
		if(trmsSaveReq.getChgUseYTrmsSn() == null  || trmsSaveReq.getChgUseYTrmsSn() == 0) {
			throw new CustomMessageException("필수값 오류_메뉴구성일련번호");
		}
		/* 1-2. [사용] 으로 change할 trmsType */
		if(trmsSaveReq.getChgUseYTrmsType() == null  || "".equals(trmsSaveReq.getChgUseYTrmsType())) {
			throw new CustomMessageException("필수값 오류_menuCptnCd");
		}

		// [[2]]. 데이터 지정
		chgUseYTrmsSn = trmsSaveReq.getChgUseYTrmsSn();
		chgUseYTrmsType = trmsSaveReq.getChgUseYTrmsType();

		// [[3]]. 해당 trmsType 에 해당하는 trms 다 USE_YN = N 으로 수정
		newTrmsSaveReq = new TrmsSaveReq();
		newTrmsSaveReq.setChgUseYn("N");
		newTrmsSaveReq.setChgUseYTrmsType(chgUseYTrmsType);
		newTrmsSaveReq.setMdfrId(SessionUtil.getLoginMngrId());
		saveCount = trmsMapper.updateChgTrmsUseYN(newTrmsSaveReq);
		if(! (saveCount > 0 ) ) {
			throw new CustomMessageException(strUpdateErrMsgCode); //수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// [[4]]. 해당 trmsSn USE_YN = Y 로 수정
		newTrmsSaveReq = new TrmsSaveReq();
		newTrmsSaveReq.setChgUseYn("Y");
		newTrmsSaveReq.setChgUseYTrmsType(chgUseYTrmsType);
		newTrmsSaveReq.setChgUseYTrmsSn(chgUseYTrmsSn);
		newTrmsSaveReq.setMdfrId(SessionUtil.getLoginMngrId());
		saveCount = trmsMapper.updateChgTrmsUseYN(newTrmsSaveReq);
		if(! (saveCount > 0 ) ) {
			throw new CustomMessageException(strUpdateErrMsgCode); //수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return trmsSaveRes;
	}

	/*****************************************************
	 * Trms 등록
	 * @param TrmsSaveReq Trms 정보
	 * @return TrmsSaveRes
	 * @throws Exception
	*****************************************************/
	public TrmsSaveRes insertTrms(TrmsSaveReq trmsSaveReq) throws Exception {

		String strSavetErrMsgCode = "result-message.messages.common.message.save.error"; //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		// [[0]]. 반환할 정보들 & 변수 지정
		TrmsSaveRes trmsSaveRes = new TrmsSaveRes();
		int saveCount = 0;

		// [[1]]. validataion check
		/* 1-1. 약관유형코드 */
		if(trmsSaveReq.getTrmsTyCd() == null  || "".equals(trmsSaveReq.getTrmsTyCd())) {
			throw new CustomMessageException("필수값 오류_약관유형코드");
		}

		// [[2]]. 파라미터셋팅
		trmsSaveReq.setCrtrId(SessionUtil.getLoginMngrId());

		// [[3]]. trms 테이블 등록
		saveCount = trmsMapper.insertTrms(trmsSaveReq);
		if(! (saveCount > 0 ) || trmsSaveReq.getTrmsSn() == null) {
			throw new CustomMessageException(strSavetErrMsgCode); //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// [[4]]. 반환값 셋팅
		trmsSaveRes.setTrmsSn(trmsSaveReq.getTrmsSn());

		return trmsSaveRes;
	}

	/*****************************************************
	 * Trms 수정
	 * @param TrmsSaveReq Trms 정보
	 * @return TrmsSaveRes
	 * @throws Exception
	 *****************************************************/
	public TrmsSaveRes updateTrms(TrmsSaveReq trmsSaveReq) throws Exception {

		String strUpdateErrMsgCode = "result-message.messages.common.message.update.error"; //수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		// [[0]]. 반환할 정보들 & 변수 지정
		TrmsSaveRes trmsSaveRes = new TrmsSaveRes();
		int saveCount = 0;

		// [[1]]. validataion check
		/* 1-0. 약관유형코드 */
		if(trmsSaveReq.getTrmsTyCd() == null  || "".equals(trmsSaveReq.getTrmsTyCd())) {
			throw new CustomMessageException("필수값 오류_메뉴구성코드");
		}
		/* 1-1. 약관일련번호 */
		if(trmsSaveReq.getTrmsSn() == null  || trmsSaveReq.getTrmsSn() == 0) {
			throw new CustomMessageException("필수값 오류_약관일련번호");
		}

		// [[2]]. 파라미터셋팅
		trmsSaveReq.setMdfrId(SessionUtil.getLoginMngrId());

		// [[3]]. trms 테이블 업데이트
		saveCount = trmsMapper.updateTrms(trmsSaveReq);
		if(! (saveCount > 0 ) || trmsSaveReq.getTrmsSn() == null) {
			throw new CustomMessageException(strUpdateErrMsgCode); //수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// [[4]]. 반환값 셋팅
		trmsSaveRes.setTrmsSn(trmsSaveReq.getTrmsSn());

		return trmsSaveRes;
	}

}

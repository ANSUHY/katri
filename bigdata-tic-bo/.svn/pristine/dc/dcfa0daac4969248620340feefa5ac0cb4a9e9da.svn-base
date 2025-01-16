package com.katri.web.ctnt.faq.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.SessionUtil;
import com.katri.web.ctnt.faq.mapper.FaqMapper;
import com.katri.web.ctnt.faq.model.FaqSaveReq;
import com.katri.web.ctnt.faq.model.FaqSaveRes;
import com.katri.web.ctnt.faq.model.FaqSelectReq;
import com.katri.web.ctnt.faq.model.FaqSelectRes;
import com.katri.web.system.code.model.CodeSelectRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class FaqService {

	private final FaqMapper faqMapper;

	/*****************************************************
	 * FAQ 게시글 개수
	 * @param faqSelectReq
	 * @return 총 게시글 수
	*****************************************************/
	public int getFaqCnt(FaqSelectReq faqSelectReq) {
		// [[0]]. 반환할 정보들
		int faqCnt = 0;
		// [[1]]. tb_ntt_mng 테이블 조회
		faqCnt = faqMapper.selectFaqCnt(faqSelectReq);

		return faqCnt;
	}

	/*****************************************************
	 * FAQ 목록 조회
	 * @param faqSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public List<FaqSelectRes> getFaqList(FaqSelectReq faqSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		List<FaqSelectRes> faqList = null;
		// [[1]]. tb_ntt_mng 테이블 조회
		faqList = faqMapper.selectFaqList(faqSelectReq);

		if (faqList == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return faqList;
	}

	/*****************************************************
	 * FAQ 분류 코드 조회
	 * @return List<CodeSelectRes>
	 * @throws Exception
	*****************************************************/
	public List<CodeSelectRes> getNoticeClfCd() throws Exception {
		// [[0]]. 반환할 정보들
		List<CodeSelectRes> faqClfCd = null;
		// [[1]]. tb_comn_cd 테이블 조회
		faqClfCd = faqMapper.selectFaqClfCd();

		if (faqClfCd == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return faqClfCd;
	}

	/*****************************************************
	 * FAQ 단건 조회
	 * @param faqSelectReq
	 * @return FaqSelectRes
	 * @throws Exception
	*****************************************************/
	public FaqSelectRes getFaqDetail(FaqSelectReq faqSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		FaqSelectRes faqSelectRes = new FaqSelectRes();

		// [[1]]. tb_ntt_mng 데이터 조회
		faqSelectRes = faqMapper.selectFaqDetail(faqSelectReq);

		if (faqSelectRes == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return faqSelectRes;
	}

	/*****************************************************
	 * FAQ 작성
	 * @param request
	 * @param faqSaveReq
	 * @return FaqSaveRes
	 * @throws Exception
	*****************************************************/
	public FaqSaveRes insertFaq(HttpServletRequest request, FaqSaveReq faqSaveReq) throws Exception {
		// 접속 사용자
		faqSaveReq.setCrtrId(SessionUtil.getLoginMngrId());

		// [[0]]. 반환할 정보들
		int result = 0;
		FaqSaveRes faqSaveRes = new FaqSaveRes();

		// [[1]]. tb_ntt_mng 데이터 추가
		result = faqMapper.insertFaq(faqSaveReq);

		if (!(result > 0)) {
			throw new CustomMessageException("result-message.messages.common.message.insert.error"); // 작성 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// [[2]]. 반환값 셋팅
		faqSaveRes.setNttSn(faqSaveReq.getNttSn());

		return faqSaveRes;
	}

	/*****************************************************
	 * FAQ 수정
	 * @param request
	 * @param faqSaveReq
	 * @return FaqSaveRes
	 * @throws Exception
	*****************************************************/
	public FaqSaveRes updateFaq(HttpServletRequest request, FaqSaveReq faqSaveReq) throws Exception {
		// 접속 사용자
		faqSaveReq.setMdfrId(SessionUtil.getLoginMngrId());

		// [[0]]. 반환할 정보들
		int result = 0;
		FaqSaveRes faqSaveRes = new FaqSaveRes();

		// [[1]]. tb_ntt_mng 데이터 수정
		result = faqMapper.updateFaq(faqSaveReq);

		if (!(result > 0)) {
			throw new CustomMessageException("result-message.messages.common.message.update.error"); // 수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// [[2]]. 반환값 셋팅
		faqSaveRes.setNttSn(faqSaveReq.getNttSn());

		return faqSaveRes;
	}

}
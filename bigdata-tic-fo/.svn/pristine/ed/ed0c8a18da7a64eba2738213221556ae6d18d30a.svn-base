package com.katri.web.ctnt.faq.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.web.comm.service.FileService;
import com.katri.web.ctnt.faq.mapper.FaqMapper;
import com.katri.web.ctnt.faq.model.FaqSaveReq;
import com.katri.web.ctnt.faq.model.FaqSelectReq;
import com.katri.web.ctnt.faq.model.FaqSelectRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = { Exception.class })
public class FaqService {

	private final FileService fileService;

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
	 * @return List<FaqSelectRes>
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
	 * FAQ 게시글 내용 조회
	 * @param faqSelectReq
	 * @return FaqSelectRes
	 * @throws Exception
	*****************************************************/
	public FaqSelectRes getFaqCn(FaqSelectReq faqSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		FaqSelectRes faqSelectRes = null;
		int uCount = 0;

		// [[1]]. tb_ntt_mng 테이블 조회
		faqSelectRes = faqMapper.selectFaqCn(faqSelectReq);

		if (faqSelectRes == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		FaqSaveReq faqSaveReq = new FaqSaveReq();
		faqSaveReq.setNttSn(faqSelectReq.getNttSn());
		faqSaveReq.setNttTyCd(faqSelectReq.getNttTyCd());

		// FAQ 조회수 증가
		uCount = faqMapper.hitFaq(faqSaveReq);

		if (!(uCount > 0)) {
			throw new CustomMessageException("result-message.messages.common.message.error"); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return faqSelectRes;
	}

}

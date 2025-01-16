package com.katri.web.comm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.web.comm.mapper.TermsMapper;
import com.katri.web.comm.model.TermsSelectRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TermsService {

	/** terms Mapper */
	private final TermsMapper termsMapper;


	/*****************************************************
	 * footer -> 약관 조회
	 * @param String 약관유형코드
	 * @return termsSelectRes 약관 내용
	 * @throws CustomMessageException
	 *****************************************************/
	public TermsSelectRes getKatriTerms(String trmsTyCd) throws CustomMessageException {
		//[[0]].필요한 정보들
		TermsSelectRes termsSelectRes = new TermsSelectRes();

		//[[1]]. 약관 조회
		termsSelectRes	= termsMapper.selectKatriTerms(trmsTyCd);

		if(termsSelectRes == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); //데이터가 없습니다.
		}

		return termsSelectRes;
	}


}

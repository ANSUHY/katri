package com.katri.web.search.keyword.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.SessionUtil;
import com.katri.web.search.keyword.mapper.KeywordMapper;
import com.katri.web.search.keyword.model.KeywordSaveReq;
import com.katri.web.search.keyword.model.KeywordSelectRes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
@Slf4j
public class KeywordService {

	/** Keyword Mapper */
	private final KeywordMapper keywordMapper;

	/*****************************************************
	 * 검색어 등록
	 * @param KeywordSaveReq
	 * @return int
	 * @throws CustomMessageException
	 *****************************************************/
	public int regKeyword(KeywordSaveReq keywordData) throws CustomMessageException {
		//[[0]].반환할 정보들
		int result	= 0;

		//[[1]].등록 데이터 보내기
		/* 1-0. 현재 로그인한 아이디 가지고 오기 */
		String crtrId = SessionUtil.getLoginMngrId();
		keywordData.setCrtrId(crtrId);


		/* 1-1. 매퍼 호출 */
		result = keywordMapper.regKeyword(keywordData);

		if(result < 0) {
			throw new CustomMessageException("resultMessage.messages.common.message.save.error");  //'저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.'
		}

		return result;
	}

	/*****************************************************
	 * 검색어 목록 조회
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public List<KeywordSelectRes> getKeywordList(String srwrdTyCd) throws CustomMessageException {
		//[[0]].반환할 정보들
		List<KeywordSelectRes> keywordList	= null;

		//[[1]].
		keywordList	= keywordMapper.getKeywordList(srwrdTyCd);

		if(keywordList == null) {
			throw new CustomMessageException("resultMessage.messages.common.message.no.data"); //데이터가 없습니다.
		}

		return keywordList;

	}

	/*****************************************************
	 * 추천 검색어 사용여부 개수 조회
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public int getUseYnCount(String srwrdTyCd) throws CustomMessageException {
		//[[0]].반환할 정보들
		int count	= 0;

		//[[1]].데이터 조회
		count	= keywordMapper.getUseYnCount(srwrdTyCd);

		if(count < 0) {
			throw new CustomMessageException("resultMessage.messages.common.message.error"); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return count;
	}

	/*****************************************************
	 * 사용중인 추천 검색어 조회
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public List<KeywordSelectRes> getUseYList(String srwrdTyCd) throws CustomMessageException {
		//[[0]].반환할 정보들
		List<KeywordSelectRes> usedList = null;

		//[[1]].데이터 조회
		usedList	= keywordMapper.getUseYList(srwrdTyCd);

		if(usedList == null) {
			throw new CustomMessageException("resultMessage.messages.common.message.error"); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return usedList;
	}

	/*****************************************************
	 * 추천 검색어 수정
	 * @param keywordSaveReq
	 * @return int
	 * @throws CustomMessageException
	 *****************************************************/
	public int updateKeyword(KeywordSaveReq updateKeywordData) throws CustomMessageException {
		//[[0]].반환할 정보들
		int result	= 0;

		//[[1]]. 매퍼 호출
		result	= keywordMapper.updateKeyword(updateKeywordData);
		if(result <= 0) {
			throw new CustomMessageException("resultMessage.messages.common.message.update.error");   //수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return result;
	}

}

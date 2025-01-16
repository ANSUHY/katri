package com.katri.web.search.keyword.controller;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.service.CommService;
import com.katri.web.search.keyword.model.KeywordSaveReq;
import com.katri.web.search.keyword.model.KeywordSelectRes;
import com.katri.web.search.keyword.service.KeywordService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Api(tags = {"Keyword Controller"})
@RequestMapping(value = "/search")
@Slf4j
public class KeywordController {

	/** Keyword Service */
	private final KeywordService keywordService;

	/** 메시지 Component */
	private final MessageSource messageSource;

	/** 공통 Service */
	private final CommService commService;

	/*****************************************************
	 * Keyword 리스트 이동
	 * @param
	 * @return String
	*****************************************************/
	@GetMapping(value ={"/keyword/keywordList"})
	public String getKeyword() {
		return "/search/keyword/keywordList";
	}

	/*****************************************************
	 * Keyword 등록
	 * @param keywordSaveReq
	 * @return ResponseEntity<ResponseDto>
	*****************************************************/
	@PostMapping(value = {"/keyword/regKeyword"})
	public ResponseEntity<ResponseDto> regKeyword(@ModelAttribute KeywordSaveReq keywordData){
		//[[0]].반환할 정보들
		int result			= 0;
		Integer resultCode	= HttpStatus.BAD_REQUEST.value();
		String resultMsg	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주세요.

		//[[1]].
		try {
			result	= keywordService.regKeyword(keywordData);
			resultCode	= HttpStatus.OK.value();
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.insert", null, SessionUtil.getLocale());	//정상적으로 등록 하였습니다.

		} catch (CustomMessageException e) {
			resultCode	= HttpStatus.BAD_REQUEST.value();
			resultMsg	= commService.rtnMessageDfError(e.getMessage());

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		} catch(Exception e) {

			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
			   .data(result)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build(),
			   HttpStatus.OK
				);
	}

	/*****************************************************
	 * Keyword 리스트 조회
	 * @param
	 * @return ResponseEntity<ResponseDto>
	*****************************************************/
	@GetMapping(value = {"/keyword/getKeywordList"})
	public ResponseEntity<ResponseDto> getKeywordList(@RequestParam String srwrdTyCd) {
		//[[0]].반환할 정보들
		List<KeywordSelectRes> keywordList	= null;
		String resultMsg	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주세요.
		Integer resultCode	= HttpStatus.BAD_REQUEST.value();

		//[[1]].데이터 조회
		try {
			keywordList	= keywordService.getKeywordList(srwrdTyCd);
			resultCode	= HttpStatus.OK.value();
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); //정상적으로 조회 하였습니다.
		} catch (CustomMessageException e) {
			resultCode	= HttpStatus.BAD_REQUEST.value();
			resultMsg	= commService.rtnMessageDfError(e.getMessage());

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		} catch(Exception e) {
			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
			   .data(keywordList)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build(),
			   HttpStatus.OK
				);
	}

	/*****************************************************
	 * Keyword 사용중인 추천 검색어 카운트
	 * @param
	 * @return ResponseEntity<ResponseDto>
	*****************************************************/
	@GetMapping(value = {"/keyword/getUseYnCount"})
	public ResponseEntity<ResponseDto> getUseYnCount(@RequestParam String srwrdTyCd) {
		//[[0]].반환할 정보들
		int count			= 0;
		String resultMsg	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주세요.
		Integer resultCode	= HttpStatus.BAD_REQUEST.value();

		//[[1]].데이터 조회
		try {
			count		= keywordService.getUseYnCount(srwrdTyCd);
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); //정상적으로 조회 하였습니다.
			resultCode	= HttpStatus.OK.value();
		} catch (CustomMessageException e) {
			resultMsg	= commService.rtnMessageDfError(e.getMessage());
			resultCode	= HttpStatus.BAD_REQUEST.value();

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		} catch (Exception e) {
			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
			   .data(count)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build(),
			   HttpStatus.OK
				);
	}

	/*****************************************************
	 * Keyword 사용중인 추천 검색어 조회
	 * @param
	 * @return ResponseEntity<ResponseDto>
	*****************************************************/
	@PostMapping(value = {"/keyword/getUseYList"})
	public ResponseEntity<ResponseDto> getUseYList(@RequestParam String srwrdTyCd) {
		//[[0]].반환할 정보들
		List<KeywordSelectRes> usedList	= null;
		String resultMsg	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale());
		Integer resultCode	= HttpStatus.BAD_REQUEST.value();

		//[[1]].데이터 조회
		try {
			usedList	= keywordService.getUseYList(srwrdTyCd);
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); //정상적으로 조회 하였습니다.
			resultCode	= HttpStatus.OK.value();
		} catch (CustomMessageException e) {
			resultMsg	= commService.rtnMessageDfError(e.getMessage());
			resultCode	= HttpStatus.BAD_REQUEST.value();

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		} catch(Exception e) {
			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
			   .data(usedList)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build(),
			   HttpStatus.OK
				);
	}

	/*****************************************************
	 * Keyword 추천 검색어 수정
	 * @param keywordSaveReq
	 * @return ResponseEntity<ResponseDto>
	*****************************************************/
	@PostMapping(value = {"/keyword/updateKeyword"})
	public ResponseEntity<ResponseDto> updateKeyword(@ModelAttribute KeywordSaveReq updateKeywordData){
		//[[0]].반환할 정보들
		int result	= 0;
		String resultMsg	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale());
		Integer resultCode	= HttpStatus.BAD_REQUEST.value();

		//[[1]]. 수정 데이터 보내기
		try {
			result		= keywordService.updateKeyword(updateKeywordData);
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.update", null, SessionUtil.getLocale()); //정상적으로 수정하였습니다.
			resultCode	= HttpStatus.OK.value();
		} catch (CustomMessageException e) {
			resultMsg	= commService.rtnMessageDfError(e.getMessage());
			resultCode	= HttpStatus.BAD_REQUEST.value();

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		} catch (Exception e) {
			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
			   .data(result)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build(),
			   HttpStatus.OK
				);
	}




}

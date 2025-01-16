package com.katri.web.search.controller;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.CommonRes;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.service.CommService;
import com.katri.web.search.model.SearchSelectReq;
import com.katri.web.search.model.SearchSelectRes;
import com.katri.web.search.service.SearchService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
@Api(tags = {"search Controller"})
public class SearchController {

	private final CommService commService;

	private final MessageSource messageSource;

	private final SearchService searchService;

	/*****************************************************
	 * 통합 검색 페이지 이동
	 * @param model
	 * @param searchSelectReq
	 * @return
	*****************************************************/
	@GetMapping("/searchList")
	public String searchList(Model model, @ModelAttribute SearchSelectReq searchSelectReq) {
		model.addAttribute("search", searchSelectReq);

		return "/search/searchList";
	}

	/*****************************************************
	 * 통합 검색
	 * @param searchSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping("/getSearchList")
	public ResponseEntity<ResponseDto> getSearchList(@ModelAttribute SearchSelectReq searchSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 					= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 					= HttpStatus.BAD_REQUEST.value(); // 상태코드
		CommonRes commonRes 				= new CommonRes(); // 응답 객체
		SearchSelectRes searchSelectRes 	= new SearchSelectRes();

		try {
			// [[1]]. 데이터 조회
			searchSelectRes = searchService.getSearchList(searchSelectReq); // 통합 검색

			resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
			resultCode = HttpStatus.OK.value();

		} catch (CustomMessageException e) {
			e.printStackTrace();

			resultCode = HttpStatus.BAD_REQUEST.value();
			resultMsg = commService.rtnMessageDfError(e.getMessage()); // service에서 exception하면서 보낸 메시지

			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		} catch(Exception e) {
			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(searchSelectRes)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * 추천 키워드 조회
	 * @param searchSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping("/getRecKeywordList")
	public ResponseEntity<ResponseDto> getKeywordList(@ModelAttribute SearchSelectReq searchSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 					= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 					= HttpStatus.BAD_REQUEST.value(); // 상태코드
		List<String> recKeywordList 		= null;

		try {
			// [[1]]. 데이터 조회
			recKeywordList = searchService.getRecKeywordList(searchSelectReq); // 추천 검색어

			resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
			resultCode = HttpStatus.OK.value();

		} catch (CustomMessageException e) {
			e.printStackTrace();

			resultCode = HttpStatus.BAD_REQUEST.value();
			resultMsg = commService.rtnMessageDfError(e.getMessage()); // service에서 exception하면서 보낸 메시지

			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		} catch(Exception e) {
			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(recKeywordList)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

}

package com.katri.web.ctnt.faq.controller;

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
import com.katri.common.model.Common;
import com.katri.common.model.CommonRes;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.CommonUtil;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.service.CommService;
import com.katri.web.ctnt.faq.model.FaqSelectReq;
import com.katri.web.ctnt.faq.model.FaqSelectRes;
import com.katri.web.ctnt.faq.service.FaqService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/ctnt")
@Api(tags = {"faq Controller"})
public class FaqController {

	private final CommService commService;

	private final MessageSource messageSource;

	private final FaqService faqService;

	/*****************************************************
	 * FAQ 목록 페이지 이동
	 * @param model
	 * @param faqSelectReq
	 * @return String
	*****************************************************/
	@GetMapping("/faq/faqList")
	public String faqList(Model model, @ModelAttribute FaqSelectReq faqSelectReq) {
		model.addAttribute("faqSearchData", faqSelectReq); // 검색조건

		return "/ctnt/faq/faqList";
	}

	/*****************************************************
	 * FAQ 목록 조회
	 * @param faqSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping("/faq/getFaqList")
	public ResponseEntity<ResponseDto> getFaqList(@ModelAttribute FaqSelectReq faqSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 			= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 			= HttpStatus.BAD_REQUEST.value(); // 상태코드
		CommonRes commonRes 		= new CommonRes(); // 응답 객체
		int faqCnt					= 0;
		List<FaqSelectRes> faqList	= null; // 응답 데이터

		try {
			// [[1]]. Paging 변수 설정
			Common common = CommonUtil.setPageParams(faqSelectReq.getCurrPage(), faqSelectReq.getRowCount());
			faqSelectReq.setStartRow(common.getStartRow());
			faqSelectReq.setEndRow(common.getEndRow());

			// [[2]]. 데이터 조회
			faqCnt = faqService.getFaqCnt(faqSelectReq); //  FAQ 갯수 조회
			faqList = faqService.getFaqList(faqSelectReq); // FAQ 목록 조회

			resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
			resultCode = HttpStatus.OK.value();

			// [[3]]. 데이터 셋팅
			commonRes.setTotCnt(faqCnt);
			commonRes.setList(faqList);
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
				.data(commonRes)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * FAQ 게시글 조회
	 * @param faqSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping("/faq/getFaqCn")
	public ResponseEntity<ResponseDto> getFaqCn(@ModelAttribute FaqSelectReq faqSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 			= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 			= HttpStatus.BAD_REQUEST.value(); // 상태코드
		FaqSelectRes faqSelectRes	= null; // 응답 데이터

		try {
			// [[1]]. 데이터 조회
			faqSelectRes = faqService.getFaqCn(faqSelectReq); // FAQ 조회

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
				.data(faqSelectRes)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

}

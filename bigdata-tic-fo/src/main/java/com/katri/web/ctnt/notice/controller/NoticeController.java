package com.katri.web.ctnt.notice.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.katri.web.ctnt.notice.model.NoticeSelectReq;
import com.katri.web.ctnt.notice.model.NoticeSelectRes;
import com.katri.web.ctnt.notice.service.NoticeService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/ctnt")
@Api(tags = {"notice Controller"})
public class NoticeController {

	private final CommService commService;

	private final MessageSource messageSource;

	private final NoticeService noticeService;

	/*****************************************************
	 * 공지사항 목록 페이지 이동
	 * @param model
	 * @param noticeSelectReq
	 * @return String
	*****************************************************/
	@GetMapping("/notice/noticeList")
	public String noticeList(Model model, @ModelAttribute NoticeSelectReq noticeSelectReq) {
		model.addAttribute("noticeSearchData", noticeSelectReq); // 검색조건

		return "/ctnt/notice/noticeList";
	}

	/*****************************************************
	 * 공지사항 목록 조회
	 * @param noticeSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping("/notice/getNoticeList")
	public ResponseEntity<ResponseDto> getNoticeList(@ModelAttribute NoticeSelectReq noticeSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 					= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 					= HttpStatus.BAD_REQUEST.value(); // 상태코드
		CommonRes commonRes 				= new CommonRes(); // 응답 객체
		int noticeCnt						= 0;
		List<NoticeSelectRes> noticeList	= null; // 응답 데이터

		try {
			// [[1]]. Paging 변수 설정
			Common common = CommonUtil.setPageParams(noticeSelectReq.getCurrPage(), noticeSelectReq.getRowCount());
			noticeSelectReq.setStartRow(common.getStartRow());
			noticeSelectReq.setEndRow(common.getEndRow());

			// [[2]]. 데이터 조회
			noticeCnt = noticeService.getNoticeCnt(noticeSelectReq); // 공지사항 글 갯수 조회
			noticeList = noticeService.getNoticeList(noticeSelectReq); // 공지사항 목록 조회

			resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
			resultCode = HttpStatus.OK.value();

			// [[3]]. 데이터 셋팅
			commonRes.setTotCnt(noticeCnt);
			commonRes.setList(noticeList);
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
	 * 공지사항 상세 조회
	 * @param response
	 * @param model
	 * @param noticeSelectReq
	 * @return String
	 * @throws Exception
	*****************************************************/
	@GetMapping("/notice/noticeDetail")
	public String noticeDetail(HttpServletResponse response, Model model, @ModelAttribute NoticeSelectReq noticeSelectReq) throws Exception {
		/* 뒤로가기 방지 */
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.

		NoticeSelectRes noticeSelectRes = new NoticeSelectRes();

		// 데이터 조회
		noticeSelectRes = noticeService.getNoticeDetail(noticeSelectReq);

		// 데이터 세팅
		model.addAttribute("noticeSearchData", noticeSelectReq); // 검색조건
		model.addAttribute("noticeDetail", noticeSelectRes);

		return "/ctnt/notice/noticeDetail";
	}

}

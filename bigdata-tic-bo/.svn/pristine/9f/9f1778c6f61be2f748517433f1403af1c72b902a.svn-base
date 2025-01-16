package com.katri.web.ctnt.notice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.Common;
import com.katri.common.model.CommonRes;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.CommonUtil;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.service.CommService;
import com.katri.web.ctnt.notice.model.NoticeSaveReq;
import com.katri.web.ctnt.notice.model.NoticeSaveRes;
import com.katri.web.ctnt.notice.model.NoticeSelectReq;
import com.katri.web.ctnt.notice.model.NoticeSelectRes;
import com.katri.web.ctnt.notice.service.NoticeService;
import com.katri.web.system.code.model.CodeSelectRes;

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
	 * 콘텐츠 관리 > 공지사항 관리 : 목록 페이지 이동
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
	 * 공지사항 글 작성/수정 팝업
	 * @return String
	*****************************************************/
	@GetMapping("/notice/noticePopUp")
	public String noticePopUp() {

		return "/ctnt/notice/noticePopUp";
	}

	/*****************************************************
	 * 공지사항 분류 코드 조회
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping("/notice/getNoticeClfCd")
	public ResponseEntity<ResponseDto> getNoticeClfCd() throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 					= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 					= HttpStatus.BAD_REQUEST.value(); // 상태코드
		CommonRes commonRes 				= new CommonRes(); // 응답 객체
		List<CodeSelectRes> noticeClfCd		= null; // 응답 데이터

		try {
			// [[1]]. 데이터 조회
			noticeClfCd = noticeService.getNoticeClfCd();

			resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
			resultCode = HttpStatus.OK.value();

			// [[3]]. 데이터 셋팅
			commonRes.setList(noticeClfCd);
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
	 * 공지사항 작성/수정
	 * @param noticeSaveReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@PostMapping("/notice/saveNotice")
	public ResponseEntity<ResponseDto> insertNotice(HttpServletRequest request, @ModelAttribute NoticeSaveReq noticeSaveReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 	= HttpStatus.BAD_REQUEST.value(); // 상태코드
		CommonRes commonRes = new CommonRes(); // 응답 객체

		try {
			// [[1]]. 데이터 추가/수정
			if (noticeSaveReq.getNttSn() == null) {
				noticeService.insertNotice(request, noticeSaveReq); // 추가

				resultMsg = messageSource.getMessage("result-message.messages.common.message.insert", null, SessionUtil.getLocale()); // 정상적으로 등록 하였습니다.
				resultCode = HttpStatus.OK.value();
			} else {
				noticeService.updateNotice(request, noticeSaveReq); // 수정

				resultMsg = messageSource.getMessage("result-message.messages.common.message.update", null, SessionUtil.getLocale()); // 정상적으로 수정 하였습니다.
				resultCode = HttpStatus.OK.value();
			}
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
	 * 공지사항 단건 조회
	 * @param noticeSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping("/notice/getNoticeDetail")
	public ResponseEntity<ResponseDto> getNoticeDetail(@ModelAttribute NoticeSelectReq noticeSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 					= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 					= HttpStatus.BAD_REQUEST.value(); // 상태코드
		NoticeSelectRes noticeSelectRes 	= new NoticeSelectRes();

		try {
			// [[1]]. 데이터 조회
			noticeSelectRes = noticeService.getNoticeDetail(noticeSelectReq);

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
				.data(noticeSelectRes)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * 공지 메인 노출 여부 변경
	 * @param noticeSaveReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@PostMapping("/notice/chgNtcExpsrYn")
	public ResponseEntity<ResponseDto> chgExpsrYn(@ModelAttribute NoticeSaveReq noticeSaveReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 			= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 			= HttpStatus.BAD_REQUEST.value(); // 상태코드
		NoticeSaveRes noticeSaveRes	= new NoticeSaveRes();
		int count = 0;

		try {
			// [[1]]. 데이터 세팅
			NoticeSelectReq noticeSelectReq = new NoticeSelectReq();
			noticeSelectReq.setNttTyCd(noticeSaveReq.getNttTyCd());

			// 공지 노출 여부 'Y' 데이터 개수 조회
			count = noticeService.getNtcExpsrYnCnt(noticeSelectReq);
			// 공지 노출 여부 변경
			noticeSaveRes = noticeService.chgNtcExpsrYn(noticeSaveReq, count);

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
				.data(noticeSaveRes)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

}

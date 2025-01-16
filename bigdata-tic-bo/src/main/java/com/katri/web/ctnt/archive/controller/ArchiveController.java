package com.katri.web.ctnt.archive.controller;

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
import com.katri.web.ctnt.archive.model.ArchiveSaveReq;
import com.katri.web.ctnt.archive.model.ArchiveSelectReq;
import com.katri.web.ctnt.archive.model.ArchiveSelectRes;
import com.katri.web.ctnt.archive.service.ArchiveService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/ctnt")
@Api(tags = {"archive Controller"})
public class ArchiveController {

	private final CommService commService;

	private final MessageSource messageSource;

	private final ArchiveService archiveService;

	/*****************************************************
	 * 콘텐츠 관리 > 자료실 관리 : 목록 페이지 이동
	 * @param model
	 * @param archiveSelectReq
	 * @return String
	*****************************************************/
	@GetMapping("/archive/archiveList")
	public String archiveList(Model model, @ModelAttribute ArchiveSelectReq archiveSelectReq) {

		return "/ctnt/archive/archiveList";
	}

	/*****************************************************
	 * 자료실 목록 조회
	 * @param archiveSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping("/archive/getArchiveList")
	public ResponseEntity<ResponseDto> getArchiveList(@ModelAttribute ArchiveSelectReq archiveSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 					= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 					= HttpStatus.BAD_REQUEST.value(); // 상태코드
		CommonRes commonRes 				= new CommonRes(); // 응답 객체
		int archiveCnt						= 0;
		List<ArchiveSelectRes> archiveList	= null; // 응답 데이터

		try {
			// [[1]]. Paging 변수 설정
			Common common = CommonUtil.setPageParams(archiveSelectReq.getCurrPage(), archiveSelectReq.getRowCount());
			archiveSelectReq.setStartRow(common.getStartRow());
			archiveSelectReq.setEndRow(common.getEndRow());

			// [[2]]. 데이터 조회
			archiveCnt = archiveService.getArchiveCnt(archiveSelectReq); // 자료실 글 갯수 조회
			archiveList = archiveService.getArchiveList(archiveSelectReq); // 자료실 목록 조회

			resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
			resultCode = HttpStatus.OK.value();

			// [[3]]. 데이터 셋팅
			commonRes.setTotCnt(archiveCnt);
			commonRes.setList(archiveList);
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
	 * 자료실 글 작성/수정 팝업
	 * @return String
	*****************************************************/
	@GetMapping("/archive/archivePopUp")
	public String archivePopUp() {

		return "/ctnt/archive/archivePopUp";
	}

	/*****************************************************
	 * 자료실 단건 조회
	 * @param archiveSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping("/archive/getArchiveDetail")
	public ResponseEntity<ResponseDto> getNoticeDetail(@ModelAttribute ArchiveSelectReq archiveSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 					= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 					= HttpStatus.BAD_REQUEST.value(); // 상태코드
		ArchiveSelectRes archiveSelectRes 	= new ArchiveSelectRes();

		try {
			// [[1]]. 데이터 조회
			archiveSelectRes = archiveService.getArchiveDetail(archiveSelectReq);

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
				.data(archiveSelectRes)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * 자료실 작성/수정
	 * @param request
	 * @param noticeSaveReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@PostMapping("/archive/saveArchive")
	public ResponseEntity<ResponseDto> insertNotice(HttpServletRequest request, @ModelAttribute ArchiveSaveReq archiveSaveReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 	= HttpStatus.BAD_REQUEST.value(); // 상태코드
		CommonRes commonRes = new CommonRes(); // 응답 객체

		try {
			// [[1]]. 데이터 추가/수정
			if (archiveSaveReq.getNttSn() == null) {
				archiveService.insertArchive(request, archiveSaveReq); // 추가

				resultMsg = messageSource.getMessage("result-message.messages.common.message.insert", null, SessionUtil.getLocale()); // 정상적으로 등록 하였습니다.
				resultCode = HttpStatus.OK.value();
			} else {
				archiveService.updateArchive(request, archiveSaveReq); // 수정

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

}

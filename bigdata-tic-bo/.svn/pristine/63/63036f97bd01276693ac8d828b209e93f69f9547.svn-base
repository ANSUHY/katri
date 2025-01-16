package com.katri.web.system.code.controller;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.CommonRes;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.SessionUtil;
import com.katri.common.util.StringUtil;
import com.katri.web.comm.service.CommService;
import com.katri.web.system.code.model.CodeSaveReq;
import com.katri.web.system.code.model.CodeSelectReq;
import com.katri.web.system.code.model.CodeSelectRes;
import com.katri.web.system.code.service.CodeService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/system")
@Api(tags = {"code Controller"})
public class CodeController {

	private final CommService commService;

	private final MessageSource messageSource;

	private final CodeService codeService;

	/*****************************************************
	 * 시스템 관리 > 코드관리 페이지 이동
	 * @return String
	*****************************************************/
	@GetMapping(value = {"/code/codeList"})
	public String codeList() {

		return  "system/code/codeList";
	}

	/*****************************************************
	 * 시스템 관리 > 코드관리 : 공통그룹코드 목록 조회
	 * @param codeSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/code/getComnGrpCdList"})
	public ResponseEntity<ResponseDto> getComnGrpCdList(CodeSelectReq codeSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 					= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 					= HttpStatus.BAD_REQUEST.value(); // 상태코드
		CommonRes commonRes 				= new CommonRes(); // 응답 객체
		List<CodeSelectRes> comnGrpCdList	= null; // 응답 데이터

		try {
			// [[1]]. 데이터 조회
			comnGrpCdList = codeService.getComnGrpCdList(codeSelectReq); // 공통그룹코드 조회

			resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
			resultCode = HttpStatus.OK.value();

			// [[2]]. 데이터 셋팅
			commonRes.setList(comnGrpCdList);
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
	 * 시스템 관리 > 코드관리 : 공통그룹코드 단건 조회
	 * @param codeSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/code/getComnGrpCdDetail"})
	public ResponseEntity<ResponseDto> getComnGrpCdDetail(@ModelAttribute CodeSelectReq codeSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 			= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 			= HttpStatus.BAD_REQUEST.value(); // 상태코드
		CommonRes commonRes 		= new CommonRes(); // 응답 객체
		CodeSelectRes codeSelectRes = null; // 응답 데이터

		try {
			// [[1]]. 데이터 조회
			codeSelectRes = codeService.getComnGrpCdDetail(codeSelectReq.getComnGrpCd());

			resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
			resultCode = HttpStatus.OK.value();

			// [[2]]. 데이터 셋팅
			commonRes.setObject(codeSelectRes);
		} catch (CustomMessageException e) {
			e.printStackTrace();

			resultCode = HttpStatus.BAD_REQUEST.value();
			resultMsg = commService.rtnMessageDfError(e.getMessage()); // service에서 exception하면서 보낸 메시지

			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		} catch (Exception e) {
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
	 * 시스템 관리 > 코드관리 : 공통그룹코드 추가/수정
	 * @param codeSaveReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/code/saveComnGrpCd"})
	public ResponseEntity<ResponseDto> updateComnGrpCd(@ModelAttribute CodeSaveReq codeSaveReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 	= HttpStatus.BAD_REQUEST.value(); // 상태코드
		CommonRes commonRes = new CommonRes(); // 응답 객체
		int count = 0;

		try {
			// [[1]]. 데이터 추가/수정
			if ("".equals(StringUtil.nvl(codeSaveReq.getTargetComnGrpCd()))) {
				// [[2]]. 중복 체크
				// 2-1. 데이터 세팅
				CodeSelectReq codeSelectReq = new CodeSelectReq();
				codeSelectReq.setComnGrpCd(codeSaveReq.getComnGrpCd());

				// 2-2. 중복 데이터 조회
				count = codeService.countComnGrpCd(codeSelectReq);

				if (!(count > 0)) {
					codeService.insertComnGrpCd(codeSaveReq); // 추가

					resultMsg = messageSource.getMessage("result-message.messages.common.message.insert", null, SessionUtil.getLocale()); // 정상적으로 등록 하였습니다.
					resultCode = HttpStatus.OK.value();
				} else {
					Object[] args = { codeSaveReq.getComnGrpCd() };

					resultMsg = messageSource.getMessage("result-message.messages.common.message.data.saved", args, SessionUtil.getLocale()); // {0}은/는 이미 등록된 데이터입니다.
					resultCode = HttpStatus.BAD_REQUEST.value();
				}
			} else {
				codeService.updateComnGrpCd(codeSaveReq); // 수정

				resultMsg = messageSource.getMessage("result-message.messages.common.message.update", null, SessionUtil.getLocale()); // 정상적으로 수정 하였습니다.
				resultCode = HttpStatus.OK.value();
			}

		} catch (CustomMessageException e) {
			e.printStackTrace();

			resultCode = HttpStatus.BAD_REQUEST.value();
			resultMsg = commService.rtnMessageDfError(e.getMessage()); // service에서 exception하면서 보낸 메시지

			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		} catch (Exception e) {
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
	 * 시스템 관리 > 코드관리 : 공통코드 목록 조회
	 * @param codeSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/code/getComnCdList"})
	public ResponseEntity<ResponseDto> getComnCdList(CodeSelectReq codeSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 				= HttpStatus.BAD_REQUEST.value(); // 상태코드
		CommonRes commonRes 			= new CommonRes(); // 응답 객체
		List<CodeSelectRes> comnCdList	= null; // 응답 데이터

		try {
			// [[1]]. 데이터 조회
			comnCdList = codeService.getComnCdList(codeSelectReq); // 공통코드 조회

			resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
			resultCode = HttpStatus.OK.value();

			// [[2]]. 데이터 셋팅
			commonRes.setList(comnCdList);
		} catch (CustomMessageException e) {
			e.printStackTrace();

			resultCode = HttpStatus.BAD_REQUEST.value();
			resultMsg = commService.rtnMessageDfError(e.getMessage()); // service에서 exception하면서 보낸 메시지

			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		} catch (Exception e) {
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
	 * 시스템 관리 > 코드관리 : 공통코드 추가/수정 팝업
	 * @return String
	*****************************************************/
	@GetMapping(value = {"/code/codeAddPopUp"})
	public String codeAddPop() {

		return "system/code/codeAddPopUp";
	}

	/*****************************************************
	 * 시스템 관리 > 코드관리 : 공통코드 단건 조회
	 * @param codeSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/code/getComnCdDetail"})
	public ResponseEntity<ResponseDto> getComnCdDetail(@ModelAttribute CodeSelectReq codeSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 	= HttpStatus.BAD_REQUEST.value(); // 상태코드
		CommonRes commonRes = new CommonRes(); // 응답 객체
		CodeSelectRes codeSelectRes = new CodeSelectRes();

		try {
			// [[1]]. 데이터 조회
			codeSelectRes = codeService.getComnCdDetail(codeSelectReq);

			resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
			resultCode = HttpStatus.OK.value();

			// [[2]]. 데이터 셋팅
			commonRes.setObject(codeSelectRes);
		} catch (CustomMessageException e) {
			e.printStackTrace();

			resultCode = HttpStatus.BAD_REQUEST.value();
			resultMsg = commService.rtnMessageDfError(e.getMessage()); // service에서 exception하면서 보낸 메시지

			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		} catch (Exception e) {
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
	 * 공통코드 순서 변경
	 * @param CodeSaveReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/code/chgComnCdSeq"})
	public ResponseEntity<ResponseDto> chgComnCdSeq(@ModelAttribute CodeSaveReq codeSaveReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 	= HttpStatus.BAD_REQUEST.value(); // 상태코드
		int result = 0;

		try {
			result = codeService.chgComnCdSeq(codeSaveReq);

			if (result > 0) {
				resultMsg = messageSource.getMessage("result-message.messages.common.message.save", null, SessionUtil.getLocale());
				resultCode = HttpStatus.OK.value();
			} else {
				resultMsg = messageSource.getMessage("result-message.messages.common.message.save.error", null, SessionUtil.getLocale());
				resultCode = HttpStatus.BAD_REQUEST.value();
			}
		} catch (CustomMessageException e) {
			e.printStackTrace();

			resultCode = HttpStatus.BAD_REQUEST.value();
			resultMsg = commService.rtnMessageDfError(e.getMessage()); // service에서 exception하면서 보낸 메시지

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
				HttpStatus.OK);
	}

	/*****************************************************
	 * 공통코드 추가/수정
	 * @param codeSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@PostMapping(value= {"/code/saveComnCd"})
	public ResponseEntity<ResponseDto> saveComnCd(@ModelAttribute CodeSaveReq codeSaveReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 	= HttpStatus.BAD_REQUEST.value(); // 상태코드
		int result = 0;

		try {
			// [[1]]. 데이터 존재 확인
			// [[1-1]]. 데이터 세팅
			CodeSelectReq codeSelectReq = new CodeSelectReq();
			codeSelectReq.setComnGrpCd(codeSaveReq.getComnGrpCd());
			codeSelectReq.setComnCd(codeSaveReq.getTargetComnCd());

			// [[1-2]]. 데이터 조회
			result = codeService.countComnCd(codeSelectReq);

			// [[2]]. 데이터 추가/수정
			if (!(result > 0)) { // 등록된 데이터가 없는 경우 추가/수정
				if ("".equals(StringUtil.nvl(codeSaveReq.getTargetComnCd()))) {
					codeService.updateComnCd(codeSaveReq); // 수정

					resultMsg = messageSource.getMessage("result-message.messages.common.message.update", null, SessionUtil.getLocale()); // 정상적으로 수정 하였습니다.
					resultCode = HttpStatus.OK.value();
				} else {
					codeService.insertComnCd(codeSaveReq); // 추가

					resultMsg = messageSource.getMessage("result-message.messages.common.message.insert", null, SessionUtil.getLocale()); // 정상적으로 등록 하였습니다.
					resultCode = HttpStatus.OK.value();
				}
			} else { // 등록된 데이터가 이미 있는 경우
				Object[] args = { codeSaveReq.getTargetComnCd() };

				resultMsg = messageSource.getMessage("result-message.messages.common.message.data.saved", args, SessionUtil.getLocale());
				resultCode 	= HttpStatus.BAD_REQUEST.value();
			}
		} catch (CustomMessageException e) {
			e.printStackTrace();

			resultCode = HttpStatus.BAD_REQUEST.value();
			resultMsg = commService.rtnMessageDfError(e.getMessage()); // service에서 exception하면서 보낸 메시지

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
				HttpStatus.OK);
	}

}

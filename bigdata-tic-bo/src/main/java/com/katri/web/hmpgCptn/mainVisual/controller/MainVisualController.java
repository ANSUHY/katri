package com.katri.web.hmpgCptn.mainVisual.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.katri.common.model.CommonRes;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.service.CommService;
import com.katri.web.hmpgCptn.mainVisual.model.MainVisualSaveReq;
import com.katri.web.hmpgCptn.mainVisual.model.MainVisualSaveRes;
import com.katri.web.hmpgCptn.mainVisual.model.MainVisualSelectReq;
import com.katri.web.hmpgCptn.mainVisual.model.MainVisualSelectRes;
import com.katri.web.hmpgCptn.mainVisual.service.MainVisualService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Api(tags = {"mainVisual Controller"})
@RequestMapping(value = "/hmpgCptn")
@Slf4j
public class MainVisualController {

	/** 공통 Service */
	private final CommService commService;

	/** mainVisual Service */
	private final MainVisualService mainVisualService;

	/** 메시지 Component */
	private final MessageSource messageSource;

	/*****************************************************
	 * mainVisual 메인 비주얼 리스트 이동
	 * @param model
	 * @return String
	*****************************************************/
	@GetMapping(value = {"/mainVisual/mainVisualList"})
	public String mainVisualList(Model model) {

		return "hmpgCptn/mainVisual/mainVisualList";
	}

	/*****************************************************
	 * mainVisual 메인 비주얼 리스트 조회
	 * @param mainVisualSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/mainVisual/getMainVisualList"})
	public ResponseEntity<ResponseDto> getMainVisualList( @ModelAttribute MainVisualSelectReq mainVisualSelectReq ) throws Exception {

		// [[0]]. 반환할 정보들
		String 						msg 			= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer 					resultCode 		= HttpStatus.BAD_REQUEST.value();
		List<MainVisualSelectRes> 	mainVisualList 	= null;
		CommonRes 					commonRes 		= new CommonRes();

		// [[1]]. 데이터 조회
		/* 1-1. 메인 비주얼 목록 개수 조회 */
		Integer nTotCnt = mainVisualService.getMainVisualCount(mainVisualSelectReq);

		if(nTotCnt != 0) {

			/* 1-2. 메인 비주얼 리스트 조회 */
			mainVisualList = mainVisualService.getMainVisualList(mainVisualSelectReq);

			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.

		}else {

			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.no.data", null, SessionUtil.getLocale()); // 데이터가 없습니다.

		}

		// [[2]]. 데이터 셋팅
		commonRes.setTotCnt(nTotCnt);
		commonRes.setList(mainVisualList);

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(commonRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);

	}

	/*****************************************************
	 * mainVisual 메인 비주얼 상세 조회
	 * @param mainVisualSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/mainVisual/getMainVisualDetail"})
	public ResponseEntity<ResponseDto> getMainVisualDetail( @ModelAttribute MainVisualSelectReq mainVisualSelectReq ) throws Exception {

		// [[0]]. 반환할 정보들
		String 				msg 				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer 			resultCode 			= HttpStatus.BAD_REQUEST.value();
		MainVisualSelectRes	mainVisualSelectRes	= new MainVisualSelectRes();

		// [[1]]. 데이터 조회
		mainVisualSelectRes = mainVisualService.getAuthrtGrpBasDetail(mainVisualSelectReq);

		if( mainVisualSelectRes != null ) {
			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
		} else {
			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.no.data", null, SessionUtil.getLocale()); // 데이터가 없습니다.
		}

		// [[2]]. 데이터 셋팅
		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(mainVisualSelectRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * mainVisual 메인 비주얼 저장
	 * @param request
	 * @param mainVisualSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/mainVisual/saveMainVisual"})
	public ResponseEntity<ResponseDto> saveMainVisual(HttpServletRequest request, @ModelAttribute @Valid MainVisualSaveReq mainVisualSaveReq) throws Exception{

		// [[0]]. 반환할 정보들 & 변수 지정
		String 				msg 				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer				resultCode 			= HttpStatus.BAD_REQUEST.value();
		MainVisualSaveRes 	mainVisualSaveRes	= new MainVisualSaveRes();

		// [[1]]. 저장 및 수정
		Integer nHmpgCptnSn	= mainVisualSaveReq.getHmpgCptnSn();

		if( nHmpgCptnSn != null ) {

			/* 수정자 셋팅 */
			mainVisualSaveReq.setMdfrId(SessionUtil.getLoginMngrId());

			/* 메인 비주얼 수정 */
			mainVisualSaveRes = mainVisualService.updateMainVisual(request, mainVisualSaveReq);

		} else {

			/* 등록자 셋팅 */
			mainVisualSaveReq.setCrtrId(SessionUtil.getLoginMngrId());

			/* 메인 비주얼 등록 */
			mainVisualSaveRes = mainVisualService.insertMainVisual(request, mainVisualSaveReq);

		}

		if(mainVisualSaveRes.getHmpgCptnSn() == null) {
			resultCode = HttpStatus.BAD_REQUEST.value();
			msg = messageSource.getMessage("result-message.messages.common.message.save.error", null, SessionUtil.getLocale()); 	//저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		} else {
			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.save", null, SessionUtil.getLocale()); 		//정상적으로 저장 하였습니다.
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(mainVisualSaveRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build()
				, HttpStatus.OK);
	}

	/*****************************************************
	 * mainVisual 정렬 순서 저장
	 * @param request
	 * @param mainVisualSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/mainVisual/saveMainVisualSeq"})
	public ResponseEntity<ResponseDto> saveMainVisualSeq( HttpServletRequest request, @ModelAttribute MainVisualSaveReq mainVisualSaveReq) throws Exception {

		// [[0]]. 반환할 정보들
		String msg 				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		int	nCount				= 0;

		try {

			// [[1]]. 비주얼 정렬 저장 시작
			nCount = mainVisualService.saveMainVisualSeq(request, mainVisualSaveReq);

			if( nCount > 0 ) {
				resultCode = HttpStatus.OK.value();
				msg = messageSource.getMessage("result-message.messages.common.message.save", null, SessionUtil.getLocale()); 		//정상적으로 저장 하였습니다.
			} else {
				resultCode = HttpStatus.BAD_REQUEST.value();
				msg = messageSource.getMessage("result-message.messages.common.message.save.error", null, SessionUtil.getLocale()); //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}

		} catch(CustomMessageException e) {

			//값 셋팅
			resultCode = HttpStatus.BAD_REQUEST.value();
			msg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

		} catch(Exception e) {

			throw e;

		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(nCount)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);

	}

	/*****************************************************
	 * mainVisual 메인 비주얼 미리보기 팝업 호출
	 * @param model
	 * @return String
	*****************************************************/
	@GetMapping(value = {"/mainVisual/mainVisualPreviewPopUp"})
	public String mainVisualPreviewPopUp(Model model) {

		return "hmpgCptn/mainVisual/mainVisualPreviewPopUp";
	}

}

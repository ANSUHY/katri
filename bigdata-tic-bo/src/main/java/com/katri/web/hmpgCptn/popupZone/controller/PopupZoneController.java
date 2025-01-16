package com.katri.web.hmpgCptn.popupZone.controller;

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
import com.katri.web.hmpgCptn.popupZone.model.PopupZoneSaveReq;
import com.katri.web.hmpgCptn.popupZone.model.PopupZoneSaveRes;
import com.katri.web.hmpgCptn.popupZone.model.PopupZoneSelectReq;
import com.katri.web.hmpgCptn.popupZone.model.PopupZoneSelectRes;
import com.katri.web.hmpgCptn.popupZone.service.PopupZoneService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Api(tags = {"popupZone Controller"})
@RequestMapping(value = "/hmpgCptn")
@Slf4j
public class PopupZoneController {

	/** 공통 Service */
	private final CommService commService;

	/** popupZone Service */
	private final PopupZoneService popupZoneService;

	/** 메시지 Component */
	private final MessageSource messageSource;


	/*****************************************************
	 * popupZone 리스트 이동
	 * @param model
	 * @return String
	*****************************************************/
	@GetMapping(value={"/popupZone/popupZoneList"})
	public String getPopupList(Model model) {
		return "/hmpgCptn/popupZone/popupZoneList";
	}

	/*****************************************************
	 * popupZone 리스트 조회
	 * @param popupZoneSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping(value={"/popupZone/getPopupList"})
	public ResponseEntity<ResponseDto> getPopupList(@ModelAttribute PopupZoneSelectReq popupZoneSelectReq) {
		//[[0]].반환할 정보들
		String resultMsg					= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode					= HttpStatus.BAD_REQUEST.value();
		List<PopupZoneSelectRes> popupList	= null;
		CommonRes commonRes					= new CommonRes();

		//[[1]].데이터 조회
		/* 1-1. 팝업존 목록 개수 조회 */
		int pTotCnt	= popupZoneService.getPopupCount(popupZoneSelectReq);

		if(pTotCnt != 0) {
			/* 1-2. 팝업존 리스트 조회 */
			popupList	= popupZoneService.getPopupZoneList(popupZoneSelectReq);

			resultCode	= HttpStatus.OK.value();
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
		} else {
			resultCode	= HttpStatus.OK.value();
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.no.data", null, SessionUtil.getLocale()); // 데이터가 없습니다.
		}

		//[[2]]. 데이터 셋팅
		commonRes.setTotCnt(pTotCnt);
		commonRes.setList(popupList);

		return new ResponseEntity<>(
				ResponseDto.builder()
			   .data(commonRes)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build(),
			   HttpStatus.OK
				);
	}

	/*****************************************************
	 * 팝업존 상세 조회
	 * @param popupZoneSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/popupZone/getPopupDetail"})
	public ResponseEntity<ResponseDto> getPopupDetail(@ModelAttribute PopupZoneSelectReq popupZoneSelectReq) throws Exception{
		//[[0]]. 반환할 정보들
		String resultMsg	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode	= HttpStatus.BAD_REQUEST.value();
		PopupZoneSelectRes popupZoneSelectRes	= new PopupZoneSelectRes();

		//[[1]].데이터 조회
		popupZoneSelectRes	= popupZoneService.getPopupDetail(popupZoneSelectReq);

		if(popupZoneSelectRes != null) {
			resultCode	= HttpStatus.OK.value();
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
		} else {
			resultCode	= HttpStatus.OK.value();
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.no.dat", null, SessionUtil.getLocale()); // 데이터가 없습니다.
		}

		//[[2]].데이터 셋팅
		return new ResponseEntity<>(
				ResponseDto.builder()
			   .data(popupZoneSelectRes)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build(),
			   HttpStatus.OK
				);
	}

	/*****************************************************
	 * 팝업존 저장
	 * @param request
	 * @param popupZoneSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@PostMapping(value={"/popupZone/savePopup"})
	public ResponseEntity<ResponseDto> savePopup(HttpServletRequest request,@ModelAttribute @Valid PopupZoneSaveReq popupZoneSaveReq ) throws Exception{
		// [[0]]. 반환할 정보들 & 변수 지정
		String 				resultMsg 			= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer				resultCode 			= HttpStatus.BAD_REQUEST.value();
		PopupZoneSaveRes	popupZoneSaveRes		= new PopupZoneSaveRes();

		//[[1]]. 저장 및 수정
		Integer pHmpgCptnSn	= popupZoneSaveReq.getHmpgCptnSn();

		if( pHmpgCptnSn != null ) {

			/* 수정자 셋팅 */
			popupZoneSaveReq.setMdfrId(SessionUtil.getLoginMngrId());

			/* 팝업존 수정 */
			popupZoneSaveRes = popupZoneService.updatePopupZone(request, popupZoneSaveReq);
		} else {

			/* 등록자 셋팅 */
			popupZoneSaveReq.setCrtrId(SessionUtil.getLoginMngrId());

			/* 메인 팝업 등록 */
			popupZoneSaveRes = popupZoneService.insertMainPopup(request, popupZoneSaveReq);
		}

		if(popupZoneSaveRes.getHmpgCptnSn() == null) {
			resultCode	= HttpStatus.BAD_REQUEST.value();
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.save.error", null, SessionUtil.getLocale()); 	//저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		} else {
			resultCode	= HttpStatus.OK.value();
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.save", null, SessionUtil.getLocale()); 		//정상적으로 저장 하였습니다.
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
			   .data(popupZoneSaveRes)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build()
			   ,HttpStatus.OK
				);


	}

	/*****************************************************
	 * popupZone 정렬 순서 저장
	 * @param request
	 * @param popupZoneSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/popupZone/savePopupZoneSeq"})
	public ResponseEntity<ResponseDto> savePopupZoneSeq(HttpServletRequest request, @ModelAttribute PopupZoneSaveReq popupZoneSaveReq){
		//[[0]]. 반환할 정보들
		String resultMsg	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale());
		Integer resultCode	= HttpStatus.BAD_REQUEST.value();
		int pCount			= 0;

		//[[1]]. 팝업 정렬 시작
		try {
			pCount	= popupZoneService.savePopupReq(request, popupZoneSaveReq);

			if(pCount > 0) {
				resultCode	= HttpStatus.OK.value();
				resultMsg	= messageSource.getMessage("result-message.messages.common.message.save", null, SessionUtil.getLocale());//정상적으로 저장 하였습니다.
			} else {
				resultCode	= HttpStatus.BAD_REQUEST.value();
				resultMsg	= messageSource.getMessage("result-message.messages.common.message.save.error", null, SessionUtil.getLocale()); //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}
		} catch (CustomMessageException e) {
			//값 셋팅
			resultCode	= HttpStatus.BAD_REQUEST.value();
			resultMsg	= commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		} catch (Exception e) {
			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
			   .data(pCount)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build(),
			   HttpStatus.OK
				);

	}

	/*****************************************************
	 * popupZone 팝업존 미리 보기 팝업 호출
	 * @param model
	 * @return String
	*****************************************************/
	@GetMapping(value = {"/popupZone/PopupZonePreviewPopUp"})
	public String PopupZonePreviewPopUp(Model model) {
		return "/hmpgCptn/popupZone/popupZonePreviewPopUp";
	}

}

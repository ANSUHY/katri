package com.katri.web.trms.trms.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.CommonRes;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.service.CommService;
import com.katri.web.trms.trms.model.TrmsSaveReq;
import com.katri.web.trms.trms.model.TrmsSaveRes;
import com.katri.web.trms.trms.model.TrmsSelectReq;
import com.katri.web.trms.trms.model.TrmsSelectRes;
import com.katri.web.trms.trms.service.TrmsService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequiredArgsConstructor
@Api(tags = {"Trms Controller"})
@Slf4j
public class TrmsController {

	/** 공통 Service */
	private final CommService commService;

	/** 메시지 Component */
	private final MessageSource messageSource;

	/** trms Service */
	private final TrmsService trmsService;

	/*****************************************************
	 * [약관 및 개인정보처리방침 관리] 리스트_페이지
	 * @return String
	*****************************************************/
	@GetMapping(value = {"/trms/trms/trmsList"})
	public String trmsList() {

		return "/trms/trms/trmsList";

	}

	/*****************************************************
	 * [약관 및 개인정보처리방침 관리] 리스트 정보 조회
	 * @param trmsSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/trms/trms/getTrmsList"})
	public ResponseEntity<ResponseDto> getTrmsList( @ModelAttribute TrmsSelectReq trmsSelectReq) throws Exception {

		// [[0]]. 반환할 정보들
		String resultMsg 				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 				= HttpStatus.BAD_REQUEST.value();
		int trmsCnt						= 0;
		List<TrmsSelectRes> trmsList	= null;
		CommonRes commonRes 			= new CommonRes();

		try {

			// [[1]]. 데이터 조회
			/* 1-1. trms Cnt 정보*/
			trmsCnt = trmsService.getTrmsCnt(trmsSelectReq);

			if(trmsCnt != 0) {

				/* 1-2. trms List 정보*/
				trmsList = trmsService.getTrmsList(trmsSelectReq);

				resultCode = HttpStatus.OK.value();
				resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.

			}else {

				resultCode = HttpStatus.OK.value();
				resultMsg = messageSource.getMessage("result-message.messages.common.message.no.data", null, SessionUtil.getLocale()); // 데이터가 없습니다.

			}

			// [[3]]. 데이터 셋팅
			commonRes.setTotCnt(trmsCnt);
			commonRes.setList(trmsList);

		} catch(CustomMessageException e) {

			//값 셋팅
			resultCode = HttpStatus.BAD_REQUEST.value();
			resultMsg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
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
	 * [약관 및 개인정보처리방침 관리] 사용여부 change
	 * @param trmsSaveReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/trms/trms/updateChgTrmsUseYn"})
	public ResponseEntity<ResponseDto> updateChgTrmsUseYn(@ModelAttribute TrmsSaveReq trmsSaveReq) throws Exception {

		// [[0]]. 반환할 정보들 & 변수 지정
		String resultMsg 		= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		TrmsSaveRes trmsSaveRes = new TrmsSaveRes();

		try {

			/* 사용여부 수정 */
			trmsSaveRes = trmsService.updateChgTrmsUseYn(trmsSaveReq);

			resultCode = HttpStatus.OK.value();
			resultMsg  = messageSource.getMessage("result-message.messages.common.message.update", null, SessionUtil.getLocale()); 		//정상적으로 수정 하였습니다.

		} catch (CustomMessageException e) {

			//값 셋팅
			resultCode = HttpStatus.BAD_REQUEST.value();
			resultMsg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);

		} catch(Exception e) {

			throw e;

		}

		return new ResponseEntity<>(
			ResponseDto.builder()
			.data(trmsSaveRes)
			.resultMessage(resultMsg)
			.resultCode(resultCode)
			.build()
			, HttpStatus.OK);

	}

	/*****************************************************
	 * [약관 및 개인정보처리방침 관리] 등록,수정_PopUp 페이지
	 * @param response
	 * @param model
	 * @param trmsSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/trms/trms/trmsRegPopUp"})
	public String trmsRegPopUp(HttpServletResponse response, Model model,TrmsSelectReq trmsSelectReq){

		return "/trms/trms/trmsRegPopUp";

	}

	/*****************************************************
	 * [약관 및 개인정보처리방침 관리] 정보 조회
	 * @param trmsSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/trms/trms/getTrmsDetail"})
	public ResponseEntity<ResponseDto> getTrmsDetail( @ModelAttribute TrmsSelectReq trmsSelectReq) throws Exception {

		// [[0]]. 반환할 정보들
		String resultMsg 				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 				= HttpStatus.BAD_REQUEST.value();
		TrmsSelectRes trmsDetail		= null;

		try {

			// [[1]]. 데이터 조회
			trmsDetail = trmsService.getTrmsDetail(trmsSelectReq);

			resultCode = HttpStatus.OK.value();
			resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.

		} catch(CustomMessageException e) {

			//값 셋팅
			resultCode = HttpStatus.BAD_REQUEST.value();
			resultMsg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);

		} catch(Exception e) {

			throw e;

		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(trmsDetail)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);

	}

	/*****************************************************
	 * [약관 및 개인정보처리방침 관리] 등록 및 수정
	 * @param TrmsSaveReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/trms/trms/saveTrms"})
	public ResponseEntity<ResponseDto> saveTrms(@ModelAttribute TrmsSaveReq trmsSaveReq) throws Exception{

		// [[0]]. 반환할 정보들 & 변수 지정
		String resultMsg 		= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		TrmsSaveRes trmsSaveRes 	= new TrmsSaveRes();

		try {

			// [[1]]. 등록 및 수정
			if("U".equals(trmsSaveReq.getSaveType()) && trmsSaveReq.getTrmsSn() != null) {

				/* 수정 */
				trmsSaveRes = trmsService.updateTrms(trmsSaveReq);

				if(trmsSaveRes.getTrmsSn() == null) {
					resultCode = HttpStatus.BAD_REQUEST.value();
					resultMsg = messageSource.getMessage("result-message.messages.common.message.update.error", null, SessionUtil.getLocale()); 	//수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
				} else {
					resultCode = HttpStatus.OK.value();
					resultMsg = messageSource.getMessage("result-message.messages.common.message.update", null, SessionUtil.getLocale()); 		//정상적으로 수정 하였습니다.
				}

			} else {

				/* 등록 */
				trmsSaveRes = trmsService.insertTrms(trmsSaveReq);

				if(trmsSaveRes.getTrmsSn() == null) {
					resultCode = HttpStatus.BAD_REQUEST.value();
					resultMsg = messageSource.getMessage("result-message.messages.common.message.save.error", null, SessionUtil.getLocale()); //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
				} else {
					resultCode = HttpStatus.OK.value();
					resultMsg = messageSource.getMessage("result-message.messages.common.message.save", null, SessionUtil.getLocale()); 		//정상적으로 저장 하였습니다.
				}

			}
		} catch (CustomMessageException e) {

			//값 셋팅
			resultCode = HttpStatus.BAD_REQUEST.value();
			resultMsg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);

		} catch(Exception e) {

			throw e;

		}

		return new ResponseEntity<>(
			ResponseDto.builder()
			.data(trmsSaveRes)
			.resultMessage(resultMsg)
			.resultCode(resultCode)
			.build()
			, HttpStatus.OK);

	}


}

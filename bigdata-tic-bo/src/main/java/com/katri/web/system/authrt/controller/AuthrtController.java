package com.katri.web.system.authrt.controller;

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
import com.katri.web.system.authrt.model.AuthrtSaveReq;
import com.katri.web.system.authrt.model.AuthrtSaveRes;
import com.katri.web.system.authrt.model.AuthrtSelectReq;
import com.katri.web.system.authrt.model.AuthrtSelectRes;
import com.katri.web.system.authrt.service.AuthrtService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Api(tags = {"authrt Controller"})
@RequestMapping(value = "/system")
@Slf4j
public class AuthrtController {

	/** 공통 Service */
	private final CommService commService;

	/** Authrt Service */
	private final AuthrtService authrtService;

	/** 메시지 Component */
	private final MessageSource messageSource;


	/*****************************************************
	 * authrt 접속 권한 리스트 이동
	 * @param model
	 * @return String
	*****************************************************/
	@GetMapping(value = {"/authrt/authrtList"})
	public String authrtList(Model model) {

		return "system/authrt/authrtList";
	}

	/*****************************************************
	 * authrt 접속 권한 리스트 조회
	 * @param authrtSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/authrt/getAuthrtGrpBasList"})
	public ResponseEntity<ResponseDto> getAuthrtGrpBasList( @ModelAttribute AuthrtSelectReq authrtSelectReq ) throws Exception {

		// [[0]]. 반환할 정보들
		String 					msg 		= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer 				resultCode 	= HttpStatus.BAD_REQUEST.value();
		List<AuthrtSelectRes> 	authrtList 	= null;
		CommonRes 				commonRes 	= new CommonRes();

		// [[1]]. 데이터 조회
		/* 1-1. 권한 그룹 개수 조회 */
		Integer nTotCnt = authrtService.getAuthrtGrpBasCnt(authrtSelectReq);

		if(nTotCnt != 0) {

			/* 1-2. 권한 그룹 리스트 조회 */
			authrtList = authrtService.getAuthrtGrpBasList(authrtSelectReq);

			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.

		}else {

			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.no.data", null, SessionUtil.getLocale()); // 데이터가 없습니다.

		}

		// [[2]]. 데이터 셋팅
		commonRes.setTotCnt(nTotCnt);
		commonRes.setList(authrtList);

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(commonRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * (팝업) authrt 접속 권한 그룹 추가 팝업 호출
	 * @param model
	 * @return String
	*****************************************************/
	@GetMapping(value = {"/authrt/authrtGrpSavePopUp"})
	public String authrtSavePopUp(Model model) {

		return "system/authrt/authrtGrpSavePopUp";
	}

	/*****************************************************
	 * authrt 접속 권한 그룹 상세 조회
	 * @param authrtSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/authrt/getAuthrtGrpBasDetail"})
	public ResponseEntity<ResponseDto> getAuthrtGrpBasDetail( @ModelAttribute AuthrtSelectReq authrtSelectReq ) throws Exception {

		// [[0]]. 반환할 정보들
		String 			msg 				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer 		resultCode 			= HttpStatus.BAD_REQUEST.value();
		AuthrtSelectRes	authrtSelectRes	= new AuthrtSelectRes();

		// [[1]]. 데이터 조회
		authrtSelectRes = authrtService.getAuthrtGrpBasDetail(authrtSelectReq);

		if( authrtSelectRes != null ) {
			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
		} else {
			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.no.data", null, SessionUtil.getLocale()); // 데이터가 없습니다.
		}

		// [[2]]. 데이터 셋팅
		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(authrtSelectRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * authrt 접속 권한 메뉴 권한 팝업 호출
	 * @param model
	 * @return String
	*****************************************************/
	@GetMapping(value = {"/authrt/authrtMenuListPopUp"})
	public String authrtMenuListPopUp(Model model) {

		return "system/authrt/authrtMenuListPopUp";
	}

	/*****************************************************
	 * authrt 접속 권한 메뉴 권한 저장
	 * @param request
	 * @param authrtSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/authrt/saveAuthrtGrpBas"})
	public ResponseEntity<ResponseDto> saveAuthrtGrpBas( HttpServletRequest request, @ModelAttribute @Valid AuthrtSaveReq authrtSaveReq ) throws Exception {

		// [[0]]. 반환할 정보들
		String 			msg 			= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer 		resultCode 		= HttpStatus.BAD_REQUEST.value();
		AuthrtSaveRes	authrtSaveRes	= new AuthrtSaveRes();

		try {

			// [[1]]. 저장 시작
			Integer nAuthrtGrpSn = authrtSaveReq.getAuthrtGrpSn();

			if( nAuthrtGrpSn != null ) {

				/* 수정자 셋팅 */
				authrtSaveReq.setMdfrId(SessionUtil.getLoginMngrId());

				/* 권한 그룹 수정 */
				authrtSaveRes = authrtService.updateAuthGrpBas(authrtSaveReq);

			} else {

				/* 등록자 셋팅 */
				authrtSaveReq.setCrtrId(SessionUtil.getLoginMngrId());

				/* 권한 그룹 등록 */
				authrtSaveRes = authrtService.insertAuthGrpBas(authrtSaveReq);

			}

			/* 성공 여부에 따른 메시지 셋팅 */
			if( authrtSaveRes.getAuthrtGrpSn() == null ) {
				resultCode = HttpStatus.BAD_REQUEST.value();
				msg = messageSource.getMessage("result-message.messages.common.message.save.error", null, SessionUtil.getLocale()); //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			} else {
				resultCode = HttpStatus.OK.value();
				msg = messageSource.getMessage("result-message.messages.common.message.save", null, SessionUtil.getLocale()); 		//정상적으로 저장 하였습니다.
			}

		} catch(CustomMessageException e) {

			//값 셋팅
			resultCode	= HttpStatus.BAD_REQUEST.value();
			msg 		= commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

		} catch(Exception e) {

			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(authrtSaveRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);

	}

	/*****************************************************
	 * authrt 메뉴 별 접속 권한 리스트 조회
	 * @param authrtSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/authrt/getAuthrtGrpMenuList"})
	public ResponseEntity<ResponseDto> getAuthrtGrpMenuList( @ModelAttribute AuthrtSelectReq authrtSelectReq ) throws Exception {

		// [[0]]. 반환할 정보들
		String 					msg 				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer 				resultCode 			= HttpStatus.BAD_REQUEST.value();
		List<AuthrtSelectRes> 	authrtGrpMenuList 	= null;
		CommonRes 				commonRes 			= new CommonRes();

		// [[1]]. 데이터 조회
		/* 1-1. 메뉴 별 접속 권한 리스트 조회 */
		authrtGrpMenuList = authrtService.getAuthrtGrpMenuList(authrtSelectReq);

		resultCode = HttpStatus.OK.value();
		msg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.

		// [[2]]. 데이터 셋팅
		commonRes.setList(authrtGrpMenuList);

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(commonRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}


	/*****************************************************
	 * 메뉴 별 접속 권한 저장 ( 삭제 > 재등록 )
	 * @param request
	 * @param authrtSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/authrt/saveAuthrtGrpMenu"})
	public ResponseEntity<ResponseDto> saveAuthrtGrpMenu( HttpServletRequest request, @ModelAttribute AuthrtSaveReq authrtSaveReq ) throws Exception {

		// [[0]]. 반환할 정보들
		String 			msg 			= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer 		resultCode 		= HttpStatus.BAD_REQUEST.value();
		AuthrtSaveRes	authrtSaveRes	= new AuthrtSaveRes();

		try {

			// [[1]]. 저장 시작
			Integer nAuthrtGrpSn = authrtSaveReq.getAuthrtGrpSn();

			if( nAuthrtGrpSn != null ) {
				authrtSaveRes = authrtService.saveAuthrtGrpMenu(authrtSaveReq);
			}

			/* 성공 여부에 따른 메시지 셋팅 */
			if( authrtSaveRes.getArrAuthrtGrpMenuSn() == null ) {
				resultCode = HttpStatus.BAD_REQUEST.value();
				msg = messageSource.getMessage("result-message.messages.common.message.save.error", null, SessionUtil.getLocale()); //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			} else {
				resultCode = HttpStatus.OK.value();
				msg = messageSource.getMessage("result-message.messages.common.message.save", null, SessionUtil.getLocale()); 		//정상적으로 저장 하였습니다.
			}

		} catch(CustomMessageException e) {

			//값 셋팅
			resultCode	= HttpStatus.BAD_REQUEST.value();
			msg 		= commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

		} catch(Exception e) {

			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(authrtSaveRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);

	}


}

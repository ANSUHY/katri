package com.katri.web.user.user.controller;

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

import com.katri.common.Const;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.Common;
import com.katri.common.model.CommonRes;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.CommonUtil;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.service.CommService;
import com.katri.web.user.user.model.UserSaveReq;
import com.katri.web.user.user.model.UserSaveRes;
import com.katri.web.user.user.model.UserSelectReq;
import com.katri.web.user.user.model.UserSelectRes;
import com.katri.web.user.user.service.UserService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Api(tags = {"user Controller"})
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

	/** 공통 Service */
	private final CommService commService;

	/** User Service */
	private final UserService userService;

	/** 메시지 Component */
	private final MessageSource messageSource;

	/*****************************************************
	 * user 회원 현황 목록 이동
	 * @param model
	 * @return String
	*****************************************************/
	@GetMapping(value = {"/user/userList"})
	public String userList(Model model) {

		return "user/user/userList";
	}

	/*****************************************************
	 * user 회원 현황 목록 조회
	 * @param userSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/user/getUserBasList"})
	public ResponseEntity<ResponseDto> getUserList( @ModelAttribute UserSelectReq userSelectReq ) throws Exception {

		// [[0]]. 반환할 정보들
		String 					msg 		= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer 				resultCode 	= HttpStatus.BAD_REQUEST.value();
		List<UserSelectRes> 	userList 	= null;
		CommonRes 				commonRes 	= new CommonRes();

		// [[1]]. Paging 변수 설정
		Common common = CommonUtil.setPageParams(userSelectReq.getCurrPage(), userSelectReq.getRowCount());
		userSelectReq.setStartRow(common.getStartRow());
		userSelectReq.setEndRow(common.getEndRow());

		// [[2]]. 데이터 조회
		/* 2-1. 회원 현황 목록 개수 조회 */
		Integer nTotCnt = userService.getUserBasCnt(userSelectReq);

		if( nTotCnt > 0 ) {

			/* 2-2. 회원 현황 목록 조회 */
			userList = userService.getUserBasList(userSelectReq);

			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.

		} else {

			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.no.data", null, SessionUtil.getLocale()); // 데이터가 없습니다.

		}

		// [[3]]. 데이터 셋팅
		commonRes.setTotCnt(nTotCnt);
		commonRes.setList(userList);

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(commonRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * user 회원 현황 > 회원 유형 별 상세 팝업 호출
	 * @param strUserTypeCd
	 * @return String
	*****************************************************/
	@PostMapping(value = {"/user/userDetailPopUp"})
	public String userDetailPopUp( @ModelAttribute UserSelectReq userSelectReq ) {

		/* 0. 기본페이지 셋팅 : 일반 회원 상세 */
		String strPopupUrl 	= "";

		String strUserTypeCd = userSelectReq.getTargetUserTypeCd().toUpperCase();

		/* 1. 기본페이지 셋팅 : 기관 / 기업 / 일반 */
		if( !"".equals(strUserTypeCd) ) {

			if( Const.Code.UserTyCd.INST_MASTER.equals(strUserTypeCd) || Const.Code.UserTyCd.INST_GENERAL.equals(strUserTypeCd) ) {

				/* 1_1. 기관 회원 */
				strPopupUrl = "user/user/userInstDetailPopUp";

			} else if( Const.Code.UserTyCd.ENT_MASTER.equals(strUserTypeCd) || Const.Code.UserTyCd.ENT_GENERAL.equals(strUserTypeCd) ) {

				/* 1_2. 기업 회원 */
				strPopupUrl = "user/user/userEntDetailPopUp";

			} else {

				/* 1_2. 일반 회원 */
				strPopupUrl = "user/user/userGnrlDetailPopUp";

			}
		}

		return strPopupUrl;

	}

	/*****************************************************
	 * user 회원 상세 조회
	 * @param userSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/user/getUserBasDetail"})
	public ResponseEntity<ResponseDto> getUserBasDetail( @ModelAttribute UserSelectReq userSelectReq ) throws Exception {

		// [[0]]. 반환할 정보들
		String 			msg 			= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer 		resultCode 		= HttpStatus.BAD_REQUEST.value();
		UserSelectRes 	userSelectRes 	= new UserSelectRes();

		// [[1]]. 데이터 조회
		userSelectRes = userService.getUserBasDetail(userSelectReq);

		if( userSelectRes != null ) {
			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
		} else {
			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.no.data", null, SessionUtil.getLocale()); // 데이터가 없습니다.
		}

		// [[2]]. 데이터 셋팅
		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(userSelectRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * user 회원 접속 로그 팝업 호출
	 * @param userSelectReq
	 * @return String
	*****************************************************/
	@GetMapping(value = {"/user/userLogHistPopUp"})
	public String userLogHistPopUp( @ModelAttribute UserSelectReq userSelectReq ) {

		return "/user/user/userLogHistPopUp";
	}

	/*****************************************************
	 * user 회원 접속 로그 목록 조회
	 * @param userSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/user/getUserLogHistList"})
	public ResponseEntity<ResponseDto> getUserLogHistList( @ModelAttribute UserSelectReq userSelectReq ) throws Exception {

		// [[0]]. 반환할 정보들
		String 					msg 		= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer 				resultCode 	= HttpStatus.BAD_REQUEST.value();
		List<UserSelectRes> 	logHistList = null;
		CommonRes 				commonRes 	= new CommonRes();

		// [[1]]. Paging 변수 설정
		Common common = CommonUtil.setPageParams(userSelectReq.getCurrPage(), userSelectReq.getRowCount());
		userSelectReq.setStartRow(common.getStartRow());
		userSelectReq.setEndRow(common.getEndRow());

		// [[2]]. 데이터 조회
		/* 2-1. 회원 접속 로그 목록 개수 조회 */
		Integer nTotCnt = userService.getUserLogHistCnt(userSelectReq);

		if( nTotCnt > 0 ) {

			/* 2-2. 회원 접속 로그 목록 조회 */
			logHistList = userService.getUserLogHistList(userSelectReq);

			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.

		} else {

			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.no.data", null, SessionUtil.getLocale()); // 데이터가 없습니다.

		}

		// [[3]]. 데이터 셋팅
		commonRes.setTotCnt(nTotCnt);
		commonRes.setList(logHistList);

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(commonRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * user 회원 약관 동의 이력 팝업 호출
	 * @param userSelectReq
	 * @return String
	*****************************************************/
	@GetMapping(value = {"/user/userTrmsAgreHistPopUp"})
	public String userTrmsAgreHistPopUp( @ModelAttribute UserSelectReq userSelectReq ) {

		return "/user/user/userTrmsAgreHistPopUp";
	}

	/*****************************************************
	 * user 회원 약관 동의 이력 목록 조회
	 * @param userSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/user/getUserTrmsAgreHistList"})
	public ResponseEntity<ResponseDto> getUserTrmsAgreHistList( @ModelAttribute UserSelectReq userSelectReq ) throws Exception {

		// [[0]]. 반환할 정보들
		String 					msg 		 = messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer 				resultCode 	 = HttpStatus.BAD_REQUEST.value();
		List<UserSelectRes> 	trmsHistList = null;
		CommonRes 				commonRes 	 = new CommonRes();

		// [[1]]. 데이터 조회
		/* 1-1. 회원 약관 동의 이력 목록 개수 조회 */
		Integer nTotCnt = userService.getUserTrmsAgreHistCnt(userSelectReq);

		if( nTotCnt > 0 ) {

			/* 1-2. 회원 약관 동의 이력 목록 조회 */
			trmsHistList = userService.getUserTrmsAgreHistList(userSelectReq);

			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.

		} else {

			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.no.data", null, SessionUtil.getLocale()); // 데이터가 없습니다.

		}

		// [[2]]. 데이터 셋팅
		commonRes.setTotCnt(nTotCnt);
		commonRes.setList(trmsHistList);

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(commonRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * user 회원 비밀번호 초기화 팝업 호출
	 * @param userSelectReq
	 * @return String
	*****************************************************/
	@GetMapping(value = {"/user/userPwdChgPopUp"})
	public String userPwdChgPopUp( @ModelAttribute UserSelectReq userSelectReq ) {

		return "/user/user/userPwdChgPopUp";
	}

	/*****************************************************
	 * user 회원 비밀번호 초기화 저장
	 * @param userSelectReq
	 * @return String
	*****************************************************/
	@PostMapping(value = {"/user/saveUserPwdChg"})
	public ResponseEntity<ResponseDto> saveUserPwdChg( HttpServletRequest request, @ModelAttribute UserSaveReq userSaveReq ) throws Exception {

		// [[0]]. 반환할 정보들
		String 			msg 			= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer 		resultCode 		= HttpStatus.BAD_REQUEST.value();
		UserSaveRes		UserSaveRes		= new UserSaveRes();

		try {

			// [[1]]. 저장 시작
			String strUserId = userSaveReq.getTargetUserId();

			if( !"".equals(strUserId) ) {

				/* 수정자 셋팅 */
				userSaveReq.setMdfrId(SessionUtil.getLoginMngrId());

				/* 비밀번호 초기화 시작 */
				UserSaveRes = userService.saveUserPwdChg(userSaveReq);

			}

			// [[2]]. 성공 여부에 따른 메시지 셋팅 */
			if( "".equals( UserSaveRes.getUserId() ) ) {
				resultCode = HttpStatus.BAD_REQUEST.value();
				msg = messageSource.getMessage("result-message.messages.common.message.save.error", null, SessionUtil.getLocale()); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			} else {
				resultCode = HttpStatus.OK.value();
				msg = messageSource.getMessage("result-message.messages.common.message.save", null, SessionUtil.getLocale()); 		// 정상적으로 저장 하였습니다.
			}

		} catch(CustomMessageException e) {

			// 값 셋팅
			resultCode	= HttpStatus.BAD_REQUEST.value();
			msg 		= commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			// 로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

		} catch(Exception e) {

			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(UserSaveRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * user 회원 담당자 정보 저장
	 * @param userSelectReq
	 * @return String
	*****************************************************/
	@PostMapping(value = {"/user/saveUserInfo"})
	public ResponseEntity<ResponseDto> saveUserInfo( HttpServletRequest request, @ModelAttribute UserSaveReq userSaveReq ) throws Exception {

		// [[0]]. 반환할 정보들
		String 			msg 			= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer 		resultCode 		= HttpStatus.BAD_REQUEST.value();
		UserSaveRes		UserSaveRes		= new UserSaveRes();

		try {

			// [[1]]. 저장 시작
			String strTargetUserId	= userSaveReq.getTargetUserId();

			if( !"".equals(strTargetUserId) ) {

				/* 수정자 셋팅 */
				userSaveReq.setMdfrId(SessionUtil.getLoginMngrId());

				/* 회원 담당자 정보 저장 */
				UserSaveRes = userService.saveUserInfo(userSaveReq);

			}

			// [[2]]. 성공 여부에 따른 메시지 셋팅 */
			if( "".equals( UserSaveRes.getUserId() ) ) {
				resultCode = HttpStatus.BAD_REQUEST.value();
				msg = messageSource.getMessage("result-message.messages.common.message.save.error", null, SessionUtil.getLocale()); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			} else {
				resultCode = HttpStatus.OK.value();
				msg = messageSource.getMessage("result-message.messages.common.message.save", null, SessionUtil.getLocale()); 		// 정상적으로 저장 하였습니다.
			}

		} catch(CustomMessageException e) {

			// 값 셋팅
			resultCode	= HttpStatus.BAD_REQUEST.value();
			msg 		= commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			// 로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

		} catch(Exception e) {

			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(UserSaveRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * user 회원 상태 승인/반려 처리
	 * @param userSelectReq
	 * @return String
	 *****************************************************/
	@PostMapping(value = {"/user/saveUserSttAppr"})
	public ResponseEntity<ResponseDto> saveUserSttAppr( HttpServletRequest request, @ModelAttribute UserSaveReq userSaveReq ) throws Exception {

		// [[0]]. 반환할 정보들
		String 			msg 			= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer 		resultCode 		= HttpStatus.BAD_REQUEST.value();
		UserSaveRes		UserSaveRes		= new UserSaveRes();

		try {

			// [[1]]. 저장 시작
			UserSaveRes = userService.saveUserSttAppr(request, userSaveReq);

			// [[2]]. 성공 여부에 따른 메시지 셋팅 */
			if( "".equals( UserSaveRes.getUserId() ) ) {
				resultCode = HttpStatus.BAD_REQUEST.value();
				msg = messageSource.getMessage("result-message.messages.common.message.save.error", null, SessionUtil.getLocale()); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			} else {
				resultCode = HttpStatus.OK.value();
				msg = messageSource.getMessage("result-message.messages.common.message.save", null, SessionUtil.getLocale()); 		// 정상적으로 저장 하였습니다.
			}

		} catch(CustomMessageException e) {

			// 값 셋팅
			resultCode	= HttpStatus.BAD_REQUEST.value();
			msg 		= commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			// 로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

		} catch(Exception e) {

			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(UserSaveRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}




}

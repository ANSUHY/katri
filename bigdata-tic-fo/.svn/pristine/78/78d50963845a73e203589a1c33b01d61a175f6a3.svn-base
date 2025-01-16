package com.katri.web.auth.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.CommonUtil;
import com.katri.common.util.SessionUtil;
import com.katri.web.auth.model.LoginReq;
import com.katri.web.auth.model.LoginRes;
import com.katri.web.auth.model.TryLoginUserSelectRes;
import com.katri.web.auth.service.LoginService;
import com.katri.web.comm.service.CommService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Api(tags = {"login Controller"})
@Slf4j
public class LoginController {

	/** 공통 Service */
	private final CommService commService;

	/** 메시지 Component */
	private final MessageSource messageSource;

	/** Login Service */
	private final LoginService loginService;

	/*****************************************************
	 * [로그인] 페이지
	 * @return String 로그인 페이지
	*****************************************************/
	@GetMapping(value = {"/auth/login"})
	public String login() {

		SessionUtil.removeLoginSession();

		return "/auth/login";
	}

	/*****************************************************
	 * 로그인 처리
	 * @param request 요청객체
	 * @param loginReq 로그인할 사람의 정보
	 * @return ResponseEntity<ResponseDto> 로그인 정보
	*****************************************************/
	@PostMapping(value = {"/auth/processLogin"})
	public ResponseEntity<ResponseDto> processLogin(HttpServletRequest request, @ModelAttribute @Valid LoginReq loginReq) throws Exception {

		// [[0]]. 반환할 정보들 & 변수 지정
		String  resultMsg 			= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 			= HttpStatus.BAD_REQUEST.value();
		LoginRes loginDetail 		= new LoginRes();

		try {

			//[[1]]. login 처리
			loginDetail = loginService.processLogin(request, loginReq);

			//[[2]]. 값 셋팅
			resultCode = HttpStatus.OK.value();
			resultMsg = "로그인 프로세스 탐";

		} catch(Exception e) {

			throw e;

		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(loginDetail)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * [5회 입력 실패로 잠금 상태] 페이지
	 * @return String [5회 입력 실패로 잠금 상태] 페이지
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/auth/overFailCntInfo"})
	public String overFailCntInfo(HttpServletResponse response, Model model, @RequestParam String loginId) throws Exception {

		TryLoginUserSelectRes tryLoginUserDetail = null;

		try {

			// 데이터 조회
			tryLoginUserDetail = loginService.selectTryLoginUserDetail(loginId);

		} catch (CustomMessageException e) {

			String msg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

			//ALERT창띄우면서 로그인페이지로 보내기
			CommonUtil.moveUrlAlertMsg(response, msg ,"/auth/login");

		} catch(Exception e) {

			throw e;

		}

		// 데이터 셋팅
		model.addAttribute("tryLoginUserDetail"	, tryLoginUserDetail);

		return "/auth/overFailCntInfo";
	}

	/*****************************************************
	 * [상태코드 : [가입 신청]일 경우] 페이지
	 * @return String [상태코드 : [가입 신청]일 경우] 페이지
	*****************************************************/
	@PostMapping(value = {"/auth/joinApplyInfo"})
	public String joinApplyInfo() {

		return "/auth/joinApplyInfo";
	}

	/*****************************************************
	 * [상태코드 : [탈퇴 신청]일 경우] 페이지
	 * @return String [상태코드 : [탈퇴 신청]일 경우] 페이지
	*****************************************************/
	@PostMapping(value = {"/auth/wIthdrawalApplyInfo"})
	public String wIthdrawalApplyInfo() {

		return "/auth/wIthdrawalApplyInfo";
	}

	/*****************************************************
	 * 로그 아웃 처리
	 * @param request
	 * @return
	*****************************************************/
	@GetMapping(value = {"/auth/logout"})
	public String logout(HttpServletRequest request) {

		SessionUtil.removeLoginSession();

		return "redirect:/";
	}

}

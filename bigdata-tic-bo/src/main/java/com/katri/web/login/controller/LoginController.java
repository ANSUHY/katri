package com.katri.web.login.controller;


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

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.model.LayoutReq;
import com.katri.web.comm.service.CommService;
import com.katri.web.login.model.LoginReq;
import com.katri.web.login.model.LoginRes;
import com.katri.web.login.service.LoginService;

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
	 * 로그인 화면
	 * @param layoutReq 공통레이아웃객체
	 * @param model
	 * @return String 로그인 페이지
	*****************************************************/
	@GetMapping(value = {"/login/login"})
	public String login(LayoutReq layoutReq, Model model) {

		// 공통레이아웃객체 set
		model.addAttribute(layoutReq);

		return "/login/login";
	}

	/*****************************************************
	 * 로그인 처리
	 * @param request 요청객체
	 * @param loginReq 로그인할 사람의 정보
	 * @return ResponseEntity<ResponseDto> 로그인 정보
	*****************************************************/
	@PostMapping(value = {"/login/processLogin"})
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
			resultMsg = "정상적으로 로그인 됨";

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
				.data(loginDetail)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * 로그 아웃 처리
	 * @param request
	 * @return
	*****************************************************/
	@GetMapping(value = {"/logout"})
	public String logout(HttpServletRequest request) {

		SessionUtil.removeLoginSession();

		return "redirect:/login/login";
	}

}

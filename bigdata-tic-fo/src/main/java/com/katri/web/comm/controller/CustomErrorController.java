package com.katri.web.comm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 공통 업무</li>
 * <li>서브 업무명 : 공통</li>
 * <li>설	   명 : 공통 API Controller</li>
 * <li>작   성  자 : Lee Han Seong</li>
 * <li>작   성  일 : 2021. 01. 18.</li>
 * </ul>
 * <pre>
 * ======================================
 * 변경자/변경일 :
 * 변경사유/내역 :
 * ======================================
 * </pre>
 ***************************************************/
@Controller
@RequiredArgsConstructor
@Api(tags = {"공통 관련 API"})
public class CustomErrorController implements ErrorController {

	/** 메시지 Component */
	//private final MessageSource messageSource;

	@GetMapping("/error")
	public String handleError(HttpServletResponse response, HttpServletRequest request) {
		/*
		String msg = messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		CommonUtil.alertMsgBack(response, msg);
		*/
		return "common/error/error";
	}

	@Override
	public String getErrorPath() {
		return null;
	}


}
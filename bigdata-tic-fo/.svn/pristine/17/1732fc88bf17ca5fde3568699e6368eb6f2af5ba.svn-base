package com.katri.web.comm.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.CommonUtil;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.mapper.CommMapper;
import com.katri.web.comm.model.TermsSelectRes;
import com.katri.web.comm.model.UserLogHistSaveReq;
import com.katri.web.comm.service.CommService;
import com.katri.web.comm.service.NiceService;
import com.katri.web.comm.service.TermsService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@Api(tags = {"공통 Controller"})
@Validated
public class CommController {

	/** 공통 서비스 Service */
	@SuppressWarnings("unused")
	private final CommService commService;

	/** comm Mapper */
	private final CommMapper commMapper;

	/** 메시지 Component */
	private final MessageSource messageSource;

	/** terms Service */
	private final TermsService termsService;

	/** Nice Service */
	private final NiceService niceService;

	/*****************************************************
	 * 사용자로그이력 저장
	*****************************************************/
	@PostMapping(value = {"/comm/addUserLogHist"})
	@ResponseBody
	public void addUserLogHist(HttpServletRequest request, @ModelAttribute UserLogHistSaveReq userLogHistSaveReq) {

		//로그데이터 셋팅
		userLogHistSaveReq.setUserIpAddr(CommonUtil.getClientIP(request)); //ip주소
		userLogHistSaveReq.setCrtrId(SessionUtil.getLoginUserId()); //로그인한 사용자
		try {
			userLogHistSaveReq.setLogUrlPrmtrCn(URLDecoder.decode(userLogHistSaveReq.getLogUrlPrmtrCn(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}

		//로그에 쌓기
		commMapper.insertUserLogHist(userLogHistSaveReq);

	}

	/*****************************************************
	 * 레이아웃 하단 이용약관/개인정보 처리방침 조회
	*****************************************************/
	@PostMapping("/comm/terms/getKatriTerms")
	public ResponseEntity<ResponseDto> getKatriTerms(@RequestParam String trmsTyCd) {
		//[[0]]. 반환할 정보들
		Integer resultCode				= HttpStatus.BAD_REQUEST.value();
		String resultMsg				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		TermsSelectRes termsSelectRes	= new TermsSelectRes();

		//[[1]]. 데이터 조회
		try {
			termsSelectRes					= termsService.getKatriTerms(trmsTyCd);

			resultCode						= HttpStatus.OK.value();
			resultMsg						= messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); //정상적으로 조회 하였습니다.
		} catch (CustomMessageException e) {
			resultCode	= HttpStatus.BAD_REQUEST.value();
			resultMsg	= commService.rtnMessageDfError(e.getMessage());

			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		} catch(Exception e) {
			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
			   .data(termsSelectRes)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build(),
			   HttpStatus.OK
				);

	}

	/*****************************************************
	 * PASS 인증 성공 Return URL
	*****************************************************/
	@RequestMapping("/comm/niceSuccess")
	public String niceSuccess(HttpServletRequest request, HttpSession session, Model model) {

		HashMap<Object, Object> responseNice = niceService.responseNiceSuccess(request, session);

		model.addAttribute("responseNice", responseNice);

		return "/common/niceSuccess";
	}

	/*****************************************************
	 * PASS 인증 실패 Return URL
	*****************************************************/
	@RequestMapping("/comm/niceFail")
	public String niceFail(HttpServletRequest request, HttpSession session, Model model) {

		HashMap<Object, Object> responseNice = niceService.responseNiceFail(request, session);

		model.addAttribute("responseNice", responseNice);

		return "/common/niceFail";
	}

}

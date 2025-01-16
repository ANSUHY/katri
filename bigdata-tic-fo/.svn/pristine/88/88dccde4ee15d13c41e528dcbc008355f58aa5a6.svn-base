package com.katri.web.auth.controller;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.CommonUtil;
import com.katri.common.util.SessionUtil;
import com.katri.web.auth.model.OverDayChgPwdSaveReq;
import com.katri.web.auth.model.OverDayChgPwdSaveRes;
import com.katri.web.auth.service.OverDayChgPwdService;
import com.katri.web.comm.service.CommService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Api(tags = {"login Controller"})
@Slf4j
public class OverDayChgPwdController {

	/** 공통 Service */
	private final CommService commService;

	/** 메시지 Component */
	private final MessageSource messageSource;

	/** [일정기간 지난 비밀번호 변경] Service */
	private final OverDayChgPwdService overDayChgPwdService;

	/*****************************************************
	 * [성공 + 일정기간 지난 비밀번호 변경] 페이지
	 * @return String [일정기간 지난 비밀번호 변경] 페이지
	*****************************************************/
	@GetMapping(value = {"/auth/overDayChgPwdInfo"})
	public String overDayChgPwdInfo(HttpSession session, HttpServletResponse response) {

		/* 로그인 체크 */
		if( ! commService.loginChk(session) ) {
			String errorMsg =  messageSource.getMessage("result-message.messages.login.message.need.login", null, SessionUtil.getLocale()); //'로그인이 필요합니다.'
			CommonUtil.moveUrlAlertMsg(response, errorMsg, "/auth/login");
		}

		return "/auth/overDayChgPwdInfo";
	}

	/*****************************************************
	 * [일정기간 지난 비밀번호 변경] 비밀번호 변경
	 * @param overDayChgPwdSaveReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/auth/updateChgOverDayChgPwd"})
	public ResponseEntity<ResponseDto> updateChgOverDayChgPwd(@ModelAttribute OverDayChgPwdSaveReq overDayChgPwdSaveReq) throws Exception {

		// [[0]]. 반환할 정보들 & 변수 지정
		String resultMsg 		= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		String chgResultCode	= "";
		OverDayChgPwdSaveRes overDayChgPwdSaveRes = new OverDayChgPwdSaveRes();

		try {

			/* 비밀번호 변경 */
			overDayChgPwdSaveRes = overDayChgPwdService.updateChgOverDayChgPwd(overDayChgPwdSaveReq);

			resultCode = HttpStatus.OK.value();
			resultMsg  = messageSource.getMessage("result-message.messages.common.message.update", null, SessionUtil.getLocale()); 		//정상적으로 수정 하였습니다.

		} catch (CustomMessageException e) {

			String strMsgCd = e.getMessage(); //service에서 exception하면서 보낸 메시지

			// 결과 code 추출하기
			if(strMsgCd.contains("||errorCode=") ) {//메시지 아규먼트가 있을경우

				String separator	= "||errorCode=";
				String separator2	= "||";

				int idx = strMsgCd.indexOf(separator);
				int inx2 = strMsgCd.substring(idx+separator.length()).indexOf(separator2);

				chgResultCode = strMsgCd.substring(idx+separator.length(), idx + separator.length() + inx2);
				strMsgCd = (strMsgCd.substring(0, idx) + strMsgCd.substring(idx + separator.length() + inx2 + separator2.length()));

			}

			//값 셋팅
			resultCode = HttpStatus.BAD_REQUEST.value();
			resultMsg = commService.rtnMessageDfError(strMsgCd);

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);

		} catch(Exception e) {

			throw e;

		}

		//결과코드(ERROR나 프론트단에서 해야할때) 셋팅
		overDayChgPwdSaveRes.setChgResultCode(chgResultCode);

		return new ResponseEntity<>(
			ResponseDto.builder()
			.data(overDayChgPwdSaveRes)
			.resultMessage(resultMsg)
			.resultCode(resultCode)
			.build()
			, HttpStatus.OK);

	}

	/*****************************************************
	 * [일정기간 지난 비밀번호 변경] (last_pwd_chg_dt를 지금으로 update) 처리
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/auth/updatePwdChgDtNow"})
	public ResponseEntity<ResponseDto> updatePwdChgDtNow() throws Exception {

		// [[0]]. 반환할 정보들 & 변수 지정
		String resultMsg 		= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		OverDayChgPwdSaveRes overDayChgPwdSaveRes = new OverDayChgPwdSaveRes();

		try {

			/* last_pwd_chg_dt 변경 */
			overDayChgPwdSaveRes = overDayChgPwdService.updatePwdChgDtNow();

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
			.data(overDayChgPwdSaveRes)
			.resultMessage(resultMsg)
			.resultCode(resultCode)
			.build()
			, HttpStatus.OK);

	}

}

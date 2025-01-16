package com.katri.web.test.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.ResponseDto;
import com.katri.web.comm.model.LayoutReq;
import com.katri.web.comm.service.CommService;
import com.katri.web.test.service.TestService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Api(tags = {"테스트 Controller"})
@Slf4j
public class TestController {

	/** 공통 Service */
	private final CommService commService;

	/** 테스트 Service */
	private final TestService testService;

	/*****************************************************
	 * 임시임시임시 test페이지
	 * @param
	 *****************************************************/
	@GetMapping(value = {"/test/test"})
	public String test(HttpServletRequest request, LayoutReq layoutReq, Model model) {

		model.addAttribute(layoutReq);

		return "/test/test";
	}

	/*****************************************************
	 * 임시임시 [한 SERVICE에서 DB업데이트 + 메일 전송 ] 개인 회원가입 완료 시 발송
	 * ##########메일이 필수인 경우 : 한 서비스에서 해서 메일에서 오류가 나면 전체 ROLLBACK
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/test/mailSendComp"})
	public ResponseEntity<ResponseDto> mailSendComp(HttpServletRequest request) throws Exception {

		/* [[0]]. 반환할 정보들 & 변수 지정 */
		String resultMsg 		="전송실패";
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		boolean mailSuccess = false;

		try {

			/*  여기서 DB업데이트 + 메일 전송 */
			mailSuccess = testService.dbUpdateAndSendMailComp(request);

			if(mailSuccess) {
				resultCode = HttpStatus.OK.value();
				resultMsg  = "전송 성공";
			}else {
				resultCode = HttpStatus.OK.value();
				resultMsg  = "전송 실패";
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
			.data(mailSuccess)
			.resultMessage(resultMsg)
			.resultCode(resultCode)
			.build()
			, HttpStatus.OK);

	}

	/*****************************************************
	 * 임시임시 [SERVICE1 에서 DB업데이트]  +  [SERVICE2 에서 메일 예약 전송(6분후)]
	 * ##########메일이 필수가 아닌 경우 : 메일에서 오류가 나면 메일만 ROLLBACK
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/test/mailSend3"})
	public ResponseEntity<ResponseDto> mailSend3(HttpServletRequest request) throws Exception {

		/* [[0]]. 반환할 정보들 & 변수 지정 */
		String resultMsg 		="전송실패";
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		boolean mailSuccess = false;
		int saveCount = 0;

		try {

			/* DB업데이트 */
			saveCount = testService.dbUpdate();

			if(saveCount != 0) {

				/*  메일 예약 전송 */
				mailSuccess = testService.sendReserveMail(request);

			}

			if(mailSuccess) {
				resultCode = HttpStatus.OK.value();
				resultMsg  = "전송 성공";
			}else {
				resultCode = HttpStatus.OK.value();
				resultMsg  = "전송 실패";
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
			.data(mailSuccess)
			.resultMessage(resultMsg)
			.resultCode(resultCode)
			.build()
			, HttpStatus.OK);

	}

}

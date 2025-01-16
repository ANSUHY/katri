package com.katri.web.test.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.CommonRes;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.SessionUtil;
import com.katri.web.board.model.BoardSelectRes;
import com.katri.web.comm.model.LayoutReq;
import com.katri.web.comm.service.CommService;
import com.katri.web.comm.service.NiceService;
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

	/** NICE Service */
	private final NiceService niceService;

	/** 메시지 Component */
	private final MessageSource messageSource;

	/*****************************************************
	 * 임시임시 테스트 조회
	 * @return 테스트 페이지
	 *****************************************************/
	@GetMapping(value = {"/test/test"})
	public String test(HttpServletRequest request, LayoutReq layoutReq, Model model) {

		model.addAttribute(layoutReq);

		return "/test/test";
	}


	/*****************************************************
	 * 임시임시 [한 SERVICE에서 DB업데이트 + 메일 전송 ]
	 * ##########메일이 필수인 경우 : 한 서비스에서 해서 메일에서 오류가 나면 전체 ROLLBACK
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/test/mailSend1"})
	public ResponseEntity<ResponseDto> mailSend1(HttpServletRequest request) throws Exception {

		/* [[0]]. 반환할 정보들 & 변수 지정 */
		String resultMsg 		="전송실패";
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		boolean mailSuccess = false;

		try {

			/*  여기서 DB업데이트 + 메일 전송 */
			mailSuccess = testService.dbUpdateAndSendMail(request);

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
	 * 임시임시 [SERVICE1 에서 DB업데이트]  +  [SERVICE2 에서 메일 전송]
	 * ##########메일이 필수가 아닌 경우 : 메일에서 오류가 나면 메일만 ROLLBACK
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/test/mailSend2"})
	public ResponseEntity<ResponseDto> mailSend2(HttpServletRequest request) throws Exception {

		/* [[0]]. 반환할 정보들 & 변수 지정 */
		String resultMsg 		="전송실패";
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		boolean mailSuccess = false;
		int saveCount = 0;

		try {

			/* DB업데이트 */
			saveCount = testService.dbUpdate();

			if(saveCount != 0) {

				/*  메일 전송 */
				mailSuccess = testService.sendMail(request);

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

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
	 * 임시임시 [한 SERVICE에서 DB업데이트 + 메일 전송 ] 1:1 문의 등록 발송
	 * ##########메일이 필수인 경우 : 한 서비스에서 해서 메일에서 오류가 나면 전체 ROLLBACK
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/test/mailSendInquiry"})
	public ResponseEntity<ResponseDto> mailSendInquiry(HttpServletRequest request) throws Exception {

		/* [[0]]. 반환할 정보들 & 변수 지정 */
		String resultMsg 		="전송실패";
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		boolean mailSuccess = false;

		try {

			/*  여기서 DB업데이트 + 메일 전송 */
			mailSuccess = testService.dbUpdateAndSendMailInquiry(request);

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

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*****************************************************
	 * @return nice임시페이지
	 *****************************************************/
	@GetMapping(value = {"/test/nice"})
	public String nice(HttpSession session, Model model) throws Exception {

		String sEncData = "";

		// [NICE 업체정보를 암호화 한 데이터] 가져오기
		sEncData = niceService.getNiceData(session);
		model.addAttribute("sEncData", sEncData);

		return "/test/nice";
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*****************************************************
	 * @return TEST Chart 임시페이지
	 *****************************************************/
	@GetMapping(value = {"/test/testChart"})
	public String testChart() {

		return "/test/testChart";

	}

	/*****************************************************
	 * board 차트 페이지 정보 조회
	 * @return ResponseEntity<ResponseDto>
	*****************************************************/
	@GetMapping(value = {"/test/getBoardChartData"})
	public ResponseEntity<ResponseDto> getBoardChartData() throws Exception {

		// [[0]]. 반환할 정보들
		String 		msg 		= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer 	resultCode 	= HttpStatus.BAD_REQUEST.value();
		CommonRes 	commonRes 	= new CommonRes();


		// 1. 게시판 타입별 데이터 조회
		List<BoardSelectRes> boardChartList = testService.getBoardChart();

		// 2. 결과값 셋팅
		if(boardChartList.size() > 0) {

			// 3. 차트 데이터 목록 셋팅
			commonRes.setList(boardChartList);

			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.

		}else {

			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.no.data", null, SessionUtil.getLocale()); // 데이터가 없습니다.

		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(commonRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

}

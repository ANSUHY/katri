package com.katri.web.mypage.inquiry.controller;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.Common;
import com.katri.common.model.CommonRes;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.CommonUtil;
import com.katri.common.util.SessionUtil;
import com.katri.web.auth.model.LoginReq;
import com.katri.web.auth.model.LoginRes;
import com.katri.web.auth.service.LoginService;
import com.katri.web.comm.service.CommService;
import com.katri.web.mypage.inquiry.model.InquirySaveReq;
import com.katri.web.mypage.inquiry.model.InquirySelectReq;
import com.katri.web.mypage.inquiry.model.InquirySelectRes;
import com.katri.web.mypage.inquiry.service.InquiryService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Api(tags = {"inquiry Controller"})
public class InquiryController {

	/** comm Service  */
	private final CommService commService;

	/** inquiry Service */
	private final InquiryService inquiryService;

	/** 메시지 Component */
	private final MessageSource messageSource;

	/** login Service */
	private final LoginService loginService;


	/*****************************************************
	 * Inquiry(1:1 문의) 리스트_페이지
	 * @param model
	 * @param boardSelectReq
	 * @return String
	 * @throws Exception
	*****************************************************/
	@GetMapping("/inquiry/inquiryList")
	public String inquiryList(Model model, @ModelAttribute InquirySelectReq inquiryData) throws Exception {
		//[[0]].반환할 데이터
		List<InquirySelectRes> categoryList	= null;
		LoginReq loginReq					= new LoginReq();
		LoginRes loginRes					= new LoginRes();

		//[[1]]. 조회

		/* 1-0. 현재 로그인한 아이디로 회원 유형 조회 */
		try {
			loginReq.setLoginId(SessionUtil.getLoginUserId());
			loginRes = loginService.selectLoginDetail(loginReq);

			/* 유저 타입 셋팅 */
			String userTyCd = loginRes.getUserTyCd();
			inquiryData.setUserTyCd(userTyCd);

			/* 1-1. 문의 유형 조회 */
			categoryList	= inquiryService.getCategoryList(userTyCd);

		} catch (Exception e) {
			throw e;
		}

		/* model에 담아 보내기 */
		model.addAttribute("userInfoData"	, loginRes);
		model.addAttribute("inquiryData"	, inquiryData);
		model.addAttribute("categoryList"	, categoryList);

		return "/mypage/inquiry/inquiryList";
	}

	/*****************************************************
	 * Inquiry(1:1 문의) 목록 조회
	 * @param inquirySelectReq
	 * @return ResponseEntity
	*****************************************************/
	@GetMapping("/inquiry/getInquiryList")
	public ResponseEntity<ResponseDto> getInquiryList(@ModelAttribute InquirySelectReq inquirySelectReq) {
		//[[0]].반환할 정보들
		Integer resultCode					= HttpStatus.BAD_REQUEST.value();
		String resultMsg					= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		List<InquirySelectRes> inquiryList	= null;
		int inquiryCtnt						= 0;
		CommonRes commonRes 				= new CommonRes();

		//[[1]]. Paging 변수 설정
		Common common	= CommonUtil.setPageParams(inquirySelectReq.getCurrPage(), inquirySelectReq.getRowCount());
		inquirySelectReq.setStartRow(common.getStartRow());
		inquirySelectReq.setEndRow(common.getEndRow());

		//[[2]]. 데이터 조회
		/* 2-0. 현재 로그인한 회원 아이디 */
		String crtrId	= SessionUtil.getLoginUserId();
		inquirySelectReq.setCrtrId(crtrId);
		/* 2-1. 현재 로그인한 회원 TYPE*/
		String userTyCd = SessionUtil.getLoginUserTyCd();
		inquirySelectReq.setUserTyCd(userTyCd);

		try {

			/* 2-1. inquiry totalCount 정보 */
			inquiryCtnt	= inquiryService.getInquiryCnt(inquirySelectReq);
			/* 2-2. inquiry List 조회 */
			inquiryList	= inquiryService.getInquiryList(inquirySelectReq);

			commonRes.setTotCnt(inquiryCtnt);
			commonRes.setList(inquiryList);

			resultCode	= HttpStatus.OK.value();
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); //정상적으로 조회 하였습니다.

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
			   .data(commonRes)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build(),
			   HttpStatus.OK);
	}

	/*****************************************************
	 * Inquiry(1:1 문의) 등록 화면
	 * @param inquirySelectReq
	 * @return 1:1 문의 등록 페이지 (String)
	*****************************************************/
	@GetMapping("/inquiry/inquiryReg")
	public String inquiryDetail(Model model, @ModelAttribute InquirySelectReq inquiryData) throws Exception {
		//[[0]].반환할 데이터
		List<InquirySelectRes> categoryList	= null;

		//[[1]]. 조회
		/* 유저 타입 셋팅 */
		String userTyCd = SessionUtil.getLoginUserTyCd();
		inquiryData.setUserTyCd(userTyCd);

		/* 문의 유형 조회 */
		categoryList	= inquiryService.getCategoryList(userTyCd);

		//[[2]]. 데이터 셋팅
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("inquiryData", inquiryData);

		return "/mypage/inquiry/inquiryReg";
	}

	/*****************************************************
	 * Inquiry(1:1 문의) 등록
	 * @param inquirySelectReq
	 * @return ResponseEntity
	 * @throws Exception
	*****************************************************/
	@PostMapping("/inquiry/regInquiry")
	public ResponseEntity<ResponseDto> regInquiry(HttpServletRequest request, @ModelAttribute InquirySaveReq inquirySaveReq) throws Exception {
		//[[0]]. 반환할 데이터
		int result			= 0;
		Integer resultCode	= HttpStatus.BAD_REQUEST.value();
		String resultMsg	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		boolean mailSuccess	= false;

		//[[1]]. 1:1 문의 등록
		try {
			result	= inquiryService.regInquiry(request, inquirySaveReq);

			resultCode	= HttpStatus.OK.value();
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); //정상적으로 조회 하였습니다.

			if(result > 0) {
				//1-2. 메일 발송 서비스 호출
				mailSuccess	= inquiryService.sendInquiryMail(request);

				if(mailSuccess) {
					resultCode	= HttpStatus.OK.value();
					resultMsg 	= "전송 성공";
				}else {
			//		resultCode	= HttpStatus.OK.value();
					resultMsg	= "전송 실패";
				}
			}

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
			   .data(result)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build(),
			   HttpStatus.OK);
	}

	/*****************************************************
	 * Inquiry(1:1 문의) 검색
	 * @param inquirySelectReq
	 * @return 검색 결과 (List)
	*****************************************************/
	@GetMapping("/inquiry/searchInquiry")
	public ResponseEntity<ResponseDto> searchInquiry(@ModelAttribute InquirySelectReq inquirySelectReq) {
		//[[0]]. 반환할 데이터
		Integer resultCode	= HttpStatus.BAD_REQUEST.value();
		String resultMsg	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		List<InquirySelectRes> inquiryList	= null;
		int inquiryCtnt						= 0;
		CommonRes commonRes 				= new CommonRes();

		//[[1]]. Paging 변수 설정
		Common common	= CommonUtil.setPageParams(inquirySelectReq.getCurrPage(), inquirySelectReq.getRowCount());
		inquirySelectReq.setStartRow(common.getStartRow());
		inquirySelectReq.setEndRow(common.getEndRow());

		//[[2]]. 데이터 조회
		/* 2-0. 현재 로그인한 회원 아이디 */
		String crtrId	= SessionUtil.getLoginUserId();
		inquirySelectReq.setCrtrId(crtrId);

		try {

			/* 2-1. inquiry Cnt 정보 */
			inquiryCtnt	= inquiryService.getInquiryCnt(inquirySelectReq);
			/* 2-2. inquiry List 조회 */
			inquiryList	= inquiryService.getInquiryList(inquirySelectReq);

			commonRes.setTotCnt(inquiryCtnt);
			commonRes.setList(inquiryList);

			resultCode	= HttpStatus.OK.value();
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); //정상적으로 조회 하였습니다.

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
			   .data(commonRes)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build(),
			   HttpStatus.OK);

	}

	/*****************************************************
	 * Inquiry(1:1 문의) 조회수 증가
	 * @param String
	 * @return ResponseEntity
	*****************************************************/
	@PostMapping("/inquiry/addViewCount")
	public ResponseEntity<ResponseDto> addViewCount(@RequestParam String num) {
		//[[0]]. 반환할 데이터
		Integer resultCode	= HttpStatus.BAD_REQUEST.value();
		String resultMsg	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		int result			= 0;

		//[[1]]. 조회수 증가
		try {
			/* 조회수 증가 결과 */
			result				=  inquiryService.addViewCount(num);
			resultCode			=  HttpStatus.OK.value();
			resultMsg			=  messageSource.getMessage("result-message.messages.common.message.update", null, SessionUtil.getLocale()); //정상적으로 수정 하였스니다.
		} catch (CustomMessageException e) {
			resultCode			= HttpStatus.BAD_REQUEST.value();
			resultMsg			= commService.rtnMessageDfError(e.getMessage());

			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		} catch(Exception e) {
			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
			   .data(result)
			   .resultCode(resultCode)
			   .resultMessage(resultMsg)
			   .build(),
			   HttpStatus.OK);
	}



}

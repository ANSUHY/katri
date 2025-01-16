package com.katri.web.ctnt.inquiry.controller;

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
import com.katri.web.comm.model.CommRes;
import com.katri.web.comm.service.CommService;
import com.katri.web.comm.service.MailService;
import com.katri.web.ctnt.inquiry.model.InquirySaveReq;
import com.katri.web.ctnt.inquiry.model.InquirySelectReq;
import com.katri.web.ctnt.inquiry.model.InquirySelectRes;
import com.katri.web.ctnt.inquiry.service.InquiryService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Api(tags = {"Help Controller"})
@RequestMapping(value = "/ctnt")
@Slf4j
public class InquiryController {

	/** inquiry Service */
	private final InquiryService inquiryService;

	/** 메시지 Component */
	private final MessageSource messageSource;

	/** 공통 Service */
	private final CommService commService;

	/***/
	private final MailService mailService;


	/*****************************************************
	 * 1:1 문의 목록 페이지
	 * @param model
	 * @param inquirySelectReq
	 * @return String
	*****************************************************/
	@GetMapping(value={"/inquiry/inquiryList"})
	public String getHelpList(Model model, @ModelAttribute InquirySelectReq inquirySelectReq) {

		model.addAttribute("inquirySelectReq", inquirySelectReq);

		return "/ctnt/inquiry/inquiryList";
	}

	/*****************************************************
	 * 1:1 문의 리스트 정보 조회
	 * @param inquirySelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throw  Exception
	*****************************************************/
	@GetMapping(value={"/inquiry/getInqList"})
	public ResponseEntity<ResponseDto> getInqList(@ModelAttribute InquirySelectReq searchData){
		//[[0]]. 반환할 정보들
		List<InquirySelectRes> inqList	= null;
		String resultMsg	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주세요.
		Integer resultCode	= HttpStatus.BAD_REQUEST.value();
		CommonRes commonRes	= new CommonRes();
		int inqCnt	= 0;

		//[[1]]. Paging 변수 설정
		Common common = CommonUtil.setPageParams(searchData.getCurrPage(), searchData.getRowCount());
		searchData.setStartRow(common.getStartRow());
		searchData.setEndRow(common.getEndRow());

		//[[2]]. 데이터 조회
		/* 2-1. inquiry Cnt 정보 */
		inqCnt	= inquiryService.getInqCnt(searchData);

			/* 2-2. inquiry List 정보 */
			try {
				inqList	= inquiryService.getInqList(searchData);

				resultCode	= HttpStatus.OK.value();
				resultMsg	= messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
			} catch (CustomMessageException e) {
				resultMsg	= commService.rtnMessageDfError(e.getMessage());
				resultCode	= HttpStatus.BAD_REQUEST.value();

				//로그에 넣기
				log.error("\n===============ERROR=========================\n" + e.getMessage());
				log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
			} catch (Exception e) {
				throw e;
			}

		//[[3]].데이터 셋팅
		commonRes.setTotCnt(inqCnt);
		commonRes.setList(inqList);

		return new ResponseEntity<>(
				ResponseDto.builder()
			   .data(commonRes)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build(),
			   HttpStatus.OK
				);

	}

	/*****************************************************
	 * 1:1 문의 상세 팝업 열기
	 * @param
	 * @return String
	 * @throw
	*****************************************************/
	@GetMapping(value={"/inquiry/inquiryDetailPopUp"})
	public String openDetailPop() {
		return "/ctnt/inquiry/inquiryDetailPopUp";
	}

	/*****************************************************
	 * 1:1 문의 상세 조회
	 * @param String
	 * @return ResponseEntity<ResponseDto>
	 * @throw  Exception
	*****************************************************/
	@PostMapping(value={"/inquiry/getDetailData"})
	public ResponseEntity<ResponseDto> getDetailData(@RequestParam String num) {
		//[[0]]. 반환할 정보들
		InquirySelectRes detailData	= null;
		int resultCode		= HttpStatus.BAD_REQUEST.value();
		String resultMsg	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주세요.
		int	nttSn			= 0;

		//[[1]].데이터 조회
		/* 1_0. 넘어온 파라미터 int로 형변환 */
		nttSn	= Integer.parseInt(num);

		try {
			detailData	= inquiryService.getDetailData(nttSn);

			resultCode	= HttpStatus.OK.value();
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
		} catch (CustomMessageException e) {
			resultMsg	= commService.rtnMessageDfError(e.getMessage());
			resultCode	= HttpStatus.BAD_REQUEST.value();

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		}


		return new ResponseEntity<>(
				ResponseDto.builder()
			   .data(detailData)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build(),
			   HttpStatus.OK
				);


	}

	/*****************************************************
	 * 1:1 문의 답변 수정
	 * @param inquirySaveReq
	 * @return ResponseEntity<ResponseDto>
	 * @throw  Exception
	*****************************************************/
	@PostMapping("/inquiry/updateInquiryAns")
	public ResponseEntity<ResponseDto> updateInquiryAns(@ModelAttribute InquirySaveReq updateData) {
		//[[0]]. 반환할 정보들
		int result	= 0;
		String resultMsg	= messageSource.getMessage("result-message.messages.common.message.update.error", null, SessionUtil.getLocale()); //수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode	= HttpStatus.BAD_REQUEST.value();

		//[[1]]. 수정 데이터
		try {
			result		= inquiryService.updateInquiryAns(updateData);
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.update", null, SessionUtil.getLocale()); //정상적으로 수정하였습니다.
			resultCode	= HttpStatus.OK.value();
		} catch (CustomMessageException e) {
			resultMsg	= commService.rtnMessageDfError(e.getMessage());
			resultCode	= HttpStatus.BAD_REQUEST.value();

			//로그에 넣기
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
			   HttpStatus.OK
				);

	}

	/*****************************************************
	 * 1:1 문의 답변 등록
	 * @param inquirySaveReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	 * @throw  Exception
	*****************************************************/
	@PostMapping("/inquiry/regInqAns")
	public ResponseEntity<ResponseDto> regInqAns(@ModelAttribute InquirySaveReq regData, HttpServletRequest request) throws Exception {
		//[[0]]. 반환할 정보들
		int result			= 0;
		boolean mailSuccess		= false;
		String resultMsg	= messageSource.getMessage("result-message.messages.common.message.save.error", null, SessionUtil.getLocale()); //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode	= HttpStatus.BAD_REQUEST.value();

		//[[1]]. 신규 데이터
		try {
			// 1-1. 답변 등록
			result	= inquiryService.regInqAns(regData);
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.insert", null, SessionUtil.getLocale()); //정상적으로 등록 하였습니다.
			resultCode	= HttpStatus.OK.value();

			// 1-2. 메일 발송 서비스 호출
			mailSuccess	= inquiryService.sendInquiryMail(request, regData.getCrtrId());

			if(mailSuccess) {
				resultCode	= HttpStatus.OK.value();
				resultMsg	= "전송 성공";
			}else {
				resultCode	= HttpStatus.OK.value();
				resultMsg	= "전송 실패";
			}


		} catch (CustomMessageException e) {
			resultMsg	= commService.rtnMessageDfError(e.getMessage());
			resultCode	= HttpStatus.BAD_REQUEST.value();

			//로그에 넣기
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
			   HttpStatus.OK
				);

	}


}

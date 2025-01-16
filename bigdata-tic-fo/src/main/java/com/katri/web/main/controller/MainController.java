package com.katri.web.main.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.service.CommService;
import com.katri.web.ctnt.notice.model.NoticeSelectRes;
import com.katri.web.main.model.MainSelectReq;
import com.katri.web.main.model.MainSelectRes;
import com.katri.web.main.service.MainService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Api(tags = {"메인 Controller"})
@Slf4j
public class MainController {

	/** 공통 Service */
	private final CommService commService;

	/** 메인 Service */
	private final MainService mainService;

	/** 메시지 Component */
	private final MessageSource messageSource;

	/*****************************************************
	 * main 페이지로가기
	 * @param request
	 * @param model
	 * @param mainSelectReq
	 * @return  main 페이지
	*****************************************************/
	@GetMapping(value = {"/"})
	public String main(HttpServletRequest request, Model model, MainSelectReq mainSelectReq) {

		//[[0]]. 필요한 정보들
		MainSelectRes mainSelectRes = new MainSelectRes();
		List<MainSelectRes> mainVisualList	= null;
		List<MainSelectRes> popUpZoneList	= null;

		//[[1]]. 데이터 조회
		/* 초기 메인 데이터 */
		mainSelectRes = mainService.getMainInfo();

		/* 메인 비주얼 조회 */
		mainVisualList		= mainService.getMainVisualList(mainSelectReq);
		/* 팝업존 조회 */
		popUpZoneList		= mainService.getPopUpZoneList(mainSelectReq);

		model.addAttribute("mainData",mainSelectRes);
		model.addAttribute("mainVisualList", mainVisualList);
		model.addAttribute("popUpZoneList", popUpZoneList);

		return "/main/main";
	}

	/*****************************************************
	 * [메인] 커뮤니티 -> 공지사항 (커뮤니티 게시글 4개-공지사항/FAQ/자료실)
	 * @param nttTyCd
	 * @return  List (공지사항 게시글)
	*****************************************************/
	@PostMapping(value = {"/main/getMainCommunityList"} )
	public ResponseEntity<ResponseDto> MainCommunityInfo(@RequestParam String nttTyCd){
		//[[0]]. 반환할 정보들
		Integer resultCode						= HttpStatus.BAD_REQUEST.value();
		String  resultMsg						= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		List<NoticeSelectRes> mainCommunityList	= null;

		//[[1]].
		try {
			mainCommunityList							= mainService.getMainCommunityInfo(nttTyCd);

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
				.data(mainCommunityList)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK
				);

	}

	/*****************************************************
	 * 공지(모달팝업)조회
	 * @param nttTyCd
	 * @return List (공지사항 게시글)
	*****************************************************/
	@PostMapping("/main/getNoticeMainVisualList")
	public ResponseEntity<ResponseDto> mainVisualNoticeList(@RequestParam String nttTyCd) {
		//[[0]]. 반환할 정보들
		Integer resultCode							= HttpStatus.BAD_REQUEST.value();
		String resultMsg							= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		List<NoticeSelectRes> mainVisualNoticeList	= null;

		//[[1]].
		try {
			mainVisualNoticeList						= mainService.getMainVisualNoticeList(nttTyCd);

			resultCode									= HttpStatus.OK.value();
			resultMsg									=  messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); //정상적으로 조회 하였습니다.
		} catch (CustomMessageException e) {
			resultCode									= HttpStatus.BAD_REQUEST.value();
			resultMsg									= commService.rtnMessageDfError(e.getMessage());

			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		} catch (Exception e) {
			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
			   .data(mainVisualNoticeList)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build(),
			   HttpStatus.OK
				);
	}

	/*****************************************************
	 * [메인_월별 인증현황_chart] TOP10 법정인증 품목 List
	 * @return List
	 * @throws Exception
	*****************************************************/
	@PostMapping("/main/getMainChartTopPrdtClfList")
	public ResponseEntity<ResponseDto> getMainChartTopPrdtClfList() throws Exception {

		//[[0]]. 반환할 정보들
		Integer resultCode		= HttpStatus.BAD_REQUEST.value();
		String resultMsg		= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		List<MainSelectRes> topPrdtClfList = null;

		try {

			//[[1]]. 데이터 조회
			topPrdtClfList		= mainService.getMainTopPrdtClfList();

			resultCode		= HttpStatus.OK.value();
			resultMsg		= messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); //정상적으로 조회 하였습니다.

		} catch (CustomMessageException e) {
			resultCode									= HttpStatus.BAD_REQUEST.value();
			resultMsg									= commService.rtnMessageDfError(e.getMessage());

			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);

		} catch (Exception e) {
			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(topPrdtClfList)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * [메인_월별 인증현황_chart] TOP10 법정인증 제품 List
	 * @return List
	 * @throws Exception
	*****************************************************/
	@PostMapping("/main/getMainChartTopPdctgList")
	public ResponseEntity<ResponseDto> getMainChartTopPdctgList() throws Exception {

		//[[0]]. 반환할 정보들
		Integer resultCode		= HttpStatus.BAD_REQUEST.value();
		String resultMsg		= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		List<MainSelectRes> topPdctgList = null;

		try {

			//[[1]]. 데이터 조회
			topPdctgList		= mainService.getMainTopPdctgList();

			resultCode		= HttpStatus.OK.value();
			resultMsg		= messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); //정상적으로 조회 하였습니다.

		} catch (CustomMessageException e) {
			resultCode									= HttpStatus.BAD_REQUEST.value();
			resultMsg									= commService.rtnMessageDfError(e.getMessage());

			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);

		} catch (Exception e) {
			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(topPdctgList)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * [메인_월별 인증현황_chart] TOP10 법정인증 제조국 List
	 * @return List
	 * @throws Exception
	*****************************************************/
	@PostMapping("/main/getMainChartTopMnftrCustcoNtnList")
	public ResponseEntity<ResponseDto> getMainChartTopMnftrCustcoNtnList() throws Exception {

		//[[0]]. 반환할 정보들
		Integer resultCode		= HttpStatus.BAD_REQUEST.value();
		String resultMsg		= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		List<MainSelectRes> topMnftrCustcoNtnList = null;

		try {

			//[[1]]. 데이터 조회
			topMnftrCustcoNtnList		= mainService.getMainTopMnftrCustcoNtnList();

			resultCode		= HttpStatus.OK.value();
			resultMsg		= messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); //정상적으로 조회 하였습니다.

		} catch (CustomMessageException e) {
			resultCode									= HttpStatus.BAD_REQUEST.value();
			resultMsg									= commService.rtnMessageDfError(e.getMessage());

			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);

		} catch (Exception e) {
			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(topMnftrCustcoNtnList)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

}

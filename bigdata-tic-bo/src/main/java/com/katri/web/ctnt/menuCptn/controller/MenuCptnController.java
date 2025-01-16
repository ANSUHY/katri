package com.katri.web.ctnt.menuCptn.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.CommonRes;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.service.CommService;
import com.katri.web.ctnt.menuCptn.model.MenuCptnSaveReq;
import com.katri.web.ctnt.menuCptn.model.MenuCptnSaveRes;
import com.katri.web.ctnt.menuCptn.model.MenuCptnSelectReq;
import com.katri.web.ctnt.menuCptn.model.MenuCptnSelectRes;
import com.katri.web.ctnt.menuCptn.service.MenuCptnService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequiredArgsConstructor
@Api(tags = {"소개 및 안내관리(메뉴구성관리) Controller"})
@Slf4j
public class MenuCptnController {

	/** 공통 Service */
	private final CommService commService;

	/** 메시지 Component */
	private final MessageSource messageSource;

	/** menuCptn Service */
	private final MenuCptnService menuCptnService;

	/*****************************************************
	 * [콘텐츠 관리] > [소개 및 안내 관리] 리스트_페이지
	 * @return String
	*****************************************************/
	@GetMapping(value = {"/ctnt/menuCptn/menuCptnList"})
	public String menuCptnList() {

		return "/ctnt/menuCptn/menuCptnList";

	}

	/*****************************************************
	 * [콘텐츠 관리] > [소개 및 안내 관리] 리스트 정보 조회
	 * @param menuCptnSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/ctnt/menuCptn/getMenuCptnList"})
	public ResponseEntity<ResponseDto> getMenuCptnList( @ModelAttribute MenuCptnSelectReq menuCptnSelectReq) throws Exception {

		// [[0]]. 반환할 정보들
		String resultMsg 				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 				= HttpStatus.BAD_REQUEST.value();
		int menuCptnCnt					= 0;
		List<MenuCptnSelectRes> menuCptnList	= null;
		CommonRes commonRes 			= new CommonRes();

		try {

			// [[1]]. 데이터 조회
			/* 1-1. menuCptn Cnt 정보*/
			menuCptnCnt = menuCptnService.getMenuCptnCnt(menuCptnSelectReq);

			if(menuCptnCnt != 0) {

				/* 1-2. menuCptn List 정보*/
				menuCptnList = menuCptnService.getMenuCptnList(menuCptnSelectReq);

				resultCode = HttpStatus.OK.value();
				resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.

			}else {

				resultCode = HttpStatus.OK.value();
				resultMsg = messageSource.getMessage("result-message.messages.common.message.no.data", null, SessionUtil.getLocale()); // 데이터가 없습니다.

			}

			// [[3]]. 데이터 셋팅
			commonRes.setTotCnt(menuCptnCnt);
			commonRes.setList(menuCptnList);

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
				.data(commonRes)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);

	}

	/*****************************************************
	 * [콘텐츠 관리] > [소개 및 안내 관리] 사용여부 change
	 * @param menuCptnSaveReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/ctnt/menuCptn/updateChgMenuCptnUseYn"})
	public ResponseEntity<ResponseDto> updateChgMenuCptnUseYn(@ModelAttribute MenuCptnSaveReq menuCptnSaveReq) throws Exception {

		// [[0]]. 반환할 정보들 & 변수 지정
		String resultMsg 		= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		MenuCptnSaveRes menuCptnSaveRes = new MenuCptnSaveRes();

		try {

			/* 사용여부 수정 */
			menuCptnSaveRes = menuCptnService.updateChgMenuCptnUseYn(menuCptnSaveReq);

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
			.data(menuCptnSaveRes)
			.resultMessage(resultMsg)
			.resultCode(resultCode)
			.build()
			, HttpStatus.OK);

	}

	/*****************************************************
	 * [콘텐츠 관리] > [소개 및 안내 관리] 등록,수정_PopUp 페이지
	 * @param response
	 * @param model
	 * @param menuCptnSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/ctnt/menuCptn/menuCptnRegPopUp"})
	public String menuCptnRegPopUp(HttpServletResponse response, Model model,MenuCptnSelectReq menuCptnSelectReq){

		return "/ctnt/menuCptn/menuCptnRegPopUp";

	}

	/*****************************************************
	 * [콘텐츠 관리] > [소개 및 안내 관리] 상세 정보 조회
	 * @param menuCptnSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/ctnt/menuCptn/getMenuCptnDetail"})
	public ResponseEntity<ResponseDto> getMenuCptnDetail( @ModelAttribute MenuCptnSelectReq menuCptnSelectReq) throws Exception {

		// [[0]]. 반환할 정보들
		String resultMsg 				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 				= HttpStatus.BAD_REQUEST.value();
		MenuCptnSelectRes menuCptnDetail		= null;

		try {

			// [[1]]. 데이터 조회
			menuCptnDetail = menuCptnService.getMenuCptnDetail(menuCptnSelectReq);

			resultCode = HttpStatus.OK.value();
			resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.

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
				.data(menuCptnDetail)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);

	}

	/*****************************************************
	 * [콘텐츠 관리] > [소개 및 안내 관리] 등록 및 수정
	 * @param request
	 * @param MenuCptnSaveReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/ctnt/menuCptn/saveMenuCptn"})
	public ResponseEntity<ResponseDto> saveMenuCptn(@ModelAttribute MenuCptnSaveReq menuCptnSaveReq) throws Exception{

		// [[0]]. 반환할 정보들 & 변수 지정
		String resultMsg 		= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		MenuCptnSaveRes menuCptnSaveRes 	= new MenuCptnSaveRes();

		try {

			// [[1]]. 등록 및 수정
			if("U".equals(menuCptnSaveReq.getSaveType()) && menuCptnSaveReq.getMenuCptnSn() != null) {

				/* 수정 */
				menuCptnSaveRes = menuCptnService.updateMenuCptn(menuCptnSaveReq);

				if(menuCptnSaveRes.getMenuCptnSn() == null) {
					resultCode = HttpStatus.BAD_REQUEST.value();
					resultMsg = messageSource.getMessage("result-message.messages.common.message.update.error", null, SessionUtil.getLocale()); 	//수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
				} else {
					resultCode = HttpStatus.OK.value();
					resultMsg = messageSource.getMessage("result-message.messages.common.message.update", null, SessionUtil.getLocale()); 		//정상적으로 수정 하였습니다.
				}

			} else {

				/* 등록 */
				menuCptnSaveRes = menuCptnService.insertMenuCptn(menuCptnSaveReq);

				if(menuCptnSaveRes.getMenuCptnSn() == null) {
					resultCode = HttpStatus.BAD_REQUEST.value();
					resultMsg = messageSource.getMessage("result-message.messages.common.message.save.error", null, SessionUtil.getLocale()); //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
				} else {
					resultCode = HttpStatus.OK.value();
					resultMsg = messageSource.getMessage("result-message.messages.common.message.save", null, SessionUtil.getLocale()); 		//정상적으로 저장 하였습니다.
				}

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
			.data(menuCptnSaveRes)
			.resultMessage(resultMsg)
			.resultCode(resultCode)
			.build()
			, HttpStatus.OK);

	}

	/*****************************************************
	 * [콘텐츠 관리] > [소개 및 안내 관리] 등록시 [불러오기] 리스트 가져오기
	 * @param menuCptnSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/ctnt/menuCptn/getImportMenuCptnSelectList"})
	public ResponseEntity<ResponseDto> getImportMenuCptnSelectList( @ModelAttribute MenuCptnSelectReq menuCptnSelectReq) throws Exception {

		// [[0]]. 반환할 정보들
		String resultMsg 				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 				= HttpStatus.BAD_REQUEST.value();
		int menuCptnCnt					= 0;
		List<MenuCptnSelectRes> menuCptnList	= null;
		CommonRes commonRes 			= new CommonRes();

		try {

			// [[1]]. 데이터 조회
			/* 1-1. menuCptn Cnt 정보*/
			menuCptnCnt = menuCptnService.getMenuCptnCnt(menuCptnSelectReq);

			if(menuCptnCnt != 0) {

				/* 1-2. menuCptn List 정보*/
				menuCptnList = menuCptnService.getMenuCptnList(menuCptnSelectReq);

				resultCode = HttpStatus.OK.value();
				resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.

			}else {

				resultCode = HttpStatus.OK.value();
				resultMsg = messageSource.getMessage("result-message.messages.common.message.no.data", null, SessionUtil.getLocale()); // 데이터가 없습니다.

			}

			// [[3]]. 데이터 셋팅
			commonRes.setTotCnt(menuCptnCnt);
			commonRes.setList(menuCptnList);

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
				.data(commonRes)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);

	}

	/*****************************************************
	 * [콘텐츠 관리] > [소개 및 안내 관리] 등록시 [불러오기] 눌렀을때 상세데이터 가져오기
	 * @param menuCptnSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/ctnt/menuCptn/getImportMenuCptnDetail"})
	public ResponseEntity<ResponseDto> getImportMenuCptnDetail( @ModelAttribute MenuCptnSelectReq menuCptnSelectReq) throws Exception {

		// [[0]]. 반환할 정보들
		String resultMsg 				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 				= HttpStatus.BAD_REQUEST.value();
		MenuCptnSelectRes menuCptnDetail		= null;

		try {

			// [[1]]. 데이터 조회
			menuCptnDetail = menuCptnService.getMenuCptnDetail(menuCptnSelectReq);

			resultCode = HttpStatus.OK.value();
			resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.

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
				.data(menuCptnDetail)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);

	}


}

package com.katri.web.system.menu.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.CommonRes;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.service.CommService;
import com.katri.web.system.menu.model.MenuSaveReq;
import com.katri.web.system.menu.model.MenuSaveRes;
import com.katri.web.system.menu.model.MenuSelectReq;
import com.katri.web.system.menu.model.MenuSelectRes;
import com.katri.web.system.menu.service.MenuService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Api(tags = {"menu Controller"})
@RequestMapping(value = "/system")
@Slf4j
public class MenuController {

	/** 공통 Service */
	private final CommService commService;

	/** Menu Service */
	private final MenuService menuService;

	/** 메시지 Component */
	private final MessageSource messageSource;

	/*****************************************************
	 * menu 리스트 이동
	 * @param model
	 * @param menuSelectReq
	 * @return String
	*****************************************************/
	@GetMapping(value = {"/menu/menuList"})
	public String menuList(Model model) {

		return "system/menu/menuList";

	}

	/*****************************************************
	 * menu 리스트 정보 조회
	 * @param MenuSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/menu/getMenuList"})
	public ResponseEntity<ResponseDto> getMenuList( @ModelAttribute MenuSelectReq menuSelectReq) throws Exception {

		// [[0]]. 반환할 정보들
		String msg 						= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 				= HttpStatus.BAD_REQUEST.value();
		List<MenuSelectRes> menuList 	= null;
		CommonRes commonRes 			= new CommonRes();

		// [[1]]. 데이터 조회
		/* 1-1. menu 개수 조회 */
		Integer nTotCnt = menuService.getMenuCnt(menuSelectReq);

		if(nTotCnt != 0) {

			/* 1-2. menu 리스트 조회 */
			menuList = menuService.getMenuList(menuSelectReq);

			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.

		}else {

			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.no.data", null, SessionUtil.getLocale()); // 데이터가 없습니다.

		}

		// [[2]]. 데이터 셋팅
		commonRes.setTotCnt(nTotCnt);
		commonRes.setList(menuList);

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(commonRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);

	}

	/*****************************************************
	 * menu 정보 저장
	 * @param request
	 * @param menuSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/menu/saveMenu"})
	public ResponseEntity<ResponseDto> saveMenu( HttpServletRequest request, @ModelAttribute @Valid MenuSaveReq menuSaveReq) throws Exception {

		// [[0]]. 반환할 정보들
		String 		msg 			= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer 	resultCode 		= HttpStatus.BAD_REQUEST.value();
		MenuSaveRes menuSaveRes 	= new MenuSaveRes();

		try {

			// [[1]]. 저장 시작
			Integer nMenuSn = menuSaveReq.getMenuSn();

			if( nMenuSn != null ) {

				/* 수정자 셋팅 */
				menuSaveReq.setMdfrId(SessionUtil.getLoginMngrId());

				/* 메뉴 수정 */
				menuSaveRes = menuService.updateMenu(request, menuSaveReq);

			} else {

				/* 등록자 셋팅 */
				menuSaveReq.setCrtrId(SessionUtil.getLoginMngrId());

				/* 메뉴 등록 */
				menuSaveRes = menuService.insertMenu(request, menuSaveReq);

			}

			if(menuSaveRes.getMenuSn() == null) {
				resultCode = HttpStatus.BAD_REQUEST.value();
				msg = messageSource.getMessage("result-message.messages.common.message.save.error", null, SessionUtil.getLocale()); //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			} else {
				resultCode = HttpStatus.OK.value();
				msg = messageSource.getMessage("result-message.messages.common.message.save", null, SessionUtil.getLocale()); 		//정상적으로 저장 하였습니다.
			}

			// [[2]]. 메뉴 레벨 셋팅
			menuSaveRes.setMenuLvlVal(menuSaveReq.getMenuLvlVal());
			menuSaveRes.setUpMenuSn(menuSaveReq.getUpMenuSn());

		} catch(CustomMessageException e) {

			//값 셋팅
			resultCode = HttpStatus.BAD_REQUEST.value();
			msg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

		} catch(Exception e) {

			throw e;

		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(menuSaveRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);

	}

	/*****************************************************
	 * menu 상세 정보 조회
	 * @param MenuSelectReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/menu/getMenuDetail"})
	public ResponseEntity<ResponseDto> getMenuDetail( @ModelAttribute MenuSelectReq menuSelectReq) throws Exception {

		// [[0]]. 반환할 정보들
		String msg 						= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 				= HttpStatus.BAD_REQUEST.value();
		MenuSelectRes menuSelectRes 	= null;

		// [[1]]. 데이터 조회
		menuSelectRes = menuService.selectMenuDetail(menuSelectReq);

		if( menuSelectRes != null ) {
			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
		} else {
			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.no.data", null, SessionUtil.getLocale()); // 데이터가 없습니다.
		}

		// [[2]]. 데이터 셋팅
		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(menuSelectRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);

	}

	/*****************************************************
	 * menu 정렬 순서 저장
	 * @param request
	 * @param menuSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/menu/saveMenuSeq"})
	public ResponseEntity<ResponseDto> saveMenuSeq( HttpServletRequest request, @ModelAttribute MenuSaveReq menuSaveReq) throws Exception {

		// [[0]]. 반환할 정보들
		String msg 				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		int	nCount				= 0;

		try {

			// [[1]]. 메뉴 정렬 저장 시작
			nCount = menuService.saveMenuSeq(request, menuSaveReq);

			if( nCount > 0 ) {
				resultCode = HttpStatus.OK.value();
				msg = messageSource.getMessage("result-message.messages.common.message.save", null, SessionUtil.getLocale()); 		//정상적으로 저장 하였습니다.
			} else {
				resultCode = HttpStatus.BAD_REQUEST.value();
				msg = messageSource.getMessage("result-message.messages.common.message.save.error", null, SessionUtil.getLocale()); //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}

		} catch(CustomMessageException e) {

			//값 셋팅
			resultCode = HttpStatus.BAD_REQUEST.value();
			msg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

		} catch(Exception e) {

			throw e;

		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(nCount)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);

	}

}

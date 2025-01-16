package com.katri.web.system.admin.controller;

import java.util.List;

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
import com.katri.web.comm.service.CommService;
import com.katri.web.system.admin.model.AdminSaveReq;
import com.katri.web.system.admin.model.AdminSelectReq;
import com.katri.web.system.admin.model.AdminSelectRes;
import com.katri.web.system.admin.model.AuthorSelectRes;
import com.katri.web.system.admin.service.AdminService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Api(tags = {"Admin Controller"})
@RequestMapping(value = "/system")
@Slf4j
public class AdminController {

	/** Admin Service */
	private final AdminService adminService;

	/** 메시지 Compoment */
	private final MessageSource messageSource;

	/** 공통 Service */
	private final CommService commService;



	/*****************************************************
	 * 관리자 관리 리스트 페이지
	 * @param model
	 * @param adminSelectReq
	 * @return String
	*****************************************************/
	@GetMapping(value = {"/admin/adminList"})
	public String adminList(Model model) {
		return "system/admin/adminList";
	}

	/*****************************************************
	 * 관리자 관리 리스트 페이지
	 * @param model
	 * @param adminSelectReq
	 * @return ResponseEntity
	*****************************************************/
	@GetMapping(value = {"/admin/getAdminList"})
	public ResponseEntity<ResponseDto> getAdminList(@ModelAttribute AdminSelectReq adminSelectReq) {

		//[[0]].반환할 정보들
		String resultMsg 				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주세요.
		Integer resultCode 				= HttpStatus.BAD_REQUEST.value();
		List<AdminSelectRes> adminList 	= null;
		CommonRes commonRes 			= new CommonRes();
		int adminCtnt					= 0;

		//[[1]].페이징 변수 설정
		Common common = CommonUtil.setPageParams(adminSelectReq.getCurrPage(), adminSelectReq.getRowCount());
		adminSelectReq.setStartRow(common.getStartRow());
		adminSelectReq.setEndRow(common.getEndRow());

		//[[2]].데이터 조회
		/* 2-1. adminCtnt 정보 */
		adminCtnt = this.adminService.getAdminCnt(adminSelectReq);


			/* 2-2. adminList 정보 */
			try {
				adminList = this.adminService.getAdminList(adminSelectReq);
				resultCode = HttpStatus.OK.value();
				resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); //정상적으로 조회 하였습니다.

			} catch (CustomMessageException e) {
				resultCode = HttpStatus.OK.value();
				resultMsg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception 하면서 보낸 메세지

				//로그에 넣기
				log.error("\n===============ERROR=========================\n" + e.getMessage());
				log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
			} catch(Exception e) {
				throw e;
			}

		//[[3]].데이터 셋팅
		commonRes.setTotCnt(adminCtnt);
		commonRes.setList(adminList);

		return new ResponseEntity<>(
				ResponseDto.builder()
			   .data(commonRes)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build(),
			   HttpStatus.OK);
	}

	/*****************************************************
	 * 관리자 추가 팝업 페이지
	 * @param
	 * @param
	 * @return String
	*****************************************************/
	@GetMapping("/admin/adminAddPopUp")
	public String addPop(Model model) {
		return "system/admin/adminAddPopUp";
	}

	/*****************************************************
	 * 관리자 아이디 중복 여부 확인
	 * @param
	 * @param
	 * @return String
	*****************************************************/
	@PostMapping("/admin/checkAdminId")
	public ResponseEntity<ResponseDto> checkAdminId(@RequestParam String adminId) {

		//[[0]]. 반환할 정보들
		int count 			= 0;
		String  resultMsg 	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode	= HttpStatus.BAD_REQUEST.value();

		//[[1]]. 데이터 조회
		try {
			/* 1-1. 아이디 중복 조회 결과 */
			count		= adminService.getAdminIdCheck(adminId);

			resultCode	= HttpStatus.OK.value();
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); //정상적으로 조회 하였습니다.
		} catch (CustomMessageException e) {
			resultCode	= HttpStatus.OK.value();
			resultMsg	= commService.rtnMessageDfError(e.getMessage());

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		} catch(Exception e) {
			throw e;
		}
		//[[2]]. 반환 데이터
		return new ResponseEntity<>(
				ResponseDto.builder()
				   .data(count)
				   .resultMessage(resultMsg)
				   .resultCode(resultCode)
				   .build(),
				   HttpStatus.OK);
	}

	/*****************************************************
	 * 관리자 등록
	 * @param model
	 * @param adminSaveReq
	 * @return String
	*****************************************************/
	@PostMapping("/admin/regAdmin")
	public ResponseEntity<ResponseDto> regAdmin(@ModelAttribute AdminSaveReq adminData) {

		//[[0]].반환할 정보들
		String resultMsg	= messageSource.getMessage("result-message.messages.common.message.save.error", null, SessionUtil.getLocale()); //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode	= HttpStatus.BAD_REQUEST.value();
		int result			= 0;

		//[[1]]. 저장
		try {
			/* 1-1. 관리자 데이터 등록  */
			result		= adminService.regAdmin(adminData);

			resultCode	= HttpStatus.OK.value();
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.insert", null, SessionUtil.getLocale());	//정상적으로 등록 하였습니다.

		} catch (CustomMessageException e) {
			resultCode	= HttpStatus.OK.value();
			resultMsg	= commService.rtnMessageDfError(e.getMessage());

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		} catch(Exception e) {
			throw e;
		}

		//[[2]].데이터 셋팅
		return new ResponseEntity<>(
				ResponseDto.builder()
			   .data(result)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build(),
			   HttpStatus.OK);
	}

	/*****************************************************
	 * 관리자 상세
	 * @param model
	 * @param adminSaveReq
	 * @return String
	*****************************************************/
	@GetMapping("/admin/adminDetailPopUp")
	public String adminDetail() {
		return "/system/admin/adminDetailPopUp";
	}

	/*****************************************************
	 * 관리자 상세 (권한 목록 조회)
	 * @param  String
	 * @param
	 * @return ResponseEntity
	*****************************************************/
	@PostMapping("/admin/getAdminDetail")
	public ResponseEntity<ResponseDto> getAdminDetail (@RequestParam String id) {
		//[[0]].반환할 정보들
		String resultMsg			= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode			= HttpStatus.BAD_REQUEST.value();
		AdminSelectRes adminDetail	= null;

		//[[1]]. 조회
		try {
			adminDetail		= adminService.getAdminDetail(id);

			resultCode		= HttpStatus.OK.value();
			resultMsg		= messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); //정상적으로 조회 하였습니다.

		} catch (CustomMessageException e) {
			resultCode		= HttpStatus.OK.value();
			resultMsg		= commService.rtnMessageDfError(e.getMessage());

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		} catch(Exception e) {
			throw e;
		}

		//[[2]].데이터 셋팅
		return new ResponseEntity<>(
				ResponseDto.builder()
			  . data(adminDetail)
			  .resultMessage(resultMsg)
			  .resultCode(resultCode)
			  .build(),
			  HttpStatus.OK);
	}

	/*****************************************************
	 * 관리자 수정
	 * @param  AdminSaveReq
	 * @param
	 * @return ResponseEntity
	*****************************************************/
	@PostMapping("/admin/modifyAdmin")
	public ResponseEntity<ResponseDto> modifyAdmin(@ModelAttribute AdminSaveReq modifyData){

		//[[0]].반환할 정보들
		String resultMsg	= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode	= HttpStatus.BAD_REQUEST.value();
		int count 			= 0;

		//[[1]]. 수정 데이터
		try {
			count	= adminService.modifyAdmin(modifyData);

			resultCode	= HttpStatus.OK.value();
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.update", null, SessionUtil.getLocale()); //정상적으로 수정하였습니다.
		} catch (CustomMessageException e) {
			resultCode	= HttpStatus.OK.value();
			resultMsg	= commService.rtnMessageDfError(e.getMessage());

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		} catch(Exception e) {
			throw e;
		}

		//[[2]].
		return new ResponseEntity<>(
				ResponseDto.builder()
			   .data(count)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * 권한 목록 조회
	 * @param
	 * @return ResponseEntity<ResponseDto>
	*****************************************************/
	@PostMapping("/admin/getAuthorList")
	public ResponseEntity<ResponseDto> getAuthorList() {
		//[[0]].반환할 정보들
		String resultMsg					= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode					= HttpStatus.BAD_REQUEST.value();
		List<AuthorSelectRes> authorList	= null;

		//[[1]].데이터 조회
		try {
			authorList	= adminService.getAuthorList();

			resultCode	= HttpStatus.OK.value();
			resultMsg	= messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); //정상적으로 조회 하였습니다.
		} catch (CustomMessageException e) {
			resultCode		= HttpStatus.OK.value();
			resultMsg		= commService.rtnMessageDfError(e.getMessage());

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
			   .data(authorList)
			   .resultMessage(resultMsg)
			   .resultCode(resultCode)
			   .build(),
				HttpStatus.OK);


	}

}

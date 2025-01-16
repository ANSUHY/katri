package com.katri.web.mypage.accountMng.controller;

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

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.Common;
import com.katri.common.model.CommonRes;
import com.katri.common.model.ResponseDto;
import com.katri.common.tlds.model.CommonTldRes;
import com.katri.common.util.CommonUtil;
import com.katri.common.util.SessionUtil;
import com.katri.web.auth.model.LoginReq;
import com.katri.web.auth.model.LoginRes;
import com.katri.web.auth.service.LoginService;
import com.katri.web.comm.service.CommService;
import com.katri.web.mypage.accountMng.model.AccountMngSaveReq;
import com.katri.web.mypage.accountMng.model.AccountMngSaveRes;
import com.katri.web.mypage.accountMng.model.AccountMngSelectReq;
import com.katri.web.mypage.accountMng.model.AccountMngSelectRes;
import com.katri.web.mypage.accountMng.service.AccountMngService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Api(tags = {"accountMng Controller"})
@RequestMapping(value = "/mypage/accountMng")
@Slf4j
public class AccountMngController {

	/** 공통 Service */
	private final CommService commService;

	/** Login Service */
	private final LoginService loginService;

	/** AccountMng Service */
	private final AccountMngService accountMngService;

	/** 메시지 Component */
	private final MessageSource messageSource;

	/*****************************************************
	 * [계정 관리] > 종속된 하위 기업/기관 목록 페이지 이동
	 * @param model
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/accountMngList"})
	public String accountMngList(Model model, @ModelAttribute AccountMngSelectReq accountMngSelectReq) throws Exception {

		LoginReq loginReq					= new LoginReq();
		LoginRes loginRes					= new LoginRes();

 		loginReq.setLoginId(SessionUtil.getLoginUserId());
		loginRes = loginService.selectLoginDetail(loginReq);

		// [0]. 검색 조건 회원 상태 목록 조회
		List<CommonTldRes> lstSttCd = accountMngService.getSearchUserSttCdList();
		if( lstSttCd != null ) {
			model.addAttribute("lstSttCd"		, lstSttCd);
		}

		model.addAttribute("userInfoData"		, loginRes);
		model.addAttribute("accountSearchData"	, accountMngSelectReq);

		return "mypage/accountMng/accountMngList";

	}

	/*****************************************************
	 * [계정 관리] > 종속된 하위 기업/기관 목록 데이터 조회
	 * @param model
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/getAccountMngList"})
	public ResponseEntity<ResponseDto> getAccountMngList( Model model, @ModelAttribute AccountMngSelectReq accountMngSelectReq ) throws Exception {

		String 						msg 		= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer 					resultCode 	= HttpStatus.BAD_REQUEST.value();
		List<AccountMngSelectRes> 	accountList = null;
		CommonRes					commonRes	= new CommonRes();

		try {

			// [0]. 현재 로그인한 계정 정보 조회
			LoginReq loginReq					= new LoginReq();
			LoginRes loginRes					= new LoginRes();

	 		loginReq.setLoginId(SessionUtil.getLoginUserId());
			loginRes = loginService.selectLoginDetail(loginReq);

			// [1]. Paging 변수 설정
			Common common = CommonUtil.setPageParams(accountMngSelectReq.getCurrPage(), accountMngSelectReq.getRowCount());
			accountMngSelectReq.setStartRow(common.getStartRow());
			accountMngSelectReq.setEndRow(common.getEndRow());

			// [2]. 데이터 조회 - 현재 로그인한 회원 값 셋팅
			accountMngSelectReq.setTargetUserId( loginRes.getUserId() );
			accountMngSelectReq.setTargetUserTyCd( loginRes.getUserTyCd() );

			// [2_0]. 계정 관리 목록 개수 조회
			Integer nTotCnt	= accountMngService.getAccountMngListCnt(accountMngSelectReq);

			// [2_1]. 계정 관리 목록 조회
			if( nTotCnt > 0 ) {
				accountList	= accountMngService.getAccountMngList(accountMngSelectReq);
				resultCode	= HttpStatus.OK.value();
			} else {
				resultCode = HttpStatus.OK.value();
			}

			// [3]. 데이터 셋팅
			commonRes.setTotCnt(nTotCnt);
			commonRes.setList(accountList);

		} catch (CustomMessageException e) {
			e.printStackTrace();

			resultCode	= HttpStatus.BAD_REQUEST.value();
			msg 		= commService.rtnMessageDfError(e.getMessage()); // service에서 exception하면서 보낸 메시지
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

		} catch(Exception e) {
			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(commonRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * [계정 관리] > 종속된 하위 기업/기관 상세 데이터 조회
	 * @param model
	 * @param accountMngSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/accountMngDetail"})
	public String accountMngDetail(Model model, @ModelAttribute AccountMngSelectReq accountMngSelectReq) throws Exception {

		// [0]. 현재 로그인한 계정 정보 조회
		LoginReq loginReq					= new LoginReq();
		LoginRes loginRes					= new LoginRes();

 		loginReq.setLoginId(SessionUtil.getLoginUserId());
		loginRes = loginService.selectLoginDetail(loginReq);

		// [1]. 데이터 조회 - 현재 로그인한 회원 값 셋팅
		accountMngSelectReq.setTargetUserId( loginRes.getUserId() );
		accountMngSelectReq.setTargetUserTyCd( loginRes.getUserTyCd() );

		// [2]. 데이터 상세 조회 - 조회할 계정 아이디
		AccountMngSelectRes accountMngSelectRes = new AccountMngSelectRes();

		accountMngSelectRes = accountMngService.getAccountMngDetail(accountMngSelectReq);

		model.addAttribute("userInfoData"		, loginRes);
		model.addAttribute("accountUserData"	, accountMngSelectRes);
		model.addAttribute("accountSearchData"	, accountMngSelectReq);

		return "mypage/accountMng/accountMngDetail";

	}


	/*****************************************************
	 * [계정 관리] > 회원 가입 승인/반려 처리
	 * @param request
	 * @param accountMngSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/saveJoinAppr"})
	public ResponseEntity<ResponseDto> saveJoinAppr( HttpServletRequest request, @ModelAttribute AccountMngSaveReq accountMngSaveReq ) throws Exception {

		// [0]. 반환할 정보들
		String 				msg 				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer 			resultCode 			= HttpStatus.BAD_REQUEST.value();
		AccountMngSaveRes	accountMngSaveRes	= new AccountMngSaveRes();

		try {
			// [1]. 현재 로그인한 마스터 정보 셋팅
			LoginReq loginReq					= new LoginReq();
			LoginRes loginRes					= new LoginRes();

	 		loginReq.setLoginId(SessionUtil.getLoginUserId());
			loginRes = loginService.selectLoginDetail(loginReq);

			// [2]. 처리 시작
			accountMngSaveRes = accountMngService.saveJoinAppr(request, accountMngSaveReq, loginRes);

			// [3]. 성공 여부에 따른 메시지 셋팅 */
			if( "".equals( accountMngSaveRes.getUserId() ) ) {
				resultCode = HttpStatus.BAD_REQUEST.value();
				msg = messageSource.getMessage("result-message.messags.common.message.save.error", null, SessionUtil.getLocale()); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			} else {
				resultCode = HttpStatus.OK.value();

				if( "Y".equals(accountMngSaveReq.getApprFlag()) ) {
					msg = messageSource.getMessage("result-message.messages.mypage.account.mng.message.join.approval.success", null, SessionUtil.getLocale()); // 가입 신청이 최종 승인이 되었습니다.
				} else {
					msg = messageSource.getMessage("result-message.messages.mypage.account.mng.message.join.reject.success", null, SessionUtil.getLocale()); // 가입 신청이 반려 처리 되었습니다.
				}
			}

		} catch(CustomMessageException e) {

			// 값 셋팅
			resultCode	= HttpStatus.BAD_REQUEST.value();
			msg 		= commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			// 로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

		} catch(Exception e) {

			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(accountMngSaveRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}


	/*****************************************************
	 * [계정 관리] > 회원 탈퇴 처리
	 * @param request
	 * @param accountMngSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/saveWhdwlPrcs"})
	public ResponseEntity<ResponseDto> saveWhdwlPrcs( HttpServletRequest request, @ModelAttribute AccountMngSaveReq accountMngSaveReq ) throws Exception {

		// [0]. 반환할 정보들
		String 				msg 				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer 			resultCode 			= HttpStatus.BAD_REQUEST.value();
		AccountMngSaveRes	accountMngSaveRes	= new AccountMngSaveRes();

		try {
			// [1]. 현재 로그인한 마스터 정보 셋팅
			LoginReq loginReq					= new LoginReq();
			LoginRes loginRes					= new LoginRes();

			loginReq.setLoginId(SessionUtil.getLoginUserId());
			loginRes = loginService.selectLoginDetail(loginReq);

			// [2]. 처리 시작
			accountMngSaveRes = accountMngService.saveWhdwlPrcs(request, accountMngSaveReq, loginRes);

			// [3]. 성공 여부에 따른 메시지 셋팅 */
			if( "".equals( accountMngSaveRes.getUserId() ) ) {
				resultCode = HttpStatus.BAD_REQUEST.value();
				msg = messageSource.getMessage("result-message.messags.common.message.save.error", null, SessionUtil.getLocale()); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			} else {
				resultCode = HttpStatus.OK.value();
				msg = messageSource.getMessage("result-message.messages.mypage.info.mng.message.whdwl.success", null, SessionUtil.getLocale()); // 탈퇴 처리가 완료되었습니다.
			}

		} catch(CustomMessageException e) {

			// 값 셋팅
			resultCode	= HttpStatus.BAD_REQUEST.value();
			msg 		= commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			// 로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

		} catch(Exception e) {

			throw e;
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(accountMngSaveRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

}

package com.katri.web.mypage.accountMng.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.Const;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.tlds.model.CommonTldRes;
import com.katri.common.util.EncryptUtil;
import com.katri.common.util.StringUtil;
import com.katri.web.auth.model.LoginRes;
import com.katri.web.comm.model.MailMakeBodyDto;
import com.katri.web.comm.model.MailSendDto;
import com.katri.web.comm.model.MailSendReceiverDto;
import com.katri.web.comm.service.AnlsApiService;
import com.katri.web.comm.service.MailService;
import com.katri.web.mypage.accountMng.mapper.AccountMngMapper;
import com.katri.web.mypage.accountMng.model.AccountMngPrdtSelectRes;
import com.katri.web.mypage.accountMng.model.AccountMngSaveReq;
import com.katri.web.mypage.accountMng.model.AccountMngSaveRes;
import com.katri.web.mypage.accountMng.model.AccountMngSelectReq;
import com.katri.web.mypage.accountMng.model.AccountMngSelectRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class AccountMngService {

	/** 메일 Service */
	private final MailService mailService;

	/** API Service */
	private final AnlsApiService anlsApiService;

	/** AccountMng Mapper */
	private final AccountMngMapper accountMngMapper;

	/*****************************************************
	 * [계정 관리] > 계정 관리 목록 개수 조회
	 * @param accountMngSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public Integer getAccountMngListCnt(AccountMngSelectReq accountMngSelectReq) throws Exception {

		Integer nTotCnt = 0;

		nTotCnt = accountMngMapper.selectAccountMngListCnt( accountMngSelectReq );

		return nTotCnt;
	}

	/*****************************************************
	 * [계정 관리] > 계정 관리 목록 데이터 조회
	 * @param accountMngSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public List<AccountMngSelectRes> getAccountMngList(AccountMngSelectReq accountMngSelectReq) throws Exception {

		List<AccountMngSelectRes> accountMngList = null;

		// [0]. 계정 관리 목록 조회
		accountMngList = accountMngMapper.selectAccountMngList( accountMngSelectReq );

		// [1]. 문자열 포맷팅 처리
		for( AccountMngSelectRes tmpAcntRes : accountMngList) {

			// [1_0]. 아이디 마스킹
			String strMaskUserId = StringUtil.stringFormatType( tmpAcntRes.getUserId() , "masking", "id" );
			tmpAcntRes.setMaskUserId( strMaskUserId );

			// [1_1]. 이름 마스킹
			if ( !"x".equals( tmpAcntRes.getUserNm() ) ) {
				String strMaskUserNm = StringUtil.stringFormatType( tmpAcntRes.getUserNm() , "masking", "name" );
				tmpAcntRes.setMaskUserNm( strMaskUserNm );
			} else {
				tmpAcntRes.setMaskUserNm( tmpAcntRes.getUserNm() );
			}

			// [1_2]. 휴대폰 복호화 -> 마스킹 -> 포맷팅
			if( !"x".equals( tmpAcntRes.getEncptMblTelnoVal() ) ) {
				String mblTelNo 	= StringUtil.nvl(EncryptUtil.decryptAes256( tmpAcntRes.getEncptMblTelnoVal() ));
				String maskMblTelno = StringUtil.stringFormatType( mblTelNo , "masking", "phone" );
				tmpAcntRes.setMaskMblTelno( StringUtil.stringFormatType( maskMblTelno, "phone", null) );
			} else {
				tmpAcntRes.setMaskMblTelno( tmpAcntRes.getEncptMblTelnoVal() );
			}

			// [1_3]. 이메일 복호화 -> 마스킹
			if( !"x".equals( tmpAcntRes.getEncptEmlAddrVal() ) ) {
				String emlAddr		= StringUtil.nvl(EncryptUtil.decryptAes256( tmpAcntRes.getEncptEmlAddrVal() ));
				String maskEmlAddr	= StringUtil.stringFormatType( emlAddr , "masking", "email" );
				tmpAcntRes.setMaskEmlAddr( maskEmlAddr );
			} else {
				tmpAcntRes.setMaskEmlAddr( tmpAcntRes.getEncptEmlAddrVal() );
			}

			// [1_4]. 가입일자 날짜 형식 포맷팅
			tmpAcntRes.setJoinYmd( StringUtil.stringFormatType( tmpAcntRes.getJoinYmd() , "date", "YYYY.MM.DD") );
		}

		return accountMngList;
	}

	/*****************************************************
	 * [계정 관리] > 계정 관리 상세 데이터 조회
	 * @param accountMngSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public AccountMngSelectRes getAccountMngDetail(AccountMngSelectReq accountMngSelectReq) throws Exception {

		AccountMngSelectRes accountMngSelectRes = null;

		// [0]. 상세 조회
		accountMngSelectRes = accountMngMapper.selectAccountMngDetail(accountMngSelectReq);

		// [1]. 조회 결과 복호화
		if ( accountMngSelectRes != null ) {

			// [0_0]. 탈퇴한 회원 상세 페이지 x
			if( Const.Code.UserSttCd.WITHDRAWAL.equals(accountMngSelectRes.getUserSttCd()) ) {
				throw new CustomMessageException("result-message.messages.mypage.account.mng.message.user.stt.whdwl"); // 탈퇴 처리된 회원입니다.
			} else if ( Const.Code.UserSttCd.JOIN_REJECT.equals(accountMngSelectRes.getUserSttCd()) ) {
				throw new CustomMessageException("result-message.messages.mypage.account.mng.message.user.stt.reject"); // 가입 반려 처리된 회원입니다.
			}

			// [1_0]. 휴대폰 복호화
			String mblTelNo 	= StringUtil.nvl(EncryptUtil.decryptAes256( accountMngSelectRes.getEncptMblTelnoVal() ));
			accountMngSelectRes.setEncptMblTelnoVal( mblTelNo );

			// [1_1]. 이메일 복호화
			String emlAddr		= StringUtil.nvl(EncryptUtil.decryptAes256( accountMngSelectRes.getEncptEmlAddrVal() ));
			accountMngSelectRes.setEncptEmlAddrVal( emlAddr );

		} else {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		// [2]. 관심 키워드 조회 - 기업(일반)인 경우
		if( Const.Code.UserTyCd.ENT_GENERAL.equals(accountMngSelectRes.getUserTyCd()) ) {
			List<AccountMngPrdtSelectRes> lstPrdt = accountMngMapper.selectUserPrdtClfChcMngList(accountMngSelectReq);
			accountMngSelectRes.setLstPrdt(lstPrdt);
		}

		return accountMngSelectRes;
	}


	/*****************************************************
	 * [계정 관리] > 회원 가입 승인/반려 처리
	 * @param request
	 * @param accountMngSaveReq
	 * @param loginRes
	 * @return
	 * @throws Exception
	*****************************************************/
	public AccountMngSaveRes saveJoinAppr(HttpServletRequest request, AccountMngSaveReq accountMngSaveReq, LoginRes loginRes) throws Exception {

		// 0. 값 셋팅
		String 				strSaveErrMsgCode 	= "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		AccountMngSaveRes 	accuntMngSaveRes 	= new AccountMngSaveRes();
		int 				nSaveCount			= 0;

		/** ================================= [0]. 회원 상세 조회 ================================ */
		AccountMngSelectReq accountMngSelectReq = new AccountMngSelectReq();
		AccountMngSelectRes accountMngSelectRes = new AccountMngSelectRes();

		// [0_0]. 현재 로그인한 마스터의 종속되어 있는 회원 상세 조회
		accountMngSelectReq.setAccountUserId( accountMngSaveReq.getTargetUserId() ); // 상세 조회 대상 사용자아이디
		accountMngSelectReq.setTargetUserId( loginRes.getUserId() );				 // 로그인한(마스터) 사용자아이디
		accountMngSelectReq.setTargetUserTyCd( loginRes.getUserTyCd() );			 // 로그인한(마스터) 사용자유형코드

		// [0_1]. 상세 조회
		accountMngSelectRes = this.getAccountMngDetail(accountMngSelectReq);
		/** ====================================================================================== */

		/** =================================== [1]. 유효성 검사 ================================= */
		// [1_0]. 조회한 상세 데이터가 없는 경우
		if( accountMngSelectRes == null ) {
			strSaveErrMsgCode = "result-message.messages.common.message.no.data"; // 데이터가 없습니다.
			throw new CustomMessageException(strSaveErrMsgCode);
		}
		/** ====================================================================================== */

		/** ================================= [2]. 가입 승인/반려 처리 ================================ */
		// [2_0]. 현재 상태값 및 승인/반려 여부 셋팅
		String strNowUserSttCd	= accountMngSelectRes.getUserSttCd();
		String strNowUserTyCd	= accountMngSelectRes.getUserTyCd();
		String strApprFlag		= accountMngSaveReq.getApprFlag().toUpperCase();

		// [2_1]. 현재 [가입신청] 상태가 아닌 경우
		if( ! Const.Code.UserSttCd.JOIN_APPLY.equals( strNowUserSttCd ) ) {
			strSaveErrMsgCode = "result-message.messages.mypage.account.mng.message.user.stt.prcs.error" + "||msgArgu=가입 신청||"; // 현재 회원이 {0} 상태가 아닙니다. 다시 확인해 주세요.
			throw new CustomMessageException(strSaveErrMsgCode);
		}

		// [2_2]. 회원 유형 별 처리 ( 기업(일반), 기관(일반) )
		AccountMngSaveReq chgAccountSaveReq = new AccountMngSaveReq();
		chgAccountSaveReq.setMdfrId( loginRes.getUserId() );					// 수정자 - 로그인한 마스터
		chgAccountSaveReq.setTargetUserId( accountMngSelectRes.getUserId() );	// 대상 사용자 아이디

		if( Const.Code.UserTyCd.ENT_GENERAL.equals( strNowUserTyCd ) ) {

			// [2_2_0]. 기업(일반) - [가입 승인] 처리 시작
			if( "Y".equals(strApprFlag)) {

				// [2_2_0_0]. [가입 승인] > 사용자 상태코드 - 정상(USC020) 수정
				chgAccountSaveReq.setTargetUserSttCd( Const.Code.UserSttCd.NORMAL ); // 정상(USC020)
				nSaveCount += accountMngMapper.updateUserSttCd( chgAccountSaveReq );

				// [2_2_0_1]. [가입 승인] > 사용자 정보 수정
				chgAccountSaveReq.setJoinAplyPrcrId( loginRes.getUserId() ); // 가입신청처리자아이디
				nSaveCount += accountMngMapper.updateJoinUserBas( chgAccountSaveReq );

			}
			// [2_2_1]. 기업(일반) - [가입 반려] 처리 시작 -> DB 사용자 정보 삭제 처리
			else {

				// [2_2_1_0]. [가입 반려] > 사용자 DB 정보 삭제
				nSaveCount += accountMngMapper.deleteUserBas( chgAccountSaveReq );

				// [2_2_1_1]. [가입 반려] > 기업 사용자 관리 DB 정보 삭제
				nSaveCount += accountMngMapper.deleteEntGrpUserMng( chgAccountSaveReq );

				// [2_2_1_2]. [가입 반려] > 사용자 관심 키워드 DB 정보 삭제
				nSaveCount += accountMngMapper.deleteUserPrdtClfChcMng( chgAccountSaveReq );
			}

		} else if( Const.Code.UserTyCd.INST_GENERAL.equals( strNowUserTyCd ) ) {

			// [2_2_3]. 기관(일반) - [가입 승인] 처리 시작
			if( "Y".equals(strApprFlag)) {

				// [2_2_3_0]. [가입 승인] > 사용자 상태코드 - 정상(USC020) 수정
				chgAccountSaveReq.setTargetUserSttCd( Const.Code.UserSttCd.NORMAL ); // 정상(USC020)
				nSaveCount += accountMngMapper.updateUserSttCd( chgAccountSaveReq );

				// [2_2_3_1]. [가입 승인] > 사용자 정보 수정
				chgAccountSaveReq.setJoinAplyPrcrId( loginRes.getUserId() ); // 가입신청처리자아이디
				nSaveCount += accountMngMapper.updateJoinUserBas( chgAccountSaveReq );
			}
			// [2_2_4]. 기관(일반) - [가입 반려] 처리 시작 -> DB 사용자 정보 'x' 처리
			else {

				// [2_2_4_0]. [가입 반려] > 사용자 상태코드 - 가입 반려(USC012) 수정
				chgAccountSaveReq.setTargetUserSttCd( Const.Code.UserSttCd.JOIN_REJECT ); // 가입 반려(USC012)
				nSaveCount += accountMngMapper.updateUserSttCd( chgAccountSaveReq );

				// [2_2_4_1]. [가입 반려] > 사용자 정보 수정 - 사용자 정보 'x' 처리
				nSaveCount += accountMngMapper.updateRejectUserBas( chgAccountSaveReq );

				// [2_2_4_2]. [가입 반려] > 기관(일반) 사용자 관리 정보 수정 - 기관 사용자 정보 'x' 처리
				nSaveCount += accountMngMapper.updateRejectInstUserMng( chgAccountSaveReq );

				// [2_2_4_3]. API 요청 값 셋팅
				Map<String, Object> mapRequestData 	= new HashMap<String, Object>();
				Map<String, Object> mapResponseData = new HashMap<String, Object>();

				mapRequestData.put("id", accountMngSelectRes.getAnlsEnvUserId() );

				// [2_2_4_4]. USER 삭제 API 호출
				mapResponseData = anlsApiService.apiConnectionUserDeleteInit(mapRequestData);

				if( mapResponseData == null || mapResponseData.isEmpty() ) {
					throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
				}

			}
		}

		if(! (nSaveCount > 0 ) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ====================================================================================== */

		/** ===================================== [3]. 메일 전송 ==================================== */
		// [3_0]. 상태 별 메일 전송값 셋팅
		Map<String, Object> mapMail		 = new HashMap<String, Object>();
		boolean 			bMailSuccess = false;

		// [3_1]. 가입 승인 메일 전송
		if( "Y".equals(strApprFlag)) {

			// [3_1_0]. [승인] 메일 전송값 셋팅
			mapMail.put("mailTmepCd"	, Const.Code.MailTemplateCd.MEM_JOIN_COMP_INST_ENT_GENERAL); // 기업/기관일반회원가입승인완료
			mapMail.put("mailSubject"	, "시험인증 빅데이터 가입 완료 안내");
		}
		// [3_2]. 가입 반려 메일 전송
		else {

			// [3_2_0]. [반려] 메일 전송값 셋팅
			mapMail.put("mailTmepCd"	, Const.Code.MailTemplateCd.MEM_REGI_REJECT); // 회원가입반려
			mapMail.put("mailSubject"	, "회원가입 반려 안내");
		}

		// [3_3]. 메일 전송
		bMailSuccess = sendJoinWhdwlApprMail( request, accountMngSelectRes, mapMail );

		if( !bMailSuccess ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ====================================================================================== */

		/** ==================================== [4]. 반환값 셋팅 =================================== */
		accuntMngSaveRes.setUserId( accountMngSelectRes.getUserId() );
		accuntMngSaveRes.setUserTyCd( accountMngSelectRes.getUserTyCd() );
		accuntMngSaveRes.setApprFlag( strApprFlag );
		/** ====================================================================================== */

		return accuntMngSaveRes;
	}

	/*****************************************************
	 * [계정 관리] > 회원 탈퇴 처리
	 * @param request
	 * @param accountMngSaveReq
	 * @param loginRes
	 * @return
	 * @throws Exception
	*****************************************************/
	public AccountMngSaveRes saveWhdwlPrcs(HttpServletRequest request, AccountMngSaveReq accountMngSaveReq, LoginRes loginRes) throws Exception {

		// 0. 값 셋팅
		String 				strSaveErrMsgCode 	= "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		AccountMngSaveRes 	accuntMngSaveRes 	= new AccountMngSaveRes();
		int 				nSaveCount			= 0;

		/** ================================= [0]. 회원 상세 조회 ================================ */
		AccountMngSelectReq accountMngSelectReq = new AccountMngSelectReq();
		AccountMngSelectRes accountMngSelectRes = new AccountMngSelectRes();

		// [0_0]. 현재 로그인한 마스터의 종속되어 있는 회원 상세 조회
		accountMngSelectReq.setAccountUserId( accountMngSaveReq.getTargetUserId() ); // 상세 조회 대상 사용자아이디
		accountMngSelectReq.setTargetUserId( loginRes.getUserId() );				 // 로그인한(마스터) 사용자아이디
		accountMngSelectReq.setTargetUserTyCd( loginRes.getUserTyCd() );			 // 로그인한(마스터) 사용자유형코드

		// [0_1]. 상세 조회
		accountMngSelectRes = this.getAccountMngDetail(accountMngSelectReq);
		/** ====================================================================================== */

		/** =================================== [1]. 유효성 검사 ================================= */
		// [1_0]. 조회한 상세 데이터가 없는 경우
		if( accountMngSelectRes == null ) {
			strSaveErrMsgCode = "result-message.messages.common.message.no.data"; // 데이터가 없습니다.
			throw new CustomMessageException(strSaveErrMsgCode);
		}
		/** ====================================================================================== */

		/** =================================== [2]. 탈퇴 처리 시작 ================================= */
		String strNowUserSttCd	= accountMngSelectRes.getUserSttCd();
		String strNowUserTyCd	= accountMngSelectRes.getUserTyCd();

		// [2_1]. 현재 [정상] 또는 [휴면] 상태가 아닌 경우
		if( Const.Code.UserSttCd.JOIN_APPLY.equals( strNowUserSttCd ) || Const.Code.UserSttCd.WITHDRAWAL.equals( strNowUserSttCd ) || Const.Code.UserSttCd.WITHDRAWAL_APPLY.equals( strNowUserSttCd ) ) {
			strSaveErrMsgCode = "result-message.messages.mypage.account.mng.message.user.stt.prcs.error" + "||msgArgu=정상 또는 휴면||"; // 현재 회원이 {0} 상태가 아닙니다. 다시 확인해 주세요.
			throw new CustomMessageException(strSaveErrMsgCode);
		}

		// [2_0]. 회원 유형 별 처리 ( 기업(일반), 기관(일반) )
		AccountMngSaveReq chgAccountSaveReq = new AccountMngSaveReq();
		chgAccountSaveReq.setMdfrId( loginRes.getUserId() );					// 수정자 - 로그인한 마스터
		chgAccountSaveReq.setTargetUserId( accountMngSelectRes.getUserId() );	// 대상 사용자 아이디

		// [2_1]. 기업(일반) 유형, 기관(일반) 유형 - 회원 상태코드 > 탈퇴(USC040) 수정
		chgAccountSaveReq.setTargetUserSttCd( Const.Code.UserSttCd.WITHDRAWAL ); // 상태값 - 탈퇴(USC040) 셋팅
		nSaveCount += accountMngMapper.updateUserSttCd( chgAccountSaveReq );

		// [2_2]. [탈퇴 완료] > 사용자 정보 수정
		chgAccountSaveReq.setWhdwlAplyPrcrId( loginRes.getUserId() );
		nSaveCount += accountMngMapper.updateWhdwlUserBas( chgAccountSaveReq );

		// [2_3]. 기업(일반) or 기관(일반) 사용자 정보 수정
		if( Const.Code.UserTyCd.INST_GENERAL.equals( strNowUserTyCd ) ) {

			// [2_3_0]. [탈퇴 완료] > 기관(일반) 사용자 관리 정보 수정
			nSaveCount += accountMngMapper.updateWhdwlInstUserMng( chgAccountSaveReq );

		} else if ( Const.Code.UserTyCd.ENT_GENERAL.equals( strNowUserTyCd ) ) {

			// [2_3_1]. [탈퇴 완료] > 기업(일반) 사용자 관리 정보 수정
			nSaveCount += accountMngMapper.updateWhdwlEntGrpUserMng( chgAccountSaveReq );
		}

		if(! (nSaveCount > 0 ) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ======================================================================================= */

		/** ================================= [3]. 게시글 및 관심 키워드 삭제 ============================ */
		// [3_0]. 게시글 관련 삭제 처리 -- 등록된 글이 없을 수 있기 때문에 따로 오류 처리 x
		accountMngMapper.updateWhdwlNttMngDelete( chgAccountSaveReq );

		// [3_1]. 관심 키워드 삭제 처리
		accountMngMapper.deleteUserPrdtClfChcMng( chgAccountSaveReq );
		/** ===================================================================================== */

		/** ==================== [4]. [기관(일반)] -> 데이터 분석환경 API 호출( USER 삭제 ) ================= */
		if( Const.Code.UserTyCd.INST_GENERAL.equals(strNowUserTyCd) ) {
			// [4_0]. API 요청 값 셋팅
			Map<String, Object> mapRequestData 	= new HashMap<String, Object>();
			Map<String, Object> mapResponseData = new HashMap<String, Object>();

			mapRequestData.put("id", accountMngSelectRes.getAnlsEnvUserId() );

			// [4_1]. USER 삭제 API 호출
			mapResponseData = anlsApiService.apiConnectionUserDeleteInit(mapRequestData);

			if( mapResponseData == null || mapResponseData.isEmpty() ) {
				throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}
		}
		/** ======================================================================================= */

		/** ===================================== [5]. 메일 전송 =================================== */
		// [5_0]. 상태 별 메일 전송값 셋팅
		Map<String, Object> mapMail		 = new HashMap<String, Object>();
		boolean 			bMailSuccess = false;

		// [5_1]. 탈퇴 메일 전송
		mapMail.put("mailTmepCd"	, Const.Code.MailTemplateCd.MEM_WITHDRAWAL_FORCE_INST_ENT_GENERAL); // 기관/기업일반회원강제탈퇴
		mapMail.put("mailSubject"	, "시험인증 빅데이터 플랫폼 회원 탈퇴 안내");

		// [5_2]. 메일 전송
		bMailSuccess = sendJoinWhdwlApprMail( request, accountMngSelectRes, mapMail );

		if( !bMailSuccess ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ====================================================================================== */

		/** ===================================== [6]. 반환값 셋팅 =================================== */
		accuntMngSaveRes.setUserId( accountMngSelectRes.getUserId() );
		/** ====================================================================================== */

		return accuntMngSaveRes;
	}

	/*****************************************************
	 * 가입 승인/반려, 탈퇴 처리 메일 발송
	 * @param request
	 * @param accountMngSelectRes
	 * @param mapMail
	 * @return
	 * @throws Exception
	*****************************************************/
	public boolean sendJoinWhdwlApprMail(HttpServletRequest request, AccountMngSelectRes accountMngSelectRes, Map<String, Object> mapMail) throws Exception {

		boolean success = false;

		/* [[1]]. 메일 body 생성 */
		// 1-1. mailParam설정
		Map<String, Object> mailParam = new HashMap<String, Object>();
		mailParam.put( "joinYmd", StringUtil.stringFormatType("today", "date", "YYYY-MM-DD") );
		mailParam.put( "userId" , StringUtil.stringFormatType( accountMngSelectRes.getUserId() , "masking" , "id") ); // 마스킹 처리

		// 1-2. mail 템플릿 및 제목 셋팅
		String tempCd	= (String) mapMail.get("mailTmepCd");
		String subject	= (String) mapMail.get("mailSubject");

		// 1-3. body
		MailMakeBodyDto mailMakeBodyDto = new MailMakeBodyDto();
		mailMakeBodyDto.setMailTmepCd( tempCd );
		mailMakeBodyDto.setMailParam(mailParam); // 메일 param (메일내용에 들어갈 param들)
		String sBody = mailService.makeMailBody(request, mailMakeBodyDto);

		/* [[2]]. 메일보내기 */
		if(! "".equals(sBody)) {

			// 2-1. 수신자 LIST
			List<MailSendReceiverDto> list_resiver = new ArrayList<MailSendReceiverDto>();
			list_resiver.add( new MailSendReceiverDto( accountMngSelectRes.getEncptEmlAddrVal() ));

			// 2-2. DTO생성 후 메일 보내기
			MailSendDto mailSendDto = new MailSendDto();
			mailSendDto.setSubject( subject );
			mailSendDto.setBody(sBody);
			mailSendDto.setReceiverList(list_resiver);
			success = mailService.sendMail(mailSendDto);
		}

		return success;
	}

	/*****************************************************
	 * 검색 조건 회원 상태 목록 조회
	 * @return
	*****************************************************/
	public List<CommonTldRes> getSearchUserSttCdList() {

		List<CommonTldRes> lstSttCd = null;

		// [0]. 검색 조건 회원 상태 목록 조회
		lstSttCd = accountMngMapper.selectSearchUserSttCdList();

		return lstSttCd;
	}

}

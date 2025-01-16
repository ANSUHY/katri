package com.katri.web.user.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.Const;
import com.katri.common.FileConst;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.EncryptUtil;
import com.katri.common.util.SessionUtil;
import com.katri.common.util.StringUtil;
import com.katri.web.comm.model.FileDto;
import com.katri.web.comm.model.MailMakeBodyDto;
import com.katri.web.comm.model.MailSendDto;
import com.katri.web.comm.model.MailSendReceiverDto;
import com.katri.web.comm.service.AnlsApiService;
import com.katri.web.comm.service.FileService;
import com.katri.web.comm.service.MailService;
import com.katri.web.user.user.mapper.UserMapper;
import com.katri.web.user.user.model.UserPrdtSelectRes;
import com.katri.web.user.user.model.UserSaveReq;
import com.katri.web.user.user.model.UserSaveRes;
import com.katri.web.user.user.model.UserSelectReq;
import com.katri.web.user.user.model.UserSelectRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class UserService {

	/** 파일 Service */
	private final FileService fileService;

	/** User Mapper */
	private final UserMapper userMapper;

	/** 메일 Service */
	private final MailService mailService;

	/** API Service */
	private final AnlsApiService anlsApiService;

	/*****************************************************
	 * 회원 현황 목록 개수 조회
	 * @param userSelectReq
	 * @return
	*****************************************************/
	public Integer getUserBasCnt(UserSelectReq userSelectReq) {

		int nTotCnt = 0;

		// 0. 회원 현황 목록 개수 조회
		nTotCnt = userMapper.selectUserBasCnt(userSelectReq);

		return nTotCnt;
	}

	/*****************************************************
	 * 회원 현황 목록 조회
	 * @param userSelectReq
	 * @return
	*****************************************************/
	public List<UserSelectRes> getUserBasList(UserSelectReq userSelectReq) {

		List<UserSelectRes> userList = null;

		// 0. 회원 현황 목록 조회
		userList = userMapper.selectUserBasList(userSelectReq);

		// 1. 회원 현황 ID 마스킹 처리
		for( UserSelectRes userSelectRes : userList ) {
			userSelectRes.setMaskingUserId( StringUtil.stringFormatType( userSelectRes.getUserId() , "masking" , "id") );
		}

		return userList;
	}

	/*****************************************************
	 * 회원 유형 별 상세 조회
	 * @param userSelectReq
	 * @return
	*****************************************************/
	public UserSelectRes getUserBasDetail(UserSelectReq userSelectReq) throws Exception {

		UserSelectRes userSelectRes = null;

		/** ========== [0]. 회원 유형 별 상세 조회 ========== */
		/* 0. 데이터 셋팅 : 기관 및 기업 */
		String strUserId = userSelectReq.getTargetUserId();
		String strUserTypeCd = userSelectReq.getTargetUserTypeCd().toUpperCase();

		if( !"".equals(strUserId) && !"".equals(strUserTypeCd) ) {

			if( Const.Code.UserTyCd.INST_MASTER.equals(strUserTypeCd) || Const.Code.UserTyCd.INST_GENERAL.equals(strUserTypeCd) ) {

				/* 0_1. 기관 회원 상세 정보 조회 */
				userSelectRes = userMapper.selectInstUserBasDetail(userSelectReq);

			} else if( Const.Code.UserTyCd.ENT_MASTER.equals(strUserTypeCd) || Const.Code.UserTyCd.ENT_GENERAL.equals(strUserTypeCd) ) {

				/* 0_2. 기업 회원 상세 정보 조회 */
				userSelectRes = userMapper.selectEntUserBasDetail(userSelectReq);

			} else {

				/* 0_3. 기업 회원 상세 정보 조회 */
				userSelectRes = userMapper.selectGnrlUserBasDetail(userSelectReq);

			}

		}

		if( userSelectRes != null ) {
		/** =============== [1]. 회원 파일 목록 조회 - 기업(마스터)만 해당 파일 존재함 =============== */
		/* 1. 파일 목록 조회 */
			/* 1_0. 파일 데이터 셋팅 - 조회한 ID  */
			FileDto fileDto = new FileDto();
			fileDto.setRefDivVal(userSelectRes.getUserId());

			/* 1_1. 파일 조회 - 재직 증명서 */
			fileDto.setFileDivNm(FileConst.File.FILE_DIV_NM_GNRL_CERT_FILE);
			List<FileDto> lstGnrlCertFile = this.fileService.selectFileList(fileDto);

			/* 1_2. 파일 조회 - 사업자 등록증 */
			fileDto.setFileDivNm(FileConst.File.FILE_DIV_NM_BZMN_REG_FILE);
			List<FileDto> lstBzmnRegFile = this.fileService.selectFileList(fileDto);

			/* 1_3. 파일 조회 - 계정발급 신청서 */
			fileDto.setFileDivNm(FileConst.File.FILE_DIV_NM_ACNT_ISSU_APLY_FILE);
			List<FileDto> lstAcntIssuAplyFile = this.fileService.selectFileList(fileDto);

			/* 1_4. 재직 증명서, 사업자 등록증, 계정발급 신청서 파일 list 정보 합치기 */
			List<FileDto> lstUserFile = new ArrayList<FileDto>();
			lstUserFile.addAll(lstGnrlCertFile);
			lstUserFile.addAll(lstBzmnRegFile);
			lstUserFile.addAll(lstAcntIssuAplyFile);

			/* 1_5. 반환값 셋팅 */
			userSelectRes.setLstUserFile(lstUserFile);

		/** =================================================== */

		/** =============== [2]. 회원 핸드폰 번호, 이메일 주소 복호화, 기타 문자열 처리 =============== */
			/* 2_1. 핸드폰 번호 복호화 - 'x' 탈퇴된 회원 */
			if( !"".equals(userSelectRes.getEncptMblTelnoVal()) && !"x".equals(userSelectRes.getEncptMblTelnoVal()) ) {
				String strEncTelNo = StringUtil.nvl(EncryptUtil.decryptAes256( userSelectRes.getEncptMblTelnoVal() ));
				userSelectRes.setEncptMblTelnoVal( StringUtil.stringFormatType(strEncTelNo, "phone", null)  );
			} else {
				userSelectRes.setEncptMblTelnoVal( userSelectRes.getEncptMblTelnoVal() );
			}

			/* 2_2. 이메일 주소 복호화 - 'x' 탈퇴된 회원 */
			if( !"".equals(userSelectRes.getEncptEmlAddrVal()) && !"x".equals(userSelectRes.getEncptEmlAddrVal()) ) {
				String strEncemlAddr = StringUtil.nvl(EncryptUtil.decryptAes256( userSelectRes.getEncptEmlAddrVal() ));
				userSelectRes.setEncptEmlAddrVal(strEncemlAddr);
			} else {
				userSelectRes.setEncptEmlAddrVal( userSelectRes.getEncptEmlAddrVal() );
			}

			/* 2_3. 직장 핸드폰 번호 문자열 처리 */
			userSelectRes.setWrcTelno( StringUtil.stringFormatType(userSelectRes.getWrcTelno(), "phone", null) );
		/** =============================================================== */

		/** =============== [3]. 회원 관심 키워드 조회 =============== */
			/* 3_0. 관심키워드 조회 */
			List<UserPrdtSelectRes> lstUserPrdt = userMapper.selectUserPrdtClfChcMngList(userSelectReq);

			/* 3_1. 키워드 목록 셋팅 */
			if( lstUserPrdt.size() > 0 ) {
				userSelectRes.setLstUserPrdt(lstUserPrdt);
			}
		/** =============================================================== */

		} else {

			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return userSelectRes;
	}

	/*****************************************************
	 * 회원 접속 로그 목록 개수 조회
	 * @param userSelectReq
	 * @return
	*****************************************************/
	public Integer getUserLogHistCnt(UserSelectReq userSelectReq) {

		int nTotCnt = 0;

		// 0. 회원 접속 로그 목록 개수 조회
		nTotCnt = userMapper.selectUserLogHistCnt(userSelectReq);

		return nTotCnt;
	}

	/*****************************************************
	 * 회원 접속 로그 목록 조회
	 * @param userSelectReq
	 * @return
	*****************************************************/
	public List<UserSelectRes> getUserLogHistList(UserSelectReq userSelectReq) {

		List<UserSelectRes> logHistList = null;

		// 0. 회원 접속 로그 목록 조회
		logHistList = userMapper.selectUserLogHistList(userSelectReq);

		return logHistList;
	}

	/*****************************************************
	 * 회원 약관 동의 이력 목록 개수 조회
	 * @param userSelectReq
	 * @return
	*****************************************************/
	public Integer getUserTrmsAgreHistCnt(UserSelectReq userSelectReq) {

		int nTotCnt = 0;

		// 0. 회원 약관 동의 이력 목록 개수 조회
		if( "JOIN".equals(userSelectReq.getSearchTrmsCdVal().toUpperCase()) ) {
			// 0_0. 회원가입 약관 조회
			nTotCnt = userMapper.selectUserTrmsAgreHistCnt(userSelectReq);
		} else {
			// 0_1. 내손안의 시험 인증 약관 조회
			nTotCnt = userMapper.selectEntGrpAgreTrmsCnt(userSelectReq);
		}

		return nTotCnt;
	}

	/*****************************************************
	 * 회원 약관 동의 이력 목록 조회
	 * @param userSelectReq
	 * @return
	*****************************************************/
	public List<UserSelectRes> getUserTrmsAgreHistList(UserSelectReq userSelectReq) {

		List<UserSelectRes> trmsHistList = null;

		// 0. 회원 약관 동의 이력 목록 조회
		if( "JOIN".equals(userSelectReq.getSearchTrmsCdVal().toUpperCase()) ) {
			// 0_0. 회원가입 약관 조회
			trmsHistList = userMapper.selectUserTrmsAgreHistList(userSelectReq);
		} else {
			// 0_1. 내손안의 시험 인증 약관 조회
			trmsHistList = userMapper.selectEntGrpAgreTrmsList(userSelectReq);
		}

		return trmsHistList;
	}

	/*****************************************************
	 * 회원 비밀번호 초기화 저장
	 * @param userSaveReq
	 * @return
	*****************************************************/
	public UserSaveRes saveUserPwdChg(UserSaveReq userSaveReq) throws Exception {

		// 0. 값 셋팅
		String 		strSaveErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		UserSaveRes userSaveRes = new UserSaveRes();
		int 		nSaveCount	= 0;

		/** ================================= [0]. 유효성 검사 ================================= */
		// 0_0. 비밀번호 일치하는지 확인
		if( !userSaveReq.getChgUserPwd().equals( userSaveReq.getChgUserPwdChk() ) ) {
			strSaveErrMsgCode = "result-message.messages.user.message.data.password.accord.error";
			throw new CustomMessageException(strSaveErrMsgCode); // 비밀번호가 일치하지 않습니다.
		}

		// 0_1. 비밀번호 형식 체크
		String pwdPattern = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$";
		Matcher pwdM = Pattern.compile(pwdPattern).matcher(userSaveReq.getChgUserPwd());
		if( ! pwdM.find() ) {
			throw new CustomMessageException("result-message.messages.user.message.data.password.error"); // '비밀번호가 형식에 맞지 않습니다.'
		}

		// 0_2. [비밀번호]가 ID 포함 하고 있는지 체크
		if( (userSaveReq.getChgUserPwd().contains( userSaveReq.getTargetUserId() )) ) {
			throw new CustomMessageException("result-message.messages.user.message.data.password.include.chgpwd.id"); // '비밀번호에 아이디가 포함되면 안됩니다.'
		}
		/** ======================================== [0] ===================================== */

		/** ================================ [1]. 기존 패스워드 검사 ================================ */
		/* 1_0. 기존 패스워드 가져오기 */
		UserSelectReq userSelectReq = new UserSelectReq();
		UserSelectRes userSelectRes = new UserSelectRes();

		userSelectReq.setTargetUserId( userSaveReq.getTargetUserId() );

		// 1_1. 해당 아이디 > 현재 비밀번호 상세 조회 및 새 비밀번호 암호화
		userSelectRes		 = userMapper.selectUserPwdInfo(userSelectReq);
		String strUserPwd 	 = userSelectRes.getUserPwd();
		String strUserApiPwd = userSaveReq.getChgUserPwd();
		String encChgUserPwd = StringUtil.nvl(EncryptUtil.encryptSha256( userSaveReq.getChgUserPwd(), userSaveReq.getTargetUserId() ));

		// 1_2. 기존 비밀번호랑 새 비밀번호 동일한지 확인
		if( strUserPwd.equals( encChgUserPwd ) ) {
			strSaveErrMsgCode = "result-message.messages.user.message.data.password.eq.chgpwd.oripwd";
			throw new CustomMessageException(strSaveErrMsgCode); // '기존비밀번호와 동일합니다.'
		}

		// 1_2. 새 비밀번호에 아이디계정 들어가 있는지 확인
		if( userSaveReq.getChgUserPwd().contains( userSaveReq.getTargetUserId() ) ) {
			strSaveErrMsgCode = "result-message.messages.user.message.data.password.include.chgpwd.id";
			throw new CustomMessageException(strSaveErrMsgCode); // '비밀번호에 아이디가 포함되면 안됩니다.'
		}
		/** ======================================== [1] ===================================== */

		/** ==================================== [2]. 저장 시작 ================================= */
		// 2_0. 암호화한 패스워드값 셋팅
		userSaveReq.setChgUserPwd(encChgUserPwd);

		nSaveCount = userMapper.updateUserPwd(userSaveReq);

		if(! (nSaveCount > 0 ) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ======================================== [2] ===================================== */

		/** ============= [3]. [기관(마스터), 기관(일반)] -> 데이터 분석환경 API 호출( USER 수정 ) ============= */
		// [3_0]. 사용자 유형 셋팅
		String strUserTyCd = userSelectRes.getUserTyCd();

		if( Const.Code.UserTyCd.INST_MASTER.equals(strUserTyCd) || Const.Code.UserTyCd.INST_GENERAL.equals(strUserTyCd) ) {

			// [3_1]. API 요청 값 셋팅
			Map<String, Object> mapRequestData = new HashMap<String, Object>();
			Map<String, Object> mapResponseData = new HashMap<String, Object>();

			mapRequestData.put("id"			, userSelectRes.getAnlsEnvUserId() );
			mapRequestData.put("userName"	, userSelectRes.getUserId() );
			mapRequestData.put("password"	, strUserApiPwd );
			mapRequestData.put("email"		, StringUtil.nvl(EncryptUtil.decryptAes256( userSelectRes.getEncptEmlAddrVal() )) );

			// [3_2]. USER 수정 API 호출
			mapResponseData = anlsApiService.apiConnectionUserUpdateInit(mapRequestData);

			if( mapResponseData == null || mapResponseData.isEmpty() ) {
				throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}

		}
		/** ==================================================================================== */

		/** ======================= [4]. 반환값 셋팅 ===================== */
		userSaveRes.setUserId( userSaveReq.getTargetUserId() );
		/** ============================ [4] ========================= */

		return userSaveRes;
	}

	/*****************************************************
	 * 회원 담당자 정보 저장
	 * @param userSaveReq
	 * @return
	*****************************************************/
	public UserSaveRes saveUserInfo(UserSaveReq userSaveReq) throws Exception {

		// 0. 값 셋팅
		String 		strSaveErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		UserSaveRes userSaveRes = new UserSaveRes();
		int 		nSaveCount	= 0;

		/** =================================== [0]. 유형 검사 =================================== */
		String strUserTypeCd = userSaveReq.getTargetUserTyCd().toUpperCase();

		if( "".equals( strUserTypeCd ) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ======================================== [0] ======================================= */

		/** =============================== [1]. 사용자 기본 정보 저장 =============================== */
		/* 1_0. 휴대폰 번호 암호화 */
		String strEncTelNo = StringUtil.nvl(EncryptUtil.encryptAes256( userSaveReq.getMblTelnoVal() ));
		userSaveReq.setEncptMblTelnoVal(strEncTelNo);

		/* 1_1. 이메일 주소 암호화 */
		String strEncemlAddr = StringUtil.nvl(EncryptUtil.encryptAes256( userSaveReq.getEmlAddrVal() ));
		userSaveReq.setEncptEmlAddrVal(strEncemlAddr);

		/* 1_2. 사용자 기본 정보 수정 */
		nSaveCount = userMapper.updateUserInfo(userSaveReq);

		if(! (nSaveCount > 0 ) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ======================================== [1] ======================================= */

		/** ===================== [2]. 유형별 저장 시작 (기관 마스터 / 기업 마스터 ) ======================= */
		if( Const.Code.UserTyCd.INST_MASTER.equals(strUserTypeCd) ) {

			/* 0_1. 기관(마스터) 회원 정보 수정 */
			nSaveCount = userMapper.updateInstUserInfo(userSaveReq);

		} else if( Const.Code.UserTyCd.ENT_MASTER.equals(strUserTypeCd) ) {

			/* 0_2. 기업(마스터) 회원 정보 수정 */
			nSaveCount = userMapper.updateEntGrpUserInfo(userSaveReq);

		}

		if(! (nSaveCount > 0 ) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ======================================== [2] ======================================= */

		/** ======================= [3]. 반환값 셋팅 ===================== */
		userSaveRes.setUserId( userSaveReq.getTargetUserId() );
		/** ============================ [3] ========================= */

		return userSaveRes;
	}

	/*****************************************************
	 * 회원 상태 승인/반려 처리 (기업마스터)
	 * @param userSaveReq
	 * @return
	*****************************************************/
	public UserSaveRes saveUserSttAppr(HttpServletRequest request, UserSaveReq userSaveReq) throws Exception {

		// 0. 값 셋팅
		String 		strSaveErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		UserSaveRes userSaveRes = new UserSaveRes();
		int 		nSaveCount	= 0;

		/** ================================= [0]. 회원 상세 조회 ================================ */
		UserSelectReq userSelectReq = new UserSelectReq();
		UserSelectRes userSelectRes = new UserSelectRes();

		userSelectReq.setTargetUserId(userSaveReq.getTargetUserId());
		userSelectReq.setTargetUserTypeCd(userSaveReq.getTargetUserTyCd());

		// 0_0. 상세 조회
		userSelectRes = this.getUserBasDetail( userSelectReq );
		/** ======================================== [0] ===================================== */

		/** ================================= [1]. 유효성 검사 ================================= */
		if( !userSelectRes.getUserTyCd().equals( userSaveReq.getTargetUserTyCd() ) ) {
			strSaveErrMsgCode = "result-message.messages.user.message.user.type.code.error";
			throw new CustomMessageException(strSaveErrMsgCode); // 회원 유형 코드가 일치하지 않습니다.
		}

		if( !userSelectRes.getUserSttCd().equals( userSaveReq.getTargetUserSttCd() ) ) {
			strSaveErrMsgCode = "result-message.messages.user.message.stt.type.code.error";
			throw new CustomMessageException(strSaveErrMsgCode); // 회원 상태 코드가 일치하지 않습니다.
		}
		/** ======================================== [1] ===================================== */

		/** ================================= [2]. 회원 상태 처리 ================================= */
		/* 2_0. 현재 상태값 및 승인/반려 여부 셋팅 */
		String strNowUserSttCd	= userSelectRes.getUserSttCd().toUpperCase();
		String strApprFlag		= userSaveReq.getApprFlag().toUpperCase();

		UserSaveReq chgUserSaveReq = new UserSaveReq();

		chgUserSaveReq.setMdfrId( SessionUtil.getLoginMngrId() ); 		 // 수정자 셋팅
		chgUserSaveReq.setTargetUserId( userSelectRes.getUserId() ); 	 // 현재 ID 값 셋팅
		chgUserSaveReq.setEntGrpSn( userSelectRes.getEntGrpSn() ); 		 // 기업 그룹 일련 번호 값 셋팅
		chgUserSaveReq.setEntGrpMngNo( userSelectRes.getEntGrpMngNo() ); // 기업 그룹 관리 번호 값 셋팅

		/* 2_1. 상태 > 가입 신청인 경우  */
		if( Const.Code.UserSttCd.JOIN_APPLY.equals( strNowUserSttCd ) ) {

			/* ----------------------------------- 2_1_0. 가입 승인 ------------------------------ */
			if( "Y".equals(strApprFlag) ) {

				/* 2_1_0_0. 사용자 상태코드 > 정상(USC020) 수정  */
				chgUserSaveReq.setTargetUserSttCd( Const.Code.UserSttCd.NORMAL ); 	// 정상(USC020)
				nSaveCount += userMapper.updateUserSttCd(chgUserSaveReq);

				/* 2_1_0_1. 가입 완료 > 사용자 정보 수정  */
				chgUserSaveReq.setJoinAplyPrcrId( SessionUtil.getLoginMngrId() ); 	// 가입신청처리자아이디
				nSaveCount += userMapper.updateJoinUserBas(chgUserSaveReq);

			}

			/* ----------------------------------- 2_1_1. 가입 반려 ----------------------------------- */
			else {

				/* 2_1_1_0. !!하위 기업 그룹이 없는 경우!! */
				UserSelectReq entSelectReq = new UserSelectReq();

				entSelectReq.setTargetBrno(userSelectRes.getBrno().replaceAll("-", ""));
				entSelectReq.setEntGrpSn(userSelectRes.getEntGrpSn());
				entSelectReq.setEntGrpMngNo(userSelectRes.getEntGrpMngNo());
				entSelectReq.setTargetUserId(userSelectRes.getUserId());

				/* 2_1_1_1. 탈퇴(USC040)를 제외한 상태의 하위 기업 그룹 목록 개수 조회 */
				Integer entListCnt = userMapper.selectDownEntGrpBasListCnt( entSelectReq );

				if( !(entListCnt > 0) ) {

					/* 2_1_1_1_0. 사용자 정보 삭제  */
					nSaveCount += userMapper.deleteUserBas(chgUserSaveReq);

					/* 2_1_1_1_1. 기업 그룹 사용자 관리 정보 삭제  */
					nSaveCount += userMapper.deleteUserEntGrpUserMng(chgUserSaveReq);

					/* 2_1_1_1_2. 기업 그룹 기본 정보 삭제 */
					nSaveCount += userMapper.deleteEntGrpBas(chgUserSaveReq);

					/* 2_1_1_1_3. 회원 > 논리 파일 삭제 */
					FileDto fileDto = new FileDto();
					fileDto.setMdfrId( SessionUtil.getLoginMngrId() );
					fileDto.setRefDivVal( userSelectRes.getUserId() );

					/* 2_1_1_1_4. 회원 파일 > 재직 증명서 */
					fileDto.setFileDivNm( FileConst.File.FILE_DIV_NM_GNRL_CERT_FILE );
					nSaveCount += userMapper.updateDeleteFile(fileDto);

					/* 2_1_1_1_5. 회원 파일 > 사업자 등록증 */
					fileDto.setFileDivNm( FileConst.File.FILE_DIV_NM_BZMN_REG_FILE );
					nSaveCount += userMapper.updateDeleteFile(fileDto);

					/* 2_1_1_1_6. 관심 키워드 삭제 */
					nSaveCount += userMapper.deleteUserPrdtClfChcMng(chgUserSaveReq);

				} else {

					strSaveErrMsgCode = "result-message.messages.user.message.stt.normal.down.user.exist.error";
					throw new CustomMessageException(strSaveErrMsgCode); // 현재 종속된 하위 그룹 회원들이 존재합니다. 다시 한번 확인해 주세요.

				}

			}
		}

		/* 2_2. 상태 > 탈퇴 신청인 경우  */
		else if( Const.Code.UserSttCd.WITHDRAWAL_APPLY.equals( strNowUserSttCd ) ) {

			/* ----------------------------------- 2_2_0. 탈퇴 승인 ----------------------------------- */
			if( "Y".equals(strApprFlag) ) {

				/* 2_2_0_0. !!하위 기업 그룹이 없는 경우!! */
				UserSelectReq entSelectReq = new UserSelectReq();

				entSelectReq.setTargetBrno(userSelectRes.getBrno());
				entSelectReq.setEntGrpSn(userSelectRes.getEntGrpSn());
				entSelectReq.setEntGrpMngNo(userSelectRes.getEntGrpMngNo());
				entSelectReq.setTargetUserId(userSelectRes.getUserId());

				/* 2_2_0_1. 탈퇴(USC040)를 제외한 상태의 하위 기업 그룹 목록 개수 조회 */
				Integer entListCnt = userMapper.selectDownEntGrpBasListCnt( entSelectReq );

				if( !(entListCnt > 0) ) {
					/** 1. 개인정보(ID 제외 전체 컬럼) 및 사용자 정보(기업그룹사용자, 기업그룹, 기관사용자) 'x'로 업데이트	*/
					/** 2. 1:1 문의 등의 게시물 del_yn Y 처리(생성자ID 기준)									*/

					/* 2_2_0_1_0. 사용자 상태코드 > 탈퇴(USC040) 수정  */
					chgUserSaveReq.setTargetUserSttCd( Const.Code.UserSttCd.WITHDRAWAL ); 	// 탈퇴(USC040)
					nSaveCount += userMapper.updateUserSttCd(chgUserSaveReq);

					/* 2_2_0_1_1. 탈퇴 완료 > 사용자 정보 수정  */
					chgUserSaveReq.setWhdwlAplyPrcrId( SessionUtil.getLoginMngrId() ); 		// 탈퇴신청처리자아이디
					nSaveCount += userMapper.updateWhdwlUserBas(chgUserSaveReq);

					/* 2_2_0_1_2. 기업 그룹 사용자 관리 정보 수정  */
					nSaveCount += userMapper.updateUserEntGrpUserMng(chgUserSaveReq);

					/* 2_2_0_1_3. 기업 그룹 기본 정보 수정 */
					nSaveCount += userMapper.updateEntGrpBas(chgUserSaveReq);

					/* 2_2_0_1_4. 논리 파일 삭제 */
					FileDto fileDto = new FileDto();
					fileDto.setMdfrId( SessionUtil.getLoginMngrId() );
					fileDto.setRefDivVal( userSelectRes.getUserId() );

					/* 2_2_0_1_5. 회원 파일 > 재직 증명서 삭제 여부 'Y' 처리 */
					fileDto.setFileDivNm( FileConst.File.FILE_DIV_NM_GNRL_CERT_FILE );
					nSaveCount += userMapper.updateDeleteFile(fileDto);

					/* 2_2_0_1_6. 회원 파일 > 사업자 등록증 삭제 여부 'Y' 처리 */
					fileDto.setFileDivNm( FileConst.File.FILE_DIV_NM_BZMN_REG_FILE );
					nSaveCount += userMapper.updateDeleteFile(fileDto);

					/* 2_2_0_1_7. 게시글 관련 삭제 여부 'Y' 처리 */
					nSaveCount += userMapper.updateNttMngDelete(chgUserSaveReq);

					/* 2_2_0_1_8. 관심 키워드 삭제 */
					nSaveCount += userMapper.deleteUserPrdtClfChcMng(chgUserSaveReq);

				} else {

					strSaveErrMsgCode = "result-message.messages.user.message.stt.normal.down.user.exist.error";
					throw new CustomMessageException(strSaveErrMsgCode); // 현재 종속된 하위 그룹 회원들이 존재합니다. 다시 한번 확인해 주세요.

				}
			}

			/* ----------------------------------- 2_2_1. 탈퇴 반려 ----------------------------------- */
			else {

				/* 2_2_1_0. 사용자 상태코드 > 정상(USC020) 수정  */
				chgUserSaveReq.setTargetUserSttCd( Const.Code.UserSttCd.NORMAL ); 	// 정상(USC020)
				nSaveCount = userMapper.updateUserSttCd(chgUserSaveReq);

			}
		}

		if(! (nSaveCount > 0 ) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ======================================== [2] ===================================== */

		/** ======================= [3]. 사용자 상태 별 메일 전송 ===================== */
		/* 3_1. 상태 별 메일 전송값 셋팅 */
		Map<String, Object> mapMail		 = new HashMap<String, Object>();
		boolean 			bMailSuccess = false;

		if( Const.Code.UserSttCd.JOIN_APPLY.equals( strNowUserSttCd ) ) {

			if( "Y".equals(strApprFlag) ) {

				/* 3_1_0. 가입 승인 메일 전송값 셋팅 */
				mapMail.put("mailTmepCd"	, Const.Code.MailTemplateCd.MEM_JOIN_COMP_ENT_MASTER);
				mapMail.put("mailSubject"	, "시험인증 빅데이터 가입 완료 안내");

			} else {

				/* 3_1_1. 가입 반려 메일 전송값 셋팅 */
				mapMail.put("mailTmepCd"	, Const.Code.MailTemplateCd.MEM_REGI_REJECT);
				mapMail.put("mailSubject"	, "회원가입 반려 안내");
			}

		} else if( Const.Code.UserSttCd.WITHDRAWAL_APPLY.equals( strNowUserSttCd ) ) {

			if( "Y".equals(strApprFlag) ) {

				/* 3_1_2. 탈퇴 승인 메일 전송값 셋팅 */
				mapMail.put("mailTmepCd"	, Const.Code.MailTemplateCd.MEM_WITHDRAWAL_COMP);
				mapMail.put("mailSubject"	, "시험인증 빅데이터 플랫폼 탈퇴 완료 안내");

			} else {

				/* 3_1_3. 탈퇴 반려 메일 전송값 셋팅 */
				mapMail.put("mailTmepCd"	, Const.Code.MailTemplateCd.MEM_WITHDRAWAL_REJECT_ENT_MASTER);
				mapMail.put("mailSubject"	, "시험인증 빅데이터 플랫폼 탈퇴 반려 안내");

			}
		}

		/* 3_2. 메일 전송 */
		bMailSuccess = sendEntMasterStatusMail(request, userSelectRes, mapMail);

		if( !bMailSuccess ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ============================ [3] ========================= */

		/** ======================= [4]. 반환값 셋팅 ===================== */
		userSaveRes.setUserId( userSelectRes.getUserId() );
		userSaveRes.setUserTyCd( userSelectRes.getUserTyCd() );
		userSaveRes.setApprFlag( strApprFlag );
		/** ============================ [4] ========================= */

		return userSaveRes;

	}

	/*****************************************************
	 * 기업(마스터) 상태 별 메일 발송
	 * @param request
	 * @param UserSelectRes
	 * @return
	 * @throws Exception
	*****************************************************/
	public boolean sendEntMasterStatusMail(HttpServletRequest request, UserSelectRes userSelectRes, Map<String, Object> mapMail) throws Exception {

		String strSavetErrMsgCode = "result-message.messages.common.message.save.error"; //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		int saveCount = 0;
		boolean success = false;

		/* [[0]]. 임시 업데이트 */
		saveCount = 1;
		if(saveCount == 0) {
			throw new CustomMessageException(strSavetErrMsgCode);
		}

		/* [[1]]. 메일 body 생성 */
		// 1-1. mailParam설정
		Map<String, Object> mailParam = new HashMap<String, Object>();
		// mailParam.put("", "");

		// 1-2. mail 템플릿 및 제목 셋팅
		String tempCd	= (String) mapMail.get("mailTmepCd");
		String subject	= (String) mapMail.get("mailSubject");

		// 1-3. body
		MailMakeBodyDto mailMakeBodyDto = new MailMakeBodyDto();
		mailMakeBodyDto.setMailTmepCd( tempCd ); 	// 메일 template 공통코드 - 기업마스터회원가입승인완료
		mailMakeBodyDto.setMailParam(mailParam); 	//메일 param (메일내용에 들어갈 param들)
		String sBody = mailService.makeMailBody(request, mailMakeBodyDto);

		/* [[2]]. 메일보내기 */
		if(! "".equals(sBody)) {

			// 2-1. 수신자 LIST
			List<MailSendReceiverDto> list_resiver = new ArrayList<MailSendReceiverDto>();
			list_resiver.add( new MailSendReceiverDto( userSelectRes.getEncptEmlAddrVal() ));

			// 2-2. DTO생성 후 메일 보내기
			MailSendDto mailSendDto = new MailSendDto();
			mailSendDto.setSubject( subject );
			mailSendDto.setBody(sBody);
			mailSendDto.setReceiverList(list_resiver);
			success = mailService.sendMail(mailSendDto);
		}

		return success;
	}

}

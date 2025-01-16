package com.katri.web.mypage.infoMng.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.Const;
import com.katri.common.FileConst;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.EncryptUtil;
import com.katri.common.util.SessionUtil;
import com.katri.common.util.StringUtil;
import com.katri.web.auth.model.LoginRes;
import com.katri.web.comm.model.FileDto;
import com.katri.web.comm.model.MailMakeBodyDto;
import com.katri.web.comm.model.MailSendDto;
import com.katri.web.comm.model.MailSendReceiverDto;
import com.katri.web.comm.service.AnlsApiService;
import com.katri.web.comm.service.FileService;
import com.katri.web.comm.service.MailService;
import com.katri.web.join.model.JoinSaveReq;
import com.katri.web.mypage.infoMng.mapper.InfoMngMapper;
import com.katri.web.mypage.infoMng.model.InfoMngPrdtSelectRes;
import com.katri.web.mypage.infoMng.model.InfoMngSaveReq;
import com.katri.web.mypage.infoMng.model.InfoMngSaveRes;
import com.katri.web.mypage.infoMng.model.InfoMngSelectReq;
import com.katri.web.mypage.infoMng.model.InfoMngSelectRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class InfoMngService {

	/** 파일 Service */
	private final FileService fileService;

	/** 메일 Service */
	private final MailService mailService;

	/** InfoMng Mapper */
	private final InfoMngMapper infoMngMapper;

	/** API Service */
	private final AnlsApiService anlsApiService;

	/*****************************************************
	 * [회원/기업/기관 정보 관리] > 비밀번호 동일한지 검사
	 * @param infoMngSelectReq
	 * @param loginRes
	 * @return
	 * @throws Exception
	*****************************************************/
	public boolean infoMngUserPwdCheck(InfoMngSelectReq infoMngSelectReq, LoginRes loginRes) throws Exception {

		boolean bReturn = false;

		String encUserPwd	 = loginRes.getUserPwd();
		String encUserPwdChk = StringUtil.nvl(EncryptUtil.encryptSha256( infoMngSelectReq.getChkUserPwd(), loginRes.getUserId() ));

		// [0]. 회원 유형별 회원 정보 수정 페이지 셋팅
		if( encUserPwdChk.equals(encUserPwd) ) {

			bReturn = true;

		} else {

			bReturn = false;
			throw new CustomMessageException("result-message.messages.pwd.message.checked.data"); // '비밀번호를 확인하시기 바랍니다.'
		}

		return bReturn;

	}


	/*****************************************************
	 * [회원/기업/기관 정보 관리] > 회원 상세 정보 조회
	 * @param infoMngSelectReq
	 * @return
	*****************************************************/
	public InfoMngSelectRes getInfoMngUserBasDetail( InfoMngSelectReq infoMngSelectReq ) throws Exception {

		InfoMngSelectRes infoMngSelectRes = null;

		/** ============================== [0]. 회원 유형 별 상세 조회 ============================== */
		// [0_0]. 데이터 셋팅 : 일반, 기업, 기관별 회원 정보 상세 조회
		String strUserId 	= infoMngSelectReq.getTargetUserId();
		String strUserTyCd	= infoMngSelectReq.getTargetUserTyCd().toUpperCase();

		if( !"".equals(strUserId) && !"".equals(strUserTyCd) ) {
			if( Const.Code.UserTyCd.INST_MASTER.equals(strUserTyCd) || Const.Code.UserTyCd.INST_GENERAL.equals(strUserTyCd) ) {

				// [0_1]. 기관(마스터 및 일반) 회원 상세 조회
				infoMngSelectRes = infoMngMapper.selectInstUserBasDetail(infoMngSelectReq);

			} else if( Const.Code.UserTyCd.ENT_MASTER.equals(strUserTyCd) || Const.Code.UserTyCd.ENT_GENERAL.equals(strUserTyCd) ) {

				// [0_2]. 기업(마스터 및 일반) 회원 상세 조회
				infoMngSelectRes = infoMngMapper.selectEntUserBasDetail(infoMngSelectReq);

			} else {

				// [0_3]. 개인(일반) 회원 상세 조회
				infoMngSelectRes = infoMngMapper.selectGnrlUserBasDetail(infoMngSelectReq);

			}
		}
		/** =================================================================================== */

		if( infoMngSelectRes != null ) {

		/** =================== [1]. 회원 파일 목록 조회 - 기업(마스터)만 해당 파일 존재함 ================== */
			if( Const.Code.UserTyCd.ENT_MASTER.equals(strUserTyCd) ) {
				FileDto fileDto = new FileDto();
				fileDto.setRefDivVal(infoMngSelectRes.getUserId());

				// [1_0]. 파일 조회 - 재직 증명서
				fileDto.setFileDivNm(FileConst.File.FILE_DIV_NM_GNRL_CERT_FILE);
				List<FileDto> lstGnrlCertFile = this.fileService.selectFileList(fileDto);

				// [1_1]. 파일 조회 - 사업자 등록증
				fileDto.setFileDivNm(FileConst.File.FILE_DIV_NM_BZMN_REG_FILE);
				List<FileDto> lstBzmnRegFile = this.fileService.selectFileList(fileDto);

				// [1_2]. 파일 조회 - 계정발급신청서
				fileDto.setFileDivNm(FileConst.File.FILE_DIV_NM_ACNT_ISSU_APLY_FILE);
				List<FileDto> lstAcntIssuAplyFile = this.fileService.selectFileList(fileDto);

				// [1_3]. 반환값 셋팅
				infoMngSelectRes.setLstGnrlCertFile(lstGnrlCertFile);
				infoMngSelectRes.setLstBzmnRegFile(lstBzmnRegFile);
				infoMngSelectRes.setLstAcntIssuAplyFile(lstAcntIssuAplyFile);
			}
		/** =================================================================================== */

		/** =================== [2]. 회원 핸드폰 번호, 이메일 주소 복호화, 기타 문자열 처리 ================= */
			// [2_0]. 핸드폰 번호 복호화
			if ( !"".equals( infoMngSelectRes.getEncptMblTelnoVal()) ) {
				String strEncTelNo = StringUtil.nvl(EncryptUtil.decryptAes256( infoMngSelectRes.getEncptMblTelnoVal() ));
				infoMngSelectRes.setEncptMblTelnoVal( strEncTelNo );
			} else {
				infoMngSelectRes.setEncptMblTelnoVal( infoMngSelectRes.getEncptMblTelnoVal() );
			}

			// [2_1]. 이메일 주소 복호화
			if ( !"".equals( infoMngSelectRes.getEncptEmlAddrVal()) ) {
				String strEncEmlAddr = StringUtil.nvl(EncryptUtil.decryptAes256( infoMngSelectRes.getEncptEmlAddrVal() ));
				infoMngSelectRes.setEncptEmlAddrVal(strEncEmlAddr);
			} else {
				infoMngSelectRes.setEncptEmlAddrVal( infoMngSelectRes.getEncptEmlAddrVal() );
			}
		/** =================================================================================== */

		/** =============================== [3]. 회원 관심 키워드 조회 ============================= */
			// 기관(마스터) or 기관(일반) 제외
			if( !( Const.Code.UserTyCd.INST_MASTER.equals(strUserTyCd) || Const.Code.UserTyCd.INST_GENERAL.equals(strUserTyCd) ) ) {

				// [3_0]. 관심키워드 조회
				List<InfoMngPrdtSelectRes> lstUserPrdt = infoMngMapper.selectUserPrdtClfChcMngList( infoMngSelectReq );

				// [3_1]. 관심키워드 목록 셋팅
				if( lstUserPrdt.size() > 0 ) {
					infoMngSelectRes.setLstUserPrdt(lstUserPrdt);
				}

			}
		/** =================================================================================== */

		} else {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return infoMngSelectRes;
	}


	/*****************************************************
	 * [회원/기업/기관 정보 관리] > 비밀번호 변경 저장
	 * @param infoMngSaveReq
	 * @return
	*****************************************************/
	public InfoMngSaveRes saveUserPwdChg(InfoMngSaveReq infoMngSaveReq) throws Exception {

		// [0]. 값 셋팅
		String 			strSaveErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		InfoMngSaveRes 	infoMngSaveRes 	= new InfoMngSaveRes();
		int 			nSaveCount		= 0;

		/** ===================================== [0]. 유효성 검사 ================================= */
		// [0_0]. 입력한 [비밀번호] 있는지 체크
		if( infoMngSaveReq.getChgUserPwd() == null  || "".equals(infoMngSaveReq.getChgUserPwd()) ) {
			throw new CustomMessageException("result-message.messages.common.message.required.data2" + "||msgArgu=비밀번호||"); // '{0}를(을) 입력해주세요.'
		}

		// [0_1]. 입력한 [비밀번호 확인] 있는지 체크
		if( infoMngSaveReq.getChgUserPwdChk() == null || "".equals(infoMngSaveReq.getChgUserPwdChk()) ) {
			throw new CustomMessageException("result-message.messages.common.message.required.data2" + "||msgArgu=비밀번호 확인||"); // '{0}를(을) 입력해주세요.'
		}

		// [0_2]. [비밀번호] 와 [비밀번호 확인] 값 같은지 체크
		if( ! (infoMngSaveReq.getChgUserPwd().equals( infoMngSaveReq.getChgUserPwdChk()) ) ) {
			throw new CustomMessageException("result-message.messages.pwd.message.nomatch.chgpwd.chgpwdcheck"); // '비밀번호가 일치하지 않습니다.'
		}

		// [0_3]. [비밀번호] 패턴 체크
		String pwdPattern = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$";
		Matcher pwdM = Pattern.compile(pwdPattern).matcher(infoMngSaveReq.getChgUserPwd());
		if( ! pwdM.find() ) {
			throw new CustomMessageException("result-message.messages.join.message.pwd.pattern.error"); // '비밀번호는 8~20자의 영문, 숫자, 특수문자 중 3가지 이상의 문자를 조합하여 사용할 수 있습니다.'
		}

		// [0_4]. [비밀번호]가 ID 포함 하고 있는지 체크_겹치면 안됨
		if( (infoMngSaveReq.getChgUserPwd().contains( infoMngSaveReq.getTargetUserId() )) ) {
			throw new CustomMessageException("result-message.messages.pwd.message.include.chgpwd.id"); // '비밀번호에 아이디가 포함되면 안됩니다.'
		}
		/** ==================================================================================== */

		/** ================================= [1]. 기존 패스워드 검사 =============================== */
		// [1_0]. 기존 패스워드 조회
		InfoMngSelectReq infoMngSelectReq = new InfoMngSelectReq();
		InfoMngSelectRes infoMngSelectRes = new InfoMngSelectRes();

		infoMngSelectReq.setTargetUserId( infoMngSaveReq.getTargetUserId() );

		// [1_1]. 해당 아이디 > 현재 비밀번호 조회 및 새 비밀번호 암호화
		infoMngSelectRes		= infoMngMapper.selectUserPwdInfo(infoMngSelectReq);
		String strUserPwd 		= infoMngSelectRes.getUserPwd();
		String strUserApiPwd	= infoMngSaveReq.getChgUserPwd();
		String encChgUserPwd	= StringUtil.nvl(EncryptUtil.encryptSha256( infoMngSaveReq.getChgUserPwd(), infoMngSaveReq.getTargetUserId() ));

		// [1_2]. 기존 비밀번호랑 새 비밀번호 동일한지 확인
		if( strUserPwd.equals( encChgUserPwd ) ) {
			strSaveErrMsgCode = "result-message.messages.pwd.message.eq.chgpwd.oripwd";
			throw new CustomMessageException(strSaveErrMsgCode); // '기존비밀번호와 동일합니다.'
		}

		// [1_3]. 새 비밀번호에 아이디계정 들어가 있는지 확인
		if( infoMngSaveReq.getChgUserPwd().contains( infoMngSaveReq.getTargetUserId() ) ) {
			strSaveErrMsgCode = "result-message.messages.pwd.message.include.chgpwd.id";
			throw new CustomMessageException(strSaveErrMsgCode); // '비밀번호에 아이디가 포함되면 안됩니다.'
		}
		/** ==================================================================================== */

		/** =================================== [2]. 비밀번호 변경 시작 =============================== */
		// [2_0]. 암호화한 비밀번호 셋팅
		infoMngSaveReq.setChgUserPwd(encChgUserPwd);

		// [2_1]. 비밀번호 변경
		nSaveCount = infoMngMapper.updateUserPwd(infoMngSaveReq);

		if(! (nSaveCount > 0 ) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ==================================================================================== */

		/** ============= [3]. [기관(마스터), 기관(일반)] -> 데이터 분석환경 API 호출( USER 수정 ) ============= */
		// [3_0]. 사용자 유형 셋팅
		String strUserTyCd = infoMngSelectRes.getUserTyCd();

		if( Const.Code.UserTyCd.INST_MASTER.equals(strUserTyCd) || Const.Code.UserTyCd.INST_GENERAL.equals(strUserTyCd) ) {

			// [3_1]. API 요청 값 셋팅
			Map<String, Object> mapRequestData = new HashMap<String, Object>();
			Map<String, Object> mapResponseData = new HashMap<String, Object>();

			mapRequestData.put("id"			, infoMngSelectRes.getAnlsEnvUserId() );
			mapRequestData.put("userName"	, infoMngSelectRes.getUserId() );
			mapRequestData.put("password"	, strUserApiPwd );
			mapRequestData.put("email"		, StringUtil.nvl(EncryptUtil.decryptAes256( infoMngSelectRes.getEncptEmlAddrVal() )) );

			// [3_2]. USER 수정 API 호출
			mapResponseData = anlsApiService.apiConnectionUserUpdateInit(mapRequestData);

			if( mapResponseData == null || mapResponseData.isEmpty() ) {
				throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}

		}
		/** ==================================================================================== */

		/** ===================================== [4]. 반환값 셋팅 ================================= */
		infoMngSaveRes.setUserId( infoMngSaveReq.getTargetUserId() );
		/** ===================================================================================== */

		return infoMngSaveRes;
	}


	/*****************************************************
	 * [회원/기업/기관 정보 관리] > 회원 수정 정보 저장
	 * @param infoMngSaveReq
	 * @param session
	 * @return
	*****************************************************/
	@SuppressWarnings("unchecked")
	public InfoMngSaveRes saveUserInfoMdfcn(InfoMngSaveReq infoMngSaveReq, HttpSession session) throws Exception {

		// 0. 값 셋팅
		String 			strSaveErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		InfoMngSaveRes	infoMngSaveRes	= new InfoMngSaveRes();
		int 			nSaveCount		= 0;

		/** ===================================== [0]. 회원 유형 검사 ================================= */
		String strUserTypeCd = infoMngSaveReq.getTargetUserTyCd().toUpperCase();

		if( "".equals( strUserTypeCd ) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ======================================================================================= */

		/** ================================== [1]. 사용자 기본 정보 저장 =============================== */
		// [1_0]. [휴대폰 번호] 암호화
		String strEncTelNo = StringUtil.nvl(EncryptUtil.encryptAes256( infoMngSaveReq.getMblTelnoVal().replaceAll("-", "") ));
		infoMngSaveReq.setEncptMblTelnoVal(strEncTelNo);

		// [!!] 휴대폰 번호 수정 시, 세션에 담긴 인증 정보와 일치하지 않는 경우 [!!] > 이상 접근 - 마스터 계정 제외
		if( Const.Code.UserTyCd.ENT_GENERAL.equals(strUserTypeCd) || Const.Code.UserTyCd.INST_GENERAL.equals(strUserTypeCd) || Const.Code.UserTyCd.GENERAL.equals(strUserTypeCd) ) {

			// 세션에 담긴 nice 인증 정보
			Map<String, Object> mapNice = (Map<String, Object>) session.getAttribute("niceInfo");

			if( ! "".equals(infoMngSaveReq.getUserLinkInfoVal()) && mapNice != null ) {
				// [1_0_0]. [휴대폰 번호] 정보 미 일치
				if( ! mapNice.get("MOBILE_NO").equals( infoMngSaveReq.getMblTelnoVal().replaceAll("-", "") ) ) {
					throw new CustomMessageException("result-message.messages.common.message.wrong.appr"); // 잘못된 접근입니다.
				}
				// [1_0_1]. [CI] 정보 미 일치
				if( ! mapNice.get("CI").equals( infoMngSaveReq.getUserLinkInfoVal() ) ) {
					throw new CustomMessageException("result-message.messages.common.message.wrong.appr"); // 잘못된 접근입니다.
				}
			} else {
				// [1_0_2]. 현재 계정 기존 핸드폰 번호 가져오기
				String compTelNo = infoMngMapper.selectUserTelNoInfo(infoMngSaveReq);

				// [1_0_3]. [현재 계정 핸드폰 번호]와 [입력 휴대폰 번호] 미일치
				if( ! strEncTelNo.equals(compTelNo) ) {
					throw new CustomMessageException("result-message.messages.common.message.wrong.appr"); // 잘못된 접근입니다.
				}
			}
		}

		// [1_1]. [이메일] 암호화
		String strEncemlAddr = StringUtil.nvl(EncryptUtil.encryptAes256( infoMngSaveReq.getEmlAddrVal() ));
		infoMngSaveReq.setEncptEmlAddrVal(strEncemlAddr);

		// [!!] 이메일 수정 안할 시, 현재 계정 이메일 정보와 일치하지 않는 경우 [!!] > 이상 접근
		if( ! "Y".equals( infoMngSaveReq.getEmailCertYn() ) ) {
			// [1_1_0]. 현재 계정 기존 메일 주소 가져오기
			String compEmailAddr = infoMngMapper.selectUserEmailInfo(infoMngSaveReq);

			// [1_1_1]. [현재 계정 메일 주소]와 [입력 메일 주소] 미 일치
			if( ! strEncemlAddr.equals(compEmailAddr) ) {
				throw new CustomMessageException("result-message.messages.common.message.wrong.appr"); // 잘못된 접근입니다.
			}
		}

		// [1_2]. 사용자 기본 정보 저장
		nSaveCount = infoMngMapper.updateUserBasInfo( infoMngSaveReq );

		if(! (nSaveCount > 0 ) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ======================================================================================= */

		/** =================================== [2]. 회원 유형별 저장 ================================== */
		if( Const.Code.UserTyCd.INST_MASTER.equals(strUserTypeCd) || Const.Code.UserTyCd.INST_GENERAL.equals(strUserTypeCd) ) {
			// [2_0]. 기관(마스터) / 기관(일반) 유형
			nSaveCount += infoMngMapper.updateInstUserMngInfo( infoMngSaveReq );

		} else if ( Const.Code.UserTyCd.ENT_MASTER.equals(strUserTypeCd) ) {
			// [2_1]. 기업(마스터) 유형
			nSaveCount += infoMngMapper.updateEntUserMngInfo( infoMngSaveReq );
			nSaveCount += infoMngMapper.updateEntGrpBasInfo( infoMngSaveReq );

		} else if ( Const.Code.UserTyCd.ENT_GENERAL.equals(strUserTypeCd) ) {
			// [2_1]. 기업(일반) 유형
			nSaveCount += infoMngMapper.updateEntUserMngInfo( infoMngSaveReq );

		}

		if(! (nSaveCount > 0 ) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ======================================================================================= */

		/** =================================== [3]. 관심키워드 저장 =================================== */
		// 기관(마스터) or 기관(일반) 제외
		if( !(Const.Code.UserTyCd.INST_MASTER.equals(strUserTypeCd) || Const.Code.UserTyCd.INST_GENERAL.equals(strUserTypeCd)) ) {
			// [3_0]. 관심키워드 선택한 내용 있는 경우, 등록되어 있는 키워드 삭제 후 재등록
			infoMngMapper.deleteUserPrdtClfChcMng(infoMngSaveReq);

			// [3_1]. 관심키워드 1 저장 시작
			String		stdLgclfCd1 	= infoMngSaveReq.getStdLgclfCd1(); 	// 관심키워드 1 - 선택한 대분류 값
			String[]	arrStdMlclfCd1 	= infoMngSaveReq.getArrStdMlclfCd1(); 	// 관심키워드 1 - 선택한 중분류 값

			if( ! "".equals(stdLgclfCd1) && stdLgclfCd1 != null ) {
				if( arrStdMlclfCd1 != null  && arrStdMlclfCd1.length > 0 ) {
					// [3_1_0]. 관심키워드 등록
					nSaveCount = this.saveUserPrdtClfChcMng(stdLgclfCd1, arrStdMlclfCd1, infoMngSaveReq);

					if(! (nSaveCount > 0 ) ) {
						throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
					}
				}
			}

			// [3_2]. 관심키워드 2 저장 시작
			String		stdLgclfCd2 	= infoMngSaveReq.getStdLgclfCd2(); 	// 관심키워드 2 - 선택한 대분류 값
			String[]	arrStdMlclfCd2 	= infoMngSaveReq.getArrStdMlclfCd2(); 	// 관심키워드 2 - 선택한 중분류 값

			if( ! "".equals(stdLgclfCd2) && stdLgclfCd2 != null ) {
				if( arrStdMlclfCd2 != null  && arrStdMlclfCd2.length > 0 ) {
					// [3_2_0]. 관심키워드 등록
					nSaveCount = this.saveUserPrdtClfChcMng(stdLgclfCd2, arrStdMlclfCd2, infoMngSaveReq);

					if(! (nSaveCount > 0 ) ) {
						throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
					}
				}
			}
		}
		/** ======================================================================================= */

		/** ==================================== [4]. 반환값 셋팅 ==================================== */
		infoMngSaveRes.setUserId( infoMngSaveReq.getTargetUserId() );
		/** ======================================================================================= */

		return infoMngSaveRes;
	}

	/*****************************************************
	 * [회원] > 관심 키워드 저장
	 * @param stdLgclfCd
	 * @param arrStdMlclfCd
	 * @param infoMngSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public int saveUserPrdtClfChcMng( String stdLgclfCd, String[] arrStdMlclfCd, InfoMngSaveReq infoMngSaveReq ) throws Exception {

		String strSavetErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		int nSaveCount = 0;

		if( stdLgclfCd != null && !"".equals(stdLgclfCd) ) {

			// 0. 관심 키워드 - 선택된 관심 키워드 있는 경우
			JoinSaveReq prdtJoinSaveReq = new JoinSaveReq();

			prdtJoinSaveReq.setUserId( infoMngSaveReq.getTargetUserId() );	// 아이디 셋팅
			prdtJoinSaveReq.setCrtrId( infoMngSaveReq.getTargetUserId() );	// 등록자 셋팅
			prdtJoinSaveReq.setStdLgclfCd( stdLgclfCd ); 					// 대분류 셋팅

			// 1. 관심 키워드 - 선택한 중분류 값
			for( int nLoop = 0; nLoop < arrStdMlclfCd.length; nLoop++ ) {

				if( "".equals(arrStdMlclfCd[nLoop]) || arrStdMlclfCd[nLoop] == null ) continue;
				prdtJoinSaveReq.setSrtSeq( nLoop + 1 );
				prdtJoinSaveReq.setStdMlclfCd( arrStdMlclfCd[nLoop] ); // 중분류 값 셋팅
				nSaveCount = infoMngMapper.insertUserPrdtClfChcMng(prdtJoinSaveReq);

				if(nSaveCount == 0) {
					throw new CustomMessageException(strSavetErrMsgCode);
				}
			}

			nSaveCount = 1;
		}

		return nSaveCount;
	}

	/*****************************************************
	 * [회원/기업/기관 정보 관리] > 회원 탈퇴 처리
	 * @param infoMngSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public InfoMngSaveRes saveUserWhdwl( HttpServletRequest request, InfoMngSaveReq infoMngSaveReq ) throws Exception {

		// 0. 값 셋팅
		String 			strSaveErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		InfoMngSaveRes	infoMngSaveRes	= new InfoMngSaveRes();
		int 			nSaveCount		= 0;

		/** ===================================== [0]. 회원 유형 검사 ================================= */
		String strUserTypeCd = infoMngSaveReq.getTargetUserTyCd().toUpperCase();

		if( "".equals( strUserTypeCd ) ) {
			strSaveErrMsgCode = "result-message.messages.common.message.no.data"; // 데이터가 없습니다.
			throw new CustomMessageException(strSaveErrMsgCode);
		}
		/** ======================================================================================= */

		/** ===================================== [1]. 유형 별 탈퇴 처리 ================================= */
		// [1_0]. 회원 정보 상세 조회
		InfoMngSelectReq infoMngSelectReq = new InfoMngSelectReq();
		InfoMngSelectRes infoMngSelectRes = new InfoMngSelectRes();

		infoMngSelectReq.setTargetUserId( infoMngSaveReq.getTargetUserId() );
		infoMngSelectReq.setTargetUserTyCd( infoMngSaveReq.getTargetUserTyCd() );

		// [1_0_0]. 회원 정보 상세 조회
		infoMngSelectRes = this.getInfoMngUserBasDetail(infoMngSelectReq);

		// [1_0_1]. 상태값 검사
		if( Const.Code.UserSttCd.WITHDRAWAL.equals(infoMngSelectRes.getUserSttCd()) || Const.Code.UserSttCd.WITHDRAWAL_APPLY.equals(infoMngSelectRes.getUserSttCd()) ) {
			throw new CustomMessageException("result-message.messages.mypage.account.mng.message.user.stt.whdwl"); // 탈퇴 처리된 회원입니다.
		} else if ( Const.Code.UserSttCd.JOIN_REJECT.equals(infoMngSelectRes.getUserSttCd()) ) {
			throw new CustomMessageException("result-message.messages.mypage.account.mng.message.user.stt.reject"); // 가입 반려 처리된 회원입니다.
		}

		if( Const.Code.UserTyCd.ENT_MASTER.equals(strUserTypeCd) ) {
			// [1_1]. 기업(마스터) 유형
			InfoMngSelectReq entSelectReq = new InfoMngSelectReq();

			// [1_1_0]. 기업(마스터)회원에 소속그룹으로 가입된 회원 수 조회
			entSelectReq.setEntGrpSn( infoMngSelectRes.getEntGrpSn() );
			entSelectReq.setTargetUserId( infoMngSelectRes.getUserId() );

			// [1_1_1]. 하위 기업 사용자 수 조회
			Integer entListCnt = infoMngMapper.selectDownEntGrpBasListCnt( entSelectReq );

			if( !(entListCnt > 0) ) {
				// [1_1_2]. 하위 기업 (정상,휴면) 상태 회원이 없는 경우
				infoMngSaveReq.setUserSttCd( Const.Code.UserSttCd.WITHDRAWAL_APPLY ); // 상태값 - 탈퇴 신청(USC041) 셋팅

				// [1_1_3]. 회원 상태코드 > 탈퇴 신청(USC041) 수정
				nSaveCount += infoMngMapper.updateUserSttCd(infoMngSaveReq);

				// [1_1_4]. 사용자 정보 수정 - 탈퇴신청요청일시 수정
				nSaveCount += infoMngMapper.updateWhdwlApplyUserBas(infoMngSaveReq);

			} else {
				// [1_1_5]. 하위 기업 ('탈퇴(USC040)' 제외의) 상태 회원이 없는 경우
				strSaveErrMsgCode = "result-message.messages.mypage.info.mng.message.ent.grp.user.exist.error";
				throw new CustomMessageException(strSaveErrMsgCode); // 종속된 회원이 있어 탈퇴 신청이 불가합니다.
			}

		} else {
			// [1_2]. 기업(일반) 유형, 기관(일반) 유형, 개인(일반) 유형
			infoMngSaveReq.setUserSttCd( Const.Code.UserSttCd.WITHDRAWAL ); // 상태값 - 탈퇴(USC040) 셋팅

			// [1_2_0]. 회원 상태코드 > 탈퇴(USC040) 수정
			nSaveCount += infoMngMapper.updateUserSttCd(infoMngSaveReq);

			// [1_2_1]. 탈퇴 완료 > 사용자 정보 수정
			infoMngSaveReq.setWhdwlAplyPrcrId( SessionUtil.getLoginUserId() );
			nSaveCount += infoMngMapper.updateWhdwlUserBas(infoMngSaveReq);

			// [1_2_2]. 유형 별 기업 or 기관 사용자 정보 수정
			if( Const.Code.UserTyCd.INST_GENERAL.equals(strUserTypeCd) ) {

				// [1_2_2_0]. 탈퇴 완료 > 기관(일반) 사용자 관리 정보 수정
				nSaveCount += infoMngMapper.updateWhdwlInstUserMng(infoMngSaveReq);

			} else if ( Const.Code.UserTyCd.ENT_GENERAL.equals(strUserTypeCd) ) {

				// [1_2_2_1]. 탈퇴 완료 > 기업(일반) 사용자 관리 정보 수정
				infoMngSaveReq.setEntGrpSn( infoMngSelectRes.getEntGrpSn() );
				nSaveCount += infoMngMapper.updateWhdwlEntGrpUserMng(infoMngSaveReq);

			}
		}

		if(! (nSaveCount > 0 ) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ======================================================================================= */

		/** ==================================== [2]. 게시글 및 관심 키워드  삭제 ================================ */
		if( ! Const.Code.UserTyCd.ENT_MASTER.equals(strUserTypeCd) ) {	// 기업(마스터) 제외 - 탈퇴 신청 처리이기 때문
			// [2_0]. 게시글 관련 삭제 처리 -- 등록된 글이 없을 수 있기 때문에 따로 오류 처리 x
			infoMngMapper.updateWhdwlNttMngDelete(infoMngSaveReq);

			// [2_1]. 관심 키워드 삭제 처리
			infoMngMapper.deleteUserPrdtClfChcMng(infoMngSaveReq);
		}
		/** ======================================================================================= */

		/** ==================== [3]. [기관(일반)] -> 데이터 분석환경 API 호출( USER 삭제 ) ================= */
		if( Const.Code.UserTyCd.INST_GENERAL.equals(strUserTypeCd) ) {
			// [3_0]. API 요청 값 셋팅
			Map<String, Object> mapRequestData 	= new HashMap<String, Object>();
			Map<String, Object> mapResponseData = new HashMap<String, Object>();

			mapRequestData.put("id", infoMngSelectRes.getAnlsEnvUserId() );

			// [3_1]. USER 삭제 API 호출
			mapResponseData = anlsApiService.apiConnectionUserDeleteInit(mapRequestData);

			if( mapResponseData == null || mapResponseData.isEmpty() ) {
				throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}
		}
		/** ======================================================================================= */

		/** ======================================= [4]. 메일 전송 ================================== */
		// [4_0]. 상태 별 메일 전송 값 셋팅
		Map<String, Object> mapMail			= new HashMap<String, Object>();
		boolean 			bMailSuccess	= false;
		String				strUserEmlAddr	= infoMngSelectRes.getEncptEmlAddrVal();

		// [4_1]. 기업(마스터) 유형
		if( Const.Code.UserTyCd.ENT_MASTER.equals(strUserTypeCd) ) {

			// [4_1_0]. 탈퇴 신청 메일 -> 탈퇴 신청한 [사용자]에게 메일 전송
			mapMail.put("mailTmepCd"	, Const.Code.MailTemplateCd.MEM_WITHDRAWAL_APPLY);
			mapMail.put("mailSubject"	, "시험인증 빅데이터 플랫폼 탈퇴 신청 완료 안내");
			infoMngSaveReq.setReciverEmlAddr( strUserEmlAddr ); // 로그인한 사용자 메일 주소

			// [4_1_1]. [사용자]에게 메일 전송
			bMailSuccess = sendWhdwlMail(request, infoMngSaveReq, mapMail);

			// [4_1_2]. 탈퇴 신청 안내 메일 -> [관리자]에게 메일 전송
			mapMail.put("mailTmepCd"	, Const.Code.MailTemplateCd.MEM_WITHDRAWAL_APPLY_ENT_MASTER);
			mapMail.put("mailSubject"	, "시험인증 빅데이터 마스터 탈퇴 신청 안내");

			// [4_1_3]. [관리자] 이메일 주소 조회 후 셋팅
			InfoMngSelectReq emlSelectReq = new InfoMngSelectReq();
			emlSelectReq.setEmlCode( Const.Code.MailTemplateCd.MEM_WITHDRAWAL_APPLY_ENT_MASTER );
			String strAdminEmlAddr = infoMngMapper.selectAdminEmlAddr(emlSelectReq);
			infoMngSaveReq.setReciverEmlAddr( strAdminEmlAddr ); // 조회한 관리자 메일 주소

			// [4_1_4]. [관리자]에게 메일 전송
			bMailSuccess = sendWhdwlMail(request, infoMngSaveReq, mapMail);

		// [4_2]. 기업(일반) 유형, 기관(일반) 유형, 개인(일반) 유형
		} else {

			// [4_2_0]. 탈퇴 완료 메일 -> 탈퇴 신청한 [사용자]에게 메일 전송
			mapMail.put("mailTmepCd"	, Const.Code.MailTemplateCd.MEM_WITHDRAWAL_COMP);
			mapMail.put("mailSubject"	, "시험인증 빅데이터 플랫폼 탈퇴 완료 안내");
			infoMngSaveReq.setReciverEmlAddr( strUserEmlAddr ); // 로그인한 사용자 메일 주소

			// [4_2_1]. [사용자]에게 메일 전송
			bMailSuccess = sendWhdwlMail(request, infoMngSaveReq, mapMail);

		}

		if( !bMailSuccess ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ======================================================================================= */

		/** ======================================= [5]. 반환값 셋팅 ================================= */
		// [5_0]. 반환값 셋팅
		infoMngSaveRes.setUserId( infoMngSaveReq.getTargetUserId() );

		// [5_1]. 세션 clear
		SessionUtil.removeLoginSession();
		/** ======================================================================================= */

		return infoMngSaveRes;
	}


	/*****************************************************
	 * [회원/기업/기관 정보 관리] > 회원 탈퇴 관련 메일 전송
	 * @param request
	 * @param infoMngSaveReq
	 * @param mapMail
	 * @return
	 * @throws Exception
	*****************************************************/
	public boolean sendWhdwlMail(HttpServletRequest request, InfoMngSaveReq infoMngSaveReq,	Map<String, Object> mapMail) throws Exception {

		String strSavetErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		int saveCount = 0;
		boolean success = false;

		/* [0]. 임시 업데이트 */
		saveCount = 1;
		if(saveCount == 0) {
			throw new CustomMessageException(strSavetErrMsgCode);
		}

		/* [1]. 메일 body 생성 */
		// [1_0]. mailParam설정
		Map<String, Object> mailParam = new HashMap<String, Object>();
		// mailParam.put("", "");

		// [1_1]. mail 템플릿 및 제목 셋팅
		String tempCd	= (String) mapMail.get("mailTmepCd");
		String subject	= (String) mapMail.get("mailSubject");

		// [1_2]. body
		MailMakeBodyDto mailMakeBodyDto = new MailMakeBodyDto();
		mailMakeBodyDto.setMailTmepCd(tempCd); 		// 메일 template 공통코드
		mailMakeBodyDto.setMailParam(mailParam); 	// 메일 param (메일내용에 들어갈 param들)
		String sBody = mailService.makeMailBody(request, mailMakeBodyDto);

		/* [2]. 메일보내기 */
		if(! "".equals(sBody)) {

			// [2_0]. 수신자 LIST
			List<MailSendReceiverDto> list_resiver = new ArrayList<MailSendReceiverDto>();
			list_resiver.add( new MailSendReceiverDto( infoMngSaveReq.getReciverEmlAddr() ));

			// [2_1]. DTO생성 후 메일 보내기
			MailSendDto mailSendDto = new MailSendDto();
			mailSendDto.setSubject( subject );
			mailSendDto.setBody(sBody);
			mailSendDto.setReceiverList(list_resiver);
			success = mailService.sendMail(mailSendDto);
		}

		return success;
	}

	/*****************************************************
	 * [정보 관리] > 관심 키워드 대분류 목록 조회
	 * @return
	*****************************************************/
	public List<InfoMngPrdtSelectRes> getStdLgclfCdList() throws Exception {

		List<InfoMngPrdtSelectRes> lstPrdt = null;

		// 0. 관심 키워드 대분류 목록 조회
		lstPrdt = infoMngMapper.selectStdLgclfCd();

		if( lstPrdt == null ) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return lstPrdt;
	}


	/*****************************************************
	 * [정보 관리] > 기업(마스터) 종속되어 있는 그룹 회원 확인
	 * @param infoMngSelectReq
	 * @param loginRes
	 * @return
	 * @throws Exception
	*****************************************************/
	public int selctEntMasterGrpUserChk(InfoMngSelectReq infoMngSelectReq, LoginRes loginRes) throws Exception {

		InfoMngSelectRes infoMngSelectRes	= new InfoMngSelectRes();
		int				 nEntChkCnt			= 0;

		// [0]. 회원 정보 상세 조회
		infoMngSelectRes = this.getInfoMngUserBasDetail(infoMngSelectReq);

		if( Const.Code.UserTyCd.ENT_MASTER.equals(infoMngSelectRes.getUserTyCd()) ) {
			// [1]. 기업(마스터) 유형 값 셋팅
			InfoMngSelectReq entSelectReq = new InfoMngSelectReq();
			entSelectReq.setEntGrpSn( infoMngSelectRes.getEntGrpSn() );
			entSelectReq.setTargetUserId( infoMngSelectRes.getUserId() );

			// [2]. 기업(마스터)회원에 소속그룹으로 가입된 회원 수 조회
			nEntChkCnt = infoMngMapper.selectDownEntGrpBasListCnt( entSelectReq );
		}

		return nEntChkCnt;
	}


	/*****************************************************
	 * [정보 관리] > 연계 정보 중복 체크
	 * @param infoMngSelectReq
	 * @return
	*****************************************************/
	public int getUserLinkInfoDpcnChkCnt(InfoMngSelectReq infoMngSelectReq) throws Exception {

		int nDpcnCnt = 0;

		// 0. 중복 조회
		nDpcnCnt = infoMngMapper.selectUserLinkInfoDpcnChkCnt(infoMngSelectReq);

		return nDpcnCnt;

	}

}

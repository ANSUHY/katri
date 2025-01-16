package com.katri.web.auth.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.Const;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.EncryptUtil;
import com.katri.common.util.SessionUtil;
import com.katri.common.util.StringUtil;
import com.katri.web.auth.mapper.OverDayChgPwdMapper;
import com.katri.web.auth.model.OverDayChgPwdSaveReq;
import com.katri.web.auth.model.OverDayChgPwdSaveRes;
import com.katri.web.auth.model.OverDayChgPwdSelectRes;
import com.katri.web.comm.service.AnlsApiService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class OverDayChgPwdService {

	/** [일정기간 지난 비밀번호 변경] Mapper */
	private final OverDayChgPwdMapper overDayChgPwdMapper;

	/** API Service */
	private final AnlsApiService anlsApiService;

	/*****************************************************
	 * [일정기간 지난 비밀번호 변경] 비밀번호 변경
	 * @param OverDayChgPwdSaveReq 비밀번호 변경할 정보
	 * @return OverDayChgPwdSaveRes
	 * @throws Exception
	 *****************************************************/
	public OverDayChgPwdSaveRes updateChgOverDayChgPwd(OverDayChgPwdSaveReq overDayChgPwdSaveReq) throws Exception {

		String strUpdateErrMsgCode = "result-message.messages.common.message.update.error"; //수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		// [[0]]. 반환할 정보들 & 변수 지정
		OverDayChgPwdSaveRes overDayChgPwdSaveRes = new OverDayChgPwdSaveRes();
		int saveCount = 0;

		// ============= [[1]]. validataion check
		/* 1-1. 로그인 했는지 체크 */
		if( SessionUtil.getLoginUserId() ==  null || "".equals(SessionUtil.getLoginUserId()) ) {
			throw new CustomMessageException("result-message.messages.login.message.need.login" + "||errorCode=noLoginId||"); //'로그인이 필요합니다.'
		}
		/* 1-2. 입력한 [현재 비밀번호] 있는지 체크*/
		if( overDayChgPwdSaveReq.getNowPwd() == null  || "".equals(overDayChgPwdSaveReq.getNowPwd()) ) {
			throw new CustomMessageException("result-message.messages.common.message.required.data2" + "||msgArgu=현재 비밀번호||"); //'{0}를(을) 입력해주세요.'
		}
		/* 1-3. 입력한 [새 비밀번호] 있는지 체크*/
		if( overDayChgPwdSaveReq.getChgPwd() == null  || "".equals(overDayChgPwdSaveReq.getChgPwd()) ) {
			throw new CustomMessageException("result-message.messages.common.message.required.data2" + "||msgArgu=새 비밀번호||"); //'{0}를(을) 입력해주세요.'
		}
		/* 1-4. 입력한 [새 비밀번호 확인] 있는지 체크 */
		if( overDayChgPwdSaveReq.getChgPwdCheck() == null  || "".equals(overDayChgPwdSaveReq.getChgPwdCheck()) ) {
			throw new CustomMessageException("result-message.messages.common.message.required.data2" + "||msgArgu=새 비밀번호 확인||"); //'{0}를(을) 입력해주세요.'
		}
		/* 1-5. [새비밀번호] 와 [새비밀번호확인] 값같은지 체크 */
		if( ! (overDayChgPwdSaveReq.getChgPwd().equals( overDayChgPwdSaveReq.getChgPwdCheck()) ) ) {
			throw new CustomMessageException("result-message.messages.pwd.message.nomatch.chgpwd.chgpwdcheck"); //'비밀번호가 일치하지 않습니다.'
		}
		/* 1-6. [새비밀번호] 패턴 체크 */
		String pattern = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$";
		Matcher m = Pattern.compile(pattern).matcher(overDayChgPwdSaveReq.getChgPwd());
		if(  ! m.find() ) {
			throw new CustomMessageException("result-message.messages.pwd.message.nopattern.chgpwd"); //'비밀번호가 형식에 맞지 않습니다.'
		}
		/* 1-7. [새비밀번호]가 ID 포함 하고 있는지 체크_겹치면 안됨 */
		if( (overDayChgPwdSaveReq.getChgPwd().contains( SessionUtil.getLoginUserId() )) ) {
			throw new CustomMessageException("result-message.messages.pwd.message.include.chgpwd.id"); //'비밀번호에 아이디가 포함되면 안됩니다.'
		}

		// ============= [[2]]. 기존 비밀번호 조회
		String oriPwd = overDayChgPwdMapper.selectOriPwd(SessionUtil.getLoginUserId());
		if(oriPwd == null || ("").equals(oriPwd) ) {
			throw new CustomMessageException("====비밀번호 없음");
		}

		// ============= [[3]]. 기존 비밀번호와 입력한 [현재 비밀번호]랑 일치하는지 체크
		String eyptNowPwd = StringUtil.nvl(EncryptUtil.encryptSha256(overDayChgPwdSaveReq.getNowPwd(), SessionUtil.getLoginUserId()));
		if( ! eyptNowPwd.equals(oriPwd) ) {
			throw new CustomMessageException("result-message.messages.pwd.message.nomatch.nowpwd.oripwd"); //'유효한 비밀번호가 아닙니다.'
		}

		// ============= [[4]]. [새비밀번호]가 기존 비밀번호랑 동일한지 체크_동일하면 안됨
		// [4_0]. API Request - "password" 값(암호화 전)
		String strUserApiPwd = overDayChgPwdSaveReq.getChgPwd();

		// [4_1]. [새비밀번호]가 기존 비밀번호랑 동일한지 체크
		String eyptChgPwd = StringUtil.nvl(EncryptUtil.encryptSha256(overDayChgPwdSaveReq.getChgPwd(), SessionUtil.getLoginUserId()));
		if( eyptChgPwd.equals(oriPwd) ) {
			throw new CustomMessageException("result-message.messages.pwd.message.eq.chgpwd.oripwd"); //'기존비밀번호와 동일합니다.'
		}

		// ============= [[5]]. [새비밀번호]로 update
		overDayChgPwdSaveReq.setUserId(SessionUtil.getLoginUserId());	//비밀번호 update시킬 ID
		overDayChgPwdSaveReq.setEyptChgPwd(eyptChgPwd);  				//암호화한 [새비밀번호]
		overDayChgPwdSaveReq.setMdfrId(SessionUtil.getLoginUserId());
		saveCount = overDayChgPwdMapper.updateChgOverDayChgPwd(overDayChgPwdSaveReq);
		if(! (saveCount > 0 ) ) {
			throw new CustomMessageException(strUpdateErrMsgCode); //'수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.'
		}

		// ============= [[6]]. [기관(마스터), 기관(일반)] -> 데이터 분석환경 API 호출( USER 수정 )
		String strUserTyCd = SessionUtil.getLoginUserTyCd();

		if( Const.Code.UserTyCd.INST_MASTER.equals(strUserTyCd) || Const.Code.UserTyCd.INST_GENERAL.equals(strUserTyCd) ) {
			// [6_0]. API 요청 값 셋팅
			Map<String, Object> mapRequestData = new HashMap<String, Object>();
			Map<String, Object> mapResponseData = new HashMap<String, Object>();

			// [6_1]. API 호출 > 수정 대상 사용자 API 관련 정보 조회
			OverDayChgPwdSelectRes overDataChgPwdSelectRes = overDayChgPwdMapper.selectAnlsEnvUserInfo(SessionUtil.getLoginUserId());

			mapRequestData.put("id"			, overDataChgPwdSelectRes.getAnlsEnvUserId() );
			mapRequestData.put("userName"	, overDataChgPwdSelectRes.getUserId() );
			mapRequestData.put("password"	, strUserApiPwd );
			mapRequestData.put("email"		, StringUtil.nvl(EncryptUtil.decryptAes256( overDataChgPwdSelectRes.getEncptEmlAddrVal() )));

			// [6_2]. USER 수정 API 호출
			mapResponseData = anlsApiService.apiConnectionUserUpdateInit(mapRequestData);

			if( mapResponseData == null || mapResponseData.isEmpty() ) {
				throw new CustomMessageException(strUpdateErrMsgCode); // '수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.'
			}
		}

		return overDayChgPwdSaveRes;
	}

	/*****************************************************
	 * [일정기간 지난 비밀번호 변경] (last_pwd_chg_dt를 지금으로 update) 처리
	 * @return OverDayChgPwdSaveRes
	 * @throws Exception
	 *****************************************************/
	public OverDayChgPwdSaveRes updatePwdChgDtNow() throws Exception {

		String strUpdateErrMsgCode = "result-message.messages.common.message.update.error"; //수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		// [[0]]. 반환할 정보들 & 변수 지정
		OverDayChgPwdSaveRes overDayChgPwdSaveRes = new OverDayChgPwdSaveRes();
		int saveCount = 0;

		// ============= [[1]]. validataion check
		/* 1-1. 로그인 했는지 체크 */
		if( SessionUtil.getLoginUserId() ==  null || "".equals(SessionUtil.getLoginUserId()) ) {
			throw new CustomMessageException("result-message.messages.login.message.need.login" + "||errorCode=noLoginId||"); //'로그인이 필요합니다.'
		}

		// ============= [[2]]. last_pwd_chg_dt를 지금으로 수정
		OverDayChgPwdSaveReq overDayChgPwdSaveReq = new OverDayChgPwdSaveReq();
		overDayChgPwdSaveReq.setUserId(SessionUtil.getLoginUserId());
		overDayChgPwdSaveReq.setMdfrId(SessionUtil.getLoginUserId());
		saveCount = overDayChgPwdMapper.updatePwdChgDtNow(overDayChgPwdSaveReq);
		if(! (saveCount > 0 ) ) {
			throw new CustomMessageException(strUpdateErrMsgCode); //'수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.'
		}

		return overDayChgPwdSaveRes;
	}


}

package com.katri.web.auth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.katri.common.Const;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.CommonUtil;
import com.katri.common.util.DateUtil;
import com.katri.common.util.EncryptUtil;
import com.katri.common.util.SessionUtil;
import com.katri.common.util.StringUtil;
import com.katri.web.auth.mapper.LoginMapper;
import com.katri.web.auth.model.LoginAuthrtMenuReq;
import com.katri.web.auth.model.LoginAuthrtMenuRes;
import com.katri.web.auth.model.LoginHistReq;
import com.katri.web.auth.model.LoginReq;
import com.katri.web.auth.model.LoginRes;
import com.katri.web.auth.model.TryLoginUserSelectRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class LoginService {

	/** 로그인 Mapper */
	private final LoginMapper loginMapper;

	/** 로그인 슈퍼키 */
	@Value("${login.password.super.key}")
	String loginPasswordSuperKey;

	/*****************************************************
	 * 로그인 처리
	 * @param request 요청객체
	 * @param loginReq 로그인할 사람의 정보
	 * @return LoginRes 로그인된 사람 정보
	 * @throws Exception
	*****************************************************/
	public LoginRes processLogin(HttpServletRequest request, @ModelAttribute @Valid LoginReq loginReq) throws Exception {

		// ============= [[0]]. 반환할 정보들 & 변수 지정
		/* 0-1. 반환할 정보들 */
		LoginRes loginDetail	= new LoginRes();
		String loginResCode 	= ""; //응답코드

		/* 0-2. IP */
		String ipAddress 		= CommonUtil.getClientIP(request);//현재 IP Address
		loginReq.setLastLgnIpAddr(ipAddress);

		/* 0-3. 로그인참조값(타임스탬프) */
		String lgnRefVal  		= DateUtil.getCurrentDate("","YYYYMMDDHHMISSMS");//현재 시간
		loginReq.setLgnRefVal(lgnRefVal);

		/* 0-4. 슈퍼키로 로그인 여부 */
		String superUserYN = "N";
		if( loginPasswordSuperKey.equals(loginReq.getLoginPwd()) ) {
			superUserYN = "Y";
		}

		// ============= [[1]]. 로그인 정보 조회
		loginDetail = loginMapper.selectLoginDetail(loginReq);

		if(loginDetail == null) { //---loginId로 조회한 값이 없을 경우
			loginDetail =  new LoginRes();

			loginResCode  = "fail_noMatchId";
			loginDetail.setLoginResCode(loginResCode);
			return loginDetail;
		}

		// ============= [[2]]. 사용자 데이터 체크

		/* 2-1. 5회 입력 실패로 잠금 상태 체크 */
		int loginFailCnt = StringUtil.nvlInt(loginDetail.getLgnFirCnt(), 0);
		if(loginFailCnt >= 5) {

			loginResCode  = "fail_overFailCnt";
			loginDetail.setLoginResCode(loginResCode);
			return loginDetail;
		}

		/* 2-2. 비밀번호 체크 (슈퍼키로 로그인 했을 경우 체크 안하고 넘어감) */
		if( ! "Y".equals(superUserYN) ) { // 일반적으로 로그인했을경우(슈퍼키로 로그인X)

			String pwd = StringUtil.nvl(loginDetail.getUserPwd());
			String eyptPwd = StringUtil.nvl(EncryptUtil.encryptSha256(loginReq.getLoginPwd(), loginReq.getLoginId()));
			if(! pwd.equals(eyptPwd)) { //---password가 맞지않을 경우

				//2-2-1. 틀린 개수 업데이트
				loginMapper.updateLoginFail(loginReq);

				loginResCode  = "fail_noMatchPwd";
				loginDetail.setLoginResCode(loginResCode);
				return loginDetail;

			}
		}

		/* 2-3. 상태체크 */
		String userSttCd = StringUtil.nvl(loginDetail.getUserSttCd());
		if( ! (Const.Code.UserSttCd.NORMAL).equals(userSttCd) ) { //정상이 아니면

			loginResCode = "fail_sttNoNomal";

			if( (Const.Code.UserSttCd.JOIN_APPLY).equals(userSttCd) ) { //[가입 신청]일 경우
				loginResCode = "fail_sttJoinApply";

			} else if( (Const.Code.UserSttCd.DRMNCY).equals(userSttCd) ) { //[휴면]일 경우
				loginResCode = "fail_sttDrmncy";

			} else if( (Const.Code.UserSttCd.WITHDRAWAL_APPLY).equals(userSttCd) ) { //[탈퇴 신청]일 경우
				loginResCode = "fail_sttWIthdrawalApply";
			}

			loginDetail.setLoginResCode(loginResCode);
			return loginDetail;

		}

		// ============= [[3]]. 로그인 성공시 로그인 정보 수정 + 로그인 HIST 등록
		/* 3-1. 로그인 정보 수정_마지막 로그인 시간, ip, 로그인참조값 update */
		loginMapper.updateLoginSucess(loginReq);

		/* 3-2. 로그인 HIST 등록 */
		//3-2-1. 로그인 HIST req 정보 setting
		LoginHistReq loginHistReq = new LoginHistReq();
		loginHistReq.setSiteTyCd(Const.Code.SiteTypeCd.FRONT); 		//사이트유형코드
		loginHistReq.setUserIpAddr(ipAddress); 						//사용자IP주소
		loginHistReq.setUserAgntVal(request.getHeader("User-Agent"));//사용자에이전트값
		loginHistReq.setCrtrId(loginDetail.getUserId());			//생성자아이디

		//3-2-2. 로그인 HIST 등록
		loginMapper.insertLoginHist(loginHistReq);

		// ============= [[4]]. 세션에 담을 메뉴 리스트 조회
		LoginAuthrtMenuReq loginAuthrtMenuReq = new LoginAuthrtMenuReq();
		loginAuthrtMenuReq.setLoginAuthrtGrpSn(loginDetail.getAuthrtGrpSn());	//로그인된 사람의 권한그룹일련번호
		loginAuthrtMenuReq.setSearchSiteTyCd(Const.Code.SiteTypeCd.FRONT); 		//사이트유형코드
		List<LoginAuthrtMenuRes> loginAuthrtMenuList = loginMapper.selectLoginAuthrtMenuList(loginAuthrtMenuReq);

		// ============= [[5]]. [기업마스터, 기업일반] 일 경우 세션에 담을 기업그룹수집동의이력 최종값이 Y인 개수(이걸로 [내손안의 시험인증] 메뉴 url달라짐)
		int entGrpClctAgreYCnt = 0;
		String userTypeCd = StringUtil.nvl(loginDetail.getUserTyCd());
		if( (Const.Code.UserTyCd.ENT_MASTER.equals(userTypeCd) || Const.Code.UserTyCd.ENT_GENERAL.equals(userTypeCd))
				&& loginDetail.getEntGrpSn() != null & loginDetail.getEntGrpSn() != 0 ){

			entGrpClctAgreYCnt = loginMapper.selectLoginEntGrpClctAgreYCnt(loginDetail.getEntGrpSn());
		}

		// ============= [[6]]. 세션 생성
		Map<String, Object> sessionMap = new HashMap<>();
		sessionMap.put("login_user_id"					, StringUtil.nvl(loginDetail.getUserId())); 	//사용자ID
		sessionMap.put("login_user_nm"					, StringUtil.nvl(loginDetail.getUserNm())); 	//사용자명
		sessionMap.put("login_authrt_grp_sn"			, StringUtil.nvl(loginDetail.getAuthrtGrpSn()));//권한그룹일렬번호
		sessionMap.put("login_user_ty_cd"				, StringUtil.nvl(loginDetail.getUserTyCd())); 	//사용자유형코드
		sessionMap.put("login_ent_grp_sn"				, StringUtil.nvl(loginDetail.getEntGrpSn())); 	//기업그룹일련번호
		sessionMap.put("login_brno"						, StringUtil.nvl(loginDetail.getBrno())); 		//사업자번호
		sessionMap.put("login_ent_grp_mng_no"			, StringUtil.nvl(loginDetail.getEntGrpMngNo()));//기업그룹관리번호
		sessionMap.put("login_ip_addr"					, loginReq.getLastLgnIpAddr()); 				//로그인시 IP
		sessionMap.put("login_ref_val"					, loginReq.getLgnRefVal()); 					//로그인시 로그인참조값
		sessionMap.put("login_ent_grp_clct_agre_y_cnt"	, entGrpClctAgreYCnt); //[기업마스터, 기업일반] 일 경우 세션에 담을 기업그룹수집동의이력 최종값이 Y인 개수
		sessionMap.put("login_authrt_menu_list"			, loginAuthrtMenuList); //권한에 따른 메뉴 리스트
		SessionUtil.setLoginSession(sessionMap);

		// ============= [[7]]. 반환데이터 셋팅
		/* 7-1. 로그인 응답 코드 셋팅 */
		loginDetail.setLoginResCode("success");

		return loginDetail;
	}

	/*****************************************************
	 * 로그인 정보 조회
	 * @param loginReq 검색조건
	 * @return 로그인 정보
	 *****************************************************/
	public LoginRes selectLoginDetail(LoginReq loginReq) throws Exception {
		return loginMapper.selectLoginDetail(loginReq);
	}

	/*****************************************************
	 * 로그인된 사람의 메뉴 조회(권한 적용한)
	 * @param loginAuthrtMenuReq 검색조건
	 * @return 메뉴 정보
	 *****************************************************/
	public LoginAuthrtMenuRes selectLoginAuthrtMenuDetail(LoginAuthrtMenuReq loginAuthrtMenuReq) throws Exception {
		return loginMapper.selectLoginAuthrtMenuDetail(loginAuthrtMenuReq);
	}

	/*****************************************************
	 * 로그인 시도한 사람의 정보 조회(LoginId로조회)
	 * @param loginId 로그인 시도한 사람의 입력 ID
	 * @return 로그인 시도한 사람의 정보
	 *****************************************************/
	public TryLoginUserSelectRes selectTryLoginUserDetail(String loginId) throws Exception {

		TryLoginUserSelectRes tryLoginUserDetail = null;
		tryLoginUserDetail = loginMapper.selectTryLoginUserDetail(loginId);

		if(tryLoginUserDetail == null) {
			throw new CustomMessageException("user 없음");
		}

		return tryLoginUserDetail;
	}



}

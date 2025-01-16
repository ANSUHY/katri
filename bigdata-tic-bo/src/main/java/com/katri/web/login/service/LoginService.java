package com.katri.web.login.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.Const;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.CommonUtil;
import com.katri.common.util.DateUtil;
import com.katri.common.util.EncryptUtil;
import com.katri.common.util.SessionUtil;
import com.katri.common.util.StringUtil;
import com.katri.web.login.mapper.LoginMapper;
import com.katri.web.login.model.LoginAuthrtMenuReq;
import com.katri.web.login.model.LoginAuthrtMenuRes;
import com.katri.web.login.model.LoginHistReq;
import com.katri.web.login.model.LoginReq;
import com.katri.web.login.model.LoginRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class LoginService {

	/** 로그인 Mapper */
	private final LoginMapper loginMapper;

	/*****************************************************
	 * 로그인 처리
	 * @param request 요청객체
	 * @param loginReq 로그인할 사람의 정보
	 * @return LoginRes 로그인된 사람 정보
	 * @throws Exception
	*****************************************************/
	public LoginRes processLogin(HttpServletRequest request, LoginReq loginReq) throws Exception {

		// ======= [[0]]. 반환할 정보들 & 변수 지정
		LoginRes loginDetail = new LoginRes();
		String ipAddress 		= CommonUtil.getClientIP(request);//현재 IP Address
		String lgnRefVal  		= DateUtil.getCurrentDate("","YYYYMMDDHHMISSMS");//현재 시간

		// ======= [[1]]. 로그인 정보 조회
		loginDetail = loginMapper.selectLoginDetail(loginReq);
		if(loginDetail == null) { //---loginId로 조회한 값이 없을 경우
			throw new CustomMessageException("result-message.messages.login.message.not.user.password"); //등록되지 않은 아이디이거나 아이디 또는 비밀번호가 회원정보와 일치하지 않습니다.
		}

		// ======= [[2]]. 사용자 데이터 체크
		/* 2-1. 비밀번호 체크*/
		String eyptPwd = StringUtil.nvl(EncryptUtil.encryptSha256(loginReq.getLoginPwd(), loginReq.getLoginId()));
		String pwd = StringUtil.nvl(loginDetail.getMngrPwd());
		if(! pwd.equals(eyptPwd)) { //---password가 맞지않을 경우
			throw new CustomMessageException("result-message.messages.login.message.not.user.password"); //등록되지 않은 아이디이거나 아이디 또는 비밀번호가 회원정보와 일치하지 않습니다.
		}

		// ======= [[3]]. 로그인 성공시 로그인 정보 수정 + 로그인 HIST 등록
		/* 3-1. 로그인 req 정보 setting*/
		loginReq.setLastLgnIpAddr(ipAddress); //ip
		loginReq.setLgnRefVal(lgnRefVal); //로그인참조값(타임스탬프)

		/* 3-2. 로그인 정보 수정_마지막 로그인 시간, ip, 로그인참조값 update */
		loginMapper.updateLoginSucess(loginReq);

		/* 3-3. 로그인 HIST req 정보 setting*/
		LoginHistReq loginHistReq = new LoginHistReq();
		loginHistReq.setSiteTyCd(Const.Code.SiteTypeCd.ADMIN); 		//사이트유형코드
		loginHistReq.setUserIpAddr(ipAddress); 						//사용자IP주소
		loginHistReq.setUserAgntVal(request.getHeader("User-Agent"));//사용자에이전트값
		loginHistReq.setCrtrId(loginDetail.getMngrId());			//생성자아이디

		/* 3-4. 로그인 HIST 등록 */
		loginMapper.insertLoginHist(loginHistReq);

		// ======= [[4]]. 세션에 담을 메뉴 리스트 조회
		LoginAuthrtMenuReq loginAuthrtMenuReq = new LoginAuthrtMenuReq();
		loginAuthrtMenuReq.setLoginAuthrtGrpSn(loginDetail.getAuthrtGrpSn());	//로그인된 사람의 권한그룹일련번호
		loginAuthrtMenuReq.setSearchSiteTyCd(Const.Code.SiteTypeCd.ADMIN); 		//사이트유형코드
		List<LoginAuthrtMenuRes> loginAuthrtMenuList = loginMapper.selectLoginAuthrtMenuList(loginAuthrtMenuReq);

		// ======= [[5]]. 세션 생성
		Map<String, Object> sessionMap = new HashMap<>();
		sessionMap.put("login_mngr_id"			, StringUtil.nvl(loginDetail.getMngrId()));
		sessionMap.put("login_mngr_nm"			, StringUtil.nvl(loginDetail.getMngrNm()));
		sessionMap.put("login_authrt_grp_sn"	, StringUtil.nvl(loginDetail.getAuthrtGrpSn()));
		sessionMap.put("login_ip_addr"			, loginReq.getLastLgnIpAddr()); //로그인시 IP
		sessionMap.put("login_ref_val"			, loginReq.getLgnRefVal()); 	//로그인시 로그인참조값
		sessionMap.put("login_authrt_menu_list"	, loginAuthrtMenuList);
		SessionUtil.setLoginSession(sessionMap);

		// ======= [[6]]. 반환데이터 셋팅
		/* 6-1. 로그인후 갈 페이지 셋팅 */
		if(loginAuthrtMenuList != null && loginAuthrtMenuList.size() != 0) {

			for(LoginAuthrtMenuRes menu : loginAuthrtMenuList) {
				if(menu.getMenuUrlAddr() != null && !("").equals(menu.getMenuUrlAddr() )) {
					loginDetail.setFirstMenuUrlAddr(menu.getMenuUrlAddr());
					break;
				}
			}
		} else {
			loginDetail.setFirstMenuUrlAddr("/noAuth");
		}

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



}

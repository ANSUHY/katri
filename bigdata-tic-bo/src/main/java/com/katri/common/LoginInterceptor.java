package com.katri.common;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.google.gson.Gson;
import com.katri.common.model.ExcludePath;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.CommonUtil;
import com.katri.common.util.SessionUtil;
import com.katri.common.util.StringUtil;
import com.katri.web.login.model.LoginAuthrtMenuReq;
import com.katri.web.login.model.LoginAuthrtMenuRes;
import com.katri.web.login.model.LoginReq;
import com.katri.web.login.model.LoginRes;
import com.katri.web.login.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 공통 업무</li>
 * <li>서브 업무명 : 공통 관련</li>
 * <li>설	 명 : 로그인 체크 Interceptor</li>
 * <li>작  성  자 : Lee Han Seong</li>
 * <li>작  성  일 : 2021. 01. 18.</li>
 * </ul>
 * <pre>
 * ======================================
 * 변경자/변경일 :
 * 변경사유/내역 :
 * ======================================
 * </pre>
 ***************************************************/
@Component
@RequiredArgsConstructor
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

	/** 공통 Service */


	/** 메시지 Component */
	@Autowired
	MessageSource messageSource;

	/** BO 도메인 PORT */
	@Value("${domain.web.bo.url}")
	String domainWebBoUrl;

	/** FO 도메인 PORT */
	@Value("${domain.web.fo.url}")
	String domainWebFoUrl;

	/** 접속한 시스템 정보 */
	@Value("${server.system}")
	String severSystemInfo;

	/** 로그인 Service */
	private final LoginService loginService;

	/** 로그인 허용 url 정보 */
	private final ExcludePath excludePath;

	/**************************************************************
	 * Controller 호출전 실행되는 interceptor handler
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @param handler handler
	 * @return boolean
	 * @throws Exception
	 **************************************************************/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 헤더 전체정보 보기
		Enumeration<String> em = request.getHeaderNames();

		String serverClsCd = "";	// 서버 구분코드(BO, FO)
		String connUrl = null;		// 접속 URL
		String ajaxForward = "";	// Ajax 통신 유무
		String errorMsg = "";		// 에러 메세지
		int errorStatus = 500;
		boolean isNonAuth = false;	// 미권한 여부

		while(em.hasMoreElements()){
			String name = StringUtil.nvl(em.nextElement()) ;
			String val = StringUtil.nvl(request.getHeader(name)) ;
			if(name.toLowerCase().contentEquals("host")) {
				// 접속 URL
				connUrl = val;
			} else if(name.toLowerCase().contentEquals("ajax-forward")) {
				// ajax통신 체크
				ajaxForward = val;
			}

			log.info("PreCheckInterceptor Headers ==================================> ["+name.replaceAll("[\r\n]","")+", "+val.replaceAll("[\r\n]","")+"]");

		}

		// 포트로 시스템 구분코드 셋팅
		if(connUrl != null) {
			for(String url: connUrl.split(",")) {
				if((","+domainWebBoUrl+",").contains(","+url+",")) {
					serverClsCd = Const.Session.SERVER_CLS_CD_BO;
					break;
				} else if((","+domainWebFoUrl+",").contains(","+url+",")) {
					serverClsCd = Const.Session.SERVER_CLS_CD_FO;
					break;
				}
			}
		}

		// 세션과 접속한 시스템구분이 다른경우 세션 셋팅
		if(request.getSession().getAttribute("server_cls_cd") == null
				|| !((String)request.getSession().getAttribute("server_cls_cd")).contentEquals(serverClsCd)) {
			request.getSession().setAttribute("server_cls_cd", serverClsCd);
		}

		String uri = StringUtil.nvl(request.getRequestURI());

		log.info("serverClsCd==="+serverClsCd.replaceAll("[\r\n]","")+", "+StringUtil.nvl(connUrl).replaceAll("[\r\n]","")+", "+uri.replaceAll("[\r\n]",""));

		// 로그인 세션 체크
		if (loginCheck(request, request.getSession(), serverClsCd)) {

			//========== ## 1. 로그인 된 사람 DB에 있는 IP와 비교해서 기존 로그인 된사람 로그아웃 시키기(DEV로들어온거 제외하고)==========
			if( (!"dev".equals(severSystemInfo)) && (!"/logout".equals(request.getRequestURI()))
					&& (request.getSession().getAttribute(SessionUtil.getSessionKey() + serverClsCd) != null) ) {

				// 1-1. 현재정보
				String strNowIp = CommonUtil.getClientIP(request); //현재 IP Address
				String strLoginMngrId = SessionUtil.getLoginMngrId(); //로그인된 아이디
				String strLoginRefVal = SessionUtil.getLoginRefVal(); //로그인시 로그인참조값

				// 1-2. 로그인 정보 조회
				LoginReq loginReq = new LoginReq();
				loginReq.setLoginId(strLoginMngrId);
				LoginRes loginDetail = loginService.selectLoginDetail(loginReq);

				// 1-3. (현재 ip와 로그인참조값)비교해서 안맞으면 처리
				if(loginDetail != null) {

					if( (! strNowIp.equals(loginDetail.getLastLgnIpAddr()) ) || (! strLoginRefVal.equals(loginDetail.getLgnRefVal()) ) ){ //안맞으면

						//로그아웃시키기
						SessionUtil.removeLoginSession();

						//msg, code셋팅
						errorMsg = "중복 로그인 입니다.";
						errorStatus = 440; // 세션 Timeout 에러 코드

						//통신의 종류에 따라 처리
						if(StringUtils.isBlank(ajaxForward)) { //Ajax 통신이 아닐경우

							if(uri.endsWith("PopUp")) { //popup 일경우 - 자기창 닫고 부모창 로그인 페이지로 이동
								CommonUtil.alertMsgFuncParentFunc(response, errorMsg, "window.close();", "location.href='/login/login'");
							} else { //popup 외 - 로그인 페이지로 이동
								this.goLoginPage(request, response, errorMsg, errorStatus);
							}
							return false;

						} else { //Ajax 통신일 경우 - response에 셋팅

							this.setResponseError(request, response, errorMsg, errorStatus);
							return false;

						}

					}
				}
			}
			//========== // ## 1. 끝 ==========

			//========== ## 2. 권한 체크 : ajax일때 안함, popup일때 안함 ==========
			if( StringUtils.isBlank(ajaxForward) && ! uri.endsWith("PopUp")
					&& Const.Session.SERVER_CLS_CD_BO.equals(serverClsCd) && !loginExcludePath(request.getRequestURI(), serverClsCd)) {

				// 권한 체크
				LoginAuthrtMenuReq loginAuthrtMenuReq = new LoginAuthrtMenuReq();
				loginAuthrtMenuReq.setLoginAuthrtGrpSn(SessionUtil.getLoginAuthrtGrpSn());	//로그인된 사람의 권한그룹일련번호
				loginAuthrtMenuReq.setSearchSiteTyCd(Const.Code.SiteTypeCd.ADMIN); 			//사이트유형코드
				loginAuthrtMenuReq.setSearchMenuUrl(uri);									//접속한 uri

				LoginAuthrtMenuRes loginAuthrtMenu = loginService.selectLoginAuthrtMenuDetail(loginAuthrtMenuReq);
				if(loginAuthrtMenu != null) { //권한이 있을 경우

					request.getSession().setAttribute("curr_menu_sn"			, loginAuthrtMenu.getMenuSn()); //메뉴일렬번호 셋팅

				} else { //권한이 없을 경우
					isNonAuth = true;

					errorMsg = messageSource.getMessage("result-message.messages.login.message.not.auth", null, SessionUtil.getLocale()); //해당 아이디에 권한이 부여되어 있지 않습니다. 관리자에게 문의하여 주십시요.
					CommonUtil.alertMsgBack(response, errorMsg);
					return false;
				}

			}
			//========== // ## 2. 끝 ==========

			if(!isNonAuth) {
				return true;
			}

		} else {

			errorMsg = "로그인이 필요합니다.";
			errorStatus = 440;	// 세션 Timeout 에러 코드

			//통신의 종류에 따라 처리
			if(StringUtils.isBlank(ajaxForward)) { //Ajax 통신이 아닐경우

				if(uri.endsWith("PopUp")) { //popup 일경우 - 자기창 닫고 부모창 로그인 페이지로 이동
					CommonUtil.alertMsgFuncParentFunc(response, errorMsg, "window.close();", "location.href='/login/login'");
				} else { //popup 외 - 로그인 페이지로 이동
					this.goLoginPage(request, response, errorMsg, errorStatus);
				}
				return false;

			} else { //Ajax 통신일 경우 - response에 셋팅

				this.setResponseError(request, response, errorMsg, errorStatus);
				return false;

			}

		}

		this.setResponseError(request, response, errorMsg, errorStatus);

		return false;

	}

	/*****************************************************
	 *  로그인페이지로 이동
	 * @param request
	 * @param response
	 * @param errorMsg
	 * @param errorStatus void
	*****************************************************/
	private void goLoginPage(HttpServletRequest request, HttpServletResponse response, String errorMsg, int errorStatus) {

		// 이메일이나 외부에서 로그인시 리다이렉트 탭 호줄할때 사용
		if(!StringUtils.isBlank(StringUtil.nvl(request.getParameter("directGbn")))) {
			request.getSession().setAttribute("direct_gbn", request.getParameter("directGbn"));
			request.getSession().setAttribute("direct_param1", request.getParameter("param1"));
			request.getSession().setAttribute("direct_param2", request.getParameter("param2"));
			request.getSession().setAttribute("direct_param3", request.getParameter("param3"));
			request.getSession().setAttribute("direct_param4", request.getParameter("param4"));
			request.getSession().setAttribute("direct_param5", request.getParameter("param5"));
		}

		CommonUtil.moveUrlAlertMsg(response, errorMsg, "/login/login");

	}

	/*****************************************************
	 * Response에 에러 셋팅
	 * @param request
	 * @param response
	 * @param errorMsg
	 * @param errorStatus void
	 * @throws IOException
	*****************************************************/
	private void setResponseError(HttpServletRequest request, HttpServletResponse response, String errorMsg, int errorStatus) throws IOException {

		String jsonString = new Gson().toJson(ResponseDto.builder()
				  .resultMessage(errorMsg)
				  .resultCode(errorStatus)
				  .build()
				);

		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
		responseWrapper.setContentType("application/json; charset=UTF-8");
		response.setStatus(errorStatus);
		response.getWriter().write(jsonString);

		log.error("\n [=== RESPONSE] \n [THREAD-ID] {} \n [CODE] {} \n [DATA] {} \n",
		Thread.currentThread().getId(), response.getStatus(), jsonString);

	}

	/**************************************************************
	 * 로그인 체크
	 *
	 * @param request 요청정보객체
	 * @param session session
	 * @return boolean
	 **************************************************************/
	private boolean loginCheck(HttpServletRequest request, HttpSession session, String serverClsCd) {

		return session.getAttribute(SessionUtil.getSessionKey() + serverClsCd) != null ||
				isTest(request) ||
				loginExcludePath(request.getRequestURI(), serverClsCd);
	}

	/**************************************************************
	 * 로그인 체크 제외 URI
	 *
	 * @param uri 요청 RUI
	 * @return boolean
	 **************************************************************/
	private boolean loginExcludePath(String uri, String serverClsCd) {
		boolean isExclude = false;
		for(String path: excludePath.getCommon()) {
			if(uri.equals(path)) {
				isExclude = true;
				break;
			}
		}

		// 관리자 허용 URL 체크
		if(!isExclude && (Const.Session.SERVER_CLS_CD_BO.contentEquals(serverClsCd))) {
			for(String path: excludePath.getBack()) {
				if(uri.contains(path)) {
					isExclude = true;
					break;
				}
			}
		}

		// 프론트 허용 URL 체크
		if(!isExclude && (Const.Session.SERVER_CLS_CD_FO.contentEquals(serverClsCd))) {
			for(String path: excludePath.getFront()) {
				if(uri.contains(path)) {
					isExclude = true;
					break;
				}
			}
		}

		// 특정 uri 의 하위 uri 를 비교하기 위해서는 startsWith 를 사용하여 비교합니다.
		return isExclude ||
				uri.contains(".html") ||
				uri.contains("/api") ||
				uri.contains("/images") ||
				uri.contains("/css") ||
				uri.contains("/js") ||
				uri.contains("/webfonts") ||
				uri.contains("/sample") ||
				uri.contains("favicon.ico") ||
				uri.contains("swagger") ||
				uri.contains("smarteditor") ||
				uri.contains("/file/") ||
				uri.contains("/upload/")
				;
	}

	/**************************************************************
	 * junit 테스트 인지 체크
	 *
	 * @param request 요청정보객체
	 * @return boolean
	 **************************************************************/
	private boolean isTest(HttpServletRequest request) {
		@SuppressWarnings("rawtypes")
		Enumeration enumeration = request.getAttributeNames();

		while (enumeration.hasMoreElements()) {
			if (enumeration.nextElement().toString().startsWith("org.springframework.test")) {
				return true;
			}
		}

		return false;
	}

}

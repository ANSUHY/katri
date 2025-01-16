package com.katri.common.util;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.katri.web.login.model.LoginAuthrtMenuRes;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 로그인 정보 취득</li>
 * <li>서브 업무명 : 공통업무</li>
 * <li>설	   명 : 로그인 정보 취득 하는 class</li>
 * <li>작  성  자 : Lee Han Seong</li>
 * <li>작  성  일 : 2021. 01. 18.</li>
 * </ul>
 * <pre>
 * ======================================
 * 변경자/변경일 :
 * 변경사유/내역 :
 * ======================================
 * </pre>
 **************************************************/
public class SessionUtil {
	/** 세션 키 */
	private static String sessionKey = "SS_KATRI_";

	/** 서버구분코드 키 */
	public static String serverClsCdKey = "server_cls_cd";

	public static String[] arrPage = {serverClsCdKey, "curr_menu_id", "cre_auth_yn", "read_auth_yn", "curr_url", "direct_gbn"
								, "direct_param1", "direct_param2", "direct_param3", "direct_param4", "direct_param5"};
	/*****************************************************
	 * session Attribute 값 취득
	 * @param key 세션키명
	 * @return
	 *****************************************************/
	@SuppressWarnings("rawtypes")
	public static Object getSessionAttribute(String key) {
		ServletRequestAttributes	attr	= (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession					session = attr.getRequest().getSession();

		if(session.getAttribute(sessionKey + getServerClsCd()) != null && !isAllowKey(key)){
			return ((Map)session.getAttribute(sessionKey + getServerClsCd())).get(key);
		}

		return session.getAttribute(key);
	}

	/*****************************************************
	 * 세션 정보 취득 허용 여부
	 * @param strKey 세션키
	 * @return 허용여부(true/false)
	 *****************************************************/
	private static boolean isAllowKey(String strKey) {

		for(int nLoop=0; nLoop < arrPage.length; nLoop++) {
			if (strKey.equals(arrPage[nLoop])) {
				return true;
			}
		}

		return false;
	}

	/*****************************************************
	 * 세션 정보 취득 여부
	 * @return 세션여부(true/false)
	 *****************************************************/
	public static boolean isLoginCheck() {
		ServletRequestAttributes	attr	= (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession					session = attr.getRequest().getSession();

		return session.getAttribute(serverClsCdKey) != null
				&& session.getAttribute(sessionKey + getServerClsCd()) != null;
	}

	/*****************************************************
	 * Session정보 Clear
	 *****************************************************/
	public static void removeLoginSession() {
		// Session값 Setting
		ServletRequestAttributes	attr	= (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession					session	= attr.getRequest().getSession();
//
//		session.removeAttribute(sessionKey + getServerClsCd());
//
//		for(String page: arrPage){
//			session.removeAttribute(page);
//		}
		session.invalidate();
	}

	/*****************************************************
	 * 세션 정보 조회
	 * @return
	 *****************************************************/
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getLoginSession() {
		ServletRequestAttributes	attr	= (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession					session = attr.getRequest().getSession();

		return (Map<String, Object>) session.getAttribute(sessionKey + getServerClsCd());
	}

	/*****************************************************
	 * 세션 정보 저장
	 * @param loginInfo
	 * @return
	 *****************************************************/
	public static void setLoginSession(Map<String, Object> loginInfo) {
		ServletRequestAttributes	attr	= (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession					session = attr.getRequest().getSession();

		session.setAttribute(sessionKey + getServerClsCd(), loginInfo);
	}

	/*****************************************************
	 * 서버구분코드 세션 저장
	 * @param serverClsCd 서버구분코드
	 * @return
	 *****************************************************/
	public static void setServerClsCd(String serverClsCd) {
		ServletRequestAttributes	attr	= (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession					session = attr.getRequest().getSession();

		session.setAttribute(serverClsCdKey, serverClsCd);
	}

	/*****************************************************
	 * 세션 키
	 * @return 세션키
	 *****************************************************/
	public static String getSessionKey() {
		return sessionKey;
	}

	/*****************************************************
	 * 서버 구분 코드(BO, FO)
	 * @return 서버구분코드
	 *****************************************************/
	public static String getServerClsCd() {
		ServletRequestAttributes	attr	= (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession					session = attr.getRequest().getSession();

		return session.getAttribute(serverClsCdKey) == null? "": (String) session.getAttribute(serverClsCdKey);
	}

	/**
	 * ***************************************************
	 * Locale 취득
	 * @return
	 ****************************************************
	 */
	public static Locale getLocale() {
		return getSessionAttribute("locale") == null ? Locale.KOREA: (Locale) getSessionAttribute("locale");
	}

	/**
	 * ***************************************************
	 * 로그인 사용자ID 취득
	 * @return
	 ****************************************************
	 */
	public static String getLoginMngrId() {
		return getSessionAttribute("login_mngr_id") == null ? null : (String) getSessionAttribute("login_mngr_id");
	}

	/**
	 * ***************************************************
	 * 로그인 사용자명 취득
	 * @return
	 ****************************************************
	 */
	public static String getLoginMngrNm() {
		return getSessionAttribute("login_mngr_nm") == null ? null : (String) getSessionAttribute("login_mngr_nm");
	}

	/**
	 * ***************************************************
	 * 로그인 사용자 권한그룹일련번호 취득
	 * @return
	 ****************************************************
	 */
	public static Integer getLoginAuthrtGrpSn() {
		return getSessionAttribute("login_authrt_grp_sn") == null ? null : Integer.parseInt( (String) getSessionAttribute("login_authrt_grp_sn") );
	}

	/**
	 * ***************************************************
	 * 로그인시 IP 취득
	 * @return
	 ****************************************************
	 */
	public static String getLoginIpAddr() {
		return getSessionAttribute("login_ip_addr") == null ? null : (String) getSessionAttribute("login_ip_addr");
	}

	/**
	 * ***************************************************
	 * 로그인시 로그인참조값
	 * @return
	 ****************************************************
	 */
	public static String getLoginRefVal() {
		return getSessionAttribute("login_ref_val") == null ? null : (String) getSessionAttribute("login_ref_val");
	}

	/**
	 * ***************************************************
	 * 로그인 사람의 권한에 따른 메뉴 리스트
	 * @return
	 ****************************************************
	 */
	@SuppressWarnings("unchecked")
	public static List<LoginAuthrtMenuRes> getLoginAuthrtMenuList() {
		return getSessionAttribute("login_authrt_menu_list") == null ? null : (List<LoginAuthrtMenuRes>) getSessionAttribute("login_authrt_menu_list");
	}
}
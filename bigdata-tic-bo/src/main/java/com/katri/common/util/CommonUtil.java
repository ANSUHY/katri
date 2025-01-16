package com.katri.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.katri.common.Const;
import com.katri.common.model.Common;

import lombok.extern.slf4j.Slf4j;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 공통 유틸 업무</li>
 * <li>서브 업무명 : 공통유틸 정보 관리</li>
 * <li>설	 명 : 공통유틸 정보</li>
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
@Slf4j
public class CommonUtil {

	/** Random 객체 */
	private static SecureRandom random =  new SecureRandom();

	/** AGENT 명 */
	private static String userAgentHeaderNm = "User-Agent";


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////~~~~~~request(접속정보) 관련 시작~~~~~///////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*****************************************************
	 * 클라이언트IP를 리턴처리
	 * @param HttpServletRequest : request
	 * @return String 클라이언트ip
	 *****************************************************/
	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if(ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if(ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if(ip == null) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

	/*****************************************************
	 * 브라우저별 파일명 리턴
	 * @param filename 파일명
	 * @return String 인코딩 파일명
	 *****************************************************/
	public static String getDisposition(HttpServletRequest request, String filename) {
		String browser = getBrowser(request.getHeader("User-Agent"));
		String encodedFilename = "";

		try {
			if(browser.equals("Firefox")) {
				encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
			} else if(browser.equals("Opera")) {
				encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
			} else if(browser.equals("Chrome")) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < filename.length(); i++) {
					char c = filename.charAt(i);
					if(c > '~') {
						sb.append(URLEncoder.encode("" + c, "UTF-8"));
					} else {
						sb.append(c);
					}
				}
				encodedFilename = sb.toString();
			} else {
				encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20"); // MSIE 외 모든 브라우저
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		return encodedFilename;
	}

	/*****************************************************
	 * 브라우저 명칭 구하기
	 * @param userAgent 유저 Agent 정보
	 * @return String 브라우저 명칭
	 *****************************************************/
	public static String getBrowser(String userAgent) {
		String browser = "";

		if(userAgent.indexOf("Trident") > -1) { // IE
			browser = "MSIE";
		} else if(userAgent.indexOf("Edge") > -1) { // Edge
			browser = "Edge";
		} else if(userAgent.indexOf("Whale") > -1) { // Naver Whale
			browser = "Whale";
		} else if(userAgent.indexOf("Opera") > -1 || userAgent.indexOf("OPR") > -1) { // Opera
			browser = "Opera";
		} else if(userAgent.indexOf("Firefox") > -1) { // Firefox
			browser = "Firefox";
		} else if(userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") == -1 ) { // Safari
			browser = "Safari";
		} else if(userAgent.indexOf("Chrome") > -1) { // Chrome
			browser = "Chrome";
		}

		return browser;
	}

	/*****************************************************
	 * 쿠키 정보 가지고 오기
	 * @param request Request 객체
	 * @param cookieName 쿠키명
	 * @return String 쿠키 정보
	 *****************************************************/
	public static String getCookieObject(HttpServletRequest request, String cookieName) throws UnsupportedEncodingException {
		// 쿠키 목록
		Cookie[] cookies = request.getCookies();

		if (cookies == null)
			return null;

		// 쿠키정보 문자열
		String value = null;

		for (int i = 0; i < cookies.length; i++) {
			if (cookieName.equals(cookies[i].getName())) {
				value = cookies[i].getValue();
				if ("".equals(value))
					value = null;
				break;
			}
		}

		return (value == null ? null : URLDecoder.decode(value, "euc-kr"));
	}

	/*****************************************************
	 * 쿠키 정보 설정
	 * @param response Response 객체
	 * @param name 쿠키명
	 * @param value 쿠키값
	 * @param iMinute 쿠키저장시간
	 *****************************************************/
	public static void setCookieObject(HttpServletResponse response, String name, String value, int iMinute)
			throws java.io.UnsupportedEncodingException {
		// Cookie 객체
		Cookie cookie = new Cookie(name, URLEncoder.encode(value, "euc-kr"));
		cookie.setSecure(true);
		cookie.setHttpOnly(true);

		int t = (60 * iMinute);
			if ( t > 3600 ) {
	         t = 3600;
		}

		cookie.setMaxAge(t);
		cookie.setPath("/");

		response.addCookie(cookie);
	}


	/*****************************************************
	 * 접속한 웹브라우저명 조회
	 * @return
	 *****************************************************/
	public static String getClntWebKind(HttpServletRequest request) {
		//HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String userAgent = request.getHeader(userAgentHeaderNm);

		// 웹브라우저 종류 조회
		String webKind = "";
		if (userAgent.toUpperCase().indexOf("GECKO") != -1) {
			if (userAgent.toUpperCase().indexOf("NESCAPE") != -1) {
				webKind = "Netscape (Gecko/Netscape)";
			} else if (userAgent.toUpperCase().indexOf("FIREFOX") != -1) {
				webKind = "Mozilla Firefox (Gecko/Firefox)";
			} else {
				webKind = "Mozilla (Gecko/Mozilla)";
			}
		} else if (userAgent.toUpperCase().indexOf("MSIE") != -1) {
			if (userAgent.toUpperCase().indexOf("OPERA") != -1) {
				webKind = "Opera (MSIE/Opera/Compatible)";
			} else {
				webKind = "Internet Explorer (MSIE/Compatible)";
			}
		} else if (userAgent.toUpperCase().indexOf("SAFARI") != -1) {
			if (userAgent.toUpperCase().indexOf("CHROME") != -1) {
				webKind = "Google Chrome";
			} else {
				webKind = "Safari";
			}
		} else if (userAgent.toUpperCase().indexOf("THUNDERBIRD") != -1) {
			webKind = "Thunderbird";
		} else {
			webKind = "Other Web Browsers";
		}

		return webKind;
	}
	/*****************************************************
	 * 웹뷰 or 웹브라우저 접속 확인
	 * @return 웹뷰(APP), 웹브라우져(MW)
	 *****************************************************/
	public static String getWebType() {
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String agent = req.getHeader(userAgentHeaderNm);

		String webBrowzType = "";

		if(agent != null) {
			if (agent.indexOf("Mobile") > -1) {
				webBrowzType = "MW";
			} else {
				webBrowzType = "PC";
			}
		}

		return webBrowzType;
	}

	/*****************************************************
	 * 웹뷰 or 웹브라우저 접속 확인
	 * @return 웹뷰(APP), 웹브라우져(MW)
	 *****************************************************/
	public static boolean getMobileUrlChk(HttpServletRequest request) {

		boolean isMobileUrlChk = false;

		if(!"/".equals(request.getRequestURI())
				&& ("/m".equals(request.getRequestURI())
				|| request.getRequestURI().indexOf("/m/") >= 0)
				) {
			isMobileUrlChk = true;
		}

		return isMobileUrlChk;
	}


	/*****************************************************
	 * 접속한 OS명 조회
	 * @return
	 *****************************************************/
	public static String getClntOsInfo(HttpServletRequest request) {
		String userAgent = request.getHeader(userAgentHeaderNm);

		String osInfo = "";

		if(userAgent != null) {
			if (userAgent.indexOf("WINDOWS NT 6.1") > -1) {
				osInfo = "Windows 7";
			} else if (userAgent.indexOf("WINDOWS NT 6.2") > -1) {
				osInfo = "Windows 8";
			} else if (userAgent.indexOf("WINDOWS NT 6.3") > -1) {
				osInfo = "Windows 8.1";
			} else if (userAgent.indexOf("WINDOWS NT 10.0") > -1) {
				osInfo = "Windows 10";
			} else if (userAgent.indexOf("WINDOWS NT 6.0") > -1) {
				osInfo = "Windows Vista";
			} else if (userAgent.indexOf("WINDOWS NT 5.1") > -1) {
				osInfo = "Windows XP";
			} else if (userAgent.indexOf("WINDOWS NT 5.0") > -1) {
				osInfo = "Windows 2000";
			} else if (userAgent.indexOf("WINDOWS NT 4.0") > -1) {
				osInfo = "Windows NT";
			} else if (userAgent.indexOf("WINDOWS 98") > -1) {
				osInfo = "Windows 98";
			} else if (userAgent.indexOf("WINDOWS 95") > -1) {
				osInfo = "Windows 95";
			} else if (userAgent.indexOf("IPHONE") > -1) {
				osInfo = "iPhone";
			} else if (userAgent.indexOf("IPAD") > -1) {
				osInfo = "iPad";
			} else if (userAgent.indexOf("ANDROID") > -1) {
				osInfo = "Android";
			} else if (userAgent.indexOf("MAC") > -1) {
				osInfo = "Mac";
			} else if (userAgent.indexOf("LINUX") > -1) {
				osInfo = "Linux";
			} else {
				osInfo = "OtherOS";
			}
		}

		return osInfo;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////~~~~~~request(접속정보) 관련 시작~~~~~///////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////~~~~~~Script 관련 시작~~~~~//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*****************************************************
	 * Alert 메세지 출력 후 화면 이동
	 * @param HttpServletResponse : response
	 * @param msg : Alert 메세지
	 * @param returnUrl : 이동 url
	 * @return Object
	 *****************************************************/
	public static Object moveUrlAlertMsg(HttpServletResponse response, String msg, String returnUrl) {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter output = null;
		try {
			output = response.getWriter();

			output.println("<script type='text/javascript' >");
			output.println("alert('"+msg.replaceAll("\\'", "\\\"")+"');");
			output.println("location.href='" + returnUrl+"';");
			output.println("</script>");

			output.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			output.close();
		}
		return null;
	}

	/*****************************************************
	 * 메시지 알럿으로 띄우면서 back
	 * @param response 반환 정보
	 * @param msg 쓸 메시지
	 *****************************************************/
	public static Object alertMsgBack(HttpServletResponse response, String msg) {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter output = null;
		try {
			output = response.getWriter();

			output.println("<script type='text/javascript' charset='utf-8'>");
			output.println("alert('" + msg.replaceAll("\\'", "\\\"") + "');");
			output.println("window.history.back();");
			output.println("</script>");
			output.println("</script>");

			output.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			output.close();
		}

		return null;
	}

	/*****************************************************
	 * 메시지 알럿으로 띄우면서 function
	 * @param response 반환 정보
	 * @param msg 쓸 메시지
	 * @param func script function
	*****************************************************/
	public static Object alertMsgFunc(HttpServletResponse response, String msg, String func){
		response.setContentType("text/html;charset=utf-8");
		PrintWriter output = null;
		try {
			output = response.getWriter();

			output.println("<script type='text/javascript' charset='utf-8'>");
			output.println("alert('" + msg.replaceAll("\\'", "\\\"") + "');");
			output.println("top.location.href='javascript:" + func + "';");
			output.println("</script>");

			output.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			output.close();
		}

		return null;
	}

	/*****************************************************
	 * 메시지 알럿으로 띄우면서 function + 부모의 function 이용
	 * @param response 반환 정보
	 * @param msg 쓸 메시지
	 * @param func script function
	 * @param parentFunc 부모창의 script function
	*****************************************************/
	public static Object alertMsgFuncParentFunc(HttpServletResponse response, String msg, String func, String parentFunc){
		response.setContentType("text/html;charset=utf-8");
		PrintWriter output = null;
		try {
			output = response.getWriter();

			output.println("<script type='text/javascript' charset='utf-8'>");
			output.println("alert('" + msg.replaceAll("\\'", "\\\"") + "');");
			output.println("opener." + parentFunc + ";");
			output.println("top.location.href='javascript:" + func + "';");
			output.println("</script>");

			output.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			output.close();
		}

		return null;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////~~~~~~Script 관련 끝~~~~~//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////~~~~~~페이징 관련 시작~~~~~//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*****************************************************
	 * 페이징 공통 변수 셋팅
	 * @param currPage 현재페이지 번호
	 * @param rowCount 로우 카운팅 수
	 * @return Common 공통 모델
	 *****************************************************/
	public static Common setPageParams(int currPage, int rowCount) {

		Common common = new Common();

		if(currPage <= 0) currPage = 1;
		if(rowCount <= 0) rowCount = Const.Paging.ROW_COUNT;

		common.setStartRow(((currPage - 1) * rowCount + 1) - 1);
		common.setEndRow(rowCount);

		return common;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////~~~~~~페이징 관련 끝~~~~~//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////~~~~~~파일 관련 시작~~~~~//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*****************************************************
	 * 파일명에서 확장자 축출
	 * @param strFileName 파일명
	 * @return String 파일확장자
	 *****************************************************/
	public static String getFileExt(String strFileName) {
		if (strFileName != null && !"".equals(strFileName)) {
			return strFileName.substring(strFileName.lastIndexOf('.') + 1, strFileName.length());
		} else {
			return "";
		}
	}

	/*****************************************************
	 * 파일사이즈 정보로 변환
	 * @param obj 변환 할 객체
	 * @return String 변환 완료된 문자열
	 *****************************************************/
	public static String getFileSize(Object obj) {
		// 변환 문자
		String strVal = "";

		if ( obj == null ) {
			return "";
		}

		// 파일사이즈
		Long nSize = Long.parseLong(obj.toString());

		nSize /= 1000; // Byte를 KB로 환산

		if (nSize < 1)
			nSize = 1L;

		if ( nSize < 1000 ) {
			strVal = String.format("%dKB", nSize);
		} else {
			strVal = String.format("%.2fMB", nSize/1000.0);
		}

		return strVal;
	}

	/*****************************************************
	 * 랜덤숫자 축출
	 * @param limit 랜덤숫자 제한 자릿수
	 * @return int 변환 완료된 랜덤숫자
	 *****************************************************/
	public static int getRandomInt(int limit) {
		// 랜덤 숫자
		int number = random.nextInt();
		number = (number >>> 16) & 0xffff;
		number /= (0xffff / limit);

		return number;
	}

	/*****************************************************
	 * 고유번호 UUID 조회
	 * @return String 고유번호 UUID
	 *****************************************************/
	public static String getUniqueId() {
		return UUID.randomUUID().toString();
	}

	/*****************************************************
	 * 난수 조회
	 * @return String 난수
	 *****************************************************/
	public static String getUniqueValue() {
		// 난수
		String strVal="";

		strVal = DateUtil.getCurrentDate("", "YYYYMMDDHHMISS");
		Random rand =  new SecureRandom();
		strVal += Long.toString(rand.nextLong());

		return strVal;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////~~~~~~파일 관련 끝~~~~~//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////~~~~~~체크 관련 시작~~~~~//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*****************************************************
	 * 객체형 비워있는지 여부 체크
	 * @param Object : 비워있는지 검사할 객체
	 * @return boolean 체크여부
	 *****************************************************/
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		if(obj == null) {
			return true;
		} else if(obj instanceof Map) {
			return ((Map) obj).isEmpty();
		} else if(obj instanceof Collection) {
			return ((Collection) obj).isEmpty();
		} else if(obj.getClass().isArray()) {
			return (Array.getLength(obj) == 0);
		} else if(obj instanceof String) {
			return (((String) obj).trim().length() == 0);
		} else if(obj instanceof File) {
			return (!((File) obj).exists());
		}

		return false;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////~~~~~~체크 관련 끝~~~~~//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////~~~~~~vo 관련 시작~~~~~//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*****************************************************
	 * vo를 map형식으로 변환해서 반환
	 * @param vo VO
	 * @return
	 * @throws Exception
	 *****************************************************/
	public static Map<String, Object> convertVoToMap(Object vo) {
		return convertVoToMap(vo, null);
	}

	/*****************************************************
	 * 특정 변수를 제외해서 vo를 map형식으로 변환해서 반환.
	 * @param vo VO
	 * @param arrExceptList 제외할 property 명 리스트
	 * @return
	 * @throws Exception
	 *****************************************************/
	public static Map<String, Object> convertVoToMap(Object vo, String[] arrExceptList) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			BeanInfo info = Introspector.getBeanInfo(vo.getClass());
			for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
				Method reader = pd.getReadMethod();
				if (reader != null) {
					if(arrExceptList != null && arrExceptList.length > 0 && isContain(arrExceptList, pd.getName())) continue;
					result.put(pd.getName(), reader.invoke(vo));
				}
			}

		} catch (IllegalAccessException e) {
			log.error("convertVoToMap IllegalAccessException error");
		} catch (IllegalArgumentException e) {
			log.error("convertVoToMap IllegalArgumentException error");
		} catch (InvocationTargetException e) {
			log.error("convertVoToMap InvocationTargetException error");
		} catch (IntrospectionException e) {
			log.error("convertVoToMap IntrospectionException error");
		}
		return result;
	}

	public static Boolean isContain(String[] arrList, String name) {
		for (String arr : arrList) {
			if (StringUtils.contains(arr, name))
				return true;
		}
		return false;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////~~~~~~vo 관련 끝~~~~~//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}

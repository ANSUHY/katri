package com.katri.common.util;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

/***************************************************
 * <ul>
 * <li>설	   명 : 문자열 공통 유틸</li>
 * <li>작   성  자 : Lee Jung Pyo(jp)</li>
 * <li>작   성  일 : 2019. 8. 21.</li>
 * </ul>
 * <pre>
 * ======================================
 * 변경자/변경일 :
 * 변경사유/내역 :
 * ======================================
 * </pre>
 ***************************************************/
@Slf4j
public class StringUtil {

	/*****************************************************
	 * DB에 들어간 Html 문자열을 Html Tag 형태로 변환
	 * @param str 변환 할 대상 문자열
	 * @return String 변환 완료된 문자열
	 *****************************************************/
	public static String convertDBToHtml( String str ) {
		// 변환 문자열
		String strTemp = "";

		if( str == null ) {
			str = "";
		}

		strTemp = str.replaceAll( "&lt;", "<" );
		strTemp = strTemp.replaceAll( "&gt;", ">" );
		strTemp = strTemp.replaceAll( "&nbsp;", " " );
		strTemp = strTemp.replaceAll( "<p> </p>", "<p>&nbsp;</p>" );
		strTemp = strTemp.replaceAll( "<div> </div>", "<div>&nbsp;</div>" );
		strTemp = strTemp.replaceAll( "&amp;", "&" );
		strTemp = strTemp.replaceAll( "&quot;", "\"" );
		strTemp = strTemp.replaceAll( "&#34;", "\"" );
		strTemp = strTemp.replaceAll( "&#39;", "\'" );
		strTemp = strTemp.replaceAll( "&#37;", "%" );

		// 스크립트 정규식
		Pattern scripts = Pattern.compile("<script([^'\"]|\"[^\"]*\"|'[^']*')*?</script>",Pattern.DOTALL);
		// 스타일 정규식
		Pattern style = Pattern.compile("<style[^>]*>.*</style>",Pattern.DOTALL);

		// Matcher 객체
		Matcher m;

		m = scripts.matcher(strTemp);
		strTemp = m.replaceAll("");

		m = style.matcher(strTemp);
		strTemp = m.replaceAll("");

		return strTemp;
	}

	/*****************************************************
	 * 태그 및 엔터 제거 전 객체 여부 체크
	 * @param obj 변환 할 객체
	 * @return String 변환 완료된 문자열
	 *****************************************************/
	public static String convertHtmlTags(Object obj) {
		if ( obj == null ) {
			return "";
		}

		return convertHtmlTags(obj.toString());
	}

	/*****************************************************
	 * 태그 및 엔터 제거 전 객체 여부 체크
	 * @param str 변환 할 문자열
	 * @return String 변환 완료된 문자열
	 *****************************************************/
	public static String convertHtmlTags(String str) {
		str = str.replaceAll("<[^>]*>", "");	// 정규식 태그삭제
		str = str.replaceAll("\r\n", " ");	  // 엔터제거

		return str;
	}

	/*****************************************************
	 * 전송 데이타 Cross Site Script 방지
	 * @param strData 체크 할 문자
	 * @return String 반환 된 문자
	 *****************************************************/
	public static String removeXSS(String strData) {
		// 체크 할 문자배열
		String[] arrSrcCode = {"<", ">", "\"", "\'", "%00", "%"};
		// 체크 할 Tag 문자배열
		String[] arrTgtCode = {"&lt;", "&gt;", "&#34;", "&#39;", "null;", "&#37;"};

		if ( strData == null || "".equals(strData) ) {
			return "";
		}

		for (int nLoop=0; nLoop < arrSrcCode.length; nLoop++) {
			strData = strData.replaceAll(arrSrcCode[nLoop], arrTgtCode[nLoop]);
		}

		return strData;
	}

	/*****************************************************
	 * 문자열 디코딩
	 * @param strIn 변환 할 문자열
	 * @return String 변환 완료된 문자열
	 *****************************************************/
	public static String setDSDecode(String strIn) {
		// 변환 문자열
		StringBuilder retStr = new StringBuilder();
		for (int i = 0; i < (strIn.length()); i++) {
			retStr.append((char) ((strIn.charAt(i)) - (i % 2) - 1));
		}

		if (retStr.toString().length() < 2 || !retStr.toString().substring(retStr.toString().length() - 6).equals("PASSWD")) {
			return "";
		} else {
			return retStr.toString().substring(0, retStr.toString().length() - 6);

		}
	}

	/*****************************************************
	 * 문자열 인코딩
	 * @param strIn 변환 할 문자열
	 * @return String 변환 완료된 문자열
	 *****************************************************/
	public static String setDSEncode(String strIn) {
		strIn = strIn + "PASSWD";

		// 변환 문자열
		StringBuilder retStr = new StringBuilder();

		for (int i = 0; i < strIn.length(); i++) {
			retStr.append((char) (strIn.charAt(i) + (i % 2) + 1));
		}

		return retStr.toString();
	}

	/*****************************************************
	 * 쿼리단 Cross Site Script 방지
	 * @param strData 체크 할 문자
	 * @return String 반환 된 문자
	 *****************************************************/
	public static String removeQueryXSS(String strData) {
		// 체크 할 문자배열
		String[] arrSrcCode = {"<", ">", "\"", "\'", "%00", "%", ";", "-"};
		// 체크 할 Tag 문자배열
		String[] arrTgtCode = {"&lt;", "&gt;", "&#34;", "&#39;", "null;", "&#37;", "&#59;", "&#45;"};

		if ( strData == null || "".equals(strData) ) {
			return strData;
		}

		for (int nLoop=0; nLoop < arrSrcCode.length; nLoop++) {
			strData = strData.replaceAll(arrSrcCode[nLoop], arrTgtCode[nLoop]);
		}

		return strData;
	}

	/*****************************************************
	 * 입력된 데이타 토크나이징 처리 후 토크나이징된 토큰들을 문자배열로 반환
	 * @param pmString 토크나이징되는 문자
	 * @param pmDelimeter 문자열를 분리하는 문자
	 * @return String 반환 된 문자배열
	 *****************************************************/
	public static String[] getTokens(String pmString, String pmDelimeter) {
		//토크나이징되는 문자
		String pmsDelimeter = pmDelimeter;
		// 문자열를 분리하는 문자
		String[] lmsReturns = null;

		if (pmString != null) {
			if( "\\".equals(pmsDelimeter) ) {
				pmsDelimeter = "/";
			}

			StringTokenizer lmoTokenizer = new StringTokenizer(pmString, pmsDelimeter);
			lmsReturns = new String[lmoTokenizer.countTokens()];

			for (int i = 0; lmoTokenizer.hasMoreTokens(); i++) {
				lmsReturns[i] = lmoTokenizer.nextToken();
			}
		}
		return lmsReturns;
	}

	/*****************************************************
	 * 이메일 유효성 체크
	 * @param n 체크 할 이메일주소
	 * @return boolean true/false
	 *****************************************************/
	public static boolean isValidEmail(String email) {
		// 유효성 체크 여부
		boolean err = false;
		// 유효성 패턴
		Pattern p = Pattern.compile( "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+" );
		// 유효성 패턴 체크 여부
		Matcher m = p.matcher(email);

		if( !m.matches() ) {
			err = true;
		}

		return err;
	}

	/*****************************************************
	 * 문자 null 체크 후 null인 경우 공백 문자로 변환
	 * @param oData null 체크 할 object
	 * @return String 반환 된 문자
	 *****************************************************/
	public static String nvl(Object oData) {
		return nvl(oData, "");
	}

	/*****************************************************
	 * 문자 null 체크 후 null인 경우 대체 숫자로 변환
	 * @param oData null 체크 할 object
	 * @param sTrans 대체 숫자
	 * @return String 반환 된 문자
	 *****************************************************/
	public static String nvl(Object oData, int sTrans) {
		return nvl(oData, Integer.toString(sTrans));
	}

	/*****************************************************
	 * 문자 null 체크 후 null인 경우 대체 문자로 변환
	 * @param oData null 체크 할 object
	 * @param sTrans 대체 문자
	 * @return String 반환 된 문자
	 *****************************************************/
	public static String nvl(Object oData, String sTrans) {
		if (sTrans == null) {
			sTrans = "";
		}

		if (oData != null && !"".equals(oData) && !"null".equals(oData)) {
			return removeXSS(oData.toString().trim());
		}

		return removeXSS(sTrans);
	}

	public static String LPadding(String str, char paddChar, int cnt) {
		return String.format("%"+ String.valueOf(cnt) +"s", str).replace(' ', paddChar);
	}

	/*****************************************************
	 * 일반 숫자형으로 변환
	 * @param num 숫자
	 * @return
	 * @throws Exception
	 *****************************************************/
	public static String changeGridNumForm(String num)  {
		String rtnNm = "";

		if(null != num && !"".equals(num)){
			if(-1 != num.indexOf(".")){
				String[] tempVal = num.split("\\.");
				rtnNm = tempVal[0].replaceAll(",", "") + "." + tempVal[1];
			}else{
				rtnNm = num.replaceAll(",", "");
			}
		}

		return rtnNm;
	}

	/*****************************************************
	 * 숫자의 왼쪽 자리를 '0'으로 추가
	 * @param sbRtn 변환 시킬 숫자
	 * @param sbRtn 추가 시킬 수
	 * @return String 반환 된 문자
	 *****************************************************/
	public static String padLeftwithZero(int convert, int size) {
		// 합칠 문자 데이타 변수
		StringBuilder sbRtn = new StringBuilder();
		// 숫자 Integer형으로 변환 변수
		Integer inTemp  = Integer.valueOf(convert);
		// Integer 문자열 변환 변수
		String  stTemp;

		stTemp = inTemp.toString();

		if (stTemp.length() < size) {
			for (int i = 0; i < size - stTemp.length(); i++) {
				sbRtn.append("0");
			}
		}

		sbRtn.append(stTemp);

		return sbRtn.toString();
	}

	/*****************************************************
	 * 왼쪽에  0이 붙은 문자를 제거
	 * @param sbRtn 변환 시킬 문자
	 * @return String 반환 된 문자
	 *****************************************************/
	public static String removeLeftZero(String sbRtn) {
		return sbRtn.replaceFirst("^0+(?!$)", "");
	}

	/*****************************************************
	 * Method Summary. <br>
	 * @param sData String : 데이터 값
	 * @param sTrans String : null, "", "null"일 경우 변경할값
	 * @return String
	 *****************************************************/
	public static int nvlInt(Object objData, int nTrans) {
		return Integer.parseInt(nvl(objData, nTrans));
	}

	/*****************************************************
	 * 날짜 string format 제거 <br>
	 * @param strDate : 날짜 문자열
	 * @return String
	 *****************************************************/
	public static String removeDateFormat(String strDate) {
		if(strDate == null)
			return "";

		strDate = strDate.replaceAll("\\.", "");
		strDate = strDate.replace("-", "");
		strDate = strDate.replace(" ", "");
		strDate = strDate.replace(":", "");

		return strDate; // getDateString((Date)objDay, "yyyy년 MM월 dd일 HH:mm");
	}

	/*****************************************************
	 * type 별 문자열 format<br>
	 * @param value  : 바꿀 문자열
	 * @param type 	 : 타입( date, money, decimal, phone, decimal_comma, masking )
	 * @param format : 변환할 문자열(delimeter 없는 문자열 / date에서 현재일시는 today 입력)
	 * 					date 	: 날짜 형식, default:YYYY-MM-DD
	 * 					money	: default:###,###
	 * 					decimal	: x
	 * 					phone	: x
	 * 					masking	: name, phone, id, email, birthday, address
	 * 					decimal_comma
	 * @return String
	*****************************************************/
	public static String stringFormatType( String value, String type, String format ) {
		return stringFormatType( value, type, format, null );
	}

	/*****************************************************
	 * type 별 문자열 format<br>
	 * @param value  : 바꿀 문자열
	 * @param type 	 : 타입( date, money, decimal, phone, decimal_comma, masking )
	 * @param format : 변환할 문자열(delimeter 없는 문자열 / date에서 현재일시는 today 입력)
	 * 					date 	: 날짜 형식, default:YYYY-MM-DD
	 * 					money	: default:###,###
	 * 					decimal	: x
	 * 					phone	: x
	 * 					masking	: name, phone, id, email, birthday, address
	 * 					decimal_comma
	 * @param decimalLen : 소수점 표현 자릿수
	 * @return String
	*****************************************************/
	public static String stringFormatType( String value, String type, String format, String decimalLen ) {

		String strReturn = "";

		try {

			if(!"".equals(value.trim())){

				/* 0. type : date */
				if("date".equals(type)){

					String strDate = value.trim();
					String strFormat = "YYYY.MM.DD";
					String strDelim = "-";

					strDate = removeDateFormat(strDate);

					if(!"".equals(format))
						strFormat = format;

					if("today".equals(strDate)) {
						if("YYYY-MM-DD".equals(strFormat) || "YYYY.MM.DD".equals(strFormat))
							strDate = DateUtil.getCurrentDate("", "YYYYMMDD");
						else if("YYYY-MM-DD HH24:MI:SS".equals(strFormat) || "YYYY.MM.DD HH24:MI:SS".equals(strFormat))
							strDate = DateUtil.getCurrentDate("", "YYYYMMDDHHMISS");
						else if("HH24:MI:SS".equals(strFormat))
							strDate = DateUtil.getCurrentDate("", "HHMISS");
						else
							strDate = DateUtil.getCurrentDate("", "YYYYMM");
					}

					if("YYYY-MM-DD".equals(strFormat)){

						if(strDate.length() < 8)
							strReturn = strDate;
						else
							strReturn = strDate.substring(0, 4) + strDelim + strDate.substring(4, 6) + strDelim + strDate.substring(6, 8);

					} else if("YYYY.MM.DD".equals(strFormat)){

						strDelim = ".";

						if(strDate.length() < 8)
							strReturn = strDate;
						else
							strReturn = strDate.substring(0, 4) + strDelim + strDate.substring(4, 6) + strDelim + strDate.substring(6, 8);

					} else if("YYYY.MM".equals(strFormat)){

						strDelim = ".";

						if(strDate.length() < 6)
							strReturn = strDate;
						else
							strReturn = strDate.substring(0, 4) + strDelim + strDate.substring(4, 6);

					} else if("YYYY-MM-DD HH24:MI:SS".equals(strFormat)){

						if(strDate.length() < 14)
							strReturn = strDate;
						else
							strReturn = strDate.substring(0, 4) + strDelim + strDate.substring(4, 6) + strDelim + strDate.substring(6, 8)
												+ " "
												+ strDate.substring(8, 10) + ":" + strDate.substring(10, 12) + ":" + strDate.substring(12, 14);

					} else if("YYYY.MM.DD HH24:MI:SS".equals(strFormat)){

						strDelim = ".";

						if(strDate.length() < 14)
							strReturn = strDate;
						else
							strReturn = strDate.substring(0, 4) + strDelim + strDate.substring(4, 6) + strDelim + strDate.substring(6, 8)
												+ " "
												+ strDate.substring(8, 10) + ":" + strDate.substring(10, 12) + ":" + strDate.substring(12, 14);

					} else if("HH24:MI:SS".equals(strFormat)){

						if(strDate.length() < 6)
							strReturn = strDate;
						else
							strReturn = strDate.substring(0, 2) + ":" + strDate.substring(2, 4) + ":" + strDate.substring(4, 6);

					} else {
						strReturn = strDate;
					}

				/* 1. type : money */
				} else if("money".equals(type)){

					String strMoney = value.trim();
					String strFormat = "###,###";

					if(strMoney.length() > 0) {

						try {

							if(!"".equals(format))
								strFormat = format;

							double dVaule = Double.parseDouble(strMoney);

							DecimalFormat df = new DecimalFormat(strFormat);
							strReturn = df.format(dVaule);

						} catch (NumberFormatException nfe) {
							//log.error(nfe.toString());
							log.error("money NumberFormatException error");
							strReturn = strMoney;
						}

					}

				/* 2. type : decimal */
				} else if("decimal".equals(type)){

					String strDecimal = value.trim();
					String strFormat = "0.00";

					if(strDecimal.length() > 0) {

						try {

							if(!"".equals(format))
								strFormat = format;

							double dVaule = Double.parseDouble(strDecimal);

							DecimalFormat df = new DecimalFormat(strFormat);
							strReturn = df.format(dVaule);

						} catch (NumberFormatException nfe) {
							log.error("decimal NumberFormatException error");
							strReturn = strDecimal;
						}

					}

				/* 3. type : phone */
				} else if("phone".equals(type)){

					String strPhone = value.trim();
					String strDelim = "-";

					if(strPhone.length() == 8){
						strReturn = strPhone.substring(0, 4) + strDelim + strPhone.substring(4, 8);
					} else if(strPhone.length() == 9){
							strReturn = strPhone.substring(0, 2) + strDelim + strPhone.substring(2, 5) + strDelim + strPhone.substring(5, 9);
					} else if(strPhone.length() == 10) {
						if(strPhone.startsWith("02"))
							strReturn = strPhone.substring(0, 2) + strDelim + strPhone.substring(2, 6) + strDelim + strPhone.substring(6, 10);
						else
							strReturn = strPhone.substring(0, 3) + strDelim + strPhone.substring(3, 6) + strDelim + strPhone.substring(6, 10);
					} else if(strPhone.length() == 11) {
						strReturn = strPhone.substring(0, 3) + strDelim + strPhone.substring(3, 7) + strDelim + strPhone.substring(7, 11);
					} else if(strPhone.length() == 13) {
						strReturn = strPhone.substring(0, 5) + strDelim + strPhone.substring(5, 9) + strDelim + strPhone.substring(9, 13);
					} else {
						strReturn = strPhone;
					}

				/* 4. type : decimal_comma */
				} else if("decimal_comma".equals(type)){

					String strDecimal = value.trim();
					String strFormat = "###,###";
					strDecimal = strDecimal.replaceAll("[^0-9-.]", "");

					if(strDecimal.length() > 0) {

						try {

							if(!"".equals(format))
								strFormat = format;

							double dVaule = Double.parseDouble(strDecimal);

							if (strDecimal.indexOf(".") != -1) {
								strReturn = String.format("%," + (decimalLen.length() > 0 ? "." +  decimalLen + "f" : "f"), dVaule);
							} else {
								DecimalFormat df = new DecimalFormat(strFormat);
								strReturn = df.format(dVaule);
							}

						} catch (NumberFormatException nfe) {
							log.error("decimal_comma NumberFormatException error");
							strReturn = strDecimal;
						} catch (Exception e) {
							log.error("decimal_comma Exception error");
						}

					}

				/* 5. type : masking */
				} else if ("masking".equals(type)) {

					String 	dot 	= "";
					String 	strMask = value.trim();

					try {

						// 이름 - 전체 마스킹
						if( "name".equals(format) ) {

							String 	regex 	= "^[0-9a-zA-Zㄱ-ㅎ가-힣]*$";
							Matcher matcher = Pattern.compile(regex).matcher(strMask);

							if( matcher.find() ) {

								int 	length 	= strMask.length();

								for(int i = 0; i < length; i++) {
									dot += "*";
								}

								// 글자수 만큼 마스킹 처리
								strReturn = strMask.replace(strMask, dot);
							}

						// 휴대전화
						} else if ( "phone".equals(format) ) {

							String  regex 	= "(\\d{2,3})-?(\\d{3,4})-?(\\d{4})$";
							Matcher matcher = Pattern.compile(regex).matcher(strMask);

							if( matcher.find() ) {

								String	target 	= matcher.group(3);
								int		length 	= target.length();
								char[] 	c 		= new char[length];

								Arrays.fill(c, '*');

								if( target.equals(matcher.group(2)) ) {
									// 앞/뒤 번호가 같으면?
									String strFirstNum 	= matcher.group(1);
									String strMidNum 	= matcher.group(2);

									if( strMask.contains("-") ) {
										strReturn = strFirstNum + "-" + strMidNum + "-" + String.valueOf(c);
									} else {
										strReturn = strFirstNum + strMidNum + String.valueOf(c);
									}

								} else {

									strReturn = strMask.replace(target, String.valueOf(c));
								}
							}

						// 아이디
						} else if ( "id".equals(format) ) {

							String	regex 	= "(^[A-Za-z0-9+_.-]+)$";
							Matcher matcher = Pattern.compile(regex).matcher(strMask);

							if( matcher.find() ) {

								String target = matcher.group(1);
								int length = target.length();
								if(length > 0) {
									char[] c = new char[length - 2]; // 앞 2자리 제외
									Arrays.fill(c, '*');

									strReturn = strMask.replace( target.substring(2, length), String.valueOf(c));
								}

							}

						// 이메일
						} else if ( "email".equals(format) ) {

							String	regex 	= "(^[A-Za-z0-9+_.-]+)@(.+)$";
							Matcher matcher = Pattern.compile(regex).matcher(strMask);

							if( matcher.find() ) {

								String target = matcher.group(1);
								int length = target.length();
								if(length > 0) {
									char[] c = new char[length - 2]; // 앞 2자리 제외
									Arrays.fill(c, '*');

									strReturn = strMask.replace( target.substring(2, length), String.valueOf(c));
								}

							}

						// 생년월일
						} else if ( "birthday".equals(format) ) {

							String  regex 	= "^((19|20)\\d\\d)?([-/.])?(0[1-9]|1[012])([-/.])?(0[1-9]|[12][0-9]|3[01])$";
							Matcher matcher = Pattern.compile(regex).matcher(strMask);

							if( matcher.find() ) {

								strReturn = strMask.replaceAll("[0-9]", "*");
							}

						// 주소
						} else if ( "address".equals(format) ) {

							// 신(구)주소, 도로명 주소
							String regex 	= "(([가-힣]+(\\d{1,5}|\\d{1,5}(,|.)\\d{1,5}|)+(읍|면|동|가|리))(^구|)((\\d{1,5}(~|-)\\d{1,5}|\\d{1,5})(가|리|)|))([ ](산(\\d{1,5}(~|-)\\d{1,5}|\\d{1,5}))|)|";
							String newRegx 	= "(([가-힣]|(\\d{1,5}(~|-)\\d{1,5})|\\d{1,5})+(로|길))";

							Matcher matcher 	= Pattern.compile(regex).matcher(strMask);
							Matcher newMatcher 	= Pattern.compile(newRegx).matcher(strMask);

							if(matcher.find()) {
								strReturn = strMask.replaceAll("[0-9]", "*");
							} else if(newMatcher.find()) {
								strReturn = strMask.replaceAll("[0-9]", "*");
							}

						} else {

							strReturn = strMask;
						}

					} catch (Exception e) {
						log.error("masking Exception error");
					}

				/* 6. type : 타입 없음 */
				} else {
					strReturn = "정의되지 않은 타입입니다. type 값을 확인하세요.";
				}

			}

		} catch (Exception e) {

			log.error("Exception error");
		}

		return strReturn;

	}

}

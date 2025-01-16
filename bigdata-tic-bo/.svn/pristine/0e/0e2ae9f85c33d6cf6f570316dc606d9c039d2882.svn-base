package com.katri.common.tlds;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.katri.common.util.StringUtil;

import lombok.extern.slf4j.Slf4j;


/**
 * 문자열 포맷팅
 */
@Slf4j
public class StringFormat extends TagSupport {
	private static final long serialVersionUID = -1L;

	/** 타입( date, money, decimal, phone, decimal_comma, masking ) */
	String type = "";

	/** 변환할 문자열(delimeter 없는 문자열 / date에서 현재일시는 today 입력) */
	String value = "";

	/** 형식(date default:YYYY-MM-DD, money default:###,###, masking : name, phone, id, email, birthday, address ) */
	String format = "";

	/** 소수점 표현 자릿수 */
	String decimalLen = "";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getDecimalLen() {
		return decimalLen;
	}

	public void setDecimalLen(String decimalLen) {
		this.decimalLen = decimalLen;
	}

	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {

		String strReturn = "";

		try {

			strReturn = StringUtil.stringFormatType( value, type, format, decimalLen );

			pageContext.getOut().write(strReturn);

		} catch (IOException ioe) {
			log.error("doEndTag IOException error");
		}
		return EVAL_PAGE;
	}

}

package com.katri.common.tlds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.katri.common.tlds.model.CommonTldReq;
import com.katri.common.tlds.model.CommonTldRes;
import com.katri.common.tlds.service.CommonTldService;
import com.katri.common.util.CommonUtil;
import com.katri.common.util.StringUtil;

/**
 * 태그생성
 */
public class MakeTag extends TagSupport {

	private static final long serialVersionUID = -7813686187678045078L;

	private static final Logger LOGGER = LoggerFactory.getLogger(MakeTag.class);

	@Autowired
	private CommonTldService commonTldService;

	/** 태그타입(select, checkbox, radio) */
	String tagType;
	/** 태그명 */
	String name;
	/** 태그ID */
	String tagId;
	/** 태그class */
	String cls;
	/** 태그style */
	String style;
	/** 태그event */
	String event;
	/** 선택값 */
	String selVal;
	/** 태그목록이 있는 경우 Map의 code id(default : CODE_ID) */
	String codeId;
	/** 태그목록이 있는 경우 Map의 code name(default : CODE_NM) */
	String codeNm;
	/** 태그를 감싸는 label의 class(checkbox와 radio에서만 사용) */
	String labelCls;
	/** 태그disabled(disabled 또는 true/false) */
	String disabled;
	/** 태그readonly(readonly 또는 true/false) */
	String readonly;
	/** 태그기본값사용여부(Y 또는 N, default : N) */
	String defaultUseYn;
	/** 태그기본값명(default : 선택하세요(select) / 전체(checkbox, radio)) */
	String defaultNm;
	/** 태그기본값 */
	String defaultVal;
	/** 태그기본체크값 */
	String defaultChk;
	/** 공통코드분류구분(1:대분류, 2:중분류, 3:소분류(default)) */
	String codeGbn;
	/** 공통코드 */
	String code;
	/** String 태그 목록 */
	String strList;
	/** 태그목록 */
//	List<Map<String, Object>> list;
	List<CommonTldRes> list;
	/** 선택값문자열(콤마로 구분한 문자열, checkbox에서만 사용) */
	String selValArrStr;
	/** 선택값목록(checkbox에서만 사용) */
	List<String> listSelVal;
	/** 선택값배열(checkbox에서만 사용) */
	String[] arraySelVal;
	/** 제외값문자열(코드 구분은 ','로 하며, checkbox에서만 사용) */
	String strExceptVal;

	public String getTagType() {
		return tagType;
	}
	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public String getCls() {
		return cls;
	}
	public void setCls(String cls) {
		this.cls = cls;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getSelVal() {
		return selVal;
	}
	public void setSelVal(String selVal) {
		this.selVal = selVal;
	}
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public String getCodeNm() {
		return codeNm;
	}
	public void setCodeNm(String codeNm) {
		this.codeNm = codeNm;
	}
	public String getLabelCls() {
		return labelCls;
	}
	public void setLabelCls(String labelCls) {
		this.labelCls = labelCls;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getReadonly() {
		return readonly;
	}
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}
	public String getDefaultUseYn() {
		return defaultUseYn;
	}
	public void setDefaultUseYn(String defaultUseYn) {
		this.defaultUseYn = defaultUseYn;
	}
	public String getDefaultNm() {
		return defaultNm;
	}
	public void setDefaultNm(String defaultNm) {
		this.defaultNm = defaultNm;
	}
	public String getDefaultVal() {
		return defaultVal;
	}
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}
	public String getDefaultChk() {
		return defaultChk;
	}
	public void setDefaultChk(String defaultChk) {
		this.defaultChk = defaultChk;
	}
	public String getCodeGbn() {
		return codeGbn;
	}
	public void setCodeGbn(String codeGbn) {
		this.codeGbn = codeGbn;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStrList() {
		return strList;
	}
	public void setStrList(String strList) {
		this.strList = strList;
	}
	public List<CommonTldRes> getList() {
		List<CommonTldRes> alistRet = null;
		if(list != null) {
			alistRet = new ArrayList<CommonTldRes>();
			alistRet.addAll(list);
		}
		return alistRet;
	}
	public void setList(List<CommonTldRes> list) {
		if(list != null && list.size() > 0) {
			this.list = new ArrayList<CommonTldRes>();
			this.list.addAll(list);
		} else {
			this.list = null;
		}
	}
	public String getSelValArrStr() {
		return selValArrStr;
	}
	public void setSelValArrStr(String selValArrStr) {
		this.selValArrStr = selValArrStr;
	}
	public List<String> getListSelVal() {
		List<String> alistRet = null;
		if(listSelVal != null) {
			alistRet = new ArrayList<String>();
			alistRet.addAll(listSelVal);
		}
		return alistRet;
	}
	public void setListSelVal(List<String> listSelVal) {
		this.listSelVal = null;
		if(listSelVal != null && listSelVal.size() > 0) {
			this.listSelVal = new ArrayList<String>();
			this.listSelVal.addAll(listSelVal);
		} else {
			this.listSelVal = null;
		}
	}
	public String[] getArraySelVal() {
		String[] ret = null;
		if (this.arraySelVal != null) {
			ret = new String[this.arraySelVal.length];
			for (int i=0; i<this.arraySelVal.length; i++) {
				ret[i] = this.arraySelVal[i];
			}
		}
		return ret;
	}
	public void setArraySelVal(String[] arraySelVal) {
		this.arraySelVal = null;
		if(arraySelVal != null) {
			this.arraySelVal = new String[arraySelVal.length];
			for(int i=0; i<arraySelVal.length; i++) {
				this.arraySelVal[i] = arraySelVal[i];
			}
		} else {
			this.arraySelVal = null;
		}
	}
	public String getStrExceptVal() {
		return strExceptVal;
	}
	public void setStrExceptVal(String strExceptVal) {
		this.strExceptVal = strExceptVal;
	}
	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			StringBuffer sb = new StringBuffer();
			String strSelVal = "";

			List<CommonTldRes> listTag = null;
			CommonTldRes commonTldRes = null;

			if(!CommonUtil.isEmpty(strList)) {

				listTag = new ArrayList<CommonTldRes>();

				for(int i=0; i<strList.split(",").length; i++) {
					commonTldRes = new CommonTldRes();

					commonTldRes.setCodeId(strList.split(",")[i].split(":")[0]);
					commonTldRes.setCodeNm(strList.split(",")[i].split(":")[1]);

					listTag.add(commonTldRes);
				}
			} else {
				if(CommonUtil.isEmpty(list)){
					try {
						ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
						commonTldService = ctx.getBean(CommonTldService.class);

						CommonTldReq commonTldReq = new CommonTldReq();
						commonTldReq.setCode(code);

						listTag = commonTldService.selectCodeList(commonTldReq);
					} catch(Exception e) {
					}
				} else {
					listTag = list;
				}
			}

			if("select".equals(StringUtil.nvl(tagType))){

				sb.append("<select");

				if(!"".equals(StringUtil.nvl(name)))
					sb.append(" name=\""+name+"\"");
				if(!"".equals(StringUtil.nvl(tagId)))
					sb.append(" id=\""+tagId+"\"");
				if(!"".equals(StringUtil.nvl(cls)))
					sb.append(" class=\""+cls+"\"");
				if(!"".equals(StringUtil.nvl(style)))
					sb.append(" style=\""+style+"\"");
				if(!"".equals(StringUtil.nvl(event)))
					sb.append(" "+event.replaceAll("\"", "\\\""));
				if(!"".equals(StringUtil.nvl(disabled)))
					sb.append(" disabled=\""+disabled+"\"");
				if(!"".equals(StringUtil.nvl(readonly)))
					sb.append(" readonly=\""+readonly+"\"");

				sb.append(">");

				if("Y".equals(StringUtil.nvl(defaultUseYn, "N"))){
					sb.append("<option value=\""+StringUtil.nvl(defaultVal)+"\">"+StringUtil.nvl(defaultNm, "선택하세요.")+"</option>");
				}

				if(!CommonUtil.isEmpty(listTag)){
					for(CommonTldRes commonTld : listTag){
						// ---------------------------수정 부분 20180720-----------------------------------------
						if(!"".equals(StringUtil.nvl(strExceptVal))) {
							String[] arrayExceptVal = strExceptVal.split(",");
							boolean bExceptVal = false;
							for(String strExceptVal : arrayExceptVal) {
								if(strExceptVal.equals(commonTld.getCodeId())) {
									bExceptVal = true;
									continue;
								}
							}

							if(bExceptVal)
								continue;
						}
						//---------------------------//수정 부분 20180720-----------------------------------------
						if(!"".equals(StringUtil.nvl(selVal))  )
							strSelVal = selVal.equals(StringUtil.nvl(commonTld.getCodeId())) ? " selected=\"selected\"" : "";
						sb.append("<option value=\""+commonTld.getCodeId()+"\""+strSelVal+" code_val=\""+commonTld.getCodeVal()+"\""+">");
						sb.append(commonTld.getCodeNm());
						sb.append("</option>");
					}
				}

				sb.append("</select>");

			} else if("checkbox".equals(StringUtil.nvl(tagType)) || "radio".equals(StringUtil.nvl(tagType))){

				int iTagIdx = 0;

				if("Y".equals(StringUtil.nvl(defaultUseYn, "N"))){
					if(!"".equals(StringUtil.nvl(selVal))) {
						if(selVal.indexOf(",") > -1)
							strSelVal = selVal.equals(StringUtil.nvl(StringUtil.nvl(defaultVal))) ? " checked=\"checked\"" : "";
					}

					if("checkbox".equals(StringUtil.nvl(tagType)) && !"".equals(StringUtil.nvl(selValArrStr))){
						String[] arrSelValArrStr = selValArrStr.split(",");
						for(String sSelVal : arrSelValArrStr){
							if(sSelVal.equals(StringUtil.nvl(defaultVal))){
								strSelVal = " checked=\"checked\"";
								break;
							}
						}
					}

					if("checkbox".equals(StringUtil.nvl(tagType)) && !CommonUtil.isEmpty(listSelVal)){
						for(String sSelVal : listSelVal){
							if(sSelVal.equals(StringUtil.nvl(defaultVal))){
								strSelVal = " checked=\"checked\"";
								break;
							}
						}
					}

					if("checkbox".equals(StringUtil.nvl(tagType)) && !CommonUtil.isEmpty(arraySelVal)){
						for(String sSelVal : arraySelVal){
							if(sSelVal.equals(StringUtil.nvl(defaultVal))){
								strSelVal = " checked=\"checked\"";
								break;
							}
						}
					}

					sb.append(getPreTag(iTagIdx));

					sb.append(" value=\""+StringUtil.nvl(defaultVal)+"\""+strSelVal);
					if ("Y".equals(StringUtil.nvl(defaultChk, "N"))) sb.append(" checked=\"checked\"");
					sb.append(" />");	// input tag close

					sb.append(getPostTag(iTagIdx, StringUtil.nvl(defaultNm, "전체")));

					iTagIdx++;
				}

				if(!CommonUtil.isEmpty(listTag)){
					for(CommonTldRes commonTld : listTag){
						strSelVal = "";

						if(!"".equals(StringUtil.nvl(strExceptVal))) {
							String[] arrayExceptVal = strExceptVal.split(",");
							boolean bExceptVal = false;
							for(String strExceptVal : arrayExceptVal) {
								if(strExceptVal.equals(commonTld.getCodeId())) {
									bExceptVal = true;
									continue;
								}
							}

							if(bExceptVal)
								continue;
						}

						if(!"".equals(StringUtil.nvl(selVal)))
							strSelVal = selVal.equals(commonTld.getCodeId()) ? " checked=\"checked\"" : "";

						if("checkbox".equals(StringUtil.nvl(tagType)) && !"".equals(StringUtil.nvl(selValArrStr))){
							String[] arrSelValArrStr = selValArrStr.split(",");
							for(String sSelVal : arrSelValArrStr){
								if(sSelVal.equals(commonTld.getCodeId())){
									strSelVal = " checked=\"checked\"";
									break;
								}
							}
						}

						if("checkbox".equals(StringUtil.nvl(tagType)) && !CommonUtil.isEmpty(listSelVal)){
							for(String sSelVal : listSelVal){
								if(sSelVal.equals(commonTld.getCodeId())){
									strSelVal = " checked=\"checked\"";
									break;
								}
							}
						}

						if("checkbox".equals(StringUtil.nvl(tagType)) && !CommonUtil.isEmpty(arraySelVal)){
							for(String sSelVal : arraySelVal){
								if(sSelVal.equals(commonTld.getCodeId())){
									strSelVal = " checked=\"checked\"";
									break;
								}
							}
						}

						sb.append(getPreTag(iTagIdx));

						sb.append(" value=\""+commonTld.getCodeId()+"\""+strSelVal+" code_val=\""+commonTld.getCodeVal()+"\"");
						sb.append(" />");	// input tag close

						sb.append(getPostTag(iTagIdx, commonTld.getCodeNm()));

						iTagIdx++;
					}
				}

			}

			pageContext.getOut().write(sb.toString());

		} catch (IOException ioe) {
			LOGGER.error(ioe.getMessage());
		}
		return EVAL_PAGE;
	}

	/**
	 * checkbox와 radio 태그 작성시 분기처리 전 태그 설정
	 * @param iTagIdx id에 추가하기 위한 태그 인덱스
	 * @return sbReturn
	 */
	private String getPreTag(int iTagIdx){
		StringBuffer sbReturn = new StringBuffer();

		sbReturn.append("<input");
		sbReturn.append(" type=\""+tagType+"\"");

		if(!"".equals(StringUtil.nvl(name)))
			sbReturn.append(" name=\""+name+"\"");
		if(!"".equals(StringUtil.nvl(tagId)))
			sbReturn.append(" id=\""+tagId+iTagIdx+"\"");
		if(!"".equals(StringUtil.nvl(cls)))
			sbReturn.append(" class=\""+cls+"\"");
		if(!"".equals(StringUtil.nvl(style)))
			sbReturn.append(" style=\""+style+"\"");
		if(!"".equals(StringUtil.nvl(event)))
			sbReturn.append(" "+event.replaceAll("\"", "\\\""));
		if(!"".equals(StringUtil.nvl(disabled)))
			sbReturn.append(" disabled=\""+disabled+"\"");
		if(!"".equals(StringUtil.nvl(readonly)))
			sbReturn.append(" readonly=\""+readonly+"\"");

		return sbReturn.toString();
	}

	/**
	 * checkbox와 radio 태그 작성시 분기처리 전 태그 설정
	 * @return sbReturn
	 */
	private String getPostTag(int iTagIdx, String strCodeNm){
		StringBuffer sbReturn = new StringBuffer();

		sbReturn.append("<label for=\""+tagId+iTagIdx+"\"");
		if(!"".equals(StringUtil.nvl(labelCls)))
			sbReturn.append(" class=\""+labelCls+"\"");
		sbReturn.append(">");
		sbReturn.append(strCodeNm);
		sbReturn.append("</label>");

		return sbReturn.toString();
	}

}

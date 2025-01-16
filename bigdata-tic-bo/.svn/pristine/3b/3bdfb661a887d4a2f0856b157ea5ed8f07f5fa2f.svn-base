package com.katri.web.login.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Login 한 사람의 권한에 따른 메뉴 Request")
public class LoginAuthrtMenuReq {

	/** 로그인된 사람의 권한그룹일련번호 */
	private Integer loginAuthrtGrpSn;

	/** 현재 사이트유형코드 */
	private String searchSiteTyCd;

	/** 검색할 url */
	private String searchMenuUrl;

}

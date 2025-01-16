package com.katri.web.login.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Login Response")
public class LoginRes {

//------ [[tb_mngr_bas ]] 관련 정보 시작

	/** 관리자아이디 */
	private String mngrId;

	/** 관리자명 */
	private String mngrNm;

	/** 관리자비밀번호 */
	private String mngrPwd;

	/** 권한그룹일련번호 */
	private Integer authrtGrpSn;

	/** 최종로그인IP주소 */
	private String lastLgnIpAddr;

	/** 로그인참조값 */
	private String lgnRefVal;

//------// [[tb_mngr_bas ]] 관련 정보 끝

	/** 권한이 있는 menu중 첫번째 menu URL */
	private String firstMenuUrlAddr;

}

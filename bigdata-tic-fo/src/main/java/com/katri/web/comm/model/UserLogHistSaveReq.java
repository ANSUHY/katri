package com.katri.web.comm.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "사용자로그이력 Request")
public class UserLogHistSaveReq {

	/** 로그일련번호 */
	private Integer logSn;

	/** 로그URL주소 */
	private String logUrlAddr;

	/** 로그URL파라미터내용 */
	private String logUrlPrmtrCn;

	/** 접속일자 */
	private String cntnYmd;

	/** 접속시각 */
	private String cntnTm;

	/** 장비접속아이디 */
	private String eqmtCntnId;

	/** 세션접속아이디 */
	private String ssnCntnId;

	/** 사용자IP주소 */
	private String userIpAddr;

	/** 장비유형코드 */
	private String eqmtTyCd;

	/** 운영체제명 */
	private String operSysmNm;

	/** 사용자에이전트값 */
	private String userAgntVal;

	/** 브라우저명 */
	private String brwsrNm;

	/** 이전URL주소 */
	private String bfrUrlAddr;

	/** 생성일시 */
	private String crtDt;

	/** 생성자아이디 */
	private String crtrId;

}
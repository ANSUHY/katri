package com.katri.web.auth.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "휴면 계정 Request")
public class DrmncySaveReq {


	/**************** 인증 메일 저장 정보 시작 ****************/
	/** 인증 번호 */
	private String certNo;

	/** 인증 받는 메일 정보 */
	private String rcvrEmlAddr;
	/**************** 인증 메일 저장 정보 종료 ****************/


	/**************** 사용자 저장 정보 시작 ****************/
	/** 사용자 아이디 */
	private String userId;

	/** 사용자 유형 코드 */
	private String userTyCd;

	/** 사용자 비밀번호 */
	private String userPwd;

	/** 사용자 비밀번호 확인 */
	private String userPwdChk;

	/** 사용자 이름 */
	private String userNm;

	/** 휴대전화번호 */
	private String mblTelnoVal;

	/** 암호화휴대전화번호값 */
	private String encptMblTelnoVal;

	/** 이메일주소 */
	private String emlAddrVal;

	/** 암호화이메일주소값 */
	private String encptEmlAddrVal;

	/** 사용자 상태 코드 */
	private String userSttCd;

	/** 권한그룹일련번호 */
	private Integer authrtGrpSn;

	/** 가입신청요청일시 */
	private String joinAplyDmndDt;

	/** 가입신청처리일시 */
	private String joinAplyPrcsDt;

	/** 가입신청처리자아이디 */
	private String joinAplyPrcrId;

	/** 가입일자 */
	private String joinYmd;

	/** 생성일시 */
	private String crtDt;

	/** 생성자아이디 */
	private String crtrId;
	/**************** 사용자 저장 정보 종료 ****************/

	/* PASS 고유값 */
	private String pCi;
}

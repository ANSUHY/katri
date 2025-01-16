package com.katri.web.auth.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "아이디 찾기 및 비밀번호 재설정 조회 Response")
public class UserFindSelectRes {

	/** 회원 아이디 */
	private String userId;

	/** 회원 비밀번호 */
	private String userPwd;

	/** 회원 이메일 */
	private String encptEmlAddrVal;

	/** 회원 유형 코드 */
	private String userTyCd;

	/** 분석환경사용자아이디 */
	private String anlsEnvUserId;

	/** [인증메일] 인증번호 */
	private String certNo;

	/** [인증메일] 실패횟수 */
	private Integer certFirCnt;

	/** [인증메일] 발송 제한 건수 */
	private Integer certEmlLimitCnt;

	/** [인증메일] 발송 제한 시간 */
	private String certEmlLimitTime;

	/** [인증메일] 인증번호 확인 제한 건수 */
	private Integer certChkLimitCnt;

}

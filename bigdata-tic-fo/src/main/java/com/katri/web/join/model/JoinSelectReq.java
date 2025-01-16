package com.katri.web.join.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "회원 가입 조회 Request")
public class JoinSelectReq extends Common {

	/** 대상 사용자 아이디 */
	private String targetUserId;

	/** 대상 사용자 유형 코드 */
	private String targetUserTypeCd;

	/** pass 사용자 연계 정보 */
	private String searchUserLinkInfoVal;

	/**************** 약관 정보 시작 ****************/
	/** 검색 약관 유형 코드 */
	private String searchTrmsTyCd;

	/** 체크한 약관일련번호 */
	private Integer[] arrChkTrmsSn;
	/**************** 약관 정보 시작 ****************/

	/**************** 인증 메일 정보 시작 ****************/
	/** 인증 받는 메일 정보 */
	private String rcvrEmlAddr;

	/** 체크하는 인증 번호*/
	private String chkCertNo;

	/** [인증메일] 발송 제한 건수 */
	private Integer certEmlLimitCnt;

	/** [인증메일] 발송 제한 시간 */
	private String certEmlLimitTime;

	/** [인증메일] 인증번호 확인 제한 건수 */
	private Integer certChkLimitCnt;
	/**************** 인증 메일 정보 시작 ****************/

	/**************** 그룹 정보 시작 ****************/
	/** 사업자 등록번호 */
	private String brno;

	/** 그룹명 */
	private String entGrpNm;

	/** 그룹ID */
	private String entGrpMngNo;
	/**************** 그룹 정보 종료 ****************/



}

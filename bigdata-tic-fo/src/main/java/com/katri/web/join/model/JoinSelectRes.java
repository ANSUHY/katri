package com.katri.web.join.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "회원 가입 조회 Response")
public class JoinSelectRes extends Common {

	/**************** 약관 정보 시작 ****************/
	/** 약관일련번호 */
	private String trmsSn;

	/** 약관유형코드 */
	private String trmsTyCd;

	/** 약관유형코드명 */
	private String trmsTyNm;

	/** 약관내용 */
	private String trmsCn;
	/**************** 약관 정보 종료 ****************/

	/**************** 기업 그룹 정보 시작 ****************/
	/** 기업그룹일련번호 */
	private Integer entGrpSn;

	/** 기업명 */
	private String grpNm;
	/**************** 기업 그룹 정보 종료 ****************/

	/**************** 기관 정보 시작 ****************/
	/** 기관아이디 */
	private String instId;

	/** 기관명 */
	private String instNm;

	/** 기관영문명 */
	private String instEngNm;
	/**************** 기관 정보 종료 ****************/

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

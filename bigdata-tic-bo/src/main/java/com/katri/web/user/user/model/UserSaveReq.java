package com.katri.web.user.user.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "회원 현황 관리 저장 Request")
public class UserSaveReq extends Common {

	/** 수정 대상 사용자아이디 */
	private String targetUserId;

	/** 수정 대상 사용자유형코드 */
	private String targetUserTyCd;

	/** 수정 대상 사용자상태코드 */
	private String targetUserSttCd;

	/** 수정자아이디 */
	private String mdfrId;

	/************ 비밀번호 초기화 항목 시작 ************/

	/** 기존 비밀번호 */
	private String userLgcyPwd;

	/** 새 비밀번호 */
	private String chgUserPwd;

	/** 비밀번호 확인 */
	private String chgUserPwdChk;

	/************ 비밀번호 초기화 항목 종료 ************/

	/************ 담당자 정보 시작 ************/

	/** 회원 비밀번호 */
	private String userPwd;

	/** 기업 그룹 일련 번호 */
	private Integer entGrpSn;

	/** 기업 그룹 관리 번호 */
	private String entGrpMngNo;

	/** 이름 */
	private String userNm;

	/** 휴대폰 번호 */
	private String mblTelnoVal;

	/** 암호화 휴대폰 번호 */
	private String encptMblTelnoVal;

	/** 직장 이메일 */
	private String emlAddrVal;

	/** 암호화 직장 이메일 */
	private String encptEmlAddrVal;

	/** 부서명 */
	private String userDeptNm;

	/** 직장 전화번호 */
	private String wrcTelno;

	/* 기업 마스터 */
		/** 직급 */
		private String userJbgdNm;

		/** 우편 번호 */
		private String wrcZip;

		/** 직장기본주소 */
		private String wrcBasAddr;

		/** 직장상세주소 */
		private String wrcDaddr;
	/* 기업 마스터 */

	/************ 담당자 정보 종료 ************/


	/************ 승인/반려 정보 시작 ************/

	/** 승인/반려 여부 */
	private String apprFlag;

	/** 가입신청처리자아이디 */
	private String joinAplyPrcrId;

	/** 탈퇴신청처리자아이디 */
	private String whdwlAplyPrcrId;

	/************ 승인/반려 정보 종료 ************/
}

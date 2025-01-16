package com.katri.web.join.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "회원 가입 저장 Request")
public class JoinSaveReq extends Common {

	/**************** 인증 메일 저장 정보 시작 ****************/
	/** 인증 번호 */
	private String certNo;

	/** 인증 받는 메일 정보 */
	private String rcvrEmlAddr;
	/**************** 인증 메일 저장 정보 종료 ****************/

	/**************** 약관 동의 이력 저장 정보 시작 ****************/
	/** 체크한 약관일련번호 배열 */
	private Integer[] arrChkTrmsSn;

	/** 약관일련번호 */
	private Integer trmsSn;
	/**************** 약관 동의 이력 저장 정보 종료 ****************/

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

	/** 사용자 연계정보 값 */
	private String userLinkInfoVal;

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

	/**************** 관심 키워드 정보 시작 ****************/
	/** 관심 키워드 1 - 선택한 대분류 값 */
	private String stdLgclfCd1;

	/** 관심 키워드 1 - 선택한 중분류 값 */
	private String[] arrStdMlclfCd1;

	/** 관심 키워드 2 - 선택한 대분류 값 */
	private String stdLgclfCd2;

	/** 관심 키워드 2- 선택한 중분류 값 */
	private String[] arrStdMlclfCd2;

	/** 관심 키워드 - 대분류 값 */
	private String stdLgclfCd;

	/** 관심 키워드 - 중분류 값 */
	private String stdMlclfCd;

	/** 관심키워드 - 정렬순서 */
	private Integer srtSeq;
	/**************** 관심 키워드 정보 종료 ****************/

	/**************** 기관 사용자 정보 시작 ****************/
	/** 기관아이디 */
	private String instId;

	/** 사용자부서명 */
	private String userDeptNm;

	/** 직장전화번호 */
	private String wrcTelno;
	/**************** 기관 사용자 정보 종료 ****************/

	/**************** 기업 사용자 정보 시작 ****************/
	/** 기업그룹일련번호 */
	private Integer entGrpSn;
//	/** 사용자부서명 */
//	private String userDeptNm;

	/** 사용자직급명 */
	private String userJbgdNm;

//	/** 직장전화번호 */
//	private String wrcTelno;

	/** 직장우편번호 */
	private String wrcZip;

	/** 직장기본주소 */
	private String wrcBasAddr;

	/** 직장상세주소 */
	private String wrcDaddr;
	/**************** 기업 사용자 정보 종료 ****************/

	/**************** 기업 그룹 정보 시작 ****************/
	/** 기업그룹 추가 선택 여부 */
	private String entGrpAddChkYn;

	/** 기업그룹관리번호 */
	private String entGrpMngNo;

	/** 기업그룹명 */
	private String entGrpNm;

	/** 기업그룹우편번호 */
	private String entGrpZip;

	/** 기업그룹기본주소 */
	private String entGrpBasAddr;

	/** 기업그룹상세주소 */
	private String entGrpDaddr;
	/**************** 기업 그룹 정보 종료 ****************/

	/**************** 기업 정보 시작 ****************/
	/** 사업자등록번호 */
	private String brno;

	/** 기업명 */
	private String entNm;

	/** 대표자명 */
	private String rprsvNm;

	/** 개업일자 */
	private String opbizYmd;
	/**************** 기업 정보 종료 ****************/

	/**************** 기타 ****************/
	private String emlCode;

	/** 분석환경사용자아이디 */
	private String anlsEnvUserId;

}

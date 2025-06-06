package com.katri.web.mypage.infoMng.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "회원 정보 관리 저장 Request")
public class InfoMngSaveReq extends Common {

	/** 수정 대상 사용자아이디 */
	private String targetUserId;

	/** 수정 대상 사용자유형코드 */
	private String targetUserTyCd;

	/** 수정자아이디 */
	private String mdfrId;

	/************ 비밀번호 초기화 항목 시작 ************/
	/** 새 비밀번호 */
	private String chgUserPwd;

	/** 비밀번호 확인 */
	private String chgUserPwdChk;
	/************ 비밀번호 초기화 항목 종료 ************/

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

	/** 사용자 연계정보값 */
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

	/** 탈퇴신청처리자아이디 */
	private String whdwlAplyPrcrId;
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

	/**************** 기관 사용자 저장 정보 시작 ****************/
	/** 사용자부서명 */
	private String userDeptNm;

	/** 직장전화번호 */
	private String wrcTelno;
	/**************** 기관 사용자 저장 정보 종료 ****************/

	/**************** 기업 사용자 저장 정보 시작 ****************/
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

	/** 기업그룹우편번호 */
	private String entGrpZip;

	/** 기업그룹기본주소 */
	private String entGrpBasAddr;

	/** 기업그룹상세주소 */
	private String entGrpDaddr;
	/**************** 기업 사용자 저장 정보 종료 ****************/

	/**************** 기타 ****************/
	/** 이메일 수신자 */
	private String reciverEmlAddr;

	/** 이메일 변경 여부 */
	private String emailCertYn;

}

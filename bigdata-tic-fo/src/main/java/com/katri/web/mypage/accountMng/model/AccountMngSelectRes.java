package com.katri.web.mypage.accountMng.model;

import java.util.List;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "계정 관리 조회 Response")
public class AccountMngSelectRes extends Common {

	/** 사용자아이디 */
	private String userId;

	/** 마스킹 처리한 사용자아이디 */
	private String maskUserId;

	/** 사용자이름 */
	private String userNm;

	/** 마스킹 처리한 사용자이름 */
	private String maskUserNm;

	/** 사용자유형코드 */
	private String userTyCd;

	/** 사용자유형코드명 */
	private String userTyNm;

	/** 암호화휴대전화번호값 */
	private String encptMblTelnoVal;

	/** 마스킹 처리한 휴대전화번호 */
	private String maskMblTelno;

	/** 암호화이메일주소값 */
	private String encptEmlAddrVal;

	/** 마스킹 처리한 이메일주소 */
	private String maskEmlAddr;

	/** 사용자 상태 코드 */
	private String userSttCd;

	/** 사용자 상태 코드명 */
	private String userSttNm;

	/** 가입일자 */
	private String joinYmd;

	/** 최종 로그인 일시 */
	private String lastLgnDt;

	/** 사용자부서명 */
	private String userDeptNm;

	/** 직장전화번호 */
	private String wrcTelno;

	/** 사용자직급명 */
	private String userJbgdNm;

	/** 직장우편번호 */
	private String wrcZip;

	/** 직장기본주소 */
	private String wrcBasAddr;

	/** 직장상세주소 */
	private String wrcDaddr;

	/** 기업그룹명 */
	private String entGrpNm;

	/** 기업명 */
	private String entNm;

	/** 기관명 */
	private String instNm;

	/** 관심키워드 대분류코드 1 */
	private String stdLgclfCd1;

	/** 관심키워드 대분류명 1 */
	private String stdLgclfNm1;

	/** 관심키워드 대분류코드 2 */
	private String stdLgclfCd2;

	/** 관심키워드 대분류명 2 */
	private String stdLgclfNm2;

	/** 관심키워드 조회 목록 */
	private List<AccountMngPrdtSelectRes> lstPrdt;

	/** 분석환경사용자아이디 */
	private String anlsEnvUserId;

}

package com.katri.web.auth.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "로그인 시도한 사람의 정보(LoginId로조회)")
public class TryLoginUserSelectRes {

//------ [[tb_user_bas]] 관련 정보 시작

	/** 사용자아이디 */
	private String userId;

	/** 사용자명 */
	private String userNm;

	/** 사용자유형코드 */
	private String userTyCd;
//
//	/** 사용자비밀번호 */
//	private String userPwd;
//
//	/** 사용자기존비밀번호 */
//	private String userLgcyPwd;
//
//	/** 사용자연계정보값 */
//	private String userLinkInfoVal;

	/** 암호화휴대전화번호값 */
	private String encptMblTelnoVal;

	/** 암호화이메일주소값 */
	private String encptEmlAddrVal;

	/** 이메일인증번호값 */
	private String emlCertNoVal;

	/** 사용자상태코드 */
	private String userSttCd;

	/** 권한그룹일련번호 */
	private Integer authrtGrpSn;

//	/** 가입신청요청일시 */
//	private String joinAplyDmndDt;
//
//	/** 가입신청처리일시 */
//	private String joinAplyPrcsDt;
//
//	/** 가입신청처리자아이디 */
//	private String joinAplyPrcrId;

//	/** 가입일자 */
//	private String joinYmd;

//	/** 탈퇴신청요청일시 */
//	private String whdwlAplyDmndDt;
//
//	/** 탈퇴신청처리일시 */
//	private String whdwlAplyPrcsDt;
//
//	/** 탈퇴신청처리자아이디 */
//	private String whdwlAplyPrcrId;
//
//	/** 탈퇴일자 */
//	private String whdwlYmd;
//
//	/** 탈퇴사유내용 */
//	private String whdwlResnCn;
//
//	/** 최종로그인일시 */
//	private String lastLgnDt;
//
//	/** 최종로그인IP주소 */
//	private String lastLgnIpAddr;
//
//	/** 로그인실패수 */
//	private Integer lgnFirCnt;
//
//	/** 최종로그인실패일시 */
//	private String lastLgnFirDt;
//
//	/** 최종비밀번호변경일시 */
//	private String lastPwdChgDt;

	/** 휴면일자 */
	private String drmncyYmd;

	/** 최종휴면일시 */
	private String lastDrmncyDt;

	/** 최종휴면해제일시 */
	private String lastDrmncyRmvDt;

//------// [[tb_user_bas]] 관련 정보 끝

	/** 응답코드 : 응답코드(아이디가 없거나, 비밀번호 틀렸거나 등등) */
	private String loginResCode;

}

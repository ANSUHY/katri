package com.katri.web.auth.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = " Request")
public class UserFindSaveReq {

	/** 인증 번호 */
	private String certNo;

	/** 인증 받는 메일 정보 */
	private String rcvrEmlAddr;

	/** 타겟 아이디 */
	private String targetUserId;

	/** 수정자 아이디 */
	private String mdfrId;

	/** 변경 패스워드 */
	private String chgUserPwd;

	/** 변경 패스워드 재확인 */
	private String chgUserPwdChk;

	/** 회원 유형 */
	private String userTyCd;

}

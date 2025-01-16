package com.katri.web.mypage.accountMng.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "계정 관리 저장 Request")
public class AccountMngSaveReq extends Common {

	/** 대상 사용자아이디 */
	private String targetUserId;

	/** 대상 사용자상태코드 */
	private String targetUserSttCd;

	/** 승인/반려 여부 */
	private String apprFlag;

	/** 가입신청처리자아이디 */
	private String joinAplyPrcrId;

	/** 탈퇴신청처리자아이디 */
	private String whdwlAplyPrcrId;

	/** 수정자아이디 */
	private String mdfrId;

}

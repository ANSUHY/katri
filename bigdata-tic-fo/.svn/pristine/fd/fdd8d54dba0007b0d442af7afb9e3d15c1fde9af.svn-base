package com.katri.web.auth.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "[일정기간 지난 비밀번호 변경] 저장 Request")
public class OverDayChgPwdSaveReq extends Common {

	/** 입력한 [현재 비밀번호] */
	private String nowPwd;

	/** 입력한 [새 비밀번호] */
	private String chgPwd;

	/** 입력한 [새 비밀번호 확인] */
	private String chgPwdCheck;

	/** 사용자 ID_로그인한 사용자 */
	private String userId;

	/** [새 비밀번호] 암호한 값 */
	private String eyptChgPwd;

	/** 수정자아이디 */
	private String mdfrId;

}

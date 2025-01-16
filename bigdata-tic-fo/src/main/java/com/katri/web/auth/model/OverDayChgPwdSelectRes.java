package com.katri.web.auth.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "[일정기간 지난 비밀번호 변경] 조회 Response")
public class OverDayChgPwdSelectRes extends Common {

	/** 사용자 아이디 */
	private String userId;

	/** 암호화이메일주소값 */
	private String encptEmlAddrVal;

	/** 분석환경사용자아이디 */
	private String anlsEnvUserId;

}

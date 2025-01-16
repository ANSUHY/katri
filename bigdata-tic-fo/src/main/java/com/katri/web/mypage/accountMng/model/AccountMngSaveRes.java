package com.katri.web.mypage.accountMng.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "계정 관리 저장 Response")
public class AccountMngSaveRes extends Common {

	/** 사용자아이디 */
	private String userId;

	/** 사용자유형코드 */
	private String userTyCd;

	/** 승인/반려 여부 */
	private String apprFlag;

}

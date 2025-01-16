package com.katri.web.join.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "회원 가입 저장 Response")
public class JoinSaveRes extends Common {

	/**************** 사용자 저장 정보 시작 ****************/
	/** 사용자 아이디 */
	private String userId;

	/** 사용자 유형 코드 */
	private String userTyCd;
	/**************** 사용자 저장 정보 종료 ****************/

}

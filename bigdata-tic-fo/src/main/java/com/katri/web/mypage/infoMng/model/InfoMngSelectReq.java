package com.katri.web.mypage.infoMng.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "회원 정보 관리 조회 Request")
public class InfoMngSelectReq extends Common {

	/** 회원 비밀번호 */
	private String chkUserPwd;

	/** 대상 사용자 아이디 */
	private String targetUserId;

	/** 대상 사용자 유형 코드 */
	private String targetUserTyCd;

	/** 기업그룹일련번호 */
	private Integer entGrpSn;

	/**************** 기타 ****************/
	/** 이메일 템플릿 코드값 */
	private String emlCode;

	/** pass 사용자 연계 정보 */
	private String searchUserLinkInfoVal;

}

package com.katri.web.user.user.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "회원 현황 관리 조회 Request")
public class UserSelectReq extends Common {

	/************ 검색 조건 시작 ************/

	/** 검색 사용자아이디 */
	private String searchUserId;

	/** 검색 사용자상태코드 */
	private String searchUserSttCd;

	/** 이력 약관 유형 */
	private String searchTrmsCdVal;

	/************ 검색 조건 종료 ************/

	/** 상세 사용자아이디 */
	private String targetUserId;

	/** 사용자 유형 코드 */
	private String targetUserTypeCd;

	/** 사업자 등록번호 */
	private String targetBrno;

	/** 기업그룹일련번호 */
	private Integer entGrpSn;

	/** 기업그룹관리번호 */
	private String entGrpMngNo;

}

package com.katri.web.mypage.accountMng.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "계정 관리 조회 Request")
public class AccountMngSelectReq extends Common {

	/************ 검색 조건 시작 ************/
	/** 검색 이름 */
	private String searchUserNm;

	/** 검색 상태값 */
	private String searchUserSttCd;

	/** 조회 회원 아이디 */
	private String targetUserId;

	/** 조회 회원 유형 */
	private String targetUserTyCd;

	/** 목록 변경 페이지 */
	private int chgPage;

	/** 계쩡 사용자 아이디 */
	private String accountUserId;
	/************ 검색 조건 종료 ************/

}

package com.katri.web.mypage.inquiry.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Inquiry 조회 Request")
public class InquirySelectReq extends Common{

	/** 게시물 일련 번호 */
	private Integer nttSn;
	/** 게시물 유형 코드 */
	private String nttTyCd;
	/** 게시물 분류 코드 */
	private String clfCd;
	/** 사용자 아이디 */
	private String crtrId;
	/** 게시물 분류 코드 */
	private String nttClfCd;

	/** 유저 타입 */
	private String userTyCd;
	/** 공통 코드 */
	private String comnCd;

	/***/
	private int chgPage;

}

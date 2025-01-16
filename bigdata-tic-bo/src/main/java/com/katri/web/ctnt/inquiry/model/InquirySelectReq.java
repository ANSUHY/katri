package com.katri.web.ctnt.inquiry.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "1:1 문의 조회 Req")
public class InquirySelectReq extends Common{

	/*-------- 검색 조건 ------------*/
	/** 검색 BOARD TYPE*/
	private String searchBoardType;
	/** 검색 */
	private String[] searchArrBoardType;
	/** target 게시글 번호 */
	private Integer targetBoardNo;
	/** 검색어 */
	private String searchKeyword;
	/*----------------------------*/

	private String nttCyCd;

	/** 작성자 */
	private String crtrId;



}

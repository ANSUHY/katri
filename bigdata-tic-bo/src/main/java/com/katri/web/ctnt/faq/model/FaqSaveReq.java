package com.katri.web.ctnt.faq.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "FAQ 저장 Request")
public class FaqSaveReq extends Common {

	/** 게시물 일련 번호 */
	private Integer nttSn;

	/** 게시글 유형 코드 */
	private String nttTyCd;

	/** 게시물 분류 코드 */
	private String nttClfCd;

	/** 게시물 제목 */
	private String nttSjNm;

	/** 게시물 내용 */
	private String nttCn;

	/** 생성자 아이디 */
	private String crtrId;

	/** 수정자 아이디 */
	private String mdfrId;

}

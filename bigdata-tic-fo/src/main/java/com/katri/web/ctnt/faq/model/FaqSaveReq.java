package com.katri.web.ctnt.faq.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "FAQ 등록,수정 Request")
public class FaqSaveReq {

	/** 게시물 일련 번호 */
	private Integer nttSn;

	/** 게시글 유형 코드 */
	private String nttTyCd;

}

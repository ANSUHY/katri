package com.katri.web.ctnt.faq.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "FAQ 조회 Response")
public class FaqSelectRes extends Common {

	/** 게시글 순번 */
	private Integer rownum;

	/** 게시물 일련 번호 */
	private Integer nttSn;

	/** 게시물 제목 */
	private String nttSjNm;

	/** 게시물 내용 */
	private String nttCn;

	/** 게시물 분류 코드 */
	private String nttClfCd;

	/** 게시글 분류 값 */
	private String comnCdNm;

	/** 생성 일시 */
	private String crtDt;

}

package com.katri.web.ctnt.faq.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "FAQ 저장 Response")
public class FaqSaveRes extends Common {

	/** 게시물 일련 번호 */
	private Integer nttSn;

}

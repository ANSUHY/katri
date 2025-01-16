package com.katri.web.ctnt.archive.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Archive 조회 Request")
public class ArchiveSelectReq extends Common {

	/** 게시물 일련 번호 */
	private Integer nttSn;

	/** 게시글 유형 코드 */
	private String nttTyCd;

}

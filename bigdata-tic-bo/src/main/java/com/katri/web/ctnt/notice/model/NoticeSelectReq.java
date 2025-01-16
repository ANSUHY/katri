package com.katri.web.ctnt.notice.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "공지사항 관리 조회 Request")
public class NoticeSelectReq extends Common {

	/** 게시물 일련 번호 */
	private Integer nttSn;

	/** 게시글 유형 코드 */
	private String nttTyCd;

	/** 공지 노출 여부 */
	private String ntcExpsrYn;

}

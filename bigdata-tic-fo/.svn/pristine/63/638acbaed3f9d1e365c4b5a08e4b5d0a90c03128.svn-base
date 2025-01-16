package com.katri.web.ctnt.notice.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "공지사항 조회 Request")
public class NoticeSelectReq extends Common {

	/** 타겟 게시물 일련 번호 */
	private Integer targetNttSn;

	/** 게시물 일련 번호 */
	private Integer nttSn;

	/** 게시글 유형 코드 */
	private String nttTyCd;

	/** 게시물 분류 코드 */
	private String nttClfCd;

	/** 게시글 변경 페이지 */
	private int chgPage;

}

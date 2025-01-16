package com.katri.web.board.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Board 조회 Request")
public class BoardSelectReq extends Common {

	//------ 검색 조건 시작
	/** 검색 BOARD TYPE */
	private String searchBoardType;

	/** 검색 BOARD TYPE ARR*/
	private String[] searchArrBoardType;

	//------// 검색 조건 끝

	/** taget 게시글 번호 */
	private Integer targetBoardNo;

	/** [미리보기] 시 받은 내용 */
	private String previewMenuCptnCn;

	/** [미리보기] 시 받은 내용_unescape */
	private String previewMenuCptnCnUnescape;


}

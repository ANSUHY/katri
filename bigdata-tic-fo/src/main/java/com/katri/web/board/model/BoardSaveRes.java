package com.katri.web.board.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Board 저장 Response")
public class BoardSaveRes extends Common {

	//------ 검색 조건 시작
	/** 검색 BOARD TYPE */
	private String searchBoardType;

	/** 검색 BOARD TYPE ARR*/
	private String[] searchArrBoardType;

	//------// 검색 조건 끝

	/** taget 게시글 번호 */
	private Integer targetBoardNo;

	/** 게시글 번호 */
	private Integer boardNo;


}

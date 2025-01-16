package com.katri.web.board.model;

import java.util.List;

import com.katri.common.model.Common;
import com.katri.web.comm.model.FileDto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Board 조회 Response")
public class BoardSelectRes extends Common {

	//------ 검색 조건 시작
	/** 검색 BOARD TYPE */
	private String searchBoardType;

	/** 검색 BOARD TYPE ARR*/
	private String[] searchArrBoardType;

	//------// 검색 조건 끝

	/** 번호 */
	private Integer rownum;

	/** 게시글 번호 */
	private Integer boardNo;

	/** 제목 */
	private String title;

	/** 내용 */
	private String cont;

	/** 타입 코드 */
	private String typeCd;

	/** 타입 이름 */
	private String typeNm;

	/** 작성일자 */
	private String regDt;

	/** 파일 정보_img */
	private List<FileDto> listFileImg;

	/** 파일 정보_file */
	private List<FileDto> listFileFile;



}

package com.katri.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 공통 업무(validation 참조 : https://bamdule.tistory.com/35)</li>
 * <li>서브 업무명 : 공통정보 관리</li>
 * <li>설	 명 : 공통모델 정보</li>
 * <li>작  성  자 : Lee Han Seong</li>
 * <li>작  성  일 : 2021. 01. 18.</li>
 * </ul>
 * <pre>
 * ======================================
 * 변경자/변경일 :
 * 변경사유/내역 :
 * ======================================
 * </pre>
 ***************************************************/
@Data
@ApiModel(description = "공통정보")
public class Common {

	/** Row Number */
	@ApiModelProperty(notes = "로우넘버", example = "1", position = 1)
	private int rnum;

	/** 검색종류 */
	@ApiModelProperty(notes = "검색종류", example = "", position = 2)
	private String searchType;

	/** 검색조건 */
	@ApiModelProperty(notes = "검색조건", example = "", position = 3)
	private String searchKeyword;

	/** 현재페이지 */
	@ApiModelProperty(notes = "현재페이지", example = "1", position = 5)
	private int currPage;

	/** 행 수 */
	@ApiModelProperty(notes = "행 수", example = "10", position = 6)
	private int rowCount;

	/** 시작 행 */
	@ApiModelProperty(notes = "시작 행", example = "1", position = 7)
	private Integer startRow;

	/** 종료 행 */
	@ApiModelProperty(notes = "종료 행", example = "10", position = 8)
	private int endRow;

	/** recordCountPerPage */
	@ApiModelProperty(notes = "레코드 카운트 페이지", example = "10", position = 9)
	private int recordCountPerPage;

	/** 총 갯수 */
	@ApiModelProperty(notes = "총 갯수", example = "50", position = 10)
	private int totCnt;

	/** 검색 시작일 */
	@ApiModelProperty(notes = "검색 시작일", example = "2019-10-01", position = 11)
	private String searchFromDate;

	/** 검색 종료일 */
	@ApiModelProperty(notes = "검색 종료일", example = "2019-10-31", position = 12)
	private String searchToDate;

	/** 검색 일 */
	@ApiModelProperty(notes = "검색 일", example = "2019-10-01", position = 13)
	private String searchDate;

	/** 그리드 데이타 */
	@ApiModelProperty(notes = "그리드 데이타", example = "Object", position = 14)
	private String gridData;

}

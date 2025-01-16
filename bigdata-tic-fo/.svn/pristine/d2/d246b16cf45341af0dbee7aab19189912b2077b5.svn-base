package com.katri.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 공통 관리 업무</li>
 * <li>서브 업무명 : 공통 관리</li>
 * <li>설	 명 : 공통 정보</li>
 * <li>작  성  자 : Lee Han Seong</li>
 * <li>작  성  일 : 2019. 10. 15.</li>
 * </ul>
 * <pre>
 * ======================================
 * 변경자/변경일 :
 * 변경사유/내역 :
 * ======================================
 * </pre>
 ***************************************************/
@Getter
@Setter
@ToString
@ApiModel(description = "공통객체")
public class CommonDto {

	/** 언어 */
	@ApiModelProperty(notes = "언어", example = "1", position = 1)
	private String langCd;

	/** 현재페이지 */
	@ApiModelProperty(notes = "현재페이지", example = "1", position = 2)
	private int currPage;

	/** 행 수 */
	@ApiModelProperty(notes = "행 수", example = "10", position = 3)
	private int rowCount;

	/** 시작 행 */
	@ApiModelProperty(notes = "시작 행", example = "1", position = 4)
	private Integer startRow;

	/** 종료 행 */
	@ApiModelProperty(notes = "종료 행", example = "10", position = 5)
	private Integer endRow;

	/** 페이지 사이즈 */
	@ApiModelProperty(notes = "페이지 사이즈", example = "10", position = 6)
	private Integer pageSize = 10;

}

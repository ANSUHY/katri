package com.katri.common.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 공통 업무</li>
 * <li>서브 업무명 : 공통 응답 객체 정보</li>
 * <li>설	 명 : 공통 응답 객체 정보</li>
 * <li>작  성  자 : Red 2021</li>
 * <li>작  성  일 : 2021. 02. 13.</li>
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
@ApiModel(description = "공통응답객체")
public class CommonRes {
	/** 총 로우수 */
	@ApiModelProperty(notes = "총 로우수", example = "1000", position = 1)
	private int totCnt;

	/** 총 목록 정보 */
	@ApiModelProperty(notes = "목록 정보", example = "[]", position = 2)
	private List<? extends Object> list;

	/** Object 정보 */
	@ApiModelProperty(notes = "Object 정보", example = "", position = 3)
	private Object object;
}

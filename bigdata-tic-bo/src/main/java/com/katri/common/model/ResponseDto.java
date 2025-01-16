package com.katri.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 공통 업무</li>
 * <li>서브 업무명 : 객체 관리</li>
 * <li>설     명 : 응답시 공통 포맷을 유지하기 위한 DTO</li>
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
@Getter
@Builder
@ToString
@ApiModel(description = "응답정보")
public class ResponseDto {

	/** 결과데이타 */
	@ApiModelProperty(notes = "결과데이타", required = true, position = 1)
	private Object data;

	/** 결과메세지 */
	@ApiModelProperty(notes = "결과메세지", position = 2)
	private String resultMessage;

	/** 결과코드 */
	@ApiModelProperty(notes = "결과코드", position = 3)
	private int resultCode;

}

package com.katri.web.comm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 공통코드 관리 업무</li>
 * <li>서브 업무명 : 공통코드저장 관리</li>
 * <li>설	 명 : 공통코드저장 정보</li>
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
@Setter
@ToString
@ApiModel(description = "공통코드저장객체")
public class CommCodeSaveReq {

	/** 대표코드 */
	@ApiModelProperty(notes = "대표코드", example = "ACL", position = 1)
	private String reprCd;

	/** 공통코드 */
	@ApiModelProperty(notes = "공통코드", example = "ACL001", position = 1)
	private String commCd;

	/** 부모공통코드 */
	@ApiModelProperty(notes = "부모공통코드", example = "ACL001001", position = 1)
	private String upCommCd;

	/** 코드명 */
	@ApiModelProperty(notes = "코드명", example = "노트북", position = 1)
	private String cdNm;

	/** 설명 */
	@ApiModelProperty(notes = "설명", example = "노트북", position = 1)
	private String dsc;

	/** 사용여부 */
	@ApiModelProperty(notes = "사용여부", example = "Y", position = 1)
	private String useYn;

	/** 뎁스 */
	@ApiModelProperty(notes = "뎁스", example = "1", position = 1)
	private String depth;

	/** 정렬순번 */
	@ApiModelProperty(notes = "정렬순번", example = "1", position = 1)
	private int sortSeq;

	/** 코드값 */
	@ApiModelProperty(notes = "코드값", example = "VAL1", position = 1)
	private String cdVal;

	/** 등록자 */
	@ApiModelProperty(notes = "등록자", example = "1", position = 1)
	private int regMbrNo;

	/** 수정자 */
	@ApiModelProperty(notes = "수정자", example = "1", position = 1)
	private int updMbrNo;

}

package com.katri.common.tlds.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 공통 TLD 정보</li>
 * <li>서브 업무명 : 공통 TLD 정보</li>
 * <li>설	 명 : 공통 TLD 응답 객체</li>
 * <li>작   성  자 : FCS</li>
 * <li>작   성  일 : 2021. 03. 14.</li>
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
@ApiModel(description = "공통 TLD 응답 객체")
public class CommonTldRes {
	
	/** 코드ID */
	@ApiModelProperty(notes = "코드ID", example = "ABC", position = 1)
	private String codeId;
	
	/** 코드명 */
	@ApiModelProperty(notes = "코드명", example = "abc", position = 2)
	private String codeNm;
	
	/** 코드값 */
	@ApiModelProperty(notes = "코드값", example = "AAA", position = 3)
	private String codeVal;
}

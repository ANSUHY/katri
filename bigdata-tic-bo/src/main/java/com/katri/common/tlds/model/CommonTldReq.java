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
 * <li>설	 명 : 공통 TLD 요청 객체</li>
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
@ApiModel(description = "공통 TLD 요청 객체")
public class CommonTldReq {
	
	/** 코드 */
	@ApiModelProperty(notes = "코드", example = "ABC", position = 1)
	private String code;
}

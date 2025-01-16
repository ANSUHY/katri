package com.katri.web.join.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "관심 키워드 조회 Response")
public class JoinPrdtSelectRes {

	/** 표준대분류코드 */
	private String stdLgclfCd;

	/** 표준대분류코드명 */
	private String stdLgclfNm;

	/** 표준중분류코드 */
	private String stdMlclfCd;

	/** 표준중분류코드명 */
	private String stdMlclfNm;

}

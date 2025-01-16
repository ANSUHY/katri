package com.katri.web.mypage.infoMng.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "회원 관심 키워드 조회 Response")
public class InfoMngPrdtSelectRes {

	/** 표준대분류코드 */
	private String stdLgclfCd;

	/** 표준대분류코드명 */
	private String stdLgclfNm;

	/** 표준중분류코드 */
	private String stdMlclfCd;

	/** 표준중분류코드명 */
	private String stdMlclfNm;

	/** 정렬순서 */
	private Integer srtSeq;

}

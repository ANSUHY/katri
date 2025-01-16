package com.katri.web.main.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Main 조회 Req")
public class MainSelectReq extends Common{

	/** 게시물 유형 코드 */
	String nttTyCd;

	/** 게시물 일련 번호 */
	private Integer nttSn;


	/************ 메인비주얼/팝업존 ************/
	/** 검색 사용 여부 */
	private String searchUseYn;

	/** 검색 홈페이지 구성 유형 코드 */
	private String searchHmpgCptnTyCd;

	/** 검색 PC 파일 이미지 div_nm */
	private String searchPcImg;

	/** 검색 Mobile 파일 이미지 div_nm */
	private String searchMobileImg;


	/************ 검색 조건 종료 ************/

	/** 홈페이지구성일련번호 */
	private Integer	hmpgCptnSn;

	private String hmpgCptnTyCd;

}

package com.katri.web.hmpgCptn.popupZone.model;

import com.katri.common.model.Common;
import com.katri.web.hmpgCptn.mainVisual.model.MainVisualSelectReq;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "메인 비주얼 관리 조회 Request")
public class PopupZoneSelectReq extends Common{

	/************ 검색 조건 시작 ************/

	/** 검색 사용 여부 */
	private String searchUseYn;

	/** 검색 홈페이지 구성 유형 코드 */
	private String searchHmpgCptnTyCd;

	/** 검색 PC 파일 이미지 div_nm */
	private String searchVisualPcImg;

	/** 검색 Mobile 파일 이미지 div_nm */
	private String searchVisualMobileImg;

	/************ 검색 조건 종료 ************/

	/** 홈페이지구성일련번호 */
	private Integer	hmpgCptnSn;
}

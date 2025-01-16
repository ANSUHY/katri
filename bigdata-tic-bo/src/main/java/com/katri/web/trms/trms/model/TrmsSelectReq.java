package com.katri.web.trms.trms.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Trms 조회 Request")
public class TrmsSelectReq extends Common {

	//------ 검색 조건 시작
	/** 검색 약관유형코드 */
	private String searchTrmsTyCd;

	//------// 검색 조건 끝

	/** target trmsSn */
	private Integer targetTrmsSn;

}

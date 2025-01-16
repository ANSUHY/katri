package com.katri.web.system.authrt.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "접속 권한 관리 조회 Request")
public class AuthrtSelectReq extends Common {

	/************ 검색 조건 시작 ************/

	/** 검색 사이트 유형 코드 */
	private String searchSiteTyCd;

	/************ 검색 조건 종료 ************/

	/** 상세 권한 그룹 일련 번호 */
	private Integer targetAuthrtGrpSn;

}

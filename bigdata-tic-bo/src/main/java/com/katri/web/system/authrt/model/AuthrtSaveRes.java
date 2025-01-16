package com.katri.web.system.authrt.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "접속 권한 관리 저장 Response")
public class AuthrtSaveRes extends Common {

	/** 권한그룹일련번호 */
	private Integer authrtGrpSn;

	/** 권한그룹메뉴일련번호 : 배열 */
	private Integer[] arrAuthrtGrpMenuSn;

}

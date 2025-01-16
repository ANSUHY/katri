package com.katri.web.hmpgCptn.mainVisual.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "메인 비주얼 관리 저장 Response")
public class MainVisualSaveRes extends Common {

	/** 홈페이지구성일련번호 */
	private Integer	hmpgCptnSn;

}

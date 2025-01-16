package com.katri.web.system.menu.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Menu 관리 저장 Response")
public class MenuSaveRes extends Common {

	/** 메뉴일련번호 */
	private Integer menuSn;

	/** 상위메뉴일련번호 */
	private Integer upMenuSn;

	/** 메뉴레벨값 */
	private Integer menuLvlVal;

}

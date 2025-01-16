package com.katri.web.system.menu.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Menu 관리 조회 Request")
public class MenuSelectReq extends Common {

	/************ 검색 조건 시작 ************/

	/** 검색 사이트 유형 코드 */
	private String searchSiteTyCd;

	/** 검색 사용 여부 */
	private String searchUseYn;

	/** 검색 메뉴 레벨 값 */
	private Integer searchMenuLvlVal;

	/** 검색 상위 메뉴 번호 */
	private Integer searchUpMenuSn;

	/** 검색 하위 메뉴 레벨 값 */
	private Integer searchDownMenuLvlVal;

	/************ 검색 조건 종료 ************/

	/** 메뉴 일련 번호 */
	private Integer menuSn;

	/** 상세 메뉴 조회 번호 */
	private Integer targetMenuSn;

}

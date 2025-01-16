package com.katri.web.system.menu.model;

import javax.validation.constraints.NotEmpty;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Menu 관리 저장 Request")
public class MenuSaveReq extends Common {

	/** 메뉴일련번호 */
	private Integer menuSn;

	/** 메뉴명 */
	@NotEmpty(message = "메뉴명이 비어있습니다.")
	private String 	menuNm;

	/** 메뉴레벨값 */
	private Integer menuLvlVal;

	/** 상위메뉴일련번호 */
	private Integer upMenuSn;

	/** 최상위메뉴일련번호 */
	private Integer bestMenuSn;

	/** 전체메뉴일련번호값 */
	private String 	allMenuSnVal;

	/** 메뉴URL주소 */
	private String 	menuUrlAddr;

	/** 연계유형코드 */
	private String linkTyCd;

	/** 사이트유형코드 */
	@NotEmpty(message = "사이트 유형 코드가 비어있습니다.")
	private String siteTyCd;

	/** 정렬순서 */
	private Integer srtSeq;

	/** 사용여부 */
	@NotEmpty(message = "사용여부가 비어있습니다.")
	private String useYn;

	/** 생성일시 */
	private String crtDt;

	/** 생성자아이디 */
	private String crtrId = "0";

	/** 수정일시 */
	private String mdfcn_dt;

	/** 수정자아이디 */
	private String mdfrId = "0";

	/*--------------------------------*/

	/** 정렬 타입 */
	private String seqType;

	/** 정렬 바뀌는 메뉴 번호 */
	private Integer chgMenuSn;

}

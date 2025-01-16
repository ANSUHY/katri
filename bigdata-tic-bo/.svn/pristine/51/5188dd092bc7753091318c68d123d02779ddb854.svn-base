package com.katri.web.login.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Login 한 사람의 권한에 따른 메뉴 Response")
public class LoginAuthrtMenuRes implements Serializable {

	private static final long serialVersionUID = -4916591343558539111L;

	/** 사이트유형코드 */
	private String siteTyCd;

	/** 메뉴일렬번호 */
	private Integer menuSn;

	/** 메뉴명 */
	private String menuNm;

	/** 메뉴URL주소 */
	private String menuUrlAddr;

	/** 연계 유형 코드 */
	private String linkTyCd;

	/** 상위메뉴일련번호 */
	private Integer upMenuSn;

	/** 모든 메뉴 일렬번호 */
	private String allMenuSnVal;

	/** 정렬순서 */
	private Integer srtSeq;

	/** 메뉴레벨값 */
	private Integer menuLvlVal;

	/** 사용여부 */
	private String useYn;

	/** 정렬순서 path */
	private String srtPath;

	/** 권한그룹메뉴일렬번호 */
	private Integer authrtGrpMenuSn;

	/** 접속권한여부 */
	private String cntnAuthrtYn;

}

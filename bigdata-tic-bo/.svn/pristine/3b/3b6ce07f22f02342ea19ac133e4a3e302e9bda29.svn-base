package com.katri.web.system.authrt.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "접속 권한 관리 조회 Response")
public class AuthrtSelectRes extends Common {

	/** 권한그룹일련번호 */
	private Integer authrtGrpSn;

	/** 권한그룹명 */
	private String 	authrtGrpNm;

	/** 사이트유형코드 */
	private String siteTyCd;

	/** 사이트유형코드명 */
	private String siteTyCdNm;

	/** 사이트유형코드값 */
	private String siteTyCdVal;

	/** 사용여부 */
	private String useYn;

	/** 생성일시 */
	private String crtDt;

	/** 생성자아이디 */
	private String crtrId;

	/** 수정일시 */
	private String mdfcnDt;

	/** 수정자아이디 */
	private String mdfrId;

	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/
	/** 권한 그룹의 메뉴별 접근 권한 정보 시작 */

	/** 메뉴일련번호 */
	private Integer menuSn;

	/** 메뉴명 */
	private String menuNm;

	/** 상위메뉴일련번호 */
	private Integer upMenuSn;

	/** 최상위메뉴일련번호 */
	private String allMenuSnVal;

	/** 정렬순서 */
	private Integer srtSeq;

	/** 메뉴레벨값 */
	private Integer menuLvlVal;

	/** 권한그룹메뉴일련번호 */
	private Integer authrtGrpMenuSn;

	/** 접속권한여부 */
	private String cntnAuthrtYn;

	/** 권한 그룹의 메뉴별 접근 권한 정보 종료 */
	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/
}

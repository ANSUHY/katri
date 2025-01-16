package com.katri.web.system.authrt.model;

import javax.validation.constraints.NotEmpty;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "접속 권한 관리 저장 Request")
public class AuthrtSaveReq extends Common {

	/** 권한그룹일련번호 */
	private Integer authrtGrpSn;

	/** 권한그룹명 */
	@NotEmpty(message = "권한그룹명이 비어있습니다.")
	private String authrtGrpNm;

	/** 사이트유형코드 */
	@NotEmpty(message = "사이트유형코드이 비어있습니다.")
	private String siteTyCd;

	/** 사용여부 */
	@NotEmpty(message = "사용여부가 비어있습니다.")
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

	/** 메뉴 번호 */
	private Integer menuSn;

	/** 메뉴 번호 : 배열 */
	private Integer[] arrMenuSn;

	/** 권한 체크 값 */
	private String cntnAuthrtYn;

	/** 권한 체크 값 : 배열 */
	private String[] arrCntnAuthrtYn;

	/** 권한 그룹 메뉴 일련 번호 */
	private Integer authrtGrpMenuSn;

	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/


}

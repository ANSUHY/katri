package com.katri.web.comm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 공통레이아웃객체 관리 업무</li>
 * <li>서브 업무명 : 공통레이아웃객체 관리</li>
 * <li>설	 명 : 공통레이아웃객체 정보</li>
 * <li>작  성  자 : Lee Han Seong</li>
 * <li>작  성  일 : 2019. 10. 15.</li>
 * </ul>
 * <pre>
 * ======================================
 * 변경자/변경일 :
 * 변경사유/내역 :
 * ======================================
 * </pre>
 ***************************************************/
@Getter
@Setter
@ToString
@ApiModel(description = "공통레이아웃객체")
public class LayoutReq {

	/** 현재URL */
	@ApiModelProperty(notes = "현재URL", example = "/system/auth/list", position = 1)
	private String currUrl;

	/** 다이렉트구분(CUST_DTL, SALECORDER_DTL, QUOT_DTL, SALECACTIVITY_DTL, APPLOVAL_DTL) */
	@ApiModelProperty(notes = "현재다이렉트구분", example = "SALECACTIVITY_DTL", position = 1)
	private String directGbn;

	/** 파라메터1 */
	@ApiModelProperty(notes = "파라메터1", example = "", position = 1)
	private String param1;

	/** 파라메터2 */
	@ApiModelProperty(notes = "파라메터2", example = "", position = 1)
	private String param2;

	/** 파라메터3 */
	@ApiModelProperty(notes = "파라메터3", example = "", position = 1)
	private String param3;

	/** 파라메터4 */
	@ApiModelProperty(notes = "파라메터4", example = "", position = 1)
	private String param4;

	/** 파라메터5 */
	@ApiModelProperty(notes = "파라메터5", example = "", position = 1)
	private String param5;

	/** 파라메터6 */
	@ApiModelProperty(notes = "파라메터6", example = "", position = 1)
	private String loginFormYn;

}

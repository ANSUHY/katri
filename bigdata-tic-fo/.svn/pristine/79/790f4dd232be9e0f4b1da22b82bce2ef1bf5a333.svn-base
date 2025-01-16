package com.katri.web.mbr.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel(description = "회원정보 리턴 객체")
public class MbrRes {
	/** 번호 */
	@ApiModelProperty(notes = "번호", example = "0")
	private String rownum;

	/** 인증기관코드 */
	@ApiModelProperty(notes = "인증기관코드", example = "")
	private String instId;

	/** 인증기관이름 */
	@ApiModelProperty(notes = "인증기관이름", example = "")
	private String certInstNm;

	/** 사용자 저장 성공 카운트 */
	@ApiModelProperty(notes = "사용자 저장 성공 카운트", example = "0")
	private int saveCount;

	/** 사용자 아이디 */
	@ApiModelProperty(notes = "사용자 아이디", example = "test", position = 1)
	private String mbrId;
	/** 사용자 명 */
	@ApiModelProperty(notes = "이름", example = "테스트", position = 4)
	private String mbrNm;
	/** 이메일 */
	@ApiModelProperty(notes = "이메일", example = "test@gmail.com", position = 14)
	private String mbrEmlAddr;
	/** 부서이름 */
	@ApiModelProperty(notes = "부서이름", example = "N", position = 8)
	private String deptNm;
	/** 비밀번호 */
	@ApiModelProperty(notes = "비밀번호", example = "1234", position = 2)
	private String mbrPwd;
	/** 핸드폰 번호 */
	@ApiModelProperty(notes = "핸드폰 번호", example = "82", position = 22)
	private String mbrCpNo;
	/** 로그인실패횟수 */
	@ApiModelProperty(notes = "로그인실패횟수", example = "0", position = 11)
	private String lgnFirNotms;
	/** 상태코드 */
	@ApiModelProperty(notes = "상태코드", example = "A", position = 9)
	private String sttCd;
	/** 폐쇄여부 */
	@ApiModelProperty(notes = "폐쇄여부", example = "Y", position = 22)
	private String clsgYn;
	/** 관리자 여부 */
	@ApiModelProperty(notes = "관리자 여부", example = "Y", position = 22)
	private String mngrYn;
	/** 작성일자 */
	@ApiModelProperty(notes = "작성일자", example = "YYYY.MM.DD", position = 22)
	private String regDt;
	/** 작성자 */
	@ApiModelProperty(notes = "작성자", example = "테스트", position = 22)
	private String regId;

	/** 아이디 중복 카운트 */
	@ApiModelProperty(notes = "아이디 중복 카운트", example = "테스트", position = 22)
	private int idCount;

	/** 이메일 중복 카운트 */
	@ApiModelProperty(notes = "이메일 중복 카운트", example = "테스트", position = 22)
	private int emailCount;
}

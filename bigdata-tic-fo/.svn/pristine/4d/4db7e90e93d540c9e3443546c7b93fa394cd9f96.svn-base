package com.katri.web.comm.model;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 캐쉬 코드 관리 업무</li>
 * <li>서브 업무명 : 공통 관리</li>
 * <li>설         명 : 파일 정보</li>
 * <li>작   성   자 : Why T</li>
 * <li>작   성   일 : 2021. 03. 38.</li>
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
@ApiModel(description = "캐쉬공통코드객체")
public class EhCacheCodeDto {

	/** 대표코드 */
	@ApiModelProperty(notes = "대표코드", example = "100400", position = 1)
	private String headCd;

	/** 코드 */
	@ApiModelProperty(notes = "코드", example = "DE", position = 2)
	private String code;

	/** 읽기코드 */
	@ApiModelProperty(notes = "읽기코드", example = "DE", position = 3)
	private String readCd;

	/** 코드명 */
	@ApiModelProperty(notes = "코드명", example = "German", position = 4)
	private String codeNm;

	/** 코드영문명 */
	@ApiModelProperty(notes = "코드영문명", example = "German", position = 4)
	private String codeEn;

	/** 코드명 */
	@ApiModelProperty(notes = "Read 코드명", example = "German", position = 4)
	private String readCodeNm;

	/** 코드영문명 */
	@ApiModelProperty(notes = "Read 코드영문명", example = "German", position = 4)
	private String readCodeEn;


	/** 대표코드 */
	@ApiModelProperty(notes = "", example = "DE001", position = 1)
	private String code4th;

	/** 대표코드 */
	@ApiModelProperty(notes = "", example = "DE001", position = 1)
	private String refChr1;

	/** 대표코드 */
	@ApiModelProperty(notes = "", example = "DE001", position = 1)
	private String refChr2;

	/** 대표코드 */
	@ApiModelProperty(notes = "", example = "DE001", position = 1)
	private String refChr3;

	/** 대표코드 */
	@ApiModelProperty(notes = "", example = "DE001", position = 1)
	private String refChr4;

	/** 대표코드 */
	@ApiModelProperty(notes = "", example = "DE001", position = 1)
	private String refChr5;

	/**  */
	@ApiModelProperty(notes = "", example = "1", position = 8)
	private Integer refNum1;

	/**  */
	@ApiModelProperty(notes = "", example = "1", position = 8)
	private Integer refNum2;

	/**  */
	@ApiModelProperty(notes = "", example = "1", position = 8)
	private Integer refNum3;

	/**  */
	@ApiModelProperty(notes = "", example = "1", position = 8)
	private Integer refNum4;

	/**  */
	@ApiModelProperty(notes = "", example = "1", position = 8)
	private Integer refNum5;

	/** 순번 */
	@ApiModelProperty(notes = "순번", example = "1", position = 8)
	private Integer priority;

	/**  */
	@ApiModelProperty(notes = "", example = "1", position = 8)
	private String refCnt;



	/** 상태(사용여부) */
	@ApiModelProperty(notes = "상태(사용여부)", example = "Y", position = 1)
	private String status;

	/** 생성일시 */
	@ApiModelProperty(notes = "생성일시", example = "2020-03-23 22:12:13.077", position = 1)
	private Date regDt;

	/** 생성자 아이디 */
	@ApiModelProperty(notes = "생성자 아이디", example = "admin", position = 1)
	private String regId;

	/** 수정일시 */
	@ApiModelProperty(notes = "수정일시", example = "2020-03-23 22:12:13.077", position = 1)
	private Date uptDt;

	/** 수정자 아이디 */
	@ApiModelProperty(notes = "수정자 아이디", example = "admin", position = 1)
	private String uptId;


	/** 국가코드 */
	@ApiModelProperty(notes = "국가코드", example = "DE", position = 1)
	private String natCd;

	/** 언어코드 */
	@ApiModelProperty(notes = "언어코드", example = "de", position = 1)
	private String langCd;

	/** 코드 목록 */
	@ApiModelProperty(notes = "캐쉬 코드 목록", example = "Object", position = 16)
	private List<EhCacheCodeDto> listCacheCode;

}

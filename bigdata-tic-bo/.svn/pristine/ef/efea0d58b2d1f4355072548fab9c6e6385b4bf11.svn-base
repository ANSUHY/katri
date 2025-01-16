package com.katri.web.hmpgCptn.mainVisual.model;

import com.katri.common.model.Common;
import com.katri.web.comm.model.FileDto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "메인 비주얼 관리 조회 Response")
public class MainVisualSelectRes extends Common {

	/** 홈페이지구성일련번호 */
	private Integer	hmpgCptnSn;

	/** 홈페이지구성유형코드 */
	private String	hmpgCptnTyCd;

	/** 홈페이지구성설명내용 */
	private String	hmpgCptnDescCn;

	/** 연계URL주소 */
	private String	linkUrlAddr;

	/** 연계유형코드 */
	private String linkTyCd;

	/** 연계유형코드명 */
	private String linkTyCdNm;

	/** 연계유형코드값 */
	private String linkTyCdVal;

	/** 정렬순서 */
	private Integer	srtSeq;

	/** 사용여부 */
	private String	useYn;

	/** 생성일시 */
	private String	crtDt;

	/** 생성자아이디 */
	private String	crtrId;

	/** 수정일시 */
	private String	mdfcnDt;

	/** 수정자아이디 */
	private String	mdfrId;

	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/
	/** main img PC 저장파일경로 전체 주소 */
	private String	pcImgStrgFileFullPathAddr;

	/** main img Mobile 저장파일경로 전체 주소 */
	private String	mobileImgStrgFileFullPathAddr;

	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/

	/** main visual PC img  파일 */
	private FileDto pcImgFile;

	/** main visual Mobile img  파일 */
	private FileDto mobileImgFile;

	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/

	/** 대라벨내용 */
	private String laLblCn;

	/** 소라벨내용 */
	private String smLblCn;

}

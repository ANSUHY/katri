package com.katri.web.main.model;

import java.util.List;

import com.katri.common.model.Common;
import com.katri.web.cert.model.CertRes;
import com.katri.web.comm.model.FileDto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Main 조회 Res")
public class MainSelectRes extends Common {

	/**----- 인증 현황 ------*/
	private Integer month;
	private List<CertRes> certResList;

	/**----- 메인비주얼 / 팝업존 -----*/
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

	/** 정렬순서 */
	private Integer	srtSeq;

	/** 사용여부 */
	private String	useYn;

	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/
	/** main visual / popUpZone img PC 저장파일경로 전체 주소 */
	private String	pcImgStrgFileFullPathAddr;

	/** main visual / popUpZone img Mobile 저장파일경로 전체 주소 */
	private String	mobileImgStrgFileFullPathAddr;

	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/

	/** main visual / popUpZone PC img  파일 */
	private FileDto pcImgFile;

	/** main visual / popUpZone Mobile img  파일 */
	private FileDto mobileImgFile;

	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/

	/** 대라벨내용 */
	private String laLblCn;

	/** 소라벨내용 */
	private String smLblCn;

	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/

	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/


	/*----chart관련 시작-------------------------------------------------------------*/
	/** 제품분류코드 */
	String prdtClfCd;

	/** 제품분류명 */
	String prdtClfNm;

	/** 품목코드 */
	String pdctgCd;

	/** 품목명 */
	String pdctgNm;

	/** 제조고객회사국가명 */
	String mnftrCustcoNtnNm;

	/** count */
	Integer cnt;
	/*----// chart관련 끝-------------------------------------------------------------*/


}

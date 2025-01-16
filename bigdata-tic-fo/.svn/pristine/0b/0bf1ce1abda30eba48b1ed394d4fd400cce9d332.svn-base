package com.katri.web.dataUsageGuide.model;

import java.util.List;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "[데이터 활용안내] > [인증데이터 조회]의 데이터 조회 Response")
public class CertDataSelectRes extends Common {

	/** 번호 */
	private Integer rownum;

	//===================== [pt_pr_cert_bas]관련 데이터
	/** 기관아이디 */
	private String instId;

	/** 기관명(국문) */
	private String instNm;

	/** 기관명(영문) */
	private String instEngNm;

	/** 기관명(영문)_소문자 */
	private String instEngNmLower;

	/** 기관명(국문, 영문) */
	private String instKoEngNm;

	/** 인증번호 */
	private String certNo;

	/** 기관인증식별값 */
	private String instCertIdntfcVal;

	/** 인증구분코드 */
	private String certDivCd;

	/** 인증구분명 */
	private String certDivNm;

	/** 인증상태코드 */
	private String certSttCd;

	/** 인증상태명 */
	private String certSttNm;

	/** 인증일자 */
	private String certYmd;

	/** 인증최종변경일자 */
	private String certLastChgYmd;

	/** 인증변경사유내용 */
	private String certChgResnCn;

	/** 최초인증번호 */
	private String frstCertNo;

	/** 제품명 */
	private String prdtNm;

	/** 브랜드명 */
	private String brdNm;

	/** 모델명 */
	private String mdlNm;

	/** 파생모델명 */
	private String drvnMdlNm;

	/** 법정제품분류코드 */
	private String sttyPrdtClfCd;

	/** 법정제품분류명 */
	private String sttyPrdtClfNm;

	/** 법정제품세분류명 */
	private String sttyPrdtDtclfNm;

	/** 규격명 */
	private String stndNm;

	/** 비고내용 */
	private String rmkCn;

	/** 수입제조구분코드 */
	private String incmMnftrDivCd;

	/** 제조고객회사명 */
	private String mnftrCustcoNm;

	/** 제조고객회사사업자등록번호 */
	private String mnftrCustcoBrno;

	/** 제조고객회사국가명 */
	private String mnftrCustcoNtnNm;

	/** 제조고객회사대표자명 */
	private String mnftrCustcoRprsvNm;

	/** 제조고객회사우편번호 */
	private String mnftrCustcoZip;

	/** 제조고객회사주소 */
	private String mnftrCustcoAddr;

	/** 제조고객회사전화번호 */
	private String mnftrCustcoTelno;

	/** 수입고객회사명 */
	private String incmCustcoNm;

	/** 수입고객회사사업자등록번호 */
	private String incmCustcoBrno;

	/** 수입고객회사대표자명 */
	private String incmCustcoRprsvNm;

	/** 수입고객회사우편번호 */
	private String incmCustcoZip;

	/** 수입고객회사주소 */
	private String incmCustcoAddr;

	/** 수입고객회사전화번호 */
	private String incmCustcoTelno;
	//=======================// [pt_pr_cert_bas]관련 데이터 끝

	//======================= [tb_cert_adit_info_mng]관련 데이터
	/** 추가정보내용 */
	private String certAditInfoCn;
	//=======================// [tb_cert_adit_info_mng]관련 데이터 끝

	//===================== 제품사진 관련 데이터
	/** imgName(파일명) 리스트 */
	private List<String> imgNameList;
	//=======================// 제품사진 관련 데이터 데이터 끝

	//===================== [##인증기관##] select박스를 만들기위한 [pt_co_inst_bas]관련 데이터
	/** [##인증기관##] SELECT박스 LIST_기관아이디 */
	private String lsInstId;

	/** [##인증기관##] SELECT박스 LIST_기관명 */
	private String lsInstNm;
	//=====================// [##인증기관##] select박스를 만들기위한 [pt_co_inst_bas]관련 데이터

	//===================== [##통합공통상세코드##] select박스를 만들기위한 [pt_co_intgr_comn_dtl_cd]관련 데이터
	/** [##통합공통상세코드##] SELECT박스 LIST_디테일코드 */
	private String lsDtlCd;

	/** [##통합공통상세코드##] SELECT박스 LIST_디테일코드명 */
	private String lsDtlCdNm;
	//=====================// [##통합공통상세코드##] select박스를 만들기위한 [pt_co_intgr_comn_dtl_cd]관련 데이터

	//===================== [##CO_법정제품분류##] select박스를 만들기위한 [pt_co_stty_prdt_clf_cd]관련 데이터
	/** [##CO_법정제품분류##] SELECT박스 LIST_법정제품분류코드 */
	private String lsSttyPrdtClfCd;

	/** [##CO_법정제품분류##] SELECT박스 LIST_법정제품세분류명 */
	private String lsSttyPrdtDtclfNm;
	//=====================// [##CO_법정제품분류##] select박스를 만들기위한 [pt_co_stty_prdt_clf_cd]관련 데이터

}

package com.katri.web.cert.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
@ApiModel(description = "인증정보 Response")
public class CertRes {


	@ApiModelProperty(notes = "번호", example = "0", position = 1)
	private String rownum;

	@ApiModelProperty(notes = "인증기관코드", example = "text", position = 1)
	private String instId;

	@ApiModelProperty(notes = "인증기관약칭코드", example = "text", position = 1)
	private String smInstNm;

	@ApiModelProperty(notes = "인증기관명", example = "text", position = 1)
	private String certInstNm;
	@ApiModelProperty(notes = "통합접수번호", example = "RC21-3404", position = 0)
	private String intgrRcptNo;
	@ApiModelProperty(notes = "인증번호", example = "CB014R1619-1001", position = 1)
	private String certNo;
	@ApiModelProperty(notes = "인증구분코드", example = "1312", position = 1)
	private String certDivCd;
	@ApiModelProperty(notes = "인증구분명", example = "제품", position = 1)
	private String certDivNm;
	@ApiModelProperty(notes = "인증일자", example = "2020.01.01", position = 1)
	private String certYmd;
	@ApiModelProperty(notes = "인증상태 코드", example = "text", position = 1)
	private String certSttCd;
	@ApiModelProperty(notes = "인증상태 명", example = "적합", position = 1)
	private String certSttNm;
	@ApiModelProperty(notes = "최초인증번호", example = "", position = 1)
	private String frstCertNo;
	@ApiModelProperty(notes = "인증최종변경일자", example = "", position = 1)
	private String certLastChgYmd;
	@ApiModelProperty(notes = "인증변경사유내용", example = "", position = 1)
	private String certChgResnCn;
	@ApiModelProperty(notes = "제품명", example = "", position = 1)
	private String prdtNm;
	@ApiModelProperty(notes = "브랜드명", example = "", position = 1)
	private String brdNm;
	@ApiModelProperty(notes = "모델명", example = "", position = 1)
	private String mdlNm;
	@ApiModelProperty(notes = "규격", example = "", position = 1)
	private String stndVal;
	@ApiModelProperty(notes = "제품분류 코드(CTG01)", example = "", position = 1)
	private String sttyPrdtClfCd;
	@ApiModelProperty(notes = "제품분류 코드명(CTG01)", example = "", position = 1)
	private String sttyPrdtClfNm;
	@ApiModelProperty(notes = "제품세분류명", example = "", position = 1)
	private String sttyPrdtDtclfNm;
	@ApiModelProperty(notes = "파생모델명", example = "", position = 1)
	private String drvnMdlNm;
	@ApiModelProperty(notes = "비고내용", example = "", position = 1)
	private String rmkCn;
	@ApiModelProperty(notes = "제조사", example = "", position = 1)
	private String mkrNm;
	@ApiModelProperty(notes = "제조사사업자번호", example = "", position = 1)
	private String mkrBrno;
	@ApiModelProperty(notes = "제조사국가명", example = "", position = 1)
	private String mkrNtnNm;
	@ApiModelProperty(notes = "제조사대표자명", example = "", position = 1)
	private String mkrRprsvNm;
	@ApiModelProperty(notes = "제조사주소", example = "", position = 1)
	private String mkrAddr;
	@ApiModelProperty(notes = "제조사전화번호", example = "", position = 1)
	private String mkrTelno;
	@ApiModelProperty(notes = "수입사명", example = "", position = 1)
	private String iptrNm;
	@ApiModelProperty(notes = "수입사사업자번호", example = "", position = 1)
	private String iptrBrno;
	@ApiModelProperty(notes = "수입사전화번호", example = "", position = 1)
	private String iptrTelno;
	@ApiModelProperty(notes = "수입사대표자명", example = "", position = 1)
	private String iptrRprsvNm;
	@ApiModelProperty(notes = "수입사주소", example = "", position = 1)
	private String iptrAddr;

}

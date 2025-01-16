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
@ApiModel(description = "인증정보 Request")

public class CertReq {

	@ApiModelProperty(notes = "페이징번호", example = "0", position = 1)
	private String pageNum;

	@ApiModelProperty(notes = "인증기관", example = "한국의류시험연구원", position = 2)
	private String instId;

	@ApiModelProperty(notes = "인증번호", example = "RC21-3407", position = 3)
	private String certNo;

	@ApiModelProperty(notes = "제조업체명", example = "주식회사 아진실업", position = 4)
	private String mkrNm;

	@ApiModelProperty(notes = "제품명", example = "학용품", position = 5)
	private String prdtNm;

	@ApiModelProperty(notes = "모델명", example = "지능놀이", position = 6)
	private String mdlNm;

	@ApiModelProperty(notes = "수입업체명", example = "수입업체", position = 7)
	private String iptrNm;

}

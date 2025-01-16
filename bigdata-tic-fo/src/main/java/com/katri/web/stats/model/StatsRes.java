package com.katri.web.stats.model;

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
@ApiModel(description = "통계 Request")
public class StatsRes {

	@ApiModelProperty(notes = "번호", example = "0", position = 1)
	private String rownum;

	@ApiModelProperty(notes = "인증기관 코드", example = "", position = 1)
	private String instId;
	@ApiModelProperty(notes = "인증기관 이름", example = "0", position = 1)
	private String certInstNm;
	@ApiModelProperty(notes = "인증구분 코드", example = "", position = 1)
	private String certDivCd;
	@ApiModelProperty(notes = "인증구분 이름", example = "", position = 1)
	private String certDivNm;
	@ApiModelProperty(notes = "총건수", example = "0", position = 1)
	private String allCount;
	@ApiModelProperty(notes = "적합건수", example = "0", position = 1)
	private String confirmCount;
	@ApiModelProperty(notes = "취소건수", example = "0", position = 1)
	private String cancelCount;
	@ApiModelProperty(notes = "기타건수", example = "0", position = 1)
	private String etcCount;

}

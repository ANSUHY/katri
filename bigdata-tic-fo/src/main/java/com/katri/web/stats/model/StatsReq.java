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
public class StatsReq {

	@ApiModelProperty(notes = "인증기관 코드", example = "", position = 1)
	private String instId;
	@ApiModelProperty(notes = "검색 시작일자", example = "", position = 1)
	private String startDate;
	@ApiModelProperty(notes = "검색 종료일자", example = "", position = 1)
	private String endDate;

	@ApiModelProperty(notes = "페이지의 시작되는 카운트 ", example = "", position = 1)
	private int startCount;
	@ApiModelProperty(notes = "페이지 제한 수", example = "", position = 1)
	private int limitCount;



}

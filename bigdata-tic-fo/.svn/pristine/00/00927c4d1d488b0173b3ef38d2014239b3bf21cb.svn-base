package com.katri.web.dbr.model;

import com.katri.web.comm.model.CommReq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel(description = "대시보드 Request")
public class DbrReq extends CommReq {
	@ApiModelProperty(notes = "요청타입", example = "ALL", position = 1)
	private String callType;

	@ApiModelProperty(notes = "조회 날짜", example = "YYYYMMDD", position = 2)
	private String selectDate;

	@ApiModelProperty(notes = "기관 코드", example = "YYYYMMDD", position = 3)
	private String instId;

	@ApiModelProperty(notes = "공통 코드", example = "YYYYMMDD", position = 3)
	private String upCd;

}

package com.katri.web.comm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

@ApiModel(description = "공통 Request")
public class CommReq {

	@ApiModelProperty(notes = "UP_CD", example = "test")
	private String upCd;
	
	@ApiModelProperty(notes = "코드 값", example = "test")
	private String comnCd;
}

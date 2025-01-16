package com.katri.web.comm.model;

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
@ApiModel(description = "공통 Response")
public class CommRes {

	@ApiModelProperty(notes = "코드 이름", example = "test")
	private String comnCdNm;
	
	@ApiModelProperty(notes = "코드 값", example = "test")
	private String comnCd;
	
	@ApiModelProperty(notes = "관계코드 값", example = "test")
	private String relCd1;
}

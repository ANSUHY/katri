package com.katri.web.comm.model;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "메일 body만들때 쓰는 DTO")
public class MailMakeBodyDto {

	/** 메일 template 공통코드 ( comn_grp_cd : 'EMT') */
	private String mailTmepCd;

	/** 메일 param (메일내용에 들어갈 param들) */
	private Map<String, Object> mailParam;


}
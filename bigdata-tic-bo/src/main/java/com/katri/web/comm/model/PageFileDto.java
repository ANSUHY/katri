package com.katri.web.comm.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "파일객체_DB에 없는 파일객체")
public class PageFileDto {

	/** 파일 저장된 이름 */
	private String strgFileNm;

	/** 파일 다운로드시 이름 */
	private String orgnlFileNm;

}
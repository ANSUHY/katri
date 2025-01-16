package com.katri.batch.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "배치실행이력 Request")
public class BtchExcnHistSaveReq {

	/** 배치아이디 */
	private String btchId;

	/** 배치결과코드(S:성공, F:실패) */
	private String btchRsltCd;

	/** 배치결과내용 */
	private String btchRsltCn;


}
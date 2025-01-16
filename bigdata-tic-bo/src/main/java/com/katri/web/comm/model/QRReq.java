package com.katri.web.comm.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "QR객체")
public class QRReq {

	/** QR URL (QR에서 가르키고 있는 URL)*/
	private String qrUrl;

	/** QR URL_UTF-8인코딩 (QR에서 가르키고 있는 URL_UTF-8인코딩)*/
	private String qrEncodeUrl;

	/** QR 파일명 */
	private String qrFileNm;

	/** QR 참조구분값*/
	private String qrRefDivVal;

}
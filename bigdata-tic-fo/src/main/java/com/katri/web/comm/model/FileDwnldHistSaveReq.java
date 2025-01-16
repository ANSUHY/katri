package com.katri.web.comm.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "파일다운로드이력 Request")
public class FileDwnldHistSaveReq {

	/** 파일일련번호 */
	private Integer fileSn;

	/** 파일명 */
	private String fileNm;

	/** 장비접속아이디 */
	private String eqmtCntnId;

	/** 세션접속아이디 */
	private String sesionCntnId;

	/** 사용자IP주소 */
	private String userIpAddr;

	/** 생성자아이디 */
	private String crtrId;

}
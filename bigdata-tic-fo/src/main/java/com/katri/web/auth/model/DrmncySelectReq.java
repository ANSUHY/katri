package com.katri.web.auth.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "휴면 계정 Request")
public class DrmncySelectReq {

	/** 인증 번호 */
	private String certNo;

	/** 사용자 아이디 */
	private String userId;

	/** 이메일 주소 */
	private String emlAddrVal;

}

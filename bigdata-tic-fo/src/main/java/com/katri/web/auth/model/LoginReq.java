package com.katri.web.auth.model;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Login Request")
public class LoginReq {

	/** 로그인시 입력한 ID */
	@NotEmpty(message = "result-message.messages.login.message.required.loginid.data") //'아이디를 입력해 주세요.'
	private String loginId;

	/** 로그인시 입력한 PWD */
	@NotEmpty(message = "result-message.messages.login.message.required.loginpwd.data") //'비밀번호를 입력해 주세요.'
	private String loginPwd;

	/** 최종로그인IP주소 */
	private String lastLgnIpAddr;

	/** 로그인참조값 */
	private String lgnRefVal;

}

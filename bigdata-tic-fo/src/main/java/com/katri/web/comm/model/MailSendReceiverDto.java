package com.katri.web.comm.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "메일 전송 수신자 객체")
public class MailSendReceiverDto {

	/** 이메일 */
	private String email;


	/////////////////////////////////////////////////

	//생성자
	public MailSendReceiverDto() {}

	//생성자2
	public MailSendReceiverDto( String email) {
		this.email = email;
	}

}
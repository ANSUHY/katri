package com.katri.web.comm.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "메일 전송 객체")
public class MailSendDto {

	/** [필수값] 메일의 제목(치환문자사용가능) <br/>
		EX) "[회원가입] [$NAME]님 환영합니다. "
	*/
	private String subject;

	/** [필수값] 메일의 본문(치환문자사용가능) <br/>
		EX) "[$NAME]님 환영합니다. 치환 문자 입니다. 수신 이메일 : [$EMAIL] 수신번호 : [$MOBILE] 비고1 : [$NOTE1] 비고2 : [$NOTE2] 비고3 : [$NOTE3] 비고4 : [$NOTE4] 비고5 : [$NOTE5]"
	*/
	private String body;

	/** [필수값] 받는 이의E-mail List */
	private List<MailSendReceiverDto> receiverList;

	/** 예약 메일 여부 */
	private String reserveYn;

	/** 예약 메일일 경우 보낼 시간 <br/>
		EX) 2022-10-12 15:01:00
	*/
	private String reserveTime;

}
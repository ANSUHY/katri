package com.katri.batch.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "휴면회원 전환 30일전 알림 메일 DTO")
public class BatchBeforeDrmncyMemDto {

	/** 결과 : 사용자아이디 */
	private String userId;

	/** 결과 : 암호화이메일주소값 */
	private String encptEmlAddrVal;

	/** 결과 : 휴먼 예정일 */
	private String eDrmncyDay;

//	/** 암호푼(decode)이메일주소값*/
//	private String decptEmlAddrVal;

}
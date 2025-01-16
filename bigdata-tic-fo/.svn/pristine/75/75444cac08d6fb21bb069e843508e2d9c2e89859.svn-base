package com.katri.web.svc.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "[QR랜딩페이지]의 데이터 조회 Request")
public class SvcSelectReq extends Common {

	/** TAGET 기관아이디 */
	private String targetInstId;

	/** TAGET 인증번호 */
	private String targetCertNo;

	/** [미리보기] 시 받은 내용 */
	private String previewMenuCptnCn;

	//===================== 제품사진 display 관련 데이터
	/** 기관명(영문)_소문자 */
	private String instEngNmLower;

	/** 인증번호 */
	private String certNo;

	/** imgName(파일명) */
	private String imgName;
	//=======================// 제품사진 display 관련 데이터 데이터 끝

}

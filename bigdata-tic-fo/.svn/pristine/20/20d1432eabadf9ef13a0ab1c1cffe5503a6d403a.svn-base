package com.katri.web.mypage.inquiry.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Inquiry 조회 Response")
public class InquirySelectRes extends Common{

	/** 게시물 일련 번호 */
	private Integer nttSn;
	/** 게시물 유형 코드 */
	private String nttTyCd;
	/** 게시물 분류 코드 */
	private String nttClfCd;
	/** 게시물 제목 */
	private String nttSjNm;
	/** 게시물 내용 */
	private String nttCn;
	/** 생성 일시 */
	private String crtDt;



	/** ------- 답변 ------------ */
	/** 답변 여부 */
	private String nttAnsYn;
	/** 답변 내용 */
	private String nttAnsCn;
	/** 답변 생성자 아이디 */
	private String nttAnsCrtrId;
	/** 답변 생성 일시 */
	private String nttAnsCrtDt;
	/** 삭제 여부 */
	private String delYn;

	/**----- 문의 유형 ----------- */
	private String comnCd;
	private String comnCdNm;
	private String comnCdDescCn;
	private String comnGrpCd;

	/**/
	private String userTyCd;
}


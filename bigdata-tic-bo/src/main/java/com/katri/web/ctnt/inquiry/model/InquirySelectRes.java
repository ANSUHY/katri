package com.katri.web.ctnt.inquiry.model;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "1:1 문의 조회 Res")
public class InquirySelectRes {

	/** 게시물 일련 번호 */
	private int nttSn;
	/** 게시물 유형 코드 */
	private String nttTyCd;
	/** 게시물 분류 코드 */
	private String nttClfCd;
	/** 게시물 제목명 */
	private String nttSjNm;
	/** 게시물 내용 */
	private String nttCn;
	/** 작성자 */
	private String crtrId;
	/** 생성 일시 */
	private String crtDt;
	/** 수정 일시 */
	private String mdfcnDt;
	/** 수정자 아이디 */
	private String mdfrId;

	/** 답변 여부 */
	private String nttAnsYn;
	/** 답변 내용 */
	private String nttAnsCn;
	/** 답변 생성 일시 */
	private String nttAnsCrtDt;
	/** 답변 생성자 아이디 */
	private String nttAnsCrtrId;
	/** 삭제 여부 */
	private String delYn;

	/** 로우 넘버 */
	private int rownum;
	/** 공통 코드명 */
	private String comnCdNm;
	/** 검색 조건 (셀렉트 박스) */
	private String searchType;

	/** 공통 코드 설명 내용 */
	private String comnCdDescCn;
}

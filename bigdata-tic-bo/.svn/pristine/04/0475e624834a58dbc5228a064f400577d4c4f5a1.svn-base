package com.katri.web.search.keyword.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Keyword 관리 조회 Request")
public class KeywordSelectReq extends Common{

	/** 검색어 일련번호 */
	private int srwrdSn;
	/** 검색어 유형 코드 */
	private String srwrdTyCd;
	/** 검색어명 */
	private String srwrdNm;
	/** 사용 여부 */
	private String useYn;
	/** 생성 일시 */
	private String crtDt;
	/** 생성자 아이디 */
	private String crtrId;
	/** 수정 일시 */
	private String mdfcnDt;
	/** 수정자 아이디 */
	private String mdfrId;

	/** 카운트 (사용중인 추천 검색어 개수 조회용) */
	private int count;
}

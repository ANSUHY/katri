package com.katri.web.trms.trms.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Trms 조회 Response")
public class TrmsSelectRes extends Common {

	/** 번호 */
	private Integer rownum;

	/** 약관일련번호 */
	private Integer trmsSn;

	/** 약관유형코드 */
	private String trmsTyCd;

	/** 약관유형 NM */
	private String trmsTyNm;

	/** 약관내용 */
	private String trmsCn;

	/** 사용여부 */
	private String useYn;

	/** 생성일시 */
	private String crtDt;

	/** 생성자아이디 */
	private String crtrId;

	/** 수정일시 */
	private String mdfcnDt;

	/** 수정자아이디 */
	private String mdfrId;

	/** 약관내용_xss복구 */
	private String unecapeTrmsCn;

}

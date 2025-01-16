package com.katri.web.system.admin.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "권한 조회 response")
@ToString
public class AuthorSelectRes extends Common {

	/** 권한그룹일련번호 */
	private int authrt_grp_sn;
	/** 권한그룹명 */
	private String authrt_grp_nm;
	/** 사이트유형코드 */
	private String site_ty_cd;
	/** 사용 여부 */
	private String use_yn;
	/** 생성 일시 */
	private String crt_dt;
	/** 생성자아이디 */
	private String crtr_id;
	/** 수정 일시 */
	private String mdfcn_dt;
	/** 수정자 아이디 */
	private String mdfr_id;
}

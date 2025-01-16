package com.katri.web.system.admin.model;

import java.util.List;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Admin 등록 request")
@ToString
public class AdminSaveReq extends Common {

	/** 관리자 아이디 */
	private String mngr_id;
	/** 관리자명 */
	private String mngr_nm;
	/** 관리자 비밀번호 */
	private String mngr_pwd;
	/** 사용 여부 */
	private String use_yn;
	/** 생성자 아이디 */
	private String ctrt_id;
	/** 권한 */
	private int auth_grp_sn;
	/** 생성자 아이디 */
	private String crtr_id;
	/** 수정 일시 */
	private String mdfcn_dt;
	/** 수정자 아이디 */
	private String mdfr_id;

}

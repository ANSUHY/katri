package com.katri.web.system.admin.model;

import java.util.List;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Admin 관리 조회 Response")
public class AdminSelectRes extends Common {

	private String mngrId;
	private String mngrNm;
	private int authrtGrpSn;
	private String lastLoginDt;
	private char useYn;
	private String crtDt;
	private String crtrId;
	private String mdfcnDt;
	private String mdfrId;
	private int rowNum; //로우 넘버

	List<AuthorSelectRes> authorList; //권한 목록

}

package com.katri.web.search.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "Search Request")
public class SearchSelectReq extends Common {

	/** 검색어 */
	private String unifiedSearchKeyword;

	/** 검색 사용자 아이디 */
	private String unifiedSearchUserId;

}

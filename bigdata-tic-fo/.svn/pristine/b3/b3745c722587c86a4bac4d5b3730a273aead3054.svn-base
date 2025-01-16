package com.katri.web.platformInfo.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "[플랫폼 소개]의 데이터 조회 Request")
public class PlatformInfoSelectReq extends Common {

	//====== 검색 조건 시작
	/** 검색 메뉴구성코드 */
	private String searchMenuCptnCd;
	//======// 검색 조건 끝

	/** [미리보기] 시 받은 내용 */
	private String previewMenuCptnCn;


	/** [참여기관 소개] 시 받은 파라미터 _ 이걸로 페이지 결정*/
	private String institution;


}

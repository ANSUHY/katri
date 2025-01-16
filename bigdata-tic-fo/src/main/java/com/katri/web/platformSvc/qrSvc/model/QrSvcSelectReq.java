package com.katri.web.platformSvc.qrSvc.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "[플랫폼 서비스] > [통합QR 서비스 안내]의 데이터 조회 Request")
public class QrSvcSelectReq extends Common {

	//====== 검색 조건 시작
	/** 검색 메뉴구성코드 */
	private String searchMenuCptnCd;
	//======// 검색 조건 끝

	/** [미리보기] 시 받은 내용 */
	private String previewMenuCptnCn;

}

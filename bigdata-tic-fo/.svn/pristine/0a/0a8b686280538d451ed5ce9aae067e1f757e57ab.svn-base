package com.katri.web.platformSvc.qrSvc.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "[플랫폼 서비스] > [통합QR 서비스 안내]의 데이터 조회 Response")
public class QrSvcSelectRes extends Common {

	//====== [tb_menu_cptn_mng]의 데이터
	/** 메뉴구성일련번호 */
	private Integer menuCptnSn;

	/** 메뉴구성코드 */
	private String menuCptnCd;

	/** 메뉴구성명 */
	private String menuCptnNm;

	/** 메뉴구성내용 */
	private String menuCptnCn;
	//======// [tb_menu_cptn_mng]의 데이터 끝

	/** 메뉴구성내용__unescape : JAVA에서 셋팅해줌*/
	private String menuCptnCnUnescape;

}

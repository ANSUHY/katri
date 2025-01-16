package com.katri.web.ctnt.menuCptn.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "메뉴구성관리 저장 Request")
public class MenuCptnSaveReq extends Common {

	/** save Type _ 수정:U, 저장:I*/
	private String saveType;

	/** 메뉴구성일련번호 */
	private Integer menuCptnSn;

	/** 메뉴구성코드 */
	private String menuCptnCd;

	/** 메뉴구성명 */
	private String menuCptnNm;

	/** 메뉴구성내용 */
	private String menuCptnCn;

	/** 생성자아이디 */
	private String crtrId;

	/** 수정자아이디 */
	private String mdfrId;

	/** [사용] 으로 change할 menuCptnSn */
	private Integer chgUseYMenuCptnSn;

	/** [사용] 으로 change할 menuCptnCd */
	private String chgUseYMenuCptnCd;

	/** [사용] 으로 change할때 사용 여부 */
	private String chgUseYn;

}

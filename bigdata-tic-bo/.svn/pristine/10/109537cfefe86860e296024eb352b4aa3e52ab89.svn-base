package com.katri.web.trms.trms.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Trms 저장 Request")
public class TrmsSaveReq extends Common {

	/** save Type _ 수정:U, 저장:I*/
	private String saveType;

	/** 약관일련번호 */
	private Integer trmsSn;

	/** 약관유형코드 */
	private String trmsTyCd;

	/** 약관내용 */
	private String trmsCn;

	/** 생성자아이디 */
	private String crtrId;

	/** 수정자아이디 */
	private String mdfrId;

	/** [사용] 으로 change할 trmsSn */
	private Integer chgUseYTrmsSn;

	/** [사용] 으로 change할 trmsType */
	private String chgUseYTrmsType;

	/** [사용] 으로 change할때 사용 여부 */
	private String chgUseYn;

}

package com.katri.web.hmpgCptn.mainVisual.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "메인 비주얼 관리 저장 Request")
public class MainVisualSaveReq extends Common {

	/** 홈페이지구성일련번호 */
	private Integer	hmpgCptnSn;

	/** 홈페이지구성유형코드 */
	@NotEmpty(message = "홈페이지 구성 유형코드가 비어있습니다.")
	private String	hmpgCptnTyCd;

	/** 홈페이지구성설명내용 */
	@NotEmpty(message = "홈페이지 구성 설명내용이 비어있습니다.")
	private String	hmpgCptnDescCn;

	/** 연계URL주소 */
	private String	linkUrlAddr;

	/** 연계유형코드 */
	private String linkTyCd;

	/** 정렬순서 */
	private Integer	srtSeq;

	/** 사용여부 */
	@NotEmpty(message = "사용여부가 비어있습니다.")
	private String	useYn;

	/** 생성일시 */
	private String	crtDt;

	/** 생성자아이디 */
	private String	crtrId;

	/** 수정일시 */
	private String	mdfcnDt;

	/** 수정자아이디 */
	private String	mdfrId;

	/*--------------------------------*/

	/** 암호화 삭제 파일 번호 ARR */
	private List<String> arrDelFileSn;

	/*--------------------------------*/

	/** 정렬 타입 */
	private String seqType;

	/** 정렬 바뀌는 홈페이지 구성 일련 번호 */
	private Integer chgHmpgCptnSn;

	/*--------------------------------*/

	/** 대라벨내용 */
	private String laLblCn;

	/** 소라벨내용 */
	private String smLblCn;

}

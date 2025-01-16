package com.katri.web.dataUsageGuide.model;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "[데이터 활용안내] > [인증데이터 조회]의 데이터 조회 Request")
public class CertDataSelectReq extends Common {

	//===================== 검색 조건 시작
	/** 검색 [검색:인증기관] */
	private String schInstId;

	/** 검색 [검색:인증번호] */
	private String schCertNo;

	/** 검색 [검색:제조업체명] */
	private String schMkrNm;

	/** 검색 [검색:모델명] */
	private String schMdlNm;

	/** 검색 [검색:수입업체명] */
	private String schIptrNm;

	/** 검색 [검색:사진유무] */
	private String schPhotoYN;

	/** 검색 [검색:인증일자_시작일] */
	private String schCertYmdF;

	/** 검색 [검색:인증일자_끝일] */
	private String schCertYmdT;

	/** 검색 [검색:인증상태] */
	private String schSttCd;

	/** 검색 [검색:제품분류] */
	private String schPrdtClfCd;

	/** 검색 [검색:제품명] */
	private String schPrdtNm;
	//=====================// 검색 조건 끝

	/** TAGET 기관아이디 */
	private String targetInstId;

	/** TAGET 인증번호 */
	private String targetCertNo;

	/** [미리보기] 시 받은 내용 */
	private String previewMenuCptnCn;

	//===================== 제품사진 display 관련 데이터
	/** 기관명(영문)_소문자 */
	private String instEngNmLower;

	/** 인증번호 */
	private String certNo;

	/** imgName(파일명) */
	private String imgName;
	//=======================// 제품사진 display 관련 데이터 데이터 끝

}

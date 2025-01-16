package com.katri.web.particiLounge.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "데이터 수집 현황 조회 Response")
public class DataGatherStatusSelectRes {

	/** 기관코드 */
	private String instId;

	/** 기관명 */
	private String instNm;

	/** 기관명 (영문) */
	private String instEngNm;

	/** 상태 */
	private String statusCd;

	/** 접수데이터 */
	private String rcptCnt;

	/** 시험데이터 */
	private String testItemCnt;

	/** 성적데이터 */
	private String rprtCnt;

	/** 인증데이터 */
	private String certCnt;

	/** 인증이미지 */
	private String certImgCnt;

	/** 최근 수집 일자 기준 일주일 */
	private String gatherDt;

	/** 최근 수집 일자 */
	private String lastGatherDt;

	/** 정상 데이터 */
	private String normalData;

	/** 비정상 데이터 */
	private String abnormalData;

	/** 오류메시지 */
	private String errCn;

}

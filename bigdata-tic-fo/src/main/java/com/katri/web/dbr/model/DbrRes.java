package com.katri.web.dbr.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel(description = "대시보드 Response")
public class DbrRes{

	@ApiModelProperty(notes = "수집 기준일", example = "YYYY.MM.DD", position = 1)
	private String referenceDt;
	@ApiModelProperty(notes = "인증기관코드", example = "text", position = 1)
	private String instId;
	@ApiModelProperty(notes = "인증기관명", example = "text", position = 2)
	private String certInstNm;
	@ApiModelProperty(notes = "수집 처리상태", example = "정상", position = 3)
	private String statusCd;
	@ApiModelProperty(notes = "수집 데이터 카운트", example = "0", position = 4)
	private String gatherRecordCnt;
	@ApiModelProperty(notes = "수집 이미지 카운트", example = "0", position = 5)
	private String gatherImageCnt;
	@ApiModelProperty(notes = "수집 성공 카운트", example = "에러 사유", position = 5)
	private String sussCnt;
	@ApiModelProperty(notes = "수집 실패 카운트", example = "에러 사유", position = 5)
	private String failCnt;
	@ApiModelProperty(notes = "에러 로그", example = "에러 사유", position = 5)
	private String errLog;
}

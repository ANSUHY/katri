package com.katri.web.comm.model;

import java.io.File;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "파일객체")
public class FileDto {

	/** 파일일련번호 */
	private Integer fileSn;

	/** 원본파일명 */
	private String orgnlFileNm;

	/** 저장파일명 */
	private String strgFileNm;

	/** 저장파일경로주소 */
	private String strgFilePathAddr;

	/** 파일확장자명 */
	private String fileExtnNm;

	/** 파일크기값 */
	private Long fileSzVal;

	/** 정렬순서 */
	private Integer srtSeq;

	/** 삭제여부 */
	private String delYn;

	/** 파일구분명 */
	private String fileDivNm;

	/** 참조구분값 */
	private String refDivVal;

	/** 연계URL주소 */
	private String linkUrlAddr;

	/** 생성일시 */
	private String crtDt;

	/** 생성자아이디 */
	private String crtrId;

	/** 수정일시 */
	private String mdfcnDt;

	/** 수정자아이디 */
	private String mdfrId;

	/** 암호화 파일일련번호 */
	private String encodeFileSn;

	/** 접미사 */
	private String suffix;

	/** 파일 정보 */
	private File file;

}
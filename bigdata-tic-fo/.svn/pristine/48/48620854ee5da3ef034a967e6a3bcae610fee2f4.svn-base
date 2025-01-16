package com.katri.web.ctnt.notice.model;

import java.util.List;

import com.katri.common.model.Common;
import com.katri.web.comm.model.FileDto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "공지사항 조회 Response")
public class NoticeSelectRes extends Common {

	/** 게시글 순번 */
	private Integer rownum;

	/** 게시물 일련 번호 */
	private Integer nttSn;

	/** 게시물 제목 */
	private String nttSjNm;

	/** 게시물 내용 */
	private String nttCn;

	/** 게시물 분류 코드 */
	private String nttClfCd;

	/** 게시글 분류 값 */
	private String comnCdNm;

	/** 생성 일시 */
	private String crtDt;

	/** 게시글 작성자 */
	private String mngrNm;

	/** 업로드 파일 목록 */
	List<FileDto> fileDtoList;

	/** 업로드 파일 개수 */
	private Integer fileDtoCnt;

	/** 게시글 변경 페이지 */
	private int chgPage;


}

package com.katri.web.board.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.katri.common.model.Common;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description = "Board 저장 Request")
public class BoardSaveReq extends Common {

	//------ 검색 조건 시작
	/** 검색 BOARD TYPE */
	private String searchBoardType;

	/** 검색 BOARD TYPE ARR*/
	private String[] searchArrBoardType;

	//------// 검색 조건 끝

	/** taget 게시글 번호 */
	private Integer targetBoardNo;

	/** 게시글 번호 */
	private Integer boardNo;

	/** 제목 */
	@NotEmpty(message = "값이 비어있으면 안됩니다.")
	private String title;

	/** 타입 코드 */
	private String typeCd;

	/** 내용 */
	private String cont;

	/** 암호화 삭제 파일 번호 ARR */
	private List<String> arrdelEncodeFileSn;


//	@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되야함.")
//	@Positive(message = "bannerNo 값은 0또는 양수여야함.")
//	@NotNull(message = "bannerNo 값이 비어있음.")

}

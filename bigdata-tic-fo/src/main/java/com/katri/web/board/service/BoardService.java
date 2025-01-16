package com.katri.web.board.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.FileConst;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.web.board.mapper.BoardMapper;
import com.katri.web.board.model.BoardSaveReq;
import com.katri.web.board.model.BoardSaveRes;
import com.katri.web.board.model.BoardSelectReq;
import com.katri.web.board.model.BoardSelectRes;
import com.katri.web.comm.model.FileDto;
import com.katri.web.comm.service.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class BoardService {

	/** 파일 Service */
	private final FileService fileService;

	/** board Mapper */
	private final BoardMapper boardMapper;

	/*****************************************************
	 * Board 리스트 개수
	 * @param boardSelectReq
	 * @return Integer Board 리스트 개수
	 * @throws Exception
	*****************************************************/
	public Integer getBoardCnt(BoardSelectReq boardSelectReq) throws Exception{

		// [[0]]. 반환할 정보들
		int boardCtnt = 0;

		// [[1]]. board 테이블 개수 조회[temp_tb_board]
		boardCtnt = boardMapper.selectBoardListCount(boardSelectReq);

		return boardCtnt;
	}

	/*****************************************************
	 * Board 리스트_데이터
	 * @param boardSelectReq
	 * @return List<BoardSelectRes> Board 리스트
	 * @throws Exception
	*****************************************************/
	public List<BoardSelectRes> getBoardList(BoardSelectReq boardSelectReq) throws Exception{

		// [[0]]. 반환할 정보들
		List<BoardSelectRes> boardList = null;

		// [[1]]. board 테이블 조회[temp_tb_board]
		boardList = boardMapper.selectBoardList(boardSelectReq);

		return boardList;
	}

	/*****************************************************
	 * Board 리스트 Map으로 반환_데이터
	 * @param boardSelectReq
	 * @return List<BoardSelectRes> Board 리스트
	 * @throws Exception
	*****************************************************/
	public List<Map<String, Object>> getBoardListMap(BoardSelectReq boardSelectReq) throws Exception{

		// [[0]]. 반환할 정보들
		List<Map<String, Object>> boardList = null;

		// [[1]]. board 테이블 조회[temp_tb_board]
		boardList = boardMapper.selectBoardListMap(boardSelectReq);

		return boardList;
	}

	/*****************************************************
	 * Board 상세_데이터
	 * @param boardNo
	 * @return BoardSelectRes Board 상세
	 * @throws Exception
	*****************************************************/
	public BoardSelectRes getBoardDetail(Integer boardNo) throws Exception {

		// [[0]]. 반환할 정보들
		BoardSelectRes boardDetail = null;

		// [[1]]. board 테이블 조회[temp_tb_board]
		boardDetail = this.boardMapper.selectBoardDetail(boardNo);

		if(boardDetail != null) {

			// [[2]]. 파일 테이블 정보 셋팅

			/* 2-1. 파일 테이블 조회하기위한 값 셋팅*/
			FileDto filedto = new FileDto();
			filedto.setRefDivVal(Integer.toString(boardDetail.getBoardNo()));

			/* 2-2. 파일_img */
			/* 2-2-1. 파일_img 테이블 조회[tb_file_mng] + fileSn 인코드*/
			filedto.setFileDivNm(FileConst.File.FILE_DIV_NM_BOARD_IMG);
			List<FileDto> listFile_img = this.fileService.selectFileList(filedto);

			/* 2-2-2. 파일_img 정보 셋팅*/
			boardDetail.setListFileImg(listFile_img);

			/* 2-3. 파일_file */
			/* 2-3-1. 파일_file 테이블 조회[tb_file_mng] + fileSn 인코드*/
			filedto.setFileDivNm(FileConst.File.FILE_DIV_NM_BOARD_FILE);
			List<FileDto> listFile_file = this.fileService.selectFileList(filedto);

			/* 2-3-2. 파일_file 정보 셋팅*/
			boardDetail.setListFileFile(listFile_file);

		} else {

			// 반환값 셋팅
			throw new CustomMessageException("result-message.messages.common.message.no.data"); //데이터가 없습니다.

		}

		return boardDetail;

	}

	/*****************************************************
	 * Board 등록
	 * @param request
	 * @param BoardSaveReq
	 * @return BoardSaveRes
	 * @throws Exception
	*****************************************************/
	public BoardSaveRes insertBoard(HttpServletRequest request, BoardSaveReq BoardSaveReq) throws Exception {

		String strSavetErrMsgCode = "result-message.messages.common.message.save.error"; //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		// [[0]]. 반환할 정보들 & 변수 지정
		BoardSaveRes boardRes = new BoardSaveRes();
		int saveCount = 0;

		// [[1]]. board 테이블 저장[temp_tb_board]
		saveCount = boardMapper.insertBoard(BoardSaveReq);
		if(! (saveCount > 0 ) || BoardSaveReq.getBoardNo() == null) {
			throw new CustomMessageException(strSavetErrMsgCode); //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// [[2]]. 파일 저장
		/* 2-1. 물리저장 + 파일dto 파일 목록 */
		List<FileDto> listFileDto = fileService.savePhyFileReturnFileList(request, "");

		/* 2-1. 파일테이블 저장[tb_file_mng] */
		if(listFileDto.size() != 0) {
			fileService.saveDBFile(Integer.toString(BoardSaveReq.getBoardNo()), listFileDto);
		}

		// [[3]]. 반환값 셋팅
		boardRes.setBoardNo(BoardSaveReq.getBoardNo());

		return boardRes;
	}

	/*****************************************************
	 * Board 수정
	 * @param BoardSaveReq 게시글 정보
	 * @return 성공 카운트
	 * @throws Exception
	 *****************************************************/
	public BoardSaveRes updateBoard(HttpServletRequest request, BoardSaveReq BoardSaveReq) throws Exception {

		String strUpdateErrMsgCode = "result-message.messages.common.message.update.error"; //수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		// [[0]]. 반환할 정보들 & 변수 지정
		BoardSaveRes boardRes = new BoardSaveRes();
		int saveCount = 0;

		// [[1]]. board 테이블 업데이트[temp_tb_board]
		saveCount = boardMapper.updateBoard(BoardSaveReq);
		if(! (saveCount > 0 ) || BoardSaveReq.getBoardNo() == null) {
			throw new CustomMessageException(strUpdateErrMsgCode); //수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// [[2]]. 파일 저장
		/* 2-0. 파일 삭제 */
		List<String> arrdelEncodeFileSn = BoardSaveReq.getArrdelEncodeFileSn();
		if(arrdelEncodeFileSn != null && arrdelEncodeFileSn.size() != 0) {
			for(String delEcodeFileSn : arrdelEncodeFileSn) {

				//2-0-1. 삭제할 데이터 셋팅
				FileDto delFileDto = new FileDto();
				delFileDto.setEncodeFileSn(delEcodeFileSn);

				//2-0-2. 단일 파일 삭제_물리
				String strReturnMassage =  fileService.deletePhyEncodeFileSn(delFileDto);
				if(! ("FileDeleteSuccess".equals(strReturnMassage))) {
					throw new CustomMessageException("물리 파일 삭제 중 오류");
				}

				//2-0-3. 단일 파일 삭제_논리(DB)
				saveCount = fileService.deleteFileData(delFileDto);
				if(! (saveCount > 0 )) {
					throw new CustomMessageException("논리 파일 삭제 중 오류");
				}

			}
		}

		/* 2-1. 물리저장 + 파일dto 파일 목록 */
		List<FileDto> listFileDto = fileService.savePhyFileReturnFileList(request, "");

		/* 2-1. 파일테이블 저장[tb_file_mng] */
		if(listFileDto.size() != 0) {
			fileService.saveDBFile(Integer.toString(BoardSaveReq.getBoardNo()), listFileDto);
		}

		// [[3]]. 반환값 셋팅
		boardRes.setBoardNo(BoardSaveReq.getBoardNo());

		return boardRes;
	}

	/*****************************************************
	 * Board 삭제
	 * @param BoardSaveReq
	 * @return int 성공 카운트
	 * @throws Exception
	*****************************************************/
	public int deleteBoard(BoardSaveReq BoardSaveReq) throws Exception {

		// [[0]]. 반환할 정보들 & 변수 지정
		int saveCount = 0;

		// [[1]]. board 테이블 DEL_YN 업데이트[temp_tb_board]
		saveCount = boardMapper.updateDeleteBoard(BoardSaveReq);
		if(! (saveCount > 0 )) {
			throw new CustomMessageException("result-message.messages.common.message.delete.error"); //삭제 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// [[2]]. 파일 삭제
		/* 2-1. 파일_img */
		/* 2-1-1. 파일 img 정보 조회*/
		FileDto filedto_img = new FileDto();
		filedto_img.setRefDivVal(Integer.toString(BoardSaveReq.getBoardNo()));
		filedto_img.setFileDivNm(FileConst.File.FILE_DIV_NM_BOARD_IMG);
		List<FileDto> listFile_img = this.fileService.selectFileList(filedto_img);

		/* 2-1-2. 파일 img 삭제 */
		if(listFile_img != null && listFile_img.size() != 0) {
			for(FileDto delFileDto : listFile_img) {

				//2-1-2-1. 단일 파일 삭제_물리
				String strReturnMassage =  fileService.deletePhyFile(delFileDto);
				if(! ("FileDeleteSuccess".equals(strReturnMassage))) {
					throw new CustomMessageException("물리 파일 삭제 중 오류");
				}

				//2-1-2-2. 단일 파일 삭제_논리(DB)
				saveCount = fileService.deleteFileData(delFileDto);
				if(! (saveCount > 0 )) {
					throw new CustomMessageException("논리 파일 삭제 중 오류");
				}

			}
		}

		/* 2-2. 파일_file */
		/* 2-2-1. 파일 file 정보 조회*/
		FileDto filedto_file = new FileDto();
		filedto_file.setRefDivVal(Integer.toString(BoardSaveReq.getBoardNo()));
		filedto_file.setFileDivNm(FileConst.File.FILE_DIV_NM_BOARD_FILE);
		List<FileDto> listFile_file = this.fileService.selectFileList(filedto_file);

		/* 2-2-2. 파일_file 삭제 */
		if(listFile_file != null && listFile_file.size() != 0) {
			for(FileDto delFileDto : listFile_file) {

				//2-2-2-1. 단일 파일 삭제_물리
				String strReturnMassage =  fileService.deletePhyFile(delFileDto);
				if(! ("FileDeleteSuccess".equals(strReturnMassage))) {
					throw new CustomMessageException("물리 파일 삭제 중 오류");
				}

				//2-2-2-2. 단일 파일 삭제_논리(DB)
				saveCount = fileService.deleteFileData(delFileDto);
				if(! (saveCount > 0 )) {
					throw new CustomMessageException("논리 파일 삭제 중 오류");
				}

			}
		}


		return saveCount;
	}

	/*****************************************************
	 * Board 차트_리스트_데이터
	 * @return List<BoardSelectRes> Board 차트 데이터 list
	 * @throws Exception
	*****************************************************/
	public List<BoardSelectRes> getBoardChart() throws Exception {

		// 0. 게시판 차트 정보 리스트 조회
		List<BoardSelectRes> boardChartList = null;

		boardChartList = boardMapper.selectBoardChartList();

		return boardChartList;
	}

}

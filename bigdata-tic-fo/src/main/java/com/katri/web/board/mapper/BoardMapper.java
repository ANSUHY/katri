package com.katri.web.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.board.model.BoardSaveReq;
import com.katri.web.board.model.BoardSelectReq;
import com.katri.web.board.model.BoardSelectRes;


@Repository
@Mapper
@MainMapperAnnotation
public interface BoardMapper {

	/*****************************************************
	 * board 리스트 개수 조회
	 * @param boardSelectReq
	 * @return 리스트개수
	 *****************************************************/
	int selectBoardListCount(BoardSelectReq boardSelectReq);

	/*****************************************************
	 * board 리스트 조회
	 * @param boardSelectReq
	 * @return board 리스트 조회
	 *****************************************************/
	List<BoardSelectRes> selectBoardList(BoardSelectReq boardSelectReq);

	/*****************************************************
	 * board 리스트 조회_Map으로 반환
	 * @param boardSelectReq
	 * @return board 리스트 조회_Map으로 반환
	 *****************************************************/
	List<Map<String, Object>> selectBoardListMap(BoardSelectReq boardSelectReq);

	/*****************************************************
	 * board Detail 조회
	 * @param board_no
	 * @return boardDetail 조회
	 *****************************************************/
	BoardSelectRes selectBoardDetail(Integer board_no);

	/*****************************************************
	 * board 입력
	 * @param BoardSaveReq 게시글 등록 정보
	 * @return 성공 카운트
	 *****************************************************/
	int insertBoard(BoardSaveReq boardReq);

	/*****************************************************
	 * board 수정
	 * @param BoardSaveReq 게시글 등록 정보
	 * @return 성공 카운트
	 *****************************************************/
	int updateBoard(BoardSaveReq boardReq);

	/*****************************************************
	 * board del_yn = 'Y' 로 수정
	 * @param BoardSaveReq 게시글 등록 정보
	 * @return 성공 카운트
	 *****************************************************/
	int updateDeleteBoard(BoardSaveReq boardReq);

	/*****************************************************
	 * board 차트 정보 리스트 조회
	 * @return board 차트 정보 리스트 조회
	 *****************************************************/
	List<BoardSelectRes> selectBoardChartList();


}

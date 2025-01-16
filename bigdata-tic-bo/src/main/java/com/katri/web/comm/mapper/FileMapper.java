package com.katri.web.comm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.comm.model.FileDto;
import com.katri.web.comm.model.FileDwnldHistSaveReq;



/***************************************************
 * <ul>
 * <li>업무 그룹명 : 파일 관리 업무</li>
 * <li>서브 업무명 : 공통 관리</li>
 * <li>설         명 : 파일 업로드 및 파일 다운로드 등 파일 관련 기능 제공</li>
 * <li>작   성   자 : Why T</li>
 * <li>작   성   일 : 2021. 03. 23.</li>
 * </ul>
 * <pre>
 * ======================================
 * 변경자/변경일 :
 * 변경사유/내역 :
 * --------------------------------------
 * 변경자/변경일 :
 * 변경사유/내역 :
 * ======================================
 * </pre>
 ***************************************************/
@Repository
@Mapper
@MainMapperAnnotation
public interface FileMapper {


	/*****************************************************
	 * 파일 목록 조회
	 * @param fileDto 조회 정보
	 * @return List<FileDto> 파일 목록 정보
	 *****************************************************/
	List<FileDto> selectFileList(FileDto fileDto);

	/*****************************************************
	 * 파일 상세 조회
	 * @param fileDto 조회 정보
	 * @return FileDto 파일 목록 정보
	 *****************************************************/
	FileDto selectFile(FileDto fileDto);

	/*****************************************************
	 * 파일 정보 등록
	 * @param fileDto 파일 등록 정보
	 * @return Integer 등록 결과
	 *****************************************************/
	Integer insertFile(FileDto fileDto);

	/*****************************************************
	 * 파일 정보 삭제
	 * @param fileDto 파일 삭제 정보
	 * @return Integer 삭제 결과
	 *****************************************************/
	Integer updateDeleteFile(FileDto fileDto);

	/*****************************************************
	 * 업로드 파일 개수
	 * @param fileDto
	 * @return 게시글의 업로드 파일 개수
	*****************************************************/
	int selectFileCnt(FileDto fileDto);

	/*****************************************************
	 * 순번 변경: 파일 등록/삭제 시
	 * @param file
	 * @return 성공 카운트
	*****************************************************/
	int chgFileSrtSeq(FileDto file);

	/*****************************************************
	 * 파일다운로드이력 입력
	 * @param fileDwnldHistSaveReq 파일다운로드이력 정보
	 * @return 성공 카운트
	 *****************************************************/
	int insertFileDwnldHist(FileDwnldHistSaveReq fileDwnldHistSaveReq);

}

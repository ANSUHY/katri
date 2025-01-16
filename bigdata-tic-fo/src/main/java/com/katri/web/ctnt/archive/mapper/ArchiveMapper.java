package com.katri.web.ctnt.archive.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.ctnt.archive.model.ArchiveSaveReq;
import com.katri.web.ctnt.archive.model.ArchiveSelectReq;
import com.katri.web.ctnt.archive.model.ArchiveSelectRes;

@Mapper
@Repository
@MainMapperAnnotation
public interface ArchiveMapper {

	/*****************************************************
	 * 자료실 게시글 개수
	 * @param archiveSelectReq
	 * @return 총 게시글 수
	*****************************************************/
	int selectArchiveCnt(ArchiveSelectReq archiveSelectReq);

	/*****************************************************
	 * 자료실 목록 조회
	 * @param archiveSelectReq
	 * @return List<ArchiveSelectRes>
	*****************************************************/
	List<ArchiveSelectRes> selectArchiveList(ArchiveSelectReq archiveSelectReq);

	/*****************************************************
	 * 자료실 상세 조회
	 * @param archiveSelectReq
	 * @return ArchiveSelectRes
	*****************************************************/
	ArchiveSelectRes selectArchiveDetail(ArchiveSelectReq archiveSelectReq);

	/*****************************************************
	 * 자료실 조회수 증가
	 * @param archiveSaveReq
	 * @return 성공 카운트
	*****************************************************/
	int hitArchive(ArchiveSaveReq archiveSaveReq);

}

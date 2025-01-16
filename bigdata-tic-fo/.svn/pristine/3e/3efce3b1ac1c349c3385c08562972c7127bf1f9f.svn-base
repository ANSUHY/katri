package com.katri.web.ctnt.archive.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.web.comm.model.FileDto;
import com.katri.web.comm.service.FileService;
import com.katri.web.ctnt.archive.mapper.ArchiveMapper;
import com.katri.web.ctnt.archive.model.ArchiveSaveReq;
import com.katri.web.ctnt.archive.model.ArchiveSelectReq;
import com.katri.web.ctnt.archive.model.ArchiveSelectRes;
import com.nhncorp.lucy.security.xss.XssPreventer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = { Exception.class })
public class ArchiveService {

	private final FileService fileService;

	private final ArchiveMapper archiveMapper;

	/*****************************************************
	 * 자료실 게시글 개수
	 * @param archiveSelectReq
	 * @return 총 게시글 수
	*****************************************************/
	public int getArchiveCnt(ArchiveSelectReq archiveSelectReq) {
		// [[0]]. 반환할 정보들
		int archiveCnt = 0;
		// [[1]]. tb_ntt_mng 테이블 조회
		archiveCnt = archiveMapper.selectArchiveCnt(archiveSelectReq);

		return archiveCnt;
	}

	/*****************************************************
	 * 자료실 목록 조회
	 * @param archiveSelectReq
	 * @return List<ArchiveSelectRes>
	 * @throws Exception
	*****************************************************/
	public List<ArchiveSelectRes> getArchiveList(ArchiveSelectReq archiveSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		List<ArchiveSelectRes> archiveList = null;
		// [[1]]. tb_ntt_mng 테이블 조회
		archiveList = archiveMapper.selectArchiveList(archiveSelectReq);

		if (archiveList == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return archiveList;
	}

	/*****************************************************
	 * 자료실 상세 조회
	 * @param archiveSelectReq
	 * @return ArchiveSelectRes
	 * @throws Exception
	*****************************************************/
	public ArchiveSelectRes getArchiveDetail(ArchiveSelectReq archiveSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		ArchiveSelectRes archiveSelectRes = new ArchiveSelectRes();
		FileDto fileDto = new FileDto();
		List<FileDto> fileDtoList = null;
		int fileCnt = 0;
		int uCount = 0;

		// [[1]]. tb_ntt_mng 데이터 조회
		archiveSelectRes = archiveMapper.selectArchiveDetail(archiveSelectReq);

		if (archiveSelectRes != null) {
			// [[2]]. 해당 공지사항의 파일 개수
			// 게시글의 일련 번호를 파일의 참조 구분 값으로 설정
			fileDto.setRefDivVal(Integer.toString(archiveSelectReq.getNttSn()));

			// 공지사항의 파일 개수 조회
			fileCnt = fileService.getFileCnt(fileDto);

			// [[3]]. 해당 공지사항의 파일 목록 조회
			// 파일 목록 조회
			fileDtoList = fileService.selectFileList(fileDto);

			// 조회한 파일 개수, 목록 세팅
			archiveSelectRes.setFileDtoCnt(fileCnt);
			archiveSelectRes.setFileDtoList(fileDtoList);
		} else {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		ArchiveSaveReq archiveSaveReq  = new ArchiveSaveReq();
		archiveSaveReq.setNttSn(archiveSelectReq.getNttSn()); // 게시글 번호
		archiveSaveReq.setNttTyCd(archiveSelectReq.getNttTyCd()); // 게시글 유형

		// 조회수 증가
		uCount = archiveMapper.hitArchive(archiveSaveReq);

		if (!(uCount > 0)) {
			throw new CustomMessageException("result-message.messages.common.message.error"); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// xss 처리
		archiveSelectRes.setNttCn(XssPreventer.unescape(archiveSelectRes.getNttCn()));

		return archiveSelectRes;
	}

}

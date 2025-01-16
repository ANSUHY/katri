package com.katri.web.ctnt.archive.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.model.FileDto;
import com.katri.web.comm.service.FileService;
import com.katri.web.ctnt.archive.mapper.ArchiveMapper;
import com.katri.web.ctnt.archive.model.ArchiveSaveReq;
import com.katri.web.ctnt.archive.model.ArchiveSaveRes;
import com.katri.web.ctnt.archive.model.ArchiveSelectReq;
import com.katri.web.ctnt.archive.model.ArchiveSelectRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class ArchiveService {

	private final FileService fileService;

	private final ArchiveMapper archiveMapper;

	/*****************************************************
	 * 자료실 게시글 개수
	 * @param archiveSelectReq
	 * @return 총 개시글 수
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
	 * 자료실 글 작성
	 * @param request
	 * @param archiveSaveReq
	 * @return ArchiveSaveRes
	 * @throws Exception
	*****************************************************/
	public ArchiveSaveRes insertArchive(HttpServletRequest request, ArchiveSaveReq archiveSaveReq) throws Exception {
		// 접속 사용자
		archiveSaveReq.setCrtrId(SessionUtil.getLoginMngrId());

		// [[0]]. 반환할 정보들
		int result = 0;
		ArchiveSaveRes archiveSaveRes = new ArchiveSaveRes();

		// [[1]]. tb_ntt_mng 데이터 추가
		result = archiveMapper.insertArchive(archiveSaveReq);

		if (!(result > 0)) {
			throw new CustomMessageException("result-message.messages.common.message.insert.error"); // 작성 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// [[2]]. 파일 저장
		// 물리 파일 저장
		List<FileDto> listFileDto = fileService.savePhyFileReturnFileList(request, "");

		// 파일 3개 업로드 초과 방지
		if (!(listFileDto.size() >= 0 && listFileDto.size() <= 3)) {
			throw new CustomMessageException("result-message.messages.common.message.file.exists.error"); // 파일이 정상적으로 업로드가 되지 않아 에러가 발생하였습니다.
		}

		// 논리 파일 저장
		fileService.saveDBFile(Integer.toString(archiveSaveReq.getNttSn()), listFileDto);

		// [[3]]. 반환값 셋팅
		archiveSaveRes.setNttSn(archiveSaveReq.getNttSn());

		return archiveSaveRes;

	}

	/*****************************************************
	 * 자료실 글 수정
	 * @param request
	 * @param archiveSaveReq
	 * @return ArchiveSaveRes
	 * @throws Exception
	*****************************************************/
	public ArchiveSaveRes updateArchive(HttpServletRequest request, ArchiveSaveReq archiveSaveReq) throws Exception {
		// 접속 사용자
		archiveSaveReq.setMdfrId(SessionUtil.getLoginMngrId());

		// [[0]]. 반환할 정보들
		int result = 0;
		int fileCnt = 0;
		ArchiveSaveRes archiveSaveRes = new ArchiveSaveRes();

		// [[1]]. tb_ntt_mng 데이터 수정
		result = archiveMapper.updateArchive(archiveSaveReq);

		if (!(result > 0)) {
			throw new CustomMessageException("result-message.messages.common.message.update.error"); // 수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// [[2]]. 파일 저장

		// 2-1. 데이터 세팅
		FileDto fileDto = new FileDto();
		fileDto.setRefDivVal(Integer.toString(archiveSaveReq.getNttSn()));

		// 2-2. 기존 업로드 파일 개수 확인
		fileCnt = fileService.getFileCnt(fileDto);

		// 2-3. 물리 파일 저장
		List<FileDto> listFileDto = fileService.savePhyFileReturnFileList(request, "");

		// 2-4. 파일 3개 업로드 초과 방지
		if (!(listFileDto.size() >= 0 && listFileDto.size() <= 3)) {
			throw new CustomMessageException("result-message.messages.common.message.file.exists.error"); // 파일이 정상적으로 업로드가 되지 않아 에러가 발생하였습니다.
		}

		// 2-5. 논리 파일 저장
		fileService.saveDBFile(Integer.toString(archiveSaveReq.getNttSn()), listFileDto);

		// 2-6. 순번 갱신
		if (listFileDto != null && listFileDto.size() != 0) {

			// 현 게시물의 파일 목록
			List<FileDto> fileDtoList = fileService.selectFileList(fileDto);

			int index = 1;

			for (FileDto file : fileDtoList) {
				int uCount = 0;

				file.setSrtSeq(index); // 순번 설정

				uCount = fileService.chgFileSrtSeq(file); // 순번 갱신

				if (uCount == 0) {
					throw new CustomMessageException("result-message.messages.common.message.update.error"); // 수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
				}

				index++;
			}
		}

		// [[3]]. 반환값 셋팅
		archiveSaveRes.setNttSn(archiveSaveReq.getNttSn());

		return archiveSaveRes;
	}

	/*****************************************************
	 * 자료실 단건 조회
	 * @param archiveSelectReq
	 * @return ArchiveSelectRes
	 * @throws Exception
	*****************************************************/
	public ArchiveSelectRes getArchiveDetail(ArchiveSelectReq archiveSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		ArchiveSelectRes archiveSelectRes = null;
		FileDto fileDto = new FileDto();
		List<FileDto> fileDtoList = null;
		int fileCnt = 0;

		// [[1]]. tb_ntt_mng 데이터 조회
		archiveSelectRes = archiveMapper.selectArchiveDetail(archiveSelectReq);

		if (archiveSelectRes == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		// [[2]]. 해당 자료실 글 파일 개수
		// 게시글의 일련 번호를 파일의 참조 구분 값으로 설정
		fileDto.setRefDivVal(Integer.toString(archiveSelectRes.getNttSn()));

		// 자료실 글 파일 개수 조회
		fileCnt = fileService.getFileCnt(fileDto);

		// [[3]]. 해당 자료실 글 파일 목록 조회
		// 파일 목록 조회
		fileDtoList = fileService.selectFileList(fileDto);

		// 조회한 파일 개수, 목록 세팅
		archiveSelectRes.setFileDtoCnt(fileCnt);
		archiveSelectRes.setFileDtoList(fileDtoList);

		return archiveSelectRes;
	}

}

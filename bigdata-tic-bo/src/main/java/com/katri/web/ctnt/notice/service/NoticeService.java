package com.katri.web.ctnt.notice.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.model.FileDto;
import com.katri.web.comm.service.FileService;
import com.katri.web.ctnt.notice.mapper.NoticeMapper;
import com.katri.web.ctnt.notice.model.NoticeSaveReq;
import com.katri.web.ctnt.notice.model.NoticeSaveRes;
import com.katri.web.ctnt.notice.model.NoticeSelectReq;
import com.katri.web.ctnt.notice.model.NoticeSelectRes;
import com.katri.web.system.code.model.CodeSelectRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class NoticeService {

	private final FileService fileService;

	private final NoticeMapper noticeMapper;

	/*****************************************************
	 * 공지사항 목록 조회
	 * @param noticeSelectReq
	 * @return List<NoticeSelectRes>
	 * @throws Exception
	*****************************************************/
	public List<NoticeSelectRes> getNoticeList(NoticeSelectReq noticeSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		List<NoticeSelectRes> noticeList = null;
		// [[1]]. tb_ntt_mng 테이블 조회
		noticeList = noticeMapper.selectNoticeList(noticeSelectReq);

		if (noticeList == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return noticeList;
	}

	/*****************************************************
	 * 공지사항 게시글 갯수
	 * @param noticeSelectReq
	 * @return 총 게시글 수
	*****************************************************/
	public int getNoticeCnt(NoticeSelectReq noticeSelectReq) {
		// [[0]]. 반환할 정보들
		int noticeCnt = 0;
		// [[1]]. tb_ntt_mng 테이블 조회
		noticeCnt = noticeMapper.selectNoticeCnt(noticeSelectReq);

		return noticeCnt;
	}

	/*****************************************************
	 * 공지사항 분류 코드 조회
	 * @return List<CodeSelectRes>
	 * @throws Exception
	*****************************************************/
	public List<CodeSelectRes> getNoticeClfCd() throws Exception {
		// [[0]]. 반환할 정보들
		List<CodeSelectRes> noticeClfCd = null;
		// [[1]]. tb_comn_cd 테이블 조회
		noticeClfCd = noticeMapper.selectNoticeClfCd();

		if (noticeClfCd == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return noticeClfCd;
	}

	/*****************************************************
	 * 공지사항 글 작성
	 * @param noticeSaveReq
	*****************************************************/
	public NoticeSaveRes insertNotice(HttpServletRequest request, NoticeSaveReq noticeSaveReq) throws Exception {
		// 접속 사용자
		noticeSaveReq.setCrtrId(SessionUtil.getLoginMngrId());

		// [[0]]. 반환할 정보들
		int result = 0;
		NoticeSaveRes noticeSaveRes = new NoticeSaveRes();

		// [[1]]. tb_ntt_mng 데이터 추가
		result = noticeMapper.insertNotice(noticeSaveReq);

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
		fileService.saveDBFile(Integer.toString(noticeSaveReq.getNttSn()), listFileDto);

		// [[3]]. 반환값 셋팅
		noticeSaveRes.setNttSn(noticeSaveReq.getNttSn());

		return noticeSaveRes;
	}

	/*****************************************************
	 * 공지사항 단건 조회
	 * @param noticeSelectReq
	 * @return NoticeSelectRes
	 * @throws Exception
	*****************************************************/
	public NoticeSelectRes getNoticeDetail(NoticeSelectReq noticeSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		NoticeSelectRes noticeSelectRes = new NoticeSelectRes();
		FileDto fileDto = new FileDto();
		List<FileDto> fileDtoList = null;
		int fileCnt = 0;

		// [[1]]. tb_ntt_mng 데이터 조회
		noticeSelectRes = noticeMapper.selectNoticeDetail(noticeSelectReq);

		if (noticeSelectRes == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		// [[2]]. 해당 공지사항의 파일 개수
		// 게시글의 일련 번호를 파일의 참조 구분 값으로 설정
		fileDto.setRefDivVal(Integer.toString(noticeSelectReq.getNttSn()));

		// 공지사항의 파일 개수 조회
		fileCnt = fileService.getFileCnt(fileDto);

		// [[3]]. 해당 공지사항의 파일 목록 조회
		// 파일 목록 조회
		fileDtoList = fileService.selectFileList(fileDto);

		// 조회한 파일 개수, 목록 세팅
		noticeSelectRes.setFileDtoCnt(fileCnt);
		noticeSelectRes.setFileDtoList(fileDtoList);

		return noticeSelectRes;
	}

	/*****************************************************
	 * 공지사항 수정
	 * @param noticeSaveReq
	 * @return 성공 카운트
	 * @throws Exception
	*****************************************************/
	public NoticeSaveRes updateNotice(HttpServletRequest request, NoticeSaveReq noticeSaveReq) throws Exception {
		// 접속 사용자
		noticeSaveReq.setMdfrId(SessionUtil.getLoginMngrId());

		// [[0]]. 반환할 정보들
		int result = 0;
		int fileCnt = 0;
		NoticeSaveRes noticeSaveRes = new NoticeSaveRes();

		// [[1]]. tb_ntt_mng 데이터 수정
		result = noticeMapper.updateNotice(noticeSaveReq);

		if (!(result > 0)) {
			throw new CustomMessageException("result-message.messages.common.message.update.error"); // 수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// [[2]]. 파일 저장

		// 2-1. 데이터 세팅
		FileDto fileDto = new FileDto();
		fileDto.setRefDivVal(Integer.toString(noticeSaveReq.getNttSn()));

		// 2-2. 기존 업로드 파일 개수 확인
		fileCnt = fileService.getFileCnt(fileDto);

		// 2-3. 물리 파일 저장
		List<FileDto> listFileDto = fileService.savePhyFileReturnFileList(request, "");

		// 2-4. 파일 3개 업로드 초과 방지
		if (!(listFileDto.size() >= 0 && listFileDto.size() <= 3)) {
			throw new CustomMessageException("result-message.messages.common.message.file.exists.error"); // 파일이 정상적으로 업로드가 되지 않아 에러가 발생하였습니다.
		}

		// 2-5. 논리 파일 저장
		fileService.saveDBFile(Integer.toString(noticeSaveReq.getNttSn()), listFileDto);

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
		noticeSaveRes.setNttSn(noticeSaveReq.getNttSn());

		return noticeSaveRes;
	}

	/*****************************************************
	 * 공지 메인 노출 여부 변경
	 * @param noticeSaveReq
	 * @param count
	 * @return NoticeSaveRes
	 * @throws Exception
	*****************************************************/
	public NoticeSaveRes chgNtcExpsrYn(NoticeSaveReq noticeSaveReq, int count) throws Exception {
		// [[0]]. 반환할 정보들
		int result = 0;
		NoticeSaveRes noticeSaveRes = new NoticeSaveRes();

		// [[1]]. 체크 가능한 개수
		if (count >= 3 && "Y".equals(noticeSaveReq.getNtcExpsrYn())) { // 3개
			throw new CustomMessageException("result-message.messages.notice.message.error.limit" + "||msgArgu=3||"); // 3개 까지만 선택 가능합니다.

		}

		// [[2]]. tb_ntt_mng 데이터 수정
		result = noticeMapper.chgNtcExpsrYn(noticeSaveReq);

		if (!(result > 0)) {
			throw new CustomMessageException("result-message.messages.common.message.update.error"); // 수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// [[3]]. 반환값 셋팅
		noticeSaveRes.setNttSn(noticeSaveReq.getNttSn());

		return noticeSaveRes;
	}

	/*****************************************************
	 * 메인 노출 공지 개수 조회
	 * @param noticeSelectReq
	 * @return 메인비주얼 노출 공지 개수
	*****************************************************/
	public int getNtcExpsrYnCnt(NoticeSelectReq noticeSelectReq) {
		// [[0]]. 반환할 정보들
		int result = 0;

		// [[1]]. 공지 노출 여부 'Y' 데이터 조회
		result = noticeMapper.selectNtcExpsrYnCnt(noticeSelectReq);

		return result;
	}

}

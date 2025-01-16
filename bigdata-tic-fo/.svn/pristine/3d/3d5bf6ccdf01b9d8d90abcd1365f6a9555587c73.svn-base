package com.katri.web.ctnt.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.web.comm.model.FileDto;
import com.katri.web.comm.service.FileService;
import com.katri.web.ctnt.notice.mapper.NoticeMapper;
import com.katri.web.ctnt.notice.model.NoticeSaveReq;
import com.katri.web.ctnt.notice.model.NoticeSelectReq;
import com.katri.web.ctnt.notice.model.NoticeSelectRes;
import com.nhncorp.lucy.security.xss.XssPreventer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = { Exception.class })
public class NoticeService {

	private final FileService fileService;

	private final NoticeMapper noticeMapper;

	/*****************************************************
	 * 공지사항 게시글 개수
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
	 * 공지사항 목록 조회
	 * @param noticeSelectReq
	 * @return List<NoticeSelectRes>
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
	 * 공지사항 상세 조회
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
		int uCount = 0;

		// [[1]]. tb_ntt_mng 데이터 조회
		noticeSelectRes = noticeMapper.selectNoticeDetail(noticeSelectReq);

		if (noticeSelectRes != null) {
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
		} else {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		NoticeSaveReq noticeSaveReq = new NoticeSaveReq();
		noticeSaveReq.setNttSn(noticeSelectReq.getNttSn()); // 게시글 번호
		noticeSaveReq.setNttTyCd(noticeSelectReq.getNttTyCd()); // 게시글 유형

		// 조회수 증가
		uCount = noticeMapper.hitNotice(noticeSaveReq);

		if (!(uCount > 0)) {
			throw new CustomMessageException("result-message.messages.common.message.error"); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// xss 처리
		noticeSelectRes.setNttCn(XssPreventer.unescape(noticeSelectRes.getNttCn()));

		return noticeSelectRes;
	}

}

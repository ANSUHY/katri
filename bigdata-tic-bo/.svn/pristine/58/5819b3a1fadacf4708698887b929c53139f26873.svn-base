package com.katri.web.ctnt.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.ctnt.notice.model.NoticeSaveReq;
import com.katri.web.ctnt.notice.model.NoticeSelectReq;
import com.katri.web.ctnt.notice.model.NoticeSelectRes;
import com.katri.web.system.code.model.CodeSelectRes;

@Mapper
@Repository
@MainMapperAnnotation
public interface NoticeMapper {

	/*****************************************************
	 * 공지사항 목록 조회
	 * @param noticeSelectReq
	 * @return List<NoticeSelectRes>
	*****************************************************/
	List<NoticeSelectRes> selectNoticeList(NoticeSelectReq noticeSelectReq);

	/*****************************************************
	 * 공지사항 게시글 갯수
	 * @param noticeSelectReq
	 * @return int
	*****************************************************/
	int selectNoticeCnt(NoticeSelectReq noticeSelectReq);

	/*****************************************************
	 * 공지사항 분류 코드 조회
	 * @return List<CodeSelectRes>
	*****************************************************/
	List<CodeSelectRes> selectNoticeClfCd();

	/*****************************************************
	 * 공지사항 글 작성
	 * @param noticeSaveReq
	 * @return int
	*****************************************************/
	int insertNotice(NoticeSaveReq noticeSaveReq);

	/*****************************************************
	 * 공지사항 글 조회
	 * @param noticeSelectReq
	 * @return NoticeSelectRes
	*****************************************************/
	NoticeSelectRes selectNoticeDetail(NoticeSelectReq noticeSelectReq);

	/*****************************************************
	 * 공지사항 수정
	 * @param noticeSaveReq
	 * @return 성공 카운트
	*****************************************************/
	int updateNotice(NoticeSaveReq noticeSaveReq);

	/*****************************************************
	 * 공지 메인 노출 여부 변경
	 * @param noticeSaveReq
	 * @return int
	*****************************************************/
	int chgNtcExpsrYn(NoticeSaveReq noticeSaveReq);

	/*****************************************************
	 * 메인 노출 공지 개수 조회
	 * @param noticeSelectReq
	 * @return int
	*****************************************************/
	int selectNtcExpsrYnCnt(NoticeSelectReq noticeSelectReq);

}

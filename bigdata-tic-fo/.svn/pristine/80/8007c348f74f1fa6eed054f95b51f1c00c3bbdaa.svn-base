package com.katri.web.ctnt.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.ctnt.notice.model.NoticeSaveReq;
import com.katri.web.ctnt.notice.model.NoticeSelectReq;
import com.katri.web.ctnt.notice.model.NoticeSelectRes;

@Mapper
@Repository
@MainMapperAnnotation
public interface NoticeMapper {

	/*****************************************************
	 * 공지사항 게시글 개수
	 * @param noticeSelectReq
	 * @return 총 게시글 수
	*****************************************************/
	int selectNoticeCnt(NoticeSelectReq noticeSelectReq);

	/*****************************************************
	 * 공지사항 목록 조회
	 * @param noticeSelectReq
	 * @return List<NoticeSelectRes>
	*****************************************************/
	List<NoticeSelectRes> selectNoticeList(NoticeSelectReq noticeSelectReq);

	/*****************************************************
	 * 공지사항 상세 조회
	 * @param noticeSelectReq
	 * @return NoticeSelectRes
	*****************************************************/
	NoticeSelectRes selectNoticeDetail(NoticeSelectReq noticeSelectReq);

	/*****************************************************
	 * 공지사항 조회수 증가
	 * @param noticeSaveReq
	 * @return 성공 카운트
	*****************************************************/
	int hitNotice(NoticeSaveReq noticeSaveReq);

}

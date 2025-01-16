package com.katri.web.search.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.ctnt.archive.model.ArchiveSelectRes;
import com.katri.web.ctnt.faq.model.FaqSelectRes;
import com.katri.web.ctnt.notice.model.NoticeSelectRes;
import com.katri.web.dataUsageGuide.model.CertDataSelectRes;
import com.katri.web.search.model.SearchSelectReq;

@Mapper
@Repository
@MainMapperAnnotation
public interface SearchMapper {

	/*****************************************************
	 * 공지사항 검색 목록 조회
	 * @param searchSelectReq
	 * @return
	*****************************************************/
	List<NoticeSelectRes> selectNoticeList(SearchSelectReq searchSelectReq);

	/*****************************************************
	 * FAQ 검색 목록 조회
	 * @param searchSelectReq
	 * @return
	*****************************************************/
	List<FaqSelectRes> selectFaqList(SearchSelectReq searchSelectReq);

	/*****************************************************
	 * 자료실 검색 목록 조회
	 * @param searchSelectReq
	 * @return
	*****************************************************/
	List<ArchiveSelectRes> selectArchiveList(SearchSelectReq searchSelectReq);

	/*****************************************************
	 * 추천 검색어 조회
	 * @param searchSelectReq
	 * @return
	*****************************************************/
	List<String> selectRecKeywordList(SearchSelectReq searchSelectReq);

	/*****************************************************
	 * 인증데이터 조회
	 * @param searchSelectReq
	 * @return
	*****************************************************/
	List<CertDataSelectRes> selectCertDataList(SearchSelectReq searchSelectReq);

}
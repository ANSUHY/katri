package com.katri.web.search.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.katri.common.util.SessionUtil;
import com.katri.web.ctnt.archive.model.ArchiveSelectRes;
import com.katri.web.ctnt.faq.model.FaqSelectRes;
import com.katri.web.ctnt.notice.model.NoticeSelectRes;
import com.katri.web.dataUsageGuide.model.CertDataSelectRes;
import com.katri.web.search.mapper.SearchMapper;
import com.katri.web.search.model.SearchSelectReq;
import com.katri.web.search.model.SearchSelectRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {

	private final SearchMapper searchMapper;

	/*****************************************************
	 * 통합 검색
	 * @param searchSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public SearchSelectRes getSearchList(SearchSelectReq searchSelectReq) throws Exception {
		// [[0]]. 반환값
		List<NoticeSelectRes> noticeList = null;
		List<FaqSelectRes> faqList = null;
		List<ArchiveSelectRes> archvieList = null;
		List<CertDataSelectRes> certDataList = null;
		SearchSelectRes searchSelectRes = new SearchSelectRes();

		// [[1]]. 데이터 조회
		noticeList = searchMapper.selectNoticeList(searchSelectReq); // 공지사항
		faqList = searchMapper.selectFaqList(searchSelectReq); // FAQ
		archvieList = searchMapper.selectArchiveList(searchSelectReq); // 자료실
		certDataList = searchMapper.selectCertDataList(searchSelectReq); // 인증데이터

		// [[2]]. 데이터 세팅
		searchSelectRes.setNoticeList(noticeList);
		searchSelectRes.setFaqList(faqList);
		searchSelectRes.setArchiveList(archvieList);
		searchSelectRes.setCertDataList(certDataList);

		return searchSelectRes;
	}

	/*****************************************************
	 * 추천 키워드 조회
	 * @param searchSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public List<String> getRecKeywordList(SearchSelectReq searchSelectReq) throws Exception {
		// 접속 아이디
		searchSelectReq.setUnifiedSearchUserId(SessionUtil.getLoginUserId());

		// [[0]]. 반환값
		List<String> recKeywordList = null;

		// [[1]]. 데이터 조회
		recKeywordList = searchMapper.selectRecKeywordList(searchSelectReq);

		return recKeywordList;
	}

}

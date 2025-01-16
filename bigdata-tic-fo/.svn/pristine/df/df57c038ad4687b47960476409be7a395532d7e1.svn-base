package com.katri.web.ctnt.faq.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.ctnt.faq.model.FaqSaveReq;
import com.katri.web.ctnt.faq.model.FaqSelectReq;
import com.katri.web.ctnt.faq.model.FaqSelectRes;

@Mapper
@Repository
@MainMapperAnnotation
public interface FaqMapper {

	/*****************************************************
	 * FAQ 게시글 개수
	 * @param faqSelectReq
	 * @return List<FaqSelectRes>
	*****************************************************/
	List<FaqSelectRes> selectFaqList(FaqSelectReq faqSelectReq);

	/*****************************************************
	 * FAQ 목록 조회
	 * @param faqSelectReq
	 * @return 총 게시글 수
	*****************************************************/
	int selectFaqCnt(FaqSelectReq faqSelectReq);

	/*****************************************************
	 * FAQ 내용 조회
	 * @param faqSelectReq
	 * @return FaqSelectRes
	*****************************************************/
	FaqSelectRes selectFaqCn(FaqSelectReq faqSelectReq);

	/*****************************************************
	 * FAQ 조회수 증가
	 * @param faqSaveReq
	 * @return
	*****************************************************/
	int hitFaq(FaqSaveReq faqSaveReq);

}

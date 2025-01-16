package com.katri.web.ctnt.faq.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.ctnt.faq.model.FaqSaveReq;
import com.katri.web.ctnt.faq.model.FaqSelectReq;
import com.katri.web.ctnt.faq.model.FaqSelectRes;
import com.katri.web.system.code.model.CodeSelectRes;

@Mapper
@Repository
@MainMapperAnnotation
public interface FaqMapper {

	/*****************************************************
	 * FAQ 게시글 개수
	 * @param faqSelectReq
	 * @return int
	*****************************************************/
	int selectFaqCnt(FaqSelectReq faqSelectReq);

	/*****************************************************
	 * FAQ 목록 조회
	 * @param faqSelectReq
	 * @return List<NoticeSelectRes>
	*****************************************************/
	List<FaqSelectRes> selectFaqList(FaqSelectReq faqSelectReq);

	/*****************************************************
	 * FAQ 분류 코드 조회
	 * @return List<CodeSelectRes>
	*****************************************************/
	List<CodeSelectRes> selectFaqClfCd();

	/*****************************************************
	 * FAQ 단건 조회
	 * @param faqSelectReq
	 * @return FaqSelectRes
	*****************************************************/
	FaqSelectRes selectFaqDetail(FaqSelectReq faqSelectReq);

	/*****************************************************
	 * FAQ 작성
	 * @param faqSaveReq
	 * @return int
	*****************************************************/
	int insertFaq(FaqSaveReq faqSaveReq);

	/*****************************************************
	 * FAQ 수정
	 * @param faqSaveReq
	 * @return int
	*****************************************************/
	int updateFaq(FaqSaveReq faqSaveReq);

}

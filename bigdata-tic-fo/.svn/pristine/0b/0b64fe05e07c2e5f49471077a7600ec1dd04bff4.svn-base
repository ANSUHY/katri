package com.katri.web.mypage.inquiry.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.mypage.inquiry.model.InquirySaveReq;
import com.katri.web.mypage.inquiry.model.InquirySelectReq;
import com.katri.web.mypage.inquiry.model.InquirySelectRes;

@Repository
@Mapper
@MainMapperAnnotation
public interface InquiryMapper {

	/*****************************************************
	 * Inquiry(1:1 문의) 문의 유형 조회
	 * @param  inqSelectReq
	 * @return 1:1문의 리스트
	 *****************************************************/
	 List<InquirySelectRes> selectInquiryCategoryList(String userTyCd);

	/*****************************************************
	 * Inquiry(1:1 문의) 리스트 개수 조회
	 * @param  inqSelectReq
	 * @return 리스트개수
	 *****************************************************/
	int selectInquiryListCount(InquirySelectReq inquirySelectReq);

	/*****************************************************
	 * Inquiry(1:1 문의) 리스트 조회
	 * @param  inqSelectReq
	 * @return 1:1문의 리스트
	 *****************************************************/
	List<InquirySelectRes> selectInquiryList(InquirySelectReq inquirySelectReq);

	/*****************************************************
	 * Inquiry(1:1 문의) 등록
	 * @param  inquirySaveReq
	 * @return 1:1문의 등록 결과 (int)
	 *****************************************************/
	int insertInquiry(InquirySaveReq inquirySaveReq);

	/*****************************************************
	 * Inquiry(1:1 문의) 조회수 증가
	 * @param  String (게시글 번호)
	 * @return 조회수 증가 결과 (int)
	 *****************************************************/
	int updateInqcnt(int nttSn);

	/*****************************************************
	 * Inquiry(1:1 문의) 등록 -> 메일 전송
	 * @return String (메일 주소)
	 *****************************************************/
	String selectMailAddress();

}

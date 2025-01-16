package com.katri.web.ctnt.inquiry.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.ctnt.inquiry.model.InquirySaveReq;
import com.katri.web.ctnt.inquiry.model.InquirySelectReq;
import com.katri.web.ctnt.inquiry.model.InquirySelectRes;

@Repository
@Mapper
@MainMapperAnnotation
public interface InquiryMapper {

	/*****************************************************
	 * inquiry 리스트 개수 조회
	 * @param inquirySelectReq
	 * @return 리스트개수
	 *****************************************************/
	int getInqCnt(InquirySelectReq inquirySelectReq);

	/*****************************************************
	 * inquiry 리스트 조회
	 * @param inquirySelectReq
	 * @return 리스트 조회
	 *****************************************************/
	List<InquirySelectRes> getInqList(InquirySelectReq searchData);

	/*****************************************************
	 * inquiry 상세 조회
	 * @param inquirySelectReq
	 * @return 상세 데이터
	 *****************************************************/
	InquirySelectRes getDetailData(int nttSn);

	/*****************************************************
	 * inquiry 답변 수정
	 * @param inquirySaveReq
	 * @return int
	 *****************************************************/
	int updateInquiryAns(InquirySaveReq updateData);

	/*****************************************************
	 * inquiry 답변 등록
	 * @param inquirySaveReq
	 * @return int
	 *****************************************************/
	int regInqAns(InquirySaveReq regData);

	/*****************************************************
	 * inquiry 작성자 이메일 조회
	 * @param inquirySaveReq
	 * @return String
	 *****************************************************/
	 String selectEmail(String crtrId);

	 /*****************************************************
	 * inquiry 조회수 증가
	 * @param int
	 * @return int (증가 결과)
	 *****************************************************/
	 int updateNttInqCnt(int nttSn);


}

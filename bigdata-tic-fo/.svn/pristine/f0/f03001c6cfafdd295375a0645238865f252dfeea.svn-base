package com.katri.web.comm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.comm.model.TermsSelectRes;

@Repository
@Mapper
@MainMapperAnnotation
public interface TermsMapper {

	/*****************************************************
	 * 약관 조회
	 * @param  String 약관유형코드
	 * @return termsSelectRes 약관내용
	 *****************************************************/
	TermsSelectRes selectKatriTerms(String trmsTyCd);

}

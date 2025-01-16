package com.katri.web.search.keyword.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.search.keyword.model.KeywordSaveReq;
import com.katri.web.search.keyword.model.KeywordSelectRes;


@Repository
@Mapper
@MainMapperAnnotation
public interface KeywordMapper {

	/*****************************************************
	 * 추천 검색어 등록
	 * @param KeywordSaveReq
	 * @return int
	 *****************************************************/
	int regKeyword(KeywordSaveReq keywordData);

	/*****************************************************
	 * 추천 검색어 목록 조회
	 * @return List
	 *****************************************************/
	List<KeywordSelectRes> getKeywordList(String srwrdTyCd);

	/*****************************************************
	 * 추천 검색어 사용여부 개수 조회
	 * @return int
	 *****************************************************/
	int getUseYnCount(String srwrdTyCd);

	/*****************************************************
	 * 사용중인 추천 검색어 목록 조회
	 * @return List
	 *****************************************************/
	List<KeywordSelectRes> getUseYList(String srwrdTyCd);

	/*****************************************************
	 * 추천 검색어 수정
	 * @param KeywordSaveReq
	 * @return int
	 *****************************************************/
	int updateKeyword(KeywordSaveReq updateKeywordData);



}
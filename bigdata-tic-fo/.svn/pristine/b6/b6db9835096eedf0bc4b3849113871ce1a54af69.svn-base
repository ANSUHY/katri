package com.katri.web.stats.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.stats.model.StatsReq;
import com.katri.web.stats.model.StatsRes;

import io.swagger.annotations.Api;

@Repository
@Mapper
@MainMapperAnnotation
@Api(tags = {"통계 Mapper"})
public interface StatsMapper {

	/*****************************************************
	 * 인증상태 통계
	 * @param StatsReq 인증상태 검색조건
	 * @return List<StatsRes> 통계 리스트
	 *****************************************************/
	List<StatsRes> selectStatsCount(StatsReq req);
	/*****************************************************
	 * 인증구분 통계 페이지 카운트
	 * @param StatsReq 인증상태 검색조건
	 * @return List<StatsRes> 통계 리스트
	 *****************************************************/
	int selectTypeTotalCount(StatsReq req);
	/*****************************************************
	* 인증구분 통계
	* @param StatsReq 인증상태 검색조건
	* @return List<StatsRes> 통계 리스트
	*****************************************************/
	List<StatsRes> selectTypeCount(StatsReq req);

}

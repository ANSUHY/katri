package com.katri.web.stats.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.web.stats.mapper.StatsMapper;
import com.katri.web.stats.model.StatsReq;
import com.katri.web.stats.model.StatsRes;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
@Slf4j
@Api(tags = {"통계 Service"})
public class StatsService {
	private final StatsMapper statsMapper;

	/*****************************************************
	 * 인증상태 통계
	 * @param StatsReq 인증상태 검색조건
	 * @return List<StatsRes> 통계 리스트
	 *****************************************************/
	public List<StatsRes> selectStatsCount(StatsReq req) {
		return this.statsMapper.selectStatsCount(req);
	}
	/*****************************************************
	 * 인증구분 통계 페이지 카운트
	 * @param StatsReq 인증상태 검색조건
	 * @return List<StatsRes> 통계 리스트
	 *****************************************************/
	public int selectTypeTotalCount(StatsReq req) {
		return this.statsMapper.selectTypeTotalCount(req);
	}
	/*****************************************************
	 * 인증구분 통계
	 * @param StatsReq 인증상태 검색조건
	 * @return List<StatsRes> 통계 리스트
	 *****************************************************/
	public List<StatsRes> selectTypeCount(StatsReq req) {
		return this.statsMapper.selectTypeCount(req);
	}
}

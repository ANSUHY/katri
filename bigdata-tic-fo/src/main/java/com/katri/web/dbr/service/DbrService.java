package com.katri.web.dbr.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.web.dbr.mapper.DbrMapper;
import com.katri.web.dbr.model.DbrReq;
import com.katri.web.dbr.model.DbrRes;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
@Slf4j
@Api(tags = {"대시보드 Service"})
public class DbrService {
	private final DbrMapper mapper;

	/*****************************************************
	 * 대시보드 기준일자
	 * @return 기준일
	 *****************************************************/
	public DbrRes selectReferenceDate() {
		return this.mapper.selectReferenceDate();
	}
	/*****************************************************
	 * 대시보드 상단 카운트
	 * @param req 검색조건
	 * @return 조회된 리스트
	 *****************************************************/
	public List<DbrRes> selectReferenceCount(DbrReq req) {
		return this.mapper.selectReferenceCount(req);
	}
	/*****************************************************
	 * 대시보드 결과 카운트
	 * @param req 검색조건
	 * @return 조회된 리스트
	 *****************************************************/
	public List<DbrRes> selectResultCount(DbrReq req) {
		return this.mapper.selectResultCount(req);
	}
	/*****************************************************
	 * 대시보드 건수 카운트
	 * @param req 검색조건
	 * @return 조회된 리스트
	 *****************************************************/
	public List<DbrRes> selectCaseCount(DbrReq req) {
		return this.mapper.selectCaseCount(req);
	}

}

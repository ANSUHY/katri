package com.katri.web.dbr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.dbr.model.DbrReq;
import com.katri.web.dbr.model.DbrRes;

import io.swagger.annotations.Api;

@Repository
@Mapper
@MainMapperAnnotation
@Api(tags = {"대시보드 Mapper"})
public interface DbrMapper {

	/*****************************************************
	 * 대시보드 상단 카운트
	 * @return 조회된 리스트
	 *****************************************************/
	DbrRes selectReferenceDate();
	/*****************************************************
	 * 대시보드 상단 카운트
	 * @param req 조회 조건
	 * @return 조회된 리스트
	 *****************************************************/
	List<DbrRes> selectReferenceCount(DbrReq req);
	/*****************************************************
	 * 대시보드 결과 카운트
	 * @param req 조회 조건
	 * @return 조회된 리스트
	 *****************************************************/
	List<DbrRes> selectResultCount(DbrReq req);
	/*****************************************************
	 * 대시보드 건수 카운트
	 * @param req 조회 조건
	 * @return 조회된 리스트
	 *****************************************************/
	List<DbrRes> selectCaseCount(DbrReq req);



}

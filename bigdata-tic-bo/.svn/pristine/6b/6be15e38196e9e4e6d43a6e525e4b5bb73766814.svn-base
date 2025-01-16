package com.katri.web.hmpgCptn.mainVisual.mapper;

import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.hmpgCptn.mainVisual.model.MainVisualSaveReq;
import com.katri.web.hmpgCptn.mainVisual.model.MainVisualSelectReq;
import com.katri.web.hmpgCptn.mainVisual.model.MainVisualSelectRes;

@Repository
@Mapper
@MainMapperAnnotation
public interface MainVisualMapper {

	/*****************************************************
	 * 메인 비주얼 목록 개수 조회
	 * @param mainVisualSelectReq
	 * @return
	*****************************************************/
	int selectMainVisualCount(MainVisualSelectReq mainVisualSelectReq);

	/*****************************************************
	 * 메인 비주얼 목록 조회
	 * @param mainVisualSelectReq
	 * @return
	*****************************************************/
	List<MainVisualSelectRes> selectMainVisualList(MainVisualSelectReq mainVisualSelectReq);

	/*****************************************************
	 * 메인 비주얼 상세 조회
	 * @param mainVisualSelectReq
	 * @return
	*****************************************************/
	MainVisualSelectRes selectMainVisualDetail(MainVisualSelectReq mainVisualSelectReq);

	/*****************************************************
	 * 메인 비주얼 수정
	 * @param mainVisualSaveReq
	 * @return
	*****************************************************/
	int updateMainVisual(@Valid MainVisualSaveReq mainVisualSaveReq);

	/*****************************************************
	 * 메인 비주얼 등록
	 * @param mainVisualSaveReq
	 * @return
	*****************************************************/
	int insertMainVisual(@Valid MainVisualSaveReq mainVisualSaveReq);

	/*****************************************************
	 * 메인 비주얼 순서 수정
	 * @param mainVisualSaveReq
	 * @return
	*****************************************************/
	int updateMainVisualSeq(MainVisualSaveReq mainVisualSaveReq);

}

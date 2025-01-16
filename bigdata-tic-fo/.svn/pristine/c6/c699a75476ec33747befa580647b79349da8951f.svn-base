package com.katri.web.particiLounge.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.particiLounge.model.ParticiLoungeSelectReq;
import com.katri.web.particiLounge.model.ParticiLoungeSelectRes;


@Repository
@Mapper
@MainMapperAnnotation
public interface ParticiLoungeMapper {

	/*****************************************************
	 * [참여기관 라운지] 각 페이지에 맞는 데이터 조회
	 * @param particiLoungeSelectReq
	 * @return [참여기관 라운지] _데이터
	*****************************************************/
	ParticiLoungeSelectRes selectParticiLoungeData(ParticiLoungeSelectReq particiLoungeSelectReq);

}

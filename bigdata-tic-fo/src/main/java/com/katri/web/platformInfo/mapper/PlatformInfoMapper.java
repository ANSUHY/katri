package com.katri.web.platformInfo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.platformInfo.model.PlatformInfoSelectReq;
import com.katri.web.platformInfo.model.PlatformInfoSelectRes;


@Repository
@Mapper
@MainMapperAnnotation
public interface PlatformInfoMapper {

	/*****************************************************
	 * [플랫폼 소개] 각 페이지에 맞는 데이터 조회
	 * @param platformInfoSelectReq
	 * @return [플랫폼 소개] _데이터
	*****************************************************/
	PlatformInfoSelectRes selectPlatformInfoData(PlatformInfoSelectReq platformInfoSelectReq);

}

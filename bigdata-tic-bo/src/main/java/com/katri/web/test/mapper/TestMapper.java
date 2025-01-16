package com.katri.web.test.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;

import io.swagger.annotations.Api;

@Repository
@Mapper
@MainMapperAnnotation
@Api(tags = {"테스트 Mapper"})
public interface TestMapper {


	/*****************************************************
	 * 임시임시 DB업데이트
	 * @param
	 * @return int 성공개수
	 *****************************************************/
	int updateTempUpdate(String comnCd);

}

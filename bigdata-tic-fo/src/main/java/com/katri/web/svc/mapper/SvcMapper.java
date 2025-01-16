package com.katri.web.svc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.svc.model.SvcSelectReq;
import com.katri.web.svc.model.SvcSelectRes;


@Repository
@Mapper
@MainMapperAnnotation
public interface SvcMapper {

	/*****************************************************
	 * [QR랜딩페이지][인증데이터 조회] 상세 조회
	 * @param svcSelectReq
	 * @return 상세정보
	 *****************************************************/
	SvcSelectRes selectCertDataDetail(SvcSelectReq svcSelectReq);

	/*****************************************************
	 * [QR랜딩페이지][인증데이터 조회] 이미지 리스트 조회
	 * @param svcSelectReq
	 * @return 리스트
	 *****************************************************/
	List<String> selectCertDataImgList(SvcSelectReq svcSelectReq);

}

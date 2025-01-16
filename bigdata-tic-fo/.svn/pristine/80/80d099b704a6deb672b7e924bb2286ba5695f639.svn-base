package com.katri.web.comm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.comm.model.CommReq;
import com.katri.web.comm.model.CommRes;
import com.katri.web.comm.model.UserLogHistSaveReq;

import io.swagger.annotations.Api;

@Repository
@Mapper
@MainMapperAnnotation
@Api(tags = {"공통 Mapper"})
public interface CommMapper {

	/*****************************************************
	 * 공통 코드 리스트
	 * @param CommReq 공통코드
	 * @return 공통코드 리스트
	 *****************************************************/
	List<CommRes> selectCode(CommReq res);

	/*****************************************************
	 * selectCertRelCode
	 * @param commReq
	 * @return
	*****************************************************/
	CommRes selectCertRelCode(CommReq commReq);

	/*****************************************************
	 * selectUpGrpCode
	 * @param commReq
	 * @return
	*****************************************************/
	List<CommRes> selectUpGrpCode(CommReq commReq);

	/*****************************************************
	 * 사용자로그이력 입력
	 * @param UserLogHistSaveReq 사용자로그이력정보
	 * @return 성공 카운트
	 *****************************************************/
	int insertUserLogHist(UserLogHistSaveReq userLogHistSaveReq);



}

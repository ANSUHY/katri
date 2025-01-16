package com.katri.web.system.authrt.mapper;

import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.system.authrt.model.AuthrtSaveReq;
import com.katri.web.system.authrt.model.AuthrtSelectReq;
import com.katri.web.system.authrt.model.AuthrtSelectRes;

@Repository
@Mapper
@MainMapperAnnotation
public interface AuthrtMapper {

	/*****************************************************
	 * 권한 그룹 리스트 개수 조회
	 * @param authrtSelectReq
	 * @return
	*****************************************************/
	int selectAuthGrpBasCount(AuthrtSelectReq authrtSelectReq);

	/*****************************************************
	 * 권한 그룹 리스트 조회
	 * @param authrtSelectReq
	 * @return
	*****************************************************/
	List<AuthrtSelectRes> selectAuthGrpBasList(AuthrtSelectReq authrtSelectReq);

	/*****************************************************
	 * 접속 권한 그룹 상세 조회
	 * @param authrtSelectReq
	 * @return
	*****************************************************/
	AuthrtSelectRes selectAuthGrpBasDetail(AuthrtSelectReq authrtSelectReq);

	/*****************************************************
	 * 접속 권한 메뉴 권한 등록
	 * @param authrtSaveReq
	 * @return
	*****************************************************/
	int insertAuthGrpBas(@Valid AuthrtSaveReq authrtSaveReq);

	/*****************************************************
	 * 접속 권한 메뉴 권한 수정
	 * @param authrtSaveReq
	 * @return
	*****************************************************/
	int updateAuthGrpBas(@Valid AuthrtSaveReq authrtSaveReq);

	/*****************************************************
	 * 메뉴 별 접속 권한 리스트 조회
	 * @param authrtSelectReq
	 * @return
	*****************************************************/
	List<AuthrtSelectRes> selectAuthrtGrpMenuList(AuthrtSelectReq authrtSelectReq);

	/*****************************************************
	 * 권한 그룹 > 메뉴 전체 삭제
	 * @param authrtSaveReq
	 * @return
	*****************************************************/
	int deleteAuthrtGrpMenu(AuthrtSaveReq authrtSaveReq);

	/*****************************************************
	 * 권한 그룹 > 메뉴 등록
	 * @param authrtSaveReq
	 * @return
	*****************************************************/
	int insertAuthrtGrpMenu(AuthrtSaveReq authrtSaveReq);

	/*****************************************************
	 * 권한 그룹 메뉴 있는지 개수 조회
	 * @param authrtSaveReq
	 * @return
	*****************************************************/
	int selectAuthGrpMenuCount(AuthrtSaveReq authrtSaveReq);

}

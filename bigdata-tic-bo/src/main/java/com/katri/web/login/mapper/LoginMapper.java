package com.katri.web.login.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.login.model.LoginAuthrtMenuReq;
import com.katri.web.login.model.LoginAuthrtMenuRes;
import com.katri.web.login.model.LoginHistReq;
import com.katri.web.login.model.LoginReq;
import com.katri.web.login.model.LoginRes;

@Repository
@Mapper
@MainMapperAnnotation
public interface LoginMapper {

	/*****************************************************
	 * 로그인 정보 조회
	 * @param loginReq 로그인할 사람의 정보
	 * @return LoginRes 로그인된 사람 정보
	 *****************************************************/
	LoginRes selectLoginDetail(LoginReq loginReq);

	/*****************************************************
	 * 로그인 성공시 DB 수정
	 * @param loginReq 로그인한 사람의 정보
	 * @return int 성공개수
	 *****************************************************/
	int updateLoginSucess(LoginReq loginReq);

	/*****************************************************
	 * 로그인 HIST 등록
	 * @param loginHistReq 로그인 HIST 정보
	 * @return int 성공개수
	 *****************************************************/
	int insertLoginHist(LoginHistReq loginHistReq);

	/*****************************************************
	 * 로그인한 사람의 권한에 따른 메뉴 리스트 조회
	 * @param loginAuthrtMenuReq 로그인한 사람의 정보
	 * @return List<LoginAuthrtMenuRes> 로그인한 사람의 권한에 따른 메뉴 정보
	 *****************************************************/
	List<LoginAuthrtMenuRes> selectLoginAuthrtMenuList(LoginAuthrtMenuReq loginAuthrtMenuReq);

	/*****************************************************
	 * 로그인한 사람의 권한에 따른 메뉴 조회
	 * @param loginAuthrtMenuReq 로그인한 사람의 정보 + 검색할 url
	 * @return List<LoginAuthrtMenuRes> 로그인한 사람의 권한에 따른 메뉴 정보
	 *****************************************************/
	LoginAuthrtMenuRes selectLoginAuthrtMenuDetail(LoginAuthrtMenuReq loginAuthrtMenuReq);
}

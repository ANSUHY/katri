package com.katri.web.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.auth.model.LoginAuthrtMenuReq;
import com.katri.web.auth.model.LoginAuthrtMenuRes;
import com.katri.web.auth.model.LoginHistReq;
import com.katri.web.auth.model.LoginReq;
import com.katri.web.auth.model.LoginRes;
import com.katri.web.auth.model.TryLoginUserSelectRes;

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
	 * 로그인 실패시 DB 수정(실패개수 늘리기)
	 * @param loginReq 로그인한 사람의 정보
	 * @return int 성공개수
	 *****************************************************/
	int updateLoginFail(LoginReq loginReq);

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

	/*****************************************************
	 * 로그인한 사람의 기업의 [기업그룹수집동의이력] 최종값이 Y인 개수
	 * @param LoginRes 로그인한 사람의 정보
	 * @return 로그인한 사람의 기업의 [기업그룹수집동의이력] 최종값이 Y인 개수
	 *****************************************************/
	int selectLoginEntGrpClctAgreYCnt(int entGrpSn);

	/*****************************************************
	 * 로그인 시도한 사람의 정보 조회(LoginId로조회)
	 * @param loginId 로그인 시도한 사람의 입력 ID
	 * @return TryLoginUserSelectRes 로그인 시도한 사람의 정보
	 *****************************************************/
	TryLoginUserSelectRes selectTryLoginUserDetail(String loginId);

}

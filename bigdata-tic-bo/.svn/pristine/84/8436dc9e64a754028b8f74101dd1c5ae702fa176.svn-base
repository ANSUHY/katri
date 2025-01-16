package com.katri.web.user.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.comm.model.FileDto;
import com.katri.web.user.user.model.UserPrdtSelectRes;
import com.katri.web.user.user.model.UserSaveReq;
import com.katri.web.user.user.model.UserSelectReq;
import com.katri.web.user.user.model.UserSelectRes;

@Repository
@Mapper
@MainMapperAnnotation
public interface UserMapper {

	/*****************************************************
	 * 회원 현황 목록 개수 조회
	 * @param userSelectReq
	 * @return
	*****************************************************/
	int selectUserBasCnt(UserSelectReq userSelectReq);

	/*****************************************************
	 * 회원 현황 목록 조회
	 * @param userSelectReq
	 * @return
	*****************************************************/
	List<UserSelectRes> selectUserBasList(UserSelectReq userSelectReq);

	/*****************************************************
	 * 회원 현황 상세 조회 ( 기관 )
	 * @param userSelectReq
	 * @return
	*****************************************************/
	UserSelectRes selectInstUserBasDetail(UserSelectReq userSelectReq);

	/*****************************************************
	 * 회원 현황 상세 조회 ( 기업 )
	 * @param userSelectReq
	 * @return
	*****************************************************/
	UserSelectRes selectEntUserBasDetail(UserSelectReq userSelectReq);

	/*****************************************************
	 * 회원 현황 상세 조회 ( 일반 )
	 * @param userSelectReq
	 * @return
	*****************************************************/
	UserSelectRes selectGnrlUserBasDetail(UserSelectReq userSelectReq);

	/*****************************************************
	 * 회원 접속 로그 목록 개수 조회
	 * @param userSelectReq
	 * @return
	*****************************************************/
	int selectUserLogHistCnt(UserSelectReq userSelectReq);

	/*****************************************************
	 * 회원 접속 로그 목록 조회
	 * @param userSelectReq
	 * @return
	*****************************************************/
	List<UserSelectRes> selectUserLogHistList(UserSelectReq userSelectReq);

	/*****************************************************
	 * 회원 약관 동의 이력 목록 개수 조회 > [회원가입 약관]
	 * @param userSelectReq
	 * @return
	*****************************************************/
	int selectUserTrmsAgreHistCnt(UserSelectReq userSelectReq);

	/*****************************************************
	 * 회원 약관 동의 이력 목록 조회 > [회원가입 약관]
	 * @param userSelectReq
	 * @return
	*****************************************************/
	List<UserSelectRes> selectUserTrmsAgreHistList(UserSelectReq userSelectReq);

	/*****************************************************
	 * 해당 아이디 > 현재 비밀번호 상세 조회
	 * @param userSelectReq
	 * @return
	*****************************************************/
	UserSelectRes selectUserPwdInfo(UserSelectReq userSelectReq);

	/*****************************************************
	 * 회원 비밀번호 초기화
	 * @param userSaveReq
	 * @return
	*****************************************************/
	int updateUserPwd(UserSaveReq userSaveReq);

	/*****************************************************
	 * 사용자 기본 정보 수정
	 * @param userSaveReq
	 * @return
	*****************************************************/
	int updateUserInfo(UserSaveReq userSaveReq);

	/*****************************************************
	 * 기관 회원 정보 수정
	 * @param userSaveReq
	 * @return
	*****************************************************/
	int updateInstUserInfo(UserSaveReq userSaveReq);

	/*****************************************************
	 * 기업 회원 정보 수정
	 * @param userSaveReq
	 * @return
	*****************************************************/
	int updateEntGrpUserInfo(UserSaveReq userSaveReq);

	/*****************************************************
	 * 사용자 상태 코드 수정
	 * @param userSaveReq
	 * @return
	*****************************************************/
	int updateUserSttCd(UserSaveReq userSaveReq);

	/*****************************************************
	 * 가입 완료 > 사용자 정보 수정
	 * @param userSaveReq
	 * @return
	*****************************************************/
	int updateJoinUserBas(UserSaveReq userSaveReq);

	/*****************************************************
	 * 탈퇴(USC040)를 제외한 상태의 하위 기업 그룹 목록 개수 조회
	 * @param entSelectReq
	 * @return
	*****************************************************/
	Integer selectDownEntGrpBasListCnt(UserSelectReq entSelectReq);

	/*****************************************************
	 * 회원 > 논리 파일 삭제
	 * @param fileDto
	 * @return
	*****************************************************/
	int updateDeleteFile(FileDto fileDto);

	/*****************************************************
	 * 사용자 정보 삭제
	 * @param userSaveReq
	 * @return
	*****************************************************/
	int deleteUserBas(UserSaveReq userSaveReq);

	/*****************************************************
	 * 기업 그룹 사용자 관리 정보 삭제
	 * @param userSaveReq
	 * @return
	*****************************************************/
	int deleteUserEntGrpUserMng(UserSaveReq userSaveReq);

	/*****************************************************
	 * 기업 그룹 기본 정보 삭제
	 * @param userSaveReq
	 * @return
	*****************************************************/
	int deleteEntGrpBas(UserSaveReq userSaveReq);

	/*****************************************************
	 * 기업 기본 정보 삭제
	 * @param userSaveReq
	 * @return
	*****************************************************/
	int deleteEntBas(UserSaveReq userSaveReq);

	/*****************************************************
	 * 탈퇴 완료 > 사용자 정보 수정
	 * @param userSaveReq
	 * @return
	*****************************************************/
	int updateWhdwlUserBas(UserSaveReq userSaveReq);

	/*****************************************************
	 * 기업 그룹 사용자 관리 정보 수정
	 * @param userSaveReq
	 * @return
	*****************************************************/
	int updateUserEntGrpUserMng(UserSaveReq userSaveReq);

	/*****************************************************
	 * 기업 그룹 기본 정보 수정
	 * @param userSaveReq
	 * @return
	*****************************************************/
	int updateEntGrpBas(UserSaveReq userSaveReq);

	/*****************************************************
	 * 기업 기본 정보 수정
	 * @param userSaveReq
	 * @return
	*****************************************************/
	int updateEntBas(UserSaveReq userSaveReq);

	/*****************************************************
	 * 탈퇴 회원 등록한 게시글 전부 삭제 처리
	 * @param chgUserSaveReq
	 * @return
	*****************************************************/
	int updateNttMngDelete(UserSaveReq chgUserSaveReq);

	/*****************************************************
	 * 회원 관심 키워드 목록 조회
	 * @param userSelectReq
	 * @return
	*****************************************************/
	List<UserPrdtSelectRes> selectUserPrdtClfChcMngList(UserSelectReq userSelectReq);

	/*****************************************************
	 * 회원 관심 키워드 삭제
	 * @param chgUserSaveReq
	 * @return
	*****************************************************/
	int deleteUserPrdtClfChcMng(UserSaveReq chgUserSaveReq);

	/*****************************************************
	 * 회원 약관 동의 이력 목록 개수 조회 > [내손안의 시험인증 약관]
	 * @param userSelectReq
	 * @return
	*****************************************************/
	int selectEntGrpAgreTrmsCnt(UserSelectReq userSelectReq);

	/*****************************************************
	 * 회원 약관 동의 이력 목록 조회 > [내손안의 시험인증 약관]
	 * @param userSelectReq
	 * @return
	*****************************************************/
	List<UserSelectRes> selectEntGrpAgreTrmsList(UserSelectReq userSelectReq);

}

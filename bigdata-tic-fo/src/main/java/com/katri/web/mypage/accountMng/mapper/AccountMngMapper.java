package com.katri.web.mypage.accountMng.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.common.tlds.model.CommonTldRes;
import com.katri.web.mypage.accountMng.model.AccountMngPrdtSelectRes;
import com.katri.web.mypage.accountMng.model.AccountMngSaveReq;
import com.katri.web.mypage.accountMng.model.AccountMngSelectReq;
import com.katri.web.mypage.accountMng.model.AccountMngSelectRes;

@Repository
@Mapper
@MainMapperAnnotation
public interface AccountMngMapper {

	/*****************************************************
	 * [계정 관리] > 계정 관리 목록 개수 조회
	 * @param accountMngSelectReq
	 * @return
	*****************************************************/
	Integer selectAccountMngListCnt(AccountMngSelectReq accountMngSelectReq);

	/*****************************************************
	 * [계정 관리] > 계정 관리 목록 데이터 조회
	 * @param accountMngSelectReq
	 * @return
	*****************************************************/
	List<AccountMngSelectRes> selectAccountMngList(AccountMngSelectReq accountMngSelectReq);

	/*****************************************************
	 * [계정 관리] > 계정 관리 상세 데이터 조회
	 * @param accountMngSelectReq
	 * @return
	*****************************************************/
	AccountMngSelectRes selectAccountMngDetail(AccountMngSelectReq accountMngSelectReq);

	/*****************************************************
	 * [계정 관리] > 회원 관심 키워드 목록 조회
	 * @param accountMngSelectReq
	 * @return
	*****************************************************/
	List<AccountMngPrdtSelectRes> selectUserPrdtClfChcMngList(AccountMngSelectReq accountMngSelectReq);

	/*****************************************************
	 * [계정 관리] > 회원 상태 코드 수정
	 * @param chgAccountSaveReq
	 * @return
	*****************************************************/
	int updateUserSttCd(AccountMngSaveReq chgAccountSaveReq);

	/*****************************************************
	 * [계정 관리] > 가입 승인 - 사용자 정보 수정
	 * @param chgAccountSaveReq
	 * @return
	*****************************************************/
	int updateJoinUserBas(AccountMngSaveReq chgAccountSaveReq);

	/*****************************************************
	 * [계정 관리] > 가입 반려 - 사용자 정보 DB 삭제
	 * @param chgAccountSaveReq
	 * @return
	*****************************************************/
	int deleteUserBas(AccountMngSaveReq chgAccountSaveReq);

	/*****************************************************
	 * [계정 관리] > 가입 반려 - 기업 사용자 관리 정보 DB 삭제
	 * @param chgAccountSaveReq
	 * @return
	*****************************************************/
	int deleteEntGrpUserMng(AccountMngSaveReq chgAccountSaveReq);

	/*****************************************************
	 * [계정 관리] > 가입 반려 - 기관 사용자 관리 정보 DB 삭제
	 * @param chgAccountSaveReq
	 * @return
	*****************************************************/
	int deleteInstUserMng(AccountMngSaveReq chgAccountSaveReq);

	/*****************************************************
	 * [계정 관리] > 가입 반려 - 사용자 관심 키워드 정보 DB 삭제
	 * @param chgAccountSaveReq
	 * @return
	*****************************************************/
	int deleteUserPrdtClfChcMng(AccountMngSaveReq chgAccountSaveReq);

	/*****************************************************
	 * [계정 관리] > 탈퇴 완료 - 사용자 정보 수정
	 * @param chgAccountSaveReq
	 * @return
	*****************************************************/
	int updateWhdwlUserBas(AccountMngSaveReq chgAccountSaveReq);

	/*****************************************************
	 * [계정 관리] > 탈퇴 완료 - 기관 사용자 관리 정보 수정
	 * @param chgAccountSaveReq
	 * @return
	*****************************************************/
	int updateWhdwlInstUserMng(AccountMngSaveReq chgAccountSaveReq);

	/*****************************************************
	 * [계정 관리] > 탈퇴 완료 - 기업 사용자 관리 정보 수정
	 * @param chgAccountSaveReq
	 * @return
	*****************************************************/
	int updateWhdwlEntGrpUserMng(AccountMngSaveReq chgAccountSaveReq);

	/*****************************************************
	 * [계정 관리] > 탈퇴 완료 - 게시글 관련 삭제 여부 처리
	 * @param chgAccountSaveReq
	 * @return
	*****************************************************/
	int updateWhdwlNttMngDelete(AccountMngSaveReq chgAccountSaveReq);

	/*****************************************************
	 * [계정 관리] > 검색 조건 회원 상태 목록 조회
	 * @return
	*****************************************************/
	List<CommonTldRes> selectSearchUserSttCdList();

	/*****************************************************
	 * [계정 관리] > 가입 반려 - 사용자 정보 수정
	 * @param chgAccountSaveReq
	 * @return
	*****************************************************/
	int updateRejectUserBas(AccountMngSaveReq chgAccountSaveReq);

	/*****************************************************
	 * [계정 관리] > 가입 반려 - 기관 사용자 정보 수정
	 * @param chgAccountSaveReq
	 * @return
	*****************************************************/
	int updateRejectInstUserMng(AccountMngSaveReq chgAccountSaveReq);

}

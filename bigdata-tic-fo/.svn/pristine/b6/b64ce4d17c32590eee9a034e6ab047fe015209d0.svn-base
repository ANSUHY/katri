package com.katri.web.join.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.comm.model.CommReq;
import com.katri.web.join.model.JoinPrdtSelectRes;
import com.katri.web.join.model.JoinSaveReq;
import com.katri.web.join.model.JoinSelectReq;
import com.katri.web.join.model.JoinSelectRes;

@Repository
@Mapper
@MainMapperAnnotation
public interface JoinMapper {

	/*****************************************************
	 * [회원 가입] > 약관 타입 별 정보 조회
	 * @param joinSelectReq
	 * @return
	*****************************************************/
	JoinSelectRes selectJoinTrmsDetail(JoinSelectReq joinSelectReq);

	/*****************************************************
	 * [회원 가입] > 인증 번호 DB 저장
	 * @param joinSaveReq
	 * @return
	*****************************************************/
	int insertCertNoSndngHist(JoinSaveReq joinSaveReq);

	/*****************************************************
	 * [회원 가입] > 이메일 가장 최신 인증 번호 가져오기
	 * @param joinSelectReq
	 * @return
	*****************************************************/
	JoinSelectRes selectCertNoOfEmlAddr(JoinSelectReq joinSelectReq);

	/*****************************************************
	 * [회원 가입] > 아이디 중복 체크 검사
	 * @param joinSelectReq
	 * @return
	*****************************************************/
	int selectIdDpcnChkCnt(JoinSelectReq joinSelectReq);

	/*****************************************************
	 * [회원 가입] > 약관 동의 이력 정보 등록
	 * @param trmsJoinSaveReq
	 * @return
	*****************************************************/
	int insertTrmsAgreHist(JoinSaveReq trmsJoinSaveReq);

	/*****************************************************
	 * [회원 가입] > 권한 관리 그룹 번호 조회
	 * @param joinSaveReq
	 * @return
	*****************************************************/
	String selectAuthrtGrpSn(JoinSaveReq joinSaveReq);

	/*****************************************************
	 * [회원 가입] > 사용자 정보 등록
	 * @param joinSaveReq
	 * @return
	*****************************************************/
	int insertUserBas(JoinSaveReq joinSaveReq);

	/*****************************************************
	 * [회원 가입] > 관심 키워드 등록
	 * @param prdtJoinSaveReq
	 * @return
	*****************************************************/
	int insertUserPrdtClfChcMng(JoinSaveReq prdtJoinSaveReq);

	/*****************************************************
	 * [회원 가입] > 기관 사용자 정보 등록
	 * @param joinSaveReq
	 * @return
	*****************************************************/
	int insertInstUserMng(JoinSaveReq joinSaveReq);

	/*****************************************************
	 * [회원 가입] > 기관 마스터 메일 주소 조회
	 * @param joinSaveReq
	 * @return
	*****************************************************/
	String selectInstMasterEmailAddr(JoinSaveReq joinSaveReq);

	/*****************************************************
	 * [회원 가입] > 기업 그룹 목록 조회
	 * @return
	*****************************************************/
	List<JoinSelectRes> selectEntGrpList();

	/*****************************************************
	 * [회원 가입] > 기업 사용자 정보 등록
	 * @param joinSaveReq
	 * @return
	*****************************************************/
	int insertEntGrpUserMng(JoinSaveReq joinSaveReq);

	/*****************************************************
	 * [회원 가입] > 기업 마스터 메일 주소 조회
	 * @param joinSaveReq
	 * @return
	*****************************************************/
	String selectEntMasterEmailAddr(JoinSaveReq joinSaveReq);

	/*****************************************************
	 * [회원 가입] > 해당 사업자 등록번호로 상호명 조회
	 * @param joinSelectReq
	 * @return
	*****************************************************/
	String selectBrnoEntNm(JoinSelectReq joinSelectReq);

	/*****************************************************
	 * [회원 가입] > 사업자등록번호 + 그룹ID + 그룹명 중복 체크 검사
	 * @param joinSelectReq
	 * @return
	*****************************************************/
	int selectEntGrpDpcnChkCnt(JoinSelectReq joinSelectReq);

	/*****************************************************
	 * [회원 가입] > 기업 그룹 정보 등록 시작
	 * @param joinSaveReq
	 * @return
	*****************************************************/
	int insertEntGrpBas(JoinSaveReq joinSaveReq);

	/*****************************************************
	 * [회원 가입] > 사업자 등록번호 유무 체크
	 * @param joinSelectReq
	 * @return
	*****************************************************/
	int selectEntBrnoChkCnt(JoinSelectReq joinSelectReq);

	/*****************************************************
	 * [회원 가입] > 기업 정보 등록 시작
	 * @param joinSaveReq
	 * @return
	*****************************************************/
	int insertEntBas(JoinSaveReq joinSaveReq);

	/*****************************************************
	 * 관리자 메일 주소 조회
	 * @param joinSaveReq
	 * @return
	*****************************************************/
	String selectEmtAdminEmailAddr(JoinSaveReq joinSaveReq);

	/*****************************************************
	 * [회원 가입] > 기관 목록 조회
	 * @return
	*****************************************************/
	List<JoinSelectRes> selectInstList();

	/*****************************************************
	 * [회원 가입] > 관심 키워드 대분류 목록 조회
	 * @return
	*****************************************************/
	List<JoinPrdtSelectRes> selectStdLgclfCd();

	/*****************************************************
	 * [회원 가입] > 관심 키워드 중분류 목록 조회
	 * @param commReq
	 * @return
	*****************************************************/
	List<JoinPrdtSelectRes> selectStdMlclfCdList(CommReq commReq);

	/*****************************************************
	 * [회원 가입] > [데이터 분석 환경] api 호출 USER 생성 ID 값 등록
	 * @param joinSaveReq
	 * @return
	*****************************************************/
	int updateAnslEnvUserId(JoinSaveReq joinSaveReq);

	/*****************************************************
	 * [회원 가입] > 연계 정보 중복 체크
	 * @param joinSelectReq
	 * @return
	*****************************************************/
	int selectUserLinkInfoDpcnChkCnt(JoinSelectReq joinSelectReq);

	/*****************************************************
	 * [회원 가입] > 인증 메일 정보 조회
	 * @return
	*****************************************************/
	JoinSelectRes selectCertMailSendChkInfo();

	/*****************************************************
	 * [회원 가입] > 전송된 인증 메일 건수 조회
	 * @param joinSelectReq
	 * @return
	*****************************************************/
	Integer selectCertMailSendCount(JoinSelectReq joinSelectReq);

	/*****************************************************
	 * [회원 가입] > 인증 번호 실패 횟수 증가
	 * @param certFilInfo
	 * @return
	*****************************************************/
	int updateCertNoFailCnt(JoinSaveReq certFilInfo);

}

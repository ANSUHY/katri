package com.katri.web.system.code.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.system.code.model.CodeSaveReq;
import com.katri.web.system.code.model.CodeSelectReq;
import com.katri.web.system.code.model.CodeSelectRes;

@Repository
@Mapper
@MainMapperAnnotation
public interface CodeMapper {

	/*****************************************************
	 * 공통그룹코드 목록 조회
	 * @param codeSelectReq
	 * @return 공통그룹코드 목록
	*****************************************************/
	List<CodeSelectRes> selectComnGrpCdList(CodeSelectReq codeSelectReq);

	/*****************************************************
	 * 공통그룹코드 단건 조회
	 * @param commGrpCd
	 * @return 공통그룹코드 정보
	*****************************************************/
	CodeSelectRes selectComnGrpCdDetail(String commGrpCd);

	/*****************************************************
	 * 공통그룹코드 추가
	 * @param codeSaveReq
	 * @return 성공 카운트
	*****************************************************/
	int insertComnGrpCd(CodeSaveReq codeSaveReq);

	/*****************************************************
	 * 공통그룹코드 수정
	 * @param codeSaveReq
	 * @return 성공 카운트
	*****************************************************/
	int updateComnGrpCd(CodeSaveReq codeSaveReq);

	/*****************************************************
	 * 공통코드 목록 조회
	 * @param codeSelectReq
	 * @return 공통코드 목록
	*****************************************************/
	List<CodeSelectRes> selectComnCdList(CodeSelectReq codeSelectReq);

	/*****************************************************
	 * 공통그룹 단건 조회
	 * @param codeSelectReq
	 * @return 공통그룹 정보
	*****************************************************/
	CodeSelectRes selectComnCdDetail(CodeSelectReq codeSelectReq);

	/*****************************************************
	 * 공통그룹 추가
	 * @param codeSaveReq
	 * @return 성공 카운트
	*****************************************************/
	int insertComnCd(CodeSaveReq codeSaveReq);

	/*****************************************************
	 * 공통그룹 수정
	 * @param codeUpdateReq
	 * @return 성공 카운트
	*****************************************************/
	int updateComnCd(CodeSaveReq codeSaveReq);

	/*****************************************************
	 * 공통코드 순서 정렬
	 * @param codeSaveReq
	 * @return 성공카운트
	*****************************************************/
	int updateComnCdSeq(CodeSaveReq codeSaveReq);

	/*****************************************************
	 * 공통그룹코드/공통코드 데이터 존재 여부 (갯수)
	 * @param codeSelectReq
	 * @return 데이터 갯수
	*****************************************************/
	int countComnCd(CodeSelectReq codeSelectReq);

	/*****************************************************
	 * 공통그룹코드 데이터 존재 여부 (갯수)
	 * @param codeSelectReq
	 * @return 데이터 갯수
	*****************************************************/
	int countComnGrpCd(CodeSelectReq codeSelectReq);

}

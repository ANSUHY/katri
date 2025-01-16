package com.katri.common.tlds.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.common.tlds.model.CommonTldReq;
import com.katri.common.tlds.model.CommonTldRes;


/***************************************************
 * <ul>
 * <li>업무 그룹명 : 공통 TLD 정보</li>
 * <li>서브 업무명 : 공통 TLD 정보</li>
 * <li>설       명 : 공통 TLD Mapper</li>
 * <li>작   성  자 : FCS</li>
 * <li>작   성  일 : 2021. 03. 14.</li>
 * </ul>
 * <pre>
 * ======================================
 * 변경자/변경일 :
 * 변경사유/내역 :
 * ======================================
 * </pre>
 ***************************************************/
@Repository
@Mapper
@MainMapperAnnotation
public interface CommonTldMapper {

	/*****************************************************
	 * 공통코드 목록 조회
	 * @param CommonTldReq 검색조건
	 * @return List<CommonTldRes> 공통코드 목록 정보
	 *****************************************************/
	List<CommonTldRes> selectCodeList(CommonTldReq commonTldReq);
}

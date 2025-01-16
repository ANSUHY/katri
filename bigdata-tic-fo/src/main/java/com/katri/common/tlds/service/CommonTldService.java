package com.katri.common.tlds.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.tlds.mapper.CommonTldMapper;
import com.katri.common.tlds.model.CommonTldReq;
import com.katri.common.tlds.model.CommonTldRes;

import lombok.RequiredArgsConstructor;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 공통 TLD 정보</li>
 * <li>서브 업무명 : 공통 TLD 정보</li>
 * <li>설       명 : 공통 TLD Service</li>
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
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class CommonTldService {

	/**
	 * Battery Pack 설치 현황 mapper
	 */
	private final CommonTldMapper commonTldMapper;

	/*****************************************************
	 * 공통코드 목록 조회
	 * @param CommonTldReq 검색조건
	 * @return 공통코드 목록 정보
	 *****************************************************/
	public List<CommonTldRes> selectCodeList(CommonTldReq commonTldReq) {
		return commonTldMapper.selectCodeList(commonTldReq);
	}
}

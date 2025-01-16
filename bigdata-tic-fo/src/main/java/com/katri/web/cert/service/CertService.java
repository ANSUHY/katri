package com.katri.web.cert.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.web.cert.mapper.CertMapper;
import com.katri.web.cert.model.CertRes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
@Slf4j
public class CertService {
	private final CertMapper certMapper;

	/*****************************************************
	 * 인증정보 리스트
	 * @param HashMap<String, Object> 검색조건
	 * @return 인증정보 정보
	 *****************************************************/
	public List<CertRes> selectList(Map<String, Object> param) {
		return this.certMapper.selectList(param);
	}

	/*****************************************************
	 * 인증정보 카운트
	 * @param HashMap<String, Object> 검색조건
	 * @return 인증정보 정보
	 *****************************************************/
	public int selectListCount(Map<String, Object> param) {
		return this.certMapper.selectListCount(param);
	}

	/*****************************************************
	 * 인증정보 상세
	 * @param HashMap<String, Object> 검색조건
	 * @return 인증정보 상세
	 *****************************************************/
	public CertRes selectInfo(Map<String, Object> param) {
		return this.certMapper.selectInfo(param);
	}

}

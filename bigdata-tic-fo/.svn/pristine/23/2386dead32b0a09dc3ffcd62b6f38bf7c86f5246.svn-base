package com.katri.web.svc.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.web.svc.mapper.SvcMapper;
import com.katri.web.svc.model.SvcSelectReq;
import com.katri.web.svc.model.SvcSelectRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class SvcService {

	/** [QR랜딩페이지]  Mapper */
	private final SvcMapper svcMapper;

	/*****************************************************
	 * 인증 상세
	 * @param svcSelectReq
	 * @return SvcSelectRes [인증데이터 조회]  상세
	 * @throws Exception
	*****************************************************/
	public SvcSelectRes getRfCertDataDetail(SvcSelectReq svcSelectReq) throws Exception {

		// [[0]]. 반환할 정보들
		SvcSelectRes certDetail = null;

		// [[1]]. Validation check
		/* 1-1. TAGET 기관아이디 */
		if(svcSelectReq.getTargetInstId() == null  || "".equals(svcSelectReq.getTargetInstId())) {
			throw new CustomMessageException("result-message.messages.common.message.wrong.appr"); //잘못된 접근입니다.
		}
		/* 1-2. TAGET 인증번호 */
		if(svcSelectReq.getTargetCertNo() == null  || "".equals(svcSelectReq.getTargetCertNo())) {
			throw new CustomMessageException("result-message.messages.common.message.wrong.appr"); //잘못된 접근입니다.
		}

		// [[2]]. 상세 조회
		certDetail = svcMapper.selectCertDataDetail(svcSelectReq);

		if(certDetail != null) {

			//기관명(영문)_소문자
			certDetail.setInstEngNmLower(certDetail.getInstEngNm().toLowerCase());

			// [[3]]. 이미지 파일 정보 셋팅
			List<String> imgNames = svcMapper.selectCertDataImgList(svcSelectReq);
			List<String> newImgNames = new ArrayList<>();
			for(String imgName : imgNames) {
				imgName =  URLEncoder.encode(imgName, "UTF-8");
				newImgNames.add(imgName);
			}
			certDetail.setImgNameList(newImgNames);

		} else {

			// 반환값 셋팅
			throw new CustomMessageException("result-message.messages.common.message.no.data"); //데이터가 없습니다.

		}

		return certDetail;

	}


}

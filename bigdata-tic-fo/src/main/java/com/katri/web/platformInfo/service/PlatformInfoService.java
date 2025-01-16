package com.katri.web.platformInfo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.web.platformInfo.mapper.PlatformInfoMapper;
import com.katri.web.platformInfo.model.PlatformInfoSelectReq;
import com.katri.web.platformInfo.model.PlatformInfoSelectRes;
import com.nhncorp.lucy.security.xss.XssPreventer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class PlatformInfoService {

	/** [플랫폼 소개] Mapper */
	private final PlatformInfoMapper platformInfoMapper;

	/*****************************************************
	 * [플랫폼 소개] 각 페이지에 맞는 데이터 조회
	 * @param PlatformInfoSelectReq
	 * @return PlatformInfoSelectRes 페이지콘텐츠 정보
	 * @throws Exception
	*****************************************************/
	public PlatformInfoSelectRes getPlatformInfoData(PlatformInfoSelectReq platformInfoSelectReq) throws Exception{

		// [[0]]. 반환할 정보들
		PlatformInfoSelectRes platformInfoData = null;

		// [[1]]. [플랫폼 소개] 각 페이지에 맞는 데이터 조회
		platformInfoData = platformInfoMapper.selectPlatformInfoData(platformInfoSelectReq);
		/* 딱히 할 필요 없을듯
 		if(platformInfoData == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); //데이터가 없습니다.
		}
		*/

		// [[2]]. 메뉴구성내용 UNESCAPE 처리
		if(platformInfoData != null) {
			String menuCptnCn = platformInfoData.getMenuCptnCn();
			platformInfoData.setMenuCptnCnUnescape(XssPreventer.unescape(menuCptnCn));
		}

		return platformInfoData;
	}



}

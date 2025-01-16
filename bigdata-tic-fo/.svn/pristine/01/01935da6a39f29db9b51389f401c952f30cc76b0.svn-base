package com.katri.web.particiLounge.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.web.particiLounge.mapper.ParticiLoungeMapper;
import com.katri.web.particiLounge.model.ParticiLoungeSelectReq;
import com.katri.web.particiLounge.model.ParticiLoungeSelectRes;
import com.nhncorp.lucy.security.xss.XssPreventer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class ParticiLoungeService {

	/** [참여기관 라운지] Mapper */
	private final ParticiLoungeMapper particiLoungeMapper;

	/*****************************************************
	 * [참여기관 라운지] 각 페이지에 맞는 데이터 조회
	 * @param ParticiLoungeSelectReq
	 * @return ParticiLoungeSelectRes 페이지콘텐츠 정보
	 * @throws Exception
	*****************************************************/
	public ParticiLoungeSelectRes getParticiLoungeData(ParticiLoungeSelectReq particiLoungeSelectReq) throws Exception{

		// [[0]]. 반환할 정보들
		ParticiLoungeSelectRes particiLoungeData = null;

		// [[1]]. [참여기관 라운지] 각 페이지에 맞는 데이터 조회
		particiLoungeData = particiLoungeMapper.selectParticiLoungeData(particiLoungeSelectReq);
		/* 딱히 할 필요 없을듯
 		if(particiLoungeData == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); //데이터가 없습니다.
		}
		*/

		// [[2]]. 메뉴구성내용 UNESCAPE 처리
		if(particiLoungeData != null) {
			String menuCptnCn = particiLoungeData.getMenuCptnCn();
			particiLoungeData.setMenuCptnCnUnescape(XssPreventer.unescape(menuCptnCn));
		}

		return particiLoungeData;
	}



}

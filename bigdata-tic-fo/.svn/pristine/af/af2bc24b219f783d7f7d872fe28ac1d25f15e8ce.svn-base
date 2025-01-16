package com.katri.web.platformSvc.qrSvc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.web.platformSvc.qrSvc.mapper.QrSvcMapper;
import com.katri.web.platformSvc.qrSvc.model.QrSvcSelectReq;
import com.katri.web.platformSvc.qrSvc.model.QrSvcSelectRes;
import com.nhncorp.lucy.security.xss.XssPreventer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class QrSvcService {

	/** [플랫폼 서비스] > [통합QR 서비스 안내] Mapper */
	private final QrSvcMapper qrSvcMapper;

	/*****************************************************
	 * [플랫폼 서비스] ] > [통합QR 서비스 안내] 각 페이지에 맞는 데이터 조회
	 * @param QrSvcSelectReq
	 * @return QrSvcSelectRes 페이지콘텐츠 정보
	 * @throws Exception
	*****************************************************/
	public QrSvcSelectRes getQrSvcData(QrSvcSelectReq qrSvcSelectReq) throws Exception{

		// [[0]]. 반환할 정보들
		QrSvcSelectRes qrSvcData = null;

		// [[1]]. [플랫폼 서비스] ] > [통합QR 서비스 안내] 각 페이지에 맞는 데이터 조회
		qrSvcData = qrSvcMapper.selectQrSvcData(qrSvcSelectReq);
		/* 딱히 할 필요 없을듯
 		if(qrSvcData == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); //데이터가 없습니다.
		}
		*/

		// [[2]]. 메뉴구성내용 UNESCAPE 처리
		if(qrSvcData != null) {
			String menuCptnCn = qrSvcData.getMenuCptnCn();
			qrSvcData.setMenuCptnCnUnescape(XssPreventer.unescape(menuCptnCn));
		}

		return qrSvcData;
	}



}

package com.katri.web.platformSvc.qrSvc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.platformSvc.qrSvc.model.QrSvcSelectReq;
import com.katri.web.platformSvc.qrSvc.model.QrSvcSelectRes;


@Repository
@Mapper
@MainMapperAnnotation
public interface QrSvcMapper {

	/*****************************************************
	 * [플랫폼 서비스] > [통합QR 서비스 안내] 각 페이지에 맞는 데이터 조회
	 * @param qrSvcSelectReq
	 * @return [플랫폼 서비스] _데이터
	*****************************************************/
	QrSvcSelectRes selectQrSvcData(QrSvcSelectReq qrSvcSelectReq);

}

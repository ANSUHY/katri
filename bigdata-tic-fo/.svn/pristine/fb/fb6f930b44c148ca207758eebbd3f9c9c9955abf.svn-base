package com.katri.web.dataUsageGuide.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.web.dataUsageGuide.mapper.CertDataMapper;
import com.katri.web.dataUsageGuide.model.CertDataSelectReq;
import com.katri.web.dataUsageGuide.model.CertDataSelectRes;
import com.nhncorp.lucy.security.xss.XssPreventer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class CertDataService {

	/** [인증데이터 조회] Mapper */
	private final CertDataMapper certDataMapper;

	/*****************************************************
	 * [인증데이터 조회] 리스트 개수
	 * @param certDataSelectReq
	 * @return Integer [인증데이터 조회] 리스트 개수
	 * @throws Exception
	*****************************************************/
	public Integer getCertDataCnt(CertDataSelectReq certDataSelectReq) throws Exception{

		// [[0]]. 반환할 정보들
		int certDataCtnt = 0;

		// [[1]]. 개수 조회
		certDataCtnt = certDataMapper.selectCertDataCount(certDataSelectReq);

		return certDataCtnt;
	}

	/*****************************************************
	 * [인증데이터 조회] 리스트
	 * @param certDataSelectReq
	 * @return List<CertDataSelectRes> [인증데이터 조회] 리스트
	 * @throws Exception
	*****************************************************/
	public List<CertDataSelectRes> getCertDataList(CertDataSelectReq certDataSelectReq) throws Exception{

		// [[0]]. 반환할 정보들
		List<CertDataSelectRes> certDataList = null;

		// [[1]]. 리스트 조회
		certDataList = certDataMapper.selectCertDataList(certDataSelectReq);

		return certDataList;
	}

	/*****************************************************
	 *[인증데이터 조회] 상세
	 * @param certDataSelectReq
	 * @return CertDataSelectRes [인증데이터 조회]  상세
	 * @throws Exception
	*****************************************************/
	public CertDataSelectRes getCertDataDetail(CertDataSelectReq certDataSelectReq) throws Exception {

		// [[0]]. 반환할 정보들
		CertDataSelectRes certDataDetail = null;

		// [[1]]. Validation check
		/* 1-1. TAGET 기관아이디 */
		if(certDataSelectReq.getTargetInstId() == null  || "".equals(certDataSelectReq.getTargetInstId())) {
			throw new CustomMessageException("result-message.messages.common.message.wrong.appr"); //잘못된 접근입니다.
		}
		/* 1-2. TAGET 인증번호 */
		if(certDataSelectReq.getTargetCertNo() == null  || "".equals(certDataSelectReq.getTargetCertNo())) {
			throw new CustomMessageException("result-message.messages.common.message.wrong.appr"); //잘못된 접근입니다.
		}

		// [[2]]. 상세 조회
		certDataDetail = certDataMapper.selectCertDataDetail(certDataSelectReq);

		if(certDataDetail != null) {

			//기관명(영문)_소문자
			certDataDetail.setInstEngNmLower(certDataDetail.getInstEngNm().toLowerCase());

			// [[3]]. 이미지 파일 정보 셋팅
			List<String> imgNames = certDataMapper.selectCertDataImgList(certDataSelectReq);
			List<String> newImgNames = new ArrayList<>();
			for(String imgName : imgNames) {
				imgName =  URLEncoder.encode(imgName, "UTF-8");
				newImgNames.add(imgName);
			}
			certDataDetail.setImgNameList(newImgNames);

		} else {

			// 반환값 셋팅
			throw new CustomMessageException("result-message.messages.common.message.no.data"); //데이터가 없습니다.

		}

		return certDataDetail;

	}

	/*****************************************************
	 * [##인증기관##] 리스트_[pt_co_inst_bas]관련 데이터
	 * @return List<CertDataSelectRes> [##인증기관##] 리스트
	 * @throws Exception
	*****************************************************/
	public List<CertDataSelectRes> getCoInstList() throws Exception{

		// [[0]]. 반환할 정보들
		List<CertDataSelectRes> coInstList = null;

		// [[1]]. 리스트 조회
		coInstList = certDataMapper.selectCoInstList();

		return coInstList;
	}

	/*****************************************************
	 * [##통합공통상세코드##] 리스트_[pt_co_intgr_comn_dtl_cd]관련 데이터
	 * @param grpCd 그룹코드
	 * @return List<CertDataSelectRes>  [##통합공통상세코드##] 리스트
	 * @throws Exception
	*****************************************************/
	public List<CertDataSelectRes> getPtComnDtlCdList(String grpCd) throws Exception{

		// [[0]]. 반환할 정보들
		List<CertDataSelectRes> ptComnDtlCdList = null;

		// [[1]]. 리스트 조회
		ptComnDtlCdList = certDataMapper.selectPtComnDtlCdList(grpCd);

		return ptComnDtlCdList;
	}

	/*****************************************************
	 * [##CO_법정제품분류##] 리스트_[pt_co_stty_prdt_clf_cd]관련 데이터
	 * @return List<CertDataSelectRes>  [##CO_법정제품분류##] 리스트
	 * @throws Exception
	*****************************************************/
	public List<CertDataSelectRes> getPtCoSttyPrdtClfCdList() throws Exception{

		// [[0]]. 반환할 정보들
		List<CertDataSelectRes> ptCoSttyPrdtClfCdList = null;

		// [[1]]. 리스트 조회
		ptCoSttyPrdtClfCdList = certDataMapper.selectPtCoSttyPrdtClfCdList();

		return ptCoSttyPrdtClfCdList;
	}

	/*****************************************************
	 * [##설명문장##] 페이지에 맞는 데이터 조회 + 내용 UNESCAPE
	 * @return UNESCAPE 된 내용
	 * @throws Exception
	*****************************************************/
	public String getExplanMenuCptnCnUnescapeData() throws Exception{

		// [[0]]. 반환할 정보들
		String menuCptnCnUnescape = "";

		// [[1]]. [##설명문장##] 페이지에 맞는 데이터 조회
		String menuCptnCn = "";
		menuCptnCn = certDataMapper.selectExplanMenuCptnCnData();
		/* 딱히 할 필요 없을듯
 		if(particiLoungeData == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); //데이터가 없습니다.
		}
		*/

		// [[2]]. [##설명문장##] UNESCAPE 처리
		if(menuCptnCn != null && ! "".equals(menuCptnCn)) {
			menuCptnCnUnescape = XssPreventer.unescape(menuCptnCn);
		}

		return menuCptnCnUnescape;
	}

}

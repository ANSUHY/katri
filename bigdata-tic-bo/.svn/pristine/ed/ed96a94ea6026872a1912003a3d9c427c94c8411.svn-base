package com.katri.web.trms.trms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.trms.trms.model.TrmsSaveReq;
import com.katri.web.trms.trms.model.TrmsSelectReq;
import com.katri.web.trms.trms.model.TrmsSelectRes;


@Repository
@Mapper
@MainMapperAnnotation
public interface TrmsMapper {

	/*****************************************************
	 * trms 리스트 개수 조회
	 * @param trmsSelectReq
	 * @return 리스트개수
	 *****************************************************/
	int selectTrmsListCount(TrmsSelectReq trmsSelectReq);

	/*****************************************************
	 * trms 리스트 조회
	 * @param trmsSelectReq
	 * @return trms 리스트 조회
	 *****************************************************/
	List<TrmsSelectRes> selectTrmsList(TrmsSelectReq trmsSelectReq);

	/*****************************************************
	 * trms Detail 조회
	 * @param trms_sn
	 * @return trms Detail 조회
	 *****************************************************/
	TrmsSelectRes selectTrmsDetail(Integer trms_sn);

	/*****************************************************
	 * trms 사용여부 수정(trms USE_YN = '넘어온타입' 로 수정)
	 * @param TrmsSaveReq 사용여부 수정 정보
	 * @return 성공 카운트
	 *****************************************************/
	int updateChgTrmsUseYN(TrmsSaveReq trmsSaveReq);

	/*****************************************************
	 * trms 등록
	 * @param TrmsSaveReq trms 등록 정보
	 * @return 성공 카운트
	 *****************************************************/
	int insertTrms(TrmsSaveReq trmsSaveReq);

	/*****************************************************
	 * trms 수정
	 * @param TrmsSaveReq trms 등록 정보
	 * @return 성공 카운트
	 *****************************************************/
	int updateTrms(TrmsSaveReq trmsSaveReq);


}

package com.katri.batch.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.batch.model.BatchBeforeDrmncyMemDto;
import com.katri.batch.model.BatchChgDrmncyMemDto;
import com.katri.batch.model.BatchNotiInstEntGrpClctAgreDto;
import com.katri.batch.model.BtchExcnHistSaveReq;
import com.katri.common.datasource.MainMapperAnnotation;

@Repository
@Mapper
@MainMapperAnnotation
public interface BatchMapper {

	/*****************************************************
	 * [휴면회원 전환] 처리 필요한 리스트 조회
	 * @param BatchChgDrmncyMemDto
	 * @return [휴면회원 전환] 처리 필요한 리스트
	 *****************************************************/
	List<BatchChgDrmncyMemDto> selectChgDrmncyMemList(BatchChgDrmncyMemDto srchChgDrmncyMem);

	/*****************************************************
	 * [휴면회원 전환] 처리
	 * @param [휴면회원 전환] 처리 필요한 회원
	 * @return 성공 카운트
	 *****************************************************/
	int updateChgDrmncyMem(BatchChgDrmncyMemDto chgDrmncyMem);

	/*****************************************************
	 * [휴면회원 전환 30일전 알림 메일] 처리 필요한 리스트 조회
	 * @param BatchBeforeDrmncyMemDto
	 * @return [휴면회원 전환 30일전 알림 메일] 처리 필요한 리스트
	 *****************************************************/
	List<BatchBeforeDrmncyMemDto> selectBeforeDrmncyMemList(BatchBeforeDrmncyMemDto srchBeforeDrmncyMem);

	/*****************************************************
	 * [기업그룹수집동의여부 기관에게 알림 메일] 기관 MASTER 리스트 조회
	 * @return [기업그룹수집동의여부 기관에게 알림 메일] 기관 MASTER 리스트
	 *****************************************************/
	List<BatchNotiInstEntGrpClctAgreDto> selectInstMasterList();

	/*****************************************************
	 * [기업그룹수집동의여부 기관에게 알림 메일] 기관에 보낼 기업그룹수집동의여부리스트 조회
	 * @param BatchNotiInstEntGrpClctAgreDto
	 * @return [기업그룹수집동의여부 기관에게 알림 메일] 기관에 보낼 기업그룹수집동의여부리스트
	 *****************************************************/
	List<BatchNotiInstEntGrpClctAgreDto> selectInstEntGrpClctAgreList(BatchNotiInstEntGrpClctAgreDto srchInstEntGrpClctAgre);

	/*****************************************************
	 * 배치실행이력 입력
	 * @param btchExcnHistSaveReq 배치실행이력 정보
	 * @return 성공 카운트
	 *****************************************************/
	int insertBtchExcnHist(BtchExcnHistSaveReq btchExcnHistSaveReq);

}

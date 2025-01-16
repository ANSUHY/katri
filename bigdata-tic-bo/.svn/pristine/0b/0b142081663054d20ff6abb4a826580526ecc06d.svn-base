package com.katri.web.comm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.comm.model.EmlSndngHistSaveReq;
import com.katri.web.comm.model.MailMakeBodyDto;



/***************************************************
 * <ul>
 * <li>업무 그룹명 : Mail 업무</li>
 * <li>서브 업무명 : Mail 업무</li>
 * <li>설         명 : Mail 업무 관련 MAPPER/li>
 * <li>작   성   자 : ASH</li>
 * <li>작   성   일 : 2022. 10. 11.</li>
 * </ul>
 * <pre>
 * ======================================
 * 변경자/변경일 :
 * 변경사유/내역 :
 * --------------------------------------
 * 변경자/변경일 :
 * 변경사유/내역 :
 * ======================================
 * </pre>
 ***************************************************/
@Repository
@Mapper
@MainMapperAnnotation
public interface MailMapper {

	/*****************************************************
	 * 메일 TEMPLATE파일명 조회
	 * @param MailMakeBodyDto 조회 정보
	 * @return 메일 TEMPLATE파일명
	 *****************************************************/
	String selectMailTemplateNm(MailMakeBodyDto mailMakeBodyDto);

	/*****************************************************
	 * 이메일발송이력 입력
	 * @param emlSndngHistSaveReq 이메일발송이력 정보
	 * @return 성공 카운트
	 *****************************************************/
	int insertEmlSndngHist(EmlSndngHistSaveReq emlSndngHistSaveReq);

}

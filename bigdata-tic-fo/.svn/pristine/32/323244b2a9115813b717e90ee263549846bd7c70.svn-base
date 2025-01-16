package com.katri.web.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.ctnt.notice.model.NoticeSelectRes;
import com.katri.web.main.model.MainSelectReq;
import com.katri.web.main.model.MainSelectRes;

import io.swagger.annotations.Api;

@Repository
@Mapper
@MainMapperAnnotation
@Api(tags = {"메인 Mapper"})
public interface MainMapper {

	/*****************************************************
	 * 메인
	 * @return mainSelectRes
	 *****************************************************/
	MainSelectRes selectMainInfo();

	/*****************************************************
	 * [메인] 커뮤니티 게시글 4개 (공지/FAQ/자료실)
	 * @return mainSelectRes
	 *****************************************************/
	List<NoticeSelectRes> selectMainCommunityList(String nttTyCd);

	/*****************************************************
	 * [메인] 메인(모달팝업) 공지사항
	 * @return mainSelectRes
	 *****************************************************/
	List<NoticeSelectRes> selectMainVisualNoticeList(String nttTyCd);

	/*****************************************************
	 * [메인] 메인비주얼 이미지/텍스트 조회
	 * @return mainSelectRes
	 *****************************************************/
	List<MainSelectRes> selectMainVisualList(MainSelectReq mainSelectReq);

	/*****************************************************
	 * [메인] 팝업존 이미지 조회
	 * @return mainSelectRes
	 *****************************************************/
	List<MainSelectRes> selectPopUpZoneList(MainSelectReq mainSelectReq);

	/*****************************************************
	 * [메인_월별 인증현황_chart] TOP10 법정인증 품목 List
	 * @return mainSelectRes
	 *****************************************************/
	List<MainSelectRes> selectMainTopPrdtClfList();

	/*****************************************************
	 * [메인_월별 인증현황_chart] TOP10 법정인증 제품 List
	 * @return mainSelectRes
	 *****************************************************/
	List<MainSelectRes> selectMainTopPdctgList();

	/*****************************************************
	 * [메인_월별 인증현황_chart] TOP10 법정인증 제조국 List
	 * @return mainSelectRes
	 *****************************************************/
	List<MainSelectRes> selectMainTopMnftrCustcoNtnList();

}

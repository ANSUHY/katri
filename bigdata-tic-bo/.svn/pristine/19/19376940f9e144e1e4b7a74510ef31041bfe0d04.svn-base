package com.katri.web.hmpgCptn.popupZone.mapper;

import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.hmpgCptn.popupZone.model.PopupZoneSaveReq;
import com.katri.web.hmpgCptn.popupZone.model.PopupZoneSelectReq;
import com.katri.web.hmpgCptn.popupZone.model.PopupZoneSelectRes;

@Repository
@Mapper
@MainMapperAnnotation
public interface PopupZoneMapper {

	/*****************************************************
	 * 팝업존 목록 개수 조회
	 * @param popupZoneSelectReq
	 * @return
	*****************************************************/
	int getPopupCount(PopupZoneSelectReq popupZoneSelectReq);

	/*****************************************************
	 * 팝업존 목록 조회
	 * @param popupZoneSelectReq
	 * @return
	*****************************************************/
	List<PopupZoneSelectRes> selectPopupZoneList(PopupZoneSelectReq popupZoneSelectReq);

	/*****************************************************
	 * 팝업존 수정
	 * @param popupZoneSaveReq
	 * @return
	*****************************************************/
	int updatePopupZone(@Valid PopupZoneSaveReq popupZoneSaveReq);

	/*****************************************************
	 * 팝업 등록
	 * @param popupZoneSaveReq
	 * @return
	*****************************************************/
	int insertPopup(@Valid PopupZoneSaveReq popupZoneSaveReq);

	/*****************************************************
	 * 팝업 상세 조회
	 * @param popupZoneSaveReq
	 * @return
	*****************************************************/
	PopupZoneSelectRes selectPopupZoneDetail(PopupZoneSelectReq popupZoneSelectReq);

	/*****************************************************
	 * 팝업 순서 수정
	 * @param popupZoneSaveReq
	 * @return
	*****************************************************/
	int updatePopupZoneSeq(PopupZoneSaveReq popupZoneSaveReq);

}

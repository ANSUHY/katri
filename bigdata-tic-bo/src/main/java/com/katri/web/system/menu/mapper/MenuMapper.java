package com.katri.web.system.menu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.system.menu.model.MenuSaveReq;
import com.katri.web.system.menu.model.MenuSelectReq;
import com.katri.web.system.menu.model.MenuSelectRes;

@Repository
@Mapper
@MainMapperAnnotation
public interface MenuMapper {

	/*****************************************************
	 * menu 리스트 개수 조회
	 * @param menuSelectReq menu 조회 정보
	 * @return
	*****************************************************/
	int selectMenuListCount(MenuSelectReq menuSelectReq);

	/*****************************************************
	 * menu 리스트 조회
	 * @param menuSelectReq menu 조회 정보
	 * @return
	*****************************************************/
	List<MenuSelectRes> selectMenuList(MenuSelectReq menuSelectReq);

	/*****************************************************
	 * menu 등록
	 * @param menuSaveReq menu 저장 정보
	 * @return
	*****************************************************/
	int insertMenu(MenuSaveReq menuSaveReq);

	/*****************************************************
	 * menu 수정
	 * @param menuSaveReq menu 저장 정보
	 * @return
	*****************************************************/
	int updateMenu(MenuSaveReq menuSaveReq);

	/*****************************************************
	 * menu 상세 조회
	 * @param menuSelectReq
	 * @return
	*****************************************************/
	MenuSelectRes selectMenuDetail(MenuSelectReq menuSelectReq);

	/*****************************************************
	 * menu 순서 정렬
	 * @param menuSaveReq
	 * @return
	*****************************************************/
	int updateMenuSeq(MenuSaveReq menuSaveReq);

	/*****************************************************
	 * 해당 menu 전체 메뉴 일련 번호 수정
	 * @param menuSaveReq
	 * @return
	*****************************************************/
	int updateAllMenuSnVal(MenuSaveReq menuSaveReq);

	/*****************************************************
	 * 해당 menu 사용여부 수정
	 * @param tmpMenuSaveReq
	 * @return
	*****************************************************/
	int updateDownMenuUseYn(MenuSaveReq menuSaveReq);

}

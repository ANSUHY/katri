package com.katri.web.ctnt.menuCptn.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.ctnt.menuCptn.model.MenuCptnSaveReq;
import com.katri.web.ctnt.menuCptn.model.MenuCptnSelectReq;
import com.katri.web.ctnt.menuCptn.model.MenuCptnSelectRes;


@Repository
@Mapper
@MainMapperAnnotation
public interface MenuCptnMapper {

	/*****************************************************
	 * 메뉴구성관리 리스트 개수 조회
	 * @param menuCptnSelectReq
	 * @return 메뉴구성관리 리스트개수
	 *****************************************************/
	int selectMenuCptnListCount(MenuCptnSelectReq menuCptnSelectReq);

	/*****************************************************
	 * 메뉴구성관리 리스트 조회
	 * @param menuCptnSelectReq
	 * @return 메뉴구성관리 리스트 조회
	 *****************************************************/
	List<MenuCptnSelectRes> selectMenuCptnList(MenuCptnSelectReq menuCptnSelectReq);

	/*****************************************************
	 * menuCptn Detail 조회
	 * @param menuCptn_sn
	 * @return menuCptn Detail 조회
	 *****************************************************/
	MenuCptnSelectRes selectMenuCptnDetail(Integer menuCptn_sn);

	/*****************************************************
	 * menuCptn 사용여부 수정(menuCptn USE_YN = '넘어온타입' 로 수정)
	 * @param MenuCptnSaveReq 사용여부 수정 정보
	 * @return 성공 카운트
	 *****************************************************/
	int updateChgMenuCptnUseYN(MenuCptnSaveReq menuCptnSaveReq);

	/*****************************************************
	 * menuCptn 등록
	 * @param MenuCptnSaveReq menuCptn 등록 정보
	 * @return 성공 카운트
	 *****************************************************/
	int insertMenuCptn(MenuCptnSaveReq menuCptnSaveReq);

	/*****************************************************
	 * menuCptn 수정
	 * @param MenuCptnSaveReq menuCptn 등록 정보
	 * @return 성공 카운트
	 *****************************************************/
	int updateMenuCptn(MenuCptnSaveReq menuCptnSaveReq);


}

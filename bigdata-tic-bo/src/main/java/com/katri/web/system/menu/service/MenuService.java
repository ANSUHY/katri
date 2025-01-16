package com.katri.web.system.menu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.SessionUtil;
import com.katri.web.system.menu.mapper.MenuMapper;
import com.katri.web.system.menu.model.MenuSaveReq;
import com.katri.web.system.menu.model.MenuSaveRes;
import com.katri.web.system.menu.model.MenuSelectReq;
import com.katri.web.system.menu.model.MenuSelectRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class MenuService {

	/** Menu Mapper */
	private final MenuMapper menuMapper;

	/*****************************************************
	 * Menu 관리 리스트 개수
	 * @param menuSelectReq
	 * @return
	*****************************************************/
	public Integer getMenuCnt(MenuSelectReq menuSelectReq) throws Exception {

		int nTotCnt = 0;

		// 0. 메뉴 관리 리스트 개수 조회
		nTotCnt = menuMapper.selectMenuListCount(menuSelectReq);

		return nTotCnt;
	}

	/*****************************************************
	 * Menu 관리 리스트 조회
	 * @param menuSelectReq
	 * @return
	*****************************************************/
	public List<MenuSelectRes> getMenuList(MenuSelectReq menuSelectReq) throws Exception  {

		List<MenuSelectRes> menuList = null;

		// 0. 메뉴 관리 리스트 조회
		menuList = menuMapper.selectMenuList(menuSelectReq);

		return menuList;
	}

	/*****************************************************
	 * Menu 등록
	 * @param request
	 * @param menuSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public MenuSaveRes insertMenu(HttpServletRequest request, MenuSaveReq menuSaveReq) throws Exception {

		String strSaveErrMsgCode = "result-message.messages.common.message.save.error"; //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		// 0. 변수 설정
		MenuSaveRes menuSaveRes = new MenuSaveRes();
		int 		nSaveCount	= 0;

		// 1. 등록 시작
		nSaveCount = menuMapper.insertMenu(menuSaveReq);
		if(! (nSaveCount > 0 ) || menuSaveReq.getMenuSn() == null) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// 2. 등록된 전체 메뉴 일련 번호값 수정
		menuSaveReq.setAllMenuSnVal( menuSaveReq.getAllMenuSnVal() + menuSaveReq.getMenuSn() );

		nSaveCount = menuMapper.updateAllMenuSnVal(menuSaveReq);
		if( !(nSaveCount > 0 ) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// 3. 반환값 설정
		menuSaveRes.setMenuSn(menuSaveReq.getMenuSn());

		return menuSaveRes;
	}

	/*****************************************************
	 * Menu 수정
	 * @param request
	 * @param menuSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public MenuSaveRes updateMenu(HttpServletRequest request, MenuSaveReq menuSaveReq) throws Exception {

		String strSaveErrMsgCode = "result-message.messages.common.message.save.error"; //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		// 0. 변수 설정
		MenuSaveRes menuSaveRes = new MenuSaveRes();
		int 		nSaveCount	= 0;

		// 1. 수정 시작
		nSaveCount = menuMapper.updateMenu(menuSaveReq);

		if(! (nSaveCount > 0 ) || menuSaveReq.getMenuSn() == null) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		/*
		 * 2. 현재 메뉴 > 사용 여부 'N' 으로 수정한 경우
		 * 		-> 하위 메뉴들 사용여부 전부 'N'
		 */
		/** ========== 2. 저장 값 사용 여부 'N'인 경우 ========== */
		if( "N".equals(menuSaveReq.getUseYn()) ) {

			/** ========== 2_1. 현재 메뉴 정보 조회 및 전체 메뉴 조회 ========== */
			// 2_1_0. 현재 메뉴 정보 셋팅
			MenuSelectReq menuSelectReq = new MenuSelectReq();
			menuSelectReq.setTargetMenuSn(menuSaveReq.getMenuSn());

			// 2_1_1. 현재 메뉴 정보 조회
			MenuSelectRes 		menuSelectRes 	= menuMapper.selectMenuDetail(menuSelectReq);

			if( menuSelectRes == null) {
				throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}

			// 2_1_2. 하위 메뉴 리스트 조회 정보 셋팅
			menuSelectReq.setSearchSiteTyCd(menuSaveReq.getSiteTyCd());
			menuSelectReq.setSearchUseYn("Y"); // 사용중인 하위 메뉴만 조회
			menuSelectReq.setSearchDownMenuLvlVal( menuSelectRes.getMenuLvlVal() );

			// 2_1_3. 현재 메뉴보다 하위 레벨의 사용중인 메뉴 리스트 조회
			List<MenuSelectRes> menuList = menuMapper.selectMenuList(menuSelectReq);

			/** ====================== [2_1] ====================== */

			/** ========== [2_2]. 하위 메뉴의 전체 메뉴 중 해당 메뉴 있으면 하위 메뉴 사용여부 수정 ========== */
			String strMenuSn = Integer.toString(menuSaveReq.getMenuSn()); // 현재 메뉴 번호

			// 2_2_0. 하위 레벨 리스트 있는 경우
			if( menuList.size() > 0 ) {

				for( MenuSelectRes tmpMenuSelectRes : menuList ) {

					// 2_2_0_1. 하위 레벨의 전체 메뉴 번호
					String[] tmpAllMenuSnVal = tmpMenuSelectRes.getAllMenuSnVal().split(",");

					// 2_2_0_2. 하위 레벨의 전체 메뉴 번호 중 현재 수정한 메뉴가 상위 메뉴에 속하는지 확인
					for( String tmpMenuSn : tmpAllMenuSnVal ) {
						if( tmpMenuSn.equals(strMenuSn) ) { // 메뉴 번호가 속해 있는 경우

							// 2_2_0_2_0. 수정할 메뉴 정보 셋팅
							MenuSaveReq tmpMenuSaveReq = new MenuSaveReq();
							tmpMenuSaveReq.setMenuSn(tmpMenuSelectRes.getMenuSn());
							tmpMenuSaveReq.setUseYn("N");
							tmpMenuSaveReq.setMdfrId(SessionUtil.getLoginMngrId());

							// 2_2_0_2_1. 해당 메뉴가 있으면 하위 메뉴까지 사용여부 수정
							nSaveCount = menuMapper.updateDownMenuUseYn(tmpMenuSaveReq);

							if(! (nSaveCount > 0 ) || menuSaveReq.getMenuSn() == null) {
								throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
							}

						} else { // 메뉴 번호가 속해 있지 않는 경우
							continue;
						}
					}
				}
			}
			/** ====================== [2_2] ====================== */

		}

		// 3. 반환값 설정
		menuSaveRes.setMenuSn(menuSaveReq.getMenuSn());

		return menuSaveRes;
	}

	/*****************************************************
	 * menu 상세 정보 조회
	 * @param menuSelectReq
	 * @return
	*****************************************************/
	public MenuSelectRes selectMenuDetail(MenuSelectReq menuSelectReq) {

		MenuSelectRes menuSelectRes 	= null;

		menuSelectRes = menuMapper.selectMenuDetail(menuSelectReq);

		return menuSelectRes;

	}

	/*****************************************************
	 * menu 정렬 순서 저장
	 * @param request
	 * @param menuSaveReq
	 * @return
	*****************************************************/
	public int saveMenuSeq(HttpServletRequest request, MenuSaveReq menuSaveReq) throws Exception {

		int nCount 			= 0;	// 저장 성공 카운트

		String strSaveErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		/** ========== [0]. 메뉴 정보 조회 시작 ========== */
		// 0_0. 현재 메뉴 정보 셋팅
		MenuSelectReq	menuSelectReq	= new MenuSelectReq();
		MenuSelectRes 	menuSelectRes	= new MenuSelectRes();

		// 0_1. 현재 메뉴 상세 조회
		menuSelectReq.setTargetMenuSn(menuSaveReq.getMenuSn());
		menuSelectRes = menuMapper.selectMenuDetail(menuSelectReq);

		if( menuSelectRes == null ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ====================== [0] ====================== */

		/** ========== [1]. 순서 바뀌는 메뉴 정보 조회 시작 ========== */
		// 1_0. 순서 바뀌는 메뉴 정보 셋팅
		MenuSelectReq	chgMenuSelectReq	= new MenuSelectReq();
		MenuSelectRes 	chgMenuSelectRes	= new MenuSelectRes();

		// 1_1. 순서 바뀌는 메뉴 상세 조회
		chgMenuSelectReq.setTargetMenuSn(menuSaveReq.getChgMenuSn());
		chgMenuSelectRes = menuMapper.selectMenuDetail(chgMenuSelectReq);

		if( chgMenuSelectRes == null ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ====================== [1] ====================== */

		/** ========== [2]. 현재 메뉴/바뀌는 메뉴 정렬 순서 수정 시작 ========== */
		// 2_0. 현재 메뉴/바뀌는 메뉴 정보 셋팅
		String 			strSeqType		= menuSaveReq.getSeqType();	 // up, down

		MenuSaveReq 	chgMenuSaveReq	= new MenuSaveReq();
		chgMenuSaveReq.setMenuSn(menuSaveReq.getChgMenuSn());

		int		nSrtSeq		= menuSelectRes.getSrtSeq();	// 현재 메뉴의 정렬 순서
		int		nChgSrtSeq	= chgMenuSelectRes.getSrtSeq(); // 바뀌는 메뉴의 정렬 순서
		int		nDiffCnt	= 0;							// 서로 바뀌는 대상 메뉴 정렬 순서 오차값

		// 2_1. 정렬 순서 담기
		if( "up".equals(strSeqType.toLowerCase()) ) {

			nDiffCnt = nSrtSeq - nChgSrtSeq;

			menuSaveReq.setSrtSeq( nSrtSeq - nDiffCnt );
			chgMenuSaveReq.setSrtSeq( nChgSrtSeq + nDiffCnt );

		} else {

			nDiffCnt = nChgSrtSeq - nSrtSeq;

			menuSaveReq.setSrtSeq( nSrtSeq + nDiffCnt );
			chgMenuSaveReq.setSrtSeq( nChgSrtSeq - nDiffCnt );

		}
		/** ====================== [2] ====================== */

		/** ========== [3]. 정렬 순서 수정 시작 ========== */
		// 3_0. 현재 메뉴 정렬 순서 수정
		menuSaveReq.setMdfrId(SessionUtil.getLoginMngrId());
		nCount = menuMapper.updateMenuSeq(menuSaveReq);

		if( !(nCount > 0) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// 3_1. 바뀌는 메뉴 정렬 순서 수정
		chgMenuSaveReq.setMdfrId(SessionUtil.getLoginMngrId());
		nCount = menuMapper.updateMenuSeq(chgMenuSaveReq);

		if( !(nCount > 0) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		/** ====================== [#] ====================== */

		return nCount;
	}

}

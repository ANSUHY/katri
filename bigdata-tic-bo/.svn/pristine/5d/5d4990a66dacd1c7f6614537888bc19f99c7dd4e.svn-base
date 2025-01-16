package com.katri.web.ctnt.menuCptn.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.SessionUtil;
import com.katri.web.ctnt.menuCptn.mapper.MenuCptnMapper;
import com.katri.web.ctnt.menuCptn.model.MenuCptnSaveReq;
import com.katri.web.ctnt.menuCptn.model.MenuCptnSaveRes;
import com.katri.web.ctnt.menuCptn.model.MenuCptnSelectReq;
import com.katri.web.ctnt.menuCptn.model.MenuCptnSelectRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class MenuCptnService {

	/** 메뉴구성관리 Mapper */
	private final MenuCptnMapper menuCptnMapper;

	/*****************************************************
	 * 메뉴구성관리 리스트 개수(각 타입으로)
	 * @param menuCptnSelectReq
	 * @return Integer 메뉴구성관리 리스트 개수
	 * @throws Exception
	*****************************************************/
	public Integer getMenuCptnCnt(MenuCptnSelectReq menuCptnSelectReq) throws Exception{

		// [[0]]. 반환할 정보들
		int menuCptnCnt = 0;

		// [[1]]. 메뉴구성관리 테이블 개수 조회
		menuCptnCnt = menuCptnMapper.selectMenuCptnListCount(menuCptnSelectReq);

		return menuCptnCnt;
	}

	/*****************************************************
	 * 메뉴구성관리 리스트_데이터
	 * @param MenuCptnSelectReq
	 * @return List<MenuCptnSelectRes> 메뉴구성관리 리스트
	 * @throws Exception
	*****************************************************/
	public List<MenuCptnSelectRes> getMenuCptnList(MenuCptnSelectReq menuCptnSelectReq) throws Exception{

		// [[0]]. 반환할 정보들
		List<MenuCptnSelectRes> menuCptnList = null;

		// [[1]]. MenuCptn 테이블 조회
		menuCptnList = menuCptnMapper.selectMenuCptnList(menuCptnSelectReq);

		return menuCptnList;
	}

	/*****************************************************
	 * 메뉴구성관리 상세_데이터
	 * @param MenuCptnSelectReq
	 * @return MenuCptnSelectRes MenuCptn 상세
	 * @throws Exception
	*****************************************************/
	public MenuCptnSelectRes getMenuCptnDetail(MenuCptnSelectReq menuCptnSelectReq) throws Exception{

		// [[0]]. 반환할 정보들
		MenuCptnSelectRes menuCptnDetail = null;

		// [[1]]. MenuCptn 테이블 조회
		menuCptnDetail = menuCptnMapper.selectMenuCptnDetail(menuCptnSelectReq.getTargetMenuCptnSn());

		if(menuCptnDetail == null) {
			// 반환값 셋팅
			throw new CustomMessageException("result-message.messages.common.message.no.data"); //데이터가 없습니다.
		}

		return menuCptnDetail;
	}

	/*****************************************************
	 * 메뉴구성관리 사용여부 수정
	 * @param MenuCptnSaveReq chgUseYMenuCptnSn,chgUseYMenuCptnCd 가 담겨있는 정보
	 * @return MenuCptnSaveRes
	 * @throws Exception
	 *****************************************************/
	public MenuCptnSaveRes updateChgMenuCptnUseYn(MenuCptnSaveReq menuCptnSaveReq) throws Exception {

		String strUpdateErrMsgCode = "result-message.messages.common.message.update.error"; //수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		// [[0]]. 반환할 정보들 & 변수 지정
		MenuCptnSaveRes menuCptnSaveRes = new MenuCptnSaveRes();
		int saveCount = 0;
		int chgUseYMenuCptnSn;
		String chgUseYMenuCptnCd;
		MenuCptnSaveReq newMenuCptnSaveReq;

		// [[1]]. validataion check
		/* 1-1. [사용] 으로 change할  메뉴구성일련번호 */
		if(menuCptnSaveReq.getChgUseYMenuCptnSn() == null  || menuCptnSaveReq.getChgUseYMenuCptnSn() == 0) {
			throw new CustomMessageException("필수값 오류_메뉴구성일련번호");
		}
		/* 1-2. [사용] 으로 change할 menuCptnCd */
		if(menuCptnSaveReq.getChgUseYMenuCptnCd() == null  || "".equals(menuCptnSaveReq.getChgUseYMenuCptnCd())) {
			throw new CustomMessageException("필수값 오류_menuCptnCd");
		}

		// [[2]]. 데이터 지정
		chgUseYMenuCptnSn = menuCptnSaveReq.getChgUseYMenuCptnSn();
		chgUseYMenuCptnCd = menuCptnSaveReq.getChgUseYMenuCptnCd();

		// [[3]]. 해당 menuCptnCd 에 해당하는 menuCptn 다 USE_YN = N 으로 수정
		newMenuCptnSaveReq = new MenuCptnSaveReq();
		newMenuCptnSaveReq.setChgUseYn("N");
		newMenuCptnSaveReq.setChgUseYMenuCptnCd(chgUseYMenuCptnCd);
		newMenuCptnSaveReq.setMdfrId(SessionUtil.getLoginMngrId());
		saveCount = menuCptnMapper.updateChgMenuCptnUseYN(newMenuCptnSaveReq);
		if(! (saveCount > 0 ) ) {
			throw new CustomMessageException(strUpdateErrMsgCode); //수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// [[4]]. 해당 menuCptnSn USE_YN = Y 로 수정
		newMenuCptnSaveReq = new MenuCptnSaveReq();
		newMenuCptnSaveReq.setChgUseYn("Y");
		newMenuCptnSaveReq.setChgUseYMenuCptnCd(chgUseYMenuCptnCd);
		newMenuCptnSaveReq.setChgUseYMenuCptnSn(chgUseYMenuCptnSn);
		newMenuCptnSaveReq.setMdfrId(SessionUtil.getLoginMngrId());
		saveCount = menuCptnMapper.updateChgMenuCptnUseYN(newMenuCptnSaveReq);
		if(! (saveCount > 0 ) ) {
			throw new CustomMessageException(strUpdateErrMsgCode); //수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return menuCptnSaveRes;
	}

	/*****************************************************
	 * 메뉴구성관리 등록
	 * @param MenuCptnSaveReq MenuCptn 정보
	 * @return MenuCptnSaveRes
	 * @throws Exception
	*****************************************************/
	public MenuCptnSaveRes insertMenuCptn(MenuCptnSaveReq menuCptnSaveReq) throws Exception {

		String strSavetErrMsgCode = "result-message.messages.common.message.save.error"; //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		// [[0]]. 반환할 정보들 & 변수 지정
		MenuCptnSaveRes menuCptnSaveRes = new MenuCptnSaveRes();
		int saveCount = 0;

		// [[1]]. validataion check
		/* 1-1. 메뉴구성코드 */
		if(menuCptnSaveReq.getMenuCptnCd() == null  || "".equals(menuCptnSaveReq.getMenuCptnCd())) {
			throw new CustomMessageException("필수값 오류_메뉴구성코드");
		}
		/* 1-2. 메뉴구성명 */
		if(menuCptnSaveReq.getMenuCptnNm() == null  || "".equals(menuCptnSaveReq.getMenuCptnNm())) {
			throw new CustomMessageException("result-message.messages.common.message.required.data" + "||msgArgu=콘텐츠명||"); // '{0}는(은) 필수 입력항목입니다.'
		}
		/* 1-3. 메뉴구성내용 */
		if(menuCptnSaveReq.getMenuCptnCn() == null  || "".equals(menuCptnSaveReq.getMenuCptnCn())) {
			throw new CustomMessageException("result-message.messages.common.message.required.data" + "||msgArgu=소스코드||"); // '{0}는(은) 필수 입력항목입니다.'
		}

		// [[2]]. 파라미터셋팅
		menuCptnSaveReq.setCrtrId(SessionUtil.getLoginMngrId());

		// [[3]]. menuCptn 테이블 등록
		saveCount = menuCptnMapper.insertMenuCptn(menuCptnSaveReq);
		if(! (saveCount > 0 ) || menuCptnSaveReq.getMenuCptnSn() == null) {
			throw new CustomMessageException(strSavetErrMsgCode); //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// [[4]]. 반환값 셋팅
		menuCptnSaveRes.setMenuCptnSn(menuCptnSaveReq.getMenuCptnSn());

		return menuCptnSaveRes;
	}

	/*****************************************************
	 * 메뉴구성관리 수정
	 * @param MenuCptnSaveReq MenuCptn 정보
	 * @return MenuCptnSaveRes
	 * @throws Exception
	 *****************************************************/
	public MenuCptnSaveRes updateMenuCptn(MenuCptnSaveReq menuCptnSaveReq) throws Exception {

		String strUpdateErrMsgCode = "result-message.messages.common.message.update.error"; //수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		// [[0]]. 반환할 정보들 & 변수 지정
		MenuCptnSaveRes menuCptnSaveRes = new MenuCptnSaveRes();
		int saveCount = 0;

		// [[1]]. validataion check
		/* 1-0. 메뉴구성코드 */
		if(menuCptnSaveReq.getMenuCptnCd() == null  || "".equals(menuCptnSaveReq.getMenuCptnCd())) {
			throw new CustomMessageException("필수값 오류_메뉴구성코드");
		}
		/* 1-1. 메뉴구성일련번호 */
		if(menuCptnSaveReq.getMenuCptnSn() == null  || menuCptnSaveReq.getMenuCptnSn() == 0) {
			throw new CustomMessageException("필수값 오류_메뉴구성일련번호");
		}
		/* 1-2. 메뉴구성명 */
		if(menuCptnSaveReq.getMenuCptnNm() == null  || "".equals(menuCptnSaveReq.getMenuCptnNm())) {
			throw new CustomMessageException("result-message.messages.common.message.required.data" + "||msgArgu=콘텐츠명||"); // '{0}는(은) 필수 입력항목입니다.'
		}
		/* 1-3. 메뉴구성내용 */
		if(menuCptnSaveReq.getMenuCptnCn() == null  || "".equals(menuCptnSaveReq.getMenuCptnCn())) {
			throw new CustomMessageException("result-message.messages.common.message.required.data" + "||msgArgu=소스코드||"); // '{0}는(은) 필수 입력항목입니다.'
		}

		// [[2]]. 파라미터셋팅
		menuCptnSaveReq.setMdfrId(SessionUtil.getLoginMngrId());

		// [[3]]. menuCptn 테이블 업데이트
		saveCount = menuCptnMapper.updateMenuCptn(menuCptnSaveReq);
		if(! (saveCount > 0 ) || menuCptnSaveReq.getMenuCptnSn() == null) {
			throw new CustomMessageException(strUpdateErrMsgCode); //수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// [[4]]. 반환값 셋팅
		menuCptnSaveRes.setMenuCptnSn(menuCptnSaveReq.getMenuCptnSn());

		return menuCptnSaveRes;
	}

}

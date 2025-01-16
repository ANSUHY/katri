package com.katri.web.hmpgCptn.popupZone.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.FileConst;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.model.FileDto;
import com.katri.web.comm.service.FileService;
import com.katri.web.hmpgCptn.popupZone.controller.PopupZoneController;
import com.katri.web.hmpgCptn.popupZone.mapper.PopupZoneMapper;
import com.katri.web.hmpgCptn.popupZone.model.PopupZoneSaveReq;
import com.katri.web.hmpgCptn.popupZone.model.PopupZoneSaveRes;
import com.katri.web.hmpgCptn.popupZone.model.PopupZoneSelectReq;
import com.katri.web.hmpgCptn.popupZone.model.PopupZoneSelectRes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
@Slf4j
public class PopupZoneService {

	/** 파일 Service */
	private final FileService fileService;

	/** 팝업존 mapper */
	private final PopupZoneMapper popupZoneMapper;

	/*****************************************************
	 * 팝업 목록 개수 조회
	 * @param popupZoneSelectReq
	 * @return
	*****************************************************/
	public int getPopupCount(PopupZoneSelectReq popupZoneSelectReq) {
		int pTotCnt	= 0;

		//[[0]]. 팝업존 리스트 개수 조회
		pTotCnt	= popupZoneMapper.getPopupCount(popupZoneSelectReq);

		return pTotCnt;
	}

	/*****************************************************
	 * 팝업존 목록 조회
	 * @param popupZoneSelectReq
	 * @return
	*****************************************************/
	public List<PopupZoneSelectRes> getPopupZoneList(PopupZoneSelectReq popupZoneSelectReq) {

		List<PopupZoneSelectRes> popupList	= null;

		//[[0]]. 팝업존 리스트 조회
		popupZoneSelectReq.setSearchVisualPcImg(FileConst.File.FILE_DIV_NM_POPUP_PC_IMG);
		popupZoneSelectReq.setSearchVisualMobileImg(FileConst.File.FILE_DIV_NM_POPUP_MOBILE_IMG);

		popupList	= popupZoneMapper.selectPopupZoneList(popupZoneSelectReq);

		return popupList;
	}

	/*****************************************************
	 * 팝업존 수정
	 * @param request
	 * @Param PopupZoneSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public PopupZoneSaveRes updatePopupZone(HttpServletRequest request, @Valid PopupZoneSaveReq popupZoneSaveReq) throws Exception {

		String strSaveErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다.

		//[[0]]. 변수 설정
		PopupZoneSaveRes popupZoneSaveRes	= new PopupZoneSaveRes();
		int pSaveCount	= 0;

		//[[1]]. 수정 시작
		pSaveCount	= popupZoneMapper.updatePopupZone(popupZoneSaveReq);

		if(! (pSaveCount > 0 ) || popupZoneSaveReq.getHmpgCptnSn() == null) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		/** [[ 수정 시 파일 처리 ]]
		* 수정 파일 o > 기존 파일 삭제 후, 새 파일 등록 ( 물리적 파일 삭제 x)
		* 수정 파일 x > 기존 파일 유지
		*/

		//[[2]]. 파일 수정 시작
		/*2_0. 파일 삭제 */
		List<String> arrDelFileSn = popupZoneSaveReq.getArrDelFileSn();

		if( arrDelFileSn != null && arrDelFileSn.size() != 0) {
			for( String strDelFileSn : arrDelFileSn ) {

				/* 2_0_0. 파일 번호 체크 */
				if( "".equals(strDelFileSn) ) {
					continue;
				}

				/* 2_0_1. 삭제 파일 번호 셋팅 */
				FileDto delFileDto = new FileDto();
				delFileDto.setEncodeFileSn(strDelFileSn);

				/* 2_0_2. 단일 파일 삭제_논리(DB) */
				pSaveCount = fileService.deleteFileData(delFileDto);
				if(! (pSaveCount > 0 )) {
					throw new CustomMessageException("논리 파일 삭제 중 오류");
				}

			}
	}

		//[[3]]. 파일 저장
		/* 3_0. 물리저장 + 파일dto 파일 목록 */
		List<FileDto> listFileDto = fileService.savePhyFileReturnFileList(request, "");

		/* 3_1. 파일 테이블 저장 [tb_file_mng]*/
		if(listFileDto.size() != 0) {
			fileService.saveDBFile(Integer.toString(popupZoneSaveReq.getHmpgCptnSn()), listFileDto);
		}

		//[[4]]. 반환값 셋팅
		popupZoneSaveRes.setHmpgCptnSn(popupZoneSaveReq.getHmpgCptnSn());

		return popupZoneSaveRes;
	}

	/*****************************************************
	 * 팝업 등록
	 * @param request
	 * @param popupZoneSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public PopupZoneSaveRes insertMainPopup(HttpServletRequest request, @Valid PopupZoneSaveReq popupZoneSaveReq) throws Exception {

		String strSaveErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		// 0. 변수 설정
		PopupZoneSaveRes popupZoneSaveRes	= new PopupZoneSaveRes();
		int				  pSaveCount		= 0;

		// 1. 등록 시작
		pSaveCount = popupZoneMapper.insertPopup(popupZoneSaveReq);

		if(! (pSaveCount > 0 ) || popupZoneSaveReq.getHmpgCptnSn() == null) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// 2. 파일 저장
		/* 2_0. 물리저장 + 파일dto 파일 목록 */
		List<FileDto> listFileDto = fileService.savePhyFileReturnFileList(request, "");

		/* 2_1. 파일테이블 저장[tb_file_mng] */
		if(listFileDto.size() != 0) {
			fileService.saveDBFile(Integer.toString(popupZoneSaveReq.getHmpgCptnSn()), listFileDto);
		}

		// 3. 반환값 셋팅
		popupZoneSaveRes.setHmpgCptnSn(popupZoneSaveReq.getHmpgCptnSn());

		return popupZoneSaveRes;

	}

	/*****************************************************
	 * 팝업존 상세 조회
	 * @param popupZoneSelectReq
	 * @return popupZoneSelectRes
	 * @throws Exception
	*****************************************************/
	public PopupZoneSelectRes getPopupDetail(PopupZoneSelectReq popupZoneSelectReq) throws Exception {
		//[[0]].반환할 데이터
		PopupZoneSelectRes	popupZoneSelectRes	= null;

		//[1]]. 팝업존 상세 조회
		popupZoneSelectRes	= popupZoneMapper.selectPopupZoneDetail(popupZoneSelectReq);

		//[2]]. 해당 파일 조회 (PC, Mobile)
		if(popupZoneSelectRes != null) {

			/* 1-0. 파일 값 셋팅 */
			FileDto fileDto	= new FileDto();
			fileDto.setRefDivVal(Integer.toString(popupZoneSelectRes.getHmpgCptnSn()));

			/* 1-1. pc_popup_img 파일 조회 */
			fileDto.setFileDivNm(FileConst.File.FILE_DIV_NM_POPUP_PC_IMG);
			FileDto pcFileDto = this.fileService.selectFile(fileDto);

			popupZoneSelectRes.setPcImgFile(pcFileDto);

			/* 1-2. mobile_popup_img 파일 조회 */
			fileDto.setFileDivNm(FileConst.File.FILE_DIV_NM_POPUP_MOBILE_IMG);
			FileDto mobileFileDto = this.fileService.selectFile(fileDto);

			popupZoneSelectRes.setMobileImgFile(mobileFileDto);

		} else {
			//반환값 셋팅
			throw new CustomMessageException("result-message.messages.common.messge.no.data"); //데이터가 없습니다.
		}

		return popupZoneSelectRes;
	}

	/*****************************************************
	 * 팝업존 정렬 순서 저장
	 * @param popupZoneSelectReq
	 * @return popupZoneSelectRes
	 * @throws CustomMessageException
	 * @throws Exception
	*****************************************************/
	public int savePopupReq(HttpServletRequest request, PopupZoneSaveReq popupZoneSaveReq) throws CustomMessageException {
		int pCount	= 0;

		String strSaveErrMsgCode	= "result-message.messages.common.message.save.error"; //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		/** =================== [0]. 메인 비주얼 정보 조회 시작 ================ */
		// 0_0. 현재 비주얼 정보 셋팅
		PopupZoneSelectReq popupZoneSelectReq	= new PopupZoneSelectReq();
		PopupZoneSelectRes popupZoneSelectRes	= new PopupZoneSelectRes();

		// 0_1. 현재 비주얼 상세 조회
		popupZoneSelectReq.setHmpgCptnSn(popupZoneSaveReq.getHmpgCptnSn());
		popupZoneSelectRes = popupZoneMapper.selectPopupZoneDetail(popupZoneSelectReq);

		if( popupZoneSelectRes == null ) {
			throw new CustomMessageException(strSaveErrMsgCode); //저장중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		/** ================== [0] ============================ */

		/** ==============[1]. 순서 바뀌는 팝업존 정보 조회 시작 ======== */
		PopupZoneSelectReq chgPopupSelectReq	= new PopupZoneSelectReq();
		PopupZoneSelectRes chgPopupSelectRes	= new PopupZoneSelectRes();

		chgPopupSelectReq.setHmpgCptnSn(popupZoneSaveReq.getChgHmpgCptnSn());
		chgPopupSelectRes = popupZoneMapper.selectPopupZoneDetail(chgPopupSelectReq);


		if( chgPopupSelectRes == null ) {
			throw new CustomMessageException(strSaveErrMsgCode); //저장중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ====================== [1] ====================== */

		/** =========== [2]. 현재 팝업/바뀌는 팝업 정렬 순서 수정 시작 ======== */
		// 2_0. 현재 팝업/바뀌는 팝업 정보 셋팅
		String strSeqType	= popupZoneSaveReq.getSeqType(); //up, down

		PopupZoneSaveReq chgPopupSaveReq = new PopupZoneSaveReq();
		chgPopupSaveReq.setHmpgCptnSn(popupZoneSaveReq.getChgHmpgCptnSn());

		int		pSrtSeq		= popupZoneSelectRes.getSrtSeq();		// 현재 팝업 정렬 순서
		int		pChgSrtSeq	= chgPopupSelectRes.getSrtSeq();		// 바뀌는 팝업의 정렬 순서
		int		pDiffCnt	= 0;									// 서로 바뀌는 대상 팝업 정렬 순서 오차값

		// 2_1. 정렬 순서 담기
				if( "up".equals(strSeqType.toLowerCase()) ) {

					pDiffCnt = pSrtSeq - pChgSrtSeq;

					popupZoneSaveReq.setSrtSeq( pSrtSeq - pDiffCnt );
					chgPopupSaveReq.setSrtSeq( pChgSrtSeq + pDiffCnt );


				} else {

					pDiffCnt = pChgSrtSeq - pSrtSeq;

					popupZoneSaveReq.setSrtSeq( pSrtSeq + pDiffCnt );
					chgPopupSaveReq.setSrtSeq( pChgSrtSeq - pDiffCnt );

				}
		/** ====================== [2] ====================== */

		/** ========== [3]. 정렬 순서 수정 시작 ========== */
		// 3_0. 현재 팝업 정렬 순서 수정
		popupZoneSaveReq.setMdfrId(SessionUtil.getLoginMngrId());

		pCount = popupZoneMapper.updatePopupZoneSeq(popupZoneSaveReq);

		if( !(pCount > 0) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// 3_1. 바뀌는 팝업 정렬 순서 수정
		chgPopupSaveReq.setMdfrId(SessionUtil.getLoginMngrId());

		pCount = popupZoneMapper.updatePopupZoneSeq(chgPopupSaveReq);

		if( !(pCount > 0) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ====================== [#] ====================== */

		return pCount;


	}





}

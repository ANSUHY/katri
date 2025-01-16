package com.katri.web.hmpgCptn.mainVisual.service;

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
import com.katri.web.hmpgCptn.mainVisual.mapper.MainVisualMapper;
import com.katri.web.hmpgCptn.mainVisual.model.MainVisualSaveReq;
import com.katri.web.hmpgCptn.mainVisual.model.MainVisualSaveRes;
import com.katri.web.hmpgCptn.mainVisual.model.MainVisualSelectReq;
import com.katri.web.hmpgCptn.mainVisual.model.MainVisualSelectRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class MainVisualService {

	/** 파일 Service */
	private final FileService fileService;

	/** MainVisual Mapper */
	private final MainVisualMapper mainVisualMapper;

	/*****************************************************
	 * 메인 비주얼 목록 개수 조회
	 * @param mainVisualSelectReq
	 * @return
	*****************************************************/
	public Integer getMainVisualCount(MainVisualSelectReq mainVisualSelectReq) {
		int nTotCnt = 0;

		// 0. 메인 비주얼 리스트 개수 조회
		nTotCnt = mainVisualMapper.selectMainVisualCount(mainVisualSelectReq);

		return nTotCnt;
	}

	/*****************************************************
	 * 메인 비주얼 목록 조회
	 * @param mainVisualSelectReq
	 * @return
	*****************************************************/
	public List<MainVisualSelectRes> getMainVisualList(MainVisualSelectReq mainVisualSelectReq) {

		List<MainVisualSelectRes> mainVisualList = null;

		// 0. 메인 비주얼 리스트 조회
		mainVisualSelectReq.setSearchVisualPcImg(FileConst.File.FILE_DIV_NM_VISUAL_PC_IMG);
		mainVisualSelectReq.setSearchVisualMobileImg(FileConst.File.FILE_DIV_NM_VISUAL_MOBILE_IMG);

		mainVisualList = mainVisualMapper.selectMainVisualList(mainVisualSelectReq);

		return mainVisualList;

	}

	/*****************************************************
	 * 메인 비주얼 상세 조회
	 * @param mainVisualSelectRes
	 * @return
	*****************************************************/
	public MainVisualSelectRes getAuthrtGrpBasDetail(MainVisualSelectReq mainVisualSelectReq) throws Exception {

		MainVisualSelectRes	mainVisualSelectRes = null;

		// 0. 메인 비주얼 상세 조회
		mainVisualSelectRes = mainVisualMapper.selectMainVisualDetail(mainVisualSelectReq);

		// 1. 해당 비주얼 파일 조회 ( PC, Mobile )
		if( mainVisualSelectRes != null ) {

			/* 1_0. 파일 값 셋팅 */
			FileDto fileDto = new FileDto();
			fileDto.setRefDivVal(Integer.toString(mainVisualSelectRes.getHmpgCptnSn()));

			/* 1_1. pc_visual_img 파일 조회 */
			fileDto.setFileDivNm(FileConst.File.FILE_DIV_NM_VISUAL_PC_IMG);
			FileDto pcFileDto = this.fileService.selectFile(fileDto);

			mainVisualSelectRes.setPcImgFile(pcFileDto);

			/* 1_2. mobile_visual_img 파일 조회 */
			fileDto.setFileDivNm(FileConst.File.FILE_DIV_NM_VISUAL_MOBILE_IMG);
			FileDto mobileFileDto = this.fileService.selectFile(fileDto);

			mainVisualSelectRes.setMobileImgFile(mobileFileDto);

		} else {
			// 반환값 셋팅
			throw new CustomMessageException("result-message.messages.common.message.no.data"); //데이터가 없습니다.
		}

		return mainVisualSelectRes;

	}

	/*****************************************************
	 * 메인 비주얼 수정
	 * @param request
	 * @param mainVisualSaveReq
	 * @return
	*****************************************************/
	public MainVisualSaveRes updateMainVisual(HttpServletRequest request, @Valid MainVisualSaveReq mainVisualSaveReq) throws Exception {

		String strSaveErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		// 0. 변수 설정
		MainVisualSaveRes mainVisualSaveRes	= new MainVisualSaveRes();
		int				  nSaveCount		= 0;

		// 1. 수정 시작
		nSaveCount = mainVisualMapper.updateMainVisual(mainVisualSaveReq);

		if(! (nSaveCount > 0 ) || mainVisualSaveReq.getHmpgCptnSn() == null) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		/** [[ 수정 시 파일 처리 ]]
			* 수정 파일 o > 기존 파일 삭제 후, 새 파일 등록 ( 물리적 파일 삭제 x)
			* 수정 파일 x > 기존 파일 유지
		*/
		// 2. 파일 수정 시작
		/* 2_0. 파일 삭제 */
		List<String> arrDelFileSn = mainVisualSaveReq.getArrDelFileSn();

		if( arrDelFileSn != null && arrDelFileSn.size() != 0 ) {
			for( String strDelFileSn : arrDelFileSn ) {

				/* 2_0_0. 파일 번호 체크 */
				if( "".equals(strDelFileSn) ) {
					continue;
				}

				/* 2_0_1. 삭제 파일 번호 셋팅 */
				FileDto delFileDto = new FileDto();
				delFileDto.setEncodeFileSn(strDelFileSn);

				/* 2_0_2. 단일 파일 삭제_논리(DB) */
				nSaveCount = fileService.deleteFileData(delFileDto);
				if(! (nSaveCount > 0 )) {
					throw new CustomMessageException("논리 파일 삭제 중 오류");
				}

			}
		}

		// 3. 파일 저장
		/* 3_0. 물리저장 + 파일dto 파일 목록 */
		List<FileDto> listFileDto = fileService.savePhyFileReturnFileList(request, "");

		/* 3_1. 파일테이블 저장[tb_file_mng] */
		if(listFileDto.size() != 0) {
			fileService.saveDBFile(Integer.toString(mainVisualSaveReq.getHmpgCptnSn()), listFileDto);
		}

		// 4. 반환값 셋팅
		mainVisualSaveRes.setHmpgCptnSn(mainVisualSaveReq.getHmpgCptnSn());

		return mainVisualSaveRes;
	}

	/*****************************************************
	 * 메인 비주얼 등록
	 * @param request
	 * @param mainVisualSaveReq
	 * @return
	*****************************************************/
	public MainVisualSaveRes insertMainVisual(HttpServletRequest request, @Valid MainVisualSaveReq mainVisualSaveReq) throws Exception {

		String strSaveErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		// 0. 변수 설정
		MainVisualSaveRes mainVisualSaveRes	= new MainVisualSaveRes();
		int				  nSaveCount		= 0;

		// 1. 등록 시작
		nSaveCount = mainVisualMapper.insertMainVisual(mainVisualSaveReq);

		if(! (nSaveCount > 0 ) || mainVisualSaveReq.getHmpgCptnSn() == null) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// 2. 파일 저장
		/* 2_0. 물리저장 + 파일dto 파일 목록 */
		List<FileDto> listFileDto = fileService.savePhyFileReturnFileList(request, "");

		/* 2_1. 파일테이블 저장[tb_file_mng] */
		if(listFileDto.size() != 0) {
			fileService.saveDBFile(Integer.toString(mainVisualSaveReq.getHmpgCptnSn()), listFileDto);
		}

		// 3. 반환값 셋팅
		mainVisualSaveRes.setHmpgCptnSn(mainVisualSaveReq.getHmpgCptnSn());

		return mainVisualSaveRes;

	}

	/*****************************************************
	 * 메인 비주얼 정렬 순서 저장
	 * @param request
	 * @param mainVisualSaveReq
	 * @return
	*****************************************************/
	public int saveMainVisualSeq(HttpServletRequest request, MainVisualSaveReq mainVisualSaveReq) throws Exception {

		int nCount = 0;

		String strSaveErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		/** ========== [0]. 메인 비주얼 정보 조회 시작 ========== */
		// 0_0. 현재 비주얼 정보 셋팅
		MainVisualSelectReq mainVisualSelectReq	= new MainVisualSelectReq();
		MainVisualSelectRes mainVisualSelectRes	= new MainVisualSelectRes();

		// 0_1. 현재 비주얼 상세 조회
		mainVisualSelectReq.setHmpgCptnSn(mainVisualSaveReq.getHmpgCptnSn());
		mainVisualSelectRes = mainVisualMapper.selectMainVisualDetail(mainVisualSelectReq);

		if( mainVisualSelectRes == null ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ====================== [0] ====================== */

		/** ========== [1]. 순서 바뀌는 메인 비주얼 정보 조회 시작 ========== */
		MainVisualSelectReq chgMainVisualSelectReq	= new MainVisualSelectReq();
		MainVisualSelectRes chgMainVisualSelectRes	= new MainVisualSelectRes();

		chgMainVisualSelectReq.setHmpgCptnSn(mainVisualSaveReq.getChgHmpgCptnSn());
		chgMainVisualSelectRes = mainVisualMapper.selectMainVisualDetail(chgMainVisualSelectReq);

		if( chgMainVisualSelectRes == null ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ====================== [1] ====================== */

		/** ========== [2]. 현재 메인 비주얼/바뀌는 메인 비주얼 정렬 순서 수정 시작 ========== */
		// 2_0. 현재 비주얼/바뀌는 비주얼 정보 셋팅
		String 	strSeqType	= mainVisualSaveReq.getSeqType();	 // up, down

		MainVisualSaveReq chgMainVisualSaveReq = new MainVisualSaveReq();
		chgMainVisualSaveReq.setHmpgCptnSn(mainVisualSaveReq.getChgHmpgCptnSn());

		int		nSrtSeq		= mainVisualSelectRes.getSrtSeq();		// 현재 비주얼 정렬 순서
		int		nChgSrtSeq	= chgMainVisualSelectRes.getSrtSeq();	// 바뀌는 비주얼의 정렬 순서
		int		nDiffCnt	= 0;									// 서로 바뀌는 대상 비주얼 정렬 순서 오차값

		// 2_1. 정렬 순서 담기
		if( "up".equals(strSeqType.toLowerCase()) ) {

			nDiffCnt = nSrtSeq - nChgSrtSeq;

			mainVisualSaveReq.setSrtSeq( nSrtSeq - nDiffCnt );
			chgMainVisualSaveReq.setSrtSeq( nChgSrtSeq + nDiffCnt );

		} else {

			nDiffCnt = nChgSrtSeq - nSrtSeq;

			mainVisualSaveReq.setSrtSeq( nSrtSeq + nDiffCnt );
			chgMainVisualSaveReq.setSrtSeq( nChgSrtSeq - nDiffCnt );

		}
		/** ====================== [2] ====================== */

		/** ========== [3]. 정렬 순서 수정 시작 ========== */
		// 3_0. 현재 비주얼 정렬 순서 수정
		mainVisualSaveReq.setMdfrId(SessionUtil.getLoginMngrId());

		nCount = mainVisualMapper.updateMainVisualSeq(mainVisualSaveReq);

		if( !(nCount > 0) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// 3_1. 바뀌는 비주얼 정렬 순서 수정
		chgMainVisualSaveReq.setMdfrId(SessionUtil.getLoginMngrId());

		nCount = mainVisualMapper.updateMainVisualSeq(chgMainVisualSaveReq);

		if( !(nCount > 0) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ====================== [#] ====================== */

		return nCount;
	}

}

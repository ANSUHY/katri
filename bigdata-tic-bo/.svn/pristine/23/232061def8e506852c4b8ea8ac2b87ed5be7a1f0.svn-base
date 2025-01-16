package com.katri.web.system.code.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.SessionUtil;
import com.katri.web.system.code.mapper.CodeMapper;
import com.katri.web.system.code.model.CodeSaveReq;
import com.katri.web.system.code.model.CodeSelectReq;
import com.katri.web.system.code.model.CodeSelectRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class CodeService {

	private final CodeMapper codeMapper;

	// 공통그룹코드
	/*****************************************************
	 * 공통그룹코드 목록 조회
	 * @param codeSelectReq
	 * @return List<CodeSelectRes>
	 * @throws Exception
	*****************************************************/
	public List<CodeSelectRes> getComnGrpCdList(CodeSelectReq codeSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		List<CodeSelectRes> comnGrpCdList = null;
		// [[1]]. tb_comm_grp_cd 테이블 조회
		comnGrpCdList = codeMapper.selectComnGrpCdList(codeSelectReq);

		if (comnGrpCdList == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return comnGrpCdList;
	}

	/*****************************************************
	 * 공통그룹코드 단건 조회
	 * @param commGrpCd
	 * @return CodeSelectRes
	 * @throws Exception
	*****************************************************/
	public CodeSelectRes getComnGrpCdDetail(String commGrpCd) throws Exception {

		CodeSelectRes codeSelectRes = null;
		// [[1]]. tb_comm_grp_cd 테이블의 동일한 comm_grp_cd 조회
		codeSelectRes = codeMapper.selectComnGrpCdDetail(commGrpCd);

		if (codeSelectRes == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return codeSelectRes;
	}

	/*****************************************************
	 * 공통그룹코드 추가
	 * @param codeSaveReq
	 * @return int
	 * @throws Exception
	*****************************************************/
	public int insertComnGrpCd(CodeSaveReq codeSaveReq) throws Exception {
		// 접속 아이디
		codeSaveReq.setCrtrId(SessionUtil.getLoginMngrId());

		// [[0]]. 반환할 정보
		int result = 0;
		// [[1]]. tb_comm_grp_cd 데이터 수정
		result = codeMapper.insertComnGrpCd(codeSaveReq);

		if (!(result > 0)) {
			throw new CustomMessageException("result-message.messages.common.message.insert.error"); // 작성 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return result;
	}

	/*****************************************************
	 * 공통그룹코드 수정
	 * @param codeSaveReq
	 * @return int
	 * @throws Exception
	*****************************************************/
	public int updateComnGrpCd(CodeSaveReq codeSaveReq) throws Exception {
		// 접속 아이디
		codeSaveReq.setMdfrId(SessionUtil.getLoginMngrId());

		// [[0]]. 반환할 정보
		int result = 0;
		// [[1]]. tb_comm_grp_cd 데이터 수정
		result = codeMapper.updateComnGrpCd(codeSaveReq);

		if (!(result > 0)) {
			throw new CustomMessageException("result-message.messages.common.message.update.error"); // 수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return result;
	}

	// 공통코드
	/*****************************************************
	 * 공통코드 목록 조회
	 * @param codeSelectReq
	 * @return List<CodeSelectRes>
	 * @throws Exception
	*****************************************************/
	public List<CodeSelectRes> getComnCdList(CodeSelectReq codeSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		List<CodeSelectRes> comnCdList = null;
		// [[1]]. tb_comm_grp_cd 테이블 조회
		comnCdList = codeMapper.selectComnCdList(codeSelectReq);

		if (comnCdList == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return comnCdList;
	}

	/*****************************************************
	 * 공통코드 단건 조회
	 * @param codeSelectReq
	 * @return CodeSelectRes
	 * @throws Exception
	*****************************************************/
	public CodeSelectRes getComnCdDetail(CodeSelectReq codeSelectReq) throws Exception {
		// [[0]]. 반환할 정보
		CodeSelectRes codeSelectRes = null;
		// [[1]]. tb_comm_cd 데이터 조회
		codeSelectRes = codeMapper.selectComnCdDetail(codeSelectReq);

		if (codeSelectRes == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return codeSelectRes;
	}

	/*****************************************************
	 * 공통코드 추가
	 * @param codeSaveReq
	 * @return int
	 * @throws Exception
	*****************************************************/
	public int insertComnCd(CodeSaveReq codeSaveReq) throws Exception {
		// 접속 아이디
		codeSaveReq.setCrtrId(SessionUtil.getLoginMngrId());

		// [[0]]. 반환할 정보
		int result = 0;
		// [[1]]. tb_comm_cd 데이터 추가
		result = codeMapper.insertComnCd(codeSaveReq);

		if (!(result > 0)) {
			throw new CustomMessageException("result-message.messages.common.message.save.error"); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return result;
	}

	/*****************************************************
	 * 공통코드 수정
	 * @param codeSaveReq
	 * @return int
	 * @throws Exception
	*****************************************************/
	public int updateComnCd(CodeSaveReq codeSaveReq) throws Exception {
		// 접속 아이디
		codeSaveReq.setMdfrId(SessionUtil.getLoginMngrId());

		// [[0]]. 반환할 정보
		int result = 0;
		// [[1]]. tb_comm_cd 데이터 수정
		result = codeMapper.updateComnCd(codeSaveReq);

		if (!(result > 0)) {
			throw new CustomMessageException("result-message.messages.common.message.update.error"); // 수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return result;
	}

	/*****************************************************
	 * 공통코드 정렬 순서 수정
	 * @param CodeSaveReq
	 * @return 성공 카운트
	 * @throws Exception
	*****************************************************/
	public int chgComnCdSeq(CodeSaveReq codeSaveReq) throws Exception {
		// [[0]]. 반환할 정보
		int result = 0;

		String comnGrpCd = codeSaveReq.getComnGrpCd(); // 공통그룹코드
		String currComdCd = codeSaveReq.getComnCd(); // 현재 공통코드
		String targetComnCd = codeSaveReq.getTargetComnCd(); // 변경 공통코드

		// [[1-1]]. 현재 공통코드
		CodeSelectReq codeSelectReq = new CodeSelectReq();
		CodeSelectRes codeSelectRes = new CodeSelectRes();

		codeSelectReq.setComnGrpCd(comnGrpCd);
		codeSelectReq.setComnCd(currComdCd);

		codeSelectRes = codeMapper.selectComnCdDetail(codeSelectReq); // 현재 공통코드 상세 조회

		if (codeSelectRes == null) {
			throw new CustomMessageException("result-message.messages.common.message.insert.error"); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// [[1-2]]. 변경 공통코드
		CodeSelectReq chgCodeSelectReq = new CodeSelectReq();
		CodeSelectRes chgCodeSelectRes = new CodeSelectRes();

		chgCodeSelectReq.setComnGrpCd(codeSaveReq.getComnGrpCd());
		chgCodeSelectReq.setComnCd(targetComnCd);

		chgCodeSelectRes = codeMapper.selectComnCdDetail(chgCodeSelectReq); // 변경 공통코드 상세 조회

		if (chgCodeSelectRes == null) {
			throw new CustomMessageException("result-message.messages.common.message.insert.error"); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// [[2]]. 정렬
		// [[2-1]]. 현재/변경 정렬 정보 세팅
		String seqType = codeSaveReq.getSeqType(); // 정렬 타입

		CodeSaveReq chgCodeSaveReq = new CodeSaveReq();
		chgCodeSaveReq.setComnGrpCd(comnGrpCd);
		chgCodeSaveReq.setComnCd(targetComnCd);

		int nSrtSeq = codeSelectRes.getSrtSeq(); // 현재 정렬 순서
		int nChgSrtSeq = chgCodeSelectRes.getSrtSeq(); // 변경 정렬 순서
		int nDiffCnt = 0; // 정렬 차

		// [[2-2]]. 정렬 순서 담기
		if( "up".equals(seqType.toLowerCase()) ) {
			nDiffCnt = nSrtSeq - nChgSrtSeq;

			codeSaveReq.setSrtSeq(nSrtSeq - nDiffCnt);
			chgCodeSaveReq.setSrtSeq( nChgSrtSeq + nDiffCnt );
		} else {
			nDiffCnt = nChgSrtSeq - nSrtSeq;

			codeSaveReq.setSrtSeq( nSrtSeq + nDiffCnt );
			chgCodeSaveReq.setSrtSeq( nChgSrtSeq - nDiffCnt );
		}

		// [[3]]. 정렬 순서 수정
		// [[3-1]]. 현재 정렬 순서 수정
		result = codeMapper.updateComnCdSeq(codeSaveReq);

		if(!(result > 0)) {
			throw new CustomMessageException("result-message.messages.common.message.insert.error"); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// [[3-1]]. 변경 정렬 순서 수정
		result = codeMapper.updateComnCdSeq(chgCodeSaveReq);

		if(!(result > 0)) {
			throw new CustomMessageException("result-message.messages.common.message.insert.error"); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return result;
	}

	/*****************************************************
	 * 공통그룹코드/공통코드 데이터 존재 여부 (갯수)
	 * @param codeSelectReq
	 * @return 데이터 갯수
	*****************************************************/
	public int countComnCd(CodeSelectReq codeSelectReq) {
		// [[0]]. 반환할 정보
		int result = 0;
		// [[1]]. 조회 데이터 갯수 반환
		result = codeMapper.countComnCd(codeSelectReq);

		return result;
	}

	/*****************************************************
	 * 공통그룹코드 데이터 존재 여부 (갯수)
	 * @param codeSelectReq
	 * @return
	*****************************************************/
	public int countComnGrpCd(CodeSelectReq codeSelectReq) {
		// [[0]]. 반환할 정보
		int result = 0;
		// [[1]]. 조회 데이터 갯수 반환
		result = codeMapper.countComnGrpCd(codeSelectReq);

		return result;
	}

}

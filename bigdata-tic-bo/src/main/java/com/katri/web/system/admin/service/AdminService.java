package com.katri.web.system.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.EncryptUtil;
import com.katri.common.util.SessionUtil;
import com.katri.common.util.StringUtil;
import com.katri.web.comm.service.FileService;
import com.katri.web.system.admin.controller.AdminController;
import com.katri.web.system.admin.mapper.AdminMapper;
import com.katri.web.system.admin.model.AdminSaveReq;
import com.katri.web.system.admin.model.AdminSelectReq;
import com.katri.web.system.admin.model.AdminSelectRes;
import com.katri.web.system.admin.model.AuthorSelectRes;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
@Slf4j
public class AdminService {

	/** admin mapper */
	private final AdminMapper adminMapper;


	/*****************************************************
	 * 관리자 목록 조회
	 * @param adminSelectReq
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public List<AdminSelectRes> getAdminList(AdminSelectReq adminSelectReq) throws CustomMessageException {
		//[[0]].반환할 정보들
		List<AdminSelectRes> adminList = null;

		//[[1]].매퍼 호출
		adminList = adminMapper.getAdminList();
		if(adminList == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); //데이터가 없습니다.
		}

		return adminList;
	}

	/*****************************************************
	 * 총 관리자 수 조회
	 * @param AdminSelectReq
	 * @return int
	 * @throws CustomMessageException
	 *****************************************************/
	public int getAdminCnt(AdminSelectReq adminSelectReq) {
		//[[0]].반환할 정보들
		int adminCtnt = 0;

		//[1].
		adminCtnt = adminMapper.getAdminCnt(adminSelectReq);
		return adminCtnt;
	}

	/*****************************************************
	 * 아이디 중복 여부 확인
	 * @param String
	 * @return int
	 * @throws CustomMessageException
	 *****************************************************/
	public int getAdminIdCheck(String adminId) throws CustomMessageException {
		//[[0]]. 반환할 정보들
		int count	= 0;

		//[[1]].매퍼 호출
		count	= adminMapper.getAdminIdCheck(adminId);
		if(count < 0) {
			throw new CustomMessageException("result-message.messages.common.message.error"); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return count;
	}

	/*****************************************************
	 * 관리자 등록
	 * @param AdminSaveReq
	 * @return int
	 * @throws CustomMessageException
	 *****************************************************/
	public int regAdmin(AdminSaveReq adminData) throws CustomMessageException {
		//[[0]].
		int result			= 0;
		String EncryptPwd	= null;

		//[1]].
		/* Session에서 로그인한 아이디 가지고 오기 */
		String crtr_id = SessionUtil.getLoginMngrId();

		/* 비밀번호 암호화 */
		String eyptPwd = StringUtil.nvl(EncryptUtil.encryptSha256(adminData.getMngr_pwd(), adminData.getMngr_id()));

		//[[2]]. 데이터 셋팅
		/* 현재 로그인한 아이디 */
		adminData.setCrtr_id(crtr_id);
		/* 암호화 된 비밀번호 */
		adminData.setMngr_pwd(eyptPwd);

		//[[3]]. 결과
		result = adminMapper.regAdmin(adminData);

		if(result < 0) {
			throw new CustomMessageException("result-message.messages.common.message.save.error"); //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return result;
	}

	/*****************************************************
	 * 권한 목록 조회
	 * @param
	 * @return List
	 * @throws CustomMessageException
	 *****************************************************/
	public List<AuthorSelectRes> getAuthorList() throws CustomMessageException {
		//[[0]].
		List<AuthorSelectRes> authorList = null;

		//[[1]].
		authorList	= adminMapper.getAuthorList();

		if(authorList == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); //데이터가 없습니다.
		}

		return authorList;
	}

	/*****************************************************
	 * 관리자 상세 조회
	 * @param  String
	 * @return AdminSelectRes
	 * @throws CustomMessageException
	 *****************************************************/
	public AdminSelectRes getAdminDetail(String id) throws CustomMessageException {
		//[[0]].반환할 정보들
		AdminSelectRes adminDetail	= null;
		List<AuthorSelectRes> authorList	= null;

		//[[1]].조회
		/* 관리자 상세 데이터 조회 */
		adminDetail	= adminMapper.getAdminDetail(id);
		/* 권한 목록 조회 */
		authorList = adminMapper.getAuthorList();

		//[[2]].데이터 셋팅
		adminDetail.setAuthorList(authorList);

		if(adminDetail == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); //데이터가 없습니다.
		}

		return adminDetail;
	}

	/*****************************************************
	 * 관리자 정보 수정
	 * @param  AdminSaveReq
	 * @return int
	 * @throws CustomMessageException
	 *****************************************************/
	public int modifyAdmin(AdminSaveReq modifyData) throws CustomMessageException {
		//[[0]].반환할 정보
		int count	= 0;
		/* 세션에서 현재 로그인한 아이디 가져오기 */
		String mdfr_id = SessionUtil.getLoginMngrId();
		modifyData.setMdfr_id(mdfr_id);

		//[[1]]. 매퍼 호출
		count = adminMapper.modifyAdmin(modifyData);

		if(count <= 0) {
			throw new CustomMessageException("result-message.messages.common.message.update.error");	//수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return count;
	}


}

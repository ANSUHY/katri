package com.katri.web.system.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.katri.common.datasource.MainMapperAnnotation;
import com.katri.web.system.admin.model.AdminSaveReq;
import com.katri.web.system.admin.model.AdminSelectReq;
import com.katri.web.system.admin.model.AdminSelectRes;
import com.katri.web.system.admin.model.AuthorSelectRes;

@Repository
@Mapper
@MainMapperAnnotation
public interface AdminMapper {

	/*****************************************************
	 * 리스트 조회
	 * @param
	 * @return admin 리스트 조회
	 *****************************************************/
	public List<AdminSelectRes> getAdminList();

	public int getAdminCnt(AdminSelectReq adminSelectReq);


	/*****************************************************
	 * 아이디 중복 여부 확인
	 * @param
	 * @return
	 *****************************************************/
	public int getAdminIdCheck(String adminId);


	/*****************************************************
	 * 관리자 등록
	 * @param adminSaveReq
	 * @return int
	 *****************************************************/
	public int regAdmin(AdminSaveReq adminData);

	/*****************************************************
	 * 권한 목록 조회
	 * @param authorSelectRess
	 * @return List
	 *****************************************************/
	public List<AuthorSelectRes> getAuthorList();

	/*****************************************************
	 * 관리자 상세 조회
	 * @param String
	 * @return AdminSelectRes
	 *****************************************************/
	public AdminSelectRes getAdminDetail(String id);

	public int modifyAdmin(AdminSaveReq modifyData);

}

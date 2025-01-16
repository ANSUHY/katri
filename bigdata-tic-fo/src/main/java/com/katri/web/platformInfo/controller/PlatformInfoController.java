package com.katri.web.platformInfo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.katri.common.Const;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.CommonUtil;
import com.katri.common.util.StringUtil;
import com.katri.web.comm.service.CommService;
import com.katri.web.platformInfo.model.PlatformInfoSelectReq;
import com.katri.web.platformInfo.model.PlatformInfoSelectRes;
import com.katri.web.platformInfo.service.PlatformInfoService;
import com.nhncorp.lucy.security.xss.XssPreventer;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequiredArgsConstructor
@Api(tags = {"[플랫폼 소개] Controller"})
@Slf4j
public class PlatformInfoController {

	/** 공통 Service */
	private final CommService commService;

	/** [플랫폼 소개] Service */
	private final PlatformInfoService platformInfoService;

	/*****************************************************
	 * [플랫폼 소개] > [시험인증 빅데이터 플랫폼 소개] _페이지 + 데이터
	 * @param request
	 * @param response
	 * @param model
	 * @param platformInfoSelectReq
	 * @return 페이지
	 * @throws Exception
	*****************************************************/
	@RequestMapping(value = {"/platformInfo/testCertBicDataInfo", "/platformInfo/previewTestCertBicDataInfo"}, method = {RequestMethod.GET, RequestMethod.POST} )
	public String testCertBicDataInfo(HttpServletRequest request, HttpServletResponse response, Model model, PlatformInfoSelectReq platformInfoSelectReq) throws Exception {

		// [[0]]. 반환할 정보들 & 변수 지정
		PlatformInfoSelectRes platformInfoData		= new PlatformInfoSelectRes(); //페이지콘텐츠 정보

		try {

			// [[1]]. [플랫폼 소개] 각 페이지에 맞는 데이터 셋팅
			if("/platformInfo/previewTestCertBicDataInfo".equals(request.getRequestURI()) ) { //1-t1. BO에서 [미리보기] 할 경우

				// 1-t1-1.[플랫폼 소개] 데이터 + 내용 UNESCAPE
				String previewMenuCptnCn = platformInfoSelectReq.getPreviewMenuCptnCn();
				platformInfoData.setMenuCptnCnUnescape(XssPreventer.unescape(previewMenuCptnCn));

			} else { //1-t2. 메뉴에서 들어갈 경우

				// 1-t2-1.[플랫폼 소개] 각 페이지에 맞는 데이터 조회 + 내용 UNESCAPE
				platformInfoSelectReq.setSearchMenuCptnCd(Const.Code.MenuCompositionCode.INFO_TEST_CERT_BIC_DATA); //플랫폼 소개 > 시험인증 빅데이터 플랫폼 소개
				platformInfoData = platformInfoService.getPlatformInfoData(platformInfoSelectReq);
			}

		} catch (CustomMessageException e) {

			//값 셋팅
			String msg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

			//ALERT창띄우면서 뒤로 보내기
			CommonUtil.alertMsgBack(response, msg);

		} catch (Exception e) {

			throw e;

		}

		// [[2]]. 데이터 셋팅
		model.addAttribute("platformInfoData"	, platformInfoData);

		return "/platformInfo/testCertBicDataInfo";

	}

	/*****************************************************
	 * [플랫폼 소개] > [시험인증 산업 소개] _페이지 + 데이터
	 * @param request
	 * @param response
	 * @param model
	 * @param platformInfoSelectReq
	 * @return 페이지
	 * @throws Exception
	*****************************************************/
	@RequestMapping(value = {"/platformInfo/testCertIndustryInfo", "/platformInfo/previewTestCertIndustryInfo"}, method = {RequestMethod.GET, RequestMethod.POST} )
	public String testCertIndustryInfo(HttpServletRequest request, HttpServletResponse response, Model model, PlatformInfoSelectReq platformInfoSelectReq) throws Exception {

		// [[0]]. 반환할 정보들 & 변수 지정
		PlatformInfoSelectRes platformInfoData		= new PlatformInfoSelectRes(); //페이지콘텐츠 정보

		try {

			// [[1]]. [플랫폼 소개] 각 페이지에 맞는 데이터 셋팅
			if("/platformInfo/previewTestCertIndustryInfo".equals(request.getRequestURI()) ) { //1-t1. BO에서 [미리보기] 할 경우

				// 1-t1-1.[플랫폼 소개] 데이터 + 내용 UNESCAPE
				String previewMenuCptnCn = platformInfoSelectReq.getPreviewMenuCptnCn();
				platformInfoData.setMenuCptnCnUnescape(XssPreventer.unescape(previewMenuCptnCn));

			} else { //1-t2. 메뉴에서 들어갈 경우

				// 1-t2-1.[플랫폼 소개] 각 페이지에 맞는 데이터 조회 + 내용 UNESCAPE
				platformInfoSelectReq.setSearchMenuCptnCd(Const.Code.MenuCompositionCode.INFO_TEST_CERT_INDUSTRY); //플랫폼 소개 > 시험인증 산업소개
				platformInfoData = platformInfoService.getPlatformInfoData(platformInfoSelectReq);
			}

		} catch (CustomMessageException e) {

			//값 셋팅
			String msg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

			//ALERT창띄우면서 뒤로 보내기
			CommonUtil.alertMsgBack(response, msg);

		} catch (Exception e) {

			throw e;

		}

		// [[2]]. 데이터 셋팅
		model.addAttribute("platformInfoData"	, platformInfoData);

		return "/platformInfo/testCertIndustryInfo";

	}

	/*****************************************************
	 * [플랫폼 소개] > [참여기관 소개] _페이지 + 데이터
	 * @param request
	 * @param response
	 * @param model
	 * @param platformInfoSelectReq
	 * @return 페이지
	 * @throws Exception
	*****************************************************/
	@RequestMapping(value = {"/platformInfo/instiParticiInfo", "/platformInfo/previewInstiParticiInfo"}, method = {RequestMethod.GET, RequestMethod.POST} )
	public String instiParticiInfo(HttpServletRequest request, HttpServletResponse response, Model model, PlatformInfoSelectReq platformInfoSelectReq) throws Exception {

		// [[0]]. 반환할 정보들 & 변수 지정
		PlatformInfoSelectRes platformInfoData		= new PlatformInfoSelectRes(); //페이지콘텐츠 정보

		try {

			// [[1]]. [플랫폼 소개] 각 페이지에 맞는 데이터 셋팅
			if("/platformInfo/previewInstiParticiInfo".equals(request.getRequestURI()) ) { //1-t1. BO에서 [미리보기] 할 경우

				// 1-t1-1.[플랫폼 소개] 데이터 + 내용 UNESCAPE
				String previewMenuCptnCn = platformInfoSelectReq.getPreviewMenuCptnCn();
				platformInfoData.setMenuCptnCnUnescape(XssPreventer.unescape(previewMenuCptnCn));

			} else { //1-t2. 메뉴에서 들어갈 경우

				// 1-t2-1. 파라미터로 넘어온 institution로 갈 페이지 번호 지정
				String institution 	= StringUtil.nvl(platformInfoSelectReq.getInstitution());
				String pageCd 		= "";

				if( "2".equals( institution ) ) {
					pageCd = Const.Code.MenuCompositionCode.INFO_PARTICI_KTL; //플랫폼 소개 > 참여기관 소개 > KTL
				} else if( "3".equals( institution ) ) {
					pageCd = Const.Code.MenuCompositionCode.INFO_PARTICI_KTR; //플랫폼 소개 > 참여기관 소개 > KTR
				} else if( "4".equals( institution ) ) {
					pageCd = Const.Code.MenuCompositionCode.INFO_PARTICI_KCL; //플랫폼 소개 > 참여기관 소개 > KCL
				} else if( "5".equals( institution ) ) {
					pageCd = Const.Code.MenuCompositionCode.INFO_PARTICI_KTC; //플랫폼 소개 > 참여기관 소개 > KTC
				} else if( "6".equals( institution ) ) {
					pageCd = Const.Code.MenuCompositionCode.INFO_PARTICI_FITI; //플랫폼 소개 > 참여기관 소개 > FITI
				} else if( "7".equals( institution ) ) {
					pageCd = Const.Code.MenuCompositionCode.INFO_PARTICI_KOTITI; //플랫폼 소개 > 참여기관 소개 > KOTITI
				} else {
					pageCd = Const.Code.MenuCompositionCode.INFO_PARTICI_KATRI; //플랫폼 소개 > 참여기관 소개 > KATRI
				}

				// 1-t2-2.[플랫폼 소개] 각 페이지에 맞는 데이터 조회 + 내용 UNESCAPE
				platformInfoSelectReq.setSearchMenuCptnCd(pageCd);
				platformInfoData = platformInfoService.getPlatformInfoData(platformInfoSelectReq);
			}

		} catch (CustomMessageException e) {

			//값 셋팅
			String msg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

			//ALERT창띄우면서 뒤로 보내기
			CommonUtil.alertMsgBack(response, msg);

		} catch (Exception e) {

			throw e;

		}

		// [[2]]. 데이터 셋팅
		model.addAttribute("platformInfoData"	, platformInfoData);

		return "/platformInfo/instiParticiInfo";

	}

}

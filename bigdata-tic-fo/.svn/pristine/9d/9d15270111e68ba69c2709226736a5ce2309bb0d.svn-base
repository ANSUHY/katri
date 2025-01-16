package com.katri.web.dataUsageGuide.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.Common;
import com.katri.common.model.CommonRes;
import com.katri.common.model.ConfigMessage;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.CommonUtil;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.service.CommService;
import com.katri.web.dataUsageGuide.model.CertDataSelectReq;
import com.katri.web.dataUsageGuide.model.CertDataSelectRes;
import com.katri.web.dataUsageGuide.service.CertDataService;
import com.nhncorp.lucy.security.xss.XssPreventer;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequiredArgsConstructor
@Api(tags = {"[데이터 활용안내] > [인증데이터 조회] Controller"})
@Slf4j
public class CertDataController {

	/** 공통 Service */
	private final CommService commService;

	/** 메시지 Component */
	private final MessageSource messageSource;

	/** ConfigMessage Component */
	private final ConfigMessage configMessage;

	/** [인증데이터 조회] Service */
	private final CertDataService certDataService;

	/*****************************************************
	 * [데이터 활용안내] > [인증데이터 조회] 리스트_페이지
	 * @param request
	 * @param response
	 * @param model
	 * @param certDataSelectReq
	 * @return 페이지
	 * @throws Exception
	*****************************************************/
	@RequestMapping(value = {"/dataUsageGuide/certDataList", "/dataUsageGuide/previewCertDataList"}, method = {RequestMethod.GET, RequestMethod.POST} )
	public String certDataList(HttpServletRequest request , HttpServletResponse response, Model model, CertDataSelectReq certDataSelectReq) throws Exception {

		try {

			//=========== 검색조건 유지하기위해 =====================================================================
			model.addAttribute("schDatacertData"	, certDataSelectReq);

			//=========== select박스 값 셋팅 =====================================================================
			/* [##인증기관##] 리스트 */
			List<CertDataSelectRes> coInstList = certDataService.getCoInstList();
			model.addAttribute("coInstList"		, coInstList);
			/* [##인증상태##] 리스트 */
			List<CertDataSelectRes> certSttList = certDataService.getPtComnDtlCdList("ATC007");
			model.addAttribute("certSttList"	, certSttList);
			/* [##CO_법정제품분류##] 리스트 */
			List<CertDataSelectRes> prdtClfList = certDataService.getPtCoSttyPrdtClfCdList();
			model.addAttribute("prdtClfList"	, prdtClfList);

			//=========== BO에서 설정한 [##설명문장##] =====================================================================
			String menuCptnCnUnescape = "";
			if("/dataUsageGuide/previewCertDataList".equals(request.getRequestURI()) ) { //1-t1. BO에서 [미리보기] 할 경우

				// 1-t1-1.[##설명문장##] 데이터 + 내용 UNESCAPE
				String previewMenuCptnCn = certDataSelectReq.getPreviewMenuCptnCn();
				menuCptnCnUnescape = XssPreventer.unescape(previewMenuCptnCn);

			} else { //1-t2. 메뉴에서 들어갈 경우

				// 1-t2-1.[##설명문장##] 페이지에 맞는 데이터 조회 + 내용 UNESCAPE
				menuCptnCnUnescape = certDataService.getExplanMenuCptnCnUnescapeData();

			}
			model.addAttribute("menuCptnCnUnescape"	, menuCptnCnUnescape);

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

		return "/dataUsageGuide/certDataList";

	}

	/*****************************************************
	 * [데이터 활용안내] > [인증데이터 조회] 리스트_데이터
	 * @param boardSelectReq
	 * @return ResponseEntity<ResponseDto>
	*****************************************************/
	@GetMapping(value = {"/dataUsageGuide/getCertDataList"})
	public ResponseEntity<ResponseDto> getCertDataList( @ModelAttribute CertDataSelectReq certDataSelectReq) throws Exception  {

		// [[0]]. 반환할 정보들
		String msg 						= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 				= HttpStatus.BAD_REQUEST.value();
		int certDataCtnt			= 0;
		List<CertDataSelectRes> certDataList = null;
		CommonRes commonRes 			= new CommonRes();

		try {

			// [[1]]. Paging 변수 설정
			Common common = CommonUtil.setPageParams(certDataSelectReq.getCurrPage(), certDataSelectReq.getRowCount());
			certDataSelectReq.setStartRow(common.getStartRow());
			certDataSelectReq.setEndRow(common.getEndRow());

			// [[2]]. 데이터 조회
			/* 2-1.Cnt 정보*/
			certDataCtnt = certDataService.getCertDataCnt(certDataSelectReq);

			if(certDataCtnt != 0) {

				/* 2-2. List 정보*/
				certDataList = certDataService.getCertDataList(certDataSelectReq);

				resultCode = HttpStatus.OK.value();
				msg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.

			} else {

				resultCode = HttpStatus.OK.value();
				msg = messageSource.getMessage("result-message.messages.common.message.no.data", null, SessionUtil.getLocale()); // 데이터가 없습니다.

			}

			// [[3]]. 데이터 셋팅
			commonRes.setTotCnt(certDataCtnt);
			commonRes.setList(certDataList);

		} catch (CustomMessageException e) {

			//값 셋팅
			resultCode = HttpStatus.BAD_REQUEST.value();
			msg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

		} catch(Exception e) {

			throw e;

		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(commonRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);

	}

	/*****************************************************
	 * [데이터 활용안내] > [인증데이터 조회] 상세_페이지 + 데이터
	 * @param response
	 * @param model
	 * @param certDataSelectReq
	 * @return 페이지
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/dataUsageGuide/certDataDetail"})
	public String certDataDetail(HttpServletResponse response, Model model, @ModelAttribute CertDataSelectReq certDataSelectReq) throws Exception {

		/* 뒤로가기 방지 */
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.

		CertDataSelectRes certDataDetail = null;

		try {

			certDataDetail = certDataService.getCertDataDetail(certDataSelectReq);

		} catch (CustomMessageException e) {

			//값 셋팅
			String msg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

			//ALERT창띄우면서 List로 보내기
			CommonUtil.moveUrlAlertMsg(response, msg ,"/dataUsageGuide/certDataList");

		} catch(Exception e) {

			throw e;

		}

		// 데이터 셋팅
		model.addAttribute("schDatacertData"	, certDataSelectReq); //검색조건 유지하기위해
		model.addAttribute("certDataDetail"		, certDataDetail);

		return "/dataUsageGuide/certDataDetail";

	}

	/*****************************************************
	 * [데이터 활용안내] > [인증데이터 조회] 제품 이미지
	 * @param certDataSelectReq
	 * @param response
	 * @return 이미지 byte배열
	*****************************************************/
	@ResponseBody
	@GetMapping(value = {"/dataUsageGuide/displayImage"})
	public byte[] displayImage(HttpServletResponse response,  @ModelAttribute CertDataSelectReq certDataSelectReq)   {

		String instEngNmLower 	= certDataSelectReq.getInstEngNmLower();	//기관명(영문)_소문자
		String certNo 			= certDataSelectReq.getCertNo();			//인증번호
		String imgName 			= certDataSelectReq.getImgName();			//imgName(파일명)

		response.setContentType("image/jpg");
		byte[] fileArray = null;
		FileInputStream fis = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {

			int readCount = 0;
			byte[] buffer = new byte[1024];

			String strRootPath = configMessage.getArgumentsMessage("file.root.path", null);
			String strUrl = "/images/"+instEngNmLower+"/cert/"+certNo+"/"+imgName;
			fis = new FileInputStream(strRootPath+strUrl);

			while((readCount = fis.read(buffer)) != -1){
				baos.write(buffer, 0, readCount);
			}

			fileArray = baos.toByteArray();

		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {

			try {
				if(fis != null) {
					fis.close();
				}
				baos.close();
			} catch (IOException e) {
				log.error(e.getMessage());
			}

		}

		return fileArray;
	}


}

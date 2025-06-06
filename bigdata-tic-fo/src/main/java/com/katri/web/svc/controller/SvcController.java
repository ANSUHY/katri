package com.katri.web.svc.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.ConfigMessage;
import com.katri.common.util.CommonUtil;
import com.katri.common.util.EncryptUtil;
import com.katri.web.comm.service.CommService;
import com.katri.web.svc.model.SvcSelectReq;
import com.katri.web.svc.model.SvcSelectRes;
import com.katri.web.svc.service.SvcService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequiredArgsConstructor
@Api(tags = {"[QR랜딩페이지] Controller"})
@Slf4j
public class SvcController {

	/** 공통 Service */
	private final CommService commService;

	/** ConfigMessage Component */
	private final ConfigMessage configMessage;

	/** [QR랜딩페이지] Service */
	private final SvcService svcService;

	/*****************************************************
	 * [QR랜딩페이지] 페이지 + 데이터
	 * @param response
	 * @param model
	 * @param encQrKey
	 * @return 페이지
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/svc/certInfo/{encQrKey}"})
	public String certInfo(HttpServletResponse response, Model model, @PathVariable("encQrKey") String encQrKey) throws Exception {

		/* 뒤로가기 방지 */
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.

		SvcSelectRes certDataDetail = null;

		try {

			// 파라미터로 넘어온 값 check
			if( (encQrKey == null) || ("".equals(encQrKey)) ) {
				throw new CustomMessageException("result-message.messages.common.message.wrong.appr"); //잘못된 접근입니다.
			}

			//파라미터로 넘어온 값 decode
			String qrKey = EncryptUtil.decryptAes256(encQrKey.replaceAll("-SLASH-", "\\/"));

			//decode된 값 check
			if( (qrKey == null) || ("".equals(qrKey)) || (! qrKey.contains("||")) ) {
				throw new CustomMessageException("result-message.messages.common.message.wrong.appr"); //잘못된 접근입니다.
			}

			//값 분리
			String[] arrQrKey =  qrKey.split("\\|\\|");
			String instId = arrQrKey[0];
			String certNo = arrQrKey[1];

			//디테일 정보 가져오기
			SvcSelectReq svcSelectReq = new SvcSelectReq();
			svcSelectReq.setTargetInstId(instId); //기관아이디
			svcSelectReq.setTargetCertNo(certNo); //인증번호
			certDataDetail = svcService.getRfCertDataDetail(svcSelectReq);

		} catch (CustomMessageException e) {

			//값 셋팅
			String msg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

			//ALERT창띄우면서 뒤로 돌리기
			CommonUtil.alertMsgBack(response, msg);

		} catch(Exception e) {

			throw e;

		}

		// 데이터 셋팅
		model.addAttribute("certDataDetail"	, certDataDetail);
		model.addAttribute("encQrKey"		, encQrKey);

		return "/svc/certInfo";

	}

	/*****************************************************
	 * [QR랜딩페이지_인증 상세정보] 페이지 + 데이터
	 * @param response
	 * @param model
	 * @param encQrKey
	 * @return 페이지
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/svc/certInfoDetail/{encQrKey}"})
	public String certInfoDetail(HttpServletResponse response, Model model, @PathVariable("encQrKey") String encQrKey) throws Exception {

		/* 뒤로가기 방지 */
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.

		SvcSelectRes certDataDetail = null;

		try {

			// 파라미터로 넘어온 값 check
			if( (encQrKey == null) || ("".equals(encQrKey)) ) {
				throw new CustomMessageException("result-message.messages.common.message.wrong.appr"); //잘못된 접근입니다.
			}

			//파라미터로 넘어온 값 decode
			String qrKey = EncryptUtil.decryptAes256(encQrKey.replaceAll("-SLASH-", "\\/"));

			//decode된 값 check
			if( (qrKey == null) || ("".equals(qrKey)) || (! qrKey.contains("||")) ) {
				throw new CustomMessageException("result-message.messages.common.message.wrong.appr"); //잘못된 접근입니다.
			}

			//값 분리
			String[] arrQrKey =  qrKey.split("\\|\\|");
			String instId = arrQrKey[0];
			String certNo = arrQrKey[1];

			//디테일 정보 가져오기
			SvcSelectReq svcSelectReq = new SvcSelectReq();
			svcSelectReq.setTargetInstId(instId); //기관아이디
			svcSelectReq.setTargetCertNo(certNo); //인증번호
			certDataDetail = svcService.getRfCertDataDetail(svcSelectReq);

		} catch (CustomMessageException e) {

			//값 셋팅
			String msg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

			//ALERT창띄우면서 뒤로 돌리기
			CommonUtil.alertMsgBack(response, msg);

		} catch(Exception e) {

			throw e;

		}

		// 데이터 셋팅
		model.addAttribute("certDataDetail"	, certDataDetail);

		return "/svc/certInfoDetail";

	}

	/*****************************************************
	 * [QR랜딩페이지] 제품 이미지
	 * @param response
	 * @param myCertSelectReq
	 * @return 이미지 byte배열
	*****************************************************/
	@ResponseBody
	@GetMapping(value = {"/svc/certInfo/displayImage"})
	public byte[] displayImage(HttpServletResponse response,  @ModelAttribute SvcSelectReq svcSelectReq)   {

		String instEngNmLower 	= svcSelectReq.getInstEngNmLower();	//기관명(영문)_소문자
		String certNo 			= svcSelectReq.getCertNo();			//인증번호
		String imgName 			= svcSelectReq.getImgName();			//imgName(파일명)

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

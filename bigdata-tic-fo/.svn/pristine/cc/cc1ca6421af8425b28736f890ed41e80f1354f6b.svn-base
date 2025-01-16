package com.katri.web.comm.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.katri.common.model.ResponseDto;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.model.FileDto;
import com.katri.web.comm.model.QRReq;
import com.katri.web.comm.service.QRService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@Api(tags = {"파일 Controller"})
public class QRController {

	/** QR Service */
	private final QRService qrService;

	/** 메시지 Component */
	private final MessageSource messageSource;

	/*****************************************************
	 * QR 파일 정보 조회 (없으면 저장 후 파일정보)
	 * @param request 요청 정보
	 * @param qrReq QR 등록정보
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@GetMapping("/qr/getQRFileData")
	public ResponseEntity<ResponseDto> getQRFileData(HttpServletRequest request, @ModelAttribute QRReq qrReq) throws Exception {

		// [[0]]. 반환할 정보들 & 변수 지정
		String msg 				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		FileDto fileDto 		= new FileDto();

		// [[1]]. QR 파일 정보 조회 (없으면 저장 후 파일정보)
		fileDto = qrService.getQRFileData(qrReq);

		if(fileDto != null) {
			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
		} else {
			resultCode = HttpStatus.BAD_REQUEST.value();
			msg = messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale());  //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(fileDto)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build()
				, HttpStatus.OK);
	}

}

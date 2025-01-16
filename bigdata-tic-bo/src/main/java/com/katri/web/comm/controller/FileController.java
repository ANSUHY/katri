package com.katri.web.comm.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.ConfigMessage;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.model.FileDto;
import com.katri.web.comm.model.FileDwnldHistSaveReq;
import com.katri.web.comm.model.PageFileDto;
import com.katri.web.comm.service.FileService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@Api(tags = {"파일 Controller"})
public class FileController {

	/** 파일 Service */
	private final FileService fileService;

	/** 메시지 Component */
	private final MessageSource messageSource;

	/** ConfigMessage */
	private final ConfigMessage configMessage;

	/*****************************************************
	 * 파일 다운로드(ENCODE 파일번호받아서)
	 * @param request 요청객체
	 * @param response 반환 정보
	 * @param fileDto 파일 정보
	 * @throws Exception
	 *****************************************************/
	@RequestMapping("/file/downloadFile")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, FileDto fileDto) throws Exception {

		// ======= validation check
		if(fileDto == null || fileDto.getEncodeFileSn() == null || "".equals(fileDto.getEncodeFileSn())) {
			throw new CustomMessageException("result-message.messages.common.message.data.file.no.filesn"); //유효하지 않은 파일번호 입니다.
		}

		/* ======= 파일 다운로드 */
		FileDto newFileDto = fileService.downloadFile(request, response, fileDto);

		/* ======= 파일다운로드이력 등록 */
		FileDwnldHistSaveReq fileDwnldHistSaveReq = new FileDwnldHistSaveReq();
		fileDwnldHistSaveReq.setFileSn(newFileDto.getFileSn()); 		//파일일련번호
		fileDwnldHistSaveReq.setFileNm(newFileDto.getOrgnlFileNm()); 	//파일명
		fileService.savetFileDwnldHist(request, fileDwnldHistSaveReq);

	}

	/*****************************************************
	 * DB에 없는 파일 다운로드(파일은 실제로 넣어줘야함)
	 * @param request 요청객체
	 * @param response 반환 정보
	 * @param pageFileDto 파일 정보
	 * @throws Exception
	 *****************************************************/
	@PostMapping(value = "/file/pageFileDownload")
	@ResponseBody
	public void fileDownloadNoDB(HttpServletRequest request, HttpServletResponse response, PageFileDto pageFileDto) throws Exception {

		/* ======= 파일 다운로드 */
		fileService.downloadPageFile(request, response, configMessage.getArgumentsMessage("file.nodbdirs.path", null) ,pageFileDto);

		/* ======= 파일다운로드이력 등록 */
		FileDwnldHistSaveReq fileDwnldHistSaveReq = new FileDwnldHistSaveReq();
		fileDwnldHistSaveReq.setFileNm(pageFileDto.getOrgnlFileNm()); 	//파일명
		fileService.savetFileDwnldHist(request, fileDwnldHistSaveReq);

	}

	/*****************************************************
	 * 단일 파일 삭제(ENCODE 파일번호받아서)
	 * @param fileDto 파일 정보
	 * @throws Exception
	 *****************************************************/
	@PostMapping("/file/deleteFile")
	public ResponseEntity<ResponseDto> deleteFile(FileDto fileDto) throws Exception {

		// ======= validation check
		if(fileDto == null || fileDto.getEncodeFileSn() == null || "".equals(fileDto.getEncodeFileSn())) {
			throw new CustomMessageException("result-message.messages.common.message.data.file.no.filesn"); //유효하지 않은 파일번호 입니다.
		}

		// [[0]]. 반환할 정보들
		String msg 				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		int saveCount			= 0;

		// [[1]]. 단일 파일 삭제_물리
		fileService.deletePhyEncodeFileSn(fileDto);

		// [[2]]. 단일 파일 삭제_논리(DB)
		saveCount = fileService.deleteFileData(fileDto);

		if(saveCount > 0) {
			msg = messageSource.getMessage("result-message.messages.common.message.delete", null, SessionUtil.getLocale());  //정상적으로 삭제 하였습니다.
			resultCode = HttpStatus.OK.value();
		} else {
			msg = messageSource.getMessage("result-message.messages.common.message.delete.error", null, SessionUtil.getLocale());  //삭제 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			resultCode = HttpStatus.BAD_REQUEST.value();
		}

 		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(null)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);

	}

}

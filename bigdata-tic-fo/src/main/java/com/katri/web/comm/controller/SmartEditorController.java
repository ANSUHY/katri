package com.katri.web.comm.controller;


import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.katri.common.FileConst;
import com.katri.web.comm.model.FileDto;
import com.katri.web.comm.service.CommService;
import com.katri.web.comm.service.FileService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Api(tags = {"smartEditor Controller"})
@Slf4j
public class SmartEditorController {

	/** 공통 Service */
	private final CommService commService;

	/** 파일 Service */
	private final FileService fileService;

	/*****************************************************
	 * 네이버 에디터 파일 저장
	 * @param request 요청 정보
	 * @return String 경로
	 *****************************************************/
	@RequestMapping("/smarteditor/file_uploader")
	public String file_uploader(HttpServletRequest request) {

		String return1 = request.getParameter("callback");
		String return2 = "?callback_func=" + request.getParameter("callback_func");
		String return3 = "";

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		MultipartFile editor = multipartRequest.getFile("Filedata");

		try {
			if (editor != null && editor.getOriginalFilename() != null && !editor.getOriginalFilename().equals("")) {

				List<FileDto> listFileDto = null;

				try {
					/* 물리저장 + 파일dto 파일 목록 */
					listFileDto = fileService.savePhyFileReturnFileList(request, FileConst.File.FILE_DIV_NM_SMARTEDITOR);
				} catch (Exception e) {

					//값 셋팅
					String msg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

					//로그에 넣기
					log.error("\n===============ERROR=========================\n" + e.getMessage());
					log.error("\n===============ERROR_MSG=====================\n" + msg);

					return3 = "&errstr=" + URLEncoder.encode(msg, "UTF-8");
				}

				if(listFileDto != null && listFileDto.size() != 0) {

					FileDto fileDto = listFileDto.get(0);

					return3 += "&bNewLine=true";
					return3 += "&sFileName=" + URLEncoder.encode(fileDto.getOrgnlFileNm(), "UTF-8");
					return3 += "&sFileURL=" + fileDto.getStrgFilePathAddr() + fileDto.getStrgFileNm();

				}

			} else {
				return3 += "&errstr=error";
			}
		} catch (Exception e) {

			return3 = "&errstr=" + "error";

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
		}

		return "redirect:" + return1 + return2 + return3;
	}

}

package com.katri.web.comm.service;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.katri.common.Const;
import com.katri.common.FileConst;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.ConfigMessage;
import com.katri.common.util.CommonUtil;
import com.katri.common.util.SessionUtil;
import com.katri.common.util.StringUtil;
import com.katri.web.comm.mapper.FileMapper;
import com.katri.web.comm.model.FileDto;
import com.katri.web.comm.model.FileDwnldHistSaveReq;
import com.katri.web.comm.model.PageFileDto;
import com.katri.web.comm.model.QRReq;

import lombok.RequiredArgsConstructor;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 파일 관리 업무</li>
 * <li>서브 업무명 : 공통 관리</li>
 * <li>설		 명 : 파일 업로드 및 파일 다운로드 등 파일 관련 기능 제공</li>
 * <li>작   성   자 : Why T</li>
 * <li>작   성   일 : 2021. 03. 23.</li>
 * </ul>
 * <pre>
 * ======================================
 * 변경자/변경일 :
 * 변경사유/내역 :
 * --------------------------------------
 * 변경자/변경일 :
 * 변경사유/내역 :
 * ======================================
 * </pre>
 ***************************************************/
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class FileService {

	/**
	 * 파일 관리 업무 mapper
	 */
	private final FileMapper fileMapper;

	/**
	 * ConfigMessage
	 */
	private final ConfigMessage configMessage;

	/*****************************************************
	 * 파일 상세 조회 + fileSn 인코드
	 * @param fileDto 조회 정보
	 * @return FileDto 파일 상세 정보
	 * @throws Exception
	*****************************************************/
	public FileDto selectFile(FileDto fileDto) throws Exception {

		FileDto file = fileMapper.selectFile(fileDto);
		if(file != null) {
			Integer iFileSn = file.getFileSn();
			String strEncodeFileSn = StringUtil.setDSEncode(iFileSn.toString());
			file.setEncodeFileSn(strEncodeFileSn);
		}

		return file;
	}

	/*****************************************************
	 * 파일 목록 조회 + fileSn 인코드
	 * @param fileDto 조회 정보
	 * @return List<FileDto> 파일 목록 정보
	 * @throws Exception
	*****************************************************/
	public List<FileDto> selectFileList(FileDto fileDto) throws Exception {

		List<FileDto> listFile = fileMapper.selectFileList(fileDto);

		if (listFile != null && listFile.size() > 0) {
			for (int i = 0; i < listFile.size(); i++) {
				Integer iFileSn = listFile.get(i).getFileSn();
				String strEncodeFileSn = StringUtil.setDSEncode(iFileSn.toString());
				listFile.get(i).setEncodeFileSn(strEncodeFileSn);
			}
		}

		return listFile;
	}


	/*****************************************************
	 * QR ImageIO 물리저장 + 파일dto 파일 리턴
	 * @param strFileDivNm file구분 코드
	 * @param bufferedImage IMG 객체
	 * @param strFileExt 저장시 사용할 파일 확장자
	 * @param qrReq QR 등록정보
	 * @return FileDto QR 등록된 파일 정보
	 * @throws Exception
	*****************************************************/
	public FileDto savePhyQRImageFileReturnFile(String strFileDivNm, BufferedImage bufferedImage, String strFileExt, QRReq qrReq) throws Exception {

		// [[0]] 기본 셋팅
		FileDto fileDto = new FileDto();
		String strFolderName = ""; // file저장시킬 폴더명

		// [[1]] strFileDivNm로 저장시킬 폴더 만들기
		strFolderName = this.getFolderName(strFileDivNm);

		// [[2]] 폴더지정
		String strDatePath = getDatePath();
		String strRootPath = configMessage.getArgumentsMessage("file.root.path", null);
		String strSeparator = configMessage.getArgumentsMessage("file.separator", null);
		String strStrgFilePathAddr = strSeparator + strFolderName + strSeparator + strDatePath + strSeparator;

		String strFullFileFolderPath = strRootPath + strSeparator + strFolderName + strSeparator + strDatePath + strSeparator;

		// [[3]] 폴더생성
		this.makeFileFolder(strFullFileFolderPath);

		// [[4]] 파일 고유 식별자 생성
		String strUuid = UUID.randomUUID().toString();

		// [[5]] 파일객체 생성
		String strOrgnlFileNm = qrReq.getQrFileNm() + "." + strFileExt;
		String strStrgFileNm = strUuid + "." + strFileExt;
		File file = new File(strFullFileFolderPath, strStrgFileNm);

		// [[6]] ImageIO를 사용한 바코드 파일쓰기
		ImageIO.write(bufferedImage, strFileExt, file);

		// [[7]] 파일 DTO생성
		fileDto.setFile(file);								// 파일 객체
		fileDto.setOrgnlFileNm(strOrgnlFileNm);				// 소스 파일 명 (사용자가 업로드한 파일 명)
		fileDto.setStrgFileNm(strStrgFileNm);				// 논리 파일 명
		fileDto.setStrgFilePathAddr(strStrgFilePathAddr);	// 파일 경로
		fileDto.setFileSzVal(file.length());				// 파일 크기
		fileDto.setFileDivNm(strFileDivNm);					// 파일구분코드
		fileDto.setFileExtnNm(strFileExt);					// 파일 확장자
		fileDto.setLinkUrlAddr(qrReq.getQrUrl());			// qr에서 가르키는 url

		return fileDto;
	}

	/*****************************************************
	 * 물리저장 + 파일dto 파일 목록 리턴
	 * @param request 요청 정보
	 * @param setStrFileDivNm file구분 코드(만약에 파일구분코드가 안넣으면 input name을 fileDivNm로 간주)
	 * @return List<FileDto> 파일 정보
	 * @throws Exception
	*****************************************************/
	public List<FileDto> savePhyFileReturnFileList(HttpServletRequest request, String setStrFileDivNm) throws Exception {

		// [[0]] 기본 셋팅
		List<FileDto> listFileDto = new ArrayList<FileDto>(); // FileDto를 넣을 리스트
		List<MultipartFile> listMultipartFile = new ArrayList<MultipartFile>(); // MultipartFile을 넣을 리스트
		String strFolderName = ""; // file저장시킬 폴더명
		List<String> listExtension = new ArrayList<String>(); // 업로드 가능한 파일 확장자
		String strFileDivNm = ""; //파일구분코드

		// [[1]] ???????
		if (!MultipartHttpServletRequest.class.isAssignableFrom(request.getClass())) {
			return new ArrayList<FileDto>();
		}

		// [[2]]. request를 MultipartHttpServletRequest로 변경
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		// [[3]]. 사용자가 upload한 input name대로 iterator처리
		Iterator<String> iterator = multipartRequest.getFileNames();
		while(iterator.hasNext()) {

			/* 3-1. 사용자가 upload한 input name 명 (이걸로 폴더명, 업로드 파일 확장자 체크) */
			String fileParameterName = iterator.next();

			/* 3-2. 파라미터로 넘어온 strFileDivNm이 없으면 [사용자가 upload한 input name 명] 으로 셋팅 */
			if(setStrFileDivNm == null || "".equals(setStrFileDivNm) ) {
				strFileDivNm = fileParameterName;
			}else {
				strFileDivNm = setStrFileDivNm;
			}

			/* 3-3. strFileDivNm로 저장시킬 폴더, 업로드 가능한 파일 확장자 만들어주기*/
			strFolderName = this.getFolderName(strFileDivNm);
			listExtension = this.getFileExtension(strFileDivNm);

			/* 3-4. 한 input에 있는 파일 리스트 생성 */
			listMultipartFile = multipartRequest.getFiles(fileParameterName);

			/* 3-5. 한 input에 있는 파일 돌면서 저장*/
			int iFileCnt = listMultipartFile.size(); //한 input파일에 있는 파일 개수
			if (iFileCnt > 0) {

				/* 3-5-1. 폴더지정*/
				String strDatePath = getDatePath();
				String strRootPath = configMessage.getArgumentsMessage("file.root.path", null);
				String strSeparator = configMessage.getArgumentsMessage("file.separator", null);
				String strStrgFilePathAddr = strSeparator + strFolderName + strSeparator + strDatePath + strSeparator;

				String strFullFileFolderPath = strRootPath + strSeparator + strFolderName + strSeparator + strDatePath + strSeparator;

				/* 3-5-2. 폴더생성*/
				this.makeFileFolder(strFullFileFolderPath);

				/* 3-5-3. 한 input에 있는 파일 안에 파일 한개씩 파일물리 저장 및 FileDto 생성*/
				FileDto fileDto;
				for(MultipartFile multipartFile : listMultipartFile) {

					if (!multipartFile.isEmpty()) {

						// 파일 확장자 발췌 및 확장자 소문자 변환
						String strFileExt = multipartFile.getOriginalFilename();
						if (strFileExt != null && !"".equals(strFileExt)) {
							strFileExt = strFileExt.substring(strFileExt.lastIndexOf(".") + 1, strFileExt.length()).toLowerCase().trim();
						} else {
							strFileExt = "";
						}

						// 파일 확장자 및 사이즈 검증
						if (!validFileExtension(listExtension, strFileExt)) {
							multipartFile.getInputStream().close();
							throw new CustomMessageException("result-message.messages.common.message.data.file.exte.error"); //파일 확장자가 맞지않습니다. 올바른 파일을 넣으세요.
						}

						// 파일 고유 식별자 생성
						String strUuid = UUID.randomUUID().toString();

						// 파일객체 생성
						String strOrgnlFileNm = multipartFile.getOriginalFilename();
						String strStrgFileNm = strUuid + "." + strFileExt;
						File file = new File(strFullFileFolderPath,strStrgFileNm);
						file.setReadable(true);
						file.setWritable(true);

						// 실제파일 저장
//						multipartFile.transferTo(file);
						org.springframework.util.FileCopyUtils.copy(multipartFile.getBytes(), file);

						//DB저장시 등에 이용할 FileDto
						fileDto = new FileDto();

						fileDto.setFile(file);								// 파일 객체
						fileDto.setOrgnlFileNm(strOrgnlFileNm);				// 소스 파일 명 (사용자가 업로드한 파일 명)
						fileDto.setStrgFileNm(strStrgFileNm);				// 논리 파일 명
						fileDto.setStrgFilePathAddr(strStrgFilePathAddr);	// 파일 경로
						fileDto.setFileSzVal(multipartFile.getSize());		// 파일 크기
						fileDto.setFileDivNm(strFileDivNm);					// 파일구분코드
						fileDto.setFileExtnNm(strFileExt);					// 파일 확장자

						listFileDto.add(fileDto);
					}
				}
			}

		}

		return listFileDto;
	}

	/*****************************************************
	 * 파일 DB 정보 등록
	 * @param strRefDivVal 관계번호
	 * @param listFile 등록 파일 목록
	 * @return  listFile 등록파일에 등록 결과 추가
	 * @throws Exception
	*****************************************************/
	public List<FileDto> saveDBFile(String strRefDivVal, List<FileDto> listFile) throws Exception   {

		Integer iResult = 0;

		// 로그인된 사용자 아이디 조회
		String strLoginId = SessionUtil.getLoginMngrId();

		// 파일 데이터 저장
		if (listFile != null && listFile.size() > 0) {
			for (int i = 0; i < listFile.size(); i++) {
				FileDto fileDto = listFile.get(i);

				fileDto.setRefDivVal(strRefDivVal);		// 관련 번호
				if( fileDto.getCrtrId() == null || "".equals(fileDto.getCrtrId())){
					fileDto.setCrtrId(strLoginId);		// 등록자 아이디
				}

				iResult = fileMapper.insertFile(fileDto);
				if(! (iResult > 0 )) {
					throw new CustomMessageException("논리 저장중 오류");
				}

				//encode파일NO도 넣어줌
				Integer iFileSn = fileDto.getFileSn();
				String strEncodeFileSn = StringUtil.setDSEncode(iFileSn.toString());
				fileDto.setEncodeFileSn(strEncodeFileSn);

			}
		}

		return listFile;
	}

	/*****************************************************
	 * 단일 파일 물리 삭제(ENCODE 파일번호받아서_물리)
	 * @param fileDto 파일 정보
	 * @return String 반환 문자
	 * @throws Exception
	*****************************************************/
	public String deletePhyEncodeFileSn(FileDto fileDto) throws Exception {

		String strReturnMassage = "FileDeleteFalse";

		// Ecoding된 전달된 파일번호가 있을 경우 복호화 해서 FileSn 셋팅
		if(fileDto.getEncodeFileSn() != null && !"".equals(fileDto.getEncodeFileSn())) {

			// Ecoding된 전달된 파일번호
			String strEncodeFileSn = fileDto.getEncodeFileSn();

			// 파일 번호 복호화
			String strFileSn = StringUtil.setDSDecode(strEncodeFileSn);

			// 파일 DTO 셋팅
			fileDto.setFileSn(Integer.parseInt(strFileSn));

		}

		// 파일 정보 가져오기
		FileDto newFileDto = this.selectFile(fileDto);
		if( newFileDto == null ) {
			throw new CustomMessageException("result-message.messages.common.message.delete.error"); //삭제 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		// 가져온 파일 정보로 물리적 삭제
		strReturnMassage = this.deletePhyFile(newFileDto);

		return strReturnMassage;
	}

	/*****************************************************
	 * 단일 파일 삭제_물리
	 * @param fileDto 파일 정보
	 * @return String 반환 문자
	 * @throws Exception
	*****************************************************/
	public String deletePhyFile(FileDto fileDto) throws Exception {

		String strReturnMassage = "FileDeleteFalse";

		String strRootPath = configMessage.getArgumentsMessage("file.root.path", null);
		String strStrgFilePathAddr = fileDto.getStrgFilePathAddr();
		String strStrgFileNm = fileDto.getStrgFileNm();

		String strFilePath = strRootPath  + strStrgFilePathAddr + strStrgFileNm;

		// 물리 파일삭제
		File delFile = new File(strFilePath);

		if (!delFile.exists()) {
			//꼭 체크해줄필요는 없지
			//throw new CustomMessageException("파일 삭제에 실패했습니다.");
		} else {

			//파일 삭제하기
			delFile.delete();

		}
		strReturnMassage = "FileDeleteSuccess";

		return strReturnMassage;
	}

	/*****************************************************
	 * 단일 파일 삭제_논리(DB)
	 * @param fileDto  파일 정보
	 * @return int 성공 여부
	 * @throws Exception
	*****************************************************/
	public int deleteFileData(FileDto fileDto) throws Exception {

		int saveCount = 0;

		// Ecoding된 전달된 파일번호가 있을 경우 복호화 해서 FileSn 셋팅
		if(fileDto.getEncodeFileSn() != null && !"".equals(fileDto.getEncodeFileSn())) {

			// Ecoding된 전달된 파일번호
			String strEncodeFileSn = fileDto.getEncodeFileSn();

			// 파일 번호 복호화
			String strFileSn = StringUtil.setDSDecode(strEncodeFileSn);

			// 파일 DTO 셋팅
			fileDto.setFileSn(Integer.parseInt(strFileSn));

		}

		// 로그인된 사용자 아이디 조회
		String strLoginId = SessionUtil.getLoginMngrId();
		// 수정자 아이디
		if( fileDto.getMdfrId() == null || "".equals(fileDto.getMdfrId())){
			fileDto.setMdfrId(strLoginId);
		}

		// 논리 파일 삭제
		saveCount = fileMapper.updateDeleteFile(fileDto);
		if(! (saveCount > 0 ) ) {
			throw new CustomMessageException("result-message.messages.common.message.delete.error"); //삭제 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return saveCount;
	}

	/*****************************************************
	 * 파일 다운로드
	 * @param request 요청객체
	 * @param response 반환 정보
	 * @param fileDto 파일 정보
	 * @return DB에서 조회해온 fileDto 파일 정보
	 * @throws Exception
	*****************************************************/
	public FileDto downloadFile(HttpServletRequest request, HttpServletResponse response, FileDto fileDto) throws Exception {

		/* [[0]] Ecoding된 전달된 파일번호가 있을 경우 복호화 해서 FileSn 셋팅 */
		if(fileDto.getEncodeFileSn() != null && !"".equals(fileDto.getEncodeFileSn())) {

			// Ecoding된 전달된 파일번호
			String strEncodeFileSn = fileDto.getEncodeFileSn();

			// 파일 번호 복호화
			String strFileSn = StringUtil.setDSDecode(strEncodeFileSn);

			// 파일 DTO 셋팅
			fileDto.setFileSn(Integer.parseInt(strFileSn));

		}

		/* [[1]] 파일 정보 가져오기 */
		FileDto newFileDto = this.selectFile(fileDto);
		if (newFileDto == null) {
			throw new CustomMessageException("result-message.messages.common.message.data.file.no.error"); //다운로드 받을 파일이 없거나 오류가 발생하였습니다.
		}

		String strRootPath = configMessage.getArgumentsMessage("file.root.path", null);
		String strStrgFilePathAddr = newFileDto.getStrgFilePathAddr();
		String strStrgFileNm = newFileDto.getStrgFileNm();

		// 1-1. 파일 저장된 path
		String strFilePath = strRootPath  + strStrgFilePathAddr + strStrgFileNm;

		// 1-2. 파일 실제이름(다운로드시 이름)
		String strOrgnlFileNm = newFileDto.getOrgnlFileNm();

		/* [[2]] 파일 다운로드 */
		this.realDownloadPhyFile(request, response, strFilePath, strOrgnlFileNm);

		/* [3]] DB에서 조회해온 파일DTO 리턴 */
		return newFileDto;

	}

	/*****************************************************
	 * 파일 다운로드_DB에 없는 파일 다운로드
	 * @param request 요청 정보
	 * @param response 반환 정보
	 * @param pageFileDto 파일 정보_DB에 없는 파일
	 * @throws Exception
	*****************************************************/
	public void downloadPageFile(HttpServletRequest request, HttpServletResponse response, String strgFilePathAddr, PageFileDto pageFileDto) throws Exception {

		/* [[1]] 파일 정보  */
		String strRootPath = configMessage.getArgumentsMessage("file.root.path", null);
		String strStrgFilePathAddr = strgFilePathAddr;
		String strStrgFileNm = pageFileDto.getStrgFileNm();

		// 1-1. 파일 저장된 path
		String strFilePath = strRootPath  + strStrgFilePathAddr + strStrgFileNm;

		// 1-2. 파일 실제이름(다운로드시 이름)
		String strOrgnlFileNm = pageFileDto.getOrgnlFileNm();

		/* [[2]] 파일 다운로드 */
		this.realDownloadPhyFile(request, response, strFilePath, strOrgnlFileNm);

	}

	/*****************************************************
	 * 실제 파일 다운받는 메소드 _ 파일path받아서
	 * @param request 요청 정보
	 * @param response 반환 정보
	 * @param fileDto 파일 정보
	 * @throws Exception
	*****************************************************/
	public void realDownloadPhyFile(HttpServletRequest request, HttpServletResponse response, String strFilePath, String strOrgnlFileNm) throws Exception {

		InputStream inputStream = null;

		try {

			File file = new File(strFilePath);
			if (!file.exists()) {
				throw new CustomMessageException("result-message.messages.common.message.data.file.no.error"); //다운로드 받을 파일이 없거나 오류가 발생하였습니다.
			}

			// 파일 변환 타입
			String strMimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (strMimeType == null) {
				strMimeType = "application/octet-stream";
			}

			// 소스 파일 명
			strOrgnlFileNm = CommonUtil.getDisposition(request, strOrgnlFileNm);

			response.setHeader("Content-Transfer-Encoding", "binary;");
			response.setHeader("Pragma", "no-cache;");
			response.setHeader("Expires", "-1;");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + strOrgnlFileNm + "\"");
			response.setContentType(strMimeType);
			response.setContentLength((int) file.length());

			inputStream = new BufferedInputStream(new FileInputStream(file));

			FileCopyUtils.copy(inputStream, response.getOutputStream());

		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

	}

	/*****************************************************
	 * 파일 년월일 기반 저장 경로 생성
	 * @return String 기반 저장 경로
	 * @throws Exception
	*****************************************************/
	public static String getDatePath() throws Exception {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

		Calendar calendar = Calendar.getInstance();

		String strDatePath = format.format(calendar.getTime());

		return strDatePath;
	}

	/*****************************************************
	 * 파일 폴더 생성
	 * @param strFullFileFolderPath 경로
	 * @throws Exception
	*****************************************************/
	private void makeFileFolder(String strFullFileFolderPath) throws Exception {

		String strMakeDir =  strFullFileFolderPath;
		File dir = new File(strMakeDir);
		dir.setExecutable(true);
		dir.setReadable(true);
		dir.setWritable(true);

		if (!dir.isDirectory()) {
			dir.mkdirs();
		}

	}

	/*****************************************************
	 * 파일 확장자 검증
	 * @param listExtension
	 * @param strFileExt
	 * @return boolean 통과여부
	 * @throws Exception
	*****************************************************/
	private boolean validFileExtension(List<String> listExtension, String strFileExt) throws Exception {

		boolean bResult = false;

		if (strFileExt == null || "".equals(strFileExt)) {
			return false;
		}

		for (int i = 0; i < listExtension.size(); i++) {
			strFileExt = strFileExt.toLowerCase();

			// 업로드 가능한 파일 확장자 검증
			if (listExtension.get(i).equals(strFileExt)) {
				return true;
			}
		}

		return bResult;
	}

	/*****************************************************
	 * 게시글의 업로드 파일 개수
	 * @param fileDto
	 * @return 게시글의 업로드 파일 개수
	*****************************************************/
	public int getFileCnt(FileDto fileDto) {
		int fileCnt = 0;

		fileCnt = fileMapper.selectFileCnt(fileDto);

		return fileCnt;
	}

	/*****************************************************
	 * 순번 증가
	 * @param file
	 * @return
	*****************************************************/
	public int chgFileSrtSeq(FileDto file) {
		int uCount = 0;

		uCount = fileMapper.chgFileSrtSeq(file);

		return uCount;
	}

	/*****************************************************
	 * 파일다운로드이력 등록
	 * @param request 요청객체
	 * @param fileDwnldHistSaveReq
	 * @throws Exception
	*****************************************************/
	public void savetFileDwnldHist(HttpServletRequest request, FileDwnldHistSaveReq fileDwnldHistSaveReq) throws Exception {

		// 로그인된 사용자 아이디 조회
		String strLoginId = SessionUtil.getLoginMngrId();

		/* 값 셋팅 */
		if( ( Const.Session.SERVER_CLS_CD_FO ).equals(SessionUtil.getSessionAttribute("server_cls_cd")) ){ //FO일때만 넣어줌
			fileDwnldHistSaveReq.setEqmtCntnId(CommonUtil.getCookieObject(request, "eqmtCntnId"));	//장비접속아이디
			fileDwnldHistSaveReq.setSesionCntnId(CommonUtil.getCookieObject(request, "ssnCntnId")); //세션접속아이디
		}
		if( fileDwnldHistSaveReq.getCrtrId() == null || "".equals(fileDwnldHistSaveReq.getCrtrId())){
			fileDwnldHistSaveReq.setCrtrId(strLoginId);		//생성자아이디
		}
		fileDwnldHistSaveReq.setUserIpAddr(CommonUtil.getClientIP(request));//사용자IP주소

		fileMapper.insertFileDwnldHist(fileDwnldHistSaveReq);

	}

////밑에는 BO, FO다름/////////////////////////////////////////////////////밑에는 BO, FO다름////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////밑에는 BO, FO다름/////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////밑에는 BO, FO다름//////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////밑에는 BO, FO다름//////////////////////////////

	/*****************************************************
	 * 업무 구분 코드에 따른 폴더명 조회
	 * @param strFileDivNm file구분 코드
	 * @return String 경로
	 * @throws Exception
	*****************************************************/
	public String getFolderName(String strFileDivNm) throws Exception {
		String strFolderName = "";

		if (FileConst.File.FILE_DIV_NM_MBR_IMG.equals(strFileDivNm)) {				// Member IMG
			strFolderName = configMessage.getArgumentsMessage("file.mkdirs.path", null) + FileConst.Folder.FOLDER_NM_MBR_IMG;
		} else if(FileConst.File.FILE_DIV_NM_SMARTEDITOR.equals(strFileDivNm)){		//smarteditor
			strFolderName = configMessage.getArgumentsMessage("file.mkdirs.path", null) + FileConst.Folder.FOLDER_NM_SMARTEDITOR;
		} else if(FileConst.File.FILE_DIV_NM_BOARD_IMG.equals(strFileDivNm)){ 		//BOARD IMG
			strFolderName = configMessage.getArgumentsMessage("file.mkdirs.path", null) + FileConst.Folder.FOLDER_NM_BOARD_IMG;
		} else if(FileConst.File.FILE_DIV_NM_BOARD_FILE.equals(strFileDivNm)){ 		//BOARD FILE
			strFolderName = configMessage.getArgumentsMessage("file.mkdirs.path", null) + FileConst.Folder.FOLDER_NM_BOARD_FILE;
		} else if(FileConst.File.FILE_DIV_NM_QR_FILE.equals(strFileDivNm)){ 		//QR FILE
			strFolderName = configMessage.getArgumentsMessage("file.mkdirs.path", null) + FileConst.Folder.FOLDER_NM_QR_FILE;
		} else if(FileConst.File.FILE_DIV_NM_VISUAL_PC_IMG.equals(strFileDivNm)){ 	//MAIN PC IMG
			strFolderName = configMessage.getArgumentsMessage("file.mkdirs.path", null) + FileConst.Folder.FOLDER_NM_VISUAL_PC_IMG;
		} else if(FileConst.File.FILE_DIV_NM_VISUAL_MOBILE_IMG.equals(strFileDivNm)){ 	//MAIN MOBILE IMG
			strFolderName = configMessage.getArgumentsMessage("file.mkdirs.path", null) + FileConst.Folder.FOLDER_NM_VISUAL_MOBILE_IMG;
		} else if(FileConst.File.FILE_DIV_NM_POPUP_PC_IMG.equals(strFileDivNm)){		//POPUP PC IMG
			strFolderName = configMessage.getArgumentsMessage("file.mkdirs.path", null) + FileConst.Folder.FOLDER_NM_POPUP_PC_IMG;
		} else if(FileConst.File.FILE_DIV_NM_POPUP_MOBILE_IMG.equals(strFileDivNm)){		//POPUP MOBILE IMG
			strFolderName = configMessage.getArgumentsMessage("file.mkdirs.path", null) + FileConst.Folder.FOLDER_NM_POPUP_MOBILE_IMG;
		} else if(FileConst.File.FILE_DIV_NM_GNRL_CERT_FILE.equals(strFileDivNm)){ 		//GNRL CERT FILE
			strFolderName = configMessage.getArgumentsMessage("file.mkdirs.path", null) + FileConst.Folder.FOLDER_NM_GNRL_CERT_FILE;
		} else if(FileConst.File.FILE_DIV_NM_BZMN_REG_FILE.equals(strFileDivNm)){ 		//BZMN REG FILE
			strFolderName = configMessage.getArgumentsMessage("file.mkdirs.path", null) + FileConst.Folder.FOLDER_NM_BZMN_REG_FILE;
		} else if(FileConst.File.FILE_DIV_NM_ACNT_ISSU_APLY_FILE.equals(strFileDivNm)){ 	//ACNT ISSU APLY FILE
			strFolderName = configMessage.getArgumentsMessage("file.mkdirs.path", null) + FileConst.Folder.FOLDER_NM_ACNT_ISSU_APLY_FILE;
		} else {																	// Temporary
			strFolderName = configMessage.getArgumentsMessage("file.mkdirs.path", null) + FileConst.Folder.FOLDER_NM_TP;
		}

		return strFolderName;
	}

	/*****************************************************
	 * 업로드 가능한 파일 확장자 조회
	 * @param strFileDivNm file구분 코드
	 * @return List<String> 확장자 리스트
	 * @throws Exception
	*****************************************************/
	public List<String> getFileExtension(String strFileDivNm) throws Exception {

		List<String> listExtension = new ArrayList<String>();

		if (FileConst.File.FILE_DIV_NM_MBR_IMG.equals(strFileDivNm)) {				//Member IMG
			listExtension = Arrays.asList("jpg", "gif", "png");
		} else if (FileConst.File.FILE_DIV_NM_SMARTEDITOR.equals(strFileDivNm)){ 	//smarteditor
			listExtension = Arrays.asList("jpg", "png", "bmp", "gif");
		} else if (FileConst.File.FILE_DIV_NM_BOARD_IMG.equals(strFileDivNm)){ 		//BOARD IMG
			listExtension = Arrays.asList("jpg", "png", "gif");
		} else if (FileConst.File.FILE_DIV_NM_BOARD_FILE.equals(strFileDivNm)){		//BOARD FILE
			listExtension = Arrays.asList("mp3","wma","wav","m4a","3gp","hwp","doc","docx","ppt","pptx","zip","xls","xlsx","pdf","txt","jpg","gif","png");
		} else if (FileConst.File.FILE_DIV_NM_GNRL_CERT_FILE.equals(strFileDivNm)){	//GNRL CERT FILE
			listExtension = Arrays.asList("png", "jpg", "gif", "pdf", "doc");
		} else if (FileConst.File.FILE_DIV_NM_BZMN_REG_FILE.equals(strFileDivNm)){	//BZMN REG FILE
			listExtension = Arrays.asList("png", "jpg", "gif", "pdf", "doc");
		} else if (FileConst.File.FILE_DIV_NM_ACNT_ISSU_APLY_FILE.equals(strFileDivNm)){	//ACNT ISSU APLY FILE
			listExtension = Arrays.asList("png", "jpg", "gif", "pdf", "doc");
		} else {																	// Temporary
			listExtension = Arrays.asList("mp3","wma","wav","m4a","3gp","hwp","doc","docx","ppt","pptx","zip","xls","xlsx","pdf","txt","jpg","gif","png");
		}

		return listExtension;
	}

}

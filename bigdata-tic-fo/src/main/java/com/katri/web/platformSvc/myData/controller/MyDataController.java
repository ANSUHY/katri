package com.katri.web.platformSvc.myData.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.katri.common.Const;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.Common;
import com.katri.common.model.CommonRes;
import com.katri.common.model.ConfigMessage;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.CommonUtil;
import com.katri.common.util.ExcelUtil;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.model.FileDto;
import com.katri.web.comm.service.CommService;
import com.katri.web.platformSvc.myData.model.MyDataSaveReq;
import com.katri.web.platformSvc.myData.model.MyDataSaveRes;
import com.katri.web.platformSvc.myData.model.MyDataSelectReq;
import com.katri.web.platformSvc.myData.model.MyDataSelectRes;
import com.katri.web.platformSvc.myData.service.MyDataService;
import com.nhncorp.lucy.security.xss.XssPreventer;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequiredArgsConstructor
@Api(tags = {"[플랫폼 서비스] > [내 손안의 시험인증] Controller"})
@Slf4j
public class MyDataController {

	/** 공통 Service */
	private final CommService commService;

	/** 메시지 Component */
	private final MessageSource messageSource;

	/** ConfigMessage Component */
	private final ConfigMessage configMessage;

	/** [플랫폼 서비스] > [내 손안의 시험인증] Service */
	private final MyDataService myDataService;

	/*****************************************************
	 * [플랫폼 서비스] > [내 손안의 시험인증] > [서비스 안내](기업그룹수집동의이력 Y인것이 0개일때) _페이지 + 데이터
	 * @param request
	 * @param response
	 * @param model
	 * @param myDataSelectReq
	 * @return 페이지
	 * @throws Exception
	*****************************************************/
	@RequestMapping(value = {"/platformSvc/myData/introSvc", "/platformSvc/myData/previewIntroSvc"}, method = {RequestMethod.GET, RequestMethod.POST} )
	public String introSvc(HttpServletRequest request, HttpServletResponse response, Model model, MyDataSelectReq myDataSelectReq) throws Exception {

		// [[0]]. 반환할 정보들 & 변수 지정
		MyDataSelectRes introSvcData		= new MyDataSelectRes(); //페이지콘텐츠 정보

		try {

			// [[1]].============ [플랫폼 서비스] > [내 손안의 시험인증] > [서비스 안내] 각 페이지에 맞는 데이터 셋팅
			if("/platformSvc/myData/previewIntroSvc".equals(request.getRequestURI()) ) { //1-t1. BO에서 [미리보기] 할 경우

				// 1-t1-1.[플랫폼 서비스] > [내 손안의 시험인증] > [서비스 안내] 데이터 + 내용 UNESCAPE
				String previewMenuCptnCn = myDataSelectReq.getPreviewMenuCptnCn();
				introSvcData.setMenuCptnCnUnescape(XssPreventer.unescape(previewMenuCptnCn));

			} else { //1-t2. 메뉴에서 들어갈 경우

				// 1-t2-1.[플랫폼 서비스] > [내 손안의 시험인증] > [서비스 안내] 각 페이지에 맞는 데이터 조회 + 내용 UNESCAPE
				myDataSelectReq.setSearchMenuCptnCd(Const.Code.MenuCompositionCode.SERVICE_INTRO_SVC); //플랫폼 서비스 > 내 손안의 시험인증 > 서비스 안내
				introSvcData = myDataService.getIntroSvcData(myDataSelectReq);

			}

			// [[2]].============ 로그인한 사용자가 [기업마스터]일때 : 이용약관 셋팅
			if(SessionUtil.getLoginUserTyCd() != null && Const.Code.UserTyCd.ENT_MASTER.equals(SessionUtil.getLoginUserTyCd())) {

				// 2-1. [서비스 신청 POP]에서 쓰일 이용약관 model에 셋팅
				myDataService.setPopSvcApplTerms(model);

				// 2-2. [서비스 신청 POP]에서 쓰일 기업리스트
				MyDataSelectReq newMyDataSelectReq = new MyDataSelectReq();
				newMyDataSelectReq = new MyDataSelectReq();
				newMyDataSelectReq.setSearchInfoPvsnAgreYn("N"); //검색 정보제공동의여부
				List<MyDataSelectRes> svcNoConnInstList	= myDataService.getCoInstEntGrpClctAgreList(newMyDataSelectReq);
				model.addAttribute("svcNoConnInstList", svcNoConnInstList);

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

		model.addAttribute("introSvcData"	, introSvcData);

		return "/platformSvc/myData/introSvc";

	}

	/*****************************************************
	 * [플랫폼 서비스] > [내 손안의 시험인증] > [나의 시험인증 정보조회 : 서비스관리]
	 * @param response
	 * @param model
	 * @param myDataSelectReq
	 * @return 페이지
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/platformSvc/myData/myInfoCert/svcMng"})
	public String svcMng(HttpServletResponse response, Model model, MyDataSelectReq myDataSelectReq) throws Exception {

		try {

			// [[0]].============ 권한체크
			if( ! Const.Code.UserTyCd.ENT_MASTER.equals(SessionUtil.getLoginUserTyCd()) ){
				String msg = commService.rtnMessageDfError("result-message.messages.login.message.not.auth"); // '해당 아이디에 권한이 부여되어 있지 않습니다. 관리자에게 문의하여 주십시요.'
				CommonUtil.alertMsgBack(response, msg);
			}

			// [[1]].============ 데이터 셋팅
			MyDataSelectReq newMyDataSelectReq = new MyDataSelectReq();

			/* 1-1. {서비스 연결 기관 && 제출처 제공신청POP} 에 필요한 리스트 */
			newMyDataSelectReq = new MyDataSelectReq();
			newMyDataSelectReq.setSearchInfoPvsnAgreYn("Y"); //검색 정보제공동의여부
			newMyDataSelectReq.setNeedSbmsnCoRlsAgreYn("Y"); //제출회사공개여부 필요한지(쿼리에 넘겨서 쓰임)
			List<MyDataSelectRes> svcConnInstList	= myDataService.getCoInstEntGrpClctAgreList(newMyDataSelectReq);
			model.addAttribute("svcConnInstList", svcConnInstList);

			/* 1-2. {서비스 기관 추가하기 && 추가신청POP} 에 필요한 리스트 */
			newMyDataSelectReq = new MyDataSelectReq();
			newMyDataSelectReq.setSearchInfoPvsnAgreYn("N"); //검색 정보제공동의여부
			List<MyDataSelectRes> svcNoConnInstList	= myDataService.getCoInstEntGrpClctAgreList(newMyDataSelectReq);
			model.addAttribute("svcNoConnInstList", svcNoConnInstList);

			// [[2]].============ 이용약관 셋팅
			/* 2-1. [서비스 신청 POP]에서 쓰일 이용약관 model에 셋팅 */
			myDataService.setPopSvcApplTerms(model);


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

		return "/platformSvc/myData/myInfoCert/svcMng";

	}

	/*****************************************************
	 * [플랫폼 서비스] > [내 손안의 시험인증] > [나의 시험인증 정보조회 : 인증] 리스트_페이지
	 * @param response
	 * @param model
	 * @param myDataSelectReq
	 * @return 페이지
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/platformSvc/myData/myInfoCert/myCertList"})
	public String myCertList(HttpServletResponse response, Model model, MyDataSelectReq myDataSelectReq) throws Exception {

		try {

			//=========== 검색조건 유지하기위해 =====================================================================
			model.addAttribute("schDatamyCert"	, myDataSelectReq);

			//=========== select박스 값 셋팅 =====================================================================
			/* [##인증기관##] 리스트 */
			List<MyDataSelectRes> coInstList = myDataService.getCoInstList();
			model.addAttribute("coInstList"		, coInstList);
			/* [##인증상태##] 리스트 */
			List<MyDataSelectRes> certSttList = myDataService.getPtComnDtlCdList("ATC007");
			model.addAttribute("certSttList"	, certSttList);
			/* [##CO_법정제품분류##] 리스트 */
			List<MyDataSelectRes> prdtClfList = myDataService.getPtCoSttyPrdtClfCdList();
			model.addAttribute("prdtClfList"	, prdtClfList);

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

		return "/platformSvc/myData/myInfoCert/myCertList";

	}

	/*****************************************************
	 * [플랫폼 서비스] > [내 손안의 시험인증] > [나의 시험인증 정보조회 : 인증] 리스트_데이터
	 * @param MyDataSelectReq
	 * @return ResponseEntity<ResponseDto>
	*****************************************************/
	@GetMapping(value = {"/platformSvc/myData/myInfoCert/getMyCertList"})
	public ResponseEntity<ResponseDto> getMyCertList( @ModelAttribute MyDataSelectReq myCertSelectReq) throws Exception  {

		// [[0]]. 반환할 정보들
		String msg 						= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 				= HttpStatus.BAD_REQUEST.value();
		int myCertCnt					= 0;
		List<MyDataSelectRes> myCertList = null;
		CommonRes commonRes 			= new CommonRes();

		try {

			// [[1]]. Paging 변수 설정
			Common common = CommonUtil.setPageParams(myCertSelectReq.getCurrPage(), myCertSelectReq.getRowCount());
			myCertSelectReq.setStartRow(common.getStartRow());
			myCertSelectReq.setEndRow(common.getEndRow());

			// [[2]]. 데이터 조회
			/* 2-1.Cnt 정보*/
			myCertCnt = myDataService.getMyCertCnt(myCertSelectReq);

			if(myCertCnt != 0) {

				/* 2-2. List 정보*/
				myCertList = myDataService.getMyCertList(myCertSelectReq);

				resultCode = HttpStatus.OK.value();
				msg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.

			} else {

				resultCode = HttpStatus.OK.value();
				msg = messageSource.getMessage("result-message.messages.common.message.no.data", null, SessionUtil.getLocale()); // 데이터가 없습니다.

			}

			// [[3]]. 데이터 셋팅
			commonRes.setTotCnt(myCertCnt);
			commonRes.setList(myCertList);

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
	 * [플랫폼 서비스] > [내 손안의 시험인증] > [나의 시험인증 정보조회 : 인증] 엑셀다운로드(리스트)
	 * @param request
	 * @param  response
	 * @param myCertSelectReq
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/platformSvc/myData/myInfoCert/downExcelMyCertList"})
	public void downExcelMyCertList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute MyDataSelectReq myCertSelectReq) throws Exception {

		ExcelUtil excelUtil = new ExcelUtil();

		try {

			// [[0]]. List 정보
			List<Map<String, Object>> list = myDataService.getMyCertListMap(myCertSelectReq);

			// [[1]]. 브라우저별 한글 깨짐 방지
			String excelName = CommonUtil.getDisposition(request, "인증목록");

			// [[2]]. 엑셀 다운로드
			excelUtil.download2(request, response, list, excelName, "my_cert_list_template.xlsx");

		} catch (CustomMessageException e) {

			//값 셋팅
			String msg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

			//ALERT창띄우면서 뒤로 보내기
			CommonUtil.alertMsgBack(response, msg);

		} catch(Exception e) {

			throw e;

		}

	}

	/*****************************************************
	 * [플랫폼 서비스] > [내 손안의 시험인증] > [나의 시험인증 정보조회 : 인증] 상세_페이지 + 데이터
	 * @param response
	 * @param model
	 * @param myCertSelectReq
	 * @return 페이지
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/platformSvc/myData/myInfoCert/myCertDetail"})
	public String myCertDetail(HttpServletResponse response, Model model, @ModelAttribute MyDataSelectReq myCertSelectReq) throws Exception {

		/* 뒤로가기 방지 */
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.

		MyDataSelectRes myCertDetail = null;

		try {

			myCertDetail = myDataService.getMyCertDetail(myCertSelectReq);

		} catch (CustomMessageException e) {

			//값 셋팅
			String msg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

			//ALERT창띄우면서 List로 보내기
			CommonUtil.moveUrlAlertMsg(response, msg ,"/platformSvc/myData/myInfoCert/myCertList");

		} catch(Exception e) {

			throw e;

		}

		// 데이터 셋팅
		model.addAttribute("schDatamyCert"	, myCertSelectReq); //검색조건 유지하기위해
		model.addAttribute("myCertDetail"	, myCertDetail);

		return "/platformSvc/myData/myInfoCert/myCertDetail";

	}

	/*****************************************************
	 * [플랫폼 서비스] > [내 손안의 시험인증] > [나의 시험인증 정보조회 : 인증] 제품 이미지
	 * @param response
	 * @param myCertSelectReq
	 * @return 이미지 byte배열
	*****************************************************/
	@ResponseBody
	@GetMapping(value = {"/platformSvc/myData/myInfoCert/displayImage"})
	public byte[] displayImage(HttpServletResponse response,  @ModelAttribute MyDataSelectReq myCertSelectReq)   {

		String instEngNmLower 	= myCertSelectReq.getInstEngNmLower();	//기관명(영문)_소문자
		String certNo 			= myCertSelectReq.getCertNo();			//인증번호
		String imgName 			= myCertSelectReq.getImgName();			//imgName(파일명)

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

	/*****************************************************
	 * [플랫폼 서비스] > [내 손안의 시험인증] > [나의 시험인증 정보조회 : 인증] QR이미지파일 정보(없으면 저장 후 파일정보)
	 * @param MyDataSelectReq
	 * @return ResponseEntity<ResponseDto>
	*****************************************************/
	@GetMapping(value = {"/platformSvc/myData/myInfoCert/getMyCertQrImgFileData"})
	public ResponseEntity<ResponseDto> getMyCertQrImgFileData( @ModelAttribute MyDataSelectReq myCertSelectReq) throws Exception  {

		// [[0]]. 반환할 정보들
		String msg 				= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		FileDto fileDto 		= new FileDto();

		try {

			// [[1]]. QR 파일 정보 조회 (없으면 저장 후 파일정보)
			fileDto = myDataService.getMyCertQrImgFileData(myCertSelectReq);

			if(fileDto != null) {
				resultCode = HttpStatus.OK.value();
				msg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
			} else {
				resultCode = HttpStatus.BAD_REQUEST.value();
				msg = messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale());  //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}

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
				.data(fileDto)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);

	}

	/*****************************************************
	 * [플랫폼 서비스] > [내 손안의 시험인증] > [나의 시험인증 정보조회 : 인증] 추가정보 저장하기
	 * @param myDataSaveReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/platformSvc/myData/myInfoCert/saveCertAditInfo"})
	public ResponseEntity<ResponseDto> saveCertAditInfo(MyDataSaveReq myDataSaveReq) throws Exception {

		// [[0]]. 반환할 정보들 & 변수 지정
		String resultMsg 		= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		MyDataSaveRes myDataSaveRes = new MyDataSaveRes();

		try {

			/* 등록,수정 */
			myDataSaveRes = myDataService.mergeCertAditInfoMng(myDataSaveReq);

			resultCode = HttpStatus.OK.value();
			resultMsg = messageSource.getMessage("result-message.messages.common.message.save", null, SessionUtil.getLocale()); 		//정상적으로 저장 하였습니다.

		} catch (CustomMessageException e) {

			//값 셋팅
			resultCode = HttpStatus.BAD_REQUEST.value();
			resultMsg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);

		} catch(Exception e) {

			throw e;

		}

		return new ResponseEntity<>(
			ResponseDto.builder()
			.data(myDataSaveRes)
			.resultMessage(resultMsg)
			.resultCode(resultCode)
			.build()
			, HttpStatus.OK);

	}

	/*****************************************************
	 * [플랫폼 서비스] > [내 손안의 시험인증] > [나의 시험인증 정보조회 : 성적서] 리스트_페이지
	 * @param request
	 * @param response
	 * @param model
	 * @param myDataSelectReq
	 * @return 페이지
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/platformSvc/myData/myInfoCert/myRprtList"})
	public String myRprtList(HttpServletResponse response, Model model, MyDataSelectReq myDataSelectReq) throws Exception {

		try {

			//=========== 검색조건 유지하기위해 =====================================================================
			model.addAttribute("schDatamyRprt"	, myDataSelectReq);

			//=========== select박스 값 셋팅 =====================================================================
			/* [##시험기관##] 리스트 */
			List<MyDataSelectRes> coInstList = myDataService.getCoInstList();
			model.addAttribute("coInstList"		, coInstList);
			/* [##통합종합판정결과##] 리스트 */
			List<MyDataSelectRes> jgmtRsltList = myDataService.getPtComnDtlCdList("ATC005");
			model.addAttribute("jgmtRsltList"	, jgmtRsltList);
			/* [##제품분류_대##] 리스트 */
			List<MyDataSelectRes> stdLgclfList = myDataService.getPtCoIndstStdLgclfCdList();
			model.addAttribute("stdLgclfList"	, stdLgclfList);

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

		return "/platformSvc/myData/myInfoCert/myRprtList";

	}

	/*****************************************************
	 * [플랫폼 서비스] > [내 손안의 시험인증] > [나의 시험인증 정보조회 : 성적서] 리스트_데이터
	 * @param MyDataSelectReq
	 * @return ResponseEntity<ResponseDto>
	*****************************************************/
	@GetMapping(value = {"/platformSvc/myData/myInfoCert/getMyRprtList"})
	public ResponseEntity<ResponseDto> getMyRprtList( @ModelAttribute MyDataSelectReq myRprtSelectReq) throws Exception  {

		// [[0]]. 반환할 정보들
		String msg 						= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 				= HttpStatus.BAD_REQUEST.value();
		int myRprtCnt					= 0;
		List<MyDataSelectRes> myRprtList = null;
		CommonRes commonRes 			= new CommonRes();

		try {

			// [[1]]. Paging 변수 설정
			Common common = CommonUtil.setPageParams(myRprtSelectReq.getCurrPage(), myRprtSelectReq.getRowCount());
			myRprtSelectReq.setStartRow(common.getStartRow());
			myRprtSelectReq.setEndRow(common.getEndRow());

			// [[2]]. 데이터 조회
			/* 2-1.Cnt 정보*/
			myRprtCnt = myDataService.getMyRprtCnt(myRprtSelectReq);

			if(myRprtCnt != 0) {

				/* 2-2. List 정보*/
				myRprtList = myDataService.getMyRprtList(myRprtSelectReq);

				resultCode = HttpStatus.OK.value();
				msg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.

			} else {

				resultCode = HttpStatus.OK.value();
				msg = messageSource.getMessage("result-message.messages.common.message.no.data", null, SessionUtil.getLocale()); // 데이터가 없습니다.

			}

			// [[3]]. 데이터 셋팅
			commonRes.setTotCnt(myRprtCnt);
			commonRes.setList(myRprtList);

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
	 * [플랫폼 서비스] > [내 손안의 시험인증] > [나의 시험인증 정보조회 : 성적서] 엑셀다운로드(리스트)
	 * @param request
	 * @param  response
	 * @param myRprtSelectReq
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/platformSvc/myData/myInfoCert/downExcelMyRprtList"})
	public void downExcelMyRprtList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute MyDataSelectReq myRprtSelectReq) throws Exception {

		ExcelUtil excelUtil = new ExcelUtil();

		try {

			// [[0]]. List 정보
			List<Map<String, Object>> list = myDataService.getMyRprtListMap(myRprtSelectReq);

			// [[1]]. 브라우저별 한글 깨짐 방지
			String excelName = CommonUtil.getDisposition(request, "성적서목록");

			// [[2]]. 엑셀 다운로드
			excelUtil.download2(request, response, list, excelName, "my_rprt_list_template.xlsx");

		} catch (CustomMessageException e) {

			//값 셋팅
			String msg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

			//ALERT창띄우면서 뒤로 보내기
			CommonUtil.alertMsgBack(response, msg);

		} catch(Exception e) {

			throw e;

		}

	}

	/*****************************************************
	 * [플랫폼 서비스] > [내 손안의 시험인증] > [나의 시험인증 정보조회 : 성적서] 상세_페이지 + 데이터
	 * @param response
	 * @param model
	 * @param myRprtSelectReq
	 * @return 페이지
	 * @throws Exception
	*****************************************************/
	@GetMapping(value = {"/platformSvc/myData/myInfoCert/myRprtDetail"})
	public String myRprtDetail(HttpServletResponse response, Model model, @ModelAttribute MyDataSelectReq myRprtSelectReq) throws Exception {

		/* 뒤로가기 방지 */
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.

		MyDataSelectRes myRprtDetail = null;

		try {

			myRprtDetail = myDataService.getMyRprtDetail(myRprtSelectReq);

		} catch (CustomMessageException e) {

			//값 셋팅
			String msg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

			//ALERT창띄우면서 List로 보내기
			CommonUtil.moveUrlAlertMsg(response, msg ,"/platformSvc/myData/myInfoCert/myRprtList");

		} catch(Exception e) {

			throw e;

		}

		// 데이터 셋팅
		model.addAttribute("schDatamyRprt"	, myRprtSelectReq); //검색조건 유지하기위해
		model.addAttribute("myRprtDetail"	, myRprtDetail);

		return "/platformSvc/myData/myInfoCert/myRprtDetail";

	}

	/*****************************************************
	 * [플랫폼 서비스] > [내 손안의 시험인증] > [나의 시험인증 정보조회 : 성적서] [##품목 정보##] 상세 + [##시료 정보##] 리스트
	 * @param MyDataSelectReq
	 * @return ResponseEntity<ResponseDto>
	*****************************************************/
	@GetMapping(value = {"/platformSvc/myData/myInfoCert/getMyRprtPdctData"})
	public ResponseEntity<ResponseDto> getMyRprtPdctData( @ModelAttribute MyDataSelectReq myRprtSelectReq ) throws Exception  {

		// [[0]]. 반환할 정보들
		String msg 						= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 				= HttpStatus.BAD_REQUEST.value();
		MyDataSelectRes rprtPdctgDetail = null;

		try {

			// [[1]]. 데이터 조회
			rprtPdctgDetail = myDataService.getMyRprtPdctData(myRprtSelectReq);

			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.

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
				.data(rprtPdctgDetail)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);

	}

	/*****************************************************
	 * [플랫폼 서비스] > [내 손안의 시험인증] > [나의 시험인증 정보조회 ]  [##제품분류_중##] 리스트_데이터
	 * @param MyDataSelectReq
	 * @return ResponseEntity<ResponseDto>
	*****************************************************/
	@GetMapping(value = {"/platformSvc/myData/myInfoCert/getStdMlclfCdList"})
	public ResponseEntity<ResponseDto> getStdMlclfCdList( @RequestParam String upStdLgclfCd) throws Exception  {

		// [[0]]. 반환할 정보들
		String msg 						= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 				= HttpStatus.BAD_REQUEST.value();
		List<MyDataSelectRes> stdMlclfList = null;

		try {

			// [[1]]. 데이터 조회
			stdMlclfList = myDataService.getPtCoIndstStdMlclfCdList(upStdLgclfCd);

			resultCode = HttpStatus.OK.value();
			msg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.

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
				.data(stdMlclfList)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);

	}

	/*****************************************************
	 * [플랫폼 서비스] > [내 손안의 시험인증] > [서비스 신청 POP] 저장하기
	 * @param myDataSaveReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/platformSvc/myData/saveSvcAppl"})
	public ResponseEntity<ResponseDto> saveSvcAppl(MyDataSaveReq myDataSaveReq) throws Exception {

		// [[0]]. 반환할 정보들 & 변수 지정
		String resultMsg 		= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		MyDataSaveRes myDataSaveRes = new MyDataSaveRes();

		try {

			/* 등록 */
			myDataSaveRes = myDataService.insertSvcAppl(myDataSaveReq);

			resultCode = HttpStatus.OK.value();
			resultMsg = messageSource.getMessage("result-message.messages.common.message.save", null, SessionUtil.getLocale()); 		//정상적으로 저장 하였습니다.

		} catch (CustomMessageException e) {

			//값 셋팅
			resultCode = HttpStatus.BAD_REQUEST.value();
			resultMsg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);

		} catch(Exception e) {

			throw e;

		}

		return new ResponseEntity<>(
			ResponseDto.builder()
			.data(myDataSaveRes)
			.resultMessage(resultMsg)
			.resultCode(resultCode)
			.build()
			, HttpStatus.OK);

	}

	/*****************************************************
	 * [플랫폼 서비스] > [내 손안의 시험인증] > [제출처 제공 신청 POP] 저장하기
	 * @param myDataSaveReq
	 * @return ResponseEntity<ResponseDto>
	 * @throws Exception
	*****************************************************/
	@PostMapping(value = {"/platformSvc/myData/saveSbmsn"})
	public ResponseEntity<ResponseDto> saveSbmsn(MyDataSaveReq myDataSaveReq) throws Exception {

		// [[0]]. 반환할 정보들 & 변수 지정
		String resultMsg 		= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		MyDataSaveRes myDataSaveRes = new MyDataSaveRes();

		try {

			/* 등록 */
			myDataSaveRes = myDataService.insertSbmsn(myDataSaveReq);

			resultCode = HttpStatus.OK.value();
			resultMsg = messageSource.getMessage("result-message.messages.common.message.save", null, SessionUtil.getLocale()); 		//정상적으로 저장 하였습니다.

		} catch (CustomMessageException e) {

			//값 셋팅
			resultCode = HttpStatus.BAD_REQUEST.value();
			resultMsg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);

		} catch(Exception e) {

			throw e;

		}

		return new ResponseEntity<>(
			ResponseDto.builder()
			.data(myDataSaveRes)
			.resultMessage(resultMsg)
			.resultCode(resultCode)
			.build()
			, HttpStatus.OK);

	}

	/*****************************************************
	 * [플랫폼 서비스] > [내 손안의 시험인증] > [서비스 해지 POP] 저장하기(서비스 해지하기)
	 * @param myDataSaveReq
	 * @throws Exception
	 *****************************************************/
	@PostMapping("/platformSvc/myData/temrmiSvcAppl")
	public ResponseEntity<ResponseDto> temrmiSvcAppl(MyDataSaveReq myDataSaveReq) throws Exception{

		// [[0]]. 반환할 정보들 & 변수 지정
		String resultMsg 		= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		MyDataSaveRes myDataSaveRes = new MyDataSaveRes();

		try {

			/* N으로 insert */
			myDataSaveRes = myDataService.insertTemrmiSvcAppl(myDataSaveReq);

			resultCode = HttpStatus.OK.value();
			resultMsg = messageSource.getMessage("result-message.messages.common.message.save", null, SessionUtil.getLocale()); 		//정상적으로 저장 하였습니다.

		} catch (CustomMessageException e) {

			//값 셋팅
			resultCode = HttpStatus.BAD_REQUEST.value();
			resultMsg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);

		} catch(Exception e) {

			throw e;

		}

		return new ResponseEntity<>(
			ResponseDto.builder()
			.data(myDataSaveRes)
			.resultMessage(resultMsg)
			.resultCode(resultCode)
			.build()
			, HttpStatus.OK);

	}

	/*****************************************************
	 * [플랫폼 서비스] > [내 손안의 시험인증] > [제출처 제공 서비스 해지 POP] 저장하기(제츨처 제공 해지하기)
	 * @param myDataSaveReq
	 * @throws Exception
	 *****************************************************/
	@PostMapping("/platformSvc/myData/temrmiSbmsn")
	public ResponseEntity<ResponseDto> temrmiSbmsn(MyDataSaveReq myDataSaveReq) throws Exception{

		// [[0]]. 반환할 정보들 & 변수 지정
		String resultMsg 		= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); //에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 		= HttpStatus.BAD_REQUEST.value();
		MyDataSaveRes myDataSaveRes = new MyDataSaveRes();

		try {

			/* N으로 insert */
			myDataSaveRes = myDataService.insertTemrmiSbmsn(myDataSaveReq);

			resultCode = HttpStatus.OK.value();
			resultMsg = messageSource.getMessage("result-message.messages.common.message.save", null, SessionUtil.getLocale()); 		//정상적으로 저장 하였습니다.

		} catch (CustomMessageException e) {

			//값 셋팅
			resultCode = HttpStatus.BAD_REQUEST.value();
			resultMsg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + resultMsg);

		} catch(Exception e) {

			throw e;

		}

		return new ResponseEntity<>(
			ResponseDto.builder()
			.data(myDataSaveRes)
			.resultMessage(resultMsg)
			.resultCode(resultCode)
			.build()
			, HttpStatus.OK);

	}

}

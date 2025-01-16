package com.katri.web.particiLounge.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import com.katri.common.Const;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.CommonUtil;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.service.CommService;
import com.katri.web.particiLounge.model.DataGatherStatusSelectReq;
import com.katri.web.particiLounge.model.DataGatherStatusSelectRes;
import com.katri.web.particiLounge.model.ParticiLoungeSelectReq;
import com.katri.web.particiLounge.model.ParticiLoungeSelectRes;
import com.katri.web.particiLounge.service.DataGatherStatusService;
import com.katri.web.particiLounge.service.ParticiLoungeService;
import com.nhncorp.lucy.security.xss.XssPreventer;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequiredArgsConstructor
@Api(tags = {"[참여기관 라운지] Controller"})
@Slf4j
public class ParticiLoungeController {

	/** 공통 Service */
	private final CommService commService;

	private final MessageSource messageSource;

	/** [참여기관 라운지] Service */
	private final ParticiLoungeService particiLoungeService;

	/** 데이터 수집 현황 Service */
	private final DataGatherStatusService dataGatherStatusService;

	/*****************************************************
	 * [참여기관 라운지] > [데이터 시각화 환경] _페이지 + 데이터
	 * @param request
	 * @param response
	 * @param model
	 * @param particiLoungeSelectReq
	 * @return 페이지
	 * @throws Exception
	*****************************************************/
	@RequestMapping(value = {"/particiLounge/dataVisualEnvi", "/particiLounge/previewDataVisualEnvi"}, method = {RequestMethod.GET, RequestMethod.POST} )
	public String dataVisualEnvi(HttpServletRequest request, HttpServletResponse response, Model model, ParticiLoungeSelectReq particiLoungeSelectReq) throws Exception {

		// [[0]]. 반환할 정보들 & 변수 지정
		ParticiLoungeSelectRes particiLoungeData		= new ParticiLoungeSelectRes(); //페이지콘텐츠 정보

		try {

			// [[1]]. [참여기관 라운지] 각 페이지에 맞는 데이터 셋팅
			if("/particiLounge/previewDataVisualEnvi".equals(request.getRequestURI()) ) { //1-t1. BO에서 [미리보기] 할 경우

				// 1-t1-1.[참여기관 라운지] 데이터 + 내용 UNESCAPE
				String previewMenuCptnCn = particiLoungeSelectReq.getPreviewMenuCptnCn();
				particiLoungeData.setMenuCptnCnUnescape(XssPreventer.unescape(previewMenuCptnCn));

			} else { //1-t2. 메뉴에서 들어갈 경우

				// 1-t2-1.[참여기관 라운지] 각 페이지에 맞는 데이터 조회 + 내용 UNESCAPE
				particiLoungeSelectReq.setSearchMenuCptnCd(Const.Code.MenuCompositionCode.PARTICI_LOUNGE_DATA_VISUAL); //참여기관 라운지 > 데이터 시각화 환경
				particiLoungeData = particiLoungeService.getParticiLoungeData(particiLoungeSelectReq);
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
		model.addAttribute("particiLoungeData"	, particiLoungeData);

		return "/particiLounge/dataVisualEnvi";

	}

	/*****************************************************
	 * [참여기관 라운지] > [데이터 분석 환경] _페이지 + 데이터
	 * @param request
	 * @param response
	 * @param model
	 * @param particiLoungeSelectReq
	 * @return 페이지
	 * @throws Exception
	*****************************************************/
	@RequestMapping(value = {"/particiLounge/dataAnalyEnvi", "/particiLounge/previewDataAnalyEnvi"}, method = {RequestMethod.GET, RequestMethod.POST} )
	public String dataAnalyEnvi(HttpServletRequest request, HttpServletResponse response, Model model, ParticiLoungeSelectReq particiLoungeSelectReq) throws Exception {

		// [[0]]. 반환할 정보들 & 변수 지정
		ParticiLoungeSelectRes particiLoungeData		= new ParticiLoungeSelectRes(); //페이지콘텐츠 정보

		try {

			// [[1]]. [참여기관 라운지] 각 페이지에 맞는 데이터 셋팅
			if("/particiLounge/previewDataAnalyEnvi".equals(request.getRequestURI()) ) { //1-t1. BO에서 [미리보기] 할 경우

				// 1-t1-1.[참여기관 라운지] 데이터 + 내용 UNESCAPE
				String previewMenuCptnCn = particiLoungeSelectReq.getPreviewMenuCptnCn();
				particiLoungeData.setMenuCptnCnUnescape(XssPreventer.unescape(previewMenuCptnCn));

			} else { //1-t2. 메뉴에서 들어갈 경우

				// 1-t2-1.[참여기관 라운지] 각 페이지에 맞는 데이터 조회 + 내용 UNESCAPE
				particiLoungeSelectReq.setSearchMenuCptnCd(Const.Code.MenuCompositionCode.PARTICI_LOUNGE_DATA_ANALY); //참여기관 라운지 > 데이터 분석 환경
				particiLoungeData = particiLoungeService.getParticiLoungeData(particiLoungeSelectReq);
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
		model.addAttribute("particiLoungeData"	, particiLoungeData);

		return "/particiLounge/dataAnalyEnvi";

	}

	/*****************************************************
	 * [참여기관 라운지] > [데이터 수집 현황]
	 * @param model
	 * @return
	*****************************************************/
	@GetMapping("/particiLounge/dataGatherStatus")
	public String dataGatherStatus(Model model) {

		// 최근 수집 일자
		DataGatherStatusSelectRes dataGatherStatusSelectRes = dataGatherStatusService.getLastGatherDt();

		// 기준일
		// DB 값이 없다면 오늘 날짜를 찍어준다
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		DateFormat df = new SimpleDateFormat("yyyyMMdd");

		String lastGatherDt = df.format(cal.getTime());
		if (dataGatherStatusSelectRes != null) lastGatherDt = dataGatherStatusSelectRes.getLastGatherDt();

		model.addAttribute("lastGatherDt", lastGatherDt);

		return "/particiLounge/dataGatherStatus";
	}

	/*****************************************************
	 * 기관코드 조회
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping("/particiLounge/getInstId")
	public ResponseEntity<ResponseDto> getInstCd() throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 					= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 					= HttpStatus.BAD_REQUEST.value(); // 상태코드

		// [[1]]. 데이터 가져오기
		// 기관코드 가져오기
		List<DataGatherStatusSelectRes> instIdList = dataGatherStatusService.getInstId();

		resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
		resultCode = HttpStatus.OK.value();

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(instIdList)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * 기관별 데이터 수집건수
	 * @param dataGatherStatusSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping("/particiLounge/getAgencySpecificData")
	public ResponseEntity<ResponseDto> getDataGatherStatus(@ModelAttribute DataGatherStatusSelectReq dataGatherStatusSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 					= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 					= HttpStatus.BAD_REQUEST.value(); // 상태코드

		// [[1]]. 데이터 가져오기
		// 기관별 데이터 수집건수
		List<DataGatherStatusSelectRes> agencySpecificData = dataGatherStatusService.getDataGatherStatus(dataGatherStatusSelectReq);

		resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
		resultCode = HttpStatus.OK.value();

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(agencySpecificData)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * 수집건수 추이
	 * @param dataGatherStatusSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping("/particiLounge/getChgNumberOfCollection")
	public ResponseEntity<ResponseDto> getChgNumberOfCollection(@ModelAttribute DataGatherStatusSelectReq dataGatherStatusSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 					= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 					= HttpStatus.BAD_REQUEST.value(); // 상태코드

		// [[1]]. 데이터 가져오기
		// 수집건수 추이
		List<DataGatherStatusSelectRes> chgNumberOfCollection = dataGatherStatusService.getNumberOfCollection(dataGatherStatusSelectReq);

		resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
		resultCode = HttpStatus.OK.value();

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(chgNumberOfCollection)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * 접수데이터 수집결과 추이
	 * @param dataGatherStatusSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping("/particiLounge/getRcptCollectionResult")
	public ResponseEntity<ResponseDto> getRcptCollectionResult(@ModelAttribute DataGatherStatusSelectReq dataGatherStatusSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 					= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 					= HttpStatus.BAD_REQUEST.value(); // 상태코드

		// [[1]]. 데이터 가져오기
		// 접수데이터 수집결과 추이
		List<DataGatherStatusSelectRes> rcptCollectionResult = dataGatherStatusService.getRcptCollectionResult(dataGatherStatusSelectReq);

		resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
		resultCode = HttpStatus.OK.value();

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(rcptCollectionResult)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * 시험데이터 수집결과 추이
	 * @param dataGatherStatusSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping("/particiLounge/getTestItemCollectionResult")
	public ResponseEntity<ResponseDto> getTestItemCollectionResult(@ModelAttribute DataGatherStatusSelectReq dataGatherStatusSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 					= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 					= HttpStatus.BAD_REQUEST.value(); // 상태코드

		// [[1]]. 데이터 가져오기
		// 접수데이터 수집결과 추이
		List<DataGatherStatusSelectRes> testItemCollectionResult = dataGatherStatusService.getTestItemCollectionResult(dataGatherStatusSelectReq);

		resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
		resultCode = HttpStatus.OK.value();

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(testItemCollectionResult)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * 성적데이터 수집결과 추이
	 * @param dataGatherStatusSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping("/particiLounge/getRprtCollectionResult")
	public ResponseEntity<ResponseDto> getRprtCollectionResult(@ModelAttribute DataGatherStatusSelectReq dataGatherStatusSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 					= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 					= HttpStatus.BAD_REQUEST.value(); // 상태코드

		// [[1]]. 데이터 가져오기
		// 접수데이터 수집결과 추이
		List<DataGatherStatusSelectRes> rprtCollectionResult = dataGatherStatusService.getRprtCollectionResult(dataGatherStatusSelectReq);

		resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
		resultCode = HttpStatus.OK.value();

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(rprtCollectionResult)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * 인증데이터 수집결과 추이
	 * @param dataGatherStatusSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping("/particiLounge/getCertCollectionResult")
	public ResponseEntity<ResponseDto> getCertCollectionResult(@ModelAttribute DataGatherStatusSelectReq dataGatherStatusSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 					= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 					= HttpStatus.BAD_REQUEST.value(); // 상태코드

		// [[1]]. 데이터 가져오기
		// 접수데이터 수집결과 추이
		List<DataGatherStatusSelectRes> certCollectionResult = dataGatherStatusService.getCertCollectionResult(dataGatherStatusSelectReq);

		resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
		resultCode = HttpStatus.OK.value();

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(certCollectionResult)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * 인증이미지 수집결과 추이
	 * @param dataGatherStatusSelectReq
	 * @return
	 * @throws Exception
	*****************************************************/
	@GetMapping("/particiLounge/getCertImgCollectionResult")
	public ResponseEntity<ResponseDto> getCertImgCollectionResult(@ModelAttribute DataGatherStatusSelectReq dataGatherStatusSelectReq) throws Exception {
		// [[0]]. 반환할 정보들
		String resultMsg 					= messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		Integer resultCode 					= HttpStatus.BAD_REQUEST.value(); // 상태코드

		// [[1]]. 데이터 가져오기
		// 접수데이터 수집결과 추이
		List<DataGatherStatusSelectRes> certImgCollectionResult = dataGatherStatusService.getCertImgCollectionResult(dataGatherStatusSelectReq);

		resultMsg = messageSource.getMessage("result-message.messages.common.message.search", null, SessionUtil.getLocale()); // 정상적으로 조회 하였습니다.
		resultCode = HttpStatus.OK.value();

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(certImgCollectionResult)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

}

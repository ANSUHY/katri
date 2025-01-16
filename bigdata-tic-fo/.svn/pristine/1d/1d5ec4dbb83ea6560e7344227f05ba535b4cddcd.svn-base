package com.katri.web.dbr.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.katri.web.dbr.model.DbrReq;
import com.katri.web.dbr.model.DbrRes;
import com.katri.common.CommonCodeKey;
import com.katri.common.model.ResponseDto;
import com.katri.web.comm.model.CommReq;
import com.katri.web.comm.model.CommRes;
import com.katri.web.comm.service.CommService;
import com.katri.web.dbr.service.DbrService;
import com.katri.web.mbr.model.MbrReq;
import com.katri.web.mbr.model.MbrRes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@Api(tags = {"대시보드 Controller"})
public class DbrController {

	/** 대시보드 Service */
	private final DbrService service;
	/** 공통코드 Service */
	private final CommService commService;

	@GetMapping(value = {"/dashboard"})
	public String dashboard(HttpServletRequest request, Model model) {

		// 인증기관 코드
		CommReq commReq = new CommReq();
		commReq.setUpCd(CommonCodeKey.instIdKey);
		List<CommRes> certInstList = this.commService.selectCode(commReq); // 토탈 카운트
		model.addAttribute("certInstList",certInstList);

		// 기준일
		// DB 값이 없다면 오늘 날짜를 찍어준다
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");

		DbrRes rfMap = this.service.selectReferenceDate();
		String referenceDt = df.format(cal.getTime());
		if (rfMap != null) referenceDt = rfMap.getReferenceDt();
		model.addAttribute("referenceDt", referenceDt);

		return "/dbr/dbrPage";

	}

	// 대시보드 API 호출
	@PostMapping(value = {"/dashboard/getData"})
	@ApiOperation(value = "로그인 정보 조회", response = DbrRes.class, responseContainer = "json")
	public ResponseEntity<ResponseDto> loginData(HttpServletRequest request, @ModelAttribute DbrReq req, BindingResult result) {
		String resultMsg = "";
		Integer resultCode = HttpStatus.BAD_REQUEST.value();

		HashMap<String, List<DbrRes>> obj = new HashMap<String, List<DbrRes>>();

		String type = req.getCallType();

		if ("ALL".equals(type)) {
			// 기준일 카운트
			req.setUpCd(CommonCodeKey.instIdKey);
			List<DbrRes> refCnt = this.service.selectReferenceCount(req);
			obj.put("refCntList", refCnt);

			// 수집결과 카운트
			List<DbrRes> resCount = this.service.selectResultCount(req);
			obj.put("resCntList", resCount);
		}

		// 수집건수 카운트
		List<DbrRes> caseCount = this.service.selectCaseCount(req);
		obj.put("caseCntList", caseCount);


		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(obj)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}


}

package com.katri.web.stats.controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.katri.common.CommonCodeKey;
import com.katri.common.model.ResponseDto;
import com.katri.common.util.StringUtil;
import com.katri.web.comm.model.CommReq;
import com.katri.web.comm.model.CommRes;
import com.katri.web.comm.service.CommService;
import com.katri.web.stats.model.StatsReq;
import com.katri.web.stats.model.StatsRes;
import com.katri.web.stats.service.StatsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@Api(tags = {"통계 Controller"})
public class StatsController {

	/** 통계 Service */
	private final StatsService statstService;

	/** 공통코드 Service */
	private final CommService commService;

	/*****************************************************
	 * 통계 페이지
	 * @param
	 * @return certInstList 기관 코드 리스트
	 *****************************************************/
	@GetMapping(value = {"/stats/statsLst"})
	public String certLst(HttpServletRequest request, Model model) {

		CommReq commReq = new CommReq();
		// 인증기관 코드
		commReq.setUpCd(CommonCodeKey.instIdKey);
		List<CommRes> certInstList = this.commService.selectCode(commReq); // 토탈 카운트
		model.addAttribute("certInstList",certInstList);

		return "/stats/statsLst";
	}

	/*****************************************************
	 * 통계 페이지 - 인증상태 데이터 조회
	 * @param
	 * @return 통계 데이터
	 *****************************************************/
	@PostMapping(value = {"/stats/selectStatsCount"})
	@ApiOperation(value = "통계 페이지 - 인증상태 데이터 조회", response = StatsRes.class, responseContainer = "List")
	public ResponseEntity<ResponseDto> selectStatsCount(HttpServletRequest request, @ModelAttribute StatsReq req, BindingResult result) {

		String resultMsg = "리스트 조회에 실패 했습니다.";
		Integer resultCode = HttpStatus.BAD_REQUEST.value();


		// 날짜 조건 확인
		String startDate = StringUtil.nvl(req.getStartDate());
		String endDate = StringUtil.nvl(req.getEndDate());

		if (!"".equals(startDate) && "".equals(endDate)) {
			Calendar cal = Calendar.getInstance();
	        cal.setTime(new Date());
	        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
	        cal.add(Calendar.YEAR, 100);
			req.setEndDate(df.format(cal.getTime()));
		}

		List<StatsRes> list = this.statstService.selectStatsCount(req);

		if (list.size() > 0) {
			resultCode = HttpStatus.OK.value();
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(list)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * 통계 페이지 - 인증상태 데이터 조회
	 * @param
	 * @return 통계 데이터
	 *****************************************************/
	@PostMapping(value = {"/stats/selectTypeCount"})
	@ApiOperation(value = "통계 페이지 - 인증상태 데이터 조회", response = StatsRes.class, responseContainer = "List")
	public ResponseEntity<ResponseDto> selectTypeCount(HttpServletRequest request, @ModelAttribute StatsReq req, BindingResult result) {

		String resultMsg = "리스트 조회에 실패 했습니다.";
		Integer resultCode = HttpStatus.BAD_REQUEST.value();


		// 날짜 조건 확인
		String startDate = StringUtil.nvl(req.getStartDate());
		String endDate = StringUtil.nvl(req.getEndDate());

		if (!"".equals(startDate) && "".equals(endDate)) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
			cal.add(Calendar.YEAR, 100);
			req.setEndDate(df.format(cal.getTime()));
		}

		// 페이징 처리 로직
		String pageNumText = StringUtil.nvl(request.getParameter("pageNum"));
		int pageNum = "".equals(pageNumText) ? 1 : Integer.parseInt(pageNumText);

		int startCount = ( pageNum * 10 ) - 10; // 시작 카운트

		req.setStartCount(startCount); // 시작 카운트
		req.setLimitCount(10); // 시작 카운트


		int allCount = this.statstService.selectTypeTotalCount(req);
		List<StatsRes> list = this.statstService.selectTypeCount(req);


		// 전체 페이지 확인
		int allPage = allCount / 10;
		if (allCount % 10 > 0) allPage++;

		// 페이지 구릅 만들기
		int allPageGroup = allPage / 10;
		if (allPage % 10 > 0) allPageGroup++;

		int nowPageGroup = pageNum / 10;
		if (pageNum % 10 > 0) nowPageGroup++;

		// 시작 페이지 카운트 계산

		int sPage = nowPageGroup * 10 - 9;
		// 끝 페이지카운트
		int ePage = nowPageGroup * 10;
		if (ePage > allPage) ePage = allPage;

		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("sPage", sPage);
		pageMap.put("ePage", ePage);
		pageMap.put("allPage", allPage);
		pageMap.put("allCount", allCount);
		pageMap.put("allPageGroup", allPageGroup);
		pageMap.put("nowPageGroup", nowPageGroup);
		pageMap.put("pageNum", pageNum);

		if (list != null) {
			resultCode = HttpStatus.OK.value();
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("pageMap", pageMap);
		returnMap.put("list", list);

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(returnMap)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}


}

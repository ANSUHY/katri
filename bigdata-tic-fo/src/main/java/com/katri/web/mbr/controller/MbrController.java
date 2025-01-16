package com.katri.web.mbr.controller;


import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.katri.common.util.CommonUtil;
import com.katri.common.util.EncryptUtil;
import com.katri.common.util.ExcelUtil;
import com.katri.common.util.SessionUtil;
import com.katri.common.util.StringUtil;
import com.katri.web.comm.model.CommReq;
import com.katri.web.comm.model.CommRes;
import com.katri.web.comm.service.CommService;
import com.katri.web.mbr.model.MbrReq;
import com.katri.web.mbr.model.MbrRes;
import com.katri.web.mbr.service.MbrService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Api(tags = {"회원 Controller"})
@Slf4j
public class MbrController {

	/** 회원 Service */
	private final MbrService mbrService;
	/** 공통코드 Service */
	private final CommService commService;


	/*****************************************************
	 * 회원등록 페이지
	 * @param mbrReq 회원
	 * @return String
	 *****************************************************/
	@GetMapping(value = {"/mbr/mbrReg"})
	public String mbrReg(HttpServletRequest request, MbrReq mbrReq, Model model) {

		// 기관 리스트
		CommReq commReq = new CommReq();
		commReq.setUpCd(CommonCodeKey.instIdKey);
		List<CommRes> certInstList = this.commService.selectCode(commReq); // 토탈 카운트
		model.addAttribute("certInstList",certInstList);

		return "mbr/mbrReg";
	}

	/*****************************************************
	 * 회원등록
	 * @param mbrReq 로그인
	 * @return ResponseEntity<ResponseDto> 등록 성공 카운트
	 *****************************************************/
	@PostMapping(value = {"/mbr/mbrSave"})
	@ApiOperation(value = "회원가입", response = MbrRes.class, responseContainer = "List")
	public ResponseEntity<ResponseDto> mbrSave(HttpServletRequest request, @ModelAttribute MbrReq mbrReq, BindingResult result) {

		// [[0]]. 반환할 정보들
		String msg = "";
		Integer resultCode = HttpStatus.BAD_REQUEST.value();
		MbrRes mbrRes = new MbrRes();

		// [[1]]. 저장 확인 및 셋팅
		/** 1-1. 세션 여부 확인*/
		String userId = SessionUtil.getUserId();
		String mngrYn = SessionUtil.getMngrYn();

		int errorCount = 0;

		/* 1-2. 회원 가입 타입 지정*/
		if (!"".equals(userId) && "Y".equals(mngrYn)) { //2-1. 관리자x
			mbrReq.setRegId(userId);
			mbrReq.setSttCd("A");
		} else { //2-2. 관리자
			mbrReq.setRegId("newUser");
			mbrReq.setSttCd("W");
		}

		/* 1-3. 패스워드 유효성 검사 */
		String pw = mbrReq.getMbrPwd();
		Boolean pwFlag = !Pattern.matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\\$%^&\\*_~])[a-zA-Z0-9!@#\\$%^&\\*_~]{9,12}$", pw);
		if (pwFlag) errorCount++;


		MbrRes res = new MbrRes();
		/* 1-4. 사용자 정보 중복 체크 */
		if (errorCount == 0) {
			res = mbrService.selectInfoChk(mbrReq);
			errorCount = res.getIdCount()+res.getEmailCount();
		}

		// [[2]]. 저장프로세스 시작
		if (errorCount == 0) {
			resultCode = HttpStatus.OK.value();

			/* 2-1-1. 비밀번호 암호화 */
			String eyptPwd = StringUtil.nvl(EncryptUtil.encryptSha256(pw, mbrReq.getMbrId()));
			mbrReq.setMbrPwd(eyptPwd);

			/* 2-1-2. 보안 처리 */
			String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s@.]";
			String mbrNm = mbrReq.getMbrNm();
			String deptNm = mbrReq.getDeptNm();
			String mbrEmlAddr = mbrReq.getMbrEmlAddr();

			mbrNm = mbrNm.replaceAll(match, ""); //StringUtil.removeXSS(mbrReq.getMbrNm());
			deptNm = mbrNm.replaceAll(match, ""); //StringUtil.removeXSS(mbrReq.getDeptNm());
			mbrEmlAddr = mbrEmlAddr.replaceAll(match, ""); //StringUtil.removeXSS(mbrReq.getMbrEmlAddr());
			mbrReq.setMbrNm(mbrNm);
			mbrReq.setDeptNm(deptNm);
			mbrReq.setMbrEmlAddr(mbrEmlAddr);

			/* 2-1-3. 멤버테이블에 저장 [tb_mbr] */
			int saveCount = mbrService.insertUser(mbrReq);
			mbrRes.setSaveCount(saveCount);

			if (saveCount > 0) {
				msg = "회원가입이 완료되었습니다.";
			} else {
				msg = "회원가입이 실패하었습니다.";
			}

		} else { /* 2-2. 반환값 셋팅(저장 못하도록 위에서 에러 지정해줬을때) */

			if (!pwFlag) { /* 2-2-1. 증복아웃 아웃(1-4. 사용자 정보 중복 체크)에서 잡은것 */
				msg = "중복된 회원 정보가 존재합니다.";
				msg+="(";
				if (res.getIdCount() > 0) msg+="아이디";
				if (res.getEmailCount() > 0) {
					if (res.getIdCount() > 0) {
						msg+=", 이메일";
					} else {
						msg+="이메일";
					}
				}
				msg+=")";

			} else { /* 2-2-2. 비밀번호 유효성검사 아웃(1-3. 패스워드 유효성 검사)에서 잡은것 */
				msg = "통과 할 수 없는 비밀번호 유효성입니다.";
			}

		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(mbrRes)
				.resultMessage(msg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * 로그인 페이지 조회
	 * @param loginReq 로그인
	 * @return  String
	 *****************************************************/
	@GetMapping(value = {"/mbr/login"})
	public String login(HttpServletRequest request, MbrReq mbrReq, Model model) {

		model.addAttribute(mbrReq);
		String view = "/mbr/login";

		return view;
	}

	@GetMapping(value = {"/mbr/logout"})
	public String logout(HttpServletRequest request) {
		/* System.out.println("logout"); */
		SessionUtil.removeLoginSession();
		return "/main/main";
	}

	/*****************************************************
	 * 로그인
	 * @param loginReq 로그인
	 * @return ResponseEntity<ResponseDto> 로그인 정보
	 *****************************************************/
	@PostMapping(value = {"/mbr/loginData"})
	@ApiOperation(value = "로그인 정보 조회", response = MbrRes.class, responseContainer = "List")
	public ResponseEntity<ResponseDto> loginData(HttpServletRequest request, @ModelAttribute MbrReq mbrReq, BindingResult result) {

		String resultMsg = "";
		Integer resultCode = HttpStatus.BAD_REQUEST.value();

		// 로그인 정보 조회
		MbrRes loginDetail = this.mbrService.selectLoginDetail(mbrReq);

		// 사용자 데이터 체크
		if(loginDetail != null) {

			String sttCd = StringUtil.nvl(loginDetail.getSttCd());
			String pwd = StringUtil.nvl(loginDetail.getMbrPwd());
			String eyptPwd = StringUtil.nvl(EncryptUtil.encryptSha256(mbrReq.getLoginPwd(), mbrReq.getLoginId()));

			// 미승인 사용자
			if("W".equals(sttCd)) {
				resultMsg = "성공적으로 가입되었지만, 현재 관리자를 통한 가입 승인 대기중입니다.";

			// 승인
			} else if ("A".equals(sttCd)) {

				// 비밀번호 맞음
				if (pwd.equals(eyptPwd)) {

					// ip주소 셋팅
					mbrReq.setIpAddress(CommonUtil.getClientIP(request));

					// 로그인 성공 로그 생성(로그인 실패 횟수 초기화 + IPAddress넣기)
					this.mbrService.updateLoginFailCntInit(mbrReq);

					// 세션 생성
					Map<String, Object> sessionMap = new HashMap<>();
					sessionMap.put("user_id", StringUtil.nvl(loginDetail.getMbrId()));
					sessionMap.put("user_nm", StringUtil.nvl(loginDetail.getMbrNm()));
					sessionMap.put("mngr_yn", StringUtil.nvl(loginDetail.getMngrYn()));

					SessionUtil.setLoginSession(sessionMap);
					resultCode = HttpStatus.OK.value();


				// 비밀번호 다름
				} else {
					resultMsg = "등록되지 않은 아이디이거나 아이디 또는 비밀번호가 회원정보와 일치하지 않습니다.";
				}

			// 기타
			} else {
				resultMsg = "휴면 또는 탈퇴 고객입니다. 관리자에게 문의하세요";
			}

		} else {
			resultMsg = "등록되지 않은 아이디이거나 아이디 또는 비밀번호가 회원정보와 일치하지 않습니다.";
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(null)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}


	/*****************************************************
	 * 회원관리 리스트
	 * @param mbrReq 회원
	 * @return String
	 *****************************************************/
	@GetMapping(value = {"/mbr/mbrLst"})
	public String mbrLst(HttpServletRequest request, HttpServletResponse response, Model model) {

		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.
		// DB로 전송되는 파라미터
		Map<String, Object> param = new HashMap<String, Object>();

		Enumeration<String> re = request.getParameterNames();
		while (re.hasMoreElements()){
			 String name = re.nextElement();    // 파라미터 값을 차례대로 가져와 name 변수에 저장한다.
			 String valueText = request.getParameter(name);

			//보안 추가
			 valueText = StringUtil.removeXSS(valueText);

			 if (!"".equals(valueText)) {
				try {
					 valueText = URLEncoder.encode(valueText, "UTF-8");
					 param.put(name, URLDecoder.decode(valueText, "UTF-8"));
					 model.addAttribute(name, URLDecoder.decode(valueText, "UTF-8"));
				} catch (Exception e) {
					e.printStackTrace();
					param.put(name, valueText); // 시작 카운트
					model.addAttribute(name, valueText);
				}
			 }
		}


		// 페이징 처리 로직
		String pageNumText = StringUtil.nvl(request.getParameter("pageNum"));
		int pageNum = "".equals(pageNumText) ? 1 : Integer.parseInt(pageNumText);

		int s_c = ( pageNum * 10 ) - 10; // 시작 카운트

		param.put("s_c", s_c ); // 시작 카운트
		param.put("e_c", 10); // 시작 카운트


		// 기본 리스트
		int allCount = this.mbrService.selectUserCount(param); // 토탈 카운트
		List<MbrRes> list = this.mbrService.selectUser(param); // 검색된 객체

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


		model.addAttribute("sPage", sPage);
		model.addAttribute("ePage", ePage);
		model.addAttribute("allPage", allPage);
		model.addAttribute("allCount", allCount);
		model.addAttribute("allPageGroup", allPageGroup);
		model.addAttribute("nowPageGroup", nowPageGroup);
		model.addAttribute("pageNum", pageNum);


		model.addAttribute("list", list);

		return "mbr/mbrLst";
	}

	/*****************************************************
	 * 회원상세 페이지
	 * @param mbrReq 회원
	 * @return String
	 *****************************************************/
	@GetMapping(value = {"/mbr/mbrDtl"})
	public String mbrDtl(HttpServletRequest request, HttpServletResponse response, Model model) {


		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.

		// 관리자 여부 확인
		String Mng = SessionUtil.getMngrYn();
		if ("Y".equals(Mng)) {
			String targetId = StringUtil.nvl(request.getParameter("targetId"));
			String mbrNm = StringUtil.nvl(request.getParameter("mbrNm"));
			String sDt = StringUtil.nvl(request.getParameter("sDt"));
			String eDt = StringUtil.nvl(request.getParameter("eDt"));
			String pageNumText = StringUtil.nvl(request.getParameter("pageNum"));
			int pageNum = "".equals(pageNumText) ? 1 : Integer.parseInt(pageNumText);

			model.addAttribute("pageNum", pageNum);
			model.addAttribute("mbrNm", mbrNm);
			model.addAttribute("sDt", sDt);
			model.addAttribute("eDt", eDt);
			model.addAttribute("targetId", targetId);

			MbrRes info = this.mbrService.selectUserInfo(targetId); // 검색된 객체

			model.addAttribute("info", info);


			// 위변조 방지를 위해 세션에 타켓을 담는다.
			Map<String, Object> sessionMap = new HashMap<>();
			sessionMap.put("user_id", StringUtil.nvl(SessionUtil.getUserId()));
			sessionMap.put("user_nm", StringUtil.nvl(SessionUtil.getUserNm()));
			sessionMap.put("mngr_yn", StringUtil.nvl(SessionUtil.getMngrYn()));
			sessionMap.put("target_id", targetId);

			SessionUtil.setLoginSession(sessionMap);
		}

		return "mbr/mbrDtl";
	}

	/*****************************************************
	 * 사용자 상태 변경
	 * @param loginReq 로그인
	 * @return ResponseEntity<ResponseDto> 로그인 정보
	 *****************************************************/
	@PostMapping(value = {"/mbr/updateUser"})
	@ApiOperation(value = "로그인 정보 조회", response = MbrRes.class, responseContainer = "code")
	public ResponseEntity<ResponseDto> updateUser(HttpServletRequest request, @ModelAttribute MbrReq mbrReq, BindingResult result) {

		String resultMsg = "잘못된 접속입니다.";
		Integer resultCode = HttpStatus.BAD_REQUEST.value();


		String target_id = StringUtil.nvl(SessionUtil.getTargetId());
		String mbrId = StringUtil.nvl(mbrReq.getMbrId());
		String sttCd = StringUtil.nvl(mbrReq.getSttCd());

		// 권한 확인 위변조 확인
		String mng = SessionUtil.getMngrYn();
		if ("Y".equals(mng) && target_id.equals(mbrId)) {

			//로그인 사용자 넣기
			mbrReq.setLoginId(SessionUtil.getUserId());

			// 사용자 정보 수정
			int count = 0;
			if ("A".equals(sttCd)) {
				count = this.mbrService.updateUser(mbrReq); //가입 승인
			} else if ("S".equals(sttCd)) {
				count = this.mbrService.deleteUser(mbrReq); //탈퇴
			}


			if (count > 0) {
				// 세션 생성
				Map<String, Object> sessionMap = new HashMap<>();
				sessionMap.put("user_id", StringUtil.nvl(SessionUtil.getUserId()));
				sessionMap.put("user_nm", StringUtil.nvl(SessionUtil.getUserNm()));
				sessionMap.put("mngr_yn", StringUtil.nvl(SessionUtil.getMngrYn()));
				sessionMap.put("target_id", "");

				SessionUtil.setLoginSession(sessionMap);
				resultMsg = "A".equals(sttCd) ? "승인되었습니다." :"탈퇴 처리되었습니다.";
				resultCode = HttpStatus.OK.value();
			} else {
				resultMsg = "회원을 찾을 수 없습니다.";
			}
		}

		return new ResponseEntity<>(
				ResponseDto.builder()
				.data(null)
				.resultMessage(resultMsg)
				.resultCode(resultCode)
				.build(),
				HttpStatus.OK);
	}

	/*****************************************************
	 * 회원 현황 엑셇 파일 다운로드
	 * @param mbrReq
	 * @return ResponseEntity<ResponseDto> 로그인 정보
	 *****************************************************/
	@GetMapping(value = {"/mbr/mbrLstExcelDown"})
	public void selectMbrListExcelDown(HttpServletRequest request, HttpServletResponse response, Model model) {

		try {

			ExcelUtil excelUtil = new ExcelUtil();

			// 0. 회원 엑셀 다운로드 목록 조회
			List<Map<String, Object>> list = mbrService.selectMbrListExcelDown();

			// 1. 브라우저별 한글 깨짐 방지
			String excelName = CommonUtil.getDisposition(request, "회원 목록 현황");

			// 2. 엑셀 다운로드
			excelUtil.download2(request, response, list, excelName, "mbr_mng_list_template.xlsx");

		} catch (Exception e) {

			log.debug(e.getMessage());
		}

	}

}

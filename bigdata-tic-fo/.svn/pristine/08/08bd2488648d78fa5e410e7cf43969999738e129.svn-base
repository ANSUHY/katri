package com.katri.web.comm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.katri.common.util.StringUtil;
import com.katri.web.comm.mapper.MailMapper;
import com.katri.web.comm.model.EmlSndngHistSaveReq;
import com.katri.web.comm.model.MailMakeBodyDto;
import com.katri.web.comm.model.MailSendDto;
import com.nhncorp.lucy.security.xss.XssPreventer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : Mail 서비스</li>
 * <li>서브 업무명 : Mail 서비스</li>
 * <li>설		 명 : Mail 관련 서비스 제공</li>
 * <li>작   성   자 : ASH</li>
 * <li>작   성   일 : 2022. 10. 07.</li>
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
@Slf4j
public class MailService {

	/** 메일 관리 업무 mapper */
	private final MailMapper mailMapper;

	/**
	 * DirectSend API URL
	 * 로컬 : https://directsend.co.kr/index.php/api_v2/mail_change_word
	 * 운영 : http://directsend.co.kr/index.php/api_v2/mail_change_word
	 * 운영은 WebToB 통해서 전송하기 때문에 http로 호출
	 *
	 *
	 * 보내는쪽의 IP는 사이트에 등록해야함_https://directsend.co.kr/index.php/address/api
	 */
	@Value("${mail.api.url}")
	String API_URL;

	/** 사이트에인증된발신E-mail _ https://directsend.co.kr/index.php/mail/option_sender */
	@Value("${mail.sender.email}")
	String SENDER_EMAIL;

	/** 발신자이름(35자 제한) */
	@Value("${mail.sender.name}")
	String SENDER_NAME;

	/** DirectSend 로그인ID */
	@Value("${mail.api.username}")
	String USER_NAME;

	/** 회원가입시발급받은Api Key _ https://directsend.co.kr/index.php/address/api */
	@Value("${mail.api.key}")
	String API_KEY;

	/** 메일 template 주소-request없을 경우 full경로 */
	@Value("${mail.template.path}")
	String MAIL_TEMPLATE_PATH;

	/** FO 도메인 주소  */
	@Value("${domain.web.fo}")
	String domainWebFo;

	/** BO 도메인 주소 */
	@Value("${domain.web.bo}")
	String domainWebBo;

	/*****************************************************
	 * 일반 메일 전송(즉각)
	 * @param mailSendDto 메일 내용
	 * @return 메일성공여부
	 * @throws Exception
	*****************************************************/
	public boolean sendMail(MailSendDto mailSendDto) throws Exception {

		// 메일 전송 서비스(진짜 메일 보내는 서비스)
		return this.sendMailServcie(mailSendDto);

	}

	/*****************************************************
	 * 예약 메일 전송(예약)
	 * @param mailSendDto 메일 내용
	 * @return 메일성공여부
	 * @throws Exception
	*****************************************************/
	public boolean sendReserveMail(MailSendDto mailSendDto) throws Exception {

		/* === 예약발송은 https://directsend.co.kr/index.php/mail/reserve 에서 확인가능함*/

		// 예약 메일 여부 = Y
		mailSendDto.setReserveYn("Y");

		// 메일 전송 서비스(진짜 메일 보내는 서비스)
		return this.sendMailServcie(mailSendDto);

	}

	/*****************************************************
	 * 메일 전송 서비스(진짜 메일 보내는 서비스)
	 * @param mailSendDto 메일 내용
	 * @return 메일성공여부
	 * @throws Exception
	*****************************************************/
	public boolean sendMailServcie(MailSendDto mailSendDto) throws Exception{

		boolean mailSuccess = false;

		// URL
		String url = API_URL;

		java.net.URL obj;
		obj = new java.net.URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestProperty("Cache-Control", "no-cache");
		con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		con.setRequestProperty("Accept", "application/json");

		/*
			#subject  : 받을 mail 제목, 치환 문자열 사용 가능.
						- 치환 문자열 : [$NAME] - 이름 (한글 10글자/영문 30byte 처리), [$EMAIL] - 이메일, [$MOBILE] - 휴대폰,
			  				[$NOTE1] - 비고1 (한글/영문 128자 처리), [$NOTE2] - 비고2 (한글/영문 128자 처리), [$NOTE3] - 비고3 (한글/영문 128자 처리), [$NOTE4] - 비고4 (한글/영문 128자 처리), [$NOTE5] - 비고5 (한글/영문 128자 처리)
			  			- 템플릿 사용시 템플릿에 입력된 메일 제목이 우선적으로 적용됩니다. 빌더로 템플릿을 저장할 경우 메일 제목은 저장되지 않으므로 subject값을 입력해주시기 바랍니다.
			#body  : 받을 mail 본문, 치환 문자열 사용 가능.
						- 치환 문자열 : [$NAME] - 이름 (한글 10글자/영문 30byte 처리), [$EMAIL] - 이메일, [$MOBILE] - 휴대폰,
							[$NOTE1] - 비고1 (한글/영문 128자 처리), [$NOTE2] - 비고2 (한글/영문 128자 처리), [$NOTE3] - 비고3 (한글/영문 128자 처리), [$NOTE4] - 비고4 (한글/영문 128자 처리), [$NOTE5] - 비고5 (한글/영문 128자 처리)
			 			- template : 사이트에 등록한 발송 할 템플릿 번호
			#sender : 발송자 메일주소
			#sender_name : 발송자 이름 (35자 제한)
			#username : directsend 발급 ID
			#receiver : 발송 할 고객 수신자 정보
			 			-json array. ex)
							[
								{"name": "강길동", "email":"test1@directsend.co.kr", "mobile":"", "note1":"", "note2":"", "note3":"", "note4":"", "note5":""}
								, {"name": "홍길동", "email":"test2@directsend.co.kr", "mobile":"수신자번호", "note1":"다이렉트 센드 2", "note2":"다이렉트센드 2", "note3":"다이렉트센드 3", "note4":"다이렉트센드 4", "note5":"다이렉트센드 5"}
							]
			#address_books : 사이트에 등록한 발송 할 주소록 번호 , 로 구분함 (ex. 0,1,2)
			#duplicate_yn : 수신자 정보가 중복될 경우 중복발송을 할지에 대한 여부
			#key : directsend 발급 api key

			각 내용이 유효하지않을 경우에는 발송이 되지 않습니다.
			비고 내용이 최대 길이(한글/영문 128자 처리)를 넘는 경우 최대 길이 만큼 잘려서 치환 됩니다.
			상업성 광고 메일이나 업체 홍보 메일을 발송하는 경우, 제목에 (광고) 문구를 표기해야 합니다.
			영리광고 발송 시, 명시적인 사전 동의를 받은 이에게만 광고 메일 발송이 가능합니다.
			수신동의 여부에 대한 분쟁이 발생하는 경우 이에 대한 입증책임은 광고성 정보 전송자에게 있습니다.
			수신자가 수신거부 또는 수신동의 철회 의사를 쉽게 표시할 수 있는 안내문을 명시해야 합니다.
			스팸 메일 발송 용도로 악용하실 경우 이용에 제한이 있을 수 있으니 이용 시 주의 부탁 드립니다.
			불법 스팸 메일 발송 시 예고없이 서비스 이용이 정지될 수 있으며 이용정지 시 해당 아이디의 주소록과 잔액은 소멸되며, 환불되지 않으니 서비스 이용에 주의를 부탁드립니다.

			API 연동 발송시 다량의 주소를 한번에 입력하여도 수신자에게는 1:1로 보내는 것으로 표기되며, 동일한 내용의 메일을 한건씩 발송하는 것보다 다량으로 한번에 보내는 것이 발송 효율이 더 높습니다.
			동일한 내용의 메일을 일부 글자만 변경하여 다수에게 발송하시는 경우 수신자 정보를 Json Array [{...}, {...}]로 구분하시어 한번에 발송하시는 것을 권장 드립니다.
		*/

		ObjectMapper objectMapper = new ObjectMapper();

		String subject		= XssPreventer.unescape(StringUtil.nvl(mailSendDto.getSubject())); //제목
		String body 		= StringUtil.nvl(mailSendDto.getBody());	//내용
		String sender		= SENDER_EMAIL;
		String sender_name 	= SENDER_NAME;
		String username 	= USER_NAME;
		String key 			= API_KEY;
		String receiver		= XssPreventer.unescape(objectMapper.writeValueAsString( mailSendDto.getReceiverList()));
		//receiver = "["+ receiver +"]";

		String mail_type 	= "NORMAL";

		/* _================= 추후에 사용할수도 있으니=========================
		//템플릿을 사용하길 원하실 경우 아래 주석을 해제하신후, 사이트에 등록한 템플릿 번호를 입력해주시기 바랍니다.
		//String template = "0";        //발송 할 템플릿 번호

		//주소록을 사용하길 원하실 경우 아래 주석을 해제하신 후, 사이트에 등록한 주소록 번호를 입력해주시기 바랍니다.
		//String address_books = "0,1,2";      //발송 할 주소록 번호 , 로 구분함 (ex. 0, 1, 2)

		//수신자 정보가 중복이고 내용이 다를 경우 아래 주석을 해제하시고 발송해주시기 바랍니다.
		//String duplicate_yn = "1";

		//실제 발송성공실패 여부를 받기 원하실 경우 아래 주석을 해제하신 후, 사이트에 등록한 URL 번호를 입력해주시기 바랍니다.
		int return_url = 0;

		//open, click 등의 결과를 받기 원하실 경우 아래 주석을 해제하신 후, 사이트에 등록한 URL 번호를 입력해주시기 바랍니다.
		//등록된 도메인이 http://domain 와 같을 경우, http://domain?type=[click | open | reject]&mail_id=[MailID]&email=[Email]&sendtime=[SendTime]&mail_reserve_id=[MailReserveID] 과 같은 형식으로 request를 보내드립니다.
		int option_return_url = 0;

		int open = 1;	// open 결과를 받으려면 아래 주석을 해제 해주시기 바랍니다.
		int click = 1;	// click 결과를 받으려면 아래 주석을 해제 해주시기 바랍니다.
		int check_period = 3;	// 트래킹 기간을 지정하며 3 / 7 / 10 / 15 일을 기준으로 지정하여 발송해 주시기 바랍니다. (단, 지정을 하지 않을 경우 결과를 받을 수 없습니다.)
		====================================================================================================
		*/

		// 예약발송 정보 추가
		String reserveUrl = ""; //예약 발송 관련한 url 생성 (예약 관련 파라미터)
		if(mailSendDto.getReserveYn() != null && "Y".equals(mailSendDto.getReserveYn()) ) {

			mail_type = "ONETIME"; // NORMAL - 즉시발송 / ONETIME - 1회예약 / WEEKLY - 매주정기예약 / MONTHLY - 매월정기예약
			String start_reserve_time = mailSendDto.getReserveTime();// 발송하고자 하는 시간
			String end_reserve_time = start_reserve_time;// 발송이 끝나는 시간 1회 예약일 경우 start_reserve_time = end_reserve_time
			// WEEKLY | MONTHLY 일 경우에 시작 시간부터 끝나는 시간까지 발송되는 횟수 Ex) type = WEEKLY, start_reserve_time = '2017-05-17 13:00:00', end_reserve_time = '2017-05-24 13:00:00' 이면 remained_count = 2 로 되어야 합니다.
			int remained_count = 1;
			// 예약 수정/취소 API는 소스 하단을 참고 해주시기 바랍니다.

			/* === 예약 발송 관련한 url 생성 (예약 관련 파라미터) */
			reserveUrl = ", \"mail_type\":\"" + mail_type + "\" "
					+ ", \"start_reserve_time\":\"" + start_reserve_time + "\" "
					+ ", \"end_reserve_time\":\"" + end_reserve_time + "\" "
					+ ", \"remained_count\":\"" + remained_count + "\" ";

		}

		/*  _================= 추후에 사용할수도 있으니=========================
		//필수안내문구 추가
		String agreement_text = "본메일은 [$NOW_DATE] 기준, 회원님의 수신동의 여부를 확인한 결과 회원님께서 수신동의를 하셨기에 발송되었습니다.";
		String deny_text = "메일 수신을 원치 않으시면 [$DENY_LINK]를 클릭하세요. \\nIf you don't want this type of information or e-mail, please click the [$EN_DENY_LINK]";
		String sender_info_text = "사업자 등록번호:-- 소재지:ㅇㅇ시(도) ㅇㅇ구(군) ㅇㅇ동 ㅇㅇㅇ번지 TEL:-- \\nEmail: <a href='mailto:test@directsend.co.kr'>test@directsend.co.kr</a>";
		int logo_state = 1; // logo 사용시 1 / 사용안할 시 0
		String logo_path = "http://logoimage.com/image.png';  //사용하실 로고 이미지를 입력하시기 바랍니다.";
		String logo_sort = "CENTER";  //로고 정렬 LEFT - 왼쪽 정렬 / CENTER - 가운데 정렬 / RIGHT - 오른쪽 정렬
		String footer_sort = "CENTER";  //메일내용, 풋터(수신옵션) 정렬 LEFT - 왼쪽 정렬 / CENTER - 가운데 정렬 / RIGHT - 오른쪽 정렬
		====================================================================================================
		*/

		/*  _ ================= 추후에 사용할수도 있으니=========================
		// 첨부파일의 URL을 보내면 DirectSend에서 파일을 download 받아 발송처리를 진행합니다. 첨부파일은 전체 10MB 이하로 발송을 해야 하며, 파일의 구분자는 '|(shift+\)'로 사용하며 5개까지만 첨부가 가능합니다.
		String file_url = "https://directsend.co.kr/test.png|https://directsend.co.kr/test1.png";
		// 첨부파일의 이름을 지정할 수 있도록 합니다.
		// 첨부파일의 이름은 순차적(https://directsend.co.kr/test.png - image.png, https://directsend.co.kr/test1.png - image2.png) 와 같이 적용이 되며, file_name을 지정하지 않은 경우 마지막의 파일의 이름으로 메일에 보여집니다.
		String file_name = "image.png|image2.png";
		====================================================================================================
		*/

		/*  _ ================= 추후에 사용할수도 있으니=========================
		int bodytag = 1;  //HTML이 기본값 입니다. 메일 내용을 텍스트로 보내실 경우 주석을 해제 해주시기 바랍니다.
		====================================================================================================
		*/

		String urlParameters = "\"subject\":\"" + subject + "\" "
			+ ", \"body\":\"" + body + "\" "
			+ ", \"sender\":\"" + sender + "\" "
			+ ", \"sender_name\":\"" + sender_name + "\" "
			+ ", \"username\":\"" + username + "\" "
			+ ", \"receiver\":" + receiver
			//+ ", \"template\":\"" + template + "\" "		        //템플릿 사용할 경우 주석 해제
			//+ ", \"address_books\":\"" + address_books + "\" "	//주소록 사용할 경우 주석 해제
			//+ ", \"duplicate_yn\":\"" + duplicate_yn + "\" "      //중복 발송을 허용할 경우 주석 해제

			// 예약 관련 파라미터
			+ reserveUrl

			// 필수 안내문구 관련 파라미터 주석 해제
			//+ ", \"agreement_text\":\"" + agreement_text + "\" "
			//+ ", \"deny_text\":\"" + deny_text + "\" "
			//+ ", \"sender_info_text\":\"" + sender_info_text + "\" "
			//+ ", \"logo_path\":\"" + logo_path + "\" "
			//+ ", \"logo_state\":\"" + logo_state + "\" "
			//+ ", \"logo_sort\":\"" + logo_sort + "\" "

			// 메일내용, 풋터(수신옵션) 정렬 사용할 경우 주석 해제
			//+ ", \"footer_sort\":\"" + footer_sort + "\" "

			// 메일 발송결과를 받고 싶은 URL     return_url이 있는 경우 주석해제 바랍니다.
			//+ ", \"return_url_yn\": " + true        //return_url 사용시 필수 입력
			//+ ", \"return_url\":\"" + return_url + "\" "		    //return_url 사용시 필수 입력

			// 발송 결과 측정 항목을 사용할 경우 주석 해제
			//+ ", \"open\":\"" + open + "\" "
			//+ ", \"click\":\"" + click + "\" "
			//+ ", \"check_period\":\"" + check_period + "\" "
			//+ ", \"option_return_url\":\"" + option_return_url + "\" "

			// 첨부 파일이 있는 경우 주석 해제
			//+ ", \"file_url\":\"" + file_url + "\" "
			//+ ", \"file_name\":\"" + file_name + "\" "

			// 메일 내용 텍스트로 보내실 경우 주석 해제
			//+ ", \"bodytag\":\"" + bodytag + "\" "

			+ ", \"key\":\"" + key + "\" ";
		urlParameters = "{"+ urlParameters  +"}";		//JSON 데이터

		con.setDoOutput(true);
		OutputStreamWriter wr = null;
		try {
			wr = new OutputStreamWriter (con.getOutputStream());
			wr.write(urlParameters);
			wr.flush();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			if(wr != null) {
				wr.close();
			}
		}

		int responseCode = con.getResponseCode();

		/*
		* responseCode 가 200 이 아니면 내부에서 문제가 발생한 케이스입니다.
		* directsend 관리자에게 문의해주시기 바랍니다.
		*/

		java.io.BufferedReader in = null;
		String inputLine = null;
		StringBuffer response = null;

		try {
			in = new java.io.BufferedReader(new java.io.InputStreamReader(con.getInputStream()));
			response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			if(in != null) {
				in.close();
			}
		}

		/*
		  * response의 실패
		  * {"status":101, "msg":"UTF-8 인코딩이 아닙니다."}
		  * 실패 코드번호, 내용
		*/

		/*
		  * response 성공
		  * {"status":0}
		  * 성공 코드번호 (성공코드는 다이렉트센드 DB서버에 정상수신됨을 뜻하며 발송성공(실패)의 결과는 발송완료 이후 확인 가능합니다.)
		  *
		  * 잘못된 이메일 주소가 포함된 경우
		  * {"status":0, "msg":"유효하지 않는 이메일을 제외하고 발송 완료 하였습니다.", "msg_detail":"error email : test2@test2, test3@test"}
		  * 성공 코드번호 (성공코드는 다이렉트센드 DB서버에 정상수신됨을 뜻하며 발송성공(실패)의 결과는 발송완료 이후 확인 가능합니다.), 내용, 잘못된 데이터
		  *
		*/

		/*
			status code
			0   : 정상발송 (성공코드는 다이렉트센드 DB서버에 정상수신됨을 뜻하며 발송성공(실패)의 결과는 발송완료 이후 확인 가능합니다.)
			100 : POST validation 실패
			101 : 회원정보가 일치하지 않음
			102 : Subject, Body 정보가 없습니다.
			103 : Sender 이메일이 유효하지 않습니다.
			104 : receiver 이메일이 유효하지 않습니다.
			105 : 본문에 포함되면 안되는 확장자가 있습니다.
			106 : body validation 실패
			107 : 받는사람이 없습니다.
			108 : 예약정보가 유효하지 않습니다.
			109 : return_url이 없습니다.
			110 : 첨부파일이 없습니다.
			111 : 첨부파일의 개수가 5개를 초과합니다.
			112 : 파일의 총Size가 10 MB를 넘어갑니다.
			113 : 첨부파일이 다운로드 되지 않았습니다.
			114 : utf-8 인코딩 에러 발생
			115 : 템플릿 validation 실패
			200 : 동일 예약시간으로는 200회 이상 API 호출을 할 수 없습니다.
			201 : 분당 300회 이상 API 호출을 할 수 없습니다.
			202 : 발송자명이 최대길이를 초과 하였습니다.
			205 : 잔액부족
			999 : Internal Error.
		 */

		/**
		 * /////////////////////////////////////////////////////////////////////////////////////////////////////////
		 * ////////////아래_____코아메소드////////////////////////////////////////////////////////////////////////////
		 * /////////////////////////////////////////////////////////////////////////////////////////////////////////
		 * */

		/* 객체로 만들어 적용하기 */
		ObjectMapper mapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, String> resultMap = mapper.readValue(response.toString(), Map.class);
		String statusCode = resultMap.get("status");

		/* 이메일발송이력 등록 */
		EmlSndngHistSaveReq emlSndngHistSaveReq = new EmlSndngHistSaveReq();
		emlSndngHistSaveReq.setSndrEmlAddr(SENDER_EMAIL); 	//발송자이메일주소
		emlSndngHistSaveReq.setSndrNm(SENDER_NAME); 		//발송자명
		emlSndngHistSaveReq.setRcvrInfoCn(receiver); 		//수신자정보내용
		emlSndngHistSaveReq.setEmlSjNm(subject);			//이메일제목명
		emlSndngHistSaveReq.setEmlCn(body);					//이메일내용
		emlSndngHistSaveReq.setEmlFomCd(null);				//이메일형식코드
		emlSndngHistSaveReq.setEmlSndngTyCd(mail_type);		//이메일발송유형코드
		emlSndngHistSaveReq.setSndngRsvtDt(mailSendDto.getReserveTime()); //발송예약일시
		emlSndngHistSaveReq.setRsltDtlCn(resultMap.toString()); //결과상세내용
		mailMapper.insertEmlSndngHist(emlSndngHistSaveReq);

		/* 응답값 셋팅 */
		if(responseCode == 200 && "0".equals(statusCode) ) {
			mailSuccess = true;
		} else {
			mailSuccess = false;
			log.error("\n===============ERROR_EMAIL=========================\n" + resultMap.toString());
		}

		return mailSuccess;

	}

	/*****************************************************
	 * 메일 body html 만들어주기
	 * @param request
	 * @param mailMakeBodyDto
	 * @return 만든 mailBody html
	 * @throws Exception
	*****************************************************/
	public String makeMailBody(HttpServletRequest request, MailMakeBodyDto mailMakeBodyDto) throws Exception {

		String rHtml = "";
		String fName = "";
		File 	f;

		/* ============= [[0]]. template 정보가져오기 */
		fName = mailMapper.selectMailTemplateNm(mailMakeBodyDto);

		/* ============= [[1]]. template html을 String으로 변경 */
		if(request == null) {
			f = new File(MAIL_TEMPLATE_PATH + fName);
		} else {
			String mRealPath = request.getSession().getServletContext().getRealPath("/");
			f = new File(mRealPath +"/WEB-INF/template/mail/"+fName);
		}

		BufferedReader reader = null;
		String line = null;
		StringBuffer sb = null;

		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(f),"UTF-8"));

			sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			rHtml = sb.toString();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			if(reader != null) {
				reader.close();
			}
		}

		/* ============= [[2]]. param 넣기 */
		// 2-1. param 추출
		Map<String, Object> mailParam = new HashMap<String, Object>();
		if(mailMakeBodyDto.getMailParam() != null) {
			mailParam = mailMakeBodyDto.getMailParam();
		}
		mailParam.put("d_domainWebFo", domainWebFo);
		mailParam.put("d_domainWebBo", domainWebBo);

		// 2-2. html에 내용 넣어주기
		Iterator<String> iter = mailParam.keySet().iterator();
		while(iter.hasNext()) {
			String key = iter.next();
			rHtml = rHtml.replaceAll("\\{"+key+"}", (String) mailParam.get(key));
		}

		return rHtml;

	}


}

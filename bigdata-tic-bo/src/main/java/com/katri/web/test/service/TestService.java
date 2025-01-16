package com.katri.web.test.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.Const;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.web.comm.model.MailMakeBodyDto;
import com.katri.web.comm.model.MailSendDto;
import com.katri.web.comm.model.MailSendReceiverDto;
import com.katri.web.comm.service.MailService;
import com.katri.web.test.mapper.TestMapper;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
@Api(tags = {"테스트 Service"})
public class TestService {

	/** 테스트 MAPPER */
	private final TestMapper testMapper;

	/** 메일 Service */
	private final MailService mailService;

	/*****************************************************
	 * 임시임시 개인 회원가입 완료 시 발송
	 * @param request
	 * @return 성공여부
	 * @throws Exception
	*****************************************************/
	public boolean dbUpdateAndSendMailComp(HttpServletRequest request) throws Exception {

		String strSavetErrMsgCode = "result-message.messages.common.message.save.error"; //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		int saveCount = 0;
		boolean success = false;

		/* [[0]]. 임시 업데이트 */
		saveCount = 1;
		if(saveCount == 0) {
			throw new CustomMessageException(strSavetErrMsgCode);
		}

		/* [[1]]. 메일 body 생성 */
		// 1-1. mailParam설정
		Map<String, Object> mailParam = new HashMap<String, Object>();
		mailParam.put("tttTempDate"	, "2022-01-01");
		mailParam.put("tttTempId"	, "ASHASH****");

		// 1-2. body
		MailMakeBodyDto mailMakeBodyDto = new MailMakeBodyDto();
		mailMakeBodyDto.setMailTmepCd(Const.Code.MailTemplateCd.MEM_REGI_COMP_GENERAL); 	//메일 template 공통코드
		mailMakeBodyDto.setMailParam(mailParam); 	//메일 param (메일내용에 들어갈 param들)
		String sBody = mailService.makeMailBody(request, mailMakeBodyDto);

		/* [[2]]. 메일보내기 */
		if(! "".equals(sBody)) {

			// 2-1. 수신자 LIST
			List<MailSendReceiverDto> list_resiver = new ArrayList<MailSendReceiverDto>();
			list_resiver.add( new MailSendReceiverDto("an.sh@coremethod.co.kr"));
			list_resiver.add( new MailSendReceiverDto("93_58@naver.com"));
			//list_resiver.add( new MailSendReceiverDto("dhfkdl26@coremethod.co.kr"));
			//list_resiver.add( new MailSendReceiverDto("kangjk@coremethod.co.kr"));

			// 2-2. DTO생성 후 메일 보내기
			MailSendDto mailSendDto = new MailSendDto();
			mailSendDto.setSubject("개인 회원가입 완료 시 메일메일_BO");
			mailSendDto.setBody(sBody);
			mailSendDto.setReceiverList(list_resiver);
			success = mailService.sendMail(mailSendDto);
		}

		return success;
	}

	/*****************************************************
	 * 임시임시 DB업데이트만
	 * @return 성공 카운트
	 * @throws Exception
	*****************************************************/
	public int dbUpdate() throws Exception {

		String strSavetErrMsgCode = "result-message.messages.common.message.save.error"; //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		int saveCount = 0;

		/* [[0]]. 임시 업데이트 */
		saveCount = testMapper.updateTempUpdate(Const.Code.MailTemplateCd.SEND_EMAIL_AUTH_NUMBER);
		if(saveCount == 0) {
			throw new CustomMessageException(strSavetErrMsgCode);
		}

		return saveCount;
	}

	/*****************************************************
	 * 임시임시 메일전송(예약)만
	 * @param request
	 * @return 메일전송 성공여부
	 * @throws Exception
	*****************************************************/
	public boolean sendReserveMail(HttpServletRequest request) throws Exception {

		boolean mailSuccess = false;

		//예약시간 하기위한 임시 코드
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, 6); //6분더하기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(cal.getTime());


		/* [[1]]. 메일 body 생성 */
		// 1-1. mailParam설정
		Map<String, Object> mailParam = new HashMap<String, Object>();
		mailParam.put("companyNm", "안수현~~~예약ㅇㅇ_bobo");
		mailParam.put("comClaInfo", "ASHASH~~~예약ㅇㅇ");
		mailParam.put("hrefAdress", "http://localhost:18083/");

		// 1-2. body
		MailMakeBodyDto mailMakeBodyDto = new MailMakeBodyDto();
		mailMakeBodyDto.setMailTmepCd(Const.Code.MailTemplateCd.SEND_EMAIL_AUTH_NUMBER); 	//메일 template 공통코드
		mailMakeBodyDto.setMailParam(mailParam); 	//메일 param (메일내용에 들어갈 param들)
		String sBody = mailService.makeMailBody(request, mailMakeBodyDto);

		/* [[2]]. 메일보내기 */
		if(! "".equals(sBody)) {

			// 2-1. 수신자 LIST
			List<MailSendReceiverDto> list_resiver = new ArrayList<MailSendReceiverDto>();
			list_resiver.add( new MailSendReceiverDto("an.sh@coremethod.co.kr"));
			list_resiver.add( new MailSendReceiverDto("93_58@naver.com"));

			// 2-2. DTO생성 후 메일 보내기
			MailSendDto mailSendDto = new MailSendDto();
			mailSendDto.setSubject("###예약_TEST테스트입니다.~BO%~~~~~~");
			mailSendDto.setBody(sBody);
			mailSendDto.setReceiverList(list_resiver);
			mailSendDto.setReserveTime(strDate);
			mailSuccess = mailService.sendReserveMail(mailSendDto);
		}

		return mailSuccess;
	}

}

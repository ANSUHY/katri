package com.katri.web.mypage.inquiry.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.Const;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.EncryptUtil;
import com.katri.common.util.SessionUtil;
import com.katri.common.util.StringUtil;
import com.katri.web.comm.model.MailMakeBodyDto;
import com.katri.web.comm.model.MailSendDto;
import com.katri.web.comm.model.MailSendReceiverDto;
import com.katri.web.comm.service.MailService;
import com.katri.web.join.model.JoinSelectReq;
import com.katri.web.mypage.inquiry.mapper.InquiryMapper;
import com.katri.web.mypage.inquiry.model.InquirySaveReq;
import com.katri.web.mypage.inquiry.model.InquirySelectReq;
import com.katri.web.mypage.inquiry.model.InquirySelectRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = { Exception.class })
public class InquiryService {

	/** Inquiry Mapper */
	private final InquiryMapper inquiryMapper;

	/** Mail Service */
	private final MailService mailService;

	/*****************************************************
	 * Inquiry(1:1 문의) 카운트 조회
	 * @param inquirySelectReq
	 * @return 1:1문의 개수
	 * @throws CustomMessageException
	 *****************************************************/
	public int getInquiryCnt(InquirySelectReq inquirySelectReq) {
		//[[0]]. 반환할 데이터
		int inquiryCtnt	= 0;
		//[[1]]. inquiry 개수 조회
		inquiryCtnt	= inquiryMapper.selectInquiryListCount(inquirySelectReq);
		return inquiryCtnt;
	}

	/*****************************************************
	 * Inquiry(1:1 문의) 데이터 조회
	 * @param inquirySelectReq
	 * @return 1:1문의 데이터
	 * @throws CustomMessageException
	 *****************************************************/
	public List<InquirySelectRes> getInquiryList(InquirySelectReq inquirySelectReq) throws CustomMessageException {
		//[[0]]. 반환할 데이터
		List<InquirySelectRes> inquiryList	= null;

		//[[1]]. inquiry 리스트 조회
		inquiryList	= inquiryMapper.selectInquiryList(inquirySelectReq);


		if(inquiryList == null) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); //데이터가 없습니다.
		}

		return inquiryList;

	}

	/*****************************************************
	 * Inquiry(1:1 문의) 유형 조회
	 * @return 1:1문의 데이터 유형 목록
	 * @throws CustomMessageException
	 *****************************************************/
	public List<InquirySelectRes> getCategoryList(String userTyCd){
		//[[0]]. 반환할 데이터
		List<InquirySelectRes> categoryList = null;

		//[[1]]. 조회
		categoryList	= inquiryMapper.selectInquiryCategoryList(userTyCd);
		return categoryList;
	}

	/*****************************************************
	 * Inquiry(1:1 문의) 등록
	 * @param inquirySaveReq
	 * @return 1:1 문의 등록 결과 (int)
	 * @throws CustomMessageException
	 *****************************************************/
	public int regInquiry(HttpServletRequest request, InquirySaveReq inquirySaveReq) throws CustomMessageException {
		//[[0]]. 반환할 데이터
		int 	result 				= 0;
		boolean mailSuccess			= false;
		String strRcvrEmlAddr		= "";
		JoinSelectReq joinSelectReq	= new JoinSelectReq();

		//[[1]]. 등록
		/* 1-0. 현재 로그인중인 아이디 셋팅 */
		inquirySaveReq.setCrtrId(SessionUtil.getLoginUserId());

		/* 1-1. 매퍼 호출 */
		result	= inquiryMapper.insertInquiry(inquirySaveReq);

		if(result <= 0) {
			throw new CustomMessageException("result-message.messages.common.message.save.error"); //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return result;
	}

	/*****************************************************
	 * Inquiry(1:1 문의) 조회수 증가
	 * @param String
	 * @return 조회수 증가 결과 (int)
	 * @throws CustomMessageException
	 *****************************************************/
	public int addViewCount(String num) throws CustomMessageException {
		//[[0]]. 반환할 데이터
		int result	= 0;

		//[[1]]. 조회수 증가
		/* int 타입으로 형변환 */
		int nttsn 	= Integer.parseInt(num);
		result		= inquiryMapper.updateInqcnt(nttsn);

		if(result < 0) {
			throw new CustomMessageException("result-message.messages.common.message.error"); //오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		return result;
	}

	/*****************************************************
	 * Inquiry(1:1 문의) 등록 -> 메일 발송
	 * @param HttpServletRequest
	 * @return boolean (메일 전송 결과)
	 * @throws Exception
	 * @throws CustomMessageException
	 *****************************************************/
	public boolean sendInquiryMail(HttpServletRequest request) throws Exception {
		boolean mailSuccess	= false;

		/*[[0]]. 관리자 이메일 가지고 오기 */
		String mailAddress	= inquiryMapper.selectMailAddress();
		//0-1. 이메일 복호화
		String strEncemlAddr = mailAddress;
	//	String strEncemlAddr = StringUtil.nvl(EncryptUtil.decryptAes256(mailAddress));


		/*[[1]]. 메일 body 설정 */
		MailMakeBodyDto mailMakeBodyDto	= new MailMakeBodyDto();
		mailMakeBodyDto.setMailTmepCd(Const.Code.MailTemplateCd.WRITE_INQUIRY); //1:1문의 작성
		String sBody = mailService.makeMailBody(request, mailMakeBodyDto);

		/*[[2]]. 메일 보내기 */
		if(! "".equals(sBody)) {
			//2-1. 수신자 LIST
			List<MailSendReceiverDto> listResiver = new ArrayList<MailSendReceiverDto>();
			listResiver.add( new MailSendReceiverDto(strEncemlAddr)); //수신자 이메일 주소

			// 2-2. DTO 생성 후 메일 보내기
			MailSendDto mailSendDto = new MailSendDto();
			mailSendDto.setSubject("1:1 문의 신규 등록 안내");
			mailSendDto.setBody(sBody);
			mailSendDto.setReceiverList(listResiver);
			mailSuccess = mailService.sendMail(mailSendDto);
		}

		return mailSuccess;
	}




}

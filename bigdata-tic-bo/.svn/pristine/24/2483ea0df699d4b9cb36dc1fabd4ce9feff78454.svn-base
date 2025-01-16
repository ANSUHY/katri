package com.katri.web.ctnt.inquiry.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.katri.web.ctnt.inquiry.mapper.InquiryMapper;
import com.katri.web.ctnt.inquiry.model.InquirySaveReq;
import com.katri.web.ctnt.inquiry.model.InquirySelectReq;
import com.katri.web.ctnt.inquiry.model.InquirySelectRes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
@Slf4j
public class InquiryService {

	/** inquiry Mapper */
	private final InquiryMapper inquiryMapper;

	/** Mail Service */
	private final MailService mailService;

	/*****************************************************
	 * inquiry 리스트 개수
	 * @param inquirySelectReq
	 * @return Integer
	 * @throws Exception
	*****************************************************/
	public int getInqCnt(InquirySelectReq inquirySelectReq) {
		//[[0]].반환할 정보들
		int inqCnt = 0;

		//[[1]]. inquiry 개수 조회
		inqCnt	= inquiryMapper.getInqCnt(inquirySelectReq);

		return inqCnt;
	}

	/*****************************************************
	 * inquiry 리스트 데이터
	 * @param inquirySelectReq
	 * @return List<InquirySelectRes> inquiry 리스트
	 * @throws CustomMessageException
	 * @throws Exception
	*****************************************************/
	public List<InquirySelectRes> getInqList(InquirySelectReq searchData) throws CustomMessageException {
		//[[0]].반환할 정보들
		List<InquirySelectRes> inqList	= null;

		//[[1]].조회
		inqList	= inquiryMapper.getInqList(searchData);

		//[[2]]. 마스킹 처리
		for(int i=0; i<=inqList.size()-1; i++) {
			String targetId = inqList.get(i).getCrtrId();
			String strReturn = StringUtil.stringFormatType(targetId, "masking", "id");

			inqList.get(i).setCrtrId(strReturn);
		}

		if(inqList == null) {
			throw new CustomMessageException("resultMessage.messages.common.message.no.data"); //데이터가 없습니다.
		}

		return inqList;
	}

	/*****************************************************
	 * inquiry 상세 데이터
	 * @param inquirySelectRes
	 * @return inquirySelectRes
	 * @throws CustomMessageException
	 * @throws Exception
	*****************************************************/
	public InquirySelectRes getDetailData(int nttSn) throws CustomMessageException {
		//[[0]].반환할 정보들
		InquirySelectRes detailData	= null;
		int result					= 0;

		//[[1]].조회
		detailData	= inquiryMapper.getDetailData(nttSn);
		//[[2]] 조회수 증가
		result		= inquiryMapper.updateNttInqCnt(nttSn);

		if(detailData == null) {
			throw new CustomMessageException("resultMessage.messages.common.message.no.data"); //데이터가 없습니다.
		}

		if(result <= 0) {
			throw new CustomMessageException("resultMessage.messages.common.message.error"); //에러가 발생했습니다. 관리자에게 문의하여 주십시오.
		}

		return detailData;

	}

	/*****************************************************
	 * inquiry 답변 수정
	 * @param InquirySaveReq
	 * @return int
	 * @throws CustomMessageException
	 * @throws Exception
	*****************************************************/
	public int updateInquiryAns(InquirySaveReq updateData) throws CustomMessageException {
		//[[0]].반환할 정보들
		int result	= 0;
		//현재 로그인중인 아이디
		String nttAnsMdfrId = SessionUtil.getLoginMngrId();
		updateData.setNttAnsMdfrId(nttAnsMdfrId);

		//[[1]].수정
		result	= inquiryMapper.updateInquiryAns(updateData);

		if(result <= 0) {
			throw new CustomMessageException("resultMessage.message.common.message.update.error"); //수정 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return result;
	}

	/*****************************************************
	 * inquiry 답변 등록
	 * @param InquirySaveReq
	 * @return int
	 * @throws CustomMessageException
	 * @throws Exception
	*****************************************************/
	public int regInqAns(InquirySaveReq regData) throws CustomMessageException {
		//[[0]]. 반환할 데이터
		int result = 0;

		//현재 로그인중인 아이디
		String nttAnsCrtrId = SessionUtil.getLoginMngrId();
		regData.setNttAnsCrtrId(nttAnsCrtrId);

		//[[1]].수정
		result	= inquiryMapper.regInqAns(regData);

		if(result <= 0) {
			throw new CustomMessageException("resultMessage.message.common.message.save.error"); //저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}

		return result;

	}

	/*****************************************************
	 * inquiry 답변 완료 시 메일 전송
	 * @param HttpServletRequest
	 * @return boolean
	 * @throws CustomMessageException
	 * @throws Exception
	*****************************************************/
	public boolean sendInquiryMail(HttpServletRequest request, String crtrId) throws Exception {
		boolean success = false;

		/* [[0]]. 회원 이메일 가지고 오기 */
		String mailAddress = inquiryMapper.selectEmail(crtrId);
		// 0-1. 이메일 복호화
		String strEncemlAddr = StringUtil.nvl(EncryptUtil.decryptAes256(mailAddress));

		/* [[1]]. 메일 body 생성 */
		// 1-1. body
		MailMakeBodyDto mailMakeBodyDto = new MailMakeBodyDto();
		mailMakeBodyDto.setMailTmepCd(Const.Code.MailTemplateCd.WRITE_INQUIRY_ANSWER); //1:1문의 답변
		String sBody = mailService.makeMailBody(request, mailMakeBodyDto);

		/* [[2]]. 메일 보내기 */
		if(! "".equals(sBody)) {
			// 2-1. 수신자 LIST
			List<MailSendReceiverDto> list_resiver = new ArrayList<MailSendReceiverDto>();
			list_resiver.add( new MailSendReceiverDto(strEncemlAddr)); //수신자 이메일주소

			// 2-2. DTO 생성 후 메일 보내기
			MailSendDto mailSendDto = new MailSendDto();
			mailSendDto.setSubject("1:1 문의 답변 안내");
			mailSendDto.setBody(sBody);
			mailSendDto.setReceiverList(list_resiver);
			success = mailService.sendMail(mailSendDto);
		}



		return success;
	}




}

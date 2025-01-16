package com.katri.web.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.Const;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.EncryptUtil;
import com.katri.common.util.StringUtil;
import com.katri.web.auth.mapper.DrmncyMapper;
import com.katri.web.auth.model.DrmncySaveReq;
import com.katri.web.auth.model.DrmncySelectReq;
import com.katri.web.auth.model.DrmncySelectRes;
import com.katri.web.comm.model.MailMakeBodyDto;
import com.katri.web.comm.model.MailSendDto;
import com.katri.web.comm.model.MailSendReceiverDto;
import com.katri.web.comm.service.MailService;
import com.katri.web.join.mapper.JoinMapper;
import com.katri.web.join.model.JoinSaveReq;
import com.katri.web.join.model.JoinSelectReq;
import com.katri.web.join.model.JoinSelectRes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = { Exception.class })
@Slf4j
public class DrmncyService {

	/** 휴면 Mapper */
	private final DrmncyMapper drmncyMapper;

	/** mail Service */
	private final MailService mailService;

	/* join Mapper */
	private final JoinMapper joinMapper;

	/*****************************************************
	 * 유저 메일 조회
	 *
	 * @param String 유저 아이디
	 * @return String 유저 메일
	 * @throws CustomMessageException
	 * @throws Exception
	 *****************************************************/
	public DrmncySelectRes getUserMail(String userId) throws CustomMessageException {
		// [[0]]. 반환할 정보
		String userMail = null;
		DrmncySelectRes drmncySelectRes = new DrmncySelectRes();

		// [[1]]. 조회
		userMail = drmncyMapper.selectUserMail(userId);

		if (userMail == null || "".equals(userMail)) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		// [[2]]. 이메일 복호화
		String strEncemlAddr = StringUtil.nvl(EncryptUtil.decryptAes256(userMail));
		// [[2]]. 마스킹 처리
		String mailAddress = StringUtil.stringFormatType(strEncemlAddr, "masking", "email");

		drmncySelectRes.setStrEncemlAddr(strEncemlAddr); // 복호화 된 이메일
		drmncySelectRes.setMailAddress(mailAddress); // 마스킹 된 이메일

		return drmncySelectRes;
	}

	/*****************************************************
	 * 인증 메일 전송
	 *
	 * @param String 유저 아이디
	 * @return boolean 메일 전송 결과
	 * @throws CustomMessageException
	 * @throws Exception
	 *****************************************************/
	public boolean sendDrmncyMail(HttpServletRequest request, String rcvrEmlAddr) throws Exception {
		String strSavetErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여
																							// 주십시오.
		boolean success = false;
		int saveCount = 0;
		JoinSelectReq joinSelectReq = new JoinSelectReq();
		joinSelectReq.setRcvrEmlAddr(rcvrEmlAddr);


		/** ---------------------------------- [0_2]. 인증 메일 제한 체크 ---------------------------------- **/
		// [0_2_0]. 인증 메일 정보 조회 ( 발송 제한 건수, 발송 제한 시간, 인증번호 확인 제한 건수 )
		JoinSelectRes mailSendChkInfo = new JoinSelectRes();
		mailSendChkInfo = joinMapper.selectCertMailSendChkInfo();
		// [0_2_1]. 인증 정보 > 현재 계정한테 전송된 인증 메일 건수 조회
		joinSelectReq.setCertEmlLimitTime( mailSendChkInfo.getCertEmlLimitTime() + "minute" ); // 발송 제한 시간( 단위 : 분 )
		Integer nSendMailChkCnt 	= joinMapper.selectCertMailSendCount(joinSelectReq); // 현재 계정에 보낸 인증메일 건수
		Integer nCertEmlLimitCnt	= mailSendChkInfo.getCertEmlLimitCnt(); // 발송제한건수
		String nCertEmlLimitTime	= mailSendChkInfo.getCertEmlLimitTime(); // 발송제한시간
		// [0_2_2]. 발송 제한 건수와 같거나 많은 경우,
		if( nSendMailChkCnt >= nCertEmlLimitCnt ) {
			throw new CustomMessageException("result-message.messages.common.message.cert.mail.limit.error" + "||msgArgu=" + nCertEmlLimitTime + "," + nCertEmlLimitCnt + "||"); // [발송제한시간]분 동안 발송 제한 횟수 [발송제한건수]회를 초과하여 메일 발송이 제한되었습니다.
		}
		/** ------------------------------------------------------------------------------------------- **/

		/* [[0]]. 메일 body 생성 */
		// 0-0. mailParam 설정
		Map<String, Object> mailParam = new HashMap<String, Object>();

		// 0-1. 6자리 랜덤 숫자문자열 추가
		int strCertNo = ThreadLocalRandom.current().nextInt(100000, 1000000);
		mailParam.put("certNo", Integer.toString(strCertNo));

		// 0-2. body
		MailMakeBodyDto mailMakeBodyDto = new MailMakeBodyDto();
		mailMakeBodyDto.setMailTmepCd(Const.Code.MailTemplateCd.SEND_EMAIL_AUTH_NUMBER); // 메일 template 공통코드 - 이메일인증번호발송
		mailMakeBodyDto.setMailParam(mailParam); // 메일 param (메일내용에 들어갈 param들)
		String sBody = mailService.makeMailBody(request, mailMakeBodyDto);

		/* [[1]]. 메일 보내기 */
		if(! "".equals(sBody)) {
			// 1_1. 수신자 LIST
			List<MailSendReceiverDto> list_resiver = new ArrayList<MailSendReceiverDto>();
			list_resiver.add(new MailSendReceiverDto(rcvrEmlAddr));

			// 1_2. DTO생성 후 메일 보내기
			MailSendDto mailSendDto = new MailSendDto();
			mailSendDto.setSubject(" 시험인증 빅데이터 이메일 인증 안내 ");
			mailSendDto.setBody(sBody);
			mailSendDto.setReceiverList(list_resiver);
			success = mailService.sendMail(mailSendDto);

		} else {
			throw new CustomMessageException(strSavetErrMsgCode);
		}

		/* [[2]]. 보낸 인증 번호 DB 저장 */
		// 메일 전송 성공 시
		if (success) {
			// 2-0. 인증번호 셋팅
			DrmncySaveReq drmncySaveReq = new DrmncySaveReq();
			drmncySaveReq.setCertNo(Integer.toString(strCertNo));
			drmncySaveReq.setRcvrEmlAddr(rcvrEmlAddr);

			// 2-1. 인증번호 DB 저장
			saveCount = drmncyMapper.insertCertNoSndngHist(drmncySaveReq);

			if (saveCount == 0) {
				throw new CustomMessageException(strSavetErrMsgCode);
			}
		}

		return success;
	}

	/*****************************************************
	 * 휴면 아이디 해제
	 *
	 * @param String 휴면 아이디
	 * @return Int 업데이트 정보
	 * @throws CustomMessageException
	 * @throws Exception
	 *****************************************************/
	public int updateDrmncy(String userId, String pCi) throws CustomMessageException {
		// [[0]]. 필요한 정보
		int result = 0;
		DrmncySaveReq drmncySaveReq = new DrmncySaveReq();

		// [[1]]. 데이터 셋팅
		String userCd = Const.Code.UserSttCd.NORMAL; // USC020 (정상)
		drmncySaveReq.setUserId(userId);
		drmncySaveReq.setUserSttCd(userCd);
		drmncySaveReq.setPCi(pCi);


		// [[2]]. 휴면 해제
		result = drmncyMapper.updateDrmncy(drmncySaveReq);

		if (result <= 0) {
			throw new CustomMessageException("result-message.messages.user.message.nomatch.user"); //입력한 정보와 일치하는 회원정보가 없습니다.
		}

		return result;
	}

	/*****************************************************
	 * 휴면 아이디 해제 (메일 인증 회원)
	 *
	 * @param String 휴면 아이디
	 * @return Int 업데이트 정보
	 * @throws CustomMessageException
	 * @throws Exception
	 *****************************************************/
	public int updateDrmncyWithMail(String userId) throws CustomMessageException {
		//[[0]]. 반환할 정보
		int result	= 0;
		DrmncySaveReq drmncySaveReq = new DrmncySaveReq();

		//[[1]]. 휴면 해지
		String userSttCd = Const.Code.UserSttCd.NORMAL;
		drmncySaveReq.setUserId(userId);
		drmncySaveReq.setUserSttCd(userSttCd);

		result			= drmncyMapper.updateDrmncyWithMail(drmncySaveReq);

		if (result <= 0) {
			throw new CustomMessageException("result-message.messages.common.message.error");
		}

		return result;
	}

	/*****************************************************
	 * 가장 최근의 이메일 인증 번호 가져오기
	 *
	 * @param drmncySelectReq
	 * @return String
	 * @throws CustomMessageException
	 * @throws Exception
	 *****************************************************/
	public boolean getCertNoOfEmlAddr(DrmncySelectReq drmncySelectReq) throws CustomMessageException {

		JoinSelectReq joinSelectReq = new JoinSelectReq();
		joinSelectReq.setRcvrEmlAddr(drmncySelectReq.getEmlAddrVal());
		joinSelectReq.setChkCertNo(drmncySelectReq.getCertNo());

		boolean bCertYn = false;

		// [0]. 인증 메일 정보 조회 ( 발송 제한 건수, 발송 제한 시간, 인증번호 확인 제한 건수)
		JoinSelectRes mailSendChkInfo = new JoinSelectRes();
		mailSendChkInfo = joinMapper.selectCertMailSendChkInfo();

		Integer nCertChkLimitCnt = mailSendChkInfo.getCertChkLimitCnt(); // 인증번호 확인 제한 건수

		// [1]. 인증번호, 실패 건수 가져오기
		JoinSelectRes certInfo = joinMapper.selectCertNoOfEmlAddr(joinSelectReq);

		if( "".equals(certInfo.getCertNo()) || certInfo.getCertNo() == null) {
			throw new CustomMessageException("result-message.messages.common.message.cert.code.no.data"); // 해당 메일의 인증시 발급한 인증코드가 없습니다.
		}

		// [1_1]. 실패 횟수가 확인 제한수 보다 같거나 많은 경우
		if( certInfo.getCertFirCnt() >= nCertChkLimitCnt ) {
			throw new CustomMessageException("result-message.messages.common.message.cert.code.check.count.limit.error"+ "||msgArgu=" + nCertChkLimitCnt + "||"); // 인증 확인 횟수 [인증번호확인제한건수]회를 초과하였습니다. 이메일 인증 번호를 재 전송하여 인증해야 합니다.
		}

		// [2]. 실패 체크 및 반환값 셋팅
		if( certInfo.getCertNo().equals( joinSelectReq.getChkCertNo() ) ) {
			bCertYn = true;
		} else {
			// [2_0]. 인증번호 실패값 셋팅
			JoinSaveReq certFilInfo = new JoinSaveReq();
			certFilInfo.setCertNo( certInfo.getCertNo() ); // 인증번호
			certFilInfo.setRcvrEmlAddr( joinSelectReq.getRcvrEmlAddr() ); // 메일 정보

			// [2_1]. 인증 번호 실패 횟수 증가
			int nSaveCount = joinMapper.updateCertNoFailCnt(certFilInfo);

			if(! (nSaveCount > 0 ) ) {
				throw new CustomMessageException("result-message.messages.common.message.error"); // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}

			// [2_2]. 반환값 셋팅
			bCertYn = false;

		}

		return bCertYn;
	}


}

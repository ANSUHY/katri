package com.katri.web.join.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.Const;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.EncryptUtil;
import com.katri.common.util.StringUtil;
import com.katri.web.comm.model.CommReq;
import com.katri.web.comm.model.FileDto;
import com.katri.web.comm.model.MailMakeBodyDto;
import com.katri.web.comm.model.MailSendDto;
import com.katri.web.comm.model.MailSendReceiverDto;
import com.katri.web.comm.service.AnlsApiService;
import com.katri.web.comm.service.FileService;
import com.katri.web.comm.service.MailService;
import com.katri.web.join.mapper.JoinMapper;
import com.katri.web.join.model.JoinPrdtSelectRes;
import com.katri.web.join.model.JoinSaveReq;
import com.katri.web.join.model.JoinSaveRes;
import com.katri.web.join.model.JoinSelectReq;
import com.katri.web.join.model.JoinSelectRes;
import com.nhncorp.lucy.security.xss.XssPreventer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class JoinService {

	/** 파일 Service */
	private final FileService fileService;

	/** Join Mapper */
	private final JoinMapper joinMapper;

	/** 메일 Service */
	private final MailService mailService;

	/** API Service */
	private final AnlsApiService anlsApiService;

	/*****************************************************
	 * [회원 가입] > 약관 타입 별 정보 조회
	 * @param joinSelectReq
	 * @return
	*****************************************************/
	public JoinSelectRes getJoinTrmsDetail(JoinSelectReq joinSelectReq) {

		JoinSelectRes joinSelectRes = null;

		// [0]. 약관 타입 별 정보 조회
		joinSelectRes = joinMapper.selectJoinTrmsDetail(joinSelectReq);

		if( joinSelectRes != null ) {
			joinSelectRes.setTrmsCn( XssPreventer.unescape(joinSelectRes.getTrmsCn()) );
		}

		return joinSelectRes;
	}

	/*****************************************************
	 * [회원 가입] > 아이디 중복 체크 검사
	 * @param joinSelectReq
	 * @return
	*****************************************************/
	public int getIdDpcnChkCnt(JoinSelectReq joinSelectReq) throws Exception {

		int nDpcnCnt = 0;

		// 0. 아이디 없는 경우
		if( "".equals(joinSelectReq.getTargetUserId()) ) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		// 1. 중복 조회
		nDpcnCnt = joinMapper.selectIdDpcnChkCnt(joinSelectReq);

		return nDpcnCnt;
	}

	/*****************************************************
	 * [회원 가입] > 메일 인증 번호 발송 후 인증 번호 DB 저장
	 * @param request
	 * @param joinSelectReq
	 * @return
	*****************************************************/
	public boolean joinCertMailSend(HttpServletRequest request, JoinSelectReq joinSelectReq) throws Exception {

		//=======CSRF 체크=============
		String referer = request.getHeader("Referer");
		String host = request.getHeader("host");
		if (referer == null || !referer.contains(host)) {
			throw new CustomMessageException("result-message.messages.common.message.wrong.appr"); //잘못된 접근입니다.
		}

		// [0_0]. 유효성 검사. 입력한 [이메일] 있는지 체크
		if( joinSelectReq.getRcvrEmlAddr() == null  || "".equals(joinSelectReq.getRcvrEmlAddr()) ) {
			throw new CustomMessageException("result-message.messages.common.message.required.data2" + "||msgArgu=이메일||"); // {0}를(을) 입력해주세요.
		}

		// [0_1]. 유효성 검사. [이메일] 형식 체크 */
		String emlPattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
		Matcher emlM = Pattern.compile(emlPattern).matcher(joinSelectReq.getRcvrEmlAddr());
		if( ! emlM.find() ) {
			throw new CustomMessageException("result-message.messages.join.message.email.pattern.error"); // 이메일을 형식에 맞게 입력해 주세요.
		}

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

		String 	strSavetErrMsgCode 	= "result-message.messages.common.message.error"; // 에러가 발생하였습니다. 관리자에게 문의하여 주십시오.
		String	strRcvrEmlAddr		= joinSelectReq.getRcvrEmlAddr(); // 인증 번호 받는 이메일 주소
		boolean success 			= false;
		int 	saveCount 			= 0;

		/* [[0]]. 메일 body 생성 */
		// 0_0. mailParam설정
		Map<String, Object> mailParam = new HashMap<String, Object>();

		// 0_1. 6자리 랜덤 숫자문자열 추가
		int strCertNo = ThreadLocalRandom.current().nextInt(100000, 1000000);
		mailParam.put( "certNo", Integer.toString(strCertNo) );

		// 0_1. body
		MailMakeBodyDto mailMakeBodyDto = new MailMakeBodyDto();
		mailMakeBodyDto.setMailTmepCd( Const.Code.MailTemplateCd.SEND_EMAIL_AUTH_NUMBER ); 	// 메일 template 공통코드 - 이메일인증번호발송
		mailMakeBodyDto.setMailParam(mailParam); //메일 param (메일내용에 들어갈 param들)
		String sBody = mailService.makeMailBody(request, mailMakeBodyDto);

		/* [[1]]. 메일보내기 */
		if(! "".equals(sBody)) {

			// 1_1. 수신자 LIST
			List<MailSendReceiverDto> list_resiver = new ArrayList<MailSendReceiverDto>();
			list_resiver.add( new MailSendReceiverDto( strRcvrEmlAddr ));

			// 1_2. DTO생성 후 메일 보내기
			MailSendDto mailSendDto = new MailSendDto();
			mailSendDto.setSubject( "시험인증 빅데이터 이메일 인증 안내" );
			mailSendDto.setBody(sBody);
			mailSendDto.setReceiverList(list_resiver);
			success = mailService.sendMail(mailSendDto);

		} else {
			throw new CustomMessageException(strSavetErrMsgCode);
		}

		/* [[2]]. 보낸 인증 번호 DB 저장 */
		// 메일 전송 성공 시
		if( success ) {
			// 2_0. 인증번호 셋팅
			JoinSaveReq joinSaveReq = new JoinSaveReq();
			joinSaveReq.setCertNo( Integer.toString(strCertNo) );
			joinSaveReq.setRcvrEmlAddr( strRcvrEmlAddr );

			// 2_1. 인증번호 DB 저장
			saveCount = joinMapper.insertCertNoSndngHist(joinSaveReq);

			if(saveCount == 0) {
				strSavetErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
				throw new CustomMessageException(strSavetErrMsgCode);
			}
		}

		return success;

	}

	/*****************************************************
	 * [회원 가입] > 이메일 가장 최신 인증 번호 가져오기
	 * @param joinSelectReq
	 * @return
	*****************************************************/
	public boolean getCertNoOfEmlAddr(JoinSelectReq joinSelectReq) throws Exception {

		boolean bCertYn = false;

		// [0]. 인증 메일 정보 조회 ( 발송 제한 건수, 발송 제한 시간, 인증번호 확인 제한 건수 )
		JoinSelectRes mailSendChkInfo = new JoinSelectRes();
		mailSendChkInfo = joinMapper.selectCertMailSendChkInfo();

		Integer nCertChkLimitCnt = mailSendChkInfo.getCertChkLimitCnt(); // 인증번호 확인 제한 건수

		// [1]. 인증번호, 실패 건수 가져오기
		JoinSelectRes certInfo = joinMapper.selectCertNoOfEmlAddr(joinSelectReq);

		if( "".equals(certInfo.getCertNo()) || certInfo.getCertNo() == null ) {
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

	/*****************************************************
	 * [회원 가입] > 회원 정보 저장 전 아이디 및 비밀번호 유효성 검사
	 * @param joinSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public void saveUserJoinInfoIdPwdValidation( JoinSaveReq joinSaveReq ) throws Exception {

		/* 0. 입력한 [아이디] 있는지 체크*/
		if( joinSaveReq.getUserId() == null  || "".equals(joinSaveReq.getUserId()) ) {
			throw new CustomMessageException("result-message.messages.common.message.required.data2" + "||msgArgu=아이디||"); // '{0}를(을) 입력해주세요.'
		}

		/* 1. [아이디] 패턴 체크 */
		String idPattern = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{6,20}$";
		Matcher idM = Pattern.compile(idPattern).matcher(joinSaveReq.getUserId());
		if( ! idM.find() ) {
			throw new CustomMessageException("result-message.messages.join.message.id.pattern.error"); // '아이디는 6~20자의 영문, 숫자 중 2가지 이상 문자를 조합하여 사용할 수 있습니다.'
		}

		/* 2. 입력한 [비밀번호] 있는지 체크*/
		if( joinSaveReq.getUserPwd() == null  || "".equals(joinSaveReq.getUserPwd()) ) {
			throw new CustomMessageException("result-message.messages.common.message.required.data2" + "||msgArgu=비밀번호||"); // '{0}를(을) 입력해주세요.'
		}

		/* 3. 입력한 [비밀번호 확인] 있는지 체크*/
		if( joinSaveReq.getUserPwdChk() == null || "".equals(joinSaveReq.getUserPwdChk()) ) {
			throw new CustomMessageException("result-message.messages.common.message.required.data2" + "||msgArgu=비밀번호 확인||"); // '{0}를(을) 입력해주세요.'
		}

		/* 4. [비밀번호] 와 [비밀번호 확인] 값 같은지 체크 */
		if( ! (joinSaveReq.getUserPwd().equals( joinSaveReq.getUserPwdChk()) ) ) {
			throw new CustomMessageException("result-message.messages.pwd.message.nomatch.chgpwd.chgpwdcheck"); // '비밀번호가 일치하지 않습니다.'
		}

		/* 5. [비밀번호] 패턴 체크 */
		String pwdPattern = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$";
		Matcher pwdM = Pattern.compile(pwdPattern).matcher(joinSaveReq.getUserPwd());
		if( ! pwdM.find() ) {
			throw new CustomMessageException("result-message.messages.join.message.pwd.pattern.error"); // '비밀번호는 8~20자의 영문, 숫자, 특수문자 중 3가지 이상의 문자를 조합하여 사용할 수 있습니다.'
		}

		/* 6. [비밀번호]가 ID 포함 하고 있는지 체크_겹치면 안됨 */
		if( (joinSaveReq.getUserPwd().contains( joinSaveReq.getUserId() )) ) {
			throw new CustomMessageException("result-message.messages.pwd.message.include.chgpwd.id"); // '비밀번호에 아이디가 포함되면 안됩니다.'
		}

		/* 7. 입력한 [이메일] 있는지 체크*/
		if( joinSaveReq.getEmlAddrVal() == null  || "".equals(joinSaveReq.getEmlAddrVal()) ) {
			throw new CustomMessageException("result-message.messages.common.message.required.data2" + "||msgArgu=이메일||"); // '{0}를(을) 입력해주세요.'
		}

		/* 8. [이메일] 형식 체크 */
		String emlPattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
		Matcher emlM = Pattern.compile(emlPattern).matcher(joinSaveReq.getEmlAddrVal());
		if( ! emlM.find() ) {
			throw new CustomMessageException("result-message.messages.join.message.email.pattern.error"); // '이메일을 형식에 맞게 입력해 주세요.'
		}

	}

	/*****************************************************
	 * [회원 가입] > 회원 정보 저장
	 * @param request
	 * @param joinSaveReq
	 * @param session
	 * @param arrChkTrmsSn
	 * @return
	*****************************************************/
	@SuppressWarnings("unchecked")
	public JoinSaveRes saveUserJoinInfo( HttpServletRequest request, JoinSaveReq joinSaveReq, HttpSession session ) throws Exception {

		// [0]. 값 셋팅 및 유효성 검사
		String 		strSaveErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		JoinSaveRes joinSaveRes = new JoinSaveRes();
		String		strUserTyCd = joinSaveReq.getUserTyCd(); // 현재 가입하는 회원의 회원 유형
		int			nSaveCount	= 0;

		/** ==================== [0]. (공통) 아이디 및 패스워드 검사와 유효성 검사 ==================== */
		// 0_0. 아이디 및 비밀번호 정책에 맞는 유효성 검사
		this.saveUserJoinInfoIdPwdValidation(joinSaveReq);

		// 0_1. 아이디 중복 재검사
		JoinSelectReq joinSelectReq = new JoinSelectReq();
		joinSelectReq.setTargetUserId( joinSaveReq.getUserId() );

		// 0_2. 중복 체크
		int nDpcnCnt = joinMapper.selectIdDpcnChkCnt(joinSelectReq);
		if( nDpcnCnt > 0 ) {
			strSaveErrMsgCode = "result-message.messages.join.message.id.use.already"; // 이미 사용 중인 아이디입니다.
			throw new CustomMessageException(strSaveErrMsgCode);
		}
		/** ============================== [0] ============================== */

		/** ==================== [1]. (공통) 동의 약관 내용 저장 ==================== */
		// 1_0. 약관 동의 이력 정보 저장
		Integer[] nChkTrmsNo =  joinSaveReq.getArrChkTrmsSn();

		if( nChkTrmsNo != null && nChkTrmsNo.length > 0 ) {
			// 1_1. 약관 동의 저장 정보 값 셋팅
			JoinSaveReq trmsJoinSaveReq = new JoinSaveReq();
			trmsJoinSaveReq.setUserId( joinSaveReq.getUserId() );	// 아이디 셋팅
			trmsJoinSaveReq.setCrtrId( joinSaveReq.getUserId() );	// 등록자 셋팅

			for( int nLoop = 0; nLoop < nChkTrmsNo.length; nLoop++ ) {
				if( nChkTrmsNo[nLoop] == null ) continue;
				// 1_2. 동의한 약관 번호 셋팅
				trmsJoinSaveReq.setTrmsSn( nChkTrmsNo[nLoop] );
				// 1_3. 약관 정보 저장
				nSaveCount = joinMapper.insertTrmsAgreHist(trmsJoinSaveReq);
			}
		}

		if(! (nSaveCount > 0 ) ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ============================== [1] ============================== */

		/** ====================== [2]. 회원(유형 별) 정보 저장 ====================== */
		// 2_0. 비밀번호 암호화
		String strApiUserPwd = joinSaveReq.getUserPwd();
		String encUserPwd = StringUtil.nvl(EncryptUtil.encryptSha256( joinSaveReq.getUserPwd(), joinSaveReq.getUserId() ));
		joinSaveReq.setUserPwd(encUserPwd);

		// 2_1. 휴대폰 번호 암호화
		String strEncTelNo = StringUtil.nvl(EncryptUtil.encryptAes256( joinSaveReq.getMblTelnoVal() ));
		joinSaveReq.setEncptMblTelnoVal(strEncTelNo);

		// [!!] 휴대폰 번호 수정 시, 세션에 담긴 인증 정보와 일치하지 않는 경우 [!!] > 이상 접근 - 마스터 계정 제외
		if( Const.Code.UserTyCd.ENT_GENERAL.equals(strUserTyCd) || Const.Code.UserTyCd.INST_GENERAL.equals(strUserTyCd) || Const.Code.UserTyCd.GENERAL.equals(strUserTyCd) ) {

			// 세션에 담긴 nice 인증 정보
			Map<String, Object> mapNice = (Map<String, Object>) session.getAttribute("niceInfo");

			if( ! "".equals(joinSaveReq.getUserLinkInfoVal()) && mapNice != null ) {
				// [1_0_0]. [휴대폰 번호] 정보 미 일치
				if( ! mapNice.get("MOBILE_NO").equals( joinSaveReq.getMblTelnoVal().replaceAll("-", "") ) ) {
					throw new CustomMessageException("result-message.messages.common.message.wrong.appr"); // 잘못된 접근입니다.
				}
				// [1_0_1]. [CI] 정보 미 일치
				if( ! mapNice.get("CI").equals( joinSaveReq.getUserLinkInfoVal() ) ) {
					throw new CustomMessageException("result-message.messages.common.message.wrong.appr"); // 잘못된 접근입니다.
				}
				// [1_0_2]. [이름] 정보 미 일치
				if( ! mapNice.get("NAME").equals( joinSaveReq.getUserNm() ) ) {
					throw new CustomMessageException("result-message.messages.common.message.wrong.appr"); // 잘못된 접근입니다.
				}
			}
		}

		// 2_2. 이메일 주소 암호화
		String strApiEmailAddr = joinSaveReq.getEmlAddrVal();
		String strEncemlAddr = StringUtil.nvl(EncryptUtil.encryptAes256( joinSaveReq.getEmlAddrVal() ));
		joinSaveReq.setEncptEmlAddrVal(strEncemlAddr);

		// 2_3. 권한관리그룹번호 조회 후 값 셋팅
		String strAuthrtGrpSn = joinMapper.selectAuthrtGrpSn(joinSaveReq);
		joinSaveReq.setAuthrtGrpSn( Integer.parseInt(strAuthrtGrpSn) );

		joinSaveReq.setCrtrId( joinSaveReq.getUserId() );

		// 2_4. 회원 별 정보 저장 시작
		if( Const.Code.UserTyCd.INST_GENERAL.equals(strUserTyCd) ) {
			// 2_4_0. 기관 일반(MTC002) 정보 저장
			joinSaveRes = this.saveInstGnrlUserInfo(joinSaveReq);

		} else if( Const.Code.UserTyCd.ENT_MASTER.equals(strUserTyCd) ) {
			// 2_4_1. 기업 마스터(MTC003) 정보 저장
			joinSaveRes = this.saveEntMasterUserInfo(joinSaveReq);

		} else if( Const.Code.UserTyCd.ENT_GENERAL.equals(strUserTyCd) ) {
			// 2_4_2. 기업 일반(MTC004) 정보 저장
			joinSaveRes = this.saveEntGnrlUserInfo(joinSaveReq);

		} else if( Const.Code.UserTyCd.GENERAL.equals(strUserTyCd) ) {
			// 2_4_3. 일반(MTC005) 정보 저장
			joinSaveRes = this.saveGnrlUserInfo(joinSaveReq);

		} else {
			// 2_4_4. 유형 없음 - 오류
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ============================== [2] ============================== */

		/** ====================== [3]. 관심 키워드(1,2) 저장 ====================== */
		// 3_0. 관심키워드 1 저장 시작
		String		stdLgclfCd1 	= joinSaveReq.getStdLgclfCd1(); 	// 관심키워드 1 - 선택한 대분류 값
		String[]	arrStdMlclfCd1 	= joinSaveReq.getArrStdMlclfCd1(); 	// 관심키워드 1 - 선택한 중분류 값

		if( ! "".equals(stdLgclfCd1) && stdLgclfCd1 != null ) {
			if( arrStdMlclfCd1 != null  && arrStdMlclfCd1.length > 0 ) {
				// 3_0_0. 관심키워드 선택한 내용 있는 경우
				nSaveCount = this.saveUserPrdtClfChcMng(stdLgclfCd1, arrStdMlclfCd1, joinSaveReq);

				if(! (nSaveCount > 0 ) ) {
					throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
				}
			}
		}

		// 3_1. 관심키워드 2 저장 시작
		String		stdLgclfCd2 	= joinSaveReq.getStdLgclfCd2(); 	// 관심키워드 2 - 선택한 대분류 값
		String[]	arrStdMlclfCd2 	= joinSaveReq.getArrStdMlclfCd2(); 	// 관심키워드 2 - 선택한 중분류 값

		if( ! "".equals(stdLgclfCd2) && stdLgclfCd2 != null ) {
			if( arrStdMlclfCd2 != null  && arrStdMlclfCd2.length > 0 ) {
				// 3_0_1. 관심키워드 선택한 내용 있는 경우
				nSaveCount = this.saveUserPrdtClfChcMng(stdLgclfCd2, arrStdMlclfCd2, joinSaveReq);

				if(! (nSaveCount > 0 ) ) {
					throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
				}
			}
		}
		/** ============================== [3] ============================== */

		/** ====================== [4]. 파일 정보 저장 ====================== */
		// 4_0. 물리저장 + 파일dto 파일 목록
		List<FileDto> listFileDto = fileService.savePhyFileReturnFileList(request, "");

		// 4_1. 파일테이블 저장[tb_file_mng]
		if(listFileDto.size() != 0) {

			for( FileDto tmpfileDto : listFileDto ) {
				tmpfileDto.setCrtrId( joinSaveReq.getUserId() );
			}

			// 3_2. 파일 DB 저장
			fileService.saveDBFile( joinSaveReq.getUserId(), listFileDto );
		}
		/** ============================== [4] ============================== */

		/** ====================== [5]. [기관(일반)] -> 데이터 분석환경 API 호출( USER 생성 ) ====================== */
		if( Const.Code.UserTyCd.INST_GENERAL.equals(strUserTyCd) ) {

			// [5_0]. API 요청 값 셋팅
			Map<String, Object> mapRequestData = new HashMap<String, Object>();
			Map<String, Object> mapResponseData = new HashMap<String, Object>();

			mapRequestData.put("userName"	, joinSaveReq.getUserId() );
			mapRequestData.put("password"	, strApiUserPwd );
			mapRequestData.put("email"		, strApiEmailAddr );

			// [5_1]. USER 생성 API 호출
			mapResponseData = anlsApiService.apiConnectionUserAddInit(mapRequestData);

			if( mapResponseData == null || mapResponseData.isEmpty() ) {
				throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}

			// [5_2]. API 응답 id 값 DB 저장
			Integer nAnlsEnvUserId = (Integer) mapResponseData.get("id");
			joinSaveReq.setAnlsEnvUserId( Integer.toString(nAnlsEnvUserId) );
			nSaveCount = joinMapper.updateAnslEnvUserId(joinSaveReq);

			if(! (nSaveCount > 0 ) ) {
				throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}

		}
		/** =============================================== [5] ===============================================*/

		/** ====================== [6]. 회원(유형 별) 가입 관련 메일 전송 ====================== */
		// 6_0. 메일 전송값 셋팅
		Map<String, Object> mapMail		 = new HashMap<String, Object>();
		boolean 			bMailSuccess = false;

		// 6_1. 회원 유형 별 메일값 셋팅
		if( Const.Code.UserTyCd.INST_GENERAL.equals(strUserTyCd) ) {

			// 6_1_0. 기관 일반(MTC002) -> 해당 기관 마스터에게 메일 전송
			mapMail.put("mailTmepCd"	, Const.Code.MailTemplateCd.MEM_JOIN_APPLY_COMP_INST_GENERAL); // EMT07 - 기관일반회원가입승인요청
			mapMail.put("mailSubject"	, "시험인증 빅데이터 가입 승인 요청 안내");

			// 6_1_1. 해당 기관 마스터 메일 주소 조회 후 셋팅( 정상,휴면 상태인 마스터 계정 - 가입신청x, 탈퇴x)
			String strInstMasEncEmlAddr = joinMapper.selectInstMasterEmailAddr(joinSaveReq);

			// 6_1_2. 조회한 메일 주소 없는 경우
			if( "".equals(strInstMasEncEmlAddr) ) {
				throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}

			// 6_1_3. 조회한 이메일 주소 복호화
			String strInstMasEmlAddr = StringUtil.nvl(EncryptUtil.decryptAes256( strInstMasEncEmlAddr ));
			mapMail.put("mailReceiver", strInstMasEmlAddr);

		} else if( Const.Code.UserTyCd.ENT_MASTER.equals(strUserTyCd) ) {

			// 6_2_0. 기업 마스터(MTC003) -> 관리자에게 메일 전송
			mapMail.put("mailTmepCd"	, Const.Code.MailTemplateCd.MEM_JOIN_APPLY_COMP_ENT_MASTER); // EMT08 - 기업마스터회원가입승인요청
			mapMail.put("mailSubject"	, "시험인증 빅데이터 가입 승인 요청 안내");

			// 6_2_1. 관리자 메일 주소 조회
			joinSaveReq.setEmlCode( Const.Code.MailTemplateCd.MEM_JOIN_APPLY_COMP_ENT_MASTER );
			String strAdminEmlAddr = joinMapper.selectEmtAdminEmailAddr(joinSaveReq);
			mapMail.put("mailReceiver"	, strAdminEmlAddr );

		} else if( Const.Code.UserTyCd.ENT_GENERAL.equals(strUserTyCd) ) {

			// 6_3_0. 기업 일반(MTC004) -> 해당 기업 마스터에게 메일 전송
			mapMail.put("mailTmepCd"	, Const.Code.MailTemplateCd.MEM_JOIN_APPLY_COMP_ENT_GENERAL); // EMT06 - 기업일반회원가입승인요청
			mapMail.put("mailSubject"	, "시험인증 빅데이터 가입 승인 요청 안내");

			// 6_3_1. 해당 기업 마스터 메일 주소 조회 후 셋팅( 정상,휴면 상태인 마스터 계정 - 가입신청x, 탈퇴x )
			String strEntMasEncEmlAddr = joinMapper.selectEntMasterEmailAddr(joinSaveReq);

			// 6_3_2. 조회한 메일 주소 없는 경우
			if( "".equals(strEntMasEncEmlAddr) ) {
				throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}

			// 6_3_3. 조회한 이메일 주소 복호화
			String strEntMasEmlAddr = StringUtil.nvl(EncryptUtil.decryptAes256( strEntMasEncEmlAddr ));
			mapMail.put("mailReceiver", strEntMasEmlAddr);

		} else if( Const.Code.UserTyCd.GENERAL.equals(strUserTyCd) ) {

			// 6_4_3. 일반(MTC005) -> 해당 가입한 개인 회원에게 메일 전송
			mapMail.put("mailTmepCd"	, Const.Code.MailTemplateCd.MEM_REGI_COMP_GENERAL); // EMT002 - 개인회원가입완료
			mapMail.put("mailSubject"	, "시험인증 빅데이터 가입 완료 안내");
			mapMail.put("mailReceiver"	, joinSaveReq.getEmlAddrVal() );

		}

		// 6_5. 메일 전송
		bMailSuccess = this.sendJoinUserMail(request, joinSaveReq, mapMail);

		if( !bMailSuccess ) {
			throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
		}
		/** ============================== [6] ============================== */

		/** ====================== [7]. 반환값 셋팅 ====================== */
		joinSaveRes.setUserTyCd( joinSaveReq.getUserTyCd() ); // 회원 유형 셋팅
		/** ========================= [7] ========================= */

		return joinSaveRes;
	}

	/*****************************************************
	 * [회원 가입] > 일반(MTC005) 정보 저장
	 * @param joinSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public JoinSaveRes saveGnrlUserInfo(JoinSaveReq joinSaveReq) throws Exception {

		String 		strSaveErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		JoinSaveRes joinSaveRes = new JoinSaveRes();
		int 		nSaveCount	= 0;

		/** ====================== [0]. 회원 정보 저장 ====================== */
		if( joinSaveReq != null ) {

			// 0_0. 저장 정보 셋팅
			joinSaveReq.setJoinAplyPrcrId( joinSaveReq.getUserId() );
			// 0_1. 일반 회원 상태값 > 정상(USC020) 처리
			joinSaveReq.setUserSttCd( Const.Code.UserSttCd.NORMAL );

			// 0_2. 회원 정보 등록 시작
			nSaveCount = joinMapper.insertUserBas(joinSaveReq);

			if(! (nSaveCount > 0 ) ) {
				throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}

		} else {
			strSaveErrMsgCode = "result-message.messages.common.message.no.data"; // 데이터가 없습니다.
			throw new CustomMessageException(strSaveErrMsgCode);
		}
		/** ============================ [0] ============================ */

		/** ====================== [1]. 반환값 셋팅 ====================== */
		joinSaveRes.setUserId( joinSaveReq.getUserId() ); // 아이디 셋팅
		/** ============================ [1] ============================ */

		return joinSaveRes;

	}

	/*****************************************************
	 * [회원 가입] > 기업 일반(MTC004) 정보 저장
	 * @param joinSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public JoinSaveRes saveEntGnrlUserInfo(JoinSaveReq joinSaveReq) throws Exception {

		String 		strSaveErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		JoinSaveRes joinSaveRes = new JoinSaveRes();
		int 		nSaveCount	= 0;

		/** ====================== [0]. 회원 정보 저장 ====================== */
		if( joinSaveReq != null ) {

			// 0_0. 저장 정보 셋팅
			// 0_1. 기업(일반) 회원 상태값 > 가입 신청(USC011) 처리
			joinSaveReq.setUserSttCd( Const.Code.UserSttCd.JOIN_APPLY );

			// 0_2. 회원 정보 등록 시작
			nSaveCount = joinMapper.insertUserBas(joinSaveReq);

			if(! (nSaveCount > 0 ) ) {
				throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}

			// 0_3. 기업 사용자 정보 등록 시작
			nSaveCount = joinMapper.insertEntGrpUserMng(joinSaveReq);

			if(! (nSaveCount > 0 ) ) {
				throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}

		} else {
			strSaveErrMsgCode = "result-message.messages.common.message.no.data"; // 데이터가 없습니다.
			throw new CustomMessageException(strSaveErrMsgCode);
		}
		/** ============================ [0] ============================ */

		/** ====================== [1]. 반환값 셋팅 ====================== */
		joinSaveRes.setUserId( joinSaveReq.getUserId() ); // 아이디 셋팅
		/** ============================ [1] ============================ */

		return joinSaveRes;

	}

	/*****************************************************
	 * [회원 가입] > 기업 마스터(MTC003) 정보 저장
	 * @param joinSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public JoinSaveRes saveEntMasterUserInfo(JoinSaveReq joinSaveReq) throws Exception {

		String 		strSaveErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		JoinSaveRes joinSaveRes = new JoinSaveRes();
		int 		nSaveCount	= 0;

		/** ====================== [0]. 회원 정보 저장 ====================== */
		if( joinSaveReq != null ) {

			// 0_1. 기업(마스터) 회원 상태값 > 가입 신청(USC011) 처리
			joinSaveReq.setUserSttCd( Const.Code.UserSttCd.JOIN_APPLY );

			// 0_2. 회원 정보 등록 시작
			nSaveCount = joinMapper.insertUserBas(joinSaveReq);
			if(! (nSaveCount > 0 ) ) {
				throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}

			// 0_3. 기업 그룹 정보 등록 시작 - 기업 그룹 등록 전 예, 아니오인 경우 둘다 중복 체크 다시하기
			JoinSelectReq joinSelectReq = new JoinSelectReq();
			String strEntGrpAddChkYn = joinSaveReq.getEntGrpAddChkYn().toUpperCase();

			joinSelectReq.setBrno( joinSaveReq.getBrno() );

			if( "Y".equals(strEntGrpAddChkYn) ) {
				// 0_3_0. 그룹 등록 (예)
				joinSelectReq.setEntGrpMngNo( joinSaveReq.getEntGrpMngNo() );
				joinSelectReq.setEntGrpNm( joinSaveReq.getEntGrpNm() );
			} else {
				// 0_3_1. 그룹 등록 (아니오)
				joinSelectReq.setEntGrpMngNo( "9999999999" );
				joinSelectReq.setEntGrpNm( "그룹미사용" );
			}

			// 0_4. 사업자등록번호 + 그룹명 + 그룹ID 중복 체크
			int nEntGrpDpcnChkCnt = joinMapper.selectEntGrpDpcnChkCnt(joinSelectReq);

			if( nEntGrpDpcnChkCnt > 0 ) {
				strSaveErrMsgCode = "result-message.messages.join.message.ent.grp.use.already"; // 이미 사용 중인 그룹정보 입니다. 다시 확인해 주세요.
				throw new CustomMessageException(strSaveErrMsgCode);
			} else {

				// 0_4_1. 기업 정보 등록 시작 - 기존 사업자 등록번호가 있는 경우, 따로 등록하지 않는다.
				int nEntBrnoChkCnt = joinMapper.selectEntBrnoChkCnt(joinSelectReq);

				if( ! (nEntBrnoChkCnt > 0) ) {
					// 0_4_1_0. 해당 사업자 등록번호 DB 없는 경우 등록
					nSaveCount = joinMapper.insertEntBas(joinSaveReq);

					if(! (nSaveCount > 0 ) ) {
						throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
					}
				}

				// 0_4_2. 기업 그룹 정보 등록 시작
				nSaveCount = joinMapper.insertEntGrpBas(joinSaveReq);
			}

			// 0_5. 기업 사용자 정보 등록
			if( joinSaveReq.getEntGrpSn() != null ) {
				nSaveCount = joinMapper.insertEntGrpUserMng(joinSaveReq);
			} else {
				strSaveErrMsgCode = "result-message.messages.common.message.no.data"; // 데이터가 없습니다.
				throw new CustomMessageException(strSaveErrMsgCode);
			}

			if(! (nSaveCount > 0 ) ) {
				throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}

		} else {
			strSaveErrMsgCode = "result-message.messages.common.message.no.data"; // 데이터가 없습니다.
			throw new CustomMessageException(strSaveErrMsgCode);
		}
		/** ============================ [0] ============================ */

		/** ====================== [1]. 반환값 셋팅 ====================== */
		joinSaveRes.setUserId( joinSaveReq.getUserId() ); // 아이디 셋팅
		/** ============================ [1] ============================ */

		return joinSaveRes;

	}

	/*****************************************************
	 * [회원 가입] > 기관 일반(MTC002) 정보 저장
	 * @param joinSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public JoinSaveRes saveInstGnrlUserInfo(JoinSaveReq joinSaveReq) throws Exception {

		String 		strSaveErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		JoinSaveRes joinSaveRes = new JoinSaveRes();
		int 		nSaveCount	= 0;

		/** ====================== [0]. 회원 정보 저장 ====================== */
		if( joinSaveReq != null ) {

			// 0_0. 저장 정보 셋팅
			// 0_1. 기관(일반) 회원 상태값 > 가입 신청(USC011) 처리
			joinSaveReq.setUserSttCd( Const.Code.UserSttCd.JOIN_APPLY );

			// 0_2. 회원 정보 등록 시작
			nSaveCount = joinMapper.insertUserBas(joinSaveReq);

			if(! (nSaveCount > 0 ) ) {
				throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}

			// 0_3. 기관 사용자 정보 등록 시작
			nSaveCount = joinMapper.insertInstUserMng(joinSaveReq);

			if(! (nSaveCount > 0 ) ) {
				throw new CustomMessageException(strSaveErrMsgCode); // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.
			}

		} else {
			strSaveErrMsgCode = "result-message.messages.common.message.no.data"; // 데이터가 없습니다.
			throw new CustomMessageException(strSaveErrMsgCode);
		}
		/** ============================ [0] ============================ */

		/** ====================== [1]. 반환값 셋팅 ====================== */
		joinSaveRes.setUserId( joinSaveReq.getUserId() ); // 아이디 셋팅
		/** ============================ [1] ============================ */

		return joinSaveRes;

	}

	/*****************************************************
	 * [회원 가입] > 관심 키워드 저장
	 * @param stdLgclfCd
	 * @param arrStdMlclfCd
	 * @param joinSaveReq
	 * @return
	 * @throws Exception
	*****************************************************/
	public int saveUserPrdtClfChcMng( String stdLgclfCd, String[] arrStdMlclfCd, JoinSaveReq joinSaveReq ) throws Exception {

		String strSavetErrMsgCode = "result-message.messages.common.message.save.error"; // 저장 중 오류가 발생하였습니다. 관리자에게 문의하여 주십시오.

		int nSaveCount = 0;

		if( stdLgclfCd != null && !"".equals(stdLgclfCd) ) {

			// 0. 관심 키워드 - 선택된 관심 키워드 있는 경우
			JoinSaveReq prdtJoinSaveReq = new JoinSaveReq();

			prdtJoinSaveReq.setUserId( joinSaveReq.getUserId() );	// 아이디 셋팅
			prdtJoinSaveReq.setCrtrId( joinSaveReq.getUserId() );	// 등록자 셋팅
			prdtJoinSaveReq.setStdLgclfCd( stdLgclfCd ); 			// 대분류 셋팅

			// 1. 관심 키워드 - 선택한 중분류 값
			for( int nLoop = 0; nLoop < arrStdMlclfCd.length; nLoop++ ) {

				if( "".equals(arrStdMlclfCd[nLoop]) || arrStdMlclfCd[nLoop] == null ) continue;
				prdtJoinSaveReq.setSrtSeq( nLoop + 1 );
				prdtJoinSaveReq.setStdMlclfCd( arrStdMlclfCd[nLoop] ); // 중분류 값 셋팅
				nSaveCount = joinMapper.insertUserPrdtClfChcMng(prdtJoinSaveReq);

				if(nSaveCount == 0) {
					throw new CustomMessageException(strSavetErrMsgCode);
				}
			}

			nSaveCount = 1;
		}

		return nSaveCount;
	}

	/*****************************************************
	 * [회원 가입] > 회원(유형 별) 메일 전송
	 * @param request
	 * @param joinSaveReq
	 * @param mapMail
	 * @return
	*****************************************************/
	public boolean sendJoinUserMail(HttpServletRequest request, JoinSaveReq joinSaveReq, Map<String, Object> mapMail) throws Exception {
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
		mailParam.put( "joinYmd", StringUtil.stringFormatType("today", "date", "YYYY-MM-DD") );
		mailParam.put( "userId" , StringUtil.stringFormatType( joinSaveReq.getUserId() , "masking" , "id") ); // 마스킹 처리

		// 1-2. mail 템플릿 및 제목 셋팅
		String tempCd	= (String) mapMail.get("mailTmepCd");
		String subject	= (String) mapMail.get("mailSubject");
		String receiver	= (String) mapMail.get("mailReceiver");

		// 1-3. body
		MailMakeBodyDto mailMakeBodyDto = new MailMakeBodyDto();
		mailMakeBodyDto.setMailTmepCd( tempCd ); 	// 메일 template 공통코드 - 기업마스터회원가입승인완료
		mailMakeBodyDto.setMailParam(mailParam); 	//메일 param (메일내용에 들어갈 param들)
		String sBody = mailService.makeMailBody(request, mailMakeBodyDto);

		/* [[2]]. 메일보내기 */
		if(! "".equals(sBody)) {

			// 2-1. 수신자 LIST
			List<MailSendReceiverDto> list_resiver = new ArrayList<MailSendReceiverDto>();
			list_resiver.add( new MailSendReceiverDto( receiver ));

			// 2-2. DTO생성 후 메일 보내기
			MailSendDto mailSendDto = new MailSendDto();
			mailSendDto.setSubject( subject );
			mailSendDto.setBody(sBody);
			mailSendDto.setReceiverList(list_resiver);
			success = mailService.sendMail(mailSendDto);
		}

		return success;
	}

	/*****************************************************
	 * [회원 가입] > 기업 그룹 목록 조회
	 * @param joinSelectReq
	 * @return
	*****************************************************/
	public List<JoinSelectRes> getEntGrpList(JoinSelectReq joinSelectReq) {

		List<JoinSelectRes> lstEntGrp = null;

		// 0. 기업 그룹 목록 조회
		lstEntGrp = joinMapper.selectEntGrpList();

		return lstEntGrp;
	}

	/*****************************************************
	 * [회원 가입] > 해당 사업자 등록번호로 상호명 조회
	 * @param joinSelectReq
	 * @return
	*****************************************************/
	public String getBrnoEntNm(JoinSelectReq joinSelectReq) throws Exception {

		String strEntNm = "";

		// 0. 상호명 조회
		strEntNm = joinMapper.selectBrnoEntNm(joinSelectReq);

		return strEntNm;

	}

	/*****************************************************
	 * [회원 가입] > 사업자등록번호 + 그룹ID + 그룹명 중복 체크 검사
	 * @param joinSelectReq
	 * @return
	*****************************************************/
	public int getEntGrpDpcnChkCnt(JoinSelectReq joinSelectReq) throws Exception {

		int nDpcnCnt = 0;

		// 1. 사업자등록번호 + 그룹ID + 그룹명 중복 조회
		nDpcnCnt = joinMapper.selectEntGrpDpcnChkCnt(joinSelectReq);

		return nDpcnCnt;
	}

	/*****************************************************
	 *  [회원 가입] > 기관 목록 조회
	 * @param joinSelectReq
	 * @return
	*****************************************************/
	public List<JoinSelectRes> getInstList(JoinSelectReq joinSelectReq) throws Exception {
		List<JoinSelectRes> lstInst = null;

		// 0. 기관 목록 조회
		lstInst = joinMapper.selectInstList();

		if( lstInst == null ) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return lstInst;
	}

	/*****************************************************
	 * [회원 가입] > 관심 키워드 대분류 목록 조회
	 * @return
	*****************************************************/
	public List<JoinPrdtSelectRes> getStdLgclfCdList() throws Exception {
		List<JoinPrdtSelectRes> lstPrdt = null;

		// 0. 관심 키워드 대분류 목록 조회
		lstPrdt = joinMapper.selectStdLgclfCd();

		if( lstPrdt == null ) {
			throw new CustomMessageException("result-message.messages.common.message.no.data"); // 데이터가 없습니다.
		}

		return lstPrdt;
	}

	/*****************************************************
	 * [회원 가입] > 관심 키워드 중분류 목록 조회
	 * @param commReq
	 * @return
	*****************************************************/
	public List<JoinPrdtSelectRes> getStdMlclfCdList(CommReq commReq) {
		List<JoinPrdtSelectRes> lstPrdt = null;

		// 0. 관심 키워드 중분류 목록 조회
		lstPrdt = joinMapper.selectStdMlclfCdList(commReq);

		return lstPrdt;
	}

	/*****************************************************
	 * [회원 가입] > 연계 정보 중복 체크
	 * @param joinSelectReq
	 * @return
	*****************************************************/
	public int getUserLinkInfoDpcnChkCnt(JoinSelectReq joinSelectReq) throws Exception {

		int nDpcnCnt = 0;

		// 0. 중복 조회
		nDpcnCnt = joinMapper.selectUserLinkInfoDpcnChkCnt(joinSelectReq);

		return nDpcnCnt;

	}

}

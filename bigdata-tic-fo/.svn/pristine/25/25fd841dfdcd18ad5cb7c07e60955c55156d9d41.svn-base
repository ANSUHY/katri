package com.katri.web.comm.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.common.Const;
import com.katri.common.util.SessionUtil;
import com.katri.web.comm.mapper.CommMapper;
import com.katri.web.comm.model.CommReq;
import com.katri.web.comm.model.CommRes;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
@Api(tags = {"공통 Service"})
public class CommService {
	private final CommMapper commMapper;

	/** 메시지 Component */
	private final MessageSource messageSource;

	/*****************************************************
	 * 공통 코드
	 * @param HashMap<String, Object> 검색조건
	 * @return 로그인 정보
	 *****************************************************/
	public List<CommRes> selectCode(CommReq res) {
		return this.commMapper.selectCode(res);
	}

	/*****************************************************
	 * 공통 코드
	 * @param commReq
	 * @return
	*****************************************************/
	public CommRes selectCertRelCode(CommReq commReq) {
		return this.commMapper.selectCertRelCode(commReq);
	}

	/*****************************************************
	 * 공통 코드
	 * @param commReq
	 * @return
	 *****************************************************/
	public List<CommRes> selectUpGrpCode(CommReq commReq) {
		return this.commMapper.selectUpGrpCode(commReq);
	}

	/*****************************************************
	 * 메세지 반환 ( 맞는 값이 없으면 ERROR 관리자에게 문의해주세요 셋팅)<br>
	 * @param strMsgCd
	 * @return String 메시지
	*****************************************************/
	public String rtnMessageDfError(String strMsgCd) {

		String[] arrMsgArgu = null;
		if(strMsgCd.contains("||msgArgu=") ) {//메시지 아규먼트가 있을경우

			String separator	= "||msgArgu=";
			String separator2	= "||";

			int idx = strMsgCd.indexOf(separator);
			int inx2 = strMsgCd.substring(idx+separator.length()).indexOf(separator2);

			String msgArgu = strMsgCd.substring(idx+separator.length(), idx + separator.length() + inx2);
			strMsgCd = (strMsgCd.substring(0, idx) + strMsgCd.substring(idx + separator.length() + inx2 + separator2.length()));

			arrMsgArgu = msgArgu.split(",");
		}

		String rtnMsg = messageSource.getMessage(strMsgCd, arrMsgArgu, messageSource.getMessage("result-message.messages.common.message.error", null, SessionUtil.getLocale()), SessionUtil.getLocale());
		return rtnMsg;
	}

	/*****************************************************
	 * 로그인 체크<br>
	 * @param session
	 * @return 로그인이 되어있으면 true / 안되어있으면 false
	*****************************************************/
	public boolean loginChk(HttpSession session) {

		boolean loginChk = false;

		String serverClsCd = Const.Session.SERVER_CLS_CD_FO;

		if(session.getAttribute(SessionUtil.getSessionKey() + serverClsCd) != null
				&& SessionUtil.getLoginUserId() != null && !"".equals(SessionUtil.getLoginUserId() )) {

			loginChk = true;

		}

		return loginChk;

	}

}

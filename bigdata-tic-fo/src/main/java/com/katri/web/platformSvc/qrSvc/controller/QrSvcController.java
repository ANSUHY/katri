package com.katri.web.platformSvc.qrSvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.katri.common.Const;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.CommonUtil;
import com.katri.web.comm.service.CommService;
import com.katri.web.platformSvc.qrSvc.model.QrSvcSelectReq;
import com.katri.web.platformSvc.qrSvc.model.QrSvcSelectRes;
import com.katri.web.platformSvc.qrSvc.service.QrSvcService;
import com.nhncorp.lucy.security.xss.XssPreventer;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequiredArgsConstructor
@Api(tags = {"[플랫폼 서비스] > [통합QR 서비스 안내] Controller"})
@Slf4j
public class QrSvcController {

	/** 공통 Service */
	private final CommService commService;

	/** [플랫폼 서비스] > [통합QR 서비스 안내] Service */
	private final QrSvcService qrSvcService;

	/*****************************************************
	 * [플랫폼 서비스] > [통합QR 서비스 안내] _페이지 + 데이터
	 * @param request
	 * @param response
	 * @param model
	 * @param qrSvcSelectReq
	 * @return 페이지
	 * @throws Exception
	*****************************************************/
	@RequestMapping(value = {"/platformSvc/qrSvc/qrSvcGuide", "/platformSvc/qrSvc/previewQrSvcGuide"}, method = {RequestMethod.GET, RequestMethod.POST} )
	public String qrSvcGuide(HttpServletRequest request, HttpServletResponse response, Model model, QrSvcSelectReq qrSvcSelectReq) throws Exception {

		// [[0]]. 반환할 정보들 & 변수 지정
		QrSvcSelectRes qrSvcData		= new QrSvcSelectRes(); //페이지콘텐츠 정보

		try {

			// [[1]]. [플랫폼 서비스] > [통합QR 서비스 안내] 각 페이지에 맞는 데이터 셋팅
			if("/platformSvc/qrSvc/previewQrSvcGuide".equals(request.getRequestURI()) ) { //1-t1. BO에서 [미리보기] 할 경우

				// 1-t1-1.[플랫폼 서비스] > [통합QR 서비스 안내] 데이터 + 내용 UNESCAPE
				String previewMenuCptnCn = qrSvcSelectReq.getPreviewMenuCptnCn();
				qrSvcData.setMenuCptnCnUnescape(XssPreventer.unescape(previewMenuCptnCn));

			} else { //1-t2. 메뉴에서 들어갈 경우

				// 1-t2-1.[플랫폼 서비스] > [통합QR 서비스 안내] 각 페이지에 맞는 데이터 조회 + 내용 UNESCAPE
				qrSvcSelectReq.setSearchMenuCptnCd(Const.Code.MenuCompositionCode.SERVICE_QR_SERVICE); //플랫폼 서비스 > 인증QR 서비스 안내
				qrSvcData = qrSvcService.getQrSvcData(qrSvcSelectReq);
			}

		} catch (CustomMessageException e) {

			//값 셋팅
			String msg = commService.rtnMessageDfError(e.getMessage()); //service에서 exception하면서 보낸 메시지

			//로그에 넣기
			log.error("\n===============ERROR=========================\n" + e.getMessage());
			log.error("\n===============ERROR_MSG=====================\n" + msg);

			//ALERT창띄우면서 뒤로 보내기
			CommonUtil.alertMsgBack(response, msg);

		} catch (Exception e) {

			throw e;

		}

		// [[2]]. 데이터 셋팅
		model.addAttribute("qrSvcData"	, qrSvcData);

		return "/platformSvc/qrSvc/qrSvcGuide";

	}

}

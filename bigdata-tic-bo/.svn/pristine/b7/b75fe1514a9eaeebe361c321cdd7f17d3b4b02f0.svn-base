package com.katri.batch.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.batch.mapper.BatchMapper;
import com.katri.batch.model.BatchBeforeDrmncyMemDto;
import com.katri.batch.model.BatchChgDrmncyMemDto;
import com.katri.batch.model.BatchNotiInstEntGrpClctAgreDto;
import com.katri.batch.model.BtchExcnHistSaveReq;
import com.katri.common.Const;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;
import com.katri.common.util.EncryptUtil;
import com.katri.common.util.StringUtil;
import com.katri.web.comm.model.MailMakeBodyDto;
import com.katri.web.comm.model.MailSendDto;
import com.katri.web.comm.model.MailSendReceiverDto;
import com.katri.web.comm.service.MailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class BatchService {

	/** 배치 Mapper */
	private final BatchMapper batchMapper;

	/** 메일 Service */
	private final MailService mailService;

	/** 날짜포맷 */
	private final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/** 예약 메일 한번 보낼때 보낼수 있는 개수 */
	@Value("${mail.reserve.maxcount}")
	int RESERVE_MAX_COUNT;

	/** [휴면회원 전환 30일전 알림 메일] 다음 메일을 예약해야하면 분 텀 */
	@Value("${mail.reserve.term.beforedrmncy}")
	int BEFORE_DRMNCY_NEXT_MAIL_TERM_MIN;

	/** [휴면회원 전환 30일전 알림 메일] 메일 예약시간_시 */
	@Value("${mail.reserve.time.beforedrmncy.hour}")
	int BEFORE_DRMNCY_RESERVE_TIME_HOUR;

	/** [휴면회원 전환 30일전 알림 메일] 메일 예약시간_분 */
	@Value("${mail.reserve.time.beforedrmncy.minute}")
	int BEFORE_DRMNCY_RESERVE_TIME_MIN;

	/** [휴면회원 전환 30일전 알림 메일] 메일 예약시간_초 */
	@Value("${mail.reserve.time.beforedrmncy.minute}")
	int BEFORE_DRMNCY_RESERVE_TIME_SEC;

	/** [기업그룹수집동의여부 기관에게 알림 메일] 메일 예약시간_시 */
	@Value("${mail.reserve.time.instagre.hour}")
	int INST_AGRE_RESERVE_TIME_HOUR;

	/** [기업그룹수집동의여부 기관에게 알림 메일] 메일 예약시간_분 */
	@Value("${mail.reserve.time.instagre.minute}")
	int INST_AGRE_RESERVE_TIME_MIN;

	/** [기업그룹수집동의여부 기관에게 알림 메일] 메일 예약시간_초 */
	@Value("${mail.reserve.time.instagre.minute}")
	int INST_AGRE_RESERVE_TIME_SEC;

	/*****************************************************
	 * [휴면회원 전환] 처리
	 * @throws Exception
	*****************************************************/
	public void processChgDrmncyMem() throws Exception {

		// ========= [[0]]. 변수 지정
		List<BatchChgDrmncyMemDto> lstChgDrmncyMem	= null; //[휴면회원 전환] 처리 필요한 리스트
		BatchChgDrmncyMemDto srchChgDrmncyMem		= new BatchChgDrmncyMemDto(); //[휴면회원 전환] 리스트 조회시 필요한 조건

		// =========  [[1]]. [휴면회원 전환] 처리 필요한 리스트 조회
		lstChgDrmncyMem = batchMapper.selectChgDrmncyMemList(srchChgDrmncyMem);

		// =========  [[2]]. [휴면회원 전환] 처리
		if(lstChgDrmncyMem != null && lstChgDrmncyMem.size() != 0) {
			for(BatchChgDrmncyMemDto chgDrmncyMem : lstChgDrmncyMem) {
				batchMapper.updateChgDrmncyMem(chgDrmncyMem);
			}
		}

	}

	/*****************************************************
	 * [휴면회원 전환 30일전 알림 메일] 처리
	 * @throws Exception
	*****************************************************/
	public void processBeforeDrmncyMem() throws Exception {

		// ========= [[0]]. 변수 지정
		List<BatchBeforeDrmncyMemDto> lstBeforeDrmncyMem	= null; //[휴면회원 전환 30일전 알림 메일] 처리 필요한 멤버 리스트
		BatchBeforeDrmncyMemDto srchBeforeDrmncyMem			= new BatchBeforeDrmncyMemDto(); //[휴면회원 전환 30일전 알림 메일] 리스트 조회시 필요한 조건

		// ========= [[1]]. [휴면회원 전환 30일전 알림 메일] 처리 필요한 리스트 조회
		lstBeforeDrmncyMem = batchMapper.selectBeforeDrmncyMemList(srchBeforeDrmncyMem);

		// ========= [[2]]. 메일 보내기
		if(lstBeforeDrmncyMem != null && lstBeforeDrmncyMem.size() != 0) {

			int mailCount 	= 0; //메일 count

			String decptEmlAddrVal		= ""; //복호화한 메일 주소
			String maskingUserId 		= ""; //마스킹처리한 userId
			Calendar reserveTime		= Calendar.getInstance(); //메일 예약 시간
			reserveTime.set(reserveTime.get(Calendar.YEAR), reserveTime.get(Calendar.MONTH), reserveTime.get(Calendar.DATE), BEFORE_DRMNCY_RESERVE_TIME_HOUR, BEFORE_DRMNCY_RESERVE_TIME_MIN, BEFORE_DRMNCY_RESERVE_TIME_SEC);

			for(BatchBeforeDrmncyMemDto beforeDrmncyMem : lstBeforeDrmncyMem) {

				mailCount ++;

				decptEmlAddrVal = "";
				maskingUserId	= "";

				/** ######## 2-1. 메일 보내기 ################ */
				if( beforeDrmncyMem.getEncptEmlAddrVal() != null && !"".equals(beforeDrmncyMem.getEncptEmlAddrVal())
						&& beforeDrmncyMem.getUserId() != null && !"".equals(beforeDrmncyMem.getUserId()) ) {

					//=====2-1-1. 데이터 셋팅========================================
					// 복호화한 메일 주소
					decptEmlAddrVal = StringUtil.nvl(EncryptUtil.decryptAes256( beforeDrmncyMem.getEncptEmlAddrVal() ));
					// 마스킹처리한 userId
					maskingUserId	= StringUtil.stringFormatType(beforeDrmncyMem.getUserId(), "masking" , "id");

					//=====2-2-2. 메일 body 생성========================================
					// 2-2-2-1. mailParam설정
					Map<String, Object> mailParam = new HashMap<String, Object>();
					mailParam.put("maskingUserId", maskingUserId);
					mailParam.put("eDrmncyDay"	 , beforeDrmncyMem.getEDrmncyDay()); //휴면예정일자

					// 2-2-2-2. body
					MailMakeBodyDto mailMakeBodyDto = new MailMakeBodyDto();
					mailMakeBodyDto.setMailTmepCd(Const.Code.MailTemplateCd.BEFORE_30_DAYS_MEM_DRMNCY); //메일 template 공통코드
					mailMakeBodyDto.setMailParam(mailParam); 	//메일 param (메일내용에 들어갈 param들)
					String sBody = mailService.makeMailBody(null, mailMakeBodyDto);

					//=====2-2-3. 메일 전송========================================
					if(! "".equals(sBody)) {

						// 2-2-3-1. 수신자 LIST
						List<MailSendReceiverDto> list_resiver = new ArrayList<MailSendReceiverDto>();
						list_resiver.add( new MailSendReceiverDto(decptEmlAddrVal)); //복호화한 메일 주소

						// 2-2-3-2. DTO생성 후 메일 보내기
						MailSendDto mailSendDto = new MailSendDto();
						mailSendDto.setSubject("시험인증 빅데이터 플랫폼 휴면계정 전환 안내");
						mailSendDto.setBody(sBody);
						mailSendDto.setReceiverList(list_resiver);
						mailSendDto.setReserveTime(SDF.format(reserveTime.getTime()));
						mailService.sendReserveMail(mailSendDto);

					}

				}

				/** ######## 2-2. 예약 메일 한번 보낼때 보낼수 있는 개수가 넘으면 시간 다시 셋팅 ################  */
				if( mailCount >= RESERVE_MAX_COUNT ) {

					reserveTime.add(Calendar.MINUTE, BEFORE_DRMNCY_NEXT_MAIL_TERM_MIN);
					mailCount = 0;

				}

			}
		}

	}

	/*****************************************************
	 * [기업그룹수집동의여부 기관에게 알림 메일] 처리
	 * @throws Exception
	*****************************************************/
	public String processNotiInstEntGrpClctAgre() throws Exception {

		// ========= [[0]]. 변수 지정
		String errMsg = "";

		List<BatchNotiInstEntGrpClctAgreDto> lstInstMaster = null; //[기업그룹수집동의여부 기관에게 알림 메일] 기관 MASTER 리스트

		// ========= [[1]]. [기업그룹수집동의여부 기관에게 알림 메일] 기관 MASTER 리스트 조회
		lstInstMaster = batchMapper.selectInstMasterList();

		// ========= [[2]]. 메일 보내기
		if(lstInstMaster != null && lstInstMaster.size() != 0) {

			//메일 예약 시간
			Calendar reserveTime = Calendar.getInstance();
			reserveTime.set(reserveTime.get(Calendar.YEAR), reserveTime.get(Calendar.MONTH), reserveTime.get(Calendar.DATE), INST_AGRE_RESERVE_TIME_HOUR, INST_AGRE_RESERVE_TIME_MIN, INST_AGRE_RESERVE_TIME_SEC);
			//어제 날짜
			Calendar cal  		= Calendar.getInstance();
			SimpleDateFormat SDF2 = new SimpleDateFormat("yyyy년 MM월 dd일");
			cal.add(Calendar.DATE, -1);
			String yesterDayFmt = SDF2.format(cal.getTime());

			for(BatchNotiInstEntGrpClctAgreDto instMaster : lstInstMaster) {

				String instId 		= instMaster.getInstId();
				String instNm		= instMaster.getInstNm();
				String mstEncptEmlAddrVal = ""; //MASTER 암호화 이메일주소값
				String mstDecptEmlAddrVal = ""; //MASTER 복호화한 이메일주소값
				List<BatchNotiInstEntGrpClctAgreDto> lstInstEntGrpClctAgre 	= null; //[기업그룹수집동의여부 기관에게 알림 메일] 기관에 보낼 기업그룹수집동의여부리스트
				BatchNotiInstEntGrpClctAgreDto srchInstEntGrpClctAgre 		= new BatchNotiInstEntGrpClctAgreDto(); //[기업그룹수집동의여부 기관에게 알림 메일] 기관에 보낼 기업그룹수집동의여부리스트 조회시 필요한 조건

				try{

					//메일값 있는지 확인
					if( instMaster.getMstEncptEmlAddrVal() == null || "".equals(StringUtil.nvl(instMaster.getMstEncptEmlAddrVal())) ) {
						throw new CustomMessageException(" MASTER 계정 또는 MAIL주소가 없음 ");
					} else {
						mstEncptEmlAddrVal = instMaster.getMstEncptEmlAddrVal();
					}

					/** ######## 2-1. 메일 보내기 ################ */
					//=====2-1-1. 데이터 셋팅========================================
					// 복호화한 메일 주소
					mstDecptEmlAddrVal = StringUtil.nvl(EncryptUtil.decryptAes256( mstEncptEmlAddrVal ));
					// [기업그룹수집동의여부 기관에게 알림 메일] 기관에 보낼 기업그룹수집동의여부리스트 조회
					srchInstEntGrpClctAgre.setInstId(instId);
					lstInstEntGrpClctAgre = batchMapper.selectInstEntGrpClctAgreList(srchInstEntGrpClctAgre);

					//=====2-2-2. 메일 body 생성=====================================
					// 2-2-2-1. mailParam설정
					Map<String, Object> mailParam = new HashMap<String, Object>();
					/* 기업그룹수집동의여부리스트 html */
					String listInstEntGrpClctAgreHtml = "";
					if(lstInstEntGrpClctAgre != null && lstInstEntGrpClctAgre.size() != 0 ) {
						for( BatchNotiInstEntGrpClctAgreDto instEntGrpClctAgre :  lstInstEntGrpClctAgre) {
							listInstEntGrpClctAgreHtml += "<tr>";
							listInstEntGrpClctAgreHtml += "		<td scope=\"row\" style=\"text-align:center;padding:13px 20px;color:#000;font-weight:normal;line-height:1.88\">" + instEntGrpClctAgre.getBrno() + "</td>\n";
							listInstEntGrpClctAgreHtml += "		<td scope=\"row\" style=\"text-align:center;padding:13px 20px;color:#000;font-weight:normal;line-height:1.88\">" + instEntGrpClctAgre.getEntGrpMngNo() + "</td>\n";
							listInstEntGrpClctAgreHtml += "		<td scope=\"row\" style=\"text-align:center;padding:13px 20px;color:#000;font-weight:normal;line-height:1.88\">" + instEntGrpClctAgre.getEntGrpNm() + "</td>\n";
							listInstEntGrpClctAgreHtml += "		<td scope=\"row\" style=\"text-align:center;padding:13px 20px;color:#000;font-weight:normal;line-height:1.88\">" + instEntGrpClctAgre.getInfoPvsnAgreYn() + "</td>\n";
							listInstEntGrpClctAgreHtml += "		<td scope=\"row\" style=\"text-align:center;padding:13px 20px;color:#000;font-weight:normal;line-height:1.88\">" + instEntGrpClctAgre.getLastInfoPvsnAgreYnDtFmt() + "</td>\n";
							listInstEntGrpClctAgreHtml += "</tr>";
						}
					} else {
						listInstEntGrpClctAgreHtml += "<tr>";
						listInstEntGrpClctAgreHtml += "		<td scope=\"row\" style=\"text-align:center;padding:13px 20px;color:#000;font-weight:normal;line-height:1.88\" colspan=\"5\">" + "동의한 기업이 없습니다." + "</td>\n";
					}
					mailParam.put("listInstEntGrpClctAgreHtml"	 , listInstEntGrpClctAgreHtml);
					/* 기관명 */
					mailParam.put("instNm", instNm);
					/* 날짜 */
					mailParam.put("yesterDayFmt", yesterDayFmt);

					// 2-2-2-2. body
					MailMakeBodyDto mailMakeBodyDto = new MailMakeBodyDto();
					mailMakeBodyDto.setMailTmepCd(Const.Code.MailTemplateCd.NOTI_INST_ENT_GRP_CLCT_AGRE); //메일 template 공통코드
					mailMakeBodyDto.setMailParam(mailParam); 	//메일 param (메일내용에 들어갈 param들)
					String sBody = mailService.makeMailBody(null, mailMakeBodyDto);

					//=====2-2-3. 메일 전송=============================================
					if(! "".equals(sBody)) {

						// 2-2-3-1. 수신자 LIST
						List<MailSendReceiverDto> list_resiver = new ArrayList<MailSendReceiverDto>();
						list_resiver.add( new MailSendReceiverDto(mstDecptEmlAddrVal)); //복호화한 메일 주소

						// 2-2-3-2. DTO생성 후 메일 보내기
						boolean mailSuccess = false;
						MailSendDto mailSendDto = new MailSendDto();
						mailSendDto.setSubject( yesterDayFmt + " 기업 수집동의 정보 안내" );
						mailSendDto.setBody(sBody);
						mailSendDto.setReceiverList(list_resiver);
						mailSendDto.setReserveTime(SDF.format(reserveTime.getTime()));
						mailSuccess = mailService.sendReserveMail(mailSendDto);
						if(! mailSuccess) {
							throw new CustomMessageException(" 메일전송중 오류");
						}

					}

				} catch(CustomMessageException e){

					errMsg += (" === " + instId + " : " + instNm + " 오류 ::: " + e.getMessage()) + "\n";

				}

			}
		}

		return errMsg;

	}

	/*****************************************************
	 * 배치실행이력 등록
	 * @param btchExcnHistSaveReq
	*****************************************************/
	public void savetBtchExcnHist(BtchExcnHistSaveReq btchExcnHistSaveReq) {

		batchMapper.insertBtchExcnHist(btchExcnHistSaveReq);

	}

}

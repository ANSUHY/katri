package com.katri.common;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/***************************************************
 * <ul>
 * <li>업무 그룹명  = 공통 상수 Class 업무</li>
 * <li>서브 업무명  = 공통 상수 관련</li>
 * <li>설	 명  = 공통 상수 Class 구현(메세지 아이디, 시스템 구분 등등)</li>
 * <li>작  성  자 : Kim ho jin</li>
 * <li>작  성  일 : 2022. 02. 17.</li>
 * </ul>
 * <pre>
 * ======================================
 * 변경자/변경일  =
 * 변경사유/내역  =
 * ======================================
 * </pre>
 ***************************************************/
@Component
@Aspect
@RequiredArgsConstructor
public class CommonCodeKey {

	public static String instIdKey = "INST03";

//	/** Agent 정보 */
//	public static class Session {
//		public static final String SERVER_CLS_CD_BO = "BO"; // 서버 구분 코드(관리자)
//		public static final String SERVER_CLS_CD_FO = "FO"; // 서버 구분 코드(프론트)
//	}
//
//	/** Agent 정보 */
//	public static class Agent {
//		public static final String MOBILE_CORE_TOKEN = "core_token";
//	}
//
//	/** Paging 정보 */
//	public static class Paging {
//		public static final int ROW_COUNT = 20; // Paging Row Count
//	}
//
//	/** 케릭터셋 */
//	public static class CharacterSet {
//		public static final String EUC_KR 	= "euc-kr";	 // euc-kr
//		public static final String UTF_8 	= "utf-8";	 // utf-8
//	}



//	public static class Code {
//		/**  */
//		public static class ModuleTypeCd {
//			public static final String MODULE 	= "MTC001";	 // Module
//			public static final String BMS	 	= "MTC002";	 // BMS
//		}
//
//		/** 등록방식코드 */
//		public static class RegTypeCd {
//			public static final String MANUAL 	= "RTC001";	 // Manual
//			public static final String EXCEL 	= "RTC002";	 // Excel
//			public static final String PDA	 	= "RTC003";	 // PDA
//		}
//
//		/** 상태코드 */
//		public static class ProcessTypeCd {
//			public static final String WEAR 	= "PSC001";	 // 입고
//			public static final String PROD	 	= "PSC002";	 // 생산
//			public static final String RELE 	= "PSC003";	 // 출고
//			public static final String INST 	= "PSC004";	 // 설치
//		}
//	}
}

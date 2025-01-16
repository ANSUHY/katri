package com.katri.common;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.katri.common.util.StringUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 공통 업무</li>
 * <li>서브 업무명 : 공통 관련</li>
 * <li>설	 명 : Spring AOP를 사용하여 공통 logging 구현</li>
 * <li>작  성  자 : Lee Han Seong</li>
 * <li>작  성  일 : 2021. 01. 18.</li>
 * </ul>
 * <pre>
 * ======================================
 * 변경자/변경일 :
 * 변경사유/내역 :
 * ======================================
 * </pre>
 ***************************************************/
@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class LogAspect {

	/** 에러메시지 Component */
	private final MessageSource messageSource;

	/**************************************************************
	 * request header 정보 출력을 위해 string 로 convert
	 *
	 * @param request 요청정보
	 * @return String string로 변경된 header 정보
	 **************************************************************/
	private String requestHeaderToString(HttpServletRequest request) {
		Enumeration headerNames = request.getHeaderNames();
		StringBuilder headerText = new StringBuilder();

		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();

			headerText.append(",\"").append(key).append("\"=\"").append(request.getHeader(key)).append("\"");
		}

		return headerText.toString().substring(1);
	}

	/**************************************************************
	 * execution 에 설정된 method 가 실행되기 전 출력되는 log 정의
	 * execution 에 설정된 method 가 controller 이므로 request log 정의
	 *
	 * @param joinPoint advice가 적용될 위치 정보
	 **************************************************************/
	@Before("execution(* com.katri.web..controller.*Controller.*(..))")
	public void requestLog(JoinPoint joinPoint) {
		long threadId = Thread.currentThread().getId();
		Object jp = joinPoint.getArgs();
		HttpServletRequest request =
				((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

		log.info("(=== REQUEST) :: (THREAD-ID) {} :: (HEADER) {} :: ({}) {} :: (PARAMS) {} ::",
				threadId, requestHeaderToString(request).replaceAll("[\r\n]",""), request.getMethod().toString().replaceAll("[\r\n]",""), request.getRequestURI().toString().replaceAll("[\r\n]",""),
				Arrays.toString(joinPoint.getArgs()).replaceAll("[\r\n]",""));
	}

	/**************************************************************
	 * execution 에 설정된 method 가 실행된 후 출력되는 log 정의
	 * execution 에 설정된 method 가 controller 이므로 response log 정의
	 *
	 * @param response 응답 정보
	 **************************************************************/
	@AfterReturning(value = "execution(* com.katri.web..controller.*Controller.*(..))", returning = "response")
	public void responseLog(Object response) {
		response = Optional.ofNullable(response).orElse(new Object());

		if (response.getClass().equals(ResponseEntity.class)) {
			ResponseEntity responseEntity = (ResponseEntity) response;
			log.info("\n [=== RESPONSE] \n [THREAD-ID] {} \n [CODE] {} \n [DATA] {} \n",
					Thread.currentThread().getId(), responseEntity.getStatusCode(),
					StringUtil.nvl(responseEntity.getBody()).toString());
		} else {
			log.info("\n [=== RESPONSE] \n [THREAD-ID] {} \n [DATA] {} \n",
					Thread.currentThread().getId(), response.toString());
		}
	}

	/**************************************************************
	 * execution 에 설정된 method 가 실행된 후 출력되는 log 정의
	 * execution 에 설정된 method 가 controller 이므로 response 로 오류가 발생하였을 경우 log 정의
	 *
	 * @param joinPoint advice가 적용될 위치 정보
	 * @param exception 오류 객체
	 * @exception
	 **************************************************************/
	@AfterThrowing(value = "execution(* com.katri.web..controller.*Controller.*(..))", throwing = "exception")
	public void writeFailLog(JoinPoint joinPoint, Exception exception) {
		log.info("\n [=== RESPONSE] \n [THREAD-ID] {} \n [ERROR] {} \n",
				Thread.currentThread().getId(), exception.toString());
	}

	/**************************************************************
	 * execution 에 설정된 method 가 실행 되기 전과 후의 log 정의
	 * SQL 실행 time 을 기록하기 위한 log 정의
	 *
	 * @param proceedingJoinPoint target method
	 * @return Object 결과 객체
	 * @throws Throwable exception
	 **************************************************************/
	@Around("execution(* com.katri.web..mapper.*Mapper.*(..))")
	public Object sqlTimeLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		long threadId = Thread.currentThread().getId();
		long startTime = System.currentTimeMillis();

		Object result = null;
		try {
			result = proceedingJoinPoint.proceed();
		} catch (Throwable throwable) {
			log.error("sqlTimeLog Error ==> ", throwable);
			throw throwable;
		}

		long endTime = System.currentTimeMillis();

		log.debug("\n [=== SQL(Mapper) Execute Time] \n [THREAD-ID] {} \n [CLASS] {} \n " +
						"[METHOD] {} \n [EXECUTE-TIME] {} ms\n",
				threadId, proceedingJoinPoint.getSignature().getDeclaringTypeName(),
				proceedingJoinPoint.getSignature().getName(), (endTime - startTime));

		return result;
	}

	/**************************************************************
	 * execution 에 설정된 method 가 실행된 후 출력되는 log 정의
	 * execution 에 설정된 method 가 mapper 이므로 response 로 오류가 발생하였을 경우 log 정의
	 *
	 * @param joinPoint advice가 적용될 위치 정보
	 * @param exception 오류 객체
	 * @exception
	 **************************************************************/
	@AfterThrowing(value = "execution(* com.katri.web..mapper.*Mapper.*(..))", throwing = "exception")
	public void executeFailLog(JoinPoint joinPoint, Exception exception) {
		log.info("\n [=== RESPONSE] \n [THREAD-ID] {} \n [ERROR] {} \n",
				Thread.currentThread().getId(), exception.toString());

		try {
			throw new GlobalExceptionHandler.CustomRuntimeException(exception.toString());
		} catch (Throwable e) {
			log.info("logAspect executeFailLog error");
		}



	}

}

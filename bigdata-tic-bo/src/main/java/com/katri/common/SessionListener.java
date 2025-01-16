package com.katri.common;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Value;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : Session Listener 업무</li>
 * <li>서브 업무명 : 공통 관리</li>
 * <li>설	 명 : Global exception 처리</li>
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
public class SessionListener implements HttpSessionListener {

	@Value("${server.servlet.session.timeout}")
	private int sessionTime;

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		se.getSession().setMaxInactiveInterval(sessionTime);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
	}
}
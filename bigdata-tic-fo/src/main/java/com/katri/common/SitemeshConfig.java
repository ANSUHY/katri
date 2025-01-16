package com.katri.common;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 공통 업무</li>
 * <li>서브 업무명 : 공통 관련</li>
 * <li>설     명 : Interceptor 등록을 위한 webconfig</li>
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
@Configuration
public class SitemeshConfig {

	@Bean
	public FilterRegistrationBean siteMeshFilter() {

		FilterRegistrationBean filter = new FilterRegistrationBean();

		filter.setFilter(new SiteMeshFilter()); //2번에서 만든 클래스 이름으로 사용
		return filter;

	}

}

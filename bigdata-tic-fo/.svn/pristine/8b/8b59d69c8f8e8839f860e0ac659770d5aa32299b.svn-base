package com.katri.common;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

import lombok.RequiredArgsConstructor;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 공통 업무</li>
 * <li>서브 업무명 : 공통 관련</li>
 * <li>설	 명 : Interceptor 등록을 위한 webconfig</li>
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
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	/** 인텁셉터 */
	private final LoginInterceptor loginInterceptor;

	private final ObjectMapper objectMapper;

	/**************************************************************
	 * 웹 설정에 interceptor 를 등록
	 *
	 * @param registry interceptor registry
	 ***************************************************/
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(this.loginInterceptor);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}


	/**************************************************************
	 * 웹 설정에 XSS 를 등록
	 *
	 * @param
	 **************************************************************/
	//Lucy Xss filter 적용
	@Bean
	public FilterRegistrationBean<XssEscapeServletFilter> xssFilterBean(){

		FilterRegistrationBean<XssEscapeServletFilter> registrationBean = new FilterRegistrationBean<XssEscapeServletFilter>();
		registrationBean.setFilter(new XssEscapeServletFilter());
		registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE);

		// url 패턴 추가 -> 모든 url에 xss filter 추가
		registrationBean.addUrlPatterns( "/*" );

		return registrationBean;
	}

	//requestBody xss 필터 적용(json/api)
	@Bean
	public MappingJackson2HttpMessageConverter jsonEscapeConverter() {

		ObjectMapper copy = objectMapper.copy();
		copy.getFactory().setCharacterEscapes(new HtmlCharacterEscapes());
		return new MappingJackson2HttpMessageConverter(copy);

	}

}

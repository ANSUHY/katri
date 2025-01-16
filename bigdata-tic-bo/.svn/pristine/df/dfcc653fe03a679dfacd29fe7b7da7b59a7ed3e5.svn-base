package com.katri.common.datasource;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 공통 업무</li>
 * <li>서브 업무명 : main master data-source 관리</li>
 * <li>설	 명 : main master data source config 정보 설정</li>
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
@MapperScan(value={"com.katri.web", "com.katri.common.tlds", "com.katri.batch"},
		annotationClass= MainMapperAnnotation.class, sqlSessionFactoryRef="mainSqlSessionFactory")
@EnableTransactionManagement
public class MainDataSourceConfig {

	/*********************************************
	 * DataSource bean 생성
	 * @return  DataSource
	 *********************************************/
	@Bean(name="mainDataSource")
	@ConfigurationProperties(prefix="spring.data-source")
	public DataSource mainDataSource() {
		return DataSourceBuilder.create().build();
	}

	/*********************************************
	 * SqlSessionFactory bean 생성
	 * @return  SqlSessionFactory
	 *********************************************/
	@Bean(name="mainSqlSessionFactory")
	@ConfigurationProperties(prefix = MybatisProperties.MYBATIS_PREFIX)
	public SqlSessionFactory mainSqlSessionFactoryBean(
			@Autowired @Qualifier("mainDataSource") DataSource mainDataSource,
			ApplicationContext applicationContext,
			MybatisProperties properties) throws Exception, IOException {

		// data-source 분기를 위한 설정 영역
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setMapUnderscoreToCamelCase(properties.getConfiguration().isMapUnderscoreToCamelCase());
		configuration.setMultipleResultSetsEnabled(properties.getConfiguration().isMultipleResultSetsEnabled());
		configuration.setUseColumnLabel(properties.getConfiguration().isUseColumnLabel());
		configuration.setCacheEnabled(properties.getConfiguration().isCacheEnabled());
		configuration.setLazyLoadingEnabled(properties.getConfiguration().isLazyLoadingEnabled());
		configuration.setDefaultStatementTimeout(properties.getConfiguration().getDefaultStatementTimeout());

		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(mainDataSource);
		factoryBean.setConfiguration(configuration);
		factoryBean.setMapperLocations(applicationContext.getResources(properties.getMapperLocations()[0]));
		return factoryBean.getObject();
	}

	/*********************************************
	 * SqlSessionTemplate bean 생성
	 * @return  SqlSessionTemplate
	 *********************************************/
	@Bean(name="mainSqlSession")
	public SqlSessionTemplate mainSqlSession(
			@Autowired @Qualifier("mainSqlSessionFactory") SqlSessionFactory mainSqlSessionFactory) {
		return new SqlSessionTemplate(mainSqlSessionFactory);
	}

}
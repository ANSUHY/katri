package com;

import javax.servlet.http.HttpSessionListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.katri.common.SessionListener;

@EnableCaching
@EnableScheduling
@SpringBootApplication
@ComponentScan({"com.katri"})
public class KatriApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(KatriApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(KatriApplication.class);
	}

	@Bean
	public HttpSessionListener httpSessionListener(){
		return new SessionListener();
	}
}

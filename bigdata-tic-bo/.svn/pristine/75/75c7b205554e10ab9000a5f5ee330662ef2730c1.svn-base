package com.katri.web.main.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.web.comm.service.MailService;
import com.katri.web.main.mapper.MainMapper;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
@Slf4j
@Api(tags = {"메인 Service"})
public class MainService {

	/** 메인 MAPPER */
	private final MainMapper mainMapper;

	/** 메일 Service */
	private final MailService mailService;

}

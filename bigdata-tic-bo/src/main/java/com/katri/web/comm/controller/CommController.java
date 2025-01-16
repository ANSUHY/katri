package com.katri.web.comm.controller;


import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import com.katri.web.comm.mapper.CommMapper;
import com.katri.web.comm.service.CommService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@Api(tags = {"공통 Controller"})
@Validated
public class CommController {

	/** 공통 서비스 Service */
	@SuppressWarnings("unused")
	private final CommService commService;

	/** comm Mapper */
	private final CommMapper commMapper;

}

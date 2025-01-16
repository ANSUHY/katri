package com.katri.web.mbr.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;

import com.katri.web.mbr.service.MbrMngService;


import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@Api(tags = {"회원관리 Controller"})
public class MbrMngController {
	
	/** 회원 Service */
	private final MbrMngService mbrMngService;

	@Autowired
	private final MessageSource messageSource;
	
	
}

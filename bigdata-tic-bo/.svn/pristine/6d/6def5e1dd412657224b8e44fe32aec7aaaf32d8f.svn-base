package com.katri.web.main.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.katri.web.comm.model.LayoutReq;
import com.katri.web.comm.service.CommService;
import com.katri.web.main.service.MainService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Api(tags = {"메인 Controller"})
@Slf4j
public class MainController {

	/** 공통 Service */
	private final CommService commService;

	/** 메인 Service */
	private final MainService mainService;

	/*****************************************************
	 * index (index.jsp가면 로그인페이지로 바로가게 만들어놓음)
	 * @param
	 *****************************************************/
	@GetMapping(value = {"/"})
	public String index(HttpServletRequest request, LayoutReq layoutReq, Model model) {

		model.addAttribute(layoutReq);

		return "/index";
	}
}

package com.katri.web.user.drmncy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Api(tags = {"drmncy Controller"})
@RequestMapping(value = "/user")
@Slf4j
public class DrmncyController {

	/*****************************************************
	 * drmncy 휴면 회원 현황 목록 이동
	 * @param model
	 * @return String
	*****************************************************/
	@GetMapping(value = {"/drmncy/drmncyList"})
	public String drmncyList(Model model) {

		return "user/drmncy/drmncyList";
	}

}

package com.katri.web.user.whdwl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Api(tags = {"whdwl Controller"})
@RequestMapping(value = "/user")
@Slf4j
public class WhdwlController {

	/*****************************************************
	 * whdwl 탈퇴 회원 현황 목록 이동
	 * @param model
	 * @return String
	*****************************************************/
	@GetMapping(value = {"/whdwl/whdwlList"})
	public String whdwlList(Model model) {

		return "user/whdwl/whdwlList";
	}

	/*****************************************************
	 * whdwl 탈퇴회원 현황 > 탈퇴 회원 상세 팝업 호출
	 * @param model
	 * @return String
	*****************************************************/
	@GetMapping(value = {"/user/whdwlDetailPopUp"})
	public String whdwlDetailPopUp(Model model) {

		return "user/whdwl/whdwlDetailPopUp";

	}

}

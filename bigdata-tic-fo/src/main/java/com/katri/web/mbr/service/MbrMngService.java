package com.katri.web.mbr.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katri.web.mbr.mapper.MbrMngMapper;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
@Slf4j
@Api(tags = {"회원관리 Service"})
public class MbrMngService {
	private final MbrMngMapper mbrMngMapper;
}

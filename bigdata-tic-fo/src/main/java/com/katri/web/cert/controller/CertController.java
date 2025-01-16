package com.katri.web.cert.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.katri.common.CommonCodeKey;
import com.katri.common.util.StringUtil;
import com.katri.web.cert.model.CertRes;
import com.katri.web.cert.service.CertService;
import com.katri.web.comm.model.CommReq;
import com.katri.web.comm.model.CommRes;
import com.katri.web.comm.service.CommService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequiredArgsConstructor
@Api(tags = {"인증정보 Controller"})
@Slf4j
public class CertController {

	/** 인증정보 Service */
	private final CertService certService;
	/** 공통코드 Service */
	private final CommService commService;

	@GetMapping(value = {"/cert/certLst"})
	public String certLst(HttpServletRequest request, Model model) {

		CommReq commReq = new CommReq();
		// 인증기관 코드
		commReq.setUpCd(CommonCodeKey.instIdKey);
		List<CommRes> certInstList = this.commService.selectCode(commReq); // 토탈 카운트
		model.addAttribute("certInstList",certInstList);

		Map<String, Object> param = new HashMap<String, Object>();

		Enumeration<String> re = request.getParameterNames();
		while (re.hasMoreElements()){
			 String name = re.nextElement();	// 파라미터 값을 차례대로 가져와 name 변수에 저장한다.
			 String valueText = request.getParameter(name);
			 //보안 추가
			 valueText = StringUtil.removeXSS(valueText);
			 if (!"".equals(valueText)) {
				 try {
					 valueText = URLEncoder.encode(valueText, "UTF-8");
					 param.put(name, URLDecoder.decode(valueText, "UTF-8"));
					 model.addAttribute(name, URLDecoder.decode(valueText, "UTF-8"));
				} catch (Exception e) {
					e.printStackTrace();
					param.put(name, valueText); // 시작 카운트
					model.addAttribute(name, valueText);
				}
			 }
		}


		// 페이징 처리 로직
		String pageNumText = StringUtil.nvl(request.getParameter("pageNum"));
		int pageNum = "".equals(pageNumText) ? 1 : Integer.parseInt(pageNumText);

		int s_c = ( pageNum * 10 ) - 10; // 시작 카운트

		param.put("s_c", s_c ); // 시작 카운트
		param.put("e_c", 10); // 시작 카운트



		// 기본 리스트
		int allCount = this.certService.selectListCount(param); // 토탈 카운트
		List<CertRes> list = this.certService.selectList(param); // 리스트

		// 전체 페이지 확인
		int allPage = allCount / 10;
		if (allCount % 10 > 0) allPage++;

		// 페이지 구릅 만들기
		int allPageGroup = allPage / 10;
		if (allPage % 10 > 0) allPageGroup++;

		int nowPageGroup = pageNum / 10;
		if (pageNum % 10 > 0) nowPageGroup++;

		// 시작 페이지 카운트 계산

		int sPage = nowPageGroup * 10 - 9;
		// 끝 페이지카운트
		int ePage = nowPageGroup * 10;
		if (ePage > allPage) ePage = allPage;


		model.addAttribute("sPage", sPage);
		model.addAttribute("ePage", ePage);
		model.addAttribute("allPage", allPage);
		model.addAttribute("allCount", allCount);
		model.addAttribute("allPageGroup", allPageGroup);
		model.addAttribute("nowPageGroup", nowPageGroup);
		model.addAttribute("pageNum", pageNum);

		model.addAttribute("list", list);

		return "/cert/certLst";

	}

	@GetMapping(value = {"/cert/certDtl"})
	public String certDtl(HttpServletRequest request, HttpServletResponse response, Model model)   {

		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.

		Map<String, Object> param = new HashMap<String, Object>();

		Enumeration<String> re = request.getParameterNames();
		while (re.hasMoreElements()){
			String name = re.nextElement();	// 파라미터 값을 차례대로 가져와 name 변수에 저장한다.
			String valueText = request.getParameter(name);
			//보안 추가
			 valueText = StringUtil.removeXSS(valueText);
			if (!"".equals(valueText)) {
				param.put(name, valueText); // 시작 카운트
				model.addAttribute(name, valueText);
			}
		}

		CertRes info = this.certService.selectInfo(param);

		String chgYmd = info.getCertLastChgYmd();
		if(chgYmd.length() == 8) {
			String y = chgYmd.substring(0, 4);
			String m = chgYmd.substring(4, 6);
			String d = chgYmd.substring(6, 8);

			info.setCertLastChgYmd(y+"."+m+"."+d);
		}

		String chgYmd2 = info.getCertYmd();
		if(chgYmd2.length() == 8) {
			String y = chgYmd2.substring(0, 4);
			String m = chgYmd2.substring(4, 6);
			String d = chgYmd2.substring(6, 8);

			info.setCertYmd(y+"."+m+"."+d);
		}


		//이미지 불러오기
		String cert = info.getCertNo();

		String certId = info.getInstId();


		/*T001	AT 한국의류시험연구원		  KATRI
		T002	CL 한국건강생활환경시험연구원  KCL
		T003	FI FITI시험연구원			  FITI
		T004	TC 한국기계전기전자시험연구원  KTC
		T005	TI KOTITI시험연구원			KOTITI
		T006	TL 한국산업기술시험원		  KTL
		T007	TR 한국화학융합시험연구원	  KTR*/

		/*
		if("T001".equals(certId)) {
			certNm = "katri";
		}else if("T002".equals(certId)) {
			certNm = "kcl";
		}else if("T003".equals(certId)) {
			certNm = "fiti";
		}else if("T004".equals(certId)) {
			certNm = "ktc";
		}else if("T005".equals(certId)) {
			certNm = "kotiti";
		}else if("T006".equals(certId)) {
			certNm = "ktl";
		}else if("T007".equals(certId)) {
			certNm = "ktr";
		}
		*/
		CommReq commReq = new CommReq();
		commReq.setUpCd("INST03");
		commReq.setComnCd(certId);

		CommRes commRes  = this.commService.selectCertRelCode(commReq);
		String sInsNm = StringUtil.nvl(commRes.getRelCd1());
		info.setSmInstNm(sInsNm);

		String hostUrl = "";//"http://192.168.0.3:9870/webhdfs/v1/STR/"+sInsNm+"/cert/"+cert+"/?op=LISTSTATUS";
		//String hostUrl = "http://manage01:9870/webhdfs/v1/STR/IMAGE/"+sInsNm+"/cert/"+cert+"/?op=LISTSTATUS";
		String inst = info.getSmInstNm();
		String DATA_DIRECTORY = "/img/"+inst+"/cert/"+cert;
		File dir = new File(DATA_DIRECTORY);

		String[] filenames = dir.list();
		JSONObject json = new JSONObject();
		JSONObject jsonob = new JSONObject();
		JSONArray jsonarr = new JSONArray();

		if(filenames != null) {
			for (String filename : filenames) {

				JSONObject data = new JSONObject();

				data.put("pathSuffix", filename);

				jsonarr.add(data);

			}

			jsonob.put("FileStatus", jsonarr);
			json.put("FileStatuses", jsonob);
		model.addAttribute("img", json);
		}else {
			model.addAttribute("img", "");
		}

		StringBuilder results = new StringBuilder();

	  //서버에서 보낸 응답 데이터 수신 받기
//		BufferedReader in;
//		try {
//			in = new BufferedReader(new InputStreamReader(((HttpURLConnection) (new URL(hostUrl)).openConnection()).getInputStream(), Charset.forName("UTF-8")));
//			 String line;
//				while ((line = in.readLine()) != null) {
//					results.append(line);
//				}
//		} catch (MalformedURLException e) {
//			results.append("");
//		} catch (IOException e) {
//			results.append("");
//		}


		//System.out.println("응답메시지 : " + results );

		model.addAttribute("info", info);
		//model.addAttribute("img", results);

		model.addAttribute("imgurl", hostUrl);
		return "/cert/certDtl";

	}
	@ResponseBody
	@GetMapping(value = {"/cert/displayImage"})
	public byte[] displayImage(HttpServletRequest request, Model model, HttpServletResponse response)   {
		//DB에 저장된 파일 정보를 불러오기
		String cert = request.getParameter("cert");
		String path = request.getParameter("path");
		String img = request.getParameter("img");
		response.setContentType("image/jpg");
		ServletOutputStream bout = null;
		byte[] fileArray = null;
		FileInputStream fis = null;

		try {
			bout = response.getOutputStream();
			 //파일의 경로
		   // String address = "http://master:9870/webhdfs/v1/STR/katri/cert/"+path+"/"+img+"?op=OPEN";
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int readCount = 0;
			byte[] buffer = new byte[1024];

			String strUrl = "/img/"+cert+"/cert/"+path+"/"+img;
			try {
/*
				 URL url = new URL(address);
				 ReadableByteChannel rbc = Channels.newChannel(url.openStream());
				 FileOutputStream fos = new FileOutputStream("/Users/yoonsikcha/Temp/"+img); //다운받을 경로 설정
				 fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);  // 처음부터 끝까지 다운로드
				 fos.close();
*/
				try{
					//fis = new FileInputStream("/Users/yoonsikcha/Temp/"+img);
					fis = new FileInputStream(strUrl);

					while((readCount = fis.read(buffer)) != -1){
						baos.write(buffer, 0, readCount);
					}
					fileArray = baos.toByteArray();
				} catch(FileNotFoundException e){
					log.debug(e.getMessage());
				} finally {
					fis.close();
					baos.close();
				}

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		} catch (IOException e) {
			log.debug(e.getMessage());
		} finally {
			try {
				bout.close();
			} catch (IOException e) {
				log.debug(e.getMessage());
			}
		}

		return fileArray;
	}

}

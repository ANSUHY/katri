package com.katri.web.comm.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.katri.common.GlobalExceptionHandler.CustomMessageException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : Anls Api 서비스</li>
 * <li>서브 업무명 : Anls Api 서비스</li>
 * <li>설		 명 : Anls Api 관련 서비스 제공 - [데이터 분석 환경] API 호출 관련</li>
 * <li>작   성   자 : LYJ</li>
 * <li>작   성   일 : 2022. 11. 04.</li>
 * </ul>
 * <pre>
 * ======================================
 * 변경자/변경일 :
 * 변경사유/내역 :
 * --------------------------------------
 * 변경자/변경일 :
 * 변경사유/내역 :
 * ======================================
 * </pre>
 ***************************************************/
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
@Slf4j
public class AnlsApiService {

	/** api : authentication 발급 인증키 */
	@Value("${anls.api.key.authentication}")
	String AUTHENTICATION;

	/** api : API 호출 URL */
	@Value("${anls.api.url}")
	String ANLS_API_URL;

	/*****************************************************
	 * API 호출 후 받은 json Text 데이터를 Map으로 변환하여 리턴.
	 * @param urlData
	 * @param methodType
	 * @param paramData
	 * @return
	 * @throws Exception
	*****************************************************/
	@SuppressWarnings("unchecked")
	public Map<String, Object> apiConnectionData(String urlData, String methodType, Map<String, Object> paramData) throws Exception {

		Map<String, Object>	responseData	= new HashMap<String, Object>();
		HttpURLConnection 	conn 			= null;
		OutputStream 		os 				= null;
		BufferedReader		br 				= null;
		String 				authentication	= AUTHENTICATION;

		// [0]. URL 유효성 검사
		if( "".equals(urlData) || urlData == null ) {
			throw new CustomMessageException("URL 입력 필요");
		}

		URL url = new URL(urlData);
		conn = (HttpURLConnection) url.openConnection();

		try {

			// [1]. HTTP 요청값 셋팅
			conn.setRequestMethod(methodType);

			conn.setRequestProperty( "Authentication"	, authentication );
			conn.setRequestProperty( "Content-Type"		, "application/json; utf-8" );

			conn.setDoOutput(true); // OutputStream을 사용해서 post body 데이터 전송

			// [2]. Request body Format
			String paramJson = "";

			JSONObject json = new JSONObject(paramData);
			paramJson 		= json.toString();

			os				= conn.getOutputStream();
			byte 			request_data[]	= paramJson.getBytes("utf-8");

			os.write(request_data, 0, request_data.length);
			os.close();

			// [3]. Response body Format
			int responseCode = conn.getResponseCode();

			// [4]. 응답 코드별 메세지 or 값 셋팅

			if (responseCode == 200) {
				/* --------------------------- Response Code 200 --------------------------- */
				br 		= new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				StringBuilder	sb 		= new StringBuilder();
				String			line	= "";

				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

				ObjectMapper mapper = new ObjectMapper();
				responseData 		= mapper.readValue(sb.toString(), Map.class);

				log.info("\n===============API_CODE_200===============\n [===RESPONSE DATA]" + responseData.toString());
				/* ------------------------------------------------------------------------- */
			} else if (responseCode == 400) {
				/* --------------------------- Response Code 400 --------------------------- */
				log.error("\n===============ERROR_API_CODE_400=========================\n");
				responseData = null;
				/* ------------------------------------------------------------------------- */
			} else if (responseCode == 401) {
				/* --------------------------- Response Code 401 --------------------------- */
				log.error("\n===============ERROR_API_CODE_401=========================\n");
				responseData = null;
				/* ------------------------------------------------------------------------- */
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		} finally {

			try {
				if(os != null) {
					os.close();
				}
				if(br != null) {
					br.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}

		return responseData;
	}


	/*****************************************************
	 * [데이터 분석 환경] > USER 생성 API 호출
	 * @param methodType
	 * @param paramData
	 * @return
	 * @throws Exception
	*****************************************************/
	public Map<String, Object> apiConnectionUserAddInit( Map<String, Object> paramData) throws Exception {

		String methodType 	= "POST";
		String urlData 		= ANLS_API_URL;

		Map<String, Object> mapRole = new HashMap<String, Object>();
		mapRole.put("id", 1);
		paramData.put("role", mapRole);

		Map<String, Object> responseData = apiConnectionData(urlData, methodType, paramData);

		return responseData;

	}

	/*****************************************************
	 * [데이터 분석 환경] > USER 수정 API 호출
	 * @param methodType
	 * @param paramData
	 * @return
	 * @throws Exception
	*****************************************************/
	public Map<String, Object> apiConnectionUserUpdateInit( Map<String, Object> paramData ) throws Exception {

		Integer	anslEnvUserId 	=  Integer.parseInt((String) paramData.get("id"));
		String	methodType 		= "PUT";
		String 	urlData 		= ANLS_API_URL + "/" + anslEnvUserId;

		Map<String, Object> mapRole = new HashMap<String, Object>();
		mapRole.put("id", 1 );
		paramData.put("role"	, mapRole);
		paramData.put("locked"	, 0);

		Map<String, Object> responseData = apiConnectionData(urlData, methodType, paramData);

		return responseData;

	}

	/*****************************************************
	 * [데이터 분석 환경] > USER 삭제 API 호출
	 * @param methodType
	 * @param paramData
	 * @return
	 * @throws Exception
	*****************************************************/
	public Map<String, Object> apiConnectionUserDeleteInit( Map<String, Object> paramData) throws Exception {

		Integer	anslEnvUserId 	=  Integer.parseInt((String) paramData.get("id"));
		String	methodType 		= "DELETE";
		String	urlData 		= ANLS_API_URL + "/" + anslEnvUserId;

		Map<String, Object> responseData = apiConnectionData(urlData, methodType, paramData);

		return responseData;

	}

}

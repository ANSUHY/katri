package com.katri.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 로그인 허용 경로 업무</li>
 * <li>서브 업무명 : 로그인 허용 경로 관리</li>
 * <li>설	 명 : 로그인 허용 경로 정보</li>
 * <li>작  성  자 : Lee Han Seong</li>
 * <li>작  성  일 : 2021. 01. 18.</li>
 * </ul>
 * <pre>
 * ======================================
 * 변경자/변경일 :
 * 변경사유/내역 :
 * ======================================
 * </pre>
 ***************************************************/
@Component
@ConfigurationProperties(prefix = "login-exclude")
@Getter
@Setter
@ApiModel(description = "로그인허용경로")
public class ExcludePath {

	/** 공통로그인허용경로 */
	@ApiModelProperty(notes = "공통로그인허용경로", example = "/swagger-ui.html", position = 1)
	private List<String> common;

	/** Mo,Pc로그인허용경로 */
	@ApiModelProperty(notes = "프론트 로그인 허용경로", example = "/mypage/mbshJn", position = 2)
	private List<String> front;

	/** Bo,Po로그인허용경로 */
	@ApiModelProperty(notes = "관리자 로그인허용경로", example = "/system/logout", position = 3)
	private List<String> back;

}

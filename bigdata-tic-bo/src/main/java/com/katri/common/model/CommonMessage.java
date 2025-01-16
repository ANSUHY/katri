package com.katri.common.model;

import lombok.Setter;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 공통 업무</li>
 * <li>서브 업무명 : Message 관리</li>
 * <li>설	 명 : 결과, 오류 메세지 객체 공통 정보 객체</li>
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
@Setter
public class CommonMessage {

	/** 메세지 */
	private Map<String, String> messages;

	/**************************************************************
	 * 파라미터로 넘어온 argument 를 message 에 대입, 변경된 메세지 리턴
	 *
	 * @return String 메세지
	 **************************************************************/
	public String getArgumentsMessage(String key, String[] args) {
		args = Optional.ofNullable(args).orElse(new String[] {});

		Pattern pattern = Pattern.compile("\\{[0-9]*\\}");
		Matcher matcher = pattern.matcher(this.messages.get(key));

		StringBuilder stringBuilder = new StringBuilder();
		int startIndex = 0;

		while (matcher.find()) {
			stringBuilder.append(this.messages.get(key), startIndex, matcher.start());

			try {
				stringBuilder
						.append(args[Integer.parseInt(matcher.group().substring(1,matcher.group().length()-1))]);
			} catch (NumberFormatException e) {
				stringBuilder.append(matcher.group());
			} 

			startIndex = matcher.end();
		}

		if (stringBuilder.length() > 0) {
			stringBuilder.append(this.messages.get(key).substring(startIndex));
		} else {
			stringBuilder.append(this.messages.get(key));
		}

		return stringBuilder.toString();
	}

}

package com.katri.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 공통 업무</li>
 * <li>서브 업무명 : Message 관리</li>
 * <li>설     명 : 결과 메세지 정보 객체</li>
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
@ConfigurationProperties(prefix = "config")
@Getter
@Setter
@ToString
public class ConfigMessage extends CommonMessage {

    /** 결과메세지 */
    private Map<String, String> messages;

    /**************************************************************
     * 파라미터로 넘어온 argument 를 message 에 대입, 변경된 메세지 리턴
     *
     * @return String 결과메세지
     **************************************************************/
    @Override
	public String getArgumentsMessage(String key, String[] args) {
        super.setMessages(this.messages);
        return  super.getArgumentsMessage(key, args);
    }

}

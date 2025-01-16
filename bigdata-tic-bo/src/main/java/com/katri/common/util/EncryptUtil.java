package com.katri.common.util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class EncryptUtil {
	/** 초기화 비밀번호 */
	
	static String loginPasswordAesKey ;

	@Value("${login.password.aes256.key}")
	private  void setLoginPasswordAesKey(String value) {
		loginPasswordAesKey = value;
	}

	/**
	 * 비밀번호를 암호화하는 기능(복호화가 되면 안되므로 SHA-256 인코딩 방식 적용)
	 *
	 * @param password 암호화될 패스워드
	 * @param id salt로 사용될 사용자 ID 지정
	 * @return
	 * @throws Exception
	 */
	public static String encryptSha256(String password, String id) {
		if (password == null) {
			return "";
		}
		String enStr = null;

		try {
			byte[] hashValue = null; // 해쉬값

			MessageDigest md = MessageDigest.getInstance("SHA-256");

			md.reset();
			md.update(id.getBytes());

			hashValue = md.digest(password.getBytes());
			enStr = new String(Base64.getEncoder().encode(hashValue));
		} catch (Exception e) {
			log.error("Sha Util Encrypt Error", e);
		}
		return enStr;
	}


	/**
	 * AES-256 암호화
	 * @param keyword
	 * @return
	 */
	public static String encryptAes256(String keyword) {
		if(StringUtils.isBlank(keyword)) {
			return null;
		}
		String enStr = null;
		try {
			
			Cipher c = Cipher.getInstance("AES/GCM/NoPadding");			
			byte[] iv = new byte[16];
			new SecureRandom().nextBytes(iv);
			iv = loginPasswordAesKey.getBytes();
			
			SecretKey secureKey = new SecretKeySpec(iv, "AES");
		   // IvParameterSpec ivSpec = new IvParameterSpec(iv);
		    GCMParameterSpec ivSpec = new GCMParameterSpec(16 * Byte.SIZE, iv);
			c.init(Cipher.ENCRYPT_MODE, secureKey, ivSpec);

			byte[] encrypted = c.doFinal(keyword.getBytes("UTF-8"));
			enStr = new String(Base64.getEncoder().encode(encrypted));
		} catch (Exception e) {
			log.error("AES Util Encrypt Error", e);
		}
		return enStr;
	}

	/**
	 * AES-256 복호화
	 * @param keyword
	 * @return
	 */
	public static String decryptAes256(String keyword) {
		if(StringUtils.isBlank(keyword)) {
			return null;
		}

		String deStr = null;
		try {
			
			
			Cipher c = Cipher.getInstance("AES/GCM/NoPadding");			
			byte[] iv = new byte[16];
			
			iv = loginPasswordAesKey.getBytes();
			
			SecretKey secureKey = new SecretKeySpec(iv, "AES");
		    //IvParameterSpec ivSpec = new IvParameterSpec(iv);
		    GCMParameterSpec ivSpec = new GCMParameterSpec(16 * Byte.SIZE, iv);
			c.init(Cipher.DECRYPT_MODE, secureKey, ivSpec);

			byte[] byteStr = Base64.getDecoder().decode(keyword.getBytes());
			
			deStr = new String(c.doFinal(byteStr), "UTF-8");
		} catch (Exception e) {
			log.error("AES Util Decrypt Error", e);
		}
		return deStr;
	}
}
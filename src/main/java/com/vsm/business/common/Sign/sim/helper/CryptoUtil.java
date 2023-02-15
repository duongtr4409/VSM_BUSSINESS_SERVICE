package com.vsm.business.common.Sign.sim.helper;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoUtil {
	public static String md5(String inp) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(inp.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
        //StringBuilder sb = new StringBuilder(); // chuyển sang StringBuilder fix báo lỗi khi chạy mvn verify
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	public static String SHA256(String inp) {

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(inp.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
        //StringBuilder sb = new StringBuilder(); // chuyển sang StringBuilder fix báo lỗi khi chạy mvn verify
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	public static byte[] HmacSHA256(String message, String secret) {
		try {
			Mac sha256HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
			sha256HMAC.init(secretKey);

			return sha256HMAC.doFinal(message.getBytes());
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static String Base64decode(String s) {
	    return StringUtils.newStringUtf8(Base64.decodeBase64(s));
        //return StringUtils.newStringUtf8(java.util.Base64.getDecoder().decode(s));  // chuyển fix báo lỗi khi chạy mvn verify
	}

	public static byte[] Base64decodeToBytes(String s) {
		return Base64.decodeBase64(s);
        //return java.util.Base64.getDecoder().decode(s); // chuyển fix báo lỗi khi chạy mvn verify
	}

	public static String Base64encode(String s) {
	    return Base64.encodeBase64String(StringUtils.getBytesUtf8(s));
        //return java.util.Base64.getEncoder().encodeToString(StringUtils.getBytesUtf8(s));   // chuyển fix báo lỗi khi chạy mvn verify
	}

	public static String Base64encode(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
        //return java.util.Base64.getEncoder().encodeToString(bytes); // chuyển fix báo lỗi khi chạy mvn verify
	}
}

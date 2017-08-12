package com.sunxl.base.util;

import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月13日 @此类作用：
 */
@SuppressWarnings("restriction")
public class EncryptionUtil {
	private static final Logger logger = LoggerFactory.getLogger(EncryptionUtil.class);
	public static final String MD5 = "md5";
	public static final String SHA1 = "sha1";

	/**
	 * 前台加密
	 */
	public static String bySHA1(String code) {
		return encrypt(code, SHA1);
	}

	/**
	 * 后台加密
	 */
	public static String byMD5(String code) {
		return encrypt(code, MD5);
	}

	/**
	 * MD5加密
	 * 
	 * @param code
	 * @param algorithm
	 * @return
	 */
	private static String encrypt(String code, String algorithm) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			byte[] bytes = md.digest(code.getBytes("UTF-8"));
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(bytes);
		} catch (Exception e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+"加密算法异常！！cause[EncryptionUtil]" + e.getMessage());
		}
		return null;
	}

	/**
	 * 加密Base64位
	 * 
	 * @param code
	 * @return
	 */
	public static String StringToBase64Function(String code) {
		try {
			if (code != null)
				return new BASE64Encoder().encode(code.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+"加密算法异常！！cause[EncryptionUtil]" + e.getMessage());
		}
		return null;
	}

	/**
	 * 解密Base64位
	 * 
	 * @param code
	 * @return
	 */
	public static String Base64ToStringFunction(String code) {
		try {
			if (code != null)
				return new String(new BASE64Decoder().decodeBuffer(code));
		} catch (IOException e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+"解密密算法异常！！cause[EncryptionUtil]" + e.getMessage());
		}
		return null;
	}

	/**
	 * MD5加码 生成32位md5码
	 * 
	 * @param code
	 * @param algorithm
	 * @return
	 */
	public static String string2MD5(String code, String algorithm) {
		try {
			MessageDigest md5 = MessageDigest.getInstance(algorithm);
			char[] charArray = code.toCharArray();
			byte[] byteArray = new byte[charArray.length];
			for (int i = 0; i < charArray.length; i++)
				byteArray[i] = (byte) charArray[i];
			byte[] md5Bytes = md5.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		} catch (Exception e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+"32位MD5解密密算法异常！！cause[EncryptionUtil]" + e.getMessage());
		}
		return null;
	}

	/**
	 * MD5加码 生成32位md5码
	 * 
	 * @param code
	 * @param algorithm
	 * @return
	 */
	public static String string2MD5(String code) {
		try {
			MessageDigest md5 = MessageDigest.getInstance(MD5);
			char[] charArray = code.toCharArray();
			byte[] byteArray = new byte[charArray.length];
			for (int i = 0; i < charArray.length; i++)
				byteArray[i] = (byte) charArray[i];
			byte[] md5Bytes = md5.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		} catch (Exception e) {
			logger.error("错误行号:【"+new Throwable().getStackTrace()[0].getLineNumber()+"】"+"32位MD5解密密算法异常！！cause[EncryptionUtil]" + e.getMessage());
		}
		return null;
	}

	/**
	 * MD5解密 加密解密算法 执行一次加密，两次解密
	 * 
	 * @param code
	 */
	public static String convertMD5(String code) {
		if (code == null)
			return null;
		char[] a = code.toCharArray();
		for (int i = 0; i < a.length; i++)
			a[i] = (char) (a[i] ^ 't');
		code = new String(a);
		return code;
	}

	/**
	 * MD5解密 加密解密算法 执行一次加密，两次解密
	 * 
	 * @param code
	 */
	public static String convertMD5(int id) {
		return convertMD5(id + "");
	}

	public static void main(String args[]) {
		String code = new String("tangfuqiang");
		System.out.println("原始：" + code);
		System.out.println("MD5后：" + string2MD5(code, MD5));
		System.out.println("加密的：" + convertMD5(code));
		System.out.println("解密的：" + convertMD5(convertMD5(code)));
	}
}

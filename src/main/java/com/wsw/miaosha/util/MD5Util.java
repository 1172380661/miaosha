package com.wsw.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author: wsw
 * @Date: 2019/5/1 16:27
 */
public class MD5Util {
	private static final String salt = "abcdss";

	/**
	 * md5加密
	 * @param str 原数组
	 * @return 加密后的数组
	 */
	public static String md5(String str) {
		return DigestUtils.md5Hex(str);
	}

	private static String inputPassToFormPass(String inputPass) {
		String str = inputPass+salt;
		return md5(str);
	}

	public static String formPassToDBPass(String formPass, String salt) {
		String str = salt+formPass;
		return md5(str);
	}

	public static String inputPassToDbPass(String inputPass, String saltDB) {
		String formPass = inputPassToFormPass(inputPass);
		return formPassToDBPass(formPass, saltDB);
	}

	public static void main(String[] args) {
		System.out.println(inputPassToDbPass("123456","02lEXJUYhm"));
	}


}

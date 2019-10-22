package my.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
	private static final String salt = "156dazx";
	
	public static String inputPassToFormPass(String str) {
		String saltPass = salt.charAt(0) + str +salt.charAt(3) +salt.charAt(5);
		return DigestUtils.md5Hex(saltPass);
	}
	
	public static String formPassToDBPass(String formPass,String salt) {
		if(salt!=null&&salt.length()>=6) {
			String saltPass = salt.charAt(0) + formPass +salt.charAt(3) +salt.charAt(5);
			return DigestUtils.md5Hex(saltPass);
		}else {
			throw new RuntimeException("salt太短");
		}
	}
	
	public static String inputPassToDBPass(String pass,String salt) throws Exception {
		String formPass = inputPassToFormPass(pass);
		String dbPass = formPassToDBPass(formPass,salt);
		return dbPass;
	}
	
	public static void main(String[] args) {
		System.out.println(inputPassToFormPass("123456"));
	}
	
}

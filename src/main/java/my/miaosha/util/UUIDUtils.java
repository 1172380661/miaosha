package my.miaosha.util;

import java.util.UUID;

public class UUIDUtils {
	
	public static String createUUId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}

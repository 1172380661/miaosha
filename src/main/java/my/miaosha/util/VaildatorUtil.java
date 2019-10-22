package my.miaosha.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class VaildatorUtil {
	
	public static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");
	
	public static boolean isMobile(Long mobile) {
		String mobileStr = String.valueOf(mobile);
		if(StringUtils.isEmpty(mobileStr)) {
			return false;
		}
		Matcher matcher = mobile_pattern.matcher(mobileStr);
		return matcher.matches();
	}
}

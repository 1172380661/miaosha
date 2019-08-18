package my.miaosha.redis.prefix;

public class HtmlPrefix extends BasePrefix{

	private static int expireSeconds = 60;
	
	public static HtmlPrefix goodsList = new HtmlPrefix(expireSeconds, "goodsList");
	public static HtmlPrefix goodsDetail = new HtmlPrefix(expireSeconds, "goodsDetail");
	
	private HtmlPrefix(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

}

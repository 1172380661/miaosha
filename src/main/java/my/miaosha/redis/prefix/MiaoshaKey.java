package my.miaosha.redis.prefix;

public class MiaoshaKey extends BasePrefix {

	private static final int expireSeconds = 3600*24*7;
	
	public static final MiaoshaKey stock = new MiaoshaKey(expireSeconds, "stock");
	
	private MiaoshaKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

}

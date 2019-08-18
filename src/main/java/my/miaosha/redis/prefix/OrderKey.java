package my.miaosha.redis.prefix;

public class OrderKey extends BasePrefix {
	
	private static final int expireSeconds = 3600*24*7;
	
	public static final OrderKey MIAOSHA_ORDER = new OrderKey(expireSeconds, "order");

	public OrderKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

}

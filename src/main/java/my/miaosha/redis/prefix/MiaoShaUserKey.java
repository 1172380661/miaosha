package my.miaosha.redis.prefix;

public class MiaoShaUserKey extends BasePrefix {

	public static final int TOKEN_EXPIRE = 3600*24*2;
	
	public static MiaoShaUserKey token = new MiaoShaUserKey(TOKEN_EXPIRE,"tk"); 
	public static MiaoShaUserKey id = new MiaoShaUserKey(TOKEN_EXPIRE,"id"); 
	
	public MiaoShaUserKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

	
}

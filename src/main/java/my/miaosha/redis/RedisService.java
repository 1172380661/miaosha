package my.miaosha.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import my.miaosha.redis.prefix.KeyPrefix;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {
	@Autowired
	JedisPool jedisPool;
	
	public <T> T get(KeyPrefix keyPrefix,String key,Class<T> clazz) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String realKey = keyPrefix.getPrefix()+key;
			String string = jedis.get(realKey);
			T t= stringToBean(string,clazz);
			return t;
		}finally {
			returnToPool(jedis);
		}	
	}
	
	public <T> boolean set(KeyPrefix keyPrefix,String key,T t) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String str = beanToString(t);
			if(str == null || str.length() <= 0) {
				return false;
			}
			String realKey = keyPrefix.getPrefix()+key;
			int seconds = keyPrefix.expireSeconds();
			if(seconds == 0) {
				jedis.set(realKey, str);
			}else {
				jedis.setex(realKey, seconds, str);
			}
			return true;
		}finally {
			returnToPool(jedis);
		}
	}
	
	public <T> String beanToString(T value) {
		if(value == null) {
			return null;
		}
		Class<?> clazz = value.getClass();
		if(clazz == int.class || clazz == Integer.class) {
			return ""+value;
		}else if(clazz == String.class) {
			return (String)value;
		}else if(clazz == long.class || clazz == Long.class) {
			return ""+value;
		}else {
			return JSON.toJSONString(value);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T stringToBean(String str,Class<T> clazz) {
		if(str == null || str.length()<=0 || clazz == null){
			return null;
		}
		if(clazz == int.class || clazz == Integer.class) {
			return (T)Integer.valueOf(str);
		}else if(clazz == long.class || clazz == Long.class) {
			return (T)Long.valueOf(str);
		}else if(clazz == String.class) {
			return (T)str;
		}else {
			return (T) JSON.toJavaObject(JSON.parseObject(str), clazz);
		}
	}
	
	private void returnToPool(Jedis jedis) {
		if(jedis!=null) {
			jedis.close();
		}
	}

	public boolean delete(KeyPrefix keyPrefix, String str) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String prefix = keyPrefix.getPrefix();
			String key = prefix + str;
			long ret =  jedis.del(key);
			return ret > 0;
		} finally {
			jedis.close();
		}
	}
	
	public long decr(KeyPrefix keyPrefix,long key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String realKey= keyPrefix.getPrefix() + key;
			return jedis.decr(realKey);
		} finally {
			returnToPool(jedis);
		}
	}
	
	public long incr(KeyPrefix keyPrefix,String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String realKey= keyPrefix.getPrefix() + key;
			return jedis.incr(realKey);
		} finally {
			returnToPool(jedis);
		}
	}
}

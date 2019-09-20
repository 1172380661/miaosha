package com.wsw.miaosha.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: wsw
 * @Date: 2019/9/17 12:12
 */
@Service
public class RedisService {
    private JedisPool jedisPool;

    @Autowired
    public RedisService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void set(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
        }
    }

    public void setex(String key, int seconds, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(key, seconds, value);
        }
    }

    public String get(String key) {
        String res;
        try (Jedis jedis = jedisPool.getResource()) {
            res = jedis.get(key);
        }
        return res;
    }

    public Long decr(String key) {
        Long res;
        try (Jedis jedis = jedisPool.getResource()) {
            res = jedis.decr(key);
        }
        return res;
    }
}

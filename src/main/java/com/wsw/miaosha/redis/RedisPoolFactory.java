package com.wsw.miaosha.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: wsw
 * @Date: 2019/9/17 11:50
 */

@Service
public class RedisPoolFactory {

    private RedisPoolConfig redisPoolConfig;

    @Autowired
    public RedisPoolFactory(RedisPoolConfig redisPoolConfig) {
        this.redisPoolConfig = redisPoolConfig;
    }

    @Bean
    public JedisPool getJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisPoolConfig.getPoolMaxTotal());
        jedisPoolConfig.setMaxIdle(redisPoolConfig.getPoolMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisPoolConfig.getPoolMaxWait());
        return new JedisPool(jedisPoolConfig, redisPoolConfig.getHost(), redisPoolConfig.getPort());
    }
}

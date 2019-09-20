package com.wsw.miaosha.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: wsw
 * @Date: 2019/9/1 18:23
 */

@Data
@Component
@ConfigurationProperties(prefix = "redis")
class RedisPoolConfig {
    private String host;
    private int port;
    private String password;
    private int timeout;
    private int poolMaxTotal;
    private int poolMaxIdle;
    private int poolMaxWait;
}



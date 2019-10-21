package com.wsw.miaosha.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @Author: wsw
 * @Date: 2019/6/19 17:49
 */

@Service
public class MQConfig {
    static final String MIAOSHA_QUEUE = "miaosha_queue";

    @Bean
    public Queue miaoshaQueue() {
        return new Queue(MIAOSHA_QUEUE);
    }

}

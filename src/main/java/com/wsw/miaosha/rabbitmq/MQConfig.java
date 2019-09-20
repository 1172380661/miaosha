package com.wsw.miaosha.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Author: wsw
 * @Date: 2019/9/19 17:49
 */

@Service
public class MQConfig {
    static final String QUEUE1 = "queue1";
    static final String QUEUE2 = "queue2";
    static final String MIAOSHA_QUEUE = "miaosha_queue";
    static final String FANOUT_EXCHANGE = "fanoutExchange";
    static final String DIRECT_EXCHANGE = "directExchange";
    static final String TOPIC_EXCHANGE = "topicExchange";
    static final String HEADERS_EXCHANGE = "headersExchange";

    @Bean
    public Queue queue1() {
        return new Queue(QUEUE1);
    }

    @Bean
    public Queue queue2() {
        return new Queue(QUEUE2);
    }

    @Bean
    public Queue miaoshaQueue() {
        return new Queue(MIAOSHA_QUEUE);
    }

    /**
     * @return fanoutExchange
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding fanoutBinding() {
        return BindingBuilder.bind(queue1()).to(fanoutExchange());
    }

    /**
     * @return directExchange
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(queue1()).to(directExchange()).with("queue1.direct");
    }

    /**
     * @return topicExchange
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding topicBinding() {
        return BindingBuilder.bind(queue2()).to(topicExchange()).with("queue2.#");
    }

    /**
     * @return headersExchange
     */
    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(HEADERS_EXCHANGE);
    }

    @Bean
    public Binding headersBinding1() {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("header", "queue2");
        return BindingBuilder.bind(queue1()).to(headersExchange()).whereAll(map).match();
    }

    @Bean
    public Binding headersBinding2() {
        return BindingBuilder.bind(queue2()).to(headersExchange()).where("header").matches("queue2");
    }

}

package com.wsw.miaosha.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: wsw
 * @Date: 2019/9/19 17:50
 */

@Service
public class MQSender {

    private AmqpTemplate amqpTemplate;

    @Autowired
    public MQSender(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void miaoshaSender(String msg) {
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, msg);
        System.out.println("msg send success");
    }

    public void fonoutSender(String msg) {
        System.out.println("fonoutSender :" + msg);
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", msg);
    }

    public void directSender(String msg) {
        System.out.println("directSender :" + msg);
        amqpTemplate.convertAndSend(MQConfig.DIRECT_EXCHANGE, "queue1.direct", msg);
    }

    public void topicSender(String msg) {
        System.out.println("topicSender :" + msg);
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "queue2.1234", msg);
    }

    public void headersSender(String msg) {
        System.out.println("headersSender :" + msg);
        MessageProperties properties = new MessageProperties();
        properties.setHeader("header", "queue2");
        Message message = new Message(msg.getBytes(), properties);
        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", message);
    }
}

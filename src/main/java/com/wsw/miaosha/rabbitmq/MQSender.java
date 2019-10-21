package com.wsw.miaosha.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Author: wsw
 * @Date: 2019/6/19 17:50
 */

@Service
public class MQSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static Map<CorrelationData, String> msgCache = new HashMap<>();

    @PostConstruct
    public void init() {
        //指定 ConfirmCallback
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    public void miaoshaSender(String msg) throws IOException, TimeoutException {
        CorrelationData correlationData = new CorrelationData();
        msgCache.put(correlationData, msg);
        rabbitTemplate.convertAndSend("error", MQConfig.MIAOSHA_QUEUE, msg, correlationData);
        System.out.println("成功已发送等待ack");
/*        //通过事务的方式来保证消息的可靠性传递 消费者接收不到消息原因不明
        ConnectionFactory connectionFactory =  new ConnectionFactory();
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(MQConfig.MIAOSHA_QUEUE, true, false, false, null);
        try {
            channel.txSelect();
            channel.basicPublish("", MQConfig.MIAOSHA_QUEUE, null, msg.getBytes());
            System.out.println("msg send success");
        }catch (Exception e){
            channel.txRollback();
        }*/

    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        //confirm确认是否到达路由器
        if (b) {
            System.out.println("消息发送成功");
            msgCache.remove(correlationData);
        } else {
            //msgCache的value应该存交换机名称、routinekey、msg和最大重发次数这里简写了
            rabbitTemplate.convertAndSend("", MQConfig.MIAOSHA_QUEUE, msgCache.get(correlationData), correlationData);
        }
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        //return确认是否触发回调
        System.out.println("消息主体 message : " + message);
        System.out.println("消息主体 message : " + i);
        System.out.println("描述：" + s);
        System.out.println("消息使用的交换器 exchange : " + s1);
        System.out.println("消息使用的路由键 routing : " + s2);
    }
}

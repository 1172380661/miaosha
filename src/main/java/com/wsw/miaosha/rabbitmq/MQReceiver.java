package com.wsw.miaosha.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.wsw.miaosha.redis.RedisService;
import com.wsw.miaosha.service.MiaoshaService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: wsw
 * @Date: 2019/6/15 17:50
 */

@Service
public class MQReceiver {

    private MiaoshaService miaoshaService;

    private RedisService redisService;

    @Autowired
    public MQReceiver(MiaoshaService miaoshaService,RedisService redisService) {
        this.miaoshaService = miaoshaService;
        this.redisService = redisService;
    }

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void miaoshaQueueReceiver(String msg) {
        //RabbitListener注解采用了手动ack来确认消息
        MiaoshaMessage t = JSON.toJavaObject(JSON.parseObject(msg), MiaoshaMessage.class);
        if (redisService.get(t.getId()) == null) {
            try {
                miaoshaService.miaosha(t.getUser(), t.getMiaoshaGoodsId());
                System.out.println("order insert success");
                redisService.setex(t.getId(), 5, "");
            } catch (Exception MySQLIntegrityConstraintViolationException) {
                System.out.println(t.getUser().getUsername() + ":重复下单");
            }
        }
/*      //RabbitListener封装了一些模板方法
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(MQConfig.MIAOSHA_QUEUE, true, false, true, null);
        Consumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                MiaoshaMessage t = JSON.toJavaObject(JSON.parseObject(msg), MiaoshaMessage.class);
                if (redisService.get(t.getId())==null){
                    try {
                        miaoshaService.miaosha(t.getUser(), t.getMiaoshaGoodsId());
                        System.out.println("order insert success");
                        redisService.set(t.getId(), "");
                    }catch (Exception MySQLIntegrityConstraintViolationException){
                        System.out.println(t.getUser().getUsername()+":重复下单");
                    }
                }
            }
        };
        channel.basicConsume(MQConfig.MIAOSHA_QUEUE, true, defaultConsumer);*/

    }
}

package com.wsw.miaosha.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.wsw.miaosha.service.MiaoshaService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: wsw
 * @Date: 2019/9/19 17:50
 */

@Service
public class MQReceiver {

    private MiaoshaService miaoshaService;

    @Autowired
    public MQReceiver(MiaoshaService miaoshaService) {
        this.miaoshaService = miaoshaService;
    }

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void miaoshaQueueReceiver(String msg) {
        MiaoshaMessage t = JSON.toJavaObject(JSON.parseObject(msg), MiaoshaMessage.class);
        miaoshaService.miaosha(t.getUser(), t.getMiaoshaGoodsId());
        System.out.println("order insert success");
    }

    @RabbitListener(queues = MQConfig.QUEUE1)
    public void queue1Receiver(String msg) {
        System.out.println("queue1Receiver" + msg);
    }

    @RabbitListener(queues = MQConfig.QUEUE2)
    public void queue2Receiver(String msg) {
        System.out.println("queue2Receiver" + msg);
    }
}

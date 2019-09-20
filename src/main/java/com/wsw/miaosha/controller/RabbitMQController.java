package com.wsw.miaosha.controller;

import com.wsw.miaosha.rabbitmq.MQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: wsw
 * @Date: 2019/9/20 11:50
 */

@Controller
@RequestMapping(value = "/rabbitMQ")
public class RabbitMQController {

    private MQSender mqSender;

    @Autowired
    public RabbitMQController(MQSender mqSender) {
        this.mqSender = mqSender;
    }

    @RequestMapping(value = "/fonoutSender")
    @ResponseBody
    public void fonoutSender() {
        mqSender.fonoutSender("fonout");
    }

    @RequestMapping(value = "/directSender")
    @ResponseBody
    public void directSender() {
        mqSender.directSender("direct");
    }

    @RequestMapping(value = "/topicSender")
    @ResponseBody
    public void topicSender() {
        mqSender.topicSender("topic");
    }

    @RequestMapping(value = "/headersSender")
    @ResponseBody
    public void headersSender() {
        mqSender.headersSender("headers");
    }
}

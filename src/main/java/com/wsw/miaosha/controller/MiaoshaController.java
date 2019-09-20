package com.wsw.miaosha.controller;

import com.alibaba.fastjson.JSON;
import com.wsw.miaosha.model.User;
import com.wsw.miaosha.rabbitmq.MQSender;
import com.wsw.miaosha.rabbitmq.MiaoshaMessage;
import com.wsw.miaosha.redis.RedisService;
import com.wsw.miaosha.result.CodeMsg;
import com.wsw.miaosha.result.Result;
import com.wsw.miaosha.service.GoodsService;
import com.wsw.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: wsw
 * @Date: 2019/9/17 18:05
 */

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

    private RedisService redisService;

    private GoodsService goodsService;

    private MQSender sender;

    @Autowired
    public MiaoshaController(RedisService redisService, GoodsService goodsService, MQSender sender) {
        this.redisService = redisService;
        this.goodsService = goodsService;
        this.sender = sender;
    }

    /**
     * @param user           用户
     * @param miaoshaGoodsId 秒杀ID
     * @return 使用redis缓存QPS：20
     * 使用rabbitMQ QPS:300
     */
    @RequestMapping("/do_miaosha")
    @ResponseBody
    public Result miaosha(User user, Long miaoshaGoodsId) {
        //redis预减库存
        Long stock = redisService.decr("miaoshaStock" + miaoshaGoodsId);
        if (stock < 0) {
            return new Result(CodeMsg.STOCK_ERROR);
        }
        //异步执行秒杀
        MiaoshaMessage miaoshaMessage = new MiaoshaMessage(user, miaoshaGoodsId);
        String msg = JSON.toJSONString(miaoshaMessage);
        sender.miaoshaSender(msg);
        return new Result(CodeMsg.QUEUING);
/*        OrderInfo orderInfo = miaoshaService.miaosha(user,miaoshaGoodsId);
        if (orderInfo == null){
            return new Result(CodeMsg.STOCK_ERROR);
        }
        return new Result(CodeMsg.SUCCESS,orderInfo);*/
    }

    /**
     * 启动时将库存信息添加到redis中
     */
    @Override
    public void afterPropertiesSet() {
        List<GoodsVo> goodsVoList = goodsService.getGoodsVoList();
        for (GoodsVo goodsVo : goodsVoList) {
            redisService.set("miaoshaStock" + goodsVo.getMiaoshaGoodsId(), String.valueOf(goodsVo.getStockCount()));
        }
    }
}

package com.wsw.miaosha.controller;

import com.alibaba.fastjson.JSON;
import com.wsw.miaosha.model.User;
import com.wsw.miaosha.redis.RedisService;
import com.wsw.miaosha.service.GoodsService;
import com.wsw.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author: wsw
 * @Date: 2019/8/31 16:27
 */

@Controller
@RequestMapping(value = "/goods")
public class GoodsController implements InitializingBean {


    private GoodsService goodsService;

    private RedisService redisService;

    @Autowired
    public GoodsController(GoodsService goodsService,RedisService redisService) {
        this.goodsService = goodsService;
        this.redisService = redisService;
    }

    /**
     * @param model M
     * @return redis缓存前QPS:560
     * redis缓存后QPS:650
     */
    @RequestMapping(value = "/to_goods")
    public String list(Model model) {
        List<GoodsVo> goodsList = JSON.parseArray(redisService.get("goodsList"), GoodsVo.class);
        if (goodsList == null) {
            goodsList = goodsService.getGoodsVoList();
        }
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @RequestMapping(value = "/to_detail/{goodsId}")
    public String detail(User user, Model model, @PathVariable("goodsId") Long goodsId) {
        GoodsVo goodsVo = goodsService.getGoodsVoById(goodsId);
        model.addAttribute("user", user);
        model.addAttribute("goods", goodsVo);
        int remainSeconds = 0;
        int miaoshaStatus = 0;
        long start = goodsVo.getStartDate().getTime();
        long end = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();
        if (start < now && end > now) {
            //秒杀已经开始
            miaoshaStatus = 1;
        } else if (end < now) {
            //秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            remainSeconds = (int) (start - now) / 1000;
        }
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        return "goods_detail";
    }

    @Override
    public void afterPropertiesSet() {
        List<GoodsVo> goodsVoList = goodsService.getGoodsVoList();
        redisService.set("goodsList", JSON.toJSONString(goodsVoList));
    }
}

package com.wsw.miaosha.service;

import com.wsw.miaosha.dao.MiaoshaDao;
import com.wsw.miaosha.model.OrderInfo;
import com.wsw.miaosha.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: wsw
 * @Date: 2019/9/17 18:11
 */

@Service
public class MiaoshaService {

    private MiaoshaDao miaoshaDao;

    private OrderInfoService orderInfoService;

    private MiaoshaOrderService miaoshaOrderService;

    @Autowired
    public MiaoshaService(MiaoshaDao miaoshaDao, OrderInfoService orderInfoService, MiaoshaOrderService miaoshaOrderService) {
        this.miaoshaDao = miaoshaDao;
        this.orderInfoService = orderInfoService;
        this.miaoshaOrderService = miaoshaOrderService;
    }

    @Transactional
    public OrderInfo miaosha(User user, Long miaoshaGoodsId) {
        //1.减库存
        int row = miaoshaDao.reduceStock(miaoshaGoodsId);
        OrderInfo orderInfo = null;
        if (row == 1) {
            //2.生成订单
            orderInfo = orderInfoService.create(user, miaoshaGoodsId);
            //生成秒杀订单
            miaoshaOrderService.create(user, orderInfo);
        }
        return orderInfo;
    }

}

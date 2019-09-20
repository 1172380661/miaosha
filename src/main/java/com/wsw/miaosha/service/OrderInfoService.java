package com.wsw.miaosha.service;

import com.wsw.miaosha.dao.OrderInfoDao;
import com.wsw.miaosha.model.OrderInfo;
import com.wsw.miaosha.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: wsw
 * @Date: 2019/9/17 19:47
 */

@Service
public class OrderInfoService {

    private OrderInfoDao orderInfoDao;

    @Autowired
    public OrderInfoService(OrderInfoDao orderInfoDao) {
        this.orderInfoDao = orderInfoDao;
    }

    public OrderInfo create(User user, Long miaoshaGoodsId) {
        OrderInfo orderInfo = orderInfoDao.getOrderInfoByMiaoshaGoodsId(miaoshaGoodsId);
        orderInfo.setUserId(user.getId());
        orderInfo.setGoodsCount(1);
        orderInfoDao.insert(orderInfo);
        return orderInfo;
    }
}

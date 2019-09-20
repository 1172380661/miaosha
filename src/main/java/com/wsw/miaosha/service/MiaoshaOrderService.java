package com.wsw.miaosha.service;

import com.wsw.miaosha.dao.MiaoshaOrderDao;
import com.wsw.miaosha.model.MiaoshaOrder;
import com.wsw.miaosha.model.OrderInfo;
import com.wsw.miaosha.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: wsw
 * @Date: 2019/9/17 19:47
 */

@Service
public class MiaoshaOrderService {

    private MiaoshaOrderDao miaoshaOrderDao;

    @Autowired
    public MiaoshaOrderService(MiaoshaOrderDao miaoshaOrderDao) {
        this.miaoshaOrderDao = miaoshaOrderDao;
    }

    public void create(User user, OrderInfo orderInfo) {
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder(user.getId(), orderInfo.getId(), orderInfo.getGoodsId());
        miaoshaOrderDao.insert(miaoshaOrder);
    }
}

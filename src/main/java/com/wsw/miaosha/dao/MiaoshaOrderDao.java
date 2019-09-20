package com.wsw.miaosha.dao;

import com.wsw.miaosha.model.MiaoshaOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Author: wsw
 * @Date: 2019/9/17 19:46
 */

@Mapper
@Component
public interface MiaoshaOrderDao {

    @Insert("INSERT INTO miaosha_order(user_id,order_id,goods_id) VALUES (#{userId},#{orderId},#{goodsId}) ")
    void insert(MiaoshaOrder miaoshaOrder);
}

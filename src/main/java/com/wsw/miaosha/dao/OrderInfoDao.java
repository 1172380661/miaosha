package com.wsw.miaosha.dao;

import com.wsw.miaosha.model.OrderInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @Author: wsw
 * @Date: 2019/9/17 19:46
 */

@Mapper
@Component
public interface OrderInfoDao {

    /**
     * 插入orderInfo表
     *
     * @param orderInfo order实体
     */
    @Insert("INSERT INTO order_info(user_id,goods_id,goods_name,goods_count,goods_price) VALUES (#{userId},#{goodsId},#{goodsName},#{goodsCount},#{goodsPrice})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void insert(OrderInfo orderInfo);

    /**
     * 根据秒杀商品ID生成订单信息
     *
     * @param miaoshaGoodsId 秒杀商品ID
     * @return 订单信息
     */
    @Select("SELECT g.id goodsId,g.goods_name,mg.miaosha_price FROM miaosha_goods mg LEFT JOIN goods g ON mg.goods_id=g.id  WHERE mg.id = #{miaoshaGoodsId}")
    OrderInfo getOrderInfoByMiaoshaGoodsId(@Param("miaoshaGoodsId") Long miaoshaGoodsId);
}

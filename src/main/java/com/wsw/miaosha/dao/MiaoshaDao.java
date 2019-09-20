package com.wsw.miaosha.dao;

import com.wsw.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @Author: wsw
 * @Date: 2019/9/17 18:13
 */

@Mapper
@Component
public interface MiaoshaDao {
    @Select("SELECT stock_count FROM miaosha_goods WHERE id = #{miaoshaGoodsId}")
    int getStock(@Param("miaoshaGoodsId") Long miaoshaGoodsId);

    @Update("UPDATE miaosha_goods SET stock_count = stock_count - 1 WHERE id = #{miaoshaGoodsId} and stock_count>0 ")
    int reduceStock(@Param("miaoshaGoodsId") Long miaoshaGoodsId);

    @Select("SELECT g.id,mg.id miaoshaGoodsId,goods_name,goods_img,goods_price,miaosha_price,goods_stock,stock_count,mg.start_date,mg.end_date FROM goods g RIGHT JOIN miaosha_goods mg on g.id = mg.goods_id WHERE mg.id = #{miaoshaGoodsId}")
    GoodsVo getGoodsVoByMiaoshaGoodsId(@Param("miaoshaGoodsId") Long miaoshaGoodsId);
}

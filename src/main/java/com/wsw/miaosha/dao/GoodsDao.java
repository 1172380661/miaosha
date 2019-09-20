package com.wsw.miaosha.dao;

import com.wsw.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: wsw
 * @Date: 2019/8/31 16:37
 */

@Mapper
@Component
public interface GoodsDao {

    @Select("select g.id,mg.id miaoshaGoodsId,goods_name,goods_img,goods_price,miaosha_price,goods_stock,stock_count FROM goods g LEFT JOIN miaosha_goods mg on mg.goods_id=g.id")
    List<GoodsVo> getGoodsVoList();

    @Select("select g.id,mg.id miaoshaGoodsId,goods_name,goods_img,goods_price,miaosha_price,goods_stock,stock_count,mg.start_date,mg.end_date FROM goods g LEFT JOIN miaosha_goods mg on mg.goods_id=g.id where g.id=#{goodsId}")
    GoodsVo getGoodsVoById(@Param("goodsId") Long goodsId);
}

package com.wsw.miaosha.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: wsw
 * @Date: 2019/8/31 17:07
 */

@Data
public class GoodsVo {
    private Long id;
    private Long miaoshaGoodsId;
    private String goodsName;
    private String goodsImg;
    private Double goodsPrice;
    private Integer goodsStock;
    private Integer stockCount;
    private Double miaoshaPrice;
    private Integer miaoshaStatus;
    private Date startDate;
    private Date endDate;
}

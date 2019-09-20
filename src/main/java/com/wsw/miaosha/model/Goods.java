package com.wsw.miaosha.model;

import lombok.Data;

/**
 * @Author: wsw
 * @Date: 2019/8/31 16:35
 */

@Data
public class Goods {
    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;
}

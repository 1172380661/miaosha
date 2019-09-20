package com.wsw.miaosha.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: wsw
 * @Date: 2019/8/31 17:06
 */

@Data
public class MiaoshaGoods {
    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Double stockPrice;
    private Date startDate;
    private Date endDate;
}

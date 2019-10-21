package com.wsw.miaosha.rabbitmq;

import com.wsw.miaosha.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wsw
 * @Date: 2019/6/20 14:12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiaoshaMessage {
    private String id;
    private User user;
    private Long miaoshaGoodsId;
}

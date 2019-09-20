package com.wsw.miaosha.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @Author: wsw
 * @Date: 2019/9/17 18:00
 */

@Data
@RequiredArgsConstructor
public class MiaoshaOrder {
    private Long id;
    @NonNull
    private Long userId;
    @NonNull
    private Long orderId;
    @NonNull
    private Long goodsId;
}

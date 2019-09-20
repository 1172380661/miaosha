package com.wsw.miaosha.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: wsw
 * @Date: 2019/8/26 21:20
 */

@Data
public class LoginVo {

    @NotNull
    private Long mobile;
    @NotNull
    private String password;
}

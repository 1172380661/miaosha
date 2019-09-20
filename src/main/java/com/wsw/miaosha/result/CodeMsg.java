package com.wsw.miaosha.result;

import lombok.Data;

/**
 * @Author: wsw
 * @Date: 2019/8/26 20:53
 */

@Data
public class CodeMsg {
    private String msg;
    private int code;
    public static final CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static final CodeMsg QUEUING = new CodeMsg(1, "排队中");
    //通用错误码 5001XX
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
    //登录模块 5002XX
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登录密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号错误");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "手机号不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");
    //秒杀模块 5003xx
    public static CodeMsg STOCK_ERROR = new CodeMsg(500215, "库存不足");
    public static CodeMsg REPEAT_MIAOSHA = new CodeMsg(500215, "重复秒杀");

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg fillArgs(String str) {
        String msg = String.format(this.msg, str);
        return new CodeMsg(this.code, msg);
    }
}

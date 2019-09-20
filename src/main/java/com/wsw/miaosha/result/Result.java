package com.wsw.miaosha.result;

import lombok.Data;

/**
 * @Author: wsw
 * @Date: 2019/8/26 20:59
 */

@Data
public class Result<T> {
    private String msg;
    private int code;
    private CodeMsg codeMsg;
    private T data;

    public Result(CodeMsg codeMsg) {
        this.msg = codeMsg.getMsg();
        this.code = codeMsg.getCode();
    }

    public Result(CodeMsg codeMsg, T data) {
        this.msg = codeMsg.getMsg();
        this.code = codeMsg.getCode();
        this.data = data;
    }
}

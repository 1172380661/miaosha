package com.wsw.miaosha.exception;

import com.wsw.miaosha.result.CodeMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: wsw
 * @Date: 2019/8/27 9:28
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class GlobalException extends RuntimeException {
    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }
}

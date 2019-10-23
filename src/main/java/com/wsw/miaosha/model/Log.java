package com.wsw.miaosha.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * @Author: wsw
 * @Date: 2019/10/22 11:49
 */

@Data
public class Log {
    private Integer id;
    private String methodName;
    private String errorInfo;
    private Date createTime;

    public Log(String methodName, String errorInfo, Date createTime) {
        this.methodName = methodName;
        this.errorInfo = errorInfo;
        this.createTime = createTime;
    }
}

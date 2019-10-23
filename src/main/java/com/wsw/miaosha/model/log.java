package com.wsw.miaosha.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: wsw
 * @Date: 2019/10/22 11:49
 */

@Data
public class log {
    private Integer id;
    private String classInfo;
    private Long executionTime;
    private Date createTime;
}

package com.wsw.miaosha.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: wsw
 * @Date: 2019/8/26 20:30
 */

@Data
@ToString
public class User {
    private Long id;
    private String username;
    private String password;
    private String salt;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;
}

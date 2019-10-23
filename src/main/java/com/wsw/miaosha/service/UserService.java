package com.wsw.miaosha.service;

import com.alibaba.fastjson.JSON;
import com.wsw.miaosha.dao.UserDao;
import com.wsw.miaosha.exception.GlobalException;
import com.wsw.miaosha.model.User;
import com.wsw.miaosha.redis.RedisService;
import com.wsw.miaosha.result.CodeMsg;
import com.wsw.miaosha.util.MD5Util;
import com.wsw.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author: wsw
 * @Date: 2019/8/26 21:09
 */

@Service
public class UserService {


    private UserDao userDao;

    private RedisService redisService;

    @Autowired
    public UserService(UserDao userDao,RedisService redisService) {
        this.userDao = userDao;
        this.redisService = redisService;
    }

    public User getUserByToken(String token) {
        return JSON.toJavaObject(JSON.parseObject(token), User.class);
    }

    public String login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        Long username = loginVo.getMobile();
        User user = userDao.getByUserName(username);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        String salt = user.getSalt();
        if (!MD5Util.formPassToDBPass(loginVo.getPassword(),salt).equals(user.getPassword())) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        String token = UUID.randomUUID().toString().replace("-","");
        addCookie(response, token, user);
        return token;
    }

    private void addCookie(HttpServletResponse response, String token, User user) {
        redisService.setex(token, 60 * 60 * 24, JSON.toJSONString(user));
        Cookie cookie = new Cookie("TOKEN", token);
        cookie.setPath("/");
        cookie.setMaxAge(3600 * 24);
        response.addCookie(cookie);
    }
}

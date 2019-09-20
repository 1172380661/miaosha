package com.wsw.miaosha.parameresolver;

import com.wsw.miaosha.model.User;
import com.wsw.miaosha.redis.RedisService;
import com.wsw.miaosha.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: wsw
 * @Date: 2019/9/1 19:04
 */

@Service
public class UserResolver implements HandlerMethodArgumentResolver {

    private UserService userService;

    private RedisService redisService;

    public UserResolver(UserService userService, RedisService redisService) {
        this.userService = userService;
        this.redisService = redisService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws ServletException, IOException {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        assert request != null;
        User user = getUser(request);
        if (user == null) {
            request.getRequestDispatcher("/login/to_login").forward(request, response);
        }
        return user;
    }

    private User getUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        for (Cookie cookie : cookies) {
            if ("TOKEN".equals(cookie.getName())) {
                token = cookie.getValue();
            }
        }
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return userService.getUserByToken(redisService.get(token));
    }
}

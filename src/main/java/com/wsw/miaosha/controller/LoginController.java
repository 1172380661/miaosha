package com.wsw.miaosha.controller;

import com.wsw.miaosha.result.CodeMsg;
import com.wsw.miaosha.result.Result;
import com.wsw.miaosha.service.UserService;
import com.wsw.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author: wsw
 * @Date: 2019/8/26 20:27
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result doLogin(@Valid LoginVo loginVo, HttpServletResponse response) {
        String token = userService.login(response, loginVo);
        return new Result<>(CodeMsg.SUCCESS, token);
    }

}

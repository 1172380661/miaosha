package com.wsw.miaosha.configure;

import com.wsw.miaosha.parameresolver.UserResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author: wsw
 * @Date: 2019/8/31 19:33
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private UserResolver userResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userResolver);
    }
}

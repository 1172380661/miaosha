package com.wsw.miaosha.dao;

import com.wsw.miaosha.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @Author: wsw
 * @Date: 2019/8/26 20:32
 */

@Mapper
@Component
public interface UserDao {

    /**
     * 根据userId获取user
     *
     * @param username userId
     * @return user
     */
    @Select("select * from user where username = #{username}")
    User getByUserName(@Param("username") Long username);

}

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
     * @param id userId
     * @return user
     */
    @Select("select * from user where id = #{id}")
    User getById(@Param("id") Long id);

}

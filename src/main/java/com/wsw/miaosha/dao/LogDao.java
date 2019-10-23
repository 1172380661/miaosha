package com.wsw.miaosha.dao;

import com.wsw.miaosha.model.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Author: wsw
 * @Date: 2019/10/23 17:41
 */

@Mapper
@Component
public interface LogDao {
    @Insert("INSERT INTO log(method_name,error_info,create_time) values (#{methodName},#{errorInfo},#{createTime})")
    void addLog(Log log);
}

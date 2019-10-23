package com.wsw.miaosha.service;

import com.wsw.miaosha.dao.LogDao;
import com.wsw.miaosha.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: wsw
 * @Date: 2019/10/23 17:40
 */

@Service
public class LogService {

    private LogDao logDao;

    @Autowired
    public LogService(LogDao logDao) {
        this.logDao = logDao;
    }

    public void addLog(Log log){
        logDao.addLog(log);
    }
}

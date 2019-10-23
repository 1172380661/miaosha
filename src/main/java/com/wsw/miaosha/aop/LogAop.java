package com.wsw.miaosha.aop;

import com.alibaba.fastjson.JSONObject;
import com.wsw.miaosha.exception.GlobalExceptionHandler;
import com.wsw.miaosha.model.Log;
import com.wsw.miaosha.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: wsw
 * @Date: 2019/10/22 11:55
 */
@Aspect
@Service
public class LogAop {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAop.class);

    private LogService logService;

    @Autowired
    public LogAop(LogService logService) {
        this.logService = logService;
    }

    @Pointcut("execution(public * com.wsw.miaosha.controller..*.*(..))")
    public void controllerMethod() {
    }

    @Around(value = "controllerMethod()")
    public Object around(ProceedingJoinPoint joinPoint){
        //获取类名、方法名、参数列表
        String className = joinPoint.getTarget().getClass().getSimpleName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        Object[] joinPointArgs = joinPoint.getArgs();
        //拼接成前置通知日志
        StringBuilder beforeLog = new StringBuilder(new Date().toString()+":");
        beforeLog.append("访问").append(className).append(":").append(methodName).append("。参数列表：");
        for (int i = 0; i< joinPointArgs.length; i++){
            beforeLog.append(joinPointArgs[i].getClass().getSimpleName());
            if (i!=joinPointArgs.length-1){
                beforeLog.append(",");
            }
        }
        LOGGER.info(beforeLog.toString());
        try {
            //返回通知
            Object result = joinPoint.proceed();
            LOGGER.info("方法正常返回"+ JSONObject.toJSONString(result));
            return result;
        }catch (Throwable e){
            //异常通知
            LOGGER.warn("================发生异常请及时检查==============");
            e.printStackTrace();
            logService.addLog(new Log(methodName,e.getMessage(),new Date()));
            return GlobalExceptionHandler.exceptionHandler(e);
        }
    }
}

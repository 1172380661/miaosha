package com.wsw.miaosha.aop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


/**
 * @Author: wsw
 * @Date: 2019/10/22 11:55
 */
@Aspect
public class LogAop {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAop.class);

    @Pointcut("execution(public * com.wsw.miaosha.controller..*.*(..))")
    public void controllerMethod() {
    }

    @Around(value = "controllerMethod()")
    public Object around(ProceedingJoinPoint joinPoint){
        Date date = new Date();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        StringBuilder beforeLog = new StringBuilder(date.toString());
        beforeLog.append("访问").append(className).append(":").append(methodName).append("====参数列表：");
        for (Object o:joinPoint.getArgs()){
            beforeLog.append(o.getClass().getSimpleName()).append(",");
        }
        LOGGER.info(beforeLog.toString());
        try {
            Object result = joinPoint.proceed();
            LOGGER.info("方法正常返回"+ JSONObject.toJSONString(result));
            return result;
        }catch (Throwable e){
            LOGGER.warn("================发生异常请及时检查==============");
            return null;
        }
    }


}

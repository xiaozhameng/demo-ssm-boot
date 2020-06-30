package com.xiaozhameng.ssm.boot.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.xiaozhameng.hk.api.common.Result;
import com.xiaozhameng.hk.api.enums.CommonResCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * mq 消费端切面类
 *
 * @author xiaozhameng
 */
@Aspect
@Component
public class ControllerInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("execution(* com.xiaozhameng.ssm.boot.controller.*.*(..))")
    public Object process(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Object[] arguments = joinPoint.getArgs();

        final String className = targetMethod.getDeclaringClass().getSimpleName();
        final String methodName = targetMethod.getName();

        Object req = arguments == null ? null : arguments[0];
        final long startTime = System.currentTimeMillis();
        try {
            // 目标调用
            Object result = joinPoint.proceed();
            logger.info("统一日志拦截 - {}.{} 请求结束, 耗时={} ms, 入参={}, 结果={}",
                    className,
                    methodName,
                    System.currentTimeMillis() - startTime,
                    JSONObject.toJSONString(req),
                    JSONObject.toJSONString(result));
            return result;
        } catch (Throwable th) {
            String errorMessage = String.format("统一日志拦截 - %s.%s 处理发生异常=%s", className, methodName, th.getMessage());
            logger.error(errorMessage,th);
            Result<Object> res = Result.of();
            res.setMessage(errorMessage);
            res.setCode(CommonResCode.FAILED.getCode());
            return res;
        }
    }
}
package com.yangfei.functionTask.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;

/**
 * <p>
 *  异步异常处理类：
 *  该处理嘞目前发现只处理异步方法中抛出的异常，不处理拒绝策略中抛出的异常
 * </p>
 *
 * @author yangfei
 * @since 2022/7/28 17:23
 */
@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        log.info("Async method: {} has uncaught exception,params:{}", method.getName(), JSONUtil.toJsonStr(objects));
        if (throwable instanceof AsyncException) {
            AsyncException asyncException = (AsyncException) throwable;
            log.info("asyncException:{}",asyncException.getErrorMessage());
        }
        log.info("Exception :");
        throwable.printStackTrace();
    }
}

package com.yangfei.functionTask.config;

import com.yangfei.functionTask.entity.User;
import com.yangfei.functionTask.exception.AsyncException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Field;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *  自定义拒绝策略
 *  实现拒绝策略需要实现RejectedExecutionHandler接口，然后实现rejectedExecution方法即可。
 *  该方法两个参数，第一个就是被拒绝的Runnable任务，第二个是你使用的线程池对象。
 * </p>
 *
 * @author yangfei
 * @since 2019-05-18
 */
@Slf4j
public class CustomRejectionHandler implements RejectedExecutionHandler {
    public AtomicInteger rejectCount = new AtomicInteger(0);
    @SneakyThrows
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        int threadCount = rejectCount.addAndGet(1);
        log.error(r.toString() + " is discard");
        log.info("多线程调用imoc设备接口，线程拒绝次数{}",rejectCount);
        // 通过反射获取异步任务参数
        Field field = r.getClass().getDeclaredField("callable");
        field.setAccessible(true);
        Callable callable = (Callable) field.get(r);
        Field invocationField = callable.getClass().getDeclaredField("arg$2");
        invocationField.setAccessible(true);
        MethodInvocation invocation = (MethodInvocation) invocationField.get(callable);

        //类信息


        String classDetailInfo = invocation.getMethod().toString();
        String className = invocation.getMethod().getDeclaringClass().toString();

        log.info(
                "classDetainInfo:{},className:{}",
                classDetailInfo,className);
        //业务方法名称
        String methodName = invocation.getMethod().getName();
        log.info("methodName:{}",methodName);
        //业务参数
        Object[] args = invocation.getArguments();
        if (args.length > 0) {
            if (args[0] instanceof User) {
                User userParam = (User) args[0];
                log.info("业务参数：{}",userParam.toString());
            } else if (args[0] instanceof Long) {
                Long param = (Long) args[0];
                log.info("业务参数：{}",param);
            } else {
                log.info("未匹配到参数类型");
            }

        }


        //throw new AsyncException(500, "线程池异常");
    }
}

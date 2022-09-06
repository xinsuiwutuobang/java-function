package com.yangfei.functionTask.config;

import com.yangfei.functionTask.exception.AsyncExceptionHandler;
import com.yangfei.functionTask.properties.ImocExecutorProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * <p>
 *  Async配置线程池以及异常处理
 *  注：该异常处理只处理业务方法中抛出的异常，拒绝策略抛出的异常不会进入异常处理函数
 * </p>
 *
 * @author yangfei
 * @since 2022/7/28 17:28
 */
@Configuration
@Slf4j
public class AsyncConfig implements AsyncConfigurer {
    @Autowired
    ImocExecutorProperties imocExecutorProperties;

    /**
     * 自定义线程池，控制并发数，
     * @return
     */
    @Bean
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(imocExecutorProperties.getCorePoolSize());
        executor.setMaxPoolSize(imocExecutorProperties.getMaxPoolSize());
        executor.setQueueCapacity(imocExecutorProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(imocExecutorProperties.getKeepAliveSeconds());
        executor.setThreadNamePrefix(imocExecutorProperties.getThreadNamePrefix());
        //executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setRejectedExecutionHandler(new CustomRejectionHandler());
        executor.initialize();

        return executor;
    }

    /**
     * 统一处理异常
     * 只能捕获异步方法中抛出的异常，拒绝策略异常不会被捕获
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }
}

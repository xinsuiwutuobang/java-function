package com.yangfei.functionTask.config;

import com.yangfei.functionTask.properties.ImocExecutorProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 *  只自定义线程池，通过@Async指定该线程池，这种方式无法配置统一异常处理
 * </p>
 *
 * @author yangfei
 * @since 2022/7/27 17:36
 */
@Configuration
@Slf4j
@Profile({"prod"})
public class TaskConfig {
    @Autowired
    ImocExecutorProperties imocExecutorProperties;
    @Bean("imocExcutor")
    public Executor imocExcutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(imocExecutorProperties.getCorePoolSize());
        executor.setMaxPoolSize(imocExecutorProperties.getMaxPoolSize());
        executor.setQueueCapacity(imocExecutorProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(imocExecutorProperties.getKeepAliveSeconds());
        executor.setThreadNamePrefix(imocExecutorProperties.getThreadNamePrefix());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //executor.setRejectedExecutionHandler(new CustomRejectionHandler());
        executor.initialize();

        return executor;
    }
}

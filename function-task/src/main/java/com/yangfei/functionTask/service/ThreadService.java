package com.yangfei.functionTask.service;

import com.yangfei.functionTask.entity.User;
import com.yangfei.functionTask.exception.AsyncException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.stream.LongStream;

import cn.hutool.extra.spring.SpringUtil;

/**
 * <p>
 *
 * </p>
 *
 * @author yangfei
 * @since 2022/7/27 17:56
 */
@Service
@Slf4j
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class ThreadService {
    //@Async("imocExcutor")
    @Async()
    public void addThread(Long id) throws InterruptedException, AsyncException {
        log.info("call method addThread");
        Thread.sleep(10 * 1000);
        //throw new AsyncException(500, "------");
    }

    @Async()
    public void addThreadObject(User suer) throws InterruptedException, AsyncException {
        log.info("call method addThreadObject");
        Thread.sleep(10 * 1000);
        //throw new AsyncException(500, "------");
    }
    public Object addThreads(Long id) {
        log.info("call method addThreads");
        if (id != null && id > 0) {
            LongStream.rangeClosed(1,id).forEach(n -> {
                try {
                    //((ThreadService) AopContext.currentProxy()).addThread(n);
                    ThreadService bean = SpringUtil.getBean(this.getClass());
                    bean.addThread(n);
                    bean.addThreadObject(new User(n, String.valueOf(n)));
                } catch (InterruptedException | AsyncException e) {
                    e.printStackTrace();
                }
            });
        }
        return id;
    }
}

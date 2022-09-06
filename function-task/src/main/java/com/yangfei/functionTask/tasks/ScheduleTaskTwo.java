package com.yangfei.functionTask.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author yangfei
 * @since 2022/7/27 15:47
 */
/*@Component*/
@Slf4j
public class ScheduleTaskTwo {
    @Scheduled(cron = "0/1 * * * * ?")
    public void One() throws InterruptedException {
        Thread.sleep(1 * 1000L);
        log.info(String.format("Two:执行方法One：休眠1秒，执行完成时间%d", System.currentTimeMillis()));
    }
    @Scheduled(cron = "0/1 * * * * ?")
    public void Two() throws InterruptedException {
        Thread.sleep(5 * 1000L);
        log.info(String.format("Two:执行方法One：休眠5秒，执行完成时间%d", System.currentTimeMillis()));
    }
}

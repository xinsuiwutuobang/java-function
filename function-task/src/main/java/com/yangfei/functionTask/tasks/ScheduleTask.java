package com.yangfei.functionTask.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  默认多个@Scheduled 执行任务时，使用同一个线程，一个@Scheduled执行完毕后，另一个才开始
 *  加入每分钟执行一次，逻辑执行了61s，下次执行时间为两分钟后。
 *
 * </p>
 *
 * @author yangfei
 * @since 2022/7/27 15:11
 */
@Configuration
@Slf4j
public class ScheduleTask {
    @Scheduled(cron = "*/5 * * * * ?")
    @Async
    public void One() throws InterruptedException {
        log.info(String.format("start:One:执行方法One：休眠1秒，执行完成时间%d", System.currentTimeMillis()));
        Thread.sleep(5 * 1000L);
        log.info(String.format("end:One:执行方法One：休眠1秒，执行完成时间%d", System.currentTimeMillis()));
    }
//    @Scheduled(cron = "*/1 * * * * ?")
    public void Two() throws InterruptedException {
        log.info(String.format("start:Two:执行方法Two：休眠5秒，执行完成时间%d", System.currentTimeMillis()));
        Thread.sleep(2 * 1000L);
        log.info(String.format("end:Two:执行方法Two：休眠5秒，执行完成时间%d", System.currentTimeMillis()));
    }

//    @Scheduled(cron = "*/1 * * * * ?")
    public void Three() throws InterruptedException {
        log.info(String.format("start:Three:执行方法Three：休眠5秒，执行完成时间%d", System.currentTimeMillis()));
        Thread.sleep(10 * 1000L);
        log.info(String.format("end:Three:执行方法Three：休眠5秒，执行完成时间%d", System.currentTimeMillis()));
    }
}

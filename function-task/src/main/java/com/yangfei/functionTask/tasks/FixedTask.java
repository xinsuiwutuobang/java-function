package com.yangfei.functionTask.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  无论是Cron方式，还是固定间隔，固定频率，所有的任务都串式执行，也就是一个任务执行完毕后，另一个任务才可能执行。
 *  Spring的Schecule默认是单线程执行的，如果你定义了多个任务，那么他们将会被串行执行，会严重不满足你的预期。
 * </p>
 *
 * @author yangfei
 * @since 2022/7/27 16:02
 */
/*@Component*/
@Slf4j
public class FixedTask {
    /**
     * 固定间隔，等上一次调度完成后，才开始计算间隔然后执行
     */
    @Scheduled(fixedDelay = 1000)
    public void fixedDelaySchedule() throws InterruptedException {
        log.info("start:fixedDelay = 1000");
        Thread.sleep(10000);
        log.info("end:fixedDelay = 1000");
    }
    /**
     * 固定间隔，等上一次调度完成后，才开始计算间隔然后执行
     * nitialDelay属性跟上面的fixedDelay、fixedRate有着密切的关系，该属性的作用是第一次执行延迟时间，只是做延迟的设定，并不会控制其他逻辑，所以要配合fixedDelay或者fixedRate来使用。
     */
    @Scheduled(initialDelay = 1000,fixedDelay = 10000)
    public void fixedInitialDelaySchedule() throws InterruptedException {
        log.info("start:fixedInitialDelaySchedule = 1000");
        Thread.sleep(10000);
        log.info("end:fixedInitialDelaySchedule = 1000");
    }
    /**
     * 固定频率，如果上次调度超过了频率时间，那么在其完成后，立刻执行
     */
    @Scheduled(fixedRate = 1000)
    public void fixedRateSchedule() throws InterruptedException {
        log.info("start:fixedRate = 1000");
        Thread.sleep(10000);
        log.info("end:fixedRate = 1000");
    }
}
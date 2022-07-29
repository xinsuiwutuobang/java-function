package com.yangfei.functionTask.controller;

import com.yangfei.functionTask.exception.AsyncException;
import com.yangfei.functionTask.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author yangfei
 * @since 2022/7/27 15:22
 */
@RestController
@RequestMapping("/task")
public class ScheduleTaskController {
    @Autowired
    private ThreadService threadService;

    @RequestMapping("/addThread")
    public Object addThread(Long id) throws InterruptedException, AsyncException {
        threadService.addThread(id);
        return id;
    }

    @RequestMapping("/addThreads")
    public Object addThreads(Long id) {
        Object ret = threadService.addThreads(id);
        return ret;
    }
}
package com.yangfei.functionspringconfiguration.controller;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxyBeanMethods")
public class ProxyBeanMethodsController {
    @RequestMapping("/test")
    public Object test() {
        Object proxyBeanMethodsUtil1 = SpringUtil.getBean("proxyBeanMethodsUtil1");
        return "ok";
    }
}

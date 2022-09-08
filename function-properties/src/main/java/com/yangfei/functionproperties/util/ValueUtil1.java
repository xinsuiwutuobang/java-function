package com.yangfei.functionproperties.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 类增加@Component注解
 * @PostConstruct 注解修饰非静态方法，在方法中将非静态变量的赋值给静态变量
 */
@Component
public class ValueUtil1 {
    @Value("${mail.host}")
    private String userName;
    public static String mailUserName;
    @PostConstruct
    public void init() {
        mailUserName = userName;
    }
}

package com.yangfei.functionproperties.util;

import cn.hutool.extra.spring.SpringUtil;
import com.yangfei.functionproperties.properties.MailProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 给静态变量赋值，需要用到set()方法(set方法需要为非静态方法)，
 * 同时类上需要加入@Component注解，方法名和参数名可以任意命名。
 */
@Component
public class ValueUtil {
    private static String mailHost;
    private static String mailUserName;

    private static String propertiesValue;

    @Value("${mail.host}")
    public void setMailUsername(String mailUsername) {
        ValueUtil.mailUserName = mailUsername;
    }
    @Value("${mail.username}")
    public void setMailHost(String mailHost) {
        ValueUtil.mailHost = mailHost;
    }

    public static String getValue() {
        return mailHost + mailUserName;
    }

    public static String getPropertiesValue() {
        MailProperties properties = SpringUtil.getBean(MailProperties.class);
        propertiesValue = properties.getUsername();
        return propertiesValue;
    }
    public static void main(String[] args) {

    }

}

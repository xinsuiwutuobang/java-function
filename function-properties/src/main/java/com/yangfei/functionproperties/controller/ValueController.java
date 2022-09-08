package com.yangfei.functionproperties.controller;

import com.yangfei.functionproperties.properties.MailProperties;
import com.yangfei.functionproperties.util.ValueUtil;
import com.yangfei.functionproperties.util.ValueUtil1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/value")
@RestController
public class ValueController {
    @Value("${mail.host}")
    private String mailHost;
    @Value("${mail.username}")
    private String mailUserName;
    @Value("${mail.password}")
    private String mailPassword;
    @Value("${mail.command}")
    private String command;
    @Autowired
    private MailProperties mailProperties;

    @RequestMapping("/getValue")
    public Object getValue() {
        return command + mailHost + mailPassword + mailUserName;
    }

    @RequestMapping("/getStaticValue")
    public Object getStaticValue() {
        return ValueUtil.getValue();
    }

    @RequestMapping("/getStaticValue1")
    public Object getStaticValue1() {
        return ValueUtil1.mailUserName;
    }

    @RequestMapping("/mailProperties")
    public Object mailProperties() {
        return mailProperties.toString();
    }

    @RequestMapping("/getPropertiesValue")
    public Object getPropertiesValue() {
        return ValueUtil.getPropertiesValue();
    }
}

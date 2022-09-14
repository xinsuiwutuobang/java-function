package com.yangfei.functionspringconfiguration.config;

import com.yangfei.functionspringconfiguration.util.ProxyBeanMethodsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class ProxyBeanMethodsConfig {
    @Bean
    public ProxyBeanMethodsUtil proxyBeanMethodsUtil1() {
        log.info("proxyBeanMethodsUtil1 方法");
        proxyBeanMethodsUtil2();
        return new ProxyBeanMethodsUtil("yang");
    }
    @Bean
    public ProxyBeanMethodsUtil proxyBeanMethodsUtil2() {
        log.info("proxyBeanMethodsUtil2 方法");

        return new ProxyBeanMethodsUtil("fei");
    }

}

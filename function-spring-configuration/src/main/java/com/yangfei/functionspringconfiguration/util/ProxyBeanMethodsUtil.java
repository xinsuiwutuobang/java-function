package com.yangfei.functionspringconfiguration.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ProxyBeanMethodsUtil {
    private String name;

    public void welcome(String costomer) {
        log.info("welcome:{}",costomer);
    }
}

package com.yangfei.functionproperties.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "mail")
public class MailProperties {
    private String username;
    private String password;
    private String host;
    private String commond;
}

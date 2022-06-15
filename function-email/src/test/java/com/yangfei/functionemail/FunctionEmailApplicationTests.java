package com.yangfei.functionemail;

import com.yangfei.functionemail.common.email.MailService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import cn.hutool.core.map.MapUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
class FunctionEmailApplicationTests {
    @Autowired
    private MailService mailService;
    private String toAddress = "fei.yang@joyoudata.com";
    private String context = "hello word";
    private String picUrl = "http://b.infotalks.cn/images/screenshot_1578753369258.png";
    private String picLocation = "C:\\Users\\Administrator\\Pictures\\lf\\1.png";
    @Test
    void sendTextMail() {
        mailService.sendTextMail(toAddress,"sendTextMail",context);
    }

    @Test
    void sendHtmlMail() {
        mailService.sendHtmlMail(toAddress,"sendHtmlMail",context);
    }
    @Test
    void sendHtmlTemplateMail() {
        mailService.sendHtmlTemplateMail(toAddress, "sendHtmlTemplateMail", "email/reg-code",
                MapUtil.of("code", "1234qwer"));
    }
    @Test
    void sendAttachmentsMail() {
        mailService.sendAttachmentsMail(toAddress, "sendAttachmentsMail", context, picLocation);
    }
    @Test
    void sendInlineResourceMail() {
        mailService
                .sendInlineResourceMail(toAddress, "sendInlineResourceMail", context, picLocation,
                        "rscId");
    }



}

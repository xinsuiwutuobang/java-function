package com.yangfei.functionemail.common.email;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  邮件服务类
 * </p>
 *
 * @author yangfei
 * @since 2022/5/18 19:21
 */
@Service
public interface MailService {
    /**
     * 发送 纯文本 邮件
     * @param toAddress
     * @param title
     * @param content
     */
    void sendTextMail(String toAddress, String title, String content);

    /**
     * 发送 html 邮件
     * @param toAddress
     * @param title
     * @param content
     */
    void sendHtmlMail(String toAddress, String title, String content);

    /**
     * 发送 html模板 邮件
     * @param toAddress
     * @param title
     * @param template
     * @param params
     */
    void sendHtmlTemplateMail(String toAddress, String title, String template,
            Map<String, Object> params);

    /**
     * 发送 带附件 的邮件
     * @param toAddress
     * @param title
     * @param content
     * @param filePath
     */
    void sendAttachmentsMail(String toAddress, String title, String content, String filePath);

    /**
     * 发送 文本中有静态资源(图片)的 邮件
     * @param toAddress
     * @param title
     * @param rscPath
     * @param rscId
     */
    void sendInlineResourceMail(String toAddress, String title, String content, String rscPath, String rscId);
}

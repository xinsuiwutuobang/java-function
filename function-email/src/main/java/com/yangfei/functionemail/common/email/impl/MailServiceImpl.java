package com.yangfei.functionemail.common.email.impl;

import com.yangfei.functionemail.common.email.MailService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

/**
 * <p>
 *  邮件服务 实现类
 * </p>
 *
 * @RequiredArgsConstructor
 * Required arguments are final fields and fields with constraints such as @NonNull.
 * 代替@Autowired注解，需要注意的是在注入时需要用final定义，或者使用@notnull注解
 *
 * @author yangfei
 * @since 2022/5/18 19:22
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送 纯文本 邮件
     * @param toAddress
     * @param subject
     * @param content
     */
    @Override
    public void sendTextMail(String toAddress, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toAddress);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(from);
        try {
            javaMailSender.send(message);
            log.info("Text 邮件已发送");
        } catch (MailException e) {
            log.error("发送Text邮件异常", e);
        }

    }

    /**
     * 发送 html 邮件
     * @param toAddress
     * @param subject
     * @param content
     */
    @Override
    public void sendHtmlMail(String toAddress, String subject, String content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(ArrayUtil.toArray(StrUtil.splitTrim(toAddress, ","), String.class));
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);
            mimeMessageHelper.setFrom(from);
            javaMailSender.send(mimeMessage);
            log.info("Html邮件已发送");
        } catch (MessagingException e) {
            log.error("发送Html邮件异常", e);
        }

    }

    /**
     * 发送 html模板 邮件
     * @param toAddress
     * @param subject
     * @param template
     * @param params
     */
    @Override
    public void sendHtmlTemplateMail(String toAddress, String subject, String template,
            Map<String, Object> params) {
        Context context = new Context();
        context.setVariables(params);
        String htmlContext = templateEngine.process(template, context);
        sendHtmlMail(toAddress, subject, htmlContext);
    }

    /**
     * 发送 带附件 的邮件
     * @param toAddress
     * @param subject
     * @param content
     * @param filePath
     */
    @Override
    public void sendAttachmentsMail(String toAddress, String subject, String content,
            String filePath) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(toAddress);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content,true);
            mimeMessageHelper.setFrom(from);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            mimeMessageHelper.addAttachment(fileName, file);
            javaMailSender.send(mimeMessage);
            log.info("带附件的邮件已发送");
        } catch (MessagingException e) {
            log.error("发送带附件的邮件时发生异常！", e);
        }

    }

    /**
     * 发送 文本中有静态资源(图片)的 邮件
     * @param toAddress
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    @Override
    public void sendInlineResourceMail(String toAddress, String subject, String content , String rscPath,
            String rscId) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(toAddress);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText("<html><body><img src='cid:" + rscId + "'>" +
                    "<h4>" + content + " says...</h4>" +
                    "<i>" + "ok" + "</i>" +
                    "</body></html>", true);
            mimeMessageHelper.setFrom(from);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            mimeMessageHelper.addInline(rscId, res);
            javaMailSender.send(mimeMessage);
            log.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送嵌入静态资源的邮件时发生异常！", e);
            e.printStackTrace();
        }
    }
}

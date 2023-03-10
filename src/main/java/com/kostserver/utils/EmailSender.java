package com.kostserver.utils;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;


@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
@Component("emailSender")
public class EmailSender {
    private final static Logger log = LoggerFactory.getLogger(EmailSender.class);

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;


    @Value("${spring.mail.sender.name:}")
    private String senderName;

    @Value("${spring.mail.sender.mail:}")
    private String senderEmail;

    @Qualifier("taskExecutor")
    @Autowired
    private TaskExecutor taskExecutor;

    public boolean sendEmail(String email, String subject, String message) {
        MimeMessage mime = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(mime,true);
            Context context = new Context();
            context.setVariable("email",email);
            helper.setFrom(senderEmail,senderName);
            helper.setTo(email);
            helper.setSubject(subject);
            String html = templateEngine.process(message,context);
            helper.setText(html, true);

            javaMailSender.send(mime);

            log.info("Sending Email to : "+email);
            log.info("Sending email from: "+senderEmail);
            log.info("Sending email with subject: "+subject);

            return true;
        }catch (Exception e){
            log.info("error : "+e.getMessage());

            return false;
        }
    }

    public void sendAsync(final String to, final String subject, final String message){
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                sendEmail(to,subject,message);
            }
        });
    }

}

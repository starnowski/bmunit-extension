package com.github.starnowski.bmunit.extension.junit5.spring.demo.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {


    @Value("${mail.sender.host}")
    private String  mailSenderHost;
    @Value("${mail.sender.port}")
    private int  mailSenderPort;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailSenderHost);
        mailSender.setPort(mailSenderPort);

        mailSender.setUsername("no.such.mail@gmail.com");
        mailSender.setPassword("no.such.password");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}

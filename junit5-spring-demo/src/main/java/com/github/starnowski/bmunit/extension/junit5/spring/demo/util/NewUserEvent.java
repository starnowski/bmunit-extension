package com.github.starnowski.bmunit.extension.junit5.spring.demo.util;

import com.github.starnowski.bmunit.extension.junit5.spring.demo.model.MailMessage;

public class NewUserEvent {

    private final MailMessage mailMessage;

    public NewUserEvent(MailMessage mailMessage) {
        this.mailMessage = mailMessage;
    }

    public MailMessage getMailMessage() {
        return mailMessage;
    }
}

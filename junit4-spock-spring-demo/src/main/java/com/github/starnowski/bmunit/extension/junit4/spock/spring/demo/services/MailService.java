package com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.services;

import com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.dto.UserDto;
import com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.util.NewUserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMessageToNewUser(UserDto dto, String emailVerificationHash)
    {
        //TODO
    }

    @Async
    @TransactionalEventListener
    @Transactional
    public void handleNewUserEvent(NewUserEvent newUserEvent)
    {
        try {
            MimeMessage smtpSession = new MimeMessage(javaMailSender.createMimeMessage());
            Message msg = new MimeMessage(smtpSession);
            msg.setFrom(new InternetAddress(newUserEvent.getMailMessage().getMailFrom()));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(newUserEvent.getMailMessage().getMailTo()));
            msg.setSubject(newUserEvent.getMailMessage().getMailSubject());
            msg.setText(newUserEvent.getMailMessage().getMailContent());
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

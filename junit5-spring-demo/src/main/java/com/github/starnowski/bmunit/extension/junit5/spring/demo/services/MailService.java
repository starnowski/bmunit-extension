package com.github.starnowski.bmunit.extension.junit5.spring.demo.services;

import com.github.starnowski.bmunit.extension.junit5.spring.demo.dto.UserDto;
import com.github.starnowski.bmunit.extension.junit5.spring.demo.model.MailMessage;
import com.github.starnowski.bmunit.extension.junit5.spring.demo.repositories.MailMessageRepository;
import com.github.starnowski.bmunit.extension.junit5.spring.demo.util.NewUserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class MailService {

    @Autowired
    private MailMessageRepository mailMessageRepository;
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void sendMessageToNewUser(UserDto dto, String emailVerificationHash)
    {
        MailMessage mailMessage = new MailMessage();
        mailMessage.setMailSubject("New user");
        mailMessage.setMailTo(dto.getEmail());
        mailMessage.setMailContent(emailVerificationHash);
        mailMessageRepository.save(mailMessage);
        applicationEventPublisher.publishEvent(new NewUserEvent(mailMessage));
    }

    @Async
    @TransactionalEventListener
    public void handleNewUserEvent(NewUserEvent newUserEvent)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(newUserEvent.getMailMessage().getMailTo());
        message.setSubject(newUserEvent.getMailMessage().getMailSubject());
        message.setText(newUserEvent.getMailMessage().getMailContent());
        emailSender.send(message);
    }
}

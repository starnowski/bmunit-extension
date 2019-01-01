package com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.services;

import com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.dto.UserDto;
import com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.model.User;
import com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.repositories.UserRepository;
import com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.util.RandomHashGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RandomHashGenerator randomHashGenerator;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserRepository repository;

    @Transactional
    public UserDto registerUser(UserDto dto)
    {
        User user = new User().setEmail(dto.getEmail()).setPassword(passwordEncoder.encode(dto.getPassword())).setEmailVerificationHash(randomHashGenerator.compute());
        user = repository.save(user);
        UserDto response = new UserDto().setId(user.getId()).setEmail(user.getEmail());
        mailService.sendMessageToNewUser(response, user.getEmailVerificationHash());
        return response;
    }
}

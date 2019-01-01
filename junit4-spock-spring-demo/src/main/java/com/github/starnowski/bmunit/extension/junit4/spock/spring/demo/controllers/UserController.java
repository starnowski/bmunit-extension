package com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.controllers;

import com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.dto.UserDto;
import com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @ResponseBody
    @PostMapping("/users")
    public UserDto post(@RequestBody UserDto dto)
    {
        return service.registerUser(dto);
    }
}

package com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.controllers;

import com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.dto.UserDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @ResponseBody
    public UserDto post(@RequestBody UserDto dto)
    {
        //TODO
        return null;
    }
}

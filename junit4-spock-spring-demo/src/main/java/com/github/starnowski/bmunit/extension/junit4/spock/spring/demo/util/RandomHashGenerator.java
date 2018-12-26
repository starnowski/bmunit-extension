package com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RandomHashGenerator {

    public String compute() {
        return UUID.randomUUID().toString();
    }
}

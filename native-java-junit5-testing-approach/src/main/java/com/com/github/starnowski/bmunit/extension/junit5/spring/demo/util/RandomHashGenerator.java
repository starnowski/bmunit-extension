package com.com.github.starnowski.bmunit.extension.junit5.spring.demo.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RandomHashGenerator {

    public String compute() {
        return UUID.randomUUID().toString();
    }
}

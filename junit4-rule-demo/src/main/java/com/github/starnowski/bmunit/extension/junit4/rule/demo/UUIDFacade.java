package com.github.starnowski.bmunit.extension.junit4.rule.demo;

import java.util.UUID;

public class UUIDFacade {

    public static String randomUUIDString()
    {
        return UUID.randomUUID().toString();
    }
}

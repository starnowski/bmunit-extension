package com.github.starnowski.bmunit.extension.junit5.spring.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTest {

    @Test
    public void runApplication()
    {}
}

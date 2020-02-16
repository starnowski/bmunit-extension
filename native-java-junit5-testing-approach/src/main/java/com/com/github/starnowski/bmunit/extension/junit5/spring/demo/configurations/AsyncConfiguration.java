package com.com.github.starnowski.bmunit.extension.junit5.spring.demo.configurations;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AsyncConfiguration implements AsyncConfigurer {

    @Value("${com.github.starnowski.bmunit.extension.junit5.spring.demo.async.pool.threads.number}")
    private int threadsNumber;

    @Bean("taskExecutor")
    @Override
    public Executor getAsyncExecutor () {
        return new ConcurrentTaskExecutor(
                Executors.newFixedThreadPool(threadsNumber));
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler () {
        return (throwable, method, objects) -> {
            System.out.println("-- exception handler -- " + throwable);
            throwable.printStackTrace();
        };
    }
}
package com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.concurrent;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
public class DummyApplicationCountDownLatch implements IApplicationCountDownLatch{

    private CountDownLatch mailServiceCountDownLatch;

    @Override
    public void mailServiceExecuteCountDownInHandleNewUserEventMethod() {
        mailServiceCountDownLatch.countDown();
    }

    @Override
    public void mailServiceWaitForCountDownLatchInHandleNewUserEventMethod(int milliseconds) throws InterruptedException {
        mailServiceCountDownLatch.await(milliseconds, TimeUnit.MILLISECONDS);
    }

    @Override
    public void mailServiceResetCountDownLatchForHandleNewUserEventMethod() {
        mailServiceCountDownLatch = new CountDownLatch(1);
    }
}

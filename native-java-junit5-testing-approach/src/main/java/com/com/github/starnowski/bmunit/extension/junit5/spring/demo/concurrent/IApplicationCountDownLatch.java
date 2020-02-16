package com.com.github.starnowski.bmunit.extension.junit5.spring.demo.concurrent;

public interface IApplicationCountDownLatch {

    void mailServiceExecuteCountDownInHandleNewUserEventMethod();

    void mailServiceWaitForCountDownLatchInHandleNewUserEventMethod(int milliseconds) throws InterruptedException;

    void mailServiceResetCountDownLatchForHandleNewUserEventMethod();

    void mailServiceClearCountDownLatchForHandleNewUserEventMethod();
}

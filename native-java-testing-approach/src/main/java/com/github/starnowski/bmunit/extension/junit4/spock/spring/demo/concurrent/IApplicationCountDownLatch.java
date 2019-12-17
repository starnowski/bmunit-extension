package com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.concurrent;

public interface IApplicationCountDownLatch {

    void mailServiceExecuteCountDownInHandleNewUserEventMethod();

    void mailServiceWaitForCountDownLatchInHandleNewUserEventMethod();

    void mailServiceRestCountDownLatchForHandleNewUserEventMethod();
}

package com.github.starnowski.bmunit.extension.junit5.utils.demo;

import java.util.concurrent.atomic.AtomicInteger;

public class IncrementIntValueThread extends Thread{

    private final AtomicInteger integerValue;


    IncrementIntValueThread(AtomicInteger integerValue) {
        this.integerValue = integerValue;
        setDaemon(true);
    }

    @Override
    public void run() {
        integerValue.incrementAndGet();
    }
}
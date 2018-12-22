package com.github.starnowski.bmunit.extension.junit4.rule.demo;

import java.util.concurrent.atomic.AtomicInteger;

class IncrementIntValueThread extends Thread{

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

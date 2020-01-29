package com.github.starnowski.bmunit.extension.junit5.utils.demo;

import org.jboss.byteman.contrib.bmunit.BMRule;
import org.jboss.byteman.contrib.bmunit.BMRules;
import org.jboss.byteman.contrib.bmunit.WithByteman;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static com.github.starnowski.bmunit.extension.utils.BMUnitUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WithByteman
class IncrementIntValueThreadWithBMUnitExtensionTest {

    @Test
    @BMRule(name = "should wait until all threads completed",
            targetClass = "com.github.starnowski.bmunit.extension.junit5.utils.demo.IncrementIntValueThread",
            targetMethod = "run",
            targetLocation = "AT EXIT",
            action = "joinEnlist(\"IncrementIntValueThreadWithBMUnitRunnerTest.shouldWaitUntilAllThreadsCompleted\")")
    public void shouldWaitUntilAllThreadsCompleted()
    {
        // given
        int expectedCount = 30;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        createJoin("IncrementIntValueThreadWithBMUnitRunnerTest.shouldWaitUntilAllThreadsCompleted", expectedCount);

        // when
        for (int i = 0; i < expectedCount; i++)
        {
            new IncrementIntValueThread(atomicInteger).start();
        }
        joinWait("IncrementIntValueThreadWithBMUnitRunnerTest.shouldWaitUntilAllThreadsCompleted", expectedCount);

        // then
        assertEquals(expectedCount, atomicInteger.get());
    }

    @Test
    @BMRules(rules = {
            @BMRule(name = "should suspend all threads",
                    targetClass = "com.github.starnowski.bmunit.extension.junit5.utils.demo.IncrementIntValueThread",
                    targetMethod = "run",
                    targetLocation = "AT ENTRY",
                    action = "rendezvous(\"IncrementIntValueThreadWithBMUnitRunnerTest.suspendThreadsAtBeginning\")"),
            @BMRule(name = "should wait until all threads completed",
                    targetClass = "com.github.starnowski.bmunit.extension.junit5.utils.demo.IncrementIntValueThread",
                    targetMethod = "run",
                    targetLocation = "AT EXIT",
                    action = "incrementCounter(\"IncrementIntValueThreadWithBMUnitRunnerTest.releaseThreadsCount\");joinEnlist(\"IncrementIntValueThreadWithBMUnitRunnerTest.waitUntilAllThreadsCompleted\")")})
    public void shouldSuspendAllThreadsAtBeginningThenWaitUntilAllThreadsCompleted() {
        // given
        int expectedCount = 30;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        createRendezvous("IncrementIntValueThreadWithBMUnitRunnerTest.suspendThreadsAtBeginning", expectedCount + 1);
        createJoin("IncrementIntValueThreadWithBMUnitRunnerTest.waitUntilAllThreadsCompleted", expectedCount);
        createCounter("IncrementIntValueThreadWithBMUnitRunnerTest.releaseThreadsCount", 0);

        // when
        for (int i = 0; i < expectedCount; i++) {
            new IncrementIntValueThread(atomicInteger).start();
        }
        int threadsStarterBeforeReleaseCount = readCounter("IncrementIntValueThreadWithBMUnitRunnerTest.releaseThreadsCount");
        rendezvous("IncrementIntValueThreadWithBMUnitRunnerTest.suspendThreadsAtBeginning");
        joinWait("IncrementIntValueThreadWithBMUnitRunnerTest.waitUntilAllThreadsCompleted", expectedCount);
        int threadsReleasedCount = readCounter("IncrementIntValueThreadWithBMUnitRunnerTest.releaseThreadsCount");

        // then
        assertEquals(expectedCount, atomicInteger.get());
        assertEquals(0, threadsStarterBeforeReleaseCount);
        assertEquals(expectedCount, threadsReleasedCount);
    }
}
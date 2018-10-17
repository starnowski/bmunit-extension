package st.bmunit.extension.junit4.rule.demo;

import org.jboss.byteman.contrib.bmunit.BMRule;
import org.jboss.byteman.contrib.bmunit.BMRules;
import org.jboss.byteman.contrib.bmunit.BMUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static st.bmunit.extension.utils.BMUnitUtils.*;

@RunWith(BMUnitRunner.class)
public class IncrementIntValueThreadWithBMUnitRunner {

    @Test
    @BMRule(name = "should wait until all threads completed",
            targetClass = "st.bmunit.extension.junit4.rule.demo.IncrementIntValueThread",
            targetMethod = "run",
            targetLocation = "AT EXIT",
            action = "joinEnlist(\"IncrementIntValueThreadWithBMUnitRunner.shouldWaitUntilAllThreadsCompleted\")")
    public void shouldWaitUntilAllThreadsCompleted()
    {
        // given
        int expectedCount = 30;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        createJoin("IncrementIntValueThreadWithBMUnitRunner.shouldWaitUntilAllThreadsCompleted", expectedCount);

        // when
        for (int i = 0; i < expectedCount; i++)
        {
            new IncrementIntValueThread(atomicInteger).start();
        }
        joinWait("IncrementIntValueThreadWithBMUnitRunner.shouldWaitUntilAllThreadsCompleted", expectedCount);

        // then
        assertEquals(expectedCount, atomicInteger.get());
    }

    @Test
    @BMRules(rules = {
            @BMRule(name = "should suspend all threads",
                    targetClass = "st.bmunit.extension.junit4.rule.demo.IncrementIntValueThread",
                    targetMethod = "run",
                    targetLocation = "AT ENTRY",
                    action = "rendezvous(\"IncrementIntValueThreadWithBMUnitRunner.suspendThreadsAtBeginning\")"),
            @BMRule(name = "should wait until all threads completed",
                    targetClass = "st.bmunit.extension.junit4.rule.demo.IncrementIntValueThread",
                    targetMethod = "run",
                    targetLocation = "AT EXIT",
                    action = "incrementCounter(\"IncrementIntValueThreadWithBMUnitRunner.releaseThreadsCount\");joinEnlist(\"IncrementIntValueThreadWithBMUnitRunner.waitUntilAllThreadsCompleted\")")})
    public void shouldSuspendAllThreadsAtBeginningThenWaitUntilAllThreadsCompleted() {
        // given
        int expectedCount = 30;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        createRendezvous("IncrementIntValueThreadWithBMUnitRunner.suspendThreadsAtBeginning", expectedCount + 1);
        createJoin("IncrementIntValueThreadWithBMUnitRunner.waitUntilAllThreadsCompleted", expectedCount);
        createCounter("IncrementIntValueThreadWithBMUnitRunner.releaseThreadsCount", 0);

        // when
        for (int i = 0; i < expectedCount; i++) {
            new IncrementIntValueThread(atomicInteger).start();
        }
        int threadsStarterBeforeReleaseCount = readCounter("IncrementIntValueThreadWithBMUnitRunner.releaseThreadsCount");
        rendezvous("IncrementIntValueThreadWithBMUnitRunner.suspendThreadsAtBeginning");
        joinWait("IncrementIntValueThreadWithBMUnitRunner.waitUntilAllThreadsCompleted", expectedCount);
        int threadsReleasedCount = readCounter("IncrementIntValueThreadWithBMUnitRunner.releaseThreadsCount");

        // then
        assertEquals(expectedCount, atomicInteger.get());
        assertEquals(0, threadsStarterBeforeReleaseCount);
        assertEquals(expectedCount, threadsReleasedCount);
    }
}

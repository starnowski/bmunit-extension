package st.bmunit.extension;

import org.jboss.byteman.contrib.bmunit.BMRule;
import org.jboss.byteman.contrib.bmunit.BMRules;
import org.junit.Rule;
import org.junit.Test;
import st.bmunit.extension.junit4.rule.BMUnitMethodRule;
import st.bmunit.extension.util.IncrementIntValueThread;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static st.bmunit.extension.utils.BMUnitUtils.*;

public class IncrementIntValueThreadWithBMUnitMethodRule {

    @Rule
    public BMUnitMethodRule bmUnitMethodRule = new BMUnitMethodRule();

    @Test
    @BMRule(name = "should wait until all threads completed",
            targetClass = "st.bmunit.extension.util.IncrementIntValueThread",
            targetMethod = "run",
            targetLocation = "AT EXIT",
            action = "joinEnlist(\"IncrementIntValueThreadWithBMUnitMethodRule.shouldWaitUntilAllThreadsCompleted\")")
    public void shouldWaitUntilAllThreadsCompleted() {
        // given
        int expectedCount = 30;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        createJoin("IncrementIntValueThreadWithBMUnitMethodRule.shouldWaitUntilAllThreadsCompleted", expectedCount);

        // when
        for (int i = 0; i < expectedCount; i++) {
            new IncrementIntValueThread(atomicInteger).start();
        }
        joinWait("IncrementIntValueThreadWithBMUnitMethodRule.shouldWaitUntilAllThreadsCompleted", expectedCount, 60000);

        // then
        assertEquals(expectedCount, atomicInteger.get());
    }

    @Test
    @BMRules(rules = {
            @BMRule(name = "should suspend all threads",
                    targetClass = "st.bmunit.extension.util.IncrementIntValueThread",
                    targetMethod = "run",
                    targetLocation = "AT ENTRY",
                    action = "rendezvous(\"IncrementIntValueThreadWithBMUnitMethodRule.suspendThreadsAtBeginning\")"),
            @BMRule(name = "should wait until all threads completed",
                    targetClass = "st.bmunit.extension.util.IncrementIntValueThread",
                    targetMethod = "run",
                    targetLocation = "AT EXIT",
                    action = "incrementCounter(\"IncrementIntValueThreadWithBMUnitMethodRule.releaseThreadsCount\");joinEnlist(\"IncrementIntValueThreadWithBMUnitMethodRule.waitUntilAllThreadsCompleted\")")})
    public void shouldSuspendAllThreadsAtBeginningThenWaitUntilAllThreadsCompleted() {
        // given
        int expectedCount = 30;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        createRendezvous("IncrementIntValueThreadWithBMUnitMethodRule.suspendThreadsAtBeginning", expectedCount + 1);
        createJoin("IncrementIntValueThreadWithBMUnitMethodRule.waitUntilAllThreadsCompleted", expectedCount);
        createCounter("IncrementIntValueThreadWithBMUnitMethodRule.releaseThreadsCount", 0);

        // when
        for (int i = 0; i < expectedCount; i++) {
            new IncrementIntValueThread(atomicInteger).start();
        }
        int threadsStarterBeforeReleaseCount = readCounter("IncrementIntValueThreadWithBMUnitMethodRule.releaseThreadsCount");
        rendezvous("IncrementIntValueThreadWithBMUnitMethodRule.suspendThreadsAtBeginning");
        joinWait("IncrementIntValueThreadWithBMUnitMethodRule.waitUntilAllThreadsCompleted", expectedCount, 60000);
        int threadsReleasedCount = readCounter("IncrementIntValueThreadWithBMUnitMethodRule.releaseThreadsCount");

        // then
        assertEquals(expectedCount, atomicInteger.get());
        assertEquals(0, threadsStarterBeforeReleaseCount);
        assertEquals(expectedCount, threadsReleasedCount);
    }
}

package st.bmunit.extension.junit4.rule.demo;

import com.github.starnowski.bmunit.extension.junit4.rule.BMUnitMethodRule;
import org.jboss.byteman.contrib.bmunit.BMRule;
import org.jboss.byteman.contrib.bmunit.BMRules;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static st.bmunit.extension.utils.BMUnitUtils.*;

public class IncrementIntValueThreadWithBMUnitMethodRuleTest {

    @Rule
    public BMUnitMethodRule bmUnitMethodRule = new BMUnitMethodRule();

    @Test
    @BMRule(name = "should wait until all threads completed",
            targetClass = "st.bmunit.extension.junit4.rule.demo.IncrementIntValueThread",
            targetMethod = "run",
            targetLocation = "AT EXIT",
            action = "joinEnlist(\"IncrementIntValueThreadWithBMUnitMethodRuleTest.shouldWaitUntilAllThreadsCompleted\")")
    public void shouldWaitUntilAllThreadsCompleted() {
        // given
        int expectedCount = 30;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        createJoin("IncrementIntValueThreadWithBMUnitMethodRuleTest.shouldWaitUntilAllThreadsCompleted", expectedCount);

        // when
        for (int i = 0; i < expectedCount; i++) {
            new IncrementIntValueThread(atomicInteger).start();
        }
        joinWait("IncrementIntValueThreadWithBMUnitMethodRuleTest.shouldWaitUntilAllThreadsCompleted", expectedCount, 60000);

        // then
        assertEquals(expectedCount, atomicInteger.get());
    }

    @Test
    @BMRules(rules = {
            @BMRule(name = "should suspend all threads",
                    targetClass = "st.bmunit.extension.junit4.rule.demo.IncrementIntValueThread",
                    targetMethod = "run",
                    targetLocation = "AT ENTRY",
                    action = "rendezvous(\"IncrementIntValueThreadWithBMUnitMethodRuleTest.suspendThreadsAtBeginning\")"),
            @BMRule(name = "should wait until all threads completed",
                    targetClass = "st.bmunit.extension.junit4.rule.demo.IncrementIntValueThread",
                    targetMethod = "run",
                    targetLocation = "AT EXIT",
                    action = "incrementCounter(\"IncrementIntValueThreadWithBMUnitMethodRuleTest.releaseThreadsCount\");joinEnlist(\"IncrementIntValueThreadWithBMUnitMethodRuleTest.waitUntilAllThreadsCompleted\")")})
    public void shouldSuspendAllThreadsAtBeginningThenWaitUntilAllThreadsCompleted() {
        // given
        int expectedCount = 30;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        createRendezvous("IncrementIntValueThreadWithBMUnitMethodRuleTest.suspendThreadsAtBeginning", expectedCount + 1);
        createJoin("IncrementIntValueThreadWithBMUnitMethodRuleTest.waitUntilAllThreadsCompleted", expectedCount);
        createCounter("IncrementIntValueThreadWithBMUnitMethodRuleTest.releaseThreadsCount", 0);

        // when
        for (int i = 0; i < expectedCount; i++) {
            new IncrementIntValueThread(atomicInteger).start();
        }
        int threadsStarterBeforeReleaseCount = readCounter("IncrementIntValueThreadWithBMUnitMethodRuleTest.releaseThreadsCount");
        rendezvous("IncrementIntValueThreadWithBMUnitMethodRuleTest.suspendThreadsAtBeginning");
        joinWait("IncrementIntValueThreadWithBMUnitMethodRuleTest.waitUntilAllThreadsCompleted", expectedCount, 60000);
        int threadsReleasedCount = readCounter("IncrementIntValueThreadWithBMUnitMethodRuleTest.releaseThreadsCount");

        // then
        assertEquals(expectedCount, atomicInteger.get());
        assertEquals(0, threadsStarterBeforeReleaseCount);
        assertEquals(expectedCount, threadsReleasedCount);
    }
}
